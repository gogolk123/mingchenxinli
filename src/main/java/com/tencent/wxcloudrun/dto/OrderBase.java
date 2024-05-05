package com.tencent.wxcloudrun.dto;

import lombok.Data;

@Data
public class OrderBase {
    private String order_id; // Order ID
    private String order_date; // Order date
    private String period; // Period of the day
    private String seq; // Sequence
}
