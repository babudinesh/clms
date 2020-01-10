'use strict';

var workerDetailsControllers = angular.module("myApp.worker", []).config(['$compileProvider',
  function($compileProvider) {
	 $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|file|ftp|blob):|data:image\//);
  }
]);



workerDetailsControllers.controller("workerSearchCtrl", ['$scope', '$http', '$resource', '$location','myservice','$cookieStore', function ($scope, $http, $resource, $location,myservice, $cookieStore) {
	var workerDetails;
	 $("#workerDiv").hide();
	$scope.workerVo= new Object();
	$scope.status ='I';
	
	
	if($cookieStore.get("roleNamesArray").includes('Vendor Admin')){
		$scope.dropdownDisable = true;
	}else{
		$scope.dropdownDisable = false;
	}
	
	
    $scope.getData = function (url, data, type) {
    
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
        	} else if (type == 'customerList')
            {
                $scope.customerList = response.data.customerList;
                if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
		                $scope.workerVo.customerId=response.data.customerList[0].customerId;		                
		                $scope.customerChange();
		        } 
                
                if ( ! $.fn.DataTable.isDataTable( '#workerDetailsTable' ) ) {        
	                workerDetails = $('#workerDetailsTable').DataTable({        
	                    "columns": [
	                       { "data": "customerName" },
	                       { "data": "companyName" },
	                       { "data": "vendorName" },
	                       { "data": "firstName" ,
	                    	   "render": function ( data, type, full, meta ) {
	                    		   
	                    		   
	                 		      return '<a href="#/workerDetails/'+full.workerInfoId+'">'+data+'</a>';
	                 		    }
	                       },
	                       { "data": "workerCode"},
	                       { "data": "isActive" }],                      
	                       
	                        });
	                
                }
             // for button views
            	if($scope.buttonArray == undefined || $scope.buttonArray == '')
            	$scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Worker Details'}, 'buttonsAction'); 
                
            }else if(type == 'customerChange'){	        		
        		 $scope.companyList = response.data; 
        		
        		 if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
    	                $scope.workerVo.companyId = response.data[0].id;
    	                
    	                if($cookieStore.get('dashboardWorkerType') != null && $cookieStore.get('dashboardWorkerType') != undefined && $cookieStore.get('dashboardWorkerType') !='' ){
    	                 	$scope.status = $cookieStore.get('dashboardWorkerType');
    	                	$scope.getData('workerController/workerGridDetails.json', { customerId: ($cookieStore.get('customerId') != null &&  $cookieStore.get('customerId') != undefined) ? $cookieStore.get('customerId') : '', companyId: ($scope.workerVo.companyId != null && $scope.workerVo.companyId != undefined) ? $scope.workerVo.companyId : '', vendorId: "" ,status: "I", workerCode:  "", workerName:  "" }, 'search');
    	                	$cookieStore.put('dashboardWorkerType',null);
    	                }
    	            	$scope.companyChange();
    	         }
        		 
        	}else if (type == 'companyChange') {       		
                $scope.vendorList = response.data.vendorList;  
                if( response.data != undefined && response.data.vendorList != "" && response.data.vendorList.length == 1 ){
	                $scope.workerVo.vendorId = response.data.vendorList[0].id;	              
	                }
               
            }else if(type=='search'){        
                	workerDetails.clear().rows.add(response.data).draw();  
                    $("#loader").hide();
                    $("#workerDiv").show();	           
            }
        },
		function () {
        	//alert('Error') 
        	
        });
    }   

    $scope.getData('vendorController/getCustomerNamesAsDropDown.json', { customerId: $cookieStore.get('customerId')}, 'customerList')
    
    
   $scope.customerChange = function () {
	    	if($scope.workerVo.customerId != undefined && $scope.workerVo.customerId != ""){
	    	$scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.workerVo.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.workerVo.companyId != undefined ? $scope.workerVo.companyId : 0}, 'customerChange');
	    	}
	    };
	    
	    
	    
	    $scope.companyChange = function () {
	    	if($scope.workerVo.companyId != undefined && $scope.workerVo.companyId != ""){
	        $scope.getData('workerController/getVendorAndWorkerNamesAsDropDowns.json', { customerId: $scope.workerVo.customerId,companyId: $scope.workerVo.companyId, vendorId:($cookieStore.get('vendorId') != null && $cookieStore.get('vendorId') != "") ? $cookieStore.get('vendorId') : $scope.workerVo.vendorId != undefined ? $scope.workerVo.vendorId : 0  }, 'companyChange');
	    	}
	    };
      
      
      
    $scope.search = function () {
    	$("#loader").show();
        $("#workerDiv").hide();
        $scope.getData('workerController/workerGridDetails.json', { customerId: $scope.workerVo.customerId != undefined ? $scope.workerVo.customerId : "", companyId: $scope.workerVo.companyId != undefined ? $scope.workerVo.companyId :"", vendorId: $scope.workerVo.vendorId != undefined ? $scope.workerVo.vendorId : "" ,status: $scope.status, workerCode: $scope.workerVo.workerCode != undefined ? $scope.workerVo.workerCode : "", workerName: $scope.workerVo.workerName != undefined ? $scope.workerVo.workerName : "",workerId:"0"}, 'search');
        
    };
    
    
    
    $scope.clear = function () {
    	$scope.workerVo = new Object();          
    };
    
    $scope.rowClick = function($event){
    	var data = table.row($(this).parents('tr')).data();
   	 	//alert("data.customerId::"+data+"::"+$event);
    }

  
}]
);



