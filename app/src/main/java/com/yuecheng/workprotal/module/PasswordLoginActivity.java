package com.yuecheng.workprotal.module;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yuecheng.workprotal.MainActivity;
import com.yuecheng.workprotal.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PasswordLoginActivity extends AppCompatActivity {

    @BindView(R.id.password_text)
    TextView password_text;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.obtain_text)
    TextView obtain_text;
    @BindView(R.id.dynamic_password)
    TextView dynamic_password;
    @BindView(R.id.login_but)
    TextView loginBut;
    private boolean isPassword = false;
    private TimeCount time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login1);
        ButterKnife.bind(this);
        time = new TimeCount(60000, 1000);

        initEvent();
    }

    private void initEvent() {
        dynamic_password.setOnClickListener(v -> {

            if (!isPassword) {
                password_text.setText("短信验证码");
                password.setHint("请输入短信验证码");
                password.setText("");
                dynamic_password.setText("账号密码登录");
                obtain_text.setVisibility(View.VISIBLE);
                isPassword = true;
            } else {
                password_text.setText("密码");
                password.setHint("请输入密码");
                password.setText("");
                dynamic_password.setText("动态密码登录");
                obtain_text.setVisibility(View.GONE);
                isPassword = false;
            }

        });
        obtain_text.setOnClickListener(v -> {
            time.start();
        });
        loginBut.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            obtain_text.setTextColor(Color.parseColor("#cccccc"));
            obtain_text.setClickable(false);
            obtain_text.setText("(" + millisUntilFinished / 1000 + ") 秒后可重新发送");
        }

        @Override
        public void onFinish() {
            obtain_text.setTextColor(Color.parseColor("#ff0000"));
            obtain_text.setText("重新获取验证码");
            obtain_text.setClickable(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在Activity销毁时停止防止内存泄露
        if (time != null) {
            time.cancel();
        }
    }
}
