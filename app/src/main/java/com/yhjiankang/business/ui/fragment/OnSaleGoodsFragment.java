package com.yhjiankang.business.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.VolleyError;
import com.yhjiankang.business.R;
import com.yhjiankang.business.adapter.GoodsListAdapter;
import com.yhjiankang.business.api.HttpApi;
import com.yhjiankang.business.bean.GoodsManageItemBean;
import com.yhjiankang.business.bean.HomeStoreInfoBean;
import com.yhjiankang.business.common.AppHelper;
import com.yhjiankang.business.common.Constants;
import com.yhjiankang.business.common.UIHelper;
import com.yhjiankang.business.http.IRequest;
import com.yhjiankang.business.http.RequestListener;
import com.yhjiankang.business.http.RequestParams;
import com.yhjiankang.business.interfaces.OnGoodsItemClickListener;
import com.yhjiankang.business.ui.activity.GoodsManageActivity;
import com.yhjiankang.business.utils.JsonUtils;
import com.yhjiankang.business.widegt.DividerItemDecoration;
import com.yhjiankang.business.widegt.MyCheckBox;
import com.yhjiankang.business.widegt.spinner.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
    *Project : 91健康商城
    *Program Name : com.yhjiankang.business.ui.fragment.OnSaleGoodsFragment.java
    *Author :马庆龙
    *Creation Date : 2015-06-16 上午9:29
    *Description：出售中
    *ModificationHistory ：
