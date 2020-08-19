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
* 角色 查询请求对象
* @author: liangc
* @date: 2020-08-17 17:18
* @version 1.0
*/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="Role 查询请求对象", description="角色 查询请求实体对象")
public class RoleQueryRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @ApiModelProperty(value = "ID", example = "0")
    private Long id;


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

    @ApiModelProperty(value = "创建开始时间")
    private Date queryStartDate;

    @ApiModelProperty(value = "创建结束时间")
    private Date queryEndDate;


}
