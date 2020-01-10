'use strict';


var usersControllers = angular.module("myApp.users",[]);

var usersSearchResultsTable;

usersControllers.controller("usersSearchCtrl", ['$scope', '$http', '$resource', '$location','$cookieStore', function ($scope, $http, $resource, $location,$cookieStore) {  

	$scope.tableView = false;
	$scope.users = new Object();
	 if($cookieStore.get('roleNamesArray').includes("Customer Audit Admin")){ 
		 $scope.users.vendorId = 1;
	 }
	 
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
        	
        	if( type == 'usersList'){
        		 $scope.usersList = response.data;
        		 if ( ! $.fn.DataTable.isDataTable( '#searchResults' ) ) {        		   
        			 usersSearchResultsTable = $('#searchResults').DataTable({          			 
                           "columns": [                              	  
                              { "data": "userName",
                              	"render": function ( data, type, full, meta ) {                 		                    		 
                     		      return '<a href="#/users/'+full.userId+'">'+data+'</a>';
                     		    }
                           },                       
                              { "data": "userType" },
                              { "data": "employeeName" },
                           	  { "data": "createdDate" },
                              { "data": "modifiedDate" }]
                     });  
            		}
        	}else if(type == 'masterData'){
        		$scope.customerList = response.data.customerList;
        		$scope.companyList = response.data.companyList;
        		$scope.usersList = response.data.usersList
        	}else if( type == 'searchList'){
        		if(response.data != null && response.data != "" && response.data != undefined){
          			$scope.tableView = true;
          			usersSearchResultsTable.clear().rows.add(response.data).draw();
          		}
        	}
          },
        function () { 
        	  
          });
    	}   
    	
    $scope.users.customerId = $cookieStore.get('customerId');
    $scope.users.companyId = $cookieStore.get('companyId');
    
    
    $scope.getData('usersController/getMaterDataForUser.json', angular.toJson($scope.users)  , 'masterData');
    
    
    $scope.getData('usersController/getUsers.json', angular.toJson($scope.users)  , 'usersList');
    
    
    $scope.fun_search = function () { 
    	$scope.getData('usersController/getUsers.json', angular.toJson($scope.users) , 'searchList');
    };
    
    $scope.fun_clearSearchFields = function () {   
    	$scope.users = new Object();
    	$scope.users.customerId = $cookieStore.get('customerId');
        $scope.users.companyId = $cookieStore.get('companyId');
    	$scope.tableView = false;
    };
}]
);



