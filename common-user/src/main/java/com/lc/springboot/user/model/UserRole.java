package com.lc.springboot.user.model;

import com.lc.springboot.common.mybatisplus.model.BaseModel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
* 用户对应角色 实体对象
* @author: liangc
* @date: 2020-08-17 17:18
* @version 1.0
*/

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("SYS_USER_ROLE")
@ApiModel(value="UserRole对象", description="用户对应角色 实体对象")
public class UserRole extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户编号")
    @TableField("USER_ID")
    private Long userId;

    @ApiModelProperty(value = "角色编号")
    @TableField("ROLE_ID")
    private Long roleId;


    public static final String COL_USER_ID = "USER_ID";

    public static final String COL_ROLE_ID = "ROLE_ID";

}
