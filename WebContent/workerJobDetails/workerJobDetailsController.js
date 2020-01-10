'use strict';

workerJobDetailsController.controller("workerJobDetailsAddViewDtls", ['$scope', '$rootScope', '$http', '$filter', '$resource','$location','$routeParams','$cookieStore', 'myservice','$window', function($scope,$rootScope, $http,$filter,$resource,$location,$routeParams,$cookieStore,myservice,$window) {
	
	$.material.init();
    
    /*$('#transactionDate,#workStartDate').bootstrapMaterialDatePicker({ 
    	time : false,
        Button : true,
        format : "DD/MM/YYYY",
        clearButton: true
    }); */
    $('#transactionDate').datepicker({
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
    
    $scope.list_status = [{id:"Y",name:"Active"},{id:"N",name:"InActive"}];
    
    $scope.list_jobTypes = [{id:"Full Time",name:"Full Time"}
    /* ,{id:"Part Time",name:"Part Time"},{id:"Permanent",name:"Permanent"},{id:"Temporary",name:"Temporary"}, {id:"ALL",name:"ALL"} */];
   
  
    
   
    $scope.list_workSkill = [{"id":"Skilled","name" : "Skilled" },
		                       {"id":"Semi Skilled","name" : "Semi Skilled" },
		                       {"id":"High Skilled","name" : "High Skilled" },
		                       {"id":"Special Skilled","name" : "Special Skilled" },
		                       {"id":"UnSkilled","name" : "UnSkilled" }];
    
   // $scope.list_reasonForChange = [{id:"Debarred",name:"Debarred"}];
    
    
    
  	 
  	 
    
    $scope.workJob = new Object();
    $scope.workerVo =  new Object();
    $scope.workJob.jobType = "Full Time";
  //  $scope.workJob.isActive = 'Y';
    $scope.reasonRequired = false;
    $scope.transactionDate = $filter('date')(new Date(),"dd/MM/yyyy");
    $scope.showActivateButton =  false;
	if($routeParams.id > 0){
		$scope.readonly = true;
		$scope.saveBtn = false;
		$scope.updateBtn = true;
		$scope.correctHistoryBtn = false;
		$scope.resetBtn = false;
		$scope.viewHistoryBtn = true;
		$scope.cancelBtn = false;
		$scope.returnTOSearchBtn = true;
		$scope.transactionList = false;
	}else{
		$scope.readonly = false;
		$scope.saveBtn = true;
		$scope.updateBtn = false;
		$scope.correctHistoryBtn = false;
		$scope.resetBtn = true;
		$scope.viewHistoryBtn = false;
		$scope.cancelBtn = false;
		$scope.returnTOSearchBtn = true;
		$scope.transactionList = false;		
	}
	
	$scope.fun_updateActionBtn = function(){
		$scope.readonly = false;
		$scope.saveBtn = true;
		$scope.updateBtn = false;
		$scope.correctHistoryBtn = false;
		$scope.resetBtn = false;
		$scope.viewHistoryBtn = false;
		$scope.cancelBtn = true;
		$scope.returnTOSearchBtn = true;
		$scope.transactionList = false;
		if($cookieStore.get("roleNamesArray").includes('Super Admin')  || $cookieStore.get("roleNamesArray").includes('Customer Audit Admin')){			
			$scope.showActivateButton = $scope.workerStatus;
		}else{
			$scope.showActivateButton =  false; 
		}
		
		$scope.transactionDate = $filter('date')(new Date(),"dd/MM/yyyy");
	}
	
	$scope.fun_viewHistoryBtn = function(){
		$scope.readonly = false;
		$scope.saveBtn = false;
		$scope.updateBtn = false;
		$scope.correctHistoryBtn = true;
		$scope.resetBtn = false;
		$scope.viewHistoryBtn = false;
		$scope.cancelBtn = false;
		$scope.returnTOSearchBtn = true;
		$scope.transactionList = true;	
		//$scope.showActivateButton = false;
		$scope.getPostData('workerJobDetailsController/getTransactionDatesOfHistory.json', {  customerId : $scope.workJob.customerDetailsId , companyId : $scope.workJob.companyDetailsId, vendorId : $scope.workJob.vendorDetailsId,workerId :   $scope.workJob.workerDetailsId }, 'transactionDatesChnage');
	    $('.dropdown-toggle').removeClass('disabled');
		
	}
	
	$scope.fun_CancelBtn = function(){
		$scope.readonly = true;
		$scope.saveBtn = false;
		$scope.updateBtn = true;
		$scope.correctHistoryBtn = false;
		$scope.resetBtn = false;
		$scope.viewHistoryBtn = true;
		$scope.cancelBtn = false;
		$scope.returnTOSearchBtn = true;
		$scope.transactionList = false;		
		//$scope.showActivateButton = false;
		$scope.getPostData("workerJobDetailsController/getMasterData.json", { workJobId : $routeParams.id == undefined ? 0 :  $routeParams.id } , "masterData");
	}
	
	
	
	
	
	$scope.getPostData = function (url, data, type, buttonDisable) {
        var req = {
            method: 'POST',
            url: ROOTURL + url,
            headers: {
                'Content-Type': 'application/json'
            },
            data: data
        }
        $http(req).then(function (response) {           
        	if(type == 'buttonsAction'){
        		$scope.buttonArray = response.data.screenButtonNamesArray;
        		$scope.workerTab = false;
       		 $scope.policeVerificationTab = false;
       		 $scope.familyTab = false;	
       		 $scope.medicalTab = false;
       		 $scope.verificationTab = false;		 
       		 $scope.jobDetailsTab = false;	
       		 $scope.badgeGenTab = false;		 
       		 
       		angular.forEach($scope.buttonArray, function(value, key){	
      		     if(value == 'WORKER DETAILS'){ 		    	
      		    	 $scope.workerTab = true;		    		
      		     }
	       		  if(value == 'FAMILY DETAILS'){ 		    	
	    		    	 $scope.familyTab = true;		    		
	    		     }
	       		 if(value == 'MEDICAL EXAMINATION'){ 		    	
	   		    	 $scope.medicalTab = true;		    		
	   		     }
	       		 if(value == 'POLICE VERIFICATION'){ 		    	
	   		    	 $scope.policeVerificationTab = true;		    		
	   		     }
	       		 if(value == 'VERIFICATION'){ 		    	
	   		    	 $scope.verificationTab = true;		    		
	   		     }
	       		 if(value == 'JOB DETAILS'){ 		    	
	   		    	 $scope.jobDetailsTab = true;		    		
	   		     }
	       		 if(value == 'BADGE GENERATION'){ 		    	
	   		    	 $scope.badgeGenTab = true;		    		
	   		     }
      		     
      		  });
       		
       		
        	} else if (type == 'masterData') {  
            	
            	$scope.list_customerList = response.data.customerList;
            	 
            	 
            	if(Object.prototype.toString.call(response.data.workJobList) === '[object Array]' &&  response.data.workJobList.length > 0 ){ 
            		//alert(angular.toJson(response.data));
            		$scope.workJob = response.data.workJobList[0];
            		//alert($scope.workJob.workAreaId);
            		$scope.workJob.locationId = response.data.workJobList[0].locationId;
            		//alert($scope.workJob.workOrderId);
            		$scope.transactionModel = response.data.workJobList[0].workJobDetailsId;
            		$scope.transactionDate = $filter('date')(response.data.workJobList[0].transactionDate,"dd/MM/yyyy");                   
                    $scope.list_companyList = response.data.companyList;	
                    $scope.list_vendorList = response.data.vendorList;
    	      		$scope.list_jobNames = response.data.jobCodes;
    	      		$scope.list_reportingManagers   = response.data.managers;	
    	      		$scope.list_cardType = response.data.cardTypes;
    	      		$scope.list_workOrders = response.data.workOrders;    
    	      		
                	$scope.list_workerDetails = response.data.jobDetailsVos;
                //	$scope.list_plant = response.data.plantList;
                //	$scope.list_section = response.data.sectionList;
                	/*$scope.list_department = response.data.departmentList;
                	$scope.list_location = response.data.locationList;  */              	
                	$scope.list_wageRate = response.data.wageRatesList;
                	$scope.getPostData('workerController/getWorkerChildScreensData.json', {workerId: $scope.workJob.workerDetailsId}, 'getWorkerChildScreensData') 
                	$scope.workerJobDetailsIds = response.data.workJobList[0].workJobDetailsId;
                	$scope.fun_locationChangeListener();
                	$scope.fun_plantChangeListener();
                	$scope.departmentChange();
                	$scope.fun_sectionChangeListener();
                	
                	
            	} else{
            		if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
    	                $scope.workJob.customerDetailsId=response.data.customerList[0].customerId;		                
    	                $scope.fun_CustomerChangeListener();
    	          }
            	}  
            	
            	
            	if($scope.list_workOrders != undefined && $scope.list_workOrders.length == 0){
            		 if($scope.workJob.companyDetailsId != undefined && $scope.workJob.companyDetailsId != ''){
            			   $scope.getPostData("CompanyController/getLocationsByCompanyId.json", { customerId : $scope.workJob.customerDetailsId , companyId : $scope.workJob.companyDetailsId } , "locationsDropDown");
            			   $scope.showLocationAndDeptDropDwn = true;
            			   $scope.showLocationAndDeptText = false;
	          		 }
	      		}else{
         			 $scope.showLocationAndDeptDropDwn = false;
          			 $scope.showLocationAndDeptText = true;
          		 }
            	
            	
            	$scope.countryList = response.data.countryList;            	
            	$scope.list_pfTypes = response.data.pfTypes;
            	$scope.list_skills = response.data.skills;  
            	
            	// for button views
            	if($scope.buttonArray == undefined || $scope.buttonArray == '')
            	$scope.getPostData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Worker Job Details'}, 'buttonsAction');
            	
            }else if(type == 'save'){
            	if($scope.saveBtn == true){           		
            		if(response.data.workJobId > 0){
            			$scope.Messager('success', 'success', 'Worker Job Details Saved Successfully',buttonDisable);            			
            			location.href = "#/workerBadgeGeneration/"+$scope.workerChildIds[0].workerBadgeId;           			
            		}
            	}else{
            		 $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
            	}
            }else if(type == 'saveAndProceed'){
            	if($scope.saveBtn == true){           		
            		if(response.data.workJobId > 0){
            			$scope.Messager('success', 'success', 'Worker Job Details Saved Successfully',buttonDisable);
            			location.href = "#/workerJobDetails/"+response.data.workJobId;             			
            		}
            	}else{
            		 $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
            	}
            }else if(type == 'companyDropDown'){
	      		$scope.list_companyList = response.data;	      		
	      		if(myservice.companyId != 0 && myservice.companyId != undefined){
	      			 $scope.workJob.companyDetailsId = myservice.companyId;
	      			$scope.fun_CompanyChangeListener();	      			
	      		}else if(response.data[0] != undefined && response.data[0] != "" && parseInt(response.data.length) == 1 ){	      			
	   	                $scope.workJob.companyDetailsId = response.data[0].id;	   	               
						$scope.fun_CompanyChangeListener();
	   	        }	      		 
	      	}else if(type == 'vendorDropDown'){      		
	      		$scope.list_vendorList = response.data.vendorList;
	      		//alert($cookieStore.get('verificationVendorId'));
	      		if($cookieStore.get('verificationVendorId') != undefined){
	      			$scope.workJob.vendorDetailsId = $cookieStore.get('verificationVendorId');
	      			$scope.fun_vendorChangeListener();
	      		}else if(myservice.vendorId != 0 && myservice.vendorId != undefined){
	      			 $scope.workJob.vendorDetailsId = myservice.vendorId;
	      			$scope.fun_vendorChangeListener();
	      		}else if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	   	                $scope.workJob.vendorDetailsId = response.data[0].id;
						$scope.fun_vendorChangeListener();
	   	         }
	      		$scope.list_jobNames = response.data.jobCodes;
	      		$scope.list_reportingManagers   = response.data.managers;	
	      		$scope.list_cardType = response.data.cardTypes;
	      		$scope.list_workOrders = response.data.workOrders;
	      		
	      		if($scope.list_workOrders != undefined && $scope.list_workOrders.length == 0){
	           		 if($scope.workJob.companyDetailsId != undefined && $scope.workJob.companyDetailsId != ''){
	           			   $scope.getPostData("CompanyController/getLocationsByCompanyId.json", { customerId : $scope.workJob.customerDetailsId , companyId : $scope.workJob.companyDetailsId } , "locationsDropDown");
	           			   $scope.showLocationAndDeptDropDwn = true;
	           			   $scope.showLocationAndDeptText = false;
	           		 }
	      		}else{
          			 $scope.showLocationAndDeptDropDwn = false;
      			   $scope.showLocationAndDeptText = true;
         		 }
	      		
	      	}else if(type == 'workerDropDown'){
	      		$scope.list_workerDetails = response.data;
	      		if($cookieStore.get('verificationworkerId') != undefined){
	      			$scope.workJob.workerDetailsId = $cookieStore.get('verificationworkerId') ;	      			
	      		}else if(myservice.workerId != 0 && myservice.workerId != undefined){
	      			 $scope.workJob.workerDetailsId = myservice.workerId;
	      		}else if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
   	                $scope.workJob.workerDetailsId = response.data[0].id;
	      		}
	      		$scope.getPostData('workerController/getWorkerChildScreensData.json', {workerId: $scope.workJob.workerDetailsId}, 'getWorkerChildScreensData') 
	      	}else if(type == 'wageRateList'){
	      		$scope.list_wageRate = response.data;
	      		if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
   	                $scope.workJob.wageRateId = response.data[0].id;
   	            }
	      	}else if(type == 'locationAndDepartment'){	      		
	      		$scope.workJob.departmentName = response.data.departmentName;
	      		$scope.workJob.departmentId = response.data.departmentId;
	      		$scope.workJob.locationId = response.data.locationId;
	      		$scope.workJob.locationName = response.data.locationName;
	      		$scope.fun_locationChangeListener();
	      		
	      	}else if(type == 'plant'){
            	//alert(angular.toJson(response.data));
            	$scope.list_plant = response.data;
            	
            
	      		//$scope.list_plant = response.data.plantList;
	      		
	      		/*if( response.data.plantList[0] != undefined && response.data.plantList[0] != "" && response.data.plantList.length == 1 ){
   	                $scope.workJob.plantId= response.data.plantList[0].id;
					$scope.fun_plantChangeListener();
   	            }*/
            	
            	if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
   	                $scope.workJob.plantId= response.data[0].id;
					$scope.fun_plantChangeListener();
   	            }
	      		
	      	}else if(type == 'sectionChange'){      		
	      		$scope.list_workArea = response.data.workAreaList;
	      		if( response.data.workAreaList != undefined && response.data.workAreaList != "" && response.data.workAreaList.length == 1 ){
	      			//alert(angular.toJson(response.data)+":::sectionChange");
   	                $scope.workJob.workAreaId = response.data.workAreaList[0].workAreaId;
   	            }
	      	}else if (type == 'transactionDatesChnage') {            
                $scope.transactionDatesList = response.data;
            }else if(type == 'getWorkerChildScreensData'){
            	$scope.workerChildIds = response.data;
            	$scope.workerInfoId = $scope.workerChildIds[0].workerInfoId;
            	
            }else if(type=='locationsDropDown'){
            	$scope.list_location = response.data.locationList;	 
	      		//$scope.list_department = response.data.departmentList;	
            }else if (type == 'departmentChange') {
            	//alert(angular.toJson(response.data));
            	   $scope.list_section = response.data.sectionList;
	        	   if( response.data.sectionList != undefined && response.data.sectionList != "" && response.data.sectionList.length == 1 ){
	   	                $scope.workJob.sectionId = response.data.sectionList[0].sectionDetailsId;
	   	             $scope.fun_sectionChangeListener();
	   	           }
	         	  	            	
	    	}else if (type == 'plantChange') {	    		
	    		 $scope.list_department = response.data.departmentList;	    		
	    		 if( response.data.departmentList != undefined && response.data.departmentList != "" && response.data.departmentList.length == 1 ){
	   	                $scope.workJob.departmentId = response.data.departmentList[0].departmentId;
	   	                
	   	           }
	        	
	      }else if(type == 'workerStatus'){            		
        		$scope.workerStatus = response.data;
        	       		
       	}else if(type == 'validateWorkerLimit'){
       		if(response.data.id == 0 ){		
        		$scope.Messager('error', 'Error', response.data.name,buttonDisable);
        	}else{
        		$scope.activateWorker(buttonDisable);
        	}
       	}else if(type == 'activateWorker'){
       		if(response.data){
       			$scope.Messager('success', 'success', 'Worker Activated Successfully',buttonDisable);
       			$scope.showActivateButton = false;
       		}else{
       			$scope.Messager('error', 'Error', 'Error in worker activation',buttonDisable);
       		}
       	}    
            
        },
        function () {
       	 $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
       	//alert('Error')         	
       })
	};
	
	
	//alert($cookieStore.get('WorkervendorId'));
	
    // GET MASTER DATA FOR  Job DETAILS SCREEN  		
	$scope.getPostData("workerJobDetailsController/getMasterData.json", { workJobId :( $routeParams.id == undefined ? 0 :  $routeParams.id), customerId: ($cookieStore.get('verificationCustomerId') != 0 && $cookieStore.get('verificationCustomerId') != undefined) ? $cookieStore.get('verificationCustomerId') : ($cookieStore.get('customerId') != undefined ? $cookieStore.get('customerId') : 0)} , "masterData");
	
	 $scope.workerVo.workerInfoId = $cookieStore.get('workerInfoId');       
     $scope.getPostData('workerController/checkWorkerStatus.json', angular.toJson($scope.workerVo), 'workerStatus');

     
     $scope.validateWorkerLimit = function ($event) {
     	
     	if($scope.workJob.vendorDetailsId != undefined && $scope.workJob.vendorDetailsId != ""){
     		$scope.getPostData('workerController/validateWorkerLimit.json', { customerId: $scope.workJob.customerDetailsId,companyId:($scope.workJob.companyDetailsId != undefined ? $scope.workJob.companyDetailsId :0),vendorId:($cookieStore.get('vendorId') != null && $cookieStore.get('vendorId') != "") ? $cookieStore.get('vendorId') : $scope.workJob.vendorDetailsId != undefined ? $scope.workJob.vendorDetailsId : 0,isActive: 'Y'}, 'validateWorkerLimit',angular.element($event.currentTarget));
     	}
     };
     
     
     $scope.activateWorker = function(buttonDisable){    	   
    	 $scope.workerVo.createdBy = $cookieStore.get('createdBy'); 
		  $scope.workerVo.modifiedBy = $cookieStore.get('modifiedBy');
		  if($scope.workerJobDetailsIds != undefined && $scope.workerJobDetailsIds >0){
			  
			  
			 $scope.getPostData('workerController/validateWorkerAndSchedulingShifts.json', angular.toJson($scope.workerVo), 'activateWorker',buttonDisable);
		  }else { 
			 $scope.Messager('error', 'Error', 'Save Job Details before activating worker',buttonDisable); 
 		  }
		 
		  
      }
     
     
     
	// vendor save logic
	$scope.fun_workJobSaveBtn = function($event,flag){ 	
		//console.log(angular.toJson($scope.workJob));
		//alert(angular.toJson($scope.workJob));
		 if($('#workerJobDetails').valid()){					
				$scope.workJob.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 					
		        //$scope.workJob.workStartDate = moment($('#workStartDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');									
		        $scope.workJob.createdBy = $cookieStore.get('createdBy'); 
		    	$scope.workJob.modifiedBy = $cookieStore.get('createdBy');		    			       
		        var savedata; 
		        //alert(angular.toJson($scope.workJob));
		        if($scope.correctHistoryBtn == true){		        	
		        	 $scope.workJob.workJobDetailsId = $scope.transactionModel;
		        	 savedata = angular.toJson($scope.workJob) ;			        	 		        	 
				}else{
					 $scope.workJob.workJobDetailsId = 0;
					 savedata = angular.toJson($scope.workJob) ;
					 
				}
		       
		        
		        if(flag){
		        	$scope.getPostData("workerJobDetailsController/saveOrUpdateWorkJobRecord.json", savedata , "save",angular.element($event.currentTarget));	
		        }else{
		        	$scope.getPostData("workerJobDetailsController/saveOrUpdateWorkJobRecord.json", savedata , "saveAndProceed",angular.element($event.currentTarget));
		        }
											            			
		}
	}
	
    // Customer Change Listener to get company details
    $scope.fun_CustomerChangeListener = function(){	
	   if($scope.workJob.customerDetailsId != null && $scope.workJob.customerDetailsId != undefined)
	   $scope.getPostData("vendorController/getCompanyNamesAsDropDown.json",  { customerId : $scope.workJob.customerDetailsId,companyId:($cookieStore.get('verificationCompanyId') != null && $cookieStore.get('verificationCompanyId') != "" && $cookieStore.get('verificationCompanyId') != undefined) ? $cookieStore.get('verificationCompanyId') : ($cookieStore.get('companyId') != 0 && $cookieStore.get('companyId') != undefined) ? $cookieStore.get('companyId') : $scope.workJob.companyDetailsId != undefined ? $scope.workJob.companyDetailsId : 0} , "companyDropDown");	   
    };
	   
	// Company Change Listener to get vendor details
    $scope.fun_CompanyChangeListener = function(){
	   if($scope.workJob.customerDetailsId != null && $scope.workJob.customerDetailsId != undefined && $scope.workJob.companyDetailsId != null && $scope.workJob.companyDetailsId != undefined)
	   $scope.getPostData("workerJobDetailsController/getVendorNamesAsDropDown.json", { customerId : $scope.workJob.customerDetailsId , companyId : $scope.workJob.companyDetailsId ,vendorId:($cookieStore.get('verificationVendorId') != null && $cookieStore.get('verificationVendorId') != "" && $cookieStore.get('verificationVendorId') != undefined) ? $cookieStore.get('verificationVendorId') : ($cookieStore.get('vendorId') != 0 && $cookieStore.get('vendorId') != undefined) ? $cookieStore.get('vendorId') : $scope.workJob.vendorDetailsId } , "vendorDropDown");		   
    };
	   
    // Vendor Change Listener to get worker details
    $scope.fun_vendorChangeListener = function(){
	   if($scope.workJob.customerDetailsId != null && $scope.workJob.customerDetailsId != undefined && $scope.workJob.companyDetailsId != null && $scope.workJob.companyDetailsId != undefined && $scope.workJob.vendorDetailsId != null && $scope.workJob.vendorDetailsId != undefined)
	   $scope.getPostData("workerJobDetailsController/getActiveAndNewWorkerNamesAsDropDown.json", { customerId : $scope.workJob.customerDetailsId , companyId : $scope.workJob.companyDetailsId, vendorId : $scope.workJob.vendorDetailsId } , "workerDropDown");		   
    };
    
   // Location Change Listener to get Departments and Plant  details
    $scope.fun_workOrderChangeListener = function(){
	   if($scope.workJob.workOrderId != null && $scope.workJob.workOrderId != undefined && $scope.workJob.workOrderId != '' ){
		   $scope.getPostData("workerJobDetailsController/getDeptAndPlant.json", { customerId : $scope.workJob.customerDetailsId , companyId : $scope.workJob.companyDetailsId, workOrderId : $scope.workJob.workOrderId } , "locationAndDepartment"); 
	   }
	   		   
    };
    
    $scope.fun_locationChangeListener = function(){
 	   if($scope.workJob.locationId != null && $scope.workJob.locationId != undefined && $scope.workJob.locationId != '' ){
 		  $scope.getPostData("workerJobDetailsController/PlantListByLocationId.json", { locationId : $scope.workJob.locationId } , "plant");
 	   }else{
			$scope.list_plant = [];
			$scope.list_department = [];
			$scope.list_section = [];
			$scope.list_workArea = [];
 	   }
 		   
 	  
     };
     
     $scope.fun_plantChangeListener = function(){
	   if($scope.workJob.plantId != null && $scope.workJob.plantId != undefined && $scope.workJob.plantId != '' ){
		   //$scope.getPostData("workerJobDetailsController/getDeptAndPlant.json", { customerId : $scope.workJob.customerDetailsId , companyId : $scope.workJob.companyDetailsId, locationId : $scope.workJob.locationId, plantId : $scope.workJob.plantId } , "section");
		   $scope.getPostData('associatingDepartmentToLocationPlantController/getDepartMentDetailsByLocationAndPlantId.json', { customerId : ($scope.workJob.customerDetailsId == undefined ||$scope.workJob.customerDetailsId == null )? '0' : $scope.workJob.customerDetailsId , companyId : ($scope.workJob.companyDetailsId == undefined || $scope.workJob.companyDetailsId == null) ? '0' : $scope.workJob.companyDetailsId  , locationId: $scope.workJob.locationId == undefined ? '' : $scope.workJob.locationId , plantId : $scope.workJob.plantId == undefined ? '' : $scope.workJob.plantId}, 'plantChange');			
	   }else{
		   $scope.list_department = [];
			$scope.list_section = [];
			$scope.list_workArea = [];
	   }
	   
	 };
	
	 
	$scope.departmentChange= function(){	
		//alert($scope.workJob.departmentId);
		if($scope.workJob.departmentId != undefined && $scope.workJob.departmentId != '' && $scope.workJob.departmentId != null){
			$scope.getPostData('sectionController/getSectionListBySearch.json', { customerId : ($scope.workJob.customerDetailsId == undefined ||$scope.workJob.customerDetailsId == null )? '0' : $scope.workJob.customerDetailsId , companyId : ($scope.workJob.companyDetailsId == undefined || $scope.workJob.companyDetailsId == null) ? '0' : $scope.workJob.companyDetailsId  , locationId: $scope.workJob.locationId == undefined ? '' : $scope.workJob.locationId , plantDetailsId : $scope.workJob.plantId == undefined ? '' : $scope.workJob.plantId,departmentId:$scope.workJob.departmentId}, 'departmentChange');
		}else{
			$scope.list_section = [];
			$scope.list_workArea = [];
		}
		
	};
 
	 
	 $scope.fun_sectionChangeListener = function(){
		if($scope.workJob.sectionId != null && $scope.workJob.sectionId != undefined && $scope.workJob.sectionId != ""){
			$scope.getPostData('WorkAreaController/getWorkAreaListBySearch.json', { customerId : ($scope.workJob.customerDetailsId == undefined ||$scope.workJob.customerDetailsId == null )? '0' : $scope.workJob.customerDetailsId , companyId : ($scope.workJob.companyDetailsId == undefined || $scope.workJob.companyDetailsId == null) ? '0' : $scope.workJob.companyDetailsId  , locationId: $scope.workJob.locationId == undefined ? '' : $scope.workJob.locationId , plantId : $scope.workJob.plantId == undefined ? '' : $scope.workJob.plantId, sectionDetailsId : $scope.workJob.sectionId == undefined ? '' : $scope.workJob.sectionId  , status : 'Y' }, 'sectionChange');
		}else{
			$scope.list_workArea = [];
		}
	};
	
	// Transaction Dates Change Listener
	$scope.fun_transactionDatesListChnage = function(){
		if($scope.transactionModel != undefined && $scope.transactionModel != '' && $scope.transactionModel> 0 )
			$scope.getPostData("workerJobDetailsController/getMasterData.json", { workJobId : $scope.transactionModel  } , "masterData");
	};
	
	/*$scope.fun_status_change = function(){
		if($scope.workJob.isActive == 'Y')
			$scope.reasonRequired = false;
		else
			$scope.reasonRequired = true;
	};*/
	
	$scope.fun_getWageRateList = function(){
		if($scope.workJob.customerDetailsId != undefined  && $scope.workJob.customerDetailsId != ""     && $scope.workJob.customerDetailsId != null
				&& $scope.workJob.companyDetailsId != undefined && $scope.workJob.companyDetailsId != "" && $scope.workJob.companyDetailsId != null
				&& $scope.workJob.vendorDetailsId != undefined  && $scope.workJob.vendorDetailsId != ""  && $scope.workJob.vendorDetailsId != null
				&& $scope.workJob.jobType != undefined   && $scope.workJob.jobType != ""   && $scope.workJob.jobType != null
				&& $scope.workJob.workSkill != undefined && $scope.workJob.workSkill != "" && $scope.workJob.workSkill != null
				&& (($scope.workJob.jobName != undefined   && $scope.workJob.jobName != ""  && $scope.workJob.jobName != null) || $scope.workJob.jobName == 0)){
			$scope.getPostData('workerJobDetailsController/getWageRateList.json', { customerId : $scope.workJob.customerDetailsId , companyId : $scope.workJob.companyDetailsId  , vendorId:  $scope.workJob.vendorDetailsId , jobName : $scope.workJob.jobName, workSkill : $scope.workJob.workSkill,   jobType :  $scope.workJob.jobType  }, 'wageRateList');

		}
	};
	
	
	 $scope.workerFamilyDetails = function($event){
		 if($scope.familyTab){
   	  if($scope.workerChildIds == undefined || $scope.workerChildIds[0].workerId == undefined || parseInt($scope.workerChildIds[0].workerId) == 0){
   		  $scope.Messager('error', 'Error', 'Enter worker Details, Then only you are allowed to enter Worker Family Details.',angular.element($event.currentTarget));
   	  }else if( $scope.workerChildIds != undefined && $scope.workerChildIds[0].workerInfoId != undefined){ 
   		  /*$cookieStore.put('verificationVendorId',$scope.workerVo.vendorId);
             $cookieStore.put('verificationworkerId',$scope.workerVo.workerId);*/
   		  location.href = "#/workerFamilyDetails/";
   	  }
		  }else{
    		  $scope.Messager('error', 'Error', 'You dont have permission to view/edit Family Details',true); 
    	  }
     };
     $scope.workerMedicalExamination = function($event){    
    	  if($scope.medicalTab){	 
      	  if($scope.workerChildIds == undefined || $scope.workerChildIds[0].workerId == undefined || parseInt($scope.workerChildIds[0].workerId) == 0){
      		  $scope.Messager('error', 'Error', 'Enter worker Details, Then only you are allowed to enter Worker Medical Examination Details.',angular.element($event.currentTarget));
      	  }else if( $scope.workerChildIds != undefined && $scope.workerChildIds[0].workerInfoId != undefined){   		  
      		  location.href = "#/workerMedicalExamination";
      	  }
    	  }else{
       		  $scope.Messager('error', 'Error', 'You dont have permission to view/edit Medical Examination Details',true); 
       	  }
        };
        
        $scope.policeVerification = function($event){ 
        	  if($scope.policeVerificationTab){
         	  if($scope.workerChildIds == undefined || $scope.workerChildIds[0].workerId == undefined || parseInt($scope.workerChildIds[0].workerId) == 0){
         		  $scope.Messager('error', 'Error', 'Enter worker Details, Then only you are allowed to enter Worker Medical Examination Details.',angular.element($event.currentTarget));
         	  }else if( $scope.workerChildIds != undefined && $scope.workerChildIds[0].workerInfoId != undefined){       		
         		  location.href = "#/workerPoliceVerification";
         	  }
        	  } else{
	    		  $scope.Messager('error', 'Error', 'You dont have permission to view/edit Police Verification Details',true); 
	    	  }
           };
	
	 $scope.workerVerification = function($event){	   
		 if($scope.verificationTab){
   	  if($scope.workerChildIds == undefined || $scope.workerChildIds[0].workerId == undefined || parseInt($scope.workerChildIds[0].workerId) == 0){
   		  $scope.Messager('error', 'Error', 'Enter worker Details, Then only you are allowed to enter Worker Verification Details.',angular.element($event.currentTarget));
   	  }else if( $scope.workerChildIds != undefined && $scope.workerChildIds[0].workerInfoId != undefined){    		 
   		  location.href = "#/workerVerification/"+$scope.workerChildIds[0].workerInfoId;
   	  }
   	  }else{
		  $scope.Messager('error', 'Error', 'You dont have permission to view/edit Verification Details',true); 
	  }
   	  
     };
     
     $scope.workerJobDetails= function($event){ 
   //	  alert(angular.toJson($scope.workerChildIds));
    	  if($scope.jobDetailsTab){
		   	  if($scope.workerChildIds == undefined || $scope.workerChildIds[0].workerId == undefined || parseInt($scope.workerChildIds[0].workerId) == 0){
		   		  $scope.Messager('error', 'Error', 'Enter worker Details, Then only you are allowed to enter Worker Job Details.',angular.element($event.currentTarget));
		   	  }else if( $scope.workerChildIds != undefined && $scope.workerChildIds[0].workJobDetailsId != undefined && parseInt($scope.workerChildIds[0].verificationId) > 0 && parseInt($scope.workerChildIds[0].workJobDetailsId) > 0){
		   		 location.href = "#/workerJobDetails/"+$scope.workerChildIds[0].workJobDetailsId;
		   	  }else if($scope.workerChildIds != undefined && $scope.workerChildIds[0].workJobDetailsId != undefined && parseInt($scope.workerChildIds[0].verificationId) > 0 && parseInt($scope.workerChildIds[0].workJobDetailsId) == 0){
		   		  location.href = "#/workerJobDetails/create";   		  
		   	  }else{
		   		  $scope.Messager('error', 'Error', 'Save Verification Details, Then only you are allowed to enter Worker Job Details.',angular.element($event.currentTarget));
		   	  }
   	   }else{
    		  $scope.Messager('error', 'Error', 'You dont have permission to view/edit Job Details',true); 
    	  }  
    	  
 	  };
 	  
 	  
 	 $scope.workerbadgeGeneration =function($event){
 		 if($scope.badgeGenTab){
		 		  if($scope.workerChildIds == undefined || $scope.workerChildIds[0].workerId == undefined || parseInt($scope.workerChildIds[0].workerId) == 0){
		   		  $scope.Messager('error', 'Error', 'Enter worker Details, Then only you are allowed to enter Worker Badge details.',angular.element($event.currentTarget));
		   	  }else if( $scope.workerChildIds != undefined && $scope.workerChildIds[0].workerBadgeId != undefined && parseInt($scope.workerChildIds[0].verificationId) > 0 && parseInt($scope.workerChildIds[0].workJobDetailsId) > 0 ){
		   		  location.href = "#/workerBadgeGeneration/"+$scope.workerChildIds[0].workerBadgeId;   	 
		   	  }else{
		   		  $scope.Messager('error', 'Error', 'Save Job Details, Then only you are allowed to enter Worker Badge Details.',angular.element($event.currentTarget));
		   	  }
 		 }else{
  			 $scope.Messager('error', 'Error', 'You dont have permission to view/edit Badge Details',true); 
  		 }	
    };
	
    
    $scope.callingUrl = function($event){
     	 if ($scope.badgeGenTab) {
 			$scope.workerbadgeGeneration($event);
 		}else{
 			 $scope.Messager('error', 'Error', 'You dont have permission to view/edit Badge Details',true); 
 		}
      };
	 
}]);
