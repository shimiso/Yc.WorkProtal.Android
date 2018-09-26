package com.yuecheng.workportal.module.update;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.yuecheng.workportal.R;
import com.yuecheng.workportal.base.BaseActivity;
import com.yuecheng.workportal.utils.ToastUtil;

/**
 *
 * 版本更新弹出层.
 * @author 史明松
 */
public class VersionUpdateDialog extends BaseActivity implements OnClickListener {

	/**** 更新对话框 ***/
	private View updateDialog;
	private Button okBtn;
	private Button cannelBtn;
	private TextView messageV;
	private TextView tvTitle;
	private View linev;

	/** 下载DownloadBinder **/
	protected ApkDownloadService.DownloadBinder downloadBinder;
	/** 下载Service是否绑定 **/
	protected boolean downServiceIsBinded;

	/** 下载进度对话框 **/
	private LinearLayout downProgressDialog;
	/** 下载进度对话框进度条 **/
	private ProgressBar downBar;
	/** 下载进度对话框 信息 **/
	private TextView downBarMsg;

	private Version newVersion;
	private View retryLine;
	/** 重试和退出按钮载体 **/
	private LinearLayout retryLayout;
	/** 重试 **/
	private Button retryBtn;
	/** 退出 **/
	private Button exitBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setFullFullscreen();//全屏
		super.onCreate(savedInstanceState);
		setContentView(R.layout.version_update);
		newVersion = (Version) getIntent().getSerializableExtra("VERSION");
		initViews();
		initDataAndListener();
	}

	private void initDataAndListener() {
		messageV.setText(newVersion.getUpdateInfo());// 可能会改变
		if (newVersion.isForcedUpdate()) {
			linev.setVisibility(View.GONE);
			cannelBtn.setVisibility(View.GONE);
		} else {
			cannelBtn.setOnClickListener(arg0 -> finish());
		}
		okBtn.setOnClickListener(arg0 -> {
            updateDialog.setVisibility(View.GONE);
            downProgressDialog.setVisibility(View.VISIBLE);

            Toast.makeText(VersionUpdateDialog.this, "正在下载中，请稍候...", Toast.LENGTH_SHORT).show();
            String saveFileName =  "medicalnurse" + newVersion.getVersionName() + "(build"+newVersion.getVersionCode()+").apk";

            Intent it = new Intent(VersionUpdateDialog.this, ApkDownloadService.class);
            it.putExtra("apkUrl", newVersion.getApkUrl());
            it.putExtra("destFileName", saveFileName);
			it.putExtra("destFileDir", spUtil.getUpdatePath());

            bindService(it, conn, BIND_AUTO_CREATE);

        });
	}

	private void initViews() {
		updateDialog = findViewById(R.id.update_dialog);
		okBtn = findViewById(R.id.yes_update_btn);
		cannelBtn = findViewById(R.id.no_update_btn);
		linev = findViewById(R.id.fengx1);
		messageV = findViewById(R.id.message);
		tvTitle = findViewById(R.id.title);
		tvTitle.setText(getResources().getString(R.string.update_dialog_title) + newVersion.getVersionName());

		downProgressDialog = findViewById(R.id.down_dialog);
		downBar = findViewById(R.id.down_progressbar);
		downBarMsg = findViewById(R.id.id_tv_loadingmsg);
		messageV.setMovementMethod(ScrollingMovementMethod.getInstance());
		retryLine = findViewById(R.id.retry_line);
		retryLayout = findViewById(R.id.retry_layout);
		retryBtn = findViewById(R.id.update_retry_btn);
		exitBtn = findViewById(R.id.update_exit_btn);
		retryBtn.setOnClickListener(this);
		exitBtn.setOnClickListener(this);
	}

	@Override
	public void onBackPressed() {

	}

	/**
	 * ServiceConnection连接
	 */
	protected ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			downServiceIsBinded = false;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			downloadBinder = (ApkDownloadService.DownloadBinder) service;
			// 开始下载
			downServiceIsBinded = true;
			downloadBinder.addCallback(callback);
			downloadBinder.start();
		}
	};
	/**
	 * DownloadService回调结果
	 */
	protected ICallbackResult callback = new ICallbackResult() {

		@Override
		public void OnBackResult(Integer progress, String message) {
			if (BACK_RESULT_FINISH.equals(message)) {
				mainApplication.relogin();
				return;
			} else if (BACK_RESULT_FAILED.equals(message)) {
				showRetry();
				downBarMsg.setText("下载发生错误，请重试");
				return;
			}
			downBar.setProgress(progress);
			downBarMsg.setText(message);

		}

	};

	/**
	 * 
	 * 功能:重试布局显示
	 * @author yinxuejian
	 */
	private void showRetry() {
		retryLayout.setVisibility(View.VISIBLE);
		retryLine.setVisibility(View.VISIBLE);
	}

	/**
	 * 
	 * 功能:重试布局隐藏
	 * @author yinxuejian
	 */
	private void dismissRetry() {
		retryLayout.setVisibility(View.GONE);
		retryLine.setVisibility(View.GONE);
	}

	public interface ICallbackResult {
		/** 下载服务结束 **/
		String BACK_RESULT_FINISH = "back_result_finish";
		String BACK_RESULT_FAILED = "back_result_failed";

		void OnBackResult(Integer progress, String message);
	}

	@Override
	public void onDestroy() {
		if (downServiceIsBinded) {
			unbindService(conn);
		}
		if ((downloadBinder != null) && downloadBinder.isCanceled()) {
			Intent it = new Intent(this, ApkDownloadService.class);
			stopService(it);
		}
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.update_retry_btn:
			if (!androidUtil.hasInternetConnected()) {
				ToastUtil.warning(VersionUpdateDialog.this, "网络已经断开连接，请检查网络!");
				return;
			}
			dismissRetry();
			downBarMsg.setText("开始下载");
			downloadBinder.retryDownload();
			break;
		case R.id.update_exit_btn:
			mainApplication.relogin();
			break;
		}
	}
}
