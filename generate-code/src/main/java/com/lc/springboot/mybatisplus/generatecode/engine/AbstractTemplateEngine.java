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
package com.lc.springboot.mybatisplus.generatecode.engine;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.lc.springboot.mybatisplus.generatecode.config.GlobalConfig;
import com.lc.springboot.mybatisplus.generatecode.config.TemplateConfig;
import com.lc.springboot.mybatisplus.generatecode.config.builder.ConfigBuilder;
import com.lc.springboot.mybatisplus.generatecode.InjectionConfig;
import com.lc.springboot.mybatisplus.generatecode.config.ConstVal;
import com.lc.springboot.mybatisplus.generatecode.config.FileOutConfig;
import com.lc.springboot.mybatisplus.generatecode.config.po.TableInfo;
import com.lc.springboot.mybatisplus.generatecode.config.rules.FileType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 模板引擎抽象类
 *
 * @author hubin
 * @since 2018-01-10
 */
public abstract class AbstractTemplateEngine {

  protected static final Logger logger = LoggerFactory.getLogger(AbstractTemplateEngine.class);
  /** 配置信息 */
  private ConfigBuilder configBuilder;

  /** 模板引擎初始化 */
  public AbstractTemplateEngine init(ConfigBuilder configBuilder) {
    this.configBuilder = configBuilder;
    return this;
  }

