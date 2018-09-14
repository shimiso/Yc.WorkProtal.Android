package com.yuecheng.workportal.module.robot;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.yuecheng.workportal.R;
import com.yuecheng.workportal.base.BaseActivity;
import com.yuecheng.workportal.common.JsApi;
import com.yuecheng.workportal.common.JsEchoApi;

import wendu.dsbridge.DWebView;


public class OpenH5Activity extends BaseActivity {

    private DWebView mWebView;
    private TextView title;
    private String url;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_h5);
        url = getIntent().getStringExtra("url");
        name = getIntent().getStringExtra("name");
        initView();
    }

    private void initView() {
        title = findViewById(R.id.title);
        title.setText(name);
        mWebView = findViewById(R.id.webview);
        DWebView.setWebContentsDebuggingEnabled(true);
        mWebView.addJavascriptObject(new JsApi(this), "");
        mWebView.addJavascriptObject(new JsEchoApi(),"echo");
       // mWebView.loadUrl("file:///android_asset/BridgeWebView/js-call-native.html");
        mWebView.loadUrl(url);//动态获取需要打开的链接

    }


    //使用Webview的时候，返回键没有重写的时候会直接关闭程序，这时候其实我们要其执行的知识回退到上一步的操作
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //这是一个监听用的按键的方法，keyCode 监听用户的动作，如果是按了返回键，同时Webview要返回的话，WebView执行回退操作，因为mWebView.canGoBack()返回的是一个Boolean类型，所以我们把它返回为true
        if(keyCode== KeyEvent.KEYCODE_BACK&&mWebView.canGoBack()){
            mWebView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
