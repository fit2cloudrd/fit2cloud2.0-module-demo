# FIT2CLOUD 云管平台 2.0 扩展模块的示范工程（模板工程）


# 目录

- [技能要求](#技能要求)
- [代码规范](#代码规范)
- [首页模式](#首页模式)
- [全局处理](#全局处理)
- [权限控制](#权限控制)
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

## 权限
 
 本次还是采用 Shiro 来实现权限，权限可以精确到某个 Button 或者某个 HTTP 请求，只要拥有对应权限就可以访问对应的资源。每个模块权限自己维护，当项目启动的时候后，公共模块会把当前模块的权限信息以及菜单信息统一加载到内存的 serverInfo 中
 ，当进入到各自模块页面的时候会把对应的信息加载出来。
 
####	module.json
每个模块都需要创建自己的 module.json 文件，里面存放了菜单信息如下

  menu(菜单列表)：
  - order：一级菜单的顺序
  - title：一级菜单名称
  - icon：一级菜单图片
  - name：菜单Angular的路由名称
  - url：菜单Angular的路由跳转地址
  - templateUrl：Angular模板地址
  - children： 二级菜单列表
  	- order：二级菜单顺序
  	- title：二级菜单名称
  	- name：菜单Angular的路由名称
  	- url：菜单Angular的路由跳转地址
  	- templateUrl：Angular模板地址
  
  
#### 权限注解

  @F2CPermission：作用于 method，主要做了两件事，将权限加到 serverInfo 以及找到对应的菜单和验证当前请求有没有权限，属性如下
  
  - id：权限 ID(资源:操作...+资源:操作...)
      - 资源：操作 USER_ADMIN:READ 用户的查看权限
      - 资源：操作1 + 操作2  USER_ADMIN:READ+CREATE 用户的创建，前提是依赖于用户的查看权限，这是同资源的权限依赖
  - resource：资源
  - desc：操作描述
  - menu：对应的菜单 name，默认为空（权限基本上会对应到菜单）
  - roles：这是指定当前权限所对应的角色，这里的角色是系统的内置角色。顺便说下，在新建角色的时候都是依赖于系统内置角色来建。默认为 ADMIN
  - logical：当类上有 @F2CRoles 注解时才生效，就是 2 个角色列表运算结果,默认为 HIDE
      - INTERSECTION：交集
      - UNION：并集
      - DIFFERENT：差集
      - COVER：覆盖
      - HIDE：隐藏
      
  
  @F2CPermissions：作用于 method，主要声明 @F2CPermission 数组和 Logical，也会校验权限和把 @F2CPermission 数组加到 ServerInfo，属性如下：
  
  - value：F2CPermission[]
  - logical：验证权限的时候多个 F2CPermission 的关系，默认为 AND
  
  
  @F2CMenu：作用于 Class，主要是给整个类声明权限对应的菜单，就可以不用在@F2CPermission自己定义。如果在@F2CPermission里设置了值，会覆盖作用于类上@F2CMenu的菜单
  
  @F2CRoles：作用于 Class，给整个类声明权限对应的角色，@F2CPermission 里的 logical 就起作用了，使 @F2CRoles 和 @F2CPermission 里指定的角色做运算
  
 
#### 权限注意事项
  
   - @F2CPermissions 和 @F2CPermission 不能同时作用于一个方法上，最好只用 @F2CPermission。多个 @F2CPermission 会自动合并成 @F2CPermissions。
   - @RequiresPermissions 和 @F2CPermission、@F2CPermission 共存一个方法，此时 @F2CPermission、@F2CPermissions 将不再具有权限校验功能，只有申明功能
   - 当不需要将权限加到 serverInfo(已经加入)，最好直接用 Shiro 的 @RequiresPermissions 注解来校验权限
   - Java8 提供重复注解 @Repeatble。所以在方法上注解多个 @F2CPermission，会自动编译成 @F2CPermissions。注意这里多个 @F2CPermission 之间的关系是AND，如果想用OR就不能这样写。

#### 权限接口
 
 ```java
public interface F2CServerInfoConfig {

    default List<Menu> configureMenu(List<Menu> menuList) {
        return menuList;
    }


    default List<Permission> configurePermission(List<Permission> permissionList) {
        return permissionList;
    }
}
``` 
实现 F2CServerInfoConfig 接口，可以对应 Menu 和 Permission 进行操作(所有的权限和菜单，当前模块, @EventListener)  
 
#### 前端页面

  在前端页面已经写的有 angular 的指令
  
  - has-permission：有这个权限的时候显示 单个权限  
  - has-permissions：有其中一个权限的时候显示，主要是控制多个权限和去掉table的单选框、操作的列
  - lack-permission：没有这个权限的时候显示
  - lack-permissions：没有这里的所有权限的时候显示
 

## 注意事项

- Form 表单，当表单内容多于8项，或者需要二次交互（弹出窗口或内容有表格，并需要操作表格等）时不使用弹出窗口的方式显示表单，直接在主页面显示。


## 编译打包

TBD

## 本地环境运行与测试

TBD

## 实际环境部署与升级

TBD

