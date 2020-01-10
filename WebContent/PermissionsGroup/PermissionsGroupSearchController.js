'use strict';


var permissionsGroupControllers = angular.module("myApp.permissionsGroup",[]);


var permissionsGroupSearchResultsTable;


permissionsGroupControllers.controller("permissionsGroupSearchCtrl", ['$scope', '$http', '$resource', '$location','$cookieStore', function ($scope, $http, $resource, $location,$cookieStore) {  
	$scope.groupPermission = new Object();
	$scope.tableView = false;
	$scope.sreenActions = new Object();
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
        	
        	if( type == 'groupNamesList'){
        		 $scope.groupNames = response.data;
        		 if ( ! $.fn.DataTable.isDataTable( '#searchResults' ) ) {        		   
        			 permissionsGroupSearchResultsTable = $('#searchResults').DataTable({          			 
                           "columns": [                              	  
                              { "data": "permissionsGroupName",
                              	"render": function ( data, type, full, meta ) {                 		                    		 
                     		      return '<a href="#/permissionsGroup/'+full.permissionsGroupId+'">'+data+'</a>';
                     		    }
                           },          
                           	  { "data": "permissionsGroupDesc" },
                           	  { "data": "createdDate" },
                              { "data": "modifiedDate" }]
                     });  
            		}
        	}else if( type == 'searchList'){
        		if(response.data != null && response.data != "" && response.data != undefined){
          			$scope.tableView = true;
          			permissionsGroupSearchResultsTable.clear().rows.add(response.data).draw();
          		}
        	}
          },
        function () { 
        	  
          });
    	}   
    	
    $scope.getData('permissionsGroupController/getPermissionsGroupName.json', {} , 'groupNamesList');
    
    $scope.fun_search = function () { 
    	$scope.getData('permissionsGroupController/getPermissionsGroupName.json', angular.toJson($scope.groupPermission) , 'searchList');
    };
    
    $scope.fun_clearSearchFields = function () {   
    	$scope.sreenActions = {};  //new Object();
    	$scope.tableView = false;
    };
}]
);



