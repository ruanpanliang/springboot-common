package com.lc.springboot.user.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 角色 更新请求对象
 *
 * @author: liangc
 * @date: 2020-08-17 17:18
 * @version 1.0
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Role 更新请求对象", description = "角色 更新请求实体对象")
public class RoleUpdateRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  /** ID */
  @ApiModelProperty(value = "ID", required = true, example = "0")
  @NotNull(message = "ID不能为空")
  private Long id;

  @ApiModelProperty(value = "角色编码", required = true)
  @NotBlank(message = "角色编码不能为空")
  private String roleCode;

  @ApiModelProperty(value = "角色名称", required = true)
  @NotBlank(message = "角色名称不能为空")
  private String roleName;

  @ApiModelProperty(value = "角色类型", required = true)
  @NotBlank(message = "角色类型不能为空")
  private String roleType;

  @ApiModelProperty(value = "角色状态 | 1:正常 0：失效", required = true)
  @NotNull(message = "角色状态 | 1:正常 0：失效不能为空")
  private Integer roleStatus;

  @ApiModelProperty(value = "备注")
  // @NotBlank(message = "备注不能为空")
  private String remark;

  @ApiModelProperty(value = "权限ID列表")
  @NotEmpty(message = "权限ID列表不能为空")
  private List<Long> privilegeIds;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

  public Integer getRoleStatus() {
    return roleStatus;
  }

  public void setRoleStatus(Integer roleStatus) {
    this.roleStatus = roleStatus;
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
