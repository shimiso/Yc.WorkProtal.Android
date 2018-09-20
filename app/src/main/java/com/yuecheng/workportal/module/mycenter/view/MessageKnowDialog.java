package com.yuecheng.workportal.module.mycenter.view;

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
import android.widget.TextView;

import com.yuecheng.workportal.R;

import java.util.List;

/**
 * Created by huochangsheng on 2018/8/15.
 * “知道了”提示对话框
 */

public class MessageKnowDialog extends Dialog {

    private TextView mTitleTv;
    private TextView mOneTv;
    private TextView mTextTv;
    private Dialog myDialog;
    private ClickListenerInterface clickListenerInterface;

    public interface ClickListenerInterface {

        void onOneClick();//点击第一个TextView

    }
    public MessageKnowDialog(@NonNull Context context) {
        super(context);
        init();
    }

    public MessageKnowDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    protected MessageKnowDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }
    private void init() {
        myDialog = new Dialog(getContext(), R.style.BottomDialogStyle);
        //填充对话框的布局
        View view = LayoutInflater.from(getContext()).inflate(R.layout.message_know_layout, null);
        //初始化控件
        mTitleTv = (TextView) view.findViewById(R.id.tv_dialog_title);
        mTextTv = (TextView) view.findViewById(R.id.tv_dialog_text);
        mOneTv = (TextView) view.findViewById(R.id.tv_dialog_one);


        mOneTv.setOnClickListener(new DialogClickListener());

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
                case R.id.tv_dialog_one:
                    clickListenerInterface.onOneClick();
                    break;
            }
        }
    }

    public void dismissDialog() {
        if (myDialog != null && myDialog.isShowing()) {
            myDialog.dismiss();
        }
    }

    /**
     * 设置标题栏文本文字
     *
     * @param stringId
     * @see #setTitleText(String)
     */
    public void setTitleText(@StringRes int stringId) {
        setTitleText(getContext().getString(stringId));
    }

    /**
     * 设置标题栏文本文字
     *
     * @param text
     */
    public void setTitleText(String text) {
        mTitleTv.setText(text);
        mTitleTv.setVisibility(View.VISIBLE);
    }


    /**
     * 设置第二个TextView文字
     *
     * @param stringId
     */
    public void setTextText(@StringRes int stringId) {
        setTextText(getContext().getString(stringId));
    }

    /**
     * 设置第二个TextView文字
     *
     * @param text
     */
    public void setTextText(String text) {
        mTextTv.setText(text);
        mTextTv.setVisibility(View.VISIBLE);
    }

    /**
     * 获取标题栏文本
     *
     * @return
     */
    public final TextView getTitleTv() {
        return mTitleTv;
    }

    /**
     * 获取第一个文本
     *
     * @return
     */
    public final TextView getOneTv() {
        return mOneTv;
    }



    /**
     * 设置字体颜色
     *
     * @param titleColor 标题的颜色,默认是灰色
     * @param otherColor 其他的颜色，默认是蓝色
     * @param i          有2种模式，1（标题和其他颜色不一样）2（标题和其他颜色一样，取消键不一样）
     */
    public void setColor(int titleColor, int otherColor, int i) {
        if (i == 1) {
            mTitleTv.setTextColor(ContextCompat.getColor(getContext(), titleColor));
            mOneTv.setTextColor(ContextCompat.getColor(getContext(), otherColor));
            mTextTv.setTextColor(ContextCompat.getColor(getContext(), otherColor));
        }
        if (i == 2) {
            mTitleTv.setTextColor(ContextCompat.getColor(getContext(), titleColor));
            mOneTv.setTextColor(ContextCompat.getColor(getContext(), titleColor));
            mTextTv.setTextColor(ContextCompat.getColor(getContext(), titleColor));
        }
    }

    @Override
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, @Nullable Menu menu, int deviceId) {

    }
}
