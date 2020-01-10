'use strict';

var departmentWiseWorkAllocation = angular.module("myApp.DepartmentWiseWorkAllocation", []);
departmentWiseWorkAllocation.controller("departmentWiseWorkAllocationSearchController", ['$scope', '$http', '$resource', '$location','myservice','$cookieStore','$filter', function ($scope, $http, $resource, $location,myservice, $cookieStore,$filter) {
	var workerDetails;
	 $("#workerDiv").hide();
	$scope.workerVo= new Object();
	$scope.status ='I';
	
	
	if($cookieStore.get('roleId') == 6){
		$scope.dropdownDisable = true;
		$scope.departmentId = $cookieStore.get('departmentId');
	}else{
		$scope.dropdownDisable = false;
	}
	
	if($cookieStore.get('roleId') == 7){
		$scope.hideNewCreation = true;
	}
	
	 $('#fromDate,#toDate').datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown'
	    	  
	    });
	 
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
		                $scope.customerId=response.data.customerList[0].customerId;		                
		                $scope.customerChange();
		                } 
                
                if ( ! $.fn.DataTable.isDataTable( '#workerDetailsTable' ) ) {        
	                workerDetails = $('#workerDetailsTable').DataTable({        
	                    "columns": [
	                       { "data": "transactionId" ,
	                    	   "render": function ( data, type, full, meta ) {   		   
	                    		   
		                 		      return '<a href="#/departmentWiseWorkAllocationEdit/'+full.workerAllocationId+'">'+data+'</a>';
		                 	}},
	                       { "data": "transactionDate", "render":function(data){
	               				  return $filter('date')(data, "dd/MM/yyyy");
                           }},
	                       { "data": "plannedOrAdhoc" },
	                       { "data": "requestStatus" },
	                       { "data": "fromDate","render":function(data){
	               				  return $filter('date')(data, "dd/MM/yyyy");
                           } },
	                       { "data": "toDate","render":function(data){
	               				  return $filter('date')(data, "dd/MM/yyyy");
                           }},
	                       { "data": "departmentName" },
	                       { "data": "locationName" }],                      
	                       
	                });
                }
                

             // for button views
  			  	if($scope.buttonArray == undefined || $scope.buttonArray == '')
                $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Department Wise Worker Allocation'}, 'buttonsAction');
                
            }else if(type == 'customerChange'){	        		
        		$scope.companyList = response.data; 
        		
        		 if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
    	                $scope.companyId = response.data[0].id;
    	                $scope.companyChange();
    	                }
        		 
        	}else if (type == 'departmentsAndLocationes') {        		
            	 $scope.countriesList = response.data.countriesList; 
                 $scope.locationsList = response.data.locationList;
                 $scope.departmentsList = response.data.departmentList;
                 $scope.countryId = response.data.countriesList[0].id
               
            }else if(type=='search')
            {                          
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
	    	if($scope.customerId != undefined && $scope.customerId != ""){
	    	$scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.companyId != undefined ? $scope.companyId : 0}, 'customerChange');
	    	}
	};
	    
	 $scope.companyChange = function () {
	    	if($scope.companyId != undefined && $scope.companyId != ""){	    		
	    		$scope.getData('CompanyController/getLocationsByCompanyId.json', {customerId:$scope.customerId,companyId:$scope.companyId}, 'departmentsAndLocationes');
	    	}
	    };
      
      
      
    $scope.search = function () {
    	$("#loader").show();
        $("#workerDiv").hide();
        if(($('#fromDate').val() != undefined && $('#fromDate').val() != null) && ($('#toDate').val() != undefined && $('#toDate').val() != null) && (new Date(moment($('#toDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime() <= new Date(moment($('#fromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime())){
   	    	$scope.Messager('error', 'Error', 'To Date Should not be lessthan From Date');
   	    	return;
 	  	}
        $scope.fromDate = $('#fromDate').val() != undefined ?  moment($('#fromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') : "";
		$scope.toDate = $('#toDate').val() != undefined ? moment($('#toDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') : "";
        $scope.getData('departmentWiseWorkerAllocationController/departmentWiseWorkerAllocationGridDetails.json', { customerId: $scope.customerId != undefined ? $scope.customerId : "", companyId: $scope.companyId != undefined ? $scope.companyId :"", countryId: $scope.countryId != undefined ? $scope.countryId : "" ,locationId: $scope.locationId != undefined ?  $scope.locationId : "", departmentId: $scope.departmentId != undefined ? $scope.departmentId : "", plannedOrAdhoc: $scope.plannedOrAdhoc != undefined ? $scope.plannedOrAdhoc : "", fromDate: $scope.fromDate != undefined ? $scope.fromDate : "", toDate: $scope.toDate != undefined ? $scope.toDate : "" }, 'search');
        
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



