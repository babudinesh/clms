'use strict';
var PTaxController = angular.module("myApp.ProfessionalTax", []);

PTaxController.controller("PTaxSearchCtrl", ['$scope', '$http', '$resource', '$routeParams', '$location', '$filter','$cookieStore', function ($scope, $http, $resource, $routeParams, $location, $filter, $cookieStore) {
     	$('#searchPanel').hide();
     	var pTaxTables;
	   
	    $scope.enable = false;
	    $scope.customerName = new Object();
	    $scope.companyName = new Object();
	    $scope.countryName = new Object();
	    $scope.locationName = "";
	    
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
	               
	                if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){
		                $scope.customerName.customerId=response.data.customerList[0].customerId;		                
		                $scope.customerChange();
		            }
	                
	                if ( ! $.fn.DataTable.isDataTable( '#pTaxTable' ) ) {        		   
	                	pTaxTables = $('#pTaxTable').DataTable({        
	 	                     "columns": [
	 	                        { "data": "customerName"},
	 	                        { "data": "companyName" },
	 	                        { "data": "countryName" },
	 	                        { "data": "locationName",
	 	                        	"render": function ( data, type, full, meta ) {       
			 	               		  			return '<a href="#/defineProfessionalTax/'+full.professionalTaxId+'">'+data+'</a>';
	 	                        			}
	 	                        }]
	 	               });  
	 	      		}
	                
	             // for button views
	                if($scope.buttonArray == undefined || $scope.buttonArray == '')
	                $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Professional Tax'}, 'buttonsAction');  
	                
	            }else if (type == 'customerChange'){
	                $scope.companyList = response.data;	                
	                if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	                	$scope.companyName = response.data[0];
	                	$scope.companyChange();
	                }
                
	            }else if (type == 'companyChange'){
	                $scope.countryList = response.data;	                
	                if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	                	$scope.countryName = response.data[0];
	                	$scope.countryChange();
	                }
                
	            }else if (type == 'countryChange'){
	                $scope.locationList = response.data;	                
	                if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	                	$scope.locationName = response.data[0];
	                }
                
	            }else if(type=='search'){
	            	$('#searchPanel').show();
	            	pTaxTables.clear().rows.add(response.data.taxList).draw(); 
	                // $scope.result = response.data.departmentList;
	            }
	        },
	        function(){
	        	$scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable); 
	        });
	    }


	    $scope.getData('vendorController/getCustomerNamesAsDropDown.json', { customerId: $cookieStore.get('customerId')}, 'customerList')

	    $scope.customerChange = function () {	    	
	        $scope.getData('vendorController/getCompanyNamesAsDropDown.json', {customerId : $scope.customerName == undefined ?'0': $scope.customerName.customerId,companyId: ($cookieStore.get('companyId') !=null && $cookieStore.get('companyId') !="") ? $cookieStore.get('companyId') : ($scope.companyName == undefined || $scope.companyName.id == undefined )? '0':$scope.companyName.id}, 'customerChange');
	    };
	    
	    $scope.companyChange = function () {	    	
	        $scope.getData('vendorController/getcountryListByCompanyId.json', {customerId : $scope.customerName == undefined ?'0': $scope.customerName.customerId,companyId: ($cookieStore.get('companyId') !=null && $cookieStore.get('companyId') !="") ? $cookieStore.get('companyId') : ($scope.companyName == undefined || $scope.companyName.id == undefined )? '0':$scope.companyName.id}, 'companyChange');
	    };
	    
	    $scope.countryChange = function () {	    	
	       // $scope.getData('CommonController/statesListByCountryId.json', {countryId : ($scope.countryName == undefined || $scope.countryName.id == undefined || $scope.countryName.id == "" ) ?'0': $scope.countryName.id}, 'countryChange');
 	        $scope.getData('LocationController/getLocationsListBySearch.json', { customerId: $scope.customerName.customerId, companyId: $scope.companyName.id,  countryId: ($scope.countryName == undefined || $scope.countryName == null || $scope.countryName == "" ) ? $scope.countryName.countryId : 0, status:'Y'}, 'countryChange');

	    };

	    $scope.search = function () {
	        $scope.getData('statutorySetupsController/getProfessionalTaxListBySearch.json', { customerId: ($scope.customerName == undefined || $scope.customerName.customerId == undefined || $scope.customerName.id == "" )? '0': $scope.customerName.customerId, companyId: ($scope.companyName == undefined || $scope.companyName.id == undefined || $scope.companyName.id == "")? '0':$scope.companyName.id, countryId:($scope.countryName == undefined || $scope.countryName.id == undefined || $scope.countryName.id == "" )? 0:$scope.countryName.id, locationId: ($scope.locationName == undefined ||  $scope.locationName == "") ? 0: $scope.locationName} , 'search');
	    };
	    
	    $scope.clear = function () {	 
	    	//$cookieStore.get('customerId') != null ? $scope.customerId : null;
	    	//$cookieStore.get('companyId') != null ? $scope.customerId : null;
	    	//$cookieStore.get('countryId') != null ? $scope.countryd :null;
	    	$scope.customerName = "";
	    	$scope.companyName = "";
	    	$scope.countryName = "";
	    	$scope.locationName = "";
	    };
}]
);





