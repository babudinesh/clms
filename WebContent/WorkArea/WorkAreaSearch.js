'use strict';

var WorkAreaController = angular.module("myApp.WorkArea", ['ngCookies']);

WorkAreaController.service('myservice', function () {
    this.customerId = 0;    
    this.companyId =0;
    this.locationId = 0;
    this.countryId = 0;
    this.plantId =0;
    this.workAreaDetailsId = 0;
    this.workAreaId=0;
 });

WorkAreaController/*.directive('onLastRepeat', function () {
    return function (scope, element, attrs) {
        if (scope.$last) {
            setTimeout(function () {
                $('.table').DataTable();
            }, 1);
        }
    };
})*/.controller("WorkAreaSearchCtrl",  ['$scope', '$http', '$resource','$location', '$routeParams','myservice','$cookieStore','$filter', function ($scope, $http, $resource, $location,  $routeParams, myservice,$cookieStore,$filter) {
	var workAreaDataTable;
	$scope.companyName = "";
    $scope.departmentCode="";
    $scope.customerName = "";
    $scope.resultDataView = false;
    $scope.status="Y";
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
    			  if ( ! $.fn.DataTable.isDataTable( '#workAreaTable' ) ) {        		   
    				 	workAreaDataTable = $('#workAreaTable').DataTable({        
	  	                     "columns": [
	  	                        { "data": "customerName" },
								{ "data": "companyName" },
	  	                        { "data": "workAreaCode",
	  	                        	"render": function ( data, type, full, meta ) {                 		                    		 
	  	               		      return '<a href="#/WorkAreaAdd/'+full.workAreaDetailsId+'">'+data+'</a>';
	  	               		    }
	  	                        },
	  	                     	{ "data": "workAreaName" },	  	                     	
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
    			  $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Work Area'}, 'buttonsAction');
	        }else if (type == 'customerChange'){	        	
	            $scope.companyList = response.data;
	            if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
   	                $scope.companyName = response.data[0].id;
   	                $scope.companyChange();
   	            }
	            
	        }else if(type == 'companyChange'){
	        	$scope.locationList = response.data;
	        	if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
   	                $scope.locationName = response.data[0].locationId;
   	                $scope.locationChange();
   	            }
	        }else if(type == "locationChange"){
	        	$scope.plantList = response.data.plantList;
	        	if( response.data.plantList[0] != undefined && response.data.plantList[0] != "" && response.data.plantList.length == 1 ){
   	                $scope.plantId = response.data.plantList[0].plantId;
   	                $scope.plantChange();
   	            }
	        }else if(type == "plantChange"){
	        	$scope.sectionList = response.data.sectionList;
	        	if( response.data.sectionList[0] != undefined && response.data.sectionList[0] != "" && response.data.sectionList.length == 1 ){
   	                $scope.sectionId = response.data.sectionList[0].sectionDetailsId;
   	             $scope.plantChange();
   	            }
	        }else if(type=='search'){
	        	$scope.resultDataView = true;
	        	workAreaDataTable.clear().rows.add(response.data.workAreaList).draw(); 
	        	//alert(JSON.stringify(response.data));
	           /* $scope.result = response.data.workAreaList;
	            if(response.data.workAreaList.length > 0){
	            	$scope.resultDataView = true;
	            }*/
	        }else if(type == 'DepartmentList'){
	        	 $scope.departmentList = response.data.departmentList;	  
	        }
	    },function () { alert('Error') });
    }

    $scope.getData('vendorController/getCustomerNamesAsDropDown.json', { customerId: $cookieStore.get('customerId')}, 'customerList')
	
	$scope.customerChange = function () {
    	//alert(JSON.stringify($scope.customerName));
    	$scope.getData('vendorController/getCompanyNamesAsDropDown.json',{customerId: ($scope.customerName == undefined || $scope.customerName == null )? '0' : $scope.customerName,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : ($scope.companyName == undefined || $scope.companyName == undefined) ? '0' : $scope.companyName} , 'customerChange');
    };
	$scope.companyChange = function () {
		//alert($scope.customerName);
		$scope.getData('LocationController/getLocationsListBySearch.json',{customerId: ($scope.customerName == undefined || $scope.customerName == null )? '0' : $scope.customerName, companyId : ($scope.companyName == undefined || $scope.companyName == undefined) ? '0' : $scope.companyName} , 'companyChange');
	};
	$scope.locationChange= function(){
		$scope.getData('PlantController/getPlantListBySearch.json', { customerId : ($scope.customerName == undefined || $scope.customerName == undefined )? '0' : $scope.customerName  , companyId : ($scope.companyName == undefined || $scope.companyName == undefined) ? '0' : $scope.companyName  , locationId: $scope.locationName == undefined ? '' : $scope.locationName}, 'locationChange');
	};
	
	$scope.plantChange= function(){		
		if($scope.locationName != undefined && $scope.locationName != null && $scope.locationName != '' && $scope.plantId != undefined && $scope.plantId != null && $scope.plantId != ''){
       		$scope.getData('associatingDepartmentToLocationPlantController/getDepartMentDetailsByLocationAndPlantId.json', {customerId:$scope.customerName,companyId:$scope.companyName,locationId:$scope.locationName,plantId:$scope.plantId}, 'DepartmentList');
       	}
	};
	
	$scope.departmentChange= function(){		
		$scope.getData('sectionController/getSectionListBySearch.json', { customerId : ($scope.customerName == undefined || $scope.customerName == undefined )? '0' : $scope.customerName  , companyId : ($scope.companyName == undefined || $scope.companyName == undefined) ? '0' : $scope.companyName  , locationId: $scope.locationName == undefined ? '' : $scope.locationName , plantDetailsId : $scope.plantId == undefined ? '' : $scope.plantId ,departmentId:$scope.departmentId}, 'plantChange');
	};
	

	
	
	$scope.search = function () {
		//alert($scope.locationId);
		$scope.getData('WorkAreaController/getWorkAreaListBySearch.json', { customerId : ($scope.customerName == undefined || $scope.customerName == undefined )? '0' : $scope.customerName  , companyId : ($scope.companyName == undefined || $scope.companyName == undefined) ? '0' : $scope.companyName  , locationId: $scope.locationName == undefined ? '' : $scope.locationName, plantId: $scope.plantId == undefined ? '' : $scope.plantId, workAreaName : $scope.workAreaName == undefined ? '' : $scope.workAreaName,   sectionDetailsId : $scope.sectionId  == undefined ? '0' : $scope.sectionId , status : $scope.status,departmentId:($scope.departmentId != undefined && $scope.departmentId != '') ? $scope.departmentId : 0 }, 'search');
	};
	$scope.clear = function () {
		$scope.companyName="";
		$scope.customerName = "";
		$scope.locationName = "";
		$scope.plantId="";
		$scope.workAreaName="";
		$scope.sectionId="";
		$scope.status="Y";
	};
    /*var Customerlist = $resource('/WorkArea/WorkAreaSearch/Customerdata.json');
    Customerlist.get(function (user) {
        $scope.result = user.result;
    });*/
    $.material.init();
}]);


