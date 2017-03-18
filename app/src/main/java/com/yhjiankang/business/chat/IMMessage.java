package com.yhjiankang.business.chat;


import android.net.Uri;
import android.os.Bundle;

import com.yhjiankang.business.chat.view.audio.Recorder;

/**
 * Created by maxiaobu on 2015/6/18.
 */
public class IMMessage {

    /**
     * 自己的账户
     */
    private String userId;
    /**
     * 对方的账户
     */
    private String sessionId;

    /**
     * 文本内容
     * @return
     */
    private String messageText;

    /**
     * 消息类型
     * @return
     */
    private int mMessageType;

    /**
     * 音频
     */
    private Recorder mRecorder;

    private Uri imageUri;

    public String getUserId() {
        return userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getMessageText(){
        return messageText;
    }

    public int getMessageType(){
        return mMessageType;
    }

    public Recorder getRecorder(){
        return mRecorder;
    }

    public Uri getImageUri(){
        return imageUri;
    }

    public IMMessage(String userId, String sessionId,String messageText,int messageType) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.messageText=messageText;
        this.mMessageType = messageType;
    }

    public IMMessage(String userId, String sessionId,Recorder recorder,int messageType){
        this.userId = userId;
        this.sessionId = sessionId;
        this.mMessageType = messageType;
        mRecorder=recorder;
    }

    public IMMessage(String userId, String sessionId,Uri imageUri, int messageType){
        this.userId = userId;
        this.sessionId = sessionId;
        this.mMessageType = messageType;
        this.imageUri=imageUri;
    }


}
