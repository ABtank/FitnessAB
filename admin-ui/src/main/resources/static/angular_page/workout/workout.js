angular.module('app').controller('workoutController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/fitnessab';

    $scope.fillTable = function () {

        $http.get(contextPath + '/api/v1/workout')
            .then(function (response) {
                $scope.Workouts = response.data;
                console.log(response.data);
            });
    };

    $scope.submitCreateNewWorkout = function () {
        console.log($scope.newWorkout);
        $scope.newWorkout['creator'] = {"id": 1};
        $http.post(contextPath + '/api/v1/workout', $scope.newWorkout)
            .then(function (response) {
                $scope.newWorkout = null;
                $scope.fillTable();
            });
    };
    $scope.submitUpdateWorkout = function () {
        console.log($scope.updWorkout);
        $http.put(contextPath + '/api/v1/workout', $scope.updWorkout)
            .then(function (response) {
                $scope.updWorkout = null;
                $("#updId").val(null);
                $scope.fillTable();
            });
    };

    $scope.setUpdWorkout = function (id) {
        $("#updId").val(id);
        $scope.fillFormUpdate();
    }

    $scope.fillFormUpdate = function () {
        $http.get(contextPath + '/api/v1/workout/' + $("#updId").val())
            .then(function (response) {
                $scope.updWorkout = response.data;
                console.log($scope.updWorkout);
            });
    }

    $scope.submitDeleteWorkout = function () {
        console.log(contextPath + '/api/v1/workout/' + $scope.delWorkout.id);
        $scope.deleteWorkout($scope.delWorkout.id);
    };

    $scope.deleteWorkout = function (id) {
        $http.delete(contextPath + '/api/v1/workout/' + id)
            .then(function (response) {
                $scope.fillTable();
            });
    }

    $('#nav_header').find('li').removeClass('active');
    $('#nav_workout').addClass('active');
    $scope.fillTable();
});