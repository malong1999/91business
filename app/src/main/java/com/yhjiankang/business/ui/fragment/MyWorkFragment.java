package com.yhjiankang.business.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.yhjiankang.business.App;
import com.yhjiankang.business.R;
import com.yhjiankang.business.api.HttpApi;
import com.yhjiankang.business.bean.HomeStoreInfoBean;
import com.yhjiankang.business.bean.TurnoverOrderCountBean;
import com.yhjiankang.business.common.Constants;
import com.yhjiankang.business.common.UIHelper;
import com.yhjiankang.business.http.IRequest;
import com.yhjiankang.business.http.RequestListener;
import com.yhjiankang.business.http.RequestParams;
import com.yhjiankang.business.ui.BaseFragment;
import com.yhjiankang.business.ui.activity.ShopInformationActivity;
import com.yhjiankang.business.ui.activity.ShopManagementActivity;
import com.yhjiankang.business.utils.JsonUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Project : 91健康商城
 * Program Name : com.yhjiankang.business.ui.fragment.MyWorkFragment.java
 * Author :马庆龙
 * Creation Date : 2015-06-06 上午8:52
 * Description：我的工作台
 * ModificationHistory ：
 */
public class MyWorkFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = "MyWorkFragment";

    @Bind(R.id.iv_search)
    ImageView ivSearch;
    @Bind(R.id.civ_head)
    CircularImageView civHead;
    @Bind(R.id.tv_jrsr)
    TextView tvJrsr;
    @Bind(R.id.tv_shouru)
    TextView tvShouru;
    @Bind(R.id.tv_zengj)
    TextView tvZengj;
    @Bind(R.id.tv_order_count)
    TextView tvOrderCount;
    @Bind(R.id.tv_visitor_count)
    TextView tvVisitorCount;

    @Bind(R.id.rl_turnover)
    RelativeLayout rlTurnover;

    @Bind(R.id.iv_shop_management)
    ImageView ivShopManagement;

    @Bind(R.id.iv_uploadgoods)
    ImageView ivUploadgoods;
    @Bind(R.id.tv_storename)
    TextView tvStorename;
    @Bind(R.id.iv_spgl)
    ImageView ivSpgl;
    @Bind(R.id.iv_ddgl)
    ImageView ivDdgl;
    @Bind(R.id.ll_data)
    LinearLayout llData;
    @Bind(R.id.iv_zhgl)
    ImageView ivZhgl;

    private String homeStoreInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mywork, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        init();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public static Fragment newInstance() {
        MyWorkFragment fragment = new MyWorkFragment();
        return fragment;
    }

    private void init() {
        getStoreData();
        getTurnoverOrderCount();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_mywork, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @OnClick({R.id.rl_turnover, R.id.iv_shop_management, R.id.civ_head, R.id.iv_uploadgoods, R.id.iv_spgl, R.id.iv_ddgl, R.id.iv_search, R.id.ll_data, R.id.iv_zhgl})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.rl_turnover:
                UIHelper.showTurnoverActivity(getActivity());
                break;
            case R.id.iv_shop_management:
                if (!homeStoreInfo.equals("")) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), ShopManagementActivity.class);
                    intent.putExtra("homeStoreInfo", homeStoreInfo);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "检查网络连接", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.civ_head:
//                if (!homeStoreInfo.equals("")) {
                if (true){
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), ShopInformationActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "检查网络连接", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.iv_uploadgoods:
                UIHelper.showUploadGoodsActivity(getActivity());
                break;
            case R.id.iv_spgl:
                UIHelper.showUploadGoodsManageActivity(getActivity(), homeStoreInfo);
                break;
            case R.id.iv_ddgl:
                UIHelper.showOrderManageActivity(getActivity());
                break;
            case R.id.iv_search:
                UIHelper.showSearchActivity(getContext());
                break;
            case R.id.ll_data:
                UIHelper.showTurnoverActivity(getActivity());
                break;
            case R.id.iv_zhgl:
                UIHelper.showAccountManageActivity(getActivity());
                break;
            default:
                break;
        }
    }

    /**
     * 获取店铺信息
     */
    private void getStoreData() {
        RequestParams requestParams = new RequestParams();
        requestParams.put(Constants.KEY_VOUCHER, Constants.KEY_TEST);
        IRequest.post(getActivity(), HttpApi.URL_STOREINFO, requestParams, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                homeStoreInfo = json;
                HomeStoreInfoBean homeStoreInfoBean = JsonUtils.object(json, HomeStoreInfoBean.class);
                App.getInstance().mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + homeStoreInfoBean.getStore().getStore_label(), civHead, App.getInstance().options);
                App.getInstance().setStoreLablePath(homeStoreInfoBean.getStore().getStore_label());
                double todayMoney;
                double yesterdayMoney;
                if (homeStoreInfoBean.getCount().getTodayMoney().equals("")) {
                    todayMoney = 0.0;
                } else {
                    todayMoney = Double.parseDouble(homeStoreInfoBean.getCount().getTodayMoney());
                }
                if (homeStoreInfoBean.getCount().getYesterdayMoney().equals("")) {
                    yesterdayMoney = 0.0;
                } else {
                    yesterdayMoney = Double.parseDouble(homeStoreInfoBean.getCount().getYesterdayMoney());
                }
                double increase = todayMoney - yesterdayMoney;

                tvShouru.setText(todayMoney + "元");
                tvZengj.setText("比昨日收入增加:" + increase + "元");
//                tvOrderCount.setText(homeStoreInfoBean.getCount().get);
                tvVisitorCount.setText(homeStoreInfoBean.getCount().getTodayVisitor());
                tvStorename.setText(homeStoreInfoBean.getStore().getStore_name());
                App.getInstance().setStorePhone(homeStoreInfoBean.getStore().getStore_phone());
                App.getInstance().setStoreComment(homeStoreInfoBean.getStore().getStore_announcement());
                App.getInstance().setStoreOpenTime(homeStoreInfoBean.getStore().getStore_workingtime());
                App.getInstance().setStoreAddress(homeStoreInfoBean.getStore().getStore_address());
            }

            @Override
            public void requestError(VolleyError e) {
                Log.i(TAG, "requestError: " + e);




            }

            @Override
            public void resultNoData(String json) {
                Log.i(TAG, "resultNoData: " + json);

            }

            @Override
            public void resultiInvalidKey(String json) {
                Log.i(TAG, "resultiInvalidKey: " + json);
            }
        });
    }

    /**
     * 获取订单数
     * 也是醉了
     */
    private void getTurnoverOrderCount() {
        RequestParams requestParams = new RequestParams();
        requestParams.put(Constants.KEY_VOUCHER, Constants.KEY_TEST);
        IRequest.post(getActivity(), HttpApi.URL_TURNOVERORDERCOUNT, requestParams, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                TurnoverOrderCountBean bean = JsonUtils.object(json, TurnoverOrderCountBean.class);
                tvOrderCount.setText(bean.getTodayOrder());
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
}
