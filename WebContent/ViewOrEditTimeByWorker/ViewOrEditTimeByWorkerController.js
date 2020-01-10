'use strict';

workerVerification.directive('onLastRepeatNew', function () {
    return function (scope, element, attrs) {
        if (scope.$last) {
            setTimeout(function () {
            	 $('#workerTimeEdit tbody tr td').find('.businessDate').bootstrapMaterialDatePicker({
 			        time : false,
 			        Button : true,
 			        format : "DD/MM/YYYY",
 			        clearButton: true
 			    });
            	 
            	$('.WorkerTimeChnageinTime').timeEntry({show24Hours: true, showSeconds: false, beforeSetTime: null});
 			 	$('.WorkerTimeChnageoutTime').timeEntry({show24Hours: true, showSeconds: false,beforeSetTime: null}); 
 				$('.workerManHOursTimeEdit').timeEntry({show24Hours: true, showSeconds: false});
 			 	
            }, 1);
        }
    };
})

workerVerification.controller("ViewOrEditTimeByWorkerController", ['$window','$scope', '$http', '$resource','$location','$filter', '$routeParams','myservice','$cookieStore', function ($window,$scope, $http, $resource, $location, $filter, $routeParams, myservice, $cookieStore) {
	 $.material.init();
	 
	
	 
     
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
     
     
     
     $scope.status = [
	                     {"id":"Present","name" : "Present" },
	                     {"id":"Absent","name" : "Absent" },
	                     {"id":"WO","name" : "Wekly Off" },	                    
	                     {"id":"Half Day","name" : "Half Day" },
	                     {"id":"PH","name" : "Public Holiday" }
	                     ];
     
     
     $scope.workerVo = $cookieStore.get('workerVo');
     $scope.workerVo.labourTimeDetails = [];
     $scope.labourTimeList = [];
     
    
    // alert(angular.toJson( $scope.workerVo));
    
   // alert($scope.workerVo.workerCode);

      
      
       
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
        	   if(type == 'search'){
        		   
        		   if(response.data.laborTimeList != undefined && response.data.laborTimeList.length >0){
        			   
        		   $scope.labourTimeList =  response.data.laborTimeList;
        		   
        		   $scope.shiftCodesList = response.data.shiftsDefineVos;        		   
        			   $scope.designation = (response.data.laborTimeList[0].designation != null && response.data.laborTimeList[0].designation != "null") ? response.data.laborTimeList[0].designation : "";
        			   $scope.employeeCode = response.data.laborTimeList[0].employeeCode;
        			   $scope.empName = response.data.laborTimeList[0].empName;        			   
        			   $scope.availableShifts = response.data.availableShifts;
        			   $scope.shiftCodesList.push({"shiftCode":"AB"       				   
        			   },{"shiftCode":"PH"       				   
        			   });        			   
        			   $("#loader").hide();
        		       $("#workerTimeEdit").show();
        		  
        	   }else{
        		   $("#loader").hide();
   		           $("#workerTimeEdit").hide();
        		   $scope.Messager('error', 'Error', 'Details Not Available',buttonDisable);
        	   }
        	   }else if(type== 'updateWorkerTimeDetails'){
        		 //  alert(angular.toJson(response.data)+":update");
        		   if(response.data == 1){
        			   $scope.Messager('error', 'Error', 'Error In Updating the Details, Please Check the Details',buttonDisable);
        		   }else{
        			   $scope.Messager('success', 'success', 'Details Updated Successfully',buttonDisable);
        		   }
        		   $("#loader").hide();
   		        	$("#workerTimeEdit").show();
        		   
        	   }else if( type ==  'schemaName'){   
        		   if(response.data.name != undefined && response.data.name != null && response.data.name != ''){
        			   $scope.workerVo.schemaName = response.data.name;   
        		   }else{
        			   $scope.workerVo.schemaName = 'null';
        		   }
        		   $scope.getData('workerController/viewWorkerTimeDetailsForEditing.json', angular.toJson($scope.workerVo), 'search');
               }else if(type == 'locationDrop'){       
            	//   alert(angular.toJson(response.data));
                   $scope.list_locations = response.data;                 
               }else if(type == 'plantsList'){
               	$scope.plantsList = response.data;
               }else if(type == 'departmentsList'){
               	$scope.departmentsList = response.data.departmentList;
               }
           },
           function () { 
        	   $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
           });
       }
       
       $("#loader").show();
       $("#workerTimeEdit").hide(); 
       $scope.getData("reportController/getSchemaNameByUserId.json",{userId : $cookieStore.get('userId') } ,"schemaName" );
      
	   
       
       $scope.getData('LocationController/getLocationsListBySearch.json', {customerId:$scope.workerVo.customerId,companyId:$scope.workerVo.companyId}, 'locationDrop');
       
       $scope.locationChange = function(){
   	   	if($scope.workerVo.locationId != undefined && $scope.workerVo.locationId != null && $scope.workerVo.locationId != ''){
   	   		$scope.departmentsList = []; 	   		
   	   		$scope.getData('jobAllocationByVendorController/getPlantsList.json', {locationId:$scope.workerVo.locationId}, 'plantsList');
   	   	}else{
   	   		$scope.plantsList =[];
   	   		$scope.departmentsList = [];   	   		
   	   	}
   	   }
   	   
   	   $scope.plantChange = function(){
   	   	if($scope.workerVo.locationId != undefined && $scope.workerVo.locationId != null && $scope.workerVo.locationId != '' && $scope.workerVo.plantId != undefined && $scope.workerVo.plantId != null && $scope.workerVo.plantId != ''){
   	   		$scope.getData('reportController/getDepartmentsByLocationAndPlantId.json', {customerId:$scope.workerVo.customerId,companyId:$scope.workerVo.companyId, locationId:$scope.workerVo.locationId,plantId:$scope.workerVo.plantId}, 'departmentsList');
   	   	//	$scope.getPostData('associatingDepartmentToLocationPlantController/getDepartMentDetailsByLocationAndPlantId.json', {customerId:$cookieStore.get('customerId'), companyId: $cookieStore.get('companyId'), locationId:$scope.locationId,plantId:$scope.plantId}, 'departmentsList');
   	   	}else{
   	   		$scope.departmentsList = [];   	   		
   	   	}
   	   }
   	  
   	  
   	   
       
       
       
       
       
       

       
       $scope.updateWorkerTimeDetails = function($event){    	
    	   	       if($('#workerEditForm').valid()){
    	   $scope.workerVo.labourTimeDetails = $scope.labourTimeList;
    	   
    	 //  alert(angular.toJson($scope.labourTimeList));
    	   var saveFlag = true;
    	   angular.forEach($scope.labourTimeList, function(value, key) {    		  
    		   var hours = value.manHours.split(":")[0];    
    		   
    		   if(value.shiftId == '' || value.shiftId == 'null' || value.shiftId == undefined || value.shiftId == null){
    			   $scope.Messager('error', 'Error', 'Please select Shift for Bussiness Date : '+value.businessDate,angular.element($event.currentTarget));
    			   saveFlag = false;
    			   return;
    		   }
    		   
    		  
    		   if( value.shiftId != 'AB' && value.shiftId != 'WO' && value.shiftId != 'PH'   && (!(value.inTime != null && value.inTime.length >=5) || !(value.outTime != null && value.outTime.length >=5) ) ){
    			   $scope.Messager('error', 'Error', 'Time Format Should be - hh:mm , Example: 09:00 for Bussiness Date : '+value.businessDate,angular.element($event.currentTarget));
    			   saveFlag = false;
    			   return;
    		   }
    		   
    		   
    		  if(value.shiftId == 'G' && hours > 11){
    			  $scope.Messager('error', 'Error', 'For General Shift Man hours should not exceed 11 Hours, Please Check the Bussiness Date : '+value.businessDate,angular.element($event.currentTarget));
    			  saveFlag = false;
    			  $("#loader").hide();
    		        $("#workerTimeEdit").show();	
    			  return;
    		  }
    		  
    		  if(value.shiftId != 'G' && hours > 10){
    			  saveFlag = false;
    			  $("#loader").hide();
  		        $("#workerTimeEdit").show();
    			  $scope.Messager('error', 'Error', 'Except General Shift,All other shifts Man hours should not exceed 10 Hours, Please Check the Bussiness Date : '+value.businessDate,angular.element($event.currentTarget));
    			  return;
    		  }
    		  
    		 });
    	   
    	   if(saveFlag){
    		   $("#loader").show();
   	        $("#workerTimeEdit").hide();
   	        $scope.workerVo.createdBy = $cookieStore.get('createdBy'); 
		 	$scope.workerVo.modifiedBy = $cookieStore.get('modifiedBy');
		 	// alert(angular.toJson($scope.workerVo));
    	    $scope.getData('workerController/saveModifiedLaborTimeDetails.json', angular.toJson($scope.workerVo), 'updateWorkerTimeDetails',angular.element($event.currentTarget));
    	   }
    	   	       }
       };
       
       
       
       $scope.intimeChange = function($event,index) {
    	  
    	   if($scope.labourTimeList[index].shiftId == 'PH' || $scope.labourTimeList[index].shiftId == 'WO' || $scope.labourTimeList[index].shiftId == 'AB'){
    		   $scope.labourTimeList[index].inTime = '';
    	   }else{
    	   
    	   $scope.labourTimeList[index].inTime = $($event.target).val();   	 
    	   var inTime = $($event.target).parent().parent().find('td').eq(2).find('input').val();
    	   var outTime = $($event.target).parent().parent().find('td').eq(3).find('input').val();
    	   if(inTime != undefined && inTime!= '' && outTime != undefined && outTime!= '') {
    		  // alert(inTime.length+"::"+outTime.length);
    		   if(inTime.length != 5 || outTime.length != 5){
    			   $scope.Messager('error', 'Error', 'Time Format Should be - hh:mm , Example: 09:00',''); 
    				$scope.labourTimeList[index].manHours = '';
    		   }else{
    			   var inTimeHours =  inTime.split(":")[0];
    	     	   var inTimeMins = inTime.split(":")[1];
    	     	
    	     	   var outTimeHours =  outTime.split(":")[0];
    	     	   var outTimeMins = outTime.split(":")[1];
    	     	   var minsNegativeFlag = false;
    	     	
    	 	 		var mins = parseInt(outTimeMins) - parseInt(inTimeMins);	 		
     	 			if(parseInt(mins) < 0){
     	 				mins = 60 - ( - parseInt(mins));
     	 				minsNegativeFlag = true;
     	 			}
    	 	 		var hours = parseInt(outTimeHours) - parseInt(inTimeHours);
    	 	 		if(parseInt(hours) < 0){ 	 		
    	 	 			hours = 24 -(- parseInt(hours)); 	 		
    	 	 		}
    	 	 		if(minsNegativeFlag){
    	 	 			hours = hours -1;
    	 	 		}
    	 	 		var totalMinutes = parseInt(hours)*60 + parseInt(mins);
    	 	 		hours = Math.floor(parseInt(totalMinutes)/60);
    	 	 		mins = parseInt(totalMinutes)% 60;
    	 	 		var strHours, strMins;
    	 	 		strHours = ''+hours;
    	 	 		strMins = ''+mins;
    	     	  if(hours < 10)
    	     		  strHours = '0'+hours;
    	     	  if(mins < 10)
    	     		  strMins = '0'+mins;
    	 	 		// $scope.labourTimeList[index].manHours = strHours+':'+strMins;
        	   
    	 		
    	 		// OT Calculation 
    	 	//	$scope.calculateOTHours($event,index);
    		   }
    		   
	    	   
	 		 
    	   } else {
   		   	$scope.labourTimeList[index].manHours = '';
   	   }
    	   }
	 		
    	    	  	    	     	    	
       };
       
       
       $scope.outTimeChange = function($event,index) {
    	   if($scope.labourTimeList[index].shiftId == 'PH' || $scope.labourTimeList[index].shiftId == 'WO' || $scope.labourTimeList[index].shiftId == 'AB'){
    		   $scope.labourTimeList[index].inTime = '';
    	   }else{
    	   
    	   $scope.labourTimeList[index].outTime = $($event.target).val();
    	   var inTime = $($event.target).parent().parent().find('td').eq(2).find('input').val();
    	   var outTime = $($event.target).parent().parent().find('td').eq(3).find('input').val();
    	   if(inTime != undefined && inTime!= '' && outTime != undefined && outTime!= '') {
    		   
    		   
    		   if(inTime.length != 5 || outTime.length != 5){
    			   $scope.Messager('error', 'Error', 'Time Format Should be -  hh:mm , Example : 09:00',''); 
    				$scope.labourTimeList[index].manHours = '';
    		   }else{
	    	   var inTimeHours =  inTime.split(":")[0];
	     	   var inTimeMins = inTime.split(":")[1];
	     	
	     	   var outTimeHours =  outTime.split(":")[0];
	     	   var outTimeMins = outTime.split(":")[1];
	     	  var minsNegativeFlag = false;
	     	
	 	 		var mins = parseInt(outTimeMins) - parseInt(inTimeMins);	 		
	 	 			if(parseInt(mins) < 0){
	 	 				mins = 60 - ( - parseInt(mins));
	 	 				minsNegativeFlag = true;
	 	 			}
	 	 			
	 	 		
	 	 		var hours = parseInt(outTimeHours) - parseInt(inTimeHours);
	 	 			
	 	 		if(parseInt(hours) < 0){ 	 		
	 	 			hours = 24 -(- parseInt(hours)); 	 		
	 	 		}
	 	 		if(minsNegativeFlag){
	 	 			hours = hours -1;
	 	 		}
		 		var totalMinutes = parseInt(hours)*60 + parseInt(mins);
		 	
		 		hours = Math.floor(parseInt(totalMinutes)/60);
		 		mins = parseInt(totalMinutes)% 60
	    	  
	 	 		var strHours, strMins;
	 	 		strHours = ''+hours;
	 	 		strMins = ''+mins;
	     	  if(hours < 10)
	     		  strHours = '0'+hours;
	     	  if(mins < 10)
	     		  strMins = '0'+mins;
	 	 		// $scope.labourTimeList[index].manHours = strHours+':'+strMins;
	 		
	 		// OT Calculation 
	 		
	 	//	$scope.calculateOTHours($event,index);
    	 }
    	   }else {
   		   		$scope.labourTimeList[index].manHours = '';
   	  		} 
	 		
    	   }
       };
       
       
    
       $scope.calculateOTHours = function ($event,index){ 
    	   
    	   angular.forEach($scope.availableShifts, function(value, key) {     		 				
	 				var actaulEndHours  = value.endTime.split(":")[0]; 			
	 				
						if ($scope.labourTimeList[index].outTime != '') {
						var punchOutHours = $scope.labourTimeList[index].outTime.split(":")[0];						
						var hours = $scope.labourTimeList[index].manHours.split(":")[0].substr(0,2);	
						var punchOutMins = $scope.labourTimeList[index].manHours.split(":")[1].substr(0,2);							
						
						//alert(hours+"::hours");
						// Status Modification Based on Hours
				 		 
				 		if(parseInt(hours) <= 4){
							 $scope.labourTimeList[index].status = 'Absent';				
						 }else if(parseInt(hours) > 4 && parseInt(hours) <=6 && parseInt(punchOutMins) <= 30){
							 $scope.labourTimeList[index].status = 'Half Day';				
						 }else if(((parseInt(hours) == 6) && parseInt(mins) >= 30) || (parseInt(hours) >= 7)){
							 $scope.labourTimeList[index].status = 'Present';				 
						 }
						
						if ((parseInt(punchOutHours) > parseInt(actaulEndHours)) ) {								
							//alert(punchOutHours);
								//alert(hours);
								if($scope.labourTimeList[index].shiftId != 'G' && hours >8){
									$scope.labourTimeList[index].otHours = parseInt(hours) - 8 ;
									//alert($scope.labourTimeList[index].otHours+"::ot");
									if(parseInt(punchOutMins) <= 29 ){											
										$scope.labourTimeList[index].otHours = $scope.labourTimeList[index].otHours+'.'+0;
									}else if(parseInt(punchOutMins) >= 30 && parseInt(punchOutMins) <= 59){
										$scope.labourTimeList[index].otHours = $scope.labourTimeList[index].otHours+'.'+5;											
									}else if(parseInt(punchOutMins) >= 60 && parseInt(punchOutMins) <= 89){
										$scope.labourTimeList[index].otHours = parseInt($scope.labourTimeList[index].otHours)+1;											
									}
								}else if($scope.labourTimeList[index].shiftId == 'G' && hours >9){
									$scope.labourTimeList[index].otHours = parseInt(hours) - 9 ;
									if(parseInt(punchOutMins) <= 29 ){
										$scope.labourTimeList[index].otHours = $scope.labourTimeList[index].otHours+'.'+0;
									}else if(parseInt(punchOutMins) >= 30 && parseInt(punchOutMins) <= 59){
										$scope.labourTimeList[index].otHours = $scope.labourTimeList[index].otHours+'.'+5;
									}else if(parseInt(punchOutMins) >= 60 && parseInt(punchOutMins) <= 89){
										$scope.labourTimeList[index].otHours = parseInt($scope.labourTimeList[index].otHours)+1;
									}
								}else{
									$scope.labourTimeList[index].otHours = '0.0';
								}
							
							
							
						} else {
							$scope.labourTimeList[index].otHours = '0.0';
							//alert($scope.labourTimeList[index].otHours+"::if else");
						}
						
						} else {
							$scope.labourTimeList[index].otHours = '0.0'
							//	alert($scope.labourTimeList[index].otHours+"::if out");
						}
	 			//}
	 		 });
       }
       
       $scope.manHoursChange = function($event,index){
    	   $scope.labourTimeList[index].manHours = $($event.target).val();
    	   var manHours = $scope.labourTimeList[index].manHours;
    	   var hours1 = manHours.split(":")[0];
    	   var mins1 =  manHours.split(":")[1]; 
    	  // alert(parseInt(hours1));
    	   if(parseInt(hours1) <= 4){
				 $scope.labourTimeList[index].status = 'Absent';				
			 }else if(parseInt(hours1) > 4 && parseInt(hours1) <=6 && parseInt(mins1) <= 30){
				 $scope.labourTimeList[index].status = 'Half Day';				
			 }else if(((parseInt(hours1) == 6) && parseInt(mins1) >= 30) || (parseInt(hours1) >= 7)){
				 $scope.labourTimeList[index].status = 'Present';				
			 }
    	  
       }
       
       $scope.shiftChnage = function($event,index){
    	   $scope.labourTimeList[index].status =  ($scope.labourTimeList[index].shiftId == 'PH') ? $scope.labourTimeList[index].status = 'PH' : ($scope.labourTimeList[index].shiftId == 'WO') ? 'WO' : ($scope.labourTimeList[index].shiftId == 'AB') ? 'Absent' : '';
    	   if($scope.labourTimeList[index].shiftId == 'PH' || $scope.labourTimeList[index].shiftId == 'AB' || $scope.labourTimeList[index].shiftId == 'WO') {
    		   $scope.labourTimeList[index].manHours= '';
    		   $scope.labourTimeList[index].outTime = '';
    		   $scope.labourTimeList[index].inTime ='';
    		   $scope.labourTimeList[index].otHours ='';
    	   } else {
    		   $scope.calculateOTHours($event,index);
    	   }
    	   
       }
       
     
    
}]);





