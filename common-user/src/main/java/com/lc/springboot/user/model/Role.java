package com.lc.springboot.user.model;

import com.lc.springboot.common.mybatisplus.model.BaseModel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
* 角色 实体对象
* @author: liangc
* @date: 2020-08-17 17:18
* @version 1.0
*/

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("SYS_ROLE")
@ApiModel(value="Role对象", description="角色 实体对象")
public class Role extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色编码")
    @TableField("ROLE_CODE")
    private String roleCode;

    @ApiModelProperty(value = "角色名称")
    @TableField("ROLE_NAME")
    private String roleName;

    @ApiModelProperty(value = "角色类型")
    @TableField("ROLE_TYPE")
    private String roleType;

    @ApiModelProperty(value = "角色状态 | 1:正常 0：失效")
    @TableField("ROLE_STATUS")
    private String roleStatus;

    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;


    public static final String COL_ROLE_CODE = "ROLE_CODE";

    public static final String COL_ROLE_NAME = "ROLE_NAME";

    public static final String COL_ROLE_TYPE = "ROLE_TYPE";

    public static final String COL_ROLE_STATUS = "ROLE_STATUS";

    public static final String COL_REMARK = "REMARK";

}
