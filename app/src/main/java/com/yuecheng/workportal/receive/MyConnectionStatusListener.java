package com.yuecheng.workportal.receive;

import android.content.Context;

import com.yuecheng.workportal.utils.ToastUtil;

import io.rong.imlib.RongIMClient;

/**
 * 描述: 连接状态监听器
 * 作者: Shims
 * 时间: 2018/9/25 16:57
 */
public class MyConnectionStatusListener implements RongIMClient.ConnectionStatusListener {
    private Context context;

    public MyConnectionStatusListener(Context context) {
        this.context = context;
    }

    @Override
    public void onChanged(RongIMClient.ConnectionStatusListener.ConnectionStatus connectionStatus) {
        switch (connectionStatus) {
            case CONNECTED://连接成功。
                break;
            case DISCONNECTED://断开连接。
                ToastUtil.warning(context,"断开连接");
                break;
            case CONNECTING://连接中。
                break;
            case NETWORK_UNAVAILABLE://网络不可用。
                ToastUtil.warning(context,"网络不可用");
                break;
            case KICKED_OFFLINE_BY_OTHER_CLIENT://用户账户在其他设备登录，本机会被踢掉线
                ToastUtil.normal(context,"账户在其他设备登录");
                break;
        }
    }
}
