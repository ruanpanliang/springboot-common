package com.lc.springboot.user.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
* 字典类型 更新请求对象
* @author: liangc
* @date: 2020-09-18 10:34
* @version 1.0
*/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="DictType 更新请求对象", description="字典类型 更新请求实体对象")
public class DictTypeUpdateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @ApiModelProperty(value = "ID", required = true, example = "0")
    @NotNull(message = "ID不能为空")
    private Long id;


    @ApiModelProperty(value = "字典类型编码", required = true)
    @NotBlank(message = "字典类型编码不能为空")
    private String dictTypeCode;

    @ApiModelProperty(value = "字典类型名称", required = true)
    @NotBlank(message = "字典类型名称不能为空")
    private String dictTypeName;

    @ApiModelProperty(value = "状态 | 1：使用 0：未使用", required = true)
    @NotNull(message = "状态 | 1：使用 0：未使用不能为空")
    private Integer status;


}
