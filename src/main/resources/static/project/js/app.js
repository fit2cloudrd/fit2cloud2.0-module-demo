/**
 * 启动app，加载菜单
 */

var ProjectApp = angular.module('ProjectApp', ['f2c.common']);

// 测试专用
var MENUS_TEST = {
    title: "自服务",
    icon: "home",
    menus: [
        {
            title: "仪表盘",
            icon: "dashboard",
            name: "dashboard",
            url: "/dashboard",
            templateUrl: "project/html/demo/dashboard.html" + '?_t=' + window.appversion
        }, {
            title: "示例1",
            icon: "assignment",
            children: [
                {
                    title: "表格",
                    name: "table",
                    url: "/table",
                    templateUrl: "project/html/demo/table.html" + '?_t=' + window.appversion
                }, {
                    title: "表单",
                    name: "form",
                    url: "form",
                    templateUrl: "project/html/demo/form.html" + '?_t=' + window.appversion
                }
            ]
        }, {
            title: "示例2",
            icon: "assignment",
            children: [
                {
                    title: "Loading",
                    name: "loading",
                    url: "/loading",
                    templateUrl: "project/html/demo/loading.html" + '?_t=' + window.appversion
                }, {
                    title: "other",
                    name: "other",
                    url: "/other",
                    templateUrl: "project/html/demo/other.html" + '?_t=' + window.appversion
                }
            ]
        }
    ]
};

ProjectApp.controller('DemoCtrl', function ($scope) {
    $scope.module = MENUS_TEST;
});