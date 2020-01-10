'use strict';


defineOverTimeControlsController.controller("defineOverTimeControlsCtrl", ['$scope', '$http', '$resource', '$location','$routeParams','$cookieStore','$filter', function ($scope, $http, $resource, $location,$routeParams,$cookieStore,$filter) {  	
	
$.material.init();
    
    /*$('#transactionDate').bootstrapMaterialDatePicker({ 
    	time : false,
        Button : true,
        format : "DD/MM/YYYY",
        clearButton: true
    }); */
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

    $scope.overTime = new Object();
    $scope.transactionDate = $filter('date')(new Date(),'dd/MM/yyyy'); 
    
    $scope.list_workedOn = [{"id":"WO", "name":"WO"},{"id":"PH", "name":"PH"},{"id":"Shift Day", "name":"Shift Day"}];
    $scope.overTime.isActive = "Y";
    if ($routeParams.id > 0) {
    	$scope.readOnly = true;
        $scope.datesReadOnly = true;
        $scope.updateBtn = true;
        $scope.saveBtn = false;
        $scope.viewOrUpdateBtn = true;
        $scope.correcttHistoryBtn = false;
        $scope.resetBtn = false;      
        $scope.transactionList = false;
        $scope.cancelBtn= false; 	
    }else{
    	$scope.readOnly = false;
        $scope.datesReadOnly = false;
        $scope.updateBtn = false;
        $scope.saveBtn = true;
        $scope.viewOrUpdateBtn = false;
        $scope.correcttHistoryBtn = false;
        $scope.resetBtn = true;
        $scope.transactionList = false;
        $scope.cancelBtn= false;
    }
    
    $scope.updateBtnAction = function (this_obj) {
        $scope.readOnly = false;
        $scope.datesReadOnly = false;
        $scope.updateBtn = false;
        $scope.saveBtn = true;
        $scope.viewOrUpdateBtn = false;
        $scope.correcttHistoryBtn = false;
        $scope.resetBtn = false;
        $scope.cancelBtn = true;
        $scope.addrHistory = true;
        $scope.transactionList = false;       
        $('.dropdown-toggle').removeClass('disabled');
        $scope.transactionDate = $filter('date')(new Date(),'dd/MM/yyyy'); 
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
         $scope.getData('defineOverTimeControlDetailsController/getTransactionDatesListForOverTimeHistory.json', angular.toJson($scope.overTime) , 'transactionDatesChnage');       
        
        $('.dropdown-toggle').removeClass('disabled');
    }

    
    $scope.fun_Company_Trans_Change = function(){
         $('.dropdown-toggle').removeClass('disabled');      
         $scope.getData('defineOverTimeControlDetailsController/getWorkerBadgeRecordByBadgeId.json', { defineOverTimeDetailsInfoId: $scope.transactionModel }, 'masterData')
    }
    
    $scope.fun_cancelBtnCLick = function(){
    	 $scope.readOnly = true;
         $scope.datesReadOnly = true;
         $scope.updateBtn = true;
         $scope.saveBtn = false;
         $scope.viewOrUpdateBtn = true;
         $scope.correcttHistoryBtn = false;
         $scope.resetBtn = false;      
         $scope.transactionList = false;
         $scope.cancelBtn= false;
         $scope.getData('defineOverTimeControlDetailsController/getWorkerBadgeRecordByBadgeId.json', { defineOverTimeDetailsInfoId : $routeParams.id } , 'masterData');        
    	
    }
    
    $scope.getData = function (url, data, type, buttonDisable) {
        var req = {
            method: 'POST',
            url: ROOTURL+url,
            headers: {
                'Content-Type': 'application/json'
            },
            data:data
        }
        $http(req).then(function (response) {           
            if (type == 'masterData')
            {
            	
            	$scope.customerList = response.data.customerList;
            	if ($routeParams.id > 0) {
	            	$scope.overTime = response.data.overTimeList[0];
	            	$scope.overTime.transactionDate = response.data.overTimeList[0].transactionDate;
	            	$scope.transactionModel = response.data.overTimeList[0].defineOverTimeDetailsInfoId;
	            	$scope.transactionDate = $filter('date')(response.data.overTimeList[0].transactionDate,'dd/MM/yyyy'); 
	                $scope.companyList = response.data.companyList; 	               
	                $scope.countryList = response.data.countriesList; 	            
	               //console.log( angular.toJson(response.data));
            	}else{
            		//$scope.overTime= new Object();
            		 if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
    		                $scope.overTime.customerDetailsId=response.data.customerList[0].customerId;		                
    		                $scope.customerChange();
    		          }
            	}
                
            }else if(type == 'customerChange'){	        		
        		$scope.companyList = response.data; 
        		  if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
     	                $scope.overTime.companyDetailsId = response.data[0].id;
  					$scope.companyChange();
     	                }
        	}else if (type == 'companyChange') {       		               
                $scope.countryList = response.data.countriesList;
                $scope.overTime.country = response.data.countriesList[0].id;
            }else if(type=='search'){
                $scope.result = response.data;
            }else if(type=='overTime'){
            	if($scope.saveBtn == true){
            		$scope.Messager('success', 'success', 'Over Time Saved Successfully',buttonDisable);
            		$location.path('/DefineOverTimeControl/'+response.data);
	            }else{
	            		$scope.Messager('success', 'success', 'Over Time Updated Successfully',buttonDisable);
	            	}
            }else if(type=='transactionDatesChnage'){
            	$scope.transactionDatesList = response.data;
            }
        },
        function () {
        	//alert('Error') 
        	
        });
    }   

    $scope.getData('defineOverTimeControlDetailsController/getWorkerBadgeRecordByBadgeId.json', { defineOverTimeDetailsInfoId : $routeParams.id ,customerDetailsId:$cookieStore.get('customerId') } , 'masterData');
    
    $scope.customerChange = function () { 	
    	
	    	if($scope.overTime.customerDetailsId != undefined && $scope.overTime.customerDetailsId != ""){
	    	$scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.overTime.customerDetailsId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.overTime.companyDetailsId ? $scope.overTime.companyDetailsId : 0}, 'customerChange');
	    	}
	    };
	    
	    
	    
    $scope.companyChange = function () {
    
    	if($scope.overTime.companyDetailsId != undefined && $scope.overTime.companyDetailsId != ""){
    		$scope.getData('vendorController/getVendorAndLocationNamesAsDropDowns.json', { customerId: $scope.overTime.customerDetailsId,companyId: $scope.overTime.companyDetailsId }, 'companyChange');
    	}
    };

      

    $scope.save = function ($event) {
    	if($('#overTimeForm').valid()){
    	  $scope.overTime.createdBy = $cookieStore.get('createdBy'); 
	 	  $scope.overTime.modifiedBy = $cookieStore.get('modifiedBy');
	 	  $scope.overTime.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
	 	  if($scope.saveBtn == true){
	 		 $scope.overTime.defineOverTimeDetailsInfoId = 0;
	 	  }
    	//  alert(JSON.stringify(angular.toJson($scope.overTime)));
    	  
	      $scope.getData('defineOverTimeControlDetailsController/saveOrUpdateOverTime.json', angular.toJson($scope.overTime), 'overTime',angular.element($event.currentTarget));	
    	}  
    };
    
    
    
    /*jj*/
    
    $scope.plusIconAction = function(){
		$scope.clearPopUP();		
    	$scope.popUpSave = true;
    	$scope.popUpUpdate =false;
    }
    $scope.clearPopUP = function(){
		$scope.fromMinutes = "";
		$scope.toMinutes = "";
		$scope.roundOffToMinutes = "";	
	}
    
    $scope.addDetailstoList = function(){     	
		 if($('#otRoundOffRules').valid()){ 
			 if($scope.overTime == undefined || $scope.overTime.otRoundOffRules == undefined)
				$scope.overTime.otRoundOffRules =[];
			 
		    	$scope.overTime.otRoundOffRules.push({		    		
		    		'fromMinutes':$scope.fromMinutes,  
		    		'toMinutes':$scope.toMinutes,  
		    		'roundOffToMinutes':$scope.roundOffToMinutes		
		    	});  
		    	$scope.clearPopUP();
		    	$('#myModal').modal('hide');
	       }
	 }
	    
	    $scope.Edit = function($event){  	
	    	$scope.complianceListIndex = $($event.target).parent().parent().index()
	    	var data = $scope.overTime.otRoundOffRules[$($event.target).parent().parent().index()];
	    	$scope.fromMinutes = data.fromMinutes;
			$scope.toMinutes = data.toMinutes;
			$scope.roundOffToMinutes = data.roundOffToMinutes;			
	    	$scope.popUpSave = false;
	    	$scope.popUpUpdate =true;
	    }
	    
	    
	    $scope.updateDetailstoList= function($event){    	
	    	$scope.overTime.otRoundOffRules.splice($scope.complianceListIndex,1);    	
	    	$scope.overTime.otRoundOffRules.push({		    		
	    		'fromMinutes':$scope.fromMinutes,  
	    		'toMinutes':$scope.toMinutes,  
	    		'roundOffToMinutes':$scope.roundOffToMinutes		
	    	}); 
	    	$scope.clearPopUP();
	    	$('#myModal').modal('hide');
	    }

	    $scope.Delete = function($event){    	
	    	$scope.overTime.otRoundOffRules.splice($($event.target).parent().parent().index(),1);	    	
	    }
	    
	    /*kkk*/
	    
	    $scope.plusIconAction1 = function(){
			$scope.clearPopUP1();		
	    	$scope.popUpSave1 = true;
	    	$scope.popUpUpdate1 =false;
	    }
	    $scope.clearPopUP1= function(){
			$scope.workedOn = "";
			$scope.regularRates = "";
			$scope.otRates = "";	
		}
	    
	    $scope.addDetailstoList1 = function(){ 	    
			 if($('#otRoundOffRules').valid()){ 
				 if($scope.overTime == undefined || $scope.overTime.wageRatesForOT == undefined)
					$scope.overTime.wageRatesForOT =[];
				 
			    	$scope.overTime.wageRatesForOT.push({		    		
			    		'workedOn':$scope.workedOn,  
			    		'regularRates':$scope.regularRates,  
			    		'otRates':$scope.otRates		
			    	});  
			    	$scope.clearPopUP1();
			    	$('#myModal1').modal('hide');
		       }
		 }
		    
		    $scope.Edit1 = function($event){  	
		    	$scope.complianceListIndex1 = $($event.target).parent().parent().index()
		    	var data = $scope.overTime.wageRatesForOT[$($event.target).parent().parent().index()];
		    	$scope.workedOn = data.workedOn;
				$scope.regularRates = data.regularRates;
				$scope.otRates = data.otRates;			
		    	$scope.popUpSave1 = false;
		    	$scope.popUpUpdate1 =true;
		    }
		    
		    
		    $scope.updateDetailstoList1= function($event){    	
		    	$scope.overTime.wageRatesForOT.splice($scope.complianceListIndex1,1);    	
		    	$scope.overTime.wageRatesForOT.push({		    		
		    		'workedOn':$scope.workedOn,  
		    		'regularRates':$scope.regularRates,  
		    		'otRates':$scope.otRates		
		    	}); 
		    	$scope.clearPopUP1();
		    	$('#myModal1').modal('hide');
		    }

		    $scope.Delete1 = function($event){    	
		    	$scope.overTime.wageRatesForOT.splice($($event.target).parent().parent().index(),1);	    	
		    }
	    
	    
	    
    
    
    
    
    
}]
)/*.directive('onLastRepeatOver', function () {
    return function (scope, element, attrs) {
        if (scope.$last) {
            setTimeout(function () {            
                $('.table').DataTable();
            }, 1);
        }
    };
})*/;



