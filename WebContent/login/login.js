'use strict';

angular.module('myApp.login',['ngRoute'])
  
angular.module('Authentication')


.controller('loginCtrl',['$scope','$cookies','$cookieStore','$location','AuthenticationService','$rootScope','$window','RoleBasedScreensService','Idle',function($scope,$cookies,$cookieStore,$location,AuthenticationService,$rootScope,$window,RoleBasedScreensService,Idle) {

	$scope.isError = false;
AuthenticationService.ClearCredentials();
	
	$("#menuDiv").attr('style','display:none');
	
	$rootScope.userName="";
	$rootScope.CustomerAuditAdmin = false;
    $scope.user=new Object();
    var cookies = $cookies.getAll();
    angular.forEach(cookies, function (v, k) {
        $cookies.remove(k);
    }); 
   
   
    $scope.login = function () {
    	
    	if($('#form1').valid()){
            AuthenticationService.Login($scope.user.name, $scope.user.password, function(response) {
            	
                 if(response.data.userId>0) {
                	 Idle.watch();
                	// $scope.dataLoading = true;
                	$('#loadingImageGif').show();
                    AuthenticationService.SetCredentials($scope.username, $scope.password);                    
                    $cookieStore.put('userId',response.data.userId);
                    $cookieStore.put('userName',response.data.userName);
                    $cookieStore.put('roleName',response.data.roleName); 
                    $cookieStore.put('roleId',response.data.roleId);                    
                    $cookieStore.put('createdBy', response.data.userId);
                    $cookieStore.put('modifiedBy',response.data.userId);
                    $cookieStore.put('customerId',response.data.customerId);
                    $cookieStore.put('companyId',response.data.companyId);
                    $cookieStore.put('vendorId',response.data.vendorId);   
                    $cookieStore.put('locationId',response.data.locationId);  
                    $cookieStore.put('departmentId',response.data.departmentId);  
                    $cookieStore.put('plantId',response.data.plantId);  
                    $cookieStore.put('sectionId',response.data.sectionId);  
                    $cookieStore.put('workAreaId',response.data.workAreaId);  
                    $cookieStore.put('divisionId',response.data.divisionId);
                    
                    $cookieStore.put('employeeUniqueId',response.data.employeeUniqueId);
                    $cookieStore.put('screenIdsArray',response.data.screenIdsArray);   
                    $cookieStore.put('screenNamesArray',response.data.screenNamesArray);   
                    $cookieStore.put('roleIdsArray',response.data.roleIdsArray);   
                    $cookieStore.put('roleNamesArray',response.data.roleNamesArray);   
                    $cookieStore.put('groupNamesArray',response.data.groupNamesArray);
                    
                    $cookieStore.put('locationArrayId',response.data.locationArrayId);   
                    $cookieStore.put('plantArrayId',response.data.plantArrayId);   
                    $cookieStore.put('departmentArrayId',response.data.departmentArrayId);   
                    $cookieStore.put('sectionArrayId',response.data.sectionArrayId);   
                    $cookieStore.put('workAreaArrayId',response.data.workAreaArrayId);
               	   	 
                    RoleBasedScreensService.roleBasedScreens();
                    
                    $rootScope.userName=response.data.userName;   
                   
                    if(response.data.roleNamesArray.includes('Vendor Admin')){
                    	$location.path('/workerSearch');
                    	$rootScope.homeUrl = 'workerSearch';
                    }else{
                    	$location.path('/dashboard');
                    	$rootScope.homeUrl = 'dashboard';
                    }
                    	//window.location.reload();
                    		
                    setTimeout (function()
                    		{
                    	$("#menuDiv").attr('style','display:block');
                    		},100);
            		
            		
                   
                } else { 
                	$scope.isError = true;
                    $scope.error = response.message;
                   // $scope.dataLoading = false;
                    $('#loadingImageGif').hide();
                }
        
            });
    	 }
    	}
        
    
    
    
   
    $scope.resetLogin=function(){
        AuthenticationService.ClearCredentials();
        $rootScope.user='';
    }
      $rootScope.ChangeLanguage=function(obj){
           // alert(obj);
        }

}]);
angular.isUndefinedOrNull = function(val) {
    return angular.isUndefined(val) || val === null 
};
String.prototype.replaceAll = function(search, replacement) {
    var target = this;
    return target.replace(new RegExp(search, 'g'), replacement);
};

