package com.yuecheng.workportal.widget;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.yuecheng.workportal.R;


/**
 * 请在此处简要描述此类所实现的功能。因为这项注释主要是为了在IDE环境中生成tip帮助，务必简明扼要
 * 
 * 请在此处详细描述类的功能、调用方法、注意事项、以及与其它类的关系.
 **/
public class LoadingDialog extends AppCompatDialog {

	private static LoadingDialog customProgressDialog = null;


	private LoadingDialog(Context context) {
		super(context);
	}

	public static LoadingDialog createDialog(Context context) {
		customProgressDialog = new LoadingDialog(context);
		customProgressDialog.setContentView(R.layout.loading_dialog);
		customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		customProgressDialog.setCanceledOnTouchOutside(false);
		return customProgressDialog;
	}

	public void onWindowFocusChanged(boolean hasFocus) {
		if (customProgressDialog == null) {
			return;
		}
	}

	/**
	 * 
	 * setMessage 提示内容
	 * 
	 * @param strMessage
	 * 
	 * @return
	 */
	public LoadingDialog setMessage(String strMessage) {
		TextView tvMsg = (TextView) customProgressDialog.findViewById(R.id.id_tv_loadingmsg);
		if (tvMsg != null) {
			tvMsg.setText(strMessage);
			tvMsg.setVisibility(View.VISIBLE);
		}
		return customProgressDialog;
	}

	@Override
	public void show() {
		super.show();
	}

	@Override
	public void dismiss() {
		super.dismiss();
	}
}
