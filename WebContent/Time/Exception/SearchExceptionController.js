'use strict';

var ExceptionController = angular.module("myApp.Exception", ['ngCookies']);

ExceptionController.controller("ExceptionSearchCtrl", ['$scope', '$http', '$resource','$location', '$routeParams','$filter', '$cookieStore', function ($scope, $http, $resource,$location,  $routeParams, $filter,  $cookieStore) {
		
	var exceptionDataTable;
		
	$scope.companyName = "";
    $scope.customerName = "";
    $scope.exceptionCode = null;
    $scope.exceptionName= null;

    $('#searchPanel').hide();
    $scope.status = 'Y';
    
    $scope.enable = false;
   
    $scope.Messager = function (type, heading, text, btn) {
    	$.toast({
    		heading: heading,
    		icon: type,
    		text: text,
    		stack: false, beforeShow: function () {
    			$scope.enable = true;
    		},
    		afterHidden: function () {
    			$scope.enable = false;
    		}
    	});
    };
    
    $scope.getData = function (url, data, type,buttonDisable) {
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
    			 if ( ! $.fn.DataTable.isDataTable( '#exceptionTable' ) ) {        		   
    				 exceptionDataTable = $('#exceptionTable').DataTable({        
  	                     "columns": [	  	                       
  	                     	{ "data": "customerName" },
                     		{ "data": "companyName" },
                     		{ "data": "countryName" },
                     		{ "data": "exceptionCode",
  	                        	"render": function ( data, type, full, meta ) {                 		                    		 
  	                        		return '<a href="#/addException/'+full.exceptionId+'">'+data+'</a>';
  	                        	}
                     		},
  	                   		{ "data": "exceptionName" },
  	                   		{ "data": "effectiveStatus" }
  	                        ]
  	               });  
  	      		}
    			// for button views
    			 if($scope.buttonArray == undefined || $scope.buttonArray == '')
    			 $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Exception'}, 'buttonsAction');
	        }else if (type == 'customerChange'){		        	
	            $scope.companyList = response.data;	 
                if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
                	$scope.companyName = response.data[0].id;
   	                $scope.companyChange();
   	            }else{
   	            	$scope.countryList = null;
   	            }
                
	        }else if(type == 'companyChange'){
	        	$scope.countryList = response.data;
	        	if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
                	$scope.countryId = response.data[0].id;
   	            }
	        	
	        }else if(type=='search'){
	        	//alert(JSON.stringify(response.data));
	        	 $('#searchPanel').show();
	        	 exceptionDataTable.clear().rows.add(response.data.exceptionList).draw(); 
	        }
	    },function () { 
	    	$scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
	    });
    }

    $scope.getData('vendorController/getCustomerNamesAsDropDown.json', {customerId: $cookieStore.get('customerId')}, 'customerList')
	
	$scope.customerChange = function () {
    	$scope.getData('vendorController/getCompanyNamesAsDropDown.json',{customerId: ($scope.customerName == undefined || $scope.customerName == null )? '0' : $scope.customerName,companyId:$cookieStore.get('companyId') != undefined || $cookieStore.get('companyId') !='' ? $cookieStore.get('companyId') :  ($scope.companyName == "" || $scope.companyName == undefined) ? '0' : $scope.companyName} , 'customerChange');
    };
    
	$scope.companyChange = function () {
		 if($scope.companyName != undefined || $scope.companyName!= null){
		    	$scope.getData('vendorController/getcountryListByCompanyId.json', { companyId: $scope.companyName }, 'companyChange');
       		}
	};

	$scope.search = function () {
		$scope.getData('timeRulesController/getExceptionsListBySearch.json', { customerId : ($scope.customerName == "" || $scope.customerName == undefined )? '0' : $scope.customerName  , companyId : ($scope.companyName == "" || $scope.companyName == undefined) ? '0' : $scope.companyName  , countryId: ( $scope.countryId == "" || $scope.countryId == undefined) ? '' : $scope.countryId, exceptionName: ($scope.exceptionName != '' || $scope.exceptionName != null || $scope.exceptionName != undefined || $scope.exceptionName != null) ? $scope.exceptionName : '', exceptionCode:($scope.exceptionCode != '' || $scope.exceptionCode != null || $scope.exceptionCode != undefined || $scope.exceptionCode != null) ? $scope.exceptionCode : '', effectiveStatus:$scope.status}, 'search');
	};
	
	
	$scope.clear = function () {
		$scope.companyName="";
		$scope.customerName = "";
		$scope.exceptionCode = null;
		$scope.exceptionName= null;
		$scope.status = 'Y';
	};
	
		
}]);


