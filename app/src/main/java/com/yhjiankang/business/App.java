package com.yhjiankang.business;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.yhjiankang.business.common.Constants;
import com.yhjiankang.business.utils.StringUtils;

import java.io.File;


/**
 * Created by 马小布 on 2015-05-24.
 */
public class App extends BaseApplication {

    public ImageLoader mImageLoader;

    public DisplayImageOptions options, noCache, optionsHead;

    private static App instance;

    public static final String phoneModel = android.os.Build.MODEL;
    /**
     * 用户ip地址
     */
    public String ip2long;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ip2long = ip2long();
        initImageLoader(this);

    }

    public static App getInstance() {
        return instance;

    }

    /**
     * 设置ImageLoader初始化参数
     */
    public void initImageLoader(Context context) {

        File cacheDir = new File(Constants.CACHE_DIR_IMAGE);
        options = new DisplayImageOptions.Builder().cacheOnDisk(false).cacheInMemory(true).showImageForEmptyUri(R.mipmap.nopic)
                .showImageOnFail(R.mipmap.nopic).showImageOnLoading(null).bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT).displayer(new SimpleBitmapDisplayer()).build();

        optionsHead = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(false).showImageForEmptyUri(R.mipmap.nohead)
                .showImageOnFail(R.mipmap.nohead).showImageOnLoading(null).bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT).displayer(new SimpleBitmapDisplayer()).build();

        noCache = new DisplayImageOptions.Builder().cacheOnDisk(false).cacheInMemory(false).showImageForEmptyUri(R.mipmap.nopic)
                .showImageOnFail(R.mipmap.nopic).showImageOnLoading(null).bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT).displayer(new SimpleBitmapDisplayer()).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCache(new UnlimitedDiscCache(cacheDir))
                .memoryCache(new WeakMemoryCache())
                .writeDebugLogs()
                .build();

        ImageLoader.getInstance().init(config);
        mImageLoader = ImageLoader.getInstance();
    }

    /**
     * 获取IP（long）
     */
    private String ip2long() {
        try {
            WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
            if (!wifiManager.isWifiEnabled()) {
                wifiManager.setWifiEnabled(true);
            }
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ip = wifiInfo.getIpAddress();
            long iplong = StringUtils.ipStrToLong(String.valueOf(StringUtils.int2ip(ip)));
            return String.valueOf(iplong);
        } catch (Exception e) {

        }
        return null;
    }
}
