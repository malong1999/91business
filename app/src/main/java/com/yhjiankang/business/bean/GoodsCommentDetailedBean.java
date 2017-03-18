package com.yhjiankang.business.bean;

import java.util.List;

public class GoodsCommentDetailedBean {

    /**
     * comment_id : 1
     * order_id : 8000000000137001
     * goods_id : 72
     * goods_name : 易和健寿多功能治疗仪
     * goods_price : 16800.00
     * goods_spec : 默认
     * goods_image : jiankang-goods-15-yihejianshouduogongnengzhiliaoyi1.jpg
     * scores : 5
     * content : 龙老板都说好
     * isanonymous : 1
     * createtime : 1460338656
     * member_id : 5
     * member_name : w408811093
     * images : []
     * explain : 无语了
     */

    private String comment_id;
    private String order_id;
    private String goods_id;
    private String goods_name;
    private String goods_price;
    private String goods_spec;
    private String goods_image;
    private String scores;
    private String content;
    private String isanonymous;
    private String createtime;
    private String member_id;
    private String member_name;
    private List<String> images;
    private String explain;

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
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

    public String getGoods_spec() {
        return goods_spec;
    }

    public void setGoods_spec(String goods_spec) {
        this.goods_spec = goods_spec;
    }

    public String getGoods_image() {
        return goods_image;
    }

    public void setGoods_image(String goods_image) {
        this.goods_image = goods_image;
    }

    public String getScores() {
        return scores;
    }

    public void setScores(String scores) {
        this.scores = scores;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIsanonymous() {
        return isanonymous;
    }

    public void setIsanonymous(String isanonymous) {
        this.isanonymous = isanonymous;
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


    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}
