package com.lc.springboot; // package com.chinamobile.zj.idc.management.config;

import com.baomidou.mybatisplus.core.enums.SqlLike;
import com.lc.springboot.mybatisplus.generatecode.AutoGenerator;
import com.lc.springboot.mybatisplus.generatecode.config.*;
import com.lc.springboot.mybatisplus.generatecode.config.converts.MySqlTypeConvert;
import com.lc.springboot.mybatisplus.generatecode.config.po.LikeTable;
import com.lc.springboot.mybatisplus.generatecode.config.rules.DbColumnType;
import com.lc.springboot.mybatisplus.generatecode.config.rules.NamingStrategy;
import com.lc.springboot.mybatisplus.generatecode.engine.FreemarkerTemplateEngine;

/**
 * mybatisplus3代码生成器 去掉了service接口层 用于用户权限端的生成
 *
 * @author liangc
 */
public class UserCodeGenerator {

  public static void main(String[] args) {

    // 系统版本(用到注释中)
    String version = "1.0";
    // 项目名称
    String projectName = "user";
    // 模板名称
    String moduleName = "";
    // 根包名
    String basePackage = "com.lc.springboot." + projectName;
    // 开发人员
    String author = "liangc";

    // 设置程序代码输出根目录
    String outputRootDir = "/Users/liangchao/tmp/";
    // 设置代码输出目录
    String outputDir = outputRootDir + projectName + "/src/main/java/";
    // 设置mapperXml文件的输出目录
    String xmlOutputDir = outputRootDir + projectName + "/src/main/resources/"; //

    // 模板文件路径
    String templateDir = "lc-custom";

    // 数据库配置---------------------
    String dbDriverName = "com.mysql.cj.jdbc.Driver";
    String dbUserName = "root";
    String dbPassword = "123456";
    String dbUrl = "jdbc:mysql://localhost:3306/lc";

    // 表前缀
    String tablePrefix = "sys";
    // 生成样例代码的表
    // String[] tables = new String[] {"hxz_chat_group"};
    String[] tables = new String[] {};
    String likeTable = "SYS_";

    // 代码生成器
    AutoGenerator mpg = new AutoGenerator();

    // 全局配置
    GlobalConfig gc = new GlobalConfig();
    gc.setProjectName(projectName);
    // 设置代码输出目录[java]
    gc.setOutputDir(outputDir);
    // 设置mapper xml文件输出目录
    gc.setXmlOutputDir(xmlOutputDir);
    // 开发人员
    gc.setAuthor(author);
    // 系统版本
    gc.setVersion(version);
    // controller URL 根路径
    gc.setControllerRootMapPath(String.format("/v1/%s/", projectName));
    // 是否开启swagger2
    gc.setSwagger2(true);
    // 是否打开输出目录
    gc.setOpen(true);
    // 设置是否生成feign client客户端代码
    gc.setEnableFeignClient(false);
    gc.setFeignClientEndpoint(String.format("${%s.%s-service-endpoint}", projectName, moduleName));

    // service 命名方式,次生成模式不用Service接口类
    // gc.setServiceName("%sService");

    // serviceImpl 命名方式
    gc.setServiceImplName("%sService");

    // 自定义文件命名，注意 %s 会自动填充表实体属性！
    gc.setMapperName("%sMapper");
    // mapper xml 命名方式
    gc.setXmlName("%sMapper");
    // feign client命名方式
    gc.setFeignClientName("%sFeignClient");

    // 是否覆盖
    gc.setFileOverride(true);

    // 关闭 ActiveRecord 模式
    gc.setActiveRecord(false);
    // XML 二级缓存
    gc.setEnableCache(false);
    // XML ResultMap
    gc.setBaseResultMap(true);
    // XML columList
    gc.setBaseColumnList(true);

    mpg.setGlobalConfig(gc);

    // 数据源配置
    DataSourceConfig dsc = new DataSourceConfig();
    dsc.setDriverName(dbDriverName);
    dsc.setUsername(dbUserName);
    dsc.setPassword(dbPassword);
    dsc.setUrl(dbUrl);
    dsc.setTypeConvert(
        new MySqlTypeConvert() {
          @Override
          public DbColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
            // 将数据库中datetime转换成date
            if (fieldType.toLowerCase().contains("datetime")) {
              return DbColumnType.DATE;
            }
            return (DbColumnType) super.processTypeConvert(globalConfig, fieldType);
          }
        });
    mpg.setDataSource(dsc);

