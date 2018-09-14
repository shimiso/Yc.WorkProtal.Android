package com.yuecheng.workportal.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.yuecheng.workportal.utils.AndroidUtil;
import com.yuecheng.workportal.utils.SharePreferenceUtil;
import com.yuecheng.workportal.MainApplication;
import com.yuecheng.workportal.utils.AndroidUtil;
import com.yuecheng.workportal.utils.SharePreferenceUtil;


/**
 * Fragment基类
 */
public class BaseFragment extends Fragment {
    /** 共享存储工具类 **/
    protected SharePreferenceUtil spUtil;
    /** Application **/
    protected MainApplication mainApplication;
    /** Android工具类 **/
    protected AndroidUtil androidUtil;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.spUtil = new SharePreferenceUtil(getActivity());
        this.mainApplication = (MainApplication)getActivity().getApplication();
        this.androidUtil = AndroidUtil.init(getActivity(),spUtil,mainApplication);
    }

}
