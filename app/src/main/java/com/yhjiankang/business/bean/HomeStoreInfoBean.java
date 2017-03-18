package com.yhjiankang.business.bean;

import java.util.List;

/**
 * Project : 91健康商城
 * Program Name : com.yhjiankang.business.bean.HomeStoreInfoBean.java
 * Author :马庆龙
 * Creation Date : 2015-06-20 上午9:38
 * Description：首页店铺信息
 * ModificationHistory ：
 */
public class HomeStoreInfoBean {


    /**
     * store_id :
     * store_name :
     * store_label :
     * sc_id_1 : 自然健康
     * sc_id_2 : ["粮油调味","休闲食品","进口食品"]
     * brand : [{"gc_id":1,"gc_name":"安佳"},{"gc_id":2,"gc_name":"金龙鱼"},{"gc_id":3,"gc_name":"谷笑爷"}]
     * store_announcement :
     * store_phone :
     * store_address :
     * store_credit : 0
     * store_workingtime :
     * start_time :
     * end_time :
     */

    private StoreBean store;
    /**
     * todayMoney : 0
     * yesterdayMoney : 0
     * yesterdayOrder : 0
     * todayVisitor : 0
     */

    private CountBean count;

    public StoreBean getStore() {
        return store;
    }

    public void setStore(StoreBean store) {
        this.store = store;
    }

    public CountBean getCount() {
        return count;
    }

    public void setCount(CountBean count) {
        this.count = count;
    }

    public static class StoreBean {
        private String store_id;
        private String store_name;
        private String store_label;
        private String sc_id_1;
        private String store_announcement;
        private String store_phone;
        private String store_address;
        private int store_credit;
        private String store_workingtime;
        private String start_time;
        private String end_time;
        private List<String> sc_id_2;
        /**
         * gc_id : 1
         * gc_name : 安佳
         */

        private List<BrandBean> brand;

        public String getStore_id() {
            return store_id;
        }

        public void setStore_id(String store_id) {
            this.store_id = store_id;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public String getStore_label() {
            return store_label;
        }

        public void setStore_label(String store_label) {
            this.store_label = store_label;
        }

        public String getSc_id_1() {
            return sc_id_1;
        }

        public void setSc_id_1(String sc_id_1) {
            this.sc_id_1 = sc_id_1;
        }

        public String getStore_announcement() {
            return store_announcement;
        }

        public void setStore_announcement(String store_announcement) {
            this.store_announcement = store_announcement;
        }

        public String getStore_phone() {
            return store_phone;
        }

        public void setStore_phone(String store_phone) {
            this.store_phone = store_phone;
        }

        public String getStore_address() {
            return store_address;
        }

        public void setStore_address(String store_address) {
            this.store_address = store_address;
        }

        public int getStore_credit() {
            return store_credit;
        }

        public void setStore_credit(int store_credit) {
            this.store_credit = store_credit;
        }

        public String getStore_workingtime() {
            return store_workingtime;
        }

        public void setStore_workingtime(String store_workingtime) {
            this.store_workingtime = store_workingtime;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public List<String> getSc_id_2() {
            return sc_id_2;
        }

        public void setSc_id_2(List<String> sc_id_2) {
            this.sc_id_2 = sc_id_2;
        }

        public List<BrandBean> getBrand() {
            return brand;
        }

        public void setBrand(List<BrandBean> brand) {
            this.brand = brand;
        }

        public static class BrandBean {
            private int gc_id;
            private String gc_name;

            public int getGc_id() {
                return gc_id;
            }

            public void setGc_id(int gc_id) {
                this.gc_id = gc_id;
            }

            public String getGc_name() {
                return gc_name;
            }

            public void setGc_name(String gc_name) {
                this.gc_name = gc_name;
            }
        }
    }

    public static class CountBean {
        private String todayMoney;
        private String yesterdayMoney;
        private String yesterdayOrder;
        private String todayVisitor;

        public String getTodayMoney() {
            return todayMoney;
        }

        public void setTodayMoney(String todayMoney) {
            this.todayMoney = todayMoney;
        }

        public String getYesterdayMoney() {
            return yesterdayMoney;
        }

        public void setYesterdayMoney(String yesterdayMoney) {
            this.yesterdayMoney = yesterdayMoney;
        }

        public String getYesterdayOrder() {
            return yesterdayOrder;
        }

        public void setYesterdayOrder(String yesterdayOrder) {
            this.yesterdayOrder = yesterdayOrder;
        }

        public String getTodayVisitor() {
            return todayVisitor;
        }

        public void setTodayVisitor(String todayVisitor) {
            this.todayVisitor = todayVisitor;
        }
    }
}