  /** 输出 java xml 文件 */
  public AbstractTemplateEngine batchOutput() {
    try {
      List<TableInfo> tableInfoList = getConfigBuilder().getTableInfoList();
      for (TableInfo tableInfo : tableInfoList) {
        Map<String, Object> objectMap = getObjectMap(tableInfo);

        logger.info(new JSONObject(objectMap).toString());

        Map<String, String> pathInfo = getConfigBuilder().getPathInfo();
        TemplateConfig template = getConfigBuilder().getTemplate();
        // 自定义内容
        InjectionConfig injectionConfig = getConfigBuilder().getInjectionConfig();
        if (null != injectionConfig) {
          injectionConfig.initTableMap(tableInfo);
          objectMap.put("cfg", injectionConfig.getMap());
          List<FileOutConfig> focList = injectionConfig.getFileOutConfigList();
          if (CollectionUtils.isNotEmpty(focList)) {
            for (FileOutConfig foc : focList) {
              if (isCreate(FileType.OTHER, foc.outputFile(tableInfo))) {
                writerFile(objectMap, foc.getTemplatePath(), foc.outputFile(tableInfo));
              }
            }
          }
        }
        // Mp.java
        String entityName = tableInfo.getEntityName();
        if (null != entityName && null != pathInfo.get(ConstVal.ENTITY_PATH)) {
          String entityFile =
              String.format(
                  (pathInfo.get(ConstVal.ENTITY_PATH) + File.separator + "%s" + suffixJavaOrKt()),
                  entityName);
          if (isCreate(FileType.ENTITY, entityFile)) {
            writerFile(
                objectMap,
                templateFilePath(
                    template.getEntity(getConfigBuilder().getGlobalConfig().isKotlin())),
                entityFile);
          }
        }
        // MpMapper.java
        if (null != tableInfo.getMapperName() && null != pathInfo.get(ConstVal.MAPPER_PATH)) {
          String mapperFile =
              String.format(
                  (pathInfo.get(ConstVal.MAPPER_PATH)
                      + File.separator
                      + tableInfo.getMapperName()
                      + suffixJavaOrKt()),
                  entityName);
          if (isCreate(FileType.MAPPER, mapperFile)) {
            writerFile(objectMap, templateFilePath(template.getMapper()), mapperFile);
          }
        }

        // 默认情况下xml文件生成到mapper包名下
        if (StringUtils.isBlank(getConfigBuilder().getGlobalConfig().getXmlOutputDir())) {
          // MpMapper.xml
          if (null != tableInfo.getXmlName() && null != pathInfo.get(ConstVal.XML_PATH)) {
            String xmlFile =
                String.format(
                    (pathInfo.get(ConstVal.XML_PATH)
                        + File.separator
                        + tableInfo.getXmlName()
                        + ConstVal.XML_SUFFIX),
                    entityName);
            if (isCreate(FileType.XML, xmlFile)) {
              writerFile(objectMap, templateFilePath(template.getXml()), xmlFile);
            }
          }
        } else {
          // v2 自定义输出目录
          // MpMapper.xml
          if (null != tableInfo.getXmlName() && null != pathInfo.get(ConstVal.XML_PATH)) {
            String xmlFile =
                String.format(
                    (pathInfo.get(ConstVal.XML_PATH)
                        + File.separator
                        + tableInfo.getXmlName()
                        + ConstVal.XML_SUFFIX),
                    entityName);
            if (isCreate(FileType.XML, xmlFile)) {
              writerFile(objectMap, templateFilePath(template.getXml()), xmlFile);
            }
          }
        }

        // IMpService.java
        if (!configBuilder.getStrategyConfig().isIgnoreInterfaceSericeGen()) {
          if (null != tableInfo.getServiceName() && null != pathInfo.get(ConstVal.SERVICE_PATH)) {
            String serviceFile =
                String.format(
                    (pathInfo.get(ConstVal.SERVICE_PATH)
                        + File.separator
                        + tableInfo.getServiceName()
                        + suffixJavaOrKt()),
                    entityName);
            if (isCreate(FileType.SERVICE, serviceFile)) {
              writerFile(objectMap, templateFilePath(template.getService()), serviceFile);
            }
          }
        }

        // MpServiceImpl.java
        if (null != tableInfo.getServiceImplName()
            && null != pathInfo.get(ConstVal.SERVICE_IMPL_PATH)) {
          String implFile =
              String.format(
                  (pathInfo.get(ConstVal.SERVICE_IMPL_PATH)
                      + File.separator
                      + tableInfo.getServiceImplName()
                      + suffixJavaOrKt()),
                  entityName);
          if (isCreate(FileType.SERVICE_IMPL, implFile)) {
            writerFile(objectMap, templateFilePath(template.getServiceImpl()), implFile);
          }
        }
        // MpController.java
        if (null != tableInfo.getControllerName()
            && null != pathInfo.get(ConstVal.CONTROLLER_PATH)) {
          String controllerFile =
              String.format(
                  (pathInfo.get(ConstVal.CONTROLLER_PATH)
                      + File.separator
                      + tableInfo.getControllerName()
                      + suffixJavaOrKt()),
                  entityName);
          if (isCreate(FileType.CONTROLLER, controllerFile)) {
            writerFile(objectMap, templateFilePath(template.getController()), controllerFile);
          }
        }

        // TODO
        // MpAddRequest.java
        if (null != tableInfo.getDtoAddName() && null != pathInfo.get(ConstVal.DTO_REQUEST_PATH)) {
          String dtoAddFile =
              String.format(
                  (pathInfo.get(ConstVal.DTO_REQUEST_PATH)
                      + File.separator
                      + tableInfo.getDtoAddName()
                      + suffixJavaOrKt()),
                  entityName);
          if (isCreate(FileType.DTO_ADD, dtoAddFile)) {
            writerFile(objectMap, templateFilePath(template.getDtoAdd()), dtoAddFile);
          }
        }

        // MpQueryRequest.java
        if (null != tableInfo.getDtoQueryName()
            && null != pathInfo.get(ConstVal.DTO_REQUEST_PATH)) {
          String dtoQueryFile =
              String.format(
                  (pathInfo.get(ConstVal.DTO_REQUEST_PATH)
                      + File.separator
                      + tableInfo.getDtoQueryName()
                      + suffixJavaOrKt()),
                  entityName);
          if (isCreate(FileType.DTO_QUERY, dtoQueryFile)) {
            writerFile(objectMap, templateFilePath(template.getDtoQuery()), dtoQueryFile);
          }
        }

        // MpUpdateRequest.java
        if (null != tableInfo.getDtoUpdateName()
            && null != pathInfo.get(ConstVal.DTO_REQUEST_PATH)) {
          String dtoUpdateFile =
              String.format(
                  (pathInfo.get(ConstVal.DTO_REQUEST_PATH)
                      + File.separator
                      + tableInfo.getDtoUpdateName()
                      + suffixJavaOrKt()),
                  entityName);
          if (isCreate(FileType.DTO_UPDATE, dtoUpdateFile)) {
            writerFile(objectMap, templateFilePath(template.getDtoUpdate()), dtoUpdateFile);
          }
        }

        // MpDetailResponse.java
        if (null != tableInfo.getDtoDetailName()
            && null != pathInfo.get(ConstVal.DTO_RESPONSE_PATH)) {
          String dtoDetailFile =
              String.format(
                  (pathInfo.get(ConstVal.DTO_RESPONSE_PATH)
                      + File.separator
                      + tableInfo.getDtoDetailName()
                      + suffixJavaOrKt()),
                  entityName);
          if (isCreate(FileType.DTO_DETAIL, dtoDetailFile)) {
            writerFile(objectMap, templateFilePath(template.getDtoDetail()), dtoDetailFile);
          }
        }

        // MpFeignClient.java
        if (null != tableInfo.getFeignClientName()
            && null != pathInfo.get(ConstVal.FEIGN_CLIENT_PATH)
            && getConfigBuilder().getGlobalConfig().isEnableFeignClient()) {
          String feignClientFiles =
              String.format(
                  (pathInfo.get(ConstVal.FEIGN_CLIENT_PATH)
                      + File.separator
                      + tableInfo.getFeignClientName()
                      + suffixJavaOrKt()),
                  entityName);
          if (isCreate(FileType.FEIGN_CLIENT, feignClientFiles)) {
            writerFile(objectMap, templateFilePath(template.getFeignClient()), feignClientFiles);
          }
        }

        // MpFeignClientConst.java
        if (null != tableInfo.getFeignClientConstName()
            && null != pathInfo.get(ConstVal.FEIGN_CLIENT_PATH)
            && getConfigBuilder().getGlobalConfig().isEnableFeignClient()) {
          String feignClientConstFiles =
              String.format(
                  (pathInfo.get(ConstVal.FEIGN_CLIENT_PATH)
                      + File.separator
                      + tableInfo.getFeignClientConstName()
                      + suffixJavaOrKt()),
                  getConfigBuilder().getPackageInfo().get(ConstVal.MODULE_NAME));
          if (isCreate(FileType.FEIGN_CLIENT_CONST, feignClientConstFiles)) {
            writerFile(
                objectMap, templateFilePath(template.getFeignClientConst()), feignClientConstFiles);
          }
        }
      }
    } catch (Exception e) {
      logger.error("无法创建文件，请检查配置信息！", e);
    }
    return this;
  }

