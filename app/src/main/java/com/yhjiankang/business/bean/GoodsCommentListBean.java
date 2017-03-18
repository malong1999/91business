package com.yhjiankang.business.bean;

import java.util.List;

/**
 * Created by 马小布 on 2015/7/4.
 */
public class GoodsCommentListBean  {


    /**
     * list : [{"goods_id":"77","goods_name":"智仁牛奶加钙豆奶粉","image":"jiankang-goods-15-zhirenniunaijiagaidounaifen1.jpg","price":"26.00","spec":"默认","num":"1","percentage":1},{"goods_id":"78","goods_name":"智仁牛奶加钙核桃粉","image":"jiankang-goods-15-zhirenniunaijiahetaofen1.jpg","price":"26.00","spec":"默认","num":"11","percentage":0.90909090909091},{"goods_id":"76","goods_name":"真心罐头","image":"jiankang-goods-15-zhenxinyeguoguantou1.jpg","price":"0.01","spec":"默认","num":"49","percentage":0.63265306122449},{"goods_id":"73","goods_name":"易和立式多功能治疗仪","image":"jiankang-goods-15-yihelishiduogongnengzhiliaoyi1.jpg","price":"19980.00","spec":"默认","num":"2","percentage":1},{"goods_id":"72","goods_name":"易和健寿多功能治疗仪","image":"jiankang-goods-15-yihejianshouduogongnengzhiliaoyi1.jpg","price":"16800.00","spec":"默认","num":"1","percentage":1}]
     * tatal : 5
     * page : 1
     */

    private String tatal;
    private int page;
    /**
     * goods_id : 77
     * goods_name : 智仁牛奶加钙豆奶粉
     * image : jiankang-goods-15-zhirenniunaijiagaidounaifen1.jpg
     * price : 26.00
     * spec : 默认
     * num : 1
     * percentage : 1
     */

    private List<ListBean> list;

    public String getTatal() {
        return tatal;
    }

    public void setTatal(String tatal) {
        this.tatal = tatal;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private String goods_id;
        private String goods_name;
        private String image;
        private String price;
        private String spec;
        private String num;
        private float percentage;

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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public float getPercentage() {
            return percentage;
        }

        public void setPercentage(float percentage) {
            this.percentage = percentage;
        }
    }
}
