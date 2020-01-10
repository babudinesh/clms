'use strict';


var ApprovalPathFlowController = angular.module("myApp.ApprovalPathFlow", []);


var screenActionsSearchResultsTable;


ApprovalPathFlowController.controller("approvalPathFlowSearchCtrl", ['$scope', '$http', '$resource', '$location','$cookieStore', function ($scope, $http, $resource, $location,$cookieStore) {  
   
		$scope.statusList = [{"id":"Y","name":"Active"},
	        				 {"id":"N","name":"In-Active"}];
		$scope.tableView = false;
		$scope.pathflow = new Object();
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
	 	                $scope.pathflow.customerId = response.data.customerList[0].customerId;
	 	               $scope.customerChange();
	                 }
	        		 if ( ! $.fn.DataTable.isDataTable( '#searchResults' ) ) {        		   
	            		 screenActionsSearchResultsTable = $('#searchResults').DataTable({          			 
	                           "columns": [      
	                        	   { "data": "moduleCode" },
	                        	   { "data": "moduleName" },
	                        	   { "data": "transactionCode" },
	                        	   { "data": "transactionName" },
	                               { "data": "pathCode",
	                              	 "render": function ( data, type, full, meta ) {                 		                    		 
	                     		      return '<a href="#/applicationApprovalPathFlow/'+full.applicationApprovalPathId+'">'+data+'</a>';
	                     		   }
	                           },                             
	                           { "data": "pathName" },
	                           { "data": "createdDate" },
	                              { "data": "modifiedDate" }]
	                     });  
	            		}
	        	} else if (type == 'customerChange'){	        	
		             $scope.companyList = response.data;
		             if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
		                $scope.pathflow.companyId = response.data[0].id;
		                $scope.companyChange();
	                 }
	               
		        } else if(type == 'companyChange'){
		        	$scope.moduleList = response.data;
		        } else if( type == 'moduleChange'){
		        	$scope.transactionList = response.data;
		        } else if( type == 'searchList'){		        
	        		if(response.data != null && response.data != "" && response.data != undefined){
	          			$scope.tableView = true;
	          			screenActionsSearchResultsTable.clear().rows.add(response.data).draw();
	          		}else{
	          			$scope.tableView = false;
	          			screenActionsSearchResultsTable.clear();
	          		}
	        	}
	          },
	        function () { 
	        	  
	          });
	    	} 
	    
	    $scope.pathflow.customerId = $cookieStore.get('customerId');
	    $scope.pathflow.companyId = $cookieStore.get('companyId');
	    $scope.pathflow.isActive = 'Y';
	    
	    $scope.getData('vendorController/getCustomerNamesAsDropDown.json', { customerId: $cookieStore.get('customerId')}, 'getMasterData')
	    
	    $scope.customerChange = function () {    	
	    	$scope.getData('vendorController/getCompanyNamesAsDropDown.json',{customerId: ($scope.pathflow.customerId == undefined || $scope.pathflow.customerId == null )? '0' : $scope.pathflow.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.pathflow.companyId} , 'customerChange');
	    };
		
	    $scope.companyChange = function () {    	
	    	$scope.getData('approvalPathModuleControler/getApprovalPathModules.json', { customerId:$scope.pathflow.customerId, companyId: $scope.pathflow.companyId, approvalPathModuleId :$scope.pathflow.approvalPathModuleId} , 'companyChange');
	    };
	    
	    $scope.moduleChange = function () {    	
	    	$scope.getData('approvalPathTransactionControler/getApprovalPathTransactions.json', { approvalPathModuleId:$scope.pathflow.approvalPathModuleId} , 'moduleChange');
	    };
	    
	    
	    $scope.fun_search = function () { 
	    	$scope.getData('applicationApprovalPathFlowControler/getApplicationApprovalPaths.json', angular.toJson($scope.pathflow) , 'searchList');
	    };
	    
	    $scope.fun_clearSearchFields = function () {   
	    	$scope.module = new Object();
	    	$scope.tableView = false;
	    };
	}]
	);



