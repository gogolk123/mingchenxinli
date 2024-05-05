package com.tencent.wxcloudrun.dto;

import lombok.Data;

@Data
public class QueryAvailableTimeRequest {
    private String counselor_id; // Counselor ID
    private String start_date; // Start date
    private String end_date; // End date
}
