'use strict';



workerDetailsControllers.controller('workerFamilyDetailsCtrl', ['$scope','$http', '$resource','$routeParams','$filter','$cookieStore', function ($scope,$http, $resource, $routeParams,$filter,$cookieStore) {
	 	$.material.init();
		
		$('#transactionDate').datepicker({
		      changeMonth: true,
		      changeYear: true,
		      dateFormat:"dd/mm/yy",
		      showAnim: 'slideDown'
		    	  
		    });
		
		
		$('#dateOfBirth').datepicker({
		      changeMonth: true,
		      changeYear: true,
		      dateFormat:"dd/mm/yy",
		      showAnim: 'slideDown',
		      maxDate : new Date(),
		      yearRange: "-100:+0"
		    	  
		    });
	 
	   
	    $scope.popUpSave = true;
    	$scope.popUpUpdate =false;
    	
    	/* if($cookieStore.get('roleNamesArray').includes("Vendor Admin")){ 
			  $scope.buttonName = "Save";
		 }else{
  		  $scope.buttonName = "Proceed"
		 }*/
    	
	   $scope.workerNomine = new Object();
	   $scope.nomineeList = [];
	    
	   
	   $scope.workerCode   = $cookieStore.get('workerCode');
	   $scope.workerName   = $cookieStore.get('workerName');
	   $scope.countryName  = $cookieStore.get('WorkercountryName');
	   $scope.customerName = $cookieStore.get('WorkercustomerName');
	   $scope.companyName  = $cookieStore.get('WorkercompanyName');
	   $scope.vendorName   = $cookieStore.get('WorkervendorName');
	   $scope.workerNomine.workerId = $cookieStore.get('workerId');
	   $scope.workerNomine.customerId = $cookieStore.get('WorkercustomerId');
	   $scope.workerNomine.companyId = $cookieStore.get('WorkercompanyId');
	   $scope.workerInfoId = $cookieStore.get('workerInfoId');	   
	   $scope.workerNomine.createdBy = $cookieStore.get('createdBy'); 
  	   $scope.workerNomine.modifiedBy = $cookieStore.get('modifiedBy');	 
  	   
  	 //alert($cookieStore.get('workerCode'));
  	 
  	 
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
	    
	    
	    
	    $scope.getData = function (url, data, type,buttonDisable) {
	        var req = {
	            method: 'POST',
	            url:ROOTURL+url,
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
	        		
	        		
	        	} else if(type == 'saveNomineeDetails'){
	            	//alert(angular.toJson(response.data));
	            	 $scope.Messager('success', 'success', 'Worker Nominee Details Saved Successfully',buttonDisable);
	            	 $scope.getData('workerController/getNomineeGridData.json', {customerId:$scope.workerNomine.customerId,companyId:$scope.workerNomine.companyId,workerId:$scope.workerNomine.workerId}, 'getNomineeGridta');
	            	 $scope.clearPopupValues();
	            }else if(type == 'Delete'){
	            	//alert(angular.toJson(response.data));
	            	if(response.data == 0){
	            		 $scope.Messager('success', 'success', 'Record Deleted Successfully',buttonDisable);
	            	}else{
	            		$scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
	            	}
	            	
	            }else if(type == 'getNomineeGridta'){
	            	//alert(angular.toJson(response.data));
	            	$scope.nomineeList = response.data.NomineeGridData;
	            	//alert(angular.toJson($scope.nomineeList[0].dateOfBirth));
	            }else if(type == 'getWorkerChildScreensData'){
	            	$scope.workerChildIds = response.data;
	            	
	            }else if(type == 'getWorkerAddress'){
	            	if(response.data != null && response.data.id == 1){
	            		//$scope.Messager('error', 'Error', response.data.name);
	            	}else{
	            		$scope.workerAddress = response.data.name;
	            	}
	            	
	            	
	            }else if(type == 'getWorkerId'){
	            	//alert(angular.toJson(response.data));
	            	if(response.data != null && parseInt(response.data.id) >0){	   
	            		//alert($scope.workerNomine.workerId+":asdfsad:");
	            		$cookieStore.put('workerId',response.data.id);
	            		$scope.workerNomine.workerId = response.data.id;
	            		 $scope.getData('workerController/getWorkerChildScreensData.json', {workerId: $scope.workerNomine.workerId}, 'getWorkerChildScreensData') ;
	                   	 
	                   	 $scope.getData('workerController/getWorkerAddressByWorkerId.json', {workerId: $scope.workerNomine.workerId}, 'getWorkerAddress') ;
	            	}
	            	
	            }else if(type == 'workerStatus'){            		
	        		$scope.workerStatus = response.data;	        		
	          		 if($scope.workerStatus && ($cookieStore.get("roleNamesArray").includes('Super Admin')  || $cookieStore.get("roleNamesArray").includes('Company Admin'))){
	          			 	$scope.showProceedButton = true;
	          		     }else{
	          		    	 $scope.showProceedButton = false;
	          		     }	
	          		 // for button views
	             	if($scope.buttonArray == undefined || $scope.buttonArray == '')
	             	$scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Worker Family Details'}, 'buttonsAction');
	          	}
	        	},
	        function () {
	        	  $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
	        	           
	          });
	    	}      
	    
	    if($scope.workerNomine.workerId != undefined && $scope.workerNomine.workerId == ''){
    		$scope.getData('workerController/getWorkerId.json', {workerCode: $cookieStore.get('workerCode')}, 'getWorkerId') ;
    	}
	    
	    
	    
	    if($scope.workerNomine.customerId != "" && $scope.workerNomine.companyId != "" && $scope.workerNomine.workerId){	   
	    	$scope.getData('workerController/getNomineeGridData.json', {customerId:$scope.workerNomine.customerId,companyId:$scope.workerNomine.companyId,workerId:$scope.workerNomine.workerId}, 'getNomineeGridta'); 
	    }/*else{
	    	 $scope.Messager('error', 'Error', 'Enter Worker Details');
	    }*/
	    
	   
	    if($scope.workerNomine.workerId != undefined && $scope.workerNomine.workerId != null && parseInt($scope.workerNomine.workerId) > 0){	    	
       	 $scope.getData('workerController/getWorkerChildScreensData.json', {workerId: $scope.workerNomine.workerId}, 'getWorkerChildScreensData') ;
       	 
       	 $scope.getData('workerController/getWorkerAddressByWorkerId.json', {workerId: $scope.workerNomine.workerId}, 'getWorkerAddress') ;
       	 
       	}
	    
	   
	    	$scope.workrVo = new Object();
	       $scope.workrVo.workerInfoId = $cookieStore.get('workerInfoId');       
	       $scope.getData('workerController/checkWorkerStatus.json', angular.toJson($scope.workrVo), 'workerStatus');
	 
	    
	    
     
	    $scope.saveNomineeDetails = function($event){ 
	    	
	    	if($('#identificationTypeForm').valid()){   		
	    	   		
	    		if($('#dateOfBirth').val() != undefined && $('#dateOfBirth').val() != "" && $('#dateOfBirth').val() != 'Invalid date' && (($scope.workerNomine.ageYears != undefined && $scope.workerNomine.ageYears != "" && $scope.workerNomine.ageYears != null) || ( $scope.workerNomine.ageMonths != undefined && $scope.workerNomine.ageMonths != "" && $scope.workerNomine.ageMonths != null))){
	    			$scope.Messager('error', 'Error', 'Enter Either Date Of Birth or Age, Both Should Not Be Entered');
	    			return ;
	    		}else if(parseInt($scope.workerNomine.ageYears) == 0){
	    			$scope.Messager('error', 'Error', 'Year and month should not be zero');
	    			return ;
	    		}else if(($('#dateOfBirth').val() == undefined || $('#dateOfBirth').val() == "")  && ($scope.workerNomine.ageYears == undefined || $scope.workerNomine.ageYears == "")){
	    			$scope.Messager('error', 'Error', 'Enter Either Date Of Birth or Age');
	    			return ;
	    		}
	    		
		    	$scope.nomineeList.push({	    		   		
		    		'memberName':$scope.workerNomine.memberName,
		    		'relationship':$scope.workerNomine.relationship,
		    		'dateOfBirth':$('#dateOfBirth').val() != undefined && $('#dateOfBirth').val() != "" ? moment($('#dateOfBirth').val(),'DD/MM/YYYY').format('YYYY-MM-DD') :"",
		    		'phoneNumber':$scope.workerNomine.phoneNumber,
		    		'percentageShareInPF':$scope.workerNomine.percentageShareInPF,
		    		'workerNomineeId':$scope.workerNomine.workerNomineeId,
		    		'gender':$scope.workerNomine.gender,
		    		'address':$scope.workerNomine.address,
		    		'guardianDetails':$scope.workerNomine.guardianDetails,
		    		'workerId':$scope.workerNomine.workerId ,
		 	   		'customerId':$scope.workerNomine.customerId,
		 	   		'companyId':$scope.workerNomine.companyId,
		 	   		'createdBy':$scope.workerNomine.createdBy,
		 	   		'modifiedBy':$scope.workerNomine.modifiedBy,
		 	   		'years':$scope.workerNomine.ageYears,
		 	   		'months':$scope.workerNomine.ageMonths
		    	}); 
		    	
		    	if($('#dateOfBirth').val() != undefined && $('#dateOfBirth').val() != "" && $('#dateOfBirth').val() != 'Invalid date'){
		    		$scope.workerNomine.dateOfBirth =   moment($('#dateOfBirth').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
		    	}
		    	
		    	//alert(angular.toJson($scope.workerNomine));
		    	 $scope.getData('workerController/saveNomineeDetails.json', angular.toJson($scope.workerNomine), 'saveNomineeDetails',angular.element($event.currentTarget));
		    	 $('div[id^="identificationType"]').modal('hide');
	    	
	    	}
	    
	       }
	    
	    
	    
	    
	    $scope.Edit = function($event){    	
	    	//alert('1');
	    	$scope.workerNomine = $scope.nomineeList[$($event.target).parent().parent().index()];	    
	    	$scope.dateOfBirth = $filter('date')($scope.nomineeList[$($event.target).parent().parent().index()].dateOfBirth, 'dd/MM/yyyy');
	    	$scope.popUpSave = false;
	    	$scope.popUpUpdate =true;

	 
	    	setTimeout(function () {
	    	   	$scope.nomineeChange();
	    	   	$scope.minirChnage();	    	   	
            }, 10);
	    	$scope.nomineeChange();	    
	    	$scope.checkMinor();
	    	$scope.minirChnage();	
	    }
	    
	    
	    $scope.updateNomineeDetails= function($event){ 
	    	if($('#identificationTypeForm').valid()){
	    		
	    		if($('#dateOfBirth').val() != undefined && $('#dateOfBirth').val() != "" && $('#dateOfBirth').val() != 'Invalid date' && (($scope.workerNomine.ageYears != undefined && $scope.workerNomine.ageYears != "" && $scope.workerNomine.ageYears != null) || ( $scope.workerNomine.ageMonths != undefined && $scope.workerNomine.ageMonths != "" && $scope.workerNomine.ageMonths != null))){
	    			$scope.Messager('error', 'Error', 'Enter Either Date Of Birth or Age, Both Should Not Be Entered');
	    			return ;
	    		}else if(parseInt($scope.workerNomine.ageYears) == 0){
	    			$scope.Messager('error', 'Error', 'Year and month should not be zero');
	    			return ;
	    		}else if(($('#dateOfBirth').val() == undefined || $('#dateOfBirth').val() == "")  && ($scope.workerNomine.ageYears == undefined || $scope.workerNomine.ageYears == "")){
	    			$scope.Messager('error', 'Error', 'Enter Either Date Of Birth or Age');
	    			return ;
	    		}
	    		
	    		
	    	$scope.nomineeList.splice($($event.target).parent().parent().index(),1); 
	    
	    	 $scope.workerNomine.createdBy = $cookieStore.get('createdBy'); 
	    	 $scope.workerNomine.modifiedBy = $cookieStore.get('modifiedBy');
	    	 
	    	 if($('#dateOfBirth').val() != undefined && $('#dateOfBirth').val() != "" && $('#dateOfBirth').val() != 'Invalid date'){
		    		$scope.workerNomine.dateOfBirth =   moment($('#dateOfBirth').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
		    	}
		    	
	    	 $scope.getData('workerController/saveNomineeDetails.json', angular.toJson($scope.workerNomine), 'saveNomineeDetails',angular.element($event.currentTarget)); 
	    	 $('div[id^="identificationType"]').modal('hide');
	    	// $scope.clearPopupValues();
	    	}
	    	 
	    	
	    }
	    
	    
	    
	    $scope.Delete = function($event){	    
	    	var res = confirm("Are you sure you want to delete this member ? ");
	    	if(res){
		    	$scope.nomineeId =$scope.nomineeList[$($event.target).parent().parent().index()].workerNomineeId;
		    	$scope.nomineeList.splice($($event.target).parent().parent().index(),1);
		    	if( $scope.nomineeId != undefined && $scope.nomineeId != ""){
		    		 $scope.getData('workerController/deleteNomineeDetails.json', {workerNomneeId:$scope.nomineeId}, 'Delete',angular.element($event.currentTarget));
		    	}
	    	}else{
	    		// do nothing
	    	}
	    }
	    
	    
	    $scope.clearPopupValues =function(){
	    	$scope.workerNomine =  new Object();	
	    	$scope.workerNomine.workerId = $cookieStore.get('workerId');
	  	     $scope.workerNomine.customerId = $cookieStore.get('WorkercustomerId');
	  	     $scope.workerNomine.companyId = $cookieStore.get('WorkercompanyId');
	    	$scope.dateOfBirth = '';    
	    	$scope.workerNomine.selectAsNominee = 'N';
    		$scope.workerNomine.isMinor = 'N';
    		$scope.popUpSave = true;
	    	$scope.popUpUpdate =false;
	    	$scope.showPercentage =false;
	    	$scope.guardianShow =false;
	    	$scope.isSameAddress = false;
	    	 $scope.years= '';
			 $scope.months = '';
			 $scope.minorDisabled = false;
	    }
	    
	    
	    $scope.nomineeChange = function(){	    
	    	if($scope.workerNomine.selectAsNominee == 'Y' || $scope.workerNomine.selectAsNominee){
	    	   $scope.showPercentage =true;
	    	}else{
	    		 $scope.showPercentage =false;
	    		 $scope.workerNomine.percentageShareInPF='';
	    	}
	    }
	    
	    $scope.minirChnage = function(){
	    	if($scope.workerNomine.isMinor == 'Y' || $scope.workerNomine.isMinor){   	
		    		if($('#dateOfBirth').val() != ''){
		    			$scope.dateOfBirthValidaton();	    			
		    		}else if($scope.workerNomine != undefined && $scope.workerNomine.ageYears != '' &&  $scope.workerNomine.ageYears < 18) {		    		
		    			 $scope.guardianShow =false;
		    			 $scope.Messager('error', 'Error', 'Age is below 18, You are not allowed to select Is Minor');
						 $scope.workerNomine.isMinor = 'N';
		    		}else{
		    			 $scope.guardianShow =true;
		    		}
		    	}else{
		    		 $scope.guardianShow =false;
		    		 $scope.workerNomine.guardianDetails = '';
		    	}
	    	
	    }
	    
	    $scope.dateOfBirthValidaton = function(){
	    	
	     if($('#dateOfBirth').val() != ''){
				 if(moment($('#dateOfBirth').val(), 'DD/MM/YYYY', true).isValid()) {
				 var d1 = new Date(moment(new Date(), 'DD/MM/YYYY').format('YYYY-MM-DD'));
				 var d2 = new Date(moment($('#dateOfBirth').val(), 'DD/MM/YYYY').format('YYYY-MM-DD'));		
				
				 var dy,dm,dd;
				 
				    
			 	dy = d1.getYear()  - d2.getYear();
			 	dm = d1.getMonth() - d2.getMonth();
			 	dd = d1.getDate()  - d2.getDate();
			    
			    if (dd < 0) { dm -= 1; dd += 30; }
			    if (dm < 0) { dy -= 1; dm += 12; }
				    
		 
				 $scope.years= dy;
				 $scope.months = dm;	
				// alert($scope.years);
				 if(parseInt($scope.years) < 18){
					 $scope.Messager('error', 'Error', 'Age is below 18, You are not allowed to select Is Minor');
					 $scope.workerNomine.isMinor = 'N';
					 $scope.guardianShow =false;
				 }
				 
				 //$scope.workerVo.age = $scope.years+" Years"+$scope.months+" Months";
			 }
			}
			 
		};  
		
		
	    $scope.ifSameAddress = function($event){	    	
	    	if($scope.isSameAddress){
	    		
	    		if($scope.workerAddress ==undefined || $scope.workerAddress == ''){
	    			$scope.Messager('warning', 'warning', 'Worker address is not available');
	    		}
	    		$scope.workerNomine.address = $scope.workerAddress;
	    	
	    	}else{
	    		$scope.workerNomine.address = '';
	    	}
	    	
	    };
	    
	    
	    $scope.workerFamilyDetails = function($event){
		    
	    	  
	    	  if($scope.familyTab){
	    	  if($scope.workerChildIds == undefined || $scope.workerChildIds[0].workerId == undefined || parseInt($scope.workerChildIds[0].workerId) == 0){
	    		  $scope.Messager('error', 'Error', 'Enter worker Details, Then only you are allowed to enter Worker Family Details.',angular.element($event.currentTarget));
	    	  }else if( $scope.workerChildIds != undefined && $scope.workerChildIds[0].workerInfoId != undefined){ 
	    		 // $cookieStore.put('verificationVendorId',$scope.workerVo.vendorId);
	              //$cookieStore.put('verificationworkerId',$scope.workerVo.workerId);
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
	    		 // $cookieStore.put('verificationVendorId',$scope.workerVo.vendorId);
	              //$cookieStore.put('verificationworkerId',$scope.workerVo.workerId);
	    		  location.href = "#/workerMedicalExamination/"+$scope.workerChildIds[0].workerInfoId;
	    	  }
	    	  }else{
	    		  $scope.Messager('error', 'Error', 'You dont have permission to view/edit Verification Details',true); 
	    	  }
	      };
	      
	      $scope.workerJobDetails= function($event,flag){ 
	    	  
	    	  if($scope.jobDetailsTab){
	    	  if($scope.workerChildIds == undefined || $scope.workerChildIds[0].workerId == undefined || parseInt($scope.workerChildIds[0].workerId) == 0){
	    		  $scope.Messager('error', 'Error', 'Enter worker Details, Then only you are allowed to enter Worker Job Details.',angular.element($event.currentTarget));
	    	  }else if( $scope.workerChildIds != undefined && $scope.workerChildIds[0].workJobDetailsId != undefined && parseInt($scope.workerChildIds[0].verificationId) > 0 && $scope.workerChildIds[0].workJobDetailsId == 0){    		  
	    		 // $cookieStore.put('verificationVendorId',$scope.workerVo.vendorId);
	             // $cookieStore.put('verificationworkerId',$scope.workerVo.workerId);
	    		  location.href = "#/workerJobDetails/create";
	    	 }else if($scope.workerChildIds != undefined && $scope.workerChildIds[0].workJobDetailsId != undefined && parseInt($scope.workerChildIds[0].verificationId) > 0 && $scope.workerChildIds[0].workJobDetailsId > 0){
	    		// $cookieStore.put('verificationVendorId',$scope.workerVo.vendorId);
	           //  $cookieStore.put('verificationworkerId',$scope.workerVo.workerId); 
	    		 location.href = "#/workerJobDetails/"+$scope.workerChildIds[0].workJobDetailsId;
	    	  }else{
	    		  if(flag){
	    			  $scope.Messager('error', 'Error', 'Enter Verification Details, Then only you are allowed to Activate Worker.',angular.element($event.currentTarget));
	    		  }else{
	    			  $scope.Messager('error', 'Error', 'Enter Verification Details, Then only you are allowed to enter Worker Job Details.',angular.element($event.currentTarget));
	    		  }
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
	    		//  $cookieStore.put('verificationVendorId',$scope.workerVo.vendorId);
	             // $cookieStore.put('verificationworkerId',$scope.workerVo.workerId);
	    		  location.href = "#/workerBadgeGeneration/"+$scope.workerChildIds[0].workerBadgeId;   	 
	    	  }else{
	    		  $scope.Messager('error', 'Error', 'Enter Verification Details and Job Details, Then only you are allowed to enter Worker Badge Details.',angular.element($event.currentTarget));
	    	  }
	  		 }else{
	  			 $scope.Messager('error', 'Error', 'You dont have permission to view/edit Badge Details',true); 
	  		 }	  
	  		  
	     };
	     
	     $scope.callingUrl = function($event){
	    	 if($scope.familyTab){
	    		 $scope.workerFamilyDetails($event);
	    	 }
	    	 else if ($scope.medicalTab) {
	 			$scope.workerMedicalExamination($event);
	 		} else if ($scope.policeVerificationTab) {
	 			 $scope.workerPoliceVerification($event);
	 		} else if ($scope.verificationTab) {
	 			 $scope.workerVerification($event);
	 		} else if ($scope.jobDetailsTab) {			
	 			$scope.workerJobDetails($event);   		  
	 		} else if ($scope.badgeGenTab) {
	 			$scope.workerbadgeGeneration($event);
	 		}else{  				
	 			$scope.Messager('error', 'Error', 'You dont have permission to view/edit Next Tabs'); 
	   		 }
	 		
	      };
	    
	     $scope.checkMinor = function(){
	    	if($scope.workerNomine.ageYears != undefined && $scope.workerNomine.ageYears != ""  && $scope.workerNomine.ageYears != null && $scope.workerNomine.ageYears >= 18 ){
	    		$scope.$apply(function(){
	    			$scope.minorDisabled = true;
	    		});
	    		
	    	}else  if(moment($('#dateOfBirth').val(), 'DD/MM/YYYY', true).isValid()) {
				 var d1 = new Date(moment(new Date(), 'DD/MM/YYYY').format('YYYY-MM-DD'));
				 var d2 = new Date(moment($('#dateOfBirth').val(), 'DD/MM/YYYY').format('YYYY-MM-DD'));		
				
				 var dy,dm,dd;
				 
				    
			 	dy = d1.getYear()  - d2.getYear();
			 	dm = d1.getMonth() - d2.getMonth();
			 	dd = d1.getDate()  - d2.getDate();
			    
			    if (dd < 0) { dm -= 1; dd += 30; }
			    if (dm < 0) { dy -= 1; dm += 12; }
				    
				 $scope.years= dy;
				 $scope.months = dm;
				 
				 if( $scope.years >= 18){
					 $scope.$apply(function(){
			    			$scope.minorDisabled = true;
			    		});
				 }else{
					 $scope.$apply(function(){
						 $scope.minorDisabled = false;
					 });
				 }
	    	
	       }else{
	    	   $scope.$apply(function(){
	    			$scope.minorDisabled = false;
	    		});
	       }
	    }
}]);


