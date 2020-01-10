'use strict';

ApprovalPathModuleController.controller('approvalPathModuleCtrl', ['$scope','$http', '$resource','$routeParams','$filter','$cookieStore','myservice','$location', '$parse', function ($scope,$http, $resource, $routeParams,$filter,$cookieStore,myservice,$location,$parse) {

	$scope.module = new Object();
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
        	if(type== 'getMasterData'){
        		$scope.bindData(response.data);    
            }else if( type = 'saveOrUpdate'){
            	if(!angular.isUndefined(response.data) && response.data == -1){
            		$scope.Messager('error', 'Error', 'Module Code already Exists...');
            	}else if(!angular.isUndefined(response.data) && response.data > 0){
            		$scope.Messager('success', 'success', 'Module Details '+$scope.saveOrUpdate+'d Successfully...');
            		$location.path('/approvalPathModule');  /* /'+response.data); */
            	}else{
            		$scope.Messager('error', 'Error', 'Module Details not saved... Try After Some Time.. ');
            	}
            }         
        },
        function () {
        	 $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
        });
    }
   
    
    
    if ($routeParams.id > 0) {
    	$scope.module.approvalPathModuleId = $routeParams.id;
    	$scope.saveOrUpdate = 'Update';
    } else {
    	$scope.saveOrUpdate = 'Save';
    }

    $scope.module.customerId = $cookieStore.get('customerId');
    $scope.module.companyId = $cookieStore.get('companyId');
    $scope.module.isActive = 'Y';
    
    $scope.getData('approvalPathModuleControler/getMaterDataForApprovalPathForModule.json', angular.toJson($scope.module) , 'getMasterData')
    
    $scope.customerChange = function () {    	
    	$scope.getData('approvalPathModuleControler/getCompanyNamesAsDropDown.json',{customerId: ($scope.module.customerId == undefined || $scope.module.customerId == null )? '0' : $scope.module.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.module.companyId} , 'customerChange');
    };
    

    
    $scope.save = function(){
    	if($('#approvalPathModuleForm').valid()){
    		$scope.module.createdBy = $cookieStore.get('createdBy');
    		$scope.module.modifiedBy = $cookieStore.get('modifiedBy');
    		$scope.getData('approvalPathModuleControler/saveOrUpdateApprovalPathModuleDetails.json', angular.toJson($scope.module) , 'saveOrUpdate');
    	}
    }
    
   
}]);