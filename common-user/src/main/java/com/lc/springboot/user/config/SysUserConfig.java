package com.lc.springboot.user.config;

import com.github.structlog4j.StructLog4J;
import com.github.structlog4j.json.JsonFormatter;
import com.lc.springboot.common.auth.AuthProperties;
import com.lc.springboot.common.auth.token.AccessTokenUtil;
import com.lc.springboot.common.config.date.StringToDateConverter;
import com.lc.springboot.common.redis.util.RedisUtil;
import com.lc.springboot.user.interceptor.UserAuthorizeInterceptor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;

/**
 * 配置类，用于配置拦截器和时间状态器
 *
 * @author liangchao
 */
@Configuration
public class SysUserConfig implements WebMvcConfigurer {

  @Autowired AuthProperties authProperties;

  @Autowired RedisUtil redisUtil;

  @Autowired AccessTokenUtil accessTokenUtil;

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    UserAuthorizeInterceptor userAuthorizeInterceptor =
        new UserAuthorizeInterceptor(authProperties);
    userAuthorizeInterceptor.setRedisUtil(redisUtil);
    userAuthorizeInterceptor.setAccessTokenUtil(accessTokenUtil);

    registry.addInterceptor(userAuthorizeInterceptor);
  }

  @Override
  public void addFormatters(FormatterRegistry registry) {
    // 字符串转时间转换器，用于拦截带有注解@RequestMapping的方法参数
    registry.addConverter(new StringToDateConverter());
  }
}
