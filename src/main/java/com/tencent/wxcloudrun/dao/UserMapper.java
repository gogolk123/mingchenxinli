package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface UserMapper {

    User getUserByUserId(@Param("userId") String userId);

    void createUser(User visitor);

    void updateUser(User visitor);

    void deleteUser(@Param("userId") String userId);
}

