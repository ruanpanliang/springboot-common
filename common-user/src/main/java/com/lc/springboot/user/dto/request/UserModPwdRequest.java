package com.lc.springboot.user.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 用户更新密码请求对象
 *
 * @author: liangc
 * @date: 2020-08-17 17:18
 * @version 1.0
 */
@ApiModel(value = "用户更新密码请求对象", description = "用户更新密码请求对象")
public class UserModPwdRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "新密码", required = true)
  @NotBlank(message = "新密码不能为空")
  private String newPwd;

  @ApiModelProperty(value = "确认新密码", required = true)
  @NotBlank(message = "确认新密码不能为空")
  private String newPwdAgain;

  public String getNewPwd() {
    return newPwd;
  }

  public void setNewPwd(String newPwd) {
    this.newPwd = newPwd;
  }

  public String getNewPwdAgain() {
    return newPwdAgain;
  }

  public void setNewPwdAgain(String newPwdAgain) {
    this.newPwdAgain = newPwdAgain;
  }
}
