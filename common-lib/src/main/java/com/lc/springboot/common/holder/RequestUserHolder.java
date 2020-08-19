package com.lc.springboot.common.holder;

/**
 * 保存当前用户ID信息
 *
 * @author liangchao
 */
public class RequestUserHolder {

  private static final ThreadLocal<Long> holder = new ThreadLocal<>();

  public static void set(Long id) {
    holder.set(id);
  }

  public static Long getId() {
    return holder.get();
  }

  public static void remove() {
    holder.remove();
  }
}
