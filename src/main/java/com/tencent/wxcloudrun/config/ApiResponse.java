package com.tencent.wxcloudrun.config;

import lombok.Data;

import java.util.HashMap;

@Data
public final class ApiResponse extends Throwable {

  private Integer code;
  private Boolean is_succ;

  private String error_msg;
  private Object data;

  private ApiResponse(int code, String errorMsg, Object data, boolean isSucc) {
    this.code = code;
    this.error_msg = errorMsg;

    this.data = data;
    this.is_succ = isSucc;

  }
  
  public static ApiResponse ok() {
    return new ApiResponse(0, "", new HashMap<>(), true);
  }

  public static ApiResponse ok(Object data) {
    return new ApiResponse(0, "", data, true);
  }

  public static ApiResponse error(String errorMsg) {
    return new ApiResponse(0, errorMsg, new HashMap<>(), false);
  }
}
