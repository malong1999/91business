package com.yhjiankang.business.bean;

import java.util.List;

/**
 * Created by 马小布 on 2015-06-17.
 */

public class GoodsManageItemBean {

    /**
     * list : [{"goods_id":"5","goods_name":"能是冰糖炖梨","goods_price":"5.00","goods_storage":"9562","goods_image":"jiankang-goods-15-15_05109421022430436.jpg","brand_id":"0","goods_state":"1","goods_verify":"1"},{"goods_id":"11","goods_name":"真心黄桃罐头","goods_price":"5.80","goods_storage":"9909","goods_image":"jiankang-goods-15-15_05109459793442213.jpg","brand_id":"0","goods_state":"1","goods_verify":"1"},{"goods_id":"12","goods_name":"谷笑爷即食大燕麦片","goods_price":"78.00","goods_storage":"10000","goods_image":"jiankang-goods-15-15_05110031037837658.jpg","brand_id":"0","goods_state":"1","goods_verify":"1"},{"goods_id":"13","goods_name":"谷笑爷即食黑麦片","goods_price":"88.00","goods_storage":"9970","goods_image":"jiankang-goods-15-15_05110045981641103.jpg","brand_id":"0","goods_state":"1","goods_verify":"1"},{"goods_id":"14","goods_name":"谷笑爷黑五宝粉","goods_price":"160.00","goods_storage":"10000","goods_image":"jiankang-goods-15-15_05110077418520672.jpg","brand_id":"0","goods_state":"1","goods_verify":"1"}]
     * total : 59
     */

    private String total;
    /**
     * goods_id : 5
     * goods_name : 能是冰糖炖梨
     * goods_price : 5.00
     * goods_storage : 9562
     * goods_image : jiankang-goods-15-15_05109421022430436.jpg
     * brand_id : 0
     * goods_state : 1
     * goods_verify : 1
     */

    private List<GoodsBean> list;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<GoodsBean> getList() {
        return list;
    }

    public void setList(List<GoodsBean> list) {
        this.list = list;
    }

    public static class GoodsBean {
        private String goods_id;
        private String goods_name;
        private String goods_price;
        private String goods_storage;
        private String goods_image;
        private String brand_id;
        private String goods_state;
        private String goods_verify;
        private boolean isChecked;

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

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

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getGoods_storage() {
            return goods_storage;
        }

        public void setGoods_storage(String goods_storage) {
            this.goods_storage = goods_storage;
        }

        public String getGoods_image() {
            return goods_image;
        }

        public void setGoods_image(String goods_image) {
            this.goods_image = goods_image;
        }

        public String getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(String brand_id) {
            this.brand_id = brand_id;
        }

        public String getGoods_state() {
            return goods_state;
        }

        public void setGoods_state(String goods_state) {
            this.goods_state = goods_state;
        }

        public String getGoods_verify() {
            return goods_verify;
        }

        public void setGoods_verify(String goods_verify) {
            this.goods_verify = goods_verify;
        }
    }
}
