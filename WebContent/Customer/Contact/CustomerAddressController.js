'use strict';

customerControlller.controller("customerAddressDtls",  ['$scope', '$rootScope', '$http', '$filter', '$resource','$location','$cookieStore','myservice',function($scope, $rootScope, $http, $filter, $resource,$location,$cookieStore,myservice) {  
    $.material.init();
		
    $scope.customerinfoId = $cookieStore.get('customerinfoId');
	/*$('#txt_effectivedate').bootstrapMaterialDatePicker({
        time : false,
        Button : true,
        format : "DD/MM/YYYY",
        clearButton: true
    });*/
	
	 $('#txt_effectivedate').datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown'
	    	  
	    });
	 
    $scope.readonly = false;
    $scope.updateBtn = false;
    $scope.saveBtn = true;
    $scope.currentHistoryBtn = false;
    $scope.resetBtn = true;
    $scope.cancelBtn = false;
    $scope.addrHistory = false;
    $scope.transactionList = false;
    $scope.validAddressType = true;
    $scope.customer = new Object();
    myservice.customerId = $cookieStore.get('CustomerCustomerId');
    $scope.customerinfoId = $cookieStore.get('customerinfoId');
    myservice.customerName = $cookieStore.get('CustomercustomerName');
    myservice.customerCode = $cookieStore.get('CustomercustomerCode');
    $scope.customerName = myservice.customerName;
    $scope.customerCode = myservice.customerCode;
    $scope.customerId = myservice.customerId;
    $scope.customer.isActive = 'Y';
    $scope.transactionDate = $filter('date')(new Date(),"dd/MM/yyyy");
    
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
            url:ROOTURL+url,
            headers: {
                'Content-Type': 'application/json'
            },
            data:data
        }
        $http(req).then(function (response) {
        	// alert(JSON.stringify(response.data));
        	if(type == 'addressDetails'){
        		$scope.result = response.data;
               // alert(JSON.stringify(response.data));
                $scope.list_countries  = response.data.countriesList;
                $scope.list_addressTypes  = response.data.addressTypes;
        	}else if(type == 'countryChnage'){
        		$scope.list_states = response.data;
				//$scope.list_cities = "";
        	}else if(type == 'addressChange'){
        		$scope.validAddressType = response.data;
        		if(!$scope.validAddressType){
        			$scope.Messager('error', 'Error', 'This address type already exists.' );
        		}
				//$scope.list_cities = "";
        	}else if(type == 'saveAddress'){
        		if(response.data.id > 0){
        			$scope.Messager('success', 'success', 'Address Details Saved Successfully',buttonDisable);
        			$location.path('/customerAddress/'+response.data.id);
        		}else{
        			 $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
        		}
        		//alert("Company Address Saved Successfully...");				
        	}
           
                
          },
        function () {
        	  $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
        	  if(type == 'countryChnage'){
        		  $scope.list_states = "";
				//  $scope.list_cities = "";
          	}
          
          });
    	}   
    
   // alert(myservice.customerId);
     $scope.getData('CustomerController/getCustomerAddressGridList.json', {customerId :myservice.customerId}, 'addressDetails') 
   
     $scope.getData('CustomerController/CustomerAddressMasterDetailsList.json', {customerId : $scope.customerId , addressId :0}, 'addressDetails') 
     
     
    $scope.fun_Countrychange = function() {
    	if ($scope.customer.countryId != "" && $scope.customer.countryId > 0) {
    	 $scope.getData('CommonController/statesListByCountryId.json', {countryId : $scope.customer.countryId}, 'countryChnage')    	 
    	}else{
    		$scope.list_states = "";
    	}
    	 
    }  ;
    
    $scope.fun_AddressChange = function(){
    	if ($scope.customer.addressTypeId != "" && $scope.customer.addressTypeId != undefined) {
       	 	$scope.getData('CustomerController/validateCustomerAddress.json', {customerId : $scope.customerId, addressTypeId:$scope.customer.addressTypeId}, 'addressChange');    	 
       	}
    }
    
    // Save Customer adress details
    $scope.fun_saveaddressdetails = function($event){	
    	if($('#customerAddress').valid()){
    		// alert(angular.toJson($scope.customer));    
    		if(!$scope.validAddressType){
    			 $scope.Messager('error', 'Error', 'This address type already exists.' );
    		}else{
		        $scope.customer.transactionDate = moment($('#txt_effectivedate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
		        $scope.customer.customerDetailsId = myservice.customerId;
		        $scope.customer.createdBy = $cookieStore.get('createdBy'); 
		    	$scope.customer.modifiedBy = $cookieStore.get('modifiedBy');
	    	
		    	$scope.getData('CustomerController/saveCustomerAddress.json', angular.toJson($scope.customer), 'saveAddress',angular.element($event.currentTarget))
    		}
		}        
    }
}]);

