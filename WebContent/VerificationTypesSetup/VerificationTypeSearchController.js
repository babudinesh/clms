'use strict';
var veificationTypeSetupCtrl = angular.module("myApp.veificationTypeSetup", ['ngCookies']);
veificationTypeSetupCtrl.controller("veificationTypeSetupCtrl", ['$scope', '$http', '$resource', '$routeParams', '$location', '$filter','$cookieStore', function ($scope, $http, $resource, $routeParams, $location, $filter, $cookieStore) {
     	$('#searchPanel').hide();
     	var deptTables;
	    $scope.isActive = "Y";
	    $scope.enable = false;
	    $scope.customerName = new Object();
	    $scope.companyName = new Object();
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
        
        $scope.statusList = [{"id":"Y","name":"Active"},{"id":"N","name":"In-Active"}]
	       
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
	        	}else if (type == 'customerList'){
	                $scope.customerList = response.data.customerList;
	               
	                if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){
		                $scope.customerName.customerId=response.data.customerList[0].customerId;		                
		                $scope.customerChange();
		                }
	                
	                if ( ! $.fn.DataTable.isDataTable( '#deptTable' ) ) {        		   
	                	deptTables = $('#deptTable').DataTable({        
	 	                     "columns": [
	 	                         {"data" : "customerName"},
	 	                         {"data" : "companyName"},
	 	                         { "data": "verificationType",
	 	                        	"render": function ( data, type, full, meta ) {                 		                    		 
			 	               		      return '<a href="#/verificationTypesSetup/'+full.workerVerificationTypesSetupId+'">'+data+'</a>';
			 	               		    }
	 	                        }/*,
	 	                        { "data": "mandatiory" }*/
	 	                      ]
	 	               });  
	 	      		}
	                
	                if($scope.buttonArray == undefined || $scope.buttonArray == '')
	                	$scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Worker Verification Type Setup'}, 'buttonsAction'); 
	                   
	                
	            }else if (type == 'customerChange'){
	                $scope.companyList = response.data;	                
	                if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	                $scope.companyName = response.data[0];
	                }
                
	            }else if (type == 'companyChange') { 
	                $scope.list_countries = response.data.countriesList;               
	                $scope.countryId = response.data.countriesList[0].id
	            } else if(type=='search'){
	            	$('#searchPanel').show();
	            	deptTables.clear().rows.add(response.data.WorkerVerificationTypesSetupList).draw(); 
	               
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
	    	if($scope.companyName.id != undefined && $scope.companyName.id != "" && $scope.customerName != undefined && $scope.customerName !=""){
	    		$scope.getData('verificationTypeSetupController/getCountryListByCompanyId.json', { companyId: $scope.companyName.id, customerId: $scope.customerName  }, 'companyChange');
	    	}
	    };
	    

	    $scope.search = function () {
	    	$scope.setupVo = new Object();
	    	$scope.setupVo.customerId = ($scope.customerName == undefined || $scope.customerName.customerId == undefined )? '0': $scope.customerName.customerId;
	    	$scope.setupVo.companyId =  ($scope.companyName == undefined || $scope.companyName.id == undefined )? '0':$scope.companyName.id;
	    	$scope.setupVo.isActive = $scope.isActive;
	    	$scope.setupVo.verificationType = $scope.verificationType;
	    	//alert(angular.toJson($scope.setupVo));
	        $scope.getData('workerVerificationTypesSetupController/getWorkerVerificationTypesSetupsGridData.json', angular.toJson($scope.setupVo), 'search');
	    };
	    
	    $scope.clear = function () {	    	
	    	 $scope.departmentCode="";
	    	 $scope.departmentName="";
	    	 $scope.isActive="Y";
	    };
}]
);





