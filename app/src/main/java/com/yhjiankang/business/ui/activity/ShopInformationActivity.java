package com.yhjiankang.business.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.borax12.materialdaterangepicker.time.RadialPickerLayout;
import com.borax12.materialdaterangepicker.time.TimePickerDialog;
import com.yhjiankang.business.R;
import com.yhjiankang.business.api.HttpApi;
import com.yhjiankang.business.bean.HomeStoreInfoBean;
import com.yhjiankang.business.common.AppHelper;
import com.yhjiankang.business.common.Constants;
import com.yhjiankang.business.http.IRequest;
import com.yhjiankang.business.http.RequestListener;
import com.yhjiankang.business.http.RequestParams;
import com.yhjiankang.business.ui.BaseActivity;
import com.yhjiankang.business.utils.JsonUtils;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShopInformationActivity extends BaseActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {
    public static final String TAG = "ShopInformationActivity";
    @Bind(R.id.tv_title_common)
    TextView mTvTitleCommon;
    @Bind(R.id.toolbar_common)
    Toolbar mToolbarCommon;
    @Bind(R.id.tv_waiter_time)
    TextView mTvWaiterTime;
    @Bind(R.id.ll_waiter_time)
    LinearLayout mLlWaiterTime;
    @Bind(R.id.et_store_phone)
    EditText mEtStorePhone;
    @Bind(R.id.ll_contact_phone)
    LinearLayout mLlContactPhone;
    @Bind(R.id.tv_business_state)
    TextView mTvBusinessState;
    @Bind(R.id.ll_business_state)
    LinearLayout mLlBusinessState;
    @Bind(R.id.et_store_address)
    EditText etStoreAddress;
    @Bind(R.id.et_comment)
    EditText etComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_information);
        ButterKnife.bind(this);
        setCommonBackToolBar(mToolbarCommon, mTvTitleCommon, "店铺资料");
        initData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_storeinfo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_save) {
            saveInfo();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initData() {
        etStoreAddress.setText(myApplication.getStoreAddress());
//        mTvWaiterTime.setText(mHomeStoreInfoBean.getStore().getStore_workingtime().substring(4));
        mTvWaiterTime.setText(myApplication.getStoreOpenTime());
        mEtStorePhone.setText(myApplication.getStorePhone());
        etComment.setText(myApplication.getStoreComment());
        mTvBusinessState.setText("营业中");


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @OnClick({R.id.ll_business_state, R.id.ll_waiter_time})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_waiter_time:
                showBriWheelView();
                break;

            case R.id.ll_business_state:
                showBusinessStateSelector();
                break;
            default:
                break;
        }

    }

    /**
     * 显示营业状态选择
     */
    private void showBusinessStateSelector() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(R.array.business_state, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mTvBusinessState.setText(getResources().getStringArray(R.array.business_state)[which]);

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void showBriWheelView() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                ShopInformationActivity.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );
        tpd.show(getFragmentManager(), "Timepickerdialog");

    }

    /**
     * 时间选择器回调
     *
     * @param view
     * @param hourOfDay
     * @param minute
     * @param hourOfDayEnd
     * @param minuteEnd
     */
    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {
        //https://github.com/borax12/MaterialDateRangePicker
        mTvWaiterTime.setText(hourOfDay + ":" + minute + "-" + hourOfDayEnd + ":" + minuteEnd);

    }

    private void saveInfo() {
        RequestParams requestParams = new RequestParams();
        requestParams.put(Constants.KEY_VOUCHER, Constants.KEY_TEST);
        requestParams.put("store_announcement", etComment.getText().toString());
        requestParams.put("store_phone", mEtStorePhone.getText().toString());
        requestParams.put("store_address", etStoreAddress.getText().toString());
        requestParams.put("store_workingtime", mTvWaiterTime.getText().toString());
        IRequest.post(this, HttpApi.URL_SETSTOREINFO, requestParams, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AppHelper.showMessage(ShopInformationActivity.this, "保存成功");
                myApplication.setStoreAddress(etStoreAddress.getText().toString());
                myApplication.setStoreComment(etComment.getText().toString());
                myApplication.setStoreOpenTime(mTvWaiterTime.getText().toString());
                myApplication.setStorePhone(mEtStorePhone.getText().toString());
                finish();
            }

            @Override
            public void requestError(VolleyError e) {

            }

            @Override
            public void resultNoData(String json) {

            }

            @Override
            public void resultiInvalidKey(String json) {

            }
        });
    }
}
