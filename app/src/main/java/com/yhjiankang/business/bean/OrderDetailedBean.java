package com.yhjiankang.business.bean;

import java.util.List;

/**
 * Created by lizm on 2015-07-02.
 */

public class OrderDetailedBean {

    /**
     * order_id : 1336
     * order_state : 0
     * order_sn : 8000000000168501
     * add_time : 1462849802
     * goods_amount : 6.90
     * shipping_fee : 0.00
     * shipping_code :
     * shipping_id : 0
     * shipping_time : 0
     * payment_time : 0
     * refund_state : 0
     * reciver_name : 韩志远
     * reciver_phone : 15802427131
     * reciver_address : 辽宁沈阳东陵区11111222
     * order_message :
     * deliver_explain :
     * invoice_info : {"type":"普通发票","name":"个人","content":"明细"}
     * extend_order_goods : [{"goods_id":"76","goods_name":"真心罐头","goods_image":"jiankang-goods-15-zhenxinyeguoguantou1.jpg","goods_num":"1","goods_pay_price":"6.90","select_spec":"248g/瓶","refund_state":"","refund_sn":""}]
     */

    private String order_id;
    private String order_state;
    private String order_sn;
    private String add_time;
    private String goods_amount;
    private String shipping_fee;
    private String shipping_code;
    private String shipping_id;
    private String shipping_time;
    private String payment_time;
    private String refund_state;
    private String reciver_name;
    private String reciver_phone;
    private String reciver_address;
    private String order_message;
    private String deliver_explain;
    /**
     * type : 普通发票
     * name : 个人
     * content : 明细
     */

    private InvoiceInfoBean invoice_info;
    /**
     * goods_id : 76
     * goods_name : 真心罐头
     * goods_image : jiankang-goods-15-zhenxinyeguoguantou1.jpg
     * goods_num : 1
     * goods_pay_price : 6.90
     * select_spec : 248g/瓶
     * refund_state :
     * refund_sn :
     */

    private List<ExtendOrderGoodsBean> extend_order_goods;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_state() {
        return order_state;
    }

    public void setOrder_state(String order_state) {
        this.order_state = order_state;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getGoods_amount() {
        return goods_amount;
    }

    public void setGoods_amount(String goods_amount) {
        this.goods_amount = goods_amount;
    }

    public String getShipping_fee() {
        return shipping_fee;
    }

    public void setShipping_fee(String shipping_fee) {
        this.shipping_fee = shipping_fee;
    }

    public String getShipping_code() {
        return shipping_code;
    }

    public void setShipping_code(String shipping_code) {
        this.shipping_code = shipping_code;
    }

    public String getShipping_id() {
        return shipping_id;
    }

    public void setShipping_id(String shipping_id) {
        this.shipping_id = shipping_id;
    }

    public String getShipping_time() {
        return shipping_time;
    }

    public void setShipping_time(String shipping_time) {
        this.shipping_time = shipping_time;
    }

    public String getPayment_time() {
        return payment_time;
    }

    public void setPayment_time(String payment_time) {
        this.payment_time = payment_time;
    }

    public String getRefund_state() {
        return refund_state;
    }

    public void setRefund_state(String refund_state) {
        this.refund_state = refund_state;
    }

    public String getReciver_name() {
        return reciver_name;
    }

    public void setReciver_name(String reciver_name) {
        this.reciver_name = reciver_name;
    }

    public String getReciver_phone() {
        return reciver_phone;
    }

    public void setReciver_phone(String reciver_phone) {
        this.reciver_phone = reciver_phone;
    }

    public String getReciver_address() {
        return reciver_address;
    }

    public void setReciver_address(String reciver_address) {
        this.reciver_address = reciver_address;
    }

    public String getOrder_message() {
        return order_message;
    }

    public void setOrder_message(String order_message) {
        this.order_message = order_message;
    }

    public String getDeliver_explain() {
        return deliver_explain;
    }

    public void setDeliver_explain(String deliver_explain) {
        this.deliver_explain = deliver_explain;
    }

    public InvoiceInfoBean getInvoice_info() {
        return invoice_info;
    }

    public void setInvoice_info(InvoiceInfoBean invoice_info) {
        this.invoice_info = invoice_info;
    }

    public List<ExtendOrderGoodsBean> getExtend_order_goods() {
        return extend_order_goods;
    }

    public void setExtend_order_goods(List<ExtendOrderGoodsBean> extend_order_goods) {
        this.extend_order_goods = extend_order_goods;
    }

    public static class InvoiceInfoBean {
        private String type;
        private String name;
        private String content;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class ExtendOrderGoodsBean {
        private String goods_id;
        private String goods_name;
        private String goods_image;
        private String goods_num;
        private String goods_pay_price;
        private String select_spec;
        private String refund_state;
        private String refund_sn;

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

        public String getGoods_image() {
            return goods_image;
        }

        public void setGoods_image(String goods_image) {
            this.goods_image = goods_image;
        }

        public String getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(String goods_num) {
            this.goods_num = goods_num;
        }

        public String getGoods_pay_price() {
            return goods_pay_price;
        }

        public void setGoods_pay_price(String goods_pay_price) {
            this.goods_pay_price = goods_pay_price;
        }

        public String getSelect_spec() {
            return select_spec;
        }

        public void setSelect_spec(String select_spec) {
            this.select_spec = select_spec;
        }

        public String getRefund_state() {
            return refund_state;
        }

        public void setRefund_state(String refund_state) {
            this.refund_state = refund_state;
        }

        public String getRefund_sn() {
            return refund_sn;
        }

        public void setRefund_sn(String refund_sn) {
            this.refund_sn = refund_sn;
        }
    }
}
