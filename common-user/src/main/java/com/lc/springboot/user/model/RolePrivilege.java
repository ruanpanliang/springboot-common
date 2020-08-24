package com.lc.springboot.user.model;

import com.lc.springboot.common.mybatisplus.model.BaseModel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
* 角色对应权限 实体对象
* @author: liangc
* @date: 2020-08-17 17:18
* @version 1.0
*/

@EqualsAndHashCode(callSuper = true)
@Builder
@TableName("SYS_ROLE_PRIVILEGE")
@ApiModel(value="RolePrivilege对象", description="角色对应权限 实体对象")
public class RolePrivilege extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色编号")
    @TableField("ROLE_ID")
    private Long roleId;

    @ApiModelProperty(value = "权限编码")
    @TableField("PRIVILEGE_ID")
    private Long privilegeId;

    public RolePrivilege() {
    }

    public RolePrivilege(Long roleId, Long privilegeId) {
        this.roleId = roleId;
        this.privilegeId = privilegeId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(Long privilegeId) {
        this.privilegeId = privilegeId;
    }

    public static final String COL_ROLE_ID = "ROLE_ID";

    public static final String COL_PRIVILEGE_ID = "PRIVILEGE_ID";

}