customerControlller.controller("customerAddressDtlsEdit",  ['$scope', '$rootScope', '$http','$filter', '$resource','$location','$routeParams','$cookieStore','myservice',function($scope, $rootScope, $http,$filter,$resource,$location,$routeParams,$cookieStore,myservice) {  
    $.material.init();
					
    $('#txt_effectivedate').datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown'
	    	  
	    });
	$scope.customer = new Object();
    $scope.readonly = true;
    $scope.updateBtn = true;
    $scope.saveBtn = false;
    $scope.currentHistoryBtn = false;
    $scope.resetBtn = false;
    $scope.cancelBtn = false;
    $scope.addrHistory = true;
    $scope.transactionList = false;
    myservice.customerId = $cookieStore.get('CustomerCustomerId');
    myservice.customerName = $cookieStore.get('CustomercustomerName');
    myservice.customerCode = $cookieStore.get('CustomercustomerCode');
    $scope.customerinfoId = $cookieStore.get('customerinfoId');
    
    
    $scope.customerName = myservice.customerName;
    $scope.customerCode = myservice.customerCode;
    $scope.customerId = myservice.customerId;
    
    $scope.updateAddressBtnAction = function(){       
        $scope.readonly = false;
        $scope.updateBtn = false;
        $scope.saveBtn = true;
        $scope.currentHistoryBtn = false;
        $scope.resetBtn = false;
        $scope.cancelBtn = true;
        $scope.addrHistory = false;
        $scope.transactionList = false;       
       $scope.transactionDate = $filter('date')(new Date(),"dd/MM/yyyy");
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
            url:ROOTURL+url,
            headers: {
                'Content-Type': 'application/json'
            },
            data:data
        }
        $http(req).then(function (response) {       	 
        	if(type == 'getAddressDetailsById'){      
        		
        		$scope.customer = response.data.addressVo[0];        		
        		 $scope.list_countries  = response.data.countriesList;
                 $scope.list_addressTypes  = response.data.addressTypes;
                 myservice.customerId = $scope.customer.customerDetailsId;
                 $scope.transactionDate = $filter('date')( response.data.addressVo[0].transactionDate, 'dd/MM/yyyy');
                 //alert($scope.customer.transactionDate);
                $scope.fun_Countrychange();
        	}else if(type == 'countryChnage'){
        		$scope.list_states = response.data;
        	}else if(type == 'getTransactionDates'){        		
        		$scope.list_transactionDatesList = response.data;        		        		
        	}else if(type == 'updateAddress'){
        		//alert(angular.toJson(response.data));
        		if($scope.saveBtn == true){
                	$location.path('/customerAddress/'+response.data.id);
                }
       		 $scope.Messager('success', 'success', 'Address Details Saved Successfully',buttonDisable);
     					
        	}
        },
        function () {
        	 $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
        	  if(type == 'countryChnage'){
        		  $scope.list_states = "";			
          	}
          
          });
    	}  
    
    
    $scope.getData('CustomerController/CustomerAddressMasterDetailsList.json', {customerId : $scope.customerId , addressId :$routeParams.id}, 'getAddressDetailsById')
    
    
    
    
     $scope.fun_Countrychange = function() {    	
    	if ($scope.customer.countryId != "" && $scope.customer.countryId > 0) {    	
    	 $scope.getData('CommonController/statesListByCountryId.json', {countryId : $scope.customer.countryId}, 'countryChnage')    	 
    	}else{
    		$scope.list_states = "";
    	}
    	 
    }  
    
    $scope.historyBtnAction = function(){
        $scope.readonly = false;
        $scope.updateBtn = false;
        $scope.saveBtn = false;
        $scope.currentHistoryBtn = true;
        $scope.resetBtn = false;
        $scope.cancelBtn = false;
        $scope.addrHistory = false;
        $scope.transactionList = true;        
        $scope.getData('CustomerController/getTransactionListForCustomerAddress.json', {customerId: myservice.customerId, addressUniqueId: $scope.customer.addressUniqueId}, 'getTransactionDates');
        $scope.getData('CustomerController/CustomerAddressMasterDetailsList.json', {customerId : $scope.customerId , addressId :$scope.customer.addressId}, 'getAddressDetailsById');
        
    }
    
    
    // transactiondates change listener
    $scope.fun_Address_Trans_Change = function(){
    	$scope.getData('CustomerController/CustomerAddressMasterDetailsList.json', {customerId : $scope.customerId , addressId :$scope.customer.addressId}, 'getAddressDetailsById')
           // $scope.getcompanyAddresRecord($scope.company.addressId);
    }
    
    
    $scope.fun_saveaddressdetails = function($event){	
    	
    	if($('#customerAddress').valid()){      
	        $scope.customer.transactionDate = moment($('#txt_effectivedate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
	        $scope.customer.addressId = null;
	        $scope.customer.createdBy = $cookieStore.get('createdBy'); 
	    	$scope.customer.modifiedBy = $cookieStore.get('modifiedBy');
	        $scope.getData('CustomerController/saveCustomerAddress.json', angular.toJson($scope.customer), 'updateAddress',angular.element($event.currentTarget))
		}
                    
    }
    
    $scope.fun_CorrectHistory = function($event){	
    	if($('#customerAddress').valid())
		{
        $scope.customer.transactionDate = moment($('#txt_effectivedate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
        $scope.customer.createdBy = $cookieStore.get('createdBy'); 
    	$scope.customer.modifiedBy = $cookieStore.get('modifiedBy');      
        $scope.getData('CustomerController/saveCustomerAddress.json', angular.toJson($scope.customer), 'updateAddress',angular.element($event.currentTarget))
		}
                    
    }
							    
   
    
    $scope.fun_cancelBtnCLick = function(){
    	$scope.readonly = true;
        $scope.updateBtn = true;
        $scope.saveBtn = false;
        $scope.currentHistoryBtn = false;
        $scope.resetBtn = false;
        $scope.cancelBtn = false;
        $scope.addrHistory = true;
        $scope.transactionList = false;
        $scope.getData('CustomerController/CustomerAddressMasterDetailsList.json', {customerId : $scope.customerId , addressId :$scope.customer.addressId}, 'getAddressDetailsById')
    	
    }
    
}]);