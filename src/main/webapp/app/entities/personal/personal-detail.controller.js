(function() {
    'use strict';

    angular
        .module('registroGafetesApp')
        .controller('PersonalDetailController', PersonalDetailController);

    PersonalDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Personal'];

    function PersonalDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Personal) {
        var vm = this;

        vm.personal = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('registroGafetesApp:personalUpdate', function(event, result) {
            vm.personal = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
