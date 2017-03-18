package com.yhjiankang.business.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yhjiankang.business.App;
import com.yhjiankang.business.R;
import com.yhjiankang.business.api.HttpApi;
import com.yhjiankang.business.bean.GoodsCommentBean;
import com.yhjiankang.business.interfaces.OnStoreCommentItemClickListener;
import com.yhjiankang.business.utils.TimesUtil;
import com.yhjiankang.business.widegt.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 马小布 on 2015-06-24.
 */

public class CommentAdapter extends RecyclerView.Adapter {

    private OnStoreCommentItemClickListener onStoreCommentItemClickListener;
    private List<GoodsCommentBean.ListBean> list = new ArrayList<>();

    public CommentAdapter(List<GoodsCommentBean.ListBean> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        GoodsCommentBean.ListBean bean = list.get(position);
        viewHolder.tvName.setText(bean.getMember_nickname());
        viewHolder.tvContent.setText(bean.getComment_message());
        viewHolder.tvTime.setText(TimesUtil.timestampToStringL(bean.getGeval_addtime()));
        viewHolder.rbComment.setRating(Float.parseFloat(bean.getGeval_scores()));
        App.getInstance().mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + bean.getMember_avatar(), viewHolder.ivHead, App.getInstance().options);
        viewHolder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStoreCommentItemClickListener.onItemClick(list.get(position).getComment_id());
            }
        });

        List<String> image = bean.getGeval_image();
        if (image.size() > 0) {
            viewHolder.llImg.setVisibility(View.VISIBLE);
            switch (image.size()) {
                case 1:
                    App.getInstance().mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + image.get(0), viewHolder.ivFirst, App.getInstance().options);
                    break;
                case 2:
                    App.getInstance().mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + image.get(0), viewHolder.ivFirst, App.getInstance().options);
                    App.getInstance().mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + image.get(1), viewHolder.ivSecond, App.getInstance().options);
                    break;
                case 3:
                    App.getInstance().mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + image.get(0), viewHolder.ivFirst, App.getInstance().options);
                    App.getInstance().mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + image.get(1), viewHolder.ivSecond, App.getInstance().options);
                    App.getInstance().mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + image.get(2), viewHolder.ivThird, App.getInstance().options);
                    break;
                default:
                    App.getInstance().mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + image.get(0), viewHolder.ivFirst, App.getInstance().options);
                    App.getInstance().mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + image.get(1), viewHolder.ivSecond, App.getInstance().options);
                    App.getInstance().mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + image.get(2), viewHolder.ivThird, App.getInstance().options);
                    break;
            }
        } else {
            viewHolder.llImg.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ivHead)
        RoundedImageView ivHead;
        @Bind(R.id.tvName)
        TextView tvName;
        @Bind(R.id.rb_comment)
        RatingBar rbComment;
        @Bind(R.id.rl_first)
        RelativeLayout rlFirst;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.tvContent)
        TextView tvContent;
        @Bind(R.id.iv_first)
        ImageView ivFirst;
        @Bind(R.id.iv_second)
        ImageView ivSecond;
        @Bind(R.id.iv_third)
        ImageView ivThird;
        @Bind(R.id.ll_img)
        LinearLayout llImg;
        @Bind(R.id.rl_item)
        RelativeLayout rlItem;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnStoreCommentItemClickListener(OnStoreCommentItemClickListener onStoreCommentItemClickListener) {
        this.onStoreCommentItemClickListener = onStoreCommentItemClickListener;
    }
}
