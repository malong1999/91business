package com.yhjiankang.business;

import android.app.Application;
import android.content.SharedPreferences;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.yhjiankang.business.common.Constants;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class BaseApplication extends Application {
    /**
     * 系统初始化配置文件操作器
     */
    private SharedPreferences sysInitSharedPreferences;

    /**
     * 记录用户登录后的密钥KEY
     */
    private String loginKey;

    /**
     * 记录用户是否保存密码，用于直接登陆
     * true保存
     */
    private boolean ifRemeberPassword;

    /**
     * 记录用户的登陆密码
     */
    private String loginPassword;

    /**
     * 记录用户登录用的账户名
     */
    private String loginID;

    /**
     * 记录用户登录后的UserName
     */
    private String userName;

    /**
     * 账户类型
     * 0为子账号， 1为主账号
     */
    private int accountType;

    /**
     * 店铺状况
     * 0为未开店， 1为已开店， 2为待审核
     */
    private int shopState;

    private String storeLablePath;

    private String storeAddress;
    //店铺公告
    private String storeComment;

    private String storePhone;
    //客服在线时间
    private String storeOpenTime;

    public String getStoreLablePath() {
        sysInitSharedPreferences.getString("storeLablePath", "");
        return storeLablePath;
    }

    public void setStoreLablePath(String storeLablePath) {
        sysInitSharedPreferences.edit().putString("storeLablePath", storeLablePath).apply();
        this.storeLablePath = storeLablePath;
    }

    @Override
    public void onCreate() {

        super.onCreate();
        Fresco.initialize(this);
        sysInitSharedPreferences = getSharedPreferences(Constants.SYSTEM_INIT_FILE_NAME, MODE_PRIVATE);
        loginKey = sysInitSharedPreferences.getString("loginKey", "-1");
        userName = sysInitSharedPreferences.getString("userName", "-1");
        ifRemeberPassword = getIfRemeberPassword();
        loginID = getLoginID();
        loginPassword = getLoginPassword();

    }

    public void setStoreAddress(String storeAddress) {
        sysInitSharedPreferences.edit().putString("storeAddress", storeAddress).apply();
    }

    public String getStoreAddress() {
        return sysInitSharedPreferences.getString("storeAddress", "");
    }

    public void setStorePhone(String storePhone) {
        sysInitSharedPreferences.edit().putString("storePhone", storePhone).apply();
    }

    public String getStorePhone() {
        return sysInitSharedPreferences.getString("storePhone", "");
    }

    public void setStoreComment(String storeComment) {
        sysInitSharedPreferences.edit().putString("storeComment", storeComment).apply();
    }

    public String getStoreComment() {
        return sysInitSharedPreferences.getString("storeComment", "");
    }

    public void setStoreOpenTime(String storeOpenTime) {
        sysInitSharedPreferences.edit().putString("storeOpenTime", storeOpenTime).apply();
    }

    public String getStoreOpenTime() {
        return sysInitSharedPreferences.getString("storeOpenTime", "");
    }

    public void setIfRemeberPassword(boolean ifRemeberPassword) {
        sysInitSharedPreferences.edit().putBoolean("ifRemeberPassword", ifRemeberPassword).apply();
        this.ifRemeberPassword = ifRemeberPassword;
    }

    public void setLoginID(String loginID) {
        sysInitSharedPreferences.edit().putString("loginID", loginID).apply();
        this.loginID = loginID;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
        sysInitSharedPreferences.edit().putString("loginPassword", loginPassword).apply();
    }

    public void setLoginKey(String loginKey) {
        this.loginKey = loginKey;
        sysInitSharedPreferences.edit().putString("loginKey", loginKey).apply();
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
        sysInitSharedPreferences.edit().putInt("accountType", accountType).apply();
    }

    public void setShopState(int shopState) {
        this.shopState = shopState;
        sysInitSharedPreferences.edit().putInt("shopState", shopState).apply();
    }

    /**
     * @return true保存了密码直接登陆
     */
    public boolean getIfRemeberPassword() {
        return sysInitSharedPreferences.getBoolean("ifRemeberPassword", false);
    }

    /**
     * @return登陆密码
     */
    public String getLoginPassword() {
        return sysInitSharedPreferences.getString("loginPassword", "-1");
    }

    /**
     * get登陆账户
     *
     * @return
     */
    public String getLoginID() {
        return sysInitSharedPreferences.getString("loginID", "-1");
    }

    /**
     * get登陆凭证
     *
     * @return
     */
    public String getLoginKey() {
        return sysInitSharedPreferences.getString("loginKey", "-1");
    }

    /**
     * 账户类型
     * 0为子账号， 1为主账号
     */
    public int getAccountType() {
        return sysInitSharedPreferences.getInt("accountType", -1);
    }

    /**
     * 店铺状况
     * 0为未开店， 1为已开店， 2为待审核
     */
    public int getShopState() {
        return sysInitSharedPreferences.getInt("shopState", -1);
    }

    /**
     * 设置登录账户列表
     *
     * @param idAndPassword
     */
    public void setAccountList(String idAndPassword) {
        Set<String> accountList = sysInitSharedPreferences.getStringSet("accountList", new TreeSet<String>());
        accountList.add(idAndPassword);
        sysInitSharedPreferences.edit().putStringSet("accountList", accountList).apply();
    }

    /**
     * 获取登录账户列表
     *
     * @return
     */
    public Set<String> getAccountList() {
        return sysInitSharedPreferences.getStringSet("accountList", new TreeSet<String>());
    }

    public void setLoginTime(Long loginTime) {
        sysInitSharedPreferences.edit().putLong("loginTime", loginTime).apply();
    }

    public long getLoginTime() {
        return sysInitSharedPreferences.getLong("loginTime", -1);
    }

}
