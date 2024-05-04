package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Visitor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface VisitorMapper {

    Visitor getVisitorByVisitorId(@Param("visitorId") String visitorId);

    void createVisitor(Visitor visitor);

    void updateVisitor(Visitor visitor);

    void deleteVisitor(@Param("visitorId") String visitorId);

    List<Visitor> queryVisitorListByUserId(@Param("userId") Integer userId);


}