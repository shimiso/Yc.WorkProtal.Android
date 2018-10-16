package com.yuecheng.workportal.module.mycenter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuecheng.workportal.R;
import com.yuecheng.workportal.module.mycenter.bean.CalendarBean;
import com.yuecheng.workportal.utils.DateUtil;
import com.yuecheng.workportal.utils.StringUtils;

import java.util.List;

/**
 * Created by ldf on 17/6/14.
 */

public class ExampleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LayoutInflater layoutInflater;
    private final Context context;
    private final boolean isShow;
    //private String[] titles;
    private List<CalendarBean> list;
    private final int DATA_VIEW = 5;
    private final int EMPTY_VIEW = 1;
    private final int LOADING_VIEW = 2;
    private final int ERROR_VIEW = 3;
    private final int NONET_VIEW = 4;
    private int viewType = DATA_VIEW;
    public View.OnClickListener onClickListener;
    private String errorMsg;

    public ExampleAdapter(Context context, boolean isShow) {
        // titles = context.getResources().getStringArray(R.array.titles);
        this.context = context;
        this.isShow = isShow;
        layoutInflater = LayoutInflater.from(context);
    }

    public void showLoadingView() {
        this.viewType = LOADING_VIEW;
        this.notifyDataSetChanged();
    }

    public void showEmptyView(View.OnClickListener onClickListener) {
        if (this.list != null) {
            this.list.clear();
        }
        this.onClickListener = onClickListener;
        this.viewType = EMPTY_VIEW;
        this.notifyDataSetChanged();
    }

    public void showErrorView(String errorMsg, View.OnClickListener onClickListener) {
        if (this.list != null) {
            this.list.clear();
        }
        this.onClickListener = onClickListener;
        this.viewType = ERROR_VIEW;
        this.errorMsg = errorMsg;
        this.notifyDataSetChanged();
    }

    public void showNoNetView(View.OnClickListener onClickListener) {
        if (this.list != null) {
            this.list.clear();
        }
        this.onClickListener = onClickListener;
        this.viewType = NONET_VIEW;
        this.notifyDataSetChanged();
    }

    //刷新数据
    public void onRefresh(List<CalendarBean> list) {
        this.viewType = DATA_VIEW;
        this.list = list;
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View itemView = layoutInflater.inflate(R.layout.attendance_head_item_rl, parent, false);
            return new HeadHolder(itemView);
        }
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
        return new ViewHolder(layoutInflater.inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder myHolder = (ViewHolder) holder;
            if (isShow) {
                position = position-1;
            }


            myHolder.title_tv.setText(list.get(position).getTitle());
            myHolder.description_tv.setText(list.get(position).getDescription());
            myHolder.dtstart_tv.setText(DateUtil.getDateToString(Long.valueOf(list.get(position).getDtstart()),"HH:mm"));
//            if(list.get(position).getDtstart()!=null && list.get(position).getDtstart()!=""){
//                myHolder.dtend_tv.setText(DateUtil.getDateToString(Long.valueOf(list.get(position).getDtend()),"HH:mm"));
//            }


        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isShow && position == 0) { // 头部
            return 0;
        } else {
            return viewType;
        }
    }

    @Override
    public int getItemCount() {
        if (isShow) {
            return list != null && list.size() > 0 ? list.size() + 1 : 2;
        }else{
            return list != null && list.size() > 0 ? list.size() : 1;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title_tv;
        TextView description_tv;
        TextView dtstart_tv;
        TextView dtend_tv;

        ViewHolder(View view) {
            super(view);
            title_tv = (TextView) view.findViewById(R.id.title_tv);
            description_tv = (TextView) view.findViewById(R.id.description_tv);
            dtstart_tv = (TextView) view.findViewById(R.id.dtstart_tv);
            dtend_tv = (TextView) view.findViewById(R.id.dtend_tv);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("ViewHolder", "onClick--> position = " + getPosition());
                }
            });
        }
    }

    public static class HeadHolder extends RecyclerView.ViewHolder {
        HeadHolder(View view) {
            super(view);
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
            error_msg_tv = (TextView) view.findViewById(R.id.error_msg_tv);
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
            error_msg_tv = (TextView) view.findViewById(R.id.error_msg_tv);
            error_img = (ImageView) view.findViewById(R.id.error_img);
            view.setOnClickListener(onClickListener);
        }
    }
}
