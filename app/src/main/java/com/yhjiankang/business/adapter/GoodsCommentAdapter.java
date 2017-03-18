package com.yhjiankang.business.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhjiankang.business.App;
import com.yhjiankang.business.R;
import com.yhjiankang.business.api.HttpApi;
import com.yhjiankang.business.bean.GoodsCommentListBean;
import com.yhjiankang.business.interfaces.OnStoreCommentItemClickListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 马小布 on 2015-06-24.
 */

public class GoodsCommentAdapter extends RecyclerView.Adapter {
    public static final String TAG = "GoodsCommentAdapter";
    private Context mContext;
    private List<GoodsCommentListBean.ListBean> mData;

    private OnStoreCommentItemClickListener onStoreCommentItemClickListener;

    public GoodsCommentAdapter(Context context, List<GoodsCommentListBean.ListBean> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goodscomment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        App.getInstance().mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + mData.get(position).getImage(), viewHolder.ivHead);
        viewHolder.tvName.setText(mData.get(position).getGoods_name());
        viewHolder.tvId.setText(mData.get(position).getPrice());
        viewHolder.tvCommentcount.setText(mData.get(position).getNum());
        if (mData.get(position).getPercentage() != 1) {
            float percentage = mData.get(position).getPercentage();
            String s = String.valueOf(percentage);
            Log.i(TAG, "onBindViewHolder: " + s);
            viewHolder.tvLv.setText(s.substring(2, 4) + "%");
        }
        viewHolder.llComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStoreCommentItemClickListener.onItemClick(mData.get(position).getGoods_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_head)
        ImageView ivHead;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_id)
        TextView tvId;
        @Bind(R.id.tv_commentcount)
        TextView tvCommentcount;
        @Bind(R.id.tv_lv)
        TextView tvLv;
        @Bind(R.id.ll_comment)
        LinearLayout llComment;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnStoreCommentItemClickListener(OnStoreCommentItemClickListener onStoreCommentItemClickListener) {
        this.onStoreCommentItemClickListener = onStoreCommentItemClickListener;
    }
}
