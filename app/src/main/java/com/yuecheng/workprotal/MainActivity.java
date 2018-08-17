package com.yuecheng.workprotal;

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
import com.yuecheng.workprotal.module.message.MyMessageFragment;
import com.yuecheng.workprotal.module.mycenter.MyCenterFragment;
import com.yuecheng.workprotal.module.contacts.MyContactsFragment;
import com.yuecheng.workprotal.module.work.MyWorkFragment;
import com.yuecheng.workprotal.widget.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity {

    // collections
    private SparseIntArray items;// used for change ViewPager selected item
    private List<Fragment> fragments;// used for ViewPager adapter

//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.navigation_message:
//                    mTextMessage.setText(R.string.title_message);
//                    return true;
//                case R.id.navigation_work:
//                    mTextMessage.setText(R.string.title_work);
//                    return true;
//                case R.id.navigation_contacts:
//                    mTextMessage.setText(R.string.title_contacts);
//                    return true;
//                case R.id.navigation_mycenter:
//                    mTextMessage.setText(R.string.title_mycenter);
//                    return true;
//            }
//            return false;
//        }
//    };
    private BottomNavigationViewEx navigation;
    private ViewPager vp;
    private VpAdapter adapter;
    private MyMessageFragment myMessageFragment;
    private MyWorkFragment myWorkFragment;
    private MyCenterFragment myCenterFragment;
    private MyContactsFragment myContactsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        navigation = (BottomNavigationViewEx) findViewById(R.id.navigation);
        vp = (ViewPager) findViewById(R.id.viewPager);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null){
            myMessageFragment = MyMessageFragment.newInstance();
            myWorkFragment = MyWorkFragment.newInstance();
            myCenterFragment = MyCenterFragment.newInstance();
            myContactsFragment = MyContactsFragment.newInstance();
        } else {
            myMessageFragment = (MyMessageFragment) getSupportFragmentManager().getFragment(savedInstanceState, MyMessageFragment.class.getName());
            myWorkFragment = (MyWorkFragment) getSupportFragmentManager().getFragment(savedInstanceState, MyWorkFragment.class.getName());
            myCenterFragment = (MyCenterFragment) getSupportFragmentManager().getFragment(savedInstanceState, MyCenterFragment.class.getName());
            myContactsFragment = (MyContactsFragment) getSupportFragmentManager().getFragment(savedInstanceState, MyContactsFragment.class.getName());
        }

        initData();

        initEvent();
    }

    private void initData() {
        fragments = new ArrayList<>(4);
        items = new SparseIntArray(4);


        // add to fragments for adapter
        fragments.add(myMessageFragment);
        fragments.add(myWorkFragment);
        fragments.add(myContactsFragment);
        fragments.add(myCenterFragment);

        // add to items for change ViewPager item
        items.put(R.id.navigation_message, 0);
        items.put(R.id.navigation_work, 1);
        items.put(R.id.navigation_contacts, 2);
        items.put(R.id.navigation_mycenter, 3);

        // set adapter
        adapter = new VpAdapter(getSupportFragmentManager(), fragments);
        vp.setAdapter(adapter);
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
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
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
