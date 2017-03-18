package com.yhjiankang.business.bean;

import java.util.List;

/**
 * Created by 马小布 on 2015-07-05.
 */

public class StoreCommentDetailedBean {


    /**
     * id : 38
     * order_id : 1273
     * order_sn : 8000000000162201
     * createtime : 1462762292
     * member_id : 10268
     * member_name : 健康_4691821462758688
     * desccredit : 2
     * servicecredit : 3
     * deliverycredit : 3
     * member_avatar : jiankang-images-upload-fc36975d890aa8874447bf8e0bc496f5.jpg
     * member_exppoints : 0
     * order_goods : [{"goods_id":"76","goods_name":"真心罐头","goods_pay_price":"0.01","goods_num":"1","goods_image":"jiankang-goods-15-zhenxinyeguoguantou1.jpg","select_spec":"248g/瓶"}]
     */

    private String id;
    private String order_id;
    private String order_sn;
    private String createtime;
    private String member_id;
    private String member_name;
    private String desccredit;
    private String servicecredit;
    private String deliverycredit;
    private String member_avatar;
    private String member_exppoints;
    /**
     * goods_id : 76
     * goods_name : 真心罐头
     * goods_pay_price : 0.01
     * goods_num : 1
     * goods_image : jiankang-goods-15-zhenxinyeguoguantou1.jpg
     * select_spec : 248g/瓶
     */

    private List<OrderGoodsBean> order_goods;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getDesccredit() {
        return desccredit;
    }

    public void setDesccredit(String desccredit) {
        this.desccredit = desccredit;
    }

    public String getServicecredit() {
        return servicecredit;
    }

    public void setServicecredit(String servicecredit) {
        this.servicecredit = servicecredit;
    }

    public String getDeliverycredit() {
        return deliverycredit;
    }

    public void setDeliverycredit(String deliverycredit) {
        this.deliverycredit = deliverycredit;
    }

    public String getMember_avatar() {
        return member_avatar;
    }

    public void setMember_avatar(String member_avatar) {
        this.member_avatar = member_avatar;
    }

    public String getMember_exppoints() {
        return member_exppoints;
    }

    public void setMember_exppoints(String member_exppoints) {
        this.member_exppoints = member_exppoints;
    }

    public List<OrderGoodsBean> getOrder_goods() {
        return order_goods;
    }

    public void setOrder_goods(List<OrderGoodsBean> order_goods) {
        this.order_goods = order_goods;
    }

    public static class OrderGoodsBean {
        private String goods_id;
        private String goods_name;
        private String goods_pay_price;
        private String goods_num;
        private String goods_image;
        private String select_spec;

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_pay_price() {
            return goods_pay_price;
        }

        public void setGoods_pay_price(String goods_pay_price) {
            this.goods_pay_price = goods_pay_price;
        }

        public String getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(String goods_num) {
            this.goods_num = goods_num;
        }

        public String getGoods_image() {
            return goods_image;
        }

        public void setGoods_image(String goods_image) {
            this.goods_image = goods_image;
        }

        public String getSelect_spec() {
            return select_spec;
        }

        public void setSelect_spec(String select_spec) {
            this.select_spec = select_spec;
        }
    }
}
