package com.lc.springboot.config;

import com.github.structlog4j.StructLog4J;
import com.github.structlog4j.json.JsonFormatter;
import com.lc.springboot.common.auth.AuthProperties;
import com.lc.springboot.common.config.date.StringToDateConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;

/** @author liangchao */
@Configuration
public class TestPmConfig implements WebMvcConfigurer {

  @Value("${spring.profiles.active:NA}")
  private String activeProfile;

  @Value("${spring.application.name:NA}")
  private String appName;

  // @Override
  // public void addInterceptors(InterceptorRegistry registry) {
  //   registry.addInterceptor(new MyAuthorizeInterceptor(authProperties));
  // }

  @PostConstruct
  public void init() {
    // init structured logging
    StructLog4J.setFormatter(JsonFormatter.getInstance());

    // global log fields setting
    StructLog4J.setMandatoryContextSupplier(
        () ->
            new Object[] {
              "env", activeProfile,
              "service", appName
            });
  }
}
