package com.yhjiankang.business.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.yhjiankang.business.R;
import com.yhjiankang.business.adapter.CommentAdapter;
import com.yhjiankang.business.api.HttpApi;
import com.yhjiankang.business.bean.GoodsCommentBean;
import com.yhjiankang.business.common.AppHelper;
import com.yhjiankang.business.common.Constants;
import com.yhjiankang.business.common.UIHelper;
import com.yhjiankang.business.http.IRequest;
import com.yhjiankang.business.http.RequestListener;
import com.yhjiankang.business.http.RequestParams;
import com.yhjiankang.business.interfaces.OnStoreCommentItemClickListener;
import com.yhjiankang.business.ui.activity.CommentListActivity;
import com.yhjiankang.business.utils.JsonUtils;
import com.yhjiankang.business.widegt.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 马小布 on 2015-06-24.
 */

public class CommentFragment extends Fragment implements OnStoreCommentItemClickListener {

    private static final String mGoodsID = "goodsID";
    private static final String mState = "state";
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipe_refresh_widget)
    SwipeRefreshLayout swipeRefreshWidget;
    private int nowPage = 1;
    private int totalPage;
    private boolean isLoadingMore = false;
    private List<GoodsCommentBean.ListBean> list = new ArrayList<>();
    private CommentAdapter commentAdapter;

    public static Fragment newInstance(String goodsID, String state) {
        CommentFragment fragment = new CommentFragment();
        Bundle bundle = new Bundle();
        bundle.putString(mGoodsID, goodsID);
        bundle.putString(mState, state);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        ButterKnife.bind(this, view);
        init();
        loadData();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void init() {
        commentAdapter = new CommentAdapter(list);
        commentAdapter.setOnStoreCommentItemClickListener(this);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL, getResources().getColor(R.color.gray_light));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(commentAdapter);
        swipeRefreshWidget.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshWidget.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                nowPage = 1;
                list.clear();
                loadData();
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

    @Override
    public void onItemClick(String id) {
        UIHelper.showCommentDetailedActivity(getActivity(), id);
    }

    private void loadData() {
        RequestParams requestParams = new RequestParams();
        requestParams.put(Constants.KEY_VOUCHER, Constants.KEY_TEST);
        requestParams.put("goods_id", getArguments().getString(mGoodsID));
        requestParams.put("page", String.valueOf(nowPage));
        requestParams.put("num", HttpApi.PAGESIZE);
        requestParams.put("state", getArguments().getString(mState));
        IRequest.post(getActivity(), HttpApi.URL_GOODSCOMMENT, requestParams, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                swipeRefreshWidget.setRefreshing(false);
                isLoadingMore = false;
                GoodsCommentBean goodsCommentBean = JsonUtils.object(json, GoodsCommentBean.class);
                String num = goodsCommentBean.getTotal();
                totalPage = goodsCommentBean.getPage_total();
                List<GoodsCommentBean.ListBean> goods = goodsCommentBean.getList();
                CommentListActivity activity = (CommentListActivity) getActivity();
                list.addAll(goods);
                switch (getArguments().getString(mState)) {
                    case "0":
                        activity.adapter.mFragmentTitles.set(0, "全部\n" + num);
                        activity.adapter.notifyDataSetChanged();
                        break;
                    case "3":
                        activity.adapter.mFragmentTitles.set(1, "好评\n" + num);
                        activity.adapter.notifyDataSetChanged();
                        break;
                    case "2":
                        activity.adapter.mFragmentTitles.set(2, "中评\n" + num);
                        activity.adapter.notifyDataSetChanged();
                        break;
                    case "1":
                        activity.adapter.mFragmentTitles.set(3, "差评\n" + num);
                        activity.adapter.notifyDataSetChanged();
                        break;
                    case "4":
                        activity.adapter.mFragmentTitles.set(4, "晒图\n" + num);
                        activity.adapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
                commentAdapter.notifyDataSetChanged();
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

}
