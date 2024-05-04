package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
public class User implements Serializable {

  private Integer id;
  private BigInteger userId;
  private String extra;

  private LocalDateTime createTime;
  private LocalDateTime updateTime;
}

