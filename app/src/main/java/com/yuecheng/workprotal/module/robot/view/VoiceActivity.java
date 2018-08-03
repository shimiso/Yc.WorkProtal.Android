package com.yuecheng.workprotal.module.robot.view;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.yuecheng.workprotal.R;
import com.yuecheng.workprotal.module.robot.bean.TalkBean;
import com.yuecheng.workprotal.module.robot.adapter.TalkListAdapter;
import com.yuecheng.workprotal.module.robot.presenter.MainPresenter;
import com.yuecheng.workprotal.module.robot.service.MusicService;
import com.yuecheng.workprotal.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;


public class VoiceActivity extends AppCompatActivity implements IMainView {


    private MainPresenter mIMainPresenter;
    private List<TalkBean> mTalkBeanList = new ArrayList<>();
    private TalkListAdapter mTalkListAdapter;
    private DrawerLayout mDrawerLayout;
    private RecyclerView recyclerView;
    private ImageView iv_talk;
    private Button voice_btn;
    private EditText et_msg;
    public static VoiceActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.robot);
        instance=this;
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

        et_msg.setImeOptions(EditorInfo.IME_ACTION_SEND);
        et_msg.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        et_msg.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String s = et_msg.getText().toString();
                if (actionId == EditorInfo.IME_ACTION_SEND && !StringUtils.isEmpty(s)){
                    mIMainPresenter.understandText(s);
                    et_msg.setText("");
                    //Toast.makeText(VoiceActivity.this,et_msg.getText().toString(),Toast.LENGTH_LONG).show();
                    return true;
                }
                return false;
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
        //设置item间距，30dp
        recyclerView.addItemDecoration(new SpaceItemDecoration(30));
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
