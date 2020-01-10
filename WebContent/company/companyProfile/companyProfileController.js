'use strict';

// var companyDetailsControllers = angular.module("myApp.companyDetailsModule", []);
companyControllers.controller("companyProfileCtrl",  ['$scope', '$rootScope', '$http', '$resource','$location','$cookieStore',function($scope, $rootScope, $http,$resource,$location,$cookieStore) {  
    
    
    $scope.comapny = new Object();
    $scope.updateBtn = true;
    $scope.saveBtn = false;
	$scope.readOnly =true;	

    
   /* myservice.customerId = $cookieStore.get('CompanyCustomerId');       
    myservice.companyId = $cookieStore.get('CompanyCompanyId'); */ 
    $scope.companyInfoId = $cookieStore.get('companyInfoId');
    if($cookieStore.get('companyInfoId') > 0){
    	
    }else{
    	$location.path('/companyDetails/create');
    }

    if($cookieStore.get('CompanyCompanyId') > 0){
    	$scope.editCompany = true;
        $scope.newCompany = false;
    }else{
    	$scope.editCompany = false;
        $scope.newCompany = true;
    }

    
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

    
    
    $scope.Week_Days = [{"id":1,"name" : "MONDAY" },
                    {"id":2,"name" : "TUESDAY" },
                    {"id":3,"name" : "WEDNESDAY" },
                    {"id":4,"name" : "THURSDAY" },
                    {"id":5,"name" : "FRIDAY" },
                    {"id":6,"name" : "SATURDAY" },
                    {"id":7,"name" : "SUNDAY" }];
   
    $.material.init();
            
    $('#businessEndTime,#businessStartTime').bootstrapMaterialDatePicker({ 
        format : 'HH:mm ',date: false, clearButton: true
    }); 
    
    /*$('#bussinessHours').bootstrapMaterialDatePicker({ 
        format : 'HH', date: false, clearButton: true,shortTime: true 
    });*/

    
	$scope.updateCompanyProfileBtnAction = function() {
		$scope.readOnly = false;			
        $scope.updateBtn = false;
        $scope.saveBtn = true;	
		$('.dropdown-toggle').removeClass('disabled');		
	} 	
			  
    // get Company Profile MAster Info
    $http(
			{
				url : ROOTURL+"CompanyController/getMasterInfoForComapanyProfile.json",
				method : "GET",				
				async : false,									
				headers : {
					"Content-Type" : "application/json"
				}
			}).then(
					function(response) {	
                       // console.log(angular.toJson(response.data.currency));
                                $scope.list_currency    = response.data.currency;                                
                                $scope.list_languages   = response.data.languages;													
                    },
					function(response) {						
		});								
				
	// get Company Profile Details...
	$http(
			{
				url : ROOTURL+"CompanyController/getCompanyProfileByCompanyId.json",
				method : "POST",
				data : { "companyId" : $cookieStore.get('CompanyCompanyId'),
						 "customerId": $cookieStore.get('CompanyCustomerId')
						 },
				async : false,									
				headers : {
					"Content-Type" : "application/json"
				}
			}).then(
					function(response) {	                                             
                                $scope.company          = response.data;                     
								$scope.companyProfileId = response.data.companyProfileId;
								/*myservice.customerId   = response.data.customerDetailsId;
								myservice.companyId    = response.data.companyDetailsId; 
								myservice.companyName = response.data.companyName;
						        myservice.companyCode = response.data.companyCode;
						        myservice.countryName    = response.data.countryName; */
						        
						        $cookieStore.put('CompanyCustomerId',response.data.customerDetailsId);       
						        $cookieStore.put('CompanycompanyId',response.data.companyDetailsId);
						        $cookieStore.put('CompanycompanyName',response.data.companyName);
						        $cookieStore.put('CompanycompanyCode',response.data.companyCode);
						        $cookieStore.put('CompanycountryName',response.data.countryName);
						        
					
					},
					function(response) {						
		});								
				
	$scope.fun_savecompanyprofile = function($event){	
		//$scope.fun_FindTime();
		//$scope.vvalidateDays();
		if($('#CompanyProfile').valid()){
	        //console.log(angular.toJson($scope.company));       
			//alert($scope.company.retireAge < $scope.company.maxAge);
			if($scope.company.maxAge != undefined && $scope.company.minAge != null && $scope.company.minAge != '' && ($scope.company.minAge < 16 )){
	       		$scope.Messager('error', 'Error', 'Minimum Age should not be less than 16');
	       	}else if($scope.company.maxAge != undefined && $scope.company.maxAge != null && $scope.company.maxAge != '' && $scope.company.maxAge > 100 ){
	       		$scope.Messager('error', 'Error', 'Maximum Age should not be greater than 100');
	       	}else if($scope.company.retireAge != undefined && $scope.company.retireAge != null && $scope.company.retireAge != '' && $scope.company.retireAge > 100){
	       		$scope.Messager('error', 'Error', 'Retirement Age should not be greater than 100');
	       	}else if($scope.company.maxAge != undefined && $scope.company.maxAge != null && $scope.company.maxAge != ''
	       				&& $scope.company.retireAge != undefined && $scope.company.retireAge != null && $scope.company.retireAge != '' && ($scope.company.maxAge > $scope.company.retireAge && $scope.company.retireAge < 100 )){
	       		$scope.Messager('error', 'Error', 'Maximum Age should not be greater than Retirement Age');
	       	}else if($scope.company.maxAge != undefined && $scope.company.maxAge != null && $scope.company.maxAge != ''
	       				&& $scope.company.minAge != undefined && $scope.company.minAge != null && $scope.company.minAge != '' && ($scope.company.maxAge < $scope.company.minAge )){
	       		$scope.Messager('error', 'Error', 'Minimum Age should not be greater than Maximum Age');
	       	}else if($scope.company.businessHoursPerDay != null && $scope.company.businessHoursPerDay != undefined  && $scope.company.businessHoursPerDay != '' && $scope.company.businessHoursPerDay > 24  ){
   			   $scope.Messager('error', 'Error', 'Business hours/Day should be less than or equal to 24 Hours');
	       	}else if($scope.company.standardHoursPerWeek != null && $scope.company.standardHoursPerWeek != undefined  && $scope.company.standardHoursPerWeek != '' &&  $scope.company.standardHoursPerWeek > 168){
   			   $scope.Messager('error', 'Error', 'Standard Hours/Week should be less than or equal to 168 Hours');
	       	}/*else if($scope.company.bussinessHoursPerDay != null && $scope.company.bussinessHoursPerDay != undefined  && $scope.company.bussinessHoursPerDay != '' && $scope.company.bussinessHoursPerDay+".00" != $scope.businessHours &&  $scope.company.bussinessHoursPerDay+".00" != $scope.businessHours1){
     		   $scope.Messager('error', 'Error', 'Business Start Time and Business End Time total duration should not be greater than Business Hours/Day.');
	       	}else if($scope.company.standarHoursPerWeek != null && $scope.company.standarHoursPerWeek != undefined  && $scope.company.standarHoursPerWeek != '' && $scope.company.standarHoursPerWeek != parseInt($scope.company.bussinessHoursPerDay)*parseInt($scope.company.numberOfWorkingDays)){
     		   $scope.Messager('error', 'Error', 'Standard Hours/Week should be the multiple of Business Hours/Day and Number of Working Days.');
	       	}*/else{
		        $scope.company.bussinessStartTime = $('#businessStartTime').val();
		        $scope.company.bussinessEndTime = $('#businessEndTime').val();
		       /* myservice.companyName = $scope.company.companyName;
		        myservice.companyCode = $scope.company.companyCode;
		        myservice.countryName    = $scope.company.countryName; */
		        
		        $cookieStore.put('CompanycustomerId',$scope.company.customerDetailsId);       
		        $cookieStore.put('CompanycompanyId',$scope.company.companyDetailsId);
		        $cookieStore.put('CompanycompanyName',$scope.company.companyName);
		        $cookieStore.put('CompanycompanyCode',$scope.company.companyCode);
		        $cookieStore.put('CompanycountryName',$scope.company.countryName);
		        $scope.company.createdBy = $cookieStore.get('createdBy'); 
		        $scope.company.modifiedBy = $cookieStore.get('modifiedBy'); 
		        
				$http(
						{
							url : ROOTURL+"CompanyController/saveCompanyProfile.json",
							method : "POST",
								data : angular.toJson($scope.company),
							headers : {
								"Content-Type" : "application/json"
							}
						})
						.then(function(response) {
							$scope.updateBtn = true;
						    $scope.saveBtn = false;
							$scope.readOnly =true;	
							$scope.Messager('success', 'success', 'Company Profile Saved Successfully',angular.element($event.currentTarget));
							// $location.path('/companyContctsSearch');
							},
						function(response) {
							$scope.Messager('error', 'Error', 'Technical Issue Please try after some time',angular.element($event.currentTarget));    
						});      
	       	}
		}
	}
	
	 $scope.validateDays = function(){
		   if ($scope.company.numberOfWorkingDays != undefined && $scope.company.numberOfWorkingDays != null && $scope.company.numberOfWorkingDays != "" && ($scope.company.numberOfWorkingDays > 7 || $scope.company.numberOfWorkingDays <= 1)){
			   $scope.Messager('error', 'Error', 'Number of Working days should be between 2 and 7.');
		   }else{
			   if(($scope.company.workWeekStartId != undefined && $scope.company.workWeekStartId != null && $scope.company.workWeekStartId != "" ) 
	      			   && ($scope.company.numberOfWorkingDays != undefined && $scope.company.numberOfWorkingDays != null && $scope.company.numberOfWorkingDays != "" ) ){
				   $scope.$apply(function(){
			  				$scope.company.workWeekEndId = (parseInt($scope.company.workWeekStartId) + parseInt($scope.company.numberOfWorkingDays)-1);
			  				if(parseInt($scope.company.workWeekEndId) >  7 ){
			  					$scope.company.workWeekEndId =( parseInt($scope.company.workWeekEndId) - 7);
			  				}
			  				//alert("2: "+$scope.location.workWeekEndId);
				   });
			   }
		   }
	 };
	 
	 $scope.changeWeekEnd = function(){
  	   if(($scope.location.workWeekStartDay != undefined && $scope.location.workWeekStartDay != null && $scope.location.workWeekStartDay != "" ) 
    			   && ($scope.location.numberOfWorkingDays != undefined && $scope.location.numberOfWorkingDays != null && $scope.location.numberOfWorkingDays != "" ) ){
				$scope.location.workWeekEndDay = (parseInt($scope.location.workWeekStartDay) + parseInt($scope.location.numberOfWorkingDays)-1)+"";
				if(parseInt($scope.location.workWeekEndDay) >  7 ){
					$scope.location.workWeekEndDay =( parseInt($scope.location.workWeekEndDay) - 7)+"";
				}
		   }
     };
	/*$scope.fun_CurrencyChange = function(){
        console.log("$('#isMultiCurrency').val():: "+$('#isMultiCurrency').val());
		if($('#isMultiCurrency').val() == 'on'){
			$('.currencyDiv').removeClass('hidden');
			 $scope.currencys = ""; 
			setTimeout(function() {$('.selectCurrencypicker').selectpicker('refresh');}, 100);
		}else{
			$('.currencyDiv').addClass('hidden');
		}
	}
	
	$scope.fun_LanguageChange = function(){
        console.log("$('#isMultiLanguage').val():: "+$('#isMultiLanguage').val());
		if($('#isMultiLanguage').val() == 'on'){
			$('.langdiv').removeClass('hidden');
			$scope.languages = ""; 
			setTimeout(function() {$('.selectLaguagepicker').selectpicker('refresh');}, 100);
		}else{
			 $('.langdiv').addClass('hidden'); 
		}
	}*/
	/* 
	 * To find the difference between business start time and business end time
	 * $scope.fun_FindTime = function(){
			 if($('#businessStartTime').val() != null && $('#businessStartTime').val() != undefined && $('#businessStartTime').val() != ""
				 && $('#businessEndTime').val() != null && $('#businessEndTime').val() != undefined && $('#businessEndTime').val() != ""){
				 	var startDateTime = $('#businessStartTime').val();
				 	var endDateTime =  $('#businessEndTime').val();	 
				 	var minsNegativeFlag = false;
				 	//alert(startDateTime +"::"+endDateTime);
				 	
				 	var startTimeHours = startDateTime.split(":")[0];
				 	var startTimemins = startDateTime.split(":")[1];
				 	
				 	var endDateTimeHours = endDateTime.split(":")[0];
			 		var endDateTimeMins = endDateTime.split(":")[1];
			 		//alert(endDateTimeMins +"::"+startTimemins);
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
			 		
			 		hours = Math.floor(parseInt(totalMinutes)/60);
			 		mins = parseInt(totalMinutes)% 60;
			 		
			 		var strHours, strMins;
			 		strHours = ''+hours;
		 	 		strHours1 = ''+hours;
		 	 		strMins = ''+mins;
			     	if(hours < 10)
			     		  strHours1 = '0'+hours;
			     	if(mins < 10)
			     		  strMins = '0'+mins;
			 		$scope.businessHours = (strHours+'.'+strMins);	
			 		$scope.businessHours1 = (strHours1+'.'+strMins);
			 }
			 
		 }	*/
    
}]);