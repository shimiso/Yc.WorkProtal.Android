package com.yuecheng.workportal;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yuecheng.workportal.base.BaseActivity;
import com.yuecheng.workportal.bean.LoginUser;
import com.yuecheng.workportal.bean.ResultInfo;
import com.yuecheng.workportal.common.CommonPostView;
import com.yuecheng.workportal.module.contacts.presenter.ContactsPresenter;
import com.yuecheng.workportal.module.mycenter.presenter.UserPresenter;
import com.yuecheng.workportal.utils.StringUtils;
import com.yuecheng.workportal.utils.TagAliasOperatorHelper;
import com.yuecheng.workportal.utils.ToastUtil;
import com.yuecheng.workportal.widget.LoadingDialog;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yuecheng.workportal.utils.TagAliasOperatorHelper.ACTION_SET;
import static com.yuecheng.workportal.utils.TagAliasOperatorHelper.sequence;
import static com.yuecheng.workportal.utils.TagAliasOperatorHelper.TagAliasBean;


public class LoginActivity extends BaseActivity {

    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.phone_text)
    TextView phone_text;
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
    LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setFullFullscreen();//全屏
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initlanguage();//初始化语言
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
                String username = this.username.getText().toString();
                String passWD = password.getText().toString();
                if(StringUtils.isEmpty(username)){
                    ToastUtil.error(context,"用户名或手机号不能为空");
                    return;
                }
                if(StringUtils.isEmpty(passWD)){
                    ToastUtil.error(context,"密码不能为空");
                    return;
                }
                loadingDialog = LoadingDialog.createDialog(context);
                loadingDialog.show();
                userPresenter.login(username,passWD,new CommonPostView<LoginUser>() {
                    @Override
                    public void postSuccess(ResultInfo<LoginUser> resultInfo) {
                        LoginUser loginUser = resultInfo.getResult();
                        userPresenter.identity(loginUser, new CommonPostView<LoginUser>() {
                            @Override
                            public void postSuccess(ResultInfo<LoginUser> resultInfo) {
                                TagAliasBean tagAliasBean = new TagAliasBean();
                                tagAliasBean.action = ACTION_SET;
                                tagAliasBean.alias = resultInfo.getResult().getGuid().replaceAll("-","");
                                tagAliasBean.isAliasAction = true;
                                TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(), sequence, tagAliasBean);

                                loadingDialog.dismiss();
                                spUtil.setCurrentUserName(username);
                                startActivity(new Intent(context, MainActivity.class));
                                finish();
                            }

                            @Override
                            public void postError(String errorMsg) {
                                loadingDialog.dismiss();
                                ToastUtil.error(context,errorMsg);
                            }
                        });

                    }

                    @Override
                    public void postError(String errorMsg) {
                        loadingDialog.dismiss();
                        ToastUtil.error(context,errorMsg);
                    }
                });
                break;
            case R.id.obtain_text://验证码
                time.start();
                break;
        }
    }

    private void initlanguage() {
        String language = spUtil.getStrings("Language", "中文");
        if (language.equals("中文")) {
            // 获得res资源对象
            Resources resources = getResources();
            Configuration config = resources.getConfiguration();
            // 获得屏幕参数：主要是分辨率，像素等。
            DisplayMetrics dm = resources.getDisplayMetrics();
            // 英语
            config.locale = Locale.CHINA;
            resources.updateConfiguration(config, dm);

        } else {
            // 获得res资源对象
            Resources resources = getResources();
            Configuration config = resources.getConfiguration();
            // 获得屏幕参数：主要是分辨率，像素等。
            DisplayMetrics dm = resources.getDisplayMetrics();
            // 英语
            config.locale = Locale.US;
            resources.updateConfiguration(config, dm);

        }
    }
    private void initEvent() {
        dynamic_password.setOnClickListener(v -> {

            if (!isPassword) {
                phone_text.setText("手机号");
                username.setHint("请输入手机号");
                password_text.setText("短信验证码");
                password.setHint("请输入短信验证码");
                password.setText("");
                dynamic_password.setText("账号密码登录");
                obtain_text.setVisibility(View.VISIBLE);
                isPassword = true;
            } else {
                phone_text.setText("用户名");
                username.setHint("请输入用户名");
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
