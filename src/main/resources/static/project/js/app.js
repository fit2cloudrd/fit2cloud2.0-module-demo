/**
 * 启动app，加载菜单
 */

// 流程管理使用方法：1、加载process-design.css和process-design.js，加载f2c.process，配置module.json
var ProjectApp = angular.module('ProjectApp', ['f2c.common', 'f2c.process']);

// 测试专用
var MENUS_TEST = {
    name: "自服务",
    icon: "shopping_cart",
    menus: [
        {
            title: "仪表盘",
            icon: "dashboard",
            name: "dashboard",
            url: "/dashboard",
            templateUrl: "project/html/demo/dashboard.html" + '?_t=' + window.appversion
        },
        {
            title: "示例1",
            icon: "assignment",
            children: [
                {
                    title: "表格",
                    name: "table",
                    url: "/table",
                    templateUrl: "project/html/demo/table.html" + '?_t=' + window.appversion
                }, {
                    title: "监控",
                    name: "metric",
                    url: "/metric",
                    templateUrl: "project/html/demo/metric.html" + '?_t=' + window.appversion
                }, {
                    title: "Stepper",
                    name: "stepper",
                    url: "/stepper",
                    templateUrl: "project/html/demo/stepper.html" + '?_t=' + window.appversion
                }, {
                    title: "按钮",
                    name: "button",
                    url: "/button",
                    templateUrl: "project/html/demo/buttons.html" + '?_t=' + window.appversion
                }, {
                    title: "树",
                    name: "tree",
                    url: "/tree",
                    templateUrl: "project/html/demo/tree.html" + '?_t=' + window.appversion
                }, {
                    title: "Notification",
                    name: "notice",
                    url: "/notice",
                    templateUrl: "project/html/demo/notification.html" + '?_t=' + window.appversion
                }, {
                    title: "选择添加",
                    name: "choose",
                    url: "/choose",
                    templateUrl: "project/html/demo/choose.html" + '?_t=' + window.appversion
                }, {
                    title: "拖拽",
                    name: "drag",
                    url: "/drag",
                    templateUrl: "project/html/demo/drag.html" + '?_t=' + window.appversion
                }, {
                    title: "文件",
                    name: "file",
                    url: "/file",
                    templateUrl: "project/html/demo/file.html" + '?_t=' + window.appversion
                }
            ]
        },
        {
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

ProjectApp.controller('TableCtrl', function ($scope, $mdDialog, $mdBottomSheet, FilterSearch, Notification, HttpUtils, Loading) {

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
        {
            key: "ajax",
            name: "异步字典",
            directive: "filter-select-ajax",
            url: "demo/status",
            convert: {value: "id", label: "name"}
        }

    ];

    // 用于传入后台的参数
    $scope.filters = [
        // 设置默认条件default:true(默认条件不会被删掉)，
        {key: "status", name: "主机状态", value: "Running", label: "运行中", default: true, operator: "="},
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
        // 点击2次关闭
        if ($scope.selected === item.$$hashKey) {
            $scope.closeInformation();
            return;
        }
        $scope.selected = item.$$hashKey;
        $scope.detail = item;
        $scope.showInformation();
    };

    $scope.closeInformation = function () {
        $scope.selected = "";
        $scope.toggleInfoForm(false);
    };

    $scope.showInformation = function () {
        $scope.infoUrl = 'project/html/demo/information.html' + '?_t=' + window.appversion;
        $scope.toggleInfoForm(true);
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
        {name: 'demo1', created: '2018-05-14', source: 'fit2cloud', email: 'demo1@fit2cloud.com'},
        {name: 'demo2', created: '2018-05-14', source: 'fit2cloud', email: 'demo2@fit2cloud.com'},
        {name: 'demo3', created: '2018-05-14', source: 'fit2cloud', email: 'demo3@fit2cloud.com'},
        {name: 'demo4', created: '2018-05-14', source: 'fit2cloud', email: 'demo4@fit2cloud.com'}
    ];

    $scope.create = function () {
        // $scope.formUrl用于side-form
        $scope.formUrl = 'project/html/demo/form.html' + '?_t=' + window.appversion;
        // toggleForm由side-form指令生成
        $scope.toggleForm();
    };

    $scope.save = function () {
        Notification.show("保存成功", function () {
            $scope.toggleForm();
        });
    };

    $scope.edit = function (item) {
        $scope.item = item;
        $scope.formUrl = 'project/html/demo/form.html' + '?_t=' + window.appversion;
        $scope.toggleForm();
    };

    $scope.openDialog = function (item, event) {
        $scope.item = item;
        $mdDialog.show({
            templateUrl: 'project/html/demo/dialog-form.html',
            parent: angular.element(document.body),
            scope: $scope,
            preserveScope: true,
            targetEvent: event,
            clickOutsideToClose: false
        }).then(function (answer) {
            $scope.status = 'You said the information was "' + answer + '".';
        }, function () {
            $scope.status = 'You cancelled the dialog.';
        });
    };

    $scope.closeDialog = function () {
        $mdDialog.cancel();
    };

    $scope.ok = function () {
        console.log("ok");
        $scope.closeDialog();
    };

    $scope.pagination = {
        page: 1,
        total: $scope.items.length,
        limits: [10, 20, 50]
    };

    $scope.list = function (sortObj) {
        var condition = FilterSearch.convert($scope.filters);
        if (sortObj) {
            $scope.sort = sortObj;
        }
        // 保留排序条件，用于分页
        if ($scope.sort) {
            condition.sort = $scope.sort.sql;
        }

        Loading.add(HttpUtils.get("demo/test1/5000", function (response) {
            console.log(response);
        }));
        Loading.add(HttpUtils.get("demo/test1/1000", function (response) {
            console.log(response);
        }));
        // 多个查询用这种方式
        $scope.loadingLayer = Loading.load();

        // 单个查询跟以前一样
        $scope.loadingLayer2 = HttpUtils.get("demo/test1/5000", function (response) {
            console.log(response);
        })
    };

    // 分页DEMO，有特殊需求可以自定义$scope.pagination，可以只定义一项，比如page:1,其他不写也可以
    // OK
    // $scope.pagination = {
    //     page: 1,
    //     limit: 10,
    //     limitOptions: [10, 20, 50, 100]
    // };
    // OK
    // $scope.pagination = {
    //     page: 2
    // };
    HttpUtils.paging($scope, "demo/list", {});
    // 需要callback就加上
    HttpUtils.paging($scope, "demo/list", {}, function (response) {
        console.log("callback function", $scope.pagination, response);
    });

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
    }

});

ProjectApp.controller('MetricController', function ($scope) {
    var now = new Date().getTime();

    $scope.request = {
        startTime: now - 240 * 3600 * 1000,
        endTime: now,
        metricDataQueries: [
            {
                resourceId: '6d8f69b3-0355-4276-a624-4f57af9d0d85',
                resourceName: 'test',
                metricSource: "API",
                metric: 'SERVER_CPU_USAGE'
            }
        ]
    }
});

ProjectApp.controller('WizardController', function ($scope, HttpUtils, Notification) {
    // 可用方法$scope.wizard.isLast()，$scope.wizard.isFirst()，$scope.wizard.isSelected()，$scope.wizard.continue()
    $scope.wizard = {
        setting: {
            title: "标题",
            subtitle: "子标题",
            closeText: "取消",
            submitText: "保存",
            nextText: "下一步",
            prevText: "上一步",
            buttons: [  // 去掉buttons，则显示submit按钮
                {
                    text: "自定义按钮",
                    class: "md-raised md-accent md-hue-2",
                    click: function () {
                        Notification.info("自定义按钮 click");
                    },
                    show: function () {
                        return $scope.wizard.isLast() || $scope.wizard.current === 2;
                    },
                    disabled: function () {
                        return $scope.wizard.current === 2;
                    }
                }
            ]
        },
        // 按顺序显示,id必须唯一并需要与页面中的id一致，select为分步初始化方法，next为下一步方法(最后一步时作为提交方法)
        steps: [
            {
                id: "1",
                name: "云帐号",
                select: function () {
                    console.log("第一步select")
                }
            }, {
                id: "2",
                name: "基础设置",
                select: function () {
                    console.log("第二步select")
                },
                next: function () {
                    console.log("第二步Next");
                    // 返回true则自动下一步
                    return true;
                }
            }, {
                id: "3",
                name: "异步验证",
                select: function () {
                    console.log("第三步select")
                },
                next: function () {
                    $scope.loadingLayer = HttpUtils.get("demo/test1/2000", function (response) {
                        console.log("第三步异步验证通过,验证时间：" + response.data);
                        $scope.wizard.continue();
                    });
                    // 返回false，则不会自动进行下一步
                    return false;
                }
            }, {
                id: "4",
                name: "权限设置",
                select: function () {
                    console.log("第四步select");
                },
                next: function () {
                    Notification.confirm("确定保存？", function () {
                        Notification.info("确定保存");
                    }, function () {
                        Notification.info("取消");
                    })
                }
            }
        ],
        // 嵌入页面需要指定关闭方法
        close: function () {
            $scope.show = false;
        }
    };

    $scope.pass = false;
    $scope.check = function () {
        $scope.pass = !$scope.pass;
    };

    $scope.show = true;

    $scope.open = function () {
        $scope.show = true;
    }
});

ProjectApp.controller('TreeController', function ($scope) {
    $scope.option = {
        select: "folder" //file, folder, all
    };
    $scope.node = {
        name: "一级",
        collapsed: false,
        children: [
            {
                name: "二级-1",
                children: [
                    {
                        name: "三级-1"
                    }, {
                        name: "三级-1"
                    }, {
                        name: "三级-1"
                    }, {
                        name: "三级-1"
                    }, {
                        name: "三级-1"
                    }, {
                        name: "三级-1"
                    }, {
                        name: "三级-2"
                    }
                ]
            }, {
                name: "二级-2"
            }, {
                name: "二级-3",
                children: [
                    {
                        name: "三级-3"
                    }, {
                        name: "三级-4",
                        children: [
                            {
                                name: "四级-1"
                            }, {
                                name: "四级-2"
                            }
                        ]
                    }
                ]
            }
        ]
    };

    // 也可以用数组
    $scope.nodes = [
        {
            name: "一级-1",
            children: [
                {
                    name: "二级-1"
                }, {
                    name: "二级-2"
                }
            ]
        }, {
            name: "一级-2",
            children: []
        }, {
            name: "一级-3",
            children: [
                {
                    name: "二级-3"
                }, {
                    name: "二级-4"
                }
            ]
        }
    ];

    // 自动生成$scope.api.getSelected();
    $scope.radio = {
        selected: "",
        onChange: function (node) {
            console.log(node)
        }
    };

    // 自动生成$scope.api.getSelected();
    $scope.root = {
        onChange: function (node) {
            if (node.name === "三级-3") {
                let levelTwo = $scope.root.getNode("name", "二级-1");
                if (node.checked) {
                    $scope.root.toggle(levelTwo, true);
                    $scope.root.disable(levelTwo, true);
                } else {
                    $scope.root.toggle(levelTwo, false);
                    $scope.root.disable(levelTwo, false);
                }
            }
        }
    };

    $scope.noroot = {};

    $scope.getSelected = function () {
        console.log("带root", JSON.stringify($scope.root.getSelected(), 4));
        console.log("不带root", JSON.stringify($scope.noroot.getSelected(), 4));
    }

});

ProjectApp.controller('NotificationCtrl', function ($scope, Notification, $mdDialog, $http) {

    $scope.open = function () {
        $mdDialog.show({
            templateUrl: 'project/html/switch-source.html',
            parent: angular.element(document.body),
            scope: $scope,
            preserveScope: true,
            targetEvent: event,
            clickOutsideToClose: false
        });
    };


    $scope.count = 1;
    $scope.show = function () {
        var msg = "消息通知" + $scope.count++;
        Notification.show(msg);
    };

    $scope.info = function () {
        var msg = "消息通知" + $scope.count++;
        Notification.info(msg);
    };

    $scope.success = function () {
        var msg = "消息通知" + $scope.count++;
        Notification.success(msg);
    };

    $scope.warn = function () {
        var msg = "消息通知" + $scope.count++;
        Notification.warn(msg);
    };

    $scope.danger = function () {
        var msg = "消息通知" + $scope.count++;
        Notification.danger(msg);
    };
});

ProjectApp.controller('ChooseCtrl', function ($scope, HttpUtils) {
    $scope.items = [
        {id: 1, name: "长长长长长长长长长长长长长长长长长长长长长长长长长长长看不见"},
        {id: 2, name: "222"},
        {id: 3, name: "333"},
        {id: 4, name: "444"},
        {id: 5, name: "555"},
        {id: 6, name: "666"},
        {id: 7, name: "777"},
        {id: 8, name: "888"}
    ];

    $scope.loadingLayer = HttpUtils.get("demo/test1/2000", function () {
        $scope.selected = [3, 4];
        $scope.done = true;
    });

    $scope.selected2 = [
        {id: 1, name: "长长长长长长长长长长长长长长长长长长长长长长长长长长长看不见"},
        {id: 4, name: "444"}
    ];

    $scope.paramList = [
        {
            id: "1",
            name: "测试1"
        },
        {
            id: "2",
            name: "测试2"
        }, {
            id: "3",
            name: "测试3"
        }, {
            id: "4",
            name: "测试4"
        }
    ];

    $scope.item1 = {};
    $scope.item2 = {};
    $scope.item3 = {};

    $scope.itemList = [];
    $scope.itemList.push($scope.item1);
    $scope.itemList.push($scope.item2);
    $scope.itemList.push($scope.item3);

});

ProjectApp.controller('DragCtrl', function ($scope) {
    $scope.items = [
        {id: 1, name: "111"},
        {id: 2, name: "222"},
        {id: 3, name: "333"},
        {id: 4, name: "444"},
        {id: 5, name: "555"},
        {id: 6, name: "666"},
        {id: 7, name: "777"},
        {id: 8, name: "888"}
    ];

    $scope.items2 = [
        {id: 1, name: "111"},
        {id: 2, name: "222"},
        {id: 3, name: "333"},
        {id: 4, name: "444"},
        {id: 5, name: "555"},
        {id: 6, name: "666"},
        {id: 7, name: "777"},
        {id: 8, name: "888"}
    ];
});

ProjectApp.filter("dbFilter", function () {
    return function (collection, items, item) {
        console.log(collection);
        console.log(items);
        let output = [];
        angular.forEach(collection, function (c) {
            let flag = true;
            angular.forEach(items, function (it) {
                if (it.id && it.id.indexOf(c.id) != -1 && !(item.id && item.id.indexOf(c.id) != -1)) {
                    flag = false;
                }
            });
            if (flag) {
                output.push(c);
            }
        });

        return output;

    }
});