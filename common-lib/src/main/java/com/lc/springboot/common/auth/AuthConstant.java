package com.lc.springboot.common.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @description: 校验类常量类
 * @author: liangc
 * @date: 2020-08-16 12:32
 * @version 1.0
 */
@ApiModel("校验常量类")
public class AuthConstant {

  /** AUTHORIZATION_HEADER 是用于访问内部授权服务的http请求头的键 */
  @ApiModelProperty(value = "授权header的key", example = "Authorization")
  public static final String AUTHORIZATION_HEADER = "Authorization";

  /** 获取cookie名称 */
  @ApiModelProperty(value = "Cookie的名称", example = "cookie-name")
  public static final String COOKIE_NAME = "cookie-name";

  /** 请求头设置为内部用户ID，一般用于集成feign client模块的是否使用 */
  @ApiModelProperty(value = "当前用户ID", example = "current-user-id")
  public static final String CURRENT_USER_ID = "current-user-id";
}
