package com.lc.springboot.user.dto.request;

import com.lc.springboot.common.mybatisplus.model.BaseModel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
/**
* 权限 新增请求对象
* @author: liangc
* @date: 2020-08-17 17:18
* @version 1.0
*/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="Privilege 新增请求对象", description="权限 新增请求实体对象")
public class PrivilegeAddRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限编码", required = true)
    @NotBlank(message = "权限编码不能为空")
    private String privilegeCode;

    @ApiModelProperty(value = "权限名称", required = true)
    @NotBlank(message = "权限名称不能为空")
    private String privilegeName;

    @ApiModelProperty(value = "权限类型", required = true)
    @NotBlank(message = "权限类型不能为空")
    private String privilegeType;

    @ApiModelProperty(value = "实体类型", required = true)
    @NotBlank(message = "实体类型不能为空")
    private String entityType;

    @ApiModelProperty(value = "实体编码", required = true)
    @NotBlank(message = "实体编码不能为空")
    private String entityId;

    @ApiModelProperty(value = "备注", required = true)
    @NotBlank(message = "备注不能为空")
    private String remark;


}
