package com.yuecheng.workportal.module.mycenter.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yuecheng.workportal.R;
import com.yuecheng.workportal.base.BaseActivity;
import com.yuecheng.workportal.common.UrlConstant;
import com.yuecheng.workportal.module.robot.OpenH5Activity;
import com.yuecheng.workportal.module.robot.view.VoiceActivity;
import com.yuecheng.workportal.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HRActivity extends BaseActivity {

    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.join_company)
    TextView joinCompany;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_center_hr);
        ButterKnife.bind(this);
        context = this;
        joinCompany.setText(getString(R.string.join_company_one) + "100" + getString(R.string.join_company_two));
    }


    @OnClick({R.id.back_iv, R.id.title_voice,R.id.wdzl, R.id.xzcx, R.id.htcx, R.id.kqcx, R.id.jqcx,R.id.jbjl,R.id.flexible_pool})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.title_voice:
                startActivity(new Intent(this, VoiceActivity.class));
                //使其由下向上弹出
                overridePendingTransition(R.anim.push_bottom_in, R.anim.push_bottom_out);
                break;
            case R.id.wdzl://我的资料
                openWebView(UrlConstant.MY_PROFILE,"我的资料",null);
                break;
            case R.id.xzcx://薪资查询
                showValidationDialog(UrlConstant.SALARY_QUERY_HR,"薪资查询");
                break;
            case R.id.htcx://合同查询
                showValidationDialog(UrlConstant.CONTRACT,"合同查询");
                break;
            case R.id.kqcx://考勤查询
                startActivity(new Intent(this, WorkAttendanceActivity.class));
                break;
            case R.id.jqcx://假期查询
                openWebView(UrlConstant.HOLIDAY_QUERY,"假期查询","请个假");
                break;
            case R.id.flexible_pool://flexible_pool
                openWebView(UrlConstant.FLEXIBLE_POOL,"Flexible Pool",null);
                break;
            case R.id.jbjl://加班记录
                openWebView(UrlConstant.OVER_TIME,"加班记录",null);
                break;
        }
    }

    protected void openWebView(String url,String title,String right_button){
        Intent intent = new Intent(context, OpenH5Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        intent.putExtra("right_button_text", right_button);
        startActivity(intent);
    }

    public void showValidationDialog(String url,String name){
        ValidationDialog validationDialog = new ValidationDialog(this);
        validationDialog.showDialog();
        validationDialog.setClicklistener(new ValidationDialog.ClickListenerInterface() {
            @Override
            public void onCancleClick() {
                validationDialog.dismissDialog();
            }

            @Override
            public void onConfirmClick() {
                String password = validationDialog.et_dialog_one.getText().toString();
                if(password.equals("123")){
                    openWebView(url,name,null);
                }else{
                    ToastUtil.info(HRActivity.this,"密码错误！");
                }

                validationDialog.dismissDialog();
            }
        });
    }

}
