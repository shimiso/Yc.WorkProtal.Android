package com.yuecheng.workprotal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseIntArray;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.yuecheng.workprotal.base.BaseActivity;
import com.yuecheng.workprotal.module.MyRobotFragment;
import com.yuecheng.workprotal.module.contacts.view.MyContactsFragment;
import com.yuecheng.workprotal.module.message.MyMessageFragment;
import com.yuecheng.workprotal.module.mycenter.MyCenterFragment;
import com.yuecheng.workprotal.module.robot.view.VoiceActivity;
import com.yuecheng.workprotal.module.work.MyTaskFragment;
import com.yuecheng.workprotal.widget.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.viewPager)
    ViewPager vp;
    @BindView(R.id.navigation)
    BottomNavigationViewEx navigation;
    // collections
    private SparseIntArray items;// used for change ViewPager selected item
    private List<Fragment> fragments;// used for ViewPager adapter

    private VpAdapter adapter;
    private MyMessageFragment myMessageFragment;
    private MyTaskFragment myTaskFragment;
    private MyCenterFragment myCenterFragment;
    private MyContactsFragment myContactsFragment;
    private MyRobotFragment myRobotFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.bind(this);

        if (savedInstanceState == null){
            myMessageFragment = MyMessageFragment.newInstance();
            myTaskFragment = MyTaskFragment.newInstance();
            myCenterFragment = MyCenterFragment.newInstance();
            myContactsFragment = MyContactsFragment.newInstance();
            myRobotFragment = MyRobotFragment.newInstance();
        } else {
            myMessageFragment = (MyMessageFragment) getSupportFragmentManager().getFragment(savedInstanceState, MyMessageFragment.class.getName());
            myTaskFragment = (MyTaskFragment) getSupportFragmentManager().getFragment(savedInstanceState, MyTaskFragment.class.getName());
            myCenterFragment = (MyCenterFragment) getSupportFragmentManager().getFragment(savedInstanceState, MyCenterFragment.class.getName());
            myContactsFragment = (MyContactsFragment) getSupportFragmentManager().getFragment(savedInstanceState, MyContactsFragment.class.getName());
            myRobotFragment = (MyRobotFragment) getSupportFragmentManager().getFragment(savedInstanceState, MyRobotFragment.class.getName());
        }

        initData();

        initEvent();
    }

    private void initData() {
        fragments = new ArrayList<>(5);
        items = new SparseIntArray(5);


        // add to fragments for adapter
        fragments.add(myMessageFragment);
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
        vp.setOffscreenPageLimit(3);
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
                //关闭打开的activity
                MainApplication.getApplication().exit();
            }
        } else {
        }
        return false;
    }
}
