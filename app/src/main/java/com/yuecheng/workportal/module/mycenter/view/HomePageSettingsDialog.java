package com.yuecheng.workportal.module.mycenter.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yuecheng.workportal.R;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huochangsheng on 2018/8/15.
 * 首页设置
 */

public class HomePageSettingsDialog extends Dialog  implements RadioGroup.OnCheckedChangeListener{

    private LinearLayout message_ll;
    private LinearLayout task_ll;
    private LinearLayout workbench_ll;
    private LinearLayout address_books_ll;
    private LinearLayout my_ll;

    private Dialog myDialog;
    private ClickListenerInterface clickListenerInterface;
    private RadioGroup tabBar;

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.tab_message:
                if(clickListenerInterface==null)return;
                clickListenerInterface.onMessageClick();
                break;
            case R.id.tab_task:
                if(clickListenerInterface==null)return;
                clickListenerInterface.onTaskClick();
                break;
            case R.id.tab_workbench:
                if(clickListenerInterface==null)return;
                clickListenerInterface.onWorkbenchClick();
                break;
            case R.id.tab_address_books:
                if(clickListenerInterface==null)return;
                clickListenerInterface.onAddressClick();
                break;
            case R.id.tab_my:
                if(clickListenerInterface==null)return;
                clickListenerInterface.onMyClick();
                break;
        }
    }

    public interface ClickListenerInterface {
        void onMessageClick();//消息

        void onTaskClick();//任务

        void onWorkbenchClick();//工作台

        void onAddressClick();//通讯录

        void onMyClick();//我的
    }

    public HomePageSettingsDialog(@NonNull Context context) {
        super(context);
        init();

    }

    public HomePageSettingsDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
        //设置选中
        RadioButton tabHome = (RadioButton) tabBar.getChildAt(themeResId);
        tabHome.setChecked(true);
    }

    protected HomePageSettingsDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    private void init() {
        myDialog = new Dialog(getContext(), R.style.BottomDialogStyle);
        //填充对话框的布局
        View view = LayoutInflater.from(getContext()).inflate(R.layout.settings_home_page_dialog, null);
        //初始化控件
//        message_ll = (LinearLayout) view.findViewById(R.id.message_ll);
//        task_ll = (LinearLayout) view.findViewById(R.id.task_ll);
//        workbench_ll = (LinearLayout) view.findViewById(R.id.workbench_ll);
//        address_books_ll = (LinearLayout) view.findViewById(R.id.address_books_ll);
//        my_ll = (LinearLayout) view.findViewById(R.id.my_ll);
//
//        message_ll.setOnClickListener(new DialogClickListener());
//        task_ll.setOnClickListener(new DialogClickListener());
//        workbench_ll.setOnClickListener(new DialogClickListener());
//        address_books_ll.setOnClickListener(new DialogClickListener());
//        my_ll.setOnClickListener(new DialogClickListener());
        tabBar = (RadioGroup) view.findViewById(R.id.tab_bar);
        tabBar.setOnCheckedChangeListener(this);
        //将布局设置给Dialog
        myDialog.setContentView(view);
        //获取当前Activity所在的窗体
        Window dialogWindow = myDialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.TOP);

        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (dialogWindow.getWindowManager().getDefaultDisplay().getWidth() * 1);
        dialogWindow.setAttributes(lp); //将属性设置给窗体


    }

    public void setClicklistener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }


    private class DialogClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.message_ll:
                    clickListenerInterface.onMessageClick();
                    break;
                case R.id.task_ll:
                    clickListenerInterface.onTaskClick();
                    break;
                case R.id.workbench_ll:
                    clickListenerInterface.onWorkbenchClick();
                    break;
                case R.id.address_books_ll:
                    clickListenerInterface.onAddressClick();
                    break;
                case R.id.my_ll:
                    clickListenerInterface.onMyClick();
                    break;
            }
        }
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
