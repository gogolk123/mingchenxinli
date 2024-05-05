package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Visitor implements Serializable {
  private Integer id;
  private String visitorId;
  private long userId;

  private String name;
  private String phone;
  private String born;
  private String gender;
  private Boolean isFirstVisit;
  private String extra;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;


  public  com.tencent.wxcloudrun.dto.Visitor ModelToDto() {
    com.tencent.wxcloudrun.dto.Visitor visitor = new com.tencent.wxcloudrun.dto.Visitor();
    visitor.setVisitor_id(this.visitorId);
    visitor.setName(this.name);
    visitor.setGender(this.gender);
    visitor.setBorn(this.born);
    visitor.setPhone(this.phone);
    visitor.set_first_visit(this.isFirstVisit);
    return visitor;
  };

}




