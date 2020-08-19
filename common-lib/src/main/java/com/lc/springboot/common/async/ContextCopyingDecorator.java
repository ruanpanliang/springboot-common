package com.lc.springboot.common.async;

import org.springframework.core.task.TaskDecorator;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * 用于父子线程切换上下文丢失的问题
 *
 * @author liangchao
 */

// https://stackoverflow.com/questions/23732089/how-to-enable-request-scope-in-async-task-executor
public class ContextCopyingDecorator implements TaskDecorator {
  @Override
  public Runnable decorate(Runnable runnable) {
    RequestAttributes context = RequestContextHolder.currentRequestAttributes();
    return () -> {
      try {
        RequestContextHolder.setRequestAttributes(context);
        runnable.run();
      } finally {
        RequestContextHolder.resetRequestAttributes();
      }
    };
  }
}
