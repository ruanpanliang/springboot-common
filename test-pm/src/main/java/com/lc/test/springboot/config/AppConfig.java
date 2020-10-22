package com.lc.test.springboot.config;

import com.lc.springboot.common.async.ContextCopyingDecorator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 异步线程池
 *
 * @author liangchao
 */
@Configuration
@EnableAsync
@Import(value = {TestPmRestConfig.class})
public class AppConfig {

  public static final String ASYNC_EXECUTOR_NAME = "asyncExecutor";

  /**
   * 异步线程池
   *
   * @return
   */
  @Bean(name = ASYNC_EXECUTOR_NAME)
  public Executor asyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    // for passing in request scope context
    executor.setTaskDecorator(new ContextCopyingDecorator());
    executor.setCorePoolSize(3);
    executor.setMaxPoolSize(5);
    executor.setQueueCapacity(300);
    executor.setWaitForTasksToCompleteOnShutdown(true);
    executor.setThreadNamePrefix("AsyncThread-");
    executor.initialize();
    return executor;
  }


}
