package com.yuecheng.workprotal.module.mycenter.presenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yuecheng.workprotal.bean.ResultInfo;
import com.yuecheng.workprotal.bean.SsoToken;
import com.yuecheng.workprotal.callback.DialogCallback;
import com.yuecheng.workprotal.common.CommonPostView;
import com.yuecheng.workprotal.common.UrlConstant;

/**
 * 描述:
 * 作者: Shims
 * 时间: ${date} ${time}
 */
public class UserPresenter {
    private Activity activity;
    public UserPresenter(Activity activity) {
        this.activity = activity;
    }

    public void login(String username,String password,final CommonPostView<SsoToken> commonPostView){
        OkGo.<String>post(UrlConstant.SSO_LOGIN)//
                .tag(this)//
                .params("username", username)//用户名
                .params("password", password)//密码
                .params("client_id", "ro.client")//接入方客户ID
                .params("client_secret", "secret")//接入方客户密码
                .params("grant_type", "password")//授权类型固定不可修改
                .params("scopes", "api1")//请求的资源范围
                .execute(new DialogCallback<String>(activity) {
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
