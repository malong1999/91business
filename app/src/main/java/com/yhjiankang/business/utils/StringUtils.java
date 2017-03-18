package com.yhjiankang.business.utils;

import java.util.regex.Pattern;

/**
 * Created by maxiaobu on 2015-03-02.
 */
public class StringUtils {

    public static String int2ip(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }

    public static long ipStrToLong(String ipaddress) {
        long[] ip = new long[4];
        int position1 = ipaddress.indexOf(".");
        int position2 = ipaddress.indexOf(".", position1 + 1);
        int position3 = ipaddress.indexOf(".", position2 + 1);
        ip[0] = Long.parseLong(ipaddress.substring(0, position1));
        ip[1] = Long.parseLong(ipaddress.substring(position1 + 1, position2));
        ip[2] = Long.parseLong(ipaddress.substring(position2 + 1, position3));
        ip[3] = Long.parseLong(ipaddress.substring(position3 + 1));
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }

    // 用正则表达式
    public static boolean IsNumericP(String str) {
        Pattern pattern = Pattern.compile("^(1[0-9])\\d{9}$");
        return pattern.matcher(str).matches();
    }

    /**
     * 用户名校验,默认用户名长度至少3个字符，最大长度为15<br>
     * 可修改正则表达式以实现不同需求
     *
     * @param username 用户名
     * @return
     */
    public static boolean isUserName(String username) {
        /***
         * 正则表达式为：^[a-z0-9_-]{3,15}$ 各部分作用如下： [a-z0-9_-] -----------
         * 匹配列表中的字符，a-z,0–9,下划线，连字符 {3,15}-----------------长度至少3个字符，最大长度为15
         * 如果有不同需求的可以参考以上修改正则表达式
         */
        return validation("^[a-z0-9_-]{3,15}$", username);
    }

    /**
     * 密码校验
     * 要求6-16位数字和英文字母组合
     *
     * @param pwd
     * @return
     */
    public static boolean isPassword(String pwd) {
        /**
         * ^ 匹配一行的开头位置(?![0-9]+$) 预测该位置后面不全是数字
         * (?![a-zA-Z]+$) 预测该位置后面不全是字母
         * [0-9A-Za-z] {6,16} 由6-16位数字或这字母组成
         */
        return validation("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$", pwd);
    }

    /**
     * 邮箱校验
     *
     * @param mail 邮箱字符串
     * @return 如果是邮箱则返回true，如果不是则返回false
     */
    public static boolean isEmail(String mail) {
        return validation(
                "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$",
                mail);
    }

    /**
     * 正则校验
     *
     * @param pattern正则表达式
     * @param str          需要校验的字符串
     * @return 验证通过返回true
     */
    public static boolean validation(String pattern, String str) {
        if (str == null)
            return false;
        return Pattern.compile(pattern).matcher(str).matches();
    }

    /**
     * 手机号校验 注：1.支持最新170手机号码 2.支持+86校验
     *
     * @param phoneNum 手机号码
     * @return 验证通过返回true
     */
    public static boolean isMobile(String phoneNum) {
        if (phoneNum == null)
            return false;
        // 如果手机中有+86则会自动替换掉
        return validation("^[1][3,4,5,7,8][0-9]{9}$",
                phoneNum.replace("+86", ""));
    }
}
