package com.lc.springboot.user.dto.response;

import com.lc.springboot.common.mybatisplus.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
* 字典类型 详情返回对象
* @author: liangc
* @date: 2020-09-18 10:34
* @version 1.0
*/

@Getter
@Setter
@ApiModel(value="DictType 详情返回对象", description="字典类型 详情返回实体对象")
public class DictTypeDetailResponse extends BaseModel {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "字典类型编码")
    private String dictTypeCode;

    @ApiModelProperty(value = "字典类型名称")
    private String dictTypeName;

    @ApiModelProperty(value = "状态 | 1：使用 0：未使用")
    private Integer status;


}
