package com.yhjiankang.business.common;

import android.os.Environment;

/**
 * Created by admin on 2015/6/6.
 */
public class Constants {

    public static final String SYSTEM_INIT_FILE_NAME = "sysini";
    /**
     * 图片缓存目录
     */
    public static final String CACHE_DIR_IMAGE;

    /**
     * 待上传图片缓存目录
     */
    public static final String CACHE_DIR_UPLOADING_IMG;

    /**
     * 本地缓存目录
     */
    public static final String CACHE_DIR;

    public static final int ADDGOODSIMAGE_COUNT = 5;

    //请求参数凭证
    /**
     * 请求参数凭证
     */
    public static final String KEY_VOUCHER = "voucher";

    public static final String KEY_TEST = "fea7b081709c791e5961ab154bbf7486";
    /**
     * 表情缓存目录
     */
    public static final String CACHE_DIR_SMILEY;

    /**
     * 数据库缓存目录
     */
    public static final String CACHE_DIR_DATABASE;

    public static final int ITEMTYEP_GRIDLAYOUT = 1;

    public static final int ITEMTYEP_LINERLAYOUT = 2;


    static {
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            CACHE_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/91shop/";//有sdcard
        } else {
            CACHE_DIR = Environment.getRootDirectory().getAbsolutePath() + "/91shop/";//没有
        }

        CACHE_DIR_SMILEY = CACHE_DIR + "/smiley";
        CACHE_DIR_IMAGE = CACHE_DIR + "/pic";
        CACHE_DIR_UPLOADING_IMG = CACHE_DIR + "/uploading_img";
        CACHE_DIR_DATABASE = CACHE_DIR + "/databases";
    }


    //全部订单
    public static final String ORDER_TYPE_ALL = "50";
    //待付款
    public static final String ORDER_TYPE_DFK = "10";
    //已发货
    public static final String ORDER_TYPE_YFH = "30";
    //退款中
    public static final String ORDER_TYPE_TKZ = "40";
    //待发货
    public static final String ORDER_TYPE_DFH = "20";
    //已完成
    public static final String ORDER_TYPE_YWC = "60";
    //已关闭
    public static final String ORDER_TYPE_YGB = "0";

    /**
     * 头像保存目录
     */
    public static final String CACHE_DIR_HEADER = CACHE_DIR_IMAGE + "header.png";

}
