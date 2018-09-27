package com.yuecheng.workportal.module.contacts.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nineoldandroids.view.ViewPropertyAnimator;
import com.yuecheng.workportal.module.contacts.adapter.AlphabetAdp;
import com.yuecheng.workportal.module.contacts.adapter.ContactSearchAdapter;
import com.yuecheng.workportal.module.contacts.bean.ContactBean;
import com.yuecheng.workportal.module.contacts.bean.PinYinStyle;
import com.yuecheng.workportal.utils.LoadViewUtil;
import com.yuecheng.workportal.utils.PinYinUtil;
import com.yuecheng.workportal.widget.LoadingDialog;
import com.yuecheng.workportal.widget.PinyinComparator;
import com.yuecheng.workportal.R;
import com.yuecheng.workportal.base.BaseFragment;
import com.yuecheng.workportal.bean.ResultInfo;
import com.yuecheng.workportal.common.CommonPostView;
import com.yuecheng.workportal.module.contacts.presenter.ContactsPresenter;
import com.yuecheng.workportal.module.contacts.bean.ContactBean;
import com.yuecheng.workportal.module.contacts.bean.PinYinStyle;
import com.yuecheng.workportal.module.contacts.adapter.AlphabetAdp;
import com.yuecheng.workportal.module.contacts.adapter.ContactAdapter;
import com.yuecheng.workportal.utils.PinYinUtil;
import com.yuecheng.workportal.widget.LoadingDialog;
import com.yuecheng.workportal.widget.PinyinComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huochangsheng on 2018/7/25.
 */

public class MyTabContactsFragment extends BaseFragment implements CommonPostView<ContactBean> {

    protected LoadViewUtil viewUtil;
    private SideLetterBar sideLetterBar;
    private ListView lv_contact;
    private ListView lv_alphabet;
    private TextView tv_alphabet;
    private TextView tv_notice;
    private TextView null_personnel;
    private ClearEditText et_clear;
    private List<String> alphabetList;
    RelativeLayout rel_notice;
    ContactAdapter adapter ;


    private List<ContactBean.StaffsBean> contactList;
    private View view;
    private List<ContactBean.StaffsBean> staffs;
    private LoadingDialog loadingDialog;
    private ArrayList<ContactBean.StaffsBean> mSortList;
    private ContactsPresenter contactsPresenter;

    public static MyTabContactsFragment newInstance() {
        Bundle args = new Bundle();
        MyTabContactsFragment fragment = new MyTabContactsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.contacts_tab_people, container, false);
        this.viewUtil = LoadViewUtil.init(view, getActivity());
        contactsPresenter = new ContactsPresenter(getActivity());

