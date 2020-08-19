/*
 * Copyright (c) 2011-2020, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.lc.springboot.mybatisplus.generatecode.config;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 模板路径配置项
 *
 * @author tzg hubin
 * @since 2017-06-17
 */
@Data
@Accessors(chain = true)
public class TemplateConfig {

  @Getter(AccessLevel.NONE)
  private String entity = ConstVal.TEMPLATE_ENTITY_JAVA;

  private String entityKt = ConstVal.TEMPLATE_ENTITY_KT;

  private String service = ConstVal.TEMPLATE_SERVICE;

  private String serviceImpl = ConstVal.TEMPLATE_SERVICE_IMPL;

  private String mapper = ConstVal.TEMPLATE_MAPPER;

  private String xml = ConstVal.TEMPLATE_XML;

  private String controller = ConstVal.TEMPLATE_CONTROLLER;

  // self add
  private String dtoAdd = ConstVal.TEMPLATE_DTO_ADD;

  private String dtoQuery = ConstVal.TEMPLATE_DTO_QUERY;

  private String dtoUpdate = ConstVal.TEMPLATE_DTO_UPDATE;

  private String dtoDetail = ConstVal.TEMPLATE_DTO_DETAIL;

  private String feignClient = ConstVal.TEMPLATE_FEIGN_CLIENT;
  private String feignClientConst = ConstVal.TEMPLATE_FEIGN_CLIENT_CONST;

  public String getEntity(boolean kotlin) {
    return kotlin ? entityKt : entity;
  }

  /**
   * 禁用模板
   *
   * @param templateTypes 模板类型
   * @return this
   * @since 3.3.2
   */
  public TemplateConfig disable(TemplateType... templateTypes) {
    if (templateTypes != null && templateTypes.length > 0) {
      for (TemplateType templateType : templateTypes) {
        switch (templateType) {
          case XML:
            setXml(null);
            break;
          case ENTITY:
            setEntity(null).setEntityKt(null);
            break;
          case MAPPER:
            setMapper(null);
            break;
          case SERVICE:
            setService(null).setServiceImpl(null);
            break;
          case CONTROLLER:
            setController(null);
            break;
          case DTO_ADD:
            setDtoAdd(null);
            break;
          case DTO_QUERY:
            setDtoQuery(null);
            break;
          case DTO_UPDATE:
            setDtoUpdate(null);
            break;
          case DTO_DETAIL:
            setDtoDetail(null);
            break;
          case FEIGN_CLIENT:
            setFeignClient(null);
            break;
          case FEIGN_CLIENT_CONST:
            setFeignClientConst(null);
            break;
        }
      }
    }
    return this;
  }
}
