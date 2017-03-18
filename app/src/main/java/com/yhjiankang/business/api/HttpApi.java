package com.yhjiankang.business.api;

/**
 * Created by 马小布 on 2015-06-03.
 */
public class HttpApi {
    /**
     * 与服务器端连接的协议名
     */
    public static final String PROTOCOL = "http://";

    /**
     * 服务器域名
     */
    public static final String HOST_TEST = "192.168.188.175/";
    public static final String HOST_OUT = "api.91jksc.com/";
    public static String HOST = HOST_TEST;

    public static String WAP_HOST = "wap.91jksc.com/";

    public static final String WAP_URL = PROTOCOL + WAP_HOST;

    public static final String WAP_GOODS_URL = WAP_URL + "tmpl/product_detail.html?goods_id=";
    public static final String IM_TEST = "101.200.12.246";

    /**
     * IM服务器地址、端口号
     */
    public static final String IM_HOST = PROTOCOL + "api.91jksc.com" + ":3001";//

    //图片服务器
    public static final String URL_IMAGEPATH = "http://img.91jksc.com/";
    /**
     * 分页显示个数
     */
    public static final String PAGESIZE = "20";
    /**
     * 服务器端口号
     */
    public static final String PORT = "80";

    /**
     * http://192.168.188.175//store/
     */
    public static final String URL_CONTEXTPATH = PROTOCOL + HOST + "/store/";

    /**
     * 登录
     */
    public static final String URL_LOGIN = URL_CONTEXTPATH + "login/login.html";

    /**
     * 收货地址
     */
    public static final String URL_GETADDRESS = URL_CONTEXTPATH + "address/get_address.html";

    /**
     * 商品管理列表
     */
    public static final String URL_GOODSMANAGELIST = URL_CONTEXTPATH + "goods/lists.html";

    //上传图片
    public static final String URL_UPLOADIMG = URL_IMAGEPATH + "upload-images-upload-upload";

    //上传图片
    public static final String URL_UPLOADUSERIMG = URL_CONTEXTPATH + "user/setUseravatar.html";

    //用户信息
    public static final String URL_USERINFO = URL_CONTEXTPATH + "user/info.html";

    /**
     * 首页店铺信息
     */
    public static final String URL_STOREINFO = URL_CONTEXTPATH + "store/info.html";

    //营业额访客下单
    public static final String URL_TURNOVERORDERCOUNT = URL_CONTEXTPATH + "store/revenueContrast.html";

    //营业额收入图
    public static final String URL_INCOMECHART = URL_CONTEXTPATH + "store/revenueSell.html";

    /**
     * 订单列表
     */
    public static final String URL_ORDER_LIST = URL_CONTEXTPATH + "Order/lists.html";
    /**
     * 二维码
     */
    public static final String URL_TWO_DIMENSIONAL = URL_CONTEXTPATH + "store/QRcode.html";

    /**
     * 店铺评价列表
     */
    public static final String URL_STORE_COMMENT_LIST = URL_CONTEXTPATH + "evaluate/store_list.html";

    /**
     * 商品评价列表
     */
    public static final String URL_GOODS_COMMENT_LIST = URL_CONTEXTPATH + "evaluate/lists.html";

    /**
     * 订单详情
     */
    public static final String URL_ORDERDETAILED = URL_CONTEXTPATH + "Order/info.html";

    //验证码
//    public static final String URL_GETCODE = URL_CONTEXTPATH + "login/spendSms.html";
    public static final String URL_GETCODE = PROTOCOL + "192.168.188.4/" + "/store/" + "login/spendSms.html";

    //验证码
    public static final String URL_REGISTE = URL_CONTEXTPATH + "login/register.html";

    //下架
    public static final String URL_OOFGOODS = URL_CONTEXTPATH + "goods/down.html";

    //下架
    public static final String URL_ONGOODS = URL_CONTEXTPATH + "goods/up.html";

    //删除商品
    public static final String URL_DELETEGOODS = URL_CONTEXTPATH + "goods/del.html";

    //商品详情
    public static final String URL_GOODSDETAILED = URL_CONTEXTPATH + "goods/detail.html";

    //指定商品评价列表
    public static final String URL_GOODSCOMMENT = URL_CONTEXTPATH + "evaluate/goods_list.html";

    //商品评价详情
    public static final String URL_GOODSCOMMENTDETAILED = URL_CONTEXTPATH + "evaluate/goods_detail.html";

    //商品评价回复
    public static final String URL_GOODSCOMMENTDETAILED_COMMIT = URL_CONTEXTPATH + "evaluate/explain.html";

    //店铺评价详情
    public static final String URL_STORECOMMENTDETAILED = URL_CONTEXTPATH + "evaluate/store_detail.html";

    //修改店铺资料
    public static final String URL_SETSTOREINFO = URL_CONTEXTPATH + "store/setInfo.html";

    //子账户列表
    public static final String URL_ACCOUNTLIST = URL_CONTEXTPATH + "children/lists.html";

    //启用子账户
    public static final String URL_OPENACCOUNT = URL_CONTEXTPATH + "children/active.html";

    //停用子账户
    public static final String URL_CLOSEACCOUNT = URL_CONTEXTPATH + "children/freeze.html";

    //删除子账户
    public static final String URL_DELETEACCOUNT = URL_CONTEXTPATH + "children/del.html";

    //修改子账户
    public static final String URL_SETACCOUNT = URL_CONTEXTPATH + "children/edit.html";

    //查询子账户
    public static final String URL_ACCOUNTINFO = URL_CONTEXTPATH + "children/info.html";
}
