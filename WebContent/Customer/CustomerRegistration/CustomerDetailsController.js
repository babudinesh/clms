'use strict';
//var vendorControllers = angular.module("myApp.CustomerDetails",[]);

customerControlller.controller('customerDetailsCtrl', ['$scope','$http', '$resource','$routeParams','$filter','$cookieStore','myservice','$location', function ($scope,$http, $resource, $routeParams,$filter,$cookieStore,myservice,$location) {

	//initialise the material-design
            $.material.init();
     /*$('#transactionDate,#registrationDate,#panRegistrationDate,#pfRegistrationDate,#pfStartDate,#esiRegistrationDate,#esiStartDate,#createdDate').bootstrapMaterialDatePicker
                ({
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
    
    $scope.customerPage = $routeParams.customerInfoId;
    $scope.customerDetailsVo = new Object();
    
    $scope.roleName = $cookieStore.get('roleName');
    
    if ($routeParams.customerInfoId > 0) {
    	$scope.gridButtons = false;
        $scope.cancelBtn = false;
        $scope.readOnly = true;
        $scope.datesReadOnly = true;
    	if( $cookieStore.get('roleNamesArray').includes('Super Admin') ){
	        $scope.updateBtn = true;
	        $scope.saveBtn = false;
	        $scope.viewOrUpdateBtn = true;
	        $scope.correcttHistoryBtn = false;
	        $scope.resetBtn = false;      
	        $scope.transactionList = false;
    	}else if( $cookieStore.get('roleNamesArray').includes('Customer Admin')){
    		$scope.updateBtn = true;
	        $scope.saveBtn = false;
	        $scope.viewOrUpdateBtn = true;
	        $scope.correcttHistoryBtn = false;
	        $scope.resetBtn = false;      
	        $scope.transactionList = false;
    	}else{
    		$scope.updateBtn = false;
	        $scope.saveBtn = false;
	        $scope.viewOrUpdateBtn = false;
	        $scope.correcttHistoryBtn = false;
	        $scope.resetBtn = false;      
	        $scope.transactionList = false;
    	}	
        
    } else {
    	$('#transactionDate').val($filter('date')(new Date(),'dd/MM/yyyy'));
    	
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
        $cookieStore.put('CustomerCustomerId',0);
        $cookieStore.put('customerinfoId',0);
        
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
        	} else if (type == 'customerList') {
                $scope.customerDetails = response.data;
                 //alert(JSON.stringify(response.data.customerCountriesList));
                $scope.customerDetailsVo = response.data.customerList[0];                
               // alert(myservice.customerId);
                if(response.data.customerList[0] != undefined){
               	 $scope.customerDetailsVo.transactionDate =  $filter('date')( response.data.customerList[0].transactionDate, 'dd/MM/yyyy');
               	 $('#transactionDate').val($filter('date')( response.data.customerList[0].transactionDate, 'dd/MM/yyyy'));
               	 $scope.transDate1 =  $filter('date')( response.data.customerList[0].transactionDate, 'dd/MM/yyyy'); 
               	 $scope.customerDetailsVo.industyIds = JSON.parse(response.data.customerList[0].industyIds);                 
                 $scope.industryChange();
                 $cookieStore.put('customerinfoId',response.data.customerList[0].customerinfoId);
                 $cookieStore.put('CustomerCustomerId',response.data.customerList[0].customerId);
                 $cookieStore.put('CustomercustomerName',response.data.customerList[0].customerName);
                 $cookieStore.put('CustomercustomerCode',response.data.customerList[0].customerCode);
                 
                 myservice.customerId = response.data.customerList[0].customerId;
                 myservice.customerName = response.data.customerList[0].customerName;
                 myservice.customerCode = response.data.customerList[0].customerCode;
                 
                 if($scope.customerDetailsVo.isMultinaional == 'Yes'){
                	 $scope.countryTable = true;
                	 if(response.data.customerCountriesList != undefined && response.data.customerCountriesList != null && (response.data.customerCountriesList.length == 0 || response.data.customerCountriesList[0].customerCountriesId == 0)){
                		 $scope.customerDetails.customerCountriesList = [];
                	 }
             	}
               }else{            	  
            	   $scope.customerDetailsVo = new Object();
            	   $scope.customerDetailsVo.transactionDate =  $filter('date')(new Date(), 'dd/MM/yyyy'); 
            	   $scope.customerDetailsVo.isActive = 'Y'
            	   $scope.customerDetailsVo.entityType = 'Legal'
            	   $scope.countryTable = false;
               	
               }
                
             // for button views
                if($scope.buttonArray == undefined || $scope.buttonArray == '')
                $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Customer'}, 'buttonsAction');         
            }
             else if (type == 'industryChange') {
                $scope.sectorList = response.data;
               // alert(angular.toJson(response.data));
            } else if (type == 'saveCustomer') {               
            	
               // if($scope.saveBtn == true &&  $scope.resetBtn == false){
                	$location.path('/CustomerDetails/'+response.data.id);
              //  }
             
                $scope.Messager('success', 'success', 'Customer Details Saved Successfully',buttonDisable);
            } else if (type == 'getTransactionDates') {
                $scope.transactionDatesList = response.data;
                var k =0;
            	var i = response.data.length;
            	if(response.data.length > 1){
	            	for(i = response.data.length-1; i> 0;i--){
		            	 if($scope.dateDiffer(response.data[i-1].name.split('-')[0])){
		            		 k = response.data[i-1].id;
		            		 break;
		            	 }
	            	}
            	}else{
            		k = response.data[0].id;
            	}
            	
            	$scope.transactionModel= k;
               // $scope.transactionModel= parseInt($routeParams.customerInfoId);
                $scope.getData('CustomerController/CustomerDtails.json', {customerInfoId:$scope.transactionModel}, 'customerList')
            }else if(type== 'validateCode'){
            	//alert(angular.toJson(response.data[0].id));
            	if(response.data[0].id == 1){
            		$scope.Messager('error', 'Error', 'Customer Code already Available',buttonDisable);
            	}else{            		
            		if($scope.saveBtn){
            			$scope.save(buttonDisable);
            		}else if($scope.correcttHistoryBtn){
            			$scope.correctHistorySave(buttonDisable)
            		}
            	}
            }           
        },
        function () {
        	 $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
        	//alert('Error')         	
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
    
    $scope.getData('CustomerController/CustomerDtails.json', {customerInfoId: $routeParams.customerInfoId}, 'customerList')
    
   
    
   
   
    $scope.industryChange = function () {
        $scope.getData('vendorController/getsubIndustryList.json', { industryId: $scope.customerDetailsVo.industyIds }, 'industryChange');
    };
    
    $scope.validateCustomerCode = function($event){
    	if($('#Form').valid()){
    		$scope.getData('CustomerController/validateCustomerCode.json', {customerId: $cookieStore.get('CustomerCustomerId'),customerCode:$scope.customerDetailsVo.customerCode} , 'validateCode',angular.element($event.currentTarget));
    	}
    };
    
    $scope.save = function (buttonDisable) { 
    	if($('#Form').valid()){
	    	if($scope.transDate1 != undefined && $scope.transDate1 != null && $scope.transDate1 != ''&& new Date(moment($scope.transDate1,'DD/MM/YYYY').format('YYYY-MM-DD')) .getTime() > new Date(moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime() ){
	    		$scope.Messager('error', 'Error', 'Transaction Date Should not be less than previous transaction date');
	    		//alert($scope.transDate1);
	     	    $scope.customerDetailsVo.transactionDate =  $scope.transDate1;
	    	}else if($scope.customerDetailsVo.totNumberOfCompanies == 0){
	    		$scope.Messager('error', 'Error', ' Total Number of Companies should not be 0');
	    	}else if(($scope.customerDetailsVo.isMultinaional == true || $scope.customerDetailsVo.isMultinaional == 'Yes') && ($scope.customerDetailsVo.totNumberOfCompanies == null ||  $scope.customerDetailsVo.totNumberOfCompanies == undefined || $scope.customerDetailsVo.totNumberOfCompanies == "" )){
	    		$scope.Messager('error', 'Error', 'Please enter total number of companies');
	    		
	    	}else {
	    		/*temporary workaround for material date picker*/
		        $scope.customerDetailsVo.transactionDate = $('#transactionDate').val();  
		        $scope.customerDetailsVo.customerCountriesList = $scope.customerDetails.customerCountriesList;
		       // alert(JSON.stringify($scope.customerDetailsVo.customerCountriesList));
		        $scope.customerDetailsVo.customerinfoId = 0;
		        $scope.customerDetailsVo.createdBy = $cookieStore.get('createdBy'); 
		    	$scope.customerDetailsVo.modifiedBy = $cookieStore.get('modifiedBy'); 
		        $scope.getData('CustomerController/saveCustomer.json', angular.toJson($scope.customerDetailsVo) , 'saveCustomer',buttonDisable);
		        // $(.resetBtn).click();    
	    	}
    	}
    };
    
   
    $scope.correctHistorySave= function(buttonDisable){
	   if($('#Form').valid()){
		   $scope.customerDetailsVo.createdBy = $cookieStore.get('createdBy'); 
		   $scope.customerDetailsVo.modifiedBy = $cookieStore.get('createdBy');
		   $scope.customerDetailsVo.transactionDate = $('#transactionDate').val();  
		   $scope.customerDetailsVo.customerCountriesList = $scope.customerDetails.customerCountriesList;       
		   //alert(angular.toJson($scope.customerDetailsVo));
		   $scope.getData('CustomerController/saveCustomer.json', angular.toJson($scope.customerDetailsVo) , 'saveCustomer',buttonDisable);
		}
    }
    
    
    
    $scope.saveCountryDetails = function(){   
    	
    	if($("#countryForm").valid()){
	    	var count = 0;
	    	var status = true;
	    	var status1 = true;
	    	if($scope.customerDetails.customerCountriesList == "" || $scope.customerDetails.customerCountriesList == null || $scope.customerDetails.customerCountriesList == undefined){
	    		if(($scope.customerCountry.noOfCompanies) > $scope.customerDetailsVo.totNumberOfCompanies){
					status = false;
				}
	    	}else{
		    	angular.forEach($scope.customerDetails.customerCountriesList , function(value,key){
		    		if($scope.customerCountry.countryId == value.countryId){
		    			status1 = false;
		    		}
		    		
		    		if(status1){
			    		if($scope.customerCountry.noOfCompanies != null && $scope.customerCountry.noOfCompanies != undefined && $scope.customerCountry.noOfCompanies != ""){
			    			count = count + parseInt(value.noOfCompanies);
			    		}
			    		if((count+ parseInt($scope.customerCountry.noOfCompanies)) > parseInt($scope.customerDetailsVo.totNumberOfCompanies)){
							status = false;
						}
		    		}
		    	});
	    	}
	    	
	    	if(!status){
	    		 $scope.Messager('error', 'Error', 'Sum of Companies should not be greater than Total No of Companies');
	    		 if(count == $scope.customerDetailsVo.totNumberOfCompanies){
	    			 $('div[id^="myModal"]').modal('hide');
	    		 }
	    	}else if(!status1){
	    		 $scope.Messager('error', 'Error', 'Selected country details already exist.');
	    	}else if($scope.customerCountry.noOfCompanies <= 0){
	    		 $scope.Messager('error', 'Error', 'Number of Companies should not be zero.');
	    	}else if($scope.customerCountry != null && $scope.customerCountry != undefined && $scope.customerCountry != ""){
		    	$scope.customerDetails.customerCountriesList.push({
		    		'SNo':$scope.customerCountry.index,    		
		    		'countryId':$scope.customerCountry.countryId,
		    		'countryName':$('#countryName option:selected').html() !='Select' ? $('#countryName option:selected').html() : '',
		    	//	'baseCompanyName':$scope.customerCountry.baseCompanyName,
		    		'noOfCompanies':$scope.customerCountry.noOfCompanies,
		    		'languageName':$('#languageName option:selected').html() != 'Select' ? $('#languageName option:selected').html() : '',
		    		'languageId':$scope.customerCountry.languageId,    		
		    		'currencyName':$('#currencyName option:selected').html() != 'Select' ? $('#currencyName option:selected').html() : '',
		    		'currencyId':$scope.customerCountry.currencyId,    		
		    	});   
		    	 $('div[id^="myModal"]').modal('hide');
		    	$scope.customerCountry.countryId = -1;
	    	}
    	}
    	//alert($scope.customerCountry.countryId);
    }
    
    $scope.Edit = function($event){    	
    	$scope.trIndex = $($event.target).parent().parent().index();
    	$scope.customerCountry = $scope.customerDetails.customerCountriesList[$($event.target).parent().parent().index()];
    	$scope.popUpSave = false;
    	$scope.popUpUpdate =true;
    }
    
    
    $scope.updateCountryDetails= function($event){
    	if($('#countryForm').valid()){
	    	$scope.customerDetails.customerCountriesList.splice($scope.trIndex,1); 
	    	var count = 0;
	    	var status = true;
	    	var status1 = true;
	    	if($scope.customerDetails.customerCountriesList == "" || $scope.customerDetails.customerCountriesList == null || $scope.customerDetails.customerCountriesList == undefined){
	    		if(($scope.customerCountry.noOfCompanies) > $scope.customerDetailsVo.totNumberOfCompanies){
					status = false;
				}
	    	}else{
		    	angular.forEach($scope.customerDetails.customerCountriesList , function(value,key){
		    		if($scope.customerCountry.countryId == value.countryId){
		    			status1 = false;
		    		}
		    		
		    		if(status1){
			    		if($scope.customerCountry.noOfCompanies != null && $scope.customerCountry.noOfCompanies != undefined && $scope.customerCountry.noOfCompanies != ""){
			    			count = count + parseInt(value.noOfCompanies);
			    		}
			    		if((count+ parseInt($scope.customerCountry.noOfCompanies)) > parseInt($scope.customerDetailsVo.totNumberOfCompanies)){
							status = false;
						}
		    		}
		    	});
	    	}
	    	
	    	if(!status){
	    		 $scope.Messager('error', 'Error', 'Sum of Companies should not be greater than Total No of Companies');
	    		 if(count == $scope.customerDetailsVo.totNumberOfCompanies){
	    			 $('div[id^="myModal"]').modal('hide');
	    		 }
	    	}else if(!status1){
	    		 $scope.Messager('error', 'Error', 'Selected country details already exist.');
	    	}else if($scope.customerCountry != null && $scope.customerCountry != undefined && $scope.customerCountry != ""){
		    	$scope.customerDetails.customerCountriesList.push({
		    		'SNo':$scope.customerCountry.index,    		
		    		'countryId':$scope.customerCountry.countryId,
		    		'countryName':$('#countryName option:selected').html() !='Select' ? $('#countryName option:selected').html() : '',
		    	//	'baseCompanyName':$scope.customerCountry.baseCompanyName,
		    		'noOfCompanies':$scope.customerCountry.noOfCompanies,
		    		'languageName':$('#languageName option:selected').html() != 'Select' ? $('#languageName option:selected').html() : '',
		    		'languageId':$scope.customerCountry.languageId,    		
		    		'currencyName':$('#currencyName option:selected').html() != 'Select' ? $('#currencyName option:selected').html() : '',
		    		'currencyId':$scope.customerCountry.currencyId,    		
		    	});   
		    	 $('div[id^="myModal"]').modal('hide');
		    	$scope.customerCountry.countryId = -1;
	    	}   	
    	}
    }
    
    
    
    $scope.Delete = function($event){    	
    	
    	var r = confirm("Do you want to delete the "+$scope.customerDetails.customerCountriesList[$($event.target).parent().parent().index()].countryName);    	
    	if(r){
    		$scope.customerDetails.customerCountriesList.splice($($event.target).parent().parent().index(),1);
    	}
    	//alert(JSON.stringify($scope.customerDetails.customerCountriesList));
    }
    
    
    

    $scope.updateBtnAction = function (this_obj) {
        $scope.readOnly = false;
        $scope.onlyRead = true;
        $scope.datesReadOnly = false;
        $scope.updateBtn = false;
        $scope.saveBtn = true;
        $scope.viewOrUpdateBtn = false;
        $scope.correcttHistoryBtn = false;
        $scope.resetBtn = false;
        $scope.cancelBtn = true;
        $scope.addrHistory = true;
        $scope.transactionList = false;  
        $scope.gridButtons = true;
        $scope.cancelBtn = true;
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
        $scope.correcttHistoryBtn = true;
        $scope.resetBtn = false;      
        $scope.transactionList = true;
        $scope.gridButtons = true;
        $scope.cancelBtn = false;
        $scope.getData('CustomerController/getTransactionListForEditingCustomerDetails.json', { customerId: $scope.customerDetailsVo.customerId }, 'getTransactionDates');      
        
        $('.dropdown-toggle').removeClass('disabled');
    }

    
    $scope.transactionDatesListChnage = function(){    
        $scope.getData('CustomerController/CustomerDtails.json', {customerInfoId:$scope.transactionModel}, 'customerList')
        $('.dropdown-toggle').removeClass('disabled');
    }
    
    
    
    $scope.cancelButnAction = function(){    	
    	 $scope.readOnly = true;
         $scope.datesReadOnly = true;
         $scope.updateBtn = true;
         $scope.saveBtn = false;
         $scope.viewOrUpdateBtn = true;
         $scope.correcttHistoryBtn = false;
         $scope.resetBtn = false;      
         $scope.transactionList = false;
         $scope.gridButtons = false;
         $scope.cancelBtn = false;
         $scope.getData('CustomerController/CustomerDtails.json', {customerInfoId: $routeParams.customerInfoId}, 'customerList')
    }
       
    $scope.plusIconAction = function(){
    	//alert($scope.customerCountry.countryId);
    	//$scope.clearPopupValues ();
    	$scope.popUpSave = true;
    	$scope.popUpUpdate =false;

    	$scope.customerCountry = new Object();

    }
    
    $scope.fun_renderTable = function(){
    	
    	if($scope.customerDetailsVo.totNumberOfCompanies != null && $scope.customerDetailsVo.totNumberOfCompanies != undefined && $scope.customerDetailsVo.totNumberOfCompanies != "" && $scope.customerDetailsVo.totNumberOfCompanies > 0){
	    	var value = ($scope.customerDetailsVo.isMultinaional == 'Yes' || $scope.customerDetailsVo.isMultinaional == true ) ? true : false;
	    	if(value ){    	    		
	    		$scope.countryTable = true;
	    		$scope.customerDetails.customerCountriesList = [];
	    		
	    	}else if($scope.customerDetails.customerCountriesList == undefined || $scope.customerDetails.customerCountriesList == "" || $scope.customerDetails.customerCountriesList == null){
	    		$scope.countryTable = false;
	    		$scope.customerDetails.customerCountriesList = [];
	    	}else{    	
	    		var multi = confirm("Are you sure you want to make these changes? ");
	    		if(multi){
		    		$scope.countryTable = false;
		    		$scope.customerDetails.customerCountriesList = [];
	    		}else{
	    			$scope.customerDetailsVo.isMultinaional = true;
	    		}
	    	} 
    	}else{
    		$scope.customerDetailsVo.isMultinaional = false;
    		alert("Please first enter Total No Of Companies.");
    		
    	}
    }
    
    
    $scope.clearPopupValues = function(){
    	   		
		$scope.customerCountry.countryId ="";		
		//$scope.customerCountry.baseCompanyName = null;
		$scope.customerCountry.noOfCompanies= null;
		$scope.customerCountry.languageId = '';    		
		$scope.customerCountry.currencyId = '';  
    	
    }
    
    
    
}]);
