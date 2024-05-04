package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.UserRelation;
import com.tencent.wxcloudrun.model.UserRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface UserRelationMapper {

    UserRelation getUserRelationByOutId(@Param("outId") String  outId, @Param("outType") Integer  outType);

    void createUserRelation(UserRelation userRelation);

    void updateUserRelation(UserRelation userRelation);

    void deleteUserRelation(@Param("visitorId") String relationId);
}

//Optional<UserRelation> getUserRelationByOutId(String outId, Integer outType);
//void createUserRelation(UserRelation user);
//void updateUserRelation(UserRelation user);
//void deleteUserRelation(String userId);