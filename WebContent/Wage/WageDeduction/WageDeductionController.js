'use strict';

WageDeductionController.controller("WageDeductionCtrl", ['$window','$scope', '$http', '$resource','$location','$filter', '$routeParams','myservice','$cookieStore', function ($window,$scope, $http, $resource, $location, $filter, $routeParams, myservice, $cookieStore) {
	 $.material.init();
	 
	

	$scope.PercentageBy_List = [{"id":"Base rate","name" : "Base rate" },
	                           {"id":"Daily rate","name" : "Daily rate" }];
	
	
	    
	$scope.list_status = [{ id:"Y" , name:"Active"},{ id:"N" , name:"In Active"}];	
	
	
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
     $scope.wageDeduction = new Object();
     $scope.wageDeduction.deductionId = 0;
    
    
     
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
           $scope.readOnly = true;
           $scope.datesReadOnly = true;
           $scope.updateBtn = true;
           $scope.saveBtn = false;
           $scope.viewOrUpdateBtn = true;
           $scope.correcttHistoryBtn = false;
           $scope.resetBtn = false;
           
           $scope.transactionList = false;
           $scope.cancelBtn = false;
           $scope.readOnlyVar= true;
           
       } else {
    	//   $('#transactionDate').val($filter('date')(new Date(),'dd/MM/yyyy'));
    	  
           $scope.readOnly = false;
           $scope.datesReadOnly = false;
           $scope.updateBtn = false;
           $scope.saveBtn = true;
           $scope.viewOrUpdateBtn = false;
           $scope.correcttHistoryBtn = false;
           $scope.resetBtn = true;
           $scope.transactionList = false;
          
           $scope.cancelBtn = false;
           $scope.popUpSave = true;
          $scope.popUpUpdate =false;
          $scope.readOnlyVar= false;
        
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
          
           $scope.transactionList = false;
           $('.addrow').hide();
           $('#transactionDate').val($filter('date')(new Date(),'dd/MM/yyyy'));
           $('.dropdown-toggle').removeClass('disabled');
       }
       
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
	       
	        $scope.returnToSearchBtn = true;
	        $scope.getData('wageDeductionController/getDetailsBywageDeductionId.json', { deductionId: $routeParams.id,customerId: $scope.wageDeduction.customerId}, 'wageDeductionList')
	    }
       
       $scope.viewOrEditHistory = function () {
           $scope.readOnly = false;
           $scope.datesReadOnly = false;
           $scope.updateBtn = false;
           $scope.saveBtn = false;
           $scope.viewOrUpdateBtn = false;
           $scope.correcttHistoryBtn = true;
           $scope.resetBtn = false;      
          
           $scope.transactionList = true;
           $scope.cancelBtn = false;
           $scope.getData('wageDeductionController/getTransactionDatesListForEditingWageDeduction.json', { deductionCode: $scope.wageDeduction.deductionCode }, 'transactionDatesChange');       
           
           $('.dropdown-toggle').removeClass('disabled');
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
        		} else if (type == 'wageDeductionList') {
            	
   				   $scope.currencyList = response.data.currency;
   				   $scope.customerList = response.data.customerList; 
   				
   				   
   				if($scope.updateBtn || $scope.correcttHistoryBtn){
   				 $scope.wageDeduction = response.data.wageDeductionList[0];
   				// alert(angular.toJson(response.data));
 				   $scope.transactionDate = $filter('date')( response.data.wageDeductionList[0].transactionDate, 'dd/MM/yyyy'); 
 				   $('#transactionDate').val($filter('date')( response.data.wageDeductionList[0].transactionDate, 'dd/MM/yyyy'));
 				   $scope.customerChange();
 				   $scope.companyChange(); 
   				}else{   				
   					$('#transactionDate').val($filter('date')(new Date(),'dd/MM/yyyy'));
   					$scope.wageDeduction.isActive = 'Y'; 
   				 if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
		                $scope.wageDeduction.customerId=response.data.customerList[0].customerId;		                
		                $scope.customerChange();
		           }
   				}
   				   
   			    // for button views
   				if($scope.buttonArray == undefined || $scope.buttonArray == '')
   				$scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Define Deduction'}, 'buttonsAction');   
   				   
               }else if (type == 'customerChange') {
                   $scope.companyList = response.data;
                   if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
     	                $scope.wageDeduction.companyId = response.data[0].id;
     	                $scope.companyChange();
     	                }
               }else if (type == 'companyChange') {            	 
            	  $scope.countriesList = response.data.countriesList;
            	  if(response.data.countriesList != undefined && response.data.countriesList != ''){
            		  $scope.wageDeduction.countryId = response.data.countriesList[0].id;
            	  }
            	  $scope.locationList = response.data.locationList;
               } else if(type == 'saveWageScale'){
            	   if(response.data != '' && response.data >0){
            	  $scope.Messager('success', 'success', 'Wage Deduction Details Saved Successfully',buttonDisable);
	            	   if($scope.saveBtn == true ){
	                   		$location.path('/WageDeduction/'+response.data);
	                   }
            	   }else{
            		   $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
            	   }
               }else if (type == 'transactionDatesChange') {
	            	var obj = response.data;
	            	$scope.transactionModel= response.data[(obj.length)-1].id;	            	
	                $scope.transactionDatesList = response.data;
	                $scope.getData('wageDeductionController/getDetailsBywageDeductionId.json', { deductionId: $scope.transactionModel,customerId: $scope.wageDeduction.customerId != undefined ? $scope.wageDeduction.customerId : 0}, 'wageDeductionList')
	            }else if(type== 'validateCode'){	            
	            	if(response.data[0].id == 1){
	            		$scope.Messager('error', 'Error', 'Deduction Code is already Available',buttonDisable);
	            	}else{            		
	            		if($scope.saveBtn){
	            			$scope.save(buttonDisable);	            		
	            		}
	            		
	            	}
	            } 
           },
           function () { 
        	   $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
           });
       }
       
              
       $scope.getData('wageDeductionController/getDetailsBywageDeductionId.json', { deductionId: $routeParams.id,customerId: $cookieStore.get('customerId') != null || $cookieStore.get('customerId') != '' ?$cookieStore.get('customerId') : 0}, 'wageDeductionList')
       
       
       
       	
       $scope.customerChange = function () {   			
            $scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.wageDeduction.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.wageDeduction.companyId ? $scope.wageDeduction.companyId : 0 }, 'customerChange');
       	};
       	
	   	$scope.companyChange = function() {
	   		//alert(JSON.stringify($scope.location.companyId));vendorController/getcountryListByCompanyId.json
	   		$scope.getData('wageDeductionController/getlocationNamesAsDropDowns.json',{customerId: $scope.wageDeduction.customerId, companyId:$scope.wageDeduction.companyId }, 'companyChange')
	   		
	   		
	  	};
	  	
	   
	   	

       $scope.save = function ($event) {
    	   
    	   if($('#wageDeductionForm').valid()){
    		   
    		  $scope.wageDeduction.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
    			if($scope.saveBtn){
    				$scope.wageDeduction.deductionId= 0;
    			}
    		  $scope.wageDeduction.createdBy = $cookieStore.get('createdBy'); 
    		  $scope.wageDeduction.modifiedBy = $cookieStore.get('modifiedBy');
    		 // alert(angular.toJson($scope.wageDeduction));
    		   $scope.getData('wageDeductionController/saveOrUpdatewageDeduction.json', angular.toJson($scope.wageDeduction), 'saveWageScale',angular.element($event.currentTarget));
    	   }
       };
       
       
       
    
       
       
       
       $scope.transactionDatesListChange = function(){
    	   //alert($scope.transactionModel);
    	   $scope.getData('wageDeductionController/getDetailsBywageDeductionId.json', { deductionId: $scope.transactionModel,customerId: $scope.wageDeduction.customerId != undefined ? $scope.wageDeduction.customerId : 0}, 'wageDeductionList')
	       
	       $('.dropdown-toggle').removeClass('disabled');
	   }
       
       
              
      $scope.validateWageDeductionCode = function($event){
    	if($scope.wageDeduction.fixedAmountOrPercentage =='Fixed Amount' && isNaN($scope.wageDeduction.employeeAmount) && isNaN($scope.wageDeduction.employerAmount)){
    		$scope.wageDeduction.employeeAmount = '';
    		$scope.wageDeduction.employerAmount = '';
    		return;
    	} else if(isNaN($scope.wageDeduction.employeeAmount)){
    		$scope.wageDeduction.employeeAmount = '';
    		return;
    	}else if(isNaN($scope.wageDeduction.employerAmount)){
    		$scope.wageDeduction.employerAmount = '';
    		return;
    	}
    	
    	if($scope.wageDeduction.fixedAmountOrPercentage =='By Percentage' && isNaN($scope.wageDeduction.employerPercentage) && isNaN($scope.wageDeduction.employeePercentage)){
    		$scope.wageDeduction.employerPercentage = '';
    		$scope.wageDeduction.employeePercentage = '';
    		return;
    	} else if(isNaN($scope.wageDeduction.employerPercentage)){
    		$scope.wageDeduction.employerPercentage = '';
    		return;
    	}else if(isNaN($scope.wageDeduction.employeePercentage)){
    		$scope.wageDeduction.employeePercentage = '';
    		return;
    	}
    	
       	if($('#wageDeductionForm').valid()  ){         		
       		if($scope.wageDeduction.deductionId != undefined && $scope.wageDeduction.deductionId == 0)    {  		
       		$scope.getData('wageDeductionController/validateWageDeductionCode.json', {customerId: $scope.wageDeduction.customerId,companyId:$scope.wageDeduction.companyId,deductionCode:$scope.wageDeduction.deductionCode,deductionId:$scope.wageDeduction.deductionId} , 'validateCode',angular.element($event.currentTarget));
       	 
       		}else{
       			$scope.save($event);	
       		}
       	}
       }
       
       
       $scope.amountOrPercentageChange= function(){
    	//  alert($scope.wageDeduction.fixedAmountOrPercentage)
    	   if($scope.wageDeduction.fixedAmountOrPercentage =='Fixed Amount'){
    		//   alert("F");
    		   $scope.wageDeduction.employerPercentage = '';
    		   $scope.wageDeduction.employerPercentageType = '';
    		   $scope.wageDeduction.employeePercentage = '';
    		   $scope.wageDeduction.employeePercentageType = '';    		   
    	   }else{
    		  // alert("e");
    		   $scope.wageDeduction.employeeAmount = '';
    		   $scope.wageDeduction.employeeAmountType = '';
    		   $scope.wageDeduction.employerAmountType = '';
    		   $scope.wageDeduction.employerAmount = '';
    	   }
    	   
       }
       
}]);





