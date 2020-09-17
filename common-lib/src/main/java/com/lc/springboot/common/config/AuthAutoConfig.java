package com.lc.springboot.common.config;

import com.lc.springboot.common.auth.AuthProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
}
