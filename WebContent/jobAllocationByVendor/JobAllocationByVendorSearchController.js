'use strict';

var jobAllocationByVendorController = angular.module("myApp.JobAllocationByVendor", []);
jobAllocationByVendorController.controller("jobAllocationByVendorController", ['$scope','$rootScope', '$http', '$resource','$filter', '$location','$cookieStore','$window','$parse',function($scope,$rootScope,$http,$resource,$filter,$location,$cookieStore,$window,$parse) {
	
	$('#jobAllocationEntryDiv').hide();
	$('#searchPage').show();
	$('#PreviewFormDiv').hide();
    $.material.init();	
    $scope.previewButton = false;
    $scope.status = "Unassigned";
    $scope.showAllocate= true;
	 
	$("#selectedDate" ).datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown'	,
	      minDate:-3,
	      maxDate:0
	 });


	if($cookieStore.get("roleNamesArray").includes('Vendor Admin')){
			$scope.dropdownDisable = true;
		}else{
			$scope.dropdownDisable = false;
		}
	 
	$scope.selectedDate = $filter('date')(new Date(),"dd/MM/yyyy");
	$scope.yearList = [{"id":2016,"name":2016},{"id":2017,"name":2017},{"id":2018,"name":2018}];
    
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
                //$scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Job Allocation'}, 'buttonsAction');
    			// for button views
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
	        	//alert(angular.toJson(response.data));
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
            	if($scope.status == "Assigned"){
            		$scope.showDeallocate = true;
            		$scope.showAllocate = true;
            	}else if($scope.status == "Unassigned"){
            		$scope.showDeallocate = false;
            		$scope.showAllocate = true;
            	}else if($scope.status == "Rejected"){
            		$scope.showDeallocate = false;
            		$scope.showAllocate = true;
            	}else {
            		$scope.showDeallocate = false;
            		$scope.showAllocate = false;
            	}
            	//alert(angular.toJson($scope.workerGridList));
            }else if(type == 'departmentsAndLocationes'){
            	//alert(angular.toJson(response.data.jobCodes));
            	//$scope.departmentsList = response.data.departmentList;
            	$scope.locationsList  = response.data.locationsList;
            	$scope.shiftCodeList = response.data.shiftCodeList;
            	//$scope.jobCodeList = response.data.jobCodes;
            	
            	$('#PreviewFormDiv1').show();
            	$scope.selectedDate = $filter('date')($scope.workerVo.selectedDate,'dd/MM/yyyy');
            	
            	var i  = 0;
            	$scope.value = new Object();	
               	$scope.value1 = new Object();
               	$scope.value2 = new Object();
                $scope.assignedDetails = [];
               
            	angular.forEach($scope.workerGridList, function (item) {
                    if(item.selected){
                    	$scope.value[i] = item.jobName;
                    	$scope.value1[i] = item.shiftName;
                    	$scope.value2[i] = item.workSkill;
                    	$scope.assignedDetails.push(item);
                    	
                    	console.log('string:'+item.workSkill);
                    	$scope.workSkillChange(item.workSkill,i,false);
                    	i++;
                    } 
                });
            }else if(type == 'plantsList'){
            	$scope.plantsList = response.data;
            }else if(type == 'departmentsList'){
            	$scope.departmentsList = response.data.departmentList;
            }else if(type == 'sectionsList'){
            	//$scope.departmentsList = response.data.departmentsList;
            	$scope.sectionesList = response.data.sectionsList;
            }else if(type == 'workAreasList'){
            	$scope.workAreasList = response.data;
            }else if(type == 'workersCount'){
            	$scope.workersCountList = response.data;
            	
            	//alert(angular.toJson(response.data));
            }else if(type == 'saveJobAllocationDetails'){
            	//alert(angular.toJson(response.data));
            	if(response.data.id == 1){
            		 $scope.Messager('error', 'Error',response.data.desc,buttonDisable);
            	}else if(response.data.id == 0){
            		 $scope.Messager('success', 'success', 'Job Allocation Is Done Successfully',buttonDisable);
            		 $scope.previewButton = true;
            	}else{
            		 $scope.Messager('error', 'Error', 'Error in Job Allocation',buttonDisable);
            	}
            }else if(type == 'saveDeallocationDetails'){
            	//alert(response.data);
            	if(response.data){
            		$scope.Messager('success', 'success', 'Job Deallocation is done Successfully',buttonDisable);
            		$scope.getData('jobAllocationByVendorController/workerGridDetails.json', angular.toJson($scope.workerVo), 'search');
            		$scope.selectAll = false; 
            	}else{
            		 $scope.Messager('error', 'Error', 'Error in Job deallocation',buttonDisable);
            	}
            }else if(type == 'jobCodesList'){
            	//alert(angular.toJson(response.data.jobCodes));
            	$scope[('jobCodeList').concat(buttonDisable)] = response.data.jobCodes;
            	
            }
        },
        function () { alert('Error') });
    }
	  // $scope.getData('jobAllocationByVendorController/getWorkerDropDownsListId.json', { customerId: $cookieStore.get('customerId'),companyId:$cookieStore.get('companyId'),vendorId:$cookieStore.get('vendorId')}, 'workersNamesList');

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
    	$scope.status = "Unassigned";
    	$scope.showAllocate= true;
    	$scope.showDeallocate= false;
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
	    	$scope.workerVo.searchFor = "JobAllocation";
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
    
    $scope.isChecked = function(){    	
    	var flag = false;
    	angular.forEach($scope.workerGridList, function (item) {
            if(item.selected){
            	flag = true;            	
            	return false;
            }          
        });
    	
    	if(flag){ 		
    		//alert('Success');
    		$('#jobAllocationEntryDiv').show();
    		$('#searchPage').hide();
    		$('#PreviewFormDiv').hide();
    		$scope.workerVo.customerId = $cookieStore.get('customerId');
        	$scope.workerVo.companyId = $cookieStore.get('companyId');   
        	$scope.workerVo.status = $scope.status;  
    		$scope.getData('jobAllocationByVendorController/getDepartmentsAndLocationesList.json', angular.toJson($scope.workerVo), 'departmentsAndLocationes');  		    		
        }else{
    		alert('Please Check Atleast one Worker..');
    	}
    };
    
   
    $scope.deallocate = function($event){    	
    	var flag = false;
    	angular.forEach($scope.workerGridList, function (item) {
            if(item.selected){
            	flag = true;            	
            	return false;
            }          
        });
    	
    	if(flag){ 		
    		$scope.jobAllocationVo = new Object();
    		$scope.jobAllocationVo.customerId = $scope.customerName;
        	$scope.jobAllocationVo.companyId = $scope.companyName;   
        	$scope.jobAllocationVo.vendorId = $scope.vendorName; 
        	
	    	$scope.jobAllocationVo.selectedDate =  moment( $('#selectedDate').val(), 'DD/MM/YYYY').format('YYYY-MM-DD'); 
	    	$scope.jobAllocationVo.createdBy = $cookieStore.get('createdBy');
	    	$scope.jobAllocationVo.userId = $scope.userId;
	    	$scope.jobAllocationVo.status = $scope.status;
	    	
	    	var i = 0;
	    	$scope.assignedDetails = [];
	    	angular.forEach($scope.workerGridList, function (item) {
	            if(item.selected){
	            	$scope.assignedDetails.push(item);
	            	i++;
	            } 
	            
	        });
	    	$scope.jobAllocationVo.workerList = $scope.assignedDetails;
        	$scope.getData('jobAllocationByVendorController/saveJobAllocationDetails.json', angular.toJson($scope.jobAllocationVo), 'saveDeallocationDetails',angular.element($event.currentTarget));    	
        	
    	}else{
    		alert('Please Check Atleast one Worker..');
    	}
    };
    
    

    
        
    $scope.locationChange = function(){
    	if($scope.locationId != undefined && $scope.locationId != null && $scope.locationId != ''){
    		$scope.departmentsList = [];
    		$scope.sectionesList = [];
    		$scope.workAreasList = [];
    		
    		$scope.getData('jobAllocationByVendorController/getPlantsList.json', {locationId:$scope.locationId}, 'plantsList');
    	}else{
    		$scope.plantsList =[];
    		$scope.departmentsList = [];
    		$scope.sectionesList = [];
    		$scope.workAreasList = [];
    	}
    }
    
    $scope.plantChange = function(){
    	if($scope.locationId != undefined && $scope.locationId != null && $scope.locationId != '' && $scope.plantId != undefined && $scope.plantId != null && $scope.plantId != ''){
    		$scope.sectionesList = [];
    		$scope.workAreasList = [];
    		$scope.getData('associatingDepartmentToLocationPlantController/getDepartMentDetailsByLocationAndPlantId.json', {customerId:$cookieStore.get('customerId'), companyId: $cookieStore.get('companyId'), locationId:$scope.locationId,plantId:$scope.plantId}, 'departmentsList');
    	}else{
    		$scope.departmentsList = [];
    		$scope.sectionesList = [];
    		$scope.workAreasList = [];
    	}
    }
    
    $scope.departmentChange = function(){
    	if($scope.locationId != undefined && $scope.locationId != null && $scope.locationId != '' && $scope.plantId != undefined && $scope.plantId != null && $scope.plantId != '' && $scope.departmentId != undefined && $scope.departmentId != null && $scope.departmentId != ''){
    		$scope.workAreasList = [];
    		//$scope.getData('jobAllocationByVendorController/getWorkersCountFromManpower.json', {customerId:$cookieStore.get('customerId'), companyId: $cookieStore.get('companyId'), locationId:$scope.locationId,plantId:$scope.plantId, departmentId : $scope.departmentId, selectedDate: moment( $('#selectedDate').val(), 'DD/MM/YYYY').format('YYYY-MM-DD')}, 'workersCount');
    		$scope.getData('associatingDepartmentToLocationPlantController/getSectionDetailsByLocationAndPlantAndDeptId.json', {customerId:$cookieStore.get('customerId'), companyId: $cookieStore.get('companyId'), locationId:$scope.locationId,plantId:$scope.plantId, departmentId : $scope.departmentId}, 'sectionsList');
    	}else{
    		$scope.sectionesList = []; 
    		$scope.workAreasList = [];
    	}
    }
    

    
    $scope.sectionChange = function(){
    	if($scope.locationId != undefined && $scope.locationId != null && $scope.locationId != '' && $scope.plantId != undefined && $scope.plantId != null && $scope.plantId != '' && $scope.sectionId != undefined && $scope.sectionId != null && $scope.sectionId != '' && $scope.departmentId != undefined && $scope.departmentId != null && $scope.departmentId != ''){
    			$scope.getData('jobAllocationByVendorController/getAllWorkAreasBySectionesAndLocationAndPlantAndDept.json', {customerId:$cookieStore.get('customerId'), companyId: $cookieStore.get('companyId'), locationId:$scope.locationId,plantId:$scope.plantId,sectionId:$scope.sectionId,departmentId:$scope.departmentId}, 'workAreasList');
    	}else{    	
    		$scope.workAreasList = [];
    	}
    }
    
    
    var TableData = new Array();
    $scope.SaveDetails = function($event){  
    	var unskilledAllocate = true;
		var skilledAllocate = true;
		var semiSkilledAllocate = true;
		var highSkilledAllocate = true;
		var specialSkilledAllocate = true;
    	
    	if($('#jobAllocationEntry').valid()){
    		
	    		/*var unskilled =0;
	    		var skilled =0;
	    		var semiSkilled = 0;
	    		var highSkilled = 0;
	    		var specialSkilled = 0;
	    		
	    		$('#PreviewFormTable1 tbody tr').each(function(){
		    		var selval = $(this).find('td').eq(3).find('select').val().split(':')[1];
		    		console.log(selval );
		    		if(selval == "UnSkilled"){
		    			unskilled = unskilled +1;
		    		}else if(selval == "Skilled"){
		    			skilled = skilled +1;
		    		}else if(selval == "High Skilled"){
		    			highSkilled = highSkilled +1;
		    		}else if(selval == "Special Skilled"){
		    			specialSkilled = specialSkilled +1;
			    	}else if(selval == "Semi Skilled"){
			    		semiSkilled = semiSkilled +1;
			    	}
	    		});
	    		*/
	    		

		    	$scope.jobAllocationVo = new Object();    
		        
		    	$scope.jobAllocationVo.departmentId = $scope.departmentId;
		    	$scope.jobAllocationVo.shiftId = $scope.shiftId;
		    	$scope.jobAllocationVo.locationId = $scope.locationId;
		    	$scope.jobAllocationVo.plantId = $scope.plantId != undefined && $scope.plantId != '' ? $scope.plantId :null;
		    	$scope.jobAllocationVo.sectionId = $scope.sectionId != undefined && $scope.sectionId != '' ? $scope.sectionId :null;
		    	$scope.jobAllocationVo.workAreaId = $scope.workAreaId != undefined && $scope.workAreaId != '' ? $scope.workAreaId :null;
		    	$scope.jobAllocationVo.selectedDate =  moment( $('#selectedDate').val(), 'DD/MM/YYYY').format('YYYY-MM-DD'); 
		    	$scope.jobAllocationVo.createdBy = $cookieStore.get('createdBy');
		    	$scope.jobAllocationVo.userId = $scope.userId;
		    	$scope.jobAllocationVo.status = 'Unassigned';
		    	
		    	$scope.jobAllocationVo.departmentName = $scope.departmentId != undefined && $scope.departmentId != null && $scope.departmentId != "" ? $('#departmentId option:selected').text() : null;
		        $scope.jobAllocationVo.plantName = $scope.plantId != undefined && $scope.plantId != null && $scope.plantId != "" ? $('#plantId option:selected').text() : null;
		        $scope.jobAllocationVo.locationName = $scope.locationId != undefined && $scope.locationId != null && $scope.locationId != "" ? $('#locationId option:selected').text() : null;
		        $scope.jobAllocationVo.sectionName = $scope.sectionId != undefined && $scope.sectionId != null && $scope.sectionId != "" ? $('#sectionId option:selected').text() : null;
		        $scope.jobAllocationVo.workAreaName = $scope.workAreaId != undefined && $scope.workAreaId != null && $scope.workAreaId != "" ? $('#workAreaId option:selected').text() : null;

		    	
		    	var i = 0;
		    	
		    	$scope.assignedDetails = [];
		    	angular.forEach($scope.workerGridList, function (item) {
		            if(item.selected){
		            	//console.log("i : +"+i+" ::::: jobName: "+$scope.value[i]);
		            	item.jobName = $scope.value[i];
		            	item.shiftName = $scope.value1[i];
		            	item.workSkill = $scope.value2[i];
		            	$scope.assignedDetails.push(item);
		            	i++;
		            	
		            } 
		            
		        });
		    	/*angular.forEach($scope.workersCountList, function (item) {
	    			if(item.name == "Skilled"){
		        	   if(item.id >= skilled){
		        		   skilledAllocate = true;
		        		  // alert(1+skilledAllocate);
		        	   }else{
		        		   skilledAllocate = false;
		        	   }
		           }else if(item.name ==  "UnSkilled"){
		        	   if(item.id >= unskilled){
		        		   unskilledAllocate = true;
		        		  // alert(2+unskilledAllocate);
		        	   }else{
		        		   unskilledAllocate = false;
		        	   }
		           }else if(item.name == "High Skilled"){
		        	   if(item.id >= highSkilled){
		        		  // alert(3);
		        		   highSkilledAllocate = true;
		        	   }else{
		        		   highSkilledAllocate = false;
		        	   }
		    		}else if(item.name == "Special Skilled"){
		    			if(item.id >= specialSkilled){
		    				//alert(4);
		    				specialSkilledAllocate = true;
		    			}else{
		    				specialSkilledAllocate = false;
		    			}
			    	}else if(item.name == "Semi Skilled"){
			    		if(item.id >= semiSkilled){
			    			//alert(5);
			    			semiSkilledAllocate = true;
			    			//alert("4 "+semiSkilledAllocate);
		        	    }else{
		        	    	semiSkilledAllocate = false;
		        	    }
			    	}
		            
		        });*/
		    	
		    	
		    	$scope.jobAllocationVo.workerList = $scope.assignedDetails;		    	
		    	
		    	/*if(!specialSkilledAllocate || !semiSkilledAllocate || !highSkilledAllocate || !skilledAllocate ||!unskilledAllocate){
		    		$scope.Messager('error','Error','Should not able to allocate as the allocation workers exceeds manpower requisition workers')
		    	}else{*/
		    	//console.log(angular.toJson($scope.jobAllocationVo));
		    		$scope.getData('jobAllocationByVendorController/saveJobAllocationDetails.json', angular.toJson($scope.jobAllocationVo), 'saveJobAllocationDetails',angular.element($event.currentTarget));
		    	//}
    	}
    }
    
    
    $scope.assignedDetails = [];

    $scope.previewDetails = function(){    
	 	$scope.departmentName = $scope.departmentId != undefined && $scope.departmentId != null && $scope.departmentId != "" ? $('#departmentId option:selected').text() : null;
        $scope.plantName = $scope.plantId != undefined && $scope.plantId != null && $scope.plantId != "" ? $('#plantId option:selected').text() : null;
        $scope.locationName = $scope.locationId != undefined && $scope.locationId != null && $scope.locationId != "" ? $('#locationId option:selected').text() : null;
        $scope.sectionName = $scope.sectionId != undefined && $scope.sectionId != null && $scope.sectionId != "" ? $('#sectionId option:selected').text() : null;
        $scope.workAreaName = $scope.workAreaId != undefined && $scope.workAreaId != null && $scope.workAreaId != "" ? $('#workAreaId option:selected').text() : null;

    	$('#PreviewFormDiv').show();
    	$('#PreviewFormDiv1').hide();
    	    	
    }
    
    
    $scope.Savingclear= function($event){    	
    	$scope.jobAllocationVo = new Object();
    	$scope.departmentId = '';
    	$scope.locationId= '';
    	$scope.plantId= '';
    	$scope.sectionId= '';
    	$scope.workAreaId= '';   
    	//$scope.shiftName = "";
    	$scope.selectedDate = $filter('date')($('#selectedDate').val(),"dd/MM/yyyy");
    	$scope.plantsList =[];
    	$scope.sectionesList = [];
    	$scope.workAreasList = [];
    	$scope.assignedDetails = [];
    	angular.forEach($scope.workerGridList, function (item) {
            if(item.selected){
            	$scope.assignedDetails.push(item);
            }          
        });
    	$('#PreviewFormDiv').hide();
    	$('#PreviewFormDiv1').show();
    	$scope.previewButton = false;
    	
    }
    
    $scope.returntoSearch = function(){
    	$scope.Savingclear();
    	$scope.searchClear();
    	$scope.assignedDetails = [];
    	$scope.previewButton = false;
    	$('#jobAllocationEntryDiv').hide();
    	$('#searchPage').show();   	   	
    	$('#PreviewFormDiv').hide();
    	$('#PreviewFormDiv1').hide();
    	$scope.selectAll = false;
    	$scope.selectedDate = $filter('date')(new Date(),"dd/MM/yyyy");
    	$scope.workerGridList = [];
    	$scope.status = "Unassigned";
    	$scope.showAllocate= true;
    }
    
    
    $scope.workSkillChange = function($index,ivalue,flag){   
    	if(flag){
    		//console.log(flag);
    		 $scope.getData('jobAllocationByVendorController/getJobCodeListBySkillType.json', { customerId :$scope.customerName ,companyId: $scope.companyName,skillType:$('#workSkill'+$index).val().replace("string:",'' ) }, 'jobCodesList',$index);
    	}else{
    		 $scope.getData('jobAllocationByVendorController/getJobCodeListBySkillType.json', { customerId :$scope.customerName ,companyId: $scope.companyName,skillType:$index}, 'jobCodesList',ivalue);
    	}
    	
    }
    
}]);



