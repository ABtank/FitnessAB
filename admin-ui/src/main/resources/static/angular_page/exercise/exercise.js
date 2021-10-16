angular.module('app').controller('exerciseController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/fitnessab';

    $scope.fillTable = function () {

        $http.get(contextPath + '/api/v1/exercise')
            .then(function (response) {
                $scope.Exercises = response.data;
                console.log(response.data);
            });
    };
    $scope.fillSelectTypes = function () {

        $http.get(contextPath + '/api/v1/type')
            .then(function (response) {
                $scope.Types = response.data;
                console.log(response.data);
            });
    };
    $scope.fillSelectCategories = function () {

        $http.get(contextPath + '/api/v1/category')
            .then(function (response) {
                $scope.Categories = response.data;
                console.log(response.data);
            });
    };

    $scope.submitCreateNewExercise = function () {
        console.log($scope.newExercise);
        $scope.newExercise.type={"id":$scope.newExercise.type};
        $scope.newExercise.category={"id":$scope.newExercise.category};
        $scope.newExercise['creator']={"id":1};
        console.log($scope.newExercise);
        $http.post(contextPath + '/api/v1/exercise', $scope.newExercise)
            .then(function (response) {
                $scope.newExercise = null;
                $scope.fillTable();
            });
    };

    $scope.submitDeleteExercise = function () {
        console.log(contextPath + '/api/v1/exercise/'+ $scope.delExercise.id);
        $http.delete(contextPath + '/api/v1/exercise/'+ $scope.delExercise.id)
            .then(function (response) {
                $scope.fillTable();
            });
    };

    $scope.fillTable();
    $scope.fillSelectTypes();
    $scope.fillSelectCategories();
});