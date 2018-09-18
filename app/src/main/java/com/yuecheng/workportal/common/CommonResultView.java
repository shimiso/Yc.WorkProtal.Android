package com.yuecheng.workportal.common;


/**
 * 类的说明：post请求
 * 作者：shims
 * 创建时间：2016/11/23 0023 17:42
 */
public interface CommonResultView<T> {

    /**
     * 加载成功
     * @param result
     */
    void success(T result);

    /**
     * 加载错误
     * @param errorMsg
     */
    void error(String errorMsg);

}
