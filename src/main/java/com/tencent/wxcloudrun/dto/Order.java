package com.tencent.wxcloudrun.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class Order {
    private OrderBase order_base; // Order base information
    private String counselor_id; // Counselor ID
    private String visitor; // Visitor information


}


