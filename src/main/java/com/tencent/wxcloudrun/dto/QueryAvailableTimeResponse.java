package com.tencent.wxcloudrun.dto;

import lombok.Data;

@Data
public class QueryAvailableTimeResponse {
    private Map<String, DayPeriod> calendar; // Calendar
    private BaseResponse base_resp; // Base response
}
