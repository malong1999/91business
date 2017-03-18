package com.yhjiankang.business.bean;

import java.util.List;

/**
 * Created by 马小布 on 2015-06-29.
 */

public class AccountManageItemBean {

    /**
     * list : [{"id":"1","username":"marry1","position":"产品经理","name":"马瑞阳","state":"1"},{"id":"2","username":"marry2","position":"产品经理","name":"马瑞阳","state":"1"},{"id":"3","username":"marry3","position":"产品经理","name":"马瑞阳","state":"1"},{"id":"4","username":"marry4","position":"产品经理","name":"马瑞阳","state":"2"},{"id":"5","username":"marry5","position":"产品经理","name":"马瑞阳","state":"1"},{"id":"6","username":"marry6","position":"产品经理","name":"马瑞阳","state":"1"},{"id":"9","username":"marry9","position":"产品经理","name":"马瑞阳","state":"1"},{"id":"13","username":"marry13","position":"产品经理","name":"马瑞阳","state":"1"},{"id":"16","username":"marry16","position":"产品经理","name":"马瑞阳","state":"1"}]
     * total : 9
     */

    private String total;
    /**
     * id : 1
     * username : marry1
     * position : 产品经理
     * name : 马瑞阳
     * state : 1
     */

    private List<ListBean> list;



    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }


    public static class ListBean {
        private String id;
        private String username;
        private String position;
        private String name;
        private String state;
        private boolean isEdit=false;
        private boolean isSelect;
        private boolean isOpen;
        public boolean isOpen() {
            return isOpen;
        }

        public void setOpen(boolean open) {
            isOpen = open;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public boolean isEdit() {
            return isEdit;
        }

        public void setEdit(boolean edit) {
            isEdit = edit;
        }
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