*/
public class OnSaleGoodsFragment extends Fragment implements View.OnClickListener, OnGoodsItemClickListener {
    @Bind(R.id.cb_allselect)
    MyCheckBox cbAllselect;
    @Bind(R.id.tv_xj)
    TextView tvXj;
    @Bind(R.id.ns_px)
    NiceSpinner nsPx;
    @Bind(R.id.ns_pp)
    NiceSpinner nsPp;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipe_refresh_widget)
    SwipeRefreshLayout swipeRefreshWidget;
    private ArrayList<GoodsManageItemBean.GoodsBean> goodsList = new ArrayList<>();

    private GoodsListAdapter goodsListAdapter;

    //商品管理排序by时间
    public final String GOODSMANAGE_ORDERBYTIME = "1";
    //商品管理排序by销量
    public final String GOODSMANAGE_ORDERBYSALECOUNT = "2";

    //商品管理排序升序
    public final String GOODSMANAGE_SORTBYASC = "0";
    //商品管理排序降序
    public final String GOODSMANAGE_SORTBYDESC = "1";

    //商品管理在售
    public final String GOODSMANAGE_STATEINSALE = "1";
    //商品管理仓库中
    public final String GOODSMANAGE_STATEINHOUSE = "0";

    private DividerItemDecoration dividerItemDecoration;

    private int nowPage = 1;

    private int totalPage;

    private boolean isLoadingMore = false;

    //销量 时间排序
    private String mSort;
    //品牌ID
    private String mPinpaiID = "null";

    private boolean isLoadingCheck = false;

    public Handler onSaleHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.ITEMTYEP_GRIDLAYOUT:
                    switchGridLayout();
                    break;
                case Constants.ITEMTYEP_LINERLAYOUT:
                    switchLinerLayout();
                    break;
                default:
                    break;
            }

        }
    };
    private static final String mInfo = "info";

    public static Fragment newInstance(String info) {
        OnSaleGoodsFragment fragment = new OnSaleGoodsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(mInfo, info);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onsalegoods, container, false);
        ButterKnife.bind(this, view);
        init();
        loadData();
        return view;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((GoodsManageActivity) activity).setOnSaleHandler(onSaleHandler);
    }

    private void init() {
        mSort = GOODSMANAGE_ORDERBYSALECOUNT;
        List<String> dataSort = new LinkedList<>(Arrays.asList("销量", "时间"));
        List<String> dataPinpai = new LinkedList<>();
        final List<String> pinpaiID = new ArrayList<>();
        String info = getArguments().getString(mInfo);
        HomeStoreInfoBean homeStoreInfoBean = JsonUtils.object(info, HomeStoreInfoBean.class);
        List<HomeStoreInfoBean.StoreBean.BrandBean> brands = homeStoreInfoBean.getStore().getBrand();
        for (HomeStoreInfoBean.StoreBean.BrandBean brandBean : brands) {
            dataPinpai.add(brandBean.getGc_name());
            pinpaiID.add(String.valueOf(brandBean.getGc_id()));
        }
        nsPp.attachDataSource(dataPinpai);
        nsPx.attachDataSource(dataSort);
        nsPx.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    mSort = GOODSMANAGE_ORDERBYSALECOUNT;
                    swipeRefreshWidget.setRefreshing(true);
                    refreshData();
                } else {
                    mSort = GOODSMANAGE_ORDERBYTIME;
                    swipeRefreshWidget.setRefreshing(true);
                    refreshData();
                }
            }
        });
        nsPp.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPinpaiID = pinpaiID.get(position);
                refreshData();
            }
        });
        goodsListAdapter = new GoodsListAdapter(goodsList);
        goodsListAdapter.setGoodsItemClickListener(this);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL, getResources().getColor(R.color.gray_light));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(goodsListAdapter);
        cbAllselect.setOnCheckedChangeListener(new MyCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MyCheckBox checkBox, boolean isChecked) {
                if (!isLoadingCheck) {
                    setCheckboxAll(isChecked);
                }
            }
        });
        swipeRefreshWidget.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshWidget.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
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

    /**
     * 获取数据
     */
    private void loadData() {
        isLoadingMore = true;
        RequestParams requestParams = new RequestParams();
        requestParams.put(Constants.KEY_VOUCHER, Constants.KEY_TEST);
        requestParams.put("page", String.valueOf(nowPage));
        requestParams.put("num", HttpApi.PAGESIZE);
        requestParams.put("order", mSort);
        requestParams.put("sort", GOODSMANAGE_SORTBYDESC);
        requestParams.put("brand_id", mPinpaiID);
        requestParams.put("goods_state", GOODSMANAGE_STATEINSALE);
        IRequest.post(getActivity(), HttpApi.URL_GOODSMANAGELIST, requestParams, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                swipeRefreshWidget.setRefreshing(false);
                isLoadingMore = false;
                GoodsManageItemBean goodsManageItemBean = JsonUtils.object(json, GoodsManageItemBean.class);
                List<GoodsManageItemBean.GoodsBean> goodsBeanList = goodsManageItemBean.getList();
                goodsList.addAll(goodsBeanList);
                goodsListAdapter.notifyDataSetChanged();
                if (cbAllselect.isChecked()) {
                    isLoadingCheck = true;
                    cbAllselect.setChecked(false);
                    isLoadingCheck = false;
                }
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

    /**
     * 全选设置
     *
     * @param isCheckedAll
     */
    private void setCheckboxAll(boolean isCheckedAll) {
        for (GoodsManageItemBean.GoodsBean goodsBean : goodsList) {
            goodsBean.setChecked(isCheckedAll);
        }
        goodsListAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.tv_xj)
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_xj:
                offGoods();
                break;
            default:
                break;
        }

    }

    private void switchGridLayout() {
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.removeItemDecoration(dividerItemDecoration);
        goodsListAdapter.setItemType(Constants.ITEMTYEP_GRIDLAYOUT);
        goodsListAdapter.notifyDataSetChanged();
    }

    private void switchLinerLayout() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        goodsListAdapter.setItemType(Constants.ITEMTYEP_LINERLAYOUT);
        goodsListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGoodsItemClick(String goodsId) {
        UIHelper.showGoodsDeatailedActivity(getActivity(), goodsId);
    }

    @Override
    public void onGoodsItemLongClick(String goodsId, int position) {
        showDeleteDialog(goodsId, position);
    }


    private void offGoods() {
        getGoodsChecked();
        if (list.size() == 0) {
            AppHelper.showMessage(getActivity(), "请选择商品");
            return;
        }
        RequestParams requestParams = new RequestParams();
        requestParams.put(Constants.KEY_VOUCHER, Constants.KEY_TEST);
        requestParams.put("goods_id", parseMyString(list.toString()));
        IRequest.post(getActivity(), HttpApi.URL_OOFGOODS, requestParams, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AppHelper.showMessage(getActivity(), "下架成功");
                swipeRefreshWidget.setRefreshing(true);
                refreshData();
            }

            @Override
            public void requestError(VolleyError e) {

            }

            @Override
            public void resultNoData(String json) {
                AppHelper.showMessage(getActivity(), json);
            }

            @Override
            public void resultiInvalidKey(String json) {

            }
        });
    }

    private ArrayList<Integer> list = new ArrayList<>();

    /**
     * 获取选中商品
     */
    private void getGoodsChecked() {

        for (GoodsManageItemBean.GoodsBean goodsBean : goodsList) {
            boolean isChecked = goodsBean.isChecked();
            if (isChecked) {
                list.add(Integer.parseInt(goodsBean.getGoods_id()));
            }
        }
    }


    /**
     * 转成格式  "1,1"
     *
     * @param str
     * @return
     */
    private String parseMyString(String str) {
        String string = str.substring(1, str.length() - 1);
        return string;
    }

    /**
     * 刷新
     */
    private void refreshData() {
        nowPage = 1;
        goodsList.clear();
        loadData();
    }

    public void showDeleteDialog(final String id, final int position) {
        new MaterialDialog.Builder(getActivity())
                .title("提示")
                .titleColor(getActivity().getResources().getColor(R.color.black))
                .content("您确认将此商品删除吗?")
                .contentColor(getActivity().getResources().getColor(R.color.black))
                .backgroundColor(getActivity().getResources().getColor(R.color.white))
                .positiveColor(getActivity().getResources().getColor(R.color.black))
                .negativeColor(getActivity().getResources().getColor(R.color.black))
                .positiveText("确定")
                .negativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        deleteGoods(id, position);
                    }
                })
                .show();
    }

    /**
     * 删除商品
     *
     * @param id
     */
    private void deleteGoods(String id, final int position) {
        RequestParams requestParams = new RequestParams();
        requestParams.put(Constants.KEY_VOUCHER, Constants.KEY_TEST);
        requestParams.put("goods_id", id);
        IRequest.post(getActivity(), HttpApi.URL_DELETEGOODS, requestParams, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AppHelper.showMessage(getActivity(), "删除成功");
                goodsListAdapter.notifyItemRemoved(position);
            }

            @Override
            public void requestError(VolleyError e) {
                AppHelper.showMessage(getActivity(), "删除失败");
            }

            @Override
            public void resultNoData(String json) {
                AppHelper.showMessage(getActivity(), json);
            }

            @Override
            public void resultiInvalidKey(String json) {

            }
        });
    }
}
