package com.yhjiankang.business.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.yhjiankang.business.R;
import com.yhjiankang.business.common.UIHelper;
import com.yhjiankang.business.ui.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ForgetPasswordActivity extends BaseActivity {


    @Bind(R.id.tv_title_common)
    TextView tvTitleCommon;
    @Bind(R.id.toolbar_common)
    Toolbar toolbarCommon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        toolbarCommon.setTitle("");
        tvTitleCommon.setText("忘记密码");
        setSupportActionBar(toolbarCommon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    /**
     * 手机找回
     * @param view
     */
    public void byPhoneOnClick(View view) {
        UIHelper.showFindPasswordByPhoneActivity(this);

    }


    /**
     * 邮箱找回
     * @param view
     */
    public void byEmailOnClick(View view) {
        UIHelper.showFindPasswordByEmailActivity(this);

    }

    /**
     * 客服找回
     * @param view
     */
    public void byWaiterOnClick(View view) {
        UIHelper.showFindPasswordByWaiterActivity(this);
    }
}
