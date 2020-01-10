'use strict';
var VendorSearchcontroller = angular.module("myapp.VendorBranchesSearch", []);
VendorSearchcontroller/*.directive('onLastRepeat', function () {
    return function (scope, element, attrs) {
        if (scope.$last) {
            setTimeout(function () {
                $('.table').DataTable();
            }, 1);
        }
    };
})*/.controller("vendorBranchesViewDtls", ['$scope', '$resource', '$http', '$location','$cookieStore', function ($scope, $resource, $http, $location,$cookieStore) {  
   
	var vendorBranchDataTable;
	  $scope.vendorBranchTableView = false;
	  if($cookieStore.get("roleNamesArray").includes('Vendor Admin')){
		  $scope.vendorAdmin = true;
	  }else{
		  $scope.vendorAdmin = false;
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
	    	  if(type == 'buttonsAction'){
	    			$scope.buttonArray = response.data.screenButtonNamesArray;
	    		} else if( type == 'gridData' ){	
	      		$scope.vendorBranchTableView = true;
	      		vendorBranchDataTable.clear().rows.add(response.data).draw(); 
	      		//$scope.result = response.data;
	      	}else if(type == 'companyDropDown'){
	      		$scope.companyList = response.data;
	      		 if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	   	                $scope.companyName = response.data[0].id;
						$scope.fun_CompanyChangeListener();
	   	                }
	      		 
	      	}else if(type == 'vendorDropDown'){
	      		$scope.vendorList = response.data.vendorList;
	      	//	console.log(angular.toJson(response.data.vendorList));
	      		 if( response.data != undefined && response.data.vendorList != "" && response.data.vendorList.length == 1 ){
		      		$scope.vendorCode = response.data.vendorList[0].desc.trim();
		      		var str = response.data.vendorList[0].name
		      		$scope.vendorName =  str.substr(0,str.indexOf("(")).trim();
		      		//console.log(vendorCode+":::"+vendorName);
	      		 }
	      		
	      		//
	      	}  else if( type == 'companyGrid' ){
	      		$scope.customerList = response.data.customerList;
	      		$scope.industryList = response.data.industryList;
	      		 if ( ! $.fn.DataTable.isDataTable( '#vendorBranchTable' ) ) {        		   
	      			vendorBranchDataTable = $('#vendorBranchTable').DataTable({        
  	                     "columns": [	  	                       
  	                        /*{ "data": "vendorCode",
  	                        	"render": function ( data, type, full, meta ) {                 		                    		 
  	               		      return '<a href="#/vendorBranchesAddOrView/'+full.vendorBranchDetailsInfoId+'">'+data+'</a>';
  	               		    }
  	                     },*/
  	                     	{ "data": "customerName" },
	                     	{ "data": "companyName" },
	                     	{ "data": "vendorName" },
  	                   		{ "data": "branchCode" ,
	                     			"render": function ( data, type, full, meta ) {                 		                    		 
	                     				return '<a href="#/vendorBranchesAddOrView/'+full.vendorBranchDetailsInfoId+'">'+data+'</a>';
	                     			}
    	               		},
  	                        { "data": "locationName" },
  	                     /* { "data": "stateName" },*/
  	                        { "data": "isActive" }]
  	               });  
  	      		}
	      	  if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
		                $scope.customerName=response.data.customerList[0].customerId;		                
		                $scope.fun_CustomerChangeListener();
		                }
	      	// for button views
	      	if($scope.buttonArray == undefined || $scope.buttonArray == '')
	      	$scope.getPostData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Vendor Branch'}, 'buttonsAction');
	      	}          	
	      })
	  };
	  
	  
   // for getting Master data for Grid List 
   $scope.getPostData("vendorController/getMasterInfoForvendorBranchGrid.json",{customerId: $cookieStore.get('customerId')} ,"companyGrid" );
	  	     
   // Customer Change Listener to get company details
   $scope.fun_CustomerChangeListener = function(){	 	   
	   $scope.getPostData("vendorController/getCompanyNamesAsDropDown.json",  { customerId : $scope.customerName, companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.companyName != undefined ? $scope.companyName : 0} , "companyDropDown");	   
   };
   
// Company Change Listener to get vendor details
   $scope.fun_CompanyChangeListener = function(){
	   $scope.getPostData("vendorController/getVendorAndLocationNamesAsDropDowns.json", { customerId : $scope.customerName , companyId : $scope.companyName ,vendorId:($cookieStore.get('vendorId') != null && $cookieStore.get('vendorId') != "") ? $cookieStore.get('vendorId') : $scope.vendorName != undefined ? $scope.vendorName : 0} , "vendorDropDown");		   
   };
   
   // for Search button 
   $scope.fun_searchGridData = function(){   
  	 $scope.getPostData("vendorController/getvendorBranchGridData.json",{customerId : $scope.customerName == undefined ? '0' : $scope.customerName, companyId : $scope.companyName == undefined ? '0' : $scope.companyName  , vendorCode : $scope.vendorCode == undefined ? '' : $scope.vendorCode ,vendorName : $scope.vendorName == undefined ? '' : $scope.vendorName, branchCode : $scope.branchId == undefined ? '' : $scope.branchId,vendorId:($cookieStore.get('vendorId') != null && $cookieStore.get('vendorId') != "") ? $cookieStore.get('vendorId') :""  } , "gridData" );    	   
  }
   
   $scope.fun_clearSsearchFields = function(){
	 //  $scope.industryId = [];
   }
   
}]);