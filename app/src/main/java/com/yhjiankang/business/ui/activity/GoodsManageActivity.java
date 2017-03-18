package com.yhjiankang.business.ui.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yhjiankang.business.R;
import com.yhjiankang.business.common.Constants;
import com.yhjiankang.business.ui.BaseActivity;
import com.yhjiankang.business.ui.fragment.InWarehouseGoodsFragment;
import com.yhjiankang.business.ui.fragment.OnSaleGoodsFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Project : 91健康商城
 * Program Name : com.yhjiankang.business.ui.activity.GoodsManageActivity.java
 * Author :马庆龙
 * Creation Date : 2015-06-15 上午11:26
 * Description：商品管理
 * ModificationHistory ：
 */
public class GoodsManageActivity extends BaseActivity {

    @Bind(R.id.tv_title_common)
    TextView tvTitleCommon;

    @Bind(R.id.toolbar_common)
    Toolbar toolbarCommon;

    @Bind(R.id.viewpager)
    ViewPager viewpager;

    @Bind(R.id.tab)
    SegmentTabLayout tab;

    private Handler onSaleHandler;

    private Handler inWareHandler;

    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private String[] mTitles = {"出售中", "仓库中"};

    private int itemType = Constants.ITEMTYEP_LINERLAYOUT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodsmanage);
        ButterKnife.bind(this);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_goodsmanager, menu);
        return true;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.layout) {
            switchLayout(item);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 初始化
     */
    private void init() {
        setCommonBackToolBar(toolbarCommon, tvTitleCommon, "商品管理");
        setupViewPager();
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void setupViewPager() {
//        mFragments.add(OnSaleGoodsFragment.newInstance(getIntent().getStringExtra("info")));
        mFragments.add(OnSaleGoodsFragment.newInstance(null));
//        mFragments.add(InWarehouseGoodsFragment.newInstance(getIntent().getStringExtra("info")));
        mFragments.add(InWarehouseGoodsFragment.newInstance(null));
        tab.setTabData(mTitles);
        viewpager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        tab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewpager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tab.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewpager.setCurrentItem(0);
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

    public void setOnSaleHandler(Handler handler) {
        this.onSaleHandler = handler;
    }

    public void setInWareHandler(Handler handler) {
        this.inWareHandler = handler;
    }

    /**
     * 切换布局
     *
     * @param item
     */
    private void switchLayout(MenuItem item) {
        Message onSaleMsg = new Message();
        Message inHouseMsg = new Message();
        if (itemType == Constants.ITEMTYEP_LINERLAYOUT) {
            onSaleMsg.what = Constants.ITEMTYEP_GRIDLAYOUT;
            inHouseMsg.what = Constants.ITEMTYEP_GRIDLAYOUT;
            itemType = Constants.ITEMTYEP_GRIDLAYOUT;
            item.setIcon(R.mipmap.ic_view_module_white_24dp);
        } else {
            onSaleMsg.what = Constants.ITEMTYEP_LINERLAYOUT;
            inHouseMsg.what = Constants.ITEMTYEP_LINERLAYOUT;
            itemType = Constants.ITEMTYEP_LINERLAYOUT;
            item.setIcon(R.mipmap.ic_view_headline_white_24dp);
        }
        onSaleHandler.sendMessage(onSaleMsg);
        inWareHandler.sendMessage(inHouseMsg);
    }
}
