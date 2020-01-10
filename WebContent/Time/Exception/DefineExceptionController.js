'use strict';

ExceptionController.controller("ExceptionAddCtrl", ['$scope', '$http', '$resource','$location','$filter', '$routeParams','myservice','$cookieStore', function ($scope, $http, $resource, $location, $filter, $routeParams, myservice, $cookieStore) {
	$.material.init();
   /* $('#transactionDate').bootstrapMaterialDatePicker({
    	time: false,
    	clearButton: true,
    	format : "DD/MM/YYYY"
    });*/
	
	 $('#transactionDate').datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown'
	    	  
	    });
    $scope.exception = new Object();
   if ($routeParams.id > 0) {
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
	   $scope.dCode = 1;
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
       $scope.exception.exceptionId = 0;
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
       $scope.getData('timeRulesController/getTransactionDatesListForException.json', { companyId: $scope.exception.companyId, customerId: $scope.exception.customerId ,exceptionId: $scope.exception.exceptionUniqueId }, 'transactionDatesChange');       
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
        $scope.getData('timeRulesController/getExceptionById.json', { exceptionDetailsId: $routeParams.id,customerId : ''}, 'exceptionList')
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
    	   if(type == 'buttonsAction'){
    			$scope.buttonArray = response.data.screenButtonNamesArray;
    		} else if (type == 'exceptionList') {
			   //alert(JSON.stringify(response.data));
               $scope.exceptionList = response.data;
               
               if( response.data.exceptionVo[0] != undefined){
            	   $scope.exception = response.data.exceptionVo[0];
            	   $scope.exception.transactionDate =  $filter('date')( response.data.exceptionVo[0].transactionDate, 'dd/MM/yyyy');
                   $scope.transDate1 =  $filter('date')( response.data.exceptionVo[0].transactionDate, 'dd/MM/yyyy'); 
            	   $scope.exceptionList.companyList = response.data.companyList;
            	   $scope.exceptionList.countryList = response.data.countryList;
                 
               }else{
            	   $scope.exception = new Object();
            	   $scope.exception.transactionDate=  $filter('date')( new Date(), 'dd/MM/yyyy');
            	   $scope.exception.effectiveStatus='Y';
            	   
            	   if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){
	                $scope.exception.customerId=response.data.customerList[0].customerId;		                
	                $scope.customerChange();
	                
	                }
               }
            // for button views
               if($scope.buttonArray == undefined || $scope.buttonArray == '')
               $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Exception'}, 'buttonsAction');
           }else if (type == 'customerChange') {
               $scope.exceptionList.companyList = response.data;
               if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
                	$scope.exception.companyId = response.data[0].id;
                	$scope.companyChange();
                }
               $scope.fun_checkExceptionCode();
               
           }else if (type == 'companyChange') {
			   //alert(JSON.stringify(response.data));
               $scope.exceptionList.countryList = response.data;
               if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
   	              $scope.exception.countryId = response.data[0].id;
   	           }
               $scope.fun_checkExceptionCode();
           }else if(type == 'validateCode'){
        	    //alert(response.data);
        	   	$scope.dCode= new Object();
        	   	$scope.dCode = response.data;
        	
        } else if (type == 'saveException') {
               //alert(angular.toJson(response.data));
        	   if(response.data.id > 0 && $scope.saveBtn){
	            	$scope.Messager('success', 'success', 'Exception Saved Successfully',buttonDisable);
	            	$location.path('/addException/'+response.data.id);
        	   }else if(response.data.id > 0 && !$scope.saveBtn){
	            	$scope.Messager('success', 'success', 'Exception Saved Successfully',buttonDisable);
	            	$scope.exception.transactionDate =  $filter('date')( $('#transactionDate').val(), 'dd/MM/yyyy');
        	   }else if(response.data.id == -1){
        		   $scope.Messager('error', 'Error', 'Transaction Date should be greater than or equal to company transaction date',buttonDisable);
               }else{
            	   $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
               }
           }else if (type == 'transactionDatesChange') {
            	//alert(JSON.stringify(response.data));
            	var k = 0;
            	if(response.data != null ){
	            	if(response.data.length > 1){
		            	for(var i = response.data.length; i>0; i--){
			            	 if($scope.dateDiffer(response.data[i-1].name.split('-')[0])){
			            		 k = response.data[i-1].id;
			            		 break;
			            	 }
		            	}
	            	}else{
	            		k = response.data[0].id;
	            	}
	            	$scope.transactionDatesList = response.data;
	            	$scope.transactionModel=k;
			        $scope.getData('timeRulesController/getExceptionById.json', { exceptionId: $scope.transactionModel,customerId : ''}, 'exceptionList')
            	}
            }
       },
       function () { 
    	   $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
       });
   }

    $scope.dateDiffer = function(date1){
       	var cmpDate = new Date( moment(date1,'DD/MM/YYYY').format('YYYY-MM-DD HH:MM:SS'));

    	   if(cmpDate.getTime() <= (new Date()).getTime()){
       			return true;
    	   }else{
    		   return false;       	
    	   }
    };
    
   $scope.getData('timeRulesController/getExceptionById.json', { exceptionId: $routeParams.id, customerId:  $cookieStore.get('customerId') }, 'exceptionList')
   	
   $scope.customerChange = function () {
	   if($scope.exception.customerId != undefined || $scope.exception.customerId != null){
		   $scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.exception.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.exception.companyId  }, 'customerChange');
	   }
   };
   
   $scope.companyChange = function(){
	   if($scope.exception.companyId != undefined || $scope.exception.companyId != null){
	    	$scope.getData('vendorController/getcountryListByCompanyId.json', { companyId: $scope.exception.companyId }, 'companyChange');
   		}
   };
  /* $scope.locationChange = function () {
   		//alert($scope.exception.companyId);
	    $scope.getData('CommonController/statesListByCountryId.json', { countryId: $scope.exception.countryId }, 'locationChange');
   };
	
   $scope.countryChange = function () {
		$scope.getData('LocationController/getLocationsListBySearch.json',{customerId: ($scope.exception.customerId == undefined || $scope.exception.customerId == null )? '0' : $scope.exception.customerId, companyId : ($scope.exception.companyId == undefined || $scope.exception.companyId == null) ? '0' : $scope.exception.companyId, countryId : ($scope.exception.countryId == undefined || $scope.exception.countryId == null) ? '0' : $scope.exception.countryId, status : 'Y'} , 'countryChange');
   };*/
   
   $scope.fun_checkExceptionCode = function(){
    	if($scope.exception.customerId != null || $scope.exception.customerId != undefined || $scope.exception.customerId != ''
    			&& ($scope.exception.companyId != null || $scope.exception.companyId != undefined || $scope.exception.companyId != '')
    				    			&& ($scope.exception.exceptionCode != null || $scope.exception.exceptionCode != undefined || $scope.exception.exceptionCode != '')
    								&& ($scope.exception.countryId != null || $scope.exception.countryId != undefined || $scope.exception.countryId != '')){
    		
    		//$scope.getData('CompanyController/validateExceptionCode.json',{ customerId : $scope.exception.customerId, companyId: $scope.exception.companyId, exceptionCode:$scope.exception.exceptionCode, countryId :$scope.exception.countryId },'validateCode')
    		$scope.getData('timeRulesController/validateExceptionCode.json',{ customerId : $scope.exception.customerId, companyId: $scope.exception.companyId, exceptionCode:$scope.exception.exceptionCode, countryId :$scope.exception.countryId },'validateCode')

    	}
    };
   
    
   $scope.save = function ($event) {
	    if($("#exceptionForm").valid()){
        	if($scope.dCode == 0){
	    		$scope.Messager('error', 'Error', 'Exception code already exists'); 
	    	}else if($scope.transDate1 != '' && $scope.transDate1 != undefined && new Date(moment($scope.transDate1,'DD/MM/YYYY').format('YYYY-MM-DD')) .getTime() > new Date(moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime()){
	    		   $scope.Messager('error', 'Error', 'Transaction Date Should not be less than previous transaction date');
	        	   $scope.exception.transactionDate =  $scope.transDate1;
	    	}else{
        		$scope.exception.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
        		$scope.exception.createdBy = $cookieStore.get('createdBy');
        		$scope.exception.modifiedBy = $cookieStore.get('modifiedBy');
        		$scope.getData('timeRulesController/saveException.json', angular.toJson($scope.exception), 'saveException');
		  }
       }
   };
   
   $scope.correctHistorySave= function($event){
	   if($('#exceptionForm').valid()){
		   $scope.exception.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
		   $scope.exception.modifiedBy = $cookieStore.get('modifiedBy');
		 //  alert(angular.toJson($scope.location));
		   $scope.getData('timeRulesController/saveException.json', angular.toJson($scope.exception), 'saveException');
	   }
   }

   $scope.transactionDatesListChange = function(){
       //alert("--" +$scope.transactionModel);
       $scope.getData('timeRulesController/getExceptionById.json', { exceptionId : ($scope.transactionModel != undefined || $scope.transactionModel != null) ? $scope.transactionModel:'0', customerId: "" }, 'exceptionList')
       $('.dropdown-toggle').removeClass('disabled');
   }
	
		   
   
}]);


