package com.yuecheng.workportal.module.mycenter.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.yuecheng.workportal.MainApplication;
import com.yuecheng.workportal.R;
import com.yuecheng.workportal.bean.ErrorInfo;
import com.yuecheng.workportal.bean.LoginUser;
import com.yuecheng.workportal.bean.ResultInfo;
import com.yuecheng.workportal.bean.SsoToken;
import com.yuecheng.workportal.common.CommonPostView;
import com.yuecheng.workportal.common.Constants;
import com.yuecheng.workportal.common.UrlConstant;
import com.yuecheng.workportal.db.DaoManager;
import com.yuecheng.workportal.greendao.LoginUserDao;
import com.yuecheng.workportal.module.contacts.bean.PersonnelDetailsBean;
import com.yuecheng.workportal.utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.yuecheng.workportal.common.Constants.INVALID_USERNAME_OR_PASSWORD;

/**
 * 描述:
 * 作者: Shims
 * 时间: ${date} ${time}
 */
public class UserPresenter {
    private Context context;
    LoginUserDao loginUserDao;
    HashMap<String,String> errorMaps ;

    public UserPresenter(Context context) {
        this.context = context;
        this.loginUserDao = DaoManager.getInstance(context).getUserDao();
        this.errorMaps= new HashMap<String,String>(){{
            put(INVALID_USERNAME_OR_PASSWORD,context.getString(R.string.invalid_username_or_password));
        }};
    }

    public LoginUser getUser(String username) {
        LoginUser loginUser = loginUserDao.queryBuilder()
                .where(LoginUserDao.Properties.Username.eq(username))
                .build().unique();
        return loginUser;
    }

