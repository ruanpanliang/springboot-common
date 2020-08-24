package com.lc.springboot.user.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * 角色 新增请求对象
 *
 * @author: liangc
 * @date: 2020-08-17 17:18
 * @version 1.0
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Role 新增请求对象", description = "角色 新增请求实体对象")
public class RoleAddRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "角色编码", required = true)
  @NotBlank(message = "角色编码不能为空")
  private String roleCode;

  @ApiModelProperty(value = "角色名称", required = true)
  @NotBlank(message = "角色名称不能为空")
  private String roleName;

  @ApiModelProperty(value = "角色类型", required = true)
  @NotBlank(message = "角色类型不能为空")
  private String roleType;

  // @ApiModelProperty(value = "角色状态 | 1:正常 0：失效", required = true)
  // @NotBlank(message = "角色状态 | 1:正常 0：失效不能为空")
  // private String roleStatus;

  @ApiModelProperty(value = "备注")
  private String remark;

  @ApiModelProperty(value = "权限ID列表", required = true)
  @NotEmpty(message = "权限ID列表不能为空")
  private List<Long> privilegeIds;

  public String getRoleCode() {
    return roleCode;
  }

  public void setRoleCode(String roleCode) {
    this.roleCode = roleCode;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public String getRoleType() {
    return roleType;
  }

  public void setRoleType(String roleType) {
    this.roleType = roleType;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public List<Long> getPrivilegeIds() {
    return privilegeIds;
  }

  public void setPrivilegeIds(List<Long> privilegeIds) {
    this.privilegeIds = privilegeIds;
  }
}
