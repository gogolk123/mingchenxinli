package com.tencent.wxcloudrun.dto;

import lombok.Data;

import java.util.List;

@Data
public class Counselor {
  private String counselor_id; // Counselor ID
  private String name; // Name
  private String edu; // Education
  private String address; // Address
  private List<CounselingWay> way_list; // Counseling methods
  private int duration; // Duration in minutes
  private int fee; // Counseling fee in cents
  private CounselorExtra extra; // Additional information
  // getters and setters
}


