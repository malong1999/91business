package com.yhjiankang.business.ui.fragment;


import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yhjiankang.business.R;
import com.yhjiankang.business.adapter.MessageNotificationAdapter;

import java.util.ArrayList;

public class MessageSecondFragment extends Fragment {

    private static final String TAG = "MessageSecondFragment";
    private View mRootView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;

    /*_______假——————————————*/
    private ArrayList<String> mData;


    public MessageSecondFragment() {
        // Required empty public constructor
    }

    public static MessageSecondFragment newInstance(int index) {
        MessageSecondFragment messageSecondFragment = new MessageSecondFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        messageSecondFragment.setArguments(args);
        return messageSecondFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: diaoyong ");
        mRootView = inflater.inflate(R.layout.fragment_message_second, container, false);

        init();

        return mRootView;
    }

    private void init() {
        mData = new ArrayList<>();
        mData.add("用户名");
        mData.add("2015-01-01");
        mData.add("啊士大夫看见哈圣诞快乐减肥哈是独立空间啊空手道解放和科技时代和风口浪尖撒旦和卡就是废话会计师大后方看哈撒疯狂的撒谎ask记得发哈斯卡了等会付款了四大护法");
        // TODO: 2015/6/15 以上是废话有数据删了

        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.sz_10);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
        mRecyclerView.setAdapter(new MessageNotificationAdapter(getContext(), mData));


    }


    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            if (parent.getChildPosition(view) != 0)
                outRect.top = space;
        }
    }


}
