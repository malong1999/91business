package com.yhjiankang.business.bean;

import java.util.List;

/**
 * Created by 马小布 on 2015/7/2.
 */
public class StoreCommentListBean {

    /**
     * list : [{"id":"38","createtime":"1462762292","member_id":"10268","member_name":"健康_4691821462758688","desccredit":"2","servicecredit":"3","deliverycredit":"3","member_avatar":"jiankang-images-upload-fc36975d890aa8874447bf8e0bc496f5.jpg","member_exppoints":"0"},{"id":"36","createtime":"1462683522","member_id":"10249","member_name":"健康_ED2A0.3183820","desccredit":"4","servicecredit":"3","deliverycredit":"5","member_avatar":"jiankang-images-upload-38f1ee9cfada94508fbb66206454c52c.jpg","member_exppoints":"0"},{"id":"35","createtime":"1462676028","member_id":"10258","member_name":"健康_oOPo0.8758480","desccredit":"5","servicecredit":"3","deliverycredit":"1","member_avatar":"jiankang-images-upload-4b2d53e2d4f8705303edbd4ed60f4c99.jpg","member_exppoints":"0"},{"id":"34","createtime":"1462667539","member_id":"10165","member_name":"ad123456","desccredit":"5","servicecredit":"5","deliverycredit":"5","member_avatar":"jiankang-images-upload-171b770e0f64e983ad05df56f46e2364.jpg","member_exppoints":"0"},{"id":"33","createtime":"1462667526","member_id":"10165","member_name":"ad123456","desccredit":"5","servicecredit":"5","deliverycredit":"5","member_avatar":"jiankang-images-upload-171b770e0f64e983ad05df56f46e2364.jpg","member_exppoints":"0"},{"id":"32","createtime":"1462608035","member_id":"10262","member_name":"健康_6841070.16515100 1462524598","desccredit":"3","servicecredit":"1","deliverycredit":"1","member_avatar":"jiankang-images-upload-bac60ecb4aa45d767bcadb2df7b1956b.jpg","member_exppoints":"0"},{"id":"31","createtime":"1462607978","member_id":"10165","member_name":"ad123456","desccredit":"1","servicecredit":"1","deliverycredit":"1","member_avatar":"jiankang-images-upload-171b770e0f64e983ad05df56f46e2364.jpg","member_exppoints":"0"},{"id":"30","createtime":"1462607676","member_id":"10256","member_name":"健康_1827310.67773900 1462433575","desccredit":"3","servicecredit":"3","deliverycredit":"5","member_avatar":"jiankang-images-upload-74ae1d5504f5e89ad78da79e70301325.jpg","member_exppoints":"0"},{"id":"29","createtime":"1462601259","member_id":"10256","member_name":"健康_1827310.67773900 1462433575","desccredit":"5","servicecredit":"5","deliverycredit":"5","member_avatar":"jiankang-images-upload-74ae1d5504f5e89ad78da79e70301325.jpg","member_exppoints":"0"},{"id":"28","createtime":"1462599088","member_id":"10262","member_name":"健康_6841070.16515100 1462524598","desccredit":"1","servicecredit":"1","deliverycredit":"1","member_avatar":"jiankang-images-upload-bac60ecb4aa45d767bcadb2df7b1956b.jpg","member_exppoints":"0"}]
     * tatal : 32
     * page : 4
     */

    private String tatal;
    private int page;
    /**
     * id : 38
     * createtime : 1462762292
     * member_id : 10268
     * member_name : 健康_4691821462758688
     * desccredit : 2
     * servicecredit : 3
     * deliverycredit : 3
     * member_avatar : jiankang-images-upload-fc36975d890aa8874447bf8e0bc496f5.jpg
     * member_exppoints : 0
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
        private String id;
        private String createtime;
        private String member_id;
        private String member_name;
        private String desccredit;
        private String servicecredit;
        private String deliverycredit;
        private String member_avatar;
        private String member_exppoints;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
    }
}
