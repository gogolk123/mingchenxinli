package com.tencent.wxcloudrun.dto;

import lombok.Data;

@Data
public class UpdateVisitorInfoRequest {
    private Visitor info; // At this point, visitor_id is not null
}
