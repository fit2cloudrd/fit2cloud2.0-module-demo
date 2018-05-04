/**
 * FIT2CLOUD Menu Frame
 */

var MenuApp = angular.module('MenuApp', ['ngMaterial', 'ngMessages']);

MenuApp.controller('MenuCtrl', function ($scope, $mdSidenav) {
    $scope.toggleMenu = buildToggler('menu');

    function buildToggler(navID) {
        return function () {
            $mdSidenav(navID).toggle();
        };
    }

    $scope.getModules = function () {
        // 测试
        $scope.modules = [
            {
                divider: true,
                menus: [
                    {
                        title: "首页",
                        url: "http://192.168.1.108/",
                        icon: "home"
                    }
                ]
            }, {
                title: "自服务",
                menus: [
                    {
                        title: "订单",
                        url: "http://192.168.1.108/self-service/#/order/list",
                        icon: "menu"
                    },{
                        title: "资源池",
                        url: "http://192.168.1.108/self-service/#/resourcePool",
                        icon: "menu"
                    }
                ]
            }
        ];
    };

    $scope.getModules();
});


MenuApp.component('moduleMenu', {
    templateUrl: 'library/fit2cloud/html/module-menu.html' + '?_t=' + window.appversion,
    bindings: {
        module: '<'
    },
    controller: function ModuleMenuController() {
        var ctrl = this;
        var frame = $("#frame");

        ctrl.navigateTo = function (module) {
            frame.attr("src", module.url);
        }
    }
});
