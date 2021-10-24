angular.module('app').controller('exerciseController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/fitnessab';

    $scope.fillTable = function (pageIndex = 1) {
        console.log("pageIndex=" + pageIndex);
        $http({
            url: contextPath + '/api/v1/exercise',
            method: 'GET',
            params: {
                name_filter: $scope.filter ? $scope.filter.name_filter : null,
                page: pageIndex
            }
        })
            .then(function (response) {
                $scope.Exercises = response.data;
                const previous = ($scope.Exercises.number < 1) ? 1 : $scope.Exercises.number;
                const next = ($scope.Exercises.number + 2 > $scope.Exercises.totalPages) ? $scope.Exercises.totalPages : $scope.Exercises.number + 2;
                $scope.PaginationArray = generatePageIndex(previous, next);
                console.log(response.data);
                console.log($scope.PaginationArray);
            });
    };

    $scope.clearFilter = function () {
        $scope.filter = null;
        $scope.fillTable();
    };

    function generatePageIndex(startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        }
        return arr;
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
        // $scope.newExercise.type = JSON.parse($scope.newExercise.type);
        // $scope.newExercise.category = JSON.parse($scope.newExercise.category);
        $scope.newExercise['creator'] = {"id": 1};
        console.log($scope.newExercise);
        $http.post(contextPath + '/api/v1/exercise', $scope.newExercise)
            .then(function (response) {
                $scope.newExercise = null;
                $scope.fillTable();
            });
    };

    $scope.submitDeleteExercise = function () {
        console.log(contextPath + '/api/v1/exercise/' + $scope.delExercise.id);
        $http.delete(contextPath + '/api/v1/exercise/' + $scope.delExercise.id)
            .then(function (response) {
                $scope.fillTable();
            });
    };

    $scope.deleteExercise = function (id){
        $http.delete(contextPath + '/api/v1/exercise/' + id)
            .then(function (response) {
                $scope.fillTable();
            });
    }

    $('#nav_header').find('li').removeClass('active');
    $('#nav_exercise').addClass('active');
    $scope.fillTable();
    $scope.fillSelectTypes();
    $scope.fillSelectCategories();
});