package com.tencent.wxcloudrun.config;

import lombok.Data;

import java.util.HashMap;

@Data
public final class WxApiResponse extends Throwable {

  private Number errcode;
  private String errmsg;

  private WxApiResponse(int code, String errorMsg ) {
    this.errcode = code;
    this.errmsg = errorMsg;
  }
  
  public static WxApiResponse ok() {
    return new WxApiResponse(0, "");
  }


  public static WxApiResponse error(String errorMsg) {
    return new WxApiResponse(-1, errorMsg);
  }
}
