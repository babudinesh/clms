'use strict';
var workerMedicalExaminationController = angular.module("myApp.workerMedicalExamination", []);

workerMedicalExaminationController.directive('fileVerifyexamin', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileVerifyexamin);
            var modelSetter = model.assign;

            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);                   
                });
            });
        }
    };
}]);

workerMedicalExaminationController.controller("workerMedicalExaminationController", ['$scope', '$rootScope', '$http', '$filter', '$resource','$location','$routeParams','$cookieStore', 'myservice','$window', function($scope,$rootScope, $http,$filter,$resource,$location,$routeParams,$cookieStore,myservice,$window) {
	
	$.material.init();
    
    $('#reviewDate').datepicker({
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
    
    
    $scope.heightList = [{ "id":"Cms", "name":"Cms"},
 		                            { "id":"Inches", "name":"Inches"}
 	                            ];
    
    
    
    $scope.weightList = [{ "id":"Kgs", "name":"Kgs"},{ "id":"Pounds", "name":"Pounds"}];
 
    
    $scope.workerMedicalExamination = new Object();
    $scope.workerVo =  new Object();  

    $scope.reasonRequired = false;
    $scope.transactionDate = $filter('date')(new Date(),"dd/MM/yyyy");
    $scope.showActivateButton =  false;
	

	
	
	
	
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
        	//	alert(angular.toJson($scope.buttonArray));
        	} else if (type == 'masterData') {  
            	//alert(angular.toJson(response.data));
            	
            	$scope.workerInfoId = $cookieStore.get('workerInfoId');	
            	$scope.customerName = $cookieStore.get('WorkercustomerName');
        		$scope.companyName = $cookieStore.get('WorkercompanyName');
        		$scope.countryName = $cookieStore.get('WorkercountryName');            		
        		$scope.vendorName = $cookieStore.get('WorkervendorName');
        		$scope.firstName = $cookieStore.get('workerName');
        		$scope.workerCode = $cookieStore.get('workerCode');
            	
            	if(response.data.workerMedicalExaminationDetails != undefined && response.data.workerMedicalExaminationDetails.length >0){
            		$scope.workerMedicalExamination = response.data.workerMedicalExaminationDetails[0]; 
            		$scope.reviewDate = $filter('date')($scope.workerMedicalExamination.reviewDate,"dd/MM/yyyy");             		
                  	 
            	}else{
            		$scope.workerMedicalExamination = new Object();             	          		
            		$scope.workerMedicalExamination.customerId = $cookieStore.get('WorkercustomerId');
            		$scope.workerMedicalExamination.companyId = $cookieStore.get('WorkercompanyId');            		
            		$scope.workerMedicalExamination.vendorId = $cookieStore.get('WorkervendorId');
            		$scope.workerMedicalExamination.workerId = $cookieStore.get('workerId');
            		$scope.workerMedicalExamination.countryId = $cookieStore.get('WorkerCountryId');
            		$scope.workerMedicalExamination.medicallyFit = 'Medically Fit';
            	}
            	$scope.getPostData('workerController/getWorkerChildScreensData.json', {workerId: $scope.workerMedicalExamination.workerId}, 'getWorkerChildScreensData') ;
            	
            	// for button views
            	if($scope.buttonArray == undefined || $scope.buttonArray == '')
            	$scope.getPostData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Worker Medical Examination'}, 'buttonsAction'); 
                
            	
            } else if(type == 'getWorkerChildScreensData'){
            	$scope.workerChildIds = response.data;
            	
            }   
            
        },
        function () {
       	 $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);        	
       })
	};
	
		
	$scope.getPostData("workerMedicalExaminationController/getWorkerMedicalExaminationDetailsByWorkerId.json", {workerId: $cookieStore.get('workerId') != undefined ? $cookieStore.get('workerId') :0} , "masterData");
	
	

	
	
	$scope.fun_Save = function($event,flag){
		if($('#workerMedicalExaminationForm').valid()){
		//alert($scope.workerMedicalExamination.customerId);		
		$scope.workerMedicalExamination.createdBy = $cookieStore.get('createdBy'); 
	    $scope.workerMedicalExamination.modifiedBy = $cookieStore.get('createdBy');
	    $scope.workerMedicalExamination.modifiedDate = null;
	    $scope.workerMedicalExamination.createdDate = null;
	    $scope.workerMedicalExamination.reviewDate = moment($('#reviewDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');	   
	    var formData = new FormData();
	    
	    formData.append("file", $scope.theFile);
	    formData.append("name",angular.toJson($scope.workerMedicalExamination)); 
	   // alert(angular.toJson($scope.workerMedicalExamination));
   	 $http.post('workerMedicalExaminationController/saveOrUpdateWorkerMedicalExaminationDetails.json', formData, {
           transformRequest: angular.identity,
           headers: {'Content-Type': undefined}
       })
       .success(function(response){
       	if(response.medicalExaminationId > 0 ){
       		$scope.Messager('success', 'Success', 'Worker Details Saved Successfully',angular.element($event.currentTarget));
       		if(flag && $scope.policeVerificationTab){
       			location.href = "#/workerPoliceVerification";
       		}else{
       			$scope.getPostData("workerMedicalExaminationController/getWorkerMedicalExaminationDetailsByWorkerId.json", {workerId: $cookieStore.get('workerId') != undefined ? $cookieStore.get('workerId') :0} , "masterData");
       		}
       		
    		        	 	           	
       	}else{
       		$scope.Messager('error', 'Error', 'Technical issue occurred. Please try again after some time...');   
       	}
      	 
       })
       .error(function(){
      	 	$scope.Messager('error', 'Error', 'File Upload Failed',angular.element($event.currentTarget));
       });
   	 
		}else{
			$scope.Messager('error', 'Error', 'Enter the fileds Marked With * ',angular.element($event.currentTarget));
		}
	}
	
	 $scope.getFileDetails = function (e) {  
	        $scope.$apply(function () {
	             $scope.theFile = e.files[0];
	        });
	    };
	    
	    
	    
	 // To download the file
	      $scope.fun_download = function(event,fileName,attachment){
	    	 
	    	  if(attachment instanceof Object){
		    	  var file = document.getElementById("fileName").files[0];
		    	  if (file) {
		    	      var reader = new FileReader();
		    	      reader.readAsArrayBuffer(attachment);
		    	      reader.onload = function (evt) {
		    	    	  var blob = new Blob([evt.target.result], {type: 'application/octet-stream'});
		  			      saveAs(blob,fileName);
		    	      }
		    	      reader.onerror = function (evt) {
		    	    	  alert("Failed to download. Please try again ");
		    	      }
		    	  }
	    	  }else{
	    		  
	    		 // alert(event+":::"+fileName);
	    		  $http({
	    			    url: ROOTURL +'vendorComplianceController/downloadfile.view',
	    			    method: 'POST',
	    			    data : { 'path': event } ,
	    			    responseType: 'arraybuffer',
	    			    //this is your json data string
	    			    headers: {
	    			        'Content-type': 'application/json',
	    			        'Accept': '*'
	    			    }
	    			}).success(function(data){
	    			  // alert("success");
	    			   var blob = new Blob([data], {
	    			        type: 'application/pdf'
	    			    });
	    			  //alert(blob);
	    			    saveAs(blob,fileName);
	    			    $("#loader").hide();
	                  $("#data_container").show();
	                  
	    			}).error(function(){	
	    				alert("Failed to download. Please try Again");
	    				
	    			});
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
   		  	 //$cookieStore.put('verificationVendorId',$scope.workerVo.vendorId);
            // $cookieStore.put('verificationworkerId',$scope.workerVo.workerId);
   		  location.href = "#/workerMedicalExamination";
   	  }
    	 }else{
      		  $scope.Messager('error', 'Error', 'You dont have permission to view/edit Medical Examination Details',true); 
      	  } 
     };
     
     $scope.workerPoliceVerification = function($event){     	
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
   	 if ($scope.policeVerificationTab) {
			 $scope.workerPoliceVerification($event);
		} else if ($scope.verificationTab) {
			 $scope.workerVerification($event);
		} else if ($scope.jobDetailsTab) {			
			$scope.workerJobDetails($event);   		  
		} else if ($scope.badgeGenTab) {
			$scope.workerbadgeGeneration($event);
		}else{
			 $scope.Messager('error', 'Error', 'You dont have permission to view/edit Next Tabs',true); 
		}
    };
	
	 
}]);
