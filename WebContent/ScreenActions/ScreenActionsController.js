'use strict';

screenActionsController.controller('screenActionsCtrl', ['$scope','$http', '$resource','$routeParams','$filter','$cookieStore','myservice','$location', '$parse', function ($scope,$http, $resource, $routeParams,$filter,$cookieStore,myservice,$location,$parse) {

	//initialise the material-design
    // $.material.init();
    // $scope.showNewGroupName = false;
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
        	if(type== 'screenActionData'){
        		$scope.bindData(response.data);
        		if(response.data != undefined && response.data != null && response.data.sreenActions != undefined && response.data.sreenActions.length > 0){
        			$scope.sreenActions = response.data.sreenActions[0];
        			angular.forEach(response.data.sreenActions[0].actionsArray, function(value, index){
		        		 $scope.sreenActions.actionsArray.push(parseInt(value));
		        	});
        		}
            }else if( type = 'saveOrUpdateScreenActions'){
            	if(!angular.isUndefined(response.data) && response.data == -1){
            		$scope.Messager('error', 'Error', 'Screen Name already Exists...');
            	}else if(!angular.isUndefined(response.data) && response.data == -2){
            		$scope.Messager('error', 'Error', 'Screen Actions are binded with Group Permission..');
            	}else if(!angular.isUndefined(response.data) && response.data > 0){
            		$scope.Messager('success', 'success', 'Screen Action Details '+$scope.saveOrUpdate+'d Successfully...');
            		$location.path('/screenActions');  /* /'+response.data); */
            	}else{
            		$scope.Messager('error', 'Error', 'Screen Action Details not saved... ');
            	}
            }         
        },
        function () {
        	 $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
        });
    }
   
    
    
    if ($routeParams.id > 0) {
    	$scope.saveOrUpdate = 'Update';
    } else {
    	$scope.saveOrUpdate = 'Save';
    }

    $scope.getData('screenActionsController/getScreenMasterData.json', {"screenActionId": $routeParams.id} , 'screenActionData');
    
 /*   $scope.fun_ShowNewGroupName = function(){
    	$scope.showNewGroupName = !$scope.showNewGroupName;
    	if(!$scope.showNewGroupName){
    		$scope.sreenActions.newGroupName="";
    	}
    }*/
    
    $scope.saveOrUpdateScreenActions = function(){
    	if($('#screenActionForm').valid()){
    		$scope.getData('screenActionsController/saveOrUpdateScreenActionsDetails.json', angular.toJson($scope.sreenActions) , 'saveOrUpdateScreenActions');
    	}
    }
    
   
}]);