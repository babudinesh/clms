'use strict';

var InvoiceController = angular.module("myApp.Invoice", []);
InvoiceController.service('myService', function () {
	
	this.invoiceCookies = null;
});

InvoiceController.controller("InvoiceSearchCtrl", ['$scope', '$http', '$resource','$location', '$routeParams','$filter', '$cookieStore','$rootScope','myService', function ($scope, $http, $resource,$location,  $routeParams, $filter,  $cookieStore, $rootScope, myService) {
		
	var invoiceSearchTable = [];
	$scope.InvoiceSearchTableView = false;
	$scope.invoiceNumber="";
	$scope.status = "";
	$scope.month = "";
	$scope.year="";
	$scope.searchBy = "";
	$scope.fromDate = "";
	$scope.toDate = "";

	$('#fromDate,#toDate').datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown'	    	  
	});
	
	$scope.list_months = [{"id":1,"name":"January"},
                        {"id":2,"name":"February"},
                        {"id":3,"name":"March"},
                        {"id":4,"name":"April"},
                        {"id":5,"name":"May"},
                        {"id":6,"name":"June"},
                        {"id":7,"name":"July"},
                        {"id":8,"name":"August"},
                        {"id":9,"name":"September"},
                        {"id":10,"name":"October"},
                        {"id":11,"name":"November"},
                        {"id":12,"name":"December"}];
	
	$scope.list_status = [{"id":"New","name":"New"},
	                      {"id":"Initiated","name":"Initiated"},
	                      {"id":"Saved","name":"Saved"},
	                      {"id":"Pending For Approval","name":"Pending For Approval"},
	                      {"id":"Correction Required","name":"Correction Required"},
	                      {"id":"Approved","name":"Approved"},	                     
	                      {"id":"Pending For Payment","name":"Pending For Payment"},	                      
	                      {"id":"Rejected","name":"Rejected"},
	                      {"id":"Closed","name":"Closed"},
	                      {"id":"Cancelled","name":"Cancelled"}];
	
    $scope.getData = function (url, data, type) {
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
	                $scope.customerName=response.data.customerList[0].customerId;	
	                $scope.dropdownDisableCustomer = true;
	                $scope.customerChange();
	                }
    			
    			 if ( ! $.fn.DataTable.isDataTable( '#InvoiceSearchTable' ) ) {        		   
    				 invoiceSearchTable = $('#InvoiceSearchTable').DataTable({        
  	                     "columns": [	  	                       
  	                     	{ "data": "invoiceNumber",
  	                     		"render": function ( data, type, full, meta ) {                 		                    		 
	                        		return '<a href="#/addInvoice/'+full.invoiceId+'">'+data+'</a>';
	                        	}
  	                     	},
                     		{ "data": "invoiceDate" ,
                     			"render": function ( data, type, full, meta ) {                 		                    		 
	  	  	               		      return $filter('date')( full.invoiceDate, 'dd/MM/yyyy');
	  	  	               		    }
                     		},
                     		{ "data": "status" },
                     		/*{ "data": "View/Edit" ,
                     			"render": function ( data, type, full, meta ) {                 		                    		 
	                        		return '<a href="#/addInvoice/'+full.invoiceId+'">'+"View/Edit"+'</a>';
	                        	}
                     		},*/
                     		{ "data": "Copy",
                     			"render": function ( data, type, full, meta ) {                 		                    		 
	                        		return '<a onclick ="angular.element(this).scope().copyInvoice('+full.invoiceId+')">'+"Copy"+'</a>';
	                        	}
                     		}
                     		]
  	               });  
  	      		}
    			// for button views
    			 if($scope.buttonArray == undefined || $scope.buttonArray == '')
    			 $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Invoice'}, 'buttonsAction');
    			 
	        }else if (type == 'customerChange'){
	            $scope.companyList = response.data;
	            if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
   	                $scope.companyName = response.data[0].id;
   	                $scope.dropdownDisableCompany = true;
   	                $scope.companyChange();
   	            }
	        }else if(type == 'companyChange'){
	        	$scope.vendorList = response.data.vendorList;
	        	if( response.data.vendorList[0] != undefined && response.data.vendorList[0] != "" && response.data.vendorList.length == 1 ){
   	                $scope.vendorName = response.data.vendorList[0].id;
   	                $scope.dropdownDisableVendor = true;
   	            }
	        }else if(type=='search'){
	        	$scope.InvoiceSearchTableView = true;
	        	invoiceSearchTable.clear().rows.add(response.data.invoiceList).draw(); 
	           // $scope.result = response.data.holidayCalendarList;
	        }else if(type == 'invoiceList'){
    			if(response.data != undefined){
    					    			
	    			/*if( response.data.companyAddress != undefined && response.data.companyAddress != null && response.data.companyAddress.length > 0){
	    				$scope.companyAddress = response.data.companyAddress[0].name;
	    				$scope.invoice.companyAddressId = response.data.companyAddress[0].id;
	    			}
	    			
	    			if(response.data.companyContact != undefined && response.data.companyContact != null && response.data.companyContact.length > 0){
	    				$scope.companyContactName = response.data.companyContact[0].name;
	    				$scope.invoice.companyContactId = response.data.companyContact[0].id;
	    			}
	    			
	                if( response.data.vendorList != undefined && response.data.vendorList != "" && response.data.vendorList.length > 0 ){
		                $scope.invoice.vendorId = response.data.vendorList[0].id;	
		                $scope.dropdownDisable = true;
		            }
	                
	                if(response.data.vendorAddressContact != undefined && response.data.vendorAddressContact != null &&  response.data.vendorAddressContact.length > 0){
	    				$scope.vendorAddress = response.data.vendorAddressContact[0].address;
	    				$scope.vendorContactNumber = response.data.vendorAddressContact[0].telephoneNumber;
	    				$scope.vendorEmail = response.data.vendorAddressContact[0].email;
	    				$scope.vendorName = response.data.vendorAddressContact[0].vendorName;
	    			}*/
	                
                	if(response.data.invoiceVo != undefined &&response.data.invoiceVo != null && response.data.invoiceVo.length > 0 ){
                		$scope.invoice = response.data.invoiceVo[0];
                		myService.invoiceCookies = response.data.invoiceVo[0]   ;
                		$cookieStore.put('invoiceCookies',  myService.invoiceCookies);
                		$scope.invoice.invoiceDate = $filter('date')(response.data.invoiceVo[0].invoiceDate, 'dd/MM/yyyy')
                		$location.path('/addInvoice/0');
                	}
                	
	                
    			}
    		}
	    },function () { alert('Error') });
    }
    
    $scope.getData('vendorController/getCustomerNamesAsDropDown.json', { customerId: $cookieStore.get('customerId')}, 'customerList')
	
	$scope.customerChange = function () {
    	$scope.getData('vendorController/getCompanyNamesAsDropDown.json',{customerId: ($scope.customerName == undefined || $scope.customerName == null )? '0' : $scope.customerName,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.companyName != undefined ? $scope.companyName : 0} , 'customerChange');
    };
	$scope.companyChange = function () {
        $scope.getData('workerController/getVendorAndWorkerNamesAsDropDowns.json', { customerId: ($scope.customerName == undefined || $scope.customerName == null )? '0' : $scope.customerName,companyId: ($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.companyName != undefined ? $scope.companyName : 0, vendorId:($cookieStore.get('vendorId') != null && $cookieStore.get('vendorId') != "") ? $cookieStore.get('vendorId') : $scope.vendorName != undefined ? $scope.vendorName : 0  }, 'companyChange');

	};

	$scope.search = function () {
		//alert($scope.customerName+", "+$scope.companyName+", "+$scope.locationId+", "+$scope.holidayCalendarCode+", "+$scope.status);
		$scope.getData('invoiceController/getInvoicesListBySearch.json', { customerId : $cookieStore.get('customerId')  , companyId : ($scope.companyName == undefined || $scope.companyName == null) ? '0' : $scope.companyName  , vendorId: $scope.vendorId == undefined ? '' : $scope.vendorId,  invoiceNumber :$scope.invoiceNumber == undefined ? '' : $scope.invoiceNumber  ,status :$scope.status, fromDate:$scope.fromDate, toDate:$scope.toDate, month:$scope.month, year:$scope.year}, 'search');
	};
	$scope.clear = function () {
		$scope.invoiceNumber="";
		$scope.status = "";
		$scope.month = "";
		$scope.year="";
		$scope.searchBy = "";
		$scope.fromDate = "";
		$scope.toDate = "";
	};
	
	
	$scope.copyInvoice = function(id){
		$scope.getData('invoiceController/getInvoiceById.json', { customerId: $cookieStore.get('customerId'),companyId:$cookieStore.get('companyId'),vendorId:($cookieStore.get('vendorId') != null && $cookieStore.get('vendorId') != "") ? $cookieStore.get('vendorId')  : $scope.vendorName, invoiceId:id }, 'invoiceList');
	}
}]);


