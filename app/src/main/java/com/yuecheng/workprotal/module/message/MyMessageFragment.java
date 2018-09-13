package com.yuecheng.workprotal.module.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuecheng.workprotal.R;
import com.yuecheng.workprotal.base.BaseFragment;
import com.yuecheng.workprotal.module.robot.view.VoiceActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * Created by huochangsheng on 2018/7/25.
 */

public class MyMessageFragment extends BaseFragment {

    String token= "6mtPOclnXCMgg/LzVsCFe+blyNka7gTIOsDS5cBS+QCKoIfBOuUkBZxo2Klo86jo+DZRbBlFe0Zx2yWqDQ0HyQ==";


    public static MyMessageFragment newInstance() {
        Bundle args = new Bundle();
        MyMessageFragment fragment = new MyMessageFragment();
//        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_fragment, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @OnClick({R.id.my_assistant,R.id.my_chat})
    protected void click(View view){
        switch (view.getId()){
            case R.id.my_assistant:
                Intent intent = new Intent(getContext(), VoiceActivity.class);
                startActivity(intent);
                break;
            case R.id.my_chat:
                connect(token);
                break;
        }
    }


    /**
     * 建立与融云服务器的连接
     *
     * @param token
     */
    private void connect(String token) {
        /**
         * IMKit SDK调用第二步,建立与服务器的连接
         */
        RongIM.connect(token, new RongIMClient.ConnectCallback() {

            /**
             * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
             */
            @Override
            public void onTokenIncorrect() {

                Log.d("LoginActivity", "--onTokenIncorrect");
            }

            /**
             * 连接融云成功
             * @param userid 当前 token
             */
            @Override
            public void onSuccess(String userid) {

                Log.d("LoginActivity", "--onSuccess" + userid);
                if (RongIM.getInstance() != null)
                    RongIM.getInstance().startPrivateChat(getActivity(), "lisi", "title");
            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             *                  http://www.rongcloud.cn/docs/android.html#常见错误码
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

                Log.d("LoginActivity", "--onError" + errorCode);
            }
        });
    }
}
