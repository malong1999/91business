package com.yhjiankang.business.chat.view.audio;

/**
 * Created by 马小布 on 2015/6/27.
 */
public class Recorder {
    float time;
    String filePath;
    public Recorder(float time, String filePath) {
        this.time = time;
        this.filePath = filePath;
    }
    public float getTime() {
        return time;
    }
    public void setTime(float time) {
        this.time = time;
    }
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
