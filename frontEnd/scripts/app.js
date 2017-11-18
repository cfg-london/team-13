var myApp = angular.module('myApp',
	        ['ngRoute',
           'appControllers',
	        ]);

console.log("app.js");

var appControllers = angular.module('appControllers', []);

myApp.config(['$routeProvider', function ($routeProvider) {
  $routeProvider.
      when('/test', {
        templateUrl: 'views/test.html',
        controller: 'Test'
      }).
      otherwise({
         //redirectTo: registered ? '/application' : '/register'
        redirectTo: '/test'
      });
  //$locationProvider.html5Mode(true);
}]);
/*var routes  = require('./routes');
myApp.get('*', routes.index);*/