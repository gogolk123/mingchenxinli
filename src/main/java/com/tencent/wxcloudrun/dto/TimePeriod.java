package com.tencent.wxcloudrun.dto;

import lombok.Data;

import java.util.List;

@Data
public class TimePeriod {
    private String start_time; // Start time
    private String end_time; // End time
    private List<UnitPeriod> unit_period_list; // Unit period list


}
