package com.yuecheng.workprotal.module;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.yuecheng.workprotal.MainActivity;
import com.yuecheng.workprotal.R;
import com.yuecheng.workprotal.base.BaseActivity;
import com.yuecheng.workprotal.bean.User;
import com.yuecheng.workprotal.db.DaoManager;
import com.yuecheng.workprotal.greendao.UserDao;
import com.yuecheng.workprotal.widget.NinePointLockView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends BaseActivity {
    UserDao userDao;
    @BindView(R.id.nplv_lock_view)
    NinePointLockView nplvLockView;
    private String password = "0124678";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
        userDao = DaoManager.getInstance(this).getUserDao();

        nplvLockView.setOnDrawLickPathListener(new NinePointLockView.OnDrawLockPathListener() {
            @Override
            public boolean onDrawPathFinish(List<Integer> pathList) {
                if (pathList != null) {
                    StringBuilder builder = new StringBuilder();
                    for (Integer integer : pathList) {
                        builder.append(integer);
                    }
                    //本地存储了密码，进行匹配
                    if (!TextUtils.equals(password, builder.toString())) {  //密码匹配失败，显示错误路径，本地密码置空
                        Toast.makeText(LoginActivity.this, "密码错误，已清空密码，请重置，当前路径：" + builder.toString(), Toast.LENGTH_SHORT).show();
                        // password = null;
                        return false;
                    } else {    //密码匹配成功
                        Toast.makeText(LoginActivity.this, "密码正确" + builder.toString(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                        return true;
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "请至少连接4个点", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        });
    }

    @OnClick({R.id.sign_in_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                User user = new User();
                user.setName("王二麻");
                user.setAddress("四川成都");
                user.setAge(36);
                user.setSex("男");
                userDao.insert(user);
                List<User> list = userDao.loadAll();

                startActivity(new Intent(this, MainActivity.class));
                finish();
        }
    }
}

