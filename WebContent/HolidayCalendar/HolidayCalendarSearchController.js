'use strict';

var HolidayController = angular.module("myApp.Holiday", ['ngCookies']);
/*HolidayController.service('myservice', function () {
    this.customerId = 0;    
    this.companyId =0;
    this.locationId = 0;
    this.countryId = 0;
    this.holidayCalendarId =0;
    this.holidayCalendarDetailsId=0;
 });*/

HolidayController/*.directive('onLastRepeat', function () {
    return function (scope, element, attrs) {
        if (scope.$last) {
            setTimeout(function () {
                $('.table').DataTable();
            }, 1);
        }
    };
})*/.controller("HolidaySearchCtrl", ['$scope', '$http', '$resource','$location', '$routeParams','$filter', '$cookieStore', function ($scope, $http, $resource,$location,  $routeParams, $filter,  $cookieStore) {
	var calendarDataTable;
		$scope.companyName = "";
	    $scope.customerName = "";
	    $scope.status = "Y";
	    $scope.calendarTableView = false;
  
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
	    			
	    			 if ( ! $.fn.DataTable.isDataTable( '#calendarTables' ) ) {        		   
	    				 calendarDataTable = $('#calendarTables').DataTable({        
	  	                     "columns": [	  	                       
	  	                     	{ "data": "customerName" },
  	                     		{ "data": "companyName" },
  	                     		{ "data": "countryName" },
  	                     		{ "data": "locationName" },
  	                     		{ "data": "holidayCalendarCode",
	  	                        	"render": function ( data, type, full, meta ) {                 		                    		 
	  	                        		return '<a href="#/HolidayCalendarAdd/'+full.holidayCalendarDetailsId+'">'+data+'</a>';
	  	                        	}
	  	                        },
	  	                   		{ "data": "calendarName" },
	  	                        { "data": "year" },
	  	                        { "data": "status" }]
	  	               });  
	  	      		}
	    			// for button views
	   			  	if($scope.buttonArray == undefined || $scope.buttonArray == '')
	    			 $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Holiday Calendar'}, 'buttonsAction'); 
		        }else if (type == 'customerChange'){
		            $scope.companyList = response.data;
		            if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	   	                $scope.companyName = response.data[0].id;
	   	             $scope.companyChange();
	   	                }
		        }else if(type == 'companyChange'){
		        	$scope.locationList = response.data;
		        }else if(type=='search'){
		        	$scope.calendarTableView = true;
		        	calendarDataTable.clear().rows.add(response.data.holidayCalendarList).draw(); 
		           // $scope.result = response.data.holidayCalendarList;
		        }
		    },function () { alert('Error') });
	    }
	    
	    $scope.getData('vendorController/getCustomerNamesAsDropDown.json', { customerId: $cookieStore.get('customerId')}, 'customerList')
		
		$scope.customerChange = function () {
	    	$scope.getData('vendorController/getCompanyNamesAsDropDown.json',{customerId: ($scope.customerName == undefined || $scope.customerName == null )? '0' : $scope.customerName,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.companyName != undefined ? $scope.companyName : 0} , 'customerChange');
	    };
		$scope.companyChange = function () {
			
			$scope.getData('LocationController/getLocationsListBySearch.json',{customerId: ($scope.customerName == undefined || $scope.customerName == null )? '0' : $scope.customerName, companyId : ($scope.companyName == undefined || $scope.companyName == '') ? '0' : $scope.companyName} , 'companyChange');
		};
	
		$scope.search = function () {
			//alert($scope.customerName+", "+$scope.companyName+", "+$scope.locationId+", "+$scope.holidayCalendarCode+", "+$scope.status);
			$scope.getData('HolidayController/getHolidayCalendarListBySearch.json', { customerId : $cookieStore.get('customerId')  , companyId : ($scope.companyName == undefined || $scope.companyName == null) ? '0' : $scope.companyName  , locationId: $scope.locationId == undefined ? '' : $scope.locationId,  holidayCalendarCode :$scope.holidayCalendarCode == undefined ? '' : $scope.holidayCalendarCode  ,status :$scope.status}, 'search');
		};
		$scope.clear = function () {
			$scope.companyName="";
			$scope.customerName = "";
			$scope.locationId = "";
			$scope.countryId="";
			$scope.holidayCalendarCode = "";
			$scope.status = "Y";
		};
	
		$scope.plusIconAction = function(){
	       	$scope.popUpSave = true;
	       	$scope.popUpUpdate =false;
	    }
}]);


