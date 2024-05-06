package com.tencent.wxcloudrun.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CounselingExtra implements Serializable {
    private List<CounselingPeriodRule> rule;
}
