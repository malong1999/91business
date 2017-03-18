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
import com.yhjiankang.business.common.UIHelper;
import com.yhjiankang.business.interfaces.OnStoreCommentItemClickListener;
import com.yhjiankang.business.ui.BaseActivity;
import com.yhjiankang.business.ui.fragment.CommentFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 马小布 on 2015-06-24.
 */

public class CommentListActivity extends BaseActivity implements OnStoreCommentItemClickListener {
    @Bind(R.id.tv_title_common)
    TextView tvTitleCommon;
    @Bind(R.id.toolbar_common)
    Toolbar toolbarCommon;
    @Bind(R.id.tabs)
    TabLayout tabs;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    public Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
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
        setCommonBackToolBar(toolbarCommon, tvTitleCommon, "评价列表");
        setupViewPager(viewpager);
        viewpager.setOffscreenPageLimit(5);
        tabs.setupWithViewPager(viewpager);
    }

    private void setupViewPager(ViewPager viewPager) {
        String goodsID = getIntent().getStringExtra("id");
        adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(CommentFragment.newInstance(goodsID, "0"), "全部\n0");
        adapter.addFragment(CommentFragment.newInstance(goodsID, "3"), "好评\n0");
        adapter.addFragment(CommentFragment.newInstance(goodsID, "2"), "中评\n0");
        adapter.addFragment(CommentFragment.newInstance(goodsID, "1"), "差评\n0");
        adapter.addFragment(CommentFragment.newInstance(goodsID, "4"), "晒单\n0");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onItemClick(String id) {
        UIHelper.showCommentDetailedActivity(this, id);
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
