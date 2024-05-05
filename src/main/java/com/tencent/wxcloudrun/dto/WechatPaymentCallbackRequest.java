package com.tencent.wxcloudrun.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WechatPaymentCallbackRequest {

    @JsonProperty("returnCode")
    private String returnCode;

    @JsonProperty("appid")
    private String appId;

    @JsonProperty("mchId")
    private String mchId;

    @JsonProperty("subAppid")
    private String subAppId;

    @JsonProperty("subMchId")
    private String subMchId;

    @JsonProperty("nonceStr")
    private String nonceStr;

    @JsonProperty("resultCode")
    private String resultCode;

    @JsonProperty("openid")
    private String openId;

    @JsonProperty("isSubscribe")
    private String isSubscribe;

    @JsonProperty("subOpenid")
    private String subOpenId;

    @JsonProperty("subIsSubscribe")
    private String subIsSubscribe;

    @JsonProperty("tradeType")
    private String tradeType;

    @JsonProperty("bankType")
    private String bankType;

    @JsonProperty("totalFee")
    private Integer totalFee;

    @JsonProperty("feeType")
    private String feeType;

    @JsonProperty("cashFee")
    private Integer cashFee;

    @JsonProperty("transactionId")
    private String transactionId;

    @JsonProperty("outTradeNo")
    private String outTradeNo;

    @JsonProperty("timeEnd")
    private String timeEnd;

    // getters and setters
}
