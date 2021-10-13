angular.module('app').controller('roleController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/fitnessab';

    $scope.fillTable = function () {

        $http.get(contextPath + '/api/v1/role')
            .then(function (response) {
                $scope.Roles = response.data;
                console.log(response.data);
            });
    };

    $scope.submitCreateNewRole = function () {
        console.log($scope.newRole);
        $http.post(contextPath + '/api/v1/role', $scope.newRole)
            .then(function (response) {
                $scope.newRole = null;
                $scope.fillTable();
            });
    };
    $scope.submitDeleteRole = function () {
        console.log(contextPath + '/api/v1/role/'+ $scope.delRole.id);
        $http.delete(contextPath + '/api/v1/role/'+ $scope.delRole.id)
            .then(function (response) {
                $scope.fillTable();
            });
    };

    $scope.fillTable();
});