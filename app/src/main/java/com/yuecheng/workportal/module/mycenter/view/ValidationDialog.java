package com.yuecheng.workportal.module.mycenter.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.yuecheng.workportal.R;

import java.util.List;

/**
 * Created by huochangsheng on 2018/8/15.
 * HR自助验证
 */

public class ValidationDialog extends Dialog {

    private TextView cancle;
    private TextView confirm;
    private TextView dialog_subtitle_title;
    private TextView dialog_title;
    public EditText et_dialog_one;
    private Dialog myDialog;
    private ClickListenerInterface clickListenerInterface;

    public interface ClickListenerInterface {

        void onCancleClick();//点击第一个TextView

        void onConfirmClick();//点击第二个TextView

    }
    public ValidationDialog(@NonNull Context context) {
        super(context);
        init();
    }

    public ValidationDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    protected ValidationDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }
    private void init() {
        myDialog = new Dialog(getContext(), R.style.BottomDialogStyle);
        //填充对话框的布局
        View view = LayoutInflater.from(getContext()).inflate(R.layout.validation_dialog, null);
        //初始化控件
        cancle = view.findViewById(R.id.tv_dialog_cancel); //取消
        confirm =  view.findViewById(R.id.tv_dialog_confirm);  //确认
        dialog_subtitle_title =  view.findViewById(R.id.dialog_subtitle_title);
        dialog_title =  view.findViewById(R.id.dialog_title);
        et_dialog_one =  view.findViewById(R.id.et_dialog_one);

        cancle.setOnClickListener(new DialogClickListener());
        confirm.setOnClickListener(new DialogClickListener());

        //将布局设置给Dialog
        myDialog.setContentView(view);
        //获取当前Activity所在的窗体
        Window dialogWindow = myDialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.CENTER);

        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (dialogWindow.getWindowManager().getDefaultDisplay().getWidth() * 0.8);
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
                case R.id.tv_dialog_cancel: //取消
                    clickListenerInterface.onCancleClick();
                    break;
                case R.id.tv_dialog_confirm: //确认
                    clickListenerInterface.onConfirmClick();
                    break;
            }
        }
    }

    /**
     * 设置标题栏文本文字
     *
     * @param text
     */
    public void setTitleText(String text) {
        dialog_title.setText(text);
    }
    public void setSubtitleTitleText(String text,int isShow) {
        dialog_subtitle_title.setText(text);
        dialog_subtitle_title.setVisibility(isShow);
    }

    public void dismissDialog() {
        if (myDialog != null && myDialog.isShowing()) {
            myDialog.dismiss();
        }
    }
    public void showDialog() {
        if (myDialog != null && !myDialog.isShowing()) {
            myDialog.show();//显示对话框
        }
    }
    @Override
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, @Nullable Menu menu, int deviceId) {

    }
}
