'use strict';

WageScaleController.controller("WageScaleCtrl", ['$window','$scope', '$http', '$resource','$location','$filter', '$routeParams','myservice','$cookieStore', function ($window,$scope, $http, $resource, $location, $filter, $routeParams, myservice, $cookieStore) {
	 $.material.init();
	 
	

	$scope.list_jobCategory = [{"id":"Skilled","name" : "Skilled" },
	                       {"id":"Semi Skilled","name" : "Semi Skilled" },
	                       {"id":"High Skilled","name" : "High Skilled" },
	                       {"id":"Special Skilled","name" : "Special Skilled" },
	                       {"id":"UnSkilled","name" : "UnSkilled" }];
	
	$scope.list_Frequency = [{"id":"Per Hour","name" : "Per Hour" },
		                       {"id":"Per Day","name" : "Per Day" },
		                       {"id":"Per Month","name" : "Per Month" },
		                       ];
	    
	$scope.list_status = [{ id:"Y" , name:"Active"},{ id:"N" , name:"Inactive"}];	
	
	
    /* $('#transactionDate').bootstrapMaterialDatePicker({
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
     $scope.wageScale = new Object();
     $scope.wageScale.wageScaleId = 0;
     $scope.wageScale.wageScaleWorkSkillList = [];
    
     
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
           $scope.gridButtons = false;
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
           $scope.gridButtons = true;
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
           $scope.gridButtons = true;
           $scope.transactionList = false;
           $('.addrow').hide();
           $scope.transactionDate = $filter('date')(new Date(),'dd/MM/yyyy');
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
	        $scope.gridButtons = true;
	        $scope.returnToSearchBtn = true;
	        $scope.getData('wageScaleController/getDetailsBywageScaleId.json', { wageScaleId: $routeParams.id,customerId: $scope.wageScale.customerId}, 'wageScaleList')
	    }
       
       $scope.viewOrEditHistory = function () {
           $scope.readOnly = false;
           $scope.datesReadOnly = false;
           $scope.updateBtn = false;
           $scope.saveBtn = false;
           $scope.viewOrUpdateBtn = false;
           $scope.correcttHistoryBtn = true;
           $scope.resetBtn = false;      
           $scope.gridButtons = true;
           $scope.transactionList = true;
           $scope.cancelBtn = false;
           $scope.getData('wageScaleController/getTransactionDatesListForEditingWageScale.json', { wageScaleCode: $scope.wageScale.wageScaleCode }, 'transactionDatesChange');       
           
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
        		} else if (type == 'wageScaleList') {
            	  // alert(angular.toJson(response.data));
   				 //  $scope.currencyList = response.data.currency;
   				   $scope.customerList = response.data.customerList; 
   				
   				   
   				if($scope.updateBtn || $scope.correcttHistoryBtn){
   				   $scope.wageScale = response.data.wageScaleDetailsList[0];
   				   $scope.transactionDate = $filter('date')( response.data.wageScaleDetailsList[0].transactionDate, 'dd/MM/yyyy');  
   				   $scope.wageScale.wageScaleWorkSkillList =response.data.wageScaleDetailsList[0].wageScaleWorkSkillList;
   				   $scope.customerChange();
   				   $scope.companyChange();  				   
   				   
   				}else{
   					$scope.wageScale = new Object();
   					$scope.wageScale.wageScaleId = 0;
   					$scope.wageScale.wageScaleWorkSkillList = [];
   					$('#transactionDate').val($filter('date')(new Date(),'dd/MM/yyyy'));
   					$scope.wageScale.isActive = 'Y'; 
   				 if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
		                $scope.wageScale.customerId=response.data.customerList[0].customerId;		                
		                $scope.customerChange();
		           }
   				}
   				   
   			    // for button views
   				if($scope.buttonArray == undefined || $scope.buttonArray == '')
   				$scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Wage Scale'}, 'buttonsAction');  
   				   
               }else if (type == 'customerChange') {
                   $scope.companyList = response.data;
                   if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
     	                $scope.wageScale.companyId = response.data[0].id;
     	                $scope.companyChange();
     	                }
               }else if (type == 'companyChange') {            	 
            	  $scope.countriesList = response.data.countriesList;
            	  if(response.data.countriesList != undefined && response.data.countriesList.length  == 1){
            		  $scope.wageScale.countryId = response.data.countriesList[0].id;
            	  }
            	  $scope.locationList = response.data.locationList;  
            	  $scope.currencyList = response.data.comanyCurrencyList;          	  
            	 
            	  
               } else if(type == 'saveWageScale'){
            	   if(response.data != '' && response.data >0){
            		   $scope.Messager('success', 'success', 'Wage Scale Details Saved Successfully',buttonDisable);
	            	   if($scope.saveBtn == true && response.data != undefined && response.data != '' && response.data >0){
	                   		$location.path('/WageScale/'+response.data);
	                   }
            	   }else{
            		   $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
            	   }
               }else if (type == 'transactionDatesChange') {
	            	var obj = response.data;
	            	$scope.transactionModel= response.data[(obj.length)-1].id;	            	
	                $scope.transactionDatesList = response.data;
	                $scope.getData('wageScaleController/getDetailsBywageScaleId.json', { wageScaleId: $scope.transactionModel,customerId: $scope.wageScale.customerId != undefined ? $scope.wageScale.customerId : 0}, 'wageScaleList')
	            }else if(type== 'validateCode'){	            
	            	if(response.data[0].id == 1){
	            		$scope.Messager('error', 'Error', 'Wage Scale ID already Available',buttonDisable);
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
       
              
       $scope.getData('wageScaleController/getDetailsBywageScaleId.json', { wageScaleId: $routeParams.id,customerId: $cookieStore.get('customerId') != null || $cookieStore.get('customerId') != '' ?$cookieStore.get('customerId') : 0}, 'wageScaleList')
       
       
       
       	
       $scope.customerChange = function () {   			
            $scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.wageScale.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.wageScale.companyId ? $scope.wageScale.companyId : 0 }, 'customerChange');
       	};
       	
	   	$scope.companyChange = function() {
	   		//alert(JSON.stringify($scope.location.companyId));vendorController/getcountryListByCompanyId.json
	   		$scope.getData('wageScaleController/getlocationNamesAsDropDowns.json',{customerId: $scope.wageScale.customerId, companyId:$scope.wageScale.companyId }, 'companyChange')
	   		
	   		
	  	};
	  	
	   
	   	

       $scope.save = function ($event) {
    	   
    	   if($('#wageScaleForm').valid()){
    		   
    		  $scope.wageScale.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
    			if($scope.saveBtn){
    				$scope.wageScale.wageScaleId= 0;
    			}
    		  $scope.wageScale.createdBy = $cookieStore.get('createdBy'); 
    		  $scope.wageScale.modifiedBy = $cookieStore.get('modifiedBy');
    		  //alert(angular.toJson($scope.wageScale));
    		   $scope.getData('wageScaleController/saveOrUpdatewageScale.json', angular.toJson($scope.wageScale), 'saveWageScale',angular.element($event.currentTarget));
    	   }
       };
       
       
       
    
       
       
       
       $scope.transactionDatesListChange = function(){
    	   //alert($scope.transactionModel);
    	   $scope.getData('wageScaleController/getDetailsBywageScaleId.json', { wageScaleId: $scope.transactionModel,customerId: $scope.wageScale.customerId != undefined ? $scope.wageScale.customerId : 0}, 'wageScaleList')
	       
	       $('.dropdown-toggle').removeClass('disabled');
	   }
       
       
       
       $scope.saveDetails = function($event){       	 
    	   
    	   if($('#WorkSkillForm').valid()){
    		   if($scope.wageScaleWorkSkill.from > $scope.wageScaleWorkSkill.to){
        		   $scope.Messager('error', 'Error', 'From Should not be greater than To..',true);
        	   }else{
    		   
		    	   $scope.wageScale.wageScaleWorkSkillList.push({
		       		'workSkll':$scope.wageScaleWorkSkill.workSkll,
		       		'from':$scope.wageScaleWorkSkill.from,
		       		'to':$scope.wageScaleWorkSkill.to,
		       		'currency':$scope.wageScaleWorkSkill.currency,
		       		'frequency':$scope.wageScaleWorkSkill.frequency,
		       		'currencyName': $('#currency option:selected').html()
		       		});  
		    	   $('div[id^="WorkSkill"]').modal('hide');
        	   }
    	   }
    	 
        }
       
       
       
       $scope.Edit = function($event){   
    	$scope.trIndex = $($event.target).parent().parent().index();
    	//alert(angular.toJson($scope.wageScale.wageScaleWorkSkillList[$($event.target).parent().parent().index()]));
       	$scope.wageScaleWorkSkill = $scope.wageScale.wageScaleWorkSkillList[$($event.target).parent().parent().index()];
       	//alert(angular.toJson($scope.wageScaleWorkSkill));
       	   $scope.popUpSave = false;
       	$scope.popUpUpdate =true;
       }
       
       
       $scope.updateDetails= function($event){  
    	   
    	   
    	   if($('#WorkSkillForm').valid()){
    		   if($scope.wageScaleWorkSkill.from > $scope.wageScaleWorkSkill.to){
        		   $scope.Messager('error', 'Error', 'From Should not be greater than To..',true);
        	   }else{
		    	   $scope.wageScale.wageScaleWorkSkillList.splice($scope.trIndex,1);    	
		    	   $scope.wageScale.wageScaleWorkSkillList.push({
		    		   'workSkll':$scope.wageScaleWorkSkill.workSkll,
		          		'from':$scope.wageScaleWorkSkill.from,
		          		'to':$scope.wageScaleWorkSkill.to,
		          		'currency':$scope.wageScaleWorkSkill.currency,
		          		'frequency':$scope.wageScaleWorkSkill.frequency ,
		          		'currencyName': $('#currency option:selected').html()
		          	});   
		    	   $('div[id^="WorkSkill"]').modal('hide');
        	   }
    	   }
       }
       
       
       
       
       $scope.Delete = function($event){    	
    	   var del = $window.confirm('Are you absolutely sure you want to delete?');
    	   if (del) {
    		   $scope.wageScale.wageScaleWorkSkillList.splice($($event.target).parent().parent().index(),1);
    		  
    	   }
       }
       
       
       
       $scope.close = function(){
    	   $('div[id^="WorkSkill"]').modal('hide');
		    $scope.popUpSave = true;
	       	$scope.popUpUpdate =false;
       }
       
       
       $scope.plusIconAction = function(){
    	   $scope.wageScaleWorkSkill = new Object();
    	   if($scope.currencyList != undefined && $scope.currencyList.length == 1){
     		  $scope.wageScaleWorkSkill.currency =  $scope.currencyList[0].id;
     	  }
    	 	$scope.popUpSave = true;
       		$scope.popUpUpdate =false;
       }
       

       
       $scope.validateWageScaleCode = function($event){
       	if($('#wageScaleForm').valid()){
       		
       		if($scope.wageScale.wageScaleId != undefined && $scope.wageScale.wageScaleId == 0)    { 
       			//alert($scope.wageScale.wageScaleId);
       			$scope.getData('wageScaleController/validateWageScaleCode.json', {customerId: $scope.wageScale.customerId,companyId:$scope.wageScale.companyId,wageScaleCode:$scope.wageScale.wageScaleCode,wageScaleId:$scope.wageScale.wageScaleId} , 'validateCode',angular.element($event.currentTarget));
	       	}else{
	       		$scope.save($event);	
	       	}
       	}
       }
       
}]);





