package com.yhjiankang.business.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.yhjiankang.business.R;
import com.yhjiankang.business.common.UIHelper;
import com.yhjiankang.business.ui.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FindPasswordByPhoneActivity extends BaseActivity {

    @Bind(R.id.tv_title_common)
    TextView tvTitleCommon;
    @Bind(R.id.toolbar_common)
    Toolbar toolbarCommon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password_by_phone);
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
     * 下一步
     *
     * @param view
     */
    public void nextStepOnClick(View view) {
        UIHelper.showSetPasswordActivity(this);
    }
}
