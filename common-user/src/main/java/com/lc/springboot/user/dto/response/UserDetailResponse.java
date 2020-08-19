package com.lc.springboot.user.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lc.springboot.common.mybatisplus.model.BaseModel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 用户 详情返回对象
 *
 * @author: liangc
 * @date: 2020-08-17 17:18
 * @version 1.0
 */
@Getter
@Setter
@ApiModel(value = "User 详情返回对象", description = "用户 详情返回实体对象")
public class UserDetailResponse extends BaseModel {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "用户账号")
  private String userAccount;

  @ApiModelProperty(value = "用户密码")
  @JsonIgnore
  private String userPassword;

  @ApiModelProperty(value = "用户类型")
  private String userType;

  @ApiModelProperty(value = "用户名称")
  private String userName;

  @ApiModelProperty(value = "电子邮件")
  private String email;

  @ApiModelProperty(value = "手机号码")
  private String phone;

  @ApiModelProperty(value = "用户状态 | 1：正常 0：注销")
  private Integer status;
}
