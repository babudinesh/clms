'use strict';


var screenActionsController = angular.module("myApp.screenActions", []);


var screenActionsSearchResultsTable;


screenActionsController.controller("screenActionsSearchCtrl", ['$scope', '$http', '$resource', '$location','$cookieStore', function ($scope, $http, $resource, $location,$cookieStore) {  
   
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
            		 screenActionsSearchResultsTable = $('#searchResults').DataTable({          			 
                           "columns": [      
                        	   { "data": "groupName" },
                              { "data": "screenName",
                              	"render": function ( data, type, full, meta ) {                 		                    		 
                     		      return '<a href="#/screenActions/'+full.screenActionId+'">'+data+'</a>';
                     		    }
                           },                             
                              { "data": "actions" }]
                     });  
            		}
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
    	
    $scope.getData('screenActionsController/getGroupNames.json', {} , 'groupNamesList');
    
    $scope.fun_search = function () { 
    	$scope.getData('screenActionsController/getScreenActionsSearchList.json', angular.toJson($scope.sreenActions) , 'searchList');
    };
    
    $scope.fun_clearSearchFields = function () {   
    	$scope.sreenActions = {};  //new Object();
    	$scope.tableView = false;
    };
}]
);



