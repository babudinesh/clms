'use strict';


var rolesControllers = angular.module("myApp.roles",[]);

var rolesSearchResultsTable;

rolesControllers.controller("rolesSearchCtrl", ['$scope', '$http', '$resource', '$location','$cookieStore', function ($scope, $http, $resource, $location,$cookieStore) {  
	$scope.groupPermission = new Object();
	$scope.tableView = false;
	$scope.roles = new Object();
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
        	
        	if( type == 'rolesList'){
        		 $scope.groupNames = response.data;
        		 if ( ! $.fn.DataTable.isDataTable( '#searchResults' ) ) {        		   
        			 rolesSearchResultsTable = $('#searchResults').DataTable({          			 
                           "columns": [                              	  
                              { "data": "roleName",
                              	"render": function ( data, type, full, meta ) {                 		                    		 
                     		      return '<a href="#/roles/'+full.roleId+'">'+data+'</a>';
                     		    }
                           },          
                           	  { "data": "roleDesc" },
                           	  { "data": "createdDate" },
                              { "data": "modifiedDate" }]
                     });  
            		}
        	}else if(type == 'masterData'){
        		$scope.customerList = response.data.customerList;
        		$scope.companyList = response.data.companyList;
        	}else if( type == 'searchList'){
        		if(response.data != null && response.data != "" && response.data != undefined){
          			$scope.tableView = true;
          			rolesSearchResultsTable.clear().rows.add(response.data).draw();
          		}
        	}
          },
        function () { 
        	  
          });
    	}   
    	
    $scope.roles.customerId = $cookieStore.get('customerId');
    $scope.roles.companyId = $cookieStore.get('companyId');
    
    $scope.getData('rolesController/getRoles.json', angular.toJson($scope.roles)  , 'rolesList');
    $scope.getData('rolesController/getMaterDataForRole.json', angular.toJson($scope.roles)  , 'masterData');
    
    $scope.fun_search = function () { 
    	$scope.getData('rolesController/getRoles.json', angular.toJson($scope.roles) , 'searchList');
    };
    
    $scope.fun_clearSearchFields = function () {   
    	$scope.roles = new Object();
    	$scope.roles.customerId = $cookieStore.get('customerId');
        $scope.roles.companyId = $cookieStore.get('companyId');
    	$scope.tableView = false;
    };
}]
);



