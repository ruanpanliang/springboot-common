package com.lc.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/** @author liangchao */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.lc.springboot.user.controller"))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(apiEndPointsInfo())
        .useDefaultResponseMessages(false);
  }

  private ApiInfo apiEndPointsInfo() {
    return new ApiInfoBuilder()
        .title("测试用户系统API接口文档")
        .description("测试用户体系描述Demo")
        .contact(new Contact("Liangc", "", "ruanpanliang@126.com"))
        // .license("The MIT License")
        // .licenseUrl("https://opensource.org/licenses/MIT")
        .version("V1")
        .build();
  }
}
