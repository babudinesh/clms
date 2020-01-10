'use strict';
var workerPoliceVerification = angular.module("myApp.workerPoliceVerification", []);

workerMedicalExaminationController.directive('filePoliceexamin', ['$parse', function ($parse) {
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

workerPoliceVerification.controller("workerPoliceVerificationController", ['$scope', '$rootScope', '$http', '$filter', '$resource','$location','$routeParams','$cookieStore', 'myservice','$window', function($scope,$rootScope, $http,$filter,$resource,$location,$routeParams,$cookieStore,myservice,$window) {
	
	$.material.init();
    
    $('#reviewDate,#identityDateOfIssue,#dateOne,#dateTwo,#datedOn').datepicker({
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
    
 
 $scope.identificationTypeList = [{ "id":"Election Card", "name":"Election Card"},
		                            { "id":"Passport", "name":"Passport"},
		                            { "id":"Driving License", "name":"Driving License"}, 
		                            { "id":"Arms License", "name":"Arms License"}, 
	                            ];
    
    $scope.workerPoliceVerification = new Object();
    $scope.workerPoliceVerification.identityList = [];
    $scope.workerVo =  new Object();
   
    $scope.transactionDate = $filter('date')(new Date(),"dd/MM/yyyy");
   
		
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
            	//alert($cookieStore.get('workerInfoId'));
            	$scope.workerInfoId = $cookieStore.get('workerInfoId');	
            	$scope.customerName = $cookieStore.get('WorkercustomerName');
        		$scope.companyName = $cookieStore.get('WorkercompanyName');
        		$scope.countryName = $cookieStore.get('WorkercountryName');            		
        		$scope.vendorName = $cookieStore.get('WorkervendorName');
        		$scope.firstName = $cookieStore.get('workerName');
        		$scope.workerCode = $cookieStore.get('workerCode');
            	
        		//alert(angular.toJson(response.data.workerAddressDetails));
        		if(response.data.workerAddressDetails != undefined && response.data.workerAddressDetails.length >0){
            		$scope.permanentAddress = response.data.workerAddressDetails[0].permanentAddress;
            		$scope.localAddress = response.data.workerAddressDetails[1].presentAddress;
        		}
            	if(response.data.workerPoliceVerificationDetails != undefined && response.data.workerPoliceVerificationDetails.length >0){
            		$scope.workerPoliceVerification = response.data.workerPoliceVerificationDetails[0];  
            		
            		$('#reviewDate').val($filter('date')( $scope.workerPoliceVerification.reviewDate, 'dd/MM/yyyy'));
            		$('#dateOne').val($filter('date')( $scope.workerPoliceVerification.dateOne, 'dd/MM/yyyy'));
            		$('#dateTwo').val($filter('date')( $scope.workerPoliceVerification.dateTwo, 'dd/MM/yyyy'));
            		$('#datedOn').val($filter('date')( $scope.workerPoliceVerification.datedOn, 'dd/MM/yyyy'));
                  	 
            	}else{
            		$scope.workerPoliceVerification = new Object();    
            		$scope.workerPoliceVerification.identityList = [];
            		$scope.workerPoliceVerification.customerId = $cookieStore.get('WorkercustomerId');
            		$scope.workerPoliceVerification.companyId = $cookieStore.get('WorkercompanyId');            		
            		$scope.workerPoliceVerification.vendorId = $cookieStore.get('WorkervendorId');
            		$scope.workerPoliceVerification.workerId = $cookieStore.get('workerId');
            		$scope.workerPoliceVerification.countryId = $cookieStore.get('WorkerCountryId');
            	}
            	$scope.getPostData('workerController/getWorkerChildScreensData.json', {workerId: $scope.workerPoliceVerification.workerId}, 'getWorkerChildScreensData') ;
            	
            	$scope.getPostData("workerController/getWorkerDetailsbyId.json", {workerInfoId: $cookieStore.get('workerInfoId') != undefined ? $cookieStore.get('workerInfoId') :0} , "getWorkerData");
            	
            	// for button views
            	if($scope.buttonArray == undefined || $scope.buttonArray == '')
            	$scope.getPostData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Worker Police Verification'}, 'buttonsAction'); 
                
            	
            }else if(type == 'getWorkerData'){
            	    // alert(angular.toJson(response.data));  
            	    // console.log(angular.toJson(response.data));
            	$scope.fatherSpouseName = response.data.workerDetails[0].fatherSpouseName;             	
            	$scope.motherName	= response.data.workerDetails[0].motherName;
            	$scope.nationality	= response.data.workerDetails[0].nationality;
            	$scope.language	= response.data.workerDetails[0].language;
            	$scope.education	= response.data.workerDetails[0].education;
            	$scope.phoneNumber = response.data.workerDetails[0].phoneNumber;
            } else if(type == 'getWorkerChildScreensData'){
            	$scope.workerChildIds = response.data;
            	
            } else if(type == 'savePoliceVerification'){
            	if(response.data.PoliceVerificationId != undefined && response.data.PoliceVerificationId > 0){
            		 $scope.Messager('success', 'success', 'Details Saved Successfully',buttonDisable);
            		 if($scope.transfer && $scope.verificationTab){
            			 location.href = "#/workerVerification/"+$scope.workerChildIds[0].workerInfoId;
            		 }else{
            			 $scope.getPostData("workerPoliceVerificationController/getWorkerPoliceVerificationDetailsByWorkerId.json", {workerId: $cookieStore.get('workerId') != undefined ? $cookieStore.get('workerId') :0,workerInfoId:$cookieStore.get('workerInfoId') != undefined ? $cookieStore.get('workerInfoId') :0} , "masterData"); 
            		 }
            		  
            	}else{
            		 $scope.Messager('error', 'Error', 'Error In Saving The Details',buttonDisable); 
            	}
            }  
            
        },
        function () {
       	 $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);        	
       })
	};
	
		
	$scope.getPostData("workerPoliceVerificationController/getWorkerPoliceVerificationDetailsByWorkerId.json", {workerId: $cookieStore.get('workerId') != undefined ? $cookieStore.get('workerId') :0,workerInfoId:$cookieStore.get('workerInfoId') != undefined ? $cookieStore.get('workerInfoId') :0} , "masterData");
	
	
	

	
	
	$scope.fun_Save = function($event,flag){
		if($('#workerPoliceVerificationForm').valid()){
			$scope.transfer = flag;
		$scope.workerPoliceVerification.createdBy = $cookieStore.get('createdBy'); 
	    $scope.workerPoliceVerification.modifiedBy = $cookieStore.get('createdBy');
	    $scope.workerPoliceVerification.modifiedDate = null;
	    $scope.workerPoliceVerification.createdDate = null;
	    
	  //  $scope.workerPoliceVerification.reviewDate = moment($('#reviewDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
	    if($('#dateOne').val() != undefined && $('#dateOne').val() != '' && $('#dateOne').val() != 'Invalid date'){
	    $scope.workerPoliceVerification.dateOne = moment($('#dateOne').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
	    }
	    if($('#dateTwo').val() != undefined && $('#dateTwo').val() != '' && $('#dateTwo').val() != 'Invalid date'){
	    $scope.workerPoliceVerification.dateTwo = moment($('#dateTwo').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
	    }
	    if($('#datedOn').val() != undefined && $('#datedOn').val() != '' && $('#datedOn').val() != 'Invalid date'){
	    $scope.workerPoliceVerification.datedOn = moment($('#datedOn').val(),'DD/MM/YYYY').format('YYYY-MM-DD');    
	    }
	    
	  // alert(angular.toJson($scope.workerPoliceVerification));
	    
	    	var formData = new FormData();
	    
	    formData.append("file", $scope.theFile);
	    formData.append("name",angular.toJson($scope.workerPoliceVerification));
	    $http.post('workerPoliceVerificationController/saveOrUpdateWorkerPoliceVerificationDetails.json', formData, {
	           transformRequest: angular.identity,
	           headers: {'Content-Type': undefined}
	       })
	       .success(function(response){
	    	 //  alert(angular.toJson(response));
	       	if(response.PoliceVerificationId > 0 ){	       		
	       	 $scope.Messager('success', 'success', 'Details Saved Successfully',angular.element($event.currentTarget));
    		 if($scope.transfer && $scope.verificationTab){
    			 location.href = "#/workerVerification/"+$scope.workerChildIds[0].workerInfoId;
    		 }else{
    			 $scope.getPostData("workerPoliceVerificationController/getWorkerPoliceVerificationDetailsByWorkerId.json", {workerId: $cookieStore.get('workerId') != undefined ? $cookieStore.get('workerId') :0,workerInfoId:$cookieStore.get('workerInfoId') != undefined ? $cookieStore.get('workerInfoId') :0} , "masterData"); 
    		 }
	       		
	    		        	 	           	
	       	}else{
	       		$scope.Messager('error', 'Error', 'Technical issue occurred. Please try again after some time...');   
	       	}
	      	 
	       })
	       .error(function(){
	      	 	$scope.Messager('error', 'Error', 'File Upload Failed',angular.element($event.currentTarget));
	       });
	    
	    
	    
	    
	  // $scope.getPostData("workerPoliceVerificationController/saveOrUpdateWorkerPoliceVerificationDetails.json",angular.toJson($scope.workerPoliceVerification) , "savePoliceVerification");
	    	 
		}
	}
	
	
	 $scope.getFileDetails = function (e) {  
	        $scope.$apply(function () {
	             $scope.theFile = e.files[0];
	        });
	    };
	    
	
	
	 $scope.plusIconAction = function(){	 
	    	$scope.popUpSave = true;
	    	$scope.popUpUpdate =false;
	    	$scope.identification = new Object();
	    	$('#identityDateOfIssue').val("");

	    }

    $scope.saveDetails = function(){   
    	//alert($("#identificationForm").valid());
    	if($("#identificationForm").valid()){	    
		    	$scope.workerPoliceVerification.identityList.push({   		
		    		'identityType':$scope.identification.identityType,		    
		    		'identificationNumber':$scope.identification.identificationNumber,		    		
		    		'identityPlaceOfIssue':$scope.identification.identityPlaceOfIssue,    		
		    		'identityDateOfIssue':$('#identityDateOfIssue').val()
		    		   		
		    	});   
		    	 $('div[id^="IdentificationModel"]').modal('hide');
		    	
	    	
    	}
    	
    }
    
    $scope.Edit = function($event){    	
    	$scope.trIndex = $($event.target).parent().parent().index();
    	$scope.identification = $scope.workerPoliceVerification.identityList[$($event.target).parent().parent().index()];
    	$scope.popUpSave = false;
    	$scope.popUpUpdate =true;
    }
    
    
    $scope.updateDetails= function($event){
    	if($('#identificationForm').valid()){
	    	$scope.workerPoliceVerification.identityList.splice($scope.trIndex,1); 
	    	$scope.workerPoliceVerification.identityList.push({   		
	    		'identityType':$scope.identification.identityType,		    
	    		'identificationNumber':$scope.identification.identificationNumber,		    		
	    		'identityPlaceOfIssue':$scope.identification.identityPlaceOfIssue,    		
	    		'identityDateOfIssue':$('#identityDateOfIssue').val()
	    		   		
	    	});   
		    	 $('div[id^="IdentificationModel"]').modal('hide');   	
	    		
    	}
    }
    
    
    
    $scope.Delete = function($event){    	
    	
    	var r = confirm("Do you want to delete the "+$scope.workerPoliceVerification.identityList[$($event.target).parent().parent().index()].countryName);    	
    	if(r){
    		$scope.workerPoliceVerification.identityList.splice($($event.target).parent().parent().index(),1);
    	}
    	
    }
    
	
	
	
	
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
   	if ($scope.verificationTab) {
			 $scope.workerVerification($event);
		} else if ($scope.jobDetailsTab) {			
			$scope.workerJobDetails($event);   		  
		} else if ($scope.badgeGenTab) {
			$scope.workerbadgeGeneration($event);
		}else{
			 $scope.Messager('error', 'Error', 'You dont have permission to view/edit Next Tabs',true); 
		}
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
    
    $scope.fun_pdf_Download = function(){ 

	          if($('#countryForm').valid()){ 
	        	  $scope.dateOne = $('#dateOne').val();
	              $scope.datedOn = $('#datedOn').val();
	              $scope.dateTwo = $('#dateTwo').val();
	              
	        	 // $("#loader").show();
	          	//  $("#data_container").hide();
	          	  
				   $http({
					    url: ROOTURL +'workerPoliceVerificationController/downloadLetter.view',
					    method: 'POST',
					    responseType: 'arraybuffer',
					    data: {			  dateOne :  $scope.dateOne, 
					    				  dateTwo : $scope.dateTwo  ,
										  datedOn: $scope.datedOn  ,									
										  letterNo : $scope.workerPoliceVerification.letterNumber == undefined ? '' : $scope.workerPoliceVerification.letterNumber ,
										  policeOne : $scope.workerPoliceVerification.thePoliceStationDetailsOne == undefined ? '' : $scope.workerPoliceVerification.thePoliceStationDetailsOne ,
										  policeTwo : $scope.workerPoliceVerification.thePoliceStationDetailsTwo == undefined ? '' : $scope.workerPoliceVerification.thePoliceStationDetailsTwo ,
										  contractEngagedWith : $scope.workerPoliceVerification.contractEngagedWith == undefined ? '' : $scope.workerPoliceVerification.contractEngagedWith ,
									      nameOfContract : $scope.workerPoliceVerification.nameOfContract == undefined ? '' : $scope.workerPoliceVerification.nameOfContract ,
									      signatureOfTheProprietor : $scope.workerPoliceVerification.signatureOfProprietor == undefined ? '' : $scope.workerPoliceVerification.signatureOfProprietor,
										  place : $scope.workerPoliceVerification.letterPlace == undefined ? '' :$scope.workerPoliceVerification.letterPlace	  
								} , //this is your json data string
					    headers: {
					        'Content-type': 'application/json',
					        'Accept': 'application/pdf'
					    }
					}).success(function(data){
					    var blob = new Blob([data], {
					        type: 'application/pdf'
					    });
					    saveAs(blob, 'Letter' + '.pdf');
					   // $("#loader").hide();
					    //if($scope.dataAvail)
                       // $("#data_container").show();
					}).error(function(){	
						$("#loader").hide();
                      //  $("#data_container").show();
					});
	          }
	   
		}
  
   
	
	 
}]);
