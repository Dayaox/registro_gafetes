(function() {
    'use strict';

    angular
        .module('registroGafetesApp')
        .controller('Personal2DialogController', Personal2DialogController);

    Personal2DialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Personal2'];

    function Personal2DialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Personal2) {
        var vm = this;

        vm.personal2 = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.personal2.id !== null) {
                Personal2.update(vm.personal2, onSaveSuccess, onSaveError);
            } else {
                Personal2.save(vm.personal2, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('registroGafetesApp:personal2Update', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setFoto = function ($file, personal2) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        personal2.foto = base64Data;
                        personal2.fotoContentType = $file.type;
                    });
                });
            }
        };

    }
})();
