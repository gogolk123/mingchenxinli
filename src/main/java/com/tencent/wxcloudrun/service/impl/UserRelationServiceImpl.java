package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.UserRelationMapper;
import com.tencent.wxcloudrun.model.UserRelation;
import com.tencent.wxcloudrun.service.UserRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserRelationServiceImpl implements UserRelationService {

  private final UserRelationMapper userRelationMapper;

  @Autowired
  public UserRelationServiceImpl(UserRelationMapper userRelationMapper) {
    this.userRelationMapper = userRelationMapper;
  }



  @Override
  public void createUserRelation(UserRelation userRelation) {
    userRelationMapper.createUserRelation(userRelation);
  }

  @Override
  public void updateUserRelation(UserRelation userRelation) {
    userRelationMapper.updateUserRelation(userRelation);
  }

  @Override
  public void deleteUserRelation(String userId) {
    userRelationMapper.deleteUserRelation(userId);
  }

  @Override
  public Optional<UserRelation> getUserRelationByOutId(String outId, Integer outIdType) {
    return Optional.ofNullable(userRelationMapper.getUserRelationByOutId(outId, outIdType));
  }
}