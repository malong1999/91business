package com.yhjiankang.business.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.yhjiankang.business.R;
import com.yhjiankang.business.ui.BaseActivity;
import com.yhjiankang.business.ui.fragment.MessageFragment;
import com.yhjiankang.business.ui.fragment.MineFragment;
import com.yhjiankang.business.ui.fragment.MyWorkFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Project : 91健康商城
 * Program Name : com.yhjiankang.business.ui.activity.HomeActivity.java
 * Author :马庆龙
 * Creation Date : 2015-06-03 上午8:47
 * Description：功能首页
 * ModificationHistory ：
 */
public class HomeActivity extends BaseActivity {

    private static final String TAG = "HomeActivity";
    @Bind(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;

    @Bind(R.id.tv_title_common)
    TextView tvTitleCommon;

    @Bind(R.id.toolbar_common)
    Toolbar toolbarCommon;

    @Bind(R.id.fragment_container)
    FrameLayout fragmentContainer;

    private Runnable runnable;

    private Fragment nowFragment = new Fragment();

    private Fragment myWorkFragment;
    private Fragment messageFragment;
    private Fragment mineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        toolbarCommon.setTitle("");
        setSupportActionBar(toolbarCommon);
        setTitle("我的店铺");
        BadgeItem numberBadgeItem = new BadgeItem()
                .setBorderWidth(4)
                .setBackgroundColorResource(R.color.white)
                .setTextColorResource(R.color.red)
                .setText("5");

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.tab_gzt, "工作台"))
                .addItem(new BottomNavigationItem(R.mipmap.tab_message, "消息").setBadgeItem(numberBadgeItem))
                .addItem(new BottomNavigationItem(R.mipmap.tab_my, "我的"))
                .initialise();
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switchFragment(position);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
        bottomNavigationBar.setBarBackgroundColor(R.color.white);

        myWorkFragment = MyWorkFragment.newInstance();
        messageFragment = MessageFragment.newInstance();
        mineFragment = MineFragment.newInstance();
        switchContent(nowFragment, myWorkFragment);
    }

    /**
     * 底部导航跳转
     *
     * @param position
     */
    public void switchFragment(int position) {
        runnable = null;
        switch (position) {
            case 0:
                switchContent(nowFragment, myWorkFragment);
                setTitle("我的店铺");
                break;
            case 1:
                switchContent(nowFragment, messageFragment);
                setTitle("消息");
                break;
            case 2:
                switchContent(nowFragment, mineFragment);
                setTitle("我的");
                break;
            default:
                break;
        }

    }

    /**
     * 导航切换
     *
     * @param from
     * @param to
     */
    private void switchContent(Fragment from, Fragment to) {
        if (nowFragment != to) {
            nowFragment = to;

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            if (!to.isAdded()) {
                transaction.hide(from).add(R.id.fragment_container, to).commit();
            } else {
                transaction.hide(from).show(to).commit();
            }
        }
    }

    private void setTitle(String title) {
        tvTitleCommon.setText(title);
    }
}
