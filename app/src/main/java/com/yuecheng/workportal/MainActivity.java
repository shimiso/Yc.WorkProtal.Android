package com.yuecheng.workportal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseIntArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yuecheng.workportal.base.BaseActivity;
import com.yuecheng.workportal.module.workbench.MyWorkbenchFragment;
import com.yuecheng.workportal.module.contacts.view.MyContactsFragment;
import com.yuecheng.workportal.module.conversation.ConversationListFragment;
import com.yuecheng.workportal.module.mycenter.view.MyCenterFragment;
import com.yuecheng.workportal.module.robot.view.VoiceActivity;
import com.yuecheng.workportal.module.work.MyTaskFragment;
import com.yuecheng.workportal.widget.BottomNavigationViewEx;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

public class MainActivity extends BaseActivity {
    String token= MainApplication.getApplication().getLoginUser().getRongCloudToken();
    @BindView(R.id.viewPager)
    ViewPager vp;
    @BindView(R.id.navigation)
    BottomNavigationViewEx navigation;
    // collections
    private SparseIntArray items;// used for change ViewPager selected item
    private List<Fragment> fragments;// used for ViewPager adapter
    private TextView countTextView;
    private VpAdapter adapter;
    private ConversationListFragment conversationListFragment;
    private MyTaskFragment myTaskFragment;
    private MyCenterFragment myCenterFragment;
    private MyContactsFragment myContactsFragment;
    private MyWorkbenchFragment myRobotFragment;
    private com.yuecheng.workportal.module.conversation.bean.Conversation conversation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        if (savedInstanceState == null){
            conversationListFragment = ConversationListFragment.newInstance();
            myTaskFragment = MyTaskFragment.newInstance();
            myCenterFragment = MyCenterFragment.newInstance();
            myContactsFragment = MyContactsFragment.newInstance();
            myRobotFragment = MyWorkbenchFragment.newInstance();
        } else {
            conversationListFragment = (ConversationListFragment) getSupportFragmentManager().getFragment(savedInstanceState, ConversationListFragment.class.getName());
            myTaskFragment = (MyTaskFragment) getSupportFragmentManager().getFragment(savedInstanceState, MyTaskFragment.class.getName());
            myCenterFragment = (MyCenterFragment) getSupportFragmentManager().getFragment(savedInstanceState, MyCenterFragment.class.getName());
            myContactsFragment = (MyContactsFragment) getSupportFragmentManager().getFragment(savedInstanceState, MyContactsFragment.class.getName());
            myRobotFragment = (MyWorkbenchFragment) getSupportFragmentManager().getFragment(savedInstanceState, MyWorkbenchFragment.class.getName());
        }

