'use strict';

WageController.controller("WageAddCtrl", ['$window','$scope', '$http', '$resource','$location','$filter', '$routeParams','myservice','$cookieStore', function ($window,$scope, $http, $resource, $location, $filter, $routeParams, myservice, $cookieStore) {
	 $.material.init();
	 $scope.currency = new Object();
	 $scope.workPeice = new Object();
	 $scope.currency.overtimeFrequency ="Per Day";
	 $scope.currency.baseRateFrequency = "Per Day";
	
	    
	 $scope.list_frequency = [
	                     {"id":"Per Hour","name" : "Per Hour" },
	                     {"id":"Per Day","name" : "Per Day" }
	                     ];
	 
	 $scope.list_jobType = [{"id":"0","name" : "ALL" },
	                        {"id":"Full Time","name" : "Full Time" },
		                    {"id":"Part Time","name" : "Part Time" },
		                    {"id":"Permanent","name" : "Permanent" },
		                    {"id":"Temporary","name" : "Temporary" }];

	$scope.list_jobCategory = [{"id":"Skilled","name" : "Skilled" },
	                       {"id":"Semi Skilled","name" : "Semi Skilled" },
	                       {"id":"High Skilled","name" : "High Skilled" },
	                       {"id":"Special Skilled","name" : "Special Skilled" },
	                       {"id":"UnSkilled","name" : "UnSkilled" }];
	    
	$scope.list_status = [{ id:"Y" , name:"Active"},{ id:"N" , name:"In Active"}];	
	
    /* $('#transactionDate').bootstrapMaterialDatePicker({
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
     
     $scope.wage = new Object();
	
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
    	   $scope.readOnlyEdit = true;
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
           $('.addrow,.addrow1').hide();         
           
       } else {
    	   $('#transactionDate').val($filter('date')(new Date(),'dd/MM/yyyy'));
           $scope.readOnly = false;
           $scope.readOnlyEdit = false;
           $scope.datesReadOnly = false;
           $scope.updateBtn = false;
           $scope.saveBtn = true;
           $scope.viewOrUpdateBtn = false;
           $scope.correcttHistoryBtn = false;
           $scope.resetBtn = true;
           $scope.transactionList = false;
           $scope.gridButtons = true;
           $scope.cancelBtn = false;
           $scope.popUpSave = true;
           $scope.popUpUpdate =false;
           $('.addrow,.addrow1').show();
       }

       $scope.updateBtnAction = function (this_obj) {
    	   $('#transactionDate').val($filter('date')(new Date(),'dd/MM/yyyy'));
    	   $scope.readOnly = false;
    	   $scope.readOnlyEdit = true;
           $scope.datesReadOnly = false;
           $scope.updateBtn = false;
           $scope.saveBtn = true;
           $scope.viewOrUpdateBtn = false;
           $scope.correctHistoryBtn = false;
           $scope.resetBtn = false;
           $scope.cancelBtn = true;
           $scope.gridButtons = true;
           $scope.transactionList = false;
           if( $scope.wageList.wageRateCurrency.length == 0)
        	   $('.addrow').show();
           else
        	   $('.addrow').hide();
           
           if($scope.wageList.workPeiceList.length == 0)
        	   $('.addrow1').show();
           else
        	   $('.addrow1').hide();
           
           $('.dropdown-toggle').removeClass('disabled');
       }
       
       $scope.cancelBtnAction = function(){
	    	$scope.readOnly = true;
	    	$scope.readOnlyEdit = true;
	        $scope.datesReadOnly = true;
	        $scope.updateBtn = true;
	        $scope.saveBtn = false;
	        $scope.viewOrUpdateBtn = true;
	        $scope.correcttHistoryBtn = false;
	        $scope.resetBtn = false;
	        $scope.transactionList = false;
	        $scope.cancelBtn = false;
	        $scope.gridButtons = true;
	        $scope.returnToSearchBtn = true;
	        $scope.getData('WageRateController/getWageRateById.json', { wageRateDetailsId: $routeParams.id,customerId : ''}, 'wageRateList')
	    }
       
       $scope.viewOrEditHistory = function () {
           $scope.readOnly = false;
           $scope.readOnlyEdit = true;
           $scope.datesReadOnly = false;
           $scope.updateBtn = false;
           $scope.saveBtn = false;
           $scope.viewOrUpdateBtn = false;
           $scope.correcttHistoryBtn = true;
           $scope.resetBtn = false;      
           $scope.gridButtons = true;
           $scope.transactionList = true;
           $scope.cancelBtn = false;
           $scope.getData('WageRateController/getWageRateTransactionDatesList.json', { companyId: $scope.wage.companyId, customerId: $scope.wage.customerId ,wageRateId: $scope.wage.wageRateId }, 'transactionDatesChange');       
           
           $('.dropdown-toggle').removeClass('disabled');
       }
       
/*       $scope.cancelBtnAction = function(){
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
	        $scope.getData('WageRateController/getWageRateById.json', { wageRateDetailsId: $routeParams.id,customerId : ''}, 'wageRateList')
	    }*/
      
  	   
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
        		} else if (type == 'wageRateList') {
            	   $scope.wageList = response.data; 
                   $scope.wage = response.data.wageRateVo[0];
                   
                   if($scope.wage == undefined){
                	   $scope.wage = new Object();
                   }
                  /* if(response.data.customerList[0] != undefined && response.data.customerList[0].customerId == 1){
                	   $scope.wage.customerId = response.data.customerList[0].customerId;
                	   $scope.customerChange();
                   }*/
                   
                   if(response.data.wageRateVo[0] != undefined){
                	   $scope.wage.transactionDate =  $filter('date')( response.data.wageRateVo[0].transactionDate, 'dd/MM/yyyy');
                	   $scope.transDate1  = $scope.wage.transactionDate;
                	   $scope.wageList.companyList  = response.data.companyList;                	   
                	   $scope.wageList.jobCodeList = response.data.jobCodeList; 
                	   var flag = true;
                	   angular.forEach($scope.wageList.jobCodeList, function(value, key) {                		  
                		   if(value.jobCodeId == 0){
                			   flag = false;
                		   }
                		 });
                	   if(flag){
                	   $scope.wageList.jobCodeList.push({"jobCodeId":0,"jobName":"ALL","jobCode":"ALL"});     
                	   }
                	   $scope.wageList.countryList = response.data.countryList;
                	   $scope.wageList.vendorList = response.data.vendorList;
    		      	   $scope.wageList.wageScaleList = response.data.wageScaleList;
    		      	   $scope.wageList.workOrderList = response.data.workOrderNames;
    		      	   $scope.defaultCurrency = response.data.companyProfile[0].defaultCurrencyId;
    		      	   if(response.data.wageRateCurrency[0].baseRate != null  && response.data.wageRateCurrency[0].baseRate != undefined){
    		      		   $scope.currency = response.data.wageRateCurrency[0];
    		      	   }else{
    		      		   $scope.wageList.wageRateCurrency = [];
    		      	   }
    		      	   if(response.data.workPeiceList[0].workPieceRate != null && response.data.workPeiceList[0].workPieceRate != undefined){
    		      		   	$scope.workPeice = response.data.wageRateCurrency[0];
    		      	   }else{
    		      		   $scope.wageList.workPeiceList = [];
    		      	   }
                   }else{                	  
                	   $scope.wage.transactionDate = $filter('date')(new Date(),"dd/MM/yyyy");
                	   $scope.wage.status = "Y";
                	   
                	   if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
   		                $scope.wage.customerId=response.data.customerList[0].customerId;		                
   		                $scope.customerChange();
   		           }
                   }
                    // for button views
                   if($scope.buttonArray == undefined || $scope.buttonArray == '')
                   $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Wage Rate'}, 'buttonsAction');
                   
               }else if (type == 'customerChange') {
                   $scope.wageList.companyList = response.data;
                   if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
    	                $scope.wage.companyId = response.data[0].id;
    	                $scope.companyChange();
    	                }
                   
               }else if (type == 'companyChange') {
            	   $scope.wageList.vendorList = response.data.vendorList;
		      	   $scope.wageList.wageScaleList = response.data.wageScaleList;
            	   $scope.wageList.countryList = response.data.countryList;
            	   if(response.data.countryList[0] != undefined && response.data.countryList.length == 1){
                	   $scope.wage.countryId = response.data.countryList[0].id;
                	   $scope.countryChange();
                   }
               } else if(type == 'countryChange'){
            	   $scope.defaultCurrency = response.data.defaultCurrencyId;
                  
               }else if(type == 'vendorChange'){
            	   $scope.wageList.workOrderList = response.data;            	  
               }else if(type == 'jobCode'){            	 
            	   $scope.wageList.jobCodeList = response.data.jobCodeList;
            	   $scope.wageList.jobCodeList.push({"jobCodeId":0,"jobName":"ALL","jobCode":"ALL"});            	  
            	  // alert(angular.toJson(response.data.jobCodeList));
               }else if (type == 'saveWageRate') {            	   
            	   if(response.data.id > 0){
		            	$scope.Messager('success', 'success', 'Wage Rate Saved Successfully',buttonDisable);
		            	 $location.path('/WageRateAdd/'+response.data.id);
            	   }else if(response.data.id == 0){
            		   $scope.Messager('error', 'Error', 'Wage Rate Code Already Exists..',buttonDisable);
            	   }else if(response.data.id < 0){
            		   $scope.Messager('error', 'Error', response.data.name,buttonDisable);
	               }else{
	            	   $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
	               }
               
               }else if (type == 'transactionDatesChange') {
	            	var obj = response.data;
	            	$scope.transactionModel= response.data[(obj.length)-1].id;
	                $scope.transactionDatesList = response.data;
	            }else if(type == 'correctHistorySave'){
	            	if(response.data.id > 0){
	            		$scope.Messager('success', 'success', 'Wage Rate Record Updated Successfully',buttonDisable);
	            	}
	            }
           },
           function () { 
        	   $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
           });
       }
       $scope.getData('WageRateController/getWageRateById.json', { wageRateDetailsId: $routeParams.id,customerId : $cookieStore.get('customerId')}, 'wageRateList')
       	
       $scope.customerChange = function () {
            $scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.wage.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.wage.companyId ? $scope.wage.companyId : 0 }, 'customerChange');
       	};
       	
	   	$scope.companyChange = function() {
	   		if($scope.wage != undefined && $scope.wage.companyId != undefined && $scope.wage.companyId > 0 && $scope.wage.companyId != '')
	   		$scope.getData('WageRateController/getVendorNamesAsDropDown.json',{customerId: $scope.wage.customerId, companyId:$scope.wage.companyId }, 'companyChange')
	  	};
	  	
	   	$scope.countryChange = function () {
	   		 $scope.getData('CompanyController/getCompanyProfileByCompanyId.json',{customerId: $scope.wage.customerId, companyId:$scope.wage.companyId }, 'countryChange')
	         $scope.getData('JobcodeController/getJobCodeListBySearch.json', { customerId : ( $scope.wage.customerId == undefined )? '0' : $scope.wage.customerId  , companyId : $scope.wage.companyId == undefined ? '0' : $scope.wage.companyId  , countryId: $scope.wage.countryId == undefined ? '' : $scope.wage.countryId}, 'jobCode');
	   	};
	   	
	    $scope.vendorChange = function(){
	 	    if($scope.wage.vendorId != undefined && $scope.wage.vendorId != '')
		 		   $scope.getData("WageRateController/getWorkOrderAsDropDown.json", { customerId: $scope.wage.customerId, companyId:$scope.wage.companyId, vendorId : $scope.wage.vendorId } , "vendorChange");
		    };
		    
		    
       $scope.save = function ($event) {    	  
//    	   if($scope.wageList.workPeiceList.length <= 0 ){
//    		   $scope.Messager('error', 'Error', ' Please Enter Wage Rates/Piece ',true);
//    		   return;
//    	   }
    	   
    	   if($('#wageRateForm').valid()){
    		   
    		   if(($scope.wageList.workPeiceList != undefined && $scope.wageList.workPeiceList.length <= 0 ) && ( $scope.wageList.wageRateCurrency != undefined && $scope.wageList.wageRateCurrency.length <= 0 )){
        		   $scope.Messager('error', 'Error', ' Please Enter Base Wage Rate or Work/Piece Rate ',true);
        	   }else if($scope.transDate1 != '' && $scope.transDate1 != undefined && new Date(moment($scope.transDate1,'DD/MM/YYYY').format('YYYY-MM-DD')) .getTime() > new Date(moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime()){
	    		   $scope.Messager('error', 'Error', 'Transaction Date Should not be less than previous transaction date');
	        	  // $scope.wage.transactionDate =  $scope.transDate1;
        	   }else{
	    		   $scope.wage.transactionDate = $('#transactionDate').val();
	    		   $scope.wage.wageRateDetailsId =0;
	    		   $scope.wage.createdBy = $cookieStore.get('createdBy');
			       $scope.wage.modifiedBy = $cookieStore.get('modifiedBy');
			       
			       if($scope.wageList.wageRateCurrency.length > 0){
				       $scope.wage.baseRate = $scope.currency.baseRate;		       
				       $scope.wage.baseRateCurrencyId = $scope.currency.baseRateCurrencyId;
				       $scope.wage.baseRateFrequency= $scope.currency.baseRateFrequency;
				       $scope.wage.baseRateMargin = $scope.currency.baseRateMargin
				       $scope.wage.overtimeRate = $scope.currency.overtimeRate;
				       $scope.wage.overtimeCurrencyId = $scope.currency.overtimeCurrencyId;
				       $scope.wage.overtimeFrequency = $scope.currency.overtimeFrequency; 
				       $scope.wage.overtimeRateMargin = $scope.currency.overtimeRateMargin;
			       }else{
			    	   $scope.wage.baseRate = null;	
				       $scope.wage.baseRateCurrencyId = null;
				       $scope.wage.baseRateFrequency= null; 
				       $scope.wage.overtimeRate = null;
				       $scope.wage.overtimeCurrencyId = null;
				       $scope.wage.overtimeFrequency = null;  
				       
			       }
			       
			       if($scope.wageList.workPeiceList.length > 0){
				       $scope.wage.workPieceRate = $scope.workPeice.workPieceRate;
					   $scope.wage.workPieceCurrency = $scope.workPeice.workPieceCurrency; 
					   $scope.wage.workPieceUnit = $scope.workPeice.workPieceUnit; 
					   $scope.wage.workRateMargin = $scope.currency.workRateMargin;
			       }else{
			    	   $scope.wage.workPieceRate = null;
					   $scope.wage.workPieceCurrency = null; 
					   $scope.wage.workPieceUnit = null;
			       }
			       
	    		   $scope.getData('WageRateController/saveWageRate.json', angular.toJson($scope.wage), 'saveWageRate',angular.element($event.currentTarget));
        	   }
    	   }
       };
       
       $scope.correctHistorySave= function($event){
//    	   if($scope.wageList.workPeiceList.length <= 0 ){
//    		   $scope.Messager('error', 'Error', ' Please Enter Wage Rates/Piece ',true);
//    		   return;
//    	   }
    	   
    	   if($('#wageRateForm').valid()){
    		   if(($scope.wageList.workPeiceList != undefined && $scope.wageList.workPeiceList.length <= 0 ) && ( $scope.wageList.wageRateCurrency != undefined && $scope.wageList.wageRateCurrency.length <= 0 )){
        		   $scope.Messager('error', 'Error', ' Please Enter Base Wage Rate or Work/Piece Rate ',true);
        	   }else{
	    		   $scope.wage.transactionDate = $('#transactionDate').val();
			       $scope.wage.modifiedBy = $cookieStore.get('modifiedBy');
			       $scope.wage.wageRateDetailsId = $scope.transactionModel;
			       
			       if($scope.wageList.wageRateCurrency.length > 0){
				       $scope.wage.baseRate = $scope.currency.baseRate;		       
				       $scope.wage.baseRateCurrencyId = $scope.currency.baseRateCurrencyId;
				       $scope.wage.baseRateFrequency= $scope.currency.baseRateFrequency; 
				       $scope.wage.overtimeRate = $scope.currency.overtimeRate;
				       $scope.wage.overtimeCurrencyId = $scope.currency.overtimeCurrencyId;
				       $scope.wage.overtimeFrequency = $scope.currency.overtimeFrequency; 
			       }else{
			    	   $scope.wage.baseRate = null;	
				       $scope.wage.baseRateCurrencyId = null;
				       $scope.wage.baseRateFrequency= null; 
				       $scope.wage.overtimeRate = null;
				       $scope.wage.overtimeCurrencyId = null;
				       $scope.wage.overtimeFrequency = null;  
				       
			       }
			       
			       if($scope.wageList.workPeiceList.length > 0){
				       $scope.wage.workPieceRate = $scope.workPeice.workPieceRate;
					   $scope.wage.workPieceCurrency = $scope.workPeice.workPieceCurrency; 
					   $scope.wage.workPieceUnit = $scope.workPeice.workPieceUnit; 
			       }else{
			    	   $scope.wage.workPieceRate = null;
					   $scope.wage.workPieceCurrency = null; 
					   $scope.wage.workPieceUnit = null;
			       }
			       /*$scope.wage.baseRate = $scope.wageList.wageRateCurrency[0].baseRate;	
			       $scope.wage.baseRateCurrencyId = $scope.wageList.wageRateCurrency[0].baseRateCurrencyId;
			       $scope.wage.baseRateFrequency= $scope.wageList.wageRateCurrency[0].baseRateFrequency; 
			       $scope.wage.overtimeRate = $scope.wageList.wageRateCurrency[0].overtimeRate;
			       $scope.wage.overtimeCurrencyId = $scope.wageList.wageRateCurrency[0].overtimeCurrencyId;
			       $scope.wage.overtimeFrequency = $scope.wageList.wageRateCurrency[0].overtimeFrequency;  
			       
			       $scope.wage.workPieceRate = $scope.wageList.workPeiceList[0].workPieceRate;
				   $scope.wage.workPieceCurrency = $scope.wageList.workPeiceList[0].workPieceCurrency; 
				   $scope.wage.workPieceUnit = $scope.wageList.workPeiceList[0].workPieceUnit; */
				   
				   $scope.getData('WageRateController/saveWageRate.json', angular.toJson($scope.wage),'correctHistorySave',angular.element($event.currentTarget));
	    	   }
    	   }
	   }
       
       $scope.transactionDatesListChange = function(){
	       $scope.getData('WageRateController/getWageRateById.json', { wageRateDetailsId : $scope.transactionModel, customerId: "" }, 'wageRateList')
	       $('.dropdown-toggle').removeClass('disabled');
	   }
       
       $scope.saveDetails = function(){  
    	   //alert($scope.currency.baseRateCurrencyId+" :: "+$('#baseCurrency option:selected').html());
    	   if($('#wageRateCurrencyForm').valid()){
	    	   $scope.wageList.wageRateCurrency.push({
	       		'baseRate':$scope.currency.baseRate,
	       		'baseRateCurrencyId':$scope.currency.baseRateCurrencyId,
	      		'baseRateCurrency':$('#baseCurrency option:selected').html(),
	       		'baseRateFrequency':$('#baseFrequency option:selected').html(), 
	       		'overtimeRate':$scope.currency.overtimeRate,
	       		'overtimeCurrencyId':$scope.currency.overtimeCurrencyId,
	       		'overtimeCurrency':$('#overtimeCurrency option:selected').html(),
	       		'overtimeFrequency':$('#overtimeFrequency option:selected').html() ,
	       		'baseRateMargin':$scope.currency.baseRateMargin,
	       		'overtimeRateMargin':$scope.currency.overtimeRateMargin
	       	});  
	    	  $('.addrow').hide();
	    	  $('div[id^="myModal"]').modal('hide');
    	   }
        }
       
       $scope.Edit = function($event){    	
       	$scope.currency = $scope.wageList.wageRateCurrency[$($event.target).parent().parent().index()];
       	$scope.popUpSave = false;
       	$scope.popUpUpdate =true;
       }
       
       
       $scope.updateDetails= function($event){  
    	   if($('#wageRateCurrencyForm').valid()){
	    	   $scope.wageList.wageRateCurrency.splice($($event.target).parent().parent().index(),1);    	
	    	   $scope.wageList.wageRateCurrency.push({
	    		    'baseRate':$scope.currency.baseRate,
	          		'baseRateCurrencyId':$scope.currency.baseRateCurrencyId,
	         		'baseRateCurrency':$('#baseCurrency option:selected').html(),
	          		'baseRateFrequency':$('#baseFrequency option:selected').html(), 
	          		'overtimeRate':$scope.currency.overtimeRate,
	          		'overtimeCurrencyId':$scope.currency.overtimeCurrencyId,
	          		'overtimeCurrency':$('#overtimeCurrency option:selected').html(),
	          		'overtimeFrequency':$('#overtimeFrequency option:selected').html(), 
	          		'baseRateMargin':$scope.currency.baseRateMargin,
		       		'overtimeRateMargin':$scope.currency.overtimeRateMargin
	          	}); 
	    	   $('div[id^="myModal"]').modal('hide');
    	   }
       }
       
       
       
       $scope.Delete = function($event){    	
    	   var del = $window.confirm('Are you absolutely sure you want to delete?');
    	   if (del) {
    		   $scope.wageList.wageRateCurrency.splice($($event.target).parent().parent().index(),1);
    		   $('.addrow').show();
    		   //$scope.wageList.wageRateCurrency = [];
    		   //$scope.currency = new Object();
    	   }
       }
       

    $scope.close = function() {
		$('div[id^="myModal"]').modal('hide');
		$scope.popUpSave = true;
		$scope.popUpUpdate = false;
	}

	$scope.plusIconAction = function() {
		$scope.popUpSave = true;
		$scope.popUpUpdate = false;
		$scope.currency = new Object();
		$scope.currency.baseRate = null;
		$scope.currency.baseRateCurrencyId = $scope.defaultCurrency;		
		$scope.currency.overtimeRate = null;
		$scope.currency.overtimeCurrencyId = $scope.defaultCurrency;		
		$scope.currency.overtimeFrequency ="Per Day";
		$scope.currency.baseRateFrequency = "Per Day";
		
		
	}
      
	$scope.plusIconAction1 = function() {
		$scope.workPeice = new Object();
		$scope.workPeice.workPieceRate = null;
		$scope.workPeice.workPieceCurrency = $scope.defaultCurrency;
		$scope.workPeice.workPieceUnit = "Per Day";		
		$scope.popUpSave1 = true;
		$scope.popUpUpdate1 = false;
	}
	
	$scope.Edit1 = function($event){    	
       	$scope.workPeice = $scope.wageList.workPeiceList[$($event.target).parent().parent().index()];
       	$scope.popUpSave1 = false;
       	$scope.popUpUpdate1 =true;
   }

	$scope.saveDetails1 = function(){ 
		 if($('#wageRatePerPieceForm').valid()){
	 	   $scope.wageList.workPeiceList.push({
	    		'workPieceRate':$scope.workPeice.workPieceRate,
	    		'workPieceCurrency':$scope.workPeice.workPieceCurrency,
	    		'workPieceCurrencyName':$('#workPieceCurrency option:selected').html(),
	    		'workPieceUnit':$('#workPieceUnit option:selected').html(),
	    		'workRateMargin':$scope.workPeice.workRateMargin
	    	});  
	 	  $('.addrow1').hide();
	 	  $('div[id^="myModal1"]').modal('hide');
		 }
     }
	
	$scope.Delete1 = function($event){    	
		if ($window.confirm('Are you absolutely sure you want to delete?')) { 	  
 		   $scope.wageList.workPeiceList.splice($($event.target).parent().parent().index(),1);
 		   $('.addrow1').show();
 		 //$scope.workPeice = new Object();
		}
    }
       
	$scope.updateDetails1= function($event){ 
		 if($('#wageRatePerPieceForm').valid()){
			$scope.wageList.workPeiceList = [];
			$scope.wageList.workPeiceList.push({
	    		'workPieceRate':$scope.workPeice.workPieceRate,
	    		'workPieceCurrency':$scope.workPeice.workPieceCurrency,
	    		'workPieceCurrencyName':$('#workPieceCurrency option:selected').html(),
	    		'workPieceUnit':$('#workPieceUnit option:selected').html(),
	    		'workRateMargin':$scope.workPeice.workRateMargin
	    	});  
	 	  $('.addrow1').hide();
	 	 $('div[id^="myModal1"]').modal('hide');
		 }
	}
}]);





