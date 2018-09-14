package com.yuecheng.workportal.module.contacts.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.yuecheng.workportal.R;
import com.yuecheng.workportal.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectGroupActivity extends BaseActivity {

    @BindView(R.id.yuecheng_img)
    ImageView yuechengImg;
    @BindView(R.id.bcis_img)
    ImageView bcisImg;
    @BindView(R.id.laonian_img)
    ImageView laonianImg;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_select_group);
        ButterKnife.bind(this);
        intent = new Intent();
        Intent intent = getIntent();
        String selectname = intent.getStringExtra("selectname");
        if(selectname == null){
            selectname = "空";
        }
        switch (selectname){
            case "乐成集团":
                yuechengImg.setVisibility(View.VISIBLE);
                bcisImg.setVisibility(View.GONE);
                laonianImg.setVisibility(View.GONE);
                break;
            case "BCIS":
                yuechengImg.setVisibility(View.GONE);
                bcisImg.setVisibility(View.VISIBLE);
                laonianImg.setVisibility(View.GONE);
                break;
            default:
                yuechengImg.setVisibility(View.GONE);
                bcisImg.setVisibility(View.GONE);
                laonianImg.setVisibility(View.VISIBLE);
                break;
        }
    }

    @OnClick({R.id.back_iv, R.id.yuecheng_rl, R.id.bcis_rl, R.id.laonian_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.yuecheng_rl:
                yuechengImg.setVisibility(View.VISIBLE);
                bcisImg.setVisibility(View.GONE);
                laonianImg.setVisibility(View.GONE);
                intent.putExtra("selectname", "yuecheng");
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.bcis_rl:
                yuechengImg.setVisibility(View.GONE);
                bcisImg.setVisibility(View.VISIBLE);
                laonianImg.setVisibility(View.GONE);
                intent.putExtra("selectname", "bcis");
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.laonian_rl:
                yuechengImg.setVisibility(View.GONE);
                bcisImg.setVisibility(View.GONE);
                laonianImg.setVisibility(View.VISIBLE);
                intent.putExtra("selectname", "laonian");
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
