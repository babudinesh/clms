'use strict';
var shiftController = angular.module("myapp.shifts", []);
shiftController.controller("shiftControllerDtls", ['$scope', '$rootScope', '$http', '$filter', '$resource','$location','$routeParams','$cookieStore', 'myservice', function($scope,$rootScope, $http,$filter,$resource,$location,$routeParams,$cookieStore,myservice) {
	
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
    
    $scope.shifts = new Object();
    $scope.shifts.isActive = 'Y';
    $scope.transactionDate = $filter('date')(new Date(),"dd/MM/yyyy");
    $scope.defaultPatternType = "Weekly";
    
    $scope.list_status = [{"id":"Y","name" : "Active" },
                                {"id":"N","name" : "In-Active" }];
    
    $scope.list_time_foramts = [{"id":"1","name" : "12 Hours" },
                              {"id":"2","name" : "24 Hours" }];
    
    
    $scope.list_time_Zones = [{"id":"1","name" : "GMT +0" },
                        {"id":"2","name" : "BST" }];
    
    $scope.Week_Days = [{"id":1,"name" : "MONDAY" },
                        {"id":2,"name" : "TUESDAY" },
                        {"id":3,"name" : "WEDNESDAY" },
                        {"id":4,"name" : "THURSDAY" },
                        {"id":5,"name" : "FRIDAY" },
                        {"id":6,"name" : "SATURDAY" },
                        {"id":7,"name" : "SUNDAY" }];
    $scope.list_defaultPatternType = [{"id":"Daily","name" : "Daily" },
                                      {"id":"Weekly","name" : "Weekly" },
                                      {"id":"Monthly","name" : "Monthly" }];
    
    
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
    		$scope.viewHistoryBtn = true;
    		$scope.readonlyEdit = true;
    		$scope.cancelEditBtn = false;
    		$scope.cancelBtn = true;	
		}else if (actionType =='DataNotAvailable'){
		
    		$scope.saveBtn = true;
    		$scope.cancelBtn = true;
    		$scope.transactionList = false;	
		}
	}
	$scope.fun_updateActionBtn = function(){
		$scope.readonly = false;
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
		$scope.getPostData("shiftsController/getTransactionDatesForShiftsHistory.json", { customerId : $scope.customerDetailsId , companyId : $scope.companyDetailsId , countryId : $scope.countryId } , "transactionDates")
	    $('.dropdown-toggle').removeClass('disabled');
		
	}
	$scope.fun_Shift_CancelBtn = function(){
		
		$scope.readonly = false;
		$scope.readonlyEdit = false;
		$scope.saveBtn = false;
		$scope.updateBtn = false;
		$scope.correctHistoryBtn = false;
		$scope.viewHistoryBtn = false;
		$scope.cancelBtn = true;
		$scope.cancelEditBtn = false;
		$scope.transactionList = false;		
		$scope.shifts = new Object();
		$scope.customerDetailsId = 0;
		$scope.companyDetailsId = 0;
		$scope.countryId = 0;
	}
	$scope.fun_Shift_Cancel_Edit_Btn = function(){		
		$scope.readonly = true;    		
		$scope.updateBtn = true;
		$scope.saveBtn = false;
		$scope.viewHistoryBtn = true;
		$scope.readonlyEdit = true;
		$scope.cancelEditBtn = false;
		$scope.cancelBtn = true;
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
            	$scope.currencyList = response.data.currencyList;     
            	 if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
		                $scope.customerDetailsId=response.data.customerList[0].customerId;		                
		                $scope.fun_CustomerChangeListener();
		                }
            	// for button views
 			  	if($scope.buttonArray == undefined || $scope.buttonArray == '')
            	 $scope.getPostData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Shift Defaults'}, 'buttonsAction');
            }else if (type == 'customerChange') {              	
            		$scope.companyList = response.data;            		
            		   if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
          	                $scope.companyDetailsId= response.data[0].id;
          	                $scope.fun_CompanyChangeListener();
          	                }
            }else if (type == 'companyChange') {              	
            	$scope.countriesList = response.data.countriesList;            	
            	if(response.data.shiftsVo[0].weekStartDay > 0){  
            		$scope.shifts = response.data.shiftsVo[0];
	            	$scope.countryId = response.data.shiftsVo[0].countryId; 
	            	$scope.transactionDate = $filter('date')(response.data.shiftsVo[0].transactionDate,"dd/MM/yyyy");            		
	            	fun_showHideButtons('DataAvailable');	            	
            	}else{            		
            		fun_showHideButtons('DataNotAvailable');
            		$scope.countryId = response.data.countriesList[0].id; 
            	}
            }else if(type == 'countryChange'){  
            	if( response.data.countryId > 0){
            		$scope.shifts = response.data;
            		$scope.transactionDate = $filter('date')(response.data.transactionDate,"dd/MM/yyyy");            	
            		$scope.readonly = true;
            		$scope.saveBtn = false;
            		$scope.updateBtn = true;
            		$scope.correctHistoryBtn = false;
            		$scope.resetBtn = false;
            		$scope.viewHistoryBtn = true;
            		$scope.cancelBtn = false;            		
            		$scope.transactionList = false;
            	}else{
            		$scope.readonly = false;
            		$scope.saveBtn = true;
            		$scope.updateBtn = false;
            		$scope.correctHistoryBtn = false;
            		$scope.resetBtn = true;
            		$scope.viewHistoryBtn = false;
            		$scope.cancelBtn = false;
            		$scope.returnTOSearchBtn = true;
            		$scope.transactionList = false;
            	}
            }else if(type == 'saveShiftRecord'){            	
            	$scope.readonly = true;
            	$scope.readonlyEdit = true;
        		$scope.updateBtn = true;
        		$scope.saveBtn = false;
        		$scope.viewHistoryBtn = true;        		
        		$scope.cancelEditBtn = false;
        		$scope.cancelBtn = true;
            }else if (type == 'saveCorrectHistory'){
            	
            }else if(type == 'transactionHistroyDatesChange'){
            	$scope.companyList = response.data.companyList;
            	$scope.countriesList = response.data.countriesList;
            	$scope.shifts = response.data.shiftsVo[0] ;
            	$scope.customerDetailsId = response.data.shiftsVo[0].customerDetailsId;
        		$scope.companyDetailsId = response.data.shiftsVo[0].companyDetailsId;
            	$scope.countryId = response.data.shiftsVo[0].countryId ; 
        		$scope.transactionDate = $filter('date')(response.data.shiftsVo[0].transactionDate,"dd/MM/yyyy");
            }else if(type == 'transactionDates'){
            	$scope.transactionDatesList = response.data.transactionDatesList;
            	$scope.shifts = response.data.shiftsVo[0];
            	$scope.countryId = response.data.shiftsVo[0].countryId; 
            	$scope.transactionDate = $filter('date')(response.data.shiftsVo[0].transactionDate,"dd/MM/yyyy");
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
	
	// Company Change Listener to get Countries List
	$scope.fun_CompanyChangeListener = function(){
		if($scope.customerDetailsId != undefined && $scope.customerDetailsId != '' && $scope.companyDetailsId != undefined && $scope.companyDetailsId != '')
		$scope.getPostData("shiftsController/getCountryNamesAsDropDown.json", { customerId : $scope.customerDetailsId , companyId : $scope.companyDetailsId }, "companyChange");	
	}
	
	// Country Change Listener to get Shift Data
	$scope.fun_CountryChangeListener = function(){
		 if( $scope.customerDetailsId != undefined && $scope.customerDetailsId != '' && $scope.companyDetailsId != undefined && $scope.companyDetailsId != '' && $scope.countryId != undefined &&  $scope.countryId != '')
		 $scope.getPostData("shiftsController/getShiftRecord.json", { customerId : $scope.customerDetailsId , companyId : $scope.companyDetailsId , countryId : $scope.countryId }, "countryChange");	
	}
	
	
	$scope.fun_save_shift_Details = function($event){
		if( $scope.shifts.isActive != 'Y'){
			$scope.Messager('error', 'Error', 'Please select Status...',angular.element($event.currentTarget));	
			return;
		}
		 if($('#shiftDefaultsDetails').valid()){
			$scope.shifts.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
			$scope.shifts.customerDetailsId  = $scope.customerDetailsId;
			$scope.shifts.companyDetailsId  = $scope.companyDetailsId;
			$scope.shifts.countryId  = $scope.countryId;
			$scope.shifts.createdBy = $cookieStore.get('createdBy'); 
		 	$scope.shifts.modifiedBy = $cookieStore.get('modifiedBy');
		 	  
			var shiftId =0;
			if($scope.saveBtn == true){
				shiftId = $scope.shifts.shiftId;
				$scope.shifts.shiftId = 0;
				$scope.getPostData("shiftsController/saveShiftRecord.json", angular.toJson($scope.shifts), "saveShiftRecord",angular.element($event.currentTarget));		
			}else {		
				$scope.getPostData("shiftsController/saveShiftRecord.json", angular.toJson($scope.shifts), "saveCorrectHistory",angular.element($event.currentTarget));		
			}
			if($scope.saveBtn == true){
				$scope.shifts.shiftId = shiftId;			
			}				
			$scope.Messager('success', 'success', 'Shift Record Saved Successfully',angular.element($event.currentTarget));			
		 }
	}
	
	$scope.fun_transactionDatesListChnage = function(){
		if($scope.shifts.shiftId != undefined && $scope.shifts.shiftId != '' )
		$scope.getPostData("shiftsController/getShiftRecordByShiftId.json", { shiftId : $scope.shifts.shiftId } , "transactionHistroyDatesChange");	
	}
	 
}]);
