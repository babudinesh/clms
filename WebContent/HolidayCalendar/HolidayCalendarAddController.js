'use strict';


HolidayController.controller("HolidayAddCtrl", ['$scope', '$window','$http', '$resource','$location','$filter', '$routeParams','myservice','$cookieStore', function ($scope,$window, $http, $resource, $location, $filter, $routeParams, myservice, $cookieStore) {
			$.material.init();
		   /* $('#fromDate,#toDate').bootstrapMaterialDatePicker({
		    	time: false,
		    	clearButton: true,
		    	format : "DD/MM/YYYY"
		    });*/
			
			$('#fromDate,#toDate').datepicker({
			      changeMonth: true,
			      changeYear: true,
			      dateFormat:"dd/mm/yy",
			      showAnim: 'slideDown'	    	  
			    });

		    var year = new Date();
		    $scope.yearList = [
			                     {"id":year.getFullYear()-1,"name" : year.getFullYear()-1 },
			                     {"id":year.getFullYear(),"name" : year.getFullYear()},
			                     {"id":year.getFullYear()+1 ,"name" : year.getFullYear()+1 }
			                    
			                     ];
		    
	
	       if ($routeParams.id > 0) {
//	    	   alert($routeParams.id);
	           $scope.readOnly = true;
	           $scope.datesReadOnly = true;
	           $scope.updateBtn = true;
	           $scope.saveBtn = false;
	           $scope.gridButtons = false;
	           $scope.resetBtn = false;
	           $scope.transactionList = false;
	           $scope.statusDisable = true;
	       } else {
	           $scope.readOnly = false;
	           $scope.datesReadOnly = false;
	           $scope.updateBtn = false;
	           $scope.saveBtn = true;
	           $scope.resetBtn = true;
	           $scope.gridButtons = false;
	           $scope.transactionList = false;
	           $scope.popUpSave = true;
		       $scope.popUpUpdate =false;
		       $scope.statusDisable = true;
	       }
	       $scope.updateBtnAction = function (this_obj) {
	    	   $scope.calCode = 1;
	    	   $scope.calYear = 1;
	           $scope.readOnly = false;
	           $scope.onlyRead = true;
	           $scope.datesReadOnly = false;
	           $scope.updateBtn = false;
	           $scope.saveBtn = true;
	           $scope.viewOrUpdateBtn = false;
	           $scope.correctHistoryBtn = false;
	           $scope.resetBtn = false;
	           $scope.cancelBtn = true;
	           $scope.gridButtons = true;
	           $scope.transactionList = false;
	           $scope.popUpSave = true;
		       $scope.popUpUpdate =false;
		       
	           $('.dropdown-toggle').removeClass('disabled');
	           if($cookieStore.get("userId") == 3){//company Admin
		       		$scope.statusDisable = false;
		       }else{
		       		$scope.statusDisable = true;
		       }
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
		        $scope.getData('HolidayController/getHolidayCalendarById.json', { holidayCalendarDetailsId: $routeParams.id,customerId : ''}, 'plantList')
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
		    		} else if (type == 'calendarList') {
	   				  // alert(JSON.stringify(response.data.countryList));
	                   $scope.calendarList = response.data;
	                   $scope.calendar = response.data.holidayVo[0];
	                   if( $scope.calendar != undefined){
	                	   $scope.calendarList.companyList = response.data.companyList;
	                	   $scope.calendarList.locationList = response.data.locationList;
	                	   $scope.calendarList.countryList = response.data.countryList;
	                	   
	                   }else{
	                	   $scope.calendar = new Object();
	                	   $scope.calendar.status='Y';
	                	   if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
	      		                $scope.calendar.customerId=response.data.customerList[0].customerId;		                
	      		                $scope.customerChange();
	      		                }
	                   }
	                // for button views
	     			  	if($scope.buttonArray == undefined || $scope.buttonArray == '')
		    			 $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Holiday Calendar'}, 'buttonsAction');  
	               }else if (type == 'customerChange') {
	   					//alert(JSON.stringify(response.data));
	                   $scope.calendarList.companyList = response.data;
	                   if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	      	                $scope.calendar.companyId = response.data[0].id;
	      	                $scope.companyChange();
	      	           }
	               }else if (type == 'companyChange') {
					   //alert(JSON.stringify(response.data));
	                   $scope.calendarList.countryList = response.data;
	                   if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	      	                $scope.calendar.countryId = response.data[0].id;
	      	                $scope.countryChange();
	      	           }
	                  // $scope.fun_checkCalendarCode();
	               }else if (type == 'countryChange') {
					   //alert(JSON.stringify(response.data));
	                   $scope.calendarList.locationList = response.data;
	                   if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	      	                $scope.calendar.locationId = response.data[0].locationId;
	      	           }
	                   $scope.fun_checkCalendarCode();
	                   $scope.fun_checkCalendarYear();
	               }else if(type == 'validateCode'){
	            	   $scope.calCode = new Object();
	            	  // alert(response.data);
	           			$scope.calCode = response.data;
	           			if(response.data == 0){
	           				$scope.Messager('error', 'Error','Calendar code already exists.');
	           			}
	               }else if(type == 'validateYear'){
	            	    $scope.calYear = new Object();
	           			$scope.calYear = response.data;
	           			if(response.data == 0){
	           				$scope.Messager('error', 'Error','Calendar year already exists for this location.');
	           				$scope.gridButtons = false;
	           			}else{
	           				$scope.gridButtons = true;
	           			}
	           			
	               }else if (type == 'saveCalendar') {
	            	  // alert(JSON.stringify(response.data));
	            	   if(response.data.id > 0){
	            		   $scope.Messager('success', 'success', 'Holiday Calendar Saved Successfully',buttonDisable);
	            		   $location.path('/HolidayCalendarAdd/'+response.data.id);
	            	   }else{
		            	   $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
		               }
	               }
	           },
	           function () { 
	        	   $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
	           });
	       }
	      
	       $scope.getData('HolidayController/getHolidayCalendarById.json', { holidayCalendarDetailsId: $routeParams.id, customerId: $cookieStore.get('customerId') }, 'calendarList')
	       	
	       $scope.customerChange = function () {
	   			//alert($scope.location.customerId);
	            $scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: ($scope.calendar.customerId != undefined || $scope.calendar.customerId != null) ? $scope.calendar.customerId : '0',companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : ($scope.calendar.companyId == undefined || $scope.calendar.companyId == null) ? '0' : $scope.calendar.companyId}, 'customerChange');
	       };
	       	
	       $scope.companyChange = function () {
	       		//alert($scope.plant.companyId);
		   		$scope.getData('vendorController/getcountryListByCompanyId.json',{customerId: ($scope.calendar.customerId != undefined || $scope.calendar.customerId != null) ?  $scope.calendar.customerId : '0' , companyId: ( $scope.calendar.companyId != undefined || $scope.calendar.companyId != null) ? $scope.calendar.companyId : '0'}, 'companyChange')

		   };
			
		   $scope.countryChange = function () {
				//alert(JSON.stringify($scope.plant.countryId));
				$scope.getData('LocationController/getLocationsListBySearch.json',{customerId: ($scope.calendar.customerId == undefined || $scope.calendar.customerId == null )? '0' : $scope.calendar.customerId, companyId : ($scope.calendar.companyId == undefined || $scope.calendar.companyId == null) ? '0' : $scope.calendar.companyId, countryId : ($scope.calendar.countryId == undefined || $scope.calendar.countryId == null) ? '0' : $scope.calendar.countryId, status:'Y'} , 'countryChange');
		   };
		   
	       $scope.save = function ($event) {
	    	   if($scope.calCode == 0){
	    		   $scope.Messager('error', 'Error', 'Calendar code already exists');
	    	   }else if($scope.calYear == 0){
	    		   $scope.Messager('error', 'Error', 'Calendar year already exists for this location');
	    	   }else if($("#holidayForm").valid()){
	     	        $scope.calendar.holidayList= $scope.calendarList.holidayList;
		            $scope.calendar.createdBy = $cookieStore.get('createdBy');
				    $scope.calendar.modifiedBy = $cookieStore.get('modifiedBy');
		           // alert(angular.toJson($scope.calendar));
		            $scope.getData('HolidayController/saveHolidayCalendar.json', angular.toJson($scope.calendar), 'saveCalendar');
	           }
	       };
	       
	       $scope.correctHistorySave= function($event){
	    	   if($('#holidayForm').valid()){
				   $scope.calendar.modifiedBy = $cookieStore.get('modifiedBy');
	    		 //  alert(angular.toJson($scope.calendar));
				   $scope.getData('HolidayController/saveHolidayCalendar.json', angular.toJson($scope.calendar), 'saveCalendar');
	    	   }
		   }

	       $scope.close = function(){
	    	   $scope.cal = new Object();
	    	   $('div[id^="myModal"]').modal('hide');
	       };
	       
	       $scope.saveChanges = function(){ 
	    	   if($('#holiday').valid()){
		    	   angular.forEach($scope.calendarList.holidayList, function(value, key){	
			    		  // alert('Value: '+value.fromDate+",  "+$('#fromDate').val());
						      if(value.holidayName == $scope.cal.holidayName && value.fromDate == $('#fromDate').val() && value.toDate == $('#toDate').val()){
						    	  $scope.Messager('error', 'Error', 'This holiday already exists',true); 
						    	  status = true;			    		
						      }else if(value.holidayName == $scope.cal.holidayName){
						    	  $scope.Messager('error', 'Error', 'This holiday name already exists',true); 
						    	  status = true;
						      }else if(value.fromDate == $('#fromDate').val() && value.toDate == $('#toDate').val()){
						    	  $scope.Messager('error', 'Error', 'This holiday date already exists',true); 
						    	  status = true;
						      }else{
						    	  $('div[id^="myModal"]').modal('hide');
						      }
		    	   });
		    	   
		    	   if(status){
		    		   $scope.Messager('error', 'Error', 'This holiday name already exists',true);
		    	   }/*else if($scope.calendar.year == undefined && $scope.calendar.year == "" &&  $scope.calendar.year == null ){
		    		   $scope.Messager('error', 'Error', 'First please select year');
		    		   $('div[id^="myModal"]').modal('hide');
		    	   }*/else if(!status && ($('#fromDate').val() != undefined && $('#fromDate').val() != null) && ($('#toDate').val() != undefined || $('#toDate').val() != null) 
	  						&&  (new Date(moment($('#fromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getFullYear() != $scope.calendar.year
	  						|| new Date(moment($('#toDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getFullYear() != $scope.calendar.year)){
						 $scope.Messager('error', 'Error', 'Please add the holiday dates for selected year only');
						 $('#fromDate').val("");
						 $('#toDate').val("");
		    	   }else if(!status && ($('#fromDate').val() != undefined && $('#fromDate').val() != null) && ($('#toDate').val() != undefined || $('#toDate').val() != null) && ($scope.toDate != undefined)
		    			   						&& (new Date(moment($('#fromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime() > new Date(moment($('#toDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime())){
	       		       $scope.Messager('error', 'Error', 'From Date should not be less than to Date');
		    		   $scope.toDate="";
	       		   }else if($scope.cal.holidayName != undefined && $scope.cal.holidayName != '' 
	    		   				&& $scope.cal.holidayTypeId != undefined && $scope.cal.holidayTypeId != '' 
	    		   				&& $('#fromDate').val() != undefined && $('#fromDate').val() != ''
	    		   				&&$('#toDate').val() != undefined && $('#toDate').val() != ''){
	    		   
		    		   $scope.calendarList.holidayList.push({
		              		'holidayName':$scope.cal.holidayName,  		
		              		'holidayTypeName':$('#holidayTypeId option:selected').html(),
		              		'holidayTypeId':$scope.cal.holidayTypeId ,
		              		'fromDate':$('#fromDate').val(),
		              		'toDate':$('#toDate').val(),
		              		
	             	   }); 
				    $('div[id^="myModal"]').modal('hide');
				    
				    $scope.popUpSave = true;
			       	$scope.popUpUpdate =false;
		    	   }else{
		    		   $scope.Messager('error', 'Error', 'Please Enter holiday details',true);
		    	   }
	    	   }
	       }
	       
	       $scope.Delete = function($event){    	
	    	   var del = $window.confirm('Are you sure you want to delete?');
	    	   if (del) {
	    		   $scope.calendarList.holidayList.splice($($event.target).parent().parent().index(),1);
	    		   $('.addrow').show();
	    	   }
	    	  
	    	  
	       }
	       
	       $scope.Edit = function($event){    	
	    	   $scope.calVar = $($event.target).parent().parent().index();
	           $scope.cal =  $scope.calendarList.holidayList[$($event.target).parent().parent().index()];
	           $scope.popUpSave = false;
	           $scope.popUpUpdate =true;
	          }
	          
	          
	          $scope.updateDetails= function($event){    	
	        	  if($('#holiday').valid()){
		        	  $scope.calendarList.holidayList.splice($scope.calVar,1);    	
			       	   if($scope.cal.holidayName != undefined && $scope.cal.holidayName != '' 
			   				&& $scope.cal.holidayTypeId != undefined && $scope.cal.holidayTypeId != '' 
	    		   				&& $('#fromDate').val() != undefined && $('#fromDate').val() != ''
	    		   				&&$('#toDate').val() != undefined && $('#toDate').val() != ''){
			    		   $scope.calendarList.holidayList.push({
			              		'holidayName':$scope.cal.holidayName,  		
			              		'holidayTypeName':$('#holidayTypeId option:selected').html(),
			              		'holidayTypeId':$scope.cal.holidayTypeId ,
			              		'fromDate':$('#fromDate').val(),
			              		'toDate':$('#toDate').val(),
			              		
			    		   }); 
			          }
			       	 $('div[id^="myModal"]').modal('hide');
				    
				    $scope.popUpSave = true;
			       	$scope.popUpUpdate =false;
	        	  }
	          };
	          
	          
	          $scope.fun_checkErr = function() {
	        	  if(($('#fromDate').val() != undefined && $('#fromDate').val() != null && $('#fromDate').val() != "") && ($('#toDate').val() != undefined && $('#toDate').val() != null && $('#toDate').val() != null) 
	  						&&  (new Date(moment($('#fromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getFullYear() != $scope.calendar.year
	  						&& new Date(moment($('#toDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getFullYear() != $scope.calendar.year)){
						 $scope.Messager('error', 'Error', 'Please add the holiday dates for selected year only');
						 $('#fromDate').val("");
						 $('#toDate').val("");
		    	   }else{
	        	  
		          	   if($('#fromDate').val() != undefined && $('#fromDate').val() != null && $('#fromDate').val() != "" &&  ( $('#toDate').val() == undefined || $('#toDate').val() == null || $('#toDate').val()== "")){
			  			 $scope.$apply(function(){
			  				$('#toDate').val($('#fromDate').val())  ;
			  			 });
			  		 	}else if(($('#fromDate').val() != undefined || $('#fromDate').val() != null) && ($('#toDate').val() != undefined || $('#toDate').val() != null) || ($scope.toDate != undefined)){
		          		  var from = new Date(moment($('#fromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') );
		        		       var to = new Date(moment($('#toDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') );
		        		       
		        		   	    if(from.getTime() > to.getTime()){
		        		      	    	$scope.Messager('error', 'Error', 'From Date should not be less than to Date');
		        		      	    	$scope.toDate="";
		        		   	    }
		          	  }
		    	   }
	          };
	          
			 $scope.plusIconAction = function(){
				 $scope.cal = new Object();
				 $('#fromDate').val('');
				 $('#toDate').val('');
				 
			  	 $scope.popUpSave = true;
			     $scope.popUpUpdate =false;
			 };
			 
			 // To validate code
			 $scope.fun_checkCalendarCode = function(){
				
		  	    	if($scope.calendar.customerId != null && $scope.calendar.customerId != undefined && $scope.calendar.customerId != ''
		  	    			&& ($scope.calendar.companyId != null && $scope.calendar.companyId != undefined && $scope.calendar.companyId != '')
		  	    				    			&& ($scope.calendar.holidayCalendarCode != null || $scope.calendar.holidayCalendarCode != undefined && $scope.calendar.holidayCalendarCode != '')
		  	    				    			//&& ($scope.calendar.locationCode != null || $scope.calendar.locationCode != undefined || $scope.calendar.locationCode != '')
		  	    				    			//&& ($scope.calendar.year != null || $scope.calendar.year != undefined || $scope.calendar.year != '')
		  	    				    			&& ($scope.calendar.countryId != null && $scope.calendar.countryId != undefined && $scope.calendar.countryId != '')){
		  	    		$scope.getData('HolidayController/validateCalendarCode.json',{ customerId : $scope.calendar.customerId, companyId: $scope.calendar.companyId,  holidayCalendarCode: $scope.calendar.holidayCalendarCode, countryId:$scope.calendar.countryId },'validateCode')

		  	    	}
		  	 };
		  	 
		  	 
		  	 // To validate year
		  	 $scope.fun_checkCalendarYear = function(){ 
			  	if($scope.calendar.customerId != null && $scope.calendar.customerId != undefined && $scope.calendar.customerId != ''
		    			&& ($scope.calendar.companyId != null && $scope.calendar.companyId != undefined && $scope.calendar.companyId != '')
		    				    			//&& ($scope.calendar.holidayCalendarCode != null && $scope.calendar.holidayCalendarCode != undefined && $scope.calendar.holidayCalendarCode != '')
		    				    			&& ($scope.calendar.locationId != null && $scope.calendar.locationId != undefined && $scope.calendar.locationId != '')
		    				    			&& ($scope.calendar.year != null && $scope.calendar.year != undefined && $scope.calendar.year != '')
		    				    			&& ($scope.calendar.countryId != null && $scope.calendar.countryId != undefined && $scope.calendar.countryId != '')){
		    		$scope.getData('HolidayController/validateCalendarCode.json',{ customerId : $scope.calendar.customerId, companyId: $scope.calendar.companyId, locationId:$scope.calendar.locationId, holidayCalendarCode: "", year:$scope.calendar.year, countryId:$scope.calendar.countryId },'validateYear')
	
		    	}
		  	 };
		  	 
		  	 $scope.locationChange = function(){
		  		$scope.fun_checkCalendarCode();
		  		$scope.fun_checkCalendarYear(); 
		  	 }
		  	 
		  	 $scope.CheckDate = function(from, to){
		  		var  startDate , endDate ,  date ;
		    	startDate  = moment(from, 'DD/MM/YYYY'); 
		    	endDate  = moment(to, 'DD/MM/YYYY');	
		    	date  = moment(new Date(),'DD/MM/YYYY');
		    	//date = moment(date1, 'DD/MM/YYYY');
		    	//alert("1: "+date1);
		    	/*alert("from "+(date.isAfter(endDate) 
			 			 +", "+ date.isAfter(startDate)) 
			 			 +", "+ (date.isSame(startDate) +", "+ date.isSame(endDate)));*/
			 	if ((date.isBefore(endDate) 
			 			 && date.isBefore(startDate)) 
			 			 || (date.isSame(startDate) || date.isSame(endDate))
			 			) { 
			 		return true;
			 		
			 	}else{
			 		return false;
			 	}
		  	 }
		  	
		  	 $scope.statusChange = function(){
		  		 
		  	 }
	 
}]);


