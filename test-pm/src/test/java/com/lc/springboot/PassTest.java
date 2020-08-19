package com.lc.springboot;

import com.lc.springboot.common.crypto.Sha256;

/**
 * @program: springboot-common
 * @description: 密码测试
 * @author: liangc
 * @date: 2020-08-19 18:22
 * @version 1.0
 **/
public class PassTest {

  public static void main(String[] args) {
    System.out.println(Sha256.getSHA256Str("123456"));
  }
}
