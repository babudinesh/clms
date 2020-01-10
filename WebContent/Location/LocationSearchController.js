'use strict';
var LocationController = angular.module("myApp.Location", ['ngCookies']);

/*LocationController.service('myservice', function () {
    this.companyId = 0;
    this.customerId = 0;
    this.locationId = 0;
    this.countryId = 0;
    this.locationDetailsId = 0;
   
});*/

/*LocationController.directive('onLastRepeat', function () {
    return function (scope, element, attrs) {
        if (scope.$last) {
            setTimeout(function () {
                $('.table').DataTable();
            }, 1);
        }
    };
})*/
LocationController.controller("LocationSearchCtrl", ['$scope', '$http','$filter', '$resource', '$routeParams', '$location', '$cookieStore', function ($scope, $http, $filter, $resource, $routeParams, $location, $cookieStore) {	
		$('#searchPanel').hide();
		var locationDataTable;	
		$scope.location = new Object();
		$scope.location.status = "Y";
	    $scope.enable = false;
	    
	    $scope.Messager = function (type, heading, text, btn) {
	    	$.toast({
	    		heading: heading,
	    		icon: type,
	    		text: text,
	    		stack: false, beforeShow: function () {
	    			$scope.enable = true;
	    		},
	    		afterHidden: function () {
	    			$scope.enable = false;
	    		}
	    	});
	    }
	    
 	    $scope.getData = function (url, data, type, buttonDisable) {
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
 	            	  $scope.customerName=response.data.customerList[0];		                
		                $scope.customerChange();
 	               }else{
 	            	   $scope.location.status = "Y";
 	               }
 	               
 	              if ( ! $.fn.DataTable.isDataTable('#locDT') ) {   
 	            	 setTimeout(function () {
 	            		locationDataTable = $('#locDT').DataTable({        
		                     "columns": [
		                        { "data": "customerName" },
		                        { "data": "companyName" }, 
		                        { "data": "locationCode",
		                        	"render": function ( data, type, full, meta ) {
		               		      return '<a href="#/LocationAdd/'+full.locationDetailsId+'">'+data.toUpperCase()+'</a>';
		               		    }
		                     },
		                        { "data": "locationName" },
		                       { "data": "transDate" },
		                        { "data": "status" }]
		               });
 	                }, 10);
 	            	  	            	
 		      		}
 	          // for button views
 				  	if($scope.buttonArray == undefined || $scope.buttonArray == '')
 	              $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Location'}, 'buttonsAction');
 	             
 	            }else if (type == 'customerChange'){
 	                $scope.companyList = response.data;
 	               if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
 		                $scope.companyName = response.data[0];
 		                }
 	            }else if(type=='search'){ 	            	
 	            	locationDataTable.clear().rows.add(response.data).draw();  	            	
 	               // $scope.locationList = response.data;
 	            }
 	        },
 	        function () { 
 	        	$scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
 	        });
 	    }


 	    $scope.getData('vendorController/getCustomerNamesAsDropDown.json', { customerId: $cookieStore.get('customerId')}, 'customerList')

 	    $scope.customerChange = function () {
 	    	//alert($scope.customerName);
 	        $scope.getData('vendorController/getCompanyNamesAsDropDown.json',{customerId: $scope.customerName.customerId == undefined ?'0': $scope.customerName.customerId, companyId: ($cookieStore.get('companyId') !=null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.companyName != undefined ? $scope.companyName : 0}, 'customerChange');
 	    };

 	    
 	    $scope.search = function () {			
 	        $scope.getData('LocationController/getLocationsListBySearch.json', { customerId: ($scope.customerName == undefined || $scope.customerName.customerId == undefined )? '0': $scope.customerName.customerId, companyId: ($scope.companyName == undefined || $scope.companyName.id == undefined )? '0':$scope.companyName.id,  locationCode:$scope.location.locationCode, locationName:$scope.location.locationName, status:$scope.location.status}, 'search');
 	       $('#searchPanel').show();
 	    };
 	    $scope.clear = function () {
 	        $scope.customerName = "";
 	        $scope.companyName = "";
 	        $scope.location.locationCode = "";
 	        $scope.location.locationName ="";
 	        $scope.location.status="Y";
 	    };
 }]

);





