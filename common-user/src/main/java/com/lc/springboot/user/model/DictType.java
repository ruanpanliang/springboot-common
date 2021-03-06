package com.lc.springboot.user.model;

import com.lc.springboot.common.mybatisplus.model.BaseModel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
* 字典类型 实体对象
* @author: liangc
* @date: 2020-09-18 10:34
* @version 1.0
*/

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("SYS_DICT_TYPE")
@ApiModel(value="DictType对象", description="字典类型 实体对象")
public class DictType extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字典类型编码")
    @TableField("DICT_TYPE_CODE")
    private String dictTypeCode;

    @ApiModelProperty(value = "字典类型名称")
    @TableField("DICT_TYPE_NAME")
    private String dictTypeName;

    @ApiModelProperty(value = "状态 | 1：使用 0：未使用")
    @TableField("STATUS")
    private Integer status;


    public static final String COL_DICT_TYPE_CODE = "DICT_TYPE_CODE";

    public static final String COL_DICT_TYPE_NAME = "DICT_TYPE_NAME";

    public static final String COL_STATUS = "STATUS";

}
