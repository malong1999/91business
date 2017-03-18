package com.yhjiankang.business.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhjiankang.business.R;
import com.yhjiankang.business.ui.BaseActivity;
import com.yhjiankang.business.widegt.MyCheckBox;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 马小布 on 2015-06-27.
 */

public class CreateAccountActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.et_zhanghu)
    EditText etZhanghu;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.et_passwordS)
    EditText etPasswordS;
    @Bind(R.id.et_nickname)
    EditText etNickname;
    @Bind(R.id.et_zhiwei)
    EditText etZhiwei;
    @Bind(R.id.cb_all)
    MyCheckBox cbAll;
    @Bind(R.id.cb_goodsmanage)
    MyCheckBox cbGoodsmanage;
    @Bind(R.id.cb_storemanage)
    MyCheckBox cbStoremanage;
    @Bind(R.id.cb_uploadgoods)
    MyCheckBox cbUploadgoods;
    @Bind(R.id.cb_ordermanage)
    MyCheckBox cbOrdermanage;
    @Bind(R.id.cb_messagemanage)
    MyCheckBox cbMessagemanage;
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_mail)
    EditText etMail;
    @Bind(R.id.et_beizhu)
    EditText etBeizhu;
    @Bind(R.id.btn_save)
    Button btnSave;
    @Bind(R.id.btn_back)
    Button btnBack;
    @Bind(R.id.tv_title_common)
    TextView tvTitleCommon;
    @Bind(R.id.toolbar_common)
    Toolbar toolbarCommon;
    @Bind(R.id.ll_allcb)
    LinearLayout llAllcb;
    @Bind(R.id.ll_goodsmanage)
    LinearLayout llGoodsmanage;
    @Bind(R.id.ll_storemanage)
    LinearLayout llStoremanage;
    @Bind(R.id.ll_uploadgoods)
    LinearLayout llUploadgoods;
    @Bind(R.id.ll_ordermanage)
    LinearLayout llOrdermanage;
    @Bind(R.id.ll_messagemanage)
    LinearLayout llMessagemanage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createaccount);

        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void init() {
        setCommonBackToolBar(toolbarCommon, tvTitleCommon, "新建员工账户");
    }

    @OnClick({R.id.ll_allcb, R.id.ll_uploadgoods, R.id.ll_goodsmanage, R.id.ll_ordermanage, R.id.ll_messagemanage, R.id.ll_storemanage})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ll_allcb:
                cbAll.toggle();

                break;
            case R.id.ll_uploadgoods:
                cbUploadgoods.toggle();
                break;
            case R.id.ll_goodsmanage:
                cbGoodsmanage.toggle();
                break;
            case R.id.ll_ordermanage:
                cbOrdermanage.toggle();
                break;
            case R.id.ll_messagemanage:
                cbMessagemanage.toggle();
                break;
            case R.id.ll_storemanage:
                cbStoremanage.toggle();
                break;
            default:
                break;
        }

    }
}
