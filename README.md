# 简介
这是一个springboot开发的公共模块

## 包含组件
| 模块                |     介绍                                                                          |
| -------------------|---------------------------------------------------------------------------------- |
| common-lib         |     公共的一些模块，集成redis，ftp，日志，mybatisplus                                              |
| generate-code      |     代码生成框架，源于mybatisplus自带的代码生成框架，根据本人的一些需求做了一些二次开发，能自动生成controller，service，dto，dmo，mapper和feign-client的固定模板代码                                           |
| common-user        |     使用基本的用户权限体系，包括用户，角色，权限和菜单等维护功能                                           |


## 安装
### Maven
先clone代码到本地
```shell script
git clone https://github.com/ruanpanliang/springboot-common.git
```
进入下载目录对应根目录，执行
```shell script
mvn clean install -Dmaven.test.skip=true
```
进行本地安装
```xml
<dependency>
    <groupId>com.lc</groupId>
    <artifactId>common-lib</artifactId>
    <version>1.0.0</version>
</dependency>
```
如果要引入代码生成框架
```xml
<dependency>
    <groupId>com.lc</groupId>
    <artifactId>generate-code</artifactId>
    <version>1.0.0</version>
    <scope>provided</scope>
</dependency>
```
如果要引入用户体系
```xml
<dependency>
    <groupId>com.lc</groupId>
    <artifactId>common-user</artifactId>
    <version>1.0.0</version>
</dependency>
```
仿照CustomCodeGenerator类做对应的自定义操作
