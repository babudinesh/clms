'use strict';


permissionsGroupControllers.controller('permissionsGroupCtrl', ['$scope','$http', '$resource','$routeParams','$filter','$cookieStore','$compile','$window', '$location', '$parse', function ($scope,$http, $resource, $routeParams,$filter,$cookieStore,$compile,$window,$location,$parse) {

	$scope.groupPermission = new Object();
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
        	if(type== 'screenMasterData'){
        		$scope.screenList = response.data.screenList;
        		$scope.actionsList = response.data.actionsList;
        		if($routeParams.id > 0)
        			$scope.groupPermission = response.data.groupPermission;
            } else if(type == 'saveOrUpdate'){
            	if(!angular.isUndefined(response.data) && response.data == -1){
            		$scope.Messager('error', 'Error', 'Group Name already Exists...');
            	}else if(!angular.isUndefined(response.data) && response.data == -2){
            		$scope.Messager('error', 'Error', 'Group Permissions are in use, Please remove dependency...');
            	}else if(!angular.isUndefined(response.data) && response.data > 0){
            		$scope.Messager('success', 'success', 'Group Permission Details '+$scope.saveOrUpdate+'d Successfully...');
            		$location.path('/permissionsGroup');  /* /'+response.data); */
            	}else{
            		$scope.Messager('error', 'Error', 'Group Permission Details not saved... ');
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
    
    $scope.getData('permissionsGroupController/getPermissionsGroupMasterData.json', {"permissionsGroupId":$routeParams.id} , 'screenMasterData');
    
    $scope.saveOrUpdateDetails = function(){
    	if($('#permissionsGroupForm').valid()){
    		if($window.confirm('Are you sure to change permissions for this group?') ){
		    	$scope.groupPermission.screenActionsVo = [];
		    	angular.forEach($scope.screenList, function(mapObjKey , mapObjIndex) {
		            angular.forEach(mapObjKey, function(value , key) {
		            	$scope.groupPermission.screenActionsVo.push(value);
		            })
		    	})
		    	$scope.getData('permissionsGroupController/saveOrUpdatePermissionsGroupDetails.json', angular.toJson($scope.groupPermission) , 'saveOrUpdate');
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
	            			$('#screenId'+value.screenActionId).find('input').attr('checked');
	            		}else{
	            			$('#screenId'+value.screenActionId).find('input').removeAttr('checked');
	            		}
	            	}
	            })
    		}
        })       
    }
   
    $scope.fun_checkALLScreenActionInGroup = function(groupName, $index){
    	angular.forEach($scope.screenList, function(mapObjKey , mapObjIndex) {
    		if(groupName == mapObjIndex){
	            angular.forEach(mapObjKey, function(value , key) {
	            	if($('#GroupLevelIndex'+$index).val() == 'on'){
	            		$("#screenId"+value.screenActionId+" input:checkbox").prop("checked", true);
	            		value.actionsArray = value.actions.split(',');
	            		value.viewScreenActions = true;
	            	}else{
	            		$("#screenId"+value.screenActionId+" input:checkbox").prop("checked", false);
	            		value.actionsArray = [];
	            		value.viewScreenActions = false;
	            	}
	            })
    		}
        })
        if($('#GroupLevelIndex'+$index).val() == 'on')
    		$('#GroupLevelIndex'+$index).val('off');
    	else
    		$('#GroupLevelIndex'+$index).val('on');
    }
    
    $scope.fun_checkAllScreenActions = function(groupName,screenActionId){
    	angular.forEach($scope.screenList, function(mapObjKey , mapObjIndex) {
    		if(groupName == mapObjIndex){
	            angular.forEach(mapObjKey, function(value , key) {
	            	if( value.screenActionId == screenActionId){
		            	if($('#screenwiseCheck'+screenActionId).val() == 'on'){
		            		$("#screenId"+value.screenActionId+" input:checkbox").prop("checked", true);
		            		value.actionsArray = value.actions.split(',');
		            		value.viewScreenActions = true;
		            	}else{
		            		$("#screenId"+value.screenActionId+" input:checkbox").prop("checked", false);
		            		value.actionsArray = [];
		            		value.viewScreenActions = false;
		            	}
	            	}
	            })
    		}
        })
        if($('#screenwiseCheck'+screenActionId).val() == 'on')
    		$('#screenwiseCheck'+screenActionId).val('off');
    	else
    		$('#screenwiseCheck'+screenActionId).val('on');
    }
    
    
}]).filter('trim', function () {
    return function(value) {
        if(!angular.isString(value)) {
        	value = value.replace(/[^\w\s]/gi, '');
            return value.split(' ').join('');
        }  
        return value.split(' ').join(''); 
    };
});