package com.lc.springboot.common.auth.token;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import com.lc.springboot.common.auth.AuthContext;
import com.lc.springboot.common.auth.AuthProperties;
import com.lc.springboot.common.error.ServiceException;
import com.lc.springboot.common.redis.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

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
  public T currentUserInfo() {
    String authz = AuthContext.getAuthz();
    checkTokenVal(authz, authProperties.getErrorMsgMissingAuthHeader());
    // 获取当前用户登录详情信息
    T user = (T) redisUtil.get(USER + authz);
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
   * 删除用户原来保存在redis中信息
   *
   * @param userId 用户主键编号
   * @return 操作成功返回true，反之返回false
   */
  public boolean removeUserCacheInfo(Long userId) {
    if (checkParams(userId)) {
      return false;
    }

    String oldAccessToken = (String) redisUtil.get(ACCESS_TOKEN_USER_CLEAR + userId);
    String oldRefreshToken = (String) redisUtil.get(REFRESH_TOKEN_USER_CLEAR + userId);

    if (StringUtils.isNotBlank(oldAccessToken)) {
      // 将原来保存的令牌删除,将原来保存的用户信息进行删除
      redisUtil.del(ACCESS_TOKEN + oldAccessToken, USER + oldAccessToken);
    }

    if (StringUtils.isNotBlank(oldRefreshToken)) {
      // 将原来刷新的令牌进行删除
      redisUtil.del(REFRESH_TOKEN + oldRefreshToken);
    }

    // 将原来用户对应的令牌信息进行删除 将原来刷新的令牌信息删除
    redisUtil.del(ACCESS_TOKEN_USER_CLEAR + userId, REFRESH_TOKEN_USER_CLEAR + userId);
    return true;
  }

  /**
   * 检测账号是否是空
   *
   * @param userId 用户主键编号
   * @return 如果是空则返回true，反之返回false
   */
  private boolean checkParams(Long userId) {
    return userId == null || userId == 0 || redisUtil == null;
  }

  /**
   * 获取当前登录用户的用户编号
   *
   * @param accessToken 令牌的值
   * @return 用户主键编号
   */
  public Long getUserId(String accessToken) {
    Object userId = redisUtil.get(ACCESS_TOKEN + accessToken);
    if (userId != null) {
      return NumberUtil.parseLong(userId.toString());
    }
    return 0L;
  }

  /**
   * 将用户信息保存到redis中
   *
   * @param accessToken 令牌对象
   * @param userId 用户主键编号
   * @return 保存成功返回true，否则返回false
   */
  public boolean saveUserInfo(AccessToken accessToken, Long userId) {
    long expiresIn = authProperties.getAccessTokenValiditySeconds();
    // 保存令牌本身信息
    redisUtil.set(ACCESS_TOKEN + accessToken.getAccess_token(), userId, expiresIn);

    // 保存刷新令牌的信息，默认设置刷新令牌的有效时长是令牌本身的3倍
    redisUtil.set(REFRESH_TOKEN + accessToken.getRefresh_token(), userId, 3 * expiresIn);

    // 保存用户账号和令牌的关系，默认过期时间为其他临牌时间的4倍（这样做是为了保证用户下次登录的时候能将原来用户相关的信息在缓存中清除）
    redisUtil.set(ACCESS_TOKEN_USER_CLEAR + userId, accessToken.getAccess_token(), 4 * expiresIn);
    redisUtil.set(REFRESH_TOKEN_USER_CLEAR + userId, accessToken.getRefresh_token(), 4 * expiresIn);

    return true;
  }

  /**
   * 将用户详细信息保存到redis中
   *
   * @param accessToken 令牌值
   * @return 保存成功返回true，否则返回false
   */
  public boolean saveUserDetails(String accessToken, T user) {
    long expiresIn = authProperties.getAccessTokenValiditySeconds();
    redisUtil.set(USER + accessToken, user, expiresIn);
    return true;
  }
}
