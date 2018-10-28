# FIT2CLOUD 云管平台 2.0 扩展模块的示范工程（模板工程）


# 目录

- [技能要求](#技能要求)
- [代码规范](#代码规范)
- [首页模式](#首页模式)
- [全局处理](#全局处理)
- [前端页面](#前端页面)
- [注意事项](#注意事项)
- [编译打包](#编译打包)
- [本地环境运行与测试](#本地环境运行与测试)
- [实际环境部署与升级](#实际环境部署与升级)

## 技能要求

- 掌握 [Angular JS 1.6.9](https://angular.io/) 的内容(此框架的不同版本差异较大，本文基于用 1.6.9)。
- 掌握 [Angular JS Material 1.1.9](https://material.angularjs.org/) 的内容。
- 开发时如需自动提示 Angular 相关内容，可以 npm install angular@1.6.9 和 npm install angular-material, 安装后务必在 .gitignore 文件中添加package-lock.json 和 node_modules (Demo 工程已经添加)
- 本项目使用 [Spring Boot 2.0](https://spring.io) 作为基础框架并集成 `thymeleaf` 、 `shiro` 、 `quartz` 和 `mail` 等功能。

## 代码规范

- css, html, js等前端文件命名, 使用 `-` 做单词分隔，例如：fit2cloud-style.css, module-menu.html, angular-material.js
- angular: Controller，Service 均使用大写首字母的驼峰命名，例如 MenuController, MenuService
- angular: Directive，Component 均使用小写首字母的驼峰命名，例如 moduleMenu
- js: 变量使用小写首字母的驼峰命名，例如 userName
- js: 常量使用全大写，下划线分隔单词的命名，例如 var MAX_HEIGHT=1000


## 首页模式

每个工程的首页默认不显示公共菜单，需要引入 web-public 的jar包，启动后输入<hostname:port>/web-public可以看到公共菜单的首页。

## 全局处理

- 后台代码对 @RestController结果集进行了统一封装成 ResultHolder,如果自己返回 ResultHolder,则不会封装。如果需要包装的数据,method 返回类型不要是Object的(new Object()和 null 不会包装，1.返回的 type 不是 application/json 2.没有对应的 Object.class 的 HttpMessageConverter)
- 后台代码做了全局异常处理
- 前台 HttpUtils的 post, get 都做了错误处理（只有 ResultHolder 的 success 为 false 时，当做错误处理）,如果需要重新定义错误可以用 error 的 function 接受,没有 error function 或自己弹出后台的错误信息

#### 前端页面

  在前端页面已经写的有 angular 的指令
  
  - has-permission：有这个权限的时候显示 单个权限  
  - has-permissions：有其中一个权限的时候显示，主要是控制多个权限和去掉table的单选框、操作的列
  - lack-permission：没有这个权限的时候显示
  - lack-permissions：没有这里的所有权限的时候显示
 

## 注意事项

- Form 表单，当表单内容多于8项，或者需要二次交互（弹出窗口或内容有表格，并需要操作表格等）时不使用弹出窗口的方式显示表单，直接在主页面显示。


## 本地环境编译、运行与测试

TBD

## 实际环境部署与升级

TBD

