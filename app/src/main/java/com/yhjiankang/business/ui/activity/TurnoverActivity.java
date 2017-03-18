package com.yhjiankang.business.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.yhjiankang.business.R;
import com.yhjiankang.business.ui.BaseActivity;
import com.yhjiankang.business.ui.fragment.AccessAmountFragment;
import com.yhjiankang.business.ui.fragment.IncomeFragment;
import com.yhjiankang.business.widegt.MyViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 营业额
 * Created by 马小布 on 2015-06-07.
 */

public class TurnoverActivity extends BaseActivity {

    @Bind(R.id.tv_title_common)
    TextView tvTitleCommon;
    @Bind(R.id.toolbar_common)
    Toolbar toolbarCommon;
    @Bind(R.id.tabs)
    TabLayout tabs;
    @Bind(R.id.viewpager)
    MyViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turnover);
        ButterKnife.bind(this);
        init();
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

    private void init() {
        setCommonBackToolBar(toolbarCommon,tvTitleCommon,"营业额");
        if (viewpager != null) {
            setupViewPager(viewpager);
            viewpager.setOffscreenPageLimit(1);
        }
        tabs.setupWithViewPager(viewpager);
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new IncomeFragment(), "收入");
        adapter.addFragment(new AccessAmountFragment(), "访客下单");
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

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
