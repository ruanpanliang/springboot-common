package com.lc.springboot.user.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
/**
* 字典 新增请求对象
* @author: liangc
* @date: 2020-09-18 10:34
* @version 1.0
*/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="Dict 新增请求对象", description="字典 新增请求实体对象")
public class DictAddRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字典编号", required = true)
    @NotBlank(message = "字典编号不能为空")
    private String dictCode;

    @ApiModelProperty(value = "字典名称", required = true)
    @NotBlank(message = "字典名称不能为空")
    private String dictName;

    @ApiModelProperty(value = "字典类型编码", required = true)
    @NotBlank(message = "字典类型编码不能为空")
    private String dictTypeCode;

    @ApiModelProperty(value = "状态 | 1：使用 0：未使用", required = true)
    @NotNull(message = "状态 | 1：使用 0：未使用不能为空")
    private Integer status;


}
