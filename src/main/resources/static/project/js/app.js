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

ProjectApp.controller('TableCtrl', function ($scope, $mdDialog, HttpUtils, FilterSearch) {
    // 定义搜索条件
    $scope.conditions = [
        {
            key: "priority",
            name: "优先级[有查询，可多选]",
            directive: "filter-select-multiple", // 使用哪个指令
            selects: [
                {value: 1, label: "选项1"},
                {value: 2, label: "选项2"},
                {value: 3, label: "选项3"},
                {value: 6, label: "其他"}
            ],
            // 测试select类型条件的搜索框
            search: true
        }, {
            key: "priority",
            name: "优先级[有查询]",
            directive: "filter-select", // 使用哪个指令
            selects: [
                {value: 1, label: "选项1"},
                {value: 2, label: "选项2"},
                {value: 6, label: "其他"}
            ],
            // 测试select类型条件的搜索框
            search: true
        }, {
            key: "priority",
            name: "优先级[无查询]",
            directive: "filter-select",
            selects: [
                {value: 1, label: "选项1"},
                {value: 2, label: "选项2"},
                {value: 6, label: "其他"}
            ]
        },
        {key: "no", name: "工单编号", directive: "filter-input"},
        //查询虚机的条件
        {key: "instanceName", name: "实例名", directive: "filter-contains"},
        {key: "created", name: "创建日期", directive: "filter-date", directiveUnit: "second"},//directiveUnit: "second"返回时间戳为秒
        {key: "os", name: "操作系统", directive: "filter-contains"},
        {key: "localIp", name: "内网IP", directive: "filter-contains"},
        //增加一个异步字典转换的例子，将请求内容转换为value,label格式
        {key: "ajax", name: "异步字典", directive: "filter-select-ajax", url: "demo/status", convert: {value: "id", label: "name"}}

    ];

    // 用于传入后台的参数
    $scope.filters = [
        // 设置默认条件default:true(默认条件不会被删掉)，
        {key: "status", name: "主机状态", value: "Running", default: true, operator: "="},
        {key: "status", name: "主机状态", value: "Running", default: true, operator: "="},
        {key: "status", name: "主机状态", value: "Running"},
        {key: "status", name: "主机状态", value: "Running"},
        {key: "status", name: "主机状态", value: "Running"},
        {key: "status", name: "主机状态", value: "Running"},
        {key: "status", name: "主机状态", value: "Running"},
        {key: "status", name: "主机状态", value: "Running"},
        {key: "status", name: "主机状态", value: "Running", operator: "="},
        // 可以设置是否显示(display:false不显示，不加display或者display:true则显示)
        {key: "status", name: "主机状态", value: "Running", default: true, display: false}
    ];

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

    $scope.help = function () {
        $scope.msg = "Bottom Sheep Demo";
        $mdBottomSheet.show({
            templateUrl: 'project/html/demo/bottom-sheet.html',
            scope: $scope,
            preserveScope: true
        }).then(function (clickedItem) {
            $scope.msg = clickedItem['name'] + ' clicked!';
        }).catch(function (error) {
            console.log(error)
            // User clicked outside or hit escape
        });
    };

    $scope.openDialog = function (event) {
        $mdDialog.show({
            templateUrl: 'project/html/demo/form.html',
            parent: angular.element(document.body),
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

    $scope.list = function (sortObj, page, limit) {
        var condition = FilterSearch.convert($scope.filters);
        if (sortObj) {
            $scope.sort = sortObj;
        }
        // 保留排序条件，用于分页
        if ($scope.sort) {
            condition.sort = $scope.sort.sql;
        }
        console.log(condition);
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