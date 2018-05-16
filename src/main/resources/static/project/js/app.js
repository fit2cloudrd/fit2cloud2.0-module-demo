/**
 * 启动app，加载菜单
 */

var ProjectApp = angular.module('ProjectApp', ['f2c.common']);

ProjectApp.config(function ($mdThemingProvider) {
    $mdThemingProvider.theme('default');
});

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

ProjectApp.controller('TableCtrl', function ($scope, $mdDialog, $document, HttpUtils) {
    // 全选按钮，添加到$scope.columns
    $scope.first = {
        default: true,
        sort: false,
        type: "checkbox",
        checkValue: false,
        change: function (checked) {
            $scope.items.forEach(function (item) {
                item.enable = checked;
            });
        },
        width: "40px"
    };

    $scope.showDetail = function (item) {
        $scope.detail = item;
    };

    $scope.columns = [
        $scope.first,
        {value: "姓名", key: "name", width: "30%"},
        {value: "创建日期", key: "created"},
        {value: "来源", key: "source"},
        {value: "邮箱", key: "email", sort: false},// 不想排序的列，用sort: false
        {value: "", default: true}
    ];

    $scope.items = [
        {name: 'demo1', created: '2018-05-14 10:00:00', source: 'fit2cloud', email: 'demo1@fit2cloud.com'},
        {name: 'demo2', created: '2018-05-14 10:00:00', source: 'fit2cloud', email: 'demo2@fit2cloud.com'},
        {name: 'demo3', created: '2018-05-14 10:00:00', source: 'fit2cloud', email: 'demo3@fit2cloud.com'},
        {name: 'demo4', created: '2018-05-14 10:00:00', source: 'fit2cloud', email: 'demo4@fit2cloud.com'}
    ];

    $scope.openDialog = function (event) {
        $mdDialog.show({
            templateUrl: 'web-public/test/demo/form.html',
            parent: angular.element($document.body),
            targetEvent: event,
            clickOutsideToClose: true
        }).then(function (answer) {
            $scope.status = 'You said the information was "' + answer + '".';
        }, function () {
            $scope.status = 'You cancelled the dialog.';
        });
    };

    $scope.pagination = {
        page: 1,
        total: $scope.items.length,
        limits: [10, 20, 50]
    };

    $scope.list = function (page, limit) {
        HttpUtils.get("demo/test1/result-holder", function (response) {
            console.log(response)
        });
        HttpUtils.post("demo/test2/no-result-holder", null, function (response) {
            console.log(response)
        });
        console.log(page, limit);
    };

    $scope.list();
});