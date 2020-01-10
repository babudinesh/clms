'use strict';
workerDetailsControllers.directive('fileModel', ['$parse', function ($parse) {
	return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;
            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);

workerDetailsControllers.directive('myUpload', [function () {
    return {
        restrict: 'A',
        link: function (scope, elem, attrs) {
            var reader = new FileReader();
            reader.onload = function (e) {
                scope.image = e.target.result;
                scope.workerVo.imagePath=null;
               // alert(scope.image);
                scope.$apply();
            }

            elem.on('change', function() {
            	
            	if(elem[0].files[0] != null){
            		reader.readAsDataURL(elem[0].files[0]);
            	}else{
            		scope.image="";
            		scope.$apply();
            	}
            });
        }
    };
}]);
workerDetailsControllers.controller('workerDetailsCtrl', ['$scope','$http', '$resource','$routeParams','$filter','myservice','$cookieStore','$location', function ($scope,$http, $resource, $routeParams,$filter,myservice,$cookieStore,$location) {
	 $.material.init();
	 $scope.workerVo = new Object();
	 $scope.identity =  new Object();
	 $scope.companyProfile = new Object();
	 $scope.result = [];
	 $scope.popUpSave = true;
	 $scope.popUpUpdate = false;
	 var flag = false;
	 $scope.workerVo.isSameAddress = false;
	 $scope.labelName1 = "Add";
	 $scope.labelName2 = "Add";	
	 $scope.wiladminBoolean = false;
	 $scope.showProceedButton = false; 
	 $scope.blood_group_list = [{ "id":"A+", "name":"A+"}, { "id":"A-", "name":"A-"},
	                            { "id":"AB+", "name":"AB+"}, { "id":"AB-", "name":"AB-"},
	                            { "id":"A1+", "name":"A1+"}, { "id":"A1-", "name":"A2-"},
	                            { "id":"A2+", "name":"A2+"}, { "id":"A1-", "name":"A2-"},
	                            { "id":"A1B+", "name":"A1B+"}, { "id":"A1B-", "name":"A1B-"},
	                            { "id":"A2B+", "name":"A2B+"}, { "id":"A2B-", "name":"A2B-"},
	                            { "id":"B+", "name":"B+"}, { "id":"B-", "name":"B-"},
	                            { "id":"O+", "name":"O+"}, { "id":"O-", "name":"O-"},
	                            { "id":"U", "name":"U"}, { "id":"Other", "name":"Other"}
	                            ];
	 
	 $scope.marital_status_list = [{ "id":"Single", "name":"Single"},
	                            { "id":"Married", "name":"Married"},
	                            { "id":"Unmarried", "name":"Unmarried"}, 
	                            { "id":"Widowed", "name":"Widowed"}, 
	                            { "id":"Divorced", "name":"Divorced"}, 
	                            { "id":"Unknown", "name":"Unknown"}
	                            ];
			 
	$scope.id_types = [{ "id":"PAN Card", "name":"BRP"},
                       
                       { "id":"Passport", "name":"Passport"}, 
                       { "id":"Driving License", "name":"Driving License"}
                       ];
	

	$scope.reasonForStatus_list =[{ "id":"Debarred", "name":"Debarred"},
	                              { "id":"Suspended", "name":"Suspended"},
	                              { "id":"Left", "name":"Left"},
	                              { "id":"Absent for 30 days", "name":"Absent for 30 days"}
	                              ];
	
	
	
	$scope.weekList = [
	                     {"id":"MON","name" : "MONDAY" },
	                     {"id":"TUE","name" : "TUESDAY" },
	                     {"id":"WED","name" : "WEDNESDAY" },
	                     {"id":"THU","name" : "THURSDAY" },
	                     {"id":"FRI","name" : "FRIDAY" },
	                     {"id":"SAT","name" : "SATURDAY" },
	                     {"id":"SUN","name" : "SUNDAY" }	                  
	                     ];

	/*$('#transactionDate,#dateOfBirth, #dateOfLeaving, #dateOfJoining').bootstrapMaterialDatePicker({
        time : false,
        Button : true,
        format : "DD/MM/YYYY",
        clearButton: true
    });*/
	
	$('#dateOfBirth, #dateOfJoining').datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown'
	    	  
	    });
	
	$('#transactionDate,#dateOfLeaving').datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown',
	      maxDate: new Date()
	    	  
	    });
	
    $scope.readonly = false;
    $scope.updateBtn = false;
    $scope.saveBtn = true;
    $scope.currentHistoryBtn = false;
    $scope.resetBtn = true;
    $scope.cancelBtn = false;
    $scope.addrHistory = false;
    $scope.transactionList = false;
    $scope.gridButtons = true;
    $scope.workerVo = new Object();
    $scope.workerVo.workerId = 0;
    $scope.workerVo.gender="U";	
	$scope.workerVo.maritalStatus="Unknown";
    $scope.workerVo.isActive = 'I';
   // $scope.disableStatus = true;
    $scope.transactionDate = $filter('date')(new Date(),"dd/MM/yyyy");
    
    $cookieStore.put('workerId',"");
	$cookieStore.put('workerCode',"");
	$cookieStore.put('workerName',"");
	$cookieStore.put('WorkercountryName',"");
	$cookieStore.put('WorkercustomerName', "");
	$cookieStore.put('WorkercompanyName',"");
	$cookieStore.put('WorkervendorName', "");
	$cookieStore.put('WorkercustomerId', "");
	$cookieStore.put('WorkercompanyId',"");
	$cookieStore.put('workerInfoId',"");
	$cookieStore.put('verificationVendorId','');
    $cookieStore.put('verificationworkerId','');
    $cookieStore.put('WorkerCountryId','');
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
    
    var message = "";
    var flag= false;
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
        		
        		
        	} else if(type == 'customerList'){        		
        		$scope.customerList = response.data.customerList;
        		if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
	                $scope.workerVo.customerId=response.data.customerList[0].customerId;		                
	                $scope.customerChange();
   		        }
        		// for button views
            	if($scope.buttonArray == undefined || $scope.buttonArray == '')
            	$scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Worker Details'}, 'buttonsAction'); 
                
        	
        	}else if(type == 'customerChange'){	        		
        		$scope.companyList = response.data; 
        		
        		if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	                $scope.workerVo.companyId = response.data[0].id;
	                $scope.companyChange();
        		}
        	
        	}else if (type == 'companyChange') {
        		$scope.countriesList = response.data.countriesList; 
                $scope.vendorList = response.data.vendorList;
                if( response.data != undefined && response.data.vendorList != "" && response.data.vendorList.length == 1 ){
	                $scope.workerVo.vendorId = response.data.vendorList[0].id;	              
	                }
                $scope.companyProfile = response.data.companyProfile[0];
                $scope.workerVo.countryId = response.data.countriesList[0].id;
                $scope.vendorChange();
           
        	}else if(type == 'countryList'){
            	$scope.list_Country = response.data.countryList;
            	$scope.shiftsList = response.data.availableShifts;
            
            }else if(type == 'saveWorkerDetails'){            	
            	if($scope.saveBtn == true && response.data.id > 0){
                	//$location.path('/workerDetails/'+response.data.id);
                	$cookieStore.put('workerInfoId',response.data.id);
                	$cookieStore.put('workerCode',$scope.workerVo.workerCode);
                	$cookieStore.put('workerName',$scope.workerVo.firstName+" "+$scope.workerVo.lastName);
                	$cookieStore.put('WorkercountryName', $('#countryId option:selected').html());
                	$cookieStore.put('WorkercustomerName', $('#customerId option:selected').html());
                	$cookieStore.put('WorkercompanyName', $('#companyId option:selected').html());
                	$cookieStore.put('WorkervendorName', $('#vendorId option:selected').html());
                	
                	$cookieStore.put('WorkercustomerId', $scope.workerVo.customerId);
                	$cookieStore.put('WorkercompanyId',$scope.workerVo.companyId); 
                	$cookieStore.put('WorkerCountryId',$scope.workerVo.countryId);                	
                	$("#loader").hide();
                	$("#workerForm").show();                	
                	//$location.path('/workerVerification/'+response.data.id); 
                	$location.path('/workerFamilyDetails');
                	$scope.Messager('success', 'success', 'Worker Details Saved Successfully',buttonDisable);
                	//$scope.Messager('success', 'success', 'Worker Saved Successfully');                	
                	               	
	            	/*var res = confirm("Do you want to Verify the worker qualification/eligibity? ");
	            	if(res){
	            		location.href = "#/workerVerification/"+$cookieStore.get('workerInfoId');
	            	}else{
	            		$location.path('/workerDetails/'+response.data.id);
	            	}*/
                }else{
                	$("#loader").hide();
                	$("#workerForm").show();
                	$scope.Messager('error', 'Error', 'Technical Issue occurred while saving. Please try again after some time');                	
                
                }
            	
            }else if (type == 'countryChangePerm') {	            	
                $scope.statesListPerm = response.data;
            
            }else if (type == 'countryChangePre') {	            	
                $scope.statesListPre = response.data;
            } else if(type == 'validateWorkerCode'){
            	//alert(angular.toJson(response.data));
            	if(parseInt(response.data.id) > 0){
            		 $scope.Messager('error', 'Error', 'Worker Code is already available..',buttonDisable);            		
            	}else{
            		$scope.validateDuplicateWorker(buttonDisable);
            	}
            }else if(type == 'validateDuplicateWorker'){
            	if(parseInt(response.data.id) > 0){
            		if(response.data.name == 'Y'){
            			 $scope.Messager('error', 'Error', 'Worker already available with entered worker name and Data of Birth',buttonDisable);
            		}else if(response.data.name == 'I' || response.data.name == 'N'){
            			var s =	confirm('Worker already available with entered worker name and Data of Birth, Do you want to proceed...?');
            			if(s){
            				$scope.saveWorkerDetails(buttonDisable);
            			}else{
            				return null;
            			}
            		}
            	}else if(response.data.id == 0){
            		$scope.saveWorkerDetails(buttonDisable);
            	}
            }else if(type == 'vendorChange'){
            	//alert(angular.toJson(response.data));
            	if(response.data.workersLimit != undefined && response.data.workersLimit != null){
            		if(response.data.workersLimit.id == 0 ){
                		flag = true;
                		message=response.data.workersLimit.name
                		$scope.Messager('error', 'Error', response.data.workersLimit.name,buttonDisable);
                	}else{
                		if(response.data.MaxWorkerCode != undefined && response.data.MaxWorkerCode != null  ){
                			$scope.workerVo.workerCode = response.data.MaxWorkerCode.name;
                		}
                		flag = false;
                		message=null;
                	}
            	}
            	
            }else if( type ==  'schemaName'){   
     		   if(response.data.name != undefined && response.data.name != null && response.data.name != ''){
    			   $scope.workerVo.schemaName = response.data.name;   
    		   }else{
    			   $scope.workerVo.schemaName = 'null';
    		   }    		  
           }
        },function () {
        	  $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
        	           
        });
    }      
    
    $scope.getData('vendorController/getCustomerNamesAsDropDown.json', { customerId: $cookieStore.get('customerId')}, 'customerList')
    
    $scope.getData('workerController/countriesListAndShiftsList.json',{customerDetailsId:$cookieStore.get('customerId'),companyDetailsId:$cookieStore.get('companyId')}, 'countryList');
    
   
    $scope.getData("reportController/getSchemaNameByUserId.json",{userId : $cookieStore.get('userId') } ,"schemaName" );
    
    
    $scope.customerChange = function () { 
    	$scope.companyList ="";
    	$scope.countriesList = ""; 
        $scope.vendorList = "";    
        
    	if($scope.workerVo.customerId != undefined && $scope.workerVo.customerId != ""){
    		$scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.workerVo.customerId ,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.workerVo.companyId != undefined ?  $scope.workerVo.companyId : 0  }, 'customerChange');
    	}
    };
    
    
    
    $scope.companyChange = function () {
    	$scope.countriesList = ""; 
        $scope.vendorList = ""; 
        $scope.companyProfile= "";
    	if($scope.workerVo.companyId != undefined && $scope.workerVo.companyId != ""){
    		$scope.getData('workerController/getVendorAndWorkerNamesAsDropDowns.json', { customerId: $scope.workerVo.customerId,companyId:($scope.workerVo.companyId != undefined ? $scope.workerVo.companyId :0),vendorId:($cookieStore.get('vendorId') != null && $cookieStore.get('vendorId') != "") ? $cookieStore.get('vendorId') : $scope.workerVo.vendorId != undefined ? $scope.workerVo.vendorId : 0 }, 'companyChange');
    	}
    };
    
    $scope.vendorChange = function () {
    	
    	if($scope.workerVo.vendorId != undefined && $scope.workerVo.vendorId != ""){

    		$scope.getData('workerController/validateWorkerLimit.json', { customerId: $scope.workerVo.customerId,companyId:($scope.workerVo.companyId != undefined ? $scope.workerVo.companyId :0),vendorId:($cookieStore.get('vendorId') != null && $cookieStore.get('vendorId') != "") ? $cookieStore.get('vendorId') : $scope.workerVo.vendorId != undefined ? $scope.workerVo.vendorId : 0,isActive: 'Y'}, 'vendorChange');

    	}
    };
    
    $scope.countryChangePerm = function () {
      	if($scope.workerVo.permanentCountryId != null && $scope.workerVo.permanentCountryId != ""){
    		$scope.getData('CommonController/statesListByCountryId.json', { countryId: $scope.workerVo.permanentCountryId }, 'countryChangePerm');
    	}
    };
	    
    $scope.countryChangePre = function () {
      	if($scope.workerVo.presentcountryId != null && $scope.workerVo.presentcountryId != ""){
    		$scope.getData('CommonController/statesListByCountryId.json', { countryId: $scope.workerVo.presentcountryId }, 'countryChangePre');
    	}
    };
	    
    $scope.validateWorkerCode = function($event){
    	if($('#workerForm').valid()){
    		if($('#dateOfLeaving').val() != undefined && $('#dateOfLeaving').val() != null && $('#dateOfLeaving').val() != '' && $scope.workerVo.isActive == 'N'){
				$scope.workerVo.dateOfLeaving =  moment( $('#dateOfLeaving').val(), 'dd/MM/yyyy').format('YYYY-MM-DD'); 
			} else{
				$scope.workerVo.dateOfLeaving = null;
			}
    		$scope.workerVo.workerId = 0
       	 $scope.getData('workerController/validateWorkerCode.json', angular.toJson($scope.workerVo) , 'validateWorkerCode',angular.element($event.currentTarget));
       		}
    }
    
    
    $scope.validateDuplicateWorker = function(buttonDisable){    
    	$scope.workerVo.dateOfBirth = moment($('#dateOfBirth1').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
    	$scope.getData('workerController/validateDuplicateWorker.json', angular.toJson($scope.workerVo) , 'validateDuplicateWorker',buttonDisable);
    } 
    
    $scope.saveWorkerDetails =function($event){
    	var regex = new RegExp("[A-Za-z]{3}(P|p|C|c|H|h|F|f|A|a|T|t|B|b|L|l|J|j|G|j)[A-Za-z]{1}[0-9]{4}[A-Za-z]{1}$");
    	
    	if($('#workerForm').valid()){
    		
    		$scope.workerVo.dateOfBirth = moment($('#dateOfBirth1').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
			$scope.workerVo.dateOfJoining = moment($('#dateOfJoining').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
			$scope.workerVo.dateOfLeaving = moment($('#dateOfLeaving').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
			
    		if(!moment($('#dateOfBirth1').val(), 'DD/MM/YYYY', true).isValid()) {
    			$scope.Messager('error', 'Error', 'Please enter valid Date of birth');
    		}else if(flag){
    			$scope.Messager('error','Error',message);
    		}
    		else if($scope.workerVo.panNumber != undefined && $scope.workerVo.panNumber != null && $scope.workerVo.panNumber !='' ){
    	  		$scope.Messager('error', 'Error', 'Please enter valid pan number');
    	  		
    	  	}else if((($scope.workerVo.permanentAddressLine1 == undefined &&  $scope.workerVo.permanentAddressLine1 == "") || ($scope.workerVo.permanentCountryId == undefined || $scope.workerVo.permanentCountryId == "" ))&& 
    	  			(($scope.workerVo.presentAddressLine1 == undefined && $scope.workerVo.presentAddressLine1 != "") || ($scope.workerVo.presentcountryId == undefined || $scope.workerVo.presentcountryId == "" ))){
    	  		$scope.Messager('error', 'Error', 'Please enter permanent or local address');
    	  		
    	  	}/*else if(($scope.workerVo.permanentAddressLine1 == undefined &&  $scope.workerVo.permanentAddressLine1 == "") || ($scope.workerVo.permanentCountryId == undefined || $scope.workerVo.permanentCountryId == "" )){
    	  		$scope.Messager('error', 'Error', 'Please enter permanent address');
    	  		
    	  	}*/else if(($scope.workerVo.presentAddressLine1 == undefined && $scope.workerVo.presentAddressLine1 != "") || ($scope.workerVo.presentcountryId == undefined || $scope.workerVo.presentcountryId == "" )  && $scope.workerVo.isSameAddress == false){
    	  		$scope.Messager('error', 'Error', 'Please enter local address');
    	  		
    	  	}
    		else if($scope.years < $scope.companyProfile.minAge) {
    	  		$scope.Messager('error', 'Error', 'Worker is below minimum age limit. Please enter valid date of birth');
    	  	
    	  	}else if($scope.years > $scope.companyProfile.maxAge) {
    	  		$scope.Messager('error', 'Error', 'Worker is over maximum age limit. Please enter valid date of birth');
    	  	
    	  	}else if($('#dateOfJoining').val() != '' && $('#dateOfJoining').val() != undefined && $('#dateOfBirth1').val() != '' && $('#dateOfBirth1').val() != undefined && !moment($scope.workerVo.dateOfJoining).isSameOrAfter($scope.workerVo.dateOfBirth)){
	        		$scope.Messager('error', 'Error', 'Date of Joining should not be earlier than Date Of Birth');
		    }else if($('#dateOfJoining').val() != '' && $('#dateOfJoining').val() != undefined && $('#dateOfLeaving').val() != '' && $('#dateOfLeaving').val() != undefined && !moment($scope.workerVo.dateOfLeaving).isSameOrAfter($scope.workerVo.dateOfJoining)){
	        	$scope.Messager('error', 'Error', 'Date of Leaving should not be earlier than Date Of Joining');
	        	
		    }else if(($scope.workerVo.ifscCode == undefined || $scope.workerVo.ifscCode == '' || $scope.workerVo.ifscCode == null ||  $scope.workerVo.ifscCode == 'null') && ($scope.workerVo.branchName == undefined || $scope.workerVo.branchName == '' || $scope.workerVo.branchName == null || $scope.workerVo.branchName == 'null')){
		    	
		    	$scope.Messager('error', 'Error', 'Enter IFSC Code or Branch Name');	
		    	
		    }else{  
    	  		var x= $('#vendorId option:selected').text();   
    	  		var res = x.slice(x.indexOf("(")+1, x.indexOf(")"));    	  		
    	  		//alert(res+":::"+'('+($scope.workerVo.workerCode));
    	  		//alert(res.trim().substring(1,4));    	  		
   			 	/*if( ('('+$scope.workerVo.workerCode.trim()).indexOf(res) != '0' ){
   			 		$scope.Messager('error', 'Error', 'Worker Code Should Start with the Characters mentioned in Vendor Name parentheses ..');

   			 		return;
   			 	}*/
    	  		if( res.trim() !=  $scope.workerVo.workerCode.trim().substring(0,3) ){
			 		$scope.Messager('error', 'Error', 'Worker code should start with the first 3 characters mentioned in vendor name parentheses ...');

			 		return;
			 	}
   			 	
    	  		$scope.workerVo.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
    			$scope.workerVo.dateOfBirth = moment($('#dateOfBirth1').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
    			
    			$scope.workerVo.dateOfJoining = moment($('#dateOfJoining').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
    			//alert($('#dateOfLeaving').val() +":::"+$scope.workerVo.isActive);
    			if($('#dateOfLeaving').val() != undefined && $('#dateOfLeaving').val() != null && $('#dateOfLeaving').val() != '' && $scope.workerVo.isActive == 'N'){
    				$scope.workerVo.dateOfLeaving =  moment( $('#dateOfLeaving').val(), 'dd/MM/yyyy').format('YYYY-MM-DD'); 
    			} else{
    				$scope.workerVo.dateOfLeaving = null;
    			}
   			 	
    			$scope.workerVo.createdBy = $cookieStore.get('createdBy'); 
   			 	$scope.workerVo.modifiedBy = $cookieStore.get('modifiedBy');
	    	   // $scope.workerVo.workerId = 0;

  
	    	    


	    	    if($scope.saveBtn == true){
   			 		$scope.workerVo.workerInfoId=0;
   			 	}
   			 	
   			 	var formData = new FormData();
   			 	$scope.workerVo.age = $scope.years+" Years "+$scope.months+" Months";
   			 	$scope.workerVo.identityList = $scope.result;
   			 	
			    if(($scope.workerVo.identityList == undefined || $scope.workerVo.identityList == null  || $scope.workerVo.identityList == '') &&( $scope.image == null || $scope.image == "")){
   			 		$scope.getData('workerController/saveWorkerWithoutFiles.json', angular.toJson($scope.workerVo), 'saveWorkerDetails',angular.element($event.currentTarget)); 
   			 		$("#loader").show();
   			 		$("#workerForm").hide();
   			 	}else{
   			 		angular.forEach($scope.result, function (value, key) {        	 
		        	   formData.append("file", value.fileData); 
		        	   formData.append("filename", value.fileName);    
   			 		});
   			 		
   			 		if($scope.image != "" && $scope.image != null){
   			 			formData.append("file",$scope.theFile1);
   			 		}
	   			 	
   			 		formData.append('name',angular.toJson($scope.workerVo));   
   			 		//alert(angular.toJson($scope.workerVo));
   			 		$http.post('workerController/saveOrUpdateWorkerDetails.json', formData, {
   			 			transformRequest: angular.identity,
   			 			headers: {'Content-Type': undefined}
   			 		}).success(function(response,data){
   			 			//$scope.Messager('success', 'success', 'File Upload Success',angular.element($event.currentTarget));
		 				if(response.id > 0){
		 					//alert(angular.toJson(response.id));
			            	$scope.Messager('success', 'success', 'Worker Saved Successfully');
			            	//$location.path('/workerVerification/'+angular.toJson(response.id));
			            	$location.path('/workerFamilyDetails');
			            	$cookieStore.put('workerInfoId',response.id);			            	
		                	$cookieStore.put('workerCode',$scope.workerVo.workerCode);
		                	$cookieStore.put('workerName',$scope.workerVo.firstName+" "+$scope.workerVo.lastName);
		                	$cookieStore.put('WorkercountryName', $('#countryId option:selected').html());
		                	$cookieStore.put('WorkercustomerName', $('#customerId option:selected').html());
		                	$cookieStore.put('WorkercompanyName', $('#companyId option:selected').html());
		                	$cookieStore.put('WorkervendorName', $('#vendorId option:selected').html());
		                	
		                	$cookieStore.put('WorkercustomerId', $scope.workerVo.customerId);
		                	$cookieStore.put('WorkercompanyId',$scope.workerVo.companyId); 
		                	$cookieStore.put('WorkerCountryId',$scope.workerVo.countryId);
		                	
			            	 $cookieStore.put('verificationVendorId',$scope.workerVo.vendorId);
			                 $cookieStore.put('verificationworkerId',$scope.workerVo.workerId);
			            	/*var res = confirm("Do you want to Verify the worker qualification/eligibity? ");
			            	if(res){
			            		location.href = "#/workerVerification/"+$cookieStore.get('workerInfoId');
			            	}else{
			            		$location.path('/workerDetails/'+response.id);
			            	}*/
			            	
		 				}else if(response.id < 0){
	            		    $scope.Messager('error', 'Error', response.name);
	            		    $scope.workerVo.transactionDate =  $filter('date')( $('#transactionDate').val(), 'DD/MM/YYYY');
		 				}else{
		            	    $scope.Messager('error', 'Error', "Technical issues occurred. Please try again after some time.");
		            	    $scope.workerVo.transactionDate =  $filter('date')( $('#transactionDate').val(), 'DD/MM/YYYY');
		 				}
   			 		}).error(function(data,status,headers,config){
   			 			$scope.Messager('error', 'Error', 'Technical Issue while saving. Please try again after some time');
   			 			$scope.workerVo.transactionDate =  $filter('date')( $('#transactionDate').val(), 'DD/MM/YYYY');
   			 		});
   			 	$("#loader").show();
   			 	$("#workerForm").hide();
   			 	}
    		}
    	}
    };
    		
	   
    $scope.years = '';
    $scope.months = '';
	   

    $scope.dateOfBirthValidaton = function($event){
    	
    	if(!moment($('#dateOfBirth1').val(), 'DD/MM/YYYY', true).isValid()) {
			$scope.Messager('error', 'Error', 'Please enter valid Date of birth');
		}   
    	
    	else if($('#dateOfBirth1').val() != '' || $event != ''){
			 if(moment($('#dateOfBirth1').val(), 'DD/MM/YYYY', true).isValid()) {
			 var d1 = new Date(moment(new Date(), 'DD/MM/YYYY').format('YYYY-MM-DD'));
			 var d2 = new Date(moment($('#dateOfBirth1').val(), 'DD/MM/YYYY').format('YYYY-MM-DD'));		
			
			 var dy,dm,dd;
			 
			    
		 	dy = d1.getYear()  - d2.getYear();
		 	dm = d1.getMonth() - d2.getMonth();
		 	dd = d1.getDate()  - d2.getDate();
		    
		    if (dd < 0) { dm -= 1; dd += 30; }
		    if (dm < 0) { dy -= 1; dm += 12; }
			    
	 
			 $scope.years= dy;
			 $scope.months = dm;
			 $scope.$apply(function(){
				 $scope.workerVo.age = $scope.years+" Years"+$scope.months+" Months";

			 });
			 //$scope.workerVo.age = $scope.years+" Years"+$scope.months+" Months";
		 }
		}
		 
	};  

			

	   
	$scope.plusIconAction = function(){
		$scope.identity =  new Object();
    	$('#fileName').val("");
    	$scope.theFile="";
    	$scope.popUpSave = true;
    	$scope.popUpUpdate = false;
    	
    };
	
    $scope.saveChanges = function(){
    	var status = false;
    	angular.forEach($scope.result, function(value, key){	
		      if(value.identificationType == $scope.identity.identificationType && value.countryId == $scope.identity.country){
		    	  $scope.Messager('error', 'Error', 'This identification already added',true); 
		    	  status = true;			    		
		      }
    	});	
	
    //	alert($scope.identity.identificationNumber);
    	if(status){
		   $scope.Messager('error', 'Error', 'This identification already added',true); 
		   
    	}else if(!status && $scope.identity != undefined && $scope.identity != '' 
		   			&&  $scope.identity.country != undefined && $scope.identity.country != ''
				    &&  $scope.theFile.name != undefined && $scope.theFile.name != null && $scope.theFile.name != ''
				    &&  $scope.identity.identificationType != undefined && $scope.identity.identificationType != null && $scope.identity.identificationType != '' && $scope.identity.identificationNumber != null && $scope.identity.identificationNumber != undefined && $scope.identity.identificationNumber != ''){
    		$scope.result.push({
			    'countryId':$scope.identity.country,
         		'countryName':$('#country option:selected').html(),  		
         		'identificationType':$('#idType option:selected').html(),
         		'identificationNumber':$scope.identity.identificationNumber,
         		'fileName':$scope.theFile.name,
         		'fileData':$scope.theFile
     	    });   
		   
		    $('div[id^="identificationType"]').modal('hide');
		    $scope.popUpSave = true;
		    $scope.popUpUpdate =false;
	    }else if($scope.identity.country == undefined || $scope.identity.country == ''){
		   $scope.Messager('error', 'Error', 'Please select country',true); 
		   
	    }else if($scope.theFile.name == undefined || $scope.theFile.name == '' && $scope.theFile.name == null){
		   $scope.Messager('error', 'Error', 'Please upload the document'); 
		   
	    }else if($scope.identity.identificationNumber == null || $scope.identity.identificationNumber == undefined || $scope.identity.identificationNumber == ''){
			   $scope.Messager('error', 'Error', 'Please Enter Identification Number'); 
		}else {
		   $scope.Messager('error', 'Error', 'Please select identification type'); 
	   }
    };
	    
    // To get the file name
    $scope.getFileDetails = function (e) {  
        $scope.$apply(function () {
             $scope.theFile = e.files[0];
        });
    };
	      
    $scope.getImageDetails = function (e) {  
    	//alert(2313);
        $scope.$apply(function () {
             $scope.theFile1 = e.files[0];
        });
        $scope.workerVo.imageName = $scope.theFile1.name;
        //alert($scope.workerVo.imageName);
    };
    
    $scope.Delete = function($event){    	
	   var del = confirm('Are you sure you want to delete the identification type  '+ $scope.result[$($event.target).parent().parent().index()].identificationType+ ' ?');
	   if (del) {
		   $scope.result.splice($($event.target).parent().parent().index(),1);
		   $('#fileName').val("");
	   }
	   $scope.popUpSave = true;
       $scope.popUpUpdate = false;
    	  
    };
		       
     $scope.Edit = function($event){    	
      	var data =  $scope.result[$($event.target).parent().parent().index()];
      	$scope.identity.country = data.countryId;
      	$scope.identity.identificationType = data.identificationType;
      	$('#fileName').val(data.fileName);
      	$scope.popUpSave = false;
      	$scope.popUpUpdate =true;
     };
		          
	 // To update identification table         
     $scope.updateChanges= function($event){    
    	 $scope.result.splice($($event.target).parent().parent().index(),1);    	
	   	 var status = false;
	   	 angular.forEach($scope.result, function(value, key){	
		     if(value.identificationType == $scope.identity.identificationType && value.countryId == $scope.identity.country){
		    	 $scope.Messager('error', 'Error', 'This identification already added',true);  
		    	  status = true;			    		
		     }
		 });	
	   	 
	   	 if(status){
			   $scope.Messager('error', 'Error', 'This identification already added',true); 
		 }else if(!status && $scope.identity != undefined && $scope.identity != ''
					&&  $scope.identity.country != undefined && $scope.identity.country != ''
				    &&  $scope.theFile.name != undefined && $scope.theFile.name != null && $scope.theFile.name != ''
				    &&  $scope.identity.identificationType != undefined && $scope.identity.identificationType != null && $scope.identity.identificationType != ''){
       		   $scope.result.push({
       			   		'countryId':$scope.identity.country,
                		'countryName':$('#country option:selected').html(),  		
                		'identificationType':$('#idType option:selected').html(),
                		'fileName':$scope.theFile.name,
                 		'fileData':$scope.theFile
               });   
       		   $('div[id^="identificationType"]').modal('hide');
       		   $scope.popUpSave = true;
       		   $scope.popUpUpdate =false;
       	 }else if($scope.identity.country == undefined && $scope.identity.country == ''){
			   $scope.Messager('error', 'Error', 'Please select country',true); 
		 }else if($scope.theFile.name == undefined && $scope.theFile.name == '' && $scope.theFile.name == null){
			   $scope.Messager('error', 'Error', 'Please upload the document'); 
		 }else {
			   $scope.Messager('error', 'Error', 'Please select identification type'); 
		 }
      };
	      
	      
      // To download the file
      $scope.fun_download = function(event,fileName){
    	  var file = document.getElementById("fileName").files[0];
    	  if (file) {
    	      var reader = new FileReader();
    	      reader.readAsArrayBuffer(event);
    	      reader.onload = function (evt) {
    	    	  var blob = new Blob([evt.target.result], {type: 'application/octet-stream'});
  			      saveAs(blob,fileName);
    	      }
    	      reader.onerror = function (evt) {
    	    	  $scope.Messager('error', 'Error', 'Failed to download ');
    	      }
    	  }
      };
	      
      //To save Permanent address
      $scope.saveAddress = function(){
    	  if(($scope.workerVo.permanentAddressLine1 != undefined &&  $scope.workerVo.permanentAddressLine1 != "" && $scope.workerVo.permanentAddressLine1 != null) && ($scope.workerVo.permanentCountryId != undefined && $scope.workerVo.permanentCountryId != "" && $scope.workerVo.permanentCountryId != null )){
    		 // $scope.workerVo.isSameAddress = false;
    		  $('#localAdressAdd').show();
    		  $scope.labelName1 = "View/Edit";
    		  $('div[id^="myModal1"]').modal('hide');
    	  }else{
    		  $scope.Messager('error', 'Error', 'Please fill required fields');
    	  }
      };
      
      // To save Local Address
      $scope.savePresent = function(){
    	  if(($scope.workerVo.presentAddressLine1 != undefined &&  $scope.workerVo.presentAddressLine1 != "" && $scope.workerVo.presentAddressLine1 != null) && ($scope.workerVo.presentcountryId != undefined && $scope.workerVo.presentcountryId != "" && $scope.workerVo.presentcountryId != null )){
    		  $scope.labelName2 = "View/Edit";
    		  $('div[id^="myModal2"]').modal('hide');
    	  }else{
    		  $scope.Messager('error', 'Error', 'Please fill required fields');
    	  }
      };
      
      $scope.familyDetails = function(){
    	  if($scope.saveBtn || $scope.correcttHistoryBtn){
    		  $scope.Messager('error', 'Error', 'Save the worker details before adding the Family information');
    	  }else{
    		 location.href ='#/workerFamilyDetails';    	  
    	  }
      };
      
      $scope.fun_localAddress = function(){  
    	 // alert($scope.workerVo.isSameAddress);
    	  if($scope.workerVo.isSameAddress){
	    	  if(($scope.workerVo.permanentAddressLine1 != undefined &&  $scope.workerVo.permanentAddressLine1 != "" && $scope.workerVo.permanentAddressLine1 != null) && ($scope.workerVo.permanentCountryId != undefined && $scope.workerVo.permanentCountryId != "" && $scope.workerVo.permanentCountryId != null )){
	    		// alert();
	    		  $('#localAdressAdd').hide();
	    		  $scope.workerVo.presentAddressLine1 = $scope.workerVo.permanentAddressLine1;
	    		  $scope.workerVo.presentAddressLine2 = $scope.workerVo.permanentAddressLine2;
	    		  $scope.workerVo.presentAddressLine3 =$scope.workerVo.permanentAddressLine3;
	    		  $scope.workerVo.presentStateId = $scope.workerVo.permanentStateId;
	    		  $scope.workerVo.presentPinCode = $scope.workerVo.permanentPinCode ;
	    		  $scope.workerVo.presentcountryId = $scope.workerVo.permanentCountryId;
	    		  $scope.workerVo.presentCity = $scope.workerVo.permanentCity;    
	    		  
	    		  $scope.labelName1 = "View/Edit";
	    	  } else{
	    		  $('#localAdressAdd').show();
	    		  $scope.labelName1 = "Add";
	    		  $scope.labelName2 = "Add";
	    		  $scope.Messager('error', 'Error', 'Enter Permanent Address');
	    		  $scope.workerVo.isSameAddress = false;
	    	  }

    	 

    	  }else{
    		  $('#localAdressAdd').show();
    		  $scope.labelName2 = "Add"
    		 $scope.workerVo.presentAddressLine1 =null;
    		 $scope.workerVo.presentAddressLine2 = null;
    		 $scope.workerVo.presentAddressLine3 = null;
    		 $scope.workerVo.presentStateId ="";
    		 $scope.workerVo.presentPinCode  =null;
    		 $scope.workerVo.presentcountryId ="";
    		 $scope.workerVo.presentCity =null; 
    	  }
      }
      
      $scope.fun_close = function(){
    	  if(($scope.workerVo.permanentAddressLine1 != undefined &&  $scope.workerVo.permanentAddressLine1 != "" && $scope.workerVo.permanentAddressLine1 != null) && ($scope.workerVo.permanentCountryId != undefined && $scope.workerVo.permanentCountryId != "" && $scope.workerVo.permanentCountryId != null )){
    	  }else{
    		  $scope.labelName1 = "Add";
    	  }
    	  
    	  if(($scope.workerVo.presentAddressLine1 != undefined &&  $scope.workerVo.presentAddressLine1 != "" && $scope.workerVo.presentAddressLine1 != null) && ($scope.workerVo.presentcountryId != undefined && $scope.workerVo.presentcountryId != "" && $scope.workerVo.presentcountryId != null )){
    	  }else{
    		  $scope.labelName2 = "Add";
    	  }
      }
      
      $scope.workerFamilyDetails =function(){
       	  $scope.Messager('error', 'Error', 'Enter worker Details, Then only you are allowed to enter Worker Family details.');
    	  
         };
         
         $scope.workerMedicalExamination = function(){
        	 $scope.Messager('error', 'Error', 'Enter worker Details, Then only you are allowed to enter Medical Examination details.');
         }
         
         $scope.workerPoliceVerification= function(){
        	 $scope.Messager('error', 'Error', 'Enter worker Details, Then only you are allowed to enter Medical Examination details.');
         }
      
      $scope.workerVerification = function(){
    	  $scope.Messager('error', 'Error', 'Enter worker Details, Then only you are allowed to enter verification details.');
    	  
      };
      
  	 $scope.workerJobDetails= function(){
   	  $scope.Messager('error', 'Error', 'Enter worker Details, Then only you are allowed to enter Worker Job Details.');
	  
     };
     
  	 $scope.workerbadgeGeneration =function(){
   	  $scope.Messager('error', 'Error', 'Enter worker Details, Then only you are allowed to enter Worker Badge details.');
	  
     };
     
     $scope.statusChange = function(){
    	 
     }
     
}]);







workerDetailsControllers.controller('workerDetailsEditCtrl', ['$scope','$http', '$resource','$routeParams','$filter','myservice','$cookieStore','$location', function ($scope,$http, $resource, $routeParams,$filter,myservice,$cookieStore,$location) {
	  
	$scope.popUpSave = true;
	$scope.popUpUpdate = false;
	$scope.identity =  new Object();
	$scope.companyProfile = new Object();
	$scope.result = [];
	$scope.workerInfoId = $routeParams.workerInfoId;
	$scope.status1 = new Object();
    //initialsie the material-deisgn
    $.material.init();
    
    
    
    
   /*  
    $('#transactionDate,#dateOfBirth, #dateOfLeaving, #dateOfJoining').bootstrapMaterialDatePicker
               ({
                   time: false,
                   clearButton: true,
                   format : "DD/MM/YYYY"
               });*/
    $('#dateOfBirth, #dateOfJoining').datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown'
	    	  
	    });
  
  
  $('#transactionDate,#dateOfLeaving').datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown',
	      maxDate: new Date()
	    	  
	    });
    
    if($cookieStore.get('roleNamesArray').includes("Customer Audit Admin")){ 
    	$scope.wiladminBoolean = false;
    }else{
    	$scope.wiladminBoolean = true;
    }
    
  
    
    $('#bankName').removeAttr("required");    
    $('#bnkNameLabel').removeClass("required");
    
    $('#accountNumber').removeAttr("required");    
    $('#accountNumberLable').removeClass("required");
    
    $('#accountHolderName').removeAttr("required");    
    $('#accountHolderNameLabel').removeClass("required");
    
    $('#ifscCode').removeAttr("required");    
    $('#ifscCodeLable').removeClass("required");
    
    $('#branchName').removeAttr("required");    
    $('#branchNameLabel').removeClass("required");
    
    
    
    
 
    $scope.blood_group_list = [{ "id":"A+", "name":"A+"}, { "id":"A-", "name":"A-"},
	                            { "id":"AB+", "name":"AB+"}, { "id":"AB-", "name":"AB-"},
	                            { "id":"A1+", "name":"A1+"}, { "id":"A1-", "name":"A2-"},
	                            { "id":"A2+", "name":"A2+"}, { "id":"A1-", "name":"A2-"},
	                            { "id":"A1B+", "name":"A1B+"}, { "id":"A1B-", "name":"A1B-"},
	                            { "id":"A2B+", "name":"A2B+"}, { "id":"A2B-", "name":"A2B-"},
	                            { "id":"B+", "name":"B+"}, { "id":"B-", "name":"B-"},
	                            { "id":"O+", "name":"O+"}, { "id":"O-", "name":"O-"},
	                            { "id":"U", "name":"U"}, { "id":"Other", "name":"Other"}
	                            ];
	 
    $scope.marital_status_list = [{ "id":"Single", "name":"Single"},
	                            { "id":"Married", "name":"Married"},
	                            { "id":"Unmarried", "name":"Unmarried"}, 
	                            { "id":"Widowed", "name":"Widowed"}, 
	                            { "id":"Divorced", "name":"Divorced"}, 
	                            { "id":"Unknown", "name":"Unknown"}
	                            ];
			 
	$scope.id_types = [{ "id":"PAN Card", "name":"BRP"},
                      
                      { "id":"Passport", "name":"Passport"}, 
                      { "id":"Driving License", "name":"Driving License"}
                      ];
	
	$scope.reasonForStatus_list =[{ "id":"Debarred", "name":"Debarred"},
	                              { "id":"Suspended", "name":"Suspended"},
	                              { "id":"Left", "name":"Left"},
	                              { "id":"Absent for 30 days", "name":"Absent for 30 days"}
	                              ];
	
	
	$scope.weekList = [
	                     {"id":"MON","name" : "MONDAY" },
	                     {"id":"TUE","name" : "TUESDAY" },
	                     {"id":"WED","name" : "WEDNESDAY" },
	                     {"id":"THU","name" : "THURSDAY" },
	                     {"id":"FRI","name" : "FRIDAY" },
	                     {"id":"SAT","name" : "SATURDAY" },
	                     {"id":"SUN","name" : "SUNDAY" }	                  
	                     ];
	 
	$scope.readOnly = true;
	$scope.onlyRead = true;
    $scope.datesReadOnly = true;
    $scope.updateBtn = true;
    $scope.saveBtn = false;
    $scope.viewOrUpdateBtn = true;
    $scope.correcttHistoryBtn = false;
    $scope.resetBtn = false;      
    $scope.transactionList = false;
    $scope.gridButtons = false;
    $scope.cancelBtn = false;
    $scope.workerVo = new Object();
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
	    
    $scope.updateBtnAction = function (this_obj) {
    	//$scope.transactionDate = $filter('date')(new Date(),"dd/MM/yyyy");
        $scope.readOnly = false;
        $scope.onlyRead = true;
        $scope.datesReadOnly = false;
        $scope.updateBtn = false;
        $scope.saveBtn = true;
        $scope.viewOrUpdateBtn = false;
        $scope.correcttHistoryBtn = false;
        $scope.resetBtn = false;
        $scope.cancelBtn = true;
        $scope.addrHistory = true;
        $scope.transactionList = false;  
        $scope.gridButtons = true;
        $scope.cancelBtn = true;
        $scope.transactionDate = $filter('date')(new Date(),"dd/MM/yyyy");        
        $('.dropdown-toggle').removeClass('disabled');
    }

    $scope.viewOrEditHistory = function () {
        $scope.readOnly = false;
        $scope.onlyRead = true;
        $scope.datesReadOnly = false;
        $scope.updateBtn = false;
        $scope.saveBtn = false;
        $scope.viewOrUpdateBtn = false;
        $scope.correcttHistoryBtn = true;
        $scope.resetBtn = false;      
        $scope.transactionList = true;
        $scope.gridButtons = true;
        $scope.cancelBtn = false;
        $scope.getData('workerController/getTransactionDatesListForEditingWorkerDetails.json', { workerId: $scope.workerVo.workerId }, 'getTransactionDates');      
        
        $('.dropdown-toggle').removeClass('disabled');
    }
	    
    $scope.cancelButnAction = function(){
    	 $scope.readOnly = true;
         $scope.datesReadOnly = true;
         $scope.updateBtn = true;
         $scope.saveBtn = false;
         $scope.viewOrUpdateBtn = true;
         $scope.correcttHistoryBtn = false;
         $scope.resetBtn = false;      
         $scope.transactionList = false;
         $scope.gridButtons = false;
         $scope.cancelBtn = false;
         $scope.getData('workerController/getWorkerDetailsbyId.json', {customerId:"",workerInfoId :$routeParams.workerInfoId}, 'getWorkerDetailsFoCancel') ;
    }
	    
    $scope.years = '';
    $scope.months = '';
	   
	$scope.dateOfBirthValidaton = function($event){
		if($('#dateOfBirth1').val() != '' || $event != ''){			

			var d1 = new Date(moment(new Date(), 'DD/MM/YYYY').format('YYYY-MM-DD'));
			var d2 = new Date(moment($('#dateOfBirth1').val(), 'DD/MM/YYYY').format('YYYY-MM-DD'));		
			 
			var dy,dm,dd;
			 
			    
		 	dy = d1.getYear()  - d2.getYear();
		 	dm = d1.getMonth() - d2.getMonth();
			dd = d1.getDate()  - d2.getDate();
			    
			if (dd < 0) { dm -= 1; dd += 30; }
			if (dm < 0) { dy -= 1; dm += 12; }

			 
			 $scope.years= dy;
			 $scope.months = dm;		
			 $scope.workerVo.age = $scope.years+" Years"+$scope.months+" Months";
		 }
		 
	};  
	    var flag = false;
	    var message = null;
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
        		
        	}else if(type == 'getWorkerDetails'){ 
        		//alert(angular.toJson(response.data));
    			$scope.customerList = response.data.customerList;
    			$scope.list_Country = response.data.countryList;
    			//alert(angular.toJson(response.data.availableShifts))
    			$scope.shiftsList = response.data.availableShifts;

    			if( $scope.updateBtn  ){
	    			$scope.workerVo = response.data.workerDetails[0];
	    			$scope.status1 = response.data.workerDetails[0].isActive;
	    			if(response.data.workerDetails[0].isSameAddress == true){
	    				$('#localAdressAdd').hide();
	    			}
	    			if(response.data.workerDetails[0].permanentAddressLine1 != null && response.data.workerDetails[0].permanentAddressLine1 != undefined){
	    				 $scope.labelName1 = "View/Edit";
	    			}else{
	    				 $scope.labelName1 = "Add";
	    			}
	    			
	    			if(response.data.workerDetails[0].presentAddressLine1 != null && response.data.workerDetails[0].presentAddressLine1 != undefined){
	    				 $scope.labelName2 = "View/Edit";
	    			}else{
	    				 $scope.labelName2 = "Add";
	    			}
	    			
	    			if(response.data.workerDetails[0].file != null){
	    				$scope.image= response.data.workerDetails[0].file;
	    			}
	    			
	    			$('#dateOfBirth1').val($filter('date')( response.data.workerDetails[0].dateOfBirth, 'dd/MM/yyyy'));

	    			$scope.status1 = response.data.workerDetails[0].isActive;
	        		$scope.customerChange();
	        		$scope.companyChange();
	        		$scope.dateOfBirthValidaton($filter('date')( response.data.workerDetails[0].dateOfBirth, 'dd/MM/yyyy'));
	        		$scope.countryChangePerm();
	        		$scope.countryChangePre();        		
	    		}else{
	    			$scope.workerVo = response.data.workerDetails[0];  	    				
	    		}    			
    			
    			if($scope.workerVo.isActive == 'I'){
    				$scope.wiladminBoolean = true;
    			}
    			
    			if($scope.workerVo.isActive == 'I' && ($cookieStore.get("roleNamesArray").includes('Super Admin')  || $cookieStore.get("roleNamesArray").includes('Company Admin'))){
    				$scope.showProceedButton = true;
    			}else{
					$scope.showProceedButton = false; 
				}
    			
    			  if($cookieStore.get('roleNamesArray').includes("Customer Audit Admin") && $scope.workerVo.isActive == 'I'){
    				  $scope.workerCodeChange = false;
    			  }else{
    				  $scope.workerCodeChange = true;
    			  }
    			
    			/*if($scope.workerVo.isActive == 'I' && $cookieStore.get("roleNamesArray").includes('Worker Code Change')){
    				$scope.workerCodeChange = false;
    			}else{
    				$scope.workerCodeChange = true;
    			}*/
    			
    			if((response.data.workerDetails[0].identityList != undefined && response.data.workerDetails[0].identityList != null)){
    				$scope.result = response.data.workerDetails[0].identityList;
    			}else{
    				$scope.result =[];
    			}
    			
    			$scope.transactionDate = $filter('date')( response.data.workerDetails[0].transactionDate, 'dd/MM/yyyy'); 
    			$scope.workerVo.dateOfJoining = $filter('date')( response.data.workerDetails[0].dateOfJoining, 'dd/MM/yyyy');
    			$('#transactionDate').val($filter('date')( response.data.workerDetails[0].transactionDate, 'dd/MM/yyyy'));
    			$('#dateOfBirth1').val($filter('date')( response.data.workerDetails[0].dateOfBirth, 'dd/MM/yyyy'));
    			$('#dateOfJoining').val($filter('date')( response.data.workerDetails[0].dateOfJoining, 'dd/MM/yyyy'));
    			
    			if(response.data.workerDetails[0].dateOfLeaving != null && response.data.workerDetails[0].dateOfLeaving != "" && response.data.workerDetails[0].dateOfLeaving != undefined){
    				$scope.dateOfLeaving = response.data.workerDetails[0].dateOfLeaving != null ? $filter('date')( response.data.workerDetails[0].dateOfLeaving, 'dd/MM/yyyy') : null;
    				$('#dateOfLeaving').val($filter('date')( response.data.workerDetails[0].dateOfLeaving, 'dd/MM/yyyy'));
    			}
    			
    			$cookieStore.put('workerId',response.data.workerDetails[0].workerId);
            	$cookieStore.put('workerCode',$scope.workerVo.workerCode);
            	$cookieStore.put('workerName',$scope.workerVo.firstName+" "+$scope.workerVo.lastName);
            	$cookieStore.put('WorkercountryName',response.data.workerDetails[0].countryName);
            	$cookieStore.put('WorkercustomerName', response.data.workerDetails[0].customerName);
            	$cookieStore.put('WorkercompanyName',response.data.workerDetails[0].companyName);
            	$cookieStore.put('WorkervendorName', response.data.workerDetails[0].vendorName);
            	$cookieStore.put('WorkerCountryId',response.data.workerDetails[0].countryId);
            	
            	$cookieStore.put('WorkercustomerId', response.data.workerDetails[0].customerId);
            	$cookieStore.put('WorkercompanyId',response.data.workerDetails[0].companyId);
            	$cookieStore.put('WorkervendorId',response.data.workerDetails[0].vendorId);
            	$cookieStore.put('workerInfoId',response.data.workerDetails[0].workerInfoId);
            	if(response.data.workerDetails[0].workerId != undefined && response.data.workerDetails[0].workerId != null && parseInt(response.data.workerDetails[0].workerId) > 0){	
            		$scope.getData('workerController/getWorkerChildScreensData.json', {workerId: response.data.workerDetails[0].workerId}, 'getWorkerChildScreensData') 
            	}	      
            	
            	if($scope.buttonArray == undefined || $scope.buttonArray == '')
                	$scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Worker Details'}, 'buttonsAction');
        	}else if(type == 'customerChange'){	        		
        		$scope.companyList = response.data; 
        	}else if (type == 'companyChange') {	
        		$scope.countriesList = response.data.countriesList;
        		if($scope.countriesList.length ==1){        			
        			$scope.identity.country = $scope.countriesList[0].id;        			
        		}
                $scope.vendorList = response.data.vendorList;               
                $scope.companyProfile = response.data.companyProfile[0];
                $scope.workerVo.countryId = response.data.countriesList[0].id;
            }else if(type == 'statusChange'){
            	if(response.data.id == 0 ){
            		flag = true;
            		message=response.data.name
            		$scope.Messager('error', 'Error', response.data.name,buttonDisable);
            	}else{
            		flag = false;
            		message=null;
            	}
            }else if(type == 'saveWorkerDetails'){
            	
            	if($scope.saveBtn == true && response.data.id > 0){
            			
            		 $("#loader").hide();
            		 $("#workerForm").show();
                	//$location.path('/workerDetails/'+response.data.id);

                	$scope.Messager('success', 'Success', 'Worker Details Saved Successfully',buttonDisable);
                	//$location.path('/workerDetails/'+response.data.id);
                	//$location.path('/workerVerification/'+response.data.id);
                	$location.path('/workerFamilyDetails');
                	 $cookieStore.put('workerInfoId',response.data.id);
                	 $cookieStore.put('workerCode',$scope.workerVo.workerCode);
	            	 $cookieStore.put('verificationVendorId',$scope.workerVo.vendorId);
	                 $cookieStore.put('verificationworkerId',$scope.workerVo.workerId);
                	/*var res = confirm("Do you want to Verify the worker qualification/eligibity? ");
	            	//alert(res);
	            	if(res){
	            		
	            		location.href = "#/workerVerification/"+$cookieStore.get('workerInfoId');
	            	}else{
	            		$location.path('/workerDetails/'+response.data.id);
	            	}*/
                }else if($scope.correcttHistoryBtn){
                	$scope.Messager('success', 'Success', 'Worker Details Saved Successfully',buttonDisable);
                	 $("#loader").hide();
                	 $("#workerForm").show();
                }else{
                	 $("#loader").hide();
                	 $("#workerForm").show();
                	$scope.Messager('error', 'Error', 'Technical Issue occurred while saving. Please try again after some time');
                }
            	
            }else if (type == 'countryChangePerm') {	            	
                $scope.statesListPerm = response.data;
            }else if (type == 'countryChangePre') {	            	
                $scope.statesListPre = response.data;
            }else if (type == 'getTransactionDates') {     
            	var obj = response.data;
            	var k ;            	
            	if(response.data.length > 1){
	            	for(var i = obj.length-1; i> 0;i--){
		            	 if($scope.dateDiffer(response.data[i].name.split('-')[0])){
		            		 k =  response.data[i].id;
		            		 break;
		            	 }
	            	}
            	}else{
            		k = response.data[0].id;
            	}
            	$scope.transactionModel=k;
            	$scope.transactionDatesList = response.data;

                $scope.getData('workerController/getWorkerDetailsbyId.json', {customerId:"",workerInfoId :$scope.transactionModel}, 'getWorkerDetails') 
            }else if(type == 'validateWorkerCode'){
            	//alert(angular.toJson(response.data));
            	if(parseInt(response.data.id) > 0){
            		 $scope.Messager('error', 'Error', 'Worker Code is already available..',buttonDisable);            		
            	}else{
            		$scope.saveWorkerDetails(buttonDisable);
            	}
            }else if(type == 'getWorkerDetailsFoCancel'){
            	$scope.workerVo = response.data.workerDetails[0];             	
            	$('#dateOfJoining').val($filter('date')( response.data.workerDetails[0].dateOfJoining, 'dd/MM/yyyy'));     
            	$('#transactionDate').val($filter('date')( response.data.workerDetails[0].transactionDate, 'dd/MM/yyyy')); 
            }else if(type == 'getWorkerChildScreensData'){
            	$scope.workerChildIds = response.data;
            	
            }else if( type ==  'schemaName'){   
            	
      		   if(response.data.name != undefined && response.data.name != null && response.data.name != ''){
     			   $scope.workerVo.schemaName = response.data.name;   
     		   }else{
     			   $scope.workerVo.schemaName = 'null';
     		   }
     		  
            }/*else if(type == 'ChangeWorkerCode'){
            	if(response.data.workerVerificationTypesSetupId > 0){
            		$scope.Messager('success', 'success', 'Worker Code Changed Successfully',buttonDisable);            		
              }else if(response.data.workerVerificationTypesSetupId == -1){
            	  $scope.Messager('error', 'Error', 'Worker Code is Already Available.',buttonDisable); 
              }else{
            	  $scope.Messager('error', 'Error', 'Error in saving the details, try after some time...',buttonDisable); 
              }
            }
        	else if(type == 'searchJob'){
            	//alert(angular.toJson(response.data[0]));
            	if(response.data.length > 0 ){
            		location.href = "#/workerJobDetails/"+response.data[0].workJobDetailsId;
            	}else{
            		myservice.customerId = $scope.workerVo.customerId;
                	myservice.companyId = $scope.workerVo.companyId;
                	myservice.vendorId = $scope.workerVo.vendorId;
                	myservice.workerId = $scope.workerVo.workerId;
            		location.href = "#/workerJobDetails/create";
            		
            	}
            }*/

        },function () {
        	 $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
        });
    }  
    
    $scope.dateDiffer = function(date1){
       	var cmpDate = new Date( moment(date1,'DD/MM/YYYY').format('YYYY-MM-DD HH:MM:SS'));

    	   if(cmpDate.getTime() <= (new Date()).getTime()){
       			return true;
    	   }else{
    		   return false;       	
    	   }
       };
       
    $scope.getData('workerController/getWorkerDetailsbyId.json', {customerId: $cookieStore.get('customerId'),workerInfoId :$routeParams.workerInfoId}, 'getWorkerDetails');
    
    $scope.getData("reportController/getSchemaNameByUserId.json",{userId : $cookieStore.get('userId') } ,"schemaName" );
    
    $scope.customerChange = function () { 
    	$scope.companyList ="";
    	$scope.countriesList = ""; 
        $scope.vendorList = "";             
    	if($scope.workerVo.customerId != undefined && $scope.workerVo.customerId != ""){
    	//$scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.workerVo.customerId}, 'customerChange');
    		$scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.workerVo.customerId ,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.workerVo.companyId != undefined ?  $scope.workerVo.companyId : 0  }, 'customerChange');
    	}
    };
    
    $scope.companyChange = function () {
    	$scope.countriesList = ""; 
        $scope.vendorList = ""; 
        $scope.companyProfile = "";
    	if($scope.workerVo.companyId != undefined && $scope.workerVo.companyId != ""){
    		$scope.getData('workerController/getVendorAndWorkerNamesAsDropDowns.json', { customerId: $scope.workerVo.customerId,companyId: $scope.workerVo.companyId,vendorId:($cookieStore.get('vendorId') != null && $cookieStore.get('vendorId') != "") ? $cookieStore.get('vendorId') : $scope.workerVo.vendorId != undefined ? $scope.workerVo.vendorId : 0 }, 'companyChange');
    	}
    };
    
    
    $scope.countryChangePerm = function () {
      	if($scope.workerVo.permanentCountryId != null && $scope.workerVo.permanentCountryId != ""){
    		$scope.getData('CommonController/statesListByCountryId.json', { countryId: $scope.workerVo.permanentCountryId }, 'countryChangePerm');
    	}
    };
	    
    $scope.countryChangePre = function () {
      	if($scope.workerVo.presentcountryId != null && $scope.workerVo.presentcountryId != ""){
    		$scope.getData('CommonController/statesListByCountryId.json', { countryId: $scope.workerVo.presentcountryId }, 'countryChangePre');
    	}
    };
    
    $scope.validateWorkerCode = function($event){
    	if($('#workerForm').valid()){				
   	 		$scope.getData('workerController/validateWorkerCode.json', {workerCode: $scope.workerVo.workerCode,workerId:$scope.workerVo.workerId} , 'validateWorkerCode',angular.element($event.currentTarget));
    	}
    	
    }
    
    
	    
    $scope.saveWorkerDetails =function($event){
    	var regex = new RegExp("[A-Za-z]{3}(P|p|C|c|H|h|F|f|A|a|T|t|B|b|L|l|J|j|G|j)[A-Za-z]{1}[0-9]{4}[A-Za-z]{1}$");
    	$scope.workerVo.identityList = [];
    
    	if($('#workerForm').valid()){   
    		$scope.workerVo.dateOfBirth = moment($('#dateOfBirth1').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
			$scope.workerVo.dateOfJoining = moment($('#dateOfJoining').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
			$scope.workerVo.dateOfLeaving = moment($('#dateOfLeaving').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
			if(flag){
				$scope.Messager('error', 'Error', message,true);
				return;
			}
    		if(!moment($('#dateOfBirth1').val(), 'DD/MM/YYYY', true).isValid()) {
    			$scope.Messager('error', 'Error', 'Please enter valid Date of birth');
    		}    		
    		if($scope.workerVo.panNumber != undefined && $scope.workerVo.panNumber != null && $scope.workerVo.panNumber !='' ){
    	  		$scope.Messager('error', 'Error', 'Please enter valid Pension ID');
    	  	}else if((($scope.workerVo.permanentAddressLine1 == undefined &&  $scope.workerVo.permanentAddressLine1 == "") || ($scope.workerVo.permanentCountryId == undefined || $scope.workerVo.permanentCountryId == "" ))&& 
    	  			(($scope.workerVo.presentAddressLine1 == undefined && $scope.workerVo.presentAddressLine1 != "") || ($scope.workerVo.presentcountryId == undefined || $scope.workerVo.presentcountryId == "" ))){
    	  		$scope.Messager('error', 'Error', 'Please enter permanent or local address');
    	  	}/*else if(($scope.workerVo.permanentAddressLine1 == undefined &&  $scope.workerVo.permanentAddressLine1 == "") || ($scope.workerVo.permanentCountryId == undefined || $scope.workerVo.permanentCountryId == "" )){
    	  		$scope.Messager('error', 'Error', 'Please enter permanent address');
    	  	}*/else if(($scope.workerVo.presentAddressLine1 == undefined && $scope.workerVo.presentAddressLine1 != "" ) || ($scope.workerVo.presentcountryId == undefined && $scope.workerVo.presentcountryId == "" ) && $scope.workerVo.isSameAddress == false){
    	  		$scope.Messager('error', 'Error', 'Please enter local address');
    	  		$scope.labelName2 = "Add";
    	  	}else if($scope.years < $scope.companyProfile.minAge) {
    	  		$scope.Messager('error', 'Error', 'Worker is below minimum age limit. Please enter valid date of birth');
    	  	}else if($scope.years > $scope.companyProfile.maxAge) {
    	  		$scope.Messager('error', 'Error', 'Worker is over maximum age limit. Please enter valid date of birth');
    	  	}else if($('#dateOfJoining').val() != '' && $('#dateOfJoining').val() != undefined && $('#dateOfBirth1').val() != '' && $('#dateOfBirth1').val() != undefined && !moment($scope.workerVo.dateOfJoining).isSameOrAfter($scope.workerVo.dateOfBirth)){
	        	$scope.Messager('error', 'Error', 'Date of Joining should not be earlier than Date Of Birth');
		    }else if($('#dateOfJoining').val() != '' && $('#dateOfJoining').val() != undefined && $('#dateOfLeaving').val() != '' && $('#dateOfLeaving').val() != undefined && !moment($scope.workerVo.dateOfLeaving).isSameOrAfter($scope.workerVo.dateOfJoining)){
	        	$scope.Messager('error', 'Error', 'Date of Leaving should not be earlier than Date Of Joining');
	        	
		    }/*else if(($scope.workerVo.ifscCode == undefined || $scope.workerVo.ifscCode == '' || $scope.workerVo.ifscCode == null ||  $scope.workerVo.ifscCode == 'null') && ($scope.workerVo.branchName == undefined || $scope.workerVo.branchName == '' || $scope.workerVo.branchName == null || $scope.workerVo.branchName == 'null')){
		    	
		    	$scope.Messager('error', 'Error', 'Enter IFSC Code or Branch Name');	
		    	
		    }*/else{
    	  		/*alert(($('#vendorId option:selected').text().slice(-6)).trim().substring(0,3));*/		    
    	  		var x= $('#vendorId option:selected').text();   
    	  		var res = x.slice(x.indexOf("(")+1, x.indexOf(")"));
    	  		   	  		
   			 	/*if( ('('+$scope.workerVo.workerCode.trim()).indexOf(res) != '0' ){
   			 		$scope.Messager('error', 'Error', 'Worker Code Should Start with the Characters mentioned in Vendor Name parentheses ..');
   			 		return;
   			 	}
    	  		
    	  		if( res.trim() !=  $scope.workerVo.workerCode.trim().substring(0,3) ){
			 		$scope.Messager('error', 'Error', 'Worker code should start with the first 3 characters mentioned in vendor name parentheses ...');
			 		return;
			 	}*/
   			 	
   			 	
    			$scope.workerVo.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
    			$scope.workerVo.dateOfBirth = moment($('#dateOfBirth1').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
    			$scope.workerVo.dateOfJoining = moment($('#dateOfJoining').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
    			
    			if($scope.workerVo.isActive == 'N'){
    				$scope.workerVo.dateOfLeaving =  moment($('#dateOfLeaving').val(), 'DD/MM/YYYY').format('YYYY-MM-DD');
    			}else{
    				$scope.workerVo.dateOfLeaving = null;
    			}
    			$scope.workerVo.createdBy = $cookieStore.get('createdBy'); 
    			$scope.workerVo.modifiedBy = $cookieStore.get('modifiedBy');
    			
    			
	    	   
	 	    	if($scope.saveBtn == true){
	 	    		$scope.workerVo.workerInfoId=0;
	 	    	}
	 	    	
			    var formData = new FormData();
			    
			    $scope.workerVo.age = $scope.years+" Years"+$scope.months+" Months";
			    $scope.workerVo.identityList = $scope.result;
			   
			    
			   
			    if(($scope.workerVo.identityList == undefined || $scope.workerVo.identityList == null  || $scope.workerVo.identityList == '') &&( $scope.image == null || $scope.image == "")){
			    	$scope.getData('workerController/saveWorkerWithoutFiles.json', angular.toJson($scope.workerVo), 'saveWorkerDetails',angular.element($event.currentTarget)); 
			    	 $("#loader").show();
			    	 $("#workerForm").hide();
	                   
			    }else{
			    	angular.forEach($scope.result, function (value, key) {        	 
			    		formData.append("file", value.fileData); 
		        		formData.append("filename", value.fileName);    
			    	});
			    	//alert($scope.theFile1);
			    	if($scope.image != "" && $scope.image != null && $scope.image != undefined){
			    		formData.append("file",$scope.theFile1);
			    	}
			    	
			    	formData.append('name',angular.toJson($scope.workerVo));
			    	//alert(angular.toJson($scope.workerVo));
			    	$http.post('workerController/saveOrUpdateWorkerDetails.json', formData,{
				                 transformRequest: angular.identity,
				                 headers: {'Content-Type': undefined
		                	 }
		            }).success(function(response,data){
		            	if(response.id > 0){
			            	$scope.Messager('success', 'success', 'Worker Saved Successfully');
			            	//$location.path('/workerVerification/'+angular.toJson(response.id));
			            	$cookieStore.put('workerInfoId',response.id);
			            	$location.path('/workerFamilyDetails');
			            	//alert(response.id);
			            	$cookieStore.put('workerCode',$scope.workerVo.workerCode);
			            	 $cookieStore.put('verificationVendorId',$scope.workerVo.vendorId);
			                 $cookieStore.put('verificationworkerId',$scope.workerVo.workerId);
			            	//$location.path('/workerDetails/'+response.id);
			            	/*var res = confirm("Do you want to Verify the worker qualification/eligibity? ");
			            	//alert(res);
			            	if(res){
			            		location.href = "#/workerVerification/"+$cookieStore.get('workerInfoId');
			            	}else{
			            		$location.path('/workerDetails/'+response.id);
			            	}*/
		            	}else if(response.id < 0){
	            		    $scope.Messager('error', 'Error', response.name);
	            		    $("#loader").hide();
	            		    $("#workerForm").show();
	            		    $scope.workerVo.transactionDate =  $filter('date')( $('#transactionDate').val(), 'dd/MM/yyyy');
		            	}else{
		            	    $scope.Messager('error', 'Error', "Technical issues occurred. Please try again after some time.");
		            	    $("#loader").hide();
		            	    $("#workerForm").show();
		            	    $scope.workerVo.transactionDate =  $filter('date')( $('#transactionDate').val(), 'DD/MM/YYYY');
		            	}
		            }).error(function(data,status,headers,config){
		            	$scope.Messager('error', 'Error', 'Technical Issue while saving. Please try again after some time');
		            	$scope.workerVo.transactionDate =  $filter('date')( $('#transactionDate').val(), 'DD/MM/YYYY');
		            });
			    	 $("#loader").show();
			    	 $("#workerForm").hide();
			    }
    	  	}
		}
    };

    $scope.transactionDatesListChnage = function(){                
        $scope.getData('workerController/getWorkerDetailsbyId.json', {customerId:"",workerInfoId :$scope.transactionModel}, 'getWorkerDetails')
        $('.dropdown-toggle').removeClass('disabled');
    };
    
    $scope.plusIconAction = function(){
    	$scope.identity =  new Object();
    	$('#fileName').val("");
    	$scope.theFile="";
    	$scope.popUpSave = true;
    	$scope.popUpUpdate = false;
    };
    
    $scope.saveChanges = function(){
	   var status = false;
	   angular.forEach($scope.result, function(value, key){	
		      if(value.identificationType == $scope.identity.identificationType && value.countryId == $scope.identity.country){
		    	  $scope.Messager('error', 'Error', 'This identification already added',true); 
		    	  status = true;			    		
		      }
	   });	
	
	   if(status){
		   $scope.Messager('error', 'Error', 'This identification already added',true); 
	   
	   }else if(!status && $scope.identity != undefined && $scope.identity != '' 
		   			&&  $scope.identity.country != undefined && $scope.identity.country != ''
				    &&  $scope.theFile.name != undefined && $scope.theFile.name != null && $scope.theFile.name != ''
				    &&  $scope.identity.identificationType != undefined && $scope.identity.identificationType != null && $scope.identity.identificationType != '' && $scope.identity.identificationNumber != null && $scope.identity.identificationNumber != undefined && $scope.identity.identificationNumber != ''){
		   $scope.result.push({
			    'countryId':$scope.identity.country,
         		'countryName':$('#country option:selected').html(),  		
         		'identificationType':$('#idType option:selected').html(),
         		'identificationNumber':$scope.identity.identificationNumber,
         		'fileName':$scope.theFile.name,
         		'fileData':$scope.theFile
     	   });   
		   //alert($scope.theFile);
		   $('div[id^="identificationType"]').modal('hide');
		   $scope.popUpSave = true;
		   $scope.popUpUpdate =false;
	   
	   }else if($scope.identity.country == undefined || $scope.identity.country == ''){
		   $scope.Messager('error', 'Error', 'Please select country',true); 
	   
	   }else if($scope.identity.identificationType == undefined || $scope.identity.identificationType == '') {
		   $scope.Messager('error', 'Error', 'Please select identification type'); 
	   
	   }else if($scope.identity.identificationNumber == null || $scope.identity.identificationNumber == undefined){
		   $scope.Messager('error', 'Error', 'Please Enter Identification Number'); 
	   }else {
		   $scope.Messager('error', 'Error', 'Please upload the document'); 
	   }
    };
    
    $scope.getFileDetails = function (e) {  
        $scope.$apply(function () {
             $scope.theFile = e.files[0];
        });
    };
        
    $scope.Delete = function($event){    	
	   var del = confirm('Are you sure you want to delete '+ $scope.result[$($event.target).parent().parent().index()].identificationType+ ' ?');
	   
	   if (del) {
		   $scope.result.splice($($event.target).parent().parent().index(),1);
		   $('#fileName').val("");
	   }
	   
	   $scope.popUpSave = true;
       $scope.popUpUpdate = false;
    	  
    };
	       
     $scope.Edit = function($event,fileData){ 
      	var data =  $scope.result[$($event.target).parent().parent().index()];
      	$scope.identity.country = data.countryId;
      	$scope.identity.identificationType = data.identificationType;
      	//$scope.myFile = data.fileName;
      	$scope.popUpSave = false;
      	$scope.popUpUpdate =true;
     };
	          
	          
     $scope.updateChanges= function($event){
    	 $scope.result.splice($($event.target).parent().parent().index(),1); 
	   	 var status = false;
	   	 angular.forEach($scope.result, function(value, key){	
		     if(value.identificationType == $scope.identity.identificationType && value.countryName == $scope.identity.countryName){
		    	  $scope.Messager('error', 'Error', 'Identification Type already added',true); 
		    	  status = true;			    		
		     }
		  });	
	   	 
	   	  if(!status && $scope.identity != undefined && $scope.identity != ''&&  $scope.identity.country != undefined && $scope.identity.country != ''
				    &&  $scope.identity.identificationType != undefined && $scope.identity.identificationType != null && $scope.identity.identificationType != ''){
			   $scope.result.push({
				    'countryId':$scope.identity.country,
	         		'countryName':$('#country option:selected').html(),  		
	         		'identificationType':$('#idType option:selected').html(),
	         		'fileName':$scope.theFile.name,
	         		'fileData':$scope.theFile
	     	   });   
			   
			   $('div[id^="identificationType"]').modal('hide');
			   $scope.popUpSave = true;
			   $scope.popUpUpdate =false;
		   
	   	  }else if($scope.identity.country == undefined && $scope.identity.country == ''){
			   $scope.Messager('error', 'Error', 'Please select country',true); 
		   
	   	  }else {
			   $scope.Messager('error', 'Error', 'Please select identification type'); 
		   }
	   	  
      };
      
      
      // To download the file
      $scope.fun_download = function(event,fileName){
    	  //alert(event);
    	  if(event instanceof Object){
	    	  var file = document.getElementById("fileName").files[0];
	    	 // alert(file);
	    	  if (file) {
	    	      var reader = new FileReader();
	    	      reader.readAsArrayBuffer(event);
	    	      reader.onload = function (evt) {
	    	    	  var blob = new Blob([evt.target.result], {type: 'application/octet-stream'});
	  			      saveAs(blob,fileName);
	    	      }
	    	      reader.onerror = function (evt) {
	    	    	  //alert("Failed to download. Please try Again");
	    	    	  $scope.Messager('error', 'Error', 'Failed to download ');
	    	      }
	    	  }
    	  }else{
    		  $http({
  			    url: ROOTURL +'vendorComplianceController/downloadfile.view',
  			    method: 'POST',
  			    data : { 'path': event } ,
  			    responseType: 'arraybuffer',
  			    headers: {
  			        'Content-type': 'application/json',
  			        'Accept': '*'
  			    }
  			}).success(function(data){
  			   var blob = new Blob([data], {
  			        type: 'application/pdf'
  			    });
  			    saveAs(blob,fileName);
  			    $("#loader").hide();
                $("#data_container").show();
                
  			}).error(function(){	
  			    $scope.Messager('error', 'Error', 'Failed to download ');
  			});
    	  }
      };
    
      $scope.saveAddress = function(){
    	 // alert($scope.workerVo.permanentCountryId);
    	  if(($scope.workerVo.permanentAddressLine1 != undefined &&  $scope.workerVo.permanentAddressLine1 != "" && $scope.workerVo.permanentAddressLine1 != null) && ($scope.workerVo.permanentCountryId != undefined && $scope.workerVo.permanentCountryId != "" && $scope.workerVo.permanentCountryId != null )){
    		  //$scope.workerVo.isSameAddress = false;
    		  $('#localAdressAdd').show();
    		  $scope.labelName1 = "View/Edit";
    		  $('div[id^="myModal1"]').modal('hide');
    		 
    	  }else{
    		  $scope.Messager('error', 'Error', 'Please fill required fields');
    	  }
      };
      
      $scope.savePresent = function(){
    	  if(($scope.workerVo.presentAddressLine1 != undefined &&  $scope.workerVo.presentAddressLine1 != "" && $scope.workerVo.presentAddressLine1 != null) && ($scope.workerVo.presentcountryId != undefined && $scope.workerVo.presentcountryId != "" && $scope.workerVo.presentcountryId != null )){
    		  $scope.labelName2 = "View/Edit";
    		  $('div[id^="myModal2"]').modal('hide');
    	  }else{
    		  $scope.Messager('error', 'Error', 'Please fill required fields');
    	  }
    	  
      };
      
      $scope.familyDetails = function(){
    	 // alert(1);
    	  if($scope.saveBtn || $scope.correcttHistoryBtn){
    		  $scope.Messager('error', 'error', 'Save the worker details before adding the Family information');
    	  }else{
    		 location.href= '#/workerFamilyDetails';
    	  }
      };
      
      $scope.wokerVerification = function(){
    	 /* $scope.getData("workerController/workerVerificationDetailsByWorkerInfoId.json",{
						workerInfoId : "Y",
					 } , "verification" );    */	
    	  
    	  location.href = "#/workerVerification/"+$cookieStore.get('workerInfoId');
      };
      
      $scope.fun_localAddress = function(){  
    	  //alert($scope.workerVo.isSameAddress);
    	  if($scope.workerVo.isSameAddress){

	    	  if(($scope.workerVo.permanentAddressLine1 != undefined &&  $scope.workerVo.permanentAddressLine1 != "" && $scope.workerVo.permanentAddressLine1 != null) && ($scope.workerVo.permanentCountryId != undefined && $scope.workerVo.permanentCountryId != "" && $scope.workerVo.permanentCountryId != null )){
	    		  $('#localAdressAdd').hide();
	    		  $scope.workerVo.presentAddressLine1 = $scope.workerVo.permanentAddressLine1;
	    		  $scope.workerVo.presentAddressLine2 = $scope.workerVo.permanentAddressLine2;
	    		  $scope.workerVo.presentAddressLine3 =$scope.workerVo.permanentAddressLine3;
	    		  $scope.workerVo.presentStateId = $scope.workerVo.permanentStateId;
	    		  $scope.workerVo.presentPinCode = $scope.workerVo.permanentPinCode ;
	    		  $scope.workerVo.presentcountryId = $scope.workerVo.permanentCountryId;
	    		  $scope.workerVo.presentCity = $scope.workerVo.permanentCity;    	
	    		  $scope.labelName1="View/Edit";
	    	  }
	    	  else{
	    		  //$('#localAdressAdd').hide();
	    		  $scope.workerVo.isSameAddress = false;
	    		  $scope.Messager('error', 'Error', 'Enter Permanent Address');
	    	  }
    	  }else{
    		  $scope.labelName2="Add";
    		  $('#localAdressAdd').show();
    		  $scope.workerVo.presentAddressLine1 =null;
     		 $scope.workerVo.presentAddressLine2 =null;
     		 $scope.workerVo.presentAddressLine3 =null;
     		 $scope.workerVo.presentStateId = null;
     		 $scope.workerVo.presentPinCode  =null;
     		 $scope.workerVo.presentcountryId ="";
     		 $scope.workerVo.presentCity =null;
    	  }
      }
      
      $scope.getImageDetails = function (e) {  
          $scope.$apply(function () {
               $scope.theFile1 = e.files[0];
          });
          $scope.workerVo.imageName = $scope.theFile1.name;
      };
      
      $scope.fun_close = function(){
    	  if(($scope.workerVo.permanentAddressLine1 != undefined &&  $scope.workerVo.permanentAddressLine1 != "" && $scope.workerVo.permanentAddressLine1 != null) && ($scope.workerVo.permanentCountryId != undefined && $scope.workerVo.permanentCountryId != "" && $scope.workerVo.permanentCountryId != null )){
    	  }else{
    		  $scope.labelName1 = "Add";
    	  }
    	  
    	  if(($scope.workerVo.presentAddressLine1 != undefined &&  $scope.workerVo.presentAddressLine1 != "" && $scope.workerVo.presentAddressLine1 != null) && ($scope.workerVo.presentcountryId != undefined && $scope.workerVo.presentcountryId != "" && $scope.workerVo.presentcountryId != null )){
    	  }else{
    		  $scope.labelName2 = "Add";
    	  }
      }
      
   
		 
		
		 $scope.badgeGenTab = false;	
 
      
      $scope.workerFamilyDetails = function($event){ 
    	    	//  alert($scope.familyTab);
    	  if($scope.familyTab){
	    	  if($scope.workerChildIds == undefined || $scope.workerChildIds[0].workerId == undefined || parseInt($scope.workerChildIds[0].workerId) == 0){
	    		  $scope.Messager('error', 'Error', 'Enter worker Details, Then only you are allowed to enter Worker Family Details.',angular.element($event.currentTarget));
	    	  }else if( $scope.workerChildIds != undefined && $scope.workerChildIds[0].workerInfoId != undefined){ 
	    		  $cookieStore.put('verificationVendorId',$scope.workerVo.vendorId);
	              $cookieStore.put('verificationworkerId',$scope.workerVo.workerId);
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
	    		  $cookieStore.put('verificationVendorId',$scope.workerVo.vendorId);
	              $cookieStore.put('verificationworkerId',$scope.workerVo.workerId);
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
	    		  $cookieStore.put('verificationVendorId',$scope.workerVo.vendorId);
	              $cookieStore.put('verificationworkerId',$scope.workerVo.workerId);
	    		  location.href = "#/workerVerification/"+$scope.workerChildIds[0].workerInfoId;
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
		    		  $cookieStore.put('verificationVendorId',$scope.workerVo.vendorId);
		              $cookieStore.put('verificationworkerId',$scope.workerVo.workerId);
		    		  location.href = "#/workerJobDetails/create";
		    	 }else if($scope.workerChildIds != undefined && $scope.workerChildIds[0].workJobDetailsId != undefined && parseInt($scope.workerChildIds[0].verificationId) > 0 && $scope.workerChildIds[0].workJobDetailsId > 0){
		    		 $cookieStore.put('verificationVendorId',$scope.workerVo.vendorId);
		             $cookieStore.put('verificationworkerId',$scope.workerVo.workerId); 
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
	    		  $cookieStore.put('verificationVendorId',$scope.workerVo.vendorId);
	              $cookieStore.put('verificationworkerId',$scope.workerVo.workerId);
	    		  location.href = "#/workerBadgeGeneration/"+$scope.workerChildIds[0].workerBadgeId;   	 
	    	  }else{
	    		  $scope.Messager('error', 'Error', 'Enter Verification Details and Job Details, Then only you are allowed to enter Worker Badge Details.',angular.element($event.currentTarget));
	    	  }  		  
  		 }else{
  			 $scope.Messager('error', 'Error', 'You dont have permission to view/edit Badge Details',true); 
  		 }
     };
     
     $scope.callingUrl = function($event){
    	if ($scope.familyTab) {
    		$scope.workerFamilyDetails($event);
		} else if ($scope.medicalTab) {
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
			 $scope.Messager('error', 'Error', 'You dont have permission to view/edit Next Tabs',true); 
 		}
     };

     $scope.statusChange = function(){
    	 if($scope.workerVo.isActive == 'Y' && $scope.status1 == 'N'){
    		 $scope.getData('workerController/validateWorkerLimit.json', { customerId: $scope.workerVo.customerId,companyId:($scope.workerVo.companyId != undefined ? $scope.workerVo.companyId :0),vendorId:($cookieStore.get('vendorId') != null && $cookieStore.get('vendorId') != "") ? $cookieStore.get('vendorId') : $scope.workerVo.vendorId != undefined ? $scope.workerVo.vendorId : 0, isActive :'Y'}, 'statusChange');
    	 }
     }   
     
     $scope.changeWorkerCode = function(){    	 
    	 $scope.workerVo.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
			$scope.workerVo.dateOfBirth = moment($('#dateOfBirth1').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
			$scope.workerVo.dateOfJoining = moment($('#dateOfJoining').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
			
			if($scope.workerVo.isActive == 'N'){
				$scope.workerVo.dateOfLeaving =  moment($('#dateOfLeaving').val(), 'DD/MM/YYYY').format('YYYY-MM-DD');
			}else{
				$scope.workerVo.dateOfLeaving = null;
			}
    	 $scope.workerVo.modifiedBy = $cookieStore.get('modifiedBy');
    	 $scope.getData('workerController/changeWorkerCode.json', angular.toJson($scope.workerVo), 'ChangeWorkerCode');
     }

     
  	
}]);
