angular.module('app').controller('indexController', function ($scope, $http, $localStorage) {
  const contextPath = 'http://localhost:8189/fitnessab';
  $scope.checkAuth = function () {
    console.log("whoami");
    $http.get(`${contextPath}/whoami`)
        .then(function successCallback(response) {}
        , function errorCallback(response) {
          $scope.tryToLogout();
        });
  };
  if ($localStorage.currentUser) {
    $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.currentUser.token;
    $scope.authUser = $localStorage.currentUser;
    $scope.checkAuth();
  }

  $scope.tryToAuth = function () {
    console.log("tryToAuth");
    $http.post(contextPath + '/auth', $scope.user)
        .then(function (response) {
          if (response.data.token) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
            $localStorage.currentUser = {username: $scope.user.username, token: response.data.token};
            $scope.authUser = $localStorage.currentUser;
          } else {
            delete $scope.authUser;
          }
          window.location.reload();
        });
  };


  $scope.tryToLogout = function () {
    console.log("tryToLogout");
    delete $localStorage.currentUser;
    $http.defaults.headers.common.Authorization = '';
    delete $scope.authUser;
    window.location.reload();
  };
});