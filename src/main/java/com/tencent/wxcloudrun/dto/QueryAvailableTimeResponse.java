package com.tencent.wxcloudrun.dto;

import lombok.Data;

import java.util.Map;

@Data
public class QueryAvailableTimeResponse {
    private Map<String, DayPeriod> calendar; // Calendar
}
