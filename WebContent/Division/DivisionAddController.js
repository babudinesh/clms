'use strict';

DivisionController.controller("DivisionAddCtrl", ['$scope', '$http', '$resource','$location','$filter', '$routeParams','myservice','$cookieStore', function ($scope, $http, $resource, $location, $filter, $routeParams, myservice, $cookieStore) {
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
    $scope.division = new Object();
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
       $scope.division.divisionDetailsId = 0;
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
       $scope.getData('CompanyController/getDivisionTransactionDatesList.json', { companyId: $scope.division.companyId, customerId: $scope.division.customerId ,divisionId: $scope.division.divisionId }, 'transactionDatesChange');       
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
        $scope.getData('CompanyController/getDivisionById.json', { divisionDetailsId: $routeParams.id,customerId : ''}, 'divisionList')
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
   			} else if (type == 'divisionList') {
			   //alert(JSON.stringify(response.data));
               $scope.divisionList = response.data;
               
               if( response.data.divisionVo[0] != undefined){
            	   $scope.division = response.data.divisionVo[0];
            	   $scope.division.transactionDate =  $filter('date')( response.data.divisionVo[0].transactionDate, 'dd/MM/yyyy');
                   $scope.transDate1 =  $filter('date')( response.data.divisionVo[0].transactionDate, 'dd/MM/yyyy'); 
            	   $scope.divisionList.companyList = response.data.companyList;
            	   $scope.divisionList.countryList = response.data.countryList;
                 
               }else{
            	   $scope.division = new Object();
            	   $scope.division.transactionDate=  $filter('date')( new Date(), 'dd/MM/yyyy');
            	   $scope.division.status='Y';
            	   
            	   if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){
	                $scope.division.customerId=response.data.customerList[0].customerId;		                
	                $scope.customerChange();
	                
	                }
               }
            // for button views
 			  	if($scope.buttonArray == undefined || $scope.buttonArray == '')
               $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Division'}, 'buttonsAction');  
           }else if (type == 'customerChange') {
               $scope.divisionList.companyList = response.data;
               if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
                	$scope.division.companyId = response.data[0].id;
                	$scope.companyChange();
                }
               $scope.fun_checkDivisionCode();
               
           }else if (type == 'companyChange') {
			   //alert(JSON.stringify(response.data));
               $scope.divisionList.countryList = response.data;
               if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
   	              $scope.division.countryId = response.data[0].id;
   	           }
               $scope.fun_checkDivisionCode();
           }else if(type == 'validateCode'){
        	    //alert(response.data);
        	   	$scope.dCode= new Object();
        	   	$scope.dCode = response.data;
        	
        } else if (type == 'saveDivision') {
               //alert(angular.toJson(response.data));
        	   if(response.data.id > 0){
	            	$scope.Messager('success', 'success', 'Division Saved Successfully',buttonDisable);
	            	$location.path('/DivisionAdd/'+response.data.id);
        	   }else if(response.data.id == -1){
        		   $scope.Messager('error', 'Error', 'Transaction Date should be greater than or equal to company transaction date',buttonDisable);
               }else{
            	   $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
               }
           }else if (type == 'transactionDatesChange') {
            	//alert(JSON.stringify(response.data));
            	var k = 0;
            	
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
		        $scope.getData('CompanyController/getDivisionById.json', { divisionDetailsId: $scope.transactionModel,customerId : ''}, 'divisionList')
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
    
   $scope.getData('CompanyController/getDivisionById.json', { divisionDetailsId: $routeParams.id, customerId:  $cookieStore.get('customerId') }, 'divisionList')
   	
   $scope.customerChange = function () {
	   if($scope.division.customerId != undefined || $scope.division.customerId != null){
		   $scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.division.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.division.companyId  }, 'customerChange');
	   }
   };
   
   $scope.companyChange = function(){
	   if($scope.division.companyId != undefined || $scope.division.companyId != null){
	    	$scope.getData('vendorController/getcountryListByCompanyId.json', { companyId: $scope.division.companyId }, 'companyChange');
   		}
   };
   $scope.locationChange = function () {
   		//alert($scope.division.companyId);
	    $scope.getData('CommonController/statesListByCountryId.json', { countryId: $scope.division.countryId }, 'locationChange');
   };
	
   $scope.countryChange = function () {
		$scope.getData('LocationController/getLocationsListBySearch.json',{customerId: ($scope.division.customerId == undefined || $scope.division.customerId == null )? '0' : $scope.division.customerId, companyId : ($scope.division.companyId == undefined || $scope.division.companyId == null) ? '0' : $scope.division.companyId, countryId : ($scope.division.countryId == undefined || $scope.division.countryId == null) ? '0' : $scope.division.countryId, status : 'Y'} , 'countryChange');
   };
   
   $scope.fun_checkDivisionCode = function(){
    	if($scope.division.customerId != null || $scope.division.customerId != undefined || $scope.division.customerId != ''
    			&& ($scope.division.companyId != null || $scope.division.companyId != undefined || $scope.division.companyId != '')
    				    			&& ($scope.division.divisionCode != null || $scope.division.divisionCode != undefined || $scope.division.divisionCode != '')
    								&& ($scope.division.countryId != null || $scope.division.countryId != undefined || $scope.division.countryId != '')){
    		
    		//$scope.getData('CompanyController/validateDivisionCode.json',{ customerId : $scope.division.customerId, companyId: $scope.division.companyId, divisionCode:$scope.division.divisionCode, countryId :$scope.division.countryId },'validateCode')
    		$scope.getData('CompanyController/validateDivisionCode.json',{ customerId : $scope.division.customerId, companyId: $scope.division.companyId, divisionCode:$scope.division.divisionCode, countryId :$scope.division.countryId },'validateCode')

    	}
    };
   
    
   $scope.save = function ($event) {
	    if($("#divisionForm").valid()){
        	if($scope.dCode == 0){
	    		$scope.Messager('error', 'Error', 'Division code already exists'); 
	    	}else if($scope.transDate1 != '' && $scope.transDate1 != undefined && new Date(moment($scope.transDate1,'DD/MM/YYYY').format('YYYY-MM-DD')) .getTime() > new Date(moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime()){
	    		   $scope.Messager('error', 'Error', 'Transaction Date Should not be less than previous transaction date');
	        	   $scope.division.transactionDate =  $scope.transDate1;
	    	}else{
        		$scope.division.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
        		$scope.division.createdBy = $cookieStore.get('createdBy');
        		$scope.division.modifiedBy = $cookieStore.get('modifiedBy');
        		$scope.getData('CompanyController/saveDivision.json', angular.toJson($scope.division), 'saveDivision');
		  }
       }
   };
   
   $scope.correctHistorySave= function($event){
	   if($('#divisionForm').valid()){
		   $scope.division.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
		   $scope.division.modifiedBy = $cookieStore.get('modifiedBy');
		 //  alert(angular.toJson($scope.location));
		   $scope.getData('CompanyController/saveDivision.json', angular.toJson($scope.division), 'saveDivision');
	   }
   }

   $scope.transactionDatesListChange = function(){
       //alert("--" +$scope.transactionModel);
       $scope.getData('CompanyController/getDivisionById.json', { divisionDetailsId : ($scope.transactionModel != undefined || $scope.transactionModel != null) ? $scope.transactionModel:'0', customerId: "" }, 'divisionList')
       $('.dropdown-toggle').removeClass('disabled');
   }
	
		   
   
}]);


