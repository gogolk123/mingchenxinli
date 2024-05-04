package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Counseling implements Serializable {
  private Integer id;
  private String counselingId;
  private String counselorId;
  private Integer duration;
  private Integer fee;
  private Integer way;
  private LocalDateTime effectStartDate;
  private LocalDateTime effectEndDate;
  private String extra;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;
}
