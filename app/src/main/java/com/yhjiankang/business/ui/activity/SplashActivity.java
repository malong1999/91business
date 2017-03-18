package com.yhjiankang.business.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yhjiankang.business.R;
import com.yhjiankang.business.common.UIHelper;
import com.yhjiankang.business.ui.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity {

    private Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        myApplication.setLoginKey("fea7b081709c791e5961ab154bbf7486");

        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                SplashActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        UIHelper.showLoginActivity(SplashActivity.this);
                        finish();
                    }
                });
            }
        }, 1000);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimer.cancel();

    }
}
