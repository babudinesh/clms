'use strict';


departmentWiseWorkAllocation.controller("departmentWiseWorkAllocationEditController", ['$scope', '$http', '$resource', '$location','$cookieStore','$routeParams','$filter', function ($scope, $http, $resource, $location, $cookieStore,$routeParams,$filter) {
	var workerDetails;
	 $("#workerDiv").hide();
	$scope.departmentWisellocation= new Object();
	$scope.workSkill = new Object();
	$scope.departmentWisellocation.workerAllocationRequirementList = [];
	$scope.workerAllocationReqList=[];
	$scope.departmentWisellocation.requestStatus ='New';
	$scope.departmentWisellocation.plannedOrAdhoc = 'Planned';
	
	
	if($cookieStore.get('roleId') == 6 || $cookieStore.get('roleId') == 7){
		$scope.dropdownDisable = true;
		$scope.departmentWisellocation.departmentId = $cookieStore.get('departmentId');
	}else{
		$scope.dropdownDisable = false;
	}
	
	

	if($cookieStore.get('roleId') == 7){
		$scope.readOnly = true;		
	}else if($cookieStore.get('roleId') == 6){
		$scope.readOnly = false;
	}

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
	    
	
	
	
	 $('#fromDate,#toDate,#workSkillFromDate,#workskillToDate').datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown',
	      minDate: $routeParams.id == 0 ? new Date() : ''	    	  
	    });
	 
	 $('#transactionDate').datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown'    	  
	    });
	 
	 
	 if($routeParams.id == 0){
		 $scope.transactionDate = $filter('date')(new Date(),"dd/MM/yyyy");		
	 }
	
	 
	 
	 $scope.list_workSkill = [{"id":"Skilled","name" : "Skilled" },
		                       {"id":"Semi Skilled","name" : "Semi Skilled" },
		                       {"id":"High Skilled","name" : "High Skilled" },
		                       {"id":"Special Skilled","name" : "Special Skilled" },
		                       {"id":"UnSkilled","name" : "UnSkilled" }];
	 
	
	
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
              } else if (type == 'customerList')
            {
                $scope.customerList = response.data.customerList;
                if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
		                $scope.departmentWisellocation.customerId=response.data.customerList[0].customerId;		                
		                $scope.customerChange();
		        } 
             // for button views
  			  	if($scope.buttonArray == undefined || $scope.buttonArray == '')
                $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Department Wise Worker Allocation'}, 'buttonsAction');
            }else if(type == 'customerChange'){	        		
        		$scope.companyList = response.data; 
        		
        		 if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
    	                $scope.departmentWisellocation.companyId = response.data[0].id;
    	                $scope.companyChange();
    	                }
        		 
        	}else if (type == 'departmentsAndLocationes') {        		
            	 $scope.countriesList = response.data.countriesList; 
                 $scope.locationsList = response.data.locationList;
                 $scope.departmentsList = response.data.departmentList;
                 $scope.departmentWisellocation.countryId = response.data.countriesList[0].id
               
            }else if(type == 'plantsList'){
            	$scope.plantsList = response.data;
            }else if(type == 'sectionesList'){
            	$scope.sectionesList = response.data;
            }else if(type == 'workAreasList'){
            	$scope.workAreasList = response.data;
            }else if(type == 'saveOrUpdateWorkerAllocationDetails'){
            	//alert(angular.toJson(response.data));
            	if(response.data.id == 0){
            		$scope.Messager('error', 'Error', 'Technical Issue occurred while saving. Please try again after some time');
            	}else{
            		$location.path('/departmentWiseWorkAllocationSearch');
            		
                	$scope.Messager('success', 'success', $scope.successMessage,buttonDisable);
            	}
            	
            	
            }else if(type == 'getDepartmentWiseWorkAllocationDetails'){
            		//alert(angular.toJson(response.data));
            	$scope.departmentWisellocation = response.data.workerAllocationDetails[0];
            	
            	if($scope.departmentWisellocation!= undefined && $scope.departmentWisellocation != '') {
            		
            	
            	if($scope.departmentWisellocation.requestStatus == 'New' || $scope.departmentWisellocation.requestStatus == 'Rejected'){
            		$scope.approvalButton = true;
            		if($cookieStore.get('roleId') == 6){
            			$scope.approvalLabelName = 'Send For Approval';
            			$scope.saveBtn = true;
            			$scope.resetBtn = true;
            		}else if($cookieStore.get('roleId') == 7){
            			$scope.approvalButton = false;
            			$scope.approvalLabelName = 'Approve';
            			$scope.rejectButton = false;
            			$scope.saveBtn = false;
            			$scope.resetBtn = false;
            		}
            		
            	}else if($scope.departmentWisellocation.requestStatus == 'Pending for Approval'){            		            		
            		if($cookieStore.get('roleId') == 6){
            			$scope.approvalButton = false;
            			$scope.saveBtn = false;
            			$scope.resetBtn = false;
            		}else if($cookieStore.get('roleId') == 7){
            			$scope.approvalButton = true;
            			$scope.approvalLabelName = 'Approve';
            			$scope.rejectButton = true;
            			$scope.saveBtn = false;
            			$scope.resetBtn = false;
            		}
            	}else if($scope.departmentWisellocation.requestStatus == 'Approved'){
            		$scope.approvalButton = false;
            		$scope.rejectButton = false;
            		$scope.saveBtn = false;
        			$scope.resetBtn = false;
            	}
        }	
            	$scope.locationChange();
            	$scope.plantChange();
            	$scope.sectionChange();
            	if(response.data.workerAllocationDetails != undefined && response.data.workerAllocationDetails != '' ){
            		$scope.workerAllocationReqList = response.data.workerAllocationDetails[0].workerAllocationRequirementList;
            	}
	            	$('#transactionDate').val($filter('date')( response.data.workerAllocationDetails[0].transactionDate, 'dd/MM/yyyy')); 
	            	$('#fromDate').val($filter('date')( response.data.workerAllocationDetails[0].fromDate, 'dd/MM/yyyy')); 
	            	$('#toDate').val($filter('date')( response.data.workerAllocationDetails[0].toDate, 'dd/MM/yyyy')); 
	            	
	            	$scope.departmentWisellocation.transactionDate = $filter('date')(response.data.workerAllocationDetails[0].transactionDate,"dd/MM/yyyy");
	            	$scope.departmentWisellocation.fromDate = $filter('date')(response.data.workerAllocationDetails[0].fromDate,"dd/MM/yyyy");
		    		$scope.departmentWisellocation.toDate = $filter('date')(response.data.workerAllocationDetails[0].toDate,"dd/MM/yyyy");          	
            	
            	}else if(type == 'currencyDetails'){
            		  $scope.defaultCurrency = response.data.defaultCurrencyName;            		 
            	}
            	//$scope.locationChange();
            	
        },
        function () {
        	//alert('Error') 
        	
        });
    }   

    $scope.getData('vendorController/getCustomerNamesAsDropDown.json', { customerId: $cookieStore.get('customerId')}, 'customerList');
    
    if(parseInt($routeParams.id) > 0){
    	 $scope.RequestType_List = [{"id":"New","name" : "New" },
    			                       {"id":"Pending for Approval","name" : "Pending for Approval" },
    			                       {"id":"Approved","name" : "Approved" },
    			                       {"id":"Rejected","name" : "Rejected" }];
    	 
        $scope.getData('departmentWiseWorkerAllocationController/getWorkerDetailsbyId.json', { workerAllocationId: $routeParams.id}, 'getDepartmentWiseWorkAllocationDetails');
    }else{
    	 $scope.RequestType_List = [{"id":"New","name" : "New" }];
    	$scope.approvalButton = true;
    	$scope.approvalLabelName = 'Send For Approval';
		$scope.rejectButton = false;
		$scope.saveBtn = true;
		$scope.resetBtn = true;
    }

    
    
    
   $scope.customerChange = function () {
		if($scope.departmentWisellocation.customerId != undefined && $scope.departmentWisellocation.customerId != ""){
			$scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.departmentWisellocation.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.departmentWisellocation.companyId != undefined ? $scope.departmentWisellocation.companyId : 0}, 'customerChange');
		}
	};
	    
	 $scope.companyChange = function () {
    	if($scope.departmentWisellocation.companyId != undefined && $scope.departmentWisellocation.companyId != ""){	    		
    		 $scope.getData('CompanyController/getLocationsByCompanyId.json', {customerId:$scope.departmentWisellocation.customerId,companyId:$scope.departmentWisellocation.companyId}, 'departmentsAndLocationes');
    		 $scope.getData('CompanyController/getCompanyProfileByCompanyId.json',{customerId: $scope.departmentWisellocation.customerId, companyId:$scope.departmentWisellocation.companyId }, 'currencyDetails')
    	}
	};
      
      
	    $scope.locationChange = function(){
	    	if($scope.departmentWisellocation.locationId != undefined && $scope.departmentWisellocation.locationId != null && $scope.departmentWisellocation.locationId != ''){
	    		$scope.getData('jobAllocationByVendorController/getPlantsList.json', {locationId:$scope.departmentWisellocation.locationId}, 'plantsList');
	    	}else{
	    		$scope.plantsList =[];
	    		$scope.sectionesList = [];
	    		$scope.workAreasList = [];
	    	}
	    }
	    
	    $scope.plantChange = function(){
	    	if($scope.departmentWisellocation.locationId != undefined && $scope.departmentWisellocation.locationId != null && $scope.departmentWisellocation.locationId != '' && $scope.departmentWisellocation.plantId != undefined && $scope.departmentWisellocation.plantId != null && $scope.departmentWisellocation.plantId != ''){
	    		$scope.getData('jobAllocationByVendorController/getAllSectionesByLocationAndPlant.json', {locationId:$scope.departmentWisellocation.locationId,plantId:$scope.departmentWisellocation.plantId}, 'sectionesList');
	    	}else{
	    		$scope.sectionesList = [];    		
	    	}
	    }
	    
	    $scope.sectionChange = function(){
	    	if($scope.departmentWisellocation.locationId != undefined && $scope.departmentWisellocation.locationId != null && $scope.departmentWisellocation.locationId != '' && $scope.departmentWisellocation.plantId != undefined && $scope.departmentWisellocation.plantId != null && $scope.departmentWisellocation.plantId != '' && $scope.departmentWisellocation.sectionId != undefined && $scope.departmentWisellocation.sectionId != null && $scope.departmentWisellocation.sectionId != ''){
	    		$scope.getData('jobAllocationByVendorController/getAllWorkAreasBySectionesAndLocationAndPlant.json', {locationId:$scope.departmentWisellocation.locationId,plantId:$scope.departmentWisellocation.plantId,sectionId:$scope.departmentWisellocation.sectionId}, 'workAreasList');
	    	}else{    	
	    		$scope.workAreasList = [];
	    	}
	    }
	      
	    
	    
	    $scope.saveDetails= function($event,approvalStatus){
	    	
	    	    	 
	    	if($('#deptWiseDtlsForm').valid()){
	    		
	    		 if(($('#fromDate').val() != undefined && $('#fromDate').val() != null) && ($('#toDate').val() != undefined && $('#toDate').val() != null) && (new Date(moment($('#toDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime() <= new Date(moment($('#fromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime())){
	       	    	$scope.Messager('error', 'Error', 'To Date Should not be lessthan From Date');
	       	    	return;
	     	  	}
	    	
	    		$scope.departmentWisellocation.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
	    		$scope.departmentWisellocation.fromDate = ($('#fromDate').val() != undefined && $('#fromDate').val() != '') ?  moment($('#fromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') : null;
	    		$scope.departmentWisellocation.toDate = ($('#toDate').val() != undefined && $('#toDate').val() != '') ?  moment($('#toDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') : null;
		    
	    		$scope.departmentWisellocation.createdBy = $cookieStore.get('createdBy'); 
   			 	$scope.departmentWisellocation.modifiedBy = $cookieStore.get('modifiedBy');
   				$scope.departmentWisellocation.workerAllocationRequirementList= $scope.workerAllocationReqList;
   				//alert(approvalStatus +":::====="+$cookieStore.get('roleId')); 
   				if(approvalStatus == 'approve' && $cookieStore.get('roleId') == 6){
   					$scope.departmentWisellocation.requestStatus = 'Pending for Approval';
   					$scope.successMessage = 'Application sent for Approval';
   				}else if(approvalStatus == 'approve' && $cookieStore.get('roleId') == 7){
   					$scope.departmentWisellocation.requestStatus = 'Approved';
   					$scope.successMessage = 'Application Approved Successfully';
   				}else if(approvalStatus == 'reject' && $cookieStore.get('roleId') == 7){
   					$scope.departmentWisellocation.requestStatus = 'Rejected';
   					$scope.successMessage = 'Application Rejected';
   				}else{
   					$scope.successMessage = 'Department Wise Work Allocation Details Saved Successfully';
   				}
   				
		   	 /*angular.forEach($scope.departmentWisellocation.workerAllocationRequirementList, function(value, key) {	 
		   			   		 
		   			$scope.departmentWisellocation.workerAllocationRequirementList[key].fromDate =moment(value.fromDate,'DD/MM/YYYY').format('YYYY-MM-DD');
		   			$scope.departmentWisellocation.workerAllocationRequirementList[key].toDate = moment(value.toDate,'DD/MM/YYYY').format('YYYY-MM-DD');	
		   			
		   	});*/
   			 
   				//alert(angular.toJson($scope.departmentWisellocation));
	    		$scope.getData('departmentWiseWorkerAllocationController/saveOrUpdateWorkerAllocationDetails.json', angular.toJson($scope.departmentWisellocation) , 'saveOrUpdateWorkerAllocationDetails',angular.element($event.currentTarget));
	    	}
	    	
	    }
	    
	     
	    
	    $scope.reset = function(){
	    	$scope.departmentWisellocation = new Object();
	    	$location.path('departmentWiseWorkAllocationEdit/0');
	    }
	    
	    
	    $scope.saveDepartmentWisellocationDetails = function(){   
	    	
	    	if($("#workSkillForm").valid()){	    		
	    		
	    	$scope.workSkillFromDateSample = moment($('#workSkillFromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
	    	$scope.workSkilltoDateSample = 	moment($('#workskillToDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
	    	
		    	if ($('#fromDate').val() == '' || $('#fromDate').val() == undefined) {
						alert("Select Actual From date");
						return;
					}else{
						$scope.fromDateSample = 	moment($('#fromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
					}
	    	
		    	if ($('#toDate').val() == '' || $('#toDate').val() == undefined) {
		    		alert("Select Actual From date");
					return;
				}else{
					$scope.toDateSample = 	moment($('#toDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
				}
	    	
		    	if (!moment($scope.workSkilltoDateSample).isSameOrAfter($scope.workSkillFromDateSample)) {
					alert(" To date should be greater than from date..");
					return;
				}
	    	
				if (!moment($scope.workSkillFromDateSample).isSameOrAfter($scope.fromDateSample)) {
					alert(" Work Skill From Date Should be Greaterthan or Equal to Actual From Date");
					return;
				}
				

				if (!moment($scope.toDateSample).isSameOrAfter($scope.workSkilltoDateSample)) {
					alert(" Work Skill To Date Should be Lessthan or Equal to Actual To Date");
					return;
				} 
	    		
	    	
	    		$scope.workerAllocationReqList.push({			    				
		    		'workSkill':$scope.workSkill.workSkill,
		    		'fromDate':$('#workSkillFromDate').val(),
		    		'toDate':$('#workskillToDate').val(),
		    		'noOfWorkers':$scope.workSkill.noOfWorkers,
		    		'rateOrDay':$scope.workSkill.rateOrDay		    		   		
		    	});  
			    	 $('div[id^="myModal"]').modal('hide');			    	
		    	}
	    	
	    }
	    
	    $scope.Edit = function($event){    	
	    	$scope.trIndex = $($event.target).parent().parent().index();	    	
	    	$scope.workSkill = $scope.workerAllocationReqList[$($event.target).parent().parent().index()];
	    	$scope.popUpSave = false;
	    	$scope.popUpUpdate =true;
	    }
	    
	    
	    $scope.updatedepartmentWisellocationDetails= function($event){
	    	
	    	
	    	$scope.workSkillFromDateSample = moment($('#workSkillFromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
	    	$scope.workSkilltoDateSample = 	moment($('#workskillToDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
	    	
		    	if ($('#fromDate').val() == '' || $('#fromDate').val() == undefined) {
						alert("Select Actual From date");
						return;
					}else{
						$scope.fromDateSample = 	moment($('#fromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
					}
	    	
		    	if ($('#toDate').val() == '' || $('#toDate').val() == undefined) {
		    		alert("Select Actual From date");
					return;
				}else{
					$scope.toDateSample = 	moment($('#toDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
				}
	    	
		    	if (!moment($scope.workSkilltoDateSample).isSameOrAfter($scope.workSkillFromDateSample)) {
					alert(" To date should be greater than from date..");
					return;
				}  
	    		
	    		

				if (!moment($scope.workSkillFromDateSample).isSameOrAfter($scope.fromDateSample)) {
					alert(" Work Skill From Date Should be Greaterthan or Equal to Actual From Date");
					return;
				}
				

				if (!moment($scope.toDateSample).isSameOrAfter($scope.workSkilltoDateSample)) {
					alert(" Work Skill To Date Should be Lessthan or Equal to Actual To Date");
					return;
				} 
	    	
	    	
	    	
	    	
	    	if($('#workSkillForm').valid()){
		    	$scope.workerAllocationReqList.splice($scope.trIndex,1); 		
			    	$scope.workerAllocationReqList.push({			    				
			    		'workSkill':$scope.workSkill.workSkill,
			    		'fromDate': $('#workSkillFromDate').val(),
			    		'toDate':$('#workskillToDate').val(),
			    		'noOfWorkers':$scope.workSkill.noOfWorkers,
			    		'rateOrDay':$scope.workSkill.rateOrDay		    		   		
			    	});   
			    	 $('div[id^="myModal"]').modal('hide');			    	
		    	}   	
	    	
	    }   
	    
	
    
    
  $scope.Delete = function($event){	
    	var r = confirm("Do you want to delete the "+$scope.workerAllocationReqList[$($event.target).parent().parent().index()].countryName);    	
    	if(r){
    		$scope.workerAllocationReqList.splice($($event.target).parent().parent().index(),1);
    	}   
    }
  
  $scope.plusIconAction = function(){
   	$scope.popUpSave = true;
  	$scope.popUpUpdate =false;
  	$scope.workSkill = new Object();  	
  	
  	$('#workSkillFromDate').val($('#fromDate').val());
  	$('#workskillToDate').val($('#toDate').val());

  }
     
  
}]
);



