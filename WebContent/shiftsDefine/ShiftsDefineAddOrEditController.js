'use strict';


/*
ShiftDefineSearchControllers.directive('onLastRepeat', function () {
    return function (scope, element, attrs) {
        if (scope.$last) {
            setTimeout(function () {
                $('.table').DataTable();
            }, 1);
        }
    };
})*/

ShiftDefineSearchControllers.controller("ShiftDefineAddOrEditCtrl",  ['$scope', '$rootScope', '$http', '$filter', '$resource','$location','$routeParams','$cookieStore', 'myservice', function($scope,$rootScope, $http,$filter,$resource,$location,$routeParams,$cookieStore,myservice) {
	$.material.init();
	$scope.unpaidBreak = 0;
	/*$('#transactionDate').bootstrapMaterialDatePicker({ 
    	time : false,
        Button : true,
        format : "DD/MM/YYYY",
        clearButton: true
    }); */	
	$('#transactionDate').datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown'
	    	  
	});
	
	$scope.shiftsDefine = new Object();
	
	/*if($scope.timeFormat == "1"){
		$('#startTime,#endTime').bootstrapMaterialDatePicker({ 
	        format : 'HH:mm A',date: false, clearButton: true ,shortTime:true
	    }); 
	}else if($scope.timeFormat == "2"){
		$('#startTime,#endTime').bootstrapMaterialDatePicker({ 
	        format : 'HH:mm',date: false, clearButton: true 
	    }); 
	}*/
	/*$('#startTime').timeEntry({show24Hours: true, showSeconds: false});
	$('#endTime').timeEntry({show24Hours: true, showSeconds: false});*/
	
	$scope.list_time_formats = [{"id":"1","name" : "12 Hours" },
	                              {"id":"2","name" : "24 Hours" }];
	
	
	$scope.list_status = [{ id:"Y" , name:"Active"},{ id:"N" , name:"In Active"}];	
	$scope.san = new Date();
	
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
    
    if(($scope.shiftsDefine.companyDetailsId == null || $scope.shiftsDefine.companyDetailsId == undefined || $scope.shiftsDefine.companyDetailsId == "")){
    	$scope.timeDisable = true;
    }else{
    	$scope.timeDisable = false;
    }
    
	if($routeParams.id > 0){
		$scope.readonly = true;
		$scope.saveBtn = false;
		$scope.updateBtn = true;
		$scope.correctHistoryBtn = false;
		$scope.resetBtn = false;
		$scope.viewHistoryBtn = true;
		$scope.cancelBtn = false;
		$scope.returnTOSearchBtn = true;
		$scope.transactionList = false;
		
	//	alert($scope.addTime);
	}else{
		$scope.shiftsDefine = new Object();
		$scope.transactionDate = $filter('date')(new Date(),"dd/MM/yyyy");
		$scope.shiftsDefine.isActive = 'Y';
		$scope.readonly = false;
		$scope.saveBtn = true;
		$scope.updateBtn = false;
		$scope.correctHistoryBtn = false;
		$scope.resetBtn = true;
		$scope.viewHistoryBtn = false;
		$scope.cancelBtn = false;
		$scope.returnTOSearchBtn = true;
		$scope.transactionList = false;
		
	}
	
	$scope.fun_updateActionBtn = function(){
		$scope.timeDisable = false;
		//alert($scope.timeFormat)
		if($scope.timeFormat == "1"){
 			$('#startTime,#endTime').bootstrapMaterialDatePicker({ 
 		        format : 'hh:mm A',date: false, clearButton: true ,shortTime:true
 		    }); 
 		}else if($scope.timeFormat == "2"){
 			$('#startTime,#endTime').bootstrapMaterialDatePicker({ 
 		        format : 'HH:mm ',date: false, clearButton: true 
 		    }); 
 		}
		$scope.readonly = false;
		$scope.saveBtn = true;
		$scope.updateBtn = false;			
		$scope.viewHistoryBtn = false;
		$scope.cancelBtn = true;
		$scope.returnTOSearchBtn = false;	
		$scope.transactionDate = $filter('date')(new Date(),"dd/MM/yyyy");
		
		$('.dropdown-toggle').removeClass('disabled');
		
	}
		
	$scope.fun_shiftDefineCancelBtn = function(){
		$scope.readonly = true;
		$scope.saveBtn = false;
		$scope.updateBtn = true;		
		$scope.viewHistoryBtn = true;
		$scope.cancelBtn = false;
		$scope.returnTOSearchBtn = true;				
		$scope.getPostData("shiftsDefineController/getMasterDataForShifteView.json",  {shiftId : $scope.shiftsDefine.shiftDefineId} , "cancelBtnAction");
	}
	
	$scope.fun_viewHistoryBtn = function(){
		$scope.timeDisable = false;
		if($scope.timeFormat == "1"){
 			$('#startTime,#endTime').bootstrapMaterialDatePicker({ 
 		        format : 'hh:mm A',date: false, clearButton: true ,shortTime:true
 		    }); 
 		}else if($scope.timeFormat == "2"){
 			$('#startTime,#endTime').bootstrapMaterialDatePicker({ 
 		        format : 'HH:mm',date: false, clearButton: true 
 		    }); 
 		}
		$scope.readonly = false;
		$scope.saveBtn = false;
		$scope.updateBtn = false;
		$scope.correctHistoryBtn = true;
		$scope.resetBtn = false;
		$scope.viewHistoryBtn = false;
		$scope.cancelBtn = false;
		$scope.returnTOSearchBtn = true;
		$scope.transactionList = true;	
		$scope.getPostData('shiftsDefineController/getTransactionDatesForShiftsHistory.json', {  customerId : $scope.shiftsDefine.customerDetailsId , companyId : $scope.shiftsDefine.companyDetailsId,shiftCode:$scope.shiftsDefine.shiftCode }, 'viewHistoryBtn');
	    $('.dropdown-toggle').removeClass('disabled');
		
	}
	
	$scope.databinding = function(data){
		$scope.transactionDate = $filter('date')(data.transactionDate,"dd/MM/yyyy");		
		$scope.startTime = data.startTime;//new Date('2016-05-05 '+data.startTime+':00');
 		$scope.endTime =  data.endTime;//new Date('2016-05-05 '+data.endTime+':00');
 		$scope.unpaidBreak =  data.unpaidBreak;
 		$scope.totalHours =  data.totalHours;
 		$('#startTime').val(data.startTime);
	 	$('#endTime').val(data.endTime);
	 	$('#unpaidBreak').val(data.unpaidBreak);
	 	$('#totalHours').val(data.totalHours);
	 	
 		
 		$scope.offShift = data.offShift == 'Y' ? true : false ;
	 	$scope.halfDayShift = data.halfDayShift == 'Y' ? true : false ;
	 	$scope.shiftAllowance= data.shiftAllowance == 'Y' ? true : false ;
	 	$scope.flexibleShift = data.flexibleShift == 'Y' ? true : false ;
	 	
	 	$("#offShift").change();
	}
	
	 $scope.getPostData = function (url, data, type,buttonDisable) {
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
	        	} else if (type == 'masterData') { 	             	
	             	$scope.customerList = response.data.customerList; 
	             	if($routeParams.id > 0){
	             		//$scope.customerList = response.data.customerList;
	             		$scope.companyList = response.data.companyList;
	             		$scope.countryList = response.data.countryList;
	             		$scope.locationList = response.data.locationList;
	             		$scope.plantList = response.data.plantList;
	             		$scope.shiftsDefine   = response.data.shiftDefineVo[0];
	             		$scope.databinding(response.data.shiftDefineVo[0]);	
	             		$scope.shiftsVo = response.data.shiftsVo;	
	             		$scope.timeFormat = response.data.shiftsVo[0].timeSetFormat;
	             		if($scope.timeFormat == "1"){
	             			$('#startTime,#endTime').bootstrapMaterialDatePicker({ 
	             		        format : 'hh:mm A',date: false, clearButton: true ,shortTime:true
	             		    }); 
	             		}else if($scope.timeFormat == "2"){
	             			$('#startTime,#endTime').bootstrapMaterialDatePicker({ 
	             		        format : 'HH:mm',date: false, clearButton: true ,shortTime:false
	             		    }); 
	             		}
	             	}else{
	             		$scope.shiftsDefine.isNextDay = 'N';
	             		 if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
	    		                $scope.shiftsDefine.customerDetailsId=response.data.customerList[0].customerId;		                
	    		                $scope.fun_CustomerChangeListener();
	    		                }
	             	}
	             // for button views
				  	if($scope.buttonArray == undefined || $scope.buttonArray == '')
	             	$scope.getPostData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Define Shift'}, 'buttonsAction');
	             }else if (type == 'cancelBtnAction') {              	
	            	 $scope.customerList = response.data.customerList; 
		             	if($scope.shiftsDefine.shiftDefineId){		             		
		             		$scope.companyList = response.data.companyList;
		             		$scope.countryList = response.data.countryList;
		             		$scope.shiftsDefine   = response.data.shiftDefineVo[0];
		             		$scope.databinding(response.data.shiftDefineVo[0]);			        		 	
		             	}
	             }else if (type == 'customerChange') {              	
	             		$scope.companyList = response.data;
	             		 if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	        	                $scope.shiftsDefine.companyDetailsId = response.data[0].id;
	        	                $scope.fun_CompanyChangeListener();
	        	                }
	             		
	             }else if (type == 'companyChange') { 
	            	 //alert(angular.toJson(response.data.shiftsVo[0].timeSetFormat));
	             		$scope.countryList = response.data.countryList;
	             		$scope.locationList = response.data.locationList;	
	             		$scope.timeFormat = response.data.shiftsVo[0].timeSetFormat;
	             		$scope.timeDisable = false;
	             		if($scope.timeFormat == "1"){
	             			$('#startTime,#endTime').bootstrapMaterialDatePicker({ 
	             		        format : 'hh:mm A',date: false, clearButton: true ,shortTime:true
	             		    }); 
	             		}else if($scope.timeFormat == "2"){
	             			$('#startTime,#endTime').bootstrapMaterialDatePicker({ 
	             		        format : 'HH:mm ',date: false, clearButton: true ,shortTime:false
	             		    }); 
	             		}
	             		
	             		/*if($scope.timeFormat == "1"){
	             			$('#startTime').timeEntry({show12Hours: true,show24Hours: false, showSeconds: false});
	             			$('#endTime').timeEntry({show12Hours: true, showSeconds: false});
	             		}else if($scope.timeFormat == "2"){
	             			$('#startTime').timeEntry({show24Hours: true, showSeconds: false});
	             			$('#endTime').timeEntry({show24Hours: true, showSeconds: false});
	             		}*/
	             }else if (type == 'locationChange') {
	            	// alert(angular.toJson(response.data));
	             		$scope.plantList = response.data;
	             }else if (type == 'getGridData') {              	
	             		$scope.result = response.data;
	             }else if (type == 'saveShiftDefine') {              	
	            	 if($scope.saveBtn == true){		 		
	     		 		$scope.Messager('success', 'success', 'Shift Record Saved Successfully',buttonDisable);	
	     		 		$location.path('/shiftsDefineAddOrEdit/'+response.data);  	     		 		
	     		 	 }else{
	     		 		$scope.Messager('success', 'success', 'Shift Record Updated Successfully',buttonDisable);		 		
	     		 	 } 
	             }else if(  type == 'transactionDateChageListener'){
	            	 	$scope.companyList = response.data.companyList;
	             		$scope.countryList = response.data.countryList;
	             		$scope.shiftsDefine   = response.data.shiftDefineVo[0];
	             		$scope.databinding(response.data.shiftDefineVo[0]);	
	             }else if(type == 'viewHistoryBtn'){
	            	 $scope.transactionDatesList = response.data.transactionList;
	            	 	$scope.companyList = response.data.companyList;
	             		$scope.countryList = response.data.countryList;
	             		$scope.shiftsDefine   = response.data.shiftDefineVo[0];
	             		$scope.databinding(response.data.shiftDefineVo[0]);	
	             }
	         },
	         function () {
	        	 $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
	        	//alert('Error')         	
	        });
	     }
	     
	     // GET MASTER DATA FOR DETAILS SCREEN   
	 	$scope.getPostData("shiftsDefineController/getMasterDataForShifteView.json", { shiftId : $routeParams.id ,customerId: $cookieStore.get('customerId') }, "masterData");		    

	 	// Customer Change Listener to get Companies List
	 	$scope.fun_CustomerChangeListener = function(){	
	 		if($scope.shiftsDefine != undefined && $scope.shiftsDefine.customerDetailsId != undefined && $scope.shiftsDefine.customerDetailsId != '')
	 		$scope.getPostData("vendorController/getCompanyNamesAsDropDown.json", { customerId : $scope.shiftsDefine.customerDetailsId ,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.companyDetailsId !=undefined ? $scope.companyDetailsId : 0 }, "customerChange");	
	 	}
	 	
	 	$scope.fun_CompanyChangeListener = function(){	
	 		if($scope.shiftsDefine != undefined && $scope.shiftsDefine.companyDetailsId != undefined && $scope.shiftsDefine.companyDetailsId != '')
	 		$scope.getPostData("shiftsDefineController/getCountryNamesAsDropDown.json", { customerId : $scope.shiftsDefine.customerDetailsId , companyId : $scope.shiftsDefine.companyDetailsId }, "companyChange");	
	 	} 
	 	$scope.fun_locationChangeListener = function () {	
	 		/*alert($scope.shiftsDefine.locationDetailsId);
	 		alert($scope.shiftsDefine != undefined);
	 		alert($scope.shiftsDefine.locationDetailsId != undefined);
	 		alert($scope.shiftsDefine.locationDetailsId != '');*/
	 		if($scope.shiftsDefine != undefined && $scope.shiftsDefine.locationDetailsId != undefined && ($scope.shiftsDefine.locationDetailsId != '' || $scope.shiftsDefine.locationDetailsId >= 0))
	 			$scope.getPostData('shiftsDefineController/getPlantsList.json', { customerId : $scope.shiftsDefine.customerDetailsId  , companyId : $scope.shiftsDefine.companyDetailsId  , locationId: $scope.shiftsDefine.locationDetailsId , status: 'Y'}, 'locationChange');
		};
	 	
	 $scope.fun_save_shiftDefineData = function($event){		 
		 if($('#shiftDefineDetails').valid()){
			$scope.shiftsDefine.createdBy = $cookieStore.get('createdBy'); 
		 	$scope.shiftsDefine.modifiedBy = $cookieStore.get('modifiedBy');
			 	
		 	$scope.shiftsDefine.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
		 	$scope.shiftsDefine.offShift= $scope.offShift == true ? 'Y' :'N';
		 	$scope.shiftsDefine.halfDayShift=$scope.halfDayShift == true ? 'Y' :'N';
		 	$scope.shiftsDefine.shiftAllowance=$scope.shiftAllowance == true ? 'Y' :'N';
		 	$scope.shiftsDefine.flexibleShift =$scope.flexibleShift == true ? 'Y' :'N';	
		 	$scope.shiftsDefine.startTime =  $('#startTime').val();
		 	$scope.shiftsDefine.endTime =  $('#endTime').val();
		 	$scope.shiftsDefine.unpaidBreak = $('#unpaidBreak').val();	 
		 	$scope.shiftsDefine.totalHours = $('#totalHours').val();
		 	var shiftDefineId = 0;
		 	var savedata;
		 	
		 	if($scope.saveBtn == true){
		 		shiftDefineId = $scope.shiftsDefine.shiftDefineId;
		 		$scope.shiftsDefine.shiftDefineId = 0 ;
		 		savedata = angular.toJson($scope.shiftsDefine)
		 		$scope.shiftsDefine.shiftDefineId = shiftDefineId;
		 	}else{
		 		savedata = angular.toJson($scope.shiftsDefine) 
		 	}		
		 	
		 	$scope.shiftsDefine.totalHours = Math.abs($scope.shiftsDefine.startTime - $scope.shiftsDefine.endTime) / 36e5;		 	
		 //	alert(savedata);
			$scope.getPostData("shiftsDefineController/saveShiftDefineRecord.json", savedata , "saveShiftDefine",angular.element($event.currentTarget));	
						
		}
	 }
	 
	 
	 $scope.fun_transactionDatesListChnage = function(){
		 if($scope.shiftsDefine != undefined && $scope.shiftsDefine.shiftDefineId != undefined && $scope.shiftsDefine.shiftDefineId != '')
			 $scope.getPostData("shiftsDefineController/getShiftRecordByShiftId.json",{ shiftId : $scope.shiftsDefine.shiftDefineId }, "transactionDateChageListener");
	 }
	 
	 $('#startTime,#endTime').change(function() {
		 if($('#startTime').val() != null && $('#startTime').val() != undefined && $('#startTime').val() != ""
			 && $('#endTime').val() != null && $('#endTime').val() != undefined && $('#endTime').val() != ""){
			 if($scope.timeFormat == "1"){
				 var splitStart = $('#startTime').val().split(" ");
			 	 var splitEnd = $('#endTime').val().split(" ");
			 	
			 	 var startTimeHours = splitStart[0].split(":")[0];
			 	 var startTimemins = splitStart[0].split(":")[1];
			 	
			 	 var endDateTimeHours = splitEnd[0].split(":")[0];
		 		 var endDateTimeMins = splitEnd[0].split(":")[1];

		 		 if(splitStart[1] != undefined && splitStart[1] !="" && splitStart[1] != null){
		 			if(splitStart[1] == 'PM'){
		 				startTimeHours = 12+parseInt(startTimeHours);
		 			}
		 		}
		 		
		 		if(splitEnd[1] != undefined && splitEnd[1] !="" && splitEnd[1] != null){
		 			if(splitEnd[1] == 'PM'){
		 				endDateTimeHours = 12+parseInt(endDateTimeHours);
		 			}
		 		}
		 		if(startTimeHours > endDateTimeHours){
					 $scope.Messager('warn','Warning', 'Please confirm Shift End time selected falls in next day? If Yes, Please Continue Else Change the End Time');	
				 	 $scope.$apply(function(){ $scope.shiftsDefine.isNextDay = 'Y';});
				 }else{
					 $scope.$apply(function(){ $scope.shiftsDefine.isNextDay = 'N';});
				 }
			 }else{
				 if($('#startTime').val() > $('#endTime').val()){
					 $scope.Messager('warn','Warning', 'Please confirm Shift End time selected falls in next day? If Yes, Please Continue Else Change the End Time');	
				 	 $scope.$apply(function(){ $scope.shiftsDefine.isNextDay = 'Y';});
				 }else{
					 $scope.$apply(function(){ $scope.shiftsDefine.isNextDay = 'N';});
				 }
			 }
		 }
		$scope.fun_timeChangeListener();
	 });
	 
	 $scope.endDateFlag = false;
	 
	 
	 $scope.fun_timeChangeListener = function(){
		 if($('#startTime').val() != null && $('#startTime').val() != undefined && $('#startTime').val() != ""
			 && $('#endTime').val() != null && $('#endTime').val() != undefined && $('#endTime').val() != ""){
			 
			 	var splitStart = $('#startTime').val().split(" ");
			 	var splitEnd = $('#endTime').val().split(" ");
			 	var startDateTime = splitStart[0];
			 	var endDateTime =  	 splitEnd[0];
			 	var minsNegativeFlag = false;
			 	//alert(startDateTime +"::"+endDateTime);
			 	
			 	
			 	var startTimeHours = startDateTime.split(":")[0];
			 	var startTimemins = startDateTime.split(":")[1];
			 	
			 	var endDateTimeHours = endDateTime.split(":")[0];
		 		var endDateTimeMins = endDateTime.split(":")[1];
		 		//alert(endDateTimeMins +"::"+startTimemins);
		 		if(splitStart[1] != undefined && splitStart[1] !="" && splitStart[1] != null){
		 			if(splitStart[1] == 'PM'){
		 				startTimeHours = 12+parseInt(startTimeHours);
		 			}
		 		}
		 		
		 		if(splitEnd[1] != undefined && splitEnd[1] !="" && splitEnd[1] != null){
		 			if(splitEnd[1] == 'PM'){
		 				endDateTimeHours = 12+parseInt(endDateTimeHours);
		 			}
		 		}
		 		var mins = parseInt(endDateTimeMins) - parseInt(startTimemins);
		 		//alert(mins);
		 			if(parseInt(mins) < 0){
		 				mins = 60 - ( - parseInt(mins));
		 				minsNegativeFlag = true;
		 			}
		 			
		 		var hours = parseInt(endDateTimeHours) - parseInt(startTimeHours);
		 		if(parseInt(hours) < 0){
		 			hours = 24 -(- parseInt(hours));
		 		}
		 		if(minsNegativeFlag){
	 	 			hours = hours -1;
	 	 		}
		 		var totalMinutes = parseInt(hours)*60 + parseInt(mins);
		 		if($('#unpaidBreak').val() != null && $('#unpaidBreak').val() != undefined && $('#unpaidBreak').val() != ""){
		 			totalMinutes = totalMinutes - parseInt($('#unpaidBreak').val());	 
		 		}
		 		hours = Math.floor(parseInt(totalMinutes)/60);
		 		mins = parseInt(totalMinutes)% 60;
		 		
		 		var strHours, strMins;
	 	 		strHours = ''+hours;
	 	 		strMins = ''+mins;
		     	if(hours < 10)
		     		  strHours = '0'+hours;
		     	if(mins < 10)
		     		  strMins = '0'+mins;
		 		$('#totalHours').val(strHours+':'+strMins);	 		
		 }
		 
	 }
	 
	 
	
		 $( "#offShift,#halfDayShift,#shiftAllowance" ).change(function() {
			 
			 
			   if($scope.offShift){
				   
				   $( "#startTimeLabel").removeClass( "required" );   
				   $( "#startTime").removeAttr( "required" );   
				   $( "#startTime").removeClass( "required" ); 
				   
				   $( "#endtimeLabel").removeClass( "required" );   
				   $( "#endTime").removeAttr( "required" );
				   $( "#endTime").removeClass( "required" ); 
				   
				   $( "#unpaidBreakLabel").removeClass( "required" );   
				   $( "#unpaidBreak").removeAttr( "required" );
				   $( "#unpaidBreak").removeClass( "required" ); 
				   
				   $( "#totalHoursLabel").removeClass( "required" );   
				   $( "#totalHours").removeAttr( "required" );
				   $( "#totalHours").removeClass( "required" ); 
				
				   $('#startTime').val('');
				   $('#endTime').val('');
				   $('#unpaidBreak').val('');
				   $('#totalHours').val(0);						   
					// alert($scope.startTime);
					 $scope.startTime = '';
					 $scope.endTime = '';
					 $scope.unpaidBreak = '';
					 $scope.totalHours =0;
					
					 $('#shifftDiv').hide();
				  
			   }else if(!$scope.offShift){				   
				   $( "#startTimeLabel").addClass( "required" );   
				   $( "#startTime").addClass( "required" );
				   $( "#startTime").attr( "required" );   
				   
				   $( "#endtimeLabel").addClass( "required" );   
				   $( "#endTime").addClass( "required" );
				   $( "#endTime").attr( "required" ); 
				   
				   $( "#unpaidBreakLabel").addClass( "required" );   
				   $( "#unpaidBreak").addClass( "required" );
				   $( "#unpaidBreak").attr( "required" ); 
				   
				   $( "#totalHoursLabel").addClass( "required" );   
				   $( "#totalHours").addClass( "required" );
				   $( "#totalHours").attr( "required" );
				   $('#shifftDiv').show();
				  
				  
			   }
			 
			   
			 
			 
			  });
		 
	
    
}]);


