(function() {
    'use strict';
    angular
        .module('registroGafetesApp')
        .factory('Personal2', Personal2);

    Personal2.$inject = ['$resource'];

    function Personal2 ($resource) {
        var resourceUrl =  'api/personal-2-s/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
