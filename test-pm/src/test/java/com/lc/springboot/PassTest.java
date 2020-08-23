package com.lc.springboot;

import com.lc.springboot.common.crypto.Sha256;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @program: springboot-common
 * @description: 密码测试
 * @author: liangc
 * @date: 2020-08-19 18:22
 * @version 1.0
 */
public class PassTest {

  public static void main(String[] args) {
    System.out.println(passwordEncodeStr("123456"));
  }

  private static String passwordEncodeStr(String password) {
    return new BCryptPasswordEncoder().encode(password);
  }

  private static String getSha256Str(String password) {
    return Sha256.getSHA256Str(password);
  }
}
