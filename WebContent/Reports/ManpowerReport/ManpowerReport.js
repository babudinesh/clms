'use strict';
var ManpowerReportController = angular.module("myApp.ManpowerReport", ['ngCookies']);

ManpowerReportController.controller("ManpowerReportCtrl", ['$scope', '$sce', '$http', '$resource','$location','$filter', '$routeParams','myservice','$cookieStore','$rootScope', function ($scope, $sce, $http, $resource, $location, $filter, $routeParams, myservice, $cookieStore, $rootScope) {		
		$.material.init();	
		$("#loader").hide(); 
		$("#data_container").hide();
	    $scope.dropdownDisableCustomer = true;
	    
	    
	    	    
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
	    
	   
	     $('#businessDate,#fromDate,#toDate').datepicker({
	         changeMonth: true,
	         changeYear: true,
	         dateFormat:"dd/mm/yy",
	         showAnim: 'slideDown'
	       	  
	       });
	     
	   
	    
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
	    

	    $scope.fun_PDFDownload = function (reportName) {
	    	
	    	if($('#manpowerReport').valid()){
		    	 if(($('#fromDate').val() != undefined && $('#fromDate').val() != null) && ($('#toDate').val() != undefined && $('#toDate').val() != null) && new Date(moment($('#fromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime() > new Date(moment($('#toDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime()  ){
		    		 $scope.Messager('error', 'Error', 'From Date should not be less than to Date');
		      	     $scope.toDate="";
		    	 }else{
		    		 $("#loader").show();	    		
		    		 $http({
						    url: ROOTURL +'reportController/downloadPDFManpowerReport.view',
						    method: 'POST',
						    responseType: 'arraybuffer',
						    data: { customerId: ($scope.customerName == undefined)? '': $scope.customerName, 
						    		companyId: ($scope.companyName == undefined  )? '':$scope.companyName, 
						    		fromDate : ($('#fromDate').val() != undefined && $('#fromDate').val() != '' ) ?  moment($('#fromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') : 'null',
						    		toDate : ($('#toDate').val() != undefined && $('#toDate').val() != '' ) ?  moment($('#toDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') : 'null', 
						    		businessDate : ($('#businessDate').val() != undefined && $('#businessDate').val() != '' ) ?  moment($('#businessDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') : 'null', 
						    		vendorId: ($scope.vendorName == undefined || $scope.vendorName == '' || $scope.vendorName == null)? 'null': $scope.vendorName,  						    		
						    	    plantId : $scope.plantId,
						    	    locationId:$scope.locationId,
						    	    plantName: $('#plantId option:selected').text(),
						    	    locationName:$('#locationId option:selected').text(),
						    	    companyName:$('#companyName option:selected').text(),
						    	    departmentName: ($scope.departmentId != undefined && $scope.departmentId != null && $scope.departmentId != "") ? $('#departmentId option:selected').text() : 'null',
						    	    departmentId: ($scope.departmentId != undefined && $scope.departmentId != null && $scope.departmentId != "") ? $scope.departmentId : 'null',
								    year : ($scope.year == undefined || $scope.year == '' )? '0' : $scope.year ,
								    month : ($scope.month == undefined || $scope.month == ''  )? '0' : $scope.month ,
									shift: ($scope.shift != undefined && $scope.shift != null && $scope.shift != "") ? $scope.shift : 'null',
								    schemaName : ($scope.schemaName == undefined || $scope.schemaName == '' || $scope.schemaName == null)? 'null' : $scope.schemaName,
								    reportName : reportName		
						    		} ,
						    headers: {
						        'Content-type': 'application/json',
						        'Accept': 'application/pdf'
						    }
						}).success(function(data){
						    var blob = new Blob([data], {
						        type: 'application/pdf'
						    });
						    saveAs(blob, 'Daily_Contract_Manpower_Report' + '.pdf');
						    $("#loader").hide(); 
						    $("#data_container").hide();
						}).error(function(){	
							$("#loader").hide();                    
						});	    		 	    		
		    	 }
	    	}
	    };
	    
	    
	    $scope.fun_previewGridData = function (reportName) {	
	    	if($('#manpowerReport').valid()){
		    	 if(($('#fromDate').val() != undefined && $('#fromDate').val() != null) && ($('#toDate').val() != undefined && $('#toDate').val() != null) && new Date(moment($('#fromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime() > new Date(moment($('#toDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime()  ){
		    		 $scope.Messager('error', 'Error', 'From Date should not be less than to Date');
		      	     $scope.toDate="";
		    	 }else{
		    		 $("#loader").show();	    		
		    		 $http({
						    url: ROOTURL +'reportController/downloadPDFManpowerReport.view',
						    method: 'POST',
						    responseType: 'arraybuffer',
						    data: { customerId: ($scope.customerName == undefined)? '': $scope.customerName, 
						    		companyId: ($scope.companyName == undefined  )? '':$scope.companyName, 
						    		fromDate : ($('#fromDate').val() != undefined && $('#fromDate').val() != '' ) ?  moment($('#fromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') : 'null',
						    		toDate : ($('#toDate').val() != undefined && $('#toDate').val() != '' ) ?  moment($('#toDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') : 'null', 
								    businessDate : ($('#businessDate').val() != undefined && $('#businessDate').val() != '' ) ?  moment($('#businessDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') : 'null', 
						    		vendorId: ($scope.vendorName == undefined || $scope.vendorName == '' || $scope.vendorName == null)? 'null': $scope.vendorName,  						    		
						    	    plantId : $scope.plantId,
						    	    locationId:$scope.locationId,
						    	    plantName: $('#plantId option:selected').text(),
						    	    locationName:$('#locationId option:selected').text(),
						    	    companyName:$('#companyName option:selected').text(),
						    	    departmentName: ($scope.departmentId != undefined && $scope.departmentId != null && $scope.departmentId != "") ? $('#departmentId option:selected').text() : 'null',
						    	    departmentId: ($scope.departmentId != undefined && $scope.departmentId != null && $scope.departmentId != "") ? $scope.departmentId : 'null',
								    year : ($scope.year == undefined || $scope.year == '' )? '0' : $scope.year ,
								    month : ($scope.month == undefined || $scope.month == ''  )? '0' : $scope.month ,
									shift: ($scope.shift != undefined && $scope.shift != null && $scope.shift != "") ? $scope.shift : 'null',
									schemaName : ($scope.schemaName == undefined || $scope.schemaName == '' || $scope.schemaName == null)? 'null' : $scope.schemaName,
									reportName: reportName
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
	    
	    $scope.fun_Excel_Download = function(reportName){

			   
			if(($('#fromDate').val() != undefined && $('#fromDate').val() != null) && ($('#toDate').val() != undefined && $('#toDate').val() != null) && new Date(moment($('#fromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime() > new Date(moment($('#toDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime()  ){
	    		 $scope.Messager('error', 'Error', 'From Date should not be less than to Date');
	      	     $scope.toDate="";
	    	 }else {			  
		          if($('#manpowerReport').valid()){ 
		        	  $scope.reporttodate = $('#todate').val();
		              $scope.reportFromdate = $('#fromdate').val();
		        	  $("#loader").show();
		          	  $("#data_container").hide();
		          	  
					   $http({
						    url: ROOTURL +'reportController/downloadExcelManpowerReport.view',
						    method: 'POST',
						    responseType: 'arraybuffer',
						   
							    data: { customerId: ($scope.customerName == undefined)? '': $scope.customerName, 
							    		companyId: ($scope.companyName == undefined  )? '':$scope.companyName, 
							    		fromDate : ($('#fromDate').val() != undefined && $('#fromDate').val() != '' ) ?  moment($('#fromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') : 'null',
							    		toDate : ($('#toDate').val() != undefined && $('#toDate').val() != '' ) ?  moment($('#toDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') : 'null', 
									    businessDate : ($('#businessDate').val() != undefined && $('#businessDate').val() != '' ) ?  moment($('#businessDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') : 'null', 
							    		vendorId: ($scope.vendorName == undefined || $scope.vendorName == '' || $scope.vendorName == null )? 'null': $scope.vendorName,  						    		
							    	    plantId : $scope.plantId,
							    	    locationId:$scope.locationId,
							    	    plantName: $('#plantId option:selected').text(),
							    	    locationName:$('#locationId option:selected').text(),
							    	    companyName:$('#companyName option:selected').text(),
							    	    departmentName: ($scope.departmentId != undefined && $scope.departmentId != null && $scope.departmentId != "") ? $('#departmentId option:selected').text() : 'null',
							    	    departmentId: ($scope.departmentId != undefined && $scope.departmentId != null && $scope.departmentId != "") ? $scope.departmentId : 'null',
									    year : ($scope.year == undefined || $scope.year == '' )? '0' : $scope.year ,
									    month : ($scope.month == undefined || $scope.month == ''  )? '0' : $scope.month ,
										shift: ($scope.shift != undefined && $scope.shift != null && $scope.shift != "") ? $scope.shift : 'null',
									    schemaName : ($scope.schemaName == undefined || $scope.schemaName == '' || $scope.schemaName == null)? 'null' : $scope.schemaName,
									    reportName : reportName	
						    		} ,	
									
						    headers: {
						        'Content-type': 'application/json',
						        'Accept': 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
						    }
						}).success(function(data){
						    var blob = new Blob([data], {
						        type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
						    });
						    saveAs(blob, 'DailyContractManpowerReport' + '.xlsx');
						    $("#loader").hide();
						    if($scope.dataAvail)
	                         $("#data_container").show();
						}).error(function(){	
							$("#loader").hide();
	                     //  $("#data_container").show();
						});
		          }
		   
			}
	  }
	    
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
	    	 $('#businessDate').val("");
	    	 $scope.locationId = "";
	    	 $scope.departmentId = "";
	    	 $scope.plantId = "";
	    	 $scope.shift = "";
		     $("#data_container").hide();	    	  
	    };
	    
	   
}]);





