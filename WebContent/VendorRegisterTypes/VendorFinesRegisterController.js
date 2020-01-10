'use strict';



vendorRegisterTypesCtrl.controller("vendorFinesRegisterCtrl", ['$window','$scope', '$http', '$resource', '$location','$filter','$cookieStore','$compile', '$routeParams', function ($window,$scope, $http, $resource, $location, $filter, $cookieStore,$compile,$routeParams) {

	var regex=new RegExp("([0-9]{1,3})(\\,?[0-9]{3})*\\.?\d{0,2}$");
	$scope.fine = new Object();
	$scope.fineVo = new Object();
	
	 $('#dateOfOffence,#dateOnWhichFineRealised').datepicker({
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
                	}
                	
                	if(response.data.finesList.length > 0){
	            		$scope.finesList = response.data.finesList;
	            		$scope.fine.companyId = response.data.finesList[0].companyId;
	                	$scope.fine.customerId = response.data.finesList[0].customerId;
	                	$scope.fine.vendorId = response.data.finesList[0].vendorId;
	                	$scope.fine.workerId = response.data.finesList[0].workerId;
	            	                	
	                	$scope.fine.vendorCode = response.data.finesList[0].vendorCode;
	                	$scope.fine.vendorName = response.data.finesList[0].vendorName;
	                	$scope.fine.workerCode = response.data.finesList[0].workerCode;
	                	$scope.fine.workerName = response.data.finesList[0].workerName; 
	                	
	                	if(( response.data.finesList[0].dateOfOffence != null && response.data.finesList[0].dateOfOffence != undefined && response.data.finesList[0].dateOfOffence != "")
	                			|| ( response.data.finesList[0].actForWhichFineIsImposed != null && response.data.finesList[0].actForWhichFineIsImposed != undefined && response.data.finesList[0].actForWhichFineIsImposed != "")
	                			|| ( response.data.finesList[0].dateOnWhichFineRealised != null && response.data.finesList[0].dateOnWhichFineRealised != undefined && response.data.finesList[0].dateOnWhichFineRealised != "")
	                			|| ( response.data.finesList[0].nameOfThePerson != null && response.data.finesList[0].nameOfThePerson != undefined && response.data.finesList[0].nameOfThePerson != "")
	                			|| ( response.data.finesList[0].description != null && response.data.finesList[0].description != undefined && response.data.finesList[0].description != "")
	                			|| ( response.data.finesList[0].whetherWorkmanShowedCause != null && response.data.v[0].whetherWorkmanShowedCause != undefined && response.data.finesList[0].whetherWorkmanShowedCause != "")
	                			|| ( response.data.finesList[0].amountOfFineImposed != null && response.data.finesList[0].amountOfFineImposed != undefined && response.data.finesList[0].amountOfFineImposed != "")
	                			|| ( response.data.finesList[0].remarks != null && response.data.finesList[0].remarks != undefined && response.data.damageList[0].remarks != "")){

	                	}else{
	                		$scope.finesList = [];
	                	}
                	}          
                }
            }else if(type == 'save'){
            	if(response.data.id > 0){
            		$('div[id^="myModal"]').modal('hide');
            		$scope.getData("vendorRegisterTypesController/getVendorFinesById.json", {workerId: response.data.id}  , "gridData" );
            		$scope.Messager('success', 'Success', 'Vendor Fines Registered Successfully');
            	}else{
            		$scope.Messager('error', 'Error', 'Technical issue occurred');
            	}
            }
        },
        function () {
        	$scope.Messager('error', 'Error', 'Technical issues occurred. Please try again...');
        });
    }  //$cookieStore.get("VendorRegisterTypeWorkerId")
	
    $scope.getData("vendorRegisterTypesController/getVendorFinesById.json", {workerId: $routeParams.id}  , "gridData" );
	
	
	
	$scope.saveDetails = function(){	
		if($('#fineRegister').valid()){
			$scope.fineVo.customerId = $scope.fine.customerId;
			$scope.fineVo.companyId = $scope.fine.companyId;
			$scope.fineVo.workerId = $scope.fine.workerId;
			$scope.fineVo.vendorId = $scope.fine.vendorId;
			$scope.fineVo.createdBy = $cookieStore.get("createdBy");
			$scope.fineVo.modifiedBy = $cookieStore.get("modifiedBy");
			$scope.fineVo.dateOfOffence = $('#dateOfOffence').val(); 
		    $scope.getData("vendorRegisterTypesController/saveVendorFines.json", angular.toJson($scope.fineVo)  , 'save' );
		    
		    
		}
	  }
    
    $scope.Edit = function($event){ 
    	$scope.trIndex = $($event.target).parent().parent().index();
    	//alert(angular.toJson($scope.damageList[$($event.target).parent().parent().index()]));
    	$scope.fineVo = $scope.finesList[$($event.target).parent().parent().index()];
    
    	if($scope.fineVo.currencyId == null || $scope.fineVo.currencyId == undefined)
    		$scope.fineVo.currencyId == $scope.currencyId;
    	$scope.popUpSave = false;
    	$scope.popUpUpdate =true;
    }
    
    
    $scope.updateDetails= function($event){
    	if($('#fineRegister').valid()){
	    	$scope.fineVo.customerId = $scope.fine.customerId;
			$scope.fineVo.companyId = $scope.fine.companyId;
			$scope.fineVo.workerId = $scope.fine.workerId;
			$scope.fineVo.vendorId = $scope.fine.vendorId;
			$scope.fineVo.createdBy = $cookieStore.get("createdBy");
			$scope.fineVo.modifiedBy = $cookieStore.get("modifiedBy");
			$scope.fineVo.dateOfOffence = $('#dateOfOffence').val(); 
			//alert(angular.toJson($scope.damageVo));
		    $scope.getData("vendorRegisterTypesController/saveVendorFines.json", angular.toJson($scope.fineVo)  , "save" );
		  //  $('div[id^="myModal"]').modal('hide');
    	}
    }
    
    $scope.plusIconAction = function(){
    	$scope.popUpSave = true;
    	$scope.popUpUpdate =false;
    	$scope.fineVo = new Object();
    	$scope.fineVo.currencyId = $scope.currencyId;
    }

    
}]
);
