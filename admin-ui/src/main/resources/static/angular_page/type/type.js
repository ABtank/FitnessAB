angular.module('app').controller('typeController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/fitnessab';

    $scope.fillTable = function () {

        $http.get(contextPath + '/api/v1/type')
            .then(function (response) {
                $scope.Types = response.data;
                console.log(response.data);
            });
    };

    $scope.submitCreateNewType = function () {
        console.log($scope.newType);
        $http.post(contextPath + '/api/v1/type', $scope.newType)
            .then(function (response) {
                $scope.newType = null;
                $scope.fillTable();
            });
    };
    $scope.submitDeleteType = function () {
        console.log(contextPath + '/api/v1/type/'+ $scope.delType.id);
        $http.delete(contextPath + '/api/v1/type/'+ $scope.delType.id)
            .then(function (response) {
                $scope.fillTable();
            });
    };

    $('#nav_header').find('li').removeClass('active');
    $('#nav_type').addClass('active');
    $scope.fillTable();
});