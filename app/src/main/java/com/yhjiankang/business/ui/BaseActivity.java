package com.yhjiankang.business.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;


import com.umeng.analytics.MobclickAgent;
import com.yhjiankang.business.App;
import com.yhjiankang.business.R;
import com.yhjiankang.business.http.RequestManager;


public class BaseActivity extends AppCompatActivity {

    public App myApplication;
    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApplication = App.getInstance();
        mContext = this;
        setStatusColor();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_remain);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestManager.cancelAll(this);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.base_slide_right_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.base_slide_right_out);
    }


    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_remain);
    }

    /**
     * 隐藏软键盘
     *
     * @param v
     */
    public void hideSoftInput(View v) {
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    /**
     * 设置手机状态栏背景色
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setStatusColor() {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);//需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        ViewGroup mContentView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            ViewCompat.setFitsSystemWindows(mChildView, true);
        }
    }

    /**
     * 设置返回toolbar
     *
     * @param toolbarCommon
     * @param title
     * @param titleString
     */
    public void setCommonBackToolBar(Toolbar toolbarCommon, TextView title, String titleString) {
        toolbarCommon.setTitle("");
        setSupportActionBar(toolbarCommon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        title.setText(titleString);
    }

}


