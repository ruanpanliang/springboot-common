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
* 权限对应菜单 新增请求对象
* @author: liangc
* @date: 2020-08-17 17:18
* @version 1.0
*/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="PrivilegeMenu 新增请求对象", description="权限对应菜单 新增请求实体对象")
public class PrivilegeMenuAddRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限ID", required = true)
    @NotNull(message = "权限ID不能为空")
    private Long privilegeId;

    @ApiModelProperty(value = "菜单ID", required = true)
    @NotNull(message = "菜单ID不能为空")
    private Long menuId;


}
