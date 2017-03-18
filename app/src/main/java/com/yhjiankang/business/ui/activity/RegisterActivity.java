package com.yhjiankang.business.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.yhjiankang.business.App;
import com.yhjiankang.business.R;
import com.yhjiankang.business.api.HttpApi;
import com.yhjiankang.business.common.AppHelper;
import com.yhjiankang.business.http.IRequest;
import com.yhjiankang.business.http.RequestListener;
import com.yhjiankang.business.http.RequestParams;
import com.yhjiankang.business.ui.BaseActivity;
import com.yhjiankang.business.utils.StringUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.tv_title_common)
    TextView mTvTitleCommon;
    @Bind(R.id.toolbar_common)
    Toolbar mToolbarCommon;
    @Bind(R.id.check_box)
    CheckBox mCheckBox;
    @Bind(R.id.btn_register)
    Button mBtnRegister;
    @Bind(R.id.et_phone_number)
    EditText mEtPhoneNumber;
    @Bind(R.id.tv_verification_code)
    TextView mTvVerificationCode;
    @Bind(R.id.et_username)
    EditText etUsername;
    @Bind(R.id.et_password_first)
    EditText etPasswordFirst;
    @Bind(R.id.et_password_second)
    EditText etPasswordSecond;
    @Bind(R.id.et_code)
    EditText etCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setCommonBackToolBar(mToolbarCommon, mTvTitleCommon, "快速注册");

        //设置checkbox文字
        String checkString = "我已阅读并同意<font color='#3eb370'>《91健康商城企业版注册协议》</font>";
        mCheckBox.setText(Html.fromHtml(checkString));

        mTvVerificationCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerCheck();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


    /**
     * 注册校验
     */
    private void registerCheck() {
        tvSmsCaptchaCountDown(this, mTvVerificationCode, 60);
        if (StringUtils.IsNumericP(mEtPhoneNumber.getText().toString())) {
            getCode();
        } else {
            Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("mobile", mEtPhoneNumber.getText().toString());
        requestParams.put("client", "android");
        requestParams.put("ip2long", "2130706433");
        requestParams.put("deviceName", App.phoneModel);
        IRequest.post(this, HttpApi.URL_GETCODE, requestParams, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AppHelper.showMessage(RegisterActivity.this, "验证码发送成功");
            }

            @Override
            public void requestError(VolleyError e) {
                AppHelper.showMessage(RegisterActivity.this, "验证码发送错误");
            }

            @Override
            public void resultNoData(String json) {

            }

            @Override
            public void resultiInvalidKey(String json) {

            }
        });

    }

    /**
     * 短信验证码倒计时
     */

    public static void tvSmsCaptchaCountDown(final Context context, final TextView tv, int smsTime) {
        tv.setActivated(false);
        tv.setClickable(false);
        new CountDownTimer(smsTime * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                tv.setText("重新获取(" + millisUntilFinished / 1000 + ")");
            }

            public void onFinish() {
                tv.setClickable(true);
                tv.setText("获取验证码");
                tv.setActivated(true);
            }
        }.start();
    }

    private void register() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("username", etUsername.getText().toString());
        requestParams.put("password", etPasswordFirst.getText().toString());
        requestParams.put("password_confirm", etPasswordSecond.getText().toString());
        requestParams.put("mobile", mEtPhoneNumber.getText().toString());
        requestParams.put("smsCode", etCode.getText().toString());
        requestParams.put("client", "android");
        requestParams.put("ip2long", "2130706433");
        requestParams.put("deviceName", App.phoneModel);
        IRequest.post(this, HttpApi.URL_REGISTE, requestParams, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AppHelper.showMessage(RegisterActivity.this, "注册成功");
                finish();
            }

            @Override
            public void requestError(VolleyError e) {
                AppHelper.showMessage(RegisterActivity.this, "注册失败");
            }

            @Override
            public void resultNoData(String json) {

            }

            @Override
            public void resultiInvalidKey(String json) {

            }
        });

    }

    @OnClick({R.id.btn_register})
    @Override
    public void onClick(View v) {
        if (!StringUtils.isUserName(etUsername.getText().toString())) {
            AppHelper.showMessage(RegisterActivity.this, "请输入正确用户名");
            return;
        }
        if (!StringUtils.isPassword(etPasswordFirst.getText().toString())) {
            AppHelper.showMessage(RegisterActivity.this, "请正确输入密码");
            return;
        }
        if (!StringUtils.isPassword(etPasswordSecond.getText().toString())) {
            AppHelper.showMessage(RegisterActivity.this, "请正确输入密码");
            return;
        }
        if (!etPasswordFirst.getText().toString().equals(etPasswordSecond.getText().toString())) {
            AppHelper.showMessage(RegisterActivity.this, "两次输入的密码不一致");
            return;
        }
        if (!StringUtils.isMobile(mEtPhoneNumber.getText().toString())) {
            AppHelper.showMessage(RegisterActivity.this, "请正确输入手机号");
            return;
        }
        if (mCheckBox.isChecked()) {
            register();
        } else {
            AppHelper.showMessage(RegisterActivity.this, "请勾选注册协议");
        }

    }
}
