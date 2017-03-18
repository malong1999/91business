package com.yhjiankang.business.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.yhjiankang.business.R;
import com.yhjiankang.business.adapter.OrderManageAdapter;
import com.yhjiankang.business.api.HttpApi;
import com.yhjiankang.business.bean.OrderManageItemBean;
import com.yhjiankang.business.common.AppHelper;
import com.yhjiankang.business.common.Constants;
import com.yhjiankang.business.common.UIHelper;
import com.yhjiankang.business.http.IRequest;
import com.yhjiankang.business.http.RequestListener;
import com.yhjiankang.business.http.RequestParams;
import com.yhjiankang.business.ui.activity.OrderManageActivity;
import com.yhjiankang.business.utils.JsonUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OrderManageFragment extends Fragment implements OrderManageAdapter.OnOrderItemClickListener {


    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.swipe_refresh_widget)
    SwipeRefreshLayout swipeRefreshWidget;

    private View mRootView;
    private static final String mState = "state";
    private String state;
    private ArrayList<OrderManageItemBean.ListBean> list = new ArrayList<>();
    private OrderManageAdapter orderManageAdapter;
    private int nowPage = 1;
    private int totalPage;
    private boolean isLoadingMore = false;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_order_manage, container, false);
        ButterKnife.bind(this, mRootView);
        init();
        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void init() {
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        orderManageAdapter = new OrderManageAdapter(getActivity(), list);
        orderManageAdapter.setOnOrderItemClickListener(this);
        mRecyclerView.setAdapter(orderManageAdapter);
        state = getArguments().getString(mState);
        loadData();
        swipeRefreshWidget.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshWidget.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                nowPage = 1;
                list.clear();
                loadData();
            }
        });
        mRecyclerView.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                int totalItemCount = linearLayoutManager.getItemCount();
                // dy>0 表示向下滑动
                if (lastVisibleItem >= totalItemCount - 1 && dy > 0) {
                    if (isLoadingMore) {

                    } else {
                        if (nowPage == totalPage) {
                            AppHelper.showMessage(getActivity(), "没有更多数据了");
                            return;
                        }
                        nowPage++;
                        loadData();
                    }
                }
            }
        });
    }

    public static OrderManageFragment newInstance(String state) {
        OrderManageFragment orderManageFragment = new OrderManageFragment();
        Bundle bundle = new Bundle();
        bundle.putString(mState, state);
        orderManageFragment.setArguments(bundle);
        return orderManageFragment;
    }

    /**
     * 加载数据
     */
    private void loadData() {
        isLoadingMore = true;
        RequestParams requestParams = new RequestParams();
        requestParams.put(Constants.KEY_VOUCHER, Constants.KEY_TEST);
        requestParams.put("state", state);
        requestParams.put("page", String.valueOf(nowPage));
        requestParams.put("num", HttpApi.PAGESIZE);
        IRequest.post(getActivity(), HttpApi.URL_ORDER_LIST, requestParams, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                swipeRefreshWidget.setRefreshing(false);
                isLoadingMore = false;
                OrderManageItemBean orderManageItemBean = JsonUtils.object(json, OrderManageItemBean.class);
                String num = orderManageItemBean.getTotal();
                totalPage = orderManageItemBean.getPage_total();
                list.addAll(orderManageItemBean.getList());
                OrderManageActivity orderManageActivity = (OrderManageActivity) getActivity();
                switch (state) {
                    case "50":
                        orderManageActivity.adapter.mFragmentTitles.set(0, "全部\n" + num);
                        orderManageActivity.adapter.notifyDataSetChanged();
                        break;
                    case "10":
                        orderManageActivity.adapter.mFragmentTitles.set(1, "待付款\n" + num);
                        orderManageActivity.adapter.notifyDataSetChanged();
                        break;
                    case "20":
                        orderManageActivity.adapter.mFragmentTitles.set(2, "待发货\n" + num);
                        orderManageActivity.adapter.notifyDataSetChanged();
                        break;
                    case "30":
                        orderManageActivity.adapter.mFragmentTitles.set(3, "已发货\n" + num);
                        orderManageActivity.adapter.notifyDataSetChanged();
                        break;
                    case "60":
                        orderManageActivity.adapter.mFragmentTitles.set(4, "退款中\n" + num);
                        orderManageActivity.adapter.notifyDataSetChanged();
                        break;
                    case "40":
                        orderManageActivity.adapter.mFragmentTitles.set(5, "已完成\n" + num);
                        orderManageActivity.adapter.notifyDataSetChanged();
                        break;
                    case "0":
                        orderManageActivity.adapter.mFragmentTitles.set(6, "已关闭\n" + num);
                        orderManageActivity.adapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
                orderManageAdapter.notifyDataSetChanged();
            }

            @Override
            public void requestError(VolleyError e) {
                swipeRefreshWidget.setRefreshing(false);
                isLoadingMore = false;
            }

            @Override
            public void resultNoData(String json) {
                swipeRefreshWidget.setRefreshing(false);
                isLoadingMore = false;
            }

            @Override
            public void resultiInvalidKey(String json) {
                swipeRefreshWidget.setRefreshing(false);
                isLoadingMore = false;
            }
        });
    }

    @Override
    public void onItemClick(String orderID, String state) {
        UIHelper.showOrderDetailedActivity(getActivity(), orderID, state);
    }
}
