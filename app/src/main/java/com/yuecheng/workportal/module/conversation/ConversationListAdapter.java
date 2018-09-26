package com.yuecheng.workportal.module.conversation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yuecheng.workportal.R;
import com.yuecheng.workportal.module.conversation.bean.Conversation;
import com.yuecheng.workportal.module.robot.view.VoiceActivity;
import com.yuecheng.workportal.utils.DateTime;
import com.yuecheng.workportal.utils.SharePreferenceUtil;
import com.yuecheng.workportal.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * 描述: 工单列表
 * 作者: Shims
 * 时间: 2017/12/20 10:49
 */
public class ConversationListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final LayoutInflater layoutInflater;
    private final Context context;
    private List<Conversation> mList = new ArrayList<>();
    private final int DATA_VIEW = 0;
    private final int EMPTY_VIEW = 1;
    private final int LOADING_VIEW = 2;
    private final int ERROR_VIEW = 3;
    private final int NONET_VIEW = 4;
    private int viewType = DATA_VIEW;
    public View.OnClickListener onClickListener;
    private String errorMsg;
    SharePreferenceUtil spUtil = null;

    public ConversationListAdapter(Context context) {
        this.context = context;
        this.spUtil = new SharePreferenceUtil(context);
        layoutInflater = LayoutInflater.from(context);
    }

    private int position;

    /**
     * 获取当前选中位置
     * @return
     */
    public int getPosition() {
        return position;
    }

    /**
     * 设置当前选中位置
     * @param position
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * 刷新数据
     *
     * @param list
     */
    public void onRefresh(List<Conversation> list) {
        if (null != this.mList) {
            this.mList.clear();
        }
        this.mList = list;
        this.viewType = DATA_VIEW;
        this.notifyDataSetChanged();
    }

    /**
     * 删除
     *
     * @param index
     */
    public void remove(int index) {
        this.mList.remove(index);
        this.notifyDataSetChanged();
    }

    /**
     * 获取item
     *
     * @param index
     */
    public Conversation getItem(int index) {
        return this.mList.get(index);
    }

    public void showLoadingView() {
        this.viewType = LOADING_VIEW;
        this.notifyDataSetChanged();
    }

    public void showEmptyView(View.OnClickListener onClickListener) {
        this.mList.clear();
        this.onClickListener = onClickListener;
        this.viewType = EMPTY_VIEW;
        this.notifyDataSetChanged();
    }

    public void showErrorView(String errorMsg, View.OnClickListener onClickListener) {
        this.mList.clear();
        this.onClickListener = onClickListener;
        this.viewType = ERROR_VIEW;
        this.errorMsg = errorMsg;
        this.notifyDataSetChanged();
    }

    public void showNoNetView(View.OnClickListener onClickListener) {
        this.mList.clear();
        this.onClickListener = onClickListener;
        this.viewType = NONET_VIEW;
        this.notifyDataSetChanged();
    }

    /**
     * 加载更多数据
     *
     * @param list
     */
    public void onLoadMore(List<Conversation> list) {
        this.viewType = DATA_VIEW;
        mList.addAll(list);
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == LOADING_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_view, parent, false);
            return new LoadingViewHolder(view);
        } else if (viewType == EMPTY_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_empty_view, parent, false);
            return new EmptyViewHolder(view);
        } else if (viewType == ERROR_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_error_view, parent, false);
            return new ErrorViewHolder(view);
        } else if (viewType == NONET_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_nonet_view, parent, false);
            return new NoNetViewHolder(view);
        }
        return new DataViewHolder(layoutInflater.inflate(R.layout.conversation_list_item, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        return viewType;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DataViewHolder) {
            DataViewHolder dataViewHolder = (DataViewHolder) holder;
            final Conversation conversation = mList.get(position);
            if (spUtil.getCurrentUserName().equals(conversation.getSenderUserId()) && conversation.getSentTime() != null) {
                dataViewHolder.message_time.setText(new DateTime().getTimePoint(conversation.getSentTime()));
            } else {
                if (conversation.getReceivedTime() != null) {
                    dataViewHolder.message_time.setText(new DateTime().getTimePoint(conversation.getReceivedTime()));
                }
            }
            if (!StringUtils.isEmpty(conversation.getTargetIcon())) {
                Glide.with(context).load(conversation.getTargetIcon()).into(dataViewHolder.message_icon);
            }

            dataViewHolder.message_content.setText(conversation.getContent());
            dataViewHolder.message_title.setText(conversation.getTitle());
            //获取未读消息数
            RongIMClient.getInstance().getUnreadCount(io.rong.imlib.model.Conversation.ConversationType.PRIVATE, conversation.getTargetId(), new RongIMClient.ResultCallback<Integer>() {
                @Override
                public void onSuccess(Integer integer) {
                    if (integer > 99) {
                        dataViewHolder.unReadMsgCountIcon.setVisibility(View.VISIBLE);
                        dataViewHolder.unReadMsgCount.setVisibility(View.VISIBLE);
                        dataViewHolder.unReadMsgCountIcon.setImageResource(io.rong.imkit.R.drawable.rc_unread_count_bg);
                        dataViewHolder.unReadMsgCount.setText(Integer.toString(99));
                    } else if (integer <= 0) {
                        dataViewHolder.unReadMsgCountIcon.setVisibility(View.GONE);
                        dataViewHolder.unReadMsgCount.setVisibility(View.GONE);
                    } else {
                        dataViewHolder.unReadMsgCountIcon.setVisibility(View.VISIBLE);
                        dataViewHolder.unReadMsgCount.setVisibility(View.VISIBLE);
                        dataViewHolder.unReadMsgCountIcon.setImageResource(io.rong.imkit.R.drawable.rc_unread_count_bg);
                        dataViewHolder.unReadMsgCount.setText(Integer.toString(integer));
                    }
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                }
            });

            dataViewHolder.itemView.setOnClickListener(view -> {
                switch (conversation.getType()) {
                    case 0:
                        Glide.with(context).load(R.mipmap.assistant).into(dataViewHolder.message_icon);
                        Intent intent = new Intent(context, VoiceActivity.class);
                        context.startActivity(intent);
                        break;
                    case 1:
                        if (RongIM.getInstance() != null) {
                            Bundle bundle = new Bundle();
                            bundle.putString("targetName", conversation.getTargetName());
                            bundle.putString("targetIcon", conversation.getTargetIcon());
                            //启动单聊界面。  targetUserId 要与之聊天的用户 Id。
                            RongIM.getInstance().startConversation(context, io.rong.imlib.model.Conversation.ConversationType.PRIVATE, conversation.getTargetId(), conversation.getTitle(), bundle);
                        }
                        break;
                }
            });
            if(position>0){
                holder.itemView.setOnLongClickListener(view -> {
                    setPosition(holder.getLayoutPosition());
                    showPopupMenu(holder.itemView,position);
                    return false;
                });
            }
        }
    }
    private void showPopupMenu(View view,int position) {
        // 这里的view代表popupMenu需要依附的view
        PopupMenu popupMenu = new PopupMenu(context, view, Gravity.END);
        // 获取布局文件
        popupMenu.getMenuInflater().inflate(R.menu.conversation, popupMenu.getMenu());
        popupMenu.show();
        // 通过上面这几行代码，就可以把控件显示出来了
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.delete:
                    new ConversationPresenter(context).remove(getItem(position));
                    remove(position);
                    break;
            }
            return true;
        });
        popupMenu.setOnDismissListener(menu -> menu.dismiss());
        popupMenu.show();
    }

    @Override
    public int getItemCount() {
        return mList.size() > 0 ? mList.size() : 1;
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        public TextView message_title;
        public TextView message_time;
        public TextView message_content;
        public ImageView message_icon;
        public ImageView unReadMsgCountIcon;
        public TextView unReadMsgCount;

        DataViewHolder(View itemView) {
            super(itemView);
            message_title = itemView.findViewById(R.id.message_title);
            message_time = itemView.findViewById(R.id.message_time);
            message_content = itemView.findViewById(R.id.message_content);
            message_icon = itemView.findViewById(R.id.message_icon);
            unReadMsgCountIcon = itemView.findViewById(R.id.rc_unread_message_icon);
            unReadMsgCount = itemView.findViewById(R.id.rc_unread_message);
        }
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder {
        public LoadingViewHolder(View view) {
            super(view);
        }
    }

    class ErrorViewHolder extends RecyclerView.ViewHolder {
        TextView error_msg_tv;

        public ErrorViewHolder(View view) {
            super(view);
            error_msg_tv = view.findViewById(R.id.error_msg_tv);
            if (!StringUtils.isEmpty(errorMsg)) {
                error_msg_tv.setVisibility(View.VISIBLE);
                error_msg_tv.setText(errorMsg);
            }

            view.setOnClickListener(onClickListener);
        }
    }

    class NoNetViewHolder extends RecyclerView.ViewHolder {
        public NoNetViewHolder(View view) {
            super(view);
            view.setOnClickListener(onClickListener);
        }
    }

    class EmptyViewHolder extends RecyclerView.ViewHolder {
        TextView error_msg_tv;
        ImageView error_img;

        public EmptyViewHolder(View view) {
            super(view);
            error_msg_tv = view.findViewById(R.id.error_msg_tv);
            error_img = view.findViewById(R.id.error_img);
            error_img.setVisibility(View.GONE);
            error_msg_tv.setVisibility(View.VISIBLE);
            error_msg_tv.setText("数据为空");
            view.setOnClickListener(onClickListener);
        }
    }
}
