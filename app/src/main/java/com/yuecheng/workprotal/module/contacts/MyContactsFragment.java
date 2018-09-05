package com.yuecheng.workprotal.module.contacts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuecheng.workprotal.R;
import com.yuecheng.workprotal.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by huochangsheng on 2018/7/25.
 */

public class MyContactsFragment extends BaseFragment {

    @BindView(R.id.tl_patient_tab)
    TabLayout tabLayout;
    @BindView(R.id.vp_patient_tab)
    ViewPager mViewPager;
    Unbinder unbinder;
    private View view;
    private MyTabContactsFragment myTabContactsFragment;
    private MyTabStructureFragment myTabStructureFragment;

    public static MyContactsFragment newInstance() {
        Bundle args = new Bundle();
        MyContactsFragment fragment = new MyContactsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.contacts_fragment, container, false);

        unbinder = ButterKnife.bind(this, view);
        MyPagerAdapter adapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
        if(savedInstanceState == null){
            myTabContactsFragment = MyTabContactsFragment.newInstance();
            myTabStructureFragment = MyTabStructureFragment.newInstance();

        }else{
            myTabContactsFragment = (MyTabContactsFragment)getActivity().getSupportFragmentManager().getFragment(savedInstanceState, MyTabContactsFragment.class.getName());
            myTabStructureFragment = (MyTabStructureFragment)getActivity().getSupportFragmentManager().getFragment(savedInstanceState, MyTabStructureFragment.class.getName());
        }
        tabLayout.addTab(tabLayout.newTab().setText("人员"));
        adapter.addFragment(myTabContactsFragment, "人员");
        tabLayout.addTab(tabLayout.newTab().setText("组织架构"));
        adapter.addFragment(myTabStructureFragment, "组织架构");

        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(1);//默认加载页面
        tabLayout.setupWithViewPager(mViewPager);
        return view;
    }

    static class MyPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
