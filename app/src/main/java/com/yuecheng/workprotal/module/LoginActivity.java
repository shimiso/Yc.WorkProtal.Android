package com.yuecheng.workprotal.module;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.yuecheng.workprotal.MainActivity;
import com.yuecheng.workprotal.R;
import com.yuecheng.workprotal.base.BaseActivity;
import com.yuecheng.workprotal.bean.User;
import com.yuecheng.workprotal.db.DaoManager;
import com.yuecheng.workprotal.greendao.UserDao;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends BaseActivity{
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
        userDao =  DaoManager.getInstance(this).getUserDao();
    }

    @OnClick({ R.id.sign_in_button})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sign_in_button:
                User user = new User();
                user.setName("王二麻");
                user.setAddress("四川成都");
                user.setAge(36);
                user.setSex("男");
                userDao.insert(user);
                List<User> list=userDao.loadAll();

                startActivity( new Intent(this, MainActivity.class));
                finish();
        }
    }
}

