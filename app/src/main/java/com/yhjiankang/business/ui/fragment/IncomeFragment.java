package com.yhjiankang.business.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
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
 * Project : 91健康商城
 * Program Name : com.yhjiankang.business.ui.fragment.IncomeFragment.java
 * Author :马庆龙
 * Creation Date : 2015-06-07 上午10:56
 * Description：收入
 * ModificationHistory ：
 */
public class IncomeFragment extends Fragment {

    @Bind(R.id.barchart)
    BarChart mChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income, container, false);
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

        mChart.setDescription("");
        mChart.setBackgroundColor(getResources().getColor(R.color.white));
        mChart.setMaxVisibleValueCount(10);
        mChart.setHorizontalScrollBarEnabled(false);
        mChart.setPinchZoom(true);
        mChart.setDrawBarShadow(false);
        mChart.setDrawGridBackground(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        xAxis.setSpaceBetweenLabels(0);
        xAxis.setDrawGridLines(false);

        mChart.getAxisLeft().setDrawGridLines(true);
        mChart.getAxisLeft().setSpaceBottom(0);
        mChart.animateXY(2000, 2000);
        mChart.setDragEnabled(false);
        mChart.setScaleEnabled(false);
        mChart.getLegend().setEnabled(false);
        mChart.getAxisRight().setEnabled(false);
        mChart.setBottom(10);
        setData(12, 50);
    }

    private void setData(int count, float range) {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < 7; i++) {
            xVals.add("6.07");
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        int j = 100;
        for (int i = 0; i < 7; i++) {
            yVals1.add(new BarEntry(j + 50, i));
            j += 100;
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
            set1 = new BarDataSet(yVals1, "本周");
            set1.setColor(rgb("#EC6D42"));
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(xVals, dataSets);

            mChart.setData(data);
//            mChart.invalidate();
//            Matrix mMatrix = new Matrix();
//            mMatrix.postScale(2.5f, 1f);
//            mChart.getViewPortHandler().refresh(mMatrix, mChart, false);
        }
    }
}
