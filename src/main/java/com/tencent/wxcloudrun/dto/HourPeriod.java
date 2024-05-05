package com.tencent.wxcloudrun.dto;

import lombok.Data;

@Data
public class HourPeriod {
    private String start_time; // Start time
    private String end_time; // End time
    private List<UnitPeriod> unit_period_list; // Unit period list
}
