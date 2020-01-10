'use strict';

companyControllers.controller("companyAddressDtls",  ['$scope', '$rootScope', '$http', '$resource','$location','$cookieStore','$filter','myservice',function($scope, $rootScope, $http,$resource,$location,$cookieStore,$filter,myservice) {  
    $.material.init();
					
	/*$('#transactionDate').bootstrapMaterialDatePicker({
        time : false,
        Button : true,
        format : "DD/MM/YYYY",
        clearButton: true
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
    $scope.addrHistory = false;
    $scope.transactionList = false;
    $scope.returnToSearchBtn = true;
    myservice.customerId = $cookieStore.get('CompanyCustomerId');       
    myservice.companyId  = $cookieStore.get('CompanyCompanyId');
    myservice.companyName = $cookieStore.get('CompanycompanyName'); 
    myservice.companyCode = $cookieStore.get('CompanycompanyCode'); 
    myservice.countryName = $cookieStore.get('countryName'); 
    $scope.companyInfoId = $cookieStore.get('companyInfoId');
    $scope.company = new Object();
    $scope.company.companyDetailsId = myservice.companyId;
    $scope.company.customerDetailsId = myservice.customerId;
    $scope.companyName = myservice.companyName;
    $scope.companyCode = myservice.companyCode;
    $scope.countryName = myservice.countryName;
    $scope.company.addressId =0;
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
            url 	: ROOTURL+"CompanyController/getAddressMasterInfo.json",
							method 	: "GET",                            
							headers : { "Content-Type" : "application/json" }
						}).then(
						function(response) {		
                            //console.log(angular.toJson(response.data));
							$scope.list_countries  = response.data.countryList;
                            $scope.list_addressTypes  = response.data.addressTypes;
						},
						function(response) {
													
						});
    


                    // Country Change Listener
					$scope.fun_Countrychange = function() {
						if ($scope.company.countryId != "" && $scope.company.countryId > 0) {
							$http({
								url 	: ROOTURL+"CommonController/statesListByCountryId.json",
								method	: "POST",
								data 	: {"countryId" : $scope.company.countryId},
								headers : { "Content-Type" : "application/json" }
							}).then(function(response) {
								$scope.list_states = response.data;
								$scope.list_cities = "";																	
							},function(response) {
								$scope.list_states = "";
								$scope.list_cities = "";								
							});
						} else {
							$scope.list_states = "";
						}
					};

					// State Change Listener 
					$scope.fun_Statechange = function() {						
						if ($scope.company.stateId != "" && $scope.company.stateId > 0) {
							$http({
								url 	: ROOTURL+"CommonController/cityLisyByStateId.json", 
								method 	: "POST",
								data 	: {"stateId" :$scope.company.stateId},
								headers : { "Content-Type" : "application/json"}
							}).then(function(response) {
								$scope.list_cities = response.data;																				
							},
							function(response) {
								$scope.list_cities = "";								
							});
						} else {
							$scope.list_cities = "";
						}

					};

                    // Save company adress details
                    $scope.fun_saveaddressdetails = function($event){	
                    	if($('#companyAddress').valid()){
                    		
                    		$scope.company.createdBy = $cookieStore.get('createdBy'); 
                    		$scope.company.modifiedBy = $cookieStore.get('modifiedBy'); 
	                        //console.log(angular.toJson($scope.company));       
	                        $scope.company.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 	                        
	                        $http(
	                                {
	                                    url : ROOTURL+"CompanyController/saveCompanyAddress.json",
	                                    method : "POST",
	                                        data : angular.toJson($scope.company),
	                                    headers : {
	                                        "Content-Type" : "application/json"
	                                    }
	                                })
	                                .then(function(response) {
	                                	$scope.Messager('success', 'success', 'Company Address Saved Successfully',angular.element($event.currentTarget)); 
	                                	if($scope.saveBtn == true)
	        	                        	$location.path('/companyAddress/'+response.data.companyAddressId);
                                    },
                                function(response) {
                                	$scope.Messager('error', 'Error', 'Technical Issue Please try after some time',angular.element($event.currentTarget));
                                });  	                      
	                    }
                    }
    }]);

companyControllers.controller("companyAddressViewDtls",  ['$scope', '$rootScope', '$http', '$resource','$location','$routeParams','$cookieStore','$filter','myservice',function($scope, $rootScope, $http,$resource,$location,$routeParams,$cookieStore,$filter,myservice) {  
    $.material.init();
					
	/*$('#transactionDate').bootstrapMaterialDatePicker({
        time : false,
        Button : true,
        format : "DD/MM/YYYY",
        clearButton: true
    });*/
	
	$( "#transactionDate" ).datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown'
	    	  
	    });
	
	$scope.company = new Object();
    $scope.readonly = true;
    $scope.updateBtn = true;
    $scope.saveBtn = false;
    $scope.currentHistoryBtn = false;
    $scope.resetBtn = false;
    $scope.cancelBtn = false;
    $scope.addrHistory = true;
    $scope.transactionList = false;
    $scope.returnToSearchBtn = true;
    myservice.companyName = $cookieStore.get('CompanycompanyName'); 
    myservice.companyCode = $cookieStore.get('CompanycompanyCode'); 
    myservice.countryName = $cookieStore.get('countryName');
    $scope.companyInfoId = $cookieStore.get('companyInfoId');
    $scope.companyName = myservice.companyName;
    $scope.companyCode = myservice.companyCode;
    $scope.countryName = myservice.countryName;
    
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
    
    $scope.updateAddressBtnAction = function(){       
        $scope.readonly = false;
        $scope.updateBtn = false;
        $scope.saveBtn = true;
        $scope.currentHistoryBtn = false;
        $scope.resetBtn = false;
        $scope.cancelBtn = true;
        $scope.addrHistory = false;
        $scope.transactionList = false;
        $scope.company.addressId =0;
        $scope.transactionDate = $filter('date')(new Date(),'dd/MM/yyyy'); 
    }
    
    $scope.cancelBtnAction = function(){
    	$scope.readonly = true;
        $scope.updateBtn = true;
        $scope.saveBtn = false;
        $scope.currentHistoryBtn = false;
        $scope.resetBtn = false;
        $scope.cancelBtn = false;
        $scope.addrHistory = true;
        $scope.transactionList = false;
        $scope.returnToSearchBtn = true;
        $scope.company.addressId = $routeParams.id;
        $scope.getcompanyAddresRecord($routeParams.id);        
    }
    $scope.historyBtnAction = function(){
        $scope.readonly = false;
        $scope.updateBtn = false;
        $scope.saveBtn = false;
        $scope.currentHistoryBtn = true;
        $scope.resetBtn = false;
        $scope.cancelBtn = false;
        $scope.returnToSearchBtn = true;
        $scope.addrHistory = false;
        $scope.transactionList = true;
        $http({
							url 	: ROOTURL+"CompanyController/getTransactionListForCompanyAddress.json",
							method 	: "POST",
							data 	: {
										"customerId" 		: myservice.customerId,
										"companyId"			: myservice.companyId,
                                        "addressUniqueId"   : $scope.company.addressUniqueId
							},
							headers : {
								"Content-Type" : "application/json"
							}
						}).then(
						function(response) {										
							$scope.list_transactionDatesList = response.data;																														
						},
						function(response) {
							$scope.list_transactionDatesList = "";							
						});	
        $scope.getcompanyAddresRecord($scope.company.addressId);
    }
    // transactiondates change listener
    $scope.fun_Address_Trans_Change = function(){
    	if($scope.company.addressId  != undefined && $scope.company.addressId != '' && $scope.company.addressId > 0)
            $scope.getcompanyAddresRecord($scope.company.addressId);
    }
    
    
     $http({
            url 	: ROOTURL+"CompanyController/getAddressMasterInfo.json",
							method 	: "GET",							
							headers : { "Content-Type" : "application/json" }
						}).then(
						function(response) {	
							$scope.getcompanyAddresRecord($routeParams.id);
							$scope.list_countries  = response.data.countryList;
                            $scope.list_addressTypes  = response.data.addressTypes;                            
						},
						function(response) {
													
						});
                    

                    // To get company address nameand company code
        $scope.getcompanyAddresRecord  = function(addressId){ 
					$http({
						url 	: ROOTURL+"CompanyController/getCompanyAddressRecordById.json",
						method 	: "POST",
						data 	: { companyAddressId : addressId },
						async 	: false,									
						headers : { "Content-Type" : "application/json" }
					}).then(function(response) {	
						//alert(angular.toJson(response.data));
						$scope.company      = response.data;	
						$scope.transactionDate = $filter('date')( response.data.transactionDate, 'dd/MM/yyyy');
						myservice.companyId = $scope.company.companyDetailsId ;
                        myservice.customerId = $scope.company.customerDetailsId ;
                        $cookieStore.put('CompanyCustomerId',$scope.company.customerDetailsId);       
                        $cookieStore.put('CompanyCompanyId', $scope.company.companyDetailsId);
                        
                        $http({
									url 	: ROOTURL+"CommonController/statesListByCountryId.json",
									method  : "POST",
									data 	: {"countryId" : response.data.countryId},
									headers : { "Content-Type" : "application/json" }		
								}).then(function(response) {																	
									$scope.list_states = response.data;																	
								},		
								function(response) {
									$scope.list_states = "";
									$scope.list_cities = "";
                            });		
							
                        $http({
									url		: ROOTURL+"CommonController/cityLisyByStateId.json",
									method  : "POST",
									data  	: {"stateId" :response.data.stateId},
									headers : {
												"Content-Type" : "application/json"
									}
								}).then(function(response){																
										$scope.list_cities = response.data;
								},
								function(response) {
										$scope.list_cities = "";										
                            });					
					},
					function(response) {
						
					});
}
                    // Country Change Listener
					$scope.fun_Countrychange = function() {
						if ($scope.company.countryId != "" && $scope.company.countryId > 0) {
							$http({
								url 	: ROOTURL+"CommonController/statesListByCountryId.json",
								method	: "POST",
								data 	: {"countryId" : $scope.company.countryId},
								headers : { "Content-Type" : "application/json" }
							}).then(function(response) {
								$scope.list_states = response.data;
								$scope.list_cities = "";																	
							},function(response) {
								$scope.list_states = "";
								$scope.list_cities = "";								
							});
						} else {
							$scope.list_states = "";
						}
					};

					// State Change Listener 
					$scope.fun_Statechange = function() {						
						if ($scope.company.stateId != "" && $scope.company.stateId > 0) {
							$http({
								url 	: ROOTURL+"CommonController/cityLisyByStateId.json", 
								method 	: "POST",
								data 	: {"stateId" :$scope.company.stateId},
								headers : { "Content-Type" : "application/json"}
							}).then(function(response) {
								$scope.list_cities = response.data;																				
							},
							function(response) {
								$scope.list_cities = "";								
							});
						} else {
							$scope.list_cities = "";
						}

					};
					
					 $scope.fun_AddressChange = function(){
					    	if ($scope.customer.addressTypeId != "" && $scope.customer.addressTypeId != undefined) {
					    		$http({
									url 	: ROOTURL+"CustomerController/validateCompanyAddress.json", 
									method 	: "POST",
									data 	: {customerId : $scope.company.customerId, companyId: $scope.company.companyId, addressTypeId:$scope.company.addressTypeId},
									headers : { "Content-Type" : "application/json"}
								}).then(function(response) {
									$scope.validAddressType = response.data;
					        		if(!$scope.validAddressType){
					        			$scope.Messager('error', 'Error', 'This address type already exists.' );
					        		}																				
								},
								function(response) {
									$scope.Messager('error', 'Error', 'Technical Issues Occurred.Please try after some time.' );						
								});
							} 
					       	 	
					 }
    
                    // Save company adress details
                    $scope.fun_saveaddressdetails = function($event){	
                    	if($('#companyAddress').valid()){
                    		$scope.company.createdBy = $cookieStore.get('createdBy'); 
                    		$scope.company.modifiedBy = $cookieStore.get('modifiedBy'); 
                    		$scope.company.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
	                        //console.log(angular.toJson($scope.company));       	                                       
	                        $http(
	                                {
	                                    url : ROOTURL+"CompanyController/saveCompanyAddress.json",
	                                    method : "POST",
	                                        data : angular.toJson($scope.company),
	                                    headers : {
	                                        "Content-Type" : "application/json"
	                                    }
	                                })
	                                .then(function(response) {
	                                	
	                                	$scope.Messager('success', 'success', 'Company Address Saved Successfully',angular.element($event.currentTarget)); 
	                                	if($scope.saveBtn == true)
	        	                        	$location.path('/companyAddress/'+response.data.companyAddressId);
	                                    },
	                                function(response) {
                                    	$scope.Messager('error', 'Error', 'Technical Issue Please try after some time',angular.element($event.currentTarget));
	                                });  	                       
	                    }
                    }
    
}]);