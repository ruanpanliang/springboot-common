package com.lc.springboot.user.enums;

/**
 * 用户体系业务处理错误信息常量类
 *
 * @author: liangc
 * @date: 2020-08-24 09:10
 * @version 1.0
 */
public enum UserResultCode {
  PWD_NOT_SAME(1000, "用户密码填写不一致"),
  NOT_LOGIN(1001, "请先登录");

  final int code;

  final String msg;

  UserResultCode(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public int getCode() {
    return code;
  }

  public String getMsg() {
    return msg;
  }
}
