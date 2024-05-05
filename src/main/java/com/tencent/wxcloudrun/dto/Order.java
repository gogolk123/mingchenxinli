package com.tencent.wxcloudrun.dto;

import lombok.Data;

@Data
public class Order {
    private OrderBase order_base; // Order base information
    private String counselor_id; // Counselor ID
    private String Visitor; // Visitor information
}
