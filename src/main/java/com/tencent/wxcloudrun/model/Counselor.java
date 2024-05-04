package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
public class Counselor implements Serializable {

  private Integer id;
  private String counselorId;
  private BigInteger userId;

  private String name;
  private String edu;
  private String address;
  private String extra;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;
}

