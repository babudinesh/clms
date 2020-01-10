'use strict';
var WageController = angular.module("myApp.WageRate", ['ngCookies']);

WageController.controller("WageSearchCtrl", ['$scope', '$http', '$resource', '$routeParams', '$location', '$filter','$cookieStore', function ($scope, $http, $resource, $routeParams, $location, $filter, $cookieStore) {
	  
	  $scope.wageRateTableView  = false;
	  var wageRateDataTable;
	  $scope.status = "Y";
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
    }
	       
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
	        	} else if (type == 'companyGrid'){
	            	$scope.customerList = response.data.customerList;
		      		 if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
	   		                $scope.customerName=response.data.customerList[0].customerId;		                
	   		                $scope.fun_CustomerChangeListener();
		                }
	                
	                if ( ! $.fn.DataTable.isDataTable( '#wageRateTable' ) ) {        		   
	                	wageRateDataTable = $('#wageRateTable').DataTable({        
	  	                     "columns": [	  	                         
	  	                        { "data": "wageRateCode",
	  	                        	"render": function ( data, type, full, meta ) {                 		                    		 
		  	               		      return '<a href="#/WageRateAdd/'+full.wageRateDetailsId+'">'+data+'</a>';
		  	               		    }
		  	                     },  	
		  	                    { "data": "wageRateName" },
	                     		{ "data": "customerName" },
	  	                   		{ "data": "companyName" },
	  	                     	{ "data": "vendorName" },
	  	                        { "data": "workOrderName" },
	  	                        { "data": "wageScaleName" },	  	                       
	  	                        { "data": "transDate" ,
	  	                        	"render":function(data){ 
                 						return $filter('date')(data, "dd/MM/yyyy");
                 					} },
	  	                        { "data": "status" }]
	  	               });  
	  	      		}
	                // for button views
	                if($scope.buttonArray == undefined || $scope.buttonArray == '')
	                $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Wage Rate'}, 'buttonsAction');
	            }else if(type == 'companyDropDown'){
		      		$scope.companyList = response.data;
		      	  if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	 	                $scope.companyName = response.data[0].id;
	 	               $scope.fun_CompanyChangeListener();
	 	                }
		      	}else if(type== 'vendorDropDown'){
		      		$scope.vendorList = response.data.vendorList;
		      		$scope.wageScaleList = response.data.wageScaleList;
		      	}else if(type== 'workOrderDropDown'){
		      		$scope.workOrderList = response.data;
		      	}else if(type=='search'){
	            	$scope.wageRateTableView  = true;
	            	wageRateDataTable.clear().rows.add(response.data.wageRateList).draw(); 
	               // $scope.result = response.data.wageRateList;
	            }
	        },
	        function(){
	        	$scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable); 
	        });
	    }

	    // for getting Master data for Grid List 
	    $scope.getData("vendorController/getCustomerNamesAsDropDown.json",{ customerId: $cookieStore.get('customerId')}, "companyGrid" );
	 	  	     
	    // Customer Change Listener to get company details
	    $scope.fun_CustomerChangeListener = function(){	 	   
	 	   if($scope.customerName != undefined && $scope.customerName != '')
	 	   $scope.getData("vendorController/getCompanyNamesAsDropDown.json",  { customerId : $scope.customerName,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : (($scope.companyName != undefined) ? $scope.companyName :0)} , "companyDropDown");	   
	    };
	    
	   // Company Change Listener to get locations details
	    $scope.fun_CompanyChangeListener = function(){
	 	   if($scope.companyName != undefined && $scope.companyName != '')
	 		   $scope.getData("WageRateController/getVendorNamesAsDropDown.json", { customerId : $scope.customerName , companyId : $scope.companyName } , "vendorDropDown");
	    };
	    
	    $scope.fun_vendorChangeListener = function(){
		 	   if($scope.vendorName != undefined && $scope.vendorName != '')
		 		   $scope.getData("WageRateController/getWorkOrderAsDropDown.json", { customerId : $scope.customerName , companyId : $scope.companyName, vendorId : $scope.vendorName } , "workOrderDropDown");
		    };
	    

	    $scope.search = function () {	
	        $scope.getData('WageRateController/getWageRatesListBySearch.json', { customerId: ($scope.customerName == undefined || $scope.customerName == undefined )? '0': $scope.customerName,
	        																	 companyId: ($scope.companyName == undefined || $scope.companyName == undefined )? '0':$scope.companyName,
	        																	 vendorId: ($scope.vendorName == undefined || $scope.vendorName == '' )? '0':$scope.vendorName,
	        																	 workOrderId:($scope.workOrderName == undefined || $scope.workOrderName == '' )? '0':$scope.workOrderName,
	        																	 wageScaleId:($scope.wageScaleId == undefined || $scope.wageScaleId == '' )? '':$scope.wageScaleId, 
	        																	 wageRateName: ($scope.wageRateName == undefined || $scope.wageRateName == '' )? '': $scope.wageRateName, 
    																			 status:$scope.status},
	        																	'search');
	    };
	    $scope.clear = function () {
	    	 $scope.customerName="";
	    	 $scope.companyName="";
	    	// $scope.wageRateCode= null;
	    	 $scope.wageRateName=null;
	    	 $scope.workOrderName = "";
	    	 $scope.vendorName ="";
	    	 $scope.wageScaleId = "";
	    	 $scope.status="Y";
	    };
	    
}]);





