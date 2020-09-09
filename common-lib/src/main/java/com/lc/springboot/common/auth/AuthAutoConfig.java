package com.lc.springboot.common.auth;

import com.lc.springboot.common.auth.token.AccessTokenUtil;
import com.lc.springboot.common.redis.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @description: 授权类配置类
 * @author: liangc
 * @date: 2020-08-16 11:13
 * @version 1.0
 */
@Configuration
@EnableConfigurationProperties(AuthProperties.class)
@ConditionalOnClass(RedisTemplate.class)
@ConditionalOnBean(RedisUtil.class)
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
