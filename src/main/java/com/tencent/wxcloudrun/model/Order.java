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

  public  com.tencent.wxcloudrun.dto.Order ModelToDto() {
    com.tencent.wxcloudrun.dto.Order visitor = new com.tencent.wxcloudrun.dto.Order();
    com.tencent.wxcloudrun.dto.OrderBase orderBase = new com.tencent.wxcloudrun.dto.OrderBase();
    orderBase.setOrder_id(this.orderId);
    //order_date 位LocalDateTime转换成的x月x日(星期x) 格式

    orderBase.setOrder_date(this.bizDate.toString());






    visitor.setOrder_base(this.orderId);

    return visitor;
}

