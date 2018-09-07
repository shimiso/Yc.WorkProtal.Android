package com.yuecheng.workprotal.module.contacts.view;

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
import com.yuecheng.workprotal.R;
import com.yuecheng.workprotal.base.BaseFragment;
import com.yuecheng.workprotal.bean.ResultInfo;
import com.yuecheng.workprotal.common.CommonPostView;
import com.yuecheng.workprotal.module.contacts.presenter.ContactsPresenter;
import com.yuecheng.workprotal.module.contacts.bean.ContactBean;
import com.yuecheng.workprotal.module.contacts.bean.PinYinStyle;
import com.yuecheng.workprotal.module.contacts.adapter.AlphabetAdp;
import com.yuecheng.workprotal.module.contacts.adapter.ContactAdapter;
import com.yuecheng.workprotal.utils.PinYinUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huochangsheng on 2018/7/25.
 */

public class MyTabContactsFragment extends BaseFragment implements CommonPostView<ContactBean> {

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
    private String[] data = new String[] {
            "15129372345","15129372334","15129372335","15129372343","15129372347","151293723423",
            //A
            "安先生", "敖先生", "艾先生", "爱先生","Devies王晴晴",
            //B
            "巴先生", "白先生", "鲍先生","包先生", "班先生", "毕先生","边先生", "卞先生", "薄先生",
            //C
            "蔡先生", "岑先生", "曹先生","陈先生", "程先生", "褚先生","昌先生", "车先生", "常先生",
            //D
            "戴先生", "狄先生", "窦先生","董先生", "杜先生","杜先生","杜先生", "丁先生","邓先生", "段先生", "党先生",
            //E
            "鄂先生",
            //F
            "费先生", "范先生","樊先生", "方先生", "房先生","丰先生", "封先生", "冯先生","法先生",
            //G
            "盖先生", "甘先生", "高先生","葛先生", "耿先生", "古先生","顾先生", "关先生", "郭先生",
            //H
            "海先生", "郝先生", "韩先生","何先生", "贺先生", "胡先生","扈先生", "黄先生", "华先生",
            //J
            "姬先生", "季先生", "纪先生","金先生", "焦先生", "姜先生","贾先生", "郏先生", "靳先生",
            //K
            "寇先生", "孔先生", "康先生","柯先生", "况先生", "亢先生","夔先生", "蒯先生", "隗先生",
            //L
            "李先生", "郎先生", "鲁先生","柳先生", "雷先生", "刘先生","林先生", "蓝先生", "吕先生",
            //M
            "马先生", "满先生", "苗先生","穆先生", "毛先生", "麻先生","孟先生", "梅先生", "莫先生",
            //N
            "那先生", "能先生", "倪先生","年先生", "宁先生", "聂先生","牛先生", "农先生", "聂先生",
            //O
            "欧先生", "欧阳先生",
            //P
            "潘先生", "庞先生", "裴先生","彭先生", "皮先生", "濮先生","蓬先生", "逄先生", "浦先生",
            //Q
            "戚先生", "齐先生", "祁先生","乔先生", "屈先生", "钱先生","秦先生", "邱先生", "裘先生",
            //R
            "冉先生", "饶先生", "任先生","阮先生", "芮先生", "戎先生","容先生", "荣先生", "融先生",
            //S
            "宋先生", "舒先生", "苏先生","孙先生", "索先生", "沈先生","邵先生", "施先生", "石先生",
            //T
            "邰先生", "谭先生", "陶先生","唐先生", "汤先生", "田先生","佟先生", "屠先生", "滕先生",
            //W
            "万先生", "邬先生", "乌先生","吴先生", "伍先生", "武先生","王先生", "韦先生", "魏先生",
            //X
            "奚先生", "席先生", "习先生","夏先生", "萧先生", "熊先生","项先生", "徐先生", "许先生",
            //Y
            "燕先生", "鄢先生", "颜先生","闫先生", "阎先生", "晏先生","姚先生", "杨先生", "叶先生",
            //Z
            "翟先生", "张先生", "章先生","赵先生", "甄先生", "曾先生","周先生", "郑先生", "祝先生",
    };

    private List<ContactBean.StaffsBean> contactList;
    private View view;
    private List<ContactBean.StaffsBean> staffs;

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

        ContactsPresenter contactsPresenter = new ContactsPresenter(getActivity());
        contactsPresenter.getContact(this);

        return view;
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
                //SCROLL_STATE_FLING  		用户之前已经使用触屏实现了一个滚动。也就是手指离开屏幕，但屏幕还在滚动
                //SCROLL_STATE_IDLE  		视图静止，空闲状态
                //SCROLL_STATE_TOUCH_SCROLL 用户的手指还在屏幕上，正在触屏滑动
//                if(scrollState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
//                    //如果垂直滑动，则需要关闭已经打开的layout
//                    SwipeManager.getInstance().closeCurrentLayout();
//                }

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                int pos = lv_contact.getFirstVisiblePosition();
                if (contactList.size()>0){
                    tv_alphabet.setVisibility(View.VISIBLE);
                    String text = contactList.get(pos).getPinyin().charAt(0)+"";
                    Pattern p = Pattern.compile("[0-9]*");
                    Matcher m1 = p.matcher(text);
                    if(m1.matches()){
                        tv_alphabet.setText("#");
                    }else {
                        tv_alphabet.setText(text);
                    }
                }else {
                    tv_alphabet.setVisibility(View.GONE);
                }
            }
        });
        et_clear.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                fuzzySearch(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

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
        // 虚拟数据
        ArrayList<ContactBean.StaffsBean> mSortList = new ArrayList<ContactBean.StaffsBean>();
        for(int i=0;i<staffs.size();i++){
            staffs.get(i).conversionpinyin(staffs.get(i).getName());
            staffs.get(i).pinYinStyle = parsePinYinStyle(staffs.get(i).getName());
            mSortList.add(staffs.get(i));
        }
        Collections.sort(mSortList);
        return mSortList;
    }
    //根据条件对数据进行筛选
    private void fuzzySearch(String str) {
        ArrayList<ContactBean.StaffsBean> filterDateList = new ArrayList<ContactBean.StaffsBean>();
        // 虚拟数据
        if (TextUtils.isEmpty(str)){
            sideLetterBar.setVisibility(View.VISIBLE);
            null_personnel.setVisibility(View.GONE);
            filterDateList = dataList();
        }else {
            filterDateList.clear();
            sideLetterBar.setVisibility(View.GONE);
            for(ContactBean.StaffsBean contactBean : dataList()){
                String name = contactBean.getName();
                Pattern p = Pattern.compile("[\u4e00-\u9fa5]+");
                Matcher m = p.matcher(str);
                if(m.matches()){
                    str = PinYinUtil.getPinyin(str);
                }
                if(PinYinUtil.getPinyin(name).contains(str.toUpperCase())||
                        contactBean.pinYinStyle.briefnessSpell.toUpperCase().contains(str.toUpperCase()) ||
                        contactBean.pinYinStyle.completeSpell.toUpperCase().contains(str.toUpperCase())){
                    filterDateList.add(contactBean);
                }
            }
        }
        contactList = filterDateList;
        adapter = new ContactAdapter(getContext(),filterDateList);
        lv_contact.setAdapter(adapter);
        adapter.notifyDataSetChanged();

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

        Log.i("MyTabContactsFragment",errorMsg);
    }
}
