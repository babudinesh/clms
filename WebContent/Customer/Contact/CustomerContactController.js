'use strict';

customerControlller.controller("customerContactDtls",  ['$scope', '$rootScope', '$http','$filter' , '$resource','$location','$cookieStore','myservice',function($scope, $rootScope, $http, $filter ,$resource,$location,$cookieStore,myservice) {  
    $.material.init();
    
    $scope.customerinfoId = $cookieStore.get('customerinfoId');
    
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
    
    $scope.customer = new Object();  
    $scope.customerName = $cookieStore.get('CustomercustomerName');
    $scope.customerCode = $cookieStore.get('CustomercustomerCode');
    $scope.customerId = $cookieStore.get('CustomerCustomerId');
    myservice.customerId = $cookieStore.get('CustomerCustomerId');
    $scope.customer.isActive = 'Y';
    $scope.transactionDate = $filter('date')(new Date(),"dd/MM/yyyy");
   // alert($scope.customer.transactionDate);
    
    
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
        	if(type == 'contactDetails'){        		
               // alert(JSON.stringify(response.data));
                $scope.list_contactTypes  = response.data.contactTypes;
                $scope.addressListData  = response.data.customerAddressList;
                //$('#transactionDate').val($filter('date')(new Date(),'dd/MM/yyyy'));
        	}else if(type == 'saveContact'){
        		if(response.data.id > 0){
        			$scope.Messager('success', 'success', 'Contact Details Saved Successfully',buttonDisable);
        			$location.path('/customerContact/'+response.data.id);
        		}else{
        			 $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
        		}
          	}                
          },
        function () {
        	  $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);  	  
          
          });
    	}   
    
   // alert(myservice.customerId);
    $scope.getData('CustomerController/CustomerContactMasterDetailsList.json', {customerId: myservice.customerId}, 'contactDetails') 
    
 // Save Customer Contact details
    $scope.fun_saveContactdetails = function($event){	
    	if($('#customerContact').valid()){
        $scope.customer.transactionDate = moment($('#txt_effectivedate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
        $scope.customer.customerId = myservice.customerId;
        $scope.customer.createdBy = $cookieStore.get('createdBy'); 
    	$scope.customer.modifiedBy = $cookieStore.get('modifiedBy'); 
    	
        $scope.getData('CustomerController/saveCustomerContact.json', angular.toJson($scope.customer), 'saveContact',angular.element($event.currentTarget))
    	}            
    }
    
}]);


customerControlller.controller("customerContactDtlsEditCtrl",  ['$scope', '$rootScope', '$http', '$filter' ,'$resource','$location','$routeParams','$cookieStore','myservice',function($scope, $rootScope, $http, $filter ,$resource,$location,$routeParams,$cookieStore,myservice) {  
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
    
    $scope.customerName = myservice.customerName;
    $scope.customerCode = myservice.customerCode;
    
    $scope.updateContactBtnAction = function(){       
        $scope.readonly = false;
        $scope.updateBtn = false;
        $scope.saveBtn = true;
        $scope.currentHistoryBtn = false;
        $scope.resetBtn = false;
        $scope.cancelBtn = true;
        $scope.addrHistory = false;
        $scope.transactionList = false; 
        //$scope.customer.transactionDate = $filter('date')(new Date(),"dd/MM/yyyy");
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
        	if(type == 'getContactDetailsById'){ 
        		//alert(JSON.stringify(response.data));
        		$scope.customer = response.data.contactVo;
        		
        		 $scope.list_contactTypes  = response.data.contactTypes;
                 $scope.addressListData  = response.data.customerAddressList;
                 myservice.customerId = response.data.contactVo.customerId;
                 // $cookieStore.put('customerId',response.data.contactVo.customerId);
                 $scope.transactionDate = $filter('date')( response.data.contactVo.transactionDate, 'dd/MM/yyyy');
               //alert($scope.customer.transactionDate);
        	}else if(type == 'getTransactionDates'){
        		//alert(response.data[0].id);
        		$scope.transactionDatesListForContact = response.data;        		        	
        	}else if(type == 'updateContact'){
        		
        		
        		$scope.Messager('success', 'success', 'Contact Details Saved Successfully',buttonDisable);
        		if($scope.saveBtn == true){
                	$location.path('/customerContact/'+response.data.id);
                }
        		//alert(JSON.stringify(response.data));
        		//alert("Company Contact Saved Successfully...");	
        	}
        },
        function () {
        	 $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
        	 
          
          });
    	}  
   // alert($routeParams.id);
    
    $scope.getData('CustomerController/getCustomerContactRecordById.json', {contactId :$routeParams.id ,customerId: myservice.customerId}, 'getContactDetailsById')
    
    
    $scope.historyBtnAction = function(){
        $scope.readonly = false;
        $scope.updateBtn = false;
        $scope.saveBtn = false;
        $scope.currentHistoryBtn = true;
        $scope.resetBtn = false;
        $scope.cancelBtn = false;
        $scope.addrHistory = false;
        $scope.transactionList = true;        
        $scope.getData('CustomerController/viewCustomerContactHistory.json', {customerId: myservice.customerId, contactUniqueId: $scope.customer.contactUniqueId}, 'getTransactionDates');
        $scope.getData('CustomerController/getCustomerContactRecordById.json', {contactId :$scope.customer.contactId,customerId: myservice.customerId}, 'getContactDetailsById');
        
    }
    
    
    // transactiondates change listener
    $scope.fun_Contact_Trans_Change = function(){
    	$scope.getData('CustomerController/getCustomerContactRecordById.json', {contactId :$scope.customer.contactId ,customerId: myservice.customerId}, 'getContactDetailsById')
           // $scope.getcompanyAddresRecord($scope.company.addressId);
    }
    
    
 // Save Customer Contact details
    $scope.fun_saveContactdetails = function($event){	
    	if($('#customerContact').valid()){      
        $scope.customer.transactionDate =moment($('#txt_effectivedate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
        $scope.customer.customerId = myservice.customerId;
        $scope.customer.contactId = null;
        $scope.customer.createdBy = $cookieStore.get('createdBy'); 
    	$scope.customer.modifiedBy = $cookieStore.get('modifiedBy'); 
        $scope.getData('CustomerController/saveCustomerContact.json', angular.toJson($scope.customer), 'updateContact',angular.element($event.currentTarget))
    	}
                    
    }
    
    $scope.fun_CorrectHistory = function($event){	
    	if($('#customerContact').valid()){
    	 $scope.customer.transactionDate = moment($('#txt_effectivedate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
         $scope.customer.customerId = myservice.customerId;     
         $scope.customer.createdBy = $cookieStore.get('createdBy'); 
     	$scope.customer.modifiedBy = $cookieStore.get('modifiedBy'); 
         $scope.getData('CustomerController/saveCustomerContact.json', angular.toJson($scope.customer), 'updateContact',angular.element($event.currentTarget))
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
        $scope.getData('CustomerController/CustomerAddressMasterDetailsList.json', {addressId :$scope.customer.addressId}, 'getAddressDetailsById');
        $scope.getData('CustomerController/getCustomerContactRecordById.json', {contactId :$scope.customer.contactId ,customerId: myservice.customerId}, 'getContactDetailsById');
        
    	
    }
    
}]);