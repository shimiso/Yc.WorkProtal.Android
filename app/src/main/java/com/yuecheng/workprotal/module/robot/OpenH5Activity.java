package com.yuecheng.workprotal.module.robot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;

import com.yuecheng.workprotal.R;
import com.yuecheng.workprotal.widget.BridgeWebView;


public class OpenH5Activity extends AppCompatActivity {

    private BridgeWebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_h5);
        initView();
        setWebViewClient();
    }

    private void initView() {
        mWebView = (BridgeWebView) findViewById(R.id.weview);

        mWebView.addBridgeInterface(new JavaSctiptMethods(OpenH5Activity.this, mWebView));//设置js和android通信桥梁方法
        mWebView.loadUrl("file:///android_asset/BridgeWebView/index.html");//本地模板

    }

    private void setWebViewClient() {
        mWebView.setWebViewClient(new WebViewClient());

        mWebView.setWebChromeClient(new WebChromeClient());
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
