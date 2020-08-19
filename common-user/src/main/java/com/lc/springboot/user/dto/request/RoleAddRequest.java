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
* 角色 新增请求对象
* @author: liangc
* @date: 2020-08-17 17:18
* @version 1.0
*/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="Role 新增请求对象", description="角色 新增请求实体对象")
public class RoleAddRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色编码", required = true)
    @NotBlank(message = "角色编码不能为空")
    private String roleCode;

    @ApiModelProperty(value = "角色名称", required = true)
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    @ApiModelProperty(value = "角色类型", required = true)
    @NotBlank(message = "角色类型不能为空")
    private String roleType;

    @ApiModelProperty(value = "角色状态 | 1:正常 0：失效", required = true)
    @NotBlank(message = "角色状态 | 1:正常 0：失效不能为空")
    private String roleStatus;

    @ApiModelProperty(value = "备注", required = true)
    @NotBlank(message = "备注不能为空")
    private String remark;


}
