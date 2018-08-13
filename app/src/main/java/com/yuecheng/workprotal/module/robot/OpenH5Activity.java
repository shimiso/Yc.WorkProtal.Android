package com.yuecheng.workprotal.module.robot;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.yuecheng.workprotal.R;
import com.yuecheng.workprotal.base.BaseActivity;
import com.yuecheng.workprotal.widget.BridgeWebView;


public class OpenH5Activity extends BaseActivity {

    private BridgeWebView mWebView;
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
        setWebViewClient();
    }

    private void initView() {
        title = findViewById(R.id.title);
        title.setText(name);
        mWebView = findViewById(R.id.weview);
        findViewById(R.id.back_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mWebView.addBridgeInterface(new JavaSctiptMethods(OpenH5Activity.this, mWebView));//设置js和android通信桥梁方法
       // mWebView.loadUrl("file:///android_asset/BridgeWebView/index.html");//本地模板
        mWebView.loadUrl(url);//动态获取需要打开的链接

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
