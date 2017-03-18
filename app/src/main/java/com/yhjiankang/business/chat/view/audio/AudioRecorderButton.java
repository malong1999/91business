package com.yhjiankang.business.chat.view.audio;


import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.yhjiankang.business.R;


/**
 * 录音按钮控件
 */
public class AudioRecorderButton extends Button implements AudioManager.AudioStateListener {
    private static final String TAG="AudioRecorderButton";

    /**
     * y轴超过此数取消
     */
    private static final int DISTANCE_Y_CANCEL = 50;
    /**
     * 正常状态
     */
    private static final int STATE_NORMAL = 1;//正常状态
    /**
     * 录音状态
     */
    private static final int STATE_RECORDING = 2;//录音状态
    /**
     * 取消状态
     */
    private static final int STATE_WANT_TO_CANCEL = 3;//取消状态

    /**
     * 当前状态
     */
    private int mCurState = STATE_NORMAL;

    /**
     * true：已經開始錄音
     */
    private boolean isRecording = false;
    /**
     * dialog管理类
     */
    private DialogManager mDialogManager;
    /**
     * 音频管理类
     */
    private AudioManager mAudioManager;

    /**
     * 计时，录音持续时间
     */
    private float mTime;
    /**
     * true:触发longclick
     */
    private boolean mReady;

    public AudioRecorderButton(Context context) {
        this(context, null);//默认调用两个参数的构造方法
    }

    public AudioRecorderButton(final Context context, AttributeSet attrs) {
        super(context, attrs);

        mDialogManager = new DialogManager(context);

        String dir = Environment.getExternalStorageDirectory() + "/imooc_recorder_audios";

        mAudioManager = AudioManager.getInstance(dir);
        mAudioManager.setOnAudioStateListener(this);

        setOnLongClickListener(new OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                mReady = true;
                mAudioManager.prepareAudio();
                return false;
            }
        });
    }

    /**
     * 录音完成后回调
     *
     * @author lenovo
     */
    public interface AudioFinishRecorderListener {
        void onFinish(float seconds, String filepath);
    }

    private AudioFinishRecorderListener mListener;

    public void setAudioFinishRecorderListener(AudioFinishRecorderListener listener) {
        this.mListener = listener;
    }

    /**
     * 获取音量大小
     */
    private Runnable mGetVoiceLevelRunnable = new Runnable() {

        @Override
        public void run() {
            while (isRecording) {
                try {
                    Thread.sleep(100);
                    mTime += 0.1f;
                    Log.i(TAG, "run: "+mTime);
                    mHandler.sendEmptyMessage(MSG_VOICE_CHANGE);
                } catch (InterruptedException e) {
                    Log.i(TAG, "run: "+e);
                    e.printStackTrace();
                }
            }
        }
    };

    /**
     * 音频准备好了
     */
    private static final int MSG_AUDIO_PREPARED = 0X110;
    private static final int MSG_VOICE_CHANGE = 0X111;
    private static final int MSG_DIALOG_DISMISS = 0X112;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_AUDIO_PREPARED:
                    mDialogManager.showRecordingDialog();
                    isRecording = true;

                    new Thread(mGetVoiceLevelRunnable).start();

                    break;
                case MSG_VOICE_CHANGE:
                    mDialogManager.updateVoiceLevel(mAudioManager.getVoiceLevel(7));
                    break;
                case MSG_DIALOG_DISMISS:
                    mDialogManager.dismissDialog();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 音频准备好后的回调
     */
    @Override
    public void wellPrepared() {
        mHandler.sendEmptyMessage(MSG_AUDIO_PREPARED);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                changeState(STATE_RECORDING);
                break;
            case MotionEvent.ACTION_MOVE:
                if (isRecording) {
                    //根据x,y坐标判断是否想要取消
                    if (wantToCancel(x, y)) {
                        changeState(STATE_WANT_TO_CANCEL);
                    } else {
                        changeState(STATE_RECORDING);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!mReady) {
                    reset();
                    return super.onTouchEvent(event);
                }
                if (!isRecording || mTime < 0.6f) {
                    System.out.println("录制时间过短");
                    mDialogManager.tooShort();
                    mAudioManager.cancel();
                    mHandler.sendEmptyMessageAtTime(MSG_DIALOG_DISMISS, 1300);
                } else if (mCurState == STATE_RECORDING)//正常结束录制
                {
                    System.out.println("正常录制结束");
                    mDialogManager.dismissDialog();

                    mAudioManager.release();
                    if (mListener != null) {
                        mListener.onFinish(mTime, mAudioManager.getCurrentFilePath());
                    }
                } else if (mCurState == STATE_WANT_TO_CANCEL) {
                    System.out.println("取消了");
                    mDialogManager.dismissDialog();
                    mAudioManager.cancel();
                }
                reset();
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 重置标志位、状态
     */
    private void reset() {
        isRecording = false;
        mReady = false;
        mTime = 0;
        changeState(STATE_NORMAL);
    }

    /**
     * 根据x,y坐标判断是否想要取消
     *
     * @param x
     * @param y
     * @return true取消
     */
    private boolean wantToCancel(int x, int y) {
        if (x < 0 || x > getWidth()) {//横坐标超过按钮宽
            return true;
        }
        if (y < -DISTANCE_Y_CANCEL || y > getHeight() + DISTANCE_Y_CANCEL) {
            return true;
        }
        return false;
    }

    /**
     * 改变状态
     * @param stateRecording
     */
    private void changeState(int stateRecording) {
        if (mCurState != stateRecording) {//当前状态不等于参数状态
            mCurState = stateRecording;
            switch (stateRecording) {
                case STATE_NORMAL:
                    // TODO: 2015/6/20 背景处理
//                    setBackgroundResource(R.drawable.btn_recorder_normal);
                    setText(R.string.str_recorder_normal);
                    break;
                case STATE_RECORDING:
//                    setBackgroundResource(R.drawable.btn_recording);
                    setText(R.string.str_recorder_recording);
                    if (isRecording) {
                        mDialogManager.recording();
                    }
                    break;
                case STATE_WANT_TO_CANCEL:
//                    setBackgroundResource(R.drawable.btn_recording);
                    setText(R.string.str_recorder_want_cancel);
                    mDialogManager.wantToCancel();
                    break;
            }
        }
    }

}
