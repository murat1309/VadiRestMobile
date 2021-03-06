'use strict';

describe('Controllers Tests ', function() {

    beforeEach(mockApiAccountCall);
    beforeEach(mockI18nCalls);

    describe('ActivationController', function() {

        var $scope, $httpBackend, $q, MockAuth; // actual implementations
        var MockAuth, MockStateParams; // mocks
        var createController; // local utility function

        beforeEach(inject(function($injector) {
            $q = $injector.get('$q');
            $scope = $injector.get('$rootScope').$new();
            $httpBackend = $injector.get('$httpBackend');
            MockAuth = jasmine.createSpyObj('MockAuth', ['activateAccount']);
            MockStateParams = jasmine.createSpy('MockStateParams');
            MockStateParams.key = 'ABC123';

            var locals = {
                '$scope': $scope,
                '$stateParams': MockStateParams,
                'Auth': MockAuth
            };
            createController = function() {
                $injector.get('$controller')('ActivationController', locals);
            };
        }));

        it('calls Auth.activateAccount with the key from stateParams', function() {
            // given
            MockAuth.activateAccount.and.returnValue($q.resolve());
            // when
            $scope.$apply(createController);
            // then
            expect(MockAuth.activateAccount).toHaveBeenCalledWith({
                key: 'ABC123'
            });
        });

        it('should set set success to OK upon successful activation', function() {
            // given
            MockAuth.activateAccount.and.returnValue($q.resolve());
            // when
            $scope.$apply(createController);
            // then
            expect($scope.error).toBe(null);
            expect($scope.success).toEqual('OK');
        });

        it('should set set error to ERROR upon activation failure', function() {
            // given
            MockAuth.activateAccount.and.returnValue($q.reject());
            // when
            $scope.$apply(createController);
            // then
            expect($scope.error).toBe('ERROR');
            expect($scope.success).toEqual(null);
        });
    });
});
