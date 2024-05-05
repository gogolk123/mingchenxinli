package com.tencent.wxcloudrun.dto;

import lombok.Data;

import java.util.List;

@Data
public class QueryVisitorInfoListResponse {
    private List<Visitor> info_list;
    // getters and setters
}
