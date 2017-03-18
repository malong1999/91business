package com.yhjiankang.business.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.yhjiankang.business.R;
import com.yhjiankang.business.api.HttpApi;
import com.yhjiankang.business.http.IRequest;
import com.yhjiankang.business.http.RequestListener;
import com.yhjiankang.business.http.RequestParams;
import com.yhjiankang.business.ui.BaseActivity;
import com.yhjiankang.business.ui.fragment.MessageSecondFragment;
import com.yhjiankang.business.ui.fragment.OrderManageFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 马小布 on 2015-06-16.
 */

public class OrderManageActivity extends BaseActivity {
    @Bind(R.id.tv_title_common)
    TextView mTvTitleCommon;
    @Bind(R.id.toolbar_common)
    Toolbar mToolbarCommon;
    @Bind(R.id.tabs)
    TabLayout mTabs;
    @Bind(R.id.viewpager)
    ViewPager mViewpager;
    /**
     * 当前页数
     */
    private int currentPage = 1;

    public Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_manage);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void initView() {
        setCommonBackToolBar(mToolbarCommon, mTvTitleCommon, "订单管理");
        if (mViewpager != null) {
            setupViewPager(mViewpager);
            mViewpager.setOffscreenPageLimit(6);
            mTabs.setupWithViewPager(mViewpager);
        }
    }


    private void setupViewPager(ViewPager viewPager) {
        adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(OrderManageFragment.newInstance("50"), "全部\n0");
        adapter.addFragment(OrderManageFragment.newInstance("10"), "待付款\n0");
        adapter.addFragment(OrderManageFragment.newInstance("20"), "待发货\n0");
        adapter.addFragment(OrderManageFragment.newInstance("30"), "已发货\n0");
        adapter.addFragment(OrderManageFragment.newInstance("60"), "退款中\n0");
        adapter.addFragment(OrderManageFragment.newInstance("40"), "已完成\n0");
        adapter.addFragment(OrderManageFragment.newInstance("0"), "已关闭\n0");
        viewPager.setAdapter(adapter);
    }

    public class Adapter extends FragmentPagerAdapter {
        public final List<Fragment> mFragments = new ArrayList<>();
        public final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }

    }

}
