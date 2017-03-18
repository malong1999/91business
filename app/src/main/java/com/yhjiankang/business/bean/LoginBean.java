package com.yhjiankang.business.bean;

/**
 * Created by 马小布 on 2015/6/16.
 */
public class LoginBean {

    /**
     * username : w408811093
     * userid : 5
     * voucher : f7776b3c40cc046435535ab0cc85ce31
     * type : 1
     * state : 1
     */

    private String username;
    private String userid;
    private String voucher;
    private int type;
    private int state;
    private String user_avatar;

    public String getUser_avatar() {
        return user_avatar;
    }

    public void setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
