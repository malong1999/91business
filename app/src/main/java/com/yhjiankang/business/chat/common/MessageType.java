package com.yhjiankang.business.chat.common;

/**
 * Created by 马小布 on 2015/6/20.
 */
public interface MessageType {
    static int LEFT_TEXT = 0x0001;
    static int RIGHT_TEXT = 0x0002;
    static int RIGHT_AUDIO = 0x003;
    int RIGHT_IMAGE = 0X004;
}
