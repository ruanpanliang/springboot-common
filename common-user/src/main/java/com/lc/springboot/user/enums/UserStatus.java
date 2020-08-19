package com.lc.springboot.user.enums;

/**
 * 用户状态
 * @author: liangc
 * @date: 2020-08-18 09:18
 * @version 1.0
 */
public enum UserStatus {


    LOGOUT(0,"注销"),
    NORMAL(1,"正常");

    /**
     * 状态码
     */
    private int code;

    /**
     * 状态信息
     */
    private String msg;


    UserStatus(int code, String msg) {
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
