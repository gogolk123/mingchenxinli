package com.tencent.wxcloudrun.dto;

import lombok.Data;

@Data
public class DayPeriod {
    private String date; // Date
    private List<TimePeriod> time_period_list; // Time period list
}
