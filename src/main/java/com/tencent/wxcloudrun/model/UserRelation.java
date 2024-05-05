package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
public class UserRelation implements Serializable {

  private Integer id;
  private String RelationId;

  private String outId;
  private Integer outIdType;
  private long userId;
  private String extra;

  private LocalDateTime createTime;
  private LocalDateTime updateTime;
}

