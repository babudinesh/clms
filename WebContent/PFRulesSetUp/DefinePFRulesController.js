'use strict';

var pfController = angular.module("myApp.definePF", ['ngCookies']);
pfController.controller("pfRulesController", ['$window','$scope', '$http', '$resource','$location','$filter', '$routeParams','myservice','$cookieStore', function ($window,$scope, $http, $resource, $location, $filter, $routeParams, myservice, $cookieStore) {
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
    
    
    
     $scope.wageDeduction.fixedAmountOrPercentage = "Fixed Amount";
     
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
          // $('.addrow').hide();
          // $('#transactionDate').val($filter('date')(new Date(),'dd/MM/yyyy'));
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
	        $scope.getData('pfRulesController/getDetailsForpfRulesController.json', { pfrulesId: 0,customerId:  $scope.wageDeduction.customerId,companyId:$scope.wageDeduction.companyId}, 'wageDeductionList');
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
           $scope.cancelBtn = true;
           $scope.getData('pfRulesController/getTransactionDatesListForEditingpfRulesController.json', { customerId: $scope.wageDeduction.customerId,companyId:$scope.wageDeduction.companyId}, 'transactionDatesChange');      
           
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
        		} else if(type == 'dropDowns'){
        		   $scope.currencyList = response.data.currency;
   				   $scope.customerList = response.data.customerList; 
   				 if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
		                $scope.wageDeduction.customerId=response.data.customerList[0].customerId;		                
		                $scope.customerChange();
		           } 
	   				 if(response.data.comanyCurrencyList != undefined && response.data.comanyCurrencyList.length == 1){
		   				 $scope.wageDeduction.employerAmountType = response.data.comanyCurrencyList[0].id;
		   				 $scope.wageDeduction.employeeAmountType = response.data.comanyCurrencyList[0].id;
	   				 }
	   			// for button views
	 			  	if($scope.buttonArray == undefined || $scope.buttonArray == '')
	    				$scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Location'}, 'buttonsAction');
        	   }else if (type == 'wageDeductionList') {
        		 //  alert(angular.toJson(response.data));
   				if(response.data.wageDeductionList[0] != undefined && response.data.wageDeductionList[0] != '' && response.data.wageDeductionList.length >=1){
	   				   $scope.wageDeduction = response.data.wageDeductionList[0];   				
	 				   $scope.transactionDate = $filter('date')( response.data.wageDeductionList[0].transactionDate, 'dd/MM/yyyy'); 
	 				   $('#transactionDate').val($filter('date')( response.data.wageDeductionList[0].transactionDate, 'dd/MM/yyyy')); 
	 				   if(!$scope.correcttHistoryBtn){	 					 
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
	 				   }
	 				  
	 				 
   				}else{   				
   					$('#transactionDate').val($filter('date')(new Date(),'dd/MM/yyyy'));
   					$scope.wageDeduction.isActive = 'Y'; 
   					$scope.wageDeduction.deductionId = 0;
   					//alert($scope.wageDeduction.deductionId);
   				}  				  			   
   				   
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
            	 
               } else if(type == 'savepfRules'){
            	   if(response.data != '' && response.data >0){
            	  $scope.Messager('success', 'success', 'Wage Deduction Details Saved Successfully',buttonDisable);
	            	  /* if($scope.saveBtn == true ){
	                   		$location.path('/WageDeduction/'+response.data);
	                   }*/
            	   }else{
            		   $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
            	   }
               }else if (type == 'transactionDatesChange') {
	            	var obj = response.data;
	            	$scope.transactionModel= response.data[(obj.length)-1].id;	            	
	                $scope.transactionDatesList = response.data;
	                $scope.getData('pfRulesController/getDetailsForpfRulesController.json', { pfrulesId: $scope.transactionModel,customerId:  0,companyId:0}, 'wageDeductionList')
	            }
           },
           function () { 
        	   $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
           });
       }
       
              
       $scope.getData('pfRulesController/getDropDownDetailsForpfRules.json', {customerId: ($cookieStore.get('customerId') != null && $cookieStore.get('customerId') != '') ? $cookieStore.get('customerId') : ($scope.wageDeduction.customerId != undefined && $scope.wageDeduction.customerId != '') ? $scope.wageDeduction.customerId : 0,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : ($scope.wageDeduction.companyId != undefined && $scope.wageDeduction.companyId != '') ? $scope.wageDeduction.companyId : 0}, 'dropDowns')
       
       
       
       	
       $scope.customerChange = function () {   			
            $scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.wageDeduction.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.wageDeduction.companyId != undefined ? $scope.wageDeduction.companyId : 0 }, 'customerChange');
            
           
       	};
       	
	  	$scope.companyChange = function() {	   		
	   		$scope.getData('wageDeductionController/getlocationNamesAsDropDowns.json',{customerId: $scope.wageDeduction.customerId, companyId:$scope.wageDeduction.companyId }, 'companyChange')
		   	 if($scope.wageDeduction.customerId != undefined && $scope.wageDeduction.customerId != '' && $scope.wageDeduction.companyId != undefined && $scope.wageDeduction.companyId != ''){	        	
		   		 $scope.getData('pfRulesController/getDetailsForpfRulesController.json', { pfrulesId: 0,customerId:  $scope.wageDeduction.customerId,companyId:$scope.wageDeduction.companyId}, 'wageDeductionList')
	        }else{
	        	$scope.wageDeduction.fixedAmountOrPercentage = "";
	        }
	  	};
	  	
	   
	   	

       $scope.save = function ($event) {    	
    	  
    	  // alert($('#wageDeductionForm').valid());
    	   if($('#wageDeductionForm').valid()){    		   
    		  $scope.wageDeduction.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
    			if($scope.saveBtn){
    				$scope.wageDeduction.deductionId= 0;
    			}
    			
    			//alert($scope.wageDeduction.deductionId);
    		  $scope.wageDeduction.createdBy = $cookieStore.get('createdBy'); 
    		  $scope.wageDeduction.modifiedBy = $cookieStore.get('modifiedBy');
    		 // alert(angular.toJson($scope.wageDeduction));
    		  $scope.getData('pfRulesController/saveOrUpdatepfRules.json', angular.toJson($scope.wageDeduction), 'savepfRules',angular.element($event.currentTarget));
    	   }
       };
       
              
       $scope.transactionDatesListChange = function(){    	
    	   $scope.getData('pfRulesController/getDetailsForpfRulesController.json', { pfrulesId: $scope.transactionModel,customerId:  0,companyId:0}, 'wageDeductionList')
	       $('.dropdown-toggle').removeClass('disabled');
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





