'use strict';


workerBadgeGenerationController.controller("WorkerBadgeGenerationCtrl", ['$scope', '$http', '$resource', '$location','$routeParams','$cookieStore', function ($scope, $http, $resource, $location,$routeParams,$cookieStore) {  	
	
	
	$scope.list_CardTypes=[{"id":"Short Term",   "name":"Short Term"},
	                       {"id":"Long Term",    "name":"Long Term"},
						   {"id":"Housekeeping", "name":"Housekeeping"},
						   {"id":"Maintenance",  "name":"Maintenance"},
						   {"id":"Shutdown",     "name":"Shutdown"}];
	
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

    if ($routeParams.id > 0) {
    	$scope.saveBtn = false;
    	$scope.updateBtn = true; 
    }else{
    	$scope.saveBtn = true;
    	$scope.updateBtn = false;
    	//$scope.addDisabled = ( $cookieStore.get('verificationVendorId') != undefined && parseInt($cookieStore.get('verificationVendorId')) > 0  && $cookieStore.get('verificationworkerId') != undefined && parseInt($cookieStore.get('verificationworkerId'))  > 0) ? true : false;
    	//alert($scope.addDisabled);
    }
    
    $scope.addDisabled = true;
    
    $scope.getData = function (url, data, type, buttonDisable) {
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
        		
        		
        	} else if (type == 'masterData')
            {
            	
            	 
            	if ($routeParams.id > 0) {
	            	$scope.badge = response.data.badgeList[0];
	                $scope.companyList = response.data.companyList; 
	                $scope.vendorList = response.data.vendorList; 
	                $scope.countryList = response.data.countriesList; 
	                $scope.workerList = response.data.workerList;   
	                $scope.customerList = response.data.customerList;
	                if($scope.badge.workerDetailsId != undefined && $scope.badge.workerDetailsId != null && parseInt($scope.badge.workerDetailsId) > 0){
	                 	 $scope.getData('workerController/getWorkerChildScreensData.json', {workerId: $scope.badge.workerDetailsId}, 'getWorkerChildScreensData') 
	               }
	                
            	}else{
            		$scope.badge = new Object();
            		$scope.badge.ppeIssued = false;
                	//$scope.badge.isActive='Y';
            		$scope.customerList = response.data.customerList;
               	 if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
   		                $scope.badge.customerDetailsId=response.data.customerList[0].customerId;		                
   		                $scope.customerChange();
   		                }
            	}
            	// for button views
            	if($scope.buttonArray == undefined || $scope.buttonArray == '')
            	$scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Worker Badge Generation'}, 'buttonsAction');
            }else if(type == 'customerChange'){	        		
        		$scope.companyList = response.data; 
        		 if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
    	                $scope.badge.companyDetailsId = response.data[0].id;
    	                $scope.companyChange();
    	                }
        		 
        	}else if (type == 'companyChange') {       		
                $scope.vendorList = response.data.vendorList;             	        	
	   	        	$scope.badge.vendorDetailsId = $cookieStore.get('verificationVendorId') != undefined ? $cookieStore.get('verificationVendorId') : 0;	   	        	
	   	        	$scope.vendorChange();
	   	        
                $scope.countryList = response.data.countriesList;
                $scope.badge.country = response.data.countriesList[0].id;
            }else if (type == 'vendorChange') {       		
                $scope.workerList = response.data;  
                $scope.badge.workerDetailsId = $cookieStore.get('verificationworkerId') != undefined ? $cookieStore.get('verificationworkerId') : 0;
                if($scope.badge.workerDetailsId != undefined && $scope.badge.workerDetailsId != null && parseInt($scope.badge.workerDetailsId) > 0){
                 	 $scope.getData('workerController/getWorkerChildScreensData.json', {workerId: $scope.badge.workerDetailsId}, 'getWorkerChildScreensData') 
               }
                
            }else if(type=='search'){
                $scope.result = response.data;
            }else if(type=='savebadge'){
            	if($scope.saveBtn == true)
            		$scope.Messager('success', 'success', 'Worker Badge Saved Successfully',buttonDisable);
            	else{
            		$scope.Messager('success', 'success', 'Worker Badge Updated Successfully',buttonDisable);
            	}
            }else if(type == 'getWorkerChildScreensData'){
            	$scope.workerChildIds = response.data;
            	$scope.workerInfoId = $scope.workerChildIds[0].workerInfoId;
            } else if(type == 'validateBadgeCode'){           	
            	//if($scope.badge.workerBadgeId != undefined && parseInt($scope.badge.workerBadgeId) >! 0){
            		if(!response.data){
                		$scope.Messager('error', 'Error', 'Badge Code Already Exists');
                	}else{
                		$scope.save(buttonDisable);
                	//}
            	}
            	
            }
        },
        function () {
        	//alert('Error') 
        	
        });
    }   

    $scope.getData('workerBadgeGenerationController/getWorkerBadgeRecordByBadgeId.json', { workerBadgeId : $routeParams.id,customerDetailsId:$cookieStore.get('verificationCustomerId') } , 'masterData');
    
    $scope.customerChange = function () { 	
    	
	    	if($scope.badge.customerDetailsId != undefined && $scope.badge.customerDetailsId != ""){
	    	$scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.badge.customerDetailsId, companyId:($cookieStore.get('verificationCompanyId') != null && $cookieStore.get('verificationCompanyId') != "" && $cookieStore.get('verificationCompanyId') != undefined) ? $cookieStore.get('verificationCompanyId') : $scope.companyDetailsId != undefined ? $scope.companyDetailsId : 0}, 'customerChange');
	    	}
	    };
	    
	    
	    
    $scope.companyChange = function () {    
    	if($scope.badge.companyDetailsId != undefined && $scope.badge.companyDetailsId != ""){
    		$scope.getData('workerController/getVendorAndWorkerNamesAsDropDowns.json', { customerId: $scope.badge.customerDetailsId,companyId: $scope.badge.companyDetailsId,vendorId:($cookieStore.get('verificationVendorId') != null && $cookieStore.get('verificationVendorId') != "") ? $cookieStore.get('verificationVendorId') : $scope.badge.vendorDetailsId != undefined ? $scope.badge.vendorDetailsId : 0 }, 'companyChange');
    	}
    };
  	$scope.vendorChange = function(){  		
		  if($scope.badge.vendorDetailsId != undefined && $scope.badge.vendorDetailsId != "")
	        $scope.getData('workerBadgeGenerationController/getWorkersByVendorId.json', { customerDetailsId: $scope.badge.customerDetailsId == undefined ? 0 :  $scope.badge.customerDetailsId , companyDetailsId: $scope.badge.companyDetailsId == undefined ? 0 :  $scope.badge.companyDetailsId, vendorDetailsId: $scope.badge.vendorDetailsId == undefined ? 0 :  $scope.badge.vendorDetailsId }, 'vendorChange');
	  }
      
      
    $scope.search = function () {
        $scope.getData('workerController/workerGridDetails.json', { customerDetailsId: $scope.badge.customerDetailsId == undefined ? 0 :  $scope.badge.customerDetailsId , companyDetailsId: $scope.badge.companyDetailsId == undefined ? 0 :  $scope.badge.companyDetailsId, vendorDetailsId: $scope.badge.vendorDetailsId == undefined ? 0 :  $scope.badge.vendorDetailsId, workerDetailsId : $scope.badge.workerDetailsId  == undefined ? 0 :  $scope.badge.workerDetailsId , isActive: $scope.badge.status }, 'search');
    };
    
    $scope.validateWorkerBadgeCode = function ($event) {
    	$scope.getData('workerBadgeGenerationController/validateVendorBadgeCode.json', angular.toJson($scope.badge), 'validateBadgeCode',angular.element($event.currentTarget));	
    }
    
    
    $scope.save = function ($event) {
    	if($('#badgeForm').valid()){
    	  $scope.badge.createdBy = $cookieStore.get('createdBy'); 
	 	  $scope.badge.modifiedBy = $cookieStore.get('modifiedBy');    
	 	 if($scope.badge.ppeIssued == null || $scope.badge.ppeIssued == undefined)
	 		$scope.badge.ppeIssued = false;    	  
	       $scope.getData('workerBadgeGenerationController/saveOrUpdateWorkerBadge.json', angular.toJson($scope.badge), 'savebadge',angular.element($event.currentTarget));	
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
    	   if($scope.jobDetailsTab){
     	  if($scope.workerChildIds == undefined || $scope.workerChildIds[0].workerId == undefined || parseInt($scope.workerChildIds[0].workerId) == 0){
     		  $scope.Messager('error', 'Error', 'Enter worker Details, Then only you are allowed to enter Worker Job Details.');
     	  }else if( $scope.workerChildIds != undefined && $scope.workerChildIds[0].workJobDetailsId != undefined && parseInt($scope.workerChildIds[0].verificationId) > 0 && parseInt($scope.workerChildIds[0].workJobDetailsId) > 0){
     		 location.href = "#/workerJobDetails/"+$scope.workerChildIds[0].workJobDetailsId;
     	  }else if($scope.workerChildIds != undefined && $scope.workerChildIds[0].workJobDetailsId != undefined && parseInt($scope.workerChildIds[0].verificationId) > 0 && parseInt($scope.workerChildIds[0].workJobDetailsId) == 0){
     		  location.href = "#/workerJobDetails/create";   		  
     	  }else{
     		  $scope.Messager('error', 'Error', 'Enter Verification Details, Then only you are allowed to enter Worker Job Details.',angular.element($event.currentTarget));
     	  }
	   }else{
    		  $scope.Messager('error', 'Error', 'You dont have permission to view/edit Job Details',true); 
    	  }  
   	  };
   	  
   	  
   	 $scope.workerbadgeGeneration =function($event){
   		 if($scope.badgeGenTab){
   		  if($scope.workerChildIds == undefined || $scope.workerChildIds[0].workerId == undefined || parseInt($scope.workerChildIds[0].workerId) == 0){
     		  $scope.Messager('error', 'Error', 'Enter worker Details, Then only you are allowed to enter Worker Badge details.');
     	  }else if( $scope.workerChildIds != undefined && $scope.workerChildIds[0].workerBadgeId != undefined && parseInt($scope.workerChildIds[0].verificationId) > 0 && parseInt($scope.workerChildIds[0].workJobDetailsId) > 0 ){
     		  location.href = "#/workerBadgeGeneration/"+$scope.workerChildIds[0].workerBadgeId;   	 
     	  }else{
     		  $scope.Messager('error', 'Error', 'Enter Verification Details and Job Details, Then only you are allowed to enter Worker Badge Details.',angular.element($event.currentTarget));
     	  }
   		  
   		 }else{
  			 $scope.Messager('error', 'Error', 'You dont have permission to view/edit Badge Details',true); 
  		 }	 
      };
    
      
    
    
}]
)/*.directive('onLastRepeat', function () {
    return function (scope, element, attrs) {
        if (scope.$last) {
            setTimeout(function () {
                $('.table').DataTable();
            }, 1);
        }
    };
})*/;



