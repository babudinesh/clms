'use strict';

jobAllocationByVendorController.controller("jobAllocationApproval", ['$scope','$rootScope', '$http', '$resource','$filter', '$location','$cookieStore','$window',function($scope,$rootScope,$http,$resource,$filter,$location,$cookieStore,$window) {
	
	$('#jobAllocationEntryDiv').hide();
	$('#searchPage').show();
    $.material.init();	
    $scope.previewButton = false;
    $scope.status = "Assigned";
	 
	 $("#selectedDate" ).datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown'	,
	      minDate:-3,
	      maxDate:0
	 });

	 if($cookieStore.get('roleNamesArray').includes('Vendor Admin')){
		$scope.dropdownDisable = true;
	 }else{
		$scope.dropdownDisable = false;
	 }
	 
	$scope.selectedDate = $filter('date')(new Date(),"dd/MM/yyyy");
	//$scope.yearList = [{"id":2016,"name":2016},{"id":2017,"name":2017},{"id":2018,"name":2018}];
    
	$scope.monthList = [{"id":1,"name":"January"},
                        {"id":2,"name":"February"},
                        {"id":3,"name":"March"},
                        {"id":4,"name":"April"},
                        {"id":5,"name":"May"},
                        {"id":6,"name":"June"},
                        {"id":7,"name":"July"},
                        {"id":8,"name":"August"},
                        {"id":9,"name":"September"},
                        {"id":10,"name":"October"},
                        {"id":11,"name":"November"},
                        {"id":12,"name":"December"}];
	
	$scope.list_skills = [{"id":"Skilled","name" : "Skilled" },
	                       {"id":"Semi Skilled","name" : "Semi Skilled" },
	                       {"id":"High Skilled","name" : "High Skilled" },
	                       {"id":"Special Skilled","name" : "Special Skilled" },
	                       {"id":"UnSkilled","name" : "UnSkilled" }];
	
	$scope.list_status = [{"id":"Assigned", "name":"Assigned"},
	                      {"id":"Unassigned", "name":"Unassigned"},
	                      {"id":"Approved", "name":"Approved"},
	                      {"id":"Rejected", "name":"Rejected"}];
	
	
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
	   
	$scope.userId = $cookieStore.get('userId');
	
	   
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
	                $scope.customerName=response.data.customerList[0].customerId;	
	                $scope.dropdownDisableCustomer = true;
	                $scope.customerChange();
	            }
               // $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Job Allocation'}, 'buttonsAction');
    			if($scope.buttonArray == undefined || $scope.buttonArray == '')
  	                $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Job Allocation'}, 'buttonsAction');
        	}else if (type == 'customerChange'){
	            $scope.companyList = response.data;
	            if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
   	                $scope.companyName = response.data[0].id;
   	                $scope.dropdownDisableCompany = true;
   	                $scope.companyChange();
   	            }
	        }else if(type == 'companyChange'){
	        	$scope.vendorList = response.data.vendorList;
	        	if( response.data.vendorList != null && response.data.vendorList[0] != undefined && response.data.vendorList[0] != "" && response.data.vendorList.length == 1 ){
   	                $scope.vendorName = response.data.vendorList[0].id;
   	                $scope.dropdownDisableVendor = true;
   	                $scope.vendorChange();
   	            }
	        }else if (type == 'workersNamesList'){
                $scope.workerList = response.data.workerDropDownList; 
                
            }else if(type == 'search'){
            	$scope.workerGridList = response.data;
            	
            }else if(type == 'saveJobAllocationDetails'){
            	//alert(response.data);
            	if(response.data){
            		$scope.Messager('success', 'success', 'Job Allocation '+$scope.status1+' Successfully',buttonDisable);
          	    	$scope.getData('jobAllocationByVendorController/workerGridDetails.json', angular.toJson($scope.workerVo), 'search');
            	}else{
            		 $scope.Messager('error', 'Error', 'Error in Job Allocation',buttonDisable);
            	}
            }
        },
        function () { alert('Error') });
    }

    $scope.getData('vendorController/getCustomerNamesAsDropDown.json', { customerId: $cookieStore.get('customerId')}, 'customerList')
    
    $scope.customerChange = function () {
   		$scope.companyName = '';       
   		$scope.getData('vendorController/getCompanyNamesAsDropDown.json', {customerId:$scope.customerName,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.companyName != undefined && $scope.companyName != '' ? $scope.companyName : 0}, 'customerChange');
   };
   
  	
   $scope.companyChange = function() {    	   
       $scope.getData('workerController/getVendorAndWorkerNamesAsDropDowns.json', { customerId: ($scope.customerName == undefined || $scope.customerName == null )? '0' : $scope.customerName,companyId: ($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.companyName != undefined ? $scope.companyName : 0, vendorId:($cookieStore.get('vendorId') != null && $cookieStore.get('vendorId') != "") ? $cookieStore.get('vendorId') : $scope.vendorName != undefined ? $scope.vendorName : 0  }, 'companyChange');
   };
    
   $scope.vendorChange = function(){
	   $scope.getData('jobAllocationByVendorController/getWorkerDropDownsListId.json', { customerId: $scope.customerName,companyId:$scope.companyName,vendorId:$scope.vendorName}, 'workersNamesList');
   }
   
    $scope.searchClear = function(){
    	$scope.workerVo = new Object();
    	//$scope.workerId='';
    	$scope.workerCode='';
    	$scope.workerName='';
    	if(!$scope.dropdownDisableVendor){
    		$scope.vendorName = "";
		}
    	$scope.selectedDate = $filter('date')(new Date(),"dd/MM/yyyy");
    	$scope.status = "Assigned";
    	$scope.workerGridList = [];
    	
    }
    
    $scope.statusChange = function(){
    	$scope.workerGridList = [];
    }
    
    
    $scope.search = function(){
    	if($('#assignShiftsSearchForm').valid()){
	    	$scope.workerVo = new Object();
	    	$scope.workerVo.customerId = $cookieStore.get('customerId');
	    	$scope.workerVo.companyId = $cookieStore.get('companyId');
	    	$scope.workerVo.vendorId = $scope.vendorName;
	    	$scope.workerVo.workerCode = $scope.workerCode != undefined && $scope.workerCode != null ? $scope.workerCode: '';
	    	$scope.workerVo.workerName = $scope.workerName != undefined && $scope.workerName != null ? $scope.workerName : '';
	    	$scope.workerVo.workerId = $scope.workerId != 0 ? $scope.workerId : '' ;
	    	$scope.workerVo.status =  $scope.status;	
	    	$scope.workerVo.searchFor = "JobApproval";
	    	$scope.workerVo.userId = $scope.userId;
	    	$scope.workerVo.selectedDate = moment( $('#selectedDate').val(), 'DD/MM/YYYY').format('YYYY-MM-DD'); 
	    	$scope.getData('jobAllocationByVendorController/workerGridDetails.json', angular.toJson($scope.workerVo), 'search');
    	}
    }
    
   
    $scope.checkAll = function () {
    	if($scope.workerGridList != undefined && $scope.workerGridList != '' && $scope.workerGridList.length > 0){
    		 angular.forEach($scope.workerGridList, function (item) {
    	            item.selected = $scope.selectAll;            
    	     });
    	}else{
    		$scope.selectAll = false;    		
    	}
    };
    
    $scope.approve = function($event){
    	$scope.status1 = "Approved";
    	$scope.save($event);
    };
    
    $scope.reject = function($event){
    	$scope.status1 = "Rejected";
    	$scope.save($event);
    }
    
    $scope.save = function($event){    	
    	var flag = false;
    	angular.forEach($scope.workerGridList, function (item) {
            if(item.selected){
            	flag = true;            	
            	return false;
            }          
        });
    	
    	if(flag){ 		
    		$scope.jobAllocationVo = new Object();
    		
	    	$scope.jobAllocationVo.selectedDate =  moment( $('#selectedDate').val(), 'DD/MM/YYYY').format('YYYY-MM-DD'); 
	    	$scope.jobAllocationVo.createdBy = $cookieStore.get('createdBy');
	    	$scope.jobAllocationVo.userId = $scope.userId;
	    	$scope.jobAllocationVo.status = $scope.status1;
	    	
	    	var i = 0;
	    	$scope.assignedDetails = [];
	    	angular.forEach($scope.workerGridList, function (item) {
	            if(item.selected){
	            	$scope.assignedDetails.push(item);
	            	i++;
	            } 
	            
	        });
	    	$scope.jobAllocationVo.workerList = $scope.assignedDetails;
        	$scope.getData('jobAllocationByVendorController/ApproveOrRejectJobAllocation.json', angular.toJson($scope.jobAllocationVo), 'saveJobAllocationDetails',angular.element($event.currentTarget));    	

    	}else{
    		alert('Please Check Atleast one Worker..');
    	}
    };

   
}]);



