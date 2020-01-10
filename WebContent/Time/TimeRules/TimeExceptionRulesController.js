'use strict';

timeController.controller('timeExceptionCtrl', ['$location','$scope','$http', '$resource','$routeParams','$filter','myservice','$cookieStore','$window', function ($location,$scope,$http, $resource, $routeParams,$filter,myservice,$cookieStore,$window) {
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
    };
		
	$('#transactionDate').datepicker({
      changeMonth: true,
      changeYear: true,
      dateFormat:"dd/mm/yy",
      showAnim: 'slideDown'
    	  
    });
	    
	$scope.list_periodTypes = [{"id":"Day","name" : "Day(s)" },
								{"id":"Week","name" : "Week(s)" },
								{"id":"Month","name" : "Month(s)" },
								{"id":"Payroll Period","name" : "Payroll Period" }
							  ];
	$scope.exceptionRule = new Object();
	$scope.exceptionRule.exceptionsList = [];
	$scope.transactionDate = $filter('date')(new Date(),"dd/MM/yyyy");
	
	if($routeParams.id > 0){
		$scope.exceptionRule.timeRulesInfoId = $routeParams.id;
		$scope.exceptionRule.customerId = $cookieStore.get("timeRuleCustomerId");
	    $scope.exceptionRule.companyId = $cookieStore.get("timeRuleCompanyId");
	    $scope.exceptionRule.countryId = $cookieStore.get("timeRuleCountryId");
	    $scope.exceptionRule.timeRuleId = $cookieStore.get("timeRuleId");
	    //$scope.exceptionRule.timeRulesInfoId = $cookieStore.get("timeRulesInfoId");
	    $scope.customerName = $cookieStore.get("timeRuleCustomerName");
	    $scope.companyName = $cookieStore.get("timeRuleCompanyName");
	    $scope.countryName = $cookieStore.get("timeRuleCountryName");
	    $scope.timeRuleCode = $cookieStore.get("timeRuleCode");
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
		$scope.readOnly = true;
	    $scope.updateBtn = false;
	    $scope.saveBtn = true;
	    $scope.correctHistoryBtn = false;
 	    $scope.resetBtn = true;
 	    $scope.cancelBtn = false;
 	    $scope.viewOrUpdateBtn = false;
 	    $scope.transactionList = false;
 	    $scope.gridButtons=false;
		$scope.Messager('error', 'Error', 'Please define Time rules first then only you are allowed to define the exception rules.',true);
	}
	
    $scope.updateBtnAction = function (this_obj) {
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
        $scope.exceptionRule.transactionDate = $filter('date')(new Date(),"dd/MM/yyyy");
    };
	    
	    

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
        $scope.getData('timeRulesController/getTransactionDatesListForTimeException.json', { exceptionUniqueId: $scope.exceptionRule.exceptionUniqueId != undefined && $scope.exceptionRule.exceptionUniqueId != '' ? $scope.exceptionRule.exceptionUniqueId : '0', customerId : $scope.exceptionRule.customerId, companyId : $scope.exceptionRule.companyId }, 'getTransactionDates');      
        
        $('.dropdown-toggle').removeClass('disabled');
    };
    
    
	    
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
         
         $scope.getData('timeRulesController/getTimeExceptionById.json', {timeRulesInfoId:$scope.timeRule.timeRulesInfoId != null ? $scope.timeRule.timeRulesInfoId : '0'}, 'getDetailsById');
         
    };
	    
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
	    	} else if(type== 'getDetailsById'){
	    		if(response.data.exceptionsList != undefined && response.data.exceptionsList != null && response.data.exceptionsList.length > 0){
	    			$scope.exceptionList = response.data.exceptionsList;
	    		}else{
	    			$scope.exceptionList = [];
	    		}
	    		
	    		if(response.data.exceptionRulesList != null && response.data.exceptionRulesList != undefined && response.data.exceptionRulesList.length  > 0){
             	    
             	   // alert(angular.toJson(response.data.exceptionRulesList[0]));
	            	$scope.exceptionRule = response.data.exceptionRulesList[0];
	            	$scope.transactionDate = $filter('date')( response.data.exceptionRulesList[0].transactionDate, 'dd/MM/yyyy');
	            	
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
	            	$scope.readOnly = false;
	        	    $scope.updateBtn = false;
	        	    $scope.saveBtn = true;
	        	    $scope.correctHistoryBtn = false;
	         	    $scope.resetBtn = true;
	         	    $scope.cancelBtn = false;
	         	    $scope.viewOrUpdateBtn = false;
	         	    $scope.transactionList = false;
	         	    $scope.gridButtons=true;
	            }
	    		// for button views
	    		if($scope.buttonArray == undefined || $scope.buttonArray == '')
	    		$scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Time Rule'}, 'buttonsAction');
            }else if(type == 'exceptionRules'){	            
	    		if(response.data.id > 0){
	            	$scope.exceptionRule.timeRulesInfoId = response.data.id;
	            	$scope.Messager('success', 'success', 'Exception Rules Saved Successfully',buttonDisable);
	            	//$location.path('/exceptionRules/'+response.data.id);
	                $scope.getData('timeRulesController/getTimeExceptionById.json', {customerId :$scope.exceptionRule.customerId, companyId :$scope.exceptionRule.companyId, countryId :$scope.exceptionRule.countryId,timeRulesInfoId: $routeParams.id}, 'getDetailsById') 
            	}else{
            		$scope.Messager('error', 'Error', 'Technical Issue.. Please Try After Some Time',buttonDisable);
            	}
            	
            }else if(type=='getTransactionDates'){
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
            	$scope.exceptionRulesId = response.data[0].id;
            	$scope.transactionModel= $scope.exceptionRule.exceptionRulesId;
            	$scope.getData('timeRulesController/getTimeExceptionById.json', {customerId :$scope.exceptionRule.customerId, companyId :$scope.exceptionRule.companyId, countryId :$scope.exceptionRule.countryId,exceptionRulesId:0, timeRulesInfoId:$scope.exceptionRule.timeRulesInfoId}, 'getDetailsById');
            }
        },function () {
        	  $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
        	           
        });
    };  
    
    $scope.dateDiffer = function(date1){
 	   var cmpDate = new Date( moment(date1,'DD/MM/YYYY').format('YYYY-MM-DD HH:MM:SS'));

 	   if(cmpDate.getTime() <= (new Date()).getTime()){
    			return true;
 	   }else{
 		   return false;       	
 	   }
    };
	    
    $scope.getData('timeRulesController/getTimeExceptionById.json', {customerId :$scope.exceptionRule.customerId, companyId :$scope.exceptionRule.companyId, countryId :$scope.exceptionRule.countryId,timeRulesInfoId: $routeParams.id}, 'getDetailsById') 
    
	$scope.saveExceptionRules =function($event){	
    	 if($('#timeExceptionForm').valid() ){    		
    		 //alert(angular.toJson($scope.exceptionRule));
	    	$scope.exceptionRule.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
	    	$scope.exceptionRule.createdBy = $cookieStore.get('createdBy'); 
	 	    $scope.exceptionRule.modifiedBy = $cookieStore.get('modifiedBy');
	 	    if($scope.saveBtn){
	 	    	$scope.exceptionRule.exceptionRulesId=0;
	 	    }
	 	    $scope.getData('timeRulesController/saveTimeException.json', angular.toJson($scope.exceptionRule), 'exceptionRules',angular.element($event.currentTarget)); 
    	 }
    };
    
    $scope.transactionDatesListChange = function(){    
        $scope.getData('timeRulesController/getTimeExceptionById.json', {customerId :$scope.exceptionRule.customerId, companyId :$scope.exceptionRule.companyId, countryId :$scope.exceptionRule.countryId,timeRulesInfoId:$scope.exceptionRule.timeRulesInfoId ,exceptionRulesId:$scope.transactionModel}, 'getDetailsById')
        $('.dropdown-toggle').removeClass('disabled');
    };
	    
    $scope.plusIconAction = function(){
    	if($scope.exceptionList == undefined || $scope.exceptionList == []){
    		$scope.Messager('error', 'Error', 'Please define exceptions first then only you are allowed to add.');
    	}else{
    		$scope.popUpSave = true;
        	$scope.popUpUpdate =false;	 
        	$scope.exception= "";
    	}
    };    
	    
    $scope.saveException = function($event){
    	var status = false;
    	if($('#exceptionForm').valid()){
    		angular.forEach($scope.exceptionRule.exceptionsList, function(value, key){	
  		      if(value.exceptionId == $scope.exception.id){
  		    	  status = true;			    		
  		      }
  	       });
    		if(status){
    			$scope.Messager('error', 'Error', 'This Exception code already added.',true);
    		}else if(!status && $scope.exception != undefined && $scope.exception != null && $scope.exception != ""){
		    	$scope.exceptionRule.exceptionsList.push({	    		   		
		    		'exceptionCode':$scope.exception.name,
		    		'exceptionName':$scope.exception.desc,	    		
					'exceptionId':$scope.exception.id
		    	}); 
		    	$('div[id^="exception"]').modal('hide');
		    }
    	}
    };
	    
    $scope.deleteException = function($event){	    	   	
    	var del = confirm("Are you sure you want to delete? ");
    	if(del){
    		$scope.exceptionRule.exceptionsList.splice($($event.target).parent().parent().index(),1);
    	}
    };

}]);


