'use strict';

var workerBadgeGenerationController = angular.module("myApp.workerBadgeGeneration", []);



workerBadgeGenerationController.controller("workerBadgeGenerationSearchCtrl", ['$scope', '$http', '$resource', '$location','myservice','$cookieStore', function ($scope, $http, $resource, $location,myservice,$cookieStore) {  	
	var badgeDataTable;
	$scope.badgeTableView = false;
	$scope.workerbadgeVo = new Object();
	
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
	                $scope.workerbadgeVo.customerName=response.data.customerList[0].customerId;		                
	                $scope.customerChange();
	                }
         	  if ( ! $.fn.DataTable.isDataTable( '#badgeTable' ) ) {        		   
         		 badgeDataTable = $('#badgeTable').DataTable({        
	                     "columns": [	  
							{ "data": "customerName" },
							{ "data": "companyName" },
							{ "data": "vendorName" },
	                        { "data": "badgeCode",
	                        	"render": function ( data, type, full, meta ) {                 		                    		 
	               		      return '<a href="#/workerBadgeGeneration/'+full.workerBadgeId+'">'+data+'</a>';
	               		    }
	                     },	                     	
	                   		{ "data": "workerName" }]
	               });  
	      		}
         	// for button views
         	 if($scope.buttonArray == undefined || $scope.buttonArray == '')
         	 $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Worker Badge Generation'}, 'buttonsAction');
         	   
            }else if(type == 'customerChange'){	        		
        		$scope.companyList = response.data; 
        		  if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
     	                $scope.workerbadgeVo.companyName = response.data[0].id;
  					$scope.companyChange();
     	                }
        	}else if (type == 'companyChange') {       		
                $scope.vendorList = response.data.vendorList;                
                if( response.data != undefined && response.data.vendorList != "" && response.data.vendorList.length == 1 ){
	                $scope.workerbadgeVo.vendorName = response.data.vendorList[0].id;	              
	                }
                
            }else if(type=='search'){
            	$scope.badgeTableView = true;
            	badgeDataTable.clear().rows.add(response.data).draw();
                // $scope.result = response.data;
            }
        },
        function () {
        	//alert('Error') 
        	
        });
    }   

    $scope.getData('vendorController/getCustomerNamesAsDropDown.json', { customerId: $cookieStore.get('customerId')}, 'customerList')
    
    $scope.customerChange = function () { 	
    	
	    	if($scope.workerbadgeVo.customerName != undefined && $scope.workerbadgeVo.customerName != ""){
	    	$scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.workerbadgeVo.customerName,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.workerbadgeVo.companyName != undefined ? $scope.workerbadgeVo.companyName : 0}, 'customerChange');
	    	}
	    };
	    
	    
	    
    $scope.companyChange = function () {
    	if($scope.workerbadgeVo.companyName != undefined && $scope.workerbadgeVo.companyName != ""){
    		$scope.getData('workerController/getVendorAndWorkerNamesAsDropDowns.json', { customerId: $scope.workerbadgeVo.customerName,companyId: $scope.workerbadgeVo.companyName ,vendorId:($cookieStore.get('vendorId') != null && $cookieStore.get('vendorId') != "") ? $cookieStore.get('vendorId') : $scope.workerbadgeVo.vendorName != undefined ? $scope.workerbadgeVo.vendorName : 0}, 'companyChange');
    	}
    };
  	
      
    $scope.search = function () {    	
        $scope.getData('workerBadgeGenerationController/getWorkerDetailsForGrid.json',  angular.toJson($scope.workerbadgeVo) , 'search');
    };
    
    
    
    $scope.clear = function () {
           
    };
}]
);



