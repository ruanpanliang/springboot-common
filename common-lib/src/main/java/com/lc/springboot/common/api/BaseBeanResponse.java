package com.lc.springboot.common.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/** @author liangchao */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("返回bean基类")
public class BaseBeanResponse<T> extends BaseResponse {

  @ApiModelProperty("BaseBeanResponse 具体实体类")
  private T info;

}
