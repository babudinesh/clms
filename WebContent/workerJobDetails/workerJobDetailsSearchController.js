'use strict';
var workerJobDetailsController = angular.module("myapp.workerJobDetails", []);
workerJobDetailsController/*.directive('onLastRepeat', function () {
    return function (scope, element, attrs) {
        if (scope.$last) {
            setTimeout(function () {
                $('.table').DataTable();
            }, 1);
        }
    };
})*/.controller("workerJobDetailsController", ['$scope', '$resource', '$http', '$location','$cookieStore', function ($scope, $resource, $http, $location,$cookieStore) {  
   
	var workJobDetailsDataTable;
	$scope.list_status = [{"id":"Y", "name":"Active"},{"id":"N", "name":"InActive"}];
	//$scope.status = "Y";
	$scope.workJobDetailsTableView = false;	
	
	if($cookieStore.get("roleNamesArray").includes('Vendor Admin')){
		$scope.dropdownDisable = true;
	}else{
		$scope.dropdownDisable = false;
	}
	
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
	      	if( type == 'gridData' ){	 
	      		//alert(angular.toJson(response.data));
	      		// $scope.result = response.data;
	      		$scope.workJobDetailsTableView = true;
	      		workJobDetailsDataTable.clear().rows.add(response.data).draw(); 
	      	}else if(type == 'companyDropDown'){
	      		$scope.companyList = response.data;
	      		 if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	   	                $scope.companyName = response.data[0].id;
						$scope.fun_CompanyChangeListener();
	   	                }
	      		 
	      		
	      		 
	      	}else if(type == 'vendorDropDown'){
	      		$scope.vendorList = response.data.vendorList;	      		
	      		if( response.data != undefined && response.data.vendorList != "" && response.data.vendorList.length == 1 ){
	                $scope.vendorName = response.data.vendorList[0].id;	              
	                }
	      		
	      	}else if( type == 'companyGrid' ){
	      		$scope.customerList = response.data.customerList;	
	      		 if ( ! $.fn.DataTable.isDataTable( '#workJobDetailsTable' ) ) {        		   
		      			workJobDetailsDataTable = $('#workJobDetailsTable').DataTable({        
	  	                     "columns": [	  	                       
	  	                        { "data": "workerName",
	  	                        	"render": function ( data, type, full, meta ) {                 		                    		 
	  	               		      return '<a href="#/workerJobDetails/'+full.workJobDetailsId+'">'+data+'</a>';
	  	               		    }
	  	                     },
	  	                     	{ "data": "customerName" },
	                     		{ "data": "companyName" },
	                     		{ "data": "vendorName" },  	                   	
	  	                        { "data": "strTransactionDate" }
	  	                        ]
	  	               });  
	  	      		}
	      	  if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
		                $scope.customerName=response.data.customerList[0].customerId;		                
		                $scope.fun_CustomerChangeListener();
		                }
					
	      	}          	
	      })
	  };
	  
	  
   // for getting Master data for Grid List 
   $scope.getPostData("vendorController/getCustomerNamesAsDropDown.json", { customerId: $cookieStore.get('customerId')}, "companyGrid" );
	  	     
   // Customer Change Listener to get company details
   $scope.fun_CustomerChangeListener = function(){	 	   
	   $scope.getPostData("vendorController/getCompanyNamesAsDropDown.json",  { customerId : $scope.customerName,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.companyName != undefined ? $scope.companyName : 0} , "companyDropDown");	   
   };
   
// Company Change Listener to get vendor details
   $scope.fun_CompanyChangeListener = function(){
	   $scope.getPostData("workerJobDetailsController/getVendorNamesAsDropDown.json", { customerId : $scope.customerName , companyId : $scope.companyName,vendorId:($cookieStore.get('vendorId') != null && $cookieStore.get('vendorId') != "") ? $cookieStore.get('vendorId') : $scope.vendorName != undefined ? $scope.vendorName : 0 } , "vendorDropDown");		   
   };
   
   // for Search button 
   $scope.fun_searchGridData = function(){   
  	 $scope.getPostData("workerJobDetailsController/getSearchGridData.json",{customerId : $scope.customerName == undefined ? '0' : $scope.customerName,
  			 															companyId : $scope.companyName == undefined ? '0' : $scope.companyName  , 
	 																	vendorId : $scope.vendorName == undefined ? '0' : $scope.vendorName, 
																		workerName : ($scope.workerName == undefined || $scope.workerName == null) ? '' : $scope.workerName ,
																		workerCode : ($scope.workerCode == undefined || $scope.workerCode == null) ? '' : $scope.workerCode ,
																		status : "" } , "gridData" );    	   
  }
   
   $scope.fun_clearSsearchFields = function(){
	   
   }
   
}]);