        loadData();
        return view;
    }
    /**
     * 加载数据
     */
    protected void loadData() {
        //如果没有网络就直接返回
        if (!androidUtil.hasInternetConnected()) {
            viewUtil.stopLoading();
            viewUtil.showLoadingErrorView(LoadViewUtil.LOADING_NONET_VIEW, () -> {
                viewUtil.startLoading();
                loadData();
            });
            return;
        }

        viewUtil.startLoading();
        contactsPresenter.getContact("",this);
    }
    private void initView() {
        sideLetterBar = (SideLetterBar) view.findViewById(R.id.sideLetterBar);
        lv_contact = (ListView) view.findViewById(R.id.lv_contact);
        lv_alphabet = (ListView) view.findViewById(R.id.lv_alphabet);
        tv_notice = (TextView) view.findViewById(R.id.tv_notice);
        rel_notice = (RelativeLayout) view.findViewById(R.id.rel_notice);
        et_clear = (ClearEditText) view.findViewById(R.id.et_clear);
        tv_alphabet = (TextView) view.findViewById(R.id.tv_alphabet);
        null_personnel = (TextView) view.findViewById(R.id.null_personnel);
        rel_notice.post(new Runnable() {
            @Override
            public void run() {
                tv_notice.getHeight();
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rel_notice.getLayoutParams();
                params.height = tv_notice.getHeight()*5;
                params.width = tv_notice.getWidth();
                rel_notice.setLayoutParams(params);
            }
        });
        loadingDialog = LoadingDialog.createDialog(getContext());
    }

    private void initEvent() {
        //右侧A-Z字母监听事件
        sideLetterBar.setOnTouchLetterListener(new SideLetterBar.OnTouchLetterListener() {
            @Override
            public void onTouchLetter(String letter) {
                alphabetList.clear();
                ViewPropertyAnimator.animate(rel_notice).alpha(1f).setDuration(0).start();
                //根据当前触摸的字母，去集合中找那个item的首字母和letter一样，然后将对应的item放到屏幕顶端
                for (int i = 0; i < contactList.size(); i++) {
                    String firstAlphabet = contactList.get(i).getPinyin().charAt(0)+"";
                    if(letter.equals(firstAlphabet)){
                        lv_contact.setSelection(i);
                        rel_notice.setVisibility(View.VISIBLE);
                        break;
                    }
                    if(letter.equals("#") || letter.equals("☆")){
                        lv_contact.setSelection(0);
                        rel_notice.setVisibility(View.GONE);
                    }
                }
                for (int i = 0; i < contactList.size(); i++) {
                    String firstAlphabet = contactList.get(i).getPinyin().toString().trim().charAt(0)+"";

                    if(letter.equals(firstAlphabet)){
                        //说明找到了，那么应该讲当前的item放到屏幕顶端
                        tv_notice.setText(letter);
                        if(!alphabetList.contains(String.valueOf(contactList.get(i).getName().trim().charAt(0)))){
                            alphabetList.add(String.valueOf(contactList.get(i).getName().trim().charAt(0)));
                        }
                    }

                }
                showCurrentWord(letter);
                //显示当前触摸的字母

                AlphabetAdp alphabetAdp = new AlphabetAdp(getContext(),alphabetList);
                lv_alphabet.setAdapter(alphabetAdp);
                alphabetAdp.notifyDataSetChanged();
            }
        });
        lv_contact.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                int pos = lv_contact.getFirstVisiblePosition();
                if (contactList.size()>0){
                    String text = contactList.get(pos).getPinyin().charAt(0)+"";
                    Pattern p = Pattern.compile("[0-9]*");
                    Matcher m1 = p.matcher(text);
                    if(m1.matches()){
                        tv_alphabet.setText("#");
                    }else {
                        tv_alphabet.setText(text);
                    }
                }
            }
        });
        et_clear.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loadingDialog = LoadingDialog.createDialog(getContext());
                loadingDialog.setMessage("正在搜索联系人...");
                loadingDialog.show();
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                fuzzySearch(s.toString());
                loadingDialog.dismiss();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        lv_alphabet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String alphabet = alphabetList.get(position).trim();
                setIsVisiable();
                for (int i = 0;i<contactList.size();i++){
                    if (alphabet.equals(String.valueOf(contactList.get(i).getName().trim().charAt(0)))){
                        int pos = i%lv_contact.getChildCount();
                        int childCount = lv_contact.getChildCount();
                        if(position==0&&pos-position==1||childCount-pos==1){
                            lv_contact.setSelection(i);
                        }else {
                            lv_contact.setSelection(i-1);
                        }
                        break;
                    }
                }
            }
        });

    }

    private void initData() {

        //3.设置Adapter   第三参数为判断是否为搜索还是全部
        adapter = new ContactAdapter(getContext(),contactList);
        lv_contact.setAdapter(adapter);
        alphabetList = new ArrayList<>();
    }

    protected void showCurrentWord(String letter) {
        tv_notice.setText(letter);
        lv_alphabet.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //SCROLL_STATE_FLING  		用户之前已经使用触屏实现了一个滚动。也就是手指离开屏幕，但屏幕还在滚动
                //SCROLL_STATE_IDLE  		视图静止，空闲状态
                //SCROLL_STATE_TOUCH_SCROLL 用户的手指还在屏幕上，正在触屏滑动
                if(scrollState== AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                    setIsVisiable();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                setIsVisiable();
            }
        });

    }
    private Handler handler = new Handler();
    private void setIsVisiable(){
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ViewPropertyAnimator.animate(rel_notice).alpha(0f).setDuration(1000).start();
            }
        }, 2000);
    }
    private ArrayList<ContactBean.StaffsBean> dataList() {
        // 数据
        mSortList = new ArrayList<ContactBean.StaffsBean>();
        for(int i=0;i<staffs.size();i++){
            staffs.get(i).conversionpinyin(staffs.get(i).getName());
            staffs.get(i).pinYinStyle = parsePinYinStyle(staffs.get(i).getName());
            mSortList.add(staffs.get(i));
        }
        Collections.sort(mSortList,new PinyinComparator());
        return mSortList;
    }
    //根据条件对数据进行筛选
    private void fuzzySearch(String str) {
        String name1 = str;
        ArrayList<ContactBean.StaffsBean> filterDateList = new ArrayList<ContactBean.StaffsBean>();
        // 数据
        if (TextUtils.isEmpty(str)){
            sideLetterBar.setVisibility(View.VISIBLE);
            null_personnel.setVisibility(View.GONE);
            tv_alphabet.setVisibility(View.VISIBLE);
            filterDateList = mSortList;
           // contactList = filterDateList;
            adapter = new ContactAdapter(getContext(),filterDateList);
            lv_contact.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }else {
            filterDateList.clear();
            sideLetterBar.setVisibility(View.GONE);
            tv_alphabet.setVisibility(View.GONE);
            Pattern p = Pattern.compile("[\u4e00-\u9fa5]+");
            Matcher m = p.matcher(str);
            if(m.matches()){
                str = PinYinUtil.getPinyin(str);
            }
            for(ContactBean.StaffsBean contactBean : mSortList){
                String name = contactBean.getName();

                if(name.contains(name1)||
                        contactBean.pinYinStyle.briefnessSpell.toUpperCase().contains(str.toUpperCase()) ){
                    filterDateList.add(contactBean);
                }
            }
          //  contactList = filterDateList;
            ContactSearchAdapter adapter = new ContactSearchAdapter(getContext(),filterDateList);
            lv_contact.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }

        if(filterDateList.size()==0){
            null_personnel.setVisibility(View.VISIBLE);
        }else{
            null_personnel.setVisibility(View.GONE);
        }
    }
    //将name解析成拼音
    public PinYinStyle parsePinYinStyle(String content) {
        PinYinStyle pinYinStyle = new PinYinStyle();
        if (content != null && content.length() > 0) {
            //其中包含的中文字符
            String[] enStrs = new String[content.length()];
            for (int i=0;i<content.length();i++){
                enStrs[i] = PinYinUtil.getPinyin(String.valueOf(content.charAt(i)));
            }
            for (int i = 0, length = enStrs.length; i < length; i++) {
                if (enStrs[i].length() > 0) {
                    //拼接简拼
                    pinYinStyle.briefnessSpell += enStrs[i].charAt(0);
                }
            }
        }
        return pinYinStyle;
    }

    @Override
    public void postSuccess(ResultInfo<ContactBean> resultInfo) {
        if(resultInfo.isSuccess()){
            viewUtil.stopLoading();
            ContactBean result = resultInfo.getResult();
            staffs = result.getStaffs();
            contactList = dataList();
            //2.对数据进行排序
            initView();
            initEvent();
            initData();
        }
    }

    @Override
    public void postError(String errorMsg) {
        viewUtil.stopLoading();
        if (loadingDialog != null) loadingDialog.dismiss();
        viewUtil.showLoadingErrorView(LoadViewUtil.LOADING_ERROR_VIEW, () -> {
            viewUtil.startLoading();
            loadData();
        });
    }
}
