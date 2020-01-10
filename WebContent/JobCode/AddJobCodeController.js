'use strict';
JobCodeController.controller("JobCodeAddctrl", ['$scope','$window', '$http', '$resource', '$location','$routeParams','$filter','myservice','$cookieStore','$timeout', function ($scope,$window, $http, $resource, $location, $routeParams, $filter, myservice, $cookieStore,$timeout) {
	  $.material.init();


		/*$('#transactionDate').bootstrapMaterialDatePicker({
	        time: false,
	        clearButton: true,
	        format : "DD/MM/YYYY"
	    });*/
	  
	  $('#transactionDate').datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown'
	    	  
	    });

		$scope.jobcode = new Object();
		$scope.job = new Object();
		$scope.list_status = [{ id:"Y" , name:"Active"},{ id:"N" , name:"In Active"}];	
		$scope.jobCodeAssociationWithWorker = false;

		$scope.list_jobType = [{"id":"Full Time","name" : "Full Time" },
			                    {"id":"Part Time","name" : "Part Time" },
			                    {"id":"Permanent","name" : "Permanent" },
			                    {"id":"Temporary","name" : "Temporary" }];


		$scope.list_managerLevel = [{"id":"None","name" : "None" },
		                    {"id":"Supervisor","name" : "Supervisor" },
		                    {"id":"Manager","name" : "Manager" },
		                    {"id":"Mid Manager","name" : "Mid Manager" },
		                    {"id":"Sr. Manager","name" : "Sr. Manager " },
		                    {"id":"VP","name" : " VP " },
		                    {"id":"Director","name" : "Director" },
		                    {"id":"Chairman","name" : "Chairman"}];
		
		$scope.list_skills = [{"id":"Skilled","name" : "Skilled" },
		                       {"id":"Semi Skilled","name" : "Semi Skilled" },
		                       {"id":"High Skilled","name" : "High Skilled" },
		                       {"id":"Special Skilled","name" : "Special Skilled" },
		                       {"id":"UnSkilled","name" : "UnSkilled" }];
		
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
	    	$scope.pageTitle  = "Job Code Details";
	        $scope.readOnly = true;
	        $scope.datesReadOnly = true;
	        $scope.updateBtn = true;
	        $scope.saveBtn = false;
	        $scope.viewOrUpdateBtn = true;
	        $scope.correcttHistoryBtn = false;
	        $scope.resetBtn = false;
	        $scope.transactionList = false;
	        $scope.cancelBtn = false;
	        $scope.returnToSearchBtn = true;
	        $('.sav').hide();
	    } else {
	    	$scope.pageTitle  = "Define Job Code";
	    	$('#transactionDate').val($filter('date')(new Date(),'dd/MM/yyyy'));
	        $scope.readOnly = false;
	        $scope.onlyRead = false;
	        $scope.datesReadOnly = false;
	        $scope.updateBtn = false;
	        $scope.saveBtn = true;
	        $scope.viewOrUpdateBtn = false;
	        $scope.correcttHistoryBtn = false;
	        $scope.resetBtn = true;
	        $scope.transactionList = false;
	        $scope.jobcode.status='Y';
	        $scope.returnToSearchBtn = true;
	        $scope.cancelBtn = false;
	        $('.sav').hide();
	        $('#trainingPanel').hide();
	        $('#equipPanel').hide();
	        $('#skillPanel').hide();
	        $('#medicalPanel').hide();
	        
	    }

	    $scope.updateJobcodeBtnAction = function (this_obj) {
	    	$('#transactionDate').val($filter('date')(new Date(),'dd/MM/yyyy'));
	    	$scope.jobcode.transactionDate = $filter('date')(new Date(),'dd/MM/yyyy');
	        $scope.readOnly = false;
	        $scope.onlyRead = true;
	        $scope.datesReadOnly = false;
	        $scope.updateBtn = false;
	        $scope.saveBtn = true;
	        $scope.viewOrUpdateBtn = false;
	        $scope.correctHistoryBtn = false;
	        $scope.resetBtn = false;
	        $scope.cancelBtn = true;
	        $scope.transactionList = false;
	        $scope.returnToSearchBtn = true;
	        $scope.jobcode.jobCodeDetailsId = 0;
	        $('.dropdown-toggle').removeClass('disabled');
	    }

	    $scope.cancelBtnAction = function(){
	    	$scope.readOnly = true;
	        $scope.datesReadOnly = true;
	        $scope.updateBtn = true;
	        $scope.saveBtn = false;
	        $scope.viewOrUpdateBtn = true;
	        $scope.correcttHistoryBtn = false;
	        $scope.resetBtn = false;
	        $scope.transactionList = false;
	        $scope.cancelBtn = false;
	        $scope.returnToSearchBtn = true;
	        $('.sav').hide();
	        $scope.getData('JobcodeController/getJobCodeById.json', { jobCodeDetailsId: $routeParams.id, jobCodeId:$routeParams.id1 ,customerId : ''}, 'jobcodeList')
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
	        $scope.returnToSearchBtn = true;
	        $scope.cancelBtn = false;
	        $scope.getData('JobcodeController/getTransactionDatesList.json', { companyId: $scope.jobcode.companyId, customerId: $scope.jobcode.customerId ,jobCodeId: $scope.jobcode.jobCodeId }, 'transactionDatesChange');

	        $('.dropdown-toggle').removeClass('disabled');
	    }


	    $scope.getData = function (url, data, type,buttonDisable) {
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
	        	} else if (type == 'jobcodeList') {
					//alert(JSON.stringify(response.data));
	                $scope.jobcodeList = response.data;
	                
	                $scope.jobcode = response.data.jobCodeVo[0];	               
	                $scope.jobcodeList.customerList= response.data.customerList;
	                $scope.jobcodeList.companyList = response.data.companyList;
	                $scope.jobcodeList.countryList = response.data.countrylist;
	                
	                if($scope.jobcode != undefined ){
	                	$scope.transactionModel= $scope.jobcode.jobCodeDetailsId;
	                	myservice.jobCodeDetailsId = $cookieStore.put('jobCodeDetailsId', $scope.jobcode.jobCodeDetailsId);
	                	$cookieStore.put('jobCodeId', $scope.jobcode.jobCodeId);
	                	//$scope.jobcode.jobCodeDetailsId = 
	                	$scope.jobcode.transactionDate =  $filter('date')( response.data.jobCodeVo[0].transactionDate, 'dd/MM/yyyy');
	                	
	                }else{
	               	   $scope.jobcode = new Object(); 
	               	   $scope.jobcode.transactionDate =  $filter('date')(new Date(), 'dd/MM/yyyy');
	               	   $scope.jobcode.status='Y';
	               	   $scope.jobcode.managerLevel="None";
	               	  if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
	   		                $scope.jobcode.customerId=response.data.customerList[0].customerId;		                
	   		                $scope.customerChange();
	   		                }
	               	  
	                  }
	               //$scope.jobcode.companyId = response.data.jobCodeVo[0].companyId;
	             // for button views
	  			  	if($scope.buttonArray == undefined || $scope.buttonArray == '')
	                $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Job Code'}, 'buttonsAction');
	                
	            } else  if (type == 'companyList') {
	            	$scope.jobcodeList.companyList = response.data;
	            	if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	   	                $scope.jobcode.companyId = response.data[0].id;
	   	                $scope.companyChange();
	   	            }
	            	
	            }else if(type == 'countryList'){
	            	//alert(JSON.stringify(response.data.Profile));
	            	$scope.jobcodeList.countryList = response.data.countryList;
	            	if( response.data.countryList[0] != undefined && response.data.countryList[0] != "" && response.data.countryList.length == 1 ){
	   	                $scope.jobcode.countryId = response.data.countryList[0].id;
	   	            }
	            	$scope.jobcode.standardHours = response.data.Profile[0].standardHoursPerWeek;
	            }else if(type == 'validateCode'){
	            	$scope.jcode = new Object();
	                $scope.jCode = response.data;
	            	
	            }else if (type == 'saveJobcode') {
	            	//myservice.jobCodeDetailsId = response.data.id;
	                //$cookieStore.put('jobCodeDetailsId', response.data.name);
	            	//alert(angular.toJson(response.data));
	            	if(response.data.id == 0){
	            		$scope.Messager('success', 'success', 'Job Code Saved Successfully',buttonDisable);                	
	                	if($scope.saveBtn == true)
	                		$location.path('/AddJobCode/'+response.data.name);
	            	}else if(response.data.id == -1){
	            		$scope.Messager('error', 'Error', response.data.name,buttonDisable); 
	            	}else{
	            		$scope.Messager('error', 'Error', 'Error in saving the details',buttonDisable); 
	            	}
                	
	            }else if (type == 'transactionDatesChange') {
	                //alert(angular.toJson(response.data));
	            	//var obj = response.data;
	            	//$scope.transactionModel= response.data[(obj.length)-1].id;
	                $scope.transactionDatesList = response.data;
	            }else if(type == 'trainingList'){
	            	$scope.TrainingName = response.data;
	            }else if(type == 'validateJobcodeAssociationWithWorker'){
	            	//alert(angular.toJson(response.data));
	            	if(response.data == 1){	            		
	            		$scope.Messager('error', 'Error', 'You cannot In Active Job Code, Because it is Associated with Active Workers'); 
	            		return;
	            	}else if(response.data == 2){
	            		$scope.Messager('error', 'Error', 'Error in Saving the data'); 
	            	}else{	            		
	            		  $scope.jobcode.createdBy = $cookieStore.get('createdBy'); 
					 	  $scope.jobcode.modifiedBy = $cookieStore.get('modifiedBy'); 
				    	  $scope.jobcode.transactionDate = $('#transactionDate').val();
				    	  $scope.jobcode.trainingList =$scope.jobcodeList.trainingTypes;
				    	  $scope.jobcode.skillList =$scope.jobcodeList.skillTests;
				    	  $scope.jobcode.medicalList =$scope.jobcodeList.medicalTests;
				    	  $scope.jobcode.equipmentList =$scope.jobcodeList.equipmentTypes;			    	 
				          $scope.getData('JobcodeController/saveJobcode.json', angular.toJson($scope.jobcode), 'saveJobcode');
	            	}
	            }
	            
	            
	        },
	        function () { 
	        	$scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable); 
	        });
	    }
	    $scope.getData('JobcodeController/getJobCodeById.json', { jobCodeDetailsId: $routeParams.id, jobCodeId:$routeParams.id1 ,customerId : $cookieStore.get('customerId')}, 'jobcodeList')
	   
	    $scope.customerChange = function () {
	    	//alert($scope.jobcode.customerId);
	    	$scope.getData('vendorController/getCompanyNamesAsDropDown.json', {customerId:$scope.jobcode.customerId ,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.jobcode.companyId  }, 'companyList');
	    }

	    $scope.companyChange= function(){
	    	if($scope.jobcode.companyId != null && $scope.jobcode.companyId != undefined && $scope.jobcode.companyId != ''
	    		&& $scope.jobcode.customerId != null && $scope.jobcode.customerId != undefined && $scope.jobcode.customerId != ''){
	    		$scope.getData('LocationController/getCmpProfileDept.json', { customerId: $scope.jobcode.customerId, companyId: $scope.jobcode.companyId }, 'countryList');
	    	}
	    };
	    
	    $scope.fun_checkJobCode = function(){
	    	if($scope.jobcode.customerId != null || $scope.jobcode.customerId != undefined || $scope.jobcode.customerId != ''
	    			&& ($scope.jobcode.companyId != null || $scope.jobcode.companyId != undefined || $scope.jobcode.companyId != '')
	    				    			&& ($scope.jobcode.jobCode != null || $scope.jobcode.jobCode != undefined || $scope.jobcode.jobCode != '')){
	    		$scope.getData('JobcodeController/validateJobcode.json',{ customerId : $scope.jobcode.customerId, companyId: $scope.jobcode.companyId, jobCode:$scope.jobcode.jobCode },'validateCode')
	    		//$scope.getData('JobcodeController/validateJobcodeAssociationWithWorker.json',{ customerId : $scope.jobcode.customerId, companyId: $scope.jobcode.companyId, jobCode:$scope.jobcode.jobCode },'validateJobcodeAssociationWithWorker')
	    	}
	    };
	    
	    $scope.statusChange = function(){
	    	
	    }
	    
	    $scope.save = function () {
	    	
	    	if($scope.jobcode.minimumStandardHours != null && $scope.jobcode.minimumStandardHours != undefined  && $scope.jobcode.minimumStandardHours != '' &&  $scope.jobcode.minimumStandardHours > 168){
				   $scope.jobcode.minimumStandardHours="";
				   $scope.Messager('error', 'Error', 'Time should be less than 168 Hours');
	       	}else if($scope.jobcode.maximumStandardHours != null && $scope.jobcode.maximumStandardHours != undefined  && $scope.jobcode.maximumStandardHours != '' &&  $scope.jobcode.maximumStandardHours > 168){
				   $scope.jobcode.maximumStandardHours="";
				   $scope.Messager('error', 'Error', 'Time should be less than 168 Hours');
	       	}else if($scope.jobcode.standardHours != null && $scope.jobcode.standardHours != undefined  && $scope.jobcode.standardHours != '' &&  $scope.jobcode.standardHours > 168){
				   $scope.jobcode.standardHours="";
				   $scope.Messager('error', 'Error', 'Time should be less than 168 Hours');
	       	}else if($('#jobcodeForm').valid()){
	    		if($scope.jCode == 0){
		    		$scope.Messager('error', 'Error', 'JobCode Already Available'); 
		    	}else{
		    		
		    		if($scope.jobcode.status == 'N'){
		    			$scope.getData('JobcodeController/validateJobcodeAssociationWithWorker.json',{ customerId : $scope.jobcode.customerId, companyId: $scope.jobcode.companyId, jobCode:$scope.jobcode.jobCode },'validateJobcodeAssociationWithWorker');
		    		}else{
		    			  $scope.jobcode.createdBy = $cookieStore.get('createdBy'); 
					 	  $scope.jobcode.modifiedBy = $cookieStore.get('modifiedBy'); 
				    	  $scope.jobcode.transactionDate = $('#transactionDate').val();
				    	  $scope.jobcode.trainingList =$scope.jobcodeList.trainingTypes;
				    	  $scope.jobcode.skillList =$scope.jobcodeList.skillTests;
				    	  $scope.jobcode.medicalList =$scope.jobcodeList.medicalTests;
				    	  $scope.jobcode.equipmentList =$scope.jobcodeList.equipmentTypes;			    	 
				          $scope.getData('JobcodeController/saveJobcode.json', angular.toJson($scope.jobcode), 'saveJobcode');
		    			
		    		}
		    		
		    		
		    	}
		       
	    	}  
	   
	    };
	    
	    $scope.correctHistorySave = function () {
	    	if($('#jobcodeForm').valid()){
	    	  $scope.jobcode.createdBy = $cookieStore.get('createdBy'); 
	 	      $scope.jobcode.modifiedBy = $cookieStore.get('modifiedBy'); 
	    	  $scope.jobcode.transactionDate = $('#transactionDate').val();
	    	  $scope.jobcode.trainingList =$scope.jobcodeList.trainingTypes;
	    	  $scope.jobcode.skillList =$scope.jobcodeList.skillTests;
	    	  $scope.jobcode.medicalList =$scope.jobcodeList.medicalTests;
	    	  $scope.jobcode.equipmentList =$scope.jobcodeList.equipmentTypes;
	    	  //$scope.jobcode.jobCodeDetailsId = $cookieStore.get('jobCodeDetailsId');
		      //$scope.jobcode.modifiedBy = $cookieStore.get('modifiedBy');
	    	 // alert(JSON.stringify(angular.toJson($scope.jobcode)));
		       $scope.getData('JobcodeController/saveJobcode.json', angular.toJson($scope.jobcode), 'saveJobcode');
	    	}  
	    };
	   
	    $scope.transactionDatesListChange = function(){
	        $('.dropdown-toggle').removeClass('disabled');
	       if($scope.transactionModel != undefined && $scope.transactionModel != '')
	       $scope.getData('JobcodeController/getJobCodeById.json', { jobCodeDetailsId: $scope.transactionModel, jobCodeId:$routeParams.id1 ,customerId: '' }, 'jobcodeList')
	       
	    }
	    
	   /* $scope.getTrainingTypes = function(){
	    	 $scope.getData('/JobcodeController/getTrainingTypes.json', { jobCodeId:$scope.jobCodeId}, 'trainingList');
	    }*/
	    
	  /*  $scope.getTrainingTypes = function(){
	    	 $scope.getData('/JobcodeController/getTrainingTypes.json', { jobCodeId:$scope.jobCodeId}, 'trainingList');
	    }
	    
	    $scope.SkillTestinglist = function(){
	    	 $scope.getData('/JobcodeController/getTrainingTypes.json', { jobCodeId:$routeParams.id1}, 'SkillTestinglist');
	    }
	    
	    $scope.HealthScreeninglist = function(){
	    	 $scope.getData('/JobcodeController/getTrainingTypes.json', { jobCodeId:$routeParams.id1}, 'HealthScreeninglist');
	    }
	    
	    $scope.PPERequiredlist = function(){
	    	 $scope.getData('/JobcodeController/getTrainingTypes.json', { jobCodeId:$routeParams.id1}, 'PPERequiredlist');
	    }*/
	
	   /* $scope.saveChanges = function(){    	 
	    	alert($scope.jobcodeList.trainingTypes.name );
	    	$scope.jobcodeList.trainingList.push({
	    		'id':$scope.job.index,
	    		'name':$scope.job.name    		
	    	});    	
	       }*/

	    
	    // Training Start
	    $scope.fun_delete = function($event){  	  
	    	var del = $window.confirm('Are you sure you want to delete?');
	    	if (del) {
	    		$scope.jobcodeList.trainingTypes.splice($($event.target).parent().parent().index(),1);	
	    		if($scope.jobcodeList.trainingTypes == null || $scope.jobcodeList.trainingTypes == undefined || $scope.jobcodeList.trainingTypes == ''){
	    		 $('#trainingPanel').hide();
	    		}
	    		$scope.job.name = "";
	    	}
	    	$('.add').show();
	    	$('.sav').hide();
	    	
	    }
	    
	    $scope.fun_edit = function($event){
	    	$('.add').hide();
	    	$('.sav').show();
	    	$scope.trainIndex = $($event.target).parent().parent().index();
	    	var data = $scope.jobcodeList.trainingTypes[$($event.target).parent().parent().index()];	    	
	    	$scope.job.name = data.name;	    		   
	    	$scope.popUpSave = false;
	    	$scope.popUpUpdate =true;
	    	 
	    }
	    
	    $scope.sav =function($event){	    	
	    	if($scope.job.name != undefined && $scope.job.name != ''){	    		
	    		var status = false;
	    		angular.forEach($scope.jobcodeList.trainingTypes, function(value, key){		    			
				      if(value.name == $scope.job.name){
				    	  
				    	  status = true;			    		
				      }				        
				   });	
	    		if(status){
	    			$scope.Messager('error', 'Error', 'Training Name Already Exists',true); 
	    			return;
	    		}
	    			
	    		
		    	$scope.jobcodeList.trainingTypes.splice($scope.trainIndex,1);	    	
		    	$scope.jobcodeList.trainingTypes.push({	    	
		    		'name':$scope.job.name    	  		
		    	});
		    	$scope.job.name = "";
		    	$('.add').show();
		    	$('.sav').hide();
	    	}else{
	    		$scope.Messager('error', 'Error', 'Please enter training name'); 
	    	}    
	    	
	    }
	    
	    $scope.add =function(){	  
	    	if($scope.job.name != undefined && $scope.job.name != ''){
	    		var status = false;
	    		angular.forEach($scope.jobcodeList.trainingTypes, function(value, key){		    		
				      if(value.name == $scope.job.name){
				    	  $scope.Messager('error', 'Error', 'Training Name Exists',true); 
				    	  status = true;			    		
				      }				         
				   });	
	    		if(status){
	    			$scope.Messager('error', 'Error', 'Training Name Exists',true); 
	    			return;
	    		}
	    		
	    		$('#trainingPanel').show();
		    	$scope.jobcodeList.trainingTypes.push({	    		
		    		'name':$scope.job.name    		
		    	});
		    	$scope.job.name = "";
		    	$('.add').show();
		    	$('.sav').hide();
	    	}	else{
	    		$scope.Messager('error', 'Error', 'Please enter training name'); 
	    	}    		   
	    	
	    }
	    // Training END 
	  
	    $scope.saveChanges=function(){
	    	  $('div[id^="myModal"]').modal('hide');
	    	  $('.add').show();
		      $('.sav').hide();
	    }
	    // Medical Start
	    $scope.m_delete = function($event){  
	    	var del = $window.confirm('Are you sure you want to delete?');
	    	if (del) {
	    		$scope.jobcodeList.medicalTests.splice($($event.target).parent().parent().index(),1);
	    		if($scope.jobcodeList.medicalTests == null || $scope.jobcodeList.medicalTests == undefined || $scope.jobcodeList.medicalTests == ''){
	    			$('#medicalPanel').hide();
	    		}
	    		$scope.job.medicalName = "";
	    		$scope.job.isOnBoard = false;
	    		$scope.job.isPeriodic = false;
	    	}
	    	$('.add').show();
    		$('.sav').hide();
	    }
	    
	    $scope.m_edit = function($event){
	    	$('.add').hide();
	    	$('.sav').show();
	    	$scope.medical_index = $($event.target).parent().parent().index();
	    	
	    	var data = $scope.jobcodeList.medicalTests[$($event.target).parent().parent().index()];	    
	    	$scope.job.medicalName = data.name;
	    	$scope.job.isOnBoard =  data.isOnBoard;
	    	$scope.job.isPeriodic = data.isPeriodic;	    	
	    	$scope.popUpSave = false;
	    	$scope.popUpUpdate =true;
	    	 
	    }
	    
	    $scope.save_medical =function($event){
	    	if($scope.job.medicalName != undefined && $scope.job.medicalName != ''){
	    		
	    		var status = false;
	    		angular.forEach($scope.jobcodeList.medicalTests, function(value, key){		    			
				      if(value.name == $scope.job.medicalName){				    	  
				    	  status = true;			    		
				      }				        
				   });	
	    		if(status){
	    			$scope.Messager('error', 'Error', 'Medical Test Already Exists',true); 
	    			return;
	    		}
	    			
	    		
		    	$scope.jobcodeList.medicalTests.splice($scope.medical_index,1);
		    	
		    	$scope.jobcodeList.medicalTests.push({	    	
		    		'name':$scope.job.medicalName,
		    		'isPeriodic': $scope.job.isPeriodic,
		    		'isOnBoard' : $scope.job.isOnBoard
		    	});
		    	$('.add').show();
		    	$('.sav').hide();
		    	$scope.job.medicalName = "";
		    	$scope.job.isOnBoard = false;
		    	$scope.job.isPeriodic = false;
		    	setTimeout(function () {                   
                    $.material.init();
                },10)
	    	}else{
	    		$scope.Messager('error', 'Error', 'Please enter medical test name'); 
	    	}    
	    }
	    
	    $scope.add_medical =function($event){	
	    	if($scope.job.medicalName != undefined && $scope.job.medicalName != ''){
	    		
	    		var status = false;
	    		angular.forEach($scope.jobcodeList.medicalTests, function(value, key){	    		
				      if(value.name == $scope.job.medicalName){				    	  
				    	  status = true;			    		
				      }				        
				   });	
	    		if(status){
	    			$scope.Messager('error', 'Error', 'Medical Test Already Exists',true); 
	    			return;
	    		}
	    		
	    		$('#medicalPanel').show();
		    	$scope.jobcodeList.medicalTests.push({
		    		//'id':$scope.job.index,
		    		'name':$scope.job.medicalName,   
		    		'isPeriodic': $scope.job.isPeriodic,
		    		'isOnBoard' : $scope.job.isOnBoard
		    	});
		    	$('.add').show();
		    	$('.sav').hide();
		    	$scope.job.medicalName = "";
		    	$scope.job.isOnBoard = false;
		    	$scope.job.isPeriodic = false;
		    	setTimeout(function () {                   
                    $.material.init();
                },10)
	    	}else{
	    		$scope.Messager('error', 'Error', 'Please enter medical test name'); 
	    	}    
	    	
	    }
	    // Medical END
	    
	    // SKILL START 
	    $scope.s_delete = function($event){  	 
	    	var del = $window.confirm('Are you sure you want to delete?');
	    	if (del) {  	
	    		$scope.jobcodeList.skillTests.splice($($event.target).parent().parent().index(),1);	
	    		if($scope.jobcodeList.skillTests == null || $scope.jobcodeList.skillTests == undefined || $scope.jobcodeList.skillTests == ''){
	    			$('#skillPanel').hide();
	    		}
	    		$scope.job.skillName = "";
	    	}
	    	$('.add').show();
    		$('.sav').hide();
	    }
	    
	   
	    $scope.s_edit = function($event){
	    	$('.add').hide();
	    	$('.sav').show();		 
	    	$scope.skill_index = $($event.target).parent().parent().index();
	    	var data = $scope.jobcodeList.skillTests[$($event.target).parent().parent().index()];	    	
	    	$scope.job.skillName = data.name;	    		   
	    	$scope.popUpSave = false;
	    	$scope.popUpUpdate =true;
	    	 
	    }
	    
	    $scope.save_skill =function($event){	    	
	    	if($scope.job.skillName != undefined && $scope.job.skillName != ''){
	    		
	    		var status = false;
	    		angular.forEach($scope.jobcodeList.skillTests, function(value, key){	    		
				      if(value.name == $scope.job.skillName){				    	  
				    	  status = true;			    		
				      }				        
				   });	
	    		if(status){
	    			$scope.Messager('error', 'Error', 'Skill  Already Exists',true); 
	    			return;
	    		}
	    		
		    	$scope.jobcodeList.skillTests.splice($scope.skill_index,1);	    	
		    	$scope.jobcodeList.skillTests.push({	    	
		    		'name':$scope.job.skillName    	  		
		    	});
		    	$scope.job.skillName = "";
		    	$('.add').show();
		    	$('.sav').hide();		    	
	    	}else{
	    		$scope.Messager('error', 'Error', 'Please enter skill name'); 
	    	}    
	    	
	    }
	    
	    $scope.add_skill =function(){	  
	    	if($scope.job.skillName != undefined && $scope.job.skillName != ''){
	    		
	    		var status = false;
	    		angular.forEach($scope.jobcodeList.skillTests, function(value, key){	    		
				      if(value.name == $scope.job.skillName){				    	  
				    	  status = true;			    		
				      }				        
				   });	
	    		if(status){
	    			$scope.Messager('error', 'Error', 'Skill  Already Exists',true); 
	    			return;
	    		}
	    		
	    		$('#skillPanel').show();
		    	$scope.jobcodeList.skillTests.push({	    		
		    		'name':$scope.job.skillName    		
		    	});
		    	$scope.job.skillName = "";
		    	$('.add').show();
		    	$('.sav').hide();			    	
	    	}	else{
	    		$scope.Messager('error', 'Error', 'Please enter skill name'); 
	    	}        		   
	    }
	    
	    // SKILL END 
	    
	    
	    // EQuip START 
	    $scope.e_delete = function($event){  	    	
	    	var del = $window.confirm('Are you sure you want to delete?');
	    	if (del) {  	
		    	$scope.jobcodeList.equipmentTypes.splice($($event.target).parent().parent().index(),1);	
	    		if($scope.jobcodeList.equipmentTypes == null || $scope.jobcodeList.equipmentTypes == undefined || $scope.jobcodeList.equipmentTypes == ''){
	    			$('#equipPanel').hide();
	    		}
	    		$scope.job.equipName = "";
	    	}
	    	$('.add').show();
	    	$('.sav').hide();
	    }
	    
	   
	    $scope.e_edit = function($event){
	    	$('.add').hide();
	    	$('.sav').show();		 
	    	$scope.equip_index = $($event.target).parent().parent().index();
	    	var data = $scope.jobcodeList.equipmentTypes[$($event.target).parent().parent().index()];
	    
	    	$scope.job.equipName = data.name;	    		   
	    	$scope.popUpSave = false;
	    	$scope.popUpUpdate =true;
	    	 
	    }
	    
	    $scope.save_equip =function($event){
	    
	    	if($scope.job.equipName != undefined && $scope.job.equipName != ''){
	    		
	    		var status = false;
	    		angular.forEach($scope.jobcodeList.equipmentTypes, function(value, key){	    		
				      if(value.name == $scope.job.equipName){				    	  
				    	  status = true;			    		
				      }				        
				   });	
	    		if(status){
	    			$scope.Messager('error', 'Error', 'equipment name Exists',true); 
	    			return;
	    		}
	    		
		    	$scope.jobcodeList.equipmentTypes.splice($scope.equip_index,1);	    	
		    	$scope.jobcodeList.equipmentTypes.push({	    	
		    		'name':$scope.job.equipName    	  		
		    	});
		    	$scope.job.equipName = "";
		    	$('.add').show();
		    	$('.sav').hide();
	    	}else{
	    		$scope.Messager('error', 'Error', 'Please enter equipment name'); 
	    	}    
	    	
	    }
	    
	    $scope.add_equip =function(){	  
	    	if($scope.job.equipName != undefined && $scope.job.equipName != ''){
	    		var status = false;
	    		angular.forEach($scope.jobcodeList.equipmentTypes, function(value, key){	    		
				      if(value.name == $scope.job.equipName){				    	  
				    	  status = true;			    		
				      }				        
				   });	
	    		if(status){
	    			$scope.Messager('error', 'Error', 'equipment name Exists',true); 
	    			return;
	    		}
	    		
	    		$('#equipPanel').show();
		    	$scope.jobcodeList.equipmentTypes.push({	    		
		    		'name':$scope.job.equipName    		
		    	});
		    	$scope.job.equipName = "";
		    	$('.add').show();
		    	$('.sav').hide();
	    	}	else{
	    		$scope.Messager('error', 'Error', 'Please enter equipment name'); 
	    	}        		   
	    }
	    
	    // EQuip END 
	    
	

    $("#AddTypesofTraining").click(function () {
    	if ($("#AddTypesofTraining").attr("disabled") == "disabled") {
            e.preventDefault();
        }else {
	        var TrainingRequired = $('#TrainingRequired').is(':checked');
	        $('.sav').hide();
	        if (TrainingRequired) {
	            $('.TrainingRequired-modal').modal({
	                backdrop: 'static'
	            });
	        } else {
	            alert('Please Check Training Required Checkbox');
	        }
        }
    });

   

    $("#AddTypesofEquipment").click(function () {
    	if ($("#AddTypesofTraining").attr("disabled") == "disabled") {
            e.preventDefault();
        }else {
	        var PPERequired = $('#PPERequired').is(':checked');
	        if (PPERequired) {
	            $('.PPERequired-modal').modal({
	                backdrop: 'static'
	            });
	        } else {
	            alert('Please Check PPE Required Checkbox');
	        }
        }
    });

   /* var PPERequiredlist = $resource('/JobCode/SearchJobCode/PPERequired.json');
    PPERequiredlist.get(function (user) {
        $scope.EquipmentName = user.result;
    });*/

    $("#AddTypesofTesting").click(function () {
    	if ($("#AddTypesofTraining").attr("disabled") == "disabled") {
            e.preventDefault();
        }else {
	        var SkillTesting = $('#SkillTesting').is(':checked');
	        if (SkillTesting) {
	            $('.SkillTesting-modal').modal({
	                backdrop: 'static'
	            });
	        } else {
	            alert('Please Check Skill Testing Checkbox');
	        }
        }
    });

    $("#AddMedicalTests").click(function () {
    	if ($("#AddTypesofTraining").attr("disabled") == "disabled") {
            e.preventDefault();
        }else {
	        var HealthScreeningrequired = $('#HealthScreeningrequired').is(':checked');
	        $.material.init();
	        if (HealthScreeningrequired) {
	            $('.HealthScreeningrequired-modal').modal({
	                backdrop: 'static'
	            });
	        } else {
	            alert('Please Check Health Screening Required Checkbox');
	        }
        }
    });



    JobCodeController.directive('onLastTrainingname', function () {
        return function (scope, element, attr) {
            if (scope.$last) {
                setTimeout(function () {
                    $('.TrainingName').DataTable({
                        "bLengthChange": false,
                        "bFilter": false,
                        "bInfo": false,
                        "bAutoWidth": false
                    });
                }, 1);
            }
        }
    });


    JobCodeController.directive('onLastEquipmentname', function () {
        return function (scope, element, attr) {
            if (scope.$last) {
                setTimeout(function () {
                    $('.EquipmentName').DataTable({
                        "bLengthChange": false,
                        "bFilter": false,
                        "bInfo": false,
                        "bAutoWidth": false
                    });
                }, 1);
            }
        }
    });

    JobCodeController.directive('onLastTestname', function () {
        return function (scope, element, attr) {
            if (scope.$last) {
                setTimeout(function () {
                    $('.Testname').DataTable({
                        "bLengthChange": false,
                        "bFilter": false,
                        "bInfo": false,
                        "bAutoWidth": false
                    });
                    $.material.init();
                }, 1);
            }
        }
    });

    JobCodeController.directive('onLastMedicaltestname', function () {
        return function (scope, element, attr) {
            if (scope.$last) {
                setTimeout(function () {
                    $('.Medicaltestname').DataTable({
                        "bLengthChange": false,
                        "bFilter": false,
                        "bInfo": false,
                        "bAutoWidth": false
                    });
                    $.material.init();
                }, 1);
            }
        }
    });
    
   
  
}]);


