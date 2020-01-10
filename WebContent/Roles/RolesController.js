'use strict';


rolesControllers.controller('rolesCtrl', ['$scope','$http', '$resource','$routeParams','$filter','$cookieStore','$compile','$window', '$location', '$parse', function ($scope,$http, $resource, $routeParams,$filter,$cookieStore,$compile,$window,$location,$parse) {

	$scope.roles = new Object();
    $scope.enable = false;
    $scope.Messager = function (type, heading, text, btn) {
        $.toast({
            heading: heading,
            icon: type,
            text: text,
            stack: false, beforeShow: function () {
                $scope.enable = true;
            },
            afterHidden: function () {
                $scope.enable = false;
            }
        });
    }
    
    $scope.bindData = function(responseData){    	
    	angular.forEach(responseData, function (value, key) {
  		    $parse(key).assign($scope, value);  
  		});
    }

    $scope.getData = function (url, data, type,buttonDisable) {
        var req = {
            method: 'POST',
            url: ROOTURL + url,
            headers: {
                'Content-Type': 'application/json'
            },
            data: data
        }
        $http(req).then(function (response) {
        	if(type == 'masterData'){
        		$scope.customerList = response.data.customerList;
        		$scope.companyList = response.data.companyList;
        		$scope.groupNames = response.data.groupNames;
        		if($routeParams.id > 0){
	        		$scope.roles = response.data.roles;
	        		$scope.roles.permissionsGroupArray = $scope.roles.permissionsGroupArray.map(Number);
        		}
        	} /*else if(type== 'groupNamesList'){
        		$scope.groupNames = response.data;
            } */else if(type == 'saveOrUpdate'){
            	if(!angular.isUndefined(response.data) && response.data == -1){
            		$scope.Messager('error', 'Error', 'Role Name already Exists...');
            	}else if(!angular.isUndefined(response.data) && response.data == -2){
            		$scope.Messager('error', 'Error', 'Roles are in use, Please remove dependency...');
            	}else if(!angular.isUndefined(response.data) && response.data > 0){
            		$scope.Messager('success', 'success', 'Role Details '+$scope.saveOrUpdate+'d Successfully...');
            		$location.path('/roles');  /* /'+response.data); */
            	}else{
            		$scope.Messager('error', 'Error', 'Role Details not saved... ');
            	}
            }    
        },
        function () {
        	 $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
        });
    }
   
    if($routeParams.id > 0){
    	$scope.saveOrUpdate = 'Update';
    }else{
    	$scope.saveOrUpdate = 'Save';
    }   
    $scope.roles.customerId = $cookieStore.get('customerId');
    $scope.roles.companyId = $cookieStore.get('companyId');
    $scope.roles.roleId = $routeParams.id;
    
    // $scope.getData('permissionsGroupController/getPermissionsGroupMasterData.json', {"permissionsGroupId":$routeParams.id} , 'screenMasterData');
   // $scope.getData('permissionsGroupController/getPermissionsGroupName.json', {} , 'groupNamesList');
    $scope.getData('rolesController/getMaterDataForRole.json', angular.toJson($scope.roles)  , 'masterData');
    
    $scope.saveOrUpdateDetails = function(){
    	if($('#rolesForm').valid()){
    		if($window.confirm('Are you sure to change Group permissions for this role ?') ){
    			$scope.roles.createdBy = $cookieStore.get('createdBy');
				$scope.roles.modifiedBy = $cookieStore.get('modifiedBy');
		    	$scope.getData('rolesController/saveOrUpdateRoleDetails.json', angular.toJson($scope.roles) , 'saveOrUpdate');
			}
    	}
    }
    
    
    $scope.fun_checkScreenAction = function($event, screenIndex, groupName, isCheckedOrNot, action){
    	angular.forEach($scope.screenList, function(mapObjKey , mapObjIndex) {
    		if(groupName == mapObjIndex){
	            angular.forEach(mapObjKey, function(value , key) {
	            	if(key == screenIndex ) {
	            		if(Object.prototype.toString.call(value.actionsArray) === '[object Array]' ){
	            			if(isCheckedOrNot == true){
	            				value.actionsArray.push(action);
	            			}else{
	            				value.actionsArray.splice(value.actionsArray.indexOf(action),1);
	            			}
	            		}else{
	            			value.actionsArray = [];
	            			if(isCheckedOrNot  == true){
	            				value.actionsArray.push(action);
	            			}else{
	            				value.actionsArray.splice(value.actionsArray.indexOf(action),1);
	            			}
	            		}
	            	}
	            })
    		}
        })
    }
    
    $scope.fun_viewScreenAction = function(screenIndex, isCheckedOrNot, groupName, screenName ){
    	var action = 'VIEW';
    	angular.forEach($scope.screenList, function(mapObjKey , mapObjIndex) {
    		if(groupName == mapObjIndex){
	            angular.forEach(mapObjKey, function(value , key) {
	            	if(value.screenName == screenName ) {
	            		value.actionsArray = [];
	            		if(isCheckedOrNot){
	            			$('#'+value.screenName.split(' ').join('')).find('input').attr('checked');
	            		}else{
	            			$('#'+value.screenName.split(' ').join('')).find('input').removeAttr('checked');
	            		}
	            	}
	            })
    		}
        })       
    }
   
}]).filter('trim', function () {
    return function(value) {
        if(!angular.isString(value)) {
            return value.split(' ').join('');
        }  
        return value.split(' ').join(''); 
    };
});