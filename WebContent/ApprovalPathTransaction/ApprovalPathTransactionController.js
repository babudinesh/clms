'use strict';

ApprovalPathTransactionController.controller('approvalPathTransactionCtrl', ['$scope','$http', '$resource','$routeParams','$filter','$cookieStore','myservice','$location', '$parse', function ($scope,$http, $resource, $routeParams,$filter,$cookieStore,myservice,$location,$parse) {

	 $('#transactionDate').datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown'
     });
     $scope.transaction = new Object();
     $scope.transaction.customerId = $cookieStore.get('customerId');
     $scope.transaction.companyId = $cookieStore.get('companyId');
     $scope.transactionDate = $filter('date')(new Date(),'dd/MM/yyyy');
     $scope.transaction.isActive = 'Y';
 if ($routeParams.id > 0) {
	 $scope.transaction.approvalPathTransactionInfoId = $routeParams.id;
     $scope.readOnly = true;
     $scope.datesReadOnly = true;
     $scope.updateBtn = true;
     $scope.saveBtn = false;
     $scope.viewOrUpdateBtn = true;
     $scope.correcttHistoryBtn = false;
     $scope.resetBtn = false;
     $scope.transactionList = false;
 } else {
	 $('#transactionDate').val($filter('date')(new Date(),'dd/MM/yyyy'));
     $scope.readOnly = false;
     $scope.datesReadOnly = false;
     $scope.updateBtn = false;
     $scope.saveBtn = true;
     $scope.viewOrUpdateBtn = false;
     $scope.correcttHistoryBtn = false;
     $scope.resetBtn = true;
     $scope.transactionList = false;
 }
 $scope.updateBtnAction = function (this_obj) {
     $scope.readOnly = false;
     $scope.onlyRead = true;
     $scope.datesReadOnly = false;
     $scope.updateBtn = false;
     $scope.saveBtn = true;
     $scope.viewOrUpdateBtn = false;
     $scope.correctHistoryBtn = false;
     $scope.resetBtn = false;
     $scope.cancelBtn = true;
     $scope.transactionList = false;
     $('#transactionDate').val($filter('date')(new Date(),'dd/MM/yyyy'));
     $('.dropdown-toggle').removeClass('disabled');
 }
 
 
 $scope.viewOrEditHistory = function () {
     $scope.readOnly = false;
     $scope.onlyRead = true;
     $scope.datesReadOnly = false;
     $scope.updateBtn = false;
     $scope.saveBtn = false;
     $scope.viewOrUpdateBtn = false;
     $scope.correcttHistoryBtn = true;
     $scope.resetBtn = false;      
     $scope.transactionList = true;
     $scope.getData('approvalPathTransactionControler/getTransationHistoryDatesList.json', { approvalPathTransactionId: $scope.transaction.approvalPathTransactionId}, 'transactionDatesChange');       
     $('.dropdown-toggle').removeClass('disabled');
 }
 
 $scope.cancelBtnAction = function(){
  	$scope.readOnly = true;
      $scope.datesReadOnly = true;
      $scope.updateBtn = true;
      $scope.saveBtn = false;
      $scope.viewOrUpdateBtn = true;
      $scope.correcttHistoryBtn = false;
      $scope.resetBtn = false;
      $scope.transactionList = false;
      $scope.cancelBtn = false;
      $scope.returnToSearchBtn = true;
      $scope.getData('approvalPathTransactionControler/getMaterDataForApprovalPathForTransaction.json', {"customerId":$scope.transaction.customerId,"companyId":$scope.transaction.companyId,"approvalPathTransactionInfoId":$scope.transaction.approvalPathTransactionInfoId,"isActive":$scope.transaction.isActive} , 'masterDataList');
  }
 
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
 

 $scope.getData = function (url, data, type, buttonDisable) {
     var req = {
         method: 'POST',
         url: ROOTURL + url,
         headers: {
             'Content-Type': 'application/json'
         },
         data: data
     }
     $http(req).then(function (response) {
  	     if (type == 'masterDataList') {
  	    	$scope.customerList = response.data.customerList;
  	    	if(response.data.companyList != undefined && response.data.companyList.length > 0)
  	    		$scope.companyList = response.data.companyList;
  	    	if(response.data.moduleList != undefined && response.data.moduleList.length > 0)
  	    		$scope.moduleList = response.data.moduleList;
  	    	if(response.data.transaction != undefined && response.data.transaction != ''){
  	    		$scope.transaction = response.data.transaction; 
  	    		$scope.transactionDate =  $filter('date')(response.data.transaction.effectiveDate,'dd/MM/yyyy');
  	    	}
  	    	 
         } else if (type == 'customerChange') {
             $scope.companyList = response.data;
             if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
              	$scope.transaction.companyId = response.data[0].id;
              	$scope.companyChange();
              }
         } else if (type == 'companyChange') {
             $scope.moduleList = response.data;
             if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
 	              $scope.transaction.approvalPathModuleId = response.data[0].approvalPathModuleId;
 	              
 	           }           
         } else if (type == 'save') {
        	 if( $scope.correcttHistoryBtn == true){
        		 $scope.Messager('success', 'success', 'Transaction Details Updated Successfully',buttonDisable);
        	 }else{
	      	   if(response.data > 0){
		            	$scope.Messager('success', 'success', 'Transaction Details Saved Successfully',buttonDisable);
		            	$location.path('/approvalPathTransaction/'+response.data);
	      	   }else if(response.data == -1){
	      		   $scope.Messager('error', 'Error', 'Transaction Code alredy Exists....',buttonDisable);
	           }else{
	          	   $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
	           }
         	}
         } else if (type == 'transactionDatesChange') {
        	 $scope.transactionDatesList = response.data;
         } else if(type == 'historyChange'){
        	 $scope.transaction = response.data;
        	 $scope.transactionDate = $filter('date')(response.data.effectiveDate,'dd/MM/yyyy');
         }
     },
     function () { 
  	   $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
     });
 }

  
  	$scope.getData('approvalPathTransactionControler/getMaterDataForApprovalPathForTransaction.json', {"customerId":$scope.transaction.customerId,"companyId":$scope.transaction.companyId,"approvalPathTransactionInfoId":$scope.transaction.approvalPathTransactionInfoId,"isActive":$scope.transaction.isActive} , 'masterDataList');
 	
 	$scope.customerChange = function () {    	
    	$scope.getData('vendorController/getCompanyNamesAsDropDown.json',{customerId: ($scope.transaction.customerId == undefined || $scope.transaction.customerId == null )? '0' : $scope.transaction.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.transaction.companyId} , 'customerChange');
    };
	
    $scope.companyChange = function () {    	
    	$scope.getData('approvalPathModuleControler/getApprovalPathModules.json', { customerId:$scope.transaction.customerId, companyId: $scope.transaction.companyId, approvalPathModuleId :$scope.transaction.approvalPathModuleId} , 'companyChange');
    };

	$scope.countryChange = function () {
		$scope.getData('LocationController/getLocationsListBySearch.json',{customerId: ($scope.transaction.customerId == undefined || $scope.transaction.customerId == null )? '0' : $scope.transaction.customerId, companyId : ($scope.transaction.companyId == undefined || $scope.transaction.companyId == null) ? '0' : $scope.transaction.companyId, countryId : ($scope.transaction.countryId == undefined || $scope.transaction.countryId == null) ? '0' : $scope.transaction.countryId, status : 'Y'} , 'countryChange');
	};
 
 
  
 $scope.save = function ($event) {
	    if($("#transactionForm").valid()){      	
      		$scope.transaction.effectiveDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
      		$scope.transaction.createdBy = $cookieStore.get('createdBy');
      		$scope.transaction.modifiedBy = $cookieStore.get('modifiedBy');
      		if( $scope.correcttHistoryBtn == true){
      		}else{
      			$scope.transaction.approvalPathTransactionInfoId = 0;
      		}
      		$scope.getData('approvalPathTransactionControler/saveOrUpdateApprovalPathTransactionDetails.json', angular.toJson($scope.transaction), 'save');
	    }
 };
 
 $scope.transactionDatesListChange = function(){
     $scope.getData('approvalPathTransactionControler/getApprovalPathTransactionByTransactionInfoId.json', { "approvalPathTransactionInfoId":$scope.transaction.approvalPathTransactionInfoId }, 'historyChange')
     $('.dropdown-toggle').removeClass('disabled');
 }
}]);