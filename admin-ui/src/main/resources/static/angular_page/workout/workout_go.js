angular.module('app').controller('workoutGoController', function ($scope, $http ,$localStorage, $route) {
    const contextPath = 'http://localhost:8189/fitnessab';
    if ($localStorage.currentUser) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.currentUser.token;
    }
// <Table
    $scope.fillWorkoutExerciseTable = function (pageIndex = 1) {
        let data= {
            workoutId: $scope.Workout.id ? $scope.Workout.id : null,
            page: pageIndex, size: 10
        };
        $http({
            url: contextPath + '/api/v1/workout_exercise',
            method: 'GET',
            params: data
        }).then(function (response) {
            $scope.WorkoutExercises = response.data;
            if(response.data.numberOfElements === 0 && response.data.totalPages < pageIndex ){
                $scope.fillWorkoutExerciseTable(--pageIndex);
            }
            $scope.WorkoutExercisesPage = pageIndex;
            $scope.PaginationWorkoutExercises = generatePageIndex($scope.WorkoutExercises);

            if($scope?.WorkoutExercise?.id == null && $scope.WorkoutExercises.content.length > 0){
                $scope.getWorkoutExerciseById($scope.WorkoutExercises.content[0].id);
            }
        });
    };

    $scope.fillRoundTable = function (pageIndex = 1) {
        let data= {
            workoutId: $scope.Workout?.id,
            workoutExerciseId: $scope?.WorkoutExercise?.id,
            page: pageIndex, size: 20
        };
        $http({
            url: contextPath + '/api/v1/round',
            method: 'GET',
            params: data
        }).then(function (response) {
            if(response.status === 200) {
                $scope.Rounds = response.data;
                console.log(response.data)
                $scope.RoundsSessions = new Map();
                let sessionDates = Array.from(new Set(response.data.content.map(x => x.sessionDate)));
                sessionDates.forEach(session => {
                    $scope.RoundsSessions.set(
                        session, response.data.content.filter(r => r.sessionDate === session));
                });
                if (response.data.numberOfElements === 0 && response.data.totalPages < pageIndex) {
                    $scope.fillRoundTable(--pageIndex);
                }
                let lustRound = Object.assign({}, response.data.content[0]);
                if (response.data.content.length > 0 && pageIndex === 1) {
                    let session = new Date(sessionDates[0]);
                    let endSession = new Date();
                    endSession.setTime(endSession.getTime() - (3 * 60 * 60 * 1000));
                    if (sessionDates.length > 1 && session > endSession) {
                        let lustRoundNumber = $scope.RoundsSessions.get(sessionDates[0]).length;
                        if (lustRoundNumber < $scope.RoundsSessions.get(sessionDates[1]).length) {
                            lustRound = Object.assign({}, $scope.RoundsSessions.get(sessionDates[1])[lustRoundNumber]);
                        } else {
                            lustRound = Object.assign({}, $scope.RoundsSessions.get(sessionDates[1]).at(-1));
                        }
                    }
                    $scope.Round = lustRound;
                    $scope.Round.id = null;
                }
                $scope.RoundsPage = pageIndex;
                $scope.PaginationRounds = generatePageIndex($scope.Rounds);
            }
        });
    };

    function generatePageIndex(data) {
        const startPage = (data.number < 1) ? 1 : data.number;
        const endPage = (data.number + 2 > data.totalPages) ? data.totalPages : data.number + 2;
        let arr = [];
        for (let i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        }
        return arr;
    };

// Table/>

// <WorkoutExercise

    $scope.getWorkoutById = function (id) {
        $http.get(`${contextPath}/api/v1/workout/${id}`)
            .then(function (response) {
                $scope.Workout = response.data;
                $scope.fillWorkoutExerciseTable();
            });
    }
    $scope.getWorkoutExerciseById = function (id) {
        $http.get(`${contextPath}/api/v1/workout_exercise/${id}`)
            .then(function (response) {
                $scope.WorkoutExercise = response.data;
                delete $scope.Round;
                $scope.getExerciseById($scope.WorkoutExercise.exerciseId);
                $scope.fillRoundTable();
            });
    }
    $scope.getExerciseById = function (id) {
        $http.get(`${contextPath}/api/v1/exercise/${id}`)
            .then(function (response) {
                $scope.Exercise = response.data;
                $scope.Exercise = response.data;
            });
    }


// WorkoutExercise />

// <Forms
    $scope.fillFormRound = function (id) {
        $http.get(`${contextPath}/api/v1/round/${id}`)
            .then(function (response) {
                $scope.Round = response.data;
            });
    }

    $scope.submitRound = function(){
        if(Boolean($scope.Round.id)){
            $scope.putRound();
        }else{
            $scope.postRound();
        }
    }

    $scope.postRound = function () {
        $scope.Round.workoutExerciseId=$scope.WorkoutExercise.id;
        $http.post(contextPath + '/api/v1/round/', $scope.Round)
            .then(function (response) {
                delete $scope.Round;
                $scope.fillRoundTable($scope.RoundsPage);
            });
    };

    $scope.putRound = function () {
        $http.put(contextPath + '/api/v1/round/', $scope.Round)
            .then(function (response) {
                delete $scope.Round;
                $scope.fillRoundTable($scope.RoundsPage);
            });
    };

    $scope.deleteRound = function (id) {
        $http.delete(contextPath + '/api/v1/round/' + id)
            .then(function (response) {
                console.log(response);
                $scope.fillRoundTable();
            });
    }

// <Forms />


// Init
    $('#nav_header').find('li').removeClass('active');
    $('#nav_workout').addClass('active');
    $scope.getWorkoutById($route.current.params.id);
});