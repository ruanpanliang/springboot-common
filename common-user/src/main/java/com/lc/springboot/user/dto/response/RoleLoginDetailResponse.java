package com.lc.springboot.user.dto.response;

import com.lc.springboot.common.mybatisplus.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 用户登录获取角色 详情返回对象
 *
 * @author: liangc
 * @date: 2020-08-17 17:18
 * @version 1.0
 */
@Getter
@Setter
@ApiModel(value = "用户登录获取角色 详情返回对象", description = "用户登录获取角色 详情返回实体对象")
public class RoleLoginDetailResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "角色编号")
  private Long roleId;

  @ApiModelProperty(value = "角色编码")
  private String roleCode;

  @ApiModelProperty(value = "角色名称")
  private String roleName;

  @ApiModelProperty(value = "角色类型")
  private String roleType;
}
