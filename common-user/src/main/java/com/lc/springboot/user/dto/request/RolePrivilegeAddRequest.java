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
* 角色对应权限 新增请求对象
* @author: liangc
* @date: 2020-08-17 17:18
* @version 1.0
*/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="RolePrivilege 新增请求对象", description="角色对应权限 新增请求实体对象")
public class RolePrivilegeAddRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色编号", required = true)
    @NotNull(message = "角色编号不能为空")
    private Long roleId;

    @ApiModelProperty(value = "权限编码", required = true)
    @NotNull(message = "权限编码不能为空")
    private Long privilegeId;


}
