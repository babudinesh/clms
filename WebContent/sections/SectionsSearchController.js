'use strict';

var sectionController = angular.module("myApp.SectionModel", ['ngCookies']);


sectionController/*.directive('onLastRepeat', function () {
    return function (scope, element, attrs) {
        if (scope.$last) {
            setTimeout(function () {
                $('.table').DataTable();
            }, 1);
        }
    };
})*/.controller("sectionSearchCtrl",  ['$scope', '$http', '$resource','$location', '$routeParams','myservice','$cookieStore','$filter', function ($scope, $http, $resource, $location,  $routeParams, myservice,$cookieStore,$filter) {
	var sectionDataTable;
	$scope.gridDataView = false;
	$scope.companyName = "";
    $scope.departmentCode="";
    $scope.customerName = "";
 
$scope.status = 'Y';
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
    			 //  alert(JSON.stringify(response.data.customerList[0]));
         	   if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){
         		//   alert(JSON.stringify(response.data.customerList[0].customerId));
	                $scope.customerName=response.data.customerList[0].customerId;		                
	                $scope.customerChange();
	                }
         	  if ( ! $.fn.DataTable.isDataTable( '#sectionTable' ) ) {        		   
         		 sectionDataTable = $('#sectionTable').DataTable({        
	                     "columns": [	
							{ "data": "customerName" },
							{ "data": "companyName" },
	                        { "data": "sectionCode",
	                        	"render": function ( data, type, full, meta ) {                 		                    		 
	               		      return '<a href="#/sectionDetails/'+full.sectionDetailsInfoId+'">'+data+'</a>';
	               		    }
	                     },
	                     	
                    		{ "data": "locationName" },
	                   		{ "data": "plantName" },
	                        { "data": "sectionName" },
	                        { "data": "transactionDate",
	                        	"render": function ( data, type, full, meta ) {                 		                    		 
	  	               		      return $filter('date')( full.transactionDate, 'dd/MM/yyyy');
	  	               		    }
	                        },
	                        { "data": "status" }]
	               });  
	      		}
         	// for button views
			  	if($scope.buttonArray == undefined || $scope.buttonArray == '')
              $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Section'}, 'buttonsAction');
	        }else if (type == 'customerChange'){	        	
	            $scope.companyList = response.data;
	             if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	                $scope.companyName = response.data[0].id;
	                $scope.companyChange();
	                }
                
	        }else if(type == 'companyChange'){
	        
	        	$scope.locationList = response.data;
	        }else if(type == "locationChange"){
	        	$scope.plantList = response.data.plantList;
	        }else if(type=='search'){	        
	        	sectionDataTable.clear().rows.add(response.data.sectionList).draw(); 
	        	$scope.gridDataView = true;
	        	/*
	            $scope.result = response.data.sectionList;
	            if(response.data.sectionList != null && response.data.sectionList.length > 0)
	            $scope.gridDataView = true;*/
	        }else if (type == 'plantChange') {            	
         	   $scope.departmentList = response.data.departmentList;	            	
    	       
	          }
	    },function () { alert('Error') });
    }

    $scope.getData('vendorController/getCustomerNamesAsDropDown.json', { customerId: $cookieStore.get('customerId')}, 'customerList')
	
	$scope.customerChange = function () {    	
    	$scope.getData('vendorController/getCompanyNamesAsDropDown.json',{customerId: ($scope.customerName == undefined || $scope.customerName == null )? '0' : $scope.customerName,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.companyName} , 'customerChange');
    };
	$scope.companyChange = function () {		
		$scope.getData('LocationController/getLocationsListBySearch.json',{customerId: ($scope.customerName == undefined || $scope.customerName == null )? '0' : $scope.customerName, companyId : ($scope.companyName == undefined || $scope.companyName == undefined) ? '0' : $scope.companyName} , 'companyChange');
	};
	$scope.locationChange= function(){
		$scope.getData('PlantController/getPlantListBySearch.json', { customerId : ($scope.customerName == undefined || $scope.customerName == '' )? '0' : $scope.customerName  , companyId : ($scope.companyName == undefined || $scope.companyName == undefined) ? '0' : $scope.companyName  , locationId: ($scope.locationName == undefined || $scope.locationName == '' )? '' : $scope.locationName}, 'locationChange');

	};
	
	
	 $scope.plantChange = function(){
	       	if($scope.locationName != undefined && $scope.locationName != null && $scope.locationName != '' && $scope.plantId != undefined && $scope.plantId != null && $scope.plantId != ''){
	       		$scope.getData('associatingDepartmentToLocationPlantController/getDepartMentDetailsByLocationAndPlantId.json', {customerId:$scope.customerName,companyId:$scope.companyName,locationId:$scope.locationName,plantId:$scope.plantId}, 'plantChange');
	       	}
	}
	 
	 
	$scope.search = function () {		
		$scope.getData('sectionController/getSectionListBySearch.json', { customerId : ($scope.customerName == undefined || $scope.customerName == '' ) ? '0' : $scope.customerName  , companyId : ($scope.companyName == undefined || $scope.companyName == undefined) ? '0' : $scope.companyName  , locationId : ($scope.locationName == undefined || $scope.locationName == '' )? '0' : $scope.locationName, plantDetailsId: ($scope.PlantName == undefined || $scope.PlantName == '')? '0' : $scope.PlantName, sectionName : $scope.sectionName == undefined ? '' : $scope.sectionName, status:$scope.status ,departmentId:($scope.departmentId != undefined && $scope.departmentId != '') ? $scope.departmentId : 0 }, 'search');
	};
	
	
	$scope.clear = function () {
		$scope.companyName="";
		$scope.customerName = "";
		$scope.locationName = "";
		$scope.PlantName="";
		$scope.sectionName = "";
	};

    $.material.init();
}]);


