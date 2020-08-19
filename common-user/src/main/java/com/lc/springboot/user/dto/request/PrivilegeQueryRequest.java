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
* 权限 查询请求对象
* @author: liangc
* @date: 2020-08-17 17:18
* @version 1.0
*/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="Privilege 查询请求对象", description="权限 查询请求实体对象")
public class PrivilegeQueryRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @ApiModelProperty(value = "ID", example = "0")
    private Long id;


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

    @ApiModelProperty(value = "创建开始时间")
    private Date queryStartDate;

    @ApiModelProperty(value = "创建结束时间")
    private Date queryEndDate;


}
