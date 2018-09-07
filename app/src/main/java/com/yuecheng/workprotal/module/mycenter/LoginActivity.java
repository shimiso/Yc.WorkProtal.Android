package com.yuecheng.workprotal.module.mycenter;

import android.content.Context;
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
import com.yuecheng.workprotal.bean.ResultInfo;
import com.yuecheng.workprotal.bean.SsoToken;
import com.yuecheng.workprotal.bean.User;
import com.yuecheng.workprotal.common.CommonPostView;
import com.yuecheng.workprotal.module.contacts.presenter.ContactsPresenter;
import com.yuecheng.workprotal.module.contacts.quicksearch.Bean.ContactBean;
import com.yuecheng.workprotal.module.mycenter.presenter.UserPresenter;
import com.yuecheng.workprotal.utils.StringUtils;
import com.yuecheng.workprotal.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.password_text)
    TextView password_text;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.obtain_text)
    TextView obtain_text;
    @BindView(R.id.dynamic_password)
    TextView dynamic_password;
    @BindView(R.id.login_btn)
    TextView loginBut;
    private boolean isPassword = false;
    private TimeCount time;
    private UserPresenter userPresenter;
    private ContactsPresenter contactsPresenter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
        time = new TimeCount(60000, 1000);
        userPresenter = new UserPresenter(this);
        contactsPresenter = new ContactsPresenter(this);
        context = this;
        initEvent();

    }

    @OnClick({R.id.login_btn,R.id.obtain_text})
    protected void onClick(View view) {
        switch (view.getId()){
            case R.id.login_btn://登陆
                String username = phone.getText().toString();
                String passWD = password.getText().toString();
                if(StringUtils.isEmpty(username)){
                    ToastUtil.error(context,"用户名或手机号不能为空");
                    return;
                }
                if(StringUtils.isEmpty(passWD)){
                    ToastUtil.error(context,"密码不能为空");
                    return;
                }
                userPresenter.login(username,passWD,new CommonPostView<SsoToken>() {
                    @Override
                    public void postSuccess(ResultInfo<SsoToken> resultInfo) {
//                        User user = new User();
//                        user.setName("王二麻");
//                        user.setAddress("四川成都");
//                        user.setAge(36);
//                        user.setSex("男");
//                        userDao.insert(user);
//                        List<User> list=userDao.loadAll();

                        startActivity(new Intent(context, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void postError(String errorMsg) {
                        ToastUtil.error(context,errorMsg);
                    }
                });
                break;
            case R.id.obtain_text://验证码
                time.start();
                break;
        }
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
