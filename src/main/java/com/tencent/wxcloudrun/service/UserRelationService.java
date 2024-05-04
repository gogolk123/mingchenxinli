package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.UserRelation;

import java.util.Optional;

public interface UserRelationService {
  Optional<UserRelation> getUserRelationByOutId(String outId, Integer outType);
  void createUserRelation(UserRelation userRelation);
  void updateUserRelation(UserRelation userRelation);
  void deleteUserRelation(String relationId);
}
