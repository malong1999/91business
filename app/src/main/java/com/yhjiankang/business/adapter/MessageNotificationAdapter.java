package com.yhjiankang.business.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.yhjiankang.business.R;
import com.yhjiankang.business.common.UIHelper;

import java.util.ArrayList;

/**
 * Project : 91健康商城
 * Program Name : com.yhjiankang.business.adapter.MessageNotificationAdapter.java
 * Author :马庆龙
 * Creation Date : 22015/6/15 上午8:35
 * Description：消息一
 * ModificationHistory ：
 */
public class MessageNotificationAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private ArrayList<String> mData;

    public MessageNotificationAdapter(Context context, ArrayList<String> data) {
        mContext = context;
        mData = data;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_message_notification_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.mCircularImageView.setImageResource(R.mipmap.test4);
        if (!mData.isEmpty()) {
            myViewHolder.mTvMessageSender.setText(mData.get(0));
            myViewHolder.mTvTime.setText(mData.get(1));
            myViewHolder.mTvContent.setText(mData.get(2));
            myViewHolder.llContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UIHelper.showChatActivity(mContext);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return 100;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public CircularImageView mCircularImageView;
        public View mPoint;
        public TextView mTvMessageSender;
        public TextView mTvTime;
        public TextView mTvContent;
        private LinearLayout llContainer;

        public MyViewHolder(View itemView) {
            super(itemView);
            mCircularImageView = (CircularImageView) itemView.findViewById(R.id.civ_head);
            mPoint = itemView.findViewById(R.id.point);
            mTvMessageSender = (TextView) itemView.findViewById(R.id.tv_message_sender);
            mTvTime = (TextView) itemView.findViewById(R.id.tv_time);
            mTvContent = (TextView) itemView.findViewById(R.id.tv_content);
            llContainer = (LinearLayout) itemView.findViewById(R.id.ll_container);
        }
    }
}
