'use strict';



vendorRegisterTypesCtrl.controller("vendorDamageRegisterCtrl", ['$window','$scope', '$http', '$resource', '$location','$filter','$cookieStore','$compile', '$routeParams', function ($window,$scope, $http, $resource, $location, $filter, $cookieStore,$compile,$routeParams) {
	var regex=new RegExp("([0-9]{1,3})(\\,?[0-9]{3})*\\.?\d{0,2}$");
	
	$scope.recovery = new Object();
	$scope.damageVo = new Object();
	$scope.damageVo.recoveryList = [];
	$scope.damage = new Object();
	
	 $('#dateOfDamage,#recoveredDate').datepicker({
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
            if( type == 'gridData' ){
            	//alert(JSON.stringify(response.data.damageList[0]));
                if(response.data != undefined && response.data != "" ){
                	$scope.currencyList= response.data.currencyList;
                	if(response.data.profileList != undefined && response.data.profileList.length > 0 && response.data.profileList[0] != null){
                		$scope.currencyId = response.data.profileList[0].defaultCurrencyId;
                		//$scope.recovery.currency = response.data.profileList[0].defaultCurrencyId;
                	}
                	if(response.data.damageList.length > 0){
                
	                	//alert(angular.toJson(response.data.damageList[0].vendorCode));
	            		$scope.damageList = response.data.damageList;
	            		$scope.damage.companyId = response.data.damageList[0].companyId;
	                	$scope.damage.customerId = response.data.damageList[0].customerId;
	                	$scope.damage.vendorId = response.data.damageList[0].vendorId;
	                	$scope.damage.workerId = response.data.damageList[0].workerId;
	            	                	
	                	$scope.damage.vendorCode = response.data.damageList[0].vendorCode;
	                	$scope.damage.vendorName = response.data.damageList[0].vendorName;
	                	$scope.damage.workerCode = response.data.damageList[0].workerCode;
	                	$scope.damage.workerName = response.data.damageList[0].workerName; 
	                	
	                	if(( response.data.damageList[0].dateOfDamage != null && response.data.damageList[0].dateOfDamage != undefined && response.data.damageList[0].dateOfDamage != "")
	                			|| ( response.data.damageList[0].particularsOfDamage != null && response.data.damageList[0].particularsOfDamage != undefined && response.data.damageList[0].particularsOfDamage != "")
	                			//|| ( response.data.damageList[0].numberOfInstallments != null && response.data.damageList[0].numberOfInstallments != undefined && response.data.damageList[0].numberOfInstallments != "")
	                			//|| ( response.data.damageList[0].nameOfThePerson != null && response.data.damageList[0].nameOfThePerson != undefined && response.data.damageList[0].nameOfThePerson != "")
	                			//|| ( response.data.damageList[0].description != null && response.data.damageList[0].description != undefined && response.data.damageList[0].description != "")
	                			|| ( response.data.damageList[0].whetherWorkmanShowedCause != null && response.data.damageList[0].whetherWorkmanShowedCause != undefined && response.data.damageList[0].whetherWorkmanShowedCause != "")
	                			|| ( response.data.damageList[0].amountOfDeduction != null && response.data.damageList[0].amountOfDeduction != undefined && response.data.damageList[0].amountOfDeduction != "")
	                		){
	                		//s$scope.damageVo.dateOfDamage = $filter('date')(response.data.deptVo[0].dateOfDamage,'dd/MM/yyyy');
	                	}else{
	                		$scope.damageList = "";
	                	}
                	}          
                }
            }else if(type == 'save'){
            	if(response.data.id > 0){
            		$scope.Messager('success', 'Success', 'Vendor Damage Or Loss Registered Successfully');
            		$('div[id^="myModal"]').modal('hide');
            		$scope.getData("vendorRegisterTypesController/getVendorDamageById.json", {workerId: response.data.id} , "gridData" );

            	}else{
            		$scope.Messager('error', 'Error', 'Technical issue occurred');
            	}
            }
        },
        function () {
        	$scope.Messager('error', 'Error', 'Technical issues occurred. Please try again...');
        });
    }  //$cookieStore.get("VendorRegisterTypeWorkerId")
	
    $scope.getData("vendorRegisterTypesController/getVendorDamageById.json", {workerId: $routeParams.id}  , "gridData" );
	
	
	
	$scope.saveDamageDetails = function(){	
		if($('#damageRegister').valid()){
			$scope.damageVo.customerId = $scope.damage.customerId;
			$scope.damageVo.companyId = $scope.damage.companyId;
			$scope.damageVo.workerId = $scope.damage.workerId;
			$scope.damageVo.vendorId = $scope.damage.vendorId;
			$scope.damageVo.dateOfDamage = $('#dateOfDamage').val(); 
			$scope.damageVo.createdBy = $cookieStore.get("createdBy");
			$scope.damageVo.modifiedBy = $cookieStore.get("modifiedBy");
		    $scope.getData("vendorRegisterTypesController/saveVendorDamage.json", angular.toJson($scope.damageVo)  , 'save' );
		    //$('div[id^="myModal"]').modal('hide');
		    /*		$scope.damageVo.damageList.push({    		
		    		'dateOfDamage':moment($('#dateOfDamage').val(),'DD/MM/YYYY').format('YYYY-MM-DD'),
		    		'particularsOfDamage':$scope.damageVo.particularsOfDamage,
		    		'nameOfThePerson':$scope.damageVo.nameOfThePerson,
		    		'whetherWorkmanShowedCause':$scope.damageVo.whetherWorkmanShowedCause,
		    		'numberOfInstallments':$scope.damageVo.numberOfInstallments,
		    		'remarks':$scope.damageVo.remarks,  
		    		'description':$scope.damageVo.description,
		    		'amountOfDeduction':$scope.damageVo.amountOfDeduction
		    	});*/
		}
	  }
    
    $scope.Edit = function($event){ 
    	$scope.trIndex = $($event.target).parent().parent().index();
    	$scope.damageVo = $scope.damageList[$($event.target).parent().parent().index()];
    	$scope.damageVo.recoveryList = $scope.damageList[$($event.target).parent().parent().index()].recoveryList;
    	$scope.popUpSave = false;
    	$scope.popUpUpdate =true;
    }
    
    
    $scope.updateDamageDetails= function($event){
    	if($('#damageRegister').valid()){
	    	$scope.damageVo.customerId = $scope.damage.customerId;
			$scope.damageVo.companyId = $scope.damage.companyId;
			$scope.damageVo.workerId = $scope.damage.workerId;
			$scope.damageVo.vendorId = $scope.damage.vendorId;
			$scope.damageVo.createdBy = $cookieStore.get("createdBy");
			$scope.damageVo.modifiedBy = $cookieStore.get("modifiedBy");
			$scope.damageVo.dateOfDamage = $('#dateOfDamage').val(); 
			//alert(angular.toJson($scope.damageVo));
		    $scope.getData("vendorRegisterTypesController/saveVendorDamage.json", angular.toJson($scope.damageVo)  , "save" );
		   // $('div[id^="myModal"]').modal('hide');
    	}
    }
    
    
    
   /* $scope.Delete = function($event){    	
    	var r = confirm("Do you want to delete the "+$scope.damageList[$($event.target).parent().parent().index()].dateOfDamage);    	
    	if(r){
    		$scope.damageList.splice($($event.target).parent().parent().index(),1);
    		
    	}
     }*/
	
    $scope.plusIconAction = function(){
    	$scope.popUpSave = true;
    	$scope.popUpUpdate =false;
    	$scope.damageVo = new Object();
    	$scope.damageVo.currencyId = $scope.currencyId;
    	$scope.damageVo.recoveryList = [];
    }

    $scope.RecoveryPlusIcon = function(){
    	$scope.popUpSave1 = true;
    	$scope.popUpUpdate1 =false;
    	$scope.recovery = new Object();
    	$scope.recovery.currencyId = $scope.currencyId;
    }
    
    $scope.saveRecoveryDetails = function(){	
    	if($('#recoveryForm').valid()){
			$scope.damageVo.recoveryList.push({    		
	    		'recoveredDate':$('#recoveredDate').val(),
	    		'recoveredAmount':$scope.recovery.recoveredAmount,
	    		'damageRecoveryId':$scope.recovery.damageRecoveryId,
	    		'currencyId':$scope.recovery.currencyId
	    	});
			
    	}
	  }
    
    	$scope.DeleteRecovery = function($event){    	
    	var r = confirm("Do you want to delete the "+$scope.damageVo.recoveryList[$($event.target).parent().parent().index()].recoveredDate);    	
    	if(r){
    		$scope.damageVo.recoveryList.splice($($event.target).parent().parent().index(),1);
    	}
     };
     
     
     
     $scope.updateRecoveryDetails = function($event){	
    	 if($('#recoveryForm').valid()){
	    	 $scope.damageVo.recoveryList.splice($scope.trIndex,1);
	    	 $scope.damageVo.recoveryList.push({    		
		    		'recoveredDate':$('#recoveredDate').val(),
		    		'recoveredAmount':$scope.recovery.recoveredAmount,
		    		'damageRecoveryId':$scope.recovery.damageRecoveryId,
		    		'currencyId':$scope.recovery.currencyId
		      });
	    	 $('#myModal1').hide();
    	 }
	  };

	  $scope.EditRecovery = function($event){ 
	    	$scope.trIndex = $($event.target).parent().parent().index();
	    	
	    	var data = $scope.damageVo.recoveryList[$($event.target).parent().parent().index()];
	    	$scope.recoveredDate = data.recoveredDate;
	    	$scope.recoveredAmount = data.recoveredAmount;
	    	$scope.currencyId = data.currencyId;

	    	$scope.recovery = data;
	    	/*if($scope.recovery.currencyId == null || $scope.recovery.currencyId == undefined || $scope.recovery.currencyId == "")
	    		$scope.recovery.currencyId = $scope.currencyId;*/
	    	$scope.popUpSave1 = false;
	    	$scope.popUpUpdate1 =true;
	    }
	  
	  $scope.closeRecovery = function(){
		  if($scope.popUpUpdate1){
			   $scope.recovery.recoveredDate = $scope.recoveredDate;
			   $scope.recovery.recoveredAmount = $scope.recoveredAmount;
			   $scope.recovery.currencyId  = $scope.currencyId;
		  }else{
			  $scope.recovery = new Object();
		  }
		   $('#myModal1').modal('hide');
	  }
	  
  
}]
);



