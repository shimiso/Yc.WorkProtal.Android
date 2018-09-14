package com.yuecheng.workportal.module.mycenter.view;

import android.os.Bundle;
import android.view.View;
import com.yuecheng.workportal.R;
import com.yuecheng.workportal.base.BaseActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 修改密码
 */
public class ModifyPasswordActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_modify_password);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back_iv,R.id.save_btn})
    protected void onClick(View view){
        switch (view.getId()){
            case R.id.back_iv://返回
                finish();
                break;
            case R.id.save_btn://保存
                break;
        }
    }
}
