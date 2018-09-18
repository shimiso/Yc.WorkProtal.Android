package com.yuecheng.workportal.module.robot.view;

import android.animation.AnimatorSet;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jude.swipbackhelper.SwipeBackHelper;
import com.yuecheng.workportal.module.robot.adapter.TalkListAdapter;
import com.yuecheng.workportal.module.robot.presenter.MainPresenter;
import com.yuecheng.workportal.module.robot.service.MusicService;
import com.yuecheng.workportal.R;
import com.yuecheng.workportal.module.robot.adapter.TalkListAdapter;
import com.yuecheng.workportal.module.robot.bean.TalkBean;
import com.yuecheng.workportal.module.robot.presenter.MainPresenter;
import com.yuecheng.workportal.module.robot.service.MusicService;
import com.yuecheng.workportal.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class VoiceActivity extends AppCompatActivity implements IMainView {

    LinearLayout mLlRoot;

    @BindView(R.id.voice_title)
    RelativeLayout voiceTitle;
    @BindView(R.id.touming)
    RelativeLayout touming;
    private MainPresenter mIMainPresenter;
    private List<TalkBean> mTalkBeanList = new ArrayList<>();
    private TalkListAdapter mTalkListAdapter;
    private RecyclerView recyclerView;
    private ImageView iv_talk;
    private Button voice_btn;
    private EditText et_msg;
    public static VoiceActivity instance = null;
    private TextView mTimerTV;
    private TextView mStateTV;
    private ImageView mStateIV;
    private ImageView getmStateIV;
    private PopupWindow mRecordWindow;
    private ObjectAnimator icon_anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.robot);
        ButterKnife.bind(this);
        lateralDestroy();
        instance = this;
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
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
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
                if (actionId == EditorInfo.IME_ACTION_SEND && !StringUtils.isEmpty(s)) {
                    mIMainPresenter.understandText(s);
                    et_msg.setText("");
                    //Toast.makeText(VoiceActivity.this,et_msg.getText().toString(),Toast.LENGTH_LONG).show();
                    return true;
                }
                return false;
            }
        });

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
                    if (isCancelled(v, event)) {
                        mIMainPresenter.cancelVoiceRobot();
                        destroyTipView();
                    } else {
                        mIMainPresenter.stopVoiceRobot();
                        setRecognizationTipView();
                    }

                    break;
            }
            return false;
        });
    }

    @Override
    public void setVoiceChanged(int volume) {
        if (this.mStateIV == null) return;
        switch (volume / 4) {
            case 0:
                this.mStateIV.setImageResource(R.mipmap.ic_volume_1);
                break;
            case 1:
                this.mStateIV.setImageResource(R.mipmap.ic_volume_2);
                break;
            case 2:
                this.mStateIV.setImageResource(R.mipmap.ic_volume_3);
                break;
            case 3:
                this.mStateIV.setImageResource(R.mipmap.ic_volume_4);
                break;
            case 4:
                this.mStateIV.setImageResource(R.mipmap.ic_volume_5);
                break;
            case 5:
                this.mStateIV.setImageResource(R.mipmap.ic_volume_6);
                break;
            case 6:
                this.mStateIV.setImageResource(R.mipmap.ic_volume_7);
                break;
            default:
                this.mStateIV.setImageResource(R.mipmap.ic_volume_8);
        }
    }

    //显示提醒
    public void showTipView() {
        View view = View.inflate(this, R.layout.popup_audio_wi_vo, null);
        mStateIV = view.findViewById(R.id.rc_audio_state_image);
        getmStateIV = view.findViewById(R.id.rc_audio_state);
        mStateTV = view.findViewById(R.id.rc_audio_state_text);
        mTimerTV = view.findViewById(R.id.rc_audio_timer);
        mRecordWindow = new PopupWindow(view, -1, -1);
        mRecordWindow.showAtLocation(mLlRoot, 17, 0, 0);
        mRecordWindow.setFocusable(true);
        mRecordWindow.setOutsideTouchable(false);
        mRecordWindow.setTouchable(false);
    }

    //识别中
    public void setRecognizationTipView() {
        if (this.mRecordWindow != null) {
            this.mTimerTV.setVisibility(View.GONE);
            this.mStateTV.setVisibility(View.VISIBLE);
            this.mStateTV.setText("正在识别中...");
            this.mStateTV.setBackgroundResource(R.drawable.bg_voice_popup);
            mStateIV.setVisibility(View.GONE);
            this.getmStateIV.setImageResource(R.mipmap.waiting);
            //
            icon_anim = ObjectAnimator.ofFloat(getmStateIV, "rotation", 0.0F, 360f);
            icon_anim.setRepeatCount(-1);
            icon_anim.setDuration(1000);
            icon_anim.setInterpolator(new LinearInterpolator()); //设置匀速旋转，不卡顿
            icon_anim.start();

        }
    }

    //手指上滑，取消发送
    public void setRecordingTipView() {
        if (this.mRecordWindow != null) {
            this.mStateIV.setVisibility(View.VISIBLE);
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
    @Override
    public void destroyTipView() {
        if (this.mRecordWindow != null) {
            mStateIV.setVisibility(View.VISIBLE);
            this.mRecordWindow.dismiss();
            this.mRecordWindow = null;
            this.mStateIV = null;
            this.mStateTV = null;
            this.mTimerTV = null;
        }
    }

    @Override
    public void showYuYin(boolean isShow) {

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

    @OnClick({R.id.voice_sl, R.id.touming, R.id.dhys, R.id.dbgz, R.id.xjhy, R.id.view_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.voice_sl:
                
                voiceTitle.setVisibility(View.VISIBLE);
//                ObjectAnimator animator = ObjectAnimator.ofFloat(touming, "scaleY", 1f, 0f);
//                animator.setDuration(2000);
//                animator.start();
                touming.setVisibility(View.GONE);
                break;
            case R.id.touming:
                finish();
                break;
            case R.id.dhys:
                break;
            case R.id.dbgz:
                break;
            case R.id.xjhy:
                break;
            case R.id.view_more:
                startActivity(new Intent(this,ViewMoreActivity.class));
                break;
        }
    }


}
