package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.Counseling;
import com.tencent.wxcloudrun.model.Counselor;

import java.util.Optional;

public interface CounselingService {

  Optional<Counseling> getCounselingByCounselorId(String counselorId);

  void createCounseling(Counseling counseling);
  void updateCounseling(Counseling counseling);
  void deleteCounseling(String counselingId);

}
