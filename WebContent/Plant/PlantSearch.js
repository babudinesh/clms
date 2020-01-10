'use strict';

var PlantController = angular.module("myApp.Plant", ['ngCookies']);
/*PlantController.service('myservice', function () {
    this.customerId = 0;    
    this.companyId =0;
    this.locationId = 0;
    this.countryId = 0;
    this.plantId =0;
    this.plantDetailsId=0;
 });*/

PlantController/*.directive('onLastRepeat', function () {
    return function (scope, element, attrs) {
        if (scope.$last) {
            setTimeout(function () {
                $('.table').DataTable();
            }, 1);
        }
    };
})*/.controller("PlantSearchCtrl", ['$scope', '$http', '$resource','$location', '$routeParams','$filter', '$cookieStore', function ($scope, $http, $resource,$location,  $routeParams, $filter,  $cookieStore) {
		var plantDataTable;
		$scope.companyName = "";
	    $scope.departmentCode="";
	    $scope.customerName = "";
	    $scope.status="Y";
	
	    $('#searchPanel').hide();
  
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
	    		} else if (type == 'customerList'){ 
	    			
	    			$scope.customerList = response.data.customerList;
	    			 if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){             		
		                $scope.customerName=response.data.customerList[0].customerId;		                
		                $scope.customerChange();
	                }
	    			 if ( ! $.fn.DataTable.isDataTable( '#plantTable' ) ) {        		   
	    				 plantDataTable = $('#plantTable').DataTable({        
	  	                     "columns": [	  	 
	  	                        { "data": "customerName" },
								{ "data": "companyName" },
								{ "data": "locationName" },
	  	                        { "data": "plantCode",
	  	                        	"render": function ( data, type, full, meta ) {                 		                    		 
	  	               		      return '<a href="#/PlantAdd/'+full.plantDetailsId+'">'+data+'</a>';
	  	               		    }
	  	                     },
	  	                     	
	  	                   		{ "data": "plantName" },
	  	                        { "data": "transDate" ,	  	                        	
	  	                        	"render": function ( data, type, full, meta ) {                 		                    		 
		  	  	               		      return $filter('date')( full.transDate, 'dd/MM/yyyy');
		  	  	               		    }
	  	                        
	  	                        },
	  	                        { "data": "status" }]
	  	               });  
	  	      		}
	    			// for button views
	 			  	if($scope.buttonArray == undefined || $scope.buttonArray == '')
	                   $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Plant'}, 'buttonsAction');
		        }else if (type == 'customerChange'){		        	
		            $scope.companyList = response.data;	 
	                   if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	   	                $scope.companyName = response.data[0].id;
	   	             $scope.companyChange();
	   	                }
		            
		        }else if(type == 'companyChange'){
		        	//alert(angular.toJson(response.data));
		        	$scope.locationList = response.data;
		        	
		        }else if(type=='search'){
		        	//alert(JSON.stringify(response.data));
		        	 $('#searchPanel').show();
		            //$scope.result = response.data.plantList;
		            plantDataTable.clear().rows.add(response.data.plantList).draw(); 
		        }
		    },function () { alert('Error') });
	    }

	    $scope.getData('vendorController/getCustomerNamesAsDropDown.json', {customerId: $cookieStore.get('customerId')}, 'customerList')
		
		$scope.customerChange = function () {
	    	//alert(JSON.stringify($scope.customerName));
	    	$scope.getData('vendorController/getCompanyNamesAsDropDown.json',{customerId: ($scope.customerName == undefined || $scope.customerName == null )? '0' : $scope.customerName,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.companyName } , 'customerChange');
	    };
	    
		$scope.companyChange = function () {
			$scope.getData('LocationController/getLocationsListBySearch.json',{customerId: ($scope.customerName == undefined || $scope.customerName == null )? '0' : $scope.customerName, companyId : ($scope.companyId == undefined || $scope.companyId.id == undefined) ? '0' : $scope.companyId.id} , 'companyChange');
		};
	
		$scope.search = function () {
			//alert($scope.customerName);
			$scope.getData('PlantController/getPlantListBySearch.json', { customerId : ($scope.customerName == undefined || $scope.customerName == undefined )? '0' : $scope.customerName  , companyId : ($scope.companyName == undefined) ? '0' : $scope.companyName  , locationId: $scope.locationId == undefined ? '' : $scope.locationId, plantName: ($scope.plantName != '' || $scope.plantName != undefined || $scope.plantName != null) ? $scope.plantName : '', status:$scope.status}, 'search');
		};
		$scope.clear = function () {
			$scope.companyName="";
			$scope.customerName = "";
			$scope.locationId = "";
			$scope.plantName="";
		};
	
		
}]);


