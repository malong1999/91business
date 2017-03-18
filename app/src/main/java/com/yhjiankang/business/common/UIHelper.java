package com.yhjiankang.business.common;


import android.content.Context;
import android.content.Intent;

import com.yhjiankang.business.ui.activity.AccountManageActivity;
import com.yhjiankang.business.ui.activity.BankInformationActivity;
import com.yhjiankang.business.ui.activity.BindEmailActivity;
import com.yhjiankang.business.ui.activity.CommentDetailedActivity;
import com.yhjiankang.business.ui.activity.CommentListActivity;
import com.yhjiankang.business.ui.activity.ChatActivity;
import com.yhjiankang.business.ui.activity.CreateAccountActivity;
import com.yhjiankang.business.ui.activity.FeedBackActivity;
import com.yhjiankang.business.ui.activity.FindPasswordByEmailActivity;
import com.yhjiankang.business.ui.activity.FindPasswordByPhoneActivity;
import com.yhjiankang.business.ui.activity.FindPasswordByWaiterActivity;
import com.yhjiankang.business.ui.activity.GoodsCommentListActivity;
import com.yhjiankang.business.ui.activity.GoodsDetailedActivity;
import com.yhjiankang.business.ui.activity.GoodsManageActivity;
import com.yhjiankang.business.ui.activity.HomeActivity;
import com.yhjiankang.business.ui.activity.LoginActivity;
import com.yhjiankang.business.ui.activity.OrderDetailedActivity;
import com.yhjiankang.business.ui.activity.OrderManageActivity;
import com.yhjiankang.business.ui.activity.PayAccountAcitivty;
import com.yhjiankang.business.ui.activity.PhoneAuthenticationActivity;
import com.yhjiankang.business.ui.activity.SearchActivity;
import com.yhjiankang.business.ui.activity.SetPasswordActivity;
import com.yhjiankang.business.ui.activity.SettledHomeActivity;
import com.yhjiankang.business.ui.activity.ShopInformationActivity;
import com.yhjiankang.business.ui.activity.ShopManagementActivity;
import com.yhjiankang.business.ui.activity.StoreCommentDetailedActivity;
import com.yhjiankang.business.ui.activity.StoreCommentListActivity;
import com.yhjiankang.business.ui.activity.StoreTwodimensionalCodeActivity;
import com.yhjiankang.business.ui.activity.TurnoverActivity;
import com.yhjiankang.business.ui.activity.UploadGoodsActivity;
import com.yhjiankang.business.ui.activity.WebViewNoTitleActivity;

/**
 * Created by 马小布 on 2015-05-29.
 * UI工具类
 */
