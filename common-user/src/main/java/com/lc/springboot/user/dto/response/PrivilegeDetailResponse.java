package com.lc.springboot.user.dto.response;

import com.lc.springboot.common.mybatisplus.model.BaseModel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
* 权限 详情返回对象
* @author: liangc
* @date: 2020-08-17 17:18
* @version 1.0
*/

@Getter
@Setter
@ApiModel(value="Privilege 详情返回对象", description="权限 详情返回实体对象")
public class PrivilegeDetailResponse extends BaseModel {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "权限编码")
    private String privilegeCode;

    @ApiModelProperty(value = "权限名称")
    private String privilegeName;

    @ApiModelProperty(value = "权限类型")
    private String privilegeType;

    @ApiModelProperty(value = "实体类型")
    private String entityType;

    @ApiModelProperty(value = "实体编码")
    private String entityId;

    @ApiModelProperty(value = "备注")
    private String remark;


}
