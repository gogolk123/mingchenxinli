package com.tencent.wxcloudrun.dto;

import lombok.Data;

@Data
public class CreateOrderResponse {
    private String order_id; // Order ID
    private RequestPaymentRequest requestPaymentRequest; // WeChat payment API input parameters
}
