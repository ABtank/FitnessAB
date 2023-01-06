angular.module('app').controller('roundController', function ($scope, $http ,$localStorage) {
    const contextPath = 'http://localhost:8189/fitnessab';
    if ($localStorage.currentUser) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.currentUser.token;
    }

    $scope.fillTable = function () {

        $http.get(contextPath + '/api/v1/round')
            .then(function (response) {
                $scope.Rounds = response.data;
                console.log(response.data);
            });
    };


    $('#nav_header').find('li').removeClass('active');
    $('#nav_round').addClass('active');
    $scope.fillTable();
});