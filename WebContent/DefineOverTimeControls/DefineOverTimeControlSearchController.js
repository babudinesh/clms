'use strict';

var defineOverTimeControlsController = angular.module("myApp.DefineOverTimeControls", ['ngCookies']);



/*defineOverTimeControlsController.directive('onLastRepeatData', function($compile, $timeout){
    return function(scope, elem, attr) {
   	 if (scope.$last) {
	        $timeout(function(){
	            elem.attr('datatable', 'ng');
	            elem.removeAttr('my-datatable');
	            $compile(elem)(scope);
	        }, 1000);
   	 }
 };
    
    
})*/

defineOverTimeControlsController.controller("defineOverTimeControlSearchCtrl",  ['$scope', '$http', '$resource', '$routeParams', '$location', '$filter','$cookieStore', function ($scope, $http, $resource, $routeParams, $location, $filter, $cookieStore) {  	
	var defineOverTimeTables;
	$scope.defineOverTimeTableShow = false;
	$scope.overTime = new Object();
	$scope.overTime.isActive = 'Y';
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
            if (type == 'customerList')
            { 
                $scope.customerList = response.data.customerList;  
                if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
		                $scope.overTime.customerDetailsId=response.data.customerList[0].customerId;		                
		                $scope.customerChange();
		        }              
                if ( ! $.fn.DataTable.isDataTable( '#defineOverTimeTable' ) ) {                  	
           		 defineOverTimeTables = $('#defineOverTimeTable').DataTable({        
                          "columns": [
                             { "data": "customerName" },
                             { "data": "companyName" },
                             { "data": "overTimeControlCode",
                             	"render": function ( data, type, full, meta ) {                 		                    		 
                    		      return '<a href="#/DefineOverTimeControl/'+full.defineOverTimeDetailsInfoId+'">'+data+'</a>';
                    		    }
                          },
                             { "data": "overTimeControlName" },
                             { "data": "isActive" }]
                    });             		 
           		}
                
            }else if(type == 'customerChange'){	        		
        		$scope.companyList = response.data; 
        		  if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
     	                $scope.overTime.companyDetailsId = response.data[0].id;
     	                $scope.companyChange();
     	                }
        	}else if (type == 'companyChange') {       		
                $scope.countryList = response.data.countriesList; 
                $scope.overTime.country = response.data.countriesList[0].id;
                $scope.overTimeNamesList = response.data.overTimeNamesList; 
                
            }else if(type=='search'){
            	$scope.defineOverTimeTableShow = true;            	
            	defineOverTimeTables.clear().rows.add(response.data).draw(); 
              		
            	
            	
                // $scope.result = response.data;
            }
        },
        function () {
        	//alert('Error') 
        	
        });
    }   

    $scope.getData('vendorController/getCustomerNamesAsDropDown.json', { customerId: $cookieStore.get('customerId')}, 'customerList')
    
    $scope.customerChange = function () { 	
    	//alert($scope.customerDetailsId);
	    	if($scope.overTime.customerDetailsId != undefined && $scope.overTime.customerDetailsId != ""){
	    	$scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.overTime.customerDetailsId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.overTime.companyDetailsId != undefined ? $scope.overTime.companyDetailsId : 0}, 'customerChange');
	    	}
	    };
	    
	    
	    
    $scope.companyChange = function () {
    	//alert($scope.companyDetailsId);
    	if($scope.overTime.companyDetailsId != undefined && $scope.overTime.companyDetailsId != ""){
    		$scope.getData('defineOverTimeControlDetailsController/getOverTimeNamesByCompanyId.json', angular.toJson($scope.overTime) , 'companyChange');
    	}
    };

      
    $scope.search = function () {
        $scope.getData('defineOverTimeControlDetailsController/getOverTimeDetailsForGrid.json',  angular.toJson($scope.overTime) , 'search');
    };
    
    
    
    $scope.clear = function () {
           
    };
}]
)/*.directive('onLastRepeatData', function($compile, $timeout){
    return function(scope, elem, attr) {
    	 if (scope.$last) {
	        $timeout(function(){
	            elem.attr('datatable', 'ng');
	            elem.removeAttr('my-datatable');
	            $compile(elem)(scope);
	        }, 1000);
    	 }
  };
})*/;



