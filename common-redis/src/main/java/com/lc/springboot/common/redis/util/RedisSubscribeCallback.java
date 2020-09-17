package com.lc.springboot.common.redis.util;

/** @author liangchao */
public interface RedisSubscribeCallback {
  /**
   * 回调函数
   *
   * @param msg 信息
   */
  void callback(String msg);
}
