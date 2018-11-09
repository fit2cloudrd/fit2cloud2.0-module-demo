# FIT2CLOUD 云管平台 2.0 扩展模块的示范工程（模板工程）


# 目录

- [技能要求](#技能要求)
- [代码规范](#代码规范)
- [全局处理](#全局处理)
- [本地环境运行与测试](#本地环境运行与测试)
- [实际环境部署与升级](#实际环境部署与升级)

## 技能要求

- 掌握 [Angular JS 1.7.2](https://angular.io/) 的内容(此框架的不同版本差异较大，本文基于用 1.7.2)。
- 掌握 [Angular JS Material 1.1.9](https://material.angularjs.org/) 的内容。
- 开发时如需自动提示 Angular 相关内容，可以 npm install angular@1.7.2 和 npm install angular-material, 安装后务必在 .gitignore 文件中添加package-lock.json 和 node_modules (Demo
 工程已经添加)
- 本项目使用 [Spring Boot 2.0](https://spring.io) 作为基础框架并集成 `thymeleaf` 、 `shiro` 、 `quartz` 和 `mail` 等功能。

## 代码规范

- css, html, js等前端文件命名, 使用 `-` 做单词分隔，例如：fit2cloud-style.css, module-menu.html, angular-material.js
- angular: Controller，Service 均使用大写首字母的驼峰命名，例如 MenuController, MenuService
- angular: Directive，Component 均使用小写首字母的驼峰命名，例如 moduleMenu
- js: 变量使用小写首字母的驼峰命名，例如 userName
- js: 常量使用全大写，下划线分隔单词的命名，例如 var MAX_HEIGHT=1000


## 全局处理

- 后台代码对 @RestController结果集进行了统一封装成 ResultHolder,如果自己返回 ResultHolder,则不会封装。如果需要包装的数据,method 返回类型不要是Object的(new Object()和 null 不会包装，1.返回的 type 不是 application/json 2.没有对应的 Object.class 的 HttpMessageConverter)
- 如果不想返回结果被分装，可以在Controller的method上加@NoResultHolder注解
- 后台代码做了全局异常处理
- 前台 HttpUtils的 post, get 都做了错误处理（只有 ResultHolder 的 success 为 false 时，当做错误处理）,如果需要重新定义错误可以用 error 的 function 接受,没有 error function 或自己弹出后台的错误信息
- 在前端页面已经写的有 angular 的指令 
  - has-permission：有这个权限的时候显示 单个权限  
  - has-permissions：有其中一个权限的时候显示，主要是控制多个权限和去掉table的单选框、操作的列
  - lack-permission：没有这个权限的时候显示
  - lack-permissions：没有这里的所有权限的时候显示


## 本地环境编译、运行与测试

### 创建和初始化数据库

根据各自的情况创建数据库，执行下面的命令初始化 database：
```
CREATE DATABASE `fit2cloud` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
```
初始数据表可以从已安装的 FIT2CLOUD 2.0 云管平台服务器上导出。

### 创建本地2.0配置文件

FIT2CLOUD 2.0 的配置文件存放路径为 /opt/fit2cloud/conf/fit2cloud.properties，拷贝以下内容并保存(相关组件的配置请参考实际情况进行设置):
```
#CMP DB configs
rdb.driver=com.mysql.jdbc.Driver
rdb.url=jdbc:mysql://localhost:3306/fit2cloud?autoReconnect=false&useUnicode=true&characterEncoding=UTF-8&characterSetResults=UTF-8&zeroDateTimeBehavior=convertToNull&useSSL=false
rdb.user=root
rdb.password=root

#eureka
eureka.client.service-url.defaultZone=http://localhost:6602/eureka/

#keycloak
keycloak-server-address=http://localhost:8080/auth
keycloak.auth-server-url=/auth/
keycloak.realm=cmp
keycloak.public-client=true
keycloak.resource=cmp-client

#redis
redis.hostname=localhost
redis.password=fit2cloud
redis.port=56379
redis.database=2

#elasticsearch
spring.data.elasticsearch.cluster-name=fit2cloud-cmp-cluster
spring.data.elasticsearch.cluster-nodes=localhost:9090

logger.level=DEBUG

prometheus.host=http://localhost:53340
prometheus.pushgateway.host=localhost:53341
```

### 修改 maven 配置文件

工程的运行需要的依赖包均存放在 FIT2CLOUD 提供的 Nexus 服务器上，下载这些依赖包需要在 maven 的 settings.xml 配置文件中，加上以下 server 的设置即可:
```
<server>
  <!-- this id should match the id of the repo server in pom.xml -->
  <id>fit2cloud-enterprise-release</id>
  <username>readonly</username>
  <password>readonly@2018</password>
</server>
<server>
  <!-- this id should match the id of the repo server in pom.xml -->
  <id>fit2cloud</id>
  <username>readonly</username>
  <password>readonly@2018</password>
</server>
```

### 编译

在工程目录下，执行 mvn clean package 即可对工程进行编译。

### 运行

在工程目录下，执行 mvn clean spring-boot:run 即可在本地将模块运行起来，默认端口为 8080，可以通过访问 http://localhost:8080 来访问服务。

### 测试

以 IDEA 为例，可以在 IDEA 的 maven 插件中，以 debug 模式运行工程进行调试。

## 实际环境部署与升级

### 工程文件说明

本工程为 FIT2CLOUD 2.0 标准扩展模块示例工程，工程目录下包含了制作扩展包所必需的文件：
- Dockerfile - Dockerfile是一个包含用于组合映像的命令的文本文档。可以使用在命令行中调用任何命令。 Docker通过读取Dockerfile中的指令自动生成映像
- build.sh - FIT2CLOUD 提供的扩展包制作脚本，执行该脚本
- docker-compose.yml - docker-compose.yml 文件是一个定义服务、网络和卷的 YAML 文件
- service.ico - 扩展模块的图标文件
- service.inf - 扩展模块的描述文件，包含模块名称、描述、模块版本、对 FIT2CLOUD 云管平台的最低版本要求等

### 扩展包的制作

实际部署时，根据具体情况修改好工程中的相关信息，执行 bash build.sh 即可生成扩展包和对应的 MD5 文件，例如 module-demo-2.0.0.tar.gz 和 module-demo-2.0.0.tar.gz.md5。

### 扩展包的安装与升级

在实际环境中部署扩展包，需要先在 [FIT2CLOUD 客户支持门户](https://support.fit2cloud.com)中下载 FIT2CLOUD 云管平台 2.0 的安装包及《FIT2CLOUD 云管平台2.0 安装及升级手册》，根据文档中的说明安装云管平台以及生成的扩展包。
