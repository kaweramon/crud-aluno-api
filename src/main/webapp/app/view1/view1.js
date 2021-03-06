'use strict';

angular.module('myApp.view1', ['ngRoute', 'ui.utils.masks'])

    .config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/view1', {
            templateUrl: 'app/view1/view1.html',
            controller: 'View1Ctrl'
        });
    }])

.controller('View1Ctrl', ["$scope", "$http", 'Notification', 'Mensagens', function($scope, $http, Notification,
                                                                                   Mensagens) {

    $scope.aluno = {};
    $scope.alunos = [];
    $scope.showForm = false;
    $scope.showFiltros = false;
    $scope.filter = {};

    this.$onInit = function() {
        listar();
    };

    var listar = function() {
        $http({
            url: "http://localhost:8080/alunos",
            method: "GET",
            headers: {
                'Authorization': 'Basic ' + btoa('admin:admin')                
            }
        })
        .then(function(response) {
            $scope.alunos = response.data.content;
        }, function(response) {
            if (response.data != null)
                Notification({message: response.data.message, title: 'Erro!'}, 'error');
        });
    };

    $scope.salvar = function() {
        $http({
            url: "http://localhost:8080/alunos",
            method: "POST",
            headers: {
                'Authorization': 'Basic ' + btoa('admin:admin')                
            },
            data: JSON.stringify($scope.aluno)
        })
        .then(function(response) {
            Notification({message: Mensagens.MSG_ALUNO_CADASTRADO, title: Mensagens.MSG_SUCESSO}, 'success');
            resetar();
        }, function(response) {
            if (response.data && response.data.length > 0)
                Notification({message: response.data[0].msgUser, title: 'Erro!'}, 'error');
        });
    };

    $scope.mostrarForm = function() {
        $scope.showForm = !$scope.showForm;
    };

    $scope.deletarAluno = function(alunoId) {
        $http({
            url: "http://localhost:8080/alunos/" + alunoId,
            headers: {
                'Authorization': 'Basic ' + btoa('admin:admin')
            },
            method: "DELETE"
        })
        .then(function(response) {
            listar();
            Notification({message: Mensagens.MSG_ALUNO_REMOVIDO, title: Mensagens.MSG_SUCESSO}, 'success');
        }, function(response) {
            if (response.data != null)
                Notification({message: response.data.message, title: 'Erro!'}, 'error');
        });
    };

    $scope.editar = function (aluno) {
      $scope.aluno = angular.copy(aluno);
      $scope.aluno.dataNascimento = new Date(moment(aluno.dataNascimento));
      $scope.showForm = true;
    };

    $scope.atualizar = function () {
        $http({
            url: "http://localhost:8080/alunos/" + $scope.aluno.id,
            method: "PUT",
            headers: {
                'Authorization': 'Basic ' + btoa('admin:admin')
            },
            data: JSON.stringify($scope.aluno)
        })
        .then(function (response) {
            Notification({message: Mensagens.MSG_ALUNO_ATUALIZADO, title: Mensagens.MSG_SUCESSO}, 'success');
            resetar();
        }, function (response) {
            if (response.data != null)
                Notification({message: response.data.message, title: 'Erro!'}, 'error');
        });
    };

    $scope.mostrarFiltros = function () {
      $scope.showFiltros = !$scope.showFiltros;
    };

    $scope.pesquisar = function () {
        var params = {};

        if ($scope.filter.id)
            params.id = $scope.filter.id;
        if ($scope.filter.nome)
            params.nome = $scope.filter.nome;
        if ($scope.filter.cpf)
            params.cpf = $scope.filter.cpf;
        if ($scope.filter.matricula)
            params.matricula = $scope.filter.matricula;

        $http({
            url: "http://localhost:8080/alunos",
            method: "GET",
            headers: {
                'Authorization': 'Basic ' + btoa('admin:admin')
            },
            params: params
        })
        .then(function (response) {
           $scope.alunos = response.data.content;
        }, function (response) {
        	if (response.data != null)
                Notification({message: response.data.message, title: 'Erro!'}, 'error');
        });
    };

    $scope.limparForm = function (formAluno) {
        $scope.aluno = angular.copy({});
        if (formAluno)
            formAluno.$setUntouched();
    };

    var resetar = function () {
        $scope.limparForm();
        listar();
        $scope.showForm = false;
    };

    $scope.fecharForm = function () {
        $scope.showForm = false;
    };

}]);