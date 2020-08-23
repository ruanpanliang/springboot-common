package com.lc.springboot.user.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 用户登录 详情返回对象
 *
 * @author: liangc
 * @date: 2020-08-17 17:18
 * @version 1.0
 */
@Getter
@Setter
@ApiModel(value = "User登录 详情返回对象", description = "用户登录 详情返回实体对象")
public class UserLoginDetailResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "用户ID")
  private Long id;

  @ApiModelProperty(value = "用户账号")
  private String userAccount;

  @ApiModelProperty(value = "用户类型")
  private String userType;

  @ApiModelProperty(value = "用户名称")
  private String userName;

  @ApiModelProperty(value = "电子邮件")
  private String email;

  @ApiModelProperty(value = "用户内置ID,随机码")
  private String randomCode;

  @ApiModelProperty("角色列表")
  private List<RoleLoginDetailResponse> roleList;

  @ApiModelProperty("权限列表")
  private List<PrivilegeLoginDetailResponse> privilegeList;

  @ApiModelProperty("菜单列表")
  private List<MenuLoginDetailResponse> menuList;

  // @ApiModelProperty("令牌信息")
  // private AccessToken accessToken;

  // @ApiModelProperty(value = "手机号码")
  // private String phone;

}
