package com.lc.springboot.common.utils;

import java.util.UUID;
/**

 * @description: uuid字符串生成器
 * @author: liangc
 * @date: 2020-07-28 15:40
 * @version 1.0
 */
public class StringGenerator {

  /**
   * 获取uuid随机字符串
   *
   * @return
   */
  public static String uuid() {
    return UUID.randomUUID().toString().replace("-", "");
  }
}
