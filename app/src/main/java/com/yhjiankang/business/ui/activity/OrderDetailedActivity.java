package com.yhjiankang.business.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.yhjiankang.business.R;
import com.yhjiankang.business.api.HttpApi;
import com.yhjiankang.business.bean.OrderDetailedBean;
import com.yhjiankang.business.common.Constants;
import com.yhjiankang.business.http.IRequest;
import com.yhjiankang.business.http.RequestListener;
import com.yhjiankang.business.http.RequestParams;
import com.yhjiankang.business.ui.BaseActivity;
import com.yhjiankang.business.utils.JsonUtils;
import com.yhjiankang.business.utils.TimesUtil;

import java.util.List;
import java.util.zip.Inflater;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Project : 91健康商城
 * Program Name : com.yhjiankang.business.ui.activity.OrderDetailedActivity.java
 * Author :马庆龙
 * Creation Date : 2015-06-17 下午2:48
 * Description：订单详情
 * ModificationHistory ：
 */
public class OrderDetailedActivity extends BaseActivity {

    @Bind(R.id.tv_title_common)
    TextView tvTitleCommon;
    @Bind(R.id.toolbar_common)
    Toolbar toolbarCommon;
    @Bind(R.id.tv_state)
    TextView tvState;
    @Bind(R.id.tv_ordernum)
    TextView tvOrdernum;
    @Bind(R.id.tv_receivername)
    TextView tvReceivername;
    @Bind(R.id.tv_phonenum)
    TextView tvPhonenum;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_ordertime)
    TextView tvOrdertime;
    @Bind(R.id.tv_invoice)
    TextView tvInvoice;
    @Bind(R.id.tv_remarks)
    TextView tvRemarks;
    @Bind(R.id.ll_goods)
    LinearLayout llGoods;
    @Bind(R.id.tv_orderprice)
    TextView tvOrderprice;
    @Bind(R.id.tv_pay)
    TextView tvPay;
    @Bind(R.id.tv_ship)
    TextView tvShip;
    @Bind(R.id.btn_edit)
    Button btnEdit;
    @Bind(R.id.btn_send)
    Button btnSend;

    private String state, orderID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivty_orderdetailed);
        ButterKnife.bind(this);
        init();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void init() {
        setCommonBackToolBar(toolbarCommon, tvTitleCommon, "订单详情");
        orderID = getIntent().getStringExtra("orderID");
        state = getIntent().getStringExtra("state");
        loadData();
    }

    private void loadData() {
        RequestParams requestParams = new RequestParams();
        requestParams.put(Constants.KEY_VOUCHER, Constants.KEY_TEST);
        requestParams.put("order_id", orderID);
        IRequest.post(this, HttpApi.URL_ORDERDETAILED, requestParams, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                OrderDetailedBean orderDetailedBean = JsonUtils.object(json, OrderDetailedBean.class);
                initView(orderDetailedBean);
            }

            @Override
            public void requestError(VolleyError e) {

            }

            @Override
            public void resultNoData(String json) {

            }

            @Override
            public void resultiInvalidKey(String json) {

            }
        });
    }

    private void initView(OrderDetailedBean orderDetailedBean) {
        tvOrdernum.setText(orderDetailedBean.getOrder_sn());
        tvAddress.setText(orderDetailedBean.getReciver_address());
        tvPhonenum.setText(orderDetailedBean.getReciver_phone());
        tvReceivername.setText(orderDetailedBean.getReciver_name());
        switch (state) {
            case Constants.ORDER_TYPE_DFH:
                tvState.setText("待发货");
                btnSend.setVisibility(View.VISIBLE);
                break;
            case Constants.ORDER_TYPE_DFK:
                tvState.setText("待付款");
                break;
            case Constants.ORDER_TYPE_YFH:
                tvState.setText("已发货");
                break;
            case Constants.ORDER_TYPE_YGB:
                tvState.setText("已关闭");
                break;
            case Constants.ORDER_TYPE_YWC:
                tvState.setText("已完成");
                break;
            case Constants.ORDER_TYPE_TKZ:
                tvState.setText("退款中");
                break;
            default:
                break;
        }
        tvOrdertime.setText(TimesUtil.timestampToStringL(orderDetailedBean.getAdd_time()));
        tvInvoice.setText(orderDetailedBean.getInvoice_info().getName());
        tvRemarks.setText(orderDetailedBean.getOrder_message());
        tvPay.setText(orderDetailedBean.getGoods_amount() + "元");
        tvShip.setText(orderDetailedBean.getShipping_fee() + "元");
        List<OrderDetailedBean.ExtendOrderGoodsBean> list = orderDetailedBean.getExtend_order_goods();
        double totalPrice = 0;
        for (OrderDetailedBean.ExtendOrderGoodsBean orderGoodsBean : list) {
            double goodsPrice = Double.parseDouble(orderGoodsBean.getGoods_pay_price());
            int goodsNum = Integer.parseInt(orderGoodsBean.getGoods_num());
            totalPrice += (goodsPrice * goodsNum);
            View view = getLayoutInflater().inflate(R.layout.activity_orderdetailed_ordergoods, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_goodsimg);
            TextView tvGoodsName = (TextView) view.findViewById(R.id.tv_goodsname);
            TextView tvGoodsCount = (TextView) view.findViewById(R.id.tv_goodscount);
            TextView tvGoodsGg = (TextView) view.findViewById(R.id.tv_guige);
            TextView tvGoodsPrice = (TextView) view.findViewById(R.id.tv_price);

            tvGoodsName.setText(orderGoodsBean.getGoods_name());
            tvGoodsPrice.setText("售价:" + orderGoodsBean.getGoods_pay_price() + "元");
            tvGoodsGg.setText(orderGoodsBean.getSelect_spec());
            tvGoodsCount.setText(orderGoodsBean.getGoods_num());

            myApplication.mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + orderGoodsBean.getGoods_image(), imageView, myApplication.options);
            llGoods.addView(view);
        }
        tvOrderprice.setText(totalPrice + "元");
    }
}
