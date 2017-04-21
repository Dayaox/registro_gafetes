(function() {
    'use strict';

    angular
        .module('registroGafetesApp')
        .controller('Personal2DeleteController',Personal2DeleteController);

    Personal2DeleteController.$inject = ['$uibModalInstance', 'entity', 'Personal2'];

    function Personal2DeleteController($uibModalInstance, entity, Personal2) {
        var vm = this;

        vm.personal2 = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Personal2.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
