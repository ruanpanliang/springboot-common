package com.lc.springboot.controller;

import com.lc.springboot.common.api.ResultCode;
import io.swagger.annotations.ApiModel;
import lombok.Builder;

/**
 * 用于解析返回报文信息
 *
 * @author: liangc
 * @date: 2020-08-20 10:28
 * @version 1.0
 */
@ApiModel("测试返回类")
public class TestBaseResponse<T> {

  private String message = "";
  @Builder.Default private ResultCode code = ResultCode.SUCCESS;
  private boolean success;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public ResultCode getCode() {
    return code;
  }

  public void setCode(ResultCode code) {
    this.code = code;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

}
