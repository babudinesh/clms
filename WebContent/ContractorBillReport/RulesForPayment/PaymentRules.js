var paymentRulesController = angular.module("myApp.PaymentRules",[]);

paymentRulesController.controller("PaymentRuleCtrl", ['$scope', '$http', '$resource','$location','$filter', '$routeParams','myservice','$cookieStore', function ($scope, $http, $resource, $location, $filter, $routeParams, myservice, $cookieStore) {
	
	$scope.list_periodTypes = [{"id":"Day","name" : "Day(s)" },
								{"id":"Week","name" : "Week(s)" },
								{"id":"Month","name" : "Month(s)" },
								{"id":"Payroll Period","name" : "Payroll Period" }
							  ];
	
	$scope.complianceList = [];
	
	
	$.material.init();
   
	$('#transactionDate').datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown'
	    	  
	    });
	$scope.rule = new Object();
	
   
   $scope.rule.transactionDate = $filter('date')(new Date(),'dd/MM/yyyy');
   $scope.readOnly = false;
   $scope.datesReadOnly = false;
   $scope.updateBtn = false;
   $scope.saveBtn = true;
   $scope.viewOrUpdateBtn = false;
   $scope.correcttHistoryBtn = false;
   $scope.resetBtn = true;
   $scope.transactionList = false;
   
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
       $scope.rule.rulesForPaymentId = 0;
       $('#transactionDate').val($filter('date')(new Date(),'dd/MM/yyyy'));
       $('.dropdown-toggle').removeClass('disabled');
   }
   
   
   $scope.viewOrEditHistory = function () {
       $scope.readOnly = false;
       $scope.onlyRead = true;
       $scope.datesReadOnly = false;
       $scope.updateBtn = false;
       $scope.saveBtn = false;
       $scope.viewOrUpdateBtn = false;
       $scope.correctHistoryBtn = true;
       $scope.resetBtn = false;      
       $scope.transactionList = true;
       $scope.getData('rulesForPaymentController/getTransactionListForRulesForPayment.json', { companyId: $scope.rule.companyId, customerId: $scope.rule.customerId ,uniqueId: $scope.rule.uniqueId }, 'transactionDatesChange');       
       
       $('.dropdown-toggle').removeClass('disabled');
   }
   
   $scope.cancelBtnAction = function(){
    	
        if($scope.saveBtn || $scope.correctHistoryBtn ){
        	$scope.readOnly = true;
            $scope.datesReadOnly = true;
            $scope.updateBtn = true;
            $scope.saveBtn = false;
            $scope.viewOrUpdateBtn = true;
            $scope.correctHistoryBtn = false;
            $scope.resetBtn = false;
            $scope.transactionList = false;
            $scope.cancelBtn = false;
            $scope.getData('rulesForPaymentController/getCompanyChangeData.json', { customerId: $scope.rule.customerId,companyId : $scope.rule.companyId, rulesForPaymentId : $scope.rule.rulesForPaymentId}, 'companyChange')
        }else{
        	$scope.readOnly = false;
            $scope.datesReadOnly = false;
            $scope.updateBtn = false;
            $scope.saveBtn = true;
            $scope.viewOrUpdateBtn = false;
            $scope.correctHistoryBtn = false;
            $scope.resetBtn = true;
            $scope.transactionList = false;
            $scope.cancelBtn = false;
            $scope.rule = new Object();
        	
            $('#transactionDate').val($filter('date')(new Date(),'dd/MM/yyyy'));
        }
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
   
   $scope.ruleList = [];

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
   		   } else if (type == 'masterData') {  
	       		$scope.ruleList.customerList = response.data.customerList;
	       		if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
	                $scope.rule.customerId =response.data.customerList[0].customerId;		                
	                $scope.customerChange();
	            }
	       		// for button views
	       		if($scope.buttonArray == undefined || $scope.buttonArray == '')
	       		$scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Rules For Payment Release'}, 'buttonsAction');
            }else if (type == 'customerChange') {
					//alert(JSON.stringify(response.data));
               $scope.ruleList.companyList = response.data;
               if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	                	$scope.rule.companyId = response.data[0].id;
	                	$scope.companyChange();
	                }
               
           }else if (type == 'companyChange') {
			   //alert(JSON.stringify(response.data));
        	   $scope.ruleList.countryList = response.data.countryList;
               if(response.data.ruleVo != null && response.data.ruleVo != undefined && response.data.ruleVo.length == 1){
            	   $scope.rule = response.data.ruleVo[0];
            	   $scope.rule.transactionDate = $filter('date')(response.data.ruleVo[0].transactionDate,'dd/MM/yyyy');
            	   $scope.transDate1 = $filter('date')(response.data.ruleVo[0].transactionDate,'dd/MM/yyyy');
            	   if(response.data.ruleVo[0].verificationList != null && response.data.ruleVo[0].verificationList != undefined && response.data.ruleVo[0].verificationList.length > 0){
            		   $scope.complianceList = response.data.ruleVo[0].verificationList;
            	   }else{
            		   $scope.complianceList = response.data.complianceTypesList;
            	   }
            	   
            	   $scope.readOnly = true;
            	   $scope.datesReadOnly = true;
            	   $scope.updateBtn = true;
            	   $scope.saveBtn = false;
            	   $scope.viewOrUpdateBtn = true;
            	   $scope.correcttHistoryBtn = false;
            	   $scope.resetBtn = false;
            	   $scope.cancelBtn = true;
            	   $scope.transactionList = false;
            	   
               }else{
            	   $scope.complianceList = response.data.complianceTypesList;
                   $scope.ruleList.countryList = response.data.countryList;
                   if( response.data.countryList[0] != undefined && response.data.countryList[0] != "" && response.data.countryList.length == 1 ){
                    	$scope.rule.countryId = response.data.countryList[0].id;
                    }
               }
           }else if (type == 'savePayment') {
               //alert(angular.toJson(response.data));
        	   if(response.data.id > 0){
        		   $scope.Messager('success', 'success', 'Rules for payment release Saved Successfully',buttonDisable);
        		   $scope.getData('rulesForPaymentController/getCompanyChangeData.json', { customerId: $scope.rule.customerId,companyId: $scope.rule.companyId,rulesForPaymentId: response.data.id }, 'companyChange');
	            	//$location.path('/paymentRules);
        	   }else if(response.data.id == -1){
        		   $scope.Messager('error', 'Error', 'Transaction Date should be greater than or equal to company transaction date',buttonDisable);
        		   $scope.rule.transactionDate =  $filter('date')($('#transactionDate').val(),'dd/MM/yyyy');
        	   }else{
            	   $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
            	   $scope.rule.transactionDate =  $filter('date')($('#transactionDate').val(),'dd/MM/yyyy');
               }
           }else if (type == 'datesChange') {
			   //alert(JSON.stringify(response.data));
               if(response.data.ruleVo != null && response.data.ruleVo != undefined && response.data.ruleVo.length == 1){
            	   $scope.rule = response.data.ruleVo[0];
            	   $scope.rule.transactionDate = $filter('date')(response.data.ruleVo[0].transactionDate,'dd/MM/yyyy');
            	   $scope.transDate1 = $filter('date')(response.data.ruleVo[0].transactionDate,'dd/MM/yyyy');
            	   if(response.data.ruleVo[0].verificationList != null && response.data.ruleVo[0].verificationList != undefined && response.data.ruleVo[0].verificationList.length > 0){
            		   $scope.complianceList = response.data.ruleVo[0].verificationList;
            	   }else{
            		   $scope.complianceList = [];
            	   }
            	   
            	  
        		   $scope.readOnly = false;
        		   $scope.onlyRead = false;
            	   $scope.datesReadOnly = false;
            	   $scope.updateBtn = false;
            	   $scope.saveBtn = false;
            	   $scope.viewOrUpdateBtn = false;
            	   $scope.correctHistoryBtn = true;
            	   $scope.resetBtn = false;
            	   $scope.cancelBtn = true;
            	   $scope.transactionList = true;
            	   
               }
           }else if (type == 'transactionDatesChange') {
            	//alert(JSON.stringify(response.data));
            	var k = response.data.length;
            	if(response.data.length > 1){
	            	for(var i = response.data.length-1; i> 0;i--){
		            	 if($scope.dateDiffer(response.data[i-1].name.split('-')[0])){
		            		 k = response.data[i].id;
		            		 break;
		            	 }
	            	}                   
            	}else{
            		k = response.data[0].id;
            	}
            	$scope.transactionDatesList = response.data;
            	$scope.transactionModel=k;
		        $scope.getData('rulesForPaymentController/getCompanyChangeData.json', { rulesForPaymentId: $scope.transactionModel,customerId : 0, companyId : 0}, 'datesChange')

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
    
	$scope.getData("shiftsController/getShiftsGridData.json", { customerId: $cookieStore.get('customerId') }, "masterData");		    

   	
   $scope.customerChange = function () {
	   if($scope.rule.customerId != undefined || $scope.rule.customerId != null){
		   $scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.rule.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.rule.companyId  }, 'customerChange');
	   }
   };
   
   $scope.companyChange = function(){
	   if($scope.rule.companyId != undefined || $scope.rule.companyId != null){
	    	$scope.getData('rulesForPaymentController/getCompanyChangeData.json', { customerId: $scope.rule.customerId,companyId: $scope.rule.companyId, rulesForPaymentId : 0 }, 'companyChange');
   		}
   };
   
	
   
   $scope.save = function ($event) {
	    if($("#paymentForm").valid()){
	    	//alert($scope.rule.beforeVerification + $scope.rule.afterVerification);
        	if($scope.transDate1 != '' && $scope.transDate1 != undefined && new Date(moment($scope.transDate1,'DD/MM/YYYY').format('YYYY-MM-DD')) .getTime() > new Date(moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime()){
	    		   $scope.Messager('error', 'Error', 'Transaction Date Should not be less than previous transaction date.');
	        	   $scope.rule.transactionDate =  $scope.transDate1;
	    	}else if($scope.rule.liabilitiesPaid > 100 ){
	    		 $scope.Messager('error', 'Error', 'In Payment Release Verification Rules, Release Percentage value Should not be greater than 100.');
	    	}else if(parseInt($scope.rule.beforeVerification) + parseInt($scope.rule.afterVerification) != 100 ){
	    		 $scope.Messager('error', 'Error', 'In Payment Release Verification Rules, Else case sum of percentages must be equal to 100.');
	    	}else{
        		$scope.rule.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
        		$scope.rule.createdBy = $cookieStore.get('createdBy');
        		$scope.rule.modifiedBy = $cookieStore.get('modifiedBy');
        		$scope.rule.verificationList = $scope.complianceList;
        		//alert(angular.toJson($scope.rule));
        		$scope.getData('rulesForPaymentController/saveRulesForPayment.json', angular.toJson($scope.rule), 'savePayment');
	    	}
	    }
   };
   
   $scope.correctHistorySave= function($event){
	   if($('#paymentForm').valid()){
		   if($scope.rule.liabilitiesPaid > 100 ){
	    		 $scope.Messager('error', 'Error', 'In Payment Release Verification Rules, Release Percentage value Should not be greater than 100.');
	    	}else if(parseInt($scope.rule.beforeVerification) + parseInt($scope.rule.afterVerification) != 100 ){
	    		 $scope.Messager('error', 'Error', 'In Payment Release Verification Rules, Else case sum of percentages must be equal to 100.');
	    	}else{
			   $scope.plant.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
			   $scope.plant.modifiedBy = $cookieStore.get('modifiedBy');
			   $scope.rule.verificationList = $scope.complianceList;
			 //  alert(angular.toJson($scope.location));
			   $scope.getData('rulesForPaymentController/saveRulesForPayment.json', angular.toJson($scope.rule), 'savePayment');
	    	}
	   }
   }

   $scope.transactionDatesListChange = function(){
      // alert("--" +$scope.transactionModel);
       $scope.getData('rulesForPaymentController/getCompanyChangeData.json', { rulesForPaymentId : ($scope.transactionModel != undefined && $scope.transactionModel != null) ? $scope.transactionModel:'0', customerId: 0, companyId : 0 }, 'datesChange');
       $('.dropdown-toggle').removeClass('disabled');
   }
   
}]);




