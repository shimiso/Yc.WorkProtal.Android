package com.yuecheng.workportal.module.conversation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuecheng.workportal.R;
import com.yuecheng.workportal.base.BaseFragment;
import com.yuecheng.workportal.common.CommonResultView;
import com.yuecheng.workportal.module.conversation.bean.Conversation;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by huochangsheng on 2018/7/25.
 */

public class ConversationListFragment extends BaseFragment {
    @BindView(R.id.list)
    RecyclerView recyclerView;
    ConversationPresenter conversationPresenter;
    ConversationListAdapter conversationListAdapter;

    public static ConversationListFragment newInstance() {
        Bundle args = new Bundle();
        ConversationListFragment fragment = new ConversationListFragment();
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Conversation conversation) {
        loadData();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.conversation_list_fragment, container, false);
        ButterKnife.bind(this,view);
        conversationPresenter = new ConversationPresenter(getActivity());
        //这里用线性显示 类似于listview
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        conversationListAdapter = new ConversationListAdapter(getActivity());
        recyclerView.setAdapter(conversationListAdapter);

        Conversation conversation = new Conversation();
        conversation.setTitle("我的个人助理");
        conversation.setContent("如需帮助请联系我");
        conversation.setTargetId("roboe_assistant");
        conversation.setType(Conversation.ROBOT_ASSISTANT);
        conversationPresenter.saveConversation(conversation);
        loadData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    /**
     * 加载数据
     */
    protected void loadData() {
        conversationListAdapter.showLoadingView();
        //如果没有网络就直接返回
        if (!androidUtil.hasInternetConnected()) {
            conversationListAdapter.showNoNetView(v -> loadData());

        }else {
            conversationPresenter.getConversationList(new CommonResultView<List<Conversation>>() {
                @Override
                public void success(List<Conversation> result) {
                    if(result==null||result.size()==0){
                        conversationListAdapter.showEmptyView(v -> loadData());
                    }else {
                        conversationListAdapter.onRefresh(result);
                    }
                }

                @Override
                public void error(String errorMsg) {
                    conversationListAdapter.showErrorView(errorMsg, v -> loadData());
                }
            });
        }
    }



}
