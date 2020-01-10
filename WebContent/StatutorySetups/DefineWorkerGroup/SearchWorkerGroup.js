'use strict';

var workerGroupController = angular.module("myApp.WorkerGroup", []);
var workerGroupSearch ;

workerGroupController.controller("WorkerGroupSearchCtrl", ['$scope', '$http', '$resource','$location','$filter', '$routeParams','myservice','$cookieStore', function ($scope, $http, $resource, $location, $filter, $routeParams, myservice, $cookieStore) {
	$.material.init();
	
	$scope.workerGroupTableView = false;
	$scope.readOnly = false;
	$scope.status = 'Y';
	
    $scope.list_Status = [{"id":"Y","name":"Active"},
                                {"id":"N","name":"Inactive"}];
    
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
         		if(response.data != null && response.data != "" && response.data.workerGroupList != undefined){
         			$scope.workerGroupTableView = true;
         			workerGroupSearch.clear().rows.add(response.data.workerGroupList).draw();
         		}
         		// $scope.result = response.data.companyDetails;
         	// for button views
			  	if($scope.buttonArray == undefined || $scope.buttonArray == '')
         		$scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Worker Group'}, 'buttonsAction');
         	}else if(type == 'customerList'){

         		$scope.customerList = response.data.customerList;
         		if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
         			$scope.customerName=response.data.customerList[0];		                
         			$scope.customerChange();
         		}
         	  
   	      	 if ( ! $.fn.DataTable.isDataTable( '#workerGroupTable' ) ) {        		   
   	      		 workerGroupSearch = $('#workerGroupTable').DataTable({        
   		                     	"columns": [
   		                                { "data": "customerName" },
   		 		                        { "data": "companyName" },   
   		 		                        { "data": "countryName" }, 
   		                                { "data": "groupCode",
   		                                	"render": function ( data, type, full, meta ) {                 		                    		 
   		                                			return '<a href="#/addWorkerGroup/'+full.workerGroupId+'">'+data+'</a>';
   		                                	}
   		                                },
   		                                { "data": "groupName" },
   		                                { "data": "status" }]
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
   	
   $scope.fun_searchGridData = function(){
   	 $scope.getData("statutorySetupsController/getWorkGroupBySearch.json" ,{customerId : $cookieStore.get('customerId') != undefined && $cookieStore.get('customerId') != '' ? $cookieStore.get('customerId') : ($scope.customerName != undefined && $scope.customerName.customerId != undefined && $scope.customerName.customerId != '') ? $scope.customerName.customerId : 0 , status : $scope.status,companyId:($cookieStore.get('companyId') != "" && $cookieStore.get('companyId') != 0 && $cookieStore.get('companyId') != undefined) ? $cookieStore.get('companyId') : ($scope.companyName == undefined || $scope.companyName.id == "" ?  "" : $scope.companyName.id), groupCode: ($scope.groupCode != undefined && $scope.groupCode != null && $scope.groupCode != "") ? $scope.groupCode : null , groupName:$scope.groupName  } , 'gridData' );    	   
   }
   
   $scope.fun_clearSearchFields  = function(){  	
	   $scope.customerName = "";	
       $scope.companyName = "";	    
       $scope.groupCode = null;	
       $scope.groupName = "";
       $scope.status='Y';
   }
   
   $scope.customerChange = function () {
	   if($scope.customerName != undefined && $scope.customerName != ""){
		   $scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.customerName.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : 0}, 'customerChange');
	   }
   };

   
}]);