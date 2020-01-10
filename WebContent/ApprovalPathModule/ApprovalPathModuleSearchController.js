'use strict';


var ApprovalPathModuleController = angular.module("myApp.ApprovalPathModule", []);


var screenActionsSearchResultsTable;


ApprovalPathModuleController.controller("approvalPathModuleSearchCtrl", ['$scope', '$http', '$resource', '$location','$cookieStore', function ($scope, $http, $resource, $location,$cookieStore) {  
	
	$scope.statusList = [{"id":"Y","name":"Active"},
        				 {"id":"N","name":"In-Active"}];
	$scope.tableView = false;
	$scope.module = new Object();
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
 	                $scope.module.customerId = response.data.customerList[0].customerId;
 	               $scope.customerChange();
                 }
        		 if ( ! $.fn.DataTable.isDataTable( '#searchResults' ) ) {        		   
            		 screenActionsSearchResultsTable = $('#searchResults').DataTable({          			 
                           "columns": [      
                        	   { "data": "moduleCode" },
                               { "data": "moduleName",
                              	 "render": function ( data, type, full, meta ) {                 		                    		 
                     		      return '<a href="#/approvalPathModule/'+full.approvalPathModuleId+'">'+data+'</a>';
                     		   }
                           },                             
                              { "data": "createdDate" },
                              { "data": "modifiedDate" }]
                     });  
            		}
        	} else if (type == 'customerChange'){	        	
	             $scope.companyList = response.data;
	             if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	                $scope.module.companyId = response.data[0].id;
                 }
               
	        } else if( type == 'searchList'){
        		if(response.data != null && response.data != "" && response.data != undefined){
          			$scope.tableView = true;
          			screenActionsSearchResultsTable.clear().rows.add(response.data).draw();
          		}
        	}
          },
        function () { 
        	  
          });
    	} 
    
    $scope.module.customerId = $cookieStore.get('customerId');
    $scope.module.companyId = $cookieStore.get('companyId');
    $scope.module.isActive = 'Y';
    
    $scope.getData('vendorController/getCustomerNamesAsDropDown.json', { customerId: $cookieStore.get('customerId')}, 'getMasterData')
    
    $scope.customerChange = function () {    	
    	$scope.getData('vendorController/getCompanyNamesAsDropDown.json',{customerId: ($scope.module.customerId == undefined || $scope.module.customerId == null )? '0' : $scope.module.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.module.companyId} , 'customerChange');
    };
	
	
    $scope.fun_search = function () { 
    	$scope.getData('approvalPathModuleControler/getApprovalPathModules.json', angular.toJson($scope.module) , 'searchList');
    };
    
    $scope.fun_clearSearchFields = function () {   
    	$scope.module = new Object();
    	$scope.tableView = false;
    };
}]
);



