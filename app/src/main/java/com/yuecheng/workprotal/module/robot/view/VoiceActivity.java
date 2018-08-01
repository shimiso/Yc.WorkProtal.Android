package com.yuecheng.workprotal.module.robot.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.yuecheng.workprotal.MainApplication;
import com.yuecheng.workprotal.R;
import com.yuecheng.workprotal.module.robot.OpenH5Activity;
import com.yuecheng.workprotal.module.robot.bean.TalkBean;
import com.yuecheng.workprotal.module.robot.adapter.TalkListAdapter;
import com.yuecheng.workprotal.module.robot.presenter.IMainPresenter;
import com.yuecheng.workprotal.module.robot.presenter.MainPresenter;
import com.yuecheng.workprotal.module.robot.service.MusicService;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;


public class VoiceActivity extends AppCompatActivity implements IMainView {


    private IMainPresenter mIMainPresenter;
    private List<TalkBean> mTalkBeanList = new ArrayList<>();
    private TalkListAdapter mTalkListAdapter;
    private DrawerLayout mDrawerLayout;
    private RecyclerView recyclerView;
    private ImageView iv_talk;
    private Button voice_btn;
    private EditText et_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.robot);

        init();
        afterView();
        voice_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIMainPresenter.startVoiceRobot();
            }
        });

    }

    private void init() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
       // iv_talk = (ImageView) findViewById(R.id.iv_talk);
        voice_btn = (Button) findViewById(R.id.voice_btn);
        et_msg = (EditText) findViewById(R.id.et_msg);
        findViewById(R.id.back_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;
    }
    private void afterView() {

        //init RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mTalkListAdapter = new TalkListAdapter(mTalkBeanList, this);
        recyclerView.setAdapter(mTalkListAdapter);

        //init presenter
        mIMainPresenter = new MainPresenter(this);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        MusicService.stopService(this);
    }



    @Override
    public void updateList(List<TalkBean> talkBeanList) {
        mTalkBeanList.clear();
        mTalkBeanList.addAll(talkBeanList);
        mTalkListAdapter.notifyDataSetChanged();
        recyclerView.smoothScrollToPosition(mTalkBeanList.size() - 1);
    }
}
