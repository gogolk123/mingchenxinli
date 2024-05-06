package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.CounselingMapper;
import com.tencent.wxcloudrun.model.Counseling;
import com.tencent.wxcloudrun.service.CounselingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CounselingServiceImpl implements CounselingService {

  private final CounselingMapper counselingMapper;

  @Autowired
  public CounselingServiceImpl(CounselingMapper counselingMapper) {
    this.counselingMapper = counselingMapper;
  }

  @Override
  public Optional<Counseling> getCounselingByCounselorId(String counselorId, LocalDateTime time) {
    return Optional.ofNullable(counselingMapper.getCounselingByCounselorId(counselorId, time));
  }

  @Override
  public void createCounseling(Counseling counseling) {
    counselingMapper.createCounseling(counseling);
  }

  @Override
  public void updateCounseling(Counseling counseling) {
    counselingMapper.updateCounseling(counseling);
  }

  @Override
  public void deleteCounseling(String counselingId) {
    counselingMapper.deleteCounseling(counselingId);
  }
}