package com.yhjiankang.business.bean;

import java.util.List;

/**
 * Created by 马小布 on 2015-06-03.
 * <p>
 * 收索商品列表页
 */
public class SearchGoodsBean {

    /**
     * total : 9
     * page_total : 3
     */

    private String total;
    private int page_total;
    private List<ClassesGoodsDetailedBean> list;

    /**
     * goods_id : 100070
     * goods_name : 多力葵花籽油
     * goods_price : 53.50
     * goods_image : 15_05011516900915869.jpg
     * goods_jingle : 科学压榨、充氮保鲜、富含维生素E
     * spec_name : N;
     * goods_spec : N;
     * is_recommend : 0
     */


    public void setTotal(String total) {
        this.total = total;
    }

    public void setPage_total(int page_total) {
        this.page_total = page_total;
    }

    public String getTotal() {
        return total;
    }

    public int getPage_total() {
        return page_total;
    }

    public void setList(List<ClassesGoodsDetailedBean> list) {
        this.list = list;
    }

    public List<ClassesGoodsDetailedBean> getList() {
        return list;
    }


    public static class ClassesGoodsDetailedBean {
        private String goods_id;
        private String goods_name;
        private String goods_price;
        private String goods_image;
        private String goods_jingle;
        private String is_recommend;

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public void setGoods_image(String goods_image) {
            this.goods_image = goods_image;
        }

        public void setGoods_jingle(String goods_jingle) {
            this.goods_jingle = goods_jingle;
        }

        public void setIs_recommend(String is_recommend) {
            this.is_recommend = is_recommend;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public String getGoods_image() {
            return goods_image;
        }

        public String getGoods_jingle() {
            return goods_jingle;
        }


        public String getIs_recommend() {
            return is_recommend;
        }
    }
}
