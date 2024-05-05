package com.tencent.wxcloudrun.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tencent.wxcloudrun.dto.CounselorExtra;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
public class Counselor implements Serializable {

  private Integer id;
  private String counselorId;
  private BigInteger userId;

  private String name;
  private String edu;
  private String address;
  private String extra;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;

  public  com.tencent.wxcloudrun.dto.Counselor ModelToDto() throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();

    com.tencent.wxcloudrun.dto.Counselor counselorDTO = new com.tencent.wxcloudrun.dto.Counselor();
    counselorDTO.setCounselor_id(this.counselorId);
    counselorDTO.setName(this.name);
    counselorDTO.setEdu(this.edu);
    counselorDTO.setAddress(this.address);
    CounselorExtra counselorExtra = mapper.readValue(this.extra, CounselorExtra.class);

    counselorDTO.setExtra(counselorExtra);
    return counselorDTO;
  }
}



