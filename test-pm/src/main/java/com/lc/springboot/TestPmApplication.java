package com.lc.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: springboot-common
 * @description: 测试用户体系
 * @author: liangc
 * @date: 2020-08-18 23:06
 * @version 1.0
 */
@SpringBootApplication
@MapperScan(basePackages = "com.lc.springboot.user.mapper")
public class TestPmApplication {

  public static void main(String[] args) {
    SpringApplication.run(TestPmApplication.class, args);
  }
}
