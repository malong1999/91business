package com.yhjiankang.business.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhjiankang.business.App;
import com.yhjiankang.business.R;
import com.yhjiankang.business.api.HttpApi;
import com.yhjiankang.business.bean.GoodsManageItemBean;
import com.yhjiankang.business.common.Constants;
import com.yhjiankang.business.interfaces.OnGoodsItemClickListener;
import com.yhjiankang.business.widegt.MyCheckBox;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Project : 91健康商城
 * Program Name : com.yhjiankang.business.adapter.GoodsListAdapter.java
 * Author :马庆龙
 * Creation Date : 2015-06-16 上午11:24
 * Description：商品
 * ModificationHistory ：
 */
public class GoodsListAdapter extends RecyclerView.Adapter {


    private ArrayList<GoodsManageItemBean.GoodsBean> goodsList;

    private int itemType = Constants.ITEMTYEP_LINERLAYOUT;

    private OnGoodsItemClickListener onGoodsItemClickListener;

    public GoodsListAdapter(ArrayList<GoodsManageItemBean.GoodsBean> goodsList) {
        this.goodsList = goodsList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch (viewType) {
            case Constants.ITEMTYEP_LINERLAYOUT:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods, parent, false);
                break;
            case Constants.ITEMTYEP_GRIDLAYOUT:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods_grid, parent, false);
                break;
            default:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods, parent, false);
                break;
        }

        return new ViewHolder(v);
    }

    @Override
    public int getItemViewType(int position) {
        return itemType;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final GoodsManageItemBean.GoodsBean goodsBean = goodsList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.llGoodsitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGoodsItemClickListener.onGoodsItemClick(goodsBean.getGoods_id());
            }
        });

        viewHolder.llGoodsitem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onGoodsItemClickListener.onGoodsItemLongClick(goodsBean.getGoods_id(), holder.getAdapterPosition());
                return true;
            }
        });
        viewHolder.checkBox.setOnCheckedChangeListener(null);
        viewHolder.checkBox.setChecked(goodsBean.isChecked());
        viewHolder.checkBox.setOnCheckedChangeListener(new MyCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MyCheckBox checkBox, boolean isChecked) {
                goodsBean.setChecked(isChecked);
            }
        });
        viewHolder.tvGoodsname.setText(goodsBean.getGoods_name());
        viewHolder.tvPrice.setText(goodsBean.getGoods_price());
        viewHolder.tvStorenum.setText("库存:" + goodsBean.getGoods_storage());
        App.getInstance().mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + goodsBean.getGoods_image(), viewHolder.ivImg, App.getInstance().options);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return goodsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.checkBox)
        MyCheckBox checkBox;
        @Bind(R.id.iv_img)
        ImageView ivImg;
        @Bind(R.id.tv_goodsname)
        TextView tvGoodsname;
        @Bind(R.id.tv_price)
        TextView tvPrice;
        @Bind(R.id.tv_storenum)
        TextView tvStorenum;
        @Bind(R.id.ll_goodsitem)
        LinearLayout llGoodsitem;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public void setGoodsItemClickListener(OnGoodsItemClickListener onGoodsItemClickListener) {
        this.onGoodsItemClickListener = onGoodsItemClickListener;
    }


}
