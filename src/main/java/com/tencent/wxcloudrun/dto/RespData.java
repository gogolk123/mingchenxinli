package com.tencent.wxcloudrun.dto;

import lombok.Data;

@Data
public class RespData {
    private String return_code;
    private String return_msg;
    private String appid;
    private String mch_id;
    private String sub_appid;
    private String sub_mch_id;
    private String nonce_str;
    private String sign;
    private String result_code;
    private String trade_type;
    private String prepay_id;
    private Payment payment;

    // getters and setters
}
