package com.yhjiankang.business.bean;

import java.util.List;

/**
 * Created by 马小布 on 2015-07-04.
 */

public class GoodsCommentBean {

    /**
     * list : [{"comment_id":"11","comment_message":"4","comment_member_id":"10165","geval_scores":"5","geval_isanonymous":"3","geval_image":["jiankang-goods-15-bxskqbylzly1.jpg","jiankang-goods-15-xuehuafen1.jpg"],"geval_addtime":"1460704844","member_nickname":"Hyj","member_avatar":"jiankang-images-upload-171b770e0f64e983ad05df56f46e2364.jpg"},{"comment_id":"2","comment_message":"龙老板都说好","comment_member_id":"5","geval_scores":"5","geval_isanonymous":"1","geval_image":[],"geval_addtime":"1460338720","member_nickname":"龙老板都说好","member_avatar":"aaa1"}]
     * page_total : 1
     * total : 2
     * totalArr : {"total0":"2","total1":"0","total2":"0","total3":"2","total4":"1"}
     */

    private int page_total;
    private String total;
    /**
     * total0 : 2
     * total1 : 0
     * total2 : 0
     * total3 : 2
     * total4 : 1
     */

    private TotalArrBean totalArr;
    /**
     * comment_id : 11
     * comment_message : 4
     * comment_member_id : 10165
     * geval_scores : 5
     * geval_isanonymous : 3
     * geval_image : ["jiankang-goods-15-bxskqbylzly1.jpg","jiankang-goods-15-xuehuafen1.jpg"]
     * geval_addtime : 1460704844
     * member_nickname : Hyj
     * member_avatar : jiankang-images-upload-171b770e0f64e983ad05df56f46e2364.jpg
     */

    private List<ListBean> list;

    public int getPage_total() {
        return page_total;
    }

    public void setPage_total(int page_total) {
        this.page_total = page_total;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public TotalArrBean getTotalArr() {
        return totalArr;
    }

    public void setTotalArr(TotalArrBean totalArr) {
        this.totalArr = totalArr;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class TotalArrBean {
        private String total0;
        private String total1;
        private String total2;
        private String total3;
        private String total4;

        public String getTotal0() {
            return total0;
        }

        public void setTotal0(String total0) {
            this.total0 = total0;
        }

        public String getTotal1() {
            return total1;
        }

        public void setTotal1(String total1) {
            this.total1 = total1;
        }

        public String getTotal2() {
            return total2;
        }

        public void setTotal2(String total2) {
            this.total2 = total2;
        }

        public String getTotal3() {
            return total3;
        }

        public void setTotal3(String total3) {
            this.total3 = total3;
        }

        public String getTotal4() {
            return total4;
        }

        public void setTotal4(String total4) {
            this.total4 = total4;
        }
    }

    public static class ListBean {
        private String comment_id;
        private String comment_message;
        private String comment_member_id;
        private String geval_scores;
        private String geval_isanonymous;
        private String geval_addtime;
        private String member_nickname;
        private String member_avatar;
        private List<String> geval_image;

        public String getComment_id() {
            return comment_id;
        }

        public void setComment_id(String comment_id) {
            this.comment_id = comment_id;
        }

        public String getComment_message() {
            return comment_message;
        }

        public void setComment_message(String comment_message) {
            this.comment_message = comment_message;
        }

        public String getComment_member_id() {
            return comment_member_id;
        }

        public void setComment_member_id(String comment_member_id) {
            this.comment_member_id = comment_member_id;
        }

        public String getGeval_scores() {
            return geval_scores;
        }

        public void setGeval_scores(String geval_scores) {
            this.geval_scores = geval_scores;
        }

        public String getGeval_isanonymous() {
            return geval_isanonymous;
        }

        public void setGeval_isanonymous(String geval_isanonymous) {
            this.geval_isanonymous = geval_isanonymous;
        }

        public String getGeval_addtime() {
            return geval_addtime;
        }

        public void setGeval_addtime(String geval_addtime) {
            this.geval_addtime = geval_addtime;
        }

        public String getMember_nickname() {
            return member_nickname;
        }

        public void setMember_nickname(String member_nickname) {
            this.member_nickname = member_nickname;
        }

        public String getMember_avatar() {
            return member_avatar;
        }

        public void setMember_avatar(String member_avatar) {
            this.member_avatar = member_avatar;
        }

        public List<String> getGeval_image() {
            return geval_image;
        }

        public void setGeval_image(List<String> geval_image) {
            this.geval_image = geval_image;
        }
    }
}
