
workerGroupController.controller("WorkerGroupAddCtrl", ['$scope','$rootScope', '$http', '$resource','$location','$filter', '$routeParams','myservice','$cookieStore', function ($scope,$rootScope, $http, $resource, $location, $filter, $routeParams, myservice, $cookieStore) {
	$.material.init();
	
	 $('#transactionDate').datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown'
	    	  
	 });
    $scope.group = new Object();
    
    $scope.groupList = [];
    $scope.group.status = "Y";
	$scope.group.transactionDate = $filter('date')(new Date(),'dd/MM/yyyy');
	   
    $scope.list_status = [{"id":"Y","name":"Active"},
                          {"id":"N","name":"Inactive"}];
    
    if($routeParams.id > 0){
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
    }else{
    	$('#transactionDate').val($filter('date')(new Date(),'dd/MM/yyyy'));
    	$scope.readOnly = false;
	    $scope.datesReadOnly = false;
	    $scope.updateBtn = false;
	    $scope.saveBtn = true;
	    $scope.viewOrUpdateBtn = false;
	    $scope.correctHistoryBtn = false;
	    $scope.resetBtn = true;
	    $scope.transactionList = false;
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
        $scope.viewOrUpdateBtn = false;
        $scope.correctHistoryBtn = false;
        $scope.resetBtn = false;
        $scope.cancelBtn = true;
        $scope.transactionList = false;
        $scope.gridButtons = true;
        $scope.group.workerGroupId = 0;
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
       $scope.gridButtons = true;
       $scope.transactionList = true;
       $scope.getData('statutorySetupsController/getTransactionDatesListForWorkerGroup.json', { companyId: $scope.group.companyId, customerId: $scope.group.customerId ,uniqueId: $scope.group.uniqueId }, 'transactionDatesChange');       
       $('.dropdown-toggle').removeClass('disabled');
   }
   
   $scope.cancelBtnAction = function(){
    	$scope.readOnly = true;
        $scope.datesReadOnly = true;
        $scope.updateBtn = true;
        $scope.saveBtn = false;
        $scope.viewOrUpdateBtn = true;
        $scope.correctHistoryBtn = false;
        $scope.resetBtn = false;
        $scope.transactionList = false;
        $scope.cancelBtn = false;
        $scope.returnToSearchBtn = true;
        $scope.gridButtons = false;
        $scope.getData('statutorySetupsController/getWorkerGroupById.json', { workerGroupId: $routeParams.id,customerId : ''}, 'workerGroupList');
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
    		} else if (type == 'workerGroupList') {
			   //alert(JSON.stringify(response.data.customerList));
        	   $scope.groupList.customerList = response.data.customerList;
        	   if(response.data.workerGroupVo != null && response.data.workerGroupVo != undefined && response.data.workerGroupVo != "" ){
            	   $scope.group = response.data.workerGroupVo[0];
            	   $scope.group.transactionDate = $filter('date')(response.data.workerGroupVo[0].transactionDate,'dd/MM/yyyy');
            	   $scope.transDate1 =  $filter('date')( response.data.workerGroupVo[0].transactionDate, 'dd/MM/yyyy');
            	   $scope.groupList.companyList = response.data.companyList;
            	   $scope.groupList.countryList = response.data.countryList;
            	   $scope.groupList.wageRateList = response.data.wageRateList;
                   $scope.groupList.shiftPatternList = response.data.shiftPatternList;
                   $scope.groupList.holidayCalendarList = response.data.holidayCalendarList;
                   $scope.groupList.timeRuleList = response.data.timeRuleList;
                   $scope.groupList.overTimeRuleList = response.data.overTimeRuleList;
        	   }
        	   
        	   if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){
        		   $scope.group.customerId=response.data.customerList[0].customerId;		                
        		   $scope.customerChange();
	            
	           }
        	// for button views
			  	if($scope.buttonArray == undefined || $scope.buttonArray == '')
        	   $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Worker Group'}, 'buttonsAction');
           }else if (type == 'customerChange') {
               $scope.groupList.companyList = response.data;
               if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
                	$scope.group.companyId = response.data[0].id;
                	$scope.companyChange();
                }
               
           }else if (type == 'companyChange') {
			   //alert(JSON.stringify(response.data));
			   $scope.groupList.countryList = response.data.countryList;
			   $scope.groupList.wageRateList = response.data.wageRateList;
               $scope.groupList.shiftPatternList = response.data.shiftPatternList;
               $scope.groupList.holidayCalendarList = response.data.holidayCalendarList;
               $scope.groupList.timeRuleList = response.data.timeRuleList;
               $scope.groupList.overTimeRuleList = response.data.overTimeRuleList;
               
               if( response.data.countryList[0] != undefined && response.data.countryList[0] != "" && response.data.countryList.length == 1 ){
   	               $scope.group.countryId = response.data.countryList[0].id;
   	           }
               
           }else if(type == 'validateCode'){
        	    //alert(response.data);
        	   	$scope.validCode= new Object();
        	   	$scope.validCode = response.data;
        	   	if(response.data > 0){
        	   		$scope.Messager('error', 'Error', 'Group ID already exists.');
        	   	}
        	
	       }else if (type == 'saveWorkerGroup') {
              // alert(angular.toJson(response.data)+", "+$scope.correctHistoryBtn);
        	   if(response.data.id > 0 && $scope.correctHistoryBtn){
	            	$scope.Messager('success', 'success', 'Worker Group Saved Successfully',buttonDisable);
	            	$scope.group.transactionDate =  $filter('date')( $('#transactionDate').val(), 'dd/MM/yyyy');
        	   }else if(response.data.id > 0 && $scope.saveBtn){
	            	$scope.Messager('success', 'success', 'Worker Group Saved Successfully',buttonDisable);
	            	$location.path('/addWorkerGroup/'+response.data.id);
        	   }else if(response.data.id == -1){
        		   $scope.Messager('error', 'Error', 'Transaction Date should be greater than or equal to company transaction date',buttonDisable);
        		   $scope.group.transactionDate =  $filter('date')( $('#transactionDate').val(), 'dd/MM/yyyy');
        	   }else{
            	   $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
            	   $scope.group.transactionDate =  $filter('date')( $('#transactionDate').val(), 'dd/MM/yyyy');
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
		        $scope.getData('statutorySetupsController/getWorkerGroupById.json', { workerGroupId: $scope.transactionModel,customerId : ''}, 'workerGroupList')
            }
       },
       function () { 
    	   $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
    	   $scope.group.transactionDate =  $filter('date')( $('#transactionDate').val(), 'dd/MM/yyyy');
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
    
   $scope.getData('statutorySetupsController/getWorkerGroupById.json', { workerGroupId: $routeParams.id,customerId : $cookieStore.get('customerId')}, 'workerGroupList')
   	
   $scope.customerChange = function () {
	   if($scope.group.customerId != undefined || $scope.group.customerId != null){
		   $scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.group.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.group.companyId  }, 'customerChange');
	   }
   };
   
   $scope.companyChange = function(){
	   if($scope.group.companyId != undefined || $scope.group.companyId != null){
	    	$scope.getData('statutorySetupsController/getDropdownsForWorkerGroup.json', { customerId:$scope.group.customerId,companyId: $scope.group.companyId }, 'companyChange');
   		}
   };
  
   $scope.checkBudgetCode = function(){
    	if($scope.group.customerId != null || $scope.group.customerId != undefined || $scope.group.customerId != ''
    			&& ($scope.group.companyId != null || $scope.group.companyId != undefined || $scope.group.companyId != '')
    				    			&& ($scope.group.groupCode != null || $scope.group.groupCode != undefined || $scope.group.groupCode != '')){
    		$scope.getData('statutorySetupsController/validateWorkerGroupCode.json',{ customerId : 0, companyId: 0, groupCode:$scope.group.groupCode },'validateCode')

    	}
    };
    
   $scope.save = function ($event) {
	    if($("#workerGroupForm").valid()){
        	if($scope.validCode > 0){
	    		$scope.Messager('error', 'Error', 'Budget ID already exists'); 
	    	}else if($scope.transDate1 != '' && $scope.transDate1 != undefined && new Date(moment($scope.transDate1,'DD/MM/YYYY').format('YYYY-MM-DD')) .getTime() > new Date(moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime()){
	    		   $scope.Messager('error', 'Error', 'Transaction Date Should not be less than previous transaction date');
	        	   $scope.group.transactionDate =  $scope.transDate1;
	    	}else{
        		$scope.group.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
        		$scope.group.createdBy = $cookieStore.get('createdBy');
        		$scope.group.modifiedBy = $cookieStore.get('modifiedBy');
        		$scope.getData('statutorySetupsController/saveWorkerGroup.json', angular.toJson($scope.group), 'saveWorkerGroup');
		  }
       }
   };
   
   $scope.transactionDatesListChange = function(){
       $scope.getData('statutorySetupsController/getWorkerGroupById.json', { workerGroupId : ($scope.transactionModel != undefined || $scope.transactionModel != null) ? $scope.transactionModel:'0', customerId: "" }, 'budgetList')
       $('.dropdown-toggle').removeClass('disabled');
   }
	
		 

   
   
   
}]);


