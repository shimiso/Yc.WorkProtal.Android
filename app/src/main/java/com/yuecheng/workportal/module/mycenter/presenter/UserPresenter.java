package com.yuecheng.workportal.module.mycenter.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.yuecheng.workportal.bean.LoginUser;
import com.yuecheng.workportal.bean.ResultInfo;
import com.yuecheng.workportal.bean.SsoToken;
import com.yuecheng.workportal.common.CommonPostView;
import com.yuecheng.workportal.common.UrlConstant;
import com.yuecheng.workportal.db.DaoManager;
import com.yuecheng.workportal.greendao.LoginUserDao;
import com.yuecheng.workportal.module.contacts.bean.PersonnelDetailsBean;
import com.yuecheng.workportal.utils.StringUtils;

import org.json.JSONObject;

import java.util.List;

/**
 * 描述:
 * 作者: Shims
 * 时间: ${date} ${time}
 */
public class UserPresenter {
    private Context context;
    LoginUserDao loginUserDao;

    public UserPresenter(Context context) {
        this.context = context;
        this.loginUserDao = DaoManager.getInstance(context).getUserDao();
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
                                commonPostView.postError("服务器发生未知异常");
                            }
                        } else if (responseCode == 400) {


                            commonPostView.postError(result);
                        } else {
                            commonPostView.postError("服务器发生未知异常");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        commonPostView.postError("发生未知异常");
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
                                commonPostView.postError("服务器返回数据异常");
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
                            commonPostView.postError("服务器发生未知异常");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        commonPostView.postError("发生未知异常");
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
