package com.yhjiankang.business.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.yhjiankang.business.R;
import com.yhjiankang.business.ui.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BindEmailActivity extends BaseActivity {

    @Bind(R.id.tv_title_common)
    TextView mTvTitleCommon;
    @Bind(R.id.toolbar_common)
    Toolbar mToolbarCommon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_email);
        ButterKnife.bind(this);

        mToolbarCommon.setTitle("");
        mTvTitleCommon.setText("邮箱认证");
        setSupportActionBar(mToolbarCommon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
