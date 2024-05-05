package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.VisitorMapper;
import com.tencent.wxcloudrun.model.Visitor;
import com.tencent.wxcloudrun.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VisitorServiceImpl implements VisitorService {

  private final VisitorMapper visitorMapper;

  @Autowired
  public VisitorServiceImpl(VisitorMapper visitorMapper) {
    this.visitorMapper = visitorMapper;
  }

  @Override
  public List<Visitor> queryVisitorListByUserId(long userId) {
    return visitorMapper.queryVisitorListByUserId(userId);
  }

  @Override
  public Optional<Visitor> getVisitorByVisitorId(String visitorId) {
    return Optional.ofNullable(visitorMapper.getVisitorByVisitorId(visitorId));
  }

  @Override
  public void createVisitor(Visitor visitor) {
    visitorMapper.createVisitor(visitor);
  }

  @Override
  public void updateVisitor(Visitor visitor) {
    visitorMapper.updateVisitor(visitor);
  }

  @Override
  public void deleteVisitor(String visitorId) {
    visitorMapper.deleteVisitor(visitorId);
  }
}