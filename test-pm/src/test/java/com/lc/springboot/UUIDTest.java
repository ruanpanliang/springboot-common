package com.lc.springboot;

import java.util.UUID;

/**

 * @description: UUID测试
 * @author: liangc
 * @date: 2020-07-28 14:51
 * @version 1.0
 */
public class UUIDTest {

  public static String getUUID() {
    String uuid = UUID.randomUUID().toString().replace("-", "");
    return uuid;
  }

  public static void main(String[] args) {
    for (int i = 0; i < 100; i++) {
      System.out.println(getUUID());
    }
  }
}
