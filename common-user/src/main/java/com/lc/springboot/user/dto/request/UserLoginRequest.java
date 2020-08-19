package com.lc.springboot.user.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @用户登录 登录请求对象
 *
 * @author: liangc
 * @date: 2020-08-17 16:54
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "User 登录请求对象", description = "用户 登录请求实体对象")
public class UserLoginRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "用户账号", required = true)
  @NotBlank(message = "用户账号不能为空")
  private String userAccount;

  @ApiModelProperty(value = "用户密码", required = true)
  @NotBlank(message = "用户密码不能为空")
  private String userPassword;
}
