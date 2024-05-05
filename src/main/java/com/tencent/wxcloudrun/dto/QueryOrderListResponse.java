package com.tencent.wxcloudrun.dto;

import lombok.Data;

import java.util.List;

@Data
public class QueryOrderListResponse {
    private List<Order> order_list; // Order list
    private String next_cursor; // Next cursor for pagination
    private BaseResponse base_resp; // Base response
}
