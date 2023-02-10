angular.module('app').controller('userController', function ($scope, $http ,$localStorage) {
    const contextPath = 'http://localhost:8189/fitnessab';
    if ($localStorage.currentUser) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.currentUser.token;
    }

    $scope.fillTable = function () {

        $http.get(contextPath + '/api/v1/user')
            .then(function (response) {
                console.log(response.data);
                $scope.Users = response.data;
            });
    };
    $scope.fillSelectRoles = function () {

        $http.get(contextPath + '/api/v1/role')
            .then(function (response) {
                $scope.Roles = response.data;
                console.log(response.data);
            });
    };

    $scope.submitCreateNewUser = function () {
        console.log($scope.newUser);
        $http.post(contextPath + '/api/v1/user', $scope.newUser)
            .then(function (response) {
                $scope.newUser = null;
                $scope.fillTable();
            });
    };

    $scope.submitDeleteUser = function () {
        console.log(contextPath + '/api/v1/user/' + $scope.delUser.id);
        $http.delete(contextPath + '/api/v1/user/' + $scope.delUser.id)
            .then(function (response) {
                $scope.fillTable();
            });
    };

    $('#nav_header').find('li').removeClass('active');
    $('#nav_user').addClass('active');
    $scope.fillTable();
    $scope.fillSelectRoles();
});