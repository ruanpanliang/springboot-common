package com.lc.springboot.common.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/** @author liangchao */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("BaseResponse 返回报文基类")
public class BaseResponse implements Serializable {

  @JsonIgnore private static final BaseResponse SUCCESS = new BaseResponse("", ResultCode.SUCCESS);
  @JsonIgnore private static final BaseResponse FAILURE = new BaseResponse("", ResultCode.FAILURE);

  @ApiModelProperty("返回报文描述")
  private String message = "";

  @ApiModelProperty("返回报文Code")
  @Builder.Default
  private ResultCode code = ResultCode.SUCCESS;

  @ApiModelProperty("处理成功标记  true:成功    false:失败")
  public boolean isSuccess() {
    return code == ResultCode.SUCCESS;
  }

  public static BaseResponse success() {
    return SUCCESS;
  }

  public static BaseResponse failure() {
    return FAILURE;
  }

  public static BaseResponse failure(String msg) {
    return new BaseResponse(msg, ResultCode.FAILURE);
  }
}
