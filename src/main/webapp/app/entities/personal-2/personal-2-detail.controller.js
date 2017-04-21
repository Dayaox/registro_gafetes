(function() {
    'use strict';

    angular
        .module('registroGafetesApp')
        .controller('Personal2DetailController', Personal2DetailController);

    Personal2DetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Personal2'];

    function Personal2DetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Personal2) {
        var vm = this;

        vm.personal2 = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('registroGafetesApp:personal2Update', function(event, result) {
            vm.personal2 = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
