package com.yhjiankang.business.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.yhjiankang.business.R;
import com.yhjiankang.business.api.HttpApi;
import com.yhjiankang.business.bean.GoodsCommentBean;
import com.yhjiankang.business.bean.GoodsDetailedBean;
import com.yhjiankang.business.common.Constants;
import com.yhjiankang.business.common.UIHelper;
import com.yhjiankang.business.http.IRequest;
import com.yhjiankang.business.http.RequestListener;
import com.yhjiankang.business.http.RequestParams;
import com.yhjiankang.business.ui.BaseActivity;
import com.yhjiankang.business.utils.JsonUtils;
import com.yhjiankang.business.utils.TimesUtil;
import com.yhjiankang.business.widegt.Carousel;
import com.yhjiankang.business.widegt.CarouselData;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商品详情
 * Created by 马小布 on 2015-06-20.
 */

public class GoodsDetailedActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.tvName)
    TextView tvName;
    @Bind(R.id.tvPrice)
    TextView tvPrice;
    @Bind(R.id.tv_salecount)
    TextView tvSalecount;
    @Bind(R.id.tvSpec)
    TextView tvSpec;
    @Bind(R.id.rlSpec)
    RelativeLayout rlSpec;
    @Bind(R.id.tv_cpjs)
    TextView tvCpjs;
    @Bind(R.id.iv_cpjs)
    ImageView ivCpjs;
    @Bind(R.id.tv_tbzc)
    TextView tvTbzc;
    @Bind(R.id.iv_tbzc)
    ImageView ivTbzc;
    @Bind(R.id.iv_cpzs)
    ImageView ivCpzs;
    @Bind(R.id.tvCommentCount)
    TextView tvCommentCount;
    @Bind(R.id.layoutComment)
    LinearLayout layoutComment;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.iv_setting)
    ImageView ivSetting;
    @Bind(R.id.crs)
    Carousel carousel;
    @Bind(R.id.tv_zysx)
    TextView tvZysx;

    private String goodsId;

    private List<CarouselData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodsdetailed);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void init() {
        goodsId = getIntent().getStringExtra("goodsId");
        loadData();
        loadComment();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void loadData() {
        RequestParams requestParams = new RequestParams();
        requestParams.put(Constants.KEY_VOUCHER, Constants.KEY_TEST);
        requestParams.put("goods_id", goodsId);
        IRequest.post(this, HttpApi.URL_GOODSDETAILED, requestParams, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                GoodsDetailedBean goodsDetailedBean = JsonUtils.object(json, GoodsDetailedBean.class);
                initView(goodsDetailedBean);
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

    @OnClick({R.id.iv_back, R.id.iv_setting})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_back:
                onBackPressed();
                break;
            default:
                break;
        }
    }

    /**
     * 轮播图
     *
     * @param banners
     */
    private void initCarousel(final List<String> banners) {
        list = new ArrayList<CarouselData>();
        for (int i = 0; i < banners.size(); i++) {
            CarouselData data = new CarouselData();
            data.setImage(HttpApi.URL_IMAGEPATH + banners.get(i));
            data.setId(i);
            list.add(data);
        }
        carousel.startup(list);
        carousel.setCallback(new Carousel.ClickCallback() {
            @Override
            public void perform(int id, int position) {

            }
        });
    }

    private void initView(GoodsDetailedBean goodsDetailedBean) {
        List<String> banners = goodsDetailedBean.getGoods_image();

        if (banners != null) {
            initCarousel(banners);
        }
        tvName.setText(goodsDetailedBean.getGoods_name());
        tvPrice.setText("¥" + goodsDetailedBean.getGoods_price());
        tvSalecount.setText("销量:" + goodsDetailedBean.getGoods_salenum());
        tvSpec.setText(goodsDetailedBean.getGoods_spec());
        List<GoodsDetailedBean.MobileBodyBean> bodyBeen = goodsDetailedBean.getMobile_body();
        if (bodyBeen != null) {
            tvCpjs.setText(bodyBeen.get(0).getValue());
            myApplication.mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + bodyBeen.get(1).getValue(), ivCpjs, myApplication.options);
            tvTbzc.setText(bodyBeen.get(2).getValue());
            myApplication.mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + bodyBeen.get(3).getValue(), ivTbzc, myApplication.options);
            myApplication.mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + bodyBeen.get(4).getValue(), ivCpzs, myApplication.options);
            tvZysx.setText(bodyBeen.get(5).getValue());
        }
    }


    /**
     * 加载评价
     */
    private void loadComment() {
        RequestParams requestParams = new RequestParams();
        requestParams.put(Constants.KEY_VOUCHER, Constants.KEY_TEST);
        requestParams.put("goods_id", goodsId);
        requestParams.put("state", "0");
        requestParams.put("page", "1");
        requestParams.put("num", "5");
        IRequest.post(this, HttpApi.URL_GOODSCOMMENT, requestParams, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                GoodsCommentBean goodsCommentBean = JsonUtils.object(json, GoodsCommentBean.class);
                List<GoodsCommentBean.ListBean> comments = goodsCommentBean.getList();
                initComment(comments);
                tvCommentCount.setText("商品评价(" + goodsCommentBean.getTotal() + ")");
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

    /**
     * 显示评价
     *
     * @param comments
     */
    private void initComment(List<GoodsCommentBean.ListBean> comments) {
        for (GoodsCommentBean.ListBean comment : comments) {
            View view = getLayoutInflater().inflate(R.layout.item_comment, null);
            ImageView imgHead = (ImageView) view.findViewById(R.id.ivHead);
            TextView tvName = (TextView) view.findViewById(R.id.tvName);
            RatingBar ratingBar = (RatingBar) view.findViewById(R.id.rb_comment);
            TextView tvTime = (TextView) view.findViewById(R.id.tv_time);
            TextView tvContent = (TextView) view.findViewById(R.id.tvContent);

            tvName.setText(comment.getMember_nickname());
            myApplication.mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + comment.getMember_avatar(), imgHead, myApplication.optionsHead);
            List<String> image = comment.getGeval_image();
            if (image.size() > 0) {
                LinearLayout layout = (LinearLayout) view.findViewById(R.id.ll_img);
                layout.setVisibility(View.VISIBLE);
                ImageView firstImageView = (ImageView) view.findViewById(R.id.iv_first);
                ImageView secondImageView = (ImageView) view.findViewById(R.id.iv_second);
                ImageView thirdImageView = (ImageView) view.findViewById(R.id.iv_third);
                switch (image.size()) {
                    case 1:
                        myApplication.mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + image.get(0), firstImageView, myApplication.options);
                        break;
                    case 2:
                        myApplication.mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + image.get(0), firstImageView, myApplication.options);
                        myApplication.mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + image.get(1), secondImageView, myApplication.options);
                        break;
                    case 3:
                        myApplication.mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + image.get(0), firstImageView, myApplication.options);
                        myApplication.mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + image.get(1), secondImageView, myApplication.options);
                        myApplication.mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + image.get(2), thirdImageView, myApplication.options);
                        break;
                    default:
                        myApplication.mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + image.get(0), firstImageView, myApplication.options);
                        myApplication.mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + image.get(1), secondImageView, myApplication.options);
                        myApplication.mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + image.get(2), thirdImageView, myApplication.options);
                        break;
                }
            }
            tvTime.setText(TimesUtil.timestampToStringL(comment.getGeval_addtime()));
            tvContent.setText(comment.getComment_message());
            ratingBar.setRating(Float.parseFloat(comment.getGeval_scores()));
            layoutComment.addView(view);
            layoutComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UIHelper.showCommentListActivity(GoodsDetailedActivity.this, goodsId);
                }
            });
        }
    }
}
