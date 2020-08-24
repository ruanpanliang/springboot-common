package com.lc.springboot.common.mybatisplus;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.lc.springboot.common.mybatisplus.handler.MyMetaObjectHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/** @author liangchao */
@EnableTransactionManagement
@Configuration
public class MyBatisPlusConfiguration {

  /** 注册乐观锁插件 */
  @Bean
  public OptimisticLockerInterceptor optimisticLockerInterceptor() {
    return new OptimisticLockerInterceptor();
  }

  /** 分页插件 */
  @Bean
  public PaginationInterceptor paginationInterceptor() {
    return new PaginationInterceptor();
  }

  @Bean
  public MyMetaObjectHandler metaObjectHandler() {
    return new MyMetaObjectHandler();
  }

  // @Bean
  // public MySqlInjector myLogicSqlInjector() {
  //   return new MySqlInjector();
  // }
}
