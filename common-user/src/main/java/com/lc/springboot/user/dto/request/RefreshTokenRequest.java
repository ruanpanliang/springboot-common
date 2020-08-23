package com.lc.springboot.user.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 刷新令牌请求对象
 *
 * @author liangchao
 */
@ApiModel("刷新令牌请求对象")
@Data
public class RefreshTokenRequest {

  /** 刷新令牌的值 */
  @NotBlank(message = "令牌值不能为空")
  @ApiModelProperty(value = "刷新令牌的值")
  private String refreshToken;
}
