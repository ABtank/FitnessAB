angular.module('app').controller('categoryController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/fitnessab';

    $scope.fillTable = function () {

        $http.get(contextPath + '/api/v1/category')
            .then(function (response) {
                $scope.Categories = response.data;
                console.log(response.data);
            });
    };

    $scope.submitCreateNewCategory = function () {
        console.log($scope.newCategory);
        $scope.newCategory['creatorLogin'] = "abtank";
        $http.post(contextPath + '/api/v1/category', $scope.newCategory)
            .then(function (response) {
                $scope.newCategory = null;
                $scope.fillTable();
            });
    };
    $scope.submitDeleteCategory = function () {
        console.log(contextPath + '/api/v1/category/' + $scope.delCategory.id);
        $scope.deleteCategory($scope.delCategory.id);
    };

    $scope.deleteCategory = function (id) {
        $http.delete(contextPath + '/api/v1/category/' + id)
            .then(function (response) {
                $scope.fillTable();
            });
    }

    $('#nav_header').find('li').removeClass('active');
    $('#nav_category').addClass('active');
    $scope.fillTable();
});