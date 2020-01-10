//var budgetController = angular.module("myApp.Budget", ['ngCookies']);

budgetController.controller("BudgetAddCtrl", ['$scope','$rootScope', '$http', '$resource','$location','$filter', '$routeParams','myservice','$cookieStore', function ($scope,$rootScope, $http, $resource, $location, $filter, $routeParams, myservice, $cookieStore) {
	$.material.init();
	
	 $('#transactionDate').datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown'
	    	  
	 });
    $scope.budget = new Object();
    $scope.budget.budgetSkillsList = [];
    $scope.budgetList = [];
    
    
    $scope.budget.budgetStatus = "Y";
	$scope.budget.considerYear = null;
	$scope.budget.approvalStatus = "Initiated";
	$scope.budget.transactionDate = $filter('date')(new Date(),'dd/MM/yyyy');
	   
    $scope.list_years = $rootScope.getYears;  
    
    $scope.list_budgetTypes = [{"id":"Regular Planned","name":"Regular Planned"},
                               {"id":"Adhoc","name":"Adhoc"},
                               {"id":"Addition","name":"Addition"},
                               {"id":"Deduction","name":"Deduction"}];
    
    $scope.list_budgetFrequencies = [{"id":"Annual","name":"Annual"},
                                     {"id":"Monthly","name":"Monthly"}];
    
    $scope.list_budgetStatus = [{"id":"Y","name":"Active"},
                                {"id":"N","name":"Inactive"}];
    
    $scope.list_approvalStatus = [{"id":"Initiated","name":"Initiated"},
                                  {"id":"Approved","name":"Approved"},
                                  {"id":"Pending For Approval","name":"Pending For Approval"},
                                  {"id":"Sent For Correction","name":"Sent For Correction"},
                                  {"id":"Rejected","name":"Initiated"} ];
    
    $scope.list_workSkill = [{"id":"Skilled","name" : "Skilled" },
		                       {"id":"Semi Skilled","name" : "Semi Skilled" },
		                       {"id":"High Skilled","name" : "High Skilled" },
		                       {"id":"Special Skilled","name" : "Special Skilled" },
		                       {"id":"UnSkilled","name" : "UnSkilled" }];
    
   
    if($routeParams.id > 0){
	    $scope.readOnly = true;
        $scope.datesReadOnly = true;
        $scope.updateBtn = true;
        $scope.saveBtn = false;
        $scope.approvalBtn  = false;
        $scope.viewOrUpdateBtn = true;
        $scope.correctHistoryBtn = false;
        $scope.resetBtn = false;
        $scope.transactionList = false;
        $scope.gridButtons = false;
        $scope.cancelBtn = false;
    }else{
    	$('#transactionDate').val($filter('date')(new Date(),'dd/MM/yyyy'));
    	$scope.readOnly = false;
	    $scope.datesReadOnly = false;
	    $scope.updateBtn = false;
	    $scope.saveBtn = true;
	    $scope.approvalBtn  = true;
	    $scope.viewOrUpdateBtn = false;
	    $scope.correctHistoryBtn = false;
	    $scope.resetBtn = true;
	    $scope.transactionList = false;
	    $scope.budget.budgetStatus = "Y";
	    $scope.budget.approvalStatus = "Initiated";
	    $scope.gridButtons = true;
	    $scope.cancelBtn = false;
    	   
    }
   
    $scope.updateBtnAction = function (this_obj) {
	    $scope.isValid = 1;
        $scope.readOnly = false;
        $scope.onlyRead = true;
        $scope.datesReadOnly = false;
        $scope.updateBtn = false;
        $scope.saveBtn = true;
        $scope.approvalBtn  = true;
        $scope.viewOrUpdateBtn = false;
        $scope.correctHistoryBtn = false;
        $scope.resetBtn = false;
        $scope.cancelBtn = true;
        $scope.transactionList = false;
        $scope.gridButtons = true;
        $scope.budget.budgetDetailsId = 0;
        $('#transactionDate').val($filter('date')(new Date(),'dd/MM/yyyy'));
        $('.dropdown-toggle').removeClass('disabled');
     }
   
   
   $scope.viewOrEditHistory = function () {
	   $scope.isValid = 1;
       $scope.readOnly = false;
       $scope.onlyRead = true;
       $scope.datesReadOnly = false;
       $scope.updateBtn = false;
       $scope.saveBtn = false;
       $scope.approvalBtn  = false;
       $scope.viewOrUpdateBtn = false;
       $scope.correctHistoryBtn = true;
       $scope.resetBtn = false;      
       $scope.gridButtons = true;
       $scope.transactionList = true;
       $scope.getData('statutorySetupsController/getTransactionDatesListForBudget.json', { companyId: $scope.budget.companyId, customerId: $scope.budget.customerId ,budgetId: $scope.budget.budgetId }, 'transactionDatesChange');       
       $('.dropdown-toggle').removeClass('disabled');
   }
   
   $scope.cancelBtnAction = function(){
    	$scope.readOnly = true;
        $scope.datesReadOnly = true;
        $scope.updateBtn = true;
        $scope.saveBtn = false;
        $scope.approvalBtn  = false;
        $scope.viewOrUpdateBtn = true;
        $scope.correctHistoryBtn = false;
        $scope.resetBtn = false;
        $scope.transactionList = false;
        $scope.cancelBtn = false;
        $scope.returnToSearchBtn = true;
        $scope.gridButtons = false;
        $scope.getData('statutorySetupsController/getBudgetDetailsById.json', { budgetDetailsId: $routeParams.id,customerId : ''}, 'budgetList')
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
    		} else if (type == 'budgetList') {
			   //alert(JSON.stringify(response.data));
        	   $scope.budgetList.customerList = response.data.customerList;
        	   if(response.data.budgetVo != null && response.data.budgetVo != undefined && response.data.budgetVo != "" ){
        		   $scope.budget = response.data.budgetVo[0];
            	   $scope.budget.transactionDate = $filter('date')(response.data.budgetVo[0].transactionDate,'dd/MM/yyyy');
            	   $scope.transDate1 =  $filter('date')( response.data.budgetVo[0].transactionDate, 'dd/MM/yyyy');
            	   $scope.budgetList.companyList = response.data.companyList;
            	   $scope.budgetList.countryList = response.data.countryList;
            	   $scope.budgetList.locationsList = response.data.locationsList;
                   $scope.budgetList.departmentsList = response.data.departmentsList;
               }
        	   
        	   if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){
        		   $scope.budget.customerId=response.data.customerList[0].customerId;		                
        		   $scope.customerChange();
	            
	           }
        	// for button views
			  	if($scope.buttonArray == undefined || $scope.buttonArray == '')
        	   $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Budget'}, 'buttonsAction');
           }else if (type == 'customerChange') {
               $scope.budgetList.companyList = response.data;
               if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
                	$scope.budget.companyId = response.data[0].id;
                	$scope.companyChange();
                }
               
           }else if (type == 'companyChange') {
			   //alert(JSON.stringify(response.data));
			   $scope.budgetList.countryList = response.data.countryList;
        	   $scope.budgetList.locationsList = response.data.locationsList;
               $scope.budgetList.departmentsList = response.data.departmentsList;
               
               if(response.data.defaultCurrency != undefined && response.data.defaultCurrency != null){
            	   $scope.defaultCurrency = response.data.defaultCurrency[0].defaultCurrencyName;
            	   $scope.defaultCurrencyId = response.data.defaultCurrency[0].defaultCurrencyId;
               }else{
            	   $scope.defaultCurrency = null;
            	   $scope.defaultCurrencyId = null;
            	   
               }
               if( response.data.countryList[0] != undefined && response.data.countryList[0] != "" && response.data.countryList.length == 1 ){
   	               $scope.budget.countryId = response.data.countryList[0].id;
   	           }
               
               if( response.data.locationsList[0] != undefined && response.data.locationsList[0] != "" && response.data.locationsList.length == 1 ){
            	   $scope.budget.locationId = response.data.locationsList[0].locationId;
    	       }
               
               if( response.data.departmentsList[0] != undefined && response.data.departmentsList[0] != "" && response.data.departmentsList.length == 1 ){
    	           $scope.budget.departmentId = response.data.departmentsList[0].id;
    	       }
           }else if(type == 'validateCode'){
        	    //alert(response.data);
        	   	$scope.validCode= new Object();
        	   	$scope.validCode = response.data;
        	   	if(response.data > 0){
        	   		$scope.Messager('error', 'Error', 'Budget ID already exists.');
        	   	}
        	
	       }else if(type == 'validateYear'){
       	    //alert(response.data);
	       	   	$scope.validYear= new Object();
	       	   	$scope.validYear = response.data;
		       	if(response.data >0){
		       		$scope.Messager('error', 'Error', 'Budget is already defined for this year');
		 	   	}
	       }else if (type == 'saveBudget') {
               //alert(angular.toJson(response.data));
        	   if(response.data.id > 0 && $scope.correctHistoryBtn){
        		   $scope.Messager('success', 'success', 'Budget Saved Successfully',buttonDisable);
        		   $scope.budget.transactionDate =  $filter('date')( $('#transactionDate').val(), 'dd/MM/yyyy');
        	   }else if(response.data.id > 0 && $scope.saveBtn){
	            	$scope.Messager('success', 'success', 'Budget Saved Successfully',buttonDisable);
	            	$location.path('/addBudget/'+response.data.id);
     	   }else if(response.data.id == -1){
        		   $scope.Messager('error', 'Error', 'Transaction Date should be greater than or equal to company transaction date',buttonDisable);
               }else{
            	   $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
               }
           }else if (type == 'transactionDatesChange') {
            	var k = 0;
            	if(response.data.length > 1){
	            	for(var i = response.data.length; i>0; i--){
		            	 if($scope.dateDiffer(response.data[i-1].name.split('-')[0])){
		            		 k = response.data[i-1].id;
		            		 break;
		            	 }
	            	}
            	}else{
            		k = response.data[0].id;
            	}
            	$scope.transactionDatesList = response.data;
            	$scope.transactionModel=k;
		        $scope.getData('statutorySetupsController/getBudgetDetailsById.json', { budgetDetailsId: $scope.transactionModel,customerId : ''}, 'budgetList')
            }
       },
       function () { 
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
    
   $scope.getData('statutorySetupsController/getBudgetDetailsById.json', { budgetDetailsId: $routeParams.id,customerId : $cookieStore.get('customerId')}, 'budgetList')
   	
   $scope.customerChange = function () {
	   if($scope.budget.customerId != undefined || $scope.budget.customerId != null){
		   $scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.budget.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.budget.companyId  }, 'customerChange');
	   }
   };
   
   $scope.companyChange = function(){
	   if($scope.budget.companyId != undefined || $scope.budget.companyId != null){
	    	$scope.getData('statutorySetupsController/getCountryLocationAndDepartmentDropdown.json', { customerId:$scope.budget.customerId,companyId: $scope.budget.companyId }, 'companyChange');
   		}
   };
  
   $scope.checkBudgetCode = function(){
    	if($scope.budget.customerId != null || $scope.budget.customerId != undefined || $scope.budget.customerId != ''
    			&& ($scope.budget.companyId != null || $scope.budget.companyId != undefined || $scope.budget.companyId != '')
    				    			&& ($scope.budget.budgetCode != null || $scope.budget.budgetCode != undefined || $scope.budget.budgetCode != '')
    								&& ($scope.budget.countryId != null || $scope.budget.countryId != undefined || $scope.budget.countryId != '')){
    		$scope.getData('statutorySetupsController/validateBudgetCodeAndYear.json',{ customerId : 0, companyId: 0, budgetCode:$scope.budget.budgetCode, budgetYear :0 },'validateCode')

    	}
    };
    
    $scope.checkBudgetYear= function(){
    	if($scope.budget.customerId != null || $scope.budget.customerId != undefined || $scope.budget.customerId != ''
    			&& ($scope.budget.companyId != null || $scope.budget.companyId != undefined || $scope.budget.companyId != '')
    				    			&& ($scope.budget.budgetYear != null || $scope.budget.budgetYear != undefined || $scope.budget.budgetYear != '')
    								&& ($scope.budget.countryId != null || $scope.budget.countryId != undefined || $scope.budget.countryId != '')){
    		$scope.getData('statutorySetupsController/validateBudgetCodeAndYear.json',{ customerId : $scope.budget.customerId, companyId: $scope.budget.companyId,  budgetYear :$scope.budget.budgetYear },'validateYear')

    	}
    };
   
    
   $scope.save = function ($event) {
	    if($("#budgetForm").valid()){
        	if($scope.validCode > 0){
	    		$scope.Messager('error', 'Error', 'Budget ID already exists'); 
	    	}else if($scope.transDate1 != '' && $scope.transDate1 != undefined && new Date(moment($scope.transDate1,'DD/MM/YYYY').format('YYYY-MM-DD')) .getTime() > new Date(moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime()){
	    		   $scope.Messager('error', 'Error', 'Transaction Date Should not be less than previous transaction date');
	        	   $scope.budget.transactionDate =  $scope.transDate1;
	    	}else{
        		$scope.budget.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
        		$scope.budget.createdBy = $cookieStore.get('createdBy');
        		$scope.budget.modifiedBy = $cookieStore.get('modifiedBy');
        		$scope.getData('statutorySetupsController/saveBudget.json', angular.toJson($scope.budget), 'saveBudget');
		  }
       }
   };
   
   $scope.sendForApproval = function(){
	   if($("#budgetForm").valid()){
       	if($scope.isValid > 0){
	    		$scope.Messager('error', 'Error', 'Budget ID already exists'); 
	    	}else if($scope.transDate1 != '' && $scope.transDate1 != undefined && new Date(moment($scope.transDate1,'DD/MM/YYYY').format('YYYY-MM-DD')) .getTime() > new Date(moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime()){
	    		   $scope.Messager('error', 'Error', 'Transaction Date Should not be less than previous transaction date');
	        	   $scope.budget.transactionDate =  $scope.transDate1;
	    	}else{
	       		$scope.budget.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
	       		$scope.budget.createdBy = $cookieStore.get('createdBy');
	       		$scope.budget.modifiedBy = $cookieStore.get('modifiedBy');
	       		$scope.budget.approvalStatus = "Pending For Approval"
	       		$scope.getData('statutorySetupsController/saveBudget.json', angular.toJson($scope.budget), 'saveBudget');
		  }
      }
   }
   
   /*$scope.correctHistorySave= function($event){
	   if($('#budgetForm').valid()){
		   $scope.budget.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
		   $scope.budget.modifiedBy = $cookieStore.get('modifiedBy');
		   $scope.budget.createdBy = $cookieStore.get('createdBy');
		 //  alert(angular.toJson($scope.location));
		   $scope.getData('statutorySetupsController/saveBudget.json', angular.toJson($scope.budget), 'saveBudget');
	   }
   }*/

   $scope.transactionDatesListChange = function(){
       //alert("--" +$scope.transactionModel);
       $scope.getData('statutorySetupsController/getBudgetDetailsById.json', { budgetDetailsId : ($scope.transactionModel != undefined || $scope.transactionModel != null) ? $scope.transactionModel:'0', customerId: "" }, 'budgetList')
       $('.dropdown-toggle').removeClass('disabled');
   }
	
		 
   $scope.plusIconAction = function(){
	   $scope.skill = new Object();
	   $scope.disableSkill = false;
	   $scope.skill.currencyName = $scope.defaultCurrency;
	   $scope.skill.currencyId = $scope.defaultCurrencyId ;
	   $scope.popUpSave = true;
	   $scope.popUpUpdate = false;
   }
   
   $scope.saveChanges = function(){
	   var status = false;
	   if($('#skillForm').valid()){
		   angular.forEach($scope.budget.budgetSkillsList, function(value, key){	
		      if(value.skillType == $scope.skill.skillType){
		    	  $scope.Messager('error', 'Error', 'This skill type is already available. Please select other skill.',true); 
		    	  status = true;			    		
		      }
	       });
	    	   
		   if(status){
			   $scope.Messager('error', 'Error', 'This skill type is already exists. Please select other skill.',true);
		   }else if(!status && $scope.skill.skillType != null && $scope.skill.skillType != undefined && $scope.skill.skillType != "" ){
			  $scope.budget.budgetSkillsList.push({	    	
		    		'skillType':$('#skillType option:selected').html(),
		    		'headCount': $scope.skill.headCount,
		    		'amount' : $scope.skill.amount != null && $scope.skill.amount != undefined && $scope.skill.amount != "" ? $scope.skill.amount : 0,
		    		'currencyName': $scope.skill.currencyName,
		    		'currencyId' : $scope.defaultCurrencyId
		    	});
			  	$('div[id^="skillsList"]').modal('hide');
		   }
	   }
   };
   
   $scope.data1 = new Object();
   
   $scope.Edit = function($event){
	    $scope.disableSkill = true;;
	   	$scope.skill_index = $($event.target).parent().parent().index();
	   	var data = $scope.budget.budgetSkillsList[$($event.target).parent().parent().index()];	  
	   	$scope.skill = data;	
	   	$scope.data1.headCount = data.headCount;
	   	$scope.data1.amount = data.amount;
	   	$scope.data1.skillType = data.skillType;
	   	$scope.popUpSave = false;
	   	$scope.popUpUpdate = true;
   }
   
   $scope.updateChanges = function(){
	   if($('#skillForm').valid()){
		   $scope.budget.budgetSkillsList.splice($scope.skill_index,1); 
		   $scope.budget.budgetSkillsList.push({	    	
	    		'skillType':$('#skillType option:selected').html(),
	    		'headCount': $scope.skill.headCount,
	    		'amount' : $scope.skill.amount,
	    		'currencyName': $scope.skill.currencyName,
	    		'currencyId' : $scope.defaultCurrencyId
	    	}); 
		   $('div[id^="skillsList"]').modal('hide');
	   }
	   $scope.popUpSave = true;
       $scope.popUpUpdate =false;
   }
   
   $scope.Delete = function($event){
	   var del = confirm("Are you sure you want to delete the skill type  '"+$scope.budget.budgetSkillsList[$($event.target).parent().parent().index()].skillType +"' ?");
	   if(del){
		   $scope.budget.budgetSkillsList.splice($($event.target).parent().parent().index(),1);
	   }
	   
   };
   
   $scope.close_skills =function(){
	   $scope.skill = new Object();
	   $scope.skill.currencyName = $scope.defaultCurrency;
	   $scope.skill.headCount = $scope.data1.headCount;
	   $scope.skill.amount = $scope.data1.amount;
	   $scope.skill.skillType =  $scope.data1.skillType;
	   /*if($('#skillForm').valid()){
		   if($scope.popUpUpdate){
			   $scope.skill.headCount = $scope.data1.headCount;
			   $scope.skill.amount = $scope.data1.amount;
			   $('div[id^="skillsList"]').modal('hide');
		   }else if($scope.popUpSave){
			   $scope.skill = new Object();
			   $scope.skill.currencyName = $scope.defaultCurrency;
		   }
	   }*/
   };
   
   $scope.close_skillsPopup =function(){
	   $('div[id^="skillsList"]').modal('hide');
   };
   
}]);


