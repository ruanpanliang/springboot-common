package com.lc.springboot.common.auth;

import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限拦截器
 *
 * @author liangchao
 */
public abstract class AuthorizeInterceptor extends HandlerInterceptorAdapter {

  protected AuthProperties authProperties;

  public AuthorizeInterceptor(AuthProperties authProperties) {
    this.authProperties = authProperties;
  }

  @Override
  public boolean preHandle(
      HttpServletRequest request, HttpServletResponse response, Object handler) {
    if (!(handler instanceof HandlerMethod)) {
      return true;
    }

    HandlerMethod handlerMethod = (HandlerMethod) handler;
    Authorize authorize = handlerMethod.getMethod().getAnnotation(Authorize.class);
    if (authorize == null) {
      // 表明不需要做权限校验
      return true;
    }

    // 检测请求头信息
    String authzHeader = request.getHeader(AuthConstant.AUTHORIZATION_HEADER);

    if (StringUtils.isEmpty(authzHeader)) {
      throw new PermissionDeniedException(authProperties.getErrorMsgMissingAuthHeader());
    }

    return checkUser(authzHeader);
  }

  /**
   * 检验用户是否是合法的用户
   *
   * @param authzHeader 请求头token信息
   * @return boolean 如果验证成功返回true，否则返回false
   */
  public abstract boolean checkUser(String authzHeader);
}
