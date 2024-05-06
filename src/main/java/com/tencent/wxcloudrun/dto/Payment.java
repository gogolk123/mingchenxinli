package com.tencent.wxcloudrun.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Payment {
    private String appId;
    private String timeStamp;
    private String nonceStr;
    @JsonProperty("package")
    private String packageName;
    private String signType;
    private String paySign;

    // getters and setters
}
