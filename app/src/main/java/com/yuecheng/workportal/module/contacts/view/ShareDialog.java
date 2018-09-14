package com.yuecheng.workportal.module.contacts.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuecheng.workportal.R;

import java.util.List;

/**
 * Created by huochangsheng on 2018/8/15.
 */

public class ShareDialog extends Dialog {

    private LinearLayout weixin;
    private LinearLayout pengyouquan;
    private LinearLayout qq;
    private Dialog myDialog;
    private ClickListenerInterface clickListenerInterface;

    public interface ClickListenerInterface {

        void onWXClick();//点击第一个TextView

        void onPYQClick();//点击第二个TextView

        void onQQClick();//点击第二个TextView
    }
    public ShareDialog(@NonNull Context context) {
        super(context);
        init();
    }

    public ShareDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    protected ShareDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }
    private void init() {
        myDialog = new Dialog(getContext(), R.style.BottomDialogStyle);
        //填充对话框的布局
        View view = LayoutInflater.from(getContext()).inflate(R.layout.contact_share_dialog, null);
        //初始化控件
        weixin = (LinearLayout) view.findViewById(R.id.weixin);
        pengyouquan = (LinearLayout) view.findViewById(R.id.pengyouquan);
        qq = (LinearLayout) view.findViewById(R.id.qq);

        weixin.setOnClickListener(new DialogClickListener());
        pengyouquan.setOnClickListener(new DialogClickListener());
        qq.setOnClickListener(new DialogClickListener());

        //将布局设置给Dialog
        myDialog.setContentView(view);
        //获取当前Activity所在的窗体
        Window dialogWindow = myDialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);

        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (dialogWindow.getWindowManager().getDefaultDisplay().getWidth() * 1);
        dialogWindow.setAttributes(lp); //将属性设置给窗体

        myDialog.show();//显示对话框
    }

    public void setClicklistener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }


    private class DialogClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.weixin:
                    clickListenerInterface.onWXClick();
                    break;
                case R.id.pengyouquan:
                    clickListenerInterface.onPYQClick();
                    break;
                case R.id.qq:
                    clickListenerInterface.onQQClick();
                    break;
            }
        }
    }

    public void dismissDialog() {
        if (myDialog != null && myDialog.isShowing()) {
            myDialog.dismiss();
        }
    }


    @Override
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, @Nullable Menu menu, int deviceId) {

    }
}
