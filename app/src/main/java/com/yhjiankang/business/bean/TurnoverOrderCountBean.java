package com.yhjiankang.business.bean;

/**
 * Project : 91健康商城
 * Program Name : com.yhjiankang.business.bean.TurnoverOrderCountBean.java
 * Author :马庆龙
 * Creation Date : 2015-06-20 上午8:39
 * Description：访客下单
 * ModificationHistory ：
 */
public class TurnoverOrderCountBean {


    /**
     * todayVisitor : 0
     * yesterVisitor : 0
     * todayOrder : 0
     * yesterOrder : 0
     */

    private String todayVisitor;
    private String yesterVisitor;
    private String todayOrder;
    private String yesterOrder;

    public String getTodayVisitor() {
        return todayVisitor;
    }

    public void setTodayVisitor(String todayVisitor) {
        this.todayVisitor = todayVisitor;
    }

    public String getYesterVisitor() {
        return yesterVisitor;
    }

    public void setYesterVisitor(String yesterVisitor) {
        this.yesterVisitor = yesterVisitor;
    }

    public String getTodayOrder() {
        return todayOrder;
    }

    public void setTodayOrder(String todayOrder) {
        this.todayOrder = todayOrder;
    }

    public String getYesterOrder() {
        return yesterOrder;
    }

    public void setYesterOrder(String yesterOrder) {
        this.yesterOrder = yesterOrder;
    }
}
