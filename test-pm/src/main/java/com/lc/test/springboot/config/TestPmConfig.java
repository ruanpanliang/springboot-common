package com.lc.test.springboot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;

/** @author liangchao */
@Configuration
public class TestPmConfig implements WebMvcConfigurer {

  @Value("${spring.profiles.active:NA}")
  private String activeProfile;

  @Value("${spring.application.name:NA}")
  private String appName;
}
