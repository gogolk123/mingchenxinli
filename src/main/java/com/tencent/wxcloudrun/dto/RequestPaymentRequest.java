package com.tencent.wxcloudrun.dto;

import lombok.Data;

@Data
public class RequestPaymentRequest {
    private String timestamp;
    private String app_id;
    private String nonce_str;
    private String sign_type;
    private String pay_sigin;
    private String pacakge;
}