        initData();
        connectRongIM(token);
        initEvent();
    }

    private void initData() {
        fragments = new ArrayList<>(5);
        items = new SparseIntArray(5);


        // add to fragments for adapter
        fragments.add(conversationListFragment);
        fragments.add(myTaskFragment);
        fragments.add(myRobotFragment);
        fragments.add(myContactsFragment);
        fragments.add(myCenterFragment);


        // add to items for change ViewPager item
        items.put(R.id.navigation_message, 0);
        items.put(R.id.navigation_work, 1);
        items.put(R.id.navigation_android, 2);
        items.put(R.id.navigation_contacts, 3);
        items.put(R.id.navigation_mycenter, 4);

        // set adapter
        adapter = new VpAdapter(getSupportFragmentManager(), fragments);
        vp.setAdapter(adapter);
        vp.setOffscreenPageLimit(3);//默认预加载几个页面
        //设置默认页
        int HomeSetting = spUtil.getInt("HomeSetting", 0);
        vp.setCurrentItem(HomeSetting);
        navigation.setCurrentItem(HomeSetting);
        //获取整个的NavigationView
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);

        //这里就是获取所添加的每一个Tab(或者叫menu)，
        View tab = menuView.getChildAt(0);
        BottomNavigationItemView itemView = (BottomNavigationItemView) tab;

        //加载我们的角标View，新创建的一个布局
        View badge = LayoutInflater.from(this).inflate(R.layout.menu_badge, menuView, false);

        countTextView = badge.findViewById(R.id.tv_msg_count);
        //添加到Tab上
        itemView.addView(badge);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUnReadCount(0);

        if (RongIM.getInstance() != null && conversation != null) {
            Bundle bundle = new Bundle();
            bundle.putString("targetName", conversation.getTargetName());
            bundle.putString("targetIcon", conversation.getTargetIcon());
            //启动单聊界面。  targetUserId 要与之聊天的用户 Id。
            RongIM.getInstance().startConversation(this, io.rong.imlib.model.Conversation.ConversationType.PRIVATE, conversation.getTargetId(), conversation.getTitle(), bundle);
            this.conversation = null;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(com.yuecheng.workportal.module.conversation.bean.Conversation conversation) {
        this.conversation = conversation;
        setUnReadCount(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    protected void setUnReadCount(final int count){
        RongIM.getInstance().getUnreadCount(new Conversation.ConversationType[]{Conversation.ConversationType.PRIVATE}, new RongIMClient.ResultCallback<Integer>() {
            @Override
            public void onSuccess(Integer integer) {
                int newCount = count + integer;
                if(newCount<=0){
                    countTextView.setVisibility(View.GONE);
                }else if(count>=99){
                    countTextView.setText(String.valueOf(99));
                    countTextView.setVisibility(View.VISIBLE);
                }else {
                    countTextView.setText(String.valueOf(newCount));
                    countTextView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });


    }


    private void initEvent() {
        disableAllAnimation(navigation);//设置样式
        // set listener to change the current item of view pager when click bottom nav item
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            private int previousPosition = -1;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int position = items.get(item.getItemId());
                if (previousPosition != position) {
                    // only set item when item changed
                    previousPosition = position;
                    vp.setCurrentItem(position);
                }
                return true;
            }
        });
        // set listener to change the current checked item of bottom nav when scroll view pager
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                navigation.setSelectedItemId(navigation.getMenu().getItem(position).getItemId());
                //当切换页面的时候才会执行此代码
                findViewById(navigation.getMenu().getItem(2).getItemId()).setOnLongClickListener(v -> {
                    Intent intent = new Intent(MainActivity.this, VoiceActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.push_bottom_in, R.anim.push_bottom_out);
                    return true;
                });
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //初始化界面的时候执行的代码当切换页面后代码废弃
        findViewById(navigation.getMenu().getItem(2).getItemId()).setOnLongClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, VoiceActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.push_bottom_in, R.anim.push_bottom_out);
            return true;
        });
    }


    /**
     * view pager adapter
     */
    private static class VpAdapter extends FragmentPagerAdapter {
        private List<Fragment> data;

        public VpAdapter(FragmentManager fm, List<Fragment> data) {
            super(fm);
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Fragment getItem(int position) {
            return data.get(position);
        }
    }

    //对自定义控件进行操作
    private void disableAllAnimation(BottomNavigationViewEx bnve) {
      //  bnve.enableAnimation(false);
        bnve.enableShiftingMode(false);
        bnve.enableItemShiftingMode(false);
    }

    private static Boolean isQuit = false;
    private Timer timer = new Timer();
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isQuit == false) {
                isQuit = true;
                Toast.makeText(this, "再次点击退出应用",Toast.LENGTH_LONG).show();
                TimerTask task = null;
                task = new TimerTask() {
                    @Override
                    public void run() {
                        isQuit = false;
                    }
                };
                timer.schedule(task, 2000);
            } else {
                //回到HOME
                Intent intent = new Intent();
                // 为Intent设置Action、Category属性
                intent.setAction(Intent.ACTION_MAIN);// "android.intent.action.MAIN"
                intent.addCategory(Intent.CATEGORY_HOME); //"android.intent.category.HOME"
                startActivity(intent);

            }
        } else {
        }
        return false;
    }
}
