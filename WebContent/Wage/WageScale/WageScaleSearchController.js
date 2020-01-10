'use strict';
var WageScaleController = angular.module("myApp.wageScale", ['ngCookies']);

WageScaleController.controller("WageScaleSearchCtrl", ['$scope', '$http', '$resource', '$routeParams', '$location', '$filter','$cookieStore', function ($scope, $http, $resource, $routeParams, $location, $filter, $cookieStore) {
	    $scope.wageRateTableView  = false;
	    var wageScaleDataTable;
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
	        	} else if (type == 'customerList'){
	                $scope.customerList = response.data.customerList;	              
	                if( response.data != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
			                $scope.customerName=response.data.customerList[0];		                
			                $scope.customerChange();
		                }
	                
	                if ( ! $.fn.DataTable.isDataTable( '#wageScaleTable' ) ) {        		   
	                	wageScaleDataTable = $('#wageScaleTable').DataTable({        
	  	                     "columns": [	  	                         
	  	                       		  	                   
	                     		{ "data": "customerName" },
	  	                   		{ "data": "companyName" },
		  	                   	{ "data": "transactionDate",
	  	                   					"render":function(data){ 
	  	                   						return $filter('date')(data, "dd/MM/yyyy");
	  	                   					} 
	  	                   		},
	  	                        { "data": "isActive" },
	  	                        { "data": "locationName" },
	  	                        { "data": "wageScaleCode",
	  	                        	"render": function ( data, type, full, meta ) {                 		                    		 
		  	               		      return '<a href="#/WageScale/'+full.wageScaleId+'">'+data+'</a>';
		  	               		    }
		  	                     },
	  	                        { "data": "wageScaleName" }
	  	                        
	  	                        
	  	                        ]
	  	               });  
	  	      		}
	                // for button views
	                if($scope.buttonArray == undefined || $scope.buttonArray == '')
	                $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Wage Scale'}, 'buttonsAction');
	            }else if (type == 'customerChange'){
	                $scope.companyList = response.data;
	                if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	   	                $scope.companyName = response.data[0].id;
	   	                $scope.companyChange();
	   	                }
	                
	            } else if(type == 'companyChange'){
	            	
	            	$scope.locationList = response.data.locationList;
	            }else if(type=='search'){
	            //	alert(angular.toJson(response.data));
	            	$scope.wageRateTableView  = true;
	            	wageScaleDataTable.clear().rows.add(response.data).draw(); 
	               // $scope.result = response.data.wageRateList;
	            }
	        },
	        function(){
	        	$scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable); 
	        });
	    }


	    $scope.getData('vendorController/getCustomerNamesAsDropDown.json', { customerId: $cookieStore.get('customerId')}, 'customerList')

	    $scope.customerChange = function () {
	    	//alert($scope.customerName);
	        $scope.getData('vendorController/getCompanyNamesAsDropDown.json',  {customerId: $scope.customerName.customerId == undefined ?'': $scope.customerName.customerId ,companyId : ($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.companyName != undefined ?  $scope.companyName : ''}, 'customerChange');
	    };
	    
	    $scope.companyChange = function(){
	    
	    	$scope.getData('wageScaleController/getlocationNamesAsDropDowns.json',  {customerId: $scope.customerName.customerId == undefined ?'': $scope.customerName.customerId ,companyId : ($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.companyName != undefined ?  $scope.companyName :''}, 'companyChange');
	    }
	    

	    $scope.search = function () {
	    	
	        $scope.getData('wageScaleController/wageScaleGridDetails.json', { customerId: ($scope.customerName == undefined || $scope.customerName.customerId == undefined )? '': $scope.customerName.customerId, companyId: ($scope.companyName == undefined)? '':$scope.companyName, wageScaleCode:($scope.wageScaleCode == undefined )? '':$scope.wageScaleCode, wageScaleName: ($scope.wageScaleName == undefined )? '': $scope.wageScaleName, status:$scope.status,locationName: $scope.locationName != undefined ? $scope.locationName : '' }, 'search');
	    };
	    
	    
	    $scope.clear = function () {
	    	 $scope.customerName="";
	    	 $scope.companyName="";
	    	 $scope.wageScaleName= null;
	    	 $scope.wageScaleCode='';
	    	 $scope.isActive="Y";
	    	 $scope.locationName = '';
	    };
}]
);





