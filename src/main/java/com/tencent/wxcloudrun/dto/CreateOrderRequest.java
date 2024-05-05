package com.tencent.wxcloudrun.dto;

import lombok.Data;

@Data
public class CreateOrderRequest {
    private String counselor_id; // Counselor ID
    private String unit_period_key; // Unit time identifier
    private String visitor_id; // Visitor ID
    private int way; // Appointment method
}
