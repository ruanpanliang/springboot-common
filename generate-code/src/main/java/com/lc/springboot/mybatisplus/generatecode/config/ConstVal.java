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

import com.baomidou.mybatisplus.core.toolkit.StringPool;

import java.nio.charset.StandardCharsets;

/**
 * 定义常量
 *
 * @author YangHu, tangguo, hubin
 * @since 2016-08-31
 */
public interface ConstVal {

  String MODULE_NAME = "ModuleName";

  // 用于包名的key
  String PARENT = "Parent";
  String ENTITY = "Entity";
  String SERVICE = "Service";
  String SERVICE_IMPL = "ServiceImpl";
  String MAPPER = "Mapper";
  String XML = "Xml";
  String CONTROLLER = "Controller";
  // self add
  String DTO_ADD = "AddRequest";
  String DTO_QUERY = "QueryRequest";
  String DTO_UPDATE = "UpdateRequest";
  String DTO_DETAIL = "DetailResponse";
  String FEIGN_CLIENT = "FeignClient";
  String FEIGN_CLIENT_CONST = "FeignClientConst";

  // 输出路径的key
  String ENTITY_PATH = "entity_path";
  String SERVICE_PATH = "service_path";
  String SERVICE_IMPL_PATH = "service_impl_path";
  String MAPPER_PATH = "mapper_path";
  String XML_PATH = "xml_path";
  String CONTROLLER_PATH = "controller_path";
  // self add
  String DTO_REQUEST_PATH = "dto_request_path";
  String DTO_RESPONSE_PATH = "dto_response_path";
  String FEIGN_CLIENT_PATH = "feign_client_path";

  String JAVA_TMPDIR = "java.io.tmpdir";
  String UTF8 = StandardCharsets.UTF_8.name();
  String UNDERLINE = "_";

  String JAVA_SUFFIX = StringPool.DOT_JAVA;
  String KT_SUFFIX = ".kt";
  String XML_SUFFIX = ".xml";

  // 模板文件路径
  String TEMPLATE_ENTITY_JAVA = "/templates/entity.java";
  String TEMPLATE_ENTITY_KT = "/templates/entity.kt";
  String TEMPLATE_MAPPER = "/templates/mapper.java";
  String TEMPLATE_XML = "/templates/mapper.xml";
  String TEMPLATE_SERVICE = "/templates/service.java";
  String TEMPLATE_SERVICE_IMPL = "/templates/serviceImpl.java";
  String TEMPLATE_CONTROLLER = "/templates/controller.java";
  /** 新增dto类 */
  String TEMPLATE_DTO_ADD = "/templates/dtoAdd.java";
  /** 查询dto类 */
  String TEMPLATE_DTO_QUERY = "/templates/dtoQuery.java";
  /** 更新dto类 */
  String TEMPLATE_DTO_UPDATE = "/templates/dtoUpdate.java";
  /** 返回详情dto类 */
  String TEMPLATE_DTO_DETAIL = "/templates/dtoDetail.java";
  /** feign client 类 */
  String TEMPLATE_FEIGN_CLIENT = "/templates/feignClient.java";
  /** feign client const 类 */
  String TEMPLATE_FEIGN_CLIENT_CONST = "/templates/feignClientConst.java";

  String VM_LOAD_PATH_KEY = "file.resource.loader.class";
  String VM_LOAD_PATH_VALUE = "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader";

  String SUPER_MAPPER_CLASS = "com.baomidou.mybatisplus.core.mapper.BaseMapper";
  String SUPER_SERVICE_CLASS = "com.baomidou.mybatisplus.extension.service.IService";
  String SUPER_SERVICE_IMPL_CLASS = "com.baomidou.mybatisplus.extension.service.impl.ServiceImpl";
}
