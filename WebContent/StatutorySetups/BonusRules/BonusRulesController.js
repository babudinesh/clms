'use strict';
var bonusRulesController = angular.module("myapp.BonusRules", []);
bonusRulesController.controller("bonusRulesControllerDtls", ['$scope', '$rootScope', '$http', '$filter', '$resource','$location','$routeParams','$cookieStore', 'myservice', function($scope,$rootScope, $http,$filter,$resource,$location,$routeParams,$cookieStore,myservice) {
	
	$.material.init();
	 $( "#transactionDate" ).datepicker({
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
    
    $scope.bonus = new Object();
    $scope.bonus.isActive = 'Y';
    $scope.transactionDate = $filter('date')(new Date(),"dd/MM/yyyy");    
    
    $scope.list_status = [{"id":"Y","name" : "Active" },{"id":"N","name" : "Inactive" }];
    
    
    fun_showHideButtons('Default');
	// show hide buttons
	function fun_showHideButtons(actionType){
		
		if(actionType == "Default"){
			$scope.readonly = false;
			$scope.readonlyEdit = false;
			$scope.saveBtn = false;
			$scope.updateBtn = false;
			$scope.correctHistoryBtn = false;			
			$scope.viewHistoryBtn = false;
			$scope.cancelBtn = true;	
			$scope.transactionList = false;
			$scope.cancelEditBtn = false;
		}else if (actionType =='DataAvailable'){
			$scope.readonly = true;    		
    		$scope.updateBtn = true;
    		$scope.viewHistoryBtn = true;
    		$scope.readonlyEdit = true;
    		$scope.cancelEditBtn = false;
    		$scope.cancelBtn = true;	
		}else if (actionType =='DataNotAvailable'){
    		$scope.saveBtn = true;
    		$scope.cancelBtn = true;
    		$scope.transactionList = false;	
		}
	}
	$scope.fun_updateActionBtn = function(){
		$scope.readonly = false;
		$scope.saveBtn = true;
		$scope.updateBtn = false;
		$scope.correctHistoryBtn = false;
		$scope.resetBtn = false;
		$scope.viewHistoryBtn = false;
		$scope.cancelBtn = false;
		$scope.cancelEditBtn = true;
		$scope.transactionList = false;
		$scope.transactionDate = $filter('date')(new Date(),"dd/MM/yyyy");
		$('.dropdown-toggle').removeClass('disabled');
	}
	$scope.fun_viewHistoryBtn = function(){
		$scope.readonly = false;
		$scope.saveBtn = false;
		$scope.updateBtn = false;
		$scope.correctHistoryBtn = true;
		$scope.resetBtn = false;
		$scope.viewHistoryBtn = false;
		$scope.cancelBtn = true;
		$scope.returnTOSearchBtn = true;
		$scope.transactionList = true;	
		$scope.getPostData("statutorySetupsController/getTransactionDatesForBonusHistory.json", { customerDetailsId : $scope.customerDetailsId , companyDetailsId : $scope.companyDetailsId , countryId : $scope.countryId } , "transactionDates")
	    $('.dropdown-toggle').removeClass('disabled');
		
	}
	$scope.fun_bonus_CancelBtn = function(){		
		$scope.readonly = false;
		$scope.readonlyEdit = false;
		$scope.saveBtn = false;
		$scope.updateBtn = false;
		$scope.correctHistoryBtn = false;
		$scope.viewHistoryBtn = false;
		$scope.cancelBtn = true;
		$scope.cancelEditBtn = false;
		$scope.transactionList = false;		
		$scope.bonus = new Object();
		$scope.customerDetailsId = 0;
		$scope.companyDetailsId = 0;
		$scope.countryId = 0;
	}
	$scope.fun_bonus_Cancel_Edit_Btn = function(){		
		$scope.readonly = true;    		
		$scope.updateBtn = true;
		$scope.saveBtn = false;
		$scope.viewHistoryBtn = true;
		$scope.readonlyEdit = true;
		$scope.cancelEditBtn = false;
		$scope.cancelBtn = true;
	}
	$scope.getPostData = function (url, data, type, buttonDisable) {
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
            	$scope.customerList = response.data.customerList;
            	$scope.currencyList = response.data.currencyList;     
            	 if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
		                $scope.customerDetailsId=response.data.customerList[0].customerId;		                
		                $scope.fun_CustomerChangeListener();
		                }
            	// for button views
 			  	if($scope.buttonArray == undefined || $scope.buttonArray == '')
            	 $scope.getPostData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Bonus Rules'}, 'buttonsAction');
            }else if (type == 'customerChange') {              	
            		$scope.companyList = response.data;            		
            		   if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
          	                $scope.companyDetailsId= response.data[0].id;
          	                $scope.fun_CompanyChangeListener();
          	                }
            }else if (type == 'companyChange') {              	
            	$scope.countriesList = response.data.countriesList; 
            	if(response.data.countriesList != undefined && response.data.countriesList[0].id != undefined && response.data.countriesList[0].id  > 0)
            		$scope.countryId = response.data.countriesList[0].id; 
            	if(response.data.bonusRulesVo != undefined && response.data.bonusRulesVo[0] != undefined && response.data.bonusRulesVo[0].sequenceId > 0){  
            		$scope.bonus = response.data.bonusRulesVo[0];	            	
	            	$scope.transactionDate = $filter('date')(response.data.bonusRulesVo[0].transactionDate,"dd/MM/yyyy");            		
	            	fun_showHideButtons('DataAvailable');	            	
            	}else{            		
            		fun_showHideButtons('DataNotAvailable');
            		$scope.countryId = response.data.countriesList[0].id; 
            	}
            }else if(type == 'countryChange'){  
            	if( response.data.countryId > 0){
            		$scope.bonus = response.data;
            		$scope.transactionDate = $filter('date')(response.data.transactionDate,"dd/MM/yyyy");            	
            		$scope.readonly = true;
            		$scope.saveBtn = false;
            		$scope.updateBtn = true;
            		$scope.correctHistoryBtn = false;
            		$scope.resetBtn = false;
            		$scope.viewHistoryBtn = true;
            		$scope.cancelBtn = false;            		
            		$scope.transactionList = false;
            	}else{
            		$scope.readonly = false;
            		$scope.saveBtn = true;
            		$scope.updateBtn = false;
            		$scope.correctHistoryBtn = false;
            		$scope.resetBtn = true;
            		$scope.viewHistoryBtn = false;
            		$scope.cancelBtn = false;
            		$scope.returnTOSearchBtn = true;
            		$scope.transactionList = false;
            	}
            }else if(type == 'savebonusRecord'){    
            	$scope.readonly = true;
            	$scope.readonlyEdit = true;
        		$scope.updateBtn = true;
        		$scope.saveBtn = false;
        		$scope.viewHistoryBtn = true;        		
        		$scope.cancelEditBtn = false;
        		$scope.cancelBtn = true;
            }else if (type == 'saveCorrectHistory'){
            	
            }else if(type == 'transactionHistroyDatesChange'){
            	//alert(angular.toJson(response.data));
            	$scope.companyList = response.data.companyList;
            	$scope.countriesList = response.data.countriesList;
            	$scope.bonus = response.data.bonusRulesVo[0] ;
            	$scope.customerDetailsId = response.data.bonusRulesVo[0].customerDetailsId;
        		$scope.companyDetailsId = response.data.bonusRulesVo[0].companyDetailsId;
            	$scope.countryId = response.data.bonusRulesVo[0].countryId ; 
        		$scope.transactionDate = $filter('date')(response.data.bonusRulesVo[0].transactionDate,"dd/MM/yyyy");
            }else if(type == 'transactionDates'){
            	$scope.transactionDatesList = response.data;            	
            }
            
        },
        function () {
       	 $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
       	//alert('Error')         	
       })
	};
	

	
    // GET MASTER DATA FOR DETAILS SCREEN   
	$scope.getPostData("shiftsController/getShiftsGridData.json", { customerId: $cookieStore.get('customerId') }, "masterData");		    

	// Customer Change Listener to get Companies List
	$scope.fun_CustomerChangeListener = function(){	
		if($scope.customerDetailsId != undefined && $scope.customerDetailsId != '')
		$scope.getPostData("vendorController/getCompanyNamesAsDropDown.json", { customerId : $scope.customerDetailsId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.companyDetailsId != undefined ? $scope.companyDetailsId : 0 }, "customerChange");	
	}
	
	// Company Change Listener to get Countries List
	$scope.fun_CompanyChangeListener = function(){
		if($scope.customerDetailsId != undefined && $scope.customerDetailsId != '' && $scope.companyDetailsId != undefined && $scope.companyDetailsId != '')
		$scope.getPostData("statutorySetupsController/getCountryNamesAsDropDown.json", { customerDetailsId : $scope.customerDetailsId , companyDetailsId : $scope.companyDetailsId }, "companyChange");	
	}
	
	// Country Change Listener to get bonus Data
	$scope.fun_CountryChangeListener = function(){
		 if( $scope.customerDetailsId != undefined && $scope.customerDetailsId != '' && $scope.companyDetailsId != undefined && $scope.companyDetailsId != '' && $scope.countryId != undefined &&  $scope.countryId != '')
		 $scope.getPostData("statutorySetupsController/getBonusRulesDetails.json", { customerDetailsId : $scope.customerDetailsId , companyDetailsId : $scope.companyDetailsId , countryId : $scope.countryId }, "countryChange");	
	}
	
	
	$scope.fun_save_bonus_Details = function($event){
		/*if( $scope.bonus.isActive != 'Y'){
			$scope.Messager('error', 'Error', 'Please select Status...',angular.element($event.currentTarget));	
			return;
		}*/
		 if($('#bonusRulesDetails').valid()){
			$scope.bonus.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
			$scope.bonus.customerDetailsId  = $scope.customerDetailsId;
			$scope.bonus.companyDetailsId  = $scope.companyDetailsId;
			$scope.bonus.countryId  = $scope.countryId;
			$scope.bonus.createdBy = $cookieStore.get('createdBy'); 
		 	$scope.bonus.modifiedBy = $cookieStore.get('modifiedBy');
		 	  
			var bonusRulesId =0;
			if($scope.saveBtn == true){
				bonusRulesId = $scope.bonus.bonusRulesId;
				$scope.bonus.bonusRulesId = 0;
				$scope.getPostData("statutorySetupsController/saveBonusRulesDetails.json", angular.toJson($scope.bonus), "savebonusRecord",angular.element($event.currentTarget));		
			}else {		
				$scope.getPostData("statutorySetupsController/saveBonusRulesDetails.json", angular.toJson($scope.bonus), "saveCorrectHistory",angular.element($event.currentTarget));		
			}
			if($scope.saveBtn == true){
				$scope.bonus.bonusRulesId = bonusRulesId;			
			}				
			$scope.Messager('success', 'success', 'Bonus Rules Record Saved Successfully',angular.element($event.currentTarget));			
		 }
	}
	
	$scope.fun_transactionDatesListChnage = function(){
		if($scope.bonus.bonusRulesId != undefined && $scope.bonus.bonusRulesId != '' )
			$scope.getPostData("statutorySetupsController/getBonusRecordByBonusRulesId.json", { bonusRulesId : $scope.bonus.bonusRulesId } , "transactionHistroyDatesChange");	
	}
	 
}]);
