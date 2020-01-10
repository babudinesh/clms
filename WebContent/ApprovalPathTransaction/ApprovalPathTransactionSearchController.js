'use strict';


var ApprovalPathTransactionController = angular.module("myApp.ApprovalPathTransaction", []);


var screenActionsSearchResultsTable;


ApprovalPathTransactionController.controller("approvalPathTransactionSearchCtrl", ['$scope', '$http', '$resource', '$location','$cookieStore', function ($scope, $http, $resource, $location,$cookieStore) {  
   
		$scope.statusList = [{"id":"Y","name":"Active"},
	        				 {"id":"N","name":"In-Active"}];
		$scope.tableView = false;
		$scope.transaction = new Object();
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
	        	
	        	if( type == 'getMasterData'){
	        		 $scope.customerList = response.data.customerList;
	        		 if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){
	 	                $scope.transaction.customerId = response.data.customerList[0].customerId;
	 	               $scope.customerChange();
	                 }
	        		 if ( ! $.fn.DataTable.isDataTable( '#searchResults' ) ) {        		   
	            		 screenActionsSearchResultsTable = $('#searchResults').DataTable({          			 
	                           "columns": [      
	                        	   { "data": "moduleCode" },
	                               { "data": "transactionCode",
	                              	 "render": function ( data, type, full, meta ) {                 		                    		 
	                     		      return '<a href="#/approvalPathTransaction/'+full.approvalPathTransactionInfoId+'">'+data+'</a>';
	                     		   }
	                           },                             
	                           { "data": "effectiveDate" },
	                           { "data": "transactionName" },
	                           { "data": "createdDate" },
	                              { "data": "modifiedDate" }]
	                     });  
	            		}
	        	} else if (type == 'customerChange'){	        	
		             $scope.companyList = response.data;
		             if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
		                $scope.transaction.companyId = response.data[0].id;
		                $scope.companyChange();
	                 }
	               
		        } else if(type == 'companyChange'){
		        	$scope.moduleList = response.data;
		        }else if( type == 'searchList'){		        
	        		if(response.data != null && response.data != "" && response.data != undefined){
	          			$scope.tableView = true;
	          			screenActionsSearchResultsTable.clear().rows.add(response.data).draw();
	          		}
	        	}
	          },
	        function () { 
	        	  
	          });
	    	} 
	    
	    $scope.transaction.customerId = $cookieStore.get('customerId');
	    $scope.transaction.companyId = $cookieStore.get('companyId');
	    $scope.transaction.isActive = 'Y';
	    
	    $scope.getData('vendorController/getCustomerNamesAsDropDown.json', { customerId: $cookieStore.get('customerId')}, 'getMasterData')
	    
	    $scope.customerChange = function () {    	
	    	$scope.getData('vendorController/getCompanyNamesAsDropDown.json',{customerId: ($scope.transaction.customerId == undefined || $scope.transaction.customerId == null )? '0' : $scope.transaction.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.transaction.companyId} , 'customerChange');
	    };
		
	    $scope.companyChange = function () {    	
	    	$scope.getData('approvalPathModuleControler/getApprovalPathModules.json', { customerId:$scope.transaction.customerId, companyId: $scope.transaction.companyId, approvalPathModuleId :$scope.transaction.approvalPathModuleId} , 'companyChange');
	    };
	    
	    $scope.fun_search = function () { 
	    	$scope.getData('approvalPathTransactionControler/getApprovalPathTransactions.json', angular.toJson($scope.transaction) , 'searchList');
	    };
	    
	    $scope.fun_clearSearchFields = function () {   
	    	$scope.module = new Object();
	    	$scope.tableView = false;
	    };
	}]
	);



