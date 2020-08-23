package com.lc.springboot.common.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

/** @author liangchao */
@ApiModel("公共配置类")
@ConfigurationProperties(prefix = "common.auth.props")
public class AuthProperties {

  /** AUTH ERROR Messages */
  @ApiModelProperty(value = "当前用户没权限", example = "You do not have access to this service")
  private String errorMsgDoNotHaveAccess = "You do not have access to this service";

  /** Missing Authorization http header */
  @ApiModelProperty(value = "缺少Authorization信息", example = "Missing Authorization http header")
  private String errorMsgMissingAuthHeader = "Missing Authorization http header";

  /** Invalid Authorization Header */
  @ApiModelProperty(value = "非法的Authorization信息", example = "Invalid Authorization Header")
  private String errorAuthorizationHeader = "Invalid Authorization Header";

  /** 令牌过期时间，单位：秒，默认24小时 */
  @ApiModelProperty(value = "令牌过期时间，单位：秒", example = "86400")
  private long accessTokenValiditySeconds = 86400;

  /** Session timed out, please log in again */
  @ApiModelProperty(value = "会话超时，请重新登录")
  private String accessTokenTimeout = "会话超时，请重新登录";

  public String getErrorMsgDoNotHaveAccess() {
    return errorMsgDoNotHaveAccess;
  }

  public void setErrorMsgDoNotHaveAccess(String errorMsgDoNotHaveAccess) {
    this.errorMsgDoNotHaveAccess = errorMsgDoNotHaveAccess;
  }

  public String getErrorMsgMissingAuthHeader() {
    return errorMsgMissingAuthHeader;
  }

  public void setErrorMsgMissingAuthHeader(String errorMsgMissingAuthHeader) {
    this.errorMsgMissingAuthHeader = errorMsgMissingAuthHeader;
  }

  public String getErrorAuthorizationHeader() {
    return errorAuthorizationHeader;
  }

  public void setErrorAuthorizationHeader(String errorAuthorizationHeader) {
    this.errorAuthorizationHeader = errorAuthorizationHeader;
  }

  public long getAccessTokenValiditySeconds() {
    return accessTokenValiditySeconds;
  }

  public void setAccessTokenValiditySeconds(long accessTokenValiditySeconds) {
    this.accessTokenValiditySeconds = accessTokenValiditySeconds;
  }

  public String getAccessTokenTimeout() {
    return accessTokenTimeout;
  }

  public void setAccessTokenTimeout(String accessTokenTimeout) {
    this.accessTokenTimeout = accessTokenTimeout;
  }
}
