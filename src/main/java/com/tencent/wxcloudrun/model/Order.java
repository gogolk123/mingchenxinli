package com.tencent.wxcloudrun.model;

import com.tencent.wxcloudrun.utils.DateUtil;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
public class Order implements Serializable {
  private Integer id;
  private String orderId;
  private Integer duration;
  private Integer fee;
  private Integer way;
  private Integer status;
  private long userId;

  private String unitPeriodKey;

  private String visitorId;
  private String counselorId;
  private LocalDateTime bizDate;
  private String extra;

  private LocalDateTime createTime;
  private LocalDateTime updateTime;

  public com.tencent.wxcloudrun.dto.OrderBase ModelToOrderBase() {
    com.tencent.wxcloudrun.dto.OrderBase orderBase = new com.tencent.wxcloudrun.dto.OrderBase();
    orderBase.setOrder_id(this.orderId);
    //order_date 位LocalDateTime转换成的x月x日(星期x) 格式
    orderBase.setOrder_date(this.GetDate());
    orderBase.setPeriod(this.GetPeriod());
    orderBase.setSeq(this.GetSeq());
    orderBase.setPeriod_key(this.unitPeriodKey);
    return orderBase;
  }

  public String GetPeriod() {
    String[] period =  this.unitPeriodKey.split("_");
   return DateUtil.TimeToPeriodName(period[1]);
  }

  public String GetDate() {
    String[] period =  this.unitPeriodKey.split("_");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM月dd日(EEE)");
    return DateUtil.dateToTime(period[0]).format(formatter);
  }

  public String GetSeq() {
    String[] period =  this.unitPeriodKey.split("_");
    return period[3];
  }
}



