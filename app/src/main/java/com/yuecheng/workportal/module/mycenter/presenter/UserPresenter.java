package com.yuecheng.workportal.module.mycenter.presenter;

import android.app.Activity;
import android.content.Context;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yuecheng.workportal.bean.LoginUser;
import com.yuecheng.workportal.bean.ResultInfo;
import com.yuecheng.workportal.bean.SsoToken;
import com.yuecheng.workportal.callback.DialogCallback;
import com.yuecheng.workportal.common.CommonPostView;
import com.yuecheng.workportal.common.UrlConstant;
import com.yuecheng.workportal.db.DaoManager;
import com.yuecheng.workportal.greendao.LoginUserDao;
import com.yuecheng.workportal.bean.LoginUser;
import com.yuecheng.workportal.bean.ResultInfo;
import com.yuecheng.workportal.bean.SsoToken;
import com.yuecheng.workportal.callback.DialogCallback;
import com.yuecheng.workportal.common.CommonPostView;
import com.yuecheng.workportal.common.UrlConstant;
import com.yuecheng.workportal.db.DaoManager;
import com.yuecheng.workportal.greendao.LoginUserDao;

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
        this.loginUserDao =  DaoManager.getInstance(context).getUserDao();
    }

    public LoginUser getUser(String username) {
        LoginUser loginUser=loginUserDao.queryBuilder()
                .where(LoginUserDao.Properties.Username.eq(username))
                .build().unique();
        if(loginUser==null)
            loginUser = new LoginUser();
        return loginUser;
    }

    public void login(String username,String password,final CommonPostView<SsoToken> commonPostView){
        OkGo.<String>post(UrlConstant.SSO_LOGIN)//
                .tag(this)//
                .params("username", username)//用户名
                .params("password", password)//密码
                .params("client_id", "ro.client")//接入方客户ID
                .params("client_secret", "secret")//接入方客户密码
                .params("grant_type", "password")//授权类型固定不可修改
                .params("scopes", "StaffInfo")//请求的资源范围
                .execute(new DialogCallback<String>(context) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        int responseCode = response.getRawResponse().code();
                        String result = new String(response.body());
                        if(responseCode==200){
                            try {
                                ResultInfo<SsoToken> resultInfo = new ResultInfo<SsoToken>();
                                Gson gson = new Gson();
                                SsoToken ssoToken = gson.fromJson(result, SsoToken.class);
                                resultInfo.result = ssoToken;

                                LoginUser user =getUser(username);
                                user.setExpires_in(ssoToken.getExpires_in());
                                user.setAccess_token(ssoToken.getAccess_token());
                                user.setToken_type(ssoToken.getToken_type());
                                user.setUsername(username);
                                user.setPassword(password);
                                LoginUserDao loginUserDao =  DaoManager.getInstance(context).getUserDao();
                                loginUserDao.save(user);
                                commonPostView.postSuccess(resultInfo);
                            } catch (Exception e) {
                                e.printStackTrace();
                                commonPostView.postError("服务器发生未知异常");
                            }
                        }else if(responseCode ==400){
                            commonPostView.postError(result);
                        }else {
                            commonPostView.postError("服务器发生未知异常");
                        }
                    }

                    @Override
                    public void onError(Response<String> response){
                        commonPostView.postError("发生未知异常");
                    }
                });
    }
}
