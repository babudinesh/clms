'use strict';

var assignShiftsSearchControllers = angular.module("myApp.AssignShiftsSearch", []);
assignShiftsSearchControllers.controller("AssignShiftsSearchCtrl", ['$scope','$rootScope', '$http', '$resource','$filter', '$location','$cookieStore','$window',function($scope,$rootScope,$http,$resource,$filter,$location,$cookieStore,$window) {
	var shiftDetails;
	$scope.saveShiftDetailsButtonRender = false;
	$("#searchPage").show();
	$("#contentPage").hide();	
	$("#shiftDetailsDiv").hide();
    $scope.companyName = "";
    $scope.customerName = "";
    $scope.vendorName = "";
    
    $.material.init();
	/*$('#patternDate').bootstrapMaterialDatePicker({
	    time: false,
	    clearButton: true,
	    format : "DD/MM/YYYY"
	});*/
	
	 $( "#patternDate" ).datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown',
	    	minDate:new Date()  
	    });

	 
	 if($cookieStore.get('roleNamesArray').includes('Vendor Admin')){
			$scope.dropdownDisable = true;
		}else{
			$scope.dropdownDisable = false;
		}
	 
	 
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
            if (type == 'customerList'){
                $scope.customerList = response.data.customerList;
                $scope.industryList = response.data.industryList;                
                if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
		                $scope.customerName=response.data.customerList[0].customerId;		                
		                $scope.customerChange();
		                }   
            }else if (type == 'customerChange'){
                $scope.companyList = response.data;                
                if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
   	                $scope.companyName = response.data[0].id;
   	                $scope.companyChange();
   	                }
            }else if (type == 'companyChange') {
            	//$scope.workAreaNamesList = response.data.workAreaList;
            	//$scope.departmentNameList = response.data.deptList;
                $scope.vendorList = response.data.vendorList;  
                $scope.locationList = response.data.locationList;
              //  $scope.shiftPatternList = response.data.shiftPatternList;
                if( response.data.vendorList[0] != undefined && response.data.vendorList[0] != "" && response.data.vendorList.length == 1 ){
   	                $scope.vendorName = response.data.vendorList[0].id;   	             
   	            }
            }else if( type =='vendorChange'){
            	$scope.workOrderNamesList = response.data;
            	
            }else if(type == 'patternCodesList'){
            	$scope.shiftPatternList = response.data;
        	}else if(type=='search'){   
            		 $scope.shiftResults = response.data;            		
                	 $("#loader").hide();
                     $("#shiftDetailsDiv").show();                    
            }else if(type == 'getDeptLocPlantDtls'){            	
            	$scope.plantList = response.data[0].plantList;
            	$scope.shift = response.data[0];            	
            }else if(type == 'saveShiftPatternDetails'){
            	if(response.data != undefined && response.data != '' && response.data > 0){
            		$scope.Messager('success', 'success', 'Shift Assign Details Saved Successfully',true); // angular.element($event.currentTarget)
            		//$scope.fun_getWorkerShiftDetails();
            		// $scope.previewDetails();
            	}else{
            		$scope.Messager('error', 'Error', 'Operation Failed... Please Try after some time..',true);
            	}
            }else if(type == 'getWorkerShiftDetails'){
            	var data = response.data;
            	$scope.workerShifthiftPatternDatesList = data[0];
            	data.splice(0,1);
            	$scope.workerShifthiftPatternDetails = data;
            	// need to write logic for binding response data to table from second record
            	/*alert("workerShifthiftPatternDatesList:: "+$scope.workerShifthiftPatternDatesList);
            	alert("workerShifthiftPatternDatesList:: "+angular.toJson($scope.workerShifthiftPatternDatesList));
            	alert("workerShifthiftPatternDetails:: "+$scope.workerShifthiftPatternDetails);
            	alert("workerShifthiftPatternDetails:: "+angular.toJson($scope.workerShifthiftPatternDetails));*/
            }else if(type == 'fun_getPlantsByLocationId'){
            	$scope.plantList = response.data;
            }else if(type == 'fun_getDepartmentsByPlantId'){
            	$scope.departmentList = response.data.departmentList;
            }else if(type == 'fun_getSectionsByDepartmentId'){
            	$scope.sectionsList = response.data.sectionsList;
            }else if(type == 'fun_getWorkAreaBySectionId'){
            	$scope.workAreaList = response.data;
            }else if(type == 'getWorkerShiftPreviewDetails'){            	            	
            	if(response.data.headers.length > 2){
            		$scope.workerShifthiftPatternDatesList = response.data.headers;
                	$scope.workerShifthiftPatternDetails =  response.data.content;
            		$scope.saveShiftDetailsButtonRender = true;
            	}else{
            		alert('No Details Found...');
            	}            		
            }
        },
        function () { alert('Error') });
    }
   
   
    $scope.getData('vendorController/getCustomerNamesAsDropDown.json', { customerId: $cookieStore.get('customerId')}, 'customerList');
    
    $scope.customerChange = function () {
    	$scope.companyName = '';
    	$scope.vendorName = '';
        $scope.getData('vendorController/getCompanyNamesAsDropDown.json', {customerId:$scope.customerName,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.companyName != undefined ? $scope.companyName : 0}, 'customerChange');
    };

    $scope.companyChange = function () {
    	$scope.vendorName = '';
    	//alert($cookieStore.get('vendorId')+"::"+$scope.vendorName);
        $scope.getData('assignShiftsController/getVendorNamesAsDropDown.json', { companyId: $scope.companyName,customerId: $scope.customerName,vendorId:($cookieStore.get('vendorId') != null && $cookieStore.get('vendorId') > 0) ? $cookieStore.get('vendorId') : $scope.vendorName != undefined ? $scope.vendorName : 0}, 'companyChange');       
    };
    
    $scope.vendorChange = function() {
    	if($scope.vendorName != undefined && $scope.vendorName != '' ){
    		$scope.getData('associateWorkOrderController/getAssociateWorkOrderList.json',{ customerId :  ($scope.customerName == undefined) ? '': $scope.customerName, companyId:  ($scope.companyName == undefined )? '0':$scope.companyName, vendorId:($cookieStore.get('vendorId') != null && $cookieStore.get('vendorId') != "") ? $cookieStore.get('vendorId') : $scope.vendorName != undefined ? $scope.vendorName : 0}, 'vendorChange')
    	}
    };
    
    $scope.workOrderChange = function(){
    	if($scope.workOrderName != undefined && $scope.workOrderName != ''  )
    		$scope.getData('assignShiftsController/getDeptLocPlantDtls.json', { customerId: ($cookieStore.get('customerId') != null && $cookieStore.get('customerId') != "") ? $cookieStore.get('customerId') : $scope.customerName != undefined ? $scope.customerName : 0, 
				        	companyId: ($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.companyName != undefined ? $scope.companyName : 0, 
		        			vendorId: ($scope.vendorName != undefined && $scope.vendorName  != "") ? $scope.vendorName : 0,		        			
		        			workOrderName: ($scope.workOrderName != undefined && $scope.workOrderName  != "") ? $scope.workOrderName : 0
    					}, 'getDeptLocPlantDtls')
    };
    
    $scope.fun_getPlantsByLocationId = function(){
    	if($scope.shift != undefined && $scope.shift.locationId != undefined && $scope.shift.locationId != '' && $scope.shift.locationId != null) {
    		/*$scope.getData('shiftsDefineController/getPlantsList.json', { customerId : $scope.customerName , 
    																	companyId : $scope.companyName  , locationId: $scope.shift.locationId, status: 'Y'}, 'fun_getPlantsByLocationId');*/    	
			$scope.departmentList = [];
    		$scope.sectionsList = [];
    		$scope.workAreaList = [];
    		$scope.getData('jobAllocationByVendorController/getPlantsList.json', { locationId: $scope.shift.locationId}, 'fun_getPlantsByLocationId');

    	}else{
    		$scope.plantsList = [];
    		$scope.departmentList = [];
    		$scope.sectionsList = [];
    		$scope.workAreaList = [];
    	}
    	
    }
    
    $scope.fun_getPatterCodes = function(){ 
    	if($scope.shift.plantId != undefined && ($scope.shift.plantId != '' || $scope.shift.plantId  >= 0 )){
    	   	$scope.getData('assignShiftsController/getPatternCodesbyPlantId.json',{customerId: ($cookieStore.get('customerId') != null && $cookieStore.get('customerId') != "") ? $cookieStore.get('customerId') : $scope.customerName != undefined ? $scope.customerName : 0, 
				        	companyId: ($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.companyName != undefined ? $scope.companyName : 0, 
				        	locationId : $scope.shift.locationId,
				        	plantId : $scope.shift.plantId
		        				
    	   			},'patternCodesList');	
    	}
    	else{
    		$scope.shiftPatternList = [];
    	}
    	/*if($scope.shift != undefined && $scope.shift.plantId != undefined && ($scope.shift.plantId != '' || $scope.shift.plantId >= 0) ){
    		$scope.getData('assignShiftsController/getSectionsListByPlantId.json', { customerId : $scope.customerName , 
    																	companyId : $scope.companyName  ,
    																	locationId: $scope.shift.locationId,
    																	plantId: $scope.shift.plantId,
    																	status: 'Y'},
    																	'fun_getSectionsByPlantId');
    	}else{
    		$scope.sectionsList = [];
    	}*/
    	
    	if($scope.shift.locationId != undefined && $scope.shift.locationId != null && $scope.shift.locationId != '' && $scope.shift.plantId != undefined && ($scope.shift.plantId != '' || $scope.shift.plantId >= 0) ){
    		$scope.workAreaList = [];
    		$scope.sectionsList = [];
    		$scope.getData('associatingDepartmentToLocationPlantController/getDepartMentDetailsByLocationAndPlantId.json', { customerId : $scope.customerName , 
    																	companyId : $scope.companyName  ,
    																	locationId: $scope.shift.locationId,
    																	plantId: $scope.shift.plantId},
    																	'fun_getDepartmentsByPlantId');
    	}else{
    		$scope.departmentList = [];
    		$scope.sectionsList = [];
    		$scope.workAreaList = [];
    	}
    	
    }
    $scope.fun_getSectionsByDepartmentId = function(){
    	if($scope.shift.locationId != undefined && $scope.shift.locationId != null && $scope.shift.locationId != '' && $scope.shift.plantId != undefined && $scope.shift.plantId != null && $scope.shift.plantId != '' && $scope.shift.departmentId != undefined && $scope.shift.departmentId != null && $scope.shift.departmentId != ''){
    		$scope.workAreasList = [];
    		$scope.getData('associatingDepartmentToLocationPlantController/getSectionDetailsByLocationAndPlantAndDeptId.json', {customerId:$cookieStore.get('customerId'), companyId: $cookieStore.get('companyId'), locationId:$scope.shift.locationId,plantId:$scope.shift.plantId, departmentId : $scope.shift.departmentId}, 'fun_getSectionsByDepartmentId');
    	}else{
    		$scope.sectionsList = []; 
    		$scope.workAreasList = [];
    	}
    	
    }
    
    $scope.fun_getWorkAreaBySectionId = function(){
    	if($scope.shift.locationId != undefined && $scope.shift.locationId != null && $scope.shift.locationId != '' && $scope.shift.plantId != undefined && $scope.shift.plantId != null && $scope.shift.plantId != '' && $scope.shift.sectionId != undefined && $scope.shift.sectionId != null && $scope.shift.sectionId != '' && $scope.shift.departmentId != undefined && $scope.shift.departmentId != null && $scope.shift.departmentId != ''){
    			$scope.getData('jobAllocationByVendorController/getAllWorkAreasBySectionesAndLocationAndPlantAndDept.json', {customerId:$cookieStore.get('customerId'), companyId: $cookieStore.get('companyId'), locationId:$scope.shift.locationId, plantId:$scope.shift.plantId, sectionId:$scope.shift.sectionId,departmentId:$scope.shift.departmentId}, 'fun_getWorkAreaBySectionId');
    	}else{    	
    		$scope.workAreasList = [];
    	}
    }
    
    $scope.search = function () {  
    	if($('#assignShiftsSearchForm').valid()){
	    	$("#loader").show();
	    	$scope.shift = new Object();
	    	$("#shiftDetailsDiv").hide();
	        $scope.getData('assignShiftsController/getWorkerDetailsToGridView.json', { customerId: ($cookieStore.get('customerId') != null && $cookieStore.get('customerId') != "") ? $cookieStore.get('customerId') : $scope.customerName != undefined ? $scope.customerName : 0, 
				        	companyId: ($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.companyName != undefined ? $scope.companyName : 0, 
		        			vendorId: ($scope.vendorName != undefined && $scope.vendorName  != "") ? $scope.vendorName : 0,
		        			/*deptId: ($scope.departmentName != undefined && $scope.departmentName  != "") ? $scope.departmentName : 0,
		        			workOrderName: ($scope.workOrderName != undefined && $scope.workOrderName  != "") ? $scope.workOrderName : 0,
		        			workAreaName: ($scope.workAreaName != undefined && $scope.workAreaName  != "") ? $scope.workAreaName : 0,*/
		        			workerName : ($scope.workerName != undefined && $scope.workerName  != "") ? $scope.workerName : '',
		        			workerCode : ($scope.workerCode != undefined && $scope.workerCode  != "") ? $scope.workerCode : ''
			        	}, 'search');
    	}
    };
    $scope.clear = function () {
        $scope.customerName = null;
        $scope.companyName = null;        
        $scope.vendorName = null;  
        $scope.shiftResults = [];
        $("#shiftDetailsDiv").hide();
    };
    
   
    $scope.checkAll = function () {     	
        angular.forEach($scope.shiftResults, function (item) {
            item.selected = $scope.selectAll;            
            });

    };
    
    $scope.isChecked = function(){
    	$scope.saveShiftDetailsButtonRender = false;
    	var flag = false;
    	angular.forEach($scope.shiftResults, function (item) {
            if(item.selected){
            	flag = true;            	
            	return false;
            }          
        });
    	
    	if(flag){
    		//$scope.getData('assignShiftsController/getMasterDataForAssignShifts.json', { customerId: $cookieStore.get('customerId')}, 'getDeptLocPlantDtls')
    		// $location.path('/AssignShiftsPattern/');
    		$("#searchPage").hide();
    		$scope.shift = new Object();
    		$scope.byDateShow = false;
    		$scope.ByMonthYearShow = false;
    		$scope.monthYear = "";
    		$scope.shift.departmentId = "";
    		$scope.shift.locationId = "";
    		$scope.shift.plantId = "";
    		$scope.shift.sectionId = "";
    		$scope.shift.workAreaId = "";
    		$scope.shift.shiftCode = "";
    		
    		$("#contentPage").show();
    	}else{
    		alert('Please Check Atleast on Worker..');
    	}
    };
    
    $scope.showSearchPage = function (){
    	$("#searchPage").show();
		$("#contentPage").hide();
		$scope.workerCode = '';
		$scope.workerName = '';
		//$scope.assignShiftsForm.$setPristine(true) ;
		//$scope.assignShiftsForm.$setUntouched();
		
		$scope.selectAll = false;
		$scope.shiftResults = [];
		$("#shiftDetailsDiv").hide();
		
		$scope.locationsList = [];
		$scope.shiftPatternList = [];
		$scope.plantList = [];
		$scope.departmentList = [];
		$scope.sectionsList = [];
		$scope.workAreaList = [];
		// $window.location.reload();
    }
    
    
    $scope.saveShiftPatternDetails = function(){
    	
    	if($('#assignShiftsForm').valid()){
    		$scope.shiftDtls = new Object();
    		$scope.shiftDtls.customerId =  $scope.customerName;
    		$scope.shiftDtls.companyId = $scope.companyName;
    		$scope.shiftDtls.vendorId = $scope.vendorName;
    		$scope.shiftDtls.workOrderId = $scope.workOrderName;
    		$scope.shiftDtls.departmentId = $scope.shift.departmentId;
    		$scope.shiftDtls.locationId = $scope.shift.locationId;
    		$scope.shiftDtls.plantId = $scope.shift.plantId;
    		$scope.shiftDtls.sectionId = $scope.shift.sectionId;
    		$scope.shiftDtls.shiftCode = $scope.shift.shiftCode;
    		$scope.shiftDtls.workAreaId = $scope.shift.workAreaId; 
    		$scope.shiftDtls.createdBy = $cookieStore.get('createdBy'); 
	        $scope.shiftDtls.modifiedBy = $cookieStore.get('modifiedBy'); 
	        $scope.shiftDtls.monthYear = $scope.monthYear;
	        if($scope.monthYear== 'N'){
	        	$scope.shiftDtls.patternDate =  moment('01/'+$scope.month+'/'+$scope.year,'DD/MM/YYYY').format('YYYY-MM-DD');
	        }else{
	        	$scope.shiftDtls.patternDate = moment($('#patternDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
	        }
    		$scope.shiftDtls.assignShiftsVo = $scope.shiftResults;
    		$scope.getData('assignShiftsController/saveShiftPatternDetails.json',    				
    				angular.toJson($scope.shiftDtls)    		    		
    		, 'saveShiftPatternDetails');
    	}
    };
    
    
    
    $scope.fun_getWorkerShiftDetails = function(){      	
    	if($('#patternDate').val() == undefined || $('#patternDate').val() ==''){
    		$scope.shift.shiftCode = '';
    		$scope.Messager('error', 'Error', 'Please select Pattern Date first...',true);
    		return;
    	}
    	$scope.shiftDtls = new Object();
    	$scope.shiftDtls.customerId =  $scope.customerName;
		$scope.shiftDtls.companyId = $scope.companyName;
		$scope.shiftDtls.vendorId = $scope.vendorName;
		$scope.shiftDtls.workOrderId = $scope.workOrderName;
    	$scope.shiftDtls.shiftCode = $scope.shift.shiftCode;
    	$scope.shiftDtls.assignShiftsVo = $scope.shiftResults;
    	$scope.shiftDtls.patternDate =moment('01/'+$scope.month+'/'+$scope.year,'DD/MM/YYYY').format('YYYY-MM-DD'); // moment($('#patternDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
    	$scope.getData('assignShiftsController/getWorkerShiftDetails.json', angular.toJson($scope.shiftDtls), 'getWorkerShiftDetails');
    }
    
    
    $scope.previewDetails = function(){
    	if($scope.monthYear == undefined || $scope.monthYear == ''){
    		$scope.Messager('error', 'Error', 'Please select Pattern Period...',true);
    		return;
    	}
    		
    	if($scope.monthYear== 'N'){
	    	if($scope.month == undefined || $scope.month =='' || $scope.year ==undefined || $scope.year ==''){    		    		
	    		$scope.Messager('error', 'Error', 'Please select Pattern Month & Year first...',true);
	    		if($scope.shift != undefined && $scope.shift.shiftCode != undefined && $scope.shift.shiftCode != '')
	    			$scope.shift.shiftCode = '';
	    		return;
	    	}
	    }else{
	    	if($('#patternDate').val() == undefined || $('#patternDate').val() ==''){	    		
	    		$scope.Messager('error', 'Error', 'Please select Pattern Date first...',true);
	    		if($scope.shift != undefined && $scope.shift.shiftCode != undefined && $scope.shift.shiftCode != '')
	    			$scope.shift.shiftCode = '';
	    		return;
	    	}
    	}
    	var status = 0;
    	if($('#assignShiftsForm').valid()){
	    	angular.forEach($scope.shiftPatternList, function(value, key){	    
	    		var pName = angular.toJson(value.desc.split(" - ")[2]);	    		
			      if(value.name == $scope.shift.shiftCode && '"Monthly"' == pName && $scope.monthYear == 'Y'){					    	 
			    	  status = 1;
			      }else if(value.name == $scope.shift.shiftCode && ('"Weekly"' == pName || '"Daily"' == pName || '"Bi Weekly"' == pName ) && $scope.monthYear == 'N'){					    	 
			    	  status = 2;
			      }		    		
			   });	 	    	
    	}
    	if(status == 1){
    		 $scope.Messager('error', 'Error', 'For Monthly Pattern, Please Select Pattern Period By Month & Year',true);
	    	  return;
    	}else if(status == 2){
	   		 $scope.Messager('error', 'Error', 'For Daily / Weekly / Bi Weekly Patterns, Please Select Pattern Period By Date',true);
	   		 return;
		}
    	
    	if($('#assignShiftsForm').valid()){    		
    		$scope.shiftDtls = new Object();
    		$scope.shiftDtls.customerId =  $scope.customerName;
    		$scope.shiftDtls.companyId = $scope.companyName;
    		$scope.shiftDtls.vendorId = $scope.vendorName;
    		$scope.shiftDtls.workOrderId = $scope.workOrderName;
    		$scope.shiftDtls.departmentId = $scope.shift.departmentId;
    		$scope.shiftDtls.locationId = $scope.shift.locationId;
    		$scope.shiftDtls.plantId = $scope.shift.plantId;
    		$scope.shiftDtls.sectionId = $scope.shift.sectionId;
    		$scope.shiftDtls.shiftCode = $scope.shift.shiftCode;
    		$scope.shiftDtls.workAreaId = $scope.shift.workAreaId;
    		$scope.shiftDtls.createdBy = $cookieStore.get('createdBy'); 
	        $scope.shiftDtls.modifiedBy = $cookieStore.get('modifiedBy'); 
	        if($scope.monthYear== 'N'){
	        	$scope.shiftDtls.patternDate =  moment('01/'+$scope.month+'/'+$scope.year,'DD/MM/YYYY').format('YYYY-MM-DD');
	        }else{
	        	$scope.shiftDtls.patternDate = moment($('#patternDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
	        }
	            	
    		$scope.shiftDtls.assignShiftsVo = $scope.shiftResults;
    		$scope.getData('assignShiftsController/previewDetails.json', angular.toJson($scope.shiftDtls), 'getWorkerShiftPreviewDetails');
    		    		
    	}
    };
        
    
    $scope.fun_date_year_Listener = function(){ 	 
 	  if($scope.monthYear== 'Y'){		  
 		  $scope.byDateShow = true;
 		  $scope.ByMonthYearShow = false;
 		  $scope.year ="";
 		  $scope.month = "";
 	  }else{
 		  $scope.byDateShow = false;
 		  $scope.ByMonthYearShow = true;
 		  $scope.year =year+"";
 		  $scope.month = month+"";
 		  $('#patternDate').val(''); 		 
 	  }
    }
    
    
    
}]);



