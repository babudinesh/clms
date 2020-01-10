'use strict';

angular.module('myApp.login',['ngRoute'])
  
angular.module('Authentication')


.controller('unAuthorizedController',['$scope','$cookies','$cookieStore','$location','AuthenticationService','$rootScope','$window',function($scope,$cookies,$cookieStore,$location,AuthenticationService,$rootScope,$window) {

AuthenticationService.ClearCredentials();
	
	$("#menuDiv").attr('style','display:none');
	$rootScope.userName="";
	$rootScope.CustomerAuditAdmin = false;
    $scope.user=new Object();
    var cookies = $cookies.getAll();
    angular.forEach(cookies, function (v, k) {
        $cookies.remove(k);
    }); 
    $window.localStorage.clear();
    $scope.login = function () {
    	alert('login');
    	$location.path('/login');
    }
        
   
   
   
}]);



