package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.CounselorMapper;
import com.tencent.wxcloudrun.model.Counselor;
import com.tencent.wxcloudrun.service.CounselorService;
import com.tencent.wxcloudrun.service.CounselorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;

@Service
public class CounselorServiceImpl implements CounselorService {

  private final CounselorMapper counselingMapper;

  @Autowired
  public CounselorServiceImpl(CounselorMapper counselingMapper) {
    this.counselingMapper = counselingMapper;
  }

  @Override
  public Optional<Counselor> getCounselorByUserId(long userId) {
    return Optional.empty();
  }

  @Override
  public Optional<Counselor> getCounselorByCounselorId(String counselorId) {
    return Optional.ofNullable(counselingMapper.getCounselorByCounselorId(counselorId));
  }

  @Override
  public void createCounselor(Counselor counseling) {
    counselingMapper.createCounselor(counseling);
  }

  @Override
  public void updateCounselor(Counselor counseling) {
    counselingMapper.updateCounselor(counseling);
  }

  @Override
  public void deleteCounselor(String counselingId) {
    counselingMapper.deleteCounselor(counselingId);
  }
}