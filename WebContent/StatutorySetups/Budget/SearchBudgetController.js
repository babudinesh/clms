'use strict';

var budgetController = angular.module("myApp.Budget", []);
var budgetSearch ;

budgetController.controller("BudgetSearchCtrl", ['$scope','$rootScope', '$http', '$resource','$location','$filter', '$routeParams','myservice','$cookieStore', function ($scope,$rootScope, $http, $resource, $location, $filter, $routeParams, myservice, $cookieStore) {
	$.material.init();
	

	$scope.budgetTableView = false;
	//$scope.approvalStatus = 'Approved';
	$scope.readOnly = false;
	   
    $scope.list_years = $rootScope.getYears;  
    
    $scope.list_budgetStatus = [{"id":"Y","name":"Active"},
                                {"id":"N","name":"Inactive"}];
    
    $scope.list_approvalStatus = [{"id":"Initiated","name":"Initiated"},
                                  {"id":"Approved","name":"Approved"},
                                  {"id":"Pending For Approval","name":"Pending For Approval"},
                                  {"id":"Sent For Correction","name":"Sent For Correction"},
                                  {"id":"Rejected","name":"Initiated"} ];
   
   

   $scope.getData = function (url, data, type, buttonDisable) {
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
    		   //alert(angular.toJson(response.data))
         		if(response.data != null && response.data != "" && response.data.budgetList != undefined){
         			$scope.budgetTableView = true;
         			budgetSearch.clear().rows.add(response.data.budgetList).draw();
         		}
         		// $scope.result = response.data.companyDetails;
         	// for button views
			  	if($scope.buttonArray == undefined || $scope.buttonArray == '')
         		$scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Budget'}, 'buttonsAction');
         	}else if(type == 'customerList'){

         		$scope.customerList = response.data.customerList;
         		if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
         			$scope.customerName=response.data.customerList[0];		                
         			$scope.customerChange();
         		}
         	  
   	      	 if ( ! $.fn.DataTable.isDataTable( '#budgetTable' ) ) {        		   
   	      		budgetSearch = $('#budgetTable').DataTable({        
   		                     	"columns": [
   		                                { "data": "customerName" },
   		 		                        { "data": "companyName" },         
   		                                { "data": "budgetCode",
   		                                	"render": function ( data, type, full, meta ) {                 		                    		 
   		                                			return '<a href="#/addBudget/'+full.budgetDetailsId+'">'+data+'</a>';
   		                                	}
   		                                },
   		                                { "data": "budgetName" },
   		                                { "data": "locationName" },
   		                                { "data": "budgetYear" },
   		                                { "data": "approvalStatus" }]
   		               			});  
   		     }
         	}else if(type == 'customerChange'){  
         		$scope.companyList = response.data;
         		if(response.data != undefined && response.data.length == 1){
         			$scope.companyName = response.data[0];
         		}
         	}  	
       });
   };

   
    
   $scope.getData('vendorController/getCustomerNamesAsDropDown.json', { customerId: $cookieStore.get('customerId')}, 'customerList')
   	
   $scope.customerChange = function () {
	   if($scope.budget.customerId != undefined || $scope.budget.customerId != null){
		   $scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.budget.companyId  }, 'customerChange');
	   }
   };
   
   $scope.companyChange = function(){
	   if($scope.budget.companyId != undefined || $scope.budget.companyId != null){
	    	$scope.getData('vendorController/getcountryListByCompanyId.json', { companyId: $scope.budget.companyId }, 'companyChange');
   		}
   };
   
   $scope.getData('vendorController/getCustomerNamesAsDropDown.json', { customerId: $cookieStore.get('customerId')}, 'customerList')
   
   $scope.fun_searchGridData = function(){
   	 $scope.getData("statutorySetupsController/getBudgetListBySearch.json" ,{customerId : $cookieStore.get('customerId') != undefined && $cookieStore.get('customerId') != '' ? $cookieStore.get('customerId') : ($scope.customerName != undefined && $scope.customerName.customerId != undefined && $scope.customerName.customerId != '') ? $scope.customerName.customerId : 0 , approvalStatus : $scope.approvalStatus,companyId:($cookieStore.get('companyId') != "" && $cookieStore.get('companyId') != 0 && $cookieStore.get('companyId') != undefined) ? $cookieStore.get('companyId') : ($scope.companyName == undefined || $scope.companyName.id == "" ?  "" : $scope.companyName.id), budgetCode: ($scope.budgetCode != undefined && $scope.budgetCode != null && $scope.budgetCode != "") ? $scope.budgetCode : null , budgetName:$scope.budgetName, budgetYear:$scope.budgetYear  } , 'gridData' );    	   
   }
   
   $scope.fun_clearSearchFields  = function(){  	
	   $scope.customerName = "";	
       $scope.companyName = "";	    
       $scope.budgetCode = "";	
       $scope.budgetName = "";
       $scope.budgetYear="";
       $scope.approvalStatus="";
	
   }
   
   $scope.customerChange = function () {
	   $scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.customerName.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : 0}, 'customerChange');
   };

   
}]);