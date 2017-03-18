package com.yhjiankang.business.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.yhjiankang.business.R;
import com.yhjiankang.business.ui.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BankInformationActivity extends BaseActivity {

    @Bind(R.id.tv_title_common)
    TextView mTvTitleCommon;
    @Bind(R.id.toolbar_common)
    Toolbar mToolbarCommon;
    @Bind(R.id.iv_bank)
    ImageView mIvBank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_information);
        ButterKnife.bind(this);

        mToolbarCommon.setTitle("");
        mTvTitleCommon.setText("银行卡");
        setSupportActionBar(mToolbarCommon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
