package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Counselor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface CounselorMapper {

    List<Counselor> getCounselorByUserId(@Param("userId") BigInteger userId);

    Counselor getCounselorByCounselorId(@Param("counselorId") String counselorId);

    void createCounselor(Counselor counselor);

    void updateCounselor(Counselor counselor);

    void deleteCounselor(@Param("counselorId") String counselorId);
}