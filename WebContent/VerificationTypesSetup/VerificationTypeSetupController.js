'use strict';
//var departmentControllers = angular.module("myApp.addDepartment", []);

/*departmentControllers
.service('myservice', function () {
    this.companyId = 0;
    this.customerId = 0;
   
});*/
veificationTypeSetupCtrl.controller('AddveificationTypeSetupCtrl', ['$scope', '$http', '$resource', '$routeParams', '$location','$filter','myservice','$cookieStore', function ($scope, $http, $resource, $routeParams, $location, $filter, myservice, $cookieStore) {
		$.material.init();
		$scope.div = true;
		
		$scope.dept=new Object();
		/*$('#transactionDate').bootstrapMaterialDatePicker({
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
        
        $scope.verificationFrequencyList = [{"id":"Period","name":"Period"},{"id":"On Boarding","name":"On Boarding"}]
        
	    if ($routeParams.id > 0) {
	        $scope.readOnly = true;	        
	        $scope.updateBtn = true;
	        $scope.saveBtn = false;	       
	        $scope.resetBtn = false;
	        $scope.cancelBtn = false;
	       
	    } else {
	    	$('#transactionDate').val($filter('date')(new Date(),'dd/MM/yyyy'));
	        $scope.readOnly = false;	       
	        $scope.updateBtn = false;
	        $scope.saveBtn = true;      
	        $scope.resetBtn = true;       	       
	        $scope.cancelBtn = false;
	    }
	    
	    $scope.updateDeptBtnAction = function (this_obj) {	    	
	        $scope.readOnly = false;	       
	        $scope.updateBtn = false;
	        $scope.saveBtn = true;	      
	        $scope.resetBtn = false;
	        $scope.cancelBtn = true;
	    	$scope.dept.transactionDate=  $filter('date')( new Date(), 'dd/MM/yyyy');
	      
	        $('.dropdown-toggle').removeClass('disabled');
	    }
	    
	   
	    
	    $scope.cancelBtnAction = function(){
	    	$scope.readOnly = true;	       
	        $scope.updateBtn = true;
	        $scope.saveBtn = false;	        
	        $scope.resetBtn = false;	       
	        $scope.cancelBtn = false;	        
	        $scope.getData('workerVerificationTypesSetupController/getWorkerVerificationTypesSetupsById.json', { workerVerificationTypesSetupId: $routeParams.id,customerId:$cookieStore.get('customerId')!= undefined ? $cookieStore.get('customerId') : 0 }, 'verificationTypesList');
	    }
	    
	    $scope.getData = function (url, data, type,buttonDisable) {
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
	        		
	        		
	        	}else if (type == 'verificationTypesList') {					
	              //  $scope.deptList = response.data;	 
	              // alert(angular.toJson(response.data.customerList.length));
	                $scope.customerList = response.data.customerList;
	                
	                if(response.data.workerVerificationTypesSetupVo != undefined &&   response.data.workerVerificationTypesSetupVo.length > 0){	                	 
	 	                 $scope.dept = response.data.workerVerificationTypesSetupVo[0];
	 	                $('#transactionDate').val($filter('date')(response.data.workerVerificationTypesSetupVo[0].transactionDate,'dd/MM/yyyy'));
	 	                
	                }else{
	                	$scope.dept = new Object();
	                	
	                	$('#transactionDate').val($filter('date')( new Date(), 'dd/MM/yyyy'));
	                	$scope.dept.isActive ="Y";	 
	                	$scope.dept.mandatiory = false;
	                	
	                }
	                if( response.data != undefined && response.data != "" && response.data.customerList.length == 1 ){
	                	$scope.dept.customerId = response.data.customerList[0].customerId;	                	
	                }
	                $scope.customerChange();
	                
	                if($scope.buttonArray == undefined || $scope.buttonArray == '')
	                	$scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Worker Verification Type Setup'}, 'buttonsAction'); 
	                    
	            	
	            }
	            else if (type == 'customerChange') {					
	                $scope.companyList = response.data;
	                if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	                	$scope.dept.companyId = response.data[0].id;
	                	$scope.companyChange();
	                }
	               
	            }else if(type == 'companyChange'){
	            	//alert(angular.toJson(response.data));
	            	$scope.countriesList = response.data.countriesList; 	              
	                $scope.dept.countryId = response.data.countriesList[0].id
	            }else if (type == 'saveVerifiTypes') {
	            //	alert(response.data.workerVerificationTypesSetupId);
	              if(response.data.workerVerificationTypesSetupId > 0){
	            		$scope.Messager('success', 'success', 'Details Saved Successfully',buttonDisable);
	            		 location.href = "#/verificationTypesSetup/"+response.data.workerVerificationTypesSetupId;
	            		 $scope.readOnly = true;	        
	         	        $scope.updateBtn = true;
	         	        $scope.saveBtn = false;	       
	         	        $scope.resetBtn = false;
	         	        $scope.cancelBtn = false;
	         	       
	              }else if(response.data.workerVerificationTypesSetupId == -1){
	            	  $scope.Messager('error', 'Error', 'Verification Type Already Available,Try with some other name',buttonDisable); 
	              }else{
	            	  $scope.Messager('error', 'Error', 'Error in saving the details, try after some time...',buttonDisable); 
	              }
	            }
	           },
	                
	        function () { 
           	 $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable); 

	        });
	    }
	    
	  
	       
	    $scope.getData('workerVerificationTypesSetupController/getWorkerVerificationTypesSetupsById.json', { workerVerificationTypesSetupId: $routeParams.id,customerId:$cookieStore.get('customerId')!= undefined ? $cookieStore.get('customerId') : 0 }, 'verificationTypesList');
	    
	    $scope.customerChange = function () {
	    	//alert();
	        $scope.getData('vendorController/getCompanyNamesAsDropDown.json', {customerId:  $scope.dept.customerId, companyId:$cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "" ? $cookieStore.get('companyId') : $scope.dept.companyId }, 'customerChange');
	    };
	    
	   
	    $scope.companyChange = function () {
	    	//alert($scope.vendorDetailsVo.companyId+"::in company CHnage");
	    	if($scope.dept.companyId != undefined && $scope.dept.companyId != "" && $scope.dept.customerId != undefined && $scope.dept.customerId !=""){
	    		$scope.getData('CompanyController/getLocationsByCompanyId.json', { companyId: $scope.dept.companyId, customerId: $scope.dept.customerId  }, 'companyChange');
	    	}
	    };
	   
	    
	    $scope.save = function ($event) {
    	if($('#veriTypeForm').valid()){	    		
		    	  $scope.dept.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 	
		    	  /*if($scope.dept.mandatiory != 'true' || !$scope.dept.mandatiory){
		    		  $scope.dept.mandatiory = 'false';
		    	  }*/
		    	  $scope.dept.createdBy = $cookieStore.get('createdBy');
		          $scope.dept.modifiedBy = $cookieStore.get('modifiedBy');
		        //  alert(angular.toJson($scope.dept));
		    	 $scope.getData('workerVerificationTypesSetupController/saveOrUpdateVerificationTypesSetups.json', angular.toJson($scope.dept), 'saveVerifiTypes');
	    	}
	    };
	
	   

}]);
