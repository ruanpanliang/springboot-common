package com.lc.springboot.user.model;

import com.lc.springboot.common.mybatisplus.model.BaseModel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
* 菜单 实体对象
* @author: liangc
* @date: 2020-08-17 17:18
* @version 1.0
*/

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("SYS_MENU")
@ApiModel(value="Menu对象", description="菜单 实体对象")
public class Menu extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜单编码")
    @TableField("MENU_CODE")
    private String menuCode;

    @ApiModelProperty(value = "菜单名字")
    @TableField("MENU_NAME")
    private String menuName;

    @ApiModelProperty(value = "菜单类型")
    @TableField("MENU_TYPE")
    private String menuType;

    @ApiModelProperty(value = "菜单序号")
    @TableField("MENU_INDEX")
    private Integer menuIndex;

    @ApiModelProperty(value = "菜单路由")
    @TableField("MENU_PATH")
    private String menuPath;

    @ApiModelProperty(value = "菜单图标")
    @TableField("MENU_ICON")
    private String menuIcon;

    @ApiModelProperty(value = "父菜单ID")
    @TableField("PARENT_ID")
    private String parentId;


    public static final String COL_MENU_CODE = "MENU_CODE";

    public static final String COL_MENU_NAME = "MENU_NAME";

    public static final String COL_MENU_TYPE = "MENU_TYPE";

    public static final String COL_MENU_INDEX = "MENU_INDEX";

    public static final String COL_MENU_PATH = "MENU_PATH";

    public static final String COL_MENU_ICON = "MENU_ICON";

    public static final String COL_PARENT_ID = "PARENT_ID";

}
