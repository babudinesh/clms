'use strict';
var JobAllocationReportController = angular.module("myApp.JobAllocationReport", ['ngCookies']);

JobAllocationReportController.controller("JobAllocationReportCtrl", ['$scope', '$sce', '$http', '$resource','$location','$filter', '$routeParams','myservice','$cookieStore','$rootScope', function ($scope, $sce, $http, $resource, $location, $filter, $routeParams, myservice, $cookieStore, $rootScope) {		
		$scope.status = "Unassigned";
		$scope.jobType = "-1";
		$.material.init();	
		$scope.searchPeriod="byDate";
		$("#loader").hide(); 
		$("#data_container").hide();
	    $scope.dropdownDisableCustomer = true;
	    
	    
	    $scope.list_skills = [{"id":"Skilled","name" : "Skilled" },
	  	                       {"id":"Semi Skilled","name" : "Semi Skilled" },
	  	                       {"id":"High Skilled","name" : "High Skilled" },
	  	                       {"id":"Special Skilled","name" : "Special Skilled" },
	  	                       {"id":"UnSkilled","name" : "UnSkilled" }];
	    
	    if($cookieStore.get('customerId') != null && $cookieStore.get('customerId') != undefined && $cookieStore.get('customerId') != 0){
	    	$scope.dropdownDisableCustomer = true;
	    }else{
	    	$scope.dropdownDisableCustomer = false;
	    }
	    
	    if($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != undefined && $cookieStore.get('companyId') != 0){
	    	$scope.dropdownDisableCompany = true;
	    }else{
	    	$scope.dropdownDisableCompany = false;
	    }
	    
	    if($cookieStore.get('vendorId') != null && $cookieStore.get('vendorId') != undefined && $cookieStore.get('vendorId') != 0){
	    	$scope.dropdownDisableVendor = true;
	    }else{
	    	$scope.dropdownDisableVendor = false;
	    }
	    
	    $scope.list_years = $rootScope.getYears; 
	   
	     $('#toDate, #fromDate').datepicker({
	         changeMonth: true,
	         changeYear: true,
	         dateFormat:"dd/mm/yy",
	         showAnim: 'slideDown'
	       	  
	       });
	     
	     $scope.list_months = [{"id":"1","name":"January"},
	                           {"id":"2","name":"February"},
	                           {"id":"3","name":"March"},
	                           {"id":"4","name":"April"},
	                           {"id":"5","name":"May"},
	                           {"id":"6","name":"June"},
	                           {"id":"7","name":"July"},
	                           {"id":"8","name":"August"},
	                           {"id":"9","name":"September"},
	                           {"id":"10","name":"October"},
	                           {"id":"11","name":"November"},
	                           {"id":"12","name":"December"}];
	     
	     $scope.list_status = [{"id":"-1", "name":"ALL"},
	                           {"id":"Assigned", "name":"Assigned"},
	 	                       {"id":"Unassigned", "name":"Unassigned"},
	 	                       {"id":"Approved", "name":"Approved"},
	 	                       {"id":"Rejected", "name":"Rejected"}];
	    
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
	     
	    $scope.getData = function (url, data, type,buttonDisable) {
	        var req = {
	            method: 'POST',
	            url: ROOTURL+url,
	            headers: {
	                'Content-Type': 'application/json'
	            },
	            data:data
	        }
	        $http(req).then(function (response) {
	        	if(type == 'buttonsAction'){
	           		$scope.buttonArray = response.data.screenButtonNamesArray;
	        	} else if (type == 'customerList'){
	                $scope.customerList = response.data.customerList;
	                if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
   		                $scope.customerName = response.data.customerList[0].customerId;		
   		                $scope.customerChange();
   		            }	
	                // for button views
	       		   if($scope.buttonArray == undefined || $scope.buttonArray == '')
	       			   	$scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Job Allocation Report'}, 'buttonsAction');
	            }else if (type == 'customerChange'){
	                $scope.companyList = response.data;
	               
	                if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	   	                $scope.companyName = response.data[0].id;
						$scope.companyChange();
	   	            }
	            }else if (type == 'companyChange') {	            	
	             	$scope.vendorList = response.data.vendorList;
	             	$scope.locationsList = response.data.locationsList;
	             	$scope.shiftsList = response.data.shiftCodes;
	             	//$scope.complianceTypeList = response.data.complianceTypesList;
	             	if( response.data.vendorList[0] != undefined && response.data.vendorList[0] != "" && response.data.vendorList.length == 1 ){
	   	                $scope.vendorName = response.data.vendorList[0].id;
	   	            }else{
	   	            	$scope.vendorName = 0;
	   	            }	             	
	             	
	            }else if(type == 'locationChange'){
	            	$scope.plantsList = response.data;
	            }else if(type == 'plantChange'){
	            	$scope.departmentsList = response.data.departmentList;
	            }else if(type == 'departmentChange'){
	            	//$scope.departmentsList = response.data.departmentsList;
	            	$scope.sectionesList = response.data.sectionsList;
	            }else if(type == 'sectionChange'){
	            	$scope.workAreasList = response.data;
	            }else if( type ==  'schemaName'){            	 
	            	  $scope.schemaName = response.data.name;
	            }
	        },
	        function(){
	        	$scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable); 
	        });
	    }

	 // getSchemaNameByUserId
	    $scope.getData("reportController/getSchemaNameByUserId.json",{userId : $cookieStore.get('userId') } ,"schemaName" );  
	   
	    $scope.getData('vendorController/getCustomerNamesAsDropDown.json', { customerId: $cookieStore.get('customerId')}, 'customerList')

	    $scope.customerChange = function () {
	    	if($scope.customerName != undefined ){
	    		$scope.getData('vendorController/getCompanyNamesAsDropDown.json', {customerId: $scope.customerName == undefined ?'': $scope.customerName,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.companyName} , 'customerChange');
	    	}
	    };
	    
	    $scope.companyChange = function() {
	    	if($scope.customerName != undefined && $scope.companyName != undefined ){
	    		$scope.getData('reportController/getVendorNamesAndDepartmentsAsDropDown.json',{ customerId :  ($scope.customerName == undefined) ? '': $scope.customerName, companyId:  ($scope.companyName == undefined )? '0':$scope.companyName, vendorId:($cookieStore.get('vendorId') != null && $cookieStore.get('vendorId') != "") ? $cookieStore.get('vendorId') : $scope.vendorName != undefined ? $scope.vendorName : 0}, 'companyChange')
	    	}
	    };
	    
	    
	    $scope.locationChange = function(){
	    	if($scope.locationId != undefined && $scope.locationId != null && $scope.locationId != ''){
	    		$scope.departmentsList = [];
	    		$scope.sectionesList = [];
	    		$scope.workAreasList = [];
	    		
	    		$scope.getData('jobAllocationByVendorController/getPlantsList.json', {locationId:$scope.locationId}, 'locationChange');
	    	}else{
	    		$scope.plantsList =[];
	    		$scope.departmentsList = [];
	    		$scope.sectionesList = [];
	    		$scope.workAreasList = [];
	    	}
	    }
	    
	    $scope.plantChange = function(){
	    	if($scope.locationId != undefined && $scope.locationId != null && $scope.locationId != '' && $scope.plantId != undefined && $scope.plantId != null && $scope.plantId != ''){
	    		$scope.sectionesList = [];
	    		$scope.workAreasList = [];
	    		
	    		$scope.getData('reportController/getDepartmentsByLocationAndPlantId.json', {customerId:$cookieStore.get('customerId'), companyId: $cookieStore.get('companyId'), locationId:$scope.locationId,plantId:$scope.plantId}, 'plantChange');
	    	}else{
	    		$scope.departmentsList = [];
	    		$scope.sectionesList = [];
	    		$scope.workAreasList = [];
	    	}
	    }
	    
	    $scope.departmentChange = function(){
	    	if($scope.locationId != undefined && $scope.locationId != null && $scope.locationId != '' && $scope.plantId != undefined && $scope.plantId != null && $scope.plantId != '' && $scope.departmentId != undefined && $scope.departmentId != null && $scope.departmentId != ''){
	    		$scope.workAreasList = [];
	    		$scope.getData('associatingDepartmentToLocationPlantController/getSectionDetailsByLocationAndPlantAndDeptId.json', {customerId:$cookieStore.get('customerId'), companyId: $cookieStore.get('companyId'), locationId:$scope.locationId,plantId:$scope.plantId, departmentId : $scope.departmentId}, 'departmentChange');
	    	}else{
	    		$scope.sectionesList = []; 
	    		$scope.workAreasList = [];
	    	}
	    }
	    
	    $scope.sectionChange = function(){
	    	if($scope.locationId != undefined && $scope.locationId != null && $scope.locationId != '' && $scope.plantId != undefined && $scope.plantId != null && $scope.plantId != '' && $scope.sectionId != undefined && $scope.sectionId != null && $scope.sectionId != '' && $scope.departmentId != undefined && $scope.departmentId != null && $scope.departmentId != ''){
	    			$scope.getData('jobAllocationByVendorController/getAllWorkAreasBySectionesAndLocationAndPlantAndDept.json', {customerId:$cookieStore.get('customerId'), companyId: $cookieStore.get('companyId'), locationId:$scope.locationId,plantId:$scope.plantId,sectionId:$scope.sectionId,departmentId:$scope.departmentId}, 'sectionChange');
	    	}else{    	
	    		$scope.workAreasList = [];
	    	}
	    }

	    $scope.search = function () {	
	    	if($('#jobAllocationReport').valid()){
		    	 if(($('#fromDate').val() != undefined && $('#fromDate').val() != null) && ($('#toDate').val() != undefined && $('#toDate').val() != null) && new Date(moment($('#fromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime() > new Date(moment($('#toDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime()  ){
		    		 $scope.Messager('error', 'Error', 'From Date should not be less than to Date');
		      	     $scope.toDate="";
		    	 }else{
		    		 $("#loader").show();	    		
		    		 $http({
						    url: ROOTURL +'reportController/jobAllocationReport.view',
						    method: 'POST',
						    responseType: 'arraybuffer',
						   /* data: { customerId: ($scope.customerName == undefined)? '': $scope.customerName, 
						    		companyId: ($scope.companyName == undefined  )? '':$scope.companyName, 
						    		fromDate : ($('#fromDate').val() != undefined && $('#fromDate').val() != '' ) ?  moment($('#fromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') : null,
						    		toDate : ($('#toDate').val() != undefined && $('#toDate').val() != '' ) ?  moment($('#toDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') : null, 
						    		vendorId: ($scope.vendorName == undefined || $scope.vendorName == '' )? '': $scope.vendorName,  						    		
						    		workOrderName :	($scope.workOrderName == undefined || $scope.workOrderName == '' )? '': $scope.workOrderName,	
						    		docName: ($('#complianceType option:selected').html() != undefined && $('#complianceType option:selected').html() != '' && $('#complianceType option:selected').html() != 'Select') ? $('#complianceType option:selected').html() : '',
						    		validated: ($scope.validated != undefined && $scope.validated != false ? true : false), saved:($scope.saved != undefined && $scope.saved != false ? true : false), 
						    		status:$scope.status} , //this is your json data string
*/						   
						    data: { customerId: ($scope.customerName == undefined)? '': $scope.customerName, 
						    		companyId: ($scope.companyName == undefined  )? '':$scope.companyName, 
						    		fromDate : ($('#fromDate').val() != undefined && $('#fromDate').val() != '' ) ?  moment($('#fromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') : 'null',
						    		toDate : ($('#toDate').val() != undefined && $('#toDate').val() != '' ) ?  moment($('#toDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') : 'null', 
						    		vendorId: ($scope.vendorName == undefined || $scope.vendorName == '' )? 'null': $scope.vendorName,  						    		
						    	    workerCode : $scope.workerCode == undefined ? 'null' : $scope.workerCode ,
						    	    workerName : $scope.workerName == undefined ? 'null' : $scope.workerName ,
						    	    plantId : $scope.plantId,
						    	    locationId:$scope.locationId,
						    	    plantName: $('#plantId option:selected').text(),
						    	    locationName:$('#locationId option:selected').text(),
						    	    companyName:$('#companyName option:selected').text(),
						    	    jobType: $scope.jobType,
						    	    departmentId: ($scope.departmentId != undefined && $scope.departmentId != null && $scope.departmentId != "") ? $scope.departmentId : 'null',
						    	    sectionId: ($scope.sectionId != undefined && $scope.sectionId != null && $scope.sectionId != "") ? $scope.sectionId : 'null',
						    	    workAreaId: ($scope.workAreaId != undefined && $scope.workAreaId != null && $scope.workAreaId != "") ? $scope.workAreaId : 'null',
								    year : ($scope.year == undefined || $scope.year == '' )? '0' : $scope.year ,
								    month : ($scope.month == undefined || $scope.month == ''  )? '0' : $scope.month ,
								    status : $scope.status == undefined ? 'null' : $scope.status,
								    workSkill: ($scope.workSkill != undefined && $scope.workSkill != null && $scope.workSkill != "") ? $scope.workSkill : 'null',
									shift: ($scope.shift != undefined && $scope.shift != null && $scope.shift != "") ? $scope.shift : 'null',
								    schemaName : ($scope.schemaName == undefined || $scope.schemaName == '')? 'null' : $scope.schemaName

						    		} , 
						    headers: {
						        'Content-type': 'application/json',
						        'Accept': 'application/pdf'
						    }
						}).success(function(data){
						    var blob = new Blob([data], {
						        type: 'application/pdf'
						    });
						    saveAs(blob, 'Job_Allocation_Report' + '.pdf');
						    $("#loader").hide(); 
						    $("#data_container").hide();
						}).error(function(){	
							$("#loader").hide();                    
						});	    		 	    		
		    	 }
	    	}
	    };
	    
	    
	    $scope.fun_previewGridData = function () {	
	    	if($('#jobAllocationReport').valid()){
		    	 if(($('#fromDate').val() != undefined && $('#fromDate').val() != null) && ($('#toDate').val() != undefined && $('#toDate').val() != null) && new Date(moment($('#fromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime() > new Date(moment($('#toDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime()  ){
		    		 $scope.Messager('error', 'Error', 'From Date should not be less than to Date');
		      	     $scope.toDate="";
		    	 }else{
		    		 $("#loader").show();	    		
		    		 $http({
						    url: ROOTURL +'reportController/jobAllocationReport.view',
						    method: 'POST',
						    responseType: 'arraybuffer',
						    data: { customerId: ($scope.customerName == undefined)? '': $scope.customerName, 
						    		companyId: ($scope.companyName == undefined  )? '':$scope.companyName, 
						    		fromDate : ($('#fromDate').val() != undefined && $('#fromDate').val() != '' ) ?  moment($('#fromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') : 'null',
						    		toDate : ($('#toDate').val() != undefined && $('#toDate').val() != '' ) ?  moment($('#toDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') : 'null', 
						    		vendorId: ($scope.vendorName == undefined || $scope.vendorName == '' )? 'null': $scope.vendorName,  						    		
						    	    workerCode : $scope.workerCode == undefined ? 'null' : $scope.workerCode ,
						    	    workerName : $scope.workerName == undefined ? 'null' : $scope.workerName ,
						    	    plantId : $scope.plantId,
						    	    locationId:$scope.locationId,
						    	    plantName: $('#plantId option:selected').text(),
						    	    locationName:$('#locationId option:selected').text(),
						    	    companyName:$('#companyName option:selected').text(),
						    	    jobType: $scope.jobType,
						    	    departmentId: ($scope.departmentId != undefined && $scope.departmentId != null && $scope.departmentId != "") ? $scope.departmentId : 'null',
						    	    sectionId: ($scope.sectionId != undefined && $scope.sectionId != null && $scope.sectionId != "") ? $scope.sectionId : 'null',
						    	    workAreaId: ($scope.workAreaId != undefined && $scope.workAreaId != null && $scope.workAreaId != "") ? $scope.workAreaId : 'null',
								    year : ($scope.year == undefined || $scope.year == '' )? '0' : $scope.year ,
								    month : ($scope.month == undefined || $scope.month == ''  )? '0' : $scope.month ,
								    status : $scope.status == undefined ? 'null' : $scope.status,
								    workSkill: ($scope.workSkill != undefined && $scope.workSkill != null && $scope.workSkill != "") ? $scope.workSkill : 'null',
									shift: ($scope.shift != undefined && $scope.shift != null && $scope.shift != "") ? $scope.shift : 'null',
								    schemaName : ($scope.schemaName == undefined || $scope.schemaName == '')? 'null' : $scope.schemaName
						    		} , 
						    headers: {
						        'Content-type': 'application/json',
						        'Accept': 'application/pdf'
						    }
						}).success(function(data){
						    var blob = new Blob([data], {
						        type: 'application/pdf'
						    });
						    var fileURL = URL.createObjectURL(blob);
						    $scope.content = $sce.trustAsResourceUrl(fileURL);
						    $("#loader").hide(); 
						    $("#data_container").show();
						}).error(function(){	
							$("#loader").hide();                    
						});	    		 	    		
		    	 }
	    	}
	    };
	    
	    $scope.fun_date_year_Listener = function(){
	    	$scope.year = "";
    		$scope.month = "";
    		$('#fromDate').val("");
    		$('#toDate').val("");
	    	
	    }
	    
	    $scope.clear = function () {
	    	 $scope.customerName = ($cookieStore.get('customerId') != null && $cookieStore.get('customerId') != undefined && $cookieStore.get('customerId') != 0 )? $cookieStore.get('customerId') : "";
	    	 $scope.companyName = ($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != undefined && $cookieStore.get('companyId') != 0 )? $cookieStore.get('companyId') : "";
	    	 $scope.vendorName = ($cookieStore.get('vendorId') != null && $cookieStore.get('vendorId') != undefined && $cookieStore.get('vendorId') != 0 )? $cookieStore.get('vendorId') : "";
	    	 $('#fromDate').val("");
	    	 $('#toDate').val("");
	    	 $scope.complianceType="";
	    	 $scope.status="Unasssigned";
	    	 $scope.jobType = "";
	    	 $scope.departmentId = "";
	    	 $scope.locationId = "";
	    	 $scope.plantId = "";
	    	 $scope.sectionId = "";
	    	 $scope.workSkill = "";
	    	 $scope.shift = "";
	    	 $scope.workAreaId = "";
	    	 $scope.month="";
	    	 $scope.year = "";
	    	 $scope.validated = false;
	    	 $scope.searchPeriod="byDate";
	    	 $scope.saved = false;	    	 
		     $("#data_container").hide();	    	  
	    };
	    
	   
}]);





