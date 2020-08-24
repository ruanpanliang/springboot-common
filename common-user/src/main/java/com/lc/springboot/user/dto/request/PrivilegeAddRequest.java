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
 * 权限 新增请求对象
 *
 * @author: liangc
 * @date: 2020-08-17 17:18
 * @version 1.0
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Privilege 新增请求对象", description = "权限 新增请求实体对象")
public class PrivilegeAddRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "权限编码", required = true)
  @NotBlank(message = "权限编码不能为空")
  private String privilegeCode;

  @ApiModelProperty(value = "权限名称", required = true)
  @NotBlank(message = "权限名称不能为空")
  private String privilegeName;

  @ApiModelProperty(value = "权限类型")
  // @NotBlank(message = "权限类型不能为空")
  private String privilegeType;

  @ApiModelProperty(value = "实体类型")
  // @NotBlank(message = "实体类型不能为空")
  private String entityType;

  @ApiModelProperty(value = "实体编码")
  // @NotBlank(message = "实体编码不能为空")
  private String entityId;

  @ApiModelProperty(value = "备注")
  //@NotBlank(message = "备注不能为空")
  private String remark;

  @ApiModelProperty(value = "菜单ID列表", required = true)
  @NotEmpty(message = "菜单ID列表不能为空")
  private List<Long> menuId;

  public String getPrivilegeCode() {
    return privilegeCode;
  }

  public void setPrivilegeCode(String privilegeCode) {
    this.privilegeCode = privilegeCode;
  }

  public String getPrivilegeName() {
    return privilegeName;
  }

  public void setPrivilegeName(String privilegeName) {
    this.privilegeName = privilegeName;
  }

  public String getPrivilegeType() {
    return privilegeType;
  }

  public void setPrivilegeType(String privilegeType) {
    this.privilegeType = privilegeType;
  }

  public String getEntityType() {
    return entityType;
  }

  public void setEntityType(String entityType) {
    this.entityType = entityType;
  }

  public String getEntityId() {
    return entityId;
  }

  public void setEntityId(String entityId) {
    this.entityId = entityId;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public List<Long> getMenuId() {
    return menuId;
  }

  public void setMenuId(List<Long> menuId) {
    this.menuId = menuId;
  }
}
