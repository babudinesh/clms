'use strict';

var companyWorkOrder = angular.module("myApp.companyWorkOrder", []);

var companyWorkOrderTable;
companyWorkOrder.controller("companyWorkOrderSearchCtrl",  ['$scope', '$rootScope', '$http', '$resource','$location','myservice','$cookieStore', function($scope,$rootScope,$http,$resource,$location,myservice,$cookieStore) {
    
   $scope.companyWorkOrderTableView = false;
	$scope.status = 'Y';
	  
	 // FOR COMMON POST METHOD
	  $scope.getPostData = function (url, data, type) {
	      var req = {
	          method: 'POST',
	          url: ROOTURL + url,
	          headers: {
	              'Content-Type': 'application/json'
	          },
	          data: data
	      }
	      $http(req).then(function (response) {
	    	if(type == 'buttonsAction'){
	    			$scope.buttonArray = response.data.screenButtonNamesArray;
	    	} else if( type == 'gridData' ){	
	      		$scope.companyWorkOrderTableView = true;
	      		companyWorkOrderTable.clear().rows.add(response.data).draw(); 
	      		//$scope.result = response.data;
	      	}else if(type == 'companyDropDown'){
	      		$scope.companyList = response.data;
	      	  if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
 	                $scope.companyName = response.data[0].id;
 	               $scope.fun_CompanyChangeListener();
 	                }
	      	}else if(type == 'locationsDropDown'){
	      		$scope.list_locations = response.data.locationList	 
	      		$scope.list_departments = response.data.departmentList;	
	      	}else if( type == 'companyGrid' ){	

	      		 if ( ! $.fn.DataTable.isDataTable( '#companyWorkOrderTable' ) ) {        		   
	      			companyWorkOrderTable = $('#companyWorkOrderTable').DataTable({        
	      		                     "columns": [
	      		                        { "data": "customerName" },
	      		                        { "data": "companyName" },
	      		                        { "data": "workOrderCode" },
	      		                        { "data": "workOrderName",
	      		                        	"render": function ( data, type, full, meta ) {                 		                    		 
	      		               		                      return '<a href="#/companyWorkOrder/'+full.workOrderInfoId+'">'+data+'</a>';
	      		               		                  }
	      		                        },
	      		                        { "data": "locationName" },
	      		                        { "data": "departmentName" },
	      		                        { "data": "isActive" }]
	      		               });  
	      		      		}
	      		
		      		$scope.customerList = response.data.customerList;
		      		 if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
	   		                $scope.customerName=response.data.customerList[0].customerId;		                
	   		                $scope.fun_CustomerChangeListener();
	   		                }
		      	// for button views
		      		if($scope.buttonArray == undefined || $scope.buttonArray == '')
		      		$scope.getPostData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Company Work Order'}, 'buttonsAction');
	      	}        	
	      })
	  };
	  
	  
   // for getting Master data for Grid List 
   $scope.getPostData("vendorController/getCustomerNamesAsDropDown.json",{ customerId: $cookieStore.get('customerId')}, "companyGrid" );
	  	     
   // Customer Change Listener to get company details
   $scope.fun_CustomerChangeListener = function(){	 	   
	   if($scope.customerName != undefined && $scope.customerName != '')
	   $scope.getPostData("vendorController/getCompanyNamesAsDropDown.json",  { customerId : $scope.customerName,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : (($scope.companyName != undefined) ? $scope.companyName :0)} , "companyDropDown");	   
   };
   
  // Company Change Listener to get locations details
   $scope.fun_CompanyChangeListener = function(){
	   if($scope.companyName != undefined && $scope.companyName != '')
	   $scope.getPostData("CompanyController/getLocationsByCompanyId.json", { customerId : $scope.customerName , companyId : $scope.companyName } , "locationsDropDown");		   
   };
   
// locations Change Listener to get Departments details
   $scope.fun_locationChangeListener = function(){
	   if($scope.locationName != undefined && $scope.locationName != '')
	   $scope.getPostData("CompanyController/getDepartmentsByLocationId.json", { locationId : $scope.locationName } , "departmentDropDown");		   
   };
   
   // for Search button 
   $scope.fun_searchGridData = function(){   
  	 $scope.getPostData("CompanyController/getCompanyWorkOrderGridData.json",{customerId : $scope.customerName == undefined ? '0' : $scope.customerName, 
  			 																  companyId : $scope.companyName == undefined ? '0' : $scope.companyName  , 
  			 																  locationName : $scope.locationName == undefined ? '0' : $scope.locationName, 
		 																	  departmentName : $scope.departmentName == undefined ? '0' : $scope.departmentName,  
		 																	  workOrderCode : $scope.workOrderCode == undefined ? '' : $scope.workOrderCode ,
		 																	  workOrderName : $scope.workOrderName == undefined ? '' : $scope.workOrderName ,
		 																	  status : $scope.status == undefined ? '' : $scope.status ,
  			 																  } , "gridData" );    	   
  }
   
   $scope.fun_clearSsearchFields = function(){
	 
   }
   
}])/*.directive('onLastRepeat', function () {
    return function (scope, element, attrs) {
        if (scope.$last) { 
            setTimeout(function () {
                $('.table').DataTable();
            }, 1);
        }
    }
})*/;
 
