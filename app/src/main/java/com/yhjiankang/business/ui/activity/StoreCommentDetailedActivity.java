package com.yhjiankang.business.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.yhjiankang.business.R;
import com.yhjiankang.business.api.HttpApi;
import com.yhjiankang.business.bean.StoreCommentDetailedBean;
import com.yhjiankang.business.common.Constants;
import com.yhjiankang.business.http.IRequest;
import com.yhjiankang.business.http.RequestListener;
import com.yhjiankang.business.http.RequestParams;
import com.yhjiankang.business.ui.BaseActivity;
import com.yhjiankang.business.utils.JsonUtils;
import com.yhjiankang.business.utils.TimesUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 店铺评价详情
 * Created by 马小布 on 2015-06-23.
 */

public class StoreCommentDetailedActivity extends BaseActivity {
    @Bind(R.id.tv_title_common)
    TextView tvTitleCommon;
    @Bind(R.id.toolbar_common)
    Toolbar toolbarCommon;
    @Bind(R.id.civ_head)
    CircularImageView civHead;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_id)
    TextView tvId;
    @Bind(R.id.tv_ordernum)
    TextView tvOrdernum;
    @Bind(R.id.tv_goodsname)
    TextView tvGoodsname;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.rb_goods)
    RatingBar rbGoods;
    @Bind(R.id.rb_service)
    RatingBar rbService;
    @Bind(R.id.rb_express)
    RatingBar rbExpress;
    @Bind(R.id.tv_jingyan)
    TextView tvJingyan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storecommentdetailed);
        ButterKnife.bind(this);
        init();
        loadData();
    }

    private void init() {
        setCommonBackToolBar(toolbarCommon, tvTitleCommon, "评价详情");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void loadData() {
        RequestParams requestParams = new RequestParams();
        requestParams.put(Constants.KEY_VOUCHER, Constants.KEY_TEST);
        requestParams.put("id",getIntent().getStringExtra("id"));
        IRequest.post(this, HttpApi.URL_STORECOMMENTDETAILED, requestParams, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                StoreCommentDetailedBean storeCommentDetailedBean = JsonUtils.object(json, StoreCommentDetailedBean.class);
                initView(storeCommentDetailedBean);
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

    private void initView(StoreCommentDetailedBean storeCommentDetailedBean) {
        tvJingyan.setText("LV" + storeCommentDetailedBean.getMember_exppoints());
        tvId.setText("用户ID:" + storeCommentDetailedBean.getMember_id());
        tvName.setText("昵称:" + storeCommentDetailedBean.getMember_name());
        myApplication.mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + storeCommentDetailedBean.getMember_avatar(), civHead, myApplication.optionsHead);
        tvOrdernum.setText(storeCommentDetailedBean.getOrder_sn());
        tvGoodsname.setText(storeCommentDetailedBean.getOrder_goods().get(0).getGoods_name());
        tvTime.setText(TimesUtil.timestampToStringL(storeCommentDetailedBean.getCreatetime()));
        rbGoods.setRating(Float.parseFloat(storeCommentDetailedBean.getDesccredit()));
        rbService.setRating(Float.parseFloat(storeCommentDetailedBean.getServicecredit()));
        rbExpress.setRating(Float.parseFloat(storeCommentDetailedBean.getDeliverycredit()));
    }

}
