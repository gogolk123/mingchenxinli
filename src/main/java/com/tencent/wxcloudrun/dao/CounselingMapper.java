package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Counseling;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CounselingMapper {

    Counseling getCounselingByCounselorId(@Param("counselorId") String counselorId);

    void createCounseling(Counseling counseling);

    void updateCounseling(Counseling counseling);

    void deleteCounseling(@Param("counselingId") String counselingId);
}