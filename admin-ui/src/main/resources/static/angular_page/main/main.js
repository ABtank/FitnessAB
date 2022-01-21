angular.module('app').controller('mController', function ($scope, $http ,$localStorage) {
    const contextPath = 'http://localhost:8189/fitnessab';
    if ($localStorage.currentUser) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.currentUser.token;
    }

    $('#nav_header').find('li').removeClass('active');
});