package com.yuecheng.workportal.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yuecheng.workportal.R;


/**
 * 确认提示框.
 *
 * @author 史明松
 */
public class ConfirmDialog extends Dialog {
    /**
     * Context上下文
     **/
    private Context context = null;
    private static ConfirmDialog confirmDialog = null;

    private ConfirmDialog(Context context) {
        super(context);
        this.context = context;
    }

    private ConfirmDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    public static ConfirmDialog createDialog(Context context) {
        confirmDialog = new ConfirmDialog(context, R.style.LodingDialog);
        confirmDialog.setContentView(R.layout.confirm_dialog);
        confirmDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        return confirmDialog;
    }

    /**
     * 设置标题.
     *
     * @param strTitle
     * @return
     * @author 史明松
     * @update 2012-7-25 下午1:16:20
     */
    public ConfirmDialog setDialogTitle(String strTitle) {
        TextView title = (TextView) confirmDialog.findViewById(R.id.title);
        if (title != null) {
            title.setText(strTitle);
        }
        return confirmDialog;
    }

    /**
     * 设置标题.
     *
     * @param titleId
     * @return
     * @author 史明松
     * @update 2012-7-25 下午1:16:20
     */
    public ConfirmDialog setDialogTitle(Integer titleId) {
        TextView title = (TextView) confirmDialog.findViewById(R.id.title);
        if (title != null) {
            title.setText(context.getString(titleId));
        }
        return confirmDialog;
    }

    /**
     * 设置弹出框提示内容
     *
     * @param strMessage
     * @return
     */
    public ConfirmDialog setDialogMessage(String strMessage) {
        TextView message = (TextView) confirmDialog.findViewById(R.id.message);
        if (message != null) {
            message.setText(strMessage);
        }
        return confirmDialog;
    }

    /**
     * 设置弹出框提示内容
     *
     * @param strMessage
     * @return
     */
    public ConfirmDialog setDialogMessage(String strMessage, Integer colorId) {
        TextView message = (TextView) confirmDialog.findViewById(R.id.message);
        if (message != null) {
            message.setText(strMessage);
        }
        if (colorId != null) {
            message.setTextColor(context.getResources().getColor(colorId));
        }
        return confirmDialog;
    }

    /**
     * 设置弹出框提示内容
     *
     * @param messageId
     * @return
     */
    public ConfirmDialog setDialogMessage(Integer messageId) {
        TextView message = (TextView) confirmDialog.findViewById(R.id.message);
        if (message != null) {
            message.setText(context.getString(messageId));
        }
        return confirmDialog;
    }

    /**
     * 设置底部红色提示文字是否显示
     *
     * @param isDisplay
     * @return
     */
    public ConfirmDialog setRedPromptDisplay(boolean isDisplay) {
        TextView message = (TextView) confirmDialog.findViewById(R.id.tv_confirm_dialog_prompt);
        if (isDisplay) {
            message.setVisibility(View.VISIBLE);
        } else {
            message.setVisibility(View.GONE);
        }
        return confirmDialog;
    }

    /**
     * 设置按钮1监听.
     *
     * @param text                   按钮文字
     * @param colorId                按钮颜色
     * @param onButton1ClickListener 按钮监听
     * @author 史明松
     * @update 2014年6月22日 下午12:34:39
     */
    public void setOnButton1ClickListener(String text, Integer colorId, final OnButton1ClickListener onButton1ClickListener) {
        Button button1 = (Button) confirmDialog.findViewById(R.id.button1);
        if (colorId != null)
            button1.setTextColor(context.getResources().getColor(colorId));
        confirmDialog.findViewById(R.id.fengx1).setVisibility(View.VISIBLE);
        button1.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(text)) {
            button1.setText(text);

        }
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                onButton1ClickListener.onClick(view, confirmDialog);
            }

        });
    }

    /**
     * 设置按钮1监听.
     *
     * @param textId                 按钮文字
     * @param colorId                按钮颜色
     * @param onButton1ClickListener 按钮监听
     * @author 史明松
     * @update 2014年6月22日 下午12:34:39
     */
    public void setOnButton1ClickListener(Integer textId, Integer colorId, final OnButton1ClickListener onButton1ClickListener) {
        Button button1 = (Button) confirmDialog.findViewById(R.id.button1);
        if (colorId != null)
            button1.setTextColor(context.getResources().getColor(colorId));
        confirmDialog.findViewById(R.id.fengx1).setVisibility(View.VISIBLE);
        button1.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(context.getString(textId))) {
            button1.setText(context.getString(textId));

        }
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                onButton1ClickListener.onClick(view, confirmDialog);
            }

        });
    }

    /**
     * 设置按钮2监听.
     *
     * @param text                   按钮文字
     * @param colorId                按钮颜色
     * @param onButton2ClickListener 按钮监听
     * @author 史明松
     * @update 2014年6月22日 下午12:34:39
     */
    public void setOnButton2ClickListener(String text, Integer colorId, final OnButton2ClickListener onButton2ClickListener) {
        Button button2 = (Button) confirmDialog.findViewById(R.id.button2);
        if (colorId != null)
            button2.setTextColor(context.getResources().getColor(colorId));
        button2.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(text))
            button2.setText(text);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButton2ClickListener.onClick(view, confirmDialog);
            }

        });
    }

    /**
     * 设置按钮2监听.
     *
     * @param textId                 按钮文字
     * @param colorId                按钮颜色
     * @param onButton2ClickListener 按钮监听
     * @author 史明松
     * @update 2014年6月22日 下午12:34:39
     */
    public void setOnButton2ClickListener(Integer textId, Integer colorId, final OnButton2ClickListener onButton2ClickListener) {
        Button button2 = (Button) confirmDialog.findViewById(R.id.button2);
        if (colorId != null)
            button2.setTextColor(context.getResources().getColor(colorId));
        button2.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(context.getString(textId)))
            button2.setText(context.getString(textId));
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButton2ClickListener.onClick(view, confirmDialog);
            }

        });
    }

    /**
     * 按钮1监听.
     *
     * @author 史明松
     */
    public interface OnButton1ClickListener {
        public void onClick(View view, DialogInterface dialog);
    }

    /**
     * 按钮2监听.
     *
     * @author 史明松
     */
    public interface OnButton2ClickListener {
        public void onClick(View view, DialogInterface dialog);
    }
}
