package com.lc.springboot.user.model;

import com.lc.springboot.common.mybatisplus.model.BaseModel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
* 字典 实体对象
* @author: liangc
* @date: 2020-09-18 10:34
* @version 1.0
*/

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("SYS_DICT")
@ApiModel(value="Dict对象", description="字典 实体对象")
public class Dict extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字典编号")
    @TableField("DICT_CODE")
    private String dictCode;

    @ApiModelProperty(value = "字典名称")
    @TableField("DICT_NAME")
    private String dictName;

    @ApiModelProperty(value = "字典类型编码")
    @TableField("DICT_TYPE_CODE")
    private String dictTypeCode;

    @ApiModelProperty(value = "状态 | 1：使用 0：未使用")
    @TableField("STATUS")
    private Integer status;


    public static final String COL_DICT_CODE = "DICT_CODE";

    public static final String COL_DICT_NAME = "DICT_NAME";

    public static final String COL_DICT_TYPE_CODE = "DICT_TYPE_CODE";

    public static final String COL_STATUS = "STATUS";

}
