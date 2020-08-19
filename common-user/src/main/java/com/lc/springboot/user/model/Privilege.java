package com.lc.springboot.user.model;

import com.lc.springboot.common.mybatisplus.model.BaseModel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
* 权限 实体对象
* @author: liangc
* @date: 2020-08-17 17:18
* @version 1.0
*/

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("SYS_PRIVILEGE")
@ApiModel(value="Privilege对象", description="权限 实体对象")
public class Privilege extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限编码")
    @TableField("PRIVILEGE_CODE")
    private String privilegeCode;

    @ApiModelProperty(value = "权限名称")
    @TableField("PRIVILEGE_NAME")
    private String privilegeName;

    @ApiModelProperty(value = "权限类型")
    @TableField("PRIVILEGE_TYPE")
    private String privilegeType;

    @ApiModelProperty(value = "实体类型")
    @TableField("ENTITY_TYPE")
    private String entityType;

    @ApiModelProperty(value = "实体编码")
    @TableField("ENTITY_ID")
    private String entityId;

    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;


    public static final String COL_PRIVILEGE_CODE = "PRIVILEGE_CODE";

    public static final String COL_PRIVILEGE_NAME = "PRIVILEGE_NAME";

    public static final String COL_PRIVILEGE_TYPE = "PRIVILEGE_TYPE";

    public static final String COL_ENTITY_TYPE = "ENTITY_TYPE";

    public static final String COL_ENTITY_ID = "ENTITY_ID";

    public static final String COL_REMARK = "REMARK";

}
