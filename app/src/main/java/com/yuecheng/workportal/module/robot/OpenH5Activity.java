package com.yuecheng.workportal.module.robot;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yuecheng.workportal.QRCActivity;
import com.yuecheng.workportal.R;
import com.yuecheng.workportal.base.BaseActivity;
import com.yuecheng.workportal.bean.MessageEvent;
import com.yuecheng.workportal.common.JsApi;
import com.yuecheng.workportal.common.JsEchoApi;
import com.yuecheng.workportal.module.robot.bean.TalkBean;
import com.yuecheng.workportal.module.robot.presenter.MainPresenter;
import com.yuecheng.workportal.module.robot.view.IMainView;
import com.yuecheng.workportal.utils.LoadViewUtil;
import com.yuecheng.workportal.utils.StringUtils;
import com.yuecheng.workportal.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import wendu.dsbridge.DWebView;

import static com.yuecheng.workportal.common.JsApi.SCAN_REQUEST_CODE;


public class OpenH5Activity extends BaseActivity implements IMainView {
    public static Button vitalSigns;
    @BindView(R.id.back_iv)
    ImageView back_iv;

    @BindView(R.id.head)
    RelativeLayout head;

    @BindView(R.id.open_h5_relative)
    RelativeLayout openH5Relative;

    @BindView(R.id.vital_signs)
    Button vital_signs;

    private MainPresenter mIMainPresenter;
    private DWebView mWebView;
    private TextView title;
    private String url;
    private String name;
    private TextView mTimerTV;
    private TextView mStateTV;
    private ImageView mStateIV;
    private ImageView getmStateIV;
    private PopupWindow mRecordWindow;
    private ObjectAnimator icon_anim;
    private Boolean isShow = true;
    private LoadViewUtil viewUtil;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.open_h5);
        ButterKnife.bind(this);
        context = this;
        url = getIntent().getStringExtra("url");
        name = getIntent().getStringExtra("name");
        //init presenter
        mIMainPresenter = new MainPresenter(this);
        mIMainPresenter.setIsAdd(false);//设置录入生命体征的时候不展示在对话列表中
        initView();
        initEvent();
    }

    private void initView() {
        this.viewUtil = LoadViewUtil.init(getWindow().getDecorView(), context);
        vitalSigns = findViewById(R.id.vital_signs);
        title = findViewById(R.id.title);
        if(!StringUtils.isEmpty(name)){
            head.setVisibility(View.VISIBLE);
            title.setText(name);
            back_iv.setOnClickListener(view -> finish());
        }else {
            head.setVisibility(View.GONE);
        }

        mWebView = findViewById(R.id.webview);
        DWebView.setWebContentsDebuggingEnabled(true);
        mWebView.addJavascriptObject(new JsApi(this), "");
        mWebView.addJavascriptObject(new JsEchoApi(), "echo");
//        mWebView.loadUrl("file:///android_asset/BridgeWebView/js-call-native.html");
        mWebView.setWebViewClient(new WebViewClient(){
            //覆写shouldOverrideUrlLoading实现内部显示网页
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                //这里进行无网络或错误处理，具体可以根据errorCode的值进行判断，做跟详细的处理。
                viewUtil.showLoadingErrorView(LoadViewUtil.LOADING_ERROR_VIEW, () -> {
                    viewUtil.startLoading();
                    mWebView.reload();//刷新
                });
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                viewUtil.startLoading();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                viewUtil.stopLoading();
            }
        });
        mWebView.loadUrl(url);//动态获取需要打开的链接
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initEvent() {
        vitalSigns.setOnTouchListener((v, event) -> {
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

    //使用Webview的时候，返回键没有重写的时候会直接关闭程序，这时候其实我们要其执行的知识回退到上一步的操作
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //这是一个监听用的按键的方法，keyCode 监听用户的动作，如果是按了返回键，同时Webview要返回的话，WebView执行回退操作，因为mWebView.canGoBack()返回的是一个Boolean类型，所以我们把它返回为true
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            if (keyCode == KeyEvent.KEYCODE_BACK && isShow) {
                isShowVoiceDialog(false);
            }
            mWebView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
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

    //显示提醒
    public void showTipView() {
        View view = View.inflate(this, R.layout.popup_audio_wi_vo, null);
        mStateIV = view.findViewById(R.id.rc_audio_state_image);
        getmStateIV = view.findViewById(R.id.rc_audio_state);
        mStateTV = view.findViewById(R.id.rc_audio_state_text);
        mTimerTV = view.findViewById(R.id.rc_audio_timer);
        mRecordWindow = new PopupWindow(view, -1, -1);
        mRecordWindow.showAtLocation(openH5Relative, 17, 0, 0);
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
    public void isShowVoiceDialog(boolean isShow) {
        if (isShow) {
            vital_signs.setVisibility(View.VISIBLE);
        } else {
            vital_signs.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent) {
        if (messageEvent.type == MessageEvent.SMTZLU_VOCE_DIALOG) {
            isShow = messageEvent.isShow;
            isShowVoiceDialog(messageEvent.isShow);
        } else if (messageEvent.type == MessageEvent.VITAL_SIGNS_JSON) {
            ToastUtil.info(OpenH5Activity.this, messageEvent.json);
            //将获取的生命体征的json传递给H5
            mWebView.callHandler("append", new String[]{messageEvent.json});
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        mIMainPresenter.setIsAdd(true);
    }

    @Override
    public void updateList(List<TalkBean> talkBeanList) {

    }

    //随声音大小改变语音图标
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == SCAN_REQUEST_CODE && data != null) {
            String result = data.getStringExtra(QRCActivity.RESULT_QRCODE_STRING);
            if (!TextUtils.isEmpty(result)) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int rid = jsonObject.optInt("rid");
                    mWebView.callHandler("getuserinfo", new String[]{String.valueOf(rid)});
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
