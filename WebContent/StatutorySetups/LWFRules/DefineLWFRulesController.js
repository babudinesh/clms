'use strict';

LWFRulesController.controller("LWFRulesAddCtrl", ['$scope', '$http', '$resource','$location','$filter', '$routeParams','myservice','$cookieStore', function ($scope, $http, $resource, $location, $filter, $routeParams, myservice, $cookieStore) {
	
	$.material.init();
	
	$('#transactionDate').datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown'
	});
	
	$scope.ruleVo = new Object();
	$scope.isValid = true;
	
	
	$scope.list_months = [ 	{"id":"All Months","name":"All Months"},
	                        {"id":"January","name":"January"},
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
	
	$scope.list_frequency=[{"id":"Monthly", "name":"Monthly"},
	                       {"id":"Quarterly", "name":"Quarterly"},
	                       {"id":"Half Yearly", "name":"Half Yearly"},
	                       {"id":"Yearly", "name":"Yearly"}];
	
    if ($routeParams.id > 0) {
       $scope.readOnly = true;
       $scope.datesReadOnly = true;
       $scope.updateBtn = true;
       $scope.saveBtn = false;
       $scope.viewOrUpdateBtn = true;
       $scope.correcttHistoryBtn = false;
       $scope.gridButtons = false;
       $scope.resetBtn = false;
       $scope.transactionList = false;
    } else {
	   $('#transactionDate').val($filter('date')(new Date(),'dd/MM/yyyy'));
       $scope.readOnly = false;
       $scope.datesReadOnly = false;
       $scope.updateBtn = false;
       $scope.saveBtn = true;
       $scope.viewOrUpdateBtn = false;
       $scope.correcttHistoryBtn = false;
       $scope.resetBtn = true;
       $scope.gridButtons = true;
       $scope.transactionList = false;
    };
    
    $scope.updateBtnAction = function (this_obj) {
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
       $scope.gridButtons = true;
      // $scope.ruleVo.lwfRulesInfoId = 0;
       $scope.ruleVo.lwfRulesId = 0;
      // $scope.ruleVo.lwfRulesUniqueId = 0;
       $('#transactionDate').val($filter('date')(new Date(),'dd/MM/yyyy'));
       $('.dropdown-toggle').removeClass('disabled');
    };
	       
	       
	$scope.viewOrEditHistory = function () {
       $scope.readOnly = false;
       $scope.onlyRead = true;
       $scope.datesReadOnly = false;
       $scope.updateBtn = false;
       $scope.saveBtn = false;
       $scope.viewOrUpdateBtn = false;
       $scope.correcttHistoryBtn = true;
       $scope.resetBtn = false;  
       $scope.gridButtons = true;
       $scope.transactionList = true;
       $scope.getData('statutorySetupsController/getTransactionDatesListForLWFRules.json', { companyId: $scope.ruleVo.companyId, customerId: $scope.ruleVo.customerId ,lwfUniqueId: $scope.ruleVo.lwfUniqueId }, 'transactionDatesChange');       
       
       $('.dropdown-toggle').removeClass('disabled');
    };
   
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
        $scope.gridButtons = false;
        $scope.returnToSearchBtn = true;
        $scope.getData('statutorySetupsController/getLWFRuleById.json', { lwfRuleId: $routeParams.id,customerId : ''}, 'lwfList')
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
   };
   

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
    		} else if (type == 'lwfList') {
			    //alert(JSON.stringify(response.data));
               $scope.lwfList = response.data;
               
               if( response.data.lwfRulesList != null && response.data.lwfRulesList[0] != undefined){
            	   $scope.ruleVo = response.data.lwfRulesList[0];
            	   $('#transactionDate').val($filter('date')( response.data.lwfRulesList[0].transactionDate, 'dd/MM/yyyy'));
            	   $scope.ruleVo.transactionDate =  $filter('date')( response.data.lwfRulesList[0].transactionDate, 'dd/MM/yyyy');
                   $scope.transDate1 =  $filter('date')( response.data.lwfRulesList[0].transactionDate, 'dd/MM/yyyy');
                   $scope.lwfList.customerList = response.data.customerList;
            	   $scope.lwfList.companyList = response.data.companyList;
            	   $scope.lwfList.countryList = response.data.countryList;
            	   $scope.lwfList.statesList = response.data.statesList;
                 
               }else{
            	   $scope.ruleVo = new Object();
            	   $scope.ruleVo.transactionDate=  $filter('date')( new Date(), 'dd/MM/yyyy');
            	   $scope.ruleVo.status='Y';
            	   $scope.lwfList.rulesList = [];
            	   if( response.data.customerList != null && response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){
            		   $scope.ruleVo.customerId = response.data.customerList[0].customerId;		                
            		   $scope.customerChange();
	               }else{
	            	   $scope. lwfList.customerList = response.data.customerList;
	                }
               }
               if($scope.buttonArray == undefined || $scope.buttonArray == '')
               $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Labor Welfare Fund (LWF) Rules'}, 'buttonsAction');
           }else if (type == 'customerChange') {
               $scope.lwfList.companyList = response.data;
               if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
            	   $scope.ruleVo.companyId = response.data[0].id;
            	   $scope.companyChange();
                }
           }else if (type == 'companyChange') {
			   //alert(JSON.stringify(response.data));
               $scope.lwfList.countryList = response.data;
               if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
            	   $scope.ruleVo.countryId = response.data[0].id;
            	   $scope.countryChange();
                }
           }else if (type == 'countryChange') {
			   //alert(JSON.stringify(response.data));
        	   $scope.lwfList.statesList = response.data;
        	   if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
            	   $scope.ruleVo.stateId = response.data[0].id;
                }
        	  
           }else if(type == 'validate'){
        	   if(response.data.lwfRulesList != null && response.data.lwfRulesList.length > 0){
        	   		$scope.isValid = false;
        	   		$scope.Messager('error', 'Error', 'LWF Rules already exists for this combination',buttonDisable);
        	   }else
        	   		$scope.isValid = true;	
        	
           } else if (type == 'saveLWFRules') {
               //alert(angular.toJson(response.data));
        	   if(response.data.id > 0){
	            	$scope.Messager('success', 'success', 'LWF Rules Saved Successfully',buttonDisable);
	            	if($scope.saveBtn){
	            		$location.path('/defineLWFRule/'+response.data.id);
	            	}else{
	            		$('#transactionDate').val($filter('date')( $scope.ruleVo.transactionDate, 'dd/MM/yyyy'));
	            	}
        	   }else if(response.data.id == -1){
        		   $scope.Messager('error', 'Error', 'Effective from date should be greater than or equal to company effective date',buttonDisable);
        		   $('#transactionDate').val($filter('date')( $scope.ruleVo.transactionDate, 'dd/MM/yyyy'));
               }else{
            	   $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
            	   $('#transactionDate').val($filter('date')( $scope.ruleVo.transactionDate, 'dd/MM/yyyy'));

               }
           }else if (type == 'transactionDatesChange') {
            	var k = response.data.length;
            	if(response.data.length > 1){
	            	for(var i = response.data.length-1; i > 0; i--){
	            		//alert(i+"  :: "+$scope.dateDiffer(response.data[i-1].name.split('-')[0]));
		            	 if($scope.dateDiffer(response.data[i].name.split('-')[0])){
		            		 k = response.data[i].id;
		            		 break;
		            	 }
	            	}
            	}else{
            		k = response.data[0].id;
            	}
            	$scope.transactionDatesList = response.data;
            	$scope.transactionModel=k;
		        $scope.getData('statutorySetupsController/getLWFRuleById.json', { lwfRuleId: $scope.transactionModel,customerId : ''}, 'lwfList')
            }
       },
       function () { 
    	   $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
    	   $('#transactionDate').val($filter('date')( $scope.ruleVo.transactionDate, 'dd/MM/yyyy'));
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
    
   $scope.getData('statutorySetupsController/getLWFRuleById.json', { lwfRuleId: $routeParams.id, customerId:  $cookieStore.get('customerId') }, 'lwfList')
   	
   $scope.customerChange = function () {
	   if($scope.ruleVo.customerId != undefined && $scope.ruleVo.customerId != null &&  $scope.ruleVo.customerId != ""){
		   $scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.ruleVo.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.ruleVo.companyId  }, 'customerChange');
	   }
   };
   
   $scope.companyChange = function(){
	   if($scope.ruleVo.companyId != undefined &&  $scope.ruleVo.companyId != null &&  $scope.ruleVo.companyId != ""){
	    	$scope.getData('vendorController/getcountryListByCompanyId.json', { companyId: $scope.ruleVo.companyId }, 'companyChange');
   		}
   };
	
   $scope.countryChange = function () {
	   if($scope.ruleVo.countryId != undefined && $scope.ruleVo.countryId != null && $scope.ruleVo.countryId != "")
		   $scope.getData('CommonController/statesListByCountryId.json', { countryId : $scope.ruleVo.countryId }, 'countryChange');
   };   
   
   $scope.validateLWFRule = function(){
	   if($scope.ruleVo.customerId != undefined && $scope.ruleVo.customerId != null &&  $scope.ruleVo.customerId != ""
			   && $scope.ruleVo.companyId != undefined &&  $scope.ruleVo.companyId != null &&  $scope.ruleVo.companyId != ""
			   && $scope.ruleVo.countryId != undefined && $scope.ruleVo.countryId != null && $scope.ruleVo.countryId != ""
			   && $scope.ruleVo.stateId != undefined && $scope.ruleVo.stateId != null && $scope.ruleVo.stateId != "")
	   $scope.getData('statutorySetupsController/getLWFRulesListBySearch.json', { customerId:$scope.ruleVo.customerId, companyId: $scope.ruleVo.companyId, countryId : $scope.ruleVo.countryId, stateId : $scope.ruleVo.stateId  }, 'validate');

   };
   
   $scope.save = function ($event) {
	    if($("#lwfRuleForm").valid()){
        	if(!$scope.isValid){
	    		$scope.Messager('error', 'Error', 'LWF Rules already defined for this combination '); 
	    	}else if($scope.transDate1 != '' && $scope.transDate1 != undefined && new Date(moment($scope.transDate1,'DD/MM/YYYY').format('YYYY-MM-DD')) .getTime() > new Date(moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime()){
	    		   $scope.Messager('error', 'Error', 'Effective From Date Should not be less than previous transaction date');
	        	   $scope.ruleVo.transactionDate =  $scope.transDate1;
	    	}else{
        		$scope.ruleVo.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
        		$scope.ruleVo.lwfRulesList = $scope.lwfList.rulesList;
        		$scope.ruleVo.createdBy = $cookieStore.get('createdBy');
        		$scope.ruleVo.modifiedBy = $cookieStore.get('modifiedBy');
        		//alert(angular.toJson($scope.ruleVo));
        		$scope.getData('statutorySetupsController/saveLWFRules.json', angular.toJson($scope.ruleVo), 'saveLWFRules');
		  }
       }
   };
   
   $scope.correctHistorySave= function($event){
	   if($('#lwfRuleForm').valid()){
		   $scope.ruleVo.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
		   $scope.ruleVo.modifiedBy = $cookieStore.get('modifiedBy');
		   $scope.ruleVo.lwfRulesList = $scope.lwfList.rulesList;
		   //alert(angular.toJson($scope.ruleVo));
		   $scope.getData('statutorySetupsController/saveLWFRules.json', angular.toJson($scope.ruleVo), 'saveLWFRules');
	   }
   };
   
   $scope.transactionDatesListChange = function(){
       $scope.getData('statutorySetupsController/getLWFRuleById.json', { lwfRuleId : ($scope.transactionModel != undefined || $scope.transactionModel != null) ? $scope.transactionModel:'0', customerId: "" }, 'lwfList')
       $('.dropdown-toggle').removeClass('disabled');
   };

   $scope.plusIconAction = function(){  	
	    $scope.rule = new Object();
 		$scope.popUpSave = true;
 		$scope.popUpUpdate = false;
   };
   
   //var regex = new RegExp("([0-9]{1|2|3})(\\,d{3})*\\.?\d{0,2}$");
   //var regex = new RegExp("\\.{0,1}\d{0,2}$");
   var regex=new RegExp("([0-9]{1,3})(\\,?[0-9]{3})*\\.?\d{0,2}$");
  /* alert("1: "+regex.test("1,000"));
   alert("2: "+regex.test("123"));
   alert("44: "+regex.test("1,00,"));
   alert("5: "+regex.test("12,000"));
   alert("6: "+regex.test("123,000,00"));
   alert("3: "+regex.test("12,00,000"));*/
  

   $scope.saveRule = function(){  
	   if($('#lwfSlabForm').valid()){
		   var status = false;
		   //alert($scope.rule.salaryFrom.split(".")[1].length);
		   //alert(parseFloat($scope.rule.salaryTo ));
		   //alert(( $scope.rule.salaryFrom.match(new RegExp('\\.','g') ) || [] ).length);
		   if($scope.rule.salaryFrom != null && $scope.rule.salaryFrom != undefined && $scope.rule.salaryFrom != "" 
			   && (!regex.test($scope.rule.salaryFrom) || 
					   	(regex.test($scope.rule.salaryFrom) && (String($scope.rule.salaryFrom).match(new RegExp('\\.','g')  || [] ) != undefined && String($scope.rule.salaryFrom).match(new RegExp('\\.','g')  || [] ) != null)&&
					   			( String($scope.rule.salaryFrom).match(new RegExp('\\.','g')  || [] ).length > 1 || 
					   				( String($scope.rule.salaryFrom).match(new RegExp('\\.','g')  || [] ).length == 1 && $scope.rule.salaryFrom.split(".")[1].length > 2 )
					   			)
					   	)
			   		)){
			   $scope.Messager('error', 'Error', 'Invalid salary from value '); 
		   }else if($scope.rule.salaryTo != null && $scope.rule.salaryTo != undefined && $scope.rule.salaryTo != "" && 
				   	(!regex.test($scope.rule.salaryTo) || 
				   			(regex.test($scope.rule.salaryTo) && ( String($scope.rule.salaryTo).match(new RegExp('\\.','g')  || [] ) != undefined  && String($scope.rule.salaryTo).match(new RegExp('\\.','g')  || [] ) != null ) &&
				   					( String($scope.rule.salaryTo).match(new RegExp('\\.','g')  || [] ).length > 1 ||
					   				( String($scope.rule.salaryTo).match(new RegExp('\\.','g')  || [] ).length == 1 && $scope.rule.salaryTo.split(".")[1].length > 2 )
				   					)
				   				)
				   	)){
			   $scope.Messager('error', 'Error', 'Invalid salary to value '); 
		   }else if($scope.rule.employeeShare != null && $scope.rule.employeeShare != undefined && $scope.rule.employeeShare != "" && 
				   (!regex.test($scope.rule.employeeShare) || 
						   (regex.test($scope.rule.employeeShare) && ( String($scope.rule.employeeShare).match(new RegExp('\\.','g')  || [] ) != undefined && String($scope.rule.employeeShare).match(new RegExp('\\.','g')  || [] ) != null)  &&
								   	( String($scope.rule.employeeShare).match(new RegExp('\\.','g')  || [] ).length > 1 ||
					   					( String($scope.rule.employeeShare).match(new RegExp('\\.','g')  || [] ).length == 1 && $scope.rule.employeeShare.split(".")[1].length > 2 )
					   				)	
							)
					)){
			   $scope.Messager('error', 'Error', 'Invalid employer share '); 
		   }else if($scope.rule.employerShare != null && $scope.rule.employerShare != undefined && $scope.rule.employerShare != "" && 
				   (!regex.test($scope.rule.employerShare) ||  
						   (regex.test($scope.rule.employeeShare) && ( String($scope.rule.employerShare).match(new RegExp('\\.','g')  || [] ) != undefined  && String($scope.rule.employerShare).match(new RegExp('\\.','g')  || [] ) != undefined) &&
								   ( String($scope.rule.employerShare).match(new RegExp('\\.','g')  || [] ).length > 1 || 
						   					(  String($scope.rule.employerShare).match(new RegExp('\\.','g')  || [] ).length == 1 && $scope.rule.employerShare.split(".")[1].length > 2 )
								   )
							)
					)){
			   $scope.Messager('error', 'Error', 'Invalid employee share'); 
		   }else if($scope.rule.salaryFrom != null && $scope.rule.salaryFrom != undefined && $scope.rule.salaryFrom != "" && 
				    $scope.rule.salaryTo != null && $scope.rule.salaryTo != undefined && $scope.rule.salaryTo != "" 	 &&
				    parseFloat($scope.rule.salaryFrom) > parseFloat($scope.rule.salaryTo ) ){
			   $scope.Messager('error', 'Error', 'Salary To should not be less than Salary From'); 
		   }else {
			   angular.forEach($scope.lwfList.rulesList, function(value, key){	
				   if(value.lwfMonth == $scope.rule.lwfMonth && value.salaryFrom == $scope.rule.salaryFrom && value.salaryTo == $scope.rule.salaryTo && 
				    		  value.employeeShare == $scope.rule.employeeShare && value.employerShare == $scope.rule.employerShare){
				    	  $scope.Messager('error', 'Error', 'Duplicate LWF Rule Exists',true); 
				    	  status = true;			    		
				    }
				         
			   });	
			   
			   //alert($scope.rule);
			   if( !status && $scope.rule != undefined && $scope.rule != '' && $scope.rule != null
					   &&  ( ($scope.rule.lwfMonth != undefined && $scope.rule.lwfMonth != '' && $scope.rule.lwfMonth != null)
								||  ($scope.rule.salaryFrom != undefined && $scope.rule.salaryFrom != '' && $scope.rule.salaryFrom != null)
								||  ($scope.rule.salaryTo != undefined && $scope.rule.salaryTo != '' && $scope.rule.salaryTo != null)
								||  ($scope.rule.employeeShare != undefined && $scope.rule.employeeShare != '' && $scope.rule.employeeShare != null)
								||  ($scope.rule.employerShare != undefined && $scope.rule.employerShare != '' && $scope.rule.employerShare != null) )
									
			   		){
				   $scope.lwfList.rulesList.push({
					   		'lwfMonth':($scope.rule.lwfMonth != null && $scope.rule.lwfMonth != undefined && $scope.rule.lwfMonth != "")? $('#lwfMonth option:selected').html() : "",  	
					   		'salaryFrom':$scope.rule.salaryFrom,
					   		'salaryTo':$scope.rule.salaryTo,
					   		'employeeShare':$scope.rule.employeeShare,
					   		'employerShare':$scope.rule.employerShare,
					   		'lwfRulesInfoId': 0
				   });   
				   $('div[id^="myModal"]').modal('hide');
				   $scope.popUpSave = true;
				   $scope.popUpUpdate =false;
			   }else if(status){
				   $scope.Messager('error', 'Error', 'Duplicate LWF Rule Exists',true); 
			   }
		   }
	   }
   };
   
 
   $scope.EditRule = function(index){  
	  // alert(JSON.stringify($($event.target).parent().parent().index()));
	   $scope.ruleIndex = index;
	   var data =  $scope.lwfList.rulesList[$scope.ruleIndex];
	   $scope.rule = data;
	   
	   $scope.lwfMonth = data.lwfMonth;
	   $scope.salaryFrom = data.salaryFrom;
	   $scope.salaryTo  = data.salaryTo;
	   $scope.employeeShare = data.employeeShare;
	   $scope.employerShare = data.employerShare;
	   
	   /*$scope.rule.lwfMonth = data.lwfMonth;
	   $scope.rule.salaryFrom = data.salaryFrom;
	   $scope.rule.salaryTo  = data.salaryTo;
	   $scope.rule.employeeShare = data.employeeShare;
	   $scope.rule.employerShare = data.employerShare;
	   $scope.rule.lwfRuleInfoId = data.lwfRuleInfoId;*/
	  // $scope.rule.index = event;
	   
	   $scope.popUpSave = false;
	   $scope.popUpUpdate =true;
   };
		          
   // To update identification table         
   $scope.updateRule = function(index){  
	   if($('#lwfSlabForm').valid()){
		   var status = false;
		   if($scope.rule.salaryFrom != null && $scope.rule.salaryFrom != undefined && $scope.rule.salaryFrom != "" 
			   && (!regex.test($scope.rule.salaryFrom) || 
					   	(regex.test($scope.rule.salaryFrom) && (String($scope.rule.salaryFrom).match(new RegExp('\\.','g')  || [] ) != undefined && String($scope.rule.salaryFrom).match(new RegExp('\\.','g')  || [] ) != null)&&
					   			(String($scope.rule.salaryFrom).match(new RegExp('\\.','g')  || [] ).length > 1 || 
					   				( String($scope.rule.salaryFrom).match(new RegExp('\\.','g')  || [] ).length == 1 && $scope.rule.salaryFrom.split(".")[1].length > 2 )
					   			)
					   	)
			   		)){
			   $scope.Messager('error', 'Error', 'Invalid salary from value '); 
		   }else if($scope.rule.salaryTo != null && $scope.rule.salaryTo != undefined && $scope.rule.salaryTo != "" && 
				   	(!regex.test($scope.rule.salaryTo) || 
				   			(regex.test($scope.rule.salaryTo) && (String($scope.rule.salaryTo).match(new RegExp('\\.','g')  || [] ) != undefined  && String($scope.rule.salaryTo).match(new RegExp('\\.','g')  || [] ) != null ) &&
				   					(String($scope.rule.salaryTo).match(new RegExp('\\.','g')  || [] ).length > 1 ||
				   						(String( $scope.rule.salaryTo).match(new RegExp('\\.','g')  || [] ).length == 1 && $scope.rule.salaryTo.split(".")[1].length > 2 )
				   					)
				   				)
				   	)){
			   $scope.Messager('error', 'Error', 'Invalid salary to value '); 
		   }else if($scope.rule.employeeShare != null && $scope.rule.employeeShare != undefined && $scope.rule.employeeShare != "" && 
				   (!regex.test($scope.rule.employeeShare) || 
						   (regex.test($scope.rule.employeeShare) && (String( $scope.rule.employeeShare).match(new RegExp('\\.','g')  || [] ) != undefined && String($scope.rule.employeeShare).match(new RegExp('\\.','g')  || [] ) != null)  &&
								   	( String($scope.rule.employeeShare).match(new RegExp('\\.','g')  || [] ).length > 1 ||
					   					( String($scope.rule.employeeShare).match(new RegExp('\\.','g')  || [] ).length == 1 && $scope.rule.employeeShare.split(".")[1].length > 2 )
					   				)	
							)
					)){
			   $scope.Messager('error', 'Error', 'Invalid employer share '); 
		   }else if($scope.rule.employerShare != null && $scope.rule.employerShare != undefined && $scope.rule.employerShare != "" && 
				   (!regex.test($scope.rule.employerShare) ||  
						   (regex.test($scope.rule.employeeShare) && (String($scope.rule.employerShare).match(new RegExp('\\.','g')  || [] ) != undefined  && String($scope.rule.employerShare).match(new RegExp('\\.','g')  || [] ) != undefined) &&
								   ( String($scope.rule.employerShare).match(new RegExp('\\.','g')  || [] ).length > 1 || 
						   					( String($scope.rule.employerShare).match(new RegExp('\\.','g')  || [] ).length == 1 && $scope.rule.employerShare.split(".")[1].length > 2 )
								   )
							)
					)){
			   $scope.Messager('error', 'Error', 'Invalid employee share'); 
		   }else if($scope.rule.salaryFrom != null && $scope.rule.salaryFrom != undefined && $scope.rule.salaryFrom != "" && 
				    $scope.rule.salaryTo != null && $scope.rule.salaryTo != undefined && $scope.rule.salaryTo != "" 	 &&
				    parseFloat($scope.rule.salaryFrom) > parseFloat($scope.rule.salaryTo ) ){
			   $scope.Messager('error', 'Error', 'Salary To should not be less than Salary From'); 
		   }else {
			   $scope.lwfList.rulesList.splice($scope.ruleIndex,1);
			   angular.forEach($scope.lwfList.rulesList, function(value, key){	
				   if( value.lwfMonth == $scope.rule.lwfMonth && value.salaryFrom == $scope.rule.salaryFrom && value.salaryTo == $scope.rule.salaryTo && 
				    		  		value.employeeShare == $scope.rule.employeeShare && value.employerShare == $scope.rule.employerShare){
				    	  $scope.Messager('error', 'Error', 'Duplicate LWF Rule Exists',true); 
				    	  status = true;			    		
				    }
			   });	
			  if(status){
				 $scope.Messager('error', 'Error', 'Duplicate LWF Rule Exists',true); 
			  }else if(!status &&  $scope.rule != undefined && $scope.rule != '' && $scope.rule != null
							&&  ( ($scope.rule.lwfMonth != undefined && $scope.rule.lwfMonth != '' && $scope.rule.lwfMonth != null)
							||  ($scope.rule.salaryFrom != undefined && $scope.rule.salaryFrom != '' && $scope.rule.salaryFrom != null)
							||  ($scope.rule.salaryTo != undefined && $scope.rule.salaryTo != '' && $scope.rule.salaryTo != null)
							||  ($scope.rule.employeeShare != undefined && $scope.rule.employeeShare != '' && $scope.rule.employeeShare != null)
							||  ($scope.rule.employerShare != undefined && $scope.rule.employerShare != '' && $scope.rule.employerShare != null) )
					){
				   
					$scope.lwfList.rulesList.push({
				   			'lwfMonth':($scope.rule.lwfMonth != null && $scope.rule.lwfMonth != undefined && $scope.rule.lwfMonth != "")? $('#lwfMonth option:selected').html() : "",  	
					   		'salaryFrom':$scope.rule.salaryFrom,
					   		'salaryTo':$scope.rule.salaryTo,
					   		'employeeShare':$scope.rule.employeeShare,
					   		'employerShare':$scope.rule.employerShare,
					   		'lwfRulesInfoId':(!$scope.saveBtn && $scope.rule.lwfRuleInfoId != undefined && $scope.rule.lwfRuleInfoId != null) ? $scope.rule.lwfRuleInfoId : 0
					});  
					
			   		$('#myModal').modal('hide');
			   		$scope.popUpSave = true;
			       	$scope.popUpUpdate =false;
			 }
		   }
	   }
   };
		  
   $scope.DeleteRule = function(index){ 
	   var del = false;
	   if($scope.updateBtn){
		   del  = false;
	   }else{
		   del = confirm('Are you sure you want to delete this LWF Rule ? ');
		   if (del) {
			   $scope.lwfList.rulesList.splice(index,1);
		   }
	   }
	   
	   $scope.popUpSave = true;
       $scope.popUpUpdate = false;
    	  
   };
	
   $scope.validatePrice = function() {
	 
   }
   
   $scope.fun_close = function(){
	  if($scope.popUpUpdate){
		   $scope.rule.lwfMonth =  $scope.lwfMonth;
		   $scope.rule.salaryFrom = $scope.salaryFrom;
		   $scope.rule.salaryTo  =  $scope.salaryTo;
		   $scope.rule.employeeShare = $scope.employeeShare;
		   $scope.rule.employerShare = $scope.employerShare ;
	  }else{
		  $scope.rule = new Object();
	  }
	   $('#myModal').modal('hide');
   }
    
}]);


