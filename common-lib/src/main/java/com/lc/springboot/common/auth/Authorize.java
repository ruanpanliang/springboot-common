package com.lc.springboot.common.auth;

import java.lang.annotation.*;

/**
 * 授权检验,通过自定义拦截器{@link AuthorizeInterceptor}进行拦截操作
 *
 * @author liangchao
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Authorize {
  String[] value();
}
