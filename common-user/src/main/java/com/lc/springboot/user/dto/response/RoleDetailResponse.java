package com.lc.springboot.user.dto.response;

import com.lc.springboot.common.mybatisplus.model.BaseModel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
* 角色 详情返回对象
* @author: liangc
* @date: 2020-08-17 17:18
* @version 1.0
*/

@Getter
@Setter
@ApiModel(value="Role 详情返回对象", description="角色 详情返回实体对象")
public class RoleDetailResponse extends BaseModel {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "角色编码")
    private String roleCode;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色类型")
    private String roleType;

    @ApiModelProperty(value = "角色状态 | 1:正常 0：失效")
    private String roleStatus;

    @ApiModelProperty(value = "备注")
    private String remark;


}
