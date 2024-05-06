package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Counseling;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

@Mapper
public interface CounselingMapper {

    Counseling getCounselingByCounselorId(@Param("counselorId") String counselorId, @Param("time") LocalDateTime time);

    void createCounseling(Counseling counseling);

    void updateCounseling(Counseling counseling);

    void deleteCounseling(@Param("counselingId") String counselingId);
}