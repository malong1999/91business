package com.yhjiankang.business.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.yhjiankang.business.App;
import com.yhjiankang.business.R;
import com.yhjiankang.business.api.HttpApi;
import com.yhjiankang.business.bean.StoreCommentListBean;
import com.yhjiankang.business.interfaces.OnStoreCommentItemClickListener;
import com.yhjiankang.business.utils.TimesUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 马小布 on 2015-06-23.
 */

public class StoreCommentAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<StoreCommentListBean.ListBean> mData;


    private OnStoreCommentItemClickListener onStoreCommentItemClickListener;

    public StoreCommentAdapter(Context context, List<StoreCommentListBean.ListBean> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_storecomment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        App.getInstance().mImageLoader.displayImage(HttpApi.URL_IMAGEPATH+mData.get(position).getMember_avatar(),viewHolder.ivHead);
        viewHolder.tvName.setText("昵称:"+mData.get(position).getMember_name());
        viewHolder.tvId.setText("用户ID:"+mData.get(position).getMember_id());
        viewHolder.tvMenberExppoints.setText("LV"+mData.get(position).getMember_exppoints());
        int  deliverycredit = Integer.parseInt(mData.get(position).getDeliverycredit());
        int  desccredit = Integer.parseInt(mData.get(position).getDesccredit());
        int  servicecredit = Integer.parseInt(mData.get(position).getServicecredit());
        int i = (deliverycredit + desccredit + servicecredit) / 3;
        viewHolder.rbComment.setRating(i);
        viewHolder.tvTime.setText(TimesUtil.timestampToStringL(mData.get(position).getCreatetime()));
        viewHolder.llComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStoreCommentItemClickListener.onItemClick(mData.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (null==mData)
            return 0;
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_head)
        ImageView ivHead;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_id)
        TextView tvId;
        @Bind(R.id.rb_comment)
        RatingBar rbComment;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.ll_comment)
        LinearLayout llComment;
        @Bind(R.id.tv_member_exppoints)
        TextView tvMenberExppoints;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnStoreCommentItemClickListener(OnStoreCommentItemClickListener onStoreCommentItemClickListener) {
        this.onStoreCommentItemClickListener = onStoreCommentItemClickListener;
    }
}
