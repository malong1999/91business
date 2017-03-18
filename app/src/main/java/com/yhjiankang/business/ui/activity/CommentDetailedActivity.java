package com.yhjiankang.business.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.yhjiankang.business.R;
import com.yhjiankang.business.bean.GoodsCommentDetailedBean;
import com.yhjiankang.business.api.HttpApi;
import com.yhjiankang.business.common.AppHelper;
import com.yhjiankang.business.common.Constants;
import com.yhjiankang.business.http.IRequest;
import com.yhjiankang.business.http.RequestListener;
import com.yhjiankang.business.http.RequestParams;
import com.yhjiankang.business.ui.BaseActivity;
import com.yhjiankang.business.utils.JsonUtils;
import com.yhjiankang.business.utils.TimesUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 马小布 on 2015-06-24.
 */

public class CommentDetailedActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.tv_title_common)
    TextView tvTitleCommon;
    @Bind(R.id.toolbar_common)
    Toolbar toolbarCommon;
    @Bind(R.id.iv_img)
    ImageView ivImg;
    @Bind(R.id.tv_guige)
    TextView tvGuige;
    @Bind(R.id.tv_jiage)
    TextView tvJiage;
    @Bind(R.id.tv_count)
    TextView tvCount;
    @Bind(R.id.rb_goods)
    RatingBar rbGoods;
    @Bind(R.id.tv_pjr)
    TextView tvPjr;
    @Bind(R.id.tv_comment)
    TextView tvComment;
    @Bind(R.id.iv_imgFirst)
    ImageView ivImgFirst;
    @Bind(R.id.iv_imgSecond)
    ImageView ivImgSecond;
    @Bind(R.id.iv_imgThird)
    ImageView ivImgThird;
    @Bind(R.id.et_huifu)
    EditText etHuifu;
    @Bind(R.id.btn_commit)
    Button btnCommit;
    @Bind(R.id.btn_back)
    Button btnBack;
    @Bind(R.id.ll_images)
    LinearLayout llImages;
    @Bind(R.id.tv_goodsname)
    TextView tvGoodsname;
    @Bind(R.id.tv_time)
    TextView tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commentdetailed);
        ButterKnife.bind(this);
        init();
        loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void init() {
        setCommonBackToolBar(toolbarCommon, tvTitleCommon, "评价详情");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void loadData() {
        RequestParams requestParams = new RequestParams();
        requestParams.put(Constants.KEY_VOUCHER, Constants.KEY_TEST);
        requestParams.put("comment_id", getIntent().getStringExtra("id"));
        IRequest.post(this, HttpApi.URL_GOODSCOMMENTDETAILED, requestParams, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                GoodsCommentDetailedBean goodsCommentDetailedBean = JsonUtils.object(json, GoodsCommentDetailedBean.class);
                initView(goodsCommentDetailedBean);
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

    private void initView(GoodsCommentDetailedBean bean) {
        tvGoodsname.setText(bean.getGoods_name());
        myApplication.mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + bean.getGoods_image(), ivImg, myApplication.options);
        tvGuige.setText("规格:" + bean.getGoods_spec());
        tvJiage.setText("价格:" + bean.getGoods_price());
        tvCount.setText("购买数量:没有购买数量");
        rbGoods.setRating(Float.parseFloat(bean.getScores()));
        tvComment.setText(bean.getContent());
        tvTime.setText("评价时间:" + TimesUtil.timestampToStringL(bean.getCreatetime()));
        if (!bean.getExplain().equals("")) {
            etHuifu.setText(bean.getExplain());
            btnCommit.setVisibility(View.GONE);
            etHuifu.setFocusable(false);
        }
        if (bean.getIsanonymous().equals("1")) {
            tvPjr.setText("匿名评价");
        } else {
            tvPjr.setText("评价人:" + bean.getMember_name());
        }
        List<String> images = bean.getImages();
        if (images != null) {
            int size = images.size();
            llImages.setVisibility(View.VISIBLE);
            switch (size) {
                case 1:
                    myApplication.mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + images.get(0), ivImgFirst, myApplication.options);
                    break;
                case 2:
                    myApplication.mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + images.get(0), ivImgFirst, myApplication.options);
                    myApplication.mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + images.get(1), ivImgSecond, myApplication.options);
                    break;
                case 3:
                    myApplication.mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + images.get(0), ivImgFirst, myApplication.options);
                    myApplication.mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + images.get(2), ivImgThird, myApplication.options);
                    myApplication.mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + images.get(1), ivImgSecond, myApplication.options);
                    break;
                default:
                    myApplication.mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + images.get(0), ivImgFirst, myApplication.options);
                    myApplication.mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + images.get(2), ivImgThird, myApplication.options);
                    myApplication.mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + images.get(1), ivImgSecond, myApplication.options);
                    break;
            }

        }
    }

    @OnClick({R.id.btn_commit, R.id.btn_back})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_commit:
                commit();
                break;
            case R.id.btn_back:
                onBackPressed();
                break;
            default:
                break;
        }
    }

    private void commit() {
        RequestParams requestParams = new RequestParams();
        requestParams.put(Constants.KEY_VOUCHER, Constants.KEY_TEST);
        requestParams.put("comment_id", getIntent().getStringExtra("id"));
        requestParams.put("explain", etHuifu.getText().toString());
        IRequest.post(this, HttpApi.URL_GOODSCOMMENTDETAILED_COMMIT, requestParams, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AppHelper.showMessage(CommentDetailedActivity.this, "回复成功");
                finish();
            }

            @Override
            public void requestError(VolleyError e) {
                AppHelper.showMessage(CommentDetailedActivity.this, "回复失败");
            }

            @Override
            public void resultNoData(String json) {
                AppHelper.showMessage(CommentDetailedActivity.this, json);
            }

            @Override
            public void resultiInvalidKey(String json) {

            }
        });
    }
}
