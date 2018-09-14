package com.yuecheng.workportal.common;


import com.yuecheng.workportal.bean.ResultInfo;
import com.yuecheng.workportal.bean.ResultInfo;

/**
 * 类的说明：post请求
 * 作者：shims
 * 创建时间：2016/11/23 0023 17:42
 */
public interface CommonPostView<T> {

    /**
     * 加载成功
     * @param resultInfo
     */
    void postSuccess(ResultInfo<T> resultInfo);

    /**
     * 加载错误
     * @param errorMsg
     */
    void postError(String errorMsg);

}
