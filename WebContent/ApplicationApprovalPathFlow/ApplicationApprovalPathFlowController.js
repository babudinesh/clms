'use strict';

ApprovalPathTransactionController.controller('approvalPathFlowCtrl', ['$scope','$http', '$resource','$routeParams','$filter','$cookieStore','myservice','$location', '$parse', function ($scope,$http, $resource, $routeParams,$filter,$cookieStore,myservice,$location,$parse) {
	   
	$.material.init();
	$scope.statusList = [{"id":"Y","name":"Active"},
        				 {"id":"N","name":"In-Active"}];
	$scope.approverStatusList = [{"id":"Start","name":"Start"},{"id":"Forward","name":"Forward"},
		 {"id":"Approve/Reject","name":"Approve/Reject"}];
	
	$scope.pathflow = new Object();
	
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
	 
    $scope.getData = function (url, data, type) {
        var req = {
            method: 'POST',
            url: ROOTURL+url,
            headers: {
                'Content-Type': 'application/json'
            },
            data:data
        }
        $http(req).then(function (response) { 
        	
        	if( type == 'getMasterData'){
        		 $scope.customerList = response.data.customerList;
        		 if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){
 	                $scope.pathflow.customerId = response.data.customerList[0].customerId;
 	               $scope.customerChange();
                 }        		
        	} else if (type == 'customerChange'){	        	
	             $scope.companyList = response.data;
	             if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	                $scope.pathflow.companyId = response.data[0].id;
	                $scope.companyChange();
                 }
               
	        } else if(type == 'companyChange'){
	        	$scope.moduleList = response.data;
	        } else if( type == 'moduleChange'){
	        	$scope.transactionList = response.data;
	        } else if( type == 'rolesList'){
	        	$scope.rolesList = response.data;
	        } else if( type == 'fromRoleChange'){
	        	$scope.fromUsersList = response.data;
	        } else if( type == 'toRoleChange'){
	        	$scope.toUsersList = response.data;
	        } else if( type == 'getMasterDataonUpdate'){
        		  $scope.customerList = response.data.customerList;
	        	  $scope.companyList = response.data.companyList;
	        	  $scope.moduleList = response.data.moduleList;
	        	  $scope.transactionList = response.data.transactionList;
	        	  $scope.pathflow = response.data.pathflow;
	        	  if($scope.pathflow.noApprovalRequired == undefined || $scope.pathflow.noApprovalRequired == 'N')
	        		  $scope.renderTable = true;
	        	  else
	        		  $scope.renderTable = false;
	        } else if( type == 'save'){
	        	if(response.data > 0){
	            	$scope.Messager('success', 'success', 'Application Flow Details '+$scope.saveOrUpdate+'d Successfully',true);
	            	$location.path('/applicationApprovalPathFlow');
	      	    }else if(response.data == -1){
	      		   $scope.Messager('error', 'Error', 'Path Code alredy Exists....',true);
	            }else{
	          	   $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',true);
	           }
	        }
          },
        function () { 
        	  
        	  
        	  
          });
    	} 
    
    if ($routeParams.id > 0) {
    	$scope.saveOrUpdate = 'Update';
    	$scope.getData('applicationApprovalPathFlowControler/getMaterDataForApprovalPathFlow.json', { customerId: $cookieStore.get('customerId'), companyId: $cookieStore.get('companyId'), applicationApprovalPathId : $routeParams.id }, 'getMasterDataonUpdate');
    } else {
    	$scope.renderTable = true;
    	$scope.pathflow.noApprovalRequired = false;
        $scope.pathflow.customerId = $cookieStore.get('customerId');
        $scope.pathflow.companyId = $cookieStore.get('companyId');
        $scope.pathflow.isActive = 'Y';
    	$scope.saveOrUpdate = 'Save';
    	$scope.getData('vendorController/getCustomerNamesAsDropDown.json', { customerId: $cookieStore.get('customerId')}, 'getMasterData');
    }
    
    $scope.getData('CommonController/getRoles.json', {customerId : $cookieStore.get('customerId') , companyId : ($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : 0 }, 'rolesList');
    
    $scope.customerChange = function () {    	
    	$scope.getData('vendorController/getCompanyNamesAsDropDown.json',{customerId: ($scope.pathflow.customerId == undefined || $scope.pathflow.customerId == null )? '0' : $scope.pathflow.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.pathflow.companyId} , 'customerChange');
    };
	
    $scope.companyChange = function () {    	
    	$scope.getData('approvalPathModuleControler/getApprovalPathModules.json', { customerId:$scope.pathflow.customerId, companyId: $scope.pathflow.companyId, approvalPathModuleId :$scope.pathflow.approvalPathModuleId} , 'companyChange');
    };
    
    $scope.moduleChange = function () {    	
    	$scope.getData('approvalPathTransactionControler/getApprovalPathTransactions.json', { approvalPathModuleId:$scope.pathflow.approvalPathModuleId} , 'moduleChange');
    };
    
    $scope.fromRoleChange = function () {    	
    	$scope.getData('CommonController/getUsersByRoleId.json', { roleId:$scope.user.fromRoleId} , 'fromRoleChange');
    };
    
    $scope.toRoleChange = function () {    	
    	$scope.getData('CommonController/getUsersByRoleId.json', { roleId:$scope.user.toRoleId} , 'toRoleChange');
    };
    
    $scope.fun_renderTable = function(){
    	if($scope.pathflow.noApprovalRequired == undefined)
    		$scope.pathflow.noApprovalRequired = 'N';
    	$scope.renderTable = ($scope.pathflow.noApprovalRequired == undefined || $scope.pathflow.noApprovalRequired == 'N') ? true : false ;
    	if($scope.renderTable){
    		$scope.pathflow.flowList = [];
    		$scope.pathflow.noOfApprovalLevels = 0;
    	}
    }

    
    $scope.save = function($event){
    	if($('#flowForm').valid()){
    		if($scope.pathflow.noApprovalRequired == false)
    			$scope.pathflow.noApprovalRequired = 'N';
    			
    		if(($scope.pathflow.noApprovalRequired == false || $scope.pathflow.noApprovalRequired == 'N') && ($scope.pathflow.noOfApprovalLevels == undefined || $scope.pathflow.noOfApprovalLevels < 1) ){
    			 $scope.Messager('error', 'Error', ' No Approval required / No of Approval Levels is required',true);
    		}else if($scope.pathflow.noOfApprovalLevels > 0 && $scope.pathflow.flowList.length == 0){
    			 $scope.Messager('error', 'Error', ' Enter Application Flow Details',true);
    		}else{
	    		$scope.pathflow.createdBy = $cookieStore.get('createdBy');
	    		$scope.pathflow.modifiedBy = $cookieStore.get('modifiedBy');
	    		$scope.getData('applicationApprovalPathFlowControler/saveOrUpdateApprovalFlowDetails.json', angular.toJson($scope.pathflow) , 'save');
    		}
    	}
    }
    
    
    $scope.plusIconAction = function () {  
    	if($scope.pathflow.flowList == undefined || $scope.pathflow.flowList.length == undefined ||  $scope.pathflow.flowList.length <=0 )
    		$scope.pathflow.flowList = [];
    	
    	$scope.user = new Object();
    	// $scope.user.flowNo = $scope.pathflow.flowList.length+1;
    	$scope.popUpSave = true;
    	$scope.popUpUpdate = false; 
    };
    
    $scope.Edit = function ($event) {   
    	$scope.userIndex = $($event.target).parent().parent().index();
    	$scope.user = $scope.pathflow.flowList[$($event.target).parent().parent().index()];
    	$scope.getData('CommonController/getUsersByRoleId.json', { roleId:$scope.user.fromRoleId} , 'fromRoleChange');
    	$scope.getData('CommonController/getUsersByRoleId.json', { roleId:$scope.user.toRoleId} , 'toRoleChange');
    	$scope.popUpSave = false;
    	$scope.popUpUpdate =true;
    };
    
    
    $scope.saveUserDetails = function () {   
    	if($('#flowLstPopup').valid()){
	    	$scope.pathflow.flowList.push({
	    		fromRoleId : $scope.user.fromRoleId,
	    		fromUserId : $scope.user.fromUserId,
	    		//flowNo : $scope.user.flowNo,
	    		fromRoleName : $('#fromRoleId option:selected').html() !='Select' ? $('#fromRoleId option:selected').html() : '',
	    		fromUserName : $('#fromUserId option:selected').html() !='Select' ? $('#fromUserId option:selected').html() : '',
				toRoleId : $scope.user.toRoleId,
	    		toUserId : $scope.user.toUserId,
	    		toRoleName : $('#toRoleId option:selected').html() !='Select' ? $('#toRoleId option:selected').html() : '',
	    		toUserName : $('#toUserId option:selected').html() !='Select' ? $('#toUserId option:selected').html() : '',
	    		approverStatus : $scope.user.approverStatus
	    	});
	    	$('div[id^="myModal"]').modal('hide');
    	}
    	$scope.pathflow.noOfApprovalLevels = $scope.pathflow.flowList.length;
    };
    
    $scope.updateUserDetails = function () {
    	if($('#flowLstPopup').valid()){
	    	$scope.pathflow.flowList.splice($scope.userIndex,1,{
	    		fromRoleId : $scope.user.fromRoleId,
	    		fromUserId : $scope.user.fromUserId,
	    		//flowNo : $scope.user.flowNo,
	    		fromRoleName : $('#fromRoleId option:selected').html() !='Select' ? $('#fromRoleId option:selected').html() : '',
	    		fromUserName : $('#fromUserId option:selected').html() !='Select' ? $('#fromUserId option:selected').html() : '',
				toRoleId : $scope.user.toRoleId,
	    		toUserId : $scope.user.toUserId,
	    		toRoleName : $('#toRoleId option:selected').html() !='Select' ? $('#toRoleId option:selected').html() : '',
	    		toUserName : $('#toUserId option:selected').html() !='Select' ? $('#toUserId option:selected').html() : '',
				approverStatus : $scope.user.approverStatus
	    	});
	    	$('div[id^="myModal"]').modal('hide');
    	}
    };
    
    $scope.Delete = function($event){    	
    	var r = confirm("Do you want to delete the "+$scope.pathflow.flowList[$($event.target).parent().parent().index()].roleName);    	
    	if(r){
    		$scope.pathflow.flowList.splice($($event.target).parent().parent().index(),1);
    	}
    }
    
    
}]);