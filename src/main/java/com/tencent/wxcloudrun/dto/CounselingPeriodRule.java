package com.tencent.wxcloudrun.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CounselingPeriodRule implements Serializable {
    private String startTime;
    private String endTime;
    private int seqSize;
}
