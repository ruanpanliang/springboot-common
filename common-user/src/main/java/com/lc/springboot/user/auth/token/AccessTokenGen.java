package com.lc.springboot.user.auth.token;

import cn.hutool.core.util.RandomUtil;

/**
 * 令牌生成器
 *
 * @author liangchao
 */
public class AccessTokenGen {

  public static AccessToken genAccessToken(long expires_in) {

    AccessToken accessToken = new AccessToken();
    accessToken.setAccess_token(RandomUtil.randomString(50));
    accessToken.setRefresh_token(RandomUtil.randomString(50));
    accessToken.setExpires_in(expires_in);
    return accessToken;
  }
}
