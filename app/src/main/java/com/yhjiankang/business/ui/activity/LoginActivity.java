package com.yhjiankang.business.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.yhjiankang.business.R;
import com.yhjiankang.business.bean.LoginBean;
import com.yhjiankang.business.common.Constants;
import com.yhjiankang.business.common.UIHelper;
import com.yhjiankang.business.ui.BaseActivity;
import com.yhjiankang.business.utils.DensityUtil;
import com.yhjiankang.business.utils.StorageUtils;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";

    @Bind(R.id.civ_head)
    CircularImageView mCivHead;
    @Bind(R.id.et_user_name)
    EditText mEtUserName;
    @Bind(R.id.iv_historical_account)
    ImageView mIvHistoricalAccount;
    @Bind(R.id.ll_user_name)
    LinearLayout mLlUserName;
    @Bind(R.id.et_password)
    EditText mEtPassword;
    @Bind(R.id.tv_remember_password)
    TextView mTvRememberPassword;
    @Bind(R.id.check_box)
    CheckBox mCheckBox;
    @Bind(R.id.btn_login)
    Button mBtnLogin;
    @Bind(R.id.tv_register)
    TextView mTvRegister;

    @Bind(R.id.keyboard_linear_layout_view)
    LinearLayout mKeyboardLinearLayoutView;
    @Bind(R.id.iv_password_showing)
    ImageView mIvPasswordShowing;
    @Bind(R.id.tv_forget_password)
    TextView mTvForgetPassword;

    //历史账户列表
    private String[] accountList;

    //历史账户弹出的popu
    private PopupWindow mPopWindow;

    //软件盘是否弹出过
    private boolean keyboardIsShowing;
    private boolean passwordShowing;

    private int screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        mKeyboardLinearLayoutView.getViewTreeObserver().addOnGlobalLayoutListener(new MyOnKeyboardListener());
        mIvHistoricalAccount.setVisibility(View.VISIBLE);
        if (myApplication.getAccountList().toString().length() > 3)
            accountList = myApplication.getAccountList().toString().substring(1, myApplication.getAccountList().toString().length() - 1).split(",");
        if (accountList == null || accountList.length < 1) {
            mIvHistoricalAccount.setVisibility(View.INVISIBLE);
        }
    }

    private void initData() {
        boolean ifRemeberPassword = myApplication.getIfRemeberPassword();
        mCheckBox.setChecked(ifRemeberPassword);

        if (StorageUtils.fileIsExists(Constants.CACHE_DIR_HEADER)) {
            Bitmap bitmap = BitmapFactory.decodeFile(Constants.CACHE_DIR_HEADER);
            mCivHead.setImageBitmap(bitmap);
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }

        if (!myApplication.getLoginID().equals("-1"))
            mEtUserName.setText(myApplication.getLoginID());
        if (ifRemeberPassword && !myApplication.getLoginPassword().equals("-1")) {
            long loginTime = myApplication.getLoginTime();
            long time = new Date().getTime();
            if (loginTime != -1 && time - loginTime < 604800000)
                mEtPassword.setText(myApplication.getLoginPassword());
            Log.i(TAG, "initData: " + myApplication.getLoginPassword());
        }
        mEtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case 6:
                        Log.i(TAG, "onEditorAction: 软键盘完成相应");
                        if (prepareForLogin()) {
                            break;
                        }
                        login();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    @OnClick({R.id.btn_login, R.id.tv_register, R.id.tv_forget_password, R.id.iv_historical_account
            , R.id.et_user_name, R.id.et_password, R.id.iv_password_showing})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (prepareForLogin()) {
                    return;
                }
                login();
                break;
            case R.id.tv_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.tv_forget_password:
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
                break;
            case R.id.iv_historical_account://历史账户弹出
                if (mPopWindow == null) {
                    showPopupWindow();
                } else if (mPopWindow.isShowing()) {
                    mPopWindow.dismiss();
                } else {
                    showPopupWindow();
                }
                break;
            case R.id.et_user_name:
                mEtUserName.setText("");
                mEtPassword.setText("");
                break;
            case R.id.et_password:
                mEtPassword.setText("");
                break;
            case R.id.iv_password_showing:
                passwordShowing = !passwordShowing;
                mIvPasswordShowing.setImageResource(passwordShowing ? R.mipmap.ic_possword_can_see : R.mipmap.ic_password_right);
                mEtPassword.setInputType(passwordShowing ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                        : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;

            default:
                break;
        }
    }

    /**
     * 显示历史账户的popu
     */
    private void showPopupWindow() {
        //设置contentView
        View contentView = LayoutInflater.from(LoginActivity.this).inflate(R.layout.view_login_popu, null);
        mPopWindow = new PopupWindow(contentView,
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置各个控件的点击响应
        ListView mListView = (ListView) contentView.findViewById(R.id.list_view);
        mListView.setAdapter(new myAdapter());
        mPopWindow.showAsDropDown(mLlUserName);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mEtUserName.setText(accountList[position]);
                mEtPassword.setText("");
                mPopWindow.dismiss();
            }
        });
    }

    /**
     * 校验
     *
     * @return true用户名/密码没写
     */
    private boolean prepareForLogin() {
        if (mEtUserName.getText().toString().isEmpty()) {
            Toast.makeText(LoginActivity.this, "请输入账号", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (mEtPassword.getText().toString().isEmpty()) {
            Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }


    private void login() {
        UIHelper.showHomeActivity(LoginActivity.this);
        finish();
        /*RequestParams params = new RequestParams();
        params.put("username", mEtUserName.getText().toString().trim());
        params.put("password", mEtPassword.getText().toString().trim());
        params.put("client", "android");
        params.put("ip2long", String.valueOf(App.getInstance().ip2long));
        params.put("deviceName", App.phoneModel);
        IRequest.post(this, HttpApi.URL_LOGIN, params, new RequestListener() {
            @Override
            public void requestSuccess(String str) {
                LoginBean loginBean = JsonUtils.object(str, LoginBean.class);
                setUser(loginBean);
                showActivity();
            }

            @Override
            public void requestError(VolleyError e) {
                Toast.makeText(LoginActivity.this, "服务器挂", Toast.LENGTH_LONG).show();
            }

            @Override
            public void resultNoData(String json) {
            }

            @Override
            public void resultiInvalidKey(String json) {
            }
        });*/

    }


    class myAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return accountList.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(mContext);
            textView.setPadding(120, 0, 0, 0);
            textView.setHeight(DensityUtil.dip2px(mContext, 33));
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setText(accountList[position]);
            textView.setTextColor(getResources().getColor(R.color.colorTextHint));
            textView.setTextSize(getResources().getDimension(R.dimen.sz_10));
            return textView;
        }
    }

    class MyOnKeyboardListener implements ViewTreeObserver.OnGlobalLayoutListener {
        @Override
        public void onGlobalLayout() {
            if (screenHeight == 0)
                screenHeight = mKeyboardLinearLayoutView.getHeight();
            int heightDiff = screenHeight - mKeyboardLinearLayoutView.getHeight();
            if (heightDiff > 100) { // 说明键盘是弹出状态
//                            mKeyboardLinearLayoutView.scrollBy(0, 400);
                mCivHead.setVisibility(View.GONE);
                keyboardIsShowing = true;
            } else {
                if (keyboardIsShowing) {
//                                mKeyboardLinearLayoutView.scrollBy(0, -400);
                    mCivHead.setVisibility(View.VISIBLE);
                    keyboardIsShowing = false;
                }
            }
        }
    }

    private void setUser(LoginBean loginBean) {
        myApplication.setLoginID(mEtUserName.getText().toString().trim());
        myApplication.setLoginPassword(mEtPassword.getText().toString().trim());
        long time = new Date().getTime();
        myApplication.setLoginTime(time);
        myApplication.setLoginKey(loginBean.getVoucher());
        myApplication.setAccountType(loginBean.getType());
        myApplication.setShopState(loginBean.getState());
        myApplication.setIfRemeberPassword(mCheckBox.isChecked());
        myApplication.setAccountList(mEtUserName.getText().toString().trim());
    }

    private void showActivity() {
        if (myApplication.getShopState() == 1) {
            UIHelper.showHomeActivity(this);
        } else {
            UIHelper.showSettledActivity(this);
        }
        finish();
    }
}



