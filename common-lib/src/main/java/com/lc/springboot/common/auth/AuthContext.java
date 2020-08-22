package com.lc.springboot.common.auth;

import com.lc.springboot.common.holder.RequestUserHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 用于获取userid和authz的上下文类
 *
 * @author liangchao
 */
public class AuthContext {

  public static final String BEARER_ = "Bearer ";

  /**
   * 获取request 指定header的信息
   *
   * @param headerName header名称
   * @return 返回headerName对象的值
   */
  private static String getRequestHeader(String headerName) {
    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    if (requestAttributes instanceof ServletRequestAttributes) {
      HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
      String value = request.getHeader(headerName);
      return value;
    }
    return null;
  }

  /**
   * 获取当前用户信息
   *
   * @return 用户ID
   */
  public static Long getUserId() {
    return RequestUserHolder.getId();
  }

  /**
   * 获取认证信息
   *
   * @return 令牌的值
   */
  public static String getAuthz() {
    String authz = getRequestHeader(AuthConstant.AUTHORIZATION_HEADER);

    if (StringUtils.isEmpty(authz)) {
      return "";
    }

    if (StringUtils.startsWith(authz, BEARER_)) {
      authz = authz.substring(BEARER_.length());
    }

    return authz;
  }
}
