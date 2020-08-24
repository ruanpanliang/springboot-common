package com.lc.springboot.user.util;

import com.lc.springboot.common.auth.AuthContext;
import com.lc.springboot.common.error.ServiceException;
import com.lc.springboot.user.enums.UserResultCode;

/**
 * 用户模块工具类
 *
 * @author: liangc
 * @date: 2020-08-24 09:35
 * @version 1.0
 */
public class UserUtil {

  /**
   * 检测用户是否处于登录状态，如果没有登录，则报异常信息
   *
   * @return 当前用户登录的编号
   */
  public static Long checkUserIdAndGet() {
    long userId = AuthContext.getUserId();
    if (userId == 0) {
      throw new ServiceException(UserResultCode.NOT_LOGIN.getMsg());
    }
    return userId;
  }
}
