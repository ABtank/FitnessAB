(function () {
    'use strict';

    angular
        .module('app', ['ngRoute', 'ngStorage'])
        .config(config)
        .run();

    function config($routeProvider, $httpProvider) {
        $routeProvider
            .when('/', { //    http://localhost:8189/fitnessab/index.html#!/
                templateUrl: 'angular_page/main/main.html',
                controller: 'mController'
            })
            .when('/role', { //    http://localhost:8189/fitnessab/index.html#!/role
                templateUrl: 'angular_page/role/role.html',
                controller: 'roleController'
            })
            .when('/exercise', { //    http://localhost:8189/fitnessab/index.html#!/exercise
                templateUrl: 'angular_page/exercise/exercise.html',
                controller: 'exerciseController'
            })
            .when('/mode', { //    http://localhost:8189/fitnessab/index.html#!/mode
                templateUrl: 'angular_page/mode/mode.html',
                controller: 'modeController'
            })
            .when('/type', { //    http://localhost:8189/fitnessab/index.html#!/type
                templateUrl: 'angular_page/type/type.html',
                controller: 'typeController'
            })
            .when('/category', { //    http://localhost:8189/fitnessab/index.html#!/category
                templateUrl: 'angular_page/category/category.html',
                controller: 'categoryController'
            })
            .when('/workout', { //    http://localhost:8189/fitnessab/index.html#!/workout
                templateUrl: 'angular_page/workout/workout.html',
                controller: 'workoutController'
            })
            .when('/round', { //    http://localhost:8189/fitnessab/index.html#!/round
                templateUrl: 'angular_page/round/round.html',
                controller: 'roundController'
            })
            .when('/user', {
                templateUrl: 'angular_page/user/user.html',
                controller: 'userController'
            });
    }
})();
