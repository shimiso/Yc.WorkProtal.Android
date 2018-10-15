package com.yuecheng.workportal.module.mycenter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuecheng.workportal.R;
import com.yuecheng.workportal.module.mycenter.view.ValidationDialog;
import com.yuecheng.workportal.utils.ConvertUtils;
import com.yuecheng.workportal.utils.StringUtils;

import java.util.List;

/**
 * Created by huochangsheng on 2018/10/15.
 */

public class ClockInAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final Context context;
    private final LayoutInflater layoutInflater;
    private List<String> list;
    private final int DATA_VIEW = 0;
    private final int EMPTY_VIEW = 1;
    private final int LOADING_VIEW = 2;
    private final int ERROR_VIEW = 3;
    private final int NONET_VIEW = 4;
    private int viewType = DATA_VIEW;
    public View.OnClickListener onClickListener;
    private String errorMsg;

    public ClockInAdapter(Context context) {
        // titles = context.getResources().getStringArray(R.array.titles);
        this.context = context;
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
    public void onRefresh(List<String> list) {
        this.viewType = DATA_VIEW;
        this.list = list;
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
        return new ViewHolder(layoutInflater.inflate(R.layout.clock_in_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder myHolder = (ViewHolder) holder;
            myHolder.editor.setOnClickListener(v -> { //输入考勤备注
                ValidationDialog validationDialog = new ValidationDialog(context);
                validationDialog.setTitleText(context.getString(R.string.attendance_note)); //主标题
                validationDialog.setSubtitleTitleText(context.getString(R.string.attendance_note),View.GONE); //副标题，是否显示
                validationDialog.et_dialog_one.setHint(context.getString(R.string.input_attendance_note)); //Hint语句
                //设置输入框高度
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) validationDialog.et_dialog_one.getLayoutParams();
                lp.height = 200;
                validationDialog.et_dialog_one.setLayoutParams(lp);

                validationDialog.showDialog();
                validationDialog.setClicklistener(new ValidationDialog.ClickListenerInterface() {
                    @Override
                    public void onCancleClick() {
                        validationDialog.dismissDialog();
                    }

                    @Override
                    public void onConfirmClick() {
                        String s = validationDialog.et_dialog_one.getText().toString();

                        validationDialog.dismissDialog();
                    }
                });
            });
        }else if(holder instanceof EmptyViewHolder){
            EmptyViewHolder emptyViewHolder = (EmptyViewHolder) holder;
            emptyViewHolder.error_msg_tv.setVisibility(View.VISIBLE);
            emptyViewHolder.error_msg_tv.setText("没有考勤记录");
            emptyViewHolder.error_img.setImageResource(R.mipmap.kq);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return viewType;
    }

    @Override
    public int getItemCount() {
//        return list != null && list.size() > 0 ? list.size() : 1;
        return 5;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        TextView location_tv;
        ImageView editor;

        ViewHolder(View view) {
            super(view);
            time = (TextView)view.findViewById(R.id.time);
            editor = (ImageView)view.findViewById(R.id.editor);
            location_tv = (TextView)view.findViewById(R.id.location_tv);
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
