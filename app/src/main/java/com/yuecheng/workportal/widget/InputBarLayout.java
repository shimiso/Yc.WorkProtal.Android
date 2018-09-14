package com.yuecheng.workportal.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yuecheng.workportal.R;
import com.yuecheng.workportal.utils.KeyBoardUtils;


/**
 * Created by dwq on 2017/7/19/019.
 * e-mail:lomapa@163.com
 */

public class InputBarLayout extends LinearLayout {
    private EditText mEditTextContent;
    private ImageView ivVoice;
    private Button voiceBtn;


    public InputBarLayout(Context context) {
        super(context);
        initView(context);
    }

    public InputBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public InputBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.robot_input_bottom_bar_layout, this);
        mEditTextContent = (EditText) findViewById(R.id.et_msg);
        ivVoice = (ImageView) findViewById(R.id.iv_voice);
        voiceBtn = (Button) findViewById(R.id.voice_btn);

        initListener(context);
    }

    private void initListener(final Context context) {

        ivVoice.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (voiceBtn.getVisibility() == View.GONE) {
                    mEditTextContent.setVisibility(View.GONE);
                    voiceBtn.setVisibility(View.VISIBLE);
                    KeyBoardUtils.hideKeyBoard(context, mEditTextContent);
                    ivVoice.setBackgroundResource(R.mipmap.ic_btn_keybroad);
                } else {
                    mEditTextContent.setVisibility(View.VISIBLE);
                    voiceBtn.setVisibility(View.GONE);
                    KeyBoardUtils.showKeyBoard(context, mEditTextContent);
                    ivVoice.setBackgroundResource(R.mipmap.ic_btn_voice);
                }
            }

        });
    }


}
