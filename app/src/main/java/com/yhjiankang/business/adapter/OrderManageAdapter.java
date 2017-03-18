package com.yhjiankang.business.adapter;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
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
import com.yhjiankang.business.bean.OrderManageItemBean;
import com.yhjiankang.business.utils.TimesUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 马小布 on 2015/6/23.
 */
public class OrderManageAdapter extends RecyclerView.Adapter {


    private Context mContext;
    private ArrayList<OrderManageItemBean.ListBean> list;
    private final int SINGLEGOODSTYPE = 1;
    private final int MULTIPLEGOODSTYPE = 2;

    //待付款
    public final String ORDER_TYPE_DFK = "10";
    //已发货
    public final String ORDER_TYPE_YFH = "30";
    //退款中
    public final String ORDER_TYPE_TKZ = "60";
    //待发货
    public final String ORDER_TYPE_DFH = "20";
    //已完成
    public final String ORDER_TYPE_YWC = "40";
    //已关闭
    public final String ORDER_TYPE_YGB = "0";

    private OnOrderItemClickListener onOrderItemClickListener;

    public void setOnOrderItemClickListener(OnOrderItemClickListener onOrderItemClickListener) {
        this.onOrderItemClickListener = onOrderItemClickListener;
    }

    public OrderManageAdapter(Context context, ArrayList<OrderManageItemBean.ListBean> list) {
        this.mContext = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == SINGLEGOODSTYPE) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_order_manage_single, parent, false);
            return new SingleOrderViewHolder(view);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_order_manage_multiple, parent, false);
            return new MultipleOrderViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MultipleOrderViewHolder) {
            MultipleOrderViewHolder multipleOrderViewHolder = (MultipleOrderViewHolder) holder;
            OrderManageItemBean.ListBean bean = list.get(position);
            List<OrderManageItemBean.ListBean.ExtendOrderGoodsBean> goodsList = bean.getExtend_order_goods();
            final String state = bean.getOrder_state();
            final String orderID = bean.getOrder_id();
            multipleOrderViewHolder.llOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onOrderItemClickListener.onItemClick(orderID, state);
                }
            });
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            multipleOrderViewHolder.multipleRecycler.setLayoutManager(linearLayoutManager);
            multipleOrderViewHolder.multipleRecycler.setItemAnimator(new DefaultItemAnimator());
            multipleOrderViewHolder.multipleRecycler.setAdapter(new MyImageAdapter(goodsList));
            switch (state) {
                case ORDER_TYPE_YFH:
                    multipleOrderViewHolder.tvState.setText("订单状态:已发货");
                    break;
                case ORDER_TYPE_DFK:
                    multipleOrderViewHolder.tvState.setText("订单状态:待付款");
                    break;
                case ORDER_TYPE_DFH:
                    multipleOrderViewHolder.tvState.setText("订单状态:待发货");
                    break;
                case ORDER_TYPE_YWC:
                    multipleOrderViewHolder.tvState.setText("订单状态:已完成");
                    break;
                case ORDER_TYPE_YGB:
                    multipleOrderViewHolder.tvState.setText("订单状态:已关闭");
                    break;
                case ORDER_TYPE_TKZ:
                    multipleOrderViewHolder.tvState.setText("订单状态:退款中");
                    break;
                default:
                    break;
            }
            multipleOrderViewHolder.tvAddress.setText(bean.getReciver_address());
            multipleOrderViewHolder.tvContactPhone.setText("联系电话:" + bean.getReciver_phone());
            multipleOrderViewHolder.tvConsigneeName.setText(bean.getReciver_name());
            multipleOrderViewHolder.tvOrderdate.setText("订单时间:" + TimesUtil.timestampToStringL(bean.getAdd_time()));
            multipleOrderViewHolder.tvOrderprice.setText(bean.getOrder_amount());
        } else {
            SingleOrderViewHolder singleOrderViewHolder = (SingleOrderViewHolder) holder;
            OrderManageItemBean.ListBean bean = list.get(position);
            final String state = bean.getOrder_state();
            final String orderID = bean.getOrder_id();
            singleOrderViewHolder.llOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onOrderItemClickListener.onItemClick(orderID, state);
                }
            });
            switch (state) {
                case ORDER_TYPE_YFH:
                    singleOrderViewHolder.tvState.setText("订单状态:已发货");
                    break;
                case ORDER_TYPE_DFK:
                    singleOrderViewHolder.tvState.setText("订单状态:待付款");
                    break;
                case ORDER_TYPE_DFH:
                    singleOrderViewHolder.tvState.setText("订单状态:待发货");
                    break;
                case ORDER_TYPE_YWC:
                    singleOrderViewHolder.tvState.setText("订单状态:已完成");
                    break;
                case ORDER_TYPE_YGB:
                    singleOrderViewHolder.tvState.setText("订单状态:已关闭");
                    break;
                case ORDER_TYPE_TKZ:
                    singleOrderViewHolder.tvState.setText("订单状态:退款中");
                    break;
                default:
                    break;
            }
            singleOrderViewHolder.tvAddress.setText(bean.getReciver_address());
            singleOrderViewHolder.tvContactPhone.setText("联系电话:" + bean.getReciver_phone());
            singleOrderViewHolder.tvConsigneeName.setText(bean.getReciver_name());
            singleOrderViewHolder.tvOrderdate.setText("订单时间:" + TimesUtil.timestampToStringL(bean.getAdd_time()));
            singleOrderViewHolder.tvOrderprice.setText(bean.getOrder_amount());
            App.getInstance().mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + bean.getExtend_order_goods().get(0).getGoods_image(), singleOrderViewHolder.ivImage, App.getInstance().options);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        int size = list.get(position).getExtend_order_goods().size();
        if (size > 1) {
            return MULTIPLEGOODSTYPE;
        } else {
            return SINGLEGOODSTYPE;
        }
    }

    public class SingleOrderViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ll_order)
        LinearLayout llOrder;
        @Bind(R.id.iv_image)
        ImageView ivImage;
        @Bind(R.id.tv_state)
        TextView tvState;
        @Bind(R.id.tv_ordernum)
        TextView tvOrdernum;
        @Bind(R.id.tv_orderdate)
        TextView tvOrderdate;
        @Bind(R.id.tv_consignee_name)
        TextView tvConsigneeName;
        @Bind(R.id.tv_orderprice)
        TextView tvOrderprice;
        @Bind(R.id.tv_contact_phone)
        TextView tvContactPhone;
        @Bind(R.id.tv_address)
        TextView tvAddress;
        @Bind(R.id.tv_detail_information)
        TextView tvDetailInformation;

        public SingleOrderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public class MultipleOrderViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ll_order)
        LinearLayout llOrder;
        @Bind(R.id.tv_state)
        TextView tvState;
        @Bind(R.id.tv_ordernum)
        TextView tvOrdernum;
        @Bind(R.id.tv_orderdate)
        TextView tvOrderdate;
        @Bind(R.id.multiple_recycler)
        RecyclerView multipleRecycler;
        @Bind(R.id.tv_consignee_name)
        TextView tvConsigneeName;
        @Bind(R.id.tv_orderprice)
        TextView tvOrderprice;
        @Bind(R.id.tv_contact_phone)
        TextView tvContactPhone;
        @Bind(R.id.tv_address)
        TextView tvAddress;
        @Bind(R.id.ll_bottom)
        LinearLayout llBottom;
        @Bind(R.id.tv_detail_information)
        TextView tvDetailInformation;

        public MultipleOrderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 多图Adapter
     */
    public class MyImageAdapter extends RecyclerView.Adapter {

        private List<OrderManageItemBean.ListBean.ExtendOrderGoodsBean> list;

        public MyImageAdapter(List<OrderManageItemBean.ListBean.ExtendOrderGoodsBean> list) {
            this.list = list;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            view = LayoutInflater.from(mContext).inflate(R.layout.item_receyler_order_manage_multiple_image, parent, false);
            return new MyImageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MyImageViewHolder mHolder = (MyImageViewHolder) holder;
            App.getInstance().mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + list.get(position).getGoods_image(), mHolder.ivImage, App.getInstance().options);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyImageViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.iv_image)
            ImageView ivImage;

            public MyImageViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    public interface OnOrderItemClickListener {
        void onItemClick(String orderID, String state);
    }
}
