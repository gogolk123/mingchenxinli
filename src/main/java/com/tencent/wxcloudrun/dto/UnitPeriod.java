package com.tencent.wxcloudrun.dto;

import lombok.Data;

@Data
public class UnitPeriod {
    private String key; // Unit time identifier
    private String name; // Unit time name
    private boolean is_available; // Availability
    private String period; // Unit time name
    private String seq; // seq

}
