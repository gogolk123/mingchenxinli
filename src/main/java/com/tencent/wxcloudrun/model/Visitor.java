package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
public class Visitor implements Serializable {
  private Integer id;
  private String visitorId;
  private BigInteger userId;

  private String name;
  private String phone;
  private String born;
  private String gender;
  private Boolean isFirstVisit;
  private String extra;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;
}


