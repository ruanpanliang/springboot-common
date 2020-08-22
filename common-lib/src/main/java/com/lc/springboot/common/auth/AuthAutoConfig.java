package com.lc.springboot.common.auth;

import com.lc.springboot.common.auth.token.AccessTokenUtil;
import com.lc.springboot.common.redis.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 授权类配置类
 * @author: liangc
 * @date: 2020-08-16 11:13
 * @version 1.0
 */
@Configuration
@EnableConfigurationProperties(AuthProperties.class)
public class AuthAutoConfig {

  @Autowired RedisUtil redisUtil;

  @Bean
  public AccessTokenUtil accessTokenUtil(AuthProperties authProperties) {
    AccessTokenUtil accessTokenUtil = new AccessTokenUtil();
    accessTokenUtil.setRedisUtil(redisUtil);
    accessTokenUtil.setAuthProperties(authProperties);
    return accessTokenUtil;
  }
}
