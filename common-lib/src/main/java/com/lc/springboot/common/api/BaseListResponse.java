package com.lc.springboot.common.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**

 * @description: 列表型返回类
 * @author: liangc
 * @date: 2020-07-30 15:10
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("BaseListResponse 返回list基类")
public class BaseListResponse<T> extends BaseResponse {

  @ApiModelProperty("具体结果集类")
  private MyPageInfo<T> resultSet;
}
