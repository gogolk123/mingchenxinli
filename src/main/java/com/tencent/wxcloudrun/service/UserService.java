package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.User;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface UserService {
  Optional<User> getUserByUserId(String userId);
  void createUser(User user);
  void updateUser(User user);
  void deleteUser(String userId);
}
