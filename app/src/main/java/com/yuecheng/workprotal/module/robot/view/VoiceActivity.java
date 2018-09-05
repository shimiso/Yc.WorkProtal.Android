package com.yuecheng.workprotal.module.robot.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.jude.swipbackhelper.SwipeBackHelper;
import com.yuecheng.workprotal.R;
import com.yuecheng.workprotal.module.robot.bean.TalkBean;
import com.yuecheng.workprotal.module.robot.adapter.TalkListAdapter;
import com.yuecheng.workprotal.module.robot.manager.XunfeiManager;
import com.yuecheng.workprotal.module.robot.presenter.MainPresenter;
import com.yuecheng.workprotal.module.robot.service.MusicService;
import com.yuecheng.workprotal.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;


public class VoiceActivity extends AppCompatActivity implements IMainView {

    LinearLayout mLlRoot;
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
        lateralDestroy();
        instance=this;
        init();
        afterView();
    }

    //侧滑销毁
    private void lateralDestroy() {
        SwipeBackHelper.onCreate(this);
        SwipeBackHelper.getCurrentPage(this)
                .setSwipeBackEnable(true)
                .setSwipeSensitivity(0.5f)
                .setSwipeRelateEnable(true)
                .setSwipeRelateOffset(300);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        mLlRoot = (LinearLayout) findViewById(R.id.activity_main);
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
//        voice_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mIMainPresenter.startVoiceRobot();
//            }
//        });

        voice_btn.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    showTipView();
                    mIMainPresenter.startVoiceRobot();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (isCancelled(v, event)) {
                        setCancelTipView();
                    } else {
                        setRecordingTipView();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    XunfeiManager.getInstance().cancelVoiceDictation();
                    destroyTipView();
                    break;
            }
            return false;
        });
    }

    private TextView mTimerTV;
    private TextView mStateTV;
    private ImageView mStateIV;
    private PopupWindow mRecordWindow;

    public void showTipView() {
        View view = View.inflate(this, R.layout.popup_audio_wi_vo, null);
        mStateIV = view.findViewById(R.id.rc_audio_state_image);
        mStateTV = view.findViewById(R.id.rc_audio_state_text);
        mTimerTV = view.findViewById(R.id.rc_audio_timer);
        mRecordWindow = new PopupWindow(view, -1, -1);
        mRecordWindow.showAtLocation(mLlRoot, 17, 0, 0);
        mRecordWindow.setFocusable(true);
        mRecordWindow.setOutsideTouchable(false);
        mRecordWindow.setTouchable(false);
    }

    public void setTimeoutTipView(int counter) {
        if (this.mRecordWindow != null) {
            this.mStateIV.setVisibility(View.GONE);
            this.mStateTV.setVisibility(View.VISIBLE);
            this.mStateTV.setText(R.string.voice_rec);
            this.mStateTV.setBackgroundResource(R.drawable.bg_voice_popup);
            this.mTimerTV.setText(String.format("%s", new Object[]{Integer.valueOf(counter)}));
            this.mTimerTV.setVisibility(View.VISIBLE);
        }
    }

    //提示：手指上滑，取消发送
    public void setRecordingTipView() {
        if (this.mRecordWindow != null) {
            this.mStateIV.setVisibility(View.VISIBLE);
            this.mStateIV.setImageResource(R.mipmap.ic_volume_1);
            this.mStateTV.setVisibility(View.VISIBLE);
            this.mStateTV.setText(R.string.voice_rec);
            this.mStateTV.setBackgroundResource(R.drawable.bg_voice_popup);
            this.mTimerTV.setVisibility(View.GONE);
        }
    }

    //录音时间太短
    public void setAudioShortTipView() {
        if (this.mRecordWindow != null) {
            mStateIV.setImageResource(R.mipmap.ic_volume_wraning);
            mStateTV.setText(R.string.voice_short);
        }
    }

    //取消提醒
    public void setCancelTipView() {
        if (this.mRecordWindow != null) {
            this.mTimerTV.setVisibility(View.GONE);
            this.mStateIV.setVisibility(View.VISIBLE);
            this.mStateIV.setImageResource(R.mipmap.ic_volume_cancel);
            this.mStateTV.setVisibility(View.VISIBLE);
            this.mStateTV.setText(R.string.voice_cancel);
            this.mStateTV.setBackgroundResource(R.drawable.corner_voice_style);
        }
    }

    //销毁提示
    public void destroyTipView() {
        if (this.mRecordWindow != null) {
            this.mRecordWindow.dismiss();
            this.mRecordWindow = null;
            this.mStateIV = null;
            this.mStateTV = null;
            this.mTimerTV = null;
        }
    }

    private boolean isCancelled(View view, MotionEvent event) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);

        if (event.getRawX() < location[0] || event.getRawX() > location[0] + view.getWidth()
                || event.getRawY() < location[1] - 40) {
            return true;
        }

        return false;
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
        SwipeBackHelper.onDestroy(this);
    }

    @Override
    public void updateList(List<TalkBean> talkBeanList) {
        mTalkBeanList.clear();
        mTalkBeanList.addAll(talkBeanList);
        mTalkListAdapter.notifyDataSetChanged();
        recyclerView.smoothScrollToPosition(mTalkBeanList.size() - 1);
    }
}