  protected void writerFile(Map<String, Object> objectMap, String templatePath, String outputFile)
      throws Exception {
    if (StringUtils.isNotBlank(templatePath)) {
      this.writer(objectMap, templatePath, outputFile);
    }
  }

  /**
   * 将模板转化成为文件
   *
   * @param objectMap 渲染对象 MAP 信息
   * @param templatePath 模板文件
   * @param outputFile 文件生成的目录
   */
  public abstract void writer(Map<String, Object> objectMap, String templatePath, String outputFile)
      throws Exception;

  /** 处理输出目录 */
  public AbstractTemplateEngine mkdirs() {
    getConfigBuilder()
        .getPathInfo()
        .forEach(
            (key, value) -> {
              File dir = new File(value);
              if (!dir.exists()) {
                boolean result = dir.mkdirs();
                if (result) {
                  logger.debug("创建目录： [" + value + "]");
                }
              }
            });
    return this;
  }

  /** 打开输出目录 */
  public void open() {
    String outDir = getConfigBuilder().getGlobalConfig().getOutputDir();
    if (getConfigBuilder().getGlobalConfig().isOpen() && StringUtils.isNotBlank(outDir)) {
      try {
        String osName = System.getProperty("os.name");
        if (osName != null) {
          if (osName.contains("Mac")) {
            Runtime.getRuntime().exec("open " + outDir);
          } else if (osName.contains("Windows")) {
            Runtime.getRuntime().exec("cmd /c start " + outDir);
          } else {
            logger.debug("文件输出目录:" + outDir);
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 渲染对象 MAP 信息
   *
   * @param tableInfo 表信息对象
   * @return ignore
   */
  public Map<String, Object> getObjectMap(TableInfo tableInfo) {
    Map<String, Object> objectMap;
    ConfigBuilder config = getConfigBuilder();
    if (config.getStrategyConfig().isControllerMappingHyphenStyle()) {
      objectMap = CollectionUtils.newHashMapWithExpectedSize(36);
      objectMap.put(
          "controllerMappingHyphenStyle",
          config.getStrategyConfig().isControllerMappingHyphenStyle());
      objectMap.put(
          "controllerMappingHyphen", StringUtils.camelToHyphen(tableInfo.getEntityPath()));
    } else {
      objectMap = CollectionUtils.newHashMapWithExpectedSize(34);
    }
    objectMap.put("restControllerStyle", config.getStrategyConfig().isRestControllerStyle());
    objectMap.put("config", config);
    objectMap.put("package", config.getPackageInfo());
    GlobalConfig globalConfig = config.getGlobalConfig();
    objectMap.put("author", globalConfig.getAuthor());
    objectMap.put("version", globalConfig.getVersion());
    objectMap.put("controllerRootMapPath", globalConfig.getControllerRootMapPath());
    objectMap.put("feignClientEndpoint", globalConfig.getFeignClientEndpoint());
    objectMap.put("projectName", globalConfig.getProjectName());

    objectMap.put(
        "idType", globalConfig.getIdType() == null ? null : globalConfig.getIdType().toString());
    objectMap.put("logicDeleteFieldName", config.getStrategyConfig().getLogicDeleteFieldName());
    objectMap.put("versionFieldName", config.getStrategyConfig().getVersionFieldName());
    objectMap.put("activeRecord", globalConfig.isActiveRecord());
    objectMap.put("kotlin", globalConfig.isKotlin());
    objectMap.put("swagger2", globalConfig.isSwagger2());
    objectMap.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
    objectMap.put("table", tableInfo);
    objectMap.put("enableCache", globalConfig.isEnableCache());
    objectMap.put("baseResultMap", globalConfig.isBaseResultMap());
    objectMap.put("baseColumnList", globalConfig.isBaseColumnList());
    objectMap.put("entity", tableInfo.getEntityName());
    objectMap.put("entitySerialVersionUID", config.getStrategyConfig().isEntitySerialVersionUID());
    objectMap.put("entityColumnConstant", config.getStrategyConfig().isEntityColumnConstant());
    objectMap.put("entityBuilderModel", config.getStrategyConfig().isEntityBuilderModel());
    objectMap.put("chainModel", config.getStrategyConfig().isChainModel());
    objectMap.put("entityLombokModel", config.getStrategyConfig().isEntityLombokModel());
    objectMap.put(
        "entityBooleanColumnRemoveIsPrefix",
        config.getStrategyConfig().isEntityBooleanColumnRemoveIsPrefix());
    objectMap.put("superEntityClass", getSuperClassName(config.getSuperEntityClass()));
    objectMap.put("superMapperClassPackage", config.getSuperMapperClass());
    objectMap.put("superMapperClass", getSuperClassName(config.getSuperMapperClass()));
    objectMap.put("superServiceClassPackage", config.getSuperServiceClass());
    objectMap.put("superServiceClass", getSuperClassName(config.getSuperServiceClass()));
    objectMap.put("superServiceImplClassPackage", config.getSuperServiceImplClass());
    objectMap.put("superServiceImplClass", getSuperClassName(config.getSuperServiceImplClass()));
    objectMap.put(
        "superControllerClassPackage", verifyClassPacket(config.getSuperControllerClass()));
    objectMap.put("superControllerClass", getSuperClassName(config.getSuperControllerClass()));
    return Objects.isNull(config.getInjectionConfig())
        ? objectMap
        : config.getInjectionConfig().prepareObjectMap(objectMap);
  }

  /**
   * 用于渲染对象MAP信息 {@link #getObjectMap(TableInfo)} 时的superClassPacket非空校验
   *
   * @param classPacket ignore
   * @return ignore
   */
  private String verifyClassPacket(String classPacket) {
    return StringUtils.isBlank(classPacket) ? null : classPacket;
  }

  /**
   * 获取类名
   *
   * @param classPath ignore
   * @return ignore
   */
  private String getSuperClassName(String classPath) {
    if (StringUtils.isBlank(classPath)) {
      return null;
    }
    return classPath.substring(classPath.lastIndexOf(StringPool.DOT) + 1);
  }

  /**
   * 模板真实文件路径
   *
   * @param filePath 文件路径
   * @return ignore
   */
  public abstract String templateFilePath(String filePath);

  /**
   * 检测文件是否存在
   *
   * @return 文件是否存在
   */
  protected boolean isCreate(FileType fileType, String filePath) {
    ConfigBuilder cb = getConfigBuilder();
    // 自定义判断
    InjectionConfig ic = cb.getInjectionConfig();
    if (null != ic && null != ic.getFileCreate()) {
      return ic.getFileCreate().isCreate(cb, fileType, filePath);
    }
    // 全局判断【默认】
    File file = new File(filePath);
    boolean exist = file.exists();
    if (!exist) {
      file.getParentFile().mkdirs();
    }
    return !exist || getConfigBuilder().getGlobalConfig().isFileOverride();
  }

  /** 文件后缀 */
  protected String suffixJavaOrKt() {
    return getConfigBuilder().getGlobalConfig().isKotlin()
        ? ConstVal.KT_SUFFIX
        : ConstVal.JAVA_SUFFIX;
  }

  public ConfigBuilder getConfigBuilder() {
    return configBuilder;
  }

  public AbstractTemplateEngine setConfigBuilder(ConfigBuilder configBuilder) {
    this.configBuilder = configBuilder;
    return this;
  }
}
