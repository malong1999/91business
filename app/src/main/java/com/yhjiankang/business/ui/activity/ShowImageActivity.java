package com.yhjiankang.business.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.yhjiankang.business.R;
import com.yhjiankang.business.common.Constants;
import com.yhjiankang.business.ui.BaseActivity;
import com.yhjiankang.business.widegt.imagescan.ChildAdapter;

import java.util.HashMap;
import java.util.List;


public class ShowImageActivity extends BaseActivity {
    private GridView mGridView;
    private List<String> list;
    private ChildAdapter adapter;
    private Button btnCancel;
    private Button btnOk;
//    private HashMap<Integer, String> mSelectMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagespan_images);

        mGridView = (GridView) findViewById(R.id.child_grid);
        list = getIntent().getStringArrayListExtra("data");
        @SuppressWarnings("unchecked")
		HashMap<String, String> mSelectMap = (HashMap<String, String>) getIntent().getSerializableExtra("allselect");
        String ptag = getIntent().getStringExtra("tag");
        adapter = new ChildAdapter(this, list, mGridView, mSelectMap, ptag);
        adapter.setTabViewListener(new ChildAdapter.ChildAdapterListener() {
            @Override
            public void onSelect(int i) {
                btnOk.setText(String.format("确定(%s/%s)", i, Constants.ADDGOODSIMAGE_COUNT));
            }
        });
        mGridView.setAdapter(adapter);


        btnCancel = (Button) findViewById(R.id.cancel);
        btnOk = (Button) findViewById(R.id.ok);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnOk.setText(String.format("确定(%s/%s)", mSelectMap.size(), Constants.ADDGOODSIMAGE_COUNT));
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> map = adapter.getSelectMap();
                Intent intent = new Intent();
                intent.putExtra("allselect", map);
                setResult(1, intent);
                finish();
            }
        });
    }
}
