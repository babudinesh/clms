'use strict';
var defineComplianceTypesController = angular.module("myapp.DefineComplianceTypes", []);

defineComplianceTypesController.controller("DefineComplianceTypesControllerDtls", ['$scope', '$rootScope', '$http', '$filter', '$resource','$location','$routeParams','$cookieStore', 'myservice', function($scope,$rootScope, $http,$filter,$resource,$location,$routeParams,$cookieStore,myservice) {
	
	$.material.init();
    
   /* $('#transactionDate').bootstrapMaterialDatePicker({ 
    	time : false,
        Button : true,
        format : "DD/MM/YYYY",
        clearButton: true
    }); */
    $('#transactionDate').datepicker({
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
       
    $scope.transactionList = true;
    $scope.defineComplianceTypes = new Object();
    
    $scope.defaultPatternType = "Weekly";
    
    $scope.list_status = [{"id":"Y","name" : "Active" },
                                {"id":"N","name" : "Inactive" }];
    
    $scope.list_applicableToList = [{"id":"Company","name" : "Company" },
                              {"id":"Vendor","name" : "Vendor" },
                              {"id":"Both","name" : "Both" } ];
    
    
    $scope.list_frequencyList = [{"id":"One Time","name" : "One Time" },
                                      {"id":"Monthly","name" : "Monthly" },
                                      {"id":"Quarterly","name" : "Quarterly" },
                                      {"id":"Half Yearly","name" : "Half Yearly" },
                                      {"id":"Yearly","name" : "Yearly" },
                                      {"id":"Not Applicable","name" : "Not Applicable" }];
    
   	$scope.list_DocumentNames = [{"id":1, "name":"Labour License"},
                                 {"id":2, "name":"WC Policy"},
                                 {"id":3, "name":"Labour Welfare Fund"},
                                 {"id":4, "name":"PF"},
                                 {"id":5, "name":"ESI"},
                                 {"id":6, "name":"Other"},
                                 {"id":7, "name":"Half yearly Return for Vendor"},
                                 {"id":8, "name":"Annual Return for PE"}];
    	
    
    // $scope.complianceList = [{"currencyName":"currencyName1"},{"currencyName":"currencyName2"},{"currencyName":"currencyName3"}];
    
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
			$scope.gridButtons = true;
			$scope.transactionList = false;
			$scope.cancelEditBtn = false;
		}else if (actionType =='DataAvailable'){
			$scope.readonly = true;    		
			$scope.updateBtn = true;
			$scope.viewHistoryBtn = true;
			$scope.readonlyEdit = true;
			$scope.cancelEditBtn = false;
			$scope.cancelBtn = true;	
			$scope.saveBtn = false;
			$scope.gridButtons = true;
		}else if (actionType =='DataNotAvailable'){
			
			$scope.saveBtn = true;
			$scope.cancelBtn = true;
			$scope.gridButtons = true;
			$scope.transactionList = false;	
		}
	};
	
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
	};
	
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
		$scope.getPostData("defineComplianceTypesController/getTransactionDatesofHistory.json", { customerId : $scope.customerDetailsId , companyId : $scope.companyDetailsId } , "transactionDates")
	    $('.dropdown-toggle').removeClass('disabled');
		
	};
	
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
		$scope.country = 0;
		$scope.transactionDate = $filter('date')(new Date(),"dd/MM/yyyy");
		$scope.defineComplianceTypes.complianceList = [];
		//$scope.transactionDate = "";
	};
	
	$scope.fun_Shift_Cancel_Edit_Btn = function(){		
		$scope.readonly = true;    		
		$scope.updateBtn = true;
		$scope.saveBtn = false;
		$scope.viewHistoryBtn = true;
		$scope.readonlyEdit = true;
		$scope.cancelEditBtn = false;
		$scope.cancelBtn = true;
	};
	
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
            if (type == 'masterData') {  
            	$scope.customerList = response.data.customerList;     
            	 if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
		                $scope.customerDetailsId=response.data.customerList[0].customerId;		                
		                $scope.fun_CustomerChangeListener();
		        }
            } else if(type == 'buttonsAction'){
            	$scope.buttonArray = response.data.screenButtonNamesArray;
            } else if (type == 'customerChange') {              	
            		$scope.companyList = response.data;
            		
            		 if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
        	                $scope.companyDetailsId = response.data[0].id;
        	                $scope.fun_CompanyChangeListener();
        	                }
            		 
            }else if (type == 'companyChange') {              	
            	$scope.countriesList = response.data.countriesList; 
            	  if( response.data != undefined && response.data.countriesList != undefined && response.data.countriesList.length == 1 ){
            		  $scope.country = response.data.countriesList[0].id;
            		  $scope.countryChange();
            	  }
            	
            	
            }else if(type == 'saveRecord'){            	
        		if(response.data.id > 0 ){
        			$scope.Messager('success', 'Success', ' Record Saved Successfully');
        			$scope.getPostData("defineComplianceTypesController/getCountryNamesAsDropDown.json", { customerId : $scope.customerDetailsId , companyId : $scope.companyDetailsId }, "companyChange");	
        		}else{
        			$scope.Messager('error', 'Error', ' Technical issue occurred. Please try again after some time...',angular.element($event.currentTarget));
        		}
            }else if (type == 'saveCorrectHistory'){
            	
        		if(response.data.id > 0){
        			$scope.Messager('success', 'success', ' Record Updated Successfully',angular.element($event.currentTarget));
        			/*$scope.readonly = false;
            		$scope.saveBtn = false;
            		$scope.updateBtn = false;
            		$scope.correctHistoryBtn = true;
            		$scope.resetBtn = false;
            		$scope.viewHistoryBtn = false;
            		$scope.cancelBtn = true;
            		$scope.returnTOSearchBtn = true;
            		$scope.transactionList = true;*/	
        		}else{
        			$scope.Messager('error', 'Error', ' Technical issue occurred. Please try again after some time...',angular.element($event.currentTarget));
        		}
            }else if(type == 'transactionHistroyDatesChange'){
            	$scope.countriesList = response.data.countriesList; 
            	  if( response.data != undefined && response.data.countriesList != undefined && response.data.countriesList.length == 1 ){
            		  $scope.country = response.data.countriesList[0].id;
            		  $scope.countryChange();
            	  }            	
            	if(Object.prototype.toString.call(response.data.complianceList) === '[object Array]' && response.data.complianceList.length > 0){            		
            		$scope.defineComplianceTypes.complianceList  = response.data.complianceList;	            		
            		$scope.companyList = response.data.companyList;
            		$scope.transactionModel = response.data.complianceList[0].defineComplianceTypeId;
            		$scope.customerDetailsId =response.data.complianceList[0].customerDetailsId;
        			$scope.companyDetailsId = response.data.complianceList[0].companyDetailsId;
        			$scope.country = $scope.country;       			
	            	$scope.transactionDate = $filter('date')(response.data.complianceList[0].transactionDate,"dd/MM/yyyy"); 
            	}
            	
            }else if(type == 'transactionDates'){
            	$scope.transactionDatesList = response.data.transactionDatesList;            	
            }else if(type == 'countryChange'){
	        	   $scope.locationList = response.data;
	        	   if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	   	                $scope.locationId = response.data[0].locationId;
	   	                $scope.locationChange();
	   	           }
	           }else if(type == 'locationChange'){
	        	   if(Object.prototype.toString.call(response.data.complianceList) === '[object Array]' && response.data.complianceList.length > 0){            		
	            		$scope.defineComplianceTypes.complianceList  = response.data.complianceList;	            		
	            		$scope.companyList = response.data.companyList;
	            		$scope.transactionModel = response.data.complianceList[0].defineComplianceTypeId;
	            		$scope.customerDetailsId =response.data.complianceList[0].customerDetailsId;
	        			$scope.companyDetailsId = response.data.complianceList[0].companyDetailsId;
	        			$scope.country = $scope.country;       			
		            	$scope.transactionDate = $filter('date')(response.data.complianceList[0].transactionDate,"dd/MM/yyyy");            		
		            	fun_showHideButtons('DataAvailable');	            	
	            	}else{            		
	            		fun_showHideButtons('DataNotAvailable');            		
	            	}
	           }
            
        },function () {
       	 	$scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
        })
	};
	
	
	// Customer Change Listener to get Companies List
	$scope.fun_CustomerChangeListener = function(){	
		if($scope.customerDetailsId != undefined && $scope.customerDetailsId != '')
		$scope.getPostData("vendorController/getCompanyNamesAsDropDown.json", { customerId : $scope.customerDetailsId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.companyDetailsId != undefined ? $scope.companyDetailsId : 0 }, "customerChange");	
	};
	
	// Company Change Listener to get Countries List
	$scope.fun_CompanyChangeListener = function(){
		if($scope.customerDetailsId != undefined && $scope.customerDetailsId != '' && $scope.companyDetailsId != undefined && $scope.companyDetailsId != '')
		$scope.getPostData("defineComplianceTypesController/getCountryNamesAsDropDown.json", { customerId : $scope.customerDetailsId , companyId : $scope.companyDetailsId }, "companyChange");	
	};
	
	
	$scope.countryChange = function(){
		if($scope.country != undefined || $scope.country != null){
			$scope.getPostData('LocationController/getLocationsListBySearch.json',{customerId: ($scope.customerDetailsId == undefined || $scope.customerDetailsId == null )? '0' : $scope.customerDetailsId, companyId : ($scope.companyDetailsId == undefined || $scope.companyDetailsId == null) ? '0' : $scope.companyDetailsId, countryId : ($scope.country == undefined || $scope.country == null) ? '0' : $scope.country } , 'countryChange');
		}
		
	}
	
	
	$scope.locationChange = function(){		
		if($scope.locationId != undefined && $scope.locationId != null && $scope.locationId > 0){
			$scope.getPostData('defineComplianceTypesController/getComplianceListByLocationId.json',{customerId: ($scope.customerDetailsId == undefined || $scope.customerDetailsId == null )? '0' : $scope.customerDetailsId, companyId : ($scope.companyDetailsId == undefined || $scope.companyDetailsId == null) ? '0' : $scope.companyDetailsId, locationId : ($scope.locationId == undefined || $scope.locationId == null) ? '0' : $scope.locationId } , 'locationChange');
		}else{
			$scope.defineComplianceTypes.complianceList = [];
		}
	}
	
	
	
	// GET MASTER DATA FOR DETAILS SCREEN   
	$scope.getPostData("shiftsController/getShiftsGridData.json", { customerId : $cookieStore.get('customerId') }, "masterData");
	
	// for button views
	  	if($scope.buttonArray == undefined || $scope.buttonArray == '')
	$scope.getPostData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Define Compliance Types'}, 'buttonsAction');

	
	
	
	$scope.fun_save_Details = function($event){		
		 if($('#defineComplianceTypes').valid()){			
			$scope.commilance = new Object();
			$scope.commilance.customerDetailsId = $scope.customerDetailsId;
			$scope.commilance.companyDetailsId = $scope.companyDetailsId;
			$scope.commilance.country = $scope.country;
			$scope.commilance.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 			
			$scope.commilance.createdBy = $cookieStore.get('createdBy'); 
		 	$scope.commilance.modifiedBy = $cookieStore.get('modifiedBy');
		 	$scope.commilance.complianceList = $scope.defineComplianceTypes.complianceList;
		 	$scope.commilance.complianceCode = 'complianceCode';
		 	$scope.commilance.locationId = $scope.locationId;
			//alert( angular.toJson($scope.commilance));
		 	if($scope.correctHistoryBtn == true){
		 		$scope.commilance.defineComplianceTypeId = 	 $scope.transactionModel;
		 		$scope.getPostData("defineComplianceTypesController/saveCompilance.json", angular.toJson($scope.commilance), "saveCorrectHistory");
		 		//$scope.Messager('success', 'success', ' Record Updated Successfully',angular.element($event.currentTarget));
		 	}else{
		 		$scope.getPostData("defineComplianceTypesController/saveCompilance.json", angular.toJson($scope.commilance), "saveRecord");
		 		//$scope.Messager('success', 'success', ' Record Saved Successfully',angular.element($event.currentTarget));
		 	}				
		 }
	};
	
	$scope.fun_transactionDatesListChnage = function(){
		if($scope.transactionModel != undefined && $scope.transactionModel != '' )
			$scope.getPostData("defineComplianceTypesController/getComplianceRecordById.json", { defineComplianceTypeId : $scope.transactionModel } , "transactionHistroyDatesChange");	
	};
	
	
	$scope.plusIconAction = function(){
		$scope.clearPopUP();
		$scope.isActive = 'Y';
		$scope.onlyRead = false;
		$scope.transactionDate = $filter('date')(new Date(),"dd/MM/yyyy");
    	$scope.popUpSave = true;
    	$scope.popUpUpdate =false;
    	$scope.isMandatory = false;
    };
	
	
	$scope.clearPopUP = function(){
		$scope.complianceAct = null;
		$scope.doccumentName = null;
		$scope.complianceUniqueId = "";
		$scope.doccumentDescription = null;
		$scope.isMandatory = false;
		$scope.isActive = 'Y';
		$scope.applicableTo = "";
		$scope.frequency = "";
	};
	
	$scope.addDetailstoList = function(){ 	
		var status = false;
		if($('#defineComplianceTypespopup').valid()){ 
			if($scope.defineComplianceTypes.complianceList == undefined){
				$scope.defineComplianceTypes.complianceList =[];
			}
			angular.forEach($scope.defineComplianceTypes.complianceList, function(value,key){
				if(value.complianceUniqueId == $scope.complianceUniqueId){
					status = true;
				}
			});
			
			if(status){
				$scope.Messager('error', 'Error', 'This document already exists. Please select other document',true);
			}else{
			/* if($scope.complianceAct != undefined && $scope.complianceAct != null && $scope.complianceAct != ''
				 		&& $scope.doccumentName != undefined && $scope.doccumentName != null && $scope.doccumentName != ''
				 		&& $scope.doccumentDescription != undefined && $scope.doccumentDescription != null && $scope.doccumentDescription != ''
					 	&& $scope.applicableTo != undefined && $scope.applicableTo != null && $scope.applicableTo != ''
					 	&& $scope.frequency != undefined && $scope.frequency != null && $scope.frequency != ''
					 	&& $scope.isActive != undefined && $scope.isActive != null && $scope.isActive != ''){*/
		    	$scope.defineComplianceTypes.complianceList.push({		    		
		    		'complianceAct':$scope.complianceAct,  
		    		//'doccumentName':$scope.doccumentName,  
		    		'doccumentName':$('#doccumentName  option:selected').html(),
		    		'complianceUniqueId':$scope.complianceUniqueId,
		    		'doccumentDescription':$scope.doccumentDescription,  
		    		'applicableTo':$scope.applicableTo,    		
		    		'frequency':$scope.frequency,   
		    		//'isActive':$scope.isActive,
		    		'isActive':$('#isActive option:Selected').html(),
		    		//'complianceUniqueId':0,
		    		'isMandatory':$scope.isMandatory
		    	});  
		    	$scope.clearPopUP();
		    	$('#myModal').modal('hide');
			}
		}
	 };
	    
	$scope.Edit = function($event){  	
		$scope.complianceListIndex = $($event.target).parent().parent().index();
		
		var data = $scope.defineComplianceTypes.complianceList[$($event.target).parent().parent().index()];
		$scope.complianceAct = data.complianceAct;
		$scope.doccumentName = data.doccumentName;
		$scope.onlyRead = true;
		$scope.isActive = data.isActive == "Active" ? "Y" : "N";
		$scope.complianceUniqueId = data.complianceUniqueId;
		$scope.doccumentDescription = data.doccumentDescription;
		$scope.applicableTo = data.applicableTo;
		$scope.frequency = data.frequency;	
		$scope.isMandatory = data.isMandatory;
		
		$scope.complianceAct1 = data.complianceAct;
		$scope.doccumentName1 = data.doccumentName;
		$scope.onlyRead1 = true;
		$scope.isActive1 = data.isActive == "Active" ? "Y" : "N";
		$scope.complianceUniqueId1 = data.complianceUniqueId;
		$scope.doccumentDescription1 = data.doccumentDescription;
		$scope.applicableTo1 = data.applicableTo;
		$scope.frequency1 = data.frequency;	
		$scope.isMandatory1 = data.isMandatory;
		
		$scope.onlyRead = true;
		$scope.popUpSave = false;
		$scope.popUpUpdate =true;
	};
	    
	    
	$scope.updateDetailstoList= function($event){    	
		if($('#defineComplianceTypespopup').valid()){ 
	    	$scope.defineComplianceTypes.complianceList.splice($scope.complianceListIndex,1);    
	    	
	    	/* if($scope.complianceAct != undefined && $scope.complianceAct != null && $scope.complianceAct != ''
			 		&& $scope.doccumentName != undefined && $scope.doccumentName != null && $scope.doccumentName != ''
			 		&& $scope.doccumentDescription != undefined && $scope.doccumentDescription != null && $scope.doccumentDescription != ''
				 	&& $scope.applicableTo != undefined && $scope.applicableTo != null && $scope.applicableTo != ''
				 	&& $scope.frequency != undefined && $scope.frequency != null && $scope.frequency != ''
				 	&& $scope.isActive != undefined && $scope.isActive != null && $scope.isActive != ''){*/
			 $scope.defineComplianceTypes.complianceList.push({	    		
	    		'complianceAct':$scope.complianceAct,  
	    		//'doccumentName':$scope.doccumentName, 
	    		'doccumentName':$('#doccumentName  option:selected').html(),
	    		'complianceUniqueId':$scope.complianceUniqueId,
	    		'doccumentDescription':$scope.doccumentDescription,  
	    		'applicableTo':$scope.applicableTo,    		
	    		'frequency':$scope.frequency,   
	    		'isActive':$('#isActive option:Selected').html(),
	    		//'complianceUniqueId':$scope.complianceUniqueId,
	    		'isMandatory':$scope.isMandatory
	    	});
	    	//$scope.clearPopUP();
	    	$('#myModal').modal('hide');
			
		}
	};
	    
    /*$scope.Delete = function($event){   
    	var isDelete = confirm('Are you sure to delete this Compliance Act? ');
    	if(isDelete){
    		$scope.defineComplianceTypes.complianceList.splice($($event.target).parent().parent().index(),1);
    		//$scope.Messager('success','Success',"Compliance deleted successfully");
    	}
    };*/
    
    $scope.statusChange= function(){
    	if($scope.isActive == 'Y'){
    		$scope.status = "Active";
    	}else{
    		$scope.status = "Inactive";
    	}
    };
	    
	$scope.fun_close = function(){
		if($scope.popUpUpdate){
	    	$scope.complianceAct = $scope.complianceAct1;
			$scope.doccumentName = $scope.doccumentName1;
			$scope.isActive = $scope.isActive1 == "Active" ? "Y" : "N";
			$scope.complianceUniqueId = $scope.complianceUniqueId1;
			$scope.doccumentDescription = $scope.doccumentDescription1;
			$scope.applicableTo = $scope.applicableTo1;
			$scope.frequency = $scope.frequency1;	
			$scope.isMandatory = $scope.isMandatory1;
			$('#myModal').modal('hide');
    	}else{
    		$scope.clearPopUP();
    		$('#myModal').modal('hide');
    	}
	};
	
	$scope.validateDocument = function(){
		if($scope.complianceUniqueId == 1){
			//$scope.$apply(function(){ $scope.isMandatory = true; });
			$scope.isMandatory = true;
		}
		angular.forEach($scope.defineComplianceTypes.complianceList, function(value,key){
			if(value.complianceUniqueId == $scope.complianceUniqueId){
				$scope.Messager('error', 'Error', 'This document already exists. Please select other document',true);
			}
		});
	};
	 
}])/*.directive('onLastRepeat', function () {
    return function (scope, element, attrs) {
        if (scope.$last) {
            setTimeout(function () {
                $('.table').DataTable();
            }, 1);
        }
    };
})*/;
