package com.yhjiankang.business.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yhjiankang.business.App;
import com.yhjiankang.business.R;
import com.yhjiankang.business.common.Constants;
import com.yhjiankang.business.widegt.itemtouchhelp.ItemTouchHelperAdapter;
import com.yhjiankang.business.widegt.itemtouchhelp.ItemTouchHelperViewHolder;
import com.yhjiankang.business.widegt.itemtouchhelp.OnStartDragListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class GoodsImageAdapter extends RecyclerView.Adapter<GoodsImageAdapter.ItemViewHolder> implements ItemTouchHelperAdapter {

    public ArrayList<HashMap<String, String>> mItemList;

    private final OnStartDragListener mDragStartListener;


    public GoodsImageAdapter(Context context, OnStartDragListener dragStartListener, ArrayList<HashMap<String, String>> itemList) {
        this.mDragStartListener = dragStartListener;
        this.mItemList = itemList;
    }

    @Override
    public GoodsImageAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goodsimage, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        if (position == mItemList.size()) {
            if (position == Constants.ADDGOODSIMAGE_COUNT) {
                holder.mImageView.setVisibility(View.GONE);
            } else {
                holder.mImageView.setVisibility(View.VISIBLE);
                holder.mImageView.setBackgroundResource(R.mipmap.ic_addimg_big);
            }

        } else {
            HashMap<String, String> jo = mItemList.get(position);
            String path = jo.get("path");
            App.getInstance().mImageLoader.displayImage("file://" + path, holder.mImageView, App.getInstance().options);
        }
    }

    @Override
    public int getItemCount() {
        return mItemList.size() + 1;
    }


    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (toPosition != mItemList.size()) {
            Collections.swap(mItemList, fromPosition, toPosition);
            notifyItemMoved(fromPosition, toPosition);
        }
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        mItemList.remove(position);
        notifyItemRemoved(position);
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

        public ImageView mImageView;

        public ItemViewHolder(View v) {
            super(v);
            mImageView = (ImageView) v.findViewById(R.id.ivGoods);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }
}
