angular.module('app').controller('typeController', function ($scope, $http ,$localStorage) {
    const contextPath = 'http://localhost:8189/fitnessab';
    if ($localStorage.currentUser) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.currentUser.token;
    }

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

    $scope.submitUpdateType = function () {
        console.log($scope.updType);
        $http.put(contextPath + '/api/v1/type', $scope.updType)
            .then(function (response) {
                $scope.updType = null;
                $("#updId").val(null);
                $scope.fillTable();
            });
    };

    $scope.setUpdType = function (id) {
        $("#updId").val(id);
        $scope.fillFormUpdate();
    }

    $scope.fillFormUpdate = function () {
        $http.get(contextPath + '/api/v1/type/' + $("#updId").val())
            .then(function (response) {
                $scope.updType = response.data;
                console.log($scope.updType);
            });
    }

    $scope.submitDeleteType = function () {
        console.log(contextPath + '/api/v1/type/' + $scope.delType.id);
        $scope.deleteType($scope.delType.id);
    };

    $scope.deleteType = function (id) {
        $http.delete(contextPath + '/api/v1/type/' + id)
            .then(function (response) {
                $scope.fillTable();
            });
    }

    $('#nav_header').find('li').removeClass('active');
    $('#nav_type').addClass('active');
    $scope.fillTable();
});