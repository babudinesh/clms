'use strict';

var companyControllers = angular.module("myApp.companyModule", []);
var dt_companySearch ;


companyControllers.controller("companyDetailsSearchCtrl",  ['$scope', '$rootScope', '$http', '$resource','$location','$cookieStore', function($scope,$rootScope,$http,$resource,$location,$cookieStore) {
    
  $scope.companyDetailsTableView = false;
  $scope.status = 'Y';
  $scope.customerName =  new Object();
  $scope.readOnly = false;
  
  
  var  URL_GET_COMPANY_DETAILS_FOR_GRID_VIEW = "CompanyController/getCompanyDetailsForGridView.json";
  
  
  $scope.render = $cookieStore.get('companyId')== null || $cookieStore.get('companyId') == ''  ? false : true ;

  $scope.getData = function (url, data, type) {
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
      		if(response.data != null && response.data != "" && response.data.companyDetails != undefined){
      			$scope.companyDetailsTableView = true;
      			dt_companySearch.clear().rows.add(response.data.companyDetails).draw();
      		}
      		// $scope.result = response.data.companyDetails;
      	}else if(type == 'customerList'){

      		$scope.customerList = response.data.customerList;
      		if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
      			$scope.customerName=response.data.customerList[0];		                
      			$scope.customerChange();
      		}
      	  
	      	 if ( ! $.fn.DataTable.isDataTable( '#companyDetailsTable' ) ) {        		   
		      		dt_companySearch = $('#companyDetailsTable').DataTable({        
		                     	"columns": [
		                                { "data": "customerCode" },
		 		                        { "data": "customerName" },         
		                                { "data": "companyCode" },
		                                { "data": "companyName",
		                                	"render": function ( data, type, full, meta ) {                 		                    		 
		                                			return '<a href="#/companyDetails/'+full.companyId+'">'+data+'</a>';
		                                	}
		                                },
		                                { "data": "countryName" },
		                                { "data": "isActive" }]
		               			});  
		     }
	      	 
	      // for button views
	      	if($scope.buttonArray == undefined || $scope.buttonArray == '')
	      	$scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Company'}, 'buttonsAction');
      	}else if(type == 'customerChange'){  
      		$scope.companyList = response.data;
      		if(response.data != undefined && response.data.length == 1){
      			$scope.companyName = response.data[0];
      			$scope.companyChange();
      		}
      	}else if(type == 'companyChange') {
      		$scope.countryList = response.data;
      		if(response.data != undefined && response.data.length == 1){
      			$scope.countryName = response.data[0];
      		}
      	}   	
      })
  };
    
   $scope.getData('CompanyController/getMasterInfoForCompanyGrid.json', { customerId: $cookieStore.get('customerId')}, 'customerList')
   
    $scope.fun_searchGridData = function(){
    	 $scope.getData( URL_GET_COMPANY_DETAILS_FOR_GRID_VIEW ,{customerId : $cookieStore.get('customerId') != undefined && $cookieStore.get('customerId') != '' ? $cookieStore.get('customerId') : ($scope.customerName.customerId != undefined && $scope.customerName.customerId != '') ? $scope.customerName.customerId : 0 , companyName : $scope.companyName == undefined || $scope.companyName.name == "" ?  "" : $scope.companyName.name  , countryName : $scope.countryName == undefined ||  $scope.countryName == null ? "" : $scope.countryName.name, status : $scope.status,companyId:($cookieStore.get('companyId') != "" && $cookieStore.get('companyId') != 0 && $cookieStore.get('companyId') != undefined) ? $cookieStore.get('companyId') : 0  } , 'gridData' );    	   
    }
    
    $scope.fun_clearSearchFields  = function(){
    	if($scope.render){
    		$scope.status = 'Y';
    	}else{
    		$scope.customerName = "";	
	        $scope.companyName = "";	    
	        $scope.countryName = "";	
    		
    	}
    }
    
    $scope.customerChange = function () {
    	$scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.customerName.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : 0}, 'customerChange');
    };
    
    $scope.companyChange = function () {
    	$scope.getData('vendorController/getcountryListByCompanyId.json', { companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : ($scope.companyName != null && $scope.companyName != "" && $scope.companyName.id != "" ? $scope.companyName.id : 0)}, 'companyChange');
    }
    
}]);
 
