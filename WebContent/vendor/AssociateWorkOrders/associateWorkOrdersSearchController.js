'use strict';

var associateWorkOrder = angular.module("myApp.associateWorkOrder", []);


associateWorkOrder.controller("associateWorkOrdersearchCtrl",  ['$scope', '$rootScope', '$http', '$resource','$location','myservice','$cookieStore', function($scope,$rootScope,$http,$resource,$location,myservice,$cookieStore) {
    
	var associateWorkOrderDataTable;
	$scope.associateWorkOrderTableView = false;
	  
	/*$.material.init();
	$('#transactionDate,#fromDate,#toDate').bootstrapMaterialDatePicker({ 
    	time : false,
        Button : true,
        format : "DD/MM/YYYY",
        clearButton: true
    }); */
	
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
	      		$scope.associateWorkOrderTableView = true;
	      		associateWorkOrderDataTable.clear().rows.add(response.data).draw(); 
	      	// for button views
	      		if($scope.buttonArray == undefined || $scope.buttonArray == '')
	      		$scope.getPostData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Associate Work Order'}, 'buttonsAction');
	      	}/*else if(type== 'vendorDropDown'){
	      		$scope.vendorList = response.data.vendorList;
	      	}*/else if(type == 'companyDropDown'){
	      		$scope.companyList = response.data;
	      	  if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
 	                $scope.companyName = response.data[0].id;
 	                // $scope.fun_CompanyChangeListener();
 	                }
	      	}else if( type == 'companyGrid' ){	

	      		 if ( ! $.fn.DataTable.isDataTable( '#associateWorkOrderTable' ) ) {        		   
	      			associateWorkOrderDataTable = $('#associateWorkOrderTable').DataTable({        
	      		                     "columns": [	      		                       
	      		                        { "data": "vendorCode" },
	      		                        { "data": "vendorName"},
	      		                        { "data": "isActive" },
	      		                        { "data": null,
	      		                        	"render": function ( data, type, full, meta ) {                 		                    		 
	      		               		                      return '<a href="#/associateWorkOrder/'+full.vendorDetailsId+'"> Add Work Order </a>';
	      		               		                  }
	      		                        }]
	      		               });  
	      		      		}
	      		
		      		$scope.customerList = response.data.customerList;
		      		 if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
	   		                $scope.customerName=response.data.customerList[0].customerId;		                
	   		                $scope.fun_CustomerChangeListener();
	   		                }
		      	
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
   /*$scope.fun_CompanyChangeListener = function(){
	   if($scope.companyName != undefined && $scope.companyName != '')
		   $scope.getPostData("vendorController/getVendorAndLocationNamesAsDropDowns.json", { customerId : $scope.customerName , companyId : $scope.companyName } , "vendorDropDown");
   };*/
   

   
   // for Search button 
   $scope.fun_searchGridData = function(){ 
	   if($('#associateSearchForm').valid()){
		   	$scope.getPostData("associateWorkOrderController/getSearchGridData.json",{customerDetailsId : $scope.customerName == undefined ? '0' : $scope.customerName, 
  			 																  companyDetailsId : $scope.companyName == undefined ? '0' : $scope.companyName  , 
  			 																  vendorCode : $scope.vendorCode == undefined || $scope.vendorCode == '' ? null : $scope.vendorCode,
  			 																vendorName : $scope.vendorName == undefined || $scope.vendorName == '' ? null : $scope.vendorName 	
  			 																  } , "gridData" );    	   
	   	}
	   }
   
   $scope.fun_clearSsearchFields = function(){
	 $scope.vendorCode = null;
	 $scope.vendorName = null;
   }
   
   
 
   
   
   
   
   
   
   
   
}]);
 
