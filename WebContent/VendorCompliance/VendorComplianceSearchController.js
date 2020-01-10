'use strict';
var VendorComplianceController = angular.module("myApp.VendorCompliance", ['ngCookies']);

VendorComplianceController.controller("VendorComplianceSearchCtrl", ['$scope', '$http', '$resource','$location','$filter', '$routeParams','myservice','$cookieStore', function ($scope, $http, $resource, $location, $filter, $routeParams, myservice, $cookieStore) {
		$scope.vendorComplianceTableView = false;   
	   // $scope.roleVendor =$cookieStore.get('vendorId')== null || $cookieStore.get('vendorId') == ''  ? false : true ;

		$.material.init();
		$('#searchPanel').hide();
	    var vendorComplianceDataTable;

	    /* $('#toDate, #fromDate').bootstrapMaterialDatePicker({
		     time: false,
		     clearButton: true,
		     format : "DD/MM/YYYY"
	     });*/
	    
	     $('#toDate, #fromDate').datepicker({
	         changeMonth: true,
	         changeYear: true,
	         dateFormat:"dd/mm/yy",
	         showAnim: 'slideDown'
	       	  
	       });
	     
	     if($cookieStore.get("roleNamesArray").includes('Vendor Admin')){
				$scope.dropdownDisable = true;
			}else{
				$scope.dropdownDisable = false;
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
	       
        $scope.vendorStatus_list = [{"id":"New", "name":"New"},
                                    {"id":"Pending For Approval", "name":"Pending For Approval"},
                                    {"id":"Validated", "name":"Validated"},
        							{"id":"Suspended", "name":"Suspended"},
        							{"id":"Debarred", "name":"Debarred"},
        							{"id":"Terminated", "name":"Terminated"}];
        
        $scope.documentStatus_list = [{"id":"Saved", "name":"Saved"},
                                      {"id":"Verified", "name":"Verified"}];
        
        
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
   		                $scope.customerName=response.data.customerList[0];		                
   		                $scope.customerChange();
   		            }
	                if ( ! $.fn.DataTable.isDataTable( '#vendorComplianceTable' ) ) {        		   
	                	vendorComplianceDataTable = $('#vendorComplianceTable').DataTable({        
	  	                     "columns": [	  	
  	                                 	{ "data": "vendorName" },
  	                                 	{ "data": "complianceName"},
  	                                 	{ "data": "issueDt"},
  	                                 	{ "data": "expiryDt"},
  	  	  	                   			{ "data": "dueDate" },	
  	  	  	                   			{ "data": "numberOfWorkersCovered" },	
  	  	  	                   			{ "data": "status" },
  	  	  	                   			{ "data": "",
	  	                        	"render": function ( data, type, full, meta ) {                 		                    		 
	  	               		      return '<a href="#/VendorComplianceAdd/'+full.vendorComplianceId+'">Details</a>';
	  	               		    }
	  	                     }]
	  	               });  
	  	      		}
	                // for button views
	                if($scope.buttonArray == undefined || $scope.buttonArray == '')
	                $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Vendor Compliance'}, 'buttonsAction');
	            }else if (type == 'customerChange'){
	                $scope.companyList = response.data;
	               
	                if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	   	                $scope.companyName = response.data[0];
						$scope.companyChange();
	   	            }
	            }else if (type == 'companyChange') {	            	
	             	$scope.vendorList = response.data.vendorList;
	             	$scope.complianceTypeList = response.data.complianceTypesList;
	             	if( response.data.vendorList[0] != undefined && response.data.vendorList[0] != "" && response.data.vendorList.length == 1 ){
	   	                $scope.vendorName = response.data.vendorList[0].id;
	   	            }
	             	//alert(response.data.complianceTypesList.length);
	             	if( response.data.complianceTypesList != undefined && response.data.complianceTypesList != "" && response.data.complianceTypesList.length == 1 ){
	   	                $scope.defineComplianceTypeId = response.data.complianceTypesList[0].defineComplianceTypeId;
	   	            }
	            } else if(type=='search'){

	            	if(response.data != undefined || response.data != null){
	            		$scope.result = response.data.vendorComplianceList;
	            		$('#searchPanel').show();
	            	}

	            	$scope.vendorComplianceTableView = true;
	            	vendorComplianceDataTable.clear().rows.add(response.data.vendorComplianceList).draw(); 
	                // $scope.result = response.data.vendorComplianceList;
	            }
	        },
	        function(){
	        	$scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable); 
	        });
	    }


	    $scope.getData('vendorController/getCustomerNamesAsDropDown.json', { customerId: $cookieStore.get('customerId')}, 'customerList')

	    $scope.customerChange = function () {
	    	if($scope.customerName != undefined ){
	    		$scope.getData('vendorController/getCompanyNamesAsDropDown.json', {customerId: $scope.customerName == undefined ?'': $scope.customerName.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.companyName} , 'customerChange');
	    	}
	    };
	    
	    $scope.companyChange = function() {
	    	if($scope.customerName != undefined && $scope.companyName != undefined ){
	    		$scope.getData('vendorComplianceController/getDropdowns.json',{ customerId :  ($scope.customerName == undefined  || $scope.customerName.customerId == undefined ) ? '': $scope.customerName.customerId, companyId:  ($scope.companyName == undefined || $scope.companyName.id == undefined )? '0':$scope.companyName.id, vendorId:($cookieStore.get('vendorId') != null && $cookieStore.get('vendorId') != "") ? $cookieStore.get('vendorId') : $scope.vendorName != undefined ? $scope.vendorName : 0}, 'companyChange')
	    	}
	    };

	    $scope.search = function () {
	    	 if(($('#fromDate').val() != undefined && $('#fromDate').val() != null) && ($('#toDate').val() != undefined && $('#toDate').val() != null) && new Date(moment($('#fromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime() > new Date(moment($('#toDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime()  ){
	    		 $scope.Messager('error', 'Error', 'From Date should not be less than to Date');
	      	     $scope.toDate="";
	    	 }else{
	    		 $scope.getData('vendorComplianceController/getVendorComplianceListBySearch.json', { customerId: ($scope.customerName == undefined || $scope.customerName.customerId == undefined )? '': $scope.customerName.customerId, companyId: ($scope.companyName == undefined || $scope.companyName.id == undefined )? '':$scope.companyName.id, fromDate : ($('#fromDate').val() != undefined && $('#fromDate').val() != '' ) ?  moment($('#fromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') : null, toDate : ($('#toDate').val() != undefined && $('#toDate').val() != '' ) ?  moment($('#toDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') : null,  vendorId: ($scope.vendorName == undefined || $scope.vendorName == null )? '': $scope.vendorName, defineComplianceTypeId: ($scope.defineComplianceTypeId == undefined || $scope.defineComplianceTypeId == null )? '': $scope.defineComplianceTypeId, status:$scope.status, vendorStatus:$scope.vendorStatus }, 'search');
	    	 }
	    };
	    
	    $scope.clear = function () {
	    	 $scope.customerName="";
	    	 $scope.companyName="";
	    	 $scope.vendorName="";
	    	 $('#fromDate').val("");
	    	 $('#toDate').val("");
	    	 $scope.defineComplianceTypeId="";
	    	 $scope.status="Y";
	    	 $scope.validated = false;
	    	 $scope.saved = false;
	    };
	    
	   
}]);





