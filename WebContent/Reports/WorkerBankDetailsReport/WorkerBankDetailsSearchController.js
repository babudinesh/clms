'use strict';
var workerBankDetailsCtrl = angular.module("myApp.workerBankDetails", ['ngCookies']);

workerBankDetailsCtrl.controller("workerBankDetailsCtrl", ['$scope', '$sce', '$http', '$resource','$location','$filter', '$routeParams','myservice','$cookieStore', function ($scope, $sce, $http, $resource, $location, $filter, $routeParams, myservice, $cookieStore) {		
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
		
		$scope.searchByWorkerList = [{"id":1,"name":"With Bank Details"},{"id":0,"name":"Without Bank Details"}];
		$scope.searchBy = 1;
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
	       			   	$scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Worker Bank Details'}, 'buttonsAction');
	            }else if (type == 'customerChange'){
	                $scope.companyList = response.data;
	               
	                if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	   	                $scope.companyName = response.data[0].id;
						$scope.companyChange();
	   	            }
	            }else if (type == 'companyChange') {	            	
	             	$scope.vendorList = response.data.vendorList;	      
	             	if( response.data.vendorList[0] != undefined && response.data.vendorList[0] != "" && response.data.vendorList.length == 1 ){
	   	                $scope.vendorId = response.data.vendorList[0].id;
	   	            }	             	
	             
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
	    		$scope.getData('reportController/getVendorNamesAndDepartmentsAsDropDown.json',{ customerId :  ($scope.customerName == undefined) ? '': $scope.customerName, companyId:  ($scope.companyName == undefined )? '0':$scope.companyName, vendorId:($cookieStore.get('vendorId') != null && $cookieStore.get('vendorId') != "") ? $cookieStore.get('vendorId') : $scope.vendorName != undefined ? $scope.vendorName : 0}, 'companyChange')
	    	}
	    };
	    
	   /* $scope.vendorChange = function() {
	    	if($scope.vendorName != undefined && $scope.vendorName != undefined ){
	    		$scope.getData('associateWorkOrderController/getVendorAssociatedWorkOrder.json',{ customerDetailsId :  ($scope.customerName == undefined) ? '': $scope.customerName, companyDetailsId:  ($scope.companyName == undefined )? '0':$scope.companyName, vendorDetailsId :($cookieStore.get('vendorId') != null && $cookieStore.get('vendorId') != "") ? $cookieStore.get('vendorId') : $scope.vendorName != undefined ? $scope.vendorName : 0}, 'vendorChange')
	    	}
	    };*/
	    

	    $scope.fun_pdf_Download = function () {	
	    	if($('#workerBankSearchForm').valid()){
		    		 $("#loader").show();	
		    		 $("#data_container").hide();
		    		 $http({
						    url: ROOTURL +'reportController/workerBankDetailsView.view',
						    method: 'POST',
						    responseType: 'arraybuffer',
						    data: { customerId: ($scope.customerName == undefined)? '': $scope.customerName, 
						    		companyId: ($scope.companyName == undefined  )? '':$scope.companyName, 
						    		vendorId: ($scope.vendorId == undefined || $scope.vendorId == '' )? '': $scope.vendorId, 	
						    		employeeCode : $scope.employeeCode == undefined ? 'null' : $scope.employeeCode ,
									employeeName : $scope.employeeName == undefined ? 'null' : $scope.employeeName ,
									bankName : $scope.bankName == undefined ? 'null' : $scope.bankName ,
									searchBy : $scope.searchBy == undefined ? 'null' : $scope.searchBy ,
									//ifscCode : $scope.ifscCode == undefined ? 'null' : $scope.ifscCode ,
							} 
		    		 , //this is your json data string
						    headers: {
						        'Content-type': 'application/json',
						        'Accept': 'application/pdf'
						    }
						}).success(function(data){
						    var blob = new Blob([data], {
						        type: 'application/pdf'
						    });
						    saveAs(blob, 'WorkerBankDetails' + '.pdf');
						    $("#loader").hide(); 
						    $("#data_container").hide();
						}).error(function(){	
							$("#loader").hide();                    
						});	    		 	    		
		    	 }
	    	
	    };
	    
	    
	    $scope.fun_searchGridData = function () {	
	    	if($('#workerBankSearchForm').valid()){		   
		    		 $("#loader").show();	    		
		    		 $http({
						    url: ROOTURL +'reportController/workerBankDetailsView.view',
						    method: 'POST',
						    responseType: 'arraybuffer',
						    data: { customerId: ($scope.customerName == undefined)? '': $scope.customerName, 
						    		companyId: ($scope.companyName == undefined  )? '':$scope.companyName, 
								    		vendorId: ($scope.vendorId == undefined || $scope.vendorId == '' )? '': $scope.vendorId, 	
								    		employeeCode : $scope.employeeCode == undefined ? 'null' : $scope.employeeCode ,
											employeeName : $scope.employeeName == undefined ? 'null' : $scope.employeeName ,
											bankName : $scope.bankName == undefined ? 'null' : $scope.bankName ,
											searchBy : $scope.searchBy == undefined ? 'null' : $scope.searchBy ,
											//ifscCode : $scope.ifscCode == undefined ? 'null' : $scope.ifscCode
													} , //this is your json data string
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
	    	
	    };
	    
	    
	    
	    $scope.fun_Excel_Download = function(){

					  
		          if($('#workerBankSearchForm').valid()){ 		        	
		        	  $("#loader").show();
		          	  $("#data_container").hide();
		          	  
					   $http({
						    url: ROOTURL +'reportController/downloadExcelBankDetailsReport.view',
						    method: 'POST',
						    responseType: 'arraybuffer',
						    data: {customerId: ($scope.customerName == undefined)? '': $scope.customerName, 
						    		companyId: ($scope.companyName == undefined  )? '':$scope.companyName, 
								    		vendorId: ($scope.vendorId == undefined || $scope.vendorId == '' )? '': $scope.vendorId, 	
								    		employeeCode : $scope.employeeCode == undefined ? 'null' : $scope.employeeCode ,
											employeeName : $scope.employeeName == undefined ? 'null' : $scope.employeeName ,
											bankName : $scope.bankName == undefined ? 'null' : $scope.bankName ,
											searchBy : $scope.searchBy == undefined ? 'null' : $scope.searchBy ,
											//ifscCode : $scope.ifscCode == undefined ? 'null' : $scope.ifscCode
													} , //this is your json data string
						    headers: {
						        'Content-type': 'application/json',
						        'Accept': 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
						    }
						}).success(function(data){
						    var blob = new Blob([data], {
						        type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
						    });
						    saveAs(blob, 'WorkerBankDetails_Report' + '.xlsx');
						    $("#loader").hide();
						    if($scope.dataAvail)
	                         $("#data_container").show();
						}).error(function(){	
							$("#loader").hide();
	                     //  $("#data_container").show();
						});
		          }
		   
			
	  }
	    
	    
	    $scope.fun_clearSsearchFields = function () {
	    	 $scope.customerName="";
	    	 $scope.companyName="";
	    	 $scope.vendorId="";
	    	// $scope.ifscCode = "";
	    	 $scope.bankName = "";
	    	// $scope.branchName="";
	    	 $scope.employeeCode = "";
	    	 $scope.employeeName = "";
	    	 $("#data_container").hide();	    	  
	    };
	    
	   
}]);





