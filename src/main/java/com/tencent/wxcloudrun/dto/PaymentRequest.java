package com.tencent.wxcloudrun.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PaymentRequest implements Serializable {
    private String body;
    private String openid;
    private String out_trade_no;
    private String spbill_create_ip;
    private String env_id;
    private String sub_mch_id;
    private int total_fee;
    private int callback_type;
    private Container container;

    public PaymentRequest(String openid, String out_trade_no, String spbill_create_ip, int total_fee) {
        this.body = "咨询预约";
        this.openid = openid;
        this.out_trade_no = out_trade_no;
        this.spbill_create_ip = spbill_create_ip;
        this.env_id = "prod-1gfe9rhw25464c5f";
        this.sub_mch_id = "1675883558";
        this.total_fee = total_fee;
        this.callback_type = 2;
        this.container = new Container("mingchenxinli", "/yuntuoguan/wechatPaySuccessCallback");
    }

    // getters and setters
}

@Data
class Container implements Serializable {
    private String service;
    private String path;

    public Container(String service, String path) {
        this.service = service;
        this.path = path;
    }

    // getters and setters
}