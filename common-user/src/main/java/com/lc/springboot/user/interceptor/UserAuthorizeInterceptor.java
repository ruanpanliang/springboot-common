package com.lc.springboot.user.interceptor;

import com.lc.springboot.common.auth.AuthProperties;
import com.lc.springboot.common.auth.AuthorizeInterceptor;
import com.lc.springboot.common.auth.PermissionDeniedException;
import com.lc.springboot.common.auth.token.AccessTokenUtil;
import com.lc.springboot.common.holder.RequestUserHolder;
import com.lc.springboot.common.redis.util.RedisUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限拦截器
 *
 * @author liangchao
 */
@Slf4j
@Getter
@Setter
public class UserAuthorizeInterceptor extends AuthorizeInterceptor {

  private RedisUtil redisUtil;
  private AccessTokenUtil accessTokenUtil;

  public UserAuthorizeInterceptor(AuthProperties authProperties) {
    super(authProperties);
  }

  /**
   * 从redis库（或者其他途径）中获取相关的用户信息进行校验
   *
   * @param authz 请求头token信息
   * @return 检验通过返回true，否则返回false
   */
  @Override
  public boolean checkUser(String authz) {
    // 将redis保存的用户信息提取出来,该值是在用户登录的时候保存到redis中的
    Long userId = accessTokenUtil.getUserId(authz);
    if (userId == null || userId == 0L) {
      throw new PermissionDeniedException(authProperties.getErrorAuthorizationHeader());
    }

    // 设置上下文
    RequestUserHolder.set(userId);
    return true;
  }

  @Override
  public void afterCompletion(
      HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
      throws Exception {
    super.afterCompletion(request, response, handler, ex);
    // 删除上下文信息
    RequestUserHolder.remove();
  }
}
