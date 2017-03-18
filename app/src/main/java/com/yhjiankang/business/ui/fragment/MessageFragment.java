package com.yhjiankang.business.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yhjiankang.business.R;
import com.yhjiankang.business.ui.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Project : 91健康商城
 * Program Name : com.yhjiankang.business.ui.fragment.MessageFragment.java
 * Author :马庆龙
 * Creation Date : 2015-06-08 上午8:58
 * Description：消息
 * ModificationHistory ：
 */
public class MessageFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "MessageFragment";

    @Bind(R.id.view_pager)
    ViewPager mViewPager;
    /**
     * 系统消息的索引
     */
    public static int INDEX_SYSTEM = 0x0001;
    /**
     * 买家消息的索引
     */
    public static int INDEX_CUSTOMER = 0x0002;
    @Bind(R.id.tv_left)
    TextView mTvLeft;
    @Bind(R.id.tv_right)
    TextView mTvRight;
    @Bind(R.id.view_indicator)
    View mViewIndicator;
    @Bind(R.id.ll_tabs)
    FrameLayout mLlTabs;
    private View mRootView;
    private MessageSecondFragment mSystemMessageSecondFragment;
    private MessageSecondFragment mCustomMessageSecondFragment;


    private int mCurrentPageIndex;
    private int mDistance;
    private int mInitDistance;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_message, container, false);
        ButterKnife.bind(this, mRootView);
        init();
        return mRootView;

    }

    public static Fragment newInstance() {
        MessageFragment fragment = new MessageFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate: 调用 ");

//        setRetainInstance(true);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView: diaoyong");
        ButterKnife.unbind(this);

    }


    private void init() {

        if (mViewPager != null) {
            setupViewPager(mViewPager);
            mViewPager.setOffscreenPageLimit(1);
            int width = mLlTabs.getLayoutParams().width;

            Log.i(TAG, "init: diaoyong" + width);
            mInitDistance = width * 33 / 400;
            mDistance = width * 200 / 400;

            mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    FrameLayout.LayoutParams lp = (android.widget.FrameLayout.LayoutParams) mViewIndicator
                            .getLayoutParams();

                    if (mCurrentPageIndex == 0 && position == 0)// 0->1
                    {
                        lp.leftMargin = (int) (positionOffset * mDistance + mInitDistance);
                    } else if (mCurrentPageIndex == 1 && position == 0)// 1->0
                    {
                        lp.leftMargin = (int) (mCurrentPageIndex * mDistance + (positionOffset - 1)
                                * mDistance);
                    }
                    mViewIndicator.setLayoutParams(lp);
                }

                @Override
                public void onPageSelected(int position) {
                    if (position == 0) {
                        mTvLeft.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
                        mTvRight.setTextColor(getActivity().getResources().getColor(R.color.colorTextPrimary));
                        mTvLeft.setBackgroundResource(R.drawable.shape_message_left_focus);
                        mTvRight.setBackgroundResource(R.drawable.shape_message_right);
                    } else if (position == 1) {
                        mTvRight.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
                        mTvLeft.setTextColor(getActivity().getResources().getColor(R.color.colorTextPrimary));
                        mTvRight.setBackgroundResource(R.drawable.shape_message_right_focus);
                        mTvLeft.setBackgroundResource(R.drawable.shape_message_left);
                    }

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        }
    }


    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getChildFragmentManager()
        );
        Log.i(TAG, "setupViewPager: adapter");
        mSystemMessageSecondFragment = MessageSecondFragment.newInstance(INDEX_SYSTEM);
        mCustomMessageSecondFragment = MessageSecondFragment.newInstance(INDEX_CUSTOMER);
        adapter.addFragment(MessageSecondFragment.newInstance(INDEX_SYSTEM), "系统消息");
        adapter.addFragment(MessageSecondFragment.newInstance(INDEX_CUSTOMER), "买家消息");
        viewPager.setAdapter(adapter);
    }

    @OnClick({R.id.tv_left, R.id.tv_right})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                mViewPager.setCurrentItem(0, true);
                break;
            case R.id.tv_right:
                mViewPager.setCurrentItem(1, true);
                break;
        }

    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            Log.i(TAG, "addFragment: ");
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
