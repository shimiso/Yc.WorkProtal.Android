package com.yuecheng.workprotal.myandroid.robot.main.view;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.yuecheng.workprotal.R;
import com.yuecheng.workprotal.myandroid.robot.bean.TalkBean;
import com.yuecheng.workprotal.myandroid.robot.main.adapter.TalkListAdapter;
import com.yuecheng.workprotal.myandroid.robot.main.presenter.IMainPresenter;
import com.yuecheng.workprotal.myandroid.robot.main.presenter.MainPresenter;

import java.util.ArrayList;
import java.util.List;


public class VoiceActivity extends AppCompatActivity implements IMainView {


    private IMainPresenter mIMainPresenter;
    private List<TalkBean> mTalkBeanList = new ArrayList<>();
    private TalkListAdapter mTalkListAdapter;
    private DrawerLayout mDrawerLayout;
    private TextView tvVoiceType;
    private TextView tvVoiceSpeed;
    private TextView tvVersion;
    private RelativeLayout rlVoiceSpeed;
    private RecyclerView recyclerView;
    private ImageView iv_talk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        afterView();
        iv_talk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIMainPresenter.startVoiceRobot();
            }
        });

    }

    private void init() {
        // mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        tvVoiceType = (TextView) findViewById(R.id.tv_voice_type);
        tvVoiceSpeed = (TextView) findViewById(R.id.tv_voice_speed);
        tvVersion = (TextView) findViewById(R.id.tv_version);
        rlVoiceSpeed = (RelativeLayout) findViewById(R.id.rl_voice_speed);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        iv_talk = (ImageView) findViewById(R.id.iv_talk);
    }

    private void afterView() {
        //init Toolbar
        // this.setSupportActionBar(mToolbar);

        //DrawerLayout bind ActionBarDrawerToggle
//        toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
//                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        mDrawerLayout.addDrawerListener(toggle);
//        toggle.syncState();

        //init RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mTalkListAdapter = new TalkListAdapter(mTalkBeanList, this);
        recyclerView.setAdapter(mTalkListAdapter);

        //init presenter
        mIMainPresenter = new MainPresenter(this);
    }



//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
////        if (mDrawerLayout != null) {
////            mDrawerLayout.removeDrawerListener(toggle);
////        }
//
//    }

    @Override
    public void updateVoiceType(String type) {
        if (tvVoiceType != null) {
            tvVoiceType.setText(type);
        }
    }

    @Override
    public void updateVoiceSpeed(String speed) {
        if (tvVoiceSpeed != null) {
            tvVoiceSpeed.setText(speed);
        }
    }

    @Override
    public void updateVersion(String version) {
        if (tvVersion != null) {
            tvVersion.setText(version);
        }
    }

    @Override
    public void updateList(List<TalkBean> talkBeanList) {
        mTalkBeanList.clear();
        mTalkBeanList.addAll(talkBeanList);
        mTalkListAdapter.notifyDataSetChanged();
        recyclerView.smoothScrollToPosition(mTalkBeanList.size() - 1);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }
}
