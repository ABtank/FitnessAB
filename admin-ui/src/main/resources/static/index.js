(function () {
    'use strict';

    angular
        .module('app', ['ngRoute', 'ngStorage'])
        .config(config)
        .run();

    function config($routeProvider, $httpProvider) {
        $routeProvider
            .when('/', { //    http://localhost:8189/fitnessab/index.html#!/
                templateUrl: 'angular_page/main/main.html'
            })

            .when('/role', { //    http://localhost:8189/fitnessab/index.html#!/role
                templateUrl: 'angular_page/role/role.html',
                controller: 'roleController'
            })
            .when('/user', {
                templateUrl: 'angular_page/user/user.html',
                controller: 'userController'
            });
    }
})();
