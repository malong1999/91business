package com.yhjiankang.business.chat.view.audio;

import android.media.MediaRecorder;
import android.util.Log;

import java.io.File;
import java.util.UUID;

public class AudioManager {
    public static final String TAG = "AudioManager";

    /**
     *
     */
    private MediaRecorder mMediaRecorder;
    /**
     *
     */
    private String mDir;
    /**
     * 正在录音的保存路径
     */
    private String mCurrentFilePath;

    private static AudioManager mInstance;

    /**
     * true准备好了
     */
    private boolean isPrepared = false;

    /**
     * 私有化构造方法
     *
     * @param mDir
     */
    private AudioManager(String mDir) {
        this.mDir = mDir;
    }

    /**
     * 回调准备完毕
     */
    public interface AudioStateListener {
        void wellPrepared();
    }

    /**
     * 音频准备完毕的回调
     */
    public AudioStateListener mListener;

    public void setOnAudioStateListener(AudioStateListener mListener) {
        this.mListener = mListener;
    }

    /**
     * @param mDir
     * @return AudioManager实例
     */
    public static AudioManager getInstance(String mDir) {
        if (mInstance == null) {
            synchronized (AudioManager.class) {
                mInstance = new AudioManager(mDir);
            }
        }
        return mInstance;
    }

    /**
     * 准备
     */
    public void prepareAudio() {
        try {
            isPrepared = false;
            File dir = new File(mDir);
            if (!dir.exists())
                dir.mkdirs();

            String fileName = generateName();
            File file = new File(dir, fileName);

            mCurrentFilePath = file.getAbsolutePath();
            mMediaRecorder = new MediaRecorder();
            //设置输出文件
            mMediaRecorder.setOutputFile(file.getAbsolutePath());
            //设置音频源为麦克风
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            //设置音频格式
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
            //设置音频编码
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

//			mMediaRecorder.setAudioSamplingRate(8000);

            mMediaRecorder.prepare();
            mMediaRecorder.start();
            //准备结束
            isPrepared = true;
            if (mListener != null)
                mListener.wellPrepared();

        } catch (Exception e) {
            Log.e(TAG, "prepareAudio: " + e);
            e.printStackTrace();
        }
    }

    /**
     * 随机生成文件的名称  后缀名。anr
     *
     * @return
     */
    private String generateName() {
        return UUID.randomUUID().toString() + ".amr";
    }

    /**
     * @param maxLevel
     * @return 音量等级
     */
    public int getVoiceLevel(int maxLevel) {
        if (isPrepared) {
            try {
                //mMediaRecorder.getMaxAmplitude() 1-32767
                //注意此处mMediaRecorder.getMaxAmplitude 只能取一次，如果前面取了一次，后边再取就为0了
                int i =  mMediaRecorder.getMaxAmplitude();
                Log.i(TAG, "getVoiceLevel: " + i);
                Log.i(TAG, "getVoiceLevel: " + i*maxLevel);
                float i1 = maxLevel *i *2/32768;
                Log.i(TAG, "getVoiceLevel: "+i1);
                return (int) (i1+ 1);
            } catch (Exception e) {
            }
        }
        return 1;
    }

    public void release() {
        if (mMediaRecorder != null) {
            mMediaRecorder.stop();
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
    }

    /**
     * 取消
     */
    public void cancel() {
        release();
        if (mCurrentFilePath != null) {
            File file = new File(mCurrentFilePath);
            if (file.exists()) {
                file.delete();
                mCurrentFilePath = null;
            }
        }
    }

    public String getCurrentFilePath() {
        return mCurrentFilePath;
    }
}
