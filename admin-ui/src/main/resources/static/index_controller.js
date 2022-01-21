angular.module('app').controller('indexController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8189/fitnessab';
    if ($localStorage.currentUser) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.currentUser.token;
    }

        $scope.tryToAuth = function () {
            console.log("tryToAuth");
            $http.post(contextPath + '/auth', $scope.user)
                .then(function (response) {
                    if (response.data.token) {
                        $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                        $localStorage.currentUser = { username: $scope.user.username, token: response.data.token };
                    }
                });
        };

        $scope.tryToLogout = function () {
            console.log("tryToLogout");
            delete $localStorage.currentUser;
            $http.defaults.headers.common.Authorization = '';
        };
});