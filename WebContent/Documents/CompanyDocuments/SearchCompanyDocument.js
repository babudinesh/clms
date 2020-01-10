'use strict';

var CompanyDocumentController = angular.module("myApp.CompanyDocument", []);

CompanyDocumentController.controller("CompanyDocumentSearchCtrl", ['$scope', '$http', '$resource','$location', '$routeParams','$filter', '$cookieStore','$rootScope','myService', function ($scope, $http, $resource,$location,  $routeParams, $filter,  $cookieStore, $rootScope, myService) {
		
	var documentSearchTable;
	$scope.DocumentSearchTableView = false;
	$scope.documentName="";
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
	
	$scope.list_searchBy = [{"id":"With Attachment","name":"With Attachment"},
	                        {"id":"Without Attachment","name":"Without Attachment"}];
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
	
	$scope.list_status = [{"id":"Saved","name":"Saved"},
	                      {"id":"Submitted","name":"Submitted"},
	                      {"id":"Verified","name":"Verified"}];
	
	
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
    			
    			 if ( ! $.fn.DataTable.isDataTable( '#DocumentSearchTable' ) ) {        		   
    				 documentSearchTable = $('#DocumentSearchTable').DataTable({        
  	                     "columns": [	  	                       
  	                     	{ "data": "documentName",
  	                     		"render": function ( data, type, full, meta ) {                 		                    		 
	                        		return '<a href="#/addCompanyDocument/'+full.companyDocumentId+'">'+data+'</a>';
	                        	}
  	                     	},
  	                     	{ "data": "challanNumber"},
                     		{ "data": "documentDate" ,
                     			"render": function ( data, type, full, meta ) {                 		                    		 
	  	  	               		      return $filter('date')( full.documentDate, 'dd/MM/yyyy');
	  	  	               		    }
                     		},
                     		{ "data": "status" }
                     		
                     		]
  	               });  
  	      		}
    			
    			// for button views
    			if($scope.buttonArray == undefined || $scope.buttonArray == '')
    				$scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Company Documents'}, 'buttonsAction');

    			 
	        }else if (type == 'customerChange'){
	            $scope.companyList = response.data;
	            if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
   	                $scope.companyName = response.data[0].id;
   	                $scope.dropdownDisableCompany = true;
   	                $scope.companyChange();
   	            }
	        }else if(type == 'companyChange'){
	        	$scope.locationsList = response.data.locationList;
	        	$scope.documentsList = response.data.documentsList;
	        	if( response.data.locationList != undefined && response.data.locationList != null && response.data.locationList != "" && response.data.locationList[0] != undefined && response.data.locationList[0] != "" && response.data.locationList.length == 1 ){
   	                $scope.locationName = response.data.locationList[0].id;
   	            }
	        }else if(type =='search'){
	        	//alert(angular.toJson(response.data.vendorDocumentsList));
	        	$scope.DocumentSearchTableView = true;
	        	documentSearchTable.clear().rows.add(response.data.vendorDocumentsList).draw(); 
	        }
	    },function () { 
	    	alert('Error') 
	    });
    }
    
    $scope.getData('vendorController/getCustomerNamesAsDropDown.json', { customerId: $cookieStore.get('customerId')}, 'customerList')
	
	$scope.customerChange = function () {
    	$scope.getData('vendorController/getCompanyNamesAsDropDown.json',{customerId: ($scope.customerName == undefined || $scope.customerName == null )? '0' : $scope.customerName,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.companyName != undefined ? $scope.companyName : 0} , 'customerChange');
    };
	$scope.companyChange = function () {
        $scope.getData('documentController/getLocationAndDocumentNames.json', { customerId: ($scope.customerName == undefined || $scope.customerName == null )? '0' : $scope.customerName,companyId: ($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.companyName != undefined ? $scope.companyName : 0  }, 'companyChange');

	};

	$scope.search = function () {
		$scope.getData('documentController/getCompanyDocumentsListBySearch.json', { customerId : $cookieStore.get('customerId')  , companyId : ($scope.companyName == undefined || $scope.companyName == null) ? '0' : $scope.companyName  , locationId: $scope.locationName == undefined ? '' : $scope.locationName,  documentName :$scope.documentName == undefined ? '' : $scope.documentName  ,status :$scope.status, fromDate:$scope.fromDate, toDate:$scope.toDate, month:$scope.month, year:$scope.year}, 'search');
	};
	$scope.clear = function () {
		$scope.documentName="";
		if($cookieStore.get('companyId') != null ){
			$scope.companyName = $cookieStore.get('companyId') 
		}
		$scope.status = "";
		$scope.month = "";
		$scope.year="";
		$scope.searchBy = "";
		$scope.fromDate = "";
		$scope.toDate = "";
	};

}]);