    public void login(String username, String password, final CommonPostView<LoginUser> commonPostView) {
        OkGo.<String>post(UrlConstant.SSO_LOGIN)//
                .tag(this)//
                .params("username", username)//用户名
                .params("password", password)//密码
                .params("client_id", "ro.client")//接入方客户ID
                .params("client_secret", "secret")//接入方客户密码
                .params("grant_type", "password")//授权类型固定不可修改
                .params("scopes", "StaffInfo")//请求的资源范围
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        int responseCode = response.getRawResponse().code();
                        String result = new String(response.body());
                        if (responseCode == 200) {
                            try {
                                ResultInfo<LoginUser> resultInfo = new ResultInfo<>();
                                Gson gson = new Gson();
                                SsoToken ssoToken = gson.fromJson(result, SsoToken.class);
                                LoginUser loginUser = new LoginUser();
                                loginUser.setExpires_in(ssoToken.getExpires_in());
                                loginUser.setAccess_token(ssoToken.getAccess_token());
                                loginUser.setToken_type(ssoToken.getToken_type());
                                loginUser.setUsername(username);
                                loginUser.setPassword(password);

                                resultInfo.result = loginUser;
                                //获取个人信息
                                commonPostView.postSuccess(resultInfo);
                            } catch (Exception e) {
                                e.printStackTrace();
                                commonPostView.postError(context.getString(R.string.server_net_error));
                            }
                        } else if (responseCode == 400) {
                            Gson gson = new Gson();
                            ErrorInfo errorInfo = gson.fromJson(result, ErrorInfo.class);
                            if(errorInfo.getError().equals(Constants.INVALID_GRANT)&&!StringUtils.isEmpty(errorInfo.getError_description())){
                                String errorMsg =errorMaps.get(errorInfo.getError_description());
                                commonPostView.postError(errorMsg);

                            }else {
                                commonPostView.postError(context.getString(R.string.server_net_error));
                            }
                        } else {
                            commonPostView.postError(context.getString(R.string.server_net_error));
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        commonPostView.postError(context.getString(R.string.server_net_error));
                    }
                });
    }


    /**
     * 获取个人信息
     * @param loginUser
     * @param commonPostView
     */
    public void identity(LoginUser loginUser, final CommonPostView<LoginUser> commonPostView) {
        OkGo.<String>get(UrlConstant.SSO_IDENTITY)//
                .tag(this)//
                .headers("Authorization", "Bearer "+loginUser.getAccess_token())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String json = new String(response.body());
                        try {
                            ResultInfo<LoginUser> resultInfo = new ResultInfo<>();
                            Gson gson = new Gson();
                            JSONObject jsonObj = new JSONObject(json);
                            JSONObject resultObj = jsonObj.getJSONObject("result");
                            PersonnelDetailsBean person = gson.fromJson(resultObj.toString(), PersonnelDetailsBean.class);
                            if(StringUtils.isEmpty(person.getRongCloudToken())){
                                commonPostView.postError(context.getString(R.string.server_net_error));
                            }else {
                                loginUser.setUserId(person.getId()+"");
                                loginUser.setCode(person.getCode());
                                loginUser.setGuid(person.getGuid());
                                loginUser.setStaffGrade(person.getStaffGrade());
                                loginUser.setEmail(person.getEmail());
                                loginUser.setGender(person.getGender());
                                loginUser.setMobilePhone(person.getMobilePhone());
                                loginUser.setName(person.getName());
                                loginUser.setOrganizationName(person.getOrganizationName());
                                loginUser.setPartTimeJobs(person.getPartTimeJobs());
                                loginUser.setPositionName(person.getPositionName());
                                loginUser.setRongCloudToken(person.getRongCloudToken());
                                loginUser.setUserIcon( "http://rongcloud-web.qiniudn.com/docs_demo_rongcloud_logo.png");//默认头像

                                resultInfo.result = loginUser;
                                saveLoginUser(loginUser);

                                commonPostView.postSuccess(resultInfo);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            commonPostView.postError(context.getString(R.string.server_net_error));
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        commonPostView.postError(context.getString(R.string.server_net_error));
                    }
                });
    }

    /**
     * 上传头像
     * @param files
     * @param commonPostView
     */
    public void uploadFile(ArrayList<File> files, CommonPostView commonPostView) {

        OkGo.<String>post(UrlConstant.UPLOAD)//
                .tag(this)//
                .addFileParams("files", files)//
               // .headers("Authorization", "Bearer "+loginUser.getAccess_token())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String json = new String(response.body());
                        try {
                            ResultInfo<List<String>> resultInfo = new ResultInfo<>();
                            Gson gson = new Gson();
                            JSONObject jsonObj = new JSONObject(json);
                            JSONObject resultObj = jsonObj.getJSONObject("result");
                            JSONArray fileNames = resultObj.getJSONArray("fileNames");
                            List<String> checkAccount = gson.fromJson(fileNames.toString(), new TypeToken<List<String>>(){}.getType());
                            resultInfo.result = checkAccount;
                            commonPostView.postSuccess(resultInfo);

                        } catch (Exception e) {
                            e.printStackTrace();
                            commonPostView.postError(context.getString(R.string.server_net_error));
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        commonPostView.postError(context.getString(R.string.server_net_error));
                    }
                });
    }
    /**
     * 打卡
     * @param commonPostView
     */
    public void clockIn(String clockTime,String address,boolean intraArea,String memo,final CommonPostView<Boolean> commonPostView) {
        LoginUser loginUser = MainApplication.getApplication().getLoginUser();
        OkGo.<String>post(UrlConstant.CLOCK_IN)//
                .tag(this)//
                .headers("Authorization", "Bearer "+loginUser.getAccess_token())
                .params("clockTime",clockTime)
                .params("address",address)
                .params("intraArea",intraArea)
                .params("memo",memo)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String json = new String(response.body());
                        try {
                            ResultInfo<Boolean> resultInfo = new ResultInfo<>();
                            JSONObject jsonObj = new JSONObject(json);
                            JSONObject resultObj = jsonObj.getJSONObject("result");
                            boolean state = resultObj.optBoolean("state");
                            resultInfo.result = state;
                            commonPostView.postSuccess(resultInfo);

                        } catch (Exception e) {
                            e.printStackTrace();
                            commonPostView.postError(context.getString(R.string.server_net_error));
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        commonPostView.postError(context.getString(R.string.server_net_error));
                    }
                });
    }
    public void saveLoginUser(LoginUser loginUser){
        List<LoginUser> list;
        try {
            list = loginUserDao.queryBuilder()
                    .where(LoginUserDao.Properties.Username.eq(loginUser.getUsername()))
                    .build().list();
            if(list!=null){
                loginUserDao.deleteInTx(list);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        loginUserDao.save(loginUser);
    }
}
