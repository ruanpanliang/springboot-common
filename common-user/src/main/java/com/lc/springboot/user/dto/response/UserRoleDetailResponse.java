package com.lc.springboot.user.dto.response;

import com.lc.springboot.common.mybatisplus.model.BaseModel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
* 用户对应角色 详情返回对象
* @author: liangc
* @date: 2020-08-17 17:18
* @version 1.0
*/

@Getter
@Setter
@ApiModel(value="UserRole 详情返回对象", description="用户对应角色 详情返回实体对象")
public class UserRoleDetailResponse extends BaseModel {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "用户编号")
    private Long userId;

    @ApiModelProperty(value = "角色编号")
    private Long roleId;


}
