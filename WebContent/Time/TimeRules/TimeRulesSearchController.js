'use strict';

var timeController = angular.module("myApp.time", []);
var timeRuleSearch ;

timeController.service('myservice', function () {
    this.timeRuleCustomerId = 0;
    this.timeRuleCompanyId = 0;
    this.timeRuleCountryId = 0;
    this.timeRuleInfoId = 0;
    this.timeRuleCustomerName = null;
    this.timeRuleCompanyName = null;
    this.timeRuleCountryName = null;
    this.timeRuleCode = null;
    
});
timeController.controller('timeSearchController', ['$location','$scope','$http', '$resource','$routeParams','$filter','myservice','$cookieStore','$window', function ($location,$scope,$http, $resource, $routeParams,$filter,myservice,$cookieStore,$window) {
    
	$scope.timeRulesTableView = false;
	$scope.status = 'Y';
  
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
	      		//alert(angular.toJson(response.data));
	      		if(response.data != null && response.data != "" && response.data.timeRulesList != undefined){
	      			$scope.timeRulesTableView = true;
	      			timeRuleSearch.clear().rows.add(response.data.timeRulesList).draw();
	      		}
	      		// $scope.result = response.data.companyDetails;
	      	    // for button views
	      		if($scope.buttonArray == undefined || $scope.buttonArray == '')
	      		$scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Time Rule'}, 'buttonsAction');
	      	}else if(type == 'customerList'){
	
	      		$scope.customerList = response.data.customerList;
	      		if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
	      			$scope.customerName=response.data.customerList[0];		                
	      			$scope.customerChange();
	      		}
	      	  
		      	 if ( ! $.fn.DataTable.isDataTable( '#timeRulesTable' ) ) {        		   
		      		timeRuleSearch = $('#timeRulesTable').DataTable({        
			                     	"columns": [
			                                { "data": "customerName" },
			 		                        { "data": "companyName" },         
			                                { "data": "countryName" },
			                                { "data": "timeRuleCode",
			                                	"render": function ( data, type, full, meta ) {                 		                    		 
			                                			return '<a href="#/timeRules/'+full.timeRulesInfoId+'">'+data+'</a>';
			                                	}
			                                },
			                                { "data": "timeRulesDescription" },
			                                { "data": "isActive" }]
			               			});  
			     }
	      	}else if(type == 'customerChange'){  
	      		$scope.companyList = response.data;
	      		if(response.data != undefined && response.data.length == 1){
	      			$scope.companyName = response.data[0];
	      		}
	      	}else if(type == 'companyChange') {
	      		$scope.countryList = response.data;
	      		if(response.data != undefined && response.data.length == 1){
	      			$scope.countryName = response.data[0];
	      		}
	      	}   	
		})
	};
    
   $scope.getData('vendorController/getCustomerNamesAsDropDown.json', { customerId: $cookieStore.get('customerId')}, 'customerList')
   
    $scope.fun_searchGridData = function(){
    	 $scope.getData("timeRulesController/getTimeRulesListBySearch.json" ,{customerId : $cookieStore.get('customerId') != undefined && $cookieStore.get('customerId') != '' ? $cookieStore.get('customerId') : ($scope.customerName != undefined && $scope.customerName.customerId != undefined && $scope.customerName.customerId != '') ? $scope.customerName.customerId : 0 , isActive : $scope.status,companyId:($cookieStore.get('companyId') != "" && $cookieStore.get('companyId') != 0 && $cookieStore.get('companyId') != undefined) ? $cookieStore.get('companyId') : ($scope.companyName == undefined || $scope.companyName.id == "" ?  "" : $scope.companyName.id), timeRuleCode: ($scope.timeRuleCode != undefined && $scope.timeRuleCode != null && $scope.timeRuleCode != "") ? $scope.timeRuleCode : null   } , 'gridData' );    	   
    };
    
    $scope.fun_clearSearchFields  = function(){
		$scope.customerName = "";	
        $scope.companyName = "";	    
        $scope.timeRuleCode = "";	
        $scope.status = 'Y'
    };
    
    $scope.customerChange = function () {
    	$scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.customerName.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : 0}, 'customerChange');
    };
    
    
}]);