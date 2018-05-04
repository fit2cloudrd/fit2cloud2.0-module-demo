# FIT2CLOUD 2.0 Demo

- [要求](#要求)
- [代码规范](#代码规范)
- [启动步骤](#启动步骤)
- [注意事项](#注意事项)

## 基础

- 掌握 [Angular JS 1.6.9](https://angular.io/) 的内容(此框架的不同版本差异较大，本文基于用 1.6.9)。
- 掌握 [Angular JS Material 1.1.8](https://material.angularjs.org/) 的内容。
- 本项目使用 [Spring Boot 2.0](https://spring.io) 作为基础框架并集成 `thymeleaf` 、 `shiro` 、 `quartz` 和 `mail` 等功能。

## 代码规范

####前端代码规范

- css, html, js等前端文件命名, 使用 `-` 做单词分隔，例如：fit2cloud-style.css, module-menu.html, angular-material.js
- angular: Controller，Service均使用大写首字母的驼峰命名，例如MenuController, MenuService
- angular: Directive，Component均使用小写首字母的驼峰命名，例如moduleMenu
- js: 变量使用小写首字母的驼峰命名，例如userName
- js: 常量使用全大写，下划线分隔单词的命名，例如var MAX_HEIGHT=1000