package com.lc.springboot.user.dto.response;

import com.lc.springboot.common.mybatisplus.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 用户登录获取菜单 详情返回对象
 *
 * @author: liangc
 * @date: 2020-08-17 17:18
 * @version 1.0
 */
@Getter
@Setter
@ApiModel(value = "用户登录获取菜单 详情返回对象", description = "用户登录获取菜单 详情返回实体对象")
public class MenuLoginDetailResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "菜单编号")
  private String menuId;

  @ApiModelProperty(value = "菜单编码")
  private String menuCode;

  @ApiModelProperty(value = "菜单名字")
  private String menuName;

  @ApiModelProperty(value = "菜单类型")
  private String menuType;

  @ApiModelProperty(value = "菜单序号")
  private Integer menuIndex;

  @ApiModelProperty(value = "菜单路由")
  private String menuPath;

  @ApiModelProperty(value = "菜单图标")
  private String menuIcon;

  @ApiModelProperty(value = "父菜单ID")
  private String parentId;
}
