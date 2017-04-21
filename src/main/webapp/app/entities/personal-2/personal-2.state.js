(function() {
    'use strict';

    angular
        .module('registroGafetesApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('personal-2', {
            parent: 'entity',
            url: '/personal-2?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Personal2S'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/personal-2/personal-2-s.html',
                    controller: 'Personal2Controller',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        })
        .state('personal-2-detail', {
            parent: 'personal-2',
            url: '/personal-2/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Personal2'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/personal-2/personal-2-detail.html',
                    controller: 'Personal2DetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Personal2', function($stateParams, Personal2) {
                    return Personal2.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'personal-2',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('personal-2-detail.edit', {
            parent: 'personal-2-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/personal-2/personal-2-dialog.html',
                    controller: 'Personal2DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Personal2', function(Personal2) {
                            return Personal2.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('personal-2.new', {
            parent: 'personal-2',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/personal-2/personal-2-dialog.html',
                    controller: 'Personal2DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                distrito: null,
                                municipio: null,
                                cargo: null,
                                nombre: null,
                                tipo: null,
                                foto: null,
                                fotoContentType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('personal-2', null, { reload: 'personal-2' });
                }, function() {
                    $state.go('personal-2');
                });
            }]
        })
        .state('personal-2.edit', {
            parent: 'personal-2',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/personal-2/personal-2-dialog.html',
                    controller: 'Personal2DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Personal2', function(Personal2) {
                            return Personal2.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('personal-2', null, { reload: 'personal-2' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('personal-2.delete', {
            parent: 'personal-2',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/personal-2/personal-2-delete-dialog.html',
                    controller: 'Personal2DeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Personal2', function(Personal2) {
                            return Personal2.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('personal-2', null, { reload: 'personal-2' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
