'use strict';



//vendorRegisterTypesCtrl.controller("vendorRegisterTypesCtrl", ['$scope', '$http', '$resource', '$location','$filter','$cookieStore','$compile', function ($scope, $http, $resource, $location, $filter, $cookieStore) {
vendorRegisterTypesCtrl.controller("vendorRegisterTypesCtrl", ['$window','$scope', '$http', '$resource', '$location','$filter','$cookieStore','$compile', '$routeParams', function ($window,$scope, $http, $resource, $location, $filter, $cookieStore,$compile,$routeParams) {
	var regex=new RegExp("([0-9]{1,3})(\\,?[0-9]{3})*\\.?\d{0,2}$");
	var dt_attendanceReport1; 
	$scope.WorkerMoneyDetails = [];
	$scope.amount = new Object();
	$scope.amount.advanceInstallments = [];
	$scope.amountVo = new Object();
	 $('#amountTakenDate,#repaymentAmountDate').datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown'
	    	  
	    });
	 
	 

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
     
    $scope.getData = function (url, data, type) {
    
        var req = {
            method: 'POST',
            url: ROOTURL+url,
            headers: {
                'Content-Type': 'application/json'
            },
            data:data
        }
        $http(req).then(function (response) {    
            if( type == "gridData" ){
            	//alert(angular.toJson(response.data.advanceList));
                if(response.data != undefined && response.data != "" ){
                	$scope.currencyList= response.data.currencyList;
                	
                	if(response.data.profileList != undefined && response.data.profileList.length > 0 && response.data.profileList[0] != null){
                		$scope.currencyId = response.data.profileList[0].defaultCurrencyId;
                	}
                	
                	if( response.data.advanceList.length >0 ){
	            		//$scope.WorkerMoneyDetails = response.data.advanceList;
	            		$scope.amountVo.companyId = response.data.advanceList[0].companyId;
	                	$scope.amountVo.customerId = response.data.advanceList[0].customerId;
	                	$scope.amountVo.vendorId = response.data.advanceList[0].vendorId;
	                	$scope.amountVo.workerId = response.data.advanceList[0].workerId;
	                	
	            	   // $scope.amountVo.currency = response.data.advanceList[0].currencyId;
	                	$scope.amountVo.vendorCode = response.data.advanceList[0].vendorCode;
	                	$scope.amountVo.vendorName = response.data.advanceList[0].vendorName;
	                	$scope.amountVo.workerCode = response.data.advanceList[0].workerCode;
	                	$scope.amountVo.workerName = response.data.advanceList[0].workerName; 
	                	
	                	if(( response.data.advanceList[0].amountTakenDate != null && response.data.advanceList[0].amountTakenDate != undefined && response.data.advanceList[0].amountTakenDate != "")
	                			&& ( response.data.advanceList[0].advanceAmountTaken != null && response.data.advanceList[0].advanceAmountTaken != undefined && response.data.advanceList[0].advanceAmountTaken != "" && response.data.advanceList[0].advanceAmountTaken > 0)
	                	){
	                		$scope.WorkerMoneyDetails = response.data.advanceList;
	                	}else{
	                		$scope.WorkerMoneyDetails = [];
	                	}
	                	
	                }          
                }
            }else if(type == "save"){
            	if(response.data.id > 0){
            		$('div[id^="myModal"]').modal('hide');
            		$scope.Messager('success', 'Success', 'Worker Advance Registered Successfully');
            		$scope.getData("vendorRegisterTypesController/getAdvanceAmountById.json", {workerId:response.data.id} , "gridData" );
            		
            	}else{
            		$scope.Messager('error', 'Error', 'Technical issue occurred');
            	}
            }
        },
        function () {
        	$scope.Messager('error', 'Error', 'Technical issues occurred. Please try again...');
        });
    }  
	
	
    $scope.getData("vendorRegisterTypesController/getAdvanceAmountById.json", {workerId:$routeParams.id} , "gridData" );
	
	
	
	$scope.saveAdvanceDetails = function(){	
		
		if($('#advanceForm').valid()){
			$scope.amount.companyId = $scope.amountVo.companyId;
        	$scope.amount.customerId = $scope.amountVo.customerId;
        	$scope.amount.vendorId = $scope.amountVo.vendorId;
        	$scope.amount.workerId = $scope.amountVo.workerId;
        	
        	$scope.amount.createdBy = $cookieStore.get("createdBy");
			$scope.amount.modifiedBy = $cookieStore.get("modifiedBy");
			$scope.amount.amountTakenDate = $('#amountTakenDate').val(); 
			
        	$scope.getData("vendorRegisterTypesController/saveOrUpdateVendorRegisterTypes.json",angular.toJson($scope.amount) , "save" );
        	//$('div[id^="myModal"]').modal('hide');
		}
	    	/*$scope.WorkerMoneyDetails.push({    		
	    		'amountTakenDate':moment($('#Date').val(),'DD/MM/YYYY').format('YYYY-MM-DD'),
	    		'advanceAmountTaken':$scope.amount.advanceAmountTaken,
	    		'currency':$scope.amount.currency,
	    		'purpose':$scope.amount.purpose,
	    		'noOfInstallmentsAllowed':$scope.amount.noOfInstallmentsAllowed,
	    		'remarks':$scope.amount.remarks,    		
	    		'currencyName':$('#currency option:selected').html() != 'Select' ? $('#currency option:selected').html() : '',
	    		'companyId':$scope.amount.companyId,
	    		'customerId':$scope.amount.customerId,  
	    		'vendorId':$scope.amount.vendorId,  
	    		'workerId':$scope.amount.workerId,
	    		'registerType' : $cookieStore.get("registerType")
	    	}); */  
	    	
	};
    
    $scope.Edit = function($event){ 
    	$scope.trIndex = $($event.target).parent().parent().index();
    	$scope.amount = $scope.WorkerMoneyDetails[$($event.target).parent().parent().index()];
    	$scope.amount.advanceInstallments = $scope.WorkerMoneyDetails[$($event.target).parent().parent().index()].advanceInstallments;
    	$scope.popUpSave = false;
    	$scope.popUpUpdate =true;
    };
    
    
    $scope.updateAdvanceDetails= function($event){
    	if($('#advanceForm').valid()){
			$scope.amount.companyId = $scope.amountVo.companyId;
        	$scope.amount.customerId = $scope.amountVo.customerId;
        	$scope.amount.vendorId = $scope.amountVo.vendorId;
        	$scope.amount.workerId = $scope.amountVo.workerId;
        	
        	$scope.amount.vendorCode = $scope.amountVo.vendorCode;
        	$scope.amount.vendorName = $scope.amountVo.vendorName;
        	$scope.amount.workerCode = $scope.amountVo.workerCode;
        	$scope.amount.workerName = $scope.amountVo.workerName; 
			
        	$scope.getData("vendorRegisterTypesController/saveOrUpdateVendorRegisterTypes.json",angular.toJson($scope.amount) , "save" );
        //	$('div[id^="myModal"]').modal('hide');
    	}
    	/*$scope.WorkerMoneyDetails.splice($scope.trIndex,1);    	
    	$scope.WorkerMoneyDetails.push({
    		'amountTakenDate':$scope.amount.amountTakenDate,
    		'advanceAmountTaken':$scope.amount.advanceAmountTaken,
    		'currency':$scope.amount.currency,
    		'purpose':$scope.amount.purpose,
    		'noOfInstallmentsAllowed':$scope.amount.noOfInstallmentsAllowed,
    		'remarks':$scope.amount.remarks,    		
    		'currencyName':$('#currency option:selected').html() != 'Select' ? $('#currency option:selected').html() : ''  ,
			'companyId':$scope.amount.companyId,
    		'customerId':$scope.amount.customerId,  
    		'vendorId':$scope.amount.vendorId,  
    		'workerId':$scope.amount.workerId,
    		'registerType' : $cookieStore.get("registerType")
    	});    	*/
    	
    };
    
	$scope.saveInstallmentDetails = function(){	
		
		if($('#installmentForm').valid()){
			$scope.amount.advanceInstallments.push({    		
				'repaymentAmountDate':$('#repaymentAmountDate').val(),
	    		'repaymentAmount':$scope.installment.repaymentAmount,
	    		'currencyId':$scope.installment.currencyId
	    	});
			$('#myModal1').hide();
		}
	};
    
    $scope.EditInstallement= function($event){ 
    	$scope.trIndex = $($event.target).parent().parent().index();
    	var data = $scope.amount.advanceInstallments[$($event.target).parent().parent().index()];
    	$scope.repaymentAmount = data.repaymentAmount;
    	$scope.repaymentAmountDate = data.repaymentAmountDate;
    	$scope.currencyId = data.currencyId;
    	
    	$scope.amount = data ;
    	$scope.popUpSave1 = false;
    	$scope.popUpUpdate1 =true;
    };
    
    
    $scope.updateInstallmentDetails= function($event){
    	if($('#installmentForm').valid()){
	    	$scope.amount.advanceInstallments.push({    		
	    		'repaymentAmountDate':$('#repaymentAmountDate').val(),
	    		'repaymentAmount':$scope.installment.repaymentAmount,
	    		'currencyId':$scope.installment.currencyId
	    	});
	    	$('#myModal1').hide();
    	}
    };
    
    $scope.DeleteInstallment = function($event){    	
    	var r = confirm("Do you want to delete the "+$scope.amount.advanceInstallments[$($event.target).parent().parent().index()].repaymentAmountDate);    	
    	if(r){
    		$scope.amount.advanceInstallments.splice($($event.target).parent().parent().index(),1);
    	}
     };
	
    $scope.plusIconAction = function(){
    	$scope.popUpSave = true;
    	$scope.popUpUpdate =false;
    	$scope.amount = new Object();
    	$scope.amount.currencyId = $scope.currencyId;
    	$scope.amount.advanceInstallments = [];
    };

    $scope.installmentPlusAction = function(){
	   $scope.popUpSave1 = true;
	   $scope.popUpUpdate1 =false;
	   $scope.installment = new Object();
	   $scope.installment.currencyId = $scope.currencyId;
    };
    
    $scope.closeInstallment = function(){
		  if($scope.popUpUpdate1){
			  $scope.installment.repaymentAmount = $scope.repaymentAmount;
		      $scope.installment.repaymentAmountDate = $scope.repaymentAmountDate;
		      $scope.installment.currencyId = $scope.currencyId;
		  }else{
			  $scope.installment = new Object();
		  }
		   $('#myModal1').modal('hide');
	  }
  
}]);



