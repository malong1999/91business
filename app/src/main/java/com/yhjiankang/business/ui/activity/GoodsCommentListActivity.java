package com.yhjiankang.business.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.yhjiankang.business.R;
import com.yhjiankang.business.adapter.GoodsCommentAdapter;
import com.yhjiankang.business.api.HttpApi;
import com.yhjiankang.business.bean.GoodsCommentListBean;
import com.yhjiankang.business.common.AppHelper;
import com.yhjiankang.business.common.Constants;
import com.yhjiankang.business.common.UIHelper;
import com.yhjiankang.business.http.IRequest;
import com.yhjiankang.business.http.RequestListener;
import com.yhjiankang.business.http.RequestParams;
import com.yhjiankang.business.interfaces.OnDateCheckedListener;
import com.yhjiankang.business.interfaces.OnStoreCommentItemClickListener;
import com.yhjiankang.business.ui.BaseActivity;
import com.yhjiankang.business.ui.fragment.DatePickerFragment;
import com.yhjiankang.business.utils.JsonUtils;
import com.yhjiankang.business.utils.TimesUtil;
import com.yhjiankang.business.widegt.DividerItemDecoration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Project : 91健康商城
 * Program Name : com.yhjiankang.business.ui.activity.GoodsCommentListActivity.java
 * Author :马庆龙
 * Creation Date : 2015-06-24 上午10:45
 * Description：
 * ModificationHistory ：
 */
public class GoodsCommentListActivity extends BaseActivity implements OnStoreCommentItemClickListener, View.OnClickListener {
    public static final String TAG = "GoodsCommentList";
    @Bind(R.id.tv_title_common)
    TextView tvTitleCommon;
    @Bind(R.id.toolbar_common)
    Toolbar toolbarCommon;
    @Bind(R.id.tv_begintime)
    TextView mTvBegintime;
    @Bind(R.id.ll_begintime)
    LinearLayout mLlBegintime;
    @Bind(R.id.tv_endtime)
    TextView mTvEndtime;
    @Bind(R.id.ll_endtime)
    LinearLayout mLlEndtime;
    @Bind(R.id.ll_time)
    LinearLayout mLlTime;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshWidget;

    private String mStartTime;
    private String mEndTime;
    private int mPage;
    private int mNUm = 10;
    private int mTatalPages;
    private int mTatalNums;
    private boolean isLoadingMore = false;
    private List<GoodsCommentListBean.ListBean> mDataList = new ArrayList<>();
    ;
    private boolean isBegin;
    private GoodsCommentAdapter mGoodsCommentAdapter;
    private LinearLayoutManager mLinearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storecommentlist);
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
        setCommonBackToolBar(toolbarCommon, tvTitleCommon, "商品评价");
        mEndTime = String.valueOf(System.currentTimeMillis()).substring(0, 10);
        mStartTime = String.valueOf(System.currentTimeMillis() - 604800000).substring(0, 10);
        mPage = 1;
        mGoodsCommentAdapter = new GoodsCommentAdapter(this, mDataList);
        mGoodsCommentAdapter.setOnStoreCommentItemClickListener(this);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL, getResources().getColor(R.color.gray_light));
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setAdapter(mGoodsCommentAdapter);
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd");
        mTvBegintime.setText(TimesUtil.timestampToStringS(String.valueOf(mStartTime), sdr));
        mTvEndtime.setText(TimesUtil.timestampToStringS(String.valueOf(mEndTime), sdr));
        loadData(String.valueOf(mStartTime), String.valueOf(mEndTime));
        mSwipeRefreshWidget.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mSwipeRefreshWidget.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                mDataList.clear();
                loadData(String.valueOf(mStartTime), String.valueOf(mEndTime));
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
                int totalItemCount = mLinearLayoutManager.getItemCount();
                // dy>0 表示向下滑动
                if (lastVisibleItem >= totalItemCount - 1 && dy > 0) {
                    if (isLoadingMore) {

                    } else {
                        if (mPage == mTatalPages) {
                            AppHelper.showMessage(GoodsCommentListActivity.this, "没有更多数据了");
                            return;
                        }
                        mPage++;
                        loadData(String.valueOf(mStartTime), String.valueOf(mEndTime));
                    }
                }
            }
        });
    }

    @Override
    public void onItemClick(String id) {
        UIHelper.showCommentListActivity(this, id);
    }

    private void loadData(String start, String end) {
        isLoadingMore = true;
        RequestParams requestParams = new RequestParams();
        requestParams.put("voucher", Constants.KEY_TEST);
        requestParams.put("start", start);
        requestParams.put("end", end);
        requestParams.put("page", String.valueOf(mPage));
        requestParams.put("num", String.valueOf(mNUm));
        IRequest.post(this, HttpApi.URL_GOODS_COMMENT_LIST, requestParams, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                mSwipeRefreshWidget.setRefreshing(false);
                isLoadingMore = false;
                GoodsCommentListBean object = JsonUtils.object(json, GoodsCommentListBean.class);
                mTatalPages = object.getPage();
                mTatalNums = Integer.parseInt(object.getTatal());
                int position = mGoodsCommentAdapter.getItemCount();
                mDataList.addAll(object.getList());
                mGoodsCommentAdapter.notifyItemRangeInserted(position, mGoodsCommentAdapter.getItemCount());

            }

            @Override
            public void requestError(VolleyError e) {
                mSwipeRefreshWidget.setRefreshing(false);
                isLoadingMore = false;

            }

            @Override
            public void resultNoData(String json) {
                mSwipeRefreshWidget.setRefreshing(false);
                isLoadingMore = false;

            }

            @Override
            public void resultiInvalidKey(String json) {
                mSwipeRefreshWidget.setRefreshing(false);
                isLoadingMore = false;
            }
        });
    }

    @OnClick({R.id.ll_begintime, R.id.ll_endtime})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_begintime:
                DatePickerFragment beginFragment = new DatePickerFragment();
                beginFragment.show(getSupportFragmentManager(), "datePicker");
                beginFragment.setOnDateCheckedListener(new OnDateCheckedListener() {
                    @Override
                    public void setDate(String time) {
                        if (Long.parseLong(TimesUtil.stringsToTimestamp(time, "yyyy-MM-dd")) <= Long.parseLong(mEndTime)) {
                            mTvBegintime.setText(time);
                            mStartTime = TimesUtil.stringsToTimestamp(time, "yyyy-MM-dd");
                            loadData(String.valueOf(mStartTime), String.valueOf(mEndTime));
                        }
                    }
                });
                break;
            case R.id.ll_endtime:
                isBegin = false;
                DatePickerFragment endFragment = new DatePickerFragment();
                endFragment.show(getSupportFragmentManager(), "datePicker");
                endFragment.setOnDateCheckedListener(new OnDateCheckedListener() {
                    @Override
                    public void setDate(String time) {
                        if (Long.parseLong(TimesUtil.stringsToTimestamp(time, "yyyy-MM-dd")) >= Long.parseLong(mStartTime)) {
                            mTvEndtime.setText(time);
                            mEndTime = TimesUtil.stringsToTimestamp(time, "yyyy-MM-dd");
                            loadData(String.valueOf(mStartTime), String.valueOf(mEndTime));
                        }
                    }
                });
                break;
            default:
                break;
        }
    }
}