    // ==============================================包配置=================================================
    PackageConfig pc = new PackageConfig();
    // 父包名
    pc.setParent(basePackage);
    // 父包模块名
    pc.setModuleName(moduleName);
    // 定义实体类包名
    pc.setEntity("model");
    // 定义mapper类包名
    pc.setMapper("mapper");
    // 定义xml包名
    // pc.setXml("mapper.xml");
    pc.setXml("mysql_mapper");

    // 定义service包名
    // pc.setService("service");

    // 定义service实现类包名
    pc.setServiceImpl("service");
    // 定义controller包名
    pc.setController("controller");
    // 定义dto请求包名
    pc.setDtoRequest("dto.request");
    // 定义dto响应包名
    pc.setDtoResponse("dto.response");
    // 定义feign client的包名
    pc.setFeignClient("client");

    mpg.setPackageInfo(pc);

    TemplateConfig tc = new TemplateConfig();
    // 设置controller模板文件
    tc.setController(String.format("/templates/%s/controller.java", templateDir));
    // 设置model类模板文件
    tc.setEntity(String.format("/templates/%s/entity.java", templateDir));
    // 设置service实现类模板文件
    tc.setServiceImpl(String.format("/templates/%s/service.java", templateDir));
    // 设置mapper类接口模板文件
    tc.setMapper(String.format("/templates/%s/mapper.java", templateDir));
    // 设置mapper xml文档模板文件
    tc.setXml(String.format("/templates/%s/mapper.xml", templateDir));
    // 设置dto add类模板文件
    tc.setDtoAdd(String.format("/templates/%s/dtoAdd.java", templateDir));
    // 设置dto query类模板文件
    tc.setDtoQuery(String.format("/templates/%s/dtoQuery.java", templateDir));
    // 设置dto update类模板文件
    tc.setDtoUpdate(String.format("/templates/%s/dtoUpdate.java", templateDir));
    // 设置dto detail类模板文件
    tc.setDtoDetail(String.format("/templates/%s/dtoDetail.java", templateDir));
    // 设置feign cleint类的模板文件
    tc.setFeignClient(String.format("/templates/%s/feignClient.java", templateDir));
    // 设置feign cleint类的模板文件
    tc.setFeignClientConst(String.format("/templates/%s/feignClientConst.java", templateDir));

    mpg.setTemplate(tc);

    // 策略配置
    StrategyConfig strategy = new StrategyConfig();
    // 忽略生成service接口层
    strategy.setIgnoreInterfaceSericeGen(true);
    // 是否生成字段常量
    strategy.setEntityColumnConstant(true);
    // 数据库表映射到实体的命名策略
    strategy.setNaming(NamingStrategy.underline_to_camel);
    strategy.setColumnNaming(
        // 数据库表字段映射到实体的命名策略, 未指定按照 naming 执行
        NamingStrategy.underline_to_camel);
    // 自定义继承的Entity类全称，带包名
    strategy.setSuperEntityClass("com.lc.springboot.common.mybatisplus.model.BaseModel");
    // 自定义基础的Entity类，公共字段
    strategy.setSuperEntityColumns(
        "id",
        "created_by",
        "created_time",
        "updated_by",
        "updated_time",
        "random_code",
        "revision",
        "delete_flag");

    // 【实体】是否为lombok模型（默认 false）
    strategy.setEntityLombokModel(true);
    // 生成 @RestController 控制器
    strategy.setRestControllerStyle(true);
    // 自定义继承的Controller类全称，带包名
    // strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");

    // 表前缀
    strategy.setTablePrefix(tablePrefix);
    // 需要包含的表名，允许正则表达式
    strategy.setInclude(tables);
    strategy.setLikeTable(new LikeTable(likeTable, SqlLike.RIGHT));
    // 驼峰转连字符
    strategy.setControllerMappingHyphenStyle(true);

    mpg.setStrategy(strategy);

    // 设置模板引擎
    mpg.setTemplateEngine(new FreemarkerTemplateEngine());
    // 执行结果
    mpg.execute();
  }
}
