'use strict';




workerVerification.directive('fileVerify', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileVerify);
            var modelSetter = model.assign;

            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]); 
                   // var reader = new FileReader();
                    //reader.readAsBinaryString(element[0].files[0]);
                    //alert(reader.readAsBinaryString(element[0].files[0])+":::asdf");
                });
            });
        }
    };
}]);


workerVerification.controller("WorkerVerificatonCtrl", ['$window','$scope', '$http', '$resource','$location','$filter', '$routeParams','myservice','$cookieStore','$timeout', function ($window,$scope, $http, $resource, $location, $filter, $routeParams, myservice, $cookieStore,$timeout) {
	 $.material.init();
	 
	// $scope.verificationList.xyz = '';

	$scope.list_VerificationType = [];
	
	
		$scope.list_VerificationStatus = [{"id":"Verified","name" : "Verified" },
	                                  {"id":"In Progress","name" : "In Progress" },
	                                  {"id":"Not Started","name" : "Not Started" },
	                                  {"id":"Not Applicable","name" : "Not Applicable" }];
	
	$scope.list_Status = [{"id":"Verified","name" : "Verified" },
	 	     	          {"id":"Not Started","name" : "Not Started" },
	 	     	          {"id":"Not Applicable","name" : "Not Applicable" }];
	
	var statusLength =$scope.list_VerificationType.length;
     
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
     $scope.verification = new Object();    
     $scope.verification.verificationStatus = "Not Started";
     
     
    
     
     
     //$scope.verification1.workerVerificationList = [];
     if ($routeParams.id > 0) {
           $scope.readOnly = true;
           $scope.datesReadOnly = true;
           $scope.updateBtn = true;
           $scope.saveBtn = false;
           $scope.viewOrUpdateBtn = true;
           $scope.correcttHistoryBtn = false;
           $scope.resetBtn = false;
           $scope.gridButtons = false;
           $scope.transactionList = false;
           $scope.cancelBtn = false;
           $scope.readOnlyVar= false;
           $scope.workerInfoId = $routeParams.id;
           
       } 
      
       
       $scope.getData = function (url, data, type, buttonDisable) {
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
	        		
	        		
        		} else if(type == 'verificationList'){
        		   //console.log(angular.toJson(response.data[0]));
        		 //  console.log(angular.toJson(response.data[0].workerVerificationList));
        		   $scope.verification1 = response.data[0];
        		   $scope.verification.customerName = response.data[0].customerName;
        		   $scope.verification.companyName = response.data[0].companyName;
        		   $scope.verification.vendorName = response.data[0].vendorName;
        		   $scope.verification.countryName = response.data[0].countryName;
        		   $scope.verification.workerCode = response.data[0].workerCode;
        		   $scope.verification.firstName = response.data[0].firstName;
        		   $scope.verification.customerId = response.data[0].customerId;
        		   $scope.verification.companyId = response.data[0].companyId;
        		   $scope.verification.vendorId = response.data[0].vendorId;
        		   $scope.verification.countryId = response.data[0].countryId;
        		   $scope.verification.workerId = response.data[0].workerId;
        		   
        		  // alert(angular.toJson(response.data[0].workerVerificationList));
        		   if( response.data[0].workerVerificationList != null ){
        			   $scope.result =response.data[0].workerVerificationList;   
        			   $scope.workerVerificationList = response.data[0].workerVerificationList;
        			   if( $scope.result == undefined || $scope.result == null || $scope.result == ""  ){
        				   $scope.verification.verificationStatus = "Not Started";
        			   }else if($scope.result != undefined && $scope.result != null && $scope.result != "" 
        				   		&&(response.data[0].verificationStatus == null || response.data[0].verificationStatus == undefined
        				   				|| response.data[0].verificationStatus == "") ){
        				   $scope.fun_VerificationStatus();	 
        			   }else if(response.data[0].verificationStatus != undefined && response.data[0].verificationStatus != null && response.data[0].verificationStatus != "" ){
        				   $scope.verification.verificationStatus =  response.data[0].verificationStatus;
        			   }
        		   }else{
        			   $scope.result =[];
        		   }
        		   
        		   if(response.data[0].workerId != undefined && response.data[0].workerId != null && parseInt(response.data[0].workerId) > 0){
        			   $cookieStore.put('verificationCustomerId',response.data[0].customerId);
        			   $cookieStore.put('verificationCompanyId',response.data[0].companyId);
        			   $cookieStore.put('verificationworkerId',response.data[0].workerId);
        			   $cookieStore.put('verificationVendorId',response.data[0].vendorId);
                  	 $scope.getData('workerController/getWorkerChildScreensData.json', {workerId: response.data[0].workerId}, 'getWorkerChildScreensData') 
                  	}
        		// for button views
        		   if($scope.buttonArray == undefined || $scope.buttonArray == '')
        		   $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Worker Verification'}, 'buttonsAction');
        		   $scope.setupVo = new Object();
        		   $scope.setupVo.customerId = $scope.verification.customerId;
        		   	$scope.setupVo.companyId =  $scope.verification.companyId;
        		   	$scope.setupVo.isActive = 'Y';  		   
        	       $scope.getData('workerVerificationTypesSetupController/getWorkerVerificationTypesSetupsGridData.json', angular.toJson($scope.setupVo), 'list_verifiactionTypes');
        	       
        		   
        	   }else if(type == 'getWorkerChildScreensData'){            	
        		   $scope.workerChildIds = response.data;
            	}else if(type == 'workerStatus'){            		
	        		$scope.workerStatus = response.data;	        		
	          		 if($scope.workerStatus && ($cookieStore.get("roleNamesArray").includes('Super Admin') || $cookieStore.get("roleNamesArray").includes('Company Admin'))){
	          			 	$scope.showProceedButton = true;
	          		     }else{
	          		    	 $scope.showProceedButton = false;
	          		     }	          		      		
	          	}else if(type == 'list_verifiactionTypes'){
	          		
	          	   angular.forEach(response.data.WorkerVerificationTypesSetupList, function (value, key) {   
	          		   
	          		// alert(angular.toJson(value.verificationType)+"::::"+key);
	          		 
	          		$scope.list_VerificationType.push(
	          				{"id":value.mandatiory,"name":value.verificationType});
	          	   });
	          		
	          		
	          	}
           },
           function () { 
        	   $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
           });
       }
       
       
       
              
       $scope.getData('workerController/workerVerificationDetailsByWorkerInfoId.json', { workerInfoId: $routeParams.id}, 'verificationList');
       
       
       
       
       
       $scope.workrVo = new Object();
       $scope.workrVo.workerInfoId = $cookieStore.get('workerInfoId');       
       $scope.getData('workerController/checkWorkerStatus.json', angular.toJson($scope.workrVo), 'workerStatus');
	   
	   	

       $scope.save = function ($event) {    	
    		  $scope.verification.createdBy = $cookieStore.get('createdBy'); 
    		  $scope.verification.modifiedBy = $cookieStore.get('modifiedBy');
    		  //alert(angular.toJson($scope.verification));
    		//   $scope.getData('workerController/saveVerificationStatus.json', angular.toJson($scope.verification), 'saveWageScale',angular.element($event.currentTarget));
    	  // }
       };
       
       
 
       

       $scope.saveDetails = function($event){   
    	   if($('#workerVerificationForm').valid()){  
    		   if($scope.verificationList.isCleared == undefined){
    			   $scope.verificationList.isCleared = 'No';
    		   }
    		 
    		   $scope.result.push({
		       		'verificationType':$scope.verificationList.verificationType,
		       	    'status':$scope.verificationList.status ,
		       		'comments':$scope.verificationList.comments,
		       		'location': null ,
	    	       	'fileName':($scope.theFile != undefined && $scope.theFile != null && $scope.theFile != "") ? $scope.theFile.name: ($scope.verificationList.fileName != undefined && $scope.verificationList.fileName != null && $scope.verificationList.fileName != "") ? $scope.verificationList.fileName : null ,
		       		'attachment': $scope.theFile != undefined ? $scope.theFile: null,
		       		'verificationId':$scope.verificationList.verificationId != undefined ? $scope.verificationList.verificationId : 0
	    	   });  
	    	 
	    	   $('div[id^="workerVerification"]').modal('hide');
    	   }
    	   
    	   $scope.fun_VerificationStatus();
    	   
        }
       
       
       
       $scope.Edit = function($event,location,i){   
	    	$scope.readOnlyVar = true;
	    	$scope.trIndex =  $($event.target).parent().parent().index();    
	    	//alert($scope.trIndex);
	    	var data =  $scope.result[$($event.target).parent().parent().index()];
	    	//alert(angular.toJson(data));
	       	$scope.verificationList = data;    
	       	$scope.myFile=null;
	        $('#fileName').val("");
	      
	        $scope.popUpSave = false;
	       	$scope.popUpUpdate =true;
       }
       
       
       $scope.updateDetails= function($event){  
    	   if($('#workerVerificationForm').valid()){
    		   $scope.result.splice($scope.trIndex,1);      
    		   $scope.result.push({
    			   'verificationType':$scope.verificationList.verificationType,
    			   'status': $scope.verificationList.status  ,
    			   'comments':$scope.verificationList.comments ,
		       	   'location':($scope.verificationList.location != undefined && $scope.verificationList.location != null && $scope.verificationList.location != "") ? $scope.verificationList.location : null ,
    	       	   'fileName':($scope.theFile != undefined && $scope.theFile != null && $scope.theFile != "") ? $scope.theFile.name: ($scope.verificationList.fileName != undefined && $scope.verificationList.fileName != null && $scope.verificationList.fileName != "") ? $scope.verificationList.fileName : null ,
    	       	   'attachment':$scope.theFile != undefined ? $scope.theFile: null,
    	       	 'verificationId':$scope.verificationList.verificationId != undefined ? $scope.verificationList.verificationId : 0
    		   });   
    		   $('div[id^="workerVerification"]').modal('hide');
    	   }
    	   
    	   $scope.fun_VerificationStatus();
    	
    	   
       }
       
       
       $scope.Delete = function($event){    
    	   if( $scope.result.length == 1){
             	 $scope.Messager('error', 'Error','Atleast one verification type is mandatory.');

    	   }else if( $scope.result.length > 1){
	    	   var del = $window.confirm('Are you absolutely sure you want to delete?');
	    	   if (del) {
	    		   $scope.result.splice($($event.target).parent().parent().index(),1);
	    		  // alert( $scope.result[$($event.target).parent().parent().index()]);
	    		   $scope.fun_VerificationStatus();	  
	    	   }
    	   }
       };
       
       $scope.fun_VerificationStatus = function(){
    	   var count = 0; // verified
    	   var count1 = 0; // not applicable
    	   var count2 = 0;// not started
    	  
    	   
    	   var c = new Array();
    	   var c1 = new Array();
    	   var c2 = new Array();
    	   
    	   var i =0;
    	   
    	   angular.forEach($scope.result, function (value, key) {    
    		   for(i = 0; i < statusLength; i++){
	    		   if(value.verificationType == $scope.list_VerificationType[i].id ){
					   if(value.status == 'Verified'){
						  // alert(c[i]);
						   if(c[i] > 0){
							   c[i] = c[i]+1;
						   	 //  alert("1: "+c[i]);
						   }else if(c[i] == undefined){
							   c[i] = 1;
							 //  alert("DDDD ; "+c[i]);
						   }
						   
					   }else if(value.status == 'Not Applicable'){
						   if(c1[i] > 0){
							   c1[i] = c1[i]+1;
							 //  alert("2: "+c1[i]);
						   }else if(c1[i] == undefined){
							   c1[i] = 1;
							 //  alert("DDDD2 ; "+c[i]);
						   }
					   }else if(value.status == 'Not Started'){
						   if(c2[i] > 0){
							   c2[i] = c2[i]+1;
							  // alert("3: "+c2[i]);
						   }else if(c2[i] == undefined){
							   c2[i] = 1;
							  // alert("DDDD3 ; "+c[i]);
						   }
					   }
				   }
    		   }
    		   
    		   /*if(value.verificationType == $scope.list_VerificationType[0].id ){
				   if(value.status == 'Verified'){
					   count1 = count1+1;
				   	  // alert("1: "+count1);
				   }else{
					   count++;
				   }
			   }else if( value.verificationType == $scope.list_VerificationType[1].id ){
				   if(value.isCleared == 'Yes'){
					   count2 = count2+1;
					  // alert("2: "+count2);
				   }else{
					   count++;
				   }
			   }else if( value.verificationType == $scope.list_VerificationType[2].id ){
				   if(value.isCleared == 'Yes'){   
					   count3 = count3+1;
					  // alert("3: "+count3);
				   }else{
					   count++;
				   }
			   }else if(value.verificationType == $scope.list_VerificationType[3].id ){
				   if(value.isCleared == 'Yes'){
					   count4 = count4+1;
					   //alert("4: "+count4);
				   }else{
					   count++;
				   }
			   }*/
			   
		   });
    	   
    	   for(i = 0 ; i < statusLength; i++){
    		   	if(c[i] == undefined){
    			   c[i] = 0;
			   	}else if(c[i] > 1){
			   		c[i] = 1;
			   	}
    		   	
		   		if(c1[i] == undefined){
    			   c1[i] = 0;
    	   		}else if(c1[i] > 1){
			   		c1[i] = 1;
			   	}
		   		
		   		if(c2[i] == undefined){
    			   c2[i] = 0;
    		    }else if(c2[i] > 1){
			   		c2[i] = 1;
			   	}
    		   count = count +c[i];
    		   count1 = count1+c1[i];
    		   count2 = count2+c2[i];
    	   }
    	  // alert(1);
    	  // alert("Count:  "+count);
    	  // alert("Count1: "+count1);
    	  // alert("Count2: "+count2);
    	   
    	  // alert("Counts:    C ::  "+c+" : C1  :: "+c1+"  : C2  ::  "+c2);
    	   
    	   if(count == statusLength || (count > 0 && count < statusLength && count1 == (statusLength-count))){
     		   $scope.verification.verificationStatus = "Verified";
     	   }else if( count1 == statusLength ){
     		   $scope.verification.verificationStatus = "Not Applicable";
     	   }else if(count2 == statusLength){
     		  $scope.verification.verificationStatus = "Not Started";
     	   }else{
     		   $scope.verification.verificationStatus = "In Progress";
     	   }
    	   
	       /*if(count1 > 0 && count2 > 0 && count3 > 0 && count4 > 0){
     		   $scope.verification.verificationStatus = "Verified";
     	   }else if(count1 == 0 && count2 == 0 && count3 == 0 && count4 == 0 && count > 0 ){
     		   $scope.verification.verificationStatus = "Not Applicable";
     	   }else if( count1 > 0  || count2 > 0 || count3 > 0 || count4 > 0 ){
     		   $scope.verification.verificationStatus = "In Progress";
     	   }*/
       }
       $scope.close = function(){
    	   $('div[id^="workerVerification"]').modal('hide');
		    $scope.popUpSave = true;
	       	$scope.popUpUpdate =false;
       }
       
       
       $scope.plusIconAction = function(){
    	    $scope.verificationList = new Object();
    	    $scope.readOnlyVar = false;
    	    $('#fileName').val("");
    	    $scope.theFile= null;
    	 	$scope.popUpSave = true;
       		$scope.popUpUpdate =false;
       }
       
       
            
       
     
       
       $scope.getFileDetails = function (e) {  
	        $scope.$apply(function () {
	             $scope.theFile = e.files[0];
	        });
	    };
	    
	    var requiredFlag = false;
	    $scope.verificationTypeChange= function (e) {
	    	 $( "#verificationTypeLabel" ).removeClass( "required" );
	    	 angular.forEach($scope.list_VerificationType, function (value, key) {
	    		// alert(key+":::"+value.id);
	    		 if(value.id == 'true' && value.name == $scope.verificationList.verificationType){ 
	    			 $( "#verificationTypeLabel" ).addClass( "required" );
	    			 requiredFlag = true;
	    		 }
	    	 });
	    	 
	    	 
	    };
       
   
       
       $scope.uploadFiles =function($event,flag){  
    	  var formData = new FormData();
    	  
    	  $scope.verification.createdBy = $cookieStore.get('createdBy'); 
 		  $scope.verification.modifiedBy = $cookieStore.get('modifiedBy');
 		  $scope.verification.workerVerificationList = $scope.result;
 		 
 		  
 		  
 		  var flags = true;
 		  var actualCount = 0;
 		 var uploadCount = 0;
 		  
 		  if($scope.result.length > 0){
 			 
 			 angular.forEach($scope.list_VerificationType, function (value, key) { 			
 				 if(value.id == 'true'){ 
 					actualCount++;
 					 angular.forEach($scope.result, function (value1, key1) {        	 
 				      	  if(value1.verificationType == value.name){
 				      		  uploadCount++;			      		
 				      	  }
 			 		});
 				 }
	 			
 			
 			});
 		  
 			 //alert(actualCount+":::"+uploadCount);
 			 if(actualCount >= 0 && uploadCount >= 0 && actualCount == uploadCount){
 			 
 		 // console.log(angular.toJson($scope.verification));
 		  angular.forEach($scope.result, function (value, key) {        	 
			      	   formData.append("file", value.attachment); 
			      	   formData.append("filename", "");    
		 		});
		 			 		
		 	formData.append('name',angular.toJson($scope.verification));  	
	    	 $http.post('workerController/saveVerificationStatus.json', formData, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            })
            .success(function(response){
            	//alert(angular.toJson(response));
            	if(response == 0 ){
            		$scope.Messager('success', 'success', 'Document Saved Successfully',angular.element($event.currentTarget));
            		
            		
            		if($cookieStore.get('verificationworkerId') != undefined && $cookieStore.get('verificationworkerId') != null && $cookieStore.get('verificationworkerId') > 0){
                   	  $scope.getData('workerController/getWorkerChildScreensData.json', {workerId: $cookieStore.get('verificationworkerId')}, 'getWorkerChildScreensData') 
                   	}
            		
            		 setTimeout(function () {
            			// alert("::::"+$scope.workerChildIds[0].verificationId+":::"+$scope.workerChildIds[0].workJobDetailsId);
		            	if($scope.workerChildIds != undefined && $scope.workerChildIds[0].workJobDetailsId != undefined && parseInt($scope.workerChildIds[0].verificationId)
		            			> 0 && parseInt($scope.workerChildIds[0].workJobDetailsId) > 0){	
		            		$timeout(function () {
		      	    		  $location.path('/workerJobDetails/'+$scope.workerChildIds[0].workJobDetailsId);
		            		},0);
		      	    	  }else if($scope.workerChildIds != undefined && $scope.workerChildIds[0].workJobDetailsId != undefined && parseInt($scope.workerChildIds[0].verificationId) > 0 && parseInt($scope.workerChildIds[0].workJobDetailsId) == 0){		      	    		
		      	    		$timeout(function () {
		      	    		  $location.path('/workerJobDetails/create');
		      	    		},0);
		      	    		 // alert();
		      	    	  }  
            		 },100);
               	 	           	
            	}else{
            		$scope.Messager('error', 'Error', 'Technical issue occurred. Please try again after some time...');
            		$scope.getData('workerController/getWorkerChildScreensData.json', {workerId: $scope.verification.workerId}, 'getWorkerChildScreensData') 
               	 	$location.path('/workerVerification/'+$routeParams.id);
            	}
           	 
            })
            .error(function(){
           	 	$scope.Messager('error', 'Error', 'File Upload Failed',angular.element($event.currentTarget));
            });
 			 }else{
 				 
 				$scope.Messager('error', 'Error', 'Upload Mandatory Documents',angular.element($event.currentTarget)); 		
 			 }	 
	    	 
 		  }else{
 			 $scope.Messager('error', 'Error', 'Minimum One Document Should Be Verified To Proceed To Next Step',angular.element($event.currentTarget)); 			  
 		  }
 		  
	    
	    }
       
       
      
       
       
       
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
	      
	      $scope.workerJobDetails= function($event,flag){ 
	    	  if($scope.jobDetailsTab){
	    	//  alert(angular.toJson($scope.workerChildIds));
	    	  if($scope.workerChildIds == undefined || $scope.workerChildIds[0].workerId == undefined || parseInt($scope.workerChildIds[0].workerId) == 0){
	    		  $scope.Messager('error', 'Error', 'Enter worker Details, Then only you are allowed to enter Worker Job Details.',angular.element($event.currentTarget));
	    	  }else if( $scope.workerChildIds != undefined && $scope.workerChildIds[0].workJobDetailsId != undefined && parseInt($scope.workerChildIds[0].verificationId) > 0 && parseInt($scope.workerChildIds[0].workJobDetailsId) > 0){	    			
	    		  location.href = "#/workerJobDetails/"+$scope.workerChildIds[0].workJobDetailsId;
	    	  }else if($scope.workerChildIds != undefined && $scope.workerChildIds[0].workJobDetailsId != undefined && parseInt($scope.workerChildIds[0].verificationId) > 0 && parseInt($scope.workerChildIds[0].workJobDetailsId) == 0){
	    		location.href = "#/workerJobDetails/create";
	    	  }else{
	    		  if(flag){
	    			  $scope.Messager('error', 'Error', 'Save Verification Details, Then only you are allowed to Activate Worker .',angular.element($event.currentTarget));
	    		  }else{
	    			  $scope.Messager('error', 'Error', 'Save Verification Details, Then only you are allowed to enter Worker Job Details.',angular.element($event.currentTarget));
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
		    		location.href = "#/workerBadgeGeneration/"+$scope.workerChildIds[0].workerBadgeId;   	 
		    	  }else{
		    		  $scope.Messager('error', 'Error', 'Save Verification Details and Job Details, Then only you are allowed to enter Worker Badge Details.',angular.element($event.currentTarget));
		    	  }
		  		  
	  		 }else{
	  			 $scope.Messager('error', 'Error', 'You dont have permission to view/edit Badge Details',true); 
	  		 }
	     };
	     
	     
	     
	     $scope.callingUrl = function($event){
	    	 if ($scope.jobDetailsTab) {			
				$scope.workerJobDetails($event);   		  
			} else if ($scope.badgeGenTab) {
				$scope.workerbadgeGeneration($event);
			}else{
				 $scope.Messager('error', 'Error', 'You dont have permission to view/edit Next Tabs',true); 
	 		}
	     };
       
       
    /*   $scope.fileDownload = function(data1){
    	      	   
 		var file = $scope.verification.workerVerificationList[$($event.target).parent().parent().index()].attachment;
 	      var reader = new FileReader();
 	      reader.readAsArrayBuffer(file);
 	      reader.onload = function (evt) {
 	    	  var blob = new Blob([evt.target.result], {
			        type: 'application/octet-stream'
			    });			 
			    saveAs(blob,file.name); 	        
 	      }
 	      reader.onerror = function (evt) { 	         
 	    	  alert("error");
 	      }    
       }*/
       
       
      /* $scope.isClearChange = function(){
    	  // alert($scope.verificationList.isCleared );
		   if($scope.verificationList.isCleared == true){
    		   $scope.verificationList.isCleared = 'Yes';
    	   }else if($scope.verificationList.isCleared == 'Yes') {
    		   $scope.verificationList.isCleared = 'No';
    	   }

       }*/
}]);





