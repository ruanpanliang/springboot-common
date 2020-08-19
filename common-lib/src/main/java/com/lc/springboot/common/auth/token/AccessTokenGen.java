package com.lc.springboot.common.auth.token;

import cn.hutool.core.util.RandomUtil;

/**
 * 令牌生成器
 *
 * @author liangchao
 */
public class AccessTokenGen {

  public static AccessToken genAccessToken(long expires_in) {

    return new AccessToken(RandomUtil.randomString(50), RandomUtil.randomString(50), expires_in);
  }
}
