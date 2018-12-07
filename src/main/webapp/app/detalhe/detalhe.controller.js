angular.module('myApp.detalhe', [])

.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/detalhe/:id', {
        templateUrl: 'app/detalhe/detalhe.html',
        controller: 'DetalheCtrl'
    });
}])

.controller('DetalheCtrl', ["$scope", "$http", 'Notification', 'Mensagens', '$routeParams', function($scope, $http, Notification,
                                                                                     Mensagens, $routeParams) {

    $scope.aluno = {};

    $http({
        url: 'http://localhost:8080/alunos/' + $routeParams.id,
        method: 'GET'
    }).then(function (response) {
        console.log(response);
        $scope.aluno = response.data;
    }, function (response) {
        console.error(response);
    });




}]);