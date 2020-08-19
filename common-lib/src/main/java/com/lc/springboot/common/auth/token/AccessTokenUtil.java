package com.lc.springboot.common.auth.token;

import cn.hutool.core.util.ObjectUtil;
import com.lc.springboot.common.auth.AuthConstant;
import com.lc.springboot.common.auth.AuthProperties;
import com.lc.springboot.common.error.ServiceException;
import com.lc.springboot.common.redis.util.RedisUtil;
import com.lc.springboot.common.utils.SysUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Token工具类
 *
 * @author liangchao
 */
@Slf4j
public class AccessTokenUtil<T> {

  /** 用户信息token前缀 */
  public static final String USER = "user:";

  /** 刷新令牌key前缀 */
  public static final String REFRESH_TOKEN = "refresh_token:";

  /** 用于清除令牌用 */
  public static final String REFRESH_TOKEN_USER_CLEAR = "refresh_token_user_clear:";

  /** 令牌key前缀 */
  public static final String ACCESS_TOKEN = "access_token:";

  /** 用于清除令牌用 */
  public static final String ACCESS_TOKEN_USER_CLEAR = "access_token_user_clear:";

  public static final String BEARER_ = "Bearer ";

  private RedisUtil redisUtil;
  private AuthProperties authProperties;

  public void setRedisUtil(RedisUtil redisUtil) {
    this.redisUtil = redisUtil;
  }

  public void setAuthProperties(AuthProperties authProperties) {
    this.authProperties = authProperties;
  }

  /**
   * 获取当前登录的用户
   *
   * @return 返回当前登录用户信息
   */
  public T currentLoginUserInfo() {
    HttpServletRequest request = SysUtils.getRequest();

    String tokenVal = request.getHeader(AuthConstant.AUTHORIZATION_HEADER);
    log.debug("tokenVal = " + tokenVal);

    checkTokenVal(tokenVal, authProperties.getErrorMsgMissingAuthHeader());

    if (StringUtils.startsWith(tokenVal, BEARER_)) {
      tokenVal = tokenVal.substring(BEARER_.length());
    }

    // 从令牌中获取信息
    T user = (T) redisUtil.get(ACCESS_TOKEN + tokenVal);

    checkTokenVal(user, authProperties.getAccessTokenTimeout());

    return user;
  }

  /**
   * 检测值，如果检测值为空，则抛出异常信息{@link ServiceException}
   *
   * @param tokenVal 检测的值
   * @param errorInfo 错误信息
   */
  private void checkTokenVal(Object tokenVal, String errorInfo) {
    if (ObjectUtil.isEmpty(tokenVal)) {
      throw new ServiceException(errorInfo);
    }
  }

  /**
   * 获取用户令牌的值
   *
   * @param request http request请求对象
   * @return 令牌的值
   */
  public static String getRequestToken(HttpServletRequest request) {
    String tokenVal = request.getHeader(AuthConstant.AUTHORIZATION_HEADER);

    if (StringUtils.isBlank(tokenVal)) {
      return "";
    }

    if (StringUtils.startsWith(tokenVal, BEARER_)) {
      tokenVal = tokenVal.substring(BEARER_.length());
    }

    return tokenVal;
  }

  /**
   * 获取用户令牌信息
   *
   * @return 令牌的值
   */
  public static String getRequestToken() {
    return getRequestToken(SysUtils.getRequest());
  }

  /**
   * 删除用户原来保存在redis中信息
   *
   * @param userAcc 用户登录账号
   * @return 操作成功返回true，反之返回false
   */
  public boolean delUserInfo(String userAcc) {
    if (checkUser(userAcc)) {
      return false;
    }

    String oldAccessToken = (String) redisUtil.get(ACCESS_TOKEN_USER_CLEAR + userAcc);
    String oldRefreshToken = (String) redisUtil.get(REFRESH_TOKEN_USER_CLEAR + userAcc);

    if (StringUtils.isNotBlank(oldAccessToken)) {
      // 将原来保存的令牌删除,将原来保存的用户信息进行删除
      redisUtil.del(ACCESS_TOKEN + oldAccessToken/*, USER + oldAccessToken*/);
    }

    if (StringUtils.isNotBlank(oldRefreshToken)) {
      // 将原来刷新的令牌进行删除
      redisUtil.del(REFRESH_TOKEN + oldRefreshToken);
    }

    // 将原来用户对应的令牌信息进行删除 将原来刷新的令牌信息删除
    redisUtil.del(ACCESS_TOKEN_USER_CLEAR + userAcc, REFRESH_TOKEN_USER_CLEAR + userAcc);
    return true;
  }

  /**
   * 检测账号是否是空
   *
   * @param operAcc 用户账号
   * @return 如果是空则返回true，反之返回false
   */
  private boolean checkUser(String operAcc) {
    return StringUtils.isBlank(operAcc) || redisUtil == null;
  }

  /**
   * 将用户信息保存到redis中
   *
   * @param accessToken 令牌对象
   * @param user 用户信息
   * @return 保存成功返回true，否则返回false
   */
  public boolean saveUserInfo(AccessToken accessToken, T user, String userAccount) {
    long expiresIn = authProperties.getAccessTokenValiditySeconds();
    // 保存令牌本身信息
    redisUtil.set(ACCESS_TOKEN + accessToken.getAccess_token(), user, expiresIn);

    // 保存刷新令牌的信息，默认设置刷新令牌的有效时长是令牌本身的3倍
    redisUtil.set(REFRESH_TOKEN + accessToken.getRefresh_token(), user, 3 * expiresIn);

    // 保存用户对象信息
    // redisUtil.set(USER + accessToken.getAccess_token(), user, expiresIn);

    // 保存用户账号和令牌的关系，默认过期时间为其他临牌时间的4倍（这样做是为了保证用户下次登录的时候能将原来用户相关的信息在缓存中清除）
    redisUtil.set(
        ACCESS_TOKEN_USER_CLEAR + userAccount, accessToken.getAccess_token(), 4 * expiresIn);
    redisUtil.set(
        REFRESH_TOKEN_USER_CLEAR + userAccount, accessToken.getRefresh_token(), 4 * expiresIn);

    return true;
  }
}
