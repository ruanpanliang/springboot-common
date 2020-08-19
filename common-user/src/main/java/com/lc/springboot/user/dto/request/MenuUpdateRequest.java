package com.lc.springboot.user.dto.request;

import com.lc.springboot.common.mybatisplus.model.BaseModel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
* 菜单 更新请求对象
* @author: liangc
* @date: 2020-08-17 17:18
* @version 1.0
*/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="Menu 更新请求对象", description="菜单 更新请求实体对象")
public class MenuUpdateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @ApiModelProperty(value = "ID", required = true, example = "0")
    @NotNull(message = "ID不能为空")
    private Long id;


    @ApiModelProperty(value = "菜单编码", required = true)
    @NotBlank(message = "菜单编码不能为空")
    private String menuCode;

    @ApiModelProperty(value = "菜单名字", required = true)
    @NotBlank(message = "菜单名字不能为空")
    private String menuName;

    @ApiModelProperty(value = "菜单类型", required = true)
    @NotBlank(message = "菜单类型不能为空")
    private String menuType;

    @ApiModelProperty(value = "菜单序号", required = true)
    @NotNull(message = "菜单序号不能为空")
    private Integer menuIndex;

    @ApiModelProperty(value = "菜单路由", required = true)
    @NotBlank(message = "菜单路由不能为空")
    private String menuPath;

    @ApiModelProperty(value = "菜单图标", required = true)
    @NotBlank(message = "菜单图标不能为空")
    private String menuIcon;

    @ApiModelProperty(value = "父菜单ID", required = true)
    @NotBlank(message = "父菜单ID不能为空")
    private String parentId;


}
