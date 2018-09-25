package com.yuecheng.workportal.utils;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuecheng.workportal.R;


/**
 * 类的说明：LoadViewUtil
 * 作者：shims
 * 创建时间：2016/3/7 0007 13:05
 */
public class LoadViewUtil {
    private Context context;
    public static LoadViewUtil loadViewUtil;


    /**
     * 错误提示页面
     **/
    protected View loadingErrorView;
    public static int LOADING_ERROR_VIEW = 0;//服务器错误类型
    public static int LOADING_NONET_VIEW = 1;//没有网络类型
    public static int LOADING_EMPTY_VIEW = 2;//没有数据类型
    private View loadView;//加载动画页
    private FrameLayout contentFrame;

    private LoadViewUtil(View view, Context context) {
        this.context = context;
        loadView = view.findViewById(R.id.loading_view);
        contentFrame = (FrameLayout) view.findViewById(R.id.content_frame);
    }

    public static LoadViewUtil init(View view, Context context) {
        loadViewUtil = new LoadViewUtil(view, context);
        return loadViewUtil;
    }

    public interface LoadingErrorListener {
        /**
         * 点击刷新操作
         *
         * @return
         * @author 史明松
         * @update 2014-5-28 下午5:20:28
         */
        void onClickRefresh();
    }


    /**
     * 这个方法作用:服务器错误、网络错误、数据为空等
     *
     * @param loadingErrorListener 加载出错页面的事件监听
     * @param type                 0:服务器数据错误、1：网络不可用、2：数据为空
     * @author 史明松
     * @update 2014-5-27 下午4:00:57
     */
    public void showLoadingErrorView(int type, final LoadingErrorListener loadingErrorListener) {
        if (loadingErrorView != null) {
            contentFrame.removeView(loadingErrorView);
            loadingErrorView = null;
        }
        if (type == LOADING_ERROR_VIEW) {// 服务器数据错误等
            loadingErrorView = View.inflate(context, R.layout.loading_error_view, null);
        } else if (type == LOADING_NONET_VIEW) {// 网络不可用
            loadingErrorView = View.inflate(context, R.layout.loading_nonet_view, null);
        } else if (type == LOADING_EMPTY_VIEW) {// 数据为空
            loadingErrorView = View.inflate(context, R.layout.loading_empty_view, null);
            ImageView errorMsgV = (ImageView) loadingErrorView.findViewById(R.id.error_img);
            errorMsgV.setImageResource(R.mipmap.loading_empty);
        }
        loadingErrorView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (null != loadingErrorListener)
                    contentFrame.removeView(loadingErrorView);
                loadingErrorView = null;
                loadingErrorListener.onClickRefresh();

            }
        });

        contentFrame.addView(loadingErrorView);
    }

    /**
     * 这个方法作用:服务器错误、网络错误、数据为空等
     *
     * @param loadingErrorListener 加载出错页面的事件监听
     * @param type                 0:服务器数据错误、1：网络不可用、2：数据为空
     * @param errorMsg             自定义提示语
     * @author 史明松
     * @update 2014-5-27 下午4:00:57
     */
    public void showLoadingErrorView(int type, String errorMsg, final LoadingErrorListener loadingErrorListener) {
        if (loadingErrorView != null) {
            contentFrame.removeView(loadingErrorView);
            loadingErrorView = null;
        }
        if (type == LOADING_ERROR_VIEW) {// 服务器数据错误等
            loadingErrorView = View.inflate(context, R.layout.loading_error_view, null);
        } else if (type == LOADING_NONET_VIEW) {// 网络不可用
            loadingErrorView = View.inflate(context, R.layout.loading_nonet_view, null);
        } else if (type == LOADING_EMPTY_VIEW) {// 数据为空
            loadingErrorView = View.inflate(context, R.layout.loading_empty_view, null);
            ImageView errorMsgV = (ImageView) loadingErrorView.findViewById(R.id.error_img);
            TextView textView = (TextView) loadingErrorView.findViewById(R.id.error_msg_tv);
            errorMsgV.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
            textView.setText(errorMsg);
        }
        loadingErrorView.setOnClickListener(arg0 -> {
            if (null != loadingErrorListener)
                contentFrame.removeView(loadingErrorView);
            loadingErrorView = null;
            loadingErrorListener.onClickRefresh();
        });
        contentFrame.addView(loadingErrorView);
    }

    /**
     * 显示用户自定义的提示页面
     *
     * @param resId                图片
     * @param errorMsg             提示信息
     * @param loadingErrorListener 回调监听
     */
    public void showCustomerView(int resId, String errorMsg, final LoadingErrorListener loadingErrorListener) {
        //把上一次的页面清除掉
        if (loadingErrorView != null) {
            contentFrame.removeView(loadingErrorView);
            loadingErrorView = null;
        }
        loadingErrorView = View.inflate(context, R.layout.loading_empty_view, null);
        ImageView errorMsgImg = (ImageView) loadingErrorView.findViewById(R.id.error_img);
        TextView errorMsgTv = (TextView) loadingErrorView.findViewById(R.id.error_msg_tv);
        errorMsgImg.setImageResource(resId);
        errorMsgTv.setVisibility(View.VISIBLE);
        errorMsgTv.setText(errorMsg);
        loadingErrorView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (null != loadingErrorListener)
                    contentFrame.removeView(loadingErrorView);
                loadingErrorView = null;
                loadingErrorListener.onClickRefresh();

            }
        });

        contentFrame.addView(loadingErrorView);
    }

    /**
     * 开启加载动画
     */
    public void startLoading() {
        //把上一次的页面清除掉
        if (loadingErrorView != null) {
            contentFrame.removeView(loadingErrorView);
            loadingErrorView = null;
        }
        loadView.setVisibility(View.VISIBLE);
    }

    /**
     * 关闭加载动画
     */
    public void stopLoading() {
        loadView.setVisibility(View.GONE);
    }

    /**
     * 清除错误提示界面
     */
    public void clearErrorView() {
        if (loadingErrorView != null) {
            contentFrame.removeView(loadingErrorView);
            loadingErrorView = null;
        }
    }

    /**
     * 错误界面是否正在显示状态
     *
     * @return boolean
     */
    public boolean errorViewIsShowing() {
        return loadingErrorView != null && loadingErrorView.getVisibility() == View.VISIBLE;
    }
}
