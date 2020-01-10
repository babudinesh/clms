'use strict';

var DivisionController = angular.module("myApp.Division", ['ngCookies']);

DivisionController.controller("DivisionSearchCtrl", ['$scope', '$http', '$resource','$location', '$routeParams','$filter', '$cookieStore', function ($scope, $http, $resource,$location,  $routeParams, $filter,  $cookieStore) {
		
	var divisionDataTable;
		
	$scope.companyName = "";
    $scope.customerName = "";
    $scope.countryId = "";
    $scope.divisionName= null;
    $scope.status="Y";

    $('#searchPanel').hide();
  
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
    			 if ( ! $.fn.DataTable.isDataTable( '#divisionTable' ) ) {        		   
    				 divisionDataTable = $('#divisionTable').DataTable({        
  	                     "columns": [	  	                       
  	                        { "data": "divisionCode",
  	                        	"render": function ( data, type, full, meta ) {                 		                    		 
  	               		      return '<a href="#/DivisionAdd/'+full.divisionDetailsId+'">'+data+'</a>';
  	               		    }
  	                     },
  	                     	{ "data": "customerName" },
                     		{ "data": "companyName" },
                     		{ "data": "countryName" },
  	                   		{ "data": "divisionName" },
  	                        { "data": "transactionDate", "render":function(data){
                       				return $filter('date')(data, "dd/MM/yyyy");
                       		 } 
  	                        },
  	                        { "data": "status" }]
  	               });  
  	      		}
    			// for button views
   			  	if($scope.buttonArray == undefined || $scope.buttonArray == '')
    			 $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Division'}, 'buttonsAction');  
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
	        	 divisionDataTable.clear().rows.add(response.data.divisionList).draw(); 
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
		$scope.getData('CompanyController/getDivisionsListBySearch.json', { customerId : ($scope.customerName == "" || $scope.customerName == undefined )? '0' : $scope.customerName  , companyId : ($scope.companyName == "" || $scope.companyName == undefined) ? '0' : $scope.companyName  , countryId: ( $scope.countryId == "" || $scope.countryId == undefined) ? '' : $scope.countryId, divisionName: ($scope.divisionName != '' || $scope.divisionName != null || $scope.divisionName != undefined || $scope.divisionName != null) ? $scope.divisionName : '', status:$scope.status,divisionId:$cookieStore.get('divisionId') > 0 ? $cookieStore.get('divisionId') :0}, 'search');
	};
	
	
	$scope.clear = function () {
		$scope.companyName="";
		$scope.customerName = "";
		$scope.countryId = "";
		$scope.divisionName= null;
		$scope.status='Y';
	};
	
		
}]);


