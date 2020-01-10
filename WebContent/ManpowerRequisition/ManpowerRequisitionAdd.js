
ManpowerRequestController.controller("ManpowerAddCtrl",  ['$routeParams','$scope', '$rootScope', '$http', '$resource','$location','myservice','$cookieStore','$filter','$window', function($routeParams, $scope, $rootScope, $http, $resource, $location, myservice, $cookieStore,$filter,$window) {
	$.material.init();
	$scope.manpower = new Object();
	$scope.manpower.manpowerSkillTypesList = [];
	$scope.manpower.status = "New";
	$scope.manpower.requestDate = $filter('date')(new Date(),'dd/MM/yyyy');
	$scope.manpower.requiredFor = 'Addition';
	//$scope.manpower.frequency = 'Annual';
	
	$scope.employeeId = $cookieStore.get('employeeUniqueId');
	
	
	$('#indenterDate, #requestDate,#approvedDate').datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown'
	    	  
	});
	
	$('#fromDate, #toDate').datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown',
	     // startDate: tomorrow
	      minDate: "1"
	    	  
	});
	
	
	$('#effectiveDate').datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown',
	      minDate:1	  
	    });
	
	$scope.list_Status = [{"id":"New","name":"New"},
	                      {"id":"Saved","name":"Saved"},
	                      {"id":"Pending For Approval","name":"Pending For Approval"},
	                      {"id":"Approved","name":"Approved"},
	                      {"id":"Rejected","name":"Rejected"}];
	
	$scope.list_RequestType = [{"id":"Planned","name" : "Planned" },
	                       {"id":"Ad-hoc","name" : "Ad-hoc" }];
	
	$scope.list_Frequency = [{"id":"Annual","name" : "Annual" },
	                       {"id":"Month","name" : "Month" }];
	
	$scope.list_RequiredFor = [{"id":"Routine Work","name":"Routine Work"},
	                      {"id":"Additional Work","name":"Additional Work"},
	                      {"id":"New Work","name":"New Work"}];
	
	$scope.list_Skills = [{"id":"Skilled","name" : "Skilled" },
	                       {"id":"Semi Skilled","name" : "Semi Skilled" },
	                       {"id":"High Skilled","name" : "High Skilled" },
	                       {"id":"Special Skilled","name" : "Special Skilled" },
	                       {"id":"UnSkilled","name" : "UnSkilled" }];
	
	
	$scope.list_Months = [{"id":"January","name":"January"},
                        {"id":"February","name":"February"},
                        {"id":"March","name":"March"},
                        {"id":"April","name":"April"},
                        {"id":"May","name":"May"},
                        {"id":"June","name":"June"},
                        {"id":"July","name":"July"},
                        {"id":"August","name":"August"},
                        {"id":"September","name":"September"},
                        {"id":"October","name":"October"},
                        {"id":"November","name":"November"},
                        {"id":"December","name":"December"}];
	
	$scope.list_Years = $rootScope.getYears; 
	
	if ($routeParams.id > 0) {
        $scope.readOnly = true;
        $scope.onlyRead = true;
        $scope.updateBtn= true;
        $scope.approveBtn = true;
        $scope.rejectBtn = true;
        $scope.sendForApprovalBtn = false;
        $scope.gridButtons = false;
        $scope.cancel = false;
        $scope.saveBtn = false;
        $scope.resetBtn = false;
	}else {
		$scope.readOnly = false;
		$scope.onlyRead = false;
        $scope.updateBtn= false;
        $scope.approveBtn = false;
        $scope.rejectBtn = false;
        $scope.sendForApprovalBtn = true;
        $scope.cancel = false;
        $scope.saveBtn = true;
        $scope.resetBtn = true;
        $scope.gridButtons = true;
	};

	$scope.updateBtnAction = function (this_obj) {
		$scope.readOnly = false;
		$scope.onlyRead = true;
        $scope.updateBtn= false;
        $scope.approveBtn = false;
        $scope.rejectBtn = false;
        $scope.sendForApprovalBtn = true;
        $scope.cancel = true;
        $scope.saveBtn = true;
        $scope.resetBtn = false;
        $scope.gridButtons =true;
	    $('.dropdown-toggle').removeClass('disabled');
	};



	$scope.cancelBtnAction = function(){
		$scope.readOnly = true;
	    $scope.updateBtn = true;
	    $scope.saveBtn = false;
	    $scope.viewOrUpdateBtn = true;
	    $scope.correcttHistoryBtn = false;
	    $scope.resetBtn = false;
	    $scope.transactionList = false;
	    $scope.cancelBtn = false;
	    $scope.gridButtons = false;
	    $scope.rejectBtn = false;
	    $scope.approveBtn = false;
		$scope.getData("manpowerRequisitionController/getManpowerRequisitionById.json",{manpowerRequisitionId : $routeParams.id,customerId : $cookieStore.get('customerId'), } , "manpowerList" );    

	};

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
	};
	 
	// FOR COMMON POST METHOD
	$scope.getData = function (url, data, type) {
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
						      }else if (type == 'manpowerList'){ 
						    	 
						    	 
						    	
						    	  if( response.data.manpowerVo != null && response.data.manpowerVo != undefined && response.data.manpowerVo.length == 1 ){
						    		 
						    		  $scope.manpower = response.data.manpowerVo[0];
						    		  $scope.manpower.requestDate = $filter('date')(response.data.manpowerVo[0].requestDate,'dd/MM/yyyy');
						    		 /* if(response.data.manpowerVo[0].effectiveDate != undefined && response.data.manpowerVo[0].effectiveDate != null && response.data.manpowerVo[0].effectiveDate != ""){
						    			  $scope.manpower.effectiveDate = $filter('date')(response.data.manpowerVo[0].effectiveDate,'dd/MM/yyyy');
						    		  }	*/
						    		 
						    		  /*if(response.data.manpowerVo[0].approvedDate != undefined && response.data.manpowerVo[0].approvedDate != null){
					    				  $scope.manpower.approvedDate = $filter('date')(response.data.manpowerVo[0].approvedDate,'dd/MM/yyyy');
					    			  }*/
						    		  
						    		  //$scope.manpower.indenterDate = $filter('date')(response.data.manpowerVo[0].indenterDate,'dd/MM/yyyy');
						    		  //$scope.manpower.year = response.data.manpowerVo[0].year+"";
						    		  
						    		  $scope.customerList = response.data.customerList;
						    		  $scope.companyList = response.data.companyList;
						    		  $scope.locationsList = response.data.locationsList;
						    		  $scope.departmentsList = response.data.departmentsList;
						    		  $scope.plantsList = response.data.plantsList;
						    		  $scope.countriesList = response.data.countriesList;
						    		  $scope.jobCodesList = response.data.jobCodesList;
						    		  $scope.employeesList = response.data.employeesList;
						    		  $scope.employeeListForApproval = response.data.employeeListForApproval;
						    		  //alert(angular.toJson(response.data.employeeListForApproval));
						    		  //$scope.employeeChange();
						    		 // alert(angular.toJson(response.data.manpowerVo[0]));
						    		  $scope.manpower.employeeId = response.data.manpowerVo[0].employeeId;
							  		  $scope.manpower.nameOfTheIndenter = response.data.manpowerVo[0].nameOfTheIndenter;
							  		  /*if($scope.manpower.employeeContactNumber == undefined || $scope.manpower.employeeContactNumber == null || $scope.manpower.employeeContactNumber == ""){
							  			  $scope.manpower.employeeContactNumber = response.data.employeesList[0].phoneNumber;
							  		  }*/
							  		  
							  		/*if(response.data.manpowerVo[0].approvedBy != undefined && response.data.manpowerVo[0].approvedBy != null){
					    				  $scope.manpower.approvedBy = response.data.manpowerVo[0].approvedBy;
					    			}*/
							  		  
							  		
						    		  $scope.readOnly = true;
					    		      $scope.onlyRead = true;
					    		      $scope.updateBtn= true;
					    		      $scope.sendForApprovalBtn = false;
					    		      $scope.gridButtons = false;
					    		      $scope.cancel = false;
					    		      $scope.saveBtn = false;
					    		      $scope.resetBtn = false;
					    		      
					    		      /*if(response.data.manpowerVo[0].status == "Pending For Approval"){
				    		        	 $scope.approveBtn = true;
						    		     $scope.rejectBtn = true;
						    		     if($scope.manpower.approvedDate == undefined || $scope.manpower.approvedDate == null){
						    				  $scope.manpower.approvedDate = $filter('date')(new Date(),'dd/MM/yyyy');
						    		     }
						    		     $scope.manpower.approvedBy = $scope.employeeId;
					    		      }else*/ if(response.data.manpowerVo[0].status == "Approved" || response.data.manpowerVo[0].status == "Rejected"){ 
				    		        	// $scope.approveBtn = false;
						    		    // $scope.rejectBtn = false;
						    		     $scope.updateBtn= false;
						    		   }else {
						    			   
				    		        	// $scope.approveBtn = false;
						    		    // $scope.rejectBtn = false;
					    		      }
					    		      
					    		     // alert(angular.toJson(response.data.employeeListForApproval));
					    		      //$scope.employeeListForApproval = response.data.employeeListForApproval;
						    		  
						    	  }else{
						    		  $scope.customerList = response.data.customerList;
							    	  if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
							    		  $scope.manpower.customerId =response.data.customerList[0].customerId;	
							    		  $scope.dropdownDisableCustomer = true;
							    		  $scope.customerChange();
							    	  }
							    	  if(response.data != null && response.data != undefined){
							    		  $scope.employeesList = response.data.employeesList;
								    	  $scope.employeeDetails = response.data.employeesList[0];
								  		  $scope.manpower.employeeId = $cookieStore.get('employeeUniqueId');
								  		  $scope.manpower.nameOfTheIndenter = response.data.employeesList[0].firstName;
								  		  if($scope.manpower.employeeContactNumber == undefined || $scope.manpower.employeeContactNumber == null || $scope.manpower.employeeContactNumber == ""){
								  			  $scope.manpower.employeeContactNumber = response.data.employeesList[0].phoneNumber;
								  		  }
							    	  }
							    	  $scope.readOnly = false;
							    	  $scope.onlyRead = false;
					    		      $scope.updateBtn= false;
					    		      $scope.approveBtn = false;
					    		      $scope.rejectBtn = false;
					    		      $scope.sendForApprovalBtn = true;
					    		      $scope.cancel = false;
					    		      $scope.saveBtn = true;
					    		      $scope.resetBtn = true;
					    		      $scope.gridButtons = true;
						    	  }
						    	  
						    	 /* if ($routeParams.id > 0) {
					    		        $scope.readOnly = true;
					    		        $scope.onlyRead = true;
					    		        $scope.updateBtn= true;
					    		        $scope.approveBtn = true;
					    		        $scope.rejectBtn = true;
					    		        $scope.sendForApprovalBtn = false;
					    		        $scope.gridButtons = false;
					    		        $scope.cancel = false;
					    		        $scope.saveBtn = false;
					    		        $scope.resetBtn = false;
					    			}else {
					    				$scope.readOnly = false;
					    				$scope.onlyRead = false;
					    		        $scope.updateBtn= false;
					    		        $scope.approveBtn = false;
					    		        $scope.rejectBtn = false;
					    		        $scope.sendForApprovalBtn = true;
					    		        $scope.cancel = false;
					    		        $scope.saveBtn = true;
					    		        $scope.resetBtn = true;
					    		        $scope.gridButtons = true;
					    			};*/
					    			
						    	// for button views
							  	  if($scope.buttonArray == undefined || $scope.buttonArray == '')
							  		  $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Manpower Requisition Request'}, 'buttonsAction');
						      }else if(type == 'customerChange'){
						    	  $scope.companyList = response.data;
						  	      if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
						  	    	  $scope.manpower.companyId = response.data[0].id;
						  	    	  $scope.companyChange();
						  	      }
						  	      
						  	  }else if(type == 'companyChange'){
						  		  $scope.locationsList = response.data.locationsList; 
						  		  $scope.countriesList = response.data.countryList;
						  		  $scope.costCenterList = response.data.costCenterList;

						  		 /* if(response.data.defaultCurrency != undefined && response.data.defaultCurrency != null){
					            	   $scope.manpower.currencyName = response.data.defaultCurrency[0].defaultCurrencyName;
					            	   $scope.manpower.currencyId = response.data.defaultCurrency[0].defaultCurrencyId;
					               }else{
					            	   $scope.manpower.currencyName = null;
					            	   $scope.manpower.currencyId = null;
					            	   
					               }*/
						  		  
					               if( response.data.countryList[0] != undefined && response.data.countryList[0] != "" && response.data.countryList.length == 1 ){
					   	               $scope.manpower.countryId = response.data.countryList[0].id;
					   	           }
					               
					               if( response.data.locationsList[0] != undefined && response.data.locationsList[0] != "" && response.data.locationsList.length == 1 ){
					            	   $scope.manpower.locationId = response.data.locationsList[0].locationId;
					            	   $scope.locationChange();
					    	       }
						  	  }else if(type == 'locationChange'){
						  		  $scope.plantsList = response.data;
						  	  }else if(type == 'plantChange'){
						  		  $scope.departmentsList = response.data.departmentList;
						  	  }/*else if(type == 'departmentChange'){
						  		  $scope.employeesList = response.data.employeesList;
						  	  }*/else if(type == 'employeeChange'){
						  		  $scope.employeeDetails = response.data.employeeDetails;
						  		  $scope.manpower.employeeId = $cookieStore.get('employeeUniqueId');
						  		  $scope.manpower.nameOfTheIndenter = response.data.employeeDetails[0].firstName;
						  		  if($scope.manpower.employeeContactNumber == undefined || $scope.manpower.employeeContactNumber == null || $scope.manpower.employeeContactNumber == ""){
						  			  $scope.manpower.employeeContactNumber = response.data.employeeDetails[0].phoneNumber;
						  		  }
						  	  }else if(type == 'skillChange'){
						  		  $scope.jobCodesList = response.data.jobCodesList;
						  		  if(response.data.assignedWorkersCount != undefined && response.data.assignedWorkersCount != null && response.data.assignedWorkersCount.length == 1){
						  			  $scope.skill.assignedWorkers = response.data.assignedWorkersCount[0].id;
						  		  }else{
						  			$scope.skill.assignedWorkers = 0;
						  		  }
						  		  
						  		  //alert($scope.skill.assignedWorkers);
						  	  }else if(type == 'validateCode'){
						  		  if(response.data == "1" || $routeParams.id > 0){
								  		$scope.manpower.requestDate = moment($('#requestDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
								  		$scope.manpower.createdBy = $cookieStore.get('createdBy'); 
									 	$scope.manpower.modifiedBy = $cookieStore.get('modifiedBy');
									 	
									 	//alert(angular.toJson($scope.manpower));
									    $scope.getData('manpowerRequisitionController/saveManpowerRequisition.json', angular.toJson($scope.manpower), 'saveManpower'); 
						  		  }else{
						  			  $scope.Messager('error', 'Error','Request ID already exists');
						    		  $scope.manpower.requestDate = $filter('date')($('#requestDate').val(),'dd/MM/yyyy');
						    		 /* if($scope.manpower.effectiveDate != undefined && $scope.manpower.effectiveDate != null && $scope.manpower.effectiveDate != ""){
						    			  $scope.manpower.effectiveDate = $filter('date')($scope.manpower.effectiveDate,'dd/MM/yyyy');
						    		  }*/
						    		  if($scope.manpower.approvedDate != null){
							  				$scope.manpower.approvedDate = $filter('date')($scope.manpower.approvedDate,'dd/MM/yyyy'); 
							  		  }

						  		  }
						  	  }else if(type == 'saveManpower'){
						  		  if(response.data.id > 0){
						  			 if($scope.manpower.status == 'Pending For Approval'){
						  				$scope.Messager('success','Success','Manpower Request Sent For Approval');
						  			 }else{
						  				 $scope.Messager('success','Success','Manpower Request '+$scope.manpower.status+' successfully');
						  			 }
						  			 $scope.getData("manpowerRequisitionController/getManpowerRequisitionById.json",{manpowerRequisitionId : response.data.id,customerId : $cookieStore.get('customerId'), uniqueId : $cookieStore.get('employeeUniqueId') } , "manpowerList" );
						  			 //$location.path('/addManpowerRequest/'+response.data.id);
						  		  }else if(response.data.id < 0){
						  			$scope.Messager('error', 'Error','Technical Issue Occurred');
						  			$scope.manpower.requestDate = $filter('date')($('#requestDate').val(),'dd/MM/yyyy');
						  			/*if($scope.manpower.requiredFor  == 'Reduction'){
						    			  $scope.manpower.effectiveDate = $filter('date')($scope.manpower.effectiveDate,'dd/MM/yyyy');
						    		 }
						  			*/
						  			if($scope.manpower.approvedDate != null){
						  				$scope.manpower.approvedDate = $filter('date')($scope.manpower.approvedDate,'dd/MM/yyyy');  
						  			}
						  		  }
						  	  }       	
						 })
	};
	 $scope.getData("manpowerRequisitionController/getManpowerRequisitionById.json",{manpowerRequisitionId : $routeParams.id,customerId : $cookieStore.get('customerId'), uniqueId : $cookieStore.get('employeeUniqueId') } , "manpowerList" );    
	  
    // for getting Master data for Grid List 
	$scope.customerChange = function () {
  		$scope.companyName = '';       
  		$scope.getData('vendorController/getCompanyNamesAsDropDown.json', {customerId:$scope.manpower.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.manpower.companyId != undefined && $scope.manpower.companyId != '' ? $scope.manpower.companyId : 0}, 'customerChange');
	};
	  
	 	
	$scope.companyChange = function() {    	   
		$scope.getData('statutorySetupsController/getCountryLocationAndDepartmentDropdown.json', { customerId: ($scope.manpower.customerId == undefined || $scope.manpower.customerId == null )? '0' : $scope.manpower.customerId,companyId: ($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.manpower.companyId != undefined ? $scope.manpower.companyId : 0  }, 'companyChange');
	};
	   
	   
	   
    $scope.locationChange = function(){
	   	if($scope.manpower.locationId != undefined && $scope.manpower.locationId != null && $scope.manpower.locationId != ''){
	   		$scope.departmentsList = [];
	   		//$scope.employeesList = [];
	   		$scope.getData('jobAllocationByVendorController/getPlantsList.json', {locationId:$scope.manpower.locationId}, 'locationChange');
	   	}else{
	   		$scope.plantsList =[];
	   		$scope.departmentsList = [];
	   		//$scope.employeesList = [];
	   	}
    }
	   
	$scope.plantChange = function(){
	   	if($scope.manpower.locationId != undefined && $scope.manpower.locationId != null && $scope.manpower.locationId != '' && $scope.manpower.plantId != undefined && $scope.manpower.plantId != null && $scope.manpower.plantId != ''){
	   		//$scope.employeesList = [];
	   		$scope.getData('associatingDepartmentToLocationPlantController/getDepartMentDetailsByLocationAndPlantId.json', {customerId:$scope.manpower.customerId, companyId: $scope.manpower.companyId, locationId:$scope.manpower.locationId,plantId:$scope.manpower.plantId}, 'plantChange');
	   	}else{
	   		$scope.departmentsList = [];
	   		//$scope.employeesList = [];
	   	}
	}
   
	$scope.departmentChange = function(){
	   	/*if($scope.manpower.locationId != undefined && $scope.manpower.locationId != null && $scope.manpower.locationId != '' && $scope.manpower.departmentId != undefined && $scope.manpower.plantId != null && $scope.manpower.plantId != ''){
	   		$scope.employeesList = [];
	   		$scope.getData('manpowerRequisitionController/getEmployeesList.json', {customerId:$scope.manpower.customerId, companyId: $scope.manpower.companyId, locationId:$scope.manpower.locationId,departmentId:$scope.manpower.departmentId}, 'departmentChange');
	   	}else{
	   		//$scope.employeesList = [];
	   	}*/
	}
  
	$scope.employeeChange = function(){
	   /*	if($scope.manpower.locationId != undefined && $scope.manpower.locationId != null && $scope.manpower.locationId != '' 
	   				&& $scope.manpower.departmentId != undefined && $scope.manpower.departmentId != null  && $scope.manpower.departmentId != "" 
	   					&& $scope.manpower.plantId != undefined  && $scope.manpower.plantId != null && $scope.manpower.plantId != ''
	   						&& $scope.manpower.employeeId != undefined  && $scope.manpower.employeeId != null && $scope.manpower.employeeId != '' ){

	   		$scope.getData('manpowerRequisitionController/getEmployeeDetails.json', {customerId:$scope.manpower.customerId, companyId: $scope.manpower.companyId, locationId:$scope.manpower.locationId,departmentId:$scope.manpower.departmentId, employeeId : $scope.manpower.employeeId}, 'employeeChange');
	   	}*/
		
	}
	
	/*$scope.frequencyChange = function(){
	   	if($scope.manpower.locationId != undefined && $scope.manpower.locationId != null && $scope.manpower.locationId != '' 
	   				&& $scope.manpower.departmentId != undefined && $scope.manpower.departmentId != null  && $scope.manpower.departmentId != "" 
	   					&& $scope.manpower.plantId != undefined  && $scope.manpower.plantId != null && $scope.manpower.plantId != ''
	   						&& $scope.manpower.frequency != undefined  && $scope.manpower.frequency != null && $scope.manpower.frequency != '' ){

	   		$scope.getData('manpowerRequisitionController/getAssignedWorkersCount.json', {customerId:$scope.manpower.customerId, companyId: $scope.manpower.companyId, locationId:$scope.manpower.locationId,departmentId:$scope.manpower.departmentId, employeeId : $scope.manpower.employeeId}, 'frequencyChange');
	   	}
		
	}*/
	
	$scope.skillChange = function(){
		if($scope.manpower.customerId != undefined && $scope.manpower.customerId != null && $scope.manpower.customerId != '' 
					&& $scope.manpower.companyId != undefined && $scope.manpower.companyId != null && $scope.manpower.companyId != ''
					&& $scope.skill.skillType != undefined && $scope.skill.skillType != null && $scope.skill.skillType != ''
					&& $scope.manpower.locationId != undefined && $scope.manpower.locationId != null && $scope.manpower.locationId != '' 
		   				&& $scope.manpower.departmentId != undefined && $scope.manpower.departmentId != null  && $scope.manpower.departmentId != "" 
		   					&& $scope.manpower.plantId != undefined  && $scope.manpower.plantId != null && $scope.manpower.plantId != ''
		   						/*&& $scope.manpower.frequency != undefined  && $scope.manpower.frequency != null && $scope.manpower.frequency != ''*/){
	   		$scope.getData('manpowerRequisitionController/getJobCodeListBySkillType.json', {customerId:$scope.manpower.customerId, companyId: $scope.manpower.companyId, skillType:$scope.skill.skillType, locationId:$scope.manpower.locationId,departmentId:$scope.manpower.departmentId, plantId: $scope.manpower.plantId}, 'skillChange');
	   	}else{
	   		$scope.jobCodesList = [];
	   	}
		
	}
	
	$scope.validateRequestCode = function(){
		if($scope.manpower.customerId != undefined && $scope.manpower.customerId != null && $scope.manpower.customerId != '' 
			&& $scope.manpower.companyId != undefined && $scope.manpower.companyId != null && $scope.manpower.companyId != ''){
			$scope.getData('manpowerRequisitionController/validateRequestCode.json', {customerId:$scope.manpower.customerId, companyId: $scope.manpower.companyId, requestCode:$scope.manpower.requestCode}, 'validateCode');
		}
	}
	
	
	$scope.sendForApprovalAction = function($event){
		if($('#ManpowerRequestForm').valid()){
			$scope.manpower.status = "Pending For Approval";
			$scope.manpower.approvedDate = null;
			$scope.manpower.approvedBy = null;
			$scope.validateRequestCode();
		}
	};
	
	$scope.saveAction = function($event){
		if($('#ManpowerRequestForm').valid()){
			$scope.manpower.status = "Saved";
			$scope.manpower.approvedDate = null;
			$scope.manpower.approvedBy = null;
			$scope.validateRequestCode();
		}
	};
	
	$scope.approveAction = function($event){
		if($('#ManpowerRequestForm').valid()){
			$scope.manpower.status = "Approved";
	  		$scope.manpower.approvedDate = moment($('#approvedDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
			//$scope.saveManpower($event);
	  		$scope.validateRequestCode();
		}
	};
	
	$scope.rejectAction = function($event){
		if($('#ManpowerRequestForm').valid()){
			$scope.manpower.status = "Rejected";
			$scope.manpower.approvedDate = moment($('#approvedDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
			$scope.validateRequestCode();
		}
	};
	
	/*$scope.correctionAction = function($event){
		$scope.manpower.status = "Correction Required";
		$scope.saveManpower($event);
	};*/
	
	
	/*$scope.saveManpower =function($event){
		if($('#ManpowerRequestForm').valid()){
			$scope.validateRequestCode();
    	}
    };*/
 
    $scope.add_ManpowerRecord = function(){
 	   $scope.skill = new Object();
 	   $('#fromDate').val("");
 	   $('#toDate').val("");
 	   
 	   $scope.popUpSave = true;
       $scope.popUpUpdate =false;
 	};
 	
 	$scope.save_ManpowerRecord = function($event){
 		var status = false;;
 		//alert((new Date(moment($('#fromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime() > new Date(moment($('#toDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime()));
 		if($('#manpowerSkillsForm').valid()){
 			if($scope.manpower.manpowerSkillTypesList !=  undefined && $scope.manpower.manpowerSkillTypesList != null && $scope.manpower.manpowerSkillTypesList != ""){
	 			angular.forEach($scope.manpower.manpowerSkillTypesList, function(value, key){	
					if(value.skillType == $scope.skill.skillType && value.jobCodeId == $scope.skill.jobCodeId ){
						status = true;
					}
				});
 			}
 			
 			if(status){
 				$scope.Messager('error', 'Error', 'Jobcode for this skill already exists');
 			}else if($('#fromDate').val() != undefined && $('#fromDate').val() != null && $('#toDate').val() != undefined && $('#toDate').val() != null && $('#toDate').val() != undefined
 	    			   						&& (new Date(moment($('#fromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime() > new Date(moment($('#toDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime())){
 				$scope.Messager('error', 'Error', 'From Date should not be less than to Date');
  		    }else {
  		    	$scope.manpower.manpowerSkillTypesList.push({
 	          		'skillType':$scope.skill.skillType,  		
 	          		'fromDate':$('#fromDate').val(),
 	          		'toDate':$('#toDate').val(),
 	          		'totalDays':$scope.skill.totalDays,
 	          		'jobCodeId':$scope.skill.jobCodeId,
 	          		'jobName':$('#jobCodeId option:selected').html(),
 	          		//'assignedWorkers':$scope.skill.assignedWorkers,
	          		'requiredWorkers':$scope.skill.requiredWorkers,
	          		//'totalCount':$scope.skill.totalCount,
  		    	}); 
 			    $('div[id^="myModal"]').modal('hide');
 			    $scope.calcTotalHeadCount();
  		    }
 		}
 	};
 	
 	
     
     $scope.delete_ManpowerRecord = function($event){    	
  	   var del = $window.confirm('Are you sure you want to delete?');
  	   if (del) {
  		   $scope.manpower.manpowerSkillTypesList.splice($($event.target).parent().parent().index(),1);
  		   $scope.calcTotalHeadCount();
  		   /*angular.forEach($scope.manpower.manpowerSkillTypesList, function(value, key){	
				if(value.totalCount != undefined && value.requiredWorkers != null ){
					total = total+parseInt(value.requiredWorkers);
				}
  		   });
  		   $scope.manpower.totalHeadCount = total;*/
  	   }
     };
     
     $scope.skill1 = new Object();
     $scope.edit_ManpowerRecord = function($event){    	
     	$scope.skillVar = $($event.target).parent().parent().index();
        var data =  $scope.manpower.manpowerSkillTypesList[$($event.target).parent().parent().index()];
        $scope.skill = data;
        //alert(data);
        $scope.skill1.skillType = data.skillType;
        $scope.skill1.jobCodeId = data.jobCodeId;
    	$scope.skill1.fromDate = data.fromDate;
    	$scope.skill1.toDate = data.toDate;
    	$scope.skill1.totalDays = data.totalDays;
    	$scope.skill1.requiredWorkers = data.requiredWorkers;
    	$scope.skillChange();
    	
        $scope.popUpSave = false;
        $scope.popUpUpdate =true;
     };
        
        
    $scope.update_ManpowerRecord = function($event){    
    	var status = false;
	  	  if($('#manpowerSkillsForm').valid()){
	  		  $scope.manpower.manpowerSkillTypesList.splice($scope.particularVar,1);
		  	  if($scope.manpower.manpowerSkillTypesList !=  undefined && $scope.manpower.manpowerSkillTypesList != null && $scope.manpower.manpowerSkillTypesList != ""){
		 			angular.forEach($scope.manpower.manpowerSkillTypesList, function(value, key){	
						if(value.skillType == $scope.skill.skillType && value.jobCodeId == $scope.skill.jobCodeId ){
							status = true;
						}
					});
	 		  }
		  	  
	  		  if(status){
	  			$scope.Messager('error', 'Error', 'Jobcode for this skill already exists');
	  		  }else if($('#fromDate').val() != undefined && $('#fromDate').val() != null && $('#toDate').val() != undefined && $('#toDate').val() != null && $('#toDate').val() != undefined
	    						&& (new Date(moment($('#fromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime() > new Date(moment($('#toDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime())){
	 				$scope.Messager('error', 'Error', 'From Date should not be less than to Date');
	  		  }else {
	  			  $scope.manpower.manpowerSkillTypesList.push({
	  				   'skillType':$scope.skill.skillType,  		
		          		'fromDate':$('#fromDate').val(),
		          		'toDate':$('#toDate').val(),
		          		'totalDays':$scope.skill.totalDays,
		          		'jobCodeId':$scope.skill.jobCodeId,
		          		'jobName':$('#jobCodeId option:selected').html(),
		          		'requiredWorkers':$scope.skill.requiredWorkers
			    	});
	 				$('div[id^="myModal"]').modal('hide');
	 				
	 				$scope.popUpSave = true;
	 				$scope.popUpUpdate =false;
	 				$scope.calcTotalHeadCount();
	 				
	 				/*var total = 0;
	 				
					angular.forEach($scope.manpower.manpowerSkillTypesList, function(value, key){	
						if(value.requiredWorkers != undefined && value.requiredWorkers != null ){
							total = total+parseInt(value.requiredWorkers);
						}
					});
	  		    	
					$scope.manpower.totalHeadCount = total;*/
	  		  }
	  	  }
    };
    
    $scope.close_ManpowerRecord = function(){
 	   $('div[id^="myModal"]').modal('hide');
 	   if($scope.popUpUpdate){
 		 $scope.skill.skillType		  =	$scope.skill1.skillType;
 		 $scope.skill.jobCodeId	 	  = $scope.skill1.jobCodeId;
 		 $scope.skill.fromDate	  	  =	$scope.skill1.fromDate;
 		 $scope.skill.toDate	  	  =	$scope.skill1.toDate;
 		 $scope.skill.totalDays	  	  =	$scope.skill1.totalDays;
 		 $scope.skill.requiredWorkers = $scope.skill1.requiredWorkers
 	   }
 	   
    };
    
    
    $scope.calcTotalHeadCount = function(){
    	var total = 0;
		   
		angular.forEach($scope.manpower.manpowerSkillTypesList, function(value, key){	
			if(value.requiredWorkers != undefined && value.requiredWorkers != null ){
				total = total+parseInt(value.requiredWorkers);
			}
		});
    	
		$scope.manpower.totalHeadCount = total;
    };
    
    $scope.dateChange = function(){
    	if($('#fromDate').val() != undefined && $('#fromDate').val() != null && $('#fromDate').val() != "" &&
    			$('#toDate').val() != undefined && $('#toDate').val() != null && $('#toDate').val() != ""){
    		
	    	var fromDate = $('#fromDate').val();
	    	var toDate = $('#toDate').val();
	    	var from, to, duration;
	    	from = moment(fromDate, 'DD/MM/YYYY'); // format in which you have the date
			to = moment(toDate, 'DD/MM/YYYY');     // format in which you have the date
	
			duration = to.diff(from, 'days')  
			$scope.$apply(function(){
				$scope.skill.totalDays = to.diff(from, 'days')+1;  
			});
    	}
    };
    
    $scope.requiredForChange = function(){
    	//alert(1);
    	$scope.manpower.requestReason = null;
    }
    
  
   
}]);
 
