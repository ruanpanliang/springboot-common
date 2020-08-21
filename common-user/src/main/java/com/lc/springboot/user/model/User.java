package com.lc.springboot.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lc.springboot.common.mybatisplus.model.BaseModel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
* 用户 实体对象
* @author: liangc
* @date: 2020-08-17 17:18
* @version 1.0
*/

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("SYS_USER")
@ApiModel(value="User对象", description="用户 实体对象")
public class User extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户账号")
    @TableField("USER_ACCOUNT")
    private String userAccount;

    @ApiModelProperty(value = "用户密码")
    @TableField("USER_PASSWORD")
    @JsonIgnore
    private String userPassword;

    @ApiModelProperty(value = "用户类型")
    @TableField("USER_TYPE")
    private String userType;

    @ApiModelProperty(value = "用户名称")
    @TableField("USER_NAME")
    private String userName;

    @ApiModelProperty(value = "电子邮件")
    @TableField("EMAIL")
    private String email;

    @ApiModelProperty(value = "手机号码")
    @TableField("PHONE")
    private String phone;

    @ApiModelProperty(value = "用户状态 | 1：正常 0：注销")
    @TableField("STATUS")
    private Integer status;


    public static final String COL_USER_ACCOUNT = "USER_ACCOUNT";

    public static final String COL_USER_PASSWORD = "USER_PASSWORD";

    public static final String COL_USER_TYPE = "USER_TYPE";

    public static final String COL_USER_NAME = "USER_NAME";

    public static final String COL_EMAIL = "EMAIL";

    public static final String COL_PHONE = "PHONE";

    public static final String COL_STATUS = "STATUS";



}
