package com.lc.springboot.common.auth.token;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 令牌对象
 *
 * @author liangchao
 */
@Data
@AllArgsConstructor
@ApiModel(value = "令牌对象")
public class AccessToken {

  /** 令牌值 */
  @ApiModelProperty(value = "令牌值")
  private String access_token;
  /** 刷新令牌值 */
  @ApiModelProperty(value = "刷新令牌值")
  private String refresh_token;
  /** 过期时间，单位：秒 */
  @ApiModelProperty(value = "过期时间，单位：秒", example = "86400*20")
  private long expires_in;
}
