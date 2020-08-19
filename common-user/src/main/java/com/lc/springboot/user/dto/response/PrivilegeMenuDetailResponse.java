package com.lc.springboot.user.dto.response;

import com.lc.springboot.common.mybatisplus.model.BaseModel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
* 权限对应菜单 详情返回对象
* @author: liangc
* @date: 2020-08-17 17:18
* @version 1.0
*/

@Getter
@Setter
@ApiModel(value="PrivilegeMenu 详情返回对象", description="权限对应菜单 详情返回实体对象")
public class PrivilegeMenuDetailResponse extends BaseModel {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "权限ID")
    private Long privilegeId;

    @ApiModelProperty(value = "菜单ID")
    private Long menuId;


}
