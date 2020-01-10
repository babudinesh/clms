'use strict';


var customerControlller = angular.module("myApp.CustomerDetails", []);


var customerSearchResultsTable;

/*customerControlller.directive('onLastRepeat', function () {
    return function (scope, element, attrs) {
        if (scope.$last) {
            setTimeout(function () {
             $('.table').DataTable();
             
            }, 1);
        }
    };
})*/customerControlller.controller("customersearchCtrl", ['$scope', '$http', '$resource', '$location','$cookieStore', function ($scope, $http, $resource, $location,$cookieStore) {  
   
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
        	} else if ( ! $.fn.DataTable.isDataTable( '#searchResults' ) ) {        		   
        		 customerSearchResultsTable = $('#searchResults').DataTable({  
        			   "data" : response.data,
                       "columns": [                        
                          { "data": "customerName",
                          	"render": function ( data, type, full, meta ) {                 		                    		 
                 		      return '<a href="#/CustomerDetails/'+full.customerinfoId+'">'+data+'</a>';
                 		    }
                       },
                          { "data": "customerCode" },
                          { "data": "isActive" }]
                 });  
        		// for button views
        		 if($scope.buttonArray == undefined || $scope.buttonArray == '')
        		 $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Customer'}, 'buttonsAction');	
        	
        	}
        	 
               // $scope.result = response.data;
                //alert(JSON.stringify(response.data));
                
          },
        function () { 
        	  
        	 // alert('Network Issue Please try again Later....')
        	  
          });
    	}   
    
    // $scope.roleId = $cookieStore.get('roleId');
    $scope.roleName = $cookieStore.get('roleName');

    	
    
    
    	
    $scope.getData('CustomerController/CustomerGridList.json', { customerId: $cookieStore.get('customerId')}, 'customerList')
    
    
    $scope.clear = function () {           
    };
}]
);



