package com.tencent.wxcloudrun.dto;

import lombok.Data;

import java.util.List;

@Data
public class CounselorExtra {
    private String motto; // Motto
    private String genre; // Counseling genre
    private List<String> scope; // Counseling scope
    private List<String> experience; // Training experience
    // getters and setters
}
