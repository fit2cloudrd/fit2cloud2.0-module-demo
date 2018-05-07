/**
 * 启动app，加载菜单
 */

var ProjectApp = angular.module('ProjectApp', ['f2c.common']);
ProjectApp.controller('DemoController', function () {

});

ProjectApp.config(function ($stateProvider) {
    $stateProvider.state({
            name: 'demo',
            url: '/demo',
            templateUrl: 'project/html/demo.html'
        }
    );
});