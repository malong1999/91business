package com.yhjiankang.business.chat.view.actionspanel;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.yhjiankang.business.R;
import com.yhjiankang.business.chat.view.inputpanel.OnActionsPanelItemSeletorListener;

import static android.support.v4.app.ActivityCompat.startActivityForResult;


/**
 * 更多操作模块
 * Created by hzxuwen on 2015/6/17.
 */
public class ActionsPanel {
    private static final int IMAGE_REQUEST_CODE = 0;

    private static final int CAMERA_REQUEST_CODE = 1;

    private static final int RESULT_REQUEST_CODE = 2;

    private static LinearLayout mLlImage;
    private static LinearLayout mLlPhotograph;

    // 初始化更多布局adapter
    public static void init(final View view, final OnActionsPanelItemSeletorListener listener) {

        mLlImage = (LinearLayout) view.findViewById(R.id.ll_action_image);
        mLlPhotograph = (LinearLayout) view.findViewById(R.id.ll_action_photograph);

        mLlImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2015/6/20 选图片
                listener.onImageSelect();

            }
        });

        mLlPhotograph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2015/6/20 拍照片
                listener.onCaptureSelect();
            }
        });
    }






}
