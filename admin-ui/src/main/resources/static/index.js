(function () {
    'use strict';

    angular
        .module('app', ['ngRoute', 'ngStorage'])
        .config(config)
        .run();

    function config($routeProvider, $httpProvider) {
        $routeProvider
            .when('/', { //    http://localhost:8189/fitnessab/index.html#!/
                templateUrl: 'main/main.html'
            })

            .when('/role', { //    http://localhost:8189/fitnessab/index.html#!/role
                templateUrl: 'role/role.html',
                controller: 'roleController'
            })
            .when('/user', {
                templateUrl: 'user/user.html',
                controller: 'userController'
            });
    }
})();