public class UIHelper {
    /**
     * 跳转主页面
     *
     * @param context
     */
    public static void showHomeActivity(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    public static void showLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void showFindPasswordByPhoneActivity(Context context) {
        Intent intent = new Intent(context, FindPasswordByPhoneActivity.class);
        context.startActivity(intent);
    }

    public static void showFindPasswordByEmailActivity(Context context) {
        Intent intent = new Intent(context, FindPasswordByEmailActivity.class);
        context.startActivity(intent);
    }

    public static void showSetPasswordActivity(Context context) {
        Intent intent = new Intent(context, SetPasswordActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转营业额页面
     *
     * @param context
     */
    public static void showTurnoverActivity(Context context) {
        Intent intent = new Intent(context, TurnoverActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转客服找回
     *
     * @param context
     */
    public static void showFindPasswordByWaiterActivity(Context context) {
        Intent intent = new Intent(context, FindPasswordByWaiterActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转店铺管理页面
     *
     * @param context
     */
    public static void showShopManagmentActivity(Context context) {
        Intent intent = new Intent(context, ShopManagementActivity.class);
        context.startActivity(intent);
    }


    /**
     * 跳转店铺详情页
     *
     * @param context
     */
    public static void showShopInformationActivity(Context context) {
        Intent intent = new Intent(context, ShopInformationActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转商品上传
     *
     * @param context
     */
    public static void showUploadGoodsActivity(Context context) {
        Intent intent = new Intent(context, UploadGoodsActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转商品管理
     *
     * @param context
     */
    public static void showUploadGoodsManageActivity(Context context, String json) {
        Intent intent = new Intent(context, GoodsManageActivity.class);
        intent.putExtra("info", json);
        context.startActivity(intent);
    }

    /**
     * 跳转订单管理
     *
     * @param context
     */
    public static void showOrderManageActivity(Context context) {
        Intent intent = new Intent(context, OrderManageActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转收索页面
     *
     * @param context
     */
    public static void showSearchActivity(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转店铺二维码
     *
     * @param context
     */
    public static void showStoreTwodimensionalCodeActivity(Context context) {
        Intent intent = new Intent(context, StoreTwodimensionalCodeActivity.class);
        context.startActivity(intent);
    }

    /**
     * webview
     *
     * @param context
     */
    public static void showWebViewNoTitleActivity(Context context, String url) {
        Intent intent = new Intent(context, WebViewNoTitleActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    /**
     * 跳转商品详情
     *
     * @param context
     */
    public static void showGoodsDeatailedActivity(Context context, String goodsId) {
        Intent intent = new Intent(context, GoodsDetailedActivity.class);
        intent.putExtra("goodsId", goodsId);
        context.startActivity(intent);
    }

    /**
     * 跳转银行卡信息
     *
     * @param context
     */
    public static void showBankInformationActivity(Context context) {
        Intent intent = new Intent(context, BankInformationActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转手机认证
     *
     * @param context
     */
    public static void showPhoneAuthenticationActivity(Context context) {
        Intent intent = new Intent(context, PhoneAuthenticationActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转邮箱认证
     *
     * @param context
     */
    public static void showBindEmailActivity(Context context) {
        Intent intent = new Intent(context, BindEmailActivity.class);
        context.startActivity(intent);
    }


    /**
     * 跳转店铺评价列表
     *
     * @param context
     */
    public static void showStroeCommentListActivity(Context context) {
        Intent intent = new Intent(context, StoreCommentListActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转意见反馈
     *
     * @param context
     */
    public static void showFeedBackActivity(Context context) {
        Intent intent = new Intent(context, FeedBackActivity.class);
        context.startActivity(intent);
    }

    /**
     * 店铺评价详情
     *
     * @param context
     * @param id
     */
    public static void showStoreCommentDetailedActivity(Context context, String id) {
        Intent intent = new Intent(context, StoreCommentDetailedActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    /**
     * 商品评价列表
     *
     * @param context
     */
    public static void showGoodsCommentListActivity(Context context) {
        Intent intent = new Intent(context, GoodsCommentListActivity.class);
        context.startActivity(intent);
    }

    /**
     * 评价列表
     *
     * @param context
     */
    public static void showCommentListActivity(Context context, String id) {
        Intent intent = new Intent(context, CommentListActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    /**
     * 评价详情
     *
     * @param context
     * @param id
     */
    public static void showCommentDetailedActivity(Context context, String id) {
        Intent intent = new Intent(context, CommentDetailedActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    /**
     * 跳转聊天
     *
     * @param context
     */
    public static void showChatActivity(Context context) {
        Intent intent = new Intent(context, ChatActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转子账户管理
     *
     * @param context
     */
    public static void showAccountManageActivity(Context context) {
        Intent intent = new Intent(context, AccountManageActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转新建员工账户
     *
     * @param context
     */
    public static void showCreateAccountActivity(Context context) {
        Intent intent = new Intent(context, CreateAccountActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转拓展员工账户
     *
     * @param context
     */
    public static void showPayAccountActivity(Context context) {
        Intent intent = new Intent(context, PayAccountAcitivty.class);
        context.startActivity(intent);
    }

    /**
     * 跳转订单详情
     *
     * @param context
     * @param orderID
     * @param state
     */
    public static void showOrderDetailedActivity(Context context, String orderID, String state) {
        Intent intent = new Intent(context, OrderDetailedActivity.class);
        intent.putExtra("orderID", orderID);
        intent.putExtra("state", state);
        context.startActivity(intent);
    }


    /**
     * 跳转入驻首页
     *
     * @param context
     */
    public static void showSettledActivity(Context context) {
        Intent intent = new Intent(context, SettledHomeActivity.class);
        context.startActivity(intent);
    }
}
