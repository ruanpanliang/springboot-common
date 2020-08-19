package com.lc.springboot.user.model;

import com.lc.springboot.common.mybatisplus.model.BaseModel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
* 权限对应菜单 实体对象
* @author: liangc
* @date: 2020-08-17 17:18
* @version 1.0
*/

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("SYS_PRIVILEGE_MENU")
@ApiModel(value="PrivilegeMenu对象", description="权限对应菜单 实体对象")
public class PrivilegeMenu extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限ID")
    @TableField("PRIVILEGE_ID")
    private Long privilegeId;

    @ApiModelProperty(value = "菜单ID")
    @TableField("MENU_ID")
    private Long menuId;


    public static final String COL_PRIVILEGE_ID = "PRIVILEGE_ID";

    public static final String COL_MENU_ID = "MENU_ID";

}
