package com.yuecheng.workportal.common;

import android.os.CountDownTimer;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.yuecheng.workportal.MainApplication;
import com.yuecheng.workportal.bean.LoginUser;
import com.yuecheng.workportal.module.robot.OpenH5Activity;
import com.yuecheng.workportal.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import io.rong.eventbus.EventBus;
import wendu.dsbridge.CompletionHandler;

/**
 * 描述: JS调用原生API
 * 作者: Shims
 * 时间: 2018/9/14 15:20
 */
public class JsApi {
    private OpenH5Activity activity;
    public JsApi(OpenH5Activity activity) {
        this.activity = activity;
    }
    //同步API
    @JavascriptInterface
    public String testSyn(Object msg)  {
        //将当前token传给H5页面
        LoginUser loginUser = MainApplication.getApplication().getLoginUser();
        return loginUser.getAccess_token();
    }
    //同步API
    @JavascriptInterface
    public static String vital_signs(Object msg)  {
        //返回json
        return "";
    }
    //异步API
    @JavascriptInterface
    public void testAsyn(Object msg, CompletionHandler<String> handler){
        activity.finish();
        //handler.complete(msg+" [ asyn call]");
    }

    @JavascriptInterface
    public void isShowbut(Object msg, CompletionHandler<String> handler){
            activity.showYuYin(true);
//        ToastUtil.info(activity,"111111");
//        EventBus.getDefault().post(isShow);
//        OpenH5Activity openH5Activity = new OpenH5Activity();
//        openH5Activity.refresh();

        //handler.complete(msg+" [ asyn call]");
    }

    @JavascriptInterface
    public String testNoArgSyn(Object arg) throws JSONException {
        return  "testNoArgSyn called [ syn call]";
    }

    @JavascriptInterface
    public void testNoArgAsyn(Object arg, CompletionHandler<String> handler) {
        handler.complete( "testNoArgAsyn   called [ asyn call]");
    }


    //@JavascriptInterface
    //without @JavascriptInterface annotation can't be called
    public String testNever(Object arg) throws JSONException {
        JSONObject jsonObject= (JSONObject) arg;
        return jsonObject.getString("msg") + "[ never call]";
    }

    @JavascriptInterface
    public void callProgress(Object args, final CompletionHandler<Integer> handler) {

        new CountDownTimer(11000, 1000) {
            int i=10;
            @Override
            public void onTick(long millisUntilFinished) {
                //setProgressData can be called many times util complete be called.
                handler.setProgressData((i--));

            }
            @Override
            public void onFinish() {
                //complete the js invocation with data; handler will be invalid when complete is called
                handler.complete(0);

            }
        }.start();
    }
}