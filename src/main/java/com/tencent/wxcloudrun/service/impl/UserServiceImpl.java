package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.UserMapper;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

  private final UserMapper userMapper;

  @Autowired
  public UserServiceImpl(UserMapper userMapper) {
    this.userMapper = userMapper;
  }

  @Override
  public Optional<User> getUserByUserId(String userId) {
    return Optional.ofNullable(userMapper.getUserByUserId(userId));
  }

  @Override
  public void createUser(User user) {
    userMapper.createUser(user);
  }

  @Override
  public void updateUser(User user) {
    userMapper.updateUser(user);
  }

  @Override
  public void deleteUser(String userId) {
    userMapper.deleteUser(userId);
  }


}