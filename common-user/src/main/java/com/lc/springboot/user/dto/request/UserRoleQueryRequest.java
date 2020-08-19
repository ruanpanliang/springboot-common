package com.lc.springboot.user.dto.request;

import com.lc.springboot.common.mybatisplus.model.BaseModel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.*;

/**
* 用户对应角色 查询请求对象
* @author: liangc
* @date: 2020-08-17 17:18
* @version 1.0
*/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="UserRole 查询请求对象", description="用户对应角色 查询请求实体对象")
public class UserRoleQueryRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @ApiModelProperty(value = "ID", example = "0")
    private Long id;


    @ApiModelProperty(value = "用户编号", example = "0")
    private Long userId;

    @ApiModelProperty(value = "角色编号", example = "0")
    private Long roleId;

    @ApiModelProperty(value = "创建开始时间")
    private Date queryStartDate;

    @ApiModelProperty(value = "创建结束时间")
    private Date queryEndDate;


}
