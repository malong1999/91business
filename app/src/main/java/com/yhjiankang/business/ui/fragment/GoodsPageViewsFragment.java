package com.yhjiankang.business.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.yhjiankang.business.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.github.mikephil.charting.utils.ColorTemplate.rgb;

/**
 * 商品浏览量
 * Created by 马小布 on 2015-06-21.
 */

public class GoodsPageViewsFragment extends Fragment {
    @Bind(R.id.hBarChat)
    HorizontalBarChart mChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goodspageviews, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void init() {
        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);
        mChart.setDescription("");
        mChart.setMaxVisibleValueCount(60);
        mChart.setPinchZoom(true);
        mChart.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);
        XAxis xl = mChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGridLineWidth(0f);

// this replaces setStartAtZero(true)
//        yl.setInverted(true);
        mChart.getAxisLeft().setEnabled(false);
        mChart.getAxisLeft().setDrawGridLines(false);
        setData(12, 50);
        mChart.animateY(2500);
    }

    private void setData(int count, float range) {

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < count; i++) {
            xVals.add("商品");
            yVals1.add(new BarEntry((float) (Math.random() * range), i));
        }

        BarDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setYVals(yVals1);
            mChart.getData().setXVals(xVals);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "浏览量");
            set1.setColor(rgb("#EC6D42"));
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            BarData data = new BarData(xVals, dataSets);
            data.setValueTextSize(10f);
            mChart.setData(data);
        }
    }
}
