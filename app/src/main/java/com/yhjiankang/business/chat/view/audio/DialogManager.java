package com.yhjiankang.business.chat.view.audio;


import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yhjiankang.business.R;


public class DialogManager {
    private static final String TAG = "DialogManager";


    private Dialog mDialog;
    /**
     * 左边麦克风，固定图
     */
    private ImageView mIcon;
    /**
     * 右边声音，变化
     */
    private ImageView mVoice;
    /**
     * 时间
     */
    private TextView mLabel;

    private Context mContext;

    public DialogManager(Context context) {
        this.mContext = context;
    }

    /**
     * 显示dialog
     */
    public void showRecordingDialog() {
        mDialog = new Dialog(mContext, R.style.Theme_AudioDialog);
        LayoutInflater inflater = (LayoutInflater) mContext.
                getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog, null);
        mDialog.setContentView(view);

        mIcon = (ImageView) mDialog.findViewById(R.id.id_recorder_dialog_icon);
        mVoice = (ImageView) mDialog.findViewById(R.id.id_recorder_dialog_voice);
        mLabel = (TextView) mDialog.findViewById(R.id.id_recorder_dialog_label);

        mDialog.show();

    }

    public void recording() {
        if (mDialog != null && mDialog.isShowing()) {
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.VISIBLE);
            mLabel.setVisibility(View.VISIBLE);

            mIcon.setImageResource(R.mipmap.recorder);
            mLabel.setText("松开手指，取消发送");
            ;
        }
    }

    /**
     * 取消
     */
    public void wantToCancel() {
        if (mDialog != null && mDialog.isShowing()) {
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.GONE);
            mLabel.setVisibility(View.VISIBLE);

            mIcon.setImageResource(R.mipmap.cancel);
            mLabel.setText("松开手指，取消发送");
        }
    }

    public void tooShort() {
        if (mDialog != null && mDialog.isShowing()) {
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.GONE);
            mLabel.setVisibility(View.VISIBLE);

            mIcon.setImageResource(R.mipmap.voice_to_short);
            mLabel.setText("录音时间过短");
        }
    }

    public void dismissDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    /**
     * 通过level去跟新voice上的图片
     *
     * @param level 1-7
     */
    public void updateVoiceLevel(int level) {
        if (mDialog != null && mDialog.isShowing()) {
//            mIcon.setVisibility(View.VISIBLE);
//            mVoice.setVisibility(View.VISIBLE);
//            mLabel.setVisibility(View.VISIBLE);

            //通过level找到资源id
			System.out.println("---level--"+level);
            int resId = mContext.getResources().getIdentifier("v" + level,
                    "mipmap", mContext.getPackageName());
//            System.out.println("---resId+" + resId);
            mVoice.setImageResource(resId);
        }
    }
}
