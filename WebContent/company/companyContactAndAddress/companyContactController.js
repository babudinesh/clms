'use strict';

companyControllers.controller("companyContactDtls",  ['$scope', '$rootScope', '$http', '$resource','$location','$cookieStore','$filter','myservice',function($scope, $rootScope, $http,$resource,$location,$cookieStore,$filter,myservice) {  
    $.material.init();
					
	/*$('#txt_effectivedate').bootstrapMaterialDatePicker({
        time : false,
        Button : true,
        format : "DD/MM/YYYY"					
    });*/
	
	$( "#transactionDate" ).datepicker({
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
    $scope.returnToSearchhBtn = true;
    $scope.addrHistory = false;
    $scope.transactionList = false;
    
    myservice.customerId = $cookieStore.get('CompanyCustomerId');       
    myservice.companyId  = $cookieStore.get('CompanyCompanyId');
    myservice.companyName = $cookieStore.get('CompanycompanyName'); 
    myservice.companyCode = $cookieStore.get('CompanycompanyCode'); 
    myservice.countryName = $cookieStore.get('countryName'); 
    $scope.companyInfoId = $cookieStore.get('companyInfoId');
    
    $scope.company = new Object();
    $scope.company.companyId = myservice.companyId;
    $scope.company.customerId = myservice.customerId;
    $scope.companyName = myservice.companyName;
    $scope.companyCode = myservice.companyCode;
    $scope.countryName = myservice.countryName;
    $scope.transactionDate = $filter('date')(new Date(),'dd/MM/yyyy'); 
    $scope.company.isActive = 'Y';
    
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
    
    
    $http({
            url 	: ROOTURL+"CompanyController/getCurrentAddressByCmpId.json",
                            method 	: "POST",
                            data 	: {
                                        "customerId" : myservice.customerId,
                                        "companyId"	 : myservice.companyId
                            },
                            headers : { "Content-Type" : "application/json" }
                        }).then(function(response) {
                            $scope.addressListData = response.data;
                        }, function(response) {	
                            
                        });

     $http({
            url 	: ROOTURL+"CompanyController/getContactMasterInfo.json",
							method 	: "GET",							
							headers : { "Content-Type" : "application/json" }
						}).then(
						function(response) {		
							$scope.list_countryList  = response.data.countryList;
                            $scope.list_contactTypes  = response.data.contactTypes;
						},
						function(response) {
													
						});
    
      // Save company adress details
                    $scope.fun_savecompanycontactdetails = function($event){	
                    	if($('#companyContact').valid()){
                    		$scope.company.createdBy = $cookieStore.get('createdBy'); 
                    		$scope.company.modifiedBy = $cookieStore.get('modifiedBy');
                    		$scope.company.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
	                        console.log(angular.toJson($scope.company));       	                                        
	                        $http(
	                                {
	                                    url : ROOTURL+"CompanyController/saveCompanyContact.json",
	                                    method : "POST",
	                                        data : angular.toJson($scope.company),
	                                    headers : {
	                                        "Content-Type" : "application/json"
	                                    }
	                                })
	                                .then(function(response) {                                	
	                                	$scope.Messager('success', 'success', 'Company Contact Saved Successfully',angular.element($event.currentTarget)); 
	                                	if($scope.saveBtn == true)
	        	                        	$location.path('/companyContact/'+response.data.contactId);
	                                },
		                            function(response) {
		                            	$scope.Messager('error', 'Error', 'Technical Issue Please try after some time',angular.element($event.currentTarget));
		                            });  	                       
	                    }
                    }
    
    
}]);


companyControllers.controller("companyContactViewDtls",  ['$scope', '$rootScope', '$http', '$resource','$location','$routeParams','$cookieStore', '$filter', 'myservice',function($scope, $rootScope, $http,$resource,$location,$routeParams,$cookieStore,$filter,myservice) {  
    $.material.init();
					
	/*$('#txt_effectivedate').bootstrapMaterialDatePicker({
        time : false,
        Button : true,
        format : "DD/MM/YYYY"					
    });*/
	
	$( "#transactionDate" ).datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown'
	    	  
	    });
    $scope.readonly = true;
    $scope.updateBtn = true;
    $scope.saveBtn = false;
    $scope.currentHistoryBtn = false;
    $scope.resetBtn = false;
    $scope.cancelBtn = false;
    $scope.returnToSearchhBtn = true;
    $scope.addrHistory = true;
    $scope.transactionList = false;
    
    myservice.companyName = $cookieStore.get('CompanycompanyName'); 
    myservice.companyCode = $cookieStore.get('CompanycompanyCode'); 
    myservice.countryName = $cookieStore.get('countryName'); 
    myservice.customerId = $cookieStore.get('CompanyCustomerId');
    myservice.companyId = $cookieStore.get('CompanyCompanyId');
    $scope.companyName = myservice.companyName;
    $scope.companyCode = myservice.companyCode;
    $scope.countryName = myservice.countryName;
    $scope.companyInfoId = $cookieStore.get('companyInfoId');
    
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
    
    $scope.cancelContactBtnAction = function(){
    	$scope.readonly = true;
        $scope.updateBtn = true;
        $scope.saveBtn = false;
        $scope.currentHistoryBtn = false;
        $scope.resetBtn = false;
        $scope.cancelBtn = false;
        $scope.returnToSearchhBtn = true;
        $scope.addrHistory = true;
        $scope.transactionList = false;
        $scope.company.contactId = $routeParams.id;
        $scope.getContactRecordByContactId( $scope.company.contactId );      
        
    }
    
     $scope.updateContactBtnAction = function(){       
        $scope.readonly = false;
        $scope.updateBtn = false;
        $scope.saveBtn = true;
        $scope.currentHistoryBtn = false;
        $scope.resetBtn = false;
        $scope.cancelBtn = true;
        $scope.returnToSearchhBtn = true;
        $scope.addrHistory = false;
        $scope.transactionList = false;
        $scope.company.contactId =0;
        $scope.transactionDate = $filter('date')(new Date(),'dd/MM/yyyy'); 
    }
    
    $scope.historyContactBtnAction = function(){
        $scope.readonly = false;
        $scope.updateBtn = false;
        $scope.saveBtn = false;
        $scope.currentHistoryBtn = true;
        $scope.returnToSearchhBtn = true;
        $scope.resetBtn = false;
        $scope.cancelBtn = false;
        $scope.addrHistory = false;
        $scope.transactionList = true;
        $http({
							url 	: ROOTURL+"CompanyController/getTransactionDatesList.json",
							method 	: "POST",
							data 	: {
										"customerId" 		: myservice.customerId,
										"companyId"			: myservice.companyId,
                                        "contactUniqueId"   : $scope.company.contactUniqueId
							},
							headers : {
								"Content-Type" : "application/json"
							}
						}).then(
						function(response) {										
							$scope.transactionDatesListForContact= response.data;																													
						},
						function(response) {
							$scope.transactionDatesListForContact = "";							
						});	
        $scope.getContactRecordByContactId( $scope.company.contactId );  
        
    }
    
    
    $scope.fun_Contact_Trans_Change = function(){
    	if($scope.company.contactId != undefined && $scope.company.contactId != '' && $scope.company.contactId != null )
    		$scope.getContactRecordByContactId( $scope.company.contactId );        
    }        
    
    
     // GET Contact record by contactId
    $scope.getContactRecordByContactId = function( contactId_value){
         $http({
							url 	: ROOTURL+"CompanyController/getCmpContactByContactId.json",
							method 	: "POST",
							data 	: { contactId : contactId_value },
							async : false,
							headers : { "Content-Type" : "application/json" }
						}).then(function(response) {
                            $scope.company = response.data; 
                            $scope.transactionDate = $filter('date')( response.data.transactionDate, 'dd/MM/yyyy');
                            myservice.companyId = response.data.companyId ;
                            myservice.customerId = response.data.customerId;
                            $cookieStore.put('CompanyCustomerId',response.data.customerId);       
                            $cookieStore.put('CompanyCompanyId',response.data.companyId);
        });
    }
       
   
                                
      $http({
            url 	: ROOTURL+"CompanyController/getCurrentAddressByCmpId.json",
                            method 	: "POST",
                            data 	: {
                                        "customerId" : myservice.customerId,
                                        "companyId"	 : myservice.companyId
                            },
                            headers : { "Content-Type" : "application/json" }
                        }).then(function(response) {
                            $scope.addressListData = response.data; 
                            
                        }, function(response) {	
                            
                        });
    
    $http({
            url 	: ROOTURL+"CompanyController/getContactMasterInfo.json",
							method 	: "GET",							
							headers : { "Content-Type" : "application/json" }
						}).then(
						function(response) {									
                            $scope.list_contactTypes  = response.data.contactTypes;
                            $scope.getContactRecordByContactId($routeParams.id);
						},
						function(response) {
													
						});
     // Save company adress details
                    $scope.fun_savecompanycontactdetails = function($event){	
                    	if($('#companyContact').valid()){
                    		$scope.company.createdBy = $cookieStore.get('createdBy'); 
                    		$scope.company.modifiedBy = $cookieStore.get('modifiedBy'); 
                    		$scope.company.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
	                        console.log(angular.toJson($scope.company));       	                                        
	                        $http(
	                                {
	                                    url : ROOTURL+"CompanyController/saveCompanyContact.json",
	                                    method : "POST",
	                                        data : angular.toJson($scope.company),
	                                    headers : {
	                                        "Content-Type" : "application/json"
	                                    }
	                                })
	                                .then(function(response) {                                	
	                                	$scope.Messager('success', 'success', 'Company Contact Saved Successfully',angular.element($event.currentTarget));
	                                	if($scope.saveBtn == true)
	        	                        	$location.path('/companyContact/'+response.data.contactId);
	                                },
	                            function(response) {
	                            	$scope.Messager('error', 'Error', 'Technical Issue Please try after some time',angular.element($event.currentTarget));
	                            });	                       
	                    }
                    }
    
}]);