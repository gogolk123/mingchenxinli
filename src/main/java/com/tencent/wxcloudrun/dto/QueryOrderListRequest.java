package com.tencent.wxcloudrun.dto;

import lombok.Data;

@Data
public class QueryOrderListRequest {
    private String cursor; // Cursor for pagination
    private int count; // Number of records to fetch
}
