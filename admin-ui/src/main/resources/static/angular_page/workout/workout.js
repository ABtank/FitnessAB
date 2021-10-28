angular.module('app').controller('workoutController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/fitnessab';
// <Table
    $scope.fillTable = function () {
        $http.get(contextPath + '/api/v1/workout')
            .then(function (response) {
                $scope.Workouts = response.data;
                console.log(response.data);
            });
    };

    $scope.fillWorkoutExerciseTable = function () {
        let data= {
            workoutId: $scope.updWorkout.id ? $scope.updWorkout.id : null
        };
        console.log(data);
        $http({
            url: contextPath + '/api/v1/workout_exercise',
            method: 'GET',
            params: data
        }).then(function (response) {
                $scope.WorkoutExercises = response.data;
                console.log(response.data);
            });
    };

    $scope.fillExerciseTable = function (pageIndex = 1) {
        console.log("pageIndex=" + pageIndex);
        $http({
            url: contextPath + '/api/v1/exercise',
            method: 'GET',
            params: {
                name_filter: $scope.filter ? $scope.filter.name_filter : null,
                page: pageIndex, size: 1000
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
    function generatePageIndex(startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        }
        return arr;
    };

    $scope.fillModeTable = function () {
        $http.get(contextPath + '/api/v1/mode')
            .then(function (response) {
                $scope.Modes = response.data;
                console.log(response.data);
            });
    };

// Table/>

// <Workout
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
                $scope.fillWorkoutExerciseTable();
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
    $scope.delWorkout = function (id) {
        $scope.deleteWorkout(id);
        $scope.updWorkout = null;
    }
// Workout />

// <WorkoutExercise
    $scope.fillFormWorkoutExercise = function () {
        $http.get(contextPath + '/api/v1/workout_exercise/' + $("#saveWorkoutExerciseId").val())
            .then(function (response) {
                $scope.saveWorkoutExercise = response.data;
                console.log('fillFormWorkoutExercise');
                setTimeout(()=>{
                    $('#saveWorkoutExerciseMode').val($scope.saveWorkoutExercise.modeId);
                    $('#saveWorkoutExerciseExercise').val($scope.saveWorkoutExercise.exerciseId);
                    // angular.element(document.getElementById('saveWorkoutExerciseExercise')).triggerHandler('change');
                },200);
            });
    }


    $scope.setSaveWorkoutExercise = function (id) {
        $("#saveWorkoutExerciseId").val(id);
        $scope.fillFormWorkoutExercise();
    }

    $scope.submitSaveWorkoutExercise = function(){
        if(Boolean($scope.saveWorkoutExercise.id)){
            $scope.putWorkoutExercise();
        }else{
            $scope.saveWorkoutExercise.workoutId = $scope.updWorkout.id;
            $scope.postWorkoutExercise();
        }
    }

    $scope.postWorkoutExercise = function () {
        console.log('POST = ');
        console.log($scope.saveWorkoutExercise);
        $http.post(contextPath + '/api/v1/workout_exercise/', $scope.saveWorkoutExercise)
            .then(function (response) {
                $scope.saveWorkoutExercise = null;
                $scope.fillWorkoutExerciseTable();
            });
    };

    $scope.putWorkoutExercise = function () {
        console.log('PUT = '+$scope.saveWorkoutExercise);
        console.log($scope.saveWorkoutExercise);
        $http.put(contextPath + '/api/v1/workout_exercise/', $scope.saveWorkoutExercise)
            .then(function (response) {
                $scope.saveWorkoutExercise = null;
                $("#saveWorkoutExerciseId").val(null);
                $scope.fillWorkoutExerciseTable();
            });
    };

    $scope.deleteWorkoutExercise = function (id) {
        $scope.saveWorkoutExercise = null;
        $("#saveWorkoutExerciseId").val(null);
        $http.delete(contextPath + '/api/v1/workout_exercise/' + id)
            .then(function (response) {
                $scope.fillWorkoutExerciseTable();
            });
    }
    $scope.delWorkoutExercise = function (id) {
        $scope.deleteWorkoutExercise(id);

    }
// <WorkoutExercise />


// Init
    $('#nav_header').find('li').removeClass('active');
    $('#nav_workout').addClass('active');
    $scope.fillTable();
    $scope.fillExerciseTable();
    $scope.fillModeTable();
});