package com.tencent.wxcloudrun.dto;

import lombok.Data;

@Data
public class WechatPaymentResponse {
    private int errcode;
    private String errmsg;
    private RespData respdata;

    // getters and setters
}

