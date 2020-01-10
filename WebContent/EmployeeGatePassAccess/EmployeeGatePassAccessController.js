'use strict';
var gatePassAcessDtlsController = angular.module("myapp.gatePassAcess", []);
gatePassAcessDtlsController.controller("EmployeeGatePassAccessCtrl", ['$scope', '$rootScope', '$http', '$filter', '$resource','$location','$routeParams','$cookieStore', 'myservice', function($scope,$rootScope, $http,$filter,$resource,$location,$routeParams,$cookieStore,myservice) {
	
	$.material.init();
    
   /* $('#transactionDate').bootstrapMaterialDatePicker({ 
    	time : false,
        Button : true,
        format : "DD/MM/YYYY",
        clearButton: true
    }); */
    
	 $( "#transactionDate" ).datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown'
	    	  
	    });
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
        
    $scope.transactionDate = $filter('date')(new Date(),"dd/MM/yyyy");       
    
    fun_showHideButtons('Default');
	// show hide buttons
	function fun_showHideButtons(actionType){
		
		if(actionType == "Default"){
			$scope.readonly = false;
			$scope.readonlyEdit = false;
			$scope.saveBtn = false;
			$scope.updateBtn = false;
			$scope.correctHistoryBtn = false;			
			$scope.viewHistoryBtn = false;
			$scope.cancelBtn = true;	
			$scope.transactionList = false;
			$scope.cancelEditBtn = false;
		}else if (actionType =='DataAvailable'){
			$scope.readonly = true;    		
    		$scope.updateBtn = true;
    		$scope.saveBtn = false;
    		$scope.viewHistoryBtn = true;
    		$scope.readonlyEdit = false;
    		$scope.cancelEditBtn = false;
    		$scope.cancelBtn = true;	
		}else if (actionType =='DataNotAvailable'){
			$scope.viewHistoryBtn = false;
			$scope.updateBtn = false;
    		$scope.saveBtn = true;
    		$scope.cancelBtn = true;
    		$scope.transactionList = false;	
		}
	}
	$scope.fun_updateActionBtn = function(){
		$scope.readonly = false;
		$scope.readonlyEdit = true;
		$scope.saveBtn = true;
		$scope.updateBtn = false;
		$scope.correctHistoryBtn = false;
		$scope.resetBtn = false;
		$scope.viewHistoryBtn = false;
		$scope.cancelBtn = false;
		$scope.cancelEditBtn = true;
		$scope.transactionList = false;
		$scope.transactionDate = $filter('date')(new Date(),"dd/MM/yyyy");
		$('.dropdown-toggle').removeClass('disabled');
	}
	$scope.fun_viewHistoryBtn = function(){
		$scope.readonly = false;
		$scope.saveBtn = false;
		$scope.updateBtn = false;
		$scope.correctHistoryBtn = true;
		$scope.resetBtn = false;
		$scope.viewHistoryBtn = false;
		$scope.cancelBtn = true;
		$scope.returnTOSearchBtn = true;
		$scope.transactionList = true;	
		$scope.getPostData("gatePassAccessController/getTransactionDatesForHistory.json", { customerId : $scope.customerDetailsId , companyId : $scope.companyDetailsId , countryId : $scope.countryId, locationId : $scope.locationDetailsId } , "transactionDates")
	    $('.dropdown-toggle').removeClass('disabled');
		
	}
	$scope.fun_gatePassAccess_CancelBtn = function(){
		
		$scope.getPostData("shiftsController/getShiftsGridData.json", { customerId: $cookieStore.get('customerId') }, "masterData");
		$scope.readonly = false;
		$scope.readonlyEdit = false;
		$scope.saveBtn = false;
		$scope.updateBtn = false;
		$scope.correctHistoryBtn = false;
		$scope.viewHistoryBtn = false;
		$scope.cancelBtn = true;
		$scope.cancelEditBtn = false;
		$scope.transactionList = false;					
		$scope.locationDetailsId = 0;		
		$scope.cuttOfHoursORMonth = '';		
		$scope.transactionDate = $filter('date')(new Date(),"dd/MM/yyyy");						
		
	}
	$scope.fun_gatePassAccess_Cancel_Edit_Btn = function(){		
		$scope.readonly = true;    		
		$scope.updateBtn = true;
		$scope.saveBtn = false;
		$scope.viewHistoryBtn = true;
		$scope.readonlyEdit = true;
		$scope.cancelEditBtn = false;
		$scope.cancelBtn = true;
		$scope.fun_LocationChangeListener();				
	}
	$scope.getPostData = function (url, data, type, buttonDisable) {
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
        	} else if (type == 'masterData') {  
            	$scope.customerList = response.data.customerList;            	  
            	 if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
		                $scope.customerDetailsId=response.data.customerList[0].customerId;		                
		                $scope.fun_CustomerChangeListener();
		                }
            	// for button views
 			  	if($scope.buttonArray == undefined || $scope.buttonArray == '')
            	 $scope.getPostData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Gate Pass Rules'}, 'buttonsAction');
            }else if (type == 'customerChange') {              	
            		$scope.companyList = response.data;            		
            		   if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
          	                $scope.companyDetailsId= response.data[0].id;
          	                $scope.fun_CompanyChangeListener();
          	                }
            }else if (type == 'companyChange') {               	
            	$scope.countriesList = response.data.countryList; 
            	$scope.countryId = response.data.countryList[0].id; 
            	$scope.locationList = response.data.locationList;             	            	
            }else if(type == 'locationChange'){
            	
            	if( response.data != undefined && response.data != '' && response.data.employeeGatePassAccessId != undefined && response.data.employeeGatePassAccessId != '' && response.data.employeeGatePassAccessId > 0){            		
            		$scope.cuttOfHoursORMonth = response.data.cuttOfHoursORMonth;  
            		$scope.employeeGatePassAccessId = response.data.employeeGatePassAccessId;
            		$scope.transactionDate = $filter('date')(response.data.transactionDate,"dd/MM/yyyy"); 
            		$('#transactionDate').val($filter('date')(response.data.transactionDate,"dd/MM/yyyy"));
            		fun_showHideButtons('DataAvailable');            		
            	}else{
            		$scope.cuttOfHoursORMonth = '';  
            		$scope.employeeGatePassAccessId = 0;
            		$scope.transactionDate = $filter('date')(new Date(),"dd/MM/yyyy");
            		fun_showHideButtons('DataNotAvailable');            		
            	}            	            	            	
            }else if(type == 'saveGatePassAccessRecord'){ 
            	if( response.data != undefined &&  response.data != '' &&  response.data > 0)
            		$scope.employeeGatePassAccessId = response.data;
            	
            	if($scope.saveBtn == true){
	            	$scope.readonly = true;
	            	$scope.readonlyEdit = false;
	        		$scope.updateBtn = true;
	        		$scope.saveBtn = false;
	        		$scope.viewHistoryBtn = true;        		
	        		$scope.cancelEditBtn = false;
	        		$scope.cancelBtn = true;
	        		$scope.Messager('success', 'success', 'Employee GatePass Record Saved Successfully',buttonDisable);	        		
	            }else{	            	
	            	$scope.Messager('success', 'success', 'Employee GatePass Record Updated Successfully',buttonDisable);
	            }
            }else if (type == 'saveCorrectHistory'){
            	
            }else if(type == 'transactionHistroyDatesChange'){
            	if(response.data != undefined && response.data != '' && response.data.accessVo[0].employeeGatePassAccessId != undefined && response.data.accessVo[0].employeeGatePassAccessId != '' && response.data.accessVo[0].employeeGatePassAccessId > 0){             		
            		
            		
            		$scope.customerDetailsId = response.data.accessVo[0].customerId;
            		$scope.companyDetailsId = response.data.accessVo[0].companyId;            		            	            		
            		$scope.countryId = response.data.accessVo[0].countryId;
            		$scope.cuttOfHoursORMonth = response.data.accessVo[0].cuttOfHoursORMonth;  
            		$scope.employeeGatePassAccessId = response.data.accessVo[0].employeeGatePassAccessId;
	        		$scope.transactionDate = $filter('date')(response.data.accessVo[0].transactionDate,"dd/MM/yyyy");
	        		$('#transactionDate').val($filter('date')(response.data.accessVo[0].transactionDate,"dd/MM/yyyy"));
	        		$scope.locationDetailsId = response.data.accessVo[0].locationId;
	        		
	        		$scope.companyList = response.data.companyList;
            		$scope.countriesList = response.data.countryList; 
            		$scope.locationList = response.data.locationList;            		
            	}else{
            		alert('error');
            	}
            }else if(type == 'transactionDates'){            	
            	$scope.transactionDatesList = response.data;            	            	
            }
            
        },
        function () {
       	 $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
       	//alert('Error')         	
       })
	};
	

	
    // GET MASTER DATA FOR DETAILS SCREEN   
	$scope.getPostData("shiftsController/getShiftsGridData.json", { customerId: $cookieStore.get('customerId') }, "masterData");		    

	// Customer Change Listener to get Companies List
	$scope.fun_CustomerChangeListener = function(){	
		if($scope.customerDetailsId != undefined && $scope.customerDetailsId != '')
		$scope.getPostData("vendorController/getCompanyNamesAsDropDown.json", { customerId : $scope.customerDetailsId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.companyDetailsId != undefined ? $scope.companyDetailsId : 0 }, "customerChange");	
	}
	
	// Company Change Listener to get Countries and Location List
	$scope.fun_CompanyChangeListener = function(){
		if($scope.customerDetailsId != undefined && $scope.customerDetailsId != '' && $scope.companyDetailsId != undefined && $scope.companyDetailsId != '')
		$scope.getPostData("gatePassAccessController/getlocationAndCountryDtlsByCompanyId.json", { customerId : $scope.customerDetailsId , companyId : $scope.companyDetailsId }, "companyChange");	
	}
	
	// Country Change Listener to get getPass Access Details
	$scope.fun_CountryChangeListener = function(){
		 if( $scope.locationDetailsId != undefined && $scope.locationDetailsId != '' && $scope.customerDetailsId != undefined && $scope.customerDetailsId != '' && $scope.companyDetailsId != undefined && $scope.companyDetailsId != '' && $scope.countryId != undefined &&  $scope.countryId != '')
		 $scope.getPostData("gatePassAccessController/getGatePassAccessDetails.json", { customerId : $scope.customerDetailsId , companyId : $scope.companyDetailsId , countryId : $scope.countryId, locationId : $scope.locationDetailsId }, "locationChange");	
	}
	
	// Location Change Listener to get getPass Access Details
	$scope.fun_LocationChangeListener = function(){
		
		if( $scope.locationDetailsId != undefined && $scope.locationDetailsId != '' && $scope.customerDetailsId != undefined && $scope.customerDetailsId != '' && $scope.companyDetailsId != undefined && $scope.companyDetailsId != '' && $scope.countryId != undefined &&  $scope.countryId != ''){
			 $scope.getPostData("gatePassAccessController/getGatePassAccessDetails.json", { customerId : $scope.customerDetailsId , companyId : $scope.companyDetailsId , countryId : $scope.countryId, locationId : $scope.locationDetailsId }, "locationChange");			 
		}
	}
	
	
	$scope.fun_save_gatePassAccess_Details = function($event){		
		 if($('#gatePassAcessDetails').valid()){
			$scope.gatePassAccess = new Object();
			$scope.gatePassAccess.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
			$scope.gatePassAccess.customerId  = $scope.customerDetailsId;
			$scope.gatePassAccess.companyId  = $scope.companyDetailsId;
			$scope.gatePassAccess.countryId  = $scope.countryId;
			$scope.gatePassAccess.locationId  = $scope.locationDetailsId;
			$scope.gatePassAccess.cuttOfHoursORMonth  = $scope.cuttOfHoursORMonth;
			$scope.gatePassAccess.createdBy = $cookieStore.get('createdBy'); 
		 	$scope.gatePassAccess.modifiedBy = $cookieStore.get('modifiedBy');
		 	
		 	if($scope.correctHistoryBtn == true){
		 		$scope.gatePassAccess.employeeGatePassAccessId = $scope.employeeGatePassAccessId;
		 	}
		 	$scope.getPostData("gatePassAccessController/saveGatePassAccessRecord.json", angular.toJson($scope.gatePassAccess), "saveGatePassAccessRecord",angular.element($event.currentTarget));		 						
						
		 }
	}
	
	$scope.fun_transactionDatesListChnage = function(){
		if($scope.employeeGatePassAccessId != undefined && $scope.employeeGatePassAccessId != '' && $scope.employeeGatePassAccessId > 0)
		$scope.getPostData("gatePassAccessController/getGatePassAccessRecordById.json", { employeeGatePassAccessId : $scope.employeeGatePassAccessId } , "transactionHistroyDatesChange");	
	}
	 
}]);
