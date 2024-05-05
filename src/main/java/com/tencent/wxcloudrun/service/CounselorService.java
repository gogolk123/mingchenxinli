package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.Counselor;

import java.math.BigInteger;
import java.util.Optional;

public interface CounselorService {

  Optional<Counselor> getCounselorByUserId(long userId);
  Optional<Counselor> getCounselorByCounselorId(String counselorId);

  void createCounselor(Counselor counselor);
  void updateCounselor(Counselor counselor);
  void deleteCounselor(String counselorId);

}
