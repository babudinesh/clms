'use strict';
var VendorComplianceReportController = angular.module("myApp.VendorComplianceReport", ['ngCookies']);

VendorComplianceReportController.controller("vendorComplianceReportCtrl", ['$scope', '$sce', '$http', '$resource','$location','$filter', '$routeParams','myservice','$cookieStore', function ($scope, $sce, $http, $resource, $location, $filter, $routeParams, myservice, $cookieStore) {		
		$scope.status = "";
		$.material.init();			  
		$("#loader").hide(); 
		$("#data_container").hide();
	    /* $('#toDate, #fromDate').bootstrapMaterialDatePicker({
		     time: false,
		     clearButton: true,
		     format : "DD/MM/YYYY"
	     });
	     */
	     $('#toDate, #fromDate').datepicker({
	         changeMonth: true,
	         changeYear: true,
	         dateFormat:"dd/mm/yy",
	         showAnim: 'slideDown'
	       	  
	       });
	     $scope.vendorStatus_list = [{"id":"New", "name":"New"},
	                                    {"id":"Pending For Approval", "name":"Pending For Approval"},
	                                    {"id":"Validated", "name":"Validated"},
	        							{"id":"Suspended", "name":"Suspended"},
	        							{"id":"Debarred", "name":"Debarred"},
	        							{"id":"Terminated", "name":"Terminated"}];
	    
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
		       			   	$scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Vendor Compliance Report'}, 'buttonsAction');
	            }else if (type == 'customerChange'){
	                $scope.companyList = response.data;
	               
	                if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	   	                $scope.companyName = response.data[0].id;
						$scope.companyChange();
	   	            }
	            }else if (type == 'companyChange') {	            	
	             	$scope.vendorList = response.data.vendorList;
	             	$scope.complianceTypeList = response.data.complianceTypesList;
	             	if( response.data.vendorList[0] != undefined && response.data.vendorList[0] != "" && response.data.vendorList.length == 1 ){
	   	                $scope.vendorName = response.data.vendorList[0].id;
	   	            }	             	
	             	if( response.data.complianceTypesList != undefined && response.data.complianceTypesList != "" && response.data.complianceTypesList.length == 1 ){
	   	                $scope.defineComplianceTypeId = response.data.complianceTypesList[0].defineComplianceTypeId;
	   	            }
	            } else if( type =='vendorChange'){
	            	$scope.workOrderNamesList = response.data;
	            }
	        },
	        function(){
	        	$scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable); 
	        });
	    }


	    $scope.getData('vendorController/getCustomerNamesAsDropDown.json', { customerId: $cookieStore.get('customerId')}, 'customerList')

	    $scope.customerChange = function () {
	    	if($scope.customerName != undefined ){
	    		$scope.getData('vendorController/getCompanyNamesAsDropDown.json', {customerId: $scope.customerName == undefined ?'': $scope.customerName,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.companyName} , 'customerChange');
	    	}
	    };
	    
	    $scope.companyChange = function() {
	    	if($scope.customerName != undefined && $scope.companyName != undefined ){
	    		$scope.getData('vendorComplianceController/getDropdowns.json',{ customerId :  ($scope.customerName == undefined) ? '': $scope.customerName, companyId:  ($scope.companyName == undefined )? '0':$scope.companyName, vendorId:($cookieStore.get('vendorId') != null && $cookieStore.get('vendorId') != "") ? $cookieStore.get('vendorId') : $scope.vendorName != undefined ? $scope.vendorName : 0}, 'companyChange')
	    	}
	    };
	    
	    $scope.vendorChange = function() {
	    	if($scope.vendorName != undefined && $scope.vendorName != undefined ){
	    		$scope.getData('associateWorkOrderController/getVendorAssociatedWorkOrder.json',{ customerDetailsId :  ($scope.customerName == undefined) ? '': $scope.customerName, companyDetailsId:  ($scope.companyName == undefined )? '0':$scope.companyName, vendorDetailsId :($cookieStore.get('vendorId') != null && $cookieStore.get('vendorId') != "") ? $cookieStore.get('vendorId') : $scope.vendorName != undefined ? $scope.vendorName : 0}, 'vendorChange')
	    	}
	    };
	    

	    $scope.search = function () {	
	    	if($('#vendorComplianceReport').valid()){
		    	 if(($('#fromDate').val() != undefined && $('#fromDate').val() != null) && ($('#toDate').val() != undefined && $('#toDate').val() != null) && new Date(moment($('#fromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime() > new Date(moment($('#toDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime()  ){
		    		 $scope.Messager('error', 'Error', 'From Date should not be less than to Date');
		      	     $scope.toDate="";
		    	 }else{
		    		 $("#loader").show();	    		
		    		 $http({
						    url: ROOTURL +'reportController/vendorComplianceReport.view',
						    method: 'POST',
						    responseType: 'arraybuffer',
						    data: { customerId: ($scope.customerName == undefined)? '': $scope.customerName, 
						    		companyId: ($scope.companyName == undefined  )? '':$scope.companyName, 
						    		fromDate : ($('#fromDate').val() != undefined && $('#fromDate').val() != '' ) ?  moment($('#fromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') : null,
						    		toDate : ($('#toDate').val() != undefined && $('#toDate').val() != '' ) ?  moment($('#toDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') : null, 
						    		vendorId: ($scope.vendorName == undefined || $scope.vendorName == '' )? '': $scope.vendorName,  						    		
						    		workOrderName :	($scope.workOrderName == undefined || $scope.workOrderName == '' )? '': $scope.workOrderName,	
						    		docName: ($('#complianceType option:selected').html() != undefined && $('#complianceType option:selected').html() != '' && $('#complianceType option:selected').html() != 'Select') ? $('#complianceType option:selected').html() : '',
						    		validated: ($scope.validated != undefined && $scope.validated != false ? true : false), saved:($scope.saved != undefined && $scope.saved != false ? true : false), 
						    		status:$scope.status} , //this is your json data string
						    headers: {
						        'Content-type': 'application/json',
						        'Accept': 'application/pdf'
						    }
						}).success(function(data){
						    var blob = new Blob([data], {
						        type: 'application/pdf'
						    });
						    saveAs(blob, 'Vendor_Compliance_Report' + '.pdf');
						    $("#loader").hide(); 
						    $("#data_container").hide();
						}).error(function(){	
							$("#loader").hide();                    
						});	    		 	    		
		    	 }
	    	}
	    };
	    
	    
	    $scope.fun_previewGridData = function () {	
	    	if($('#vendorComplianceReport').valid()){
		    	 if(($('#fromDate').val() != undefined && $('#fromDate').val() != null) && ($('#toDate').val() != undefined && $('#toDate').val() != null) && new Date(moment($('#fromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime() > new Date(moment($('#toDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime()  ){
		    		 $scope.Messager('error', 'Error', 'From Date should not be less than to Date');
		      	     $scope.toDate="";
		    	 }else{
		    		 $("#loader").show();	    		
		    		 $http({
						    url: ROOTURL +'reportController/vendorComplianceReport.view',
						    method: 'POST',
						    responseType: 'arraybuffer',
						    data: { customerId: ($scope.customerName == undefined)? '': $scope.customerName, 
						    		companyId: ($scope.companyName == undefined  )? '':$scope.companyName, 
						    		fromDate : ($('#fromDate').val() != undefined && $('#fromDate').val() != '' ) ?  moment($('#fromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') : null,
						    		toDate : ($('#toDate').val() != undefined && $('#toDate').val() != '' ) ?  moment($('#toDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') : null, 
						    		vendorId: ($scope.vendorName == undefined || $scope.vendorName == '' )? '': $scope.vendorName,  						    		
						    		workOrderName :	($scope.workOrderName == undefined || $scope.workOrderName == '' )? '': $scope.workOrderName,	
						    		docName: ($('#complianceType option:selected').html() != undefined && $('#complianceType option:selected').html() != '' && $('#complianceType option:selected').html() != 'Select') ? $('#complianceType option:selected').html() : '',
						    		validated: ($scope.validated != undefined && $scope.validated != false ? true : false), saved:($scope.saved != undefined && $scope.saved != false ? true : false), 
						    		status:$scope.status} , //this is your json data string
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
	    
	    
	    $scope.clear = function () {
	    	 $scope.customerName="";
	    	 $scope.companyName="";
	    	 $scope.vendorName="";
	    	 $('#fromDate').val("");
	    	 $('#toDate').val("");
	    	 $scope.complianceType="";
	    	 $scope.status="Y";
	    	 $scope.validated = false;
	    	 $scope.saved = false;	    	 
		     $("#data_container").hide();	    	  
	    };
	    
	   
}]);





