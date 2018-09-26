/**
 * @author yinxuejian
 * @version 创建时间：2014-7-7 下午12:04:31
 */

package com.yuecheng.workportal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.yuecheng.workportal.base.BaseActivity;


/**
 * 类的说明：登陆失效.
 * 作者：shims
 */
public class InvalidateActivity extends BaseActivity implements OnClickListener {
    public static final String INVALIDATE_MESSAGE = "invalidate_message";
    protected MainApplication mainApplication;
    private Button reLoginBtn, exitBtn;//重新登录和退出按钮
    Intent lastIntent;
    private String message;//超时提示信息
    private TextView tv_message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setFullFullscreen();//全屏
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invalid_dialog);
        lastIntent = getIntent();
        message = lastIntent.getStringExtra(INVALIDATE_MESSAGE);
        mainApplication = MainApplication.getApplication();
        initView();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.relogin_btn:
                mainApplication.relogin();
                finish();
                break;
            case R.id.exit_btn:
                mainApplication.exit();
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {

    }

    public void initView() {
        reLoginBtn =findViewById(R.id.relogin_btn);
        exitBtn = findViewById(R.id.exit_btn);
        reLoginBtn.setOnClickListener(this);
        exitBtn.setOnClickListener(this);

        tv_message = findViewById(R.id.message);
        if (!TextUtils.isEmpty(message)) {
            tv_message.setText(message);
        }
    }
    @Override
    public void finish() {
        super.finish();//对话框形式的Activity，添加结束页面动画，从底部消失
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_bottom_out);
    }
}
