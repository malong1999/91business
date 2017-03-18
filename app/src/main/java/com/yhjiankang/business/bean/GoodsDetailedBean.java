package com.yhjiankang.business.bean;

import java.util.List;

/**
 * Created by 马小布 on 2015-07-04.
 */

public class GoodsDetailedBean {


    /**
     * goods_id : 203
     * goods_commonid : 100285
     * goods_name : 松花粉11
     * goods_image : ["a.png","b.png","c.png","d.png","e.png"]
     * goods_spec : 6kg/袋
     * goods_price : 189.00
     * mobile_body : [{"type":"text","value":"产品介绍"},{"type":"image","value":"jiankang-goods-15-15_05112701011706943_1280.jpg"},{"type":"text","value":"特别之处"},{"type":"image","value":"jiankang-goods-15-15_05112701011706943_1280.jpg"},{"type":"image","value":"jiankang-goods-15-15_05112701011706943_1280.jpg"},{"type":"text","value":"注意事项"},{"type":"text","value":"产品描述"}]
     * goods_salenum : 0
     */

    private String goods_id;
    private String goods_commonid;
    private String goods_name;
    private String goods_spec;
    private String goods_price;
    private int goods_salenum;
    private List<String> goods_image;
    /**
     * type : text
     * value : 产品介绍
     */

    private List<MobileBodyBean> mobile_body;

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_commonid() {
        return goods_commonid;
    }

    public void setGoods_commonid(String goods_commonid) {
        this.goods_commonid = goods_commonid;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_spec() {
        return goods_spec;
    }

    public void setGoods_spec(String goods_spec) {
        this.goods_spec = goods_spec;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public int getGoods_salenum() {
        return goods_salenum;
    }

    public void setGoods_salenum(int goods_salenum) {
        this.goods_salenum = goods_salenum;
    }

    public List<String> getGoods_image() {
        return goods_image;
    }

    public void setGoods_image(List<String> goods_image) {
        this.goods_image = goods_image;
    }

    public List<MobileBodyBean> getMobile_body() {
        return mobile_body;
    }

    public void setMobile_body(List<MobileBodyBean> mobile_body) {
        this.mobile_body = mobile_body;
    }

    public static class MobileBodyBean {
        private String type;
        private String value;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
