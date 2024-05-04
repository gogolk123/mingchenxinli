package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
public class Order implements Serializable {

  private Integer id;
  private String orderId;
  private String duration;
  private Integer fee;
  private Integer way;
  private Integer status;
  private BigInteger userId;

  private String unitPeriodKey;

  private String visitorId;
  private String counselorId;
  private LocalDateTime bizDate;
  private String extra;

  private LocalDateTime createTime;
  private LocalDateTime updateTime;
}

