# FIT2CLOUD 2.0 Demo

- [要求](#要求)
- [代码规范](#代码规范)
- [首页模式](#首页模式)
- [注意事项](#注意事项)

## 基础

- 掌握 [Angular JS 1.6.9](https://angular.io/) 的内容(此框架的不同版本差异较大，本文基于用 1.6.9)。
- 掌握 [Angular JS Material 1.1.9](https://material.angularjs.org/) 的内容。
- 开发时如需自动提示angular相关内容，可以npm install angular@1.6.9和npm install angular-material, 安装后务必在.gitignore文件中添加package-lock.json和node_modules(Demo工程已经添加)
- 本项目使用 [Spring Boot 2.0](https://spring.io) 作为基础框架并集成 `thymeleaf` 、 `shiro` 、 `quartz` 和 `mail` 等功能。

## 代码规范

####前端代码规范

- css, html, js等前端文件命名, 使用 `-` 做单词分隔，例如：fit2cloud-style.css, module-menu.html, angular-material.js
- angular: Controller，Service均使用大写首字母的驼峰命名，例如MenuController, MenuService
- angular: Directive，Component均使用小写首字母的驼峰命名，例如moduleMenu
- js: 变量使用小写首字母的驼峰命名，例如userName
- js: 常量使用全大写，下划线分隔单词的命名，例如var MAX_HEIGHT=1000


## 首页模式

每个工程的首页默认不显示公共菜单，需要引入web-public的jar包，启动后输入<hostname:port>/web-public可以看到公共菜单的首页。

## 注意事项

- Form表单，当表单内容多于8项，或者需要二次交互（弹出窗口或跳转）时不使用弹出窗口的方式显示表单，直接在主页面显示。