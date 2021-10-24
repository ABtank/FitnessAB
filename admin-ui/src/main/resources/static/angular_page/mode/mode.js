angular.module('app').controller('modeController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/fitnessab';

    $scope.fillTable = function () {

        $http.get(contextPath + '/api/v1/mode')
            .then(function (response) {
                $scope.Modes = response.data;
                console.log(response.data);
            });
    };

    $scope.submitCreateNewMode = function () {
        console.log($scope.newMode);
        $http.post(contextPath + '/api/v1/mode', $scope.newMode)
            .then(function (response) {
                $scope.newMode = null;
                $scope.fillTable();
            });
    };
    $scope.submitDeleteMode = function () {
        console.log(contextPath + '/api/v1/mode/' + $scope.delMode.id);
        $http.delete(contextPath + '/api/v1/mode/' + $scope.delMode.id)
            .then(function (response) {
                $scope.fillTable();
            });
    };

    $('#nav_header').find('li').removeClass('active');
    $('#nav_mode').addClass('active');
    $scope.fillTable();
});