package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.Counselor;
import com.tencent.wxcloudrun.model.Counter;
import com.tencent.wxcloudrun.model.Visitor;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface VisitorService {

  List<Visitor> queryVisitorListByUserId(Integer userId);
  Optional<Visitor> getVisitorByVisitorId(String visitorId);
  void createVisitor(Visitor visitor);
  void updateVisitor(Visitor visitor);
  void deleteVisitor(String visitorId);
}
