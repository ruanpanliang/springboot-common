package com.lc.springboot.common.redis.prefix;

/** @author liangchao */
public interface KeyPrefix {

  public int expireSeconds();

  public String getPrefix();
}
