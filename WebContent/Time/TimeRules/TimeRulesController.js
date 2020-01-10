'use strict';

timeController.controller('timeCtrl', ['$location','$scope','$http', '$resource','$routeParams','$filter','myservice','$cookieStore','$window', function ($location,$scope,$http, $resource, $routeParams,$filter,myservice,$cookieStore,$window) {
	$.material.init();
		
		/*$('#transactionDate').bootstrapMaterialDatePicker({
	        time : false,
	        Button : true,
	        format : "DD/MM/YYYY",
	        clearButton: true
	    }); */
	 
	 //$('#hoursDay').timeEntry({show24Hours: true, showSeconds: false});
	 //$('#hoursWeek').timeEntry({show24Hours: true, showSeconds: false});
	 
	 $('#transactionDate').datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown'
	    	  
	 });
	 
	 $scope.weekDays = [ 	{"id":"1","name" : "Monday" },
							{"id":"2","name" : "Tuesday" },
							{"id":"3","name" : "Wednesday" },
							{"id":"4","name" : "Thursday" },
							{"id":"5","name" : "Friday" },
							{"id":"6","name" : "Saturday" },
							{"id":"7","name" : "Sunday" }
							];
    
     $scope.timeEventList = [ 	{"id":"Late Punch In","name" : "Late Punch In" },
							{"id":"Early Punch Out","name" : "Early Punch Out" },
							{"id":"Long Break","name" : "Long Break" },
							{"id":"Long Meals","name" : "Long Meals" },
							{"id":"Late In and Early Out","name" : "Late In and Early Out" },
							
							];
    
     $scope.penalityList = [ {"id":"Mark Absent","name" : "Mark Absent" },
								{"id":"Mark HD","name" : "Mark HD" }];
		
		
     
     $scope.timeRule = new Object();
	 $scope.timeRule.timeRulesInfoId = $routeParams.id;
	 $scope.timeRule.workDayStatusList = [];
	 $scope.timeRule.bufferTimeLsit = [];

	 if($routeParams.id > 0){
	    $scope.readonly = true;
	    $scope.updateBtn = true;
	    $scope.saveBtn = false;
	    $scope.correctHistoryBtn = false;
	    $scope.resetBtn = false;
	    $scope.cancelBtn = false;
	    $scope.viewOrUpdateBtn = true;
	    $scope.transactionList = false;
	    $scope.gridButtons=false;
     }else{
    	$scope.readonly = false;
 	    $scope.updateBtn = false;
 	    $scope.saveBtn = true;
 	    $scope.correctHistoryBtn = false;
 	    $scope.resetBtn = true;
 	    $scope.cancelBtn = false;
 	    $scope.viewOrUpdateBtn = false;
 	    $scope.transactionList = false;
 	    $scope.gridButtons=true;
 	    $scope.timeRule.isActive = 'Y';
	    $scope.timeRule.followFirstInLastOut = 'Y';
	    $scope.transactionDate = $filter('date')(new Date(),"dd/MM/yyyy");
     }
	 
	 $scope.updateBtnAction = function (this_obj) {
	    $scope.ruleCode = 0;
        $scope.readOnly = false;
        $scope.datesReadOnly = false;
        $scope.updateBtn = false;
        $scope.saveBtn = true;
        $scope.viewOrUpdateBtn = false;
        $scope.correctHistoryBtn = false;
        $scope.resetBtn = false;
        $scope.cancelBtn = true;
        $scope.addrHistory = true;
        $scope.transactionList = false;  
        $scope.gridButtons = true;
        $scope.cancelBtn = true;
        $('.dropdown-toggle').removeClass('disabled');
        $scope.timeRule.transactionDate = $filter('date')(new Date(),"dd/MM/yyyy");
	 }

     $scope.viewOrEditHistory = function () {
        $scope.readOnly = false;
        $scope.datesReadOnly = false;
        $scope.updateBtn = false;
        $scope.saveBtn = false;
        $scope.viewOrUpdateBtn = false;
        $scope.correctHistoryBtn = true;
        $scope.resetBtn = false;      
        $scope.transactionList = true;
        $scope.gridButtons = true;
        $scope.cancelBtn = false;
        $scope.getData('timeRulesController/getTransactionDatesListForTimeRules.json', { timeRulesId: $scope.timeRule.timeRulesId != undefined && $scope.timeRule.timeRulesId != '' ? $scope.timeRule.timeRulesId : '0' }, 'getTransactionDates');      
        $('.dropdown-toggle').removeClass('disabled');
     }
 
	 $scope.cancelButnAction = function(){
    	 $scope.readOnly = true;
         $scope.datesReadOnly = true;
         $scope.updateBtn = true;
         $scope.saveBtn = false;
         $scope.viewOrUpdateBtn = true;
         $scope.correctHistoryBtn = false;
         $scope.resetBtn = false;      
         $scope.transactionList = false;
         $scope.gridButtons = false;
         $scope.cancelBtn = false;
         $scope.getData('timeRulesController/getDetailsByRuleInfoId.json', {timeRulesInfoId:$scope.timeRule.timeRulesId != null ? $scope.timeRule.timeRulesId : '0'}, 'getDetailsById');
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
	    	} else if(type == 'getDetailsById'){        		
	        		$scope.customerList = response.data.customerList; 
	        		
	        		if(response.data.timeRulesList != null && response.data.timeRulesList != undefined && response.data.timeRulesList.length  > 0){
	        			$scope.timeRule= response.data.timeRulesList[0];
	        			$scope.companyList = response.data.companyList; 
	        			$scope.countriesList = response.data.countriesList;
	        			
	        			$scope.transactionDate = $filter('date')( response.data.timeRulesList[0].transactionDate, 'dd/MM/yyyy');
	        		}else {
	        			if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
	        				$scope.timeRule.customerId=response.data.customerList[0].customerId;		                
	        				$scope.customerChange();
   		              	}
	        		}
	        		// for button views
	        		if($scope.buttonArray == undefined || $scope.buttonArray == '')
	        		$scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Time Rule'}, 'buttonsAction');	 
        	}else if(type == 'customerChange'){	        		
        		$scope.companyList = response.data; 
        		
        		if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
        			$scope.timeRule.companyId = response.data[0].id;
     	            $scope.companyChange();
     	        }
        	}else if (type == 'companyChange') {	
        		//alert(angular.toJson(response.data));
        		$scope.countriesList = response.data.countriesList;            
                $scope.timeRule.countryId = response.data.countriesList[0].id;
                
                $scope.list = response.data.profileList[0];
                $scope.timeRule.weekStartDay  = $scope.list.workWeekStartDay;
                $scope.timeRule.weekEndDay = $scope.list.workWeekEndDay;
                $scope.timeRule.hoursDay = $scope.list.businessHoursperDay;	                  
                $scope.timeRule.hoursWeek = $scope.list.standardHoursPerWeek;
            }else if(type == 'timeRules'){	            
            	if(response.data >0){
            		$scope.timeRule.timeRulesInfoId = response.data;
            		//$scope.getData('timeRulesController/getDetailsByRuleInfoId.json', {timeRulesInfoId:$scope.timeRule.timeRulesInfoId}, 'getDetailsById');
            		$scope.Messager('success', 'success', 'Time Rules Saved Successfully',buttonDisable);
            		$location.path('/timeRules/'+response.data);
            	}else{
            		$scope.Messager('error', 'Error', 'Technical Issue.. Please Try After Some Time',buttonDisable);
            	}
            }else if(type=='getTransactionDates'){
            	$scope.transactionDatesList = response.data;
            	$scope.timeRulesInfoId = response.data[0].id;
            	$scope.transactionModel= $scope.timeRule.timeRulesInfoId;
            	$scope.getData('timeRulesController/getDetailsByRuleInfoId.json', {timeRulesInfoId:$scope.timeRule.timeRulesInfoId}, 'getDetailsById');
            }else if(type=='validateCode'){
            	$scope.ruleCode = response.data;
            	if($scope.ruleCode > 0){
            		$scope.Messager('error', 'Error', 'Time Rule Code  already exists. ',buttonDisable);
            	}
            }
        },function () {
        	  $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
         });
	};      
    
	$scope.getData('timeRulesController/getDetailsByRuleInfoId.json', {customerId :$cookieStore.get('customerId'), timeRulesInfoId: $routeParams.id}, 'getDetailsById') 
     
	$scope.customerChange = function () { 
		$scope.companyList ="";
		$scope.countriesList = ""; 
		$scope.vendorList = ""; 
		$scope.locationList = "";
		if($scope.timeRule.customerId != undefined && $scope.timeRule.customerId != ""){
			$scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.timeRule.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.timeRule.companyId != undefined ? $scope.timeRule.companyId : 0}, 'customerChange');
		}
	 };
	    
    $scope.companyChange = function () {
    	$scope.countriesList = ""; 
        $scope.vendorList = ""; 
        $scope.locationList = "";
    	if($scope.timeRule.companyId != undefined && $scope.timeRule.companyId != ""){
    		$scope.getData('timeRulesController/getCountryandShiftDetails.json', { customerId: $scope.timeRule.customerId,companyId: $scope.timeRule.companyId }, 'companyChange');
    	}
    };
	    
    $scope.validateCode = function(){
    	if($scope.timeRule.customerId != undefined && $scope.timeRule.customerId != null && $scope.timeRule.customerId != "" && 
    			$scope.timeRule.companyId != undefined && $scope.timeRule.companyId != null && $scope.timeRule.companyId != "" &&
    			$scope.timeRule.timeRuleCode != undefined && $scope.timeRule.timeRuleCode != null && $scope.timeRule.timeRuleCode != ""){
	        $scope.getData('timeRulesController/validateTimeRuleCode.json', { customerId: $scope.timeRule.customerId,companyId: $scope.timeRule.companyId,timeRuleCode:$scope.timeRule.timeRuleCode }, 'validateCode');
    	}
    }
	    
    $scope.saveTimeRuleDetails =function($event){	
    	
    	 if($('#timeForm').valid() ){    		
    		// alert(angular.toJson($scope.timeRule));
    		 if($scope.ruleCode > 0){
    			 $scope.Messager('error', 'Error', 'Time Rule Code already exists.');
    		 }else if($scope.timeRule.workDayStatusList.length >0){
    			 if($scope.timeRule.bufferTimeLsit.length >0){
		    		 $scope.timeRule.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
		    		 $scope.timeRule.createdBy = $cookieStore.get('createdBy'); 
		 	    	 $scope.timeRule.modifiedBy = $cookieStore.get('modifiedBy');
		 	    	 if($scope.saveBtn){
		 	    		$scope.timeRule.timeRulesInfoId=0;
		 	    	 }
		 	    	 $scope.getData('timeRulesController/saveOrUpdateTimeRules.json', angular.toJson($scope.timeRule), 'timeRules',angular.element($event.currentTarget)); 
    			 }else{
		    		 $scope.Messager('error', 'Error', 'Please define atleast one buffer time',angular.element($event.currentTarget));
		    	 }
    		 }else{
    			 $scope.Messager('error', 'Error', 'Please define atleast one penalization rule ',angular.element($event.currentTarget));
    		 }
    	 }
    
    };
	    
   /* $scope.CodeChange = function(){
    	
    	if($scope.timeRulesInfoId != undefined && $scope.timeRulesInfoId != ""){
    	 $scope.getData('timeRulesController/getDetailsByRuleInfoId.json', {timeRulesInfoId:$scope.timeRulesInfoId}, 'getDetailsById');
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
    	}else{
    		$scope.timeRule.timeRuleCode="";
    		$scope.timeRule = new Object();
    		
    	}
    }*/
    
    $scope.transactionDatesListChange = function(){    
        $scope.getData('timeRulesController/getDetailsByRuleInfoId.json', {timeRulesInfoId:$scope.transactionModel}, 'getDetailsById')
        $('.dropdown-toggle').removeClass('disabled');
    };
    
    $scope.workDay1 = new Object();
    $scope.saveWorkDayStatus = function($event){
    	if($('#penalizationForm').valid()){
    		angular.forEach($scope.timeRule.workDayStatusList, function(value, key){	
	  		      if(value.fromHours == $scope.workDayStatusVar.fromHours && value.toHours == $scope.workDayStatusVar.toHours
	  		    		  && value.statusCode == $scope.workDayStatusVar.statusCode && value.statusDescription == $scope.workDayStatusVar.statusDescription	){
	  		    	 // $scope.Messager('error', 'Error', 'This Exception code already exists.',true); 
	  		    	  status = true;			    		
	  		      }
	  	       });
    		if(status){
    			$scope.Messager('error', 'Error', 'Data already exists.',true);
    		}else{
		    	$scope.timeRule.workDayStatusList.push({	    		   		
		    		'fromHours':$scope.workDayStatusVar.fromHours,
		    		'toHours':$scope.workDayStatusVar.toHours,	    		
					'statusDescription':$scope.workDayStatusVar.statusDescription,
					'statusCode':$scope.workDayStatusVar.statusCode				 	   		
		    	}); 
		    	
		    	 $('div[id^="penalization"]').modal('hide');
		    	// $scope.clearWorkDayStatusPopupValues();
    		}
    	}
    	
    };
    
    
    $scope.EditWorkDayStatus = function($event){	
    	$scope.trIndex = $($event.target).parent().parent().index();
    	var data = $scope.timeRule.workDayStatusList[$($event.target).parent().parent().index()];  
    	$scope.workDayStatusVar = data;
    	$scope.workDay1.fromHours = data.fromHours;
    	$scope.workDay1.toHours = data.toHours;
    	$scope.workDay1.statusDescription = data.statusDescription;
    	$scope.workDay1.statusCode = data.statusCode;
    	
    	$scope.popUpSave = false;
    	$scope.popUpUpdate =true;
    	
    };
    
    $scope.updateWorkDayStatus= function($event){
    	if($('#penalizationForm').valid()){	
    		$scope.timeRule.workDayStatusList.splice($scope.trIndex ,1);    		
    		$scope.timeRule.workDayStatusList.push({	    		   		
	    		'fromHours':$scope.workDayStatusVar.fromHours,
	    		'toHours':$scope.workDayStatusVar.toHours,	    		
				'statusDescription':$scope.workDayStatusVar.statusDescription,
				'statusCode':$scope.workDayStatusVar.statusCode				 	   		
	    	}); 
	    	
	    	$('div[id^="penalization"]').modal('hide');
	    	//$scope.clearWorkDayStatusPopupValues();
	    }  	
    };
    
    $scope.DeleteWorkDayStatus = function($event){	    	   	
    	var del = confirm("Are you sure you want to delete? ");
    	if(del){
    		$scope.timeRule.workDayStatusList.splice($($event.target).parent().parent().index(),1);
    	}
    };
    
    $scope.closeWorkDayStatus = function($event){    
    	if($scope.popUpUpdate){
    		$scope.workDayStatusVar.fromHours = $scope.workDay1.fromHours;
    		$scope.workDayStatusVar.toHours = $scope.workDay1.toHours;
    		$scope.workDayStatusVar.statusCode = $scope.workDay1.statusCode;
    		$scope.workDayStatusVar.statusDescription = $scope.workDay1.statusDescription;
    	}else if($scope.popUpSave){
    		$scope.clearWorkDayStatusPopupValues();
    	}
    	$('div[id^="penalization"]').modal('hide');
	};
    
    $scope.clearWorkDayStatusPopupValues = function(){	        		   		
    	$scope.workDayStatusVar.fromHours= "";
    	$scope.workDayStatusVar.toHours= "";  		
		$scope.workDayStatusVar.statusDescription= "";
		$scope.workDayStatusVar.statusCode	= "";			   		
	};
	    
    $scope.plusIconAction = function(){
    	$scope.popUpSave = true;
    	$scope.popUpUpdate =false;	 
    	$scope.workDayStatusVar= new Object();
    	$scope.disableTimeEvent= false;
    	//$scope.buferTimeVar = new Object();
    };
    
    $scope.bufferTimePlusIconAction = function(){
    	$scope.popUpSave1 = true;
    	$scope.popUpUpdate1 =false;	 
    	$scope.disableTimeEvent= false;
    	$scope.buferTimeVar = new Object();
    };
    
 
    $scope.saveBufferTime = function($event){
    	if($('#bufferTimeForm').valid()){
    		angular.forEach($scope.timeRule.bufferTimeLsit, function(value, key){	
  		      if(value.timeEvent == $scope.buferTimeVar.timeEvent 	){
  		    	  status = true;			    		
  		      }
    		});
    		
    		if(status){
    			$scope.Messager('error', 'Error', 'Data already exists for the selected time event.',true);
    		}else{
		    	$scope.timeRule.bufferTimeLsit.push({	    		   		
		    		'timeEvent':$scope.buferTimeVar.timeEvent,
		    		'bufferTime':$scope.buferTimeVar.bufferTime,
		    		'exemptedOccurrence':$scope.buferTimeVar.exemptedOccurrence,
		    		'greaterExemptedOccurrence':$scope.buferTimeVar.greaterExemptedOccurrence,
					'fromHours' : $scope.buferTimeVar.fromHours,
					'toHours' : $scope.buferTimeVar.toHours,
					'penality' : $scope.buferTimeVar.penality,
		    	}); 
		    	$('div[id^="bufferTime"]').modal('hide');
		    	//$scope.clearBufferTime();
    		}
    	}
    };  
    
    $scope.buffer1 = new Object();
    $scope.EditBufferTime = function($event){	
    	$scope.disableTimeEvent = true;
    	$scope.trBuffetTimeIndex = $($event.target).parent().parent().index();
    	var data = $scope.timeRule.bufferTimeLsit[$($event.target).parent().parent().index()];   
    	$scope.buferTimeVar = data;
    	$scope.buffer1.fromHours = data.fromHours;
    	$scope.buffer1.toHours = data.toHours;
    	$scope.buffer1.penality = data.penality;
    	$scope.buffer1.exemptedOccurrence = data.exemptedOccurrence;
    	$scope.buffer1.greaterExemptedOccurrence = data.greaterExemptedOccurrence;
    	$scope.buffer1.bufferTime = data.bufferTime;
    	$scope.buffer1.timeEvent = data.timeEvent;
    	$scope.popUpSave1 = false;
    	$scope.popUpUpdate1 =true;
    	
    };    
    
    $scope.updateBufferTime= function($event){
    	if($('#bufferTimeForm').valid()){	
    		$scope.timeRule.bufferTimeLsit.splice($scope.trBuffetTimeIndex ,1);    	
    		/*if($scope.buferTimeVar.fromHours > $scope.buferTimeVar.toHours){
    			$scope.Messager('error', 'Error', 'From Hours should be greater than To Hours.',true);
    			return;
    		}*/
    		$scope.timeRule.bufferTimeLsit.push({	    		   		
	    		'timeEvent':$scope.buferTimeVar.timeEvent,
	    		'bufferTime':$scope.buferTimeVar.bufferTime,
	    		'exemptedOccurrence':$scope.buferTimeVar.exemptedOccurrence,
	    		'greaterExemptedOccurrence':$scope.buferTimeVar.greaterExemptedOccurrence,
				'fromHours' : $scope.buferTimeVar.fromHours,
				'toHours' : $scope.buferTimeVar.toHours,
				'penality' : $scope.buferTimeVar.penality,			 	   		
	    	}); 
    		//alert(angular.toJson($scope.timeRule.bufferTimeLsit));
	    	$('div[id^="bufferTime"]').modal('hide');
	    	//$scope.clearBufferTime();
	    }		  	
    };
    
    $scope.DeleteBufferTime = function($event){    
    	var del = confirm("Are you sure you want to delete? ");
	    	if(del){
	    		$scope.timeRule.bufferTimeLsit.splice($($event.target).parent().parent().index(),1);
	    	}
	};
  
    $scope.closeBuffer = function($event){    
    	if($scope.popUpUpdate1){
    		$scope.buferTimeVar.exemptedOccurrence = $scope.buffer1.exemptedOccurrence;
    		$scope.buferTimeVar.greaterExemptedOccurrence = $scope.buffer1.greaterExemptedOccurrence;
    		$scope.buferTimeVar.fromHours = $scope.buffer1.fromHours;
    		$scope.buferTimeVar.toHours = $scope.buffer1.toHours;
    		$scope.buferTimeVar.penality = $scope.buffer1.penality;
    		$scope.buferTimeVar.bufferTime = $scope.buffer1.bufferTime;
    	}else if($scope.popUpSave1){
    		$scope.clearBufferTime();
    	}
    	$('div[id^="bufferTime"]').modal('hide');
	};
  
	$scope.clearBufferTime = function(){	        		   		
	    $scope.buferTimeVar.timeEvent ="";
	    $scope.buferTimeVar.bufferTime	= null;	
	    $scope.buferTimeVar.exemptedOccurrence = null;
	    $scope.buferTimeVar.greaterExemptedOccurrence = null;
	    $scope.buferTimeVar.fromHours = null;
	    $scope.buferTimeVar.toHours =  null;
	    $scope.buferTimeVar.penality = "";
    };  
    
    
    $scope.exceptionRules =function(){
    	if($scope.timeRule.timeRulesInfoId == 0){
       	  $scope.Messager('error', 'Error', 'Enter time rules, Then only you are allowed to enter exception rules.');
    	}else{
    		//alert($scope.timeRule.timeRulesId);
    		myservice.timeRuleCustomerId = $scope.timeRule.customerId;
    		myservice.timeRuleCompanyId = $scope.timeRule.companyId;
    		myservice.timeRuleCountryId = $scope.timeRule.countryId;
    		myservice.timeRuleId = $scope.timeRule.timeRulesId;
    		myservice.timeRulesInfoId = $scope.timeRule.timeRulesInfoId;
    		myservice.timeRuleCustomerName = $('#customerId option:selected').html();
    		myservice.timeRuleCompanyName = $('#companyId option:selected').html();
    		myservice.timeRuleCountryName = $('#countryId option:selected').html();
    		myservice.timeRuleCode = $scope.timeRule.timeRuleCode;
    		
    		$cookieStore.put("timeRuleCustomerId", myservice.timeRuleCustomerId);
    		$cookieStore.put("timeRuleCompanyId",myservice.timeRuleCompanyId);
    		$cookieStore.put("timeRuleCountryId",myservice.timeRuleCountryId);
    		$cookieStore.put("timeRuleId",myservice.timeRuleId);
    		$cookieStore.put("timeRulesInfoId", myservice.timeRulesInfoId);
    		$cookieStore.put("timeRuleCustomerName",myservice.timeRuleCustomerName);
    		$cookieStore.put("timeRuleCompanyName",myservice.timeRuleCompanyName);
    		$cookieStore.put("timeRuleCountryName",myservice.timeRuleCountryName);
    		$cookieStore.put("timeRuleCode",myservice.timeRuleCode);
    	
    		$location.path('/exceptionRules/'+$scope.timeRule.timeRulesInfoId);
    	}
    	  
    };
	    
     
}]);


