'use strict';

companyControllers.controller("companyDetailsCreateCtrl",  ['$scope','$rootScope', '$http', '$resource','$filter', '$location','$cookieStore','myservice',function($scope,$rootScope,$http,$resource,$filter,$location,$cookieStore,myservice) {  
        
    //initialsie the material-deisgn
    $.material.init();

   /*  $('#transactionDate,#registrationDate,#panRegistrationDate,#pfRegistrationDate,#pfStartDate,#esiRegistrationDate,#esiStartDate,#createdDate').bootstrapMaterialDatePicker
    ({
        time: false,
        clearButton: true,
        format : "DD/MM/YYYY"
    });*/
     
     
     $('#transactionDate').datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown',
	          	  
	    });
     
     $('#registrationDate,#panRegistrationDate,#pfRegistrationDate,#pfStartDate,#esiRegistrationDate,#esiStartDate,#createdDate').datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown',
	      maxDate: new Date()
	    	  
	    });
       
    $cookieStore.put('companyInfoId',0);
    $scope.updateBtn = false;
    $scope.saveBtn = true;
    $scope.currentHistoryBtn = false;
    $scope.resetBtn = true;
    $scope.cancelBtn = false;
    $scope.returnTOSearchBtn = true;
    $scope.addrHistory = false;
    $scope.company = new Object();
    $scope.transactionDate = $filter('date')(new Date(),'dd/MM/yyyy'); 
    $scope.company.isActive = 'Y';
    $scope.editCompany = false;
    $scope.newCompany = true;
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

 // for button views
    if($scope.buttonArray == undefined || $scope.buttonArray == '')
    $http(
			{
				url : ROOTURL+"CommonController/getUserScreenButtons.json" ,
				method : "POST",
				data : { userName: $cookieStore.get('userName'), screenName:'Company'},
				headers : {
					"Content-Type" : "application/json"
				}
			}).then(
			function(response) {										
				$scope.buttonArray = response.data.screenButtonNamesArray;
			},
			function(response) {					
				
			});	
    
   $http(
				{
					url : ROOTURL+"CompanyController/getMasterInfoForCompanyGrid.json" ,
					method : "POST",
					data : {"customerId": $cookieStore.get('customerId')},
					headers : {
						"Content-Type" : "application/json"
					}
				}).then(
				function(response) {										
					$scope.list_customerList = response.data.customerList;	
					 if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
	   		                $scope.company.customerDetailsId=response.data.customerList[0].customerId;		                
	   		                $scope.fun_Customer_Change();
	   		                }
				},
				function(response) {					
					
				});	
   
   
   
    $http(
					{
						url : ROOTURL+"CompanyController/getCompanyDetailsMasterInfoByCustomerd.json" ,
						async : false,	
						method : "POST",
						data : { "customerId" : ($scope.company == undefined || $scope.company.customerDetailsId == undefined )? '0' : $scope.company.customerDetailsId   },
						headers : {
							"Content-Type" : "application/json"
						}
					}).then(
					function(response) {																	
                        $scope.list_company_sectors = response.data.companySectors;
                        $scope.list_providentFundTypes = response.data.pfTypes;
                        $scope.list_registration_acts = response.data.regTypes;                                                                   
					},
					function(response) {														
                        $scope.list_company_sectors = "";	
                        $scope.list_providentFundTypes = "";	
                        $scope.list_registration_acts = "";	  
                       
					});
  
    
    $scope.fun_Customer_Change = function(){       
    $http(
					{
						url : ROOTURL+"CompanyController/getCompanyDetailsMasterInfoByCustomerd.json" ,
						async : false,	
						method : "POST",
						data : { "customerId" :  $scope.company.customerDetailsId },
						headers : {
							"Content-Type" : "application/json"
						}
					}).then(
					function(response) {																	
                        $scope.list_industries = response.data.industries;
                        $scope.list_countries = response.data.countries;                     
					},
					function(response) {														                      
                        $scope.list_industries = "";	
                        $scope.list_countries = "";	                      
					});
  
    }
    // For Industry Change Listener 
	$scope.fun_Industrychange = function(){
						
            if ($scope.company.industryId != "" && $scope.company.industryId > 0 && $scope.company.industryId != undefined ) {           
                    $http(
                            {
                                url : ROOTURL+"CompanyController/getSubIndustriesByCustomerId.json" ,
                                async : false,	
                                method : "POST",
                                data : {"industryId" :$scope.company.industryId, "customerId" : $scope.company.customerDetailsId        },
                                headers : {
                                    "Content-Type" : "application/json"
                                }
                            }).then(
                            function(response) {
                                $scope.list_sub_industries = response.data;																				
                            },
                            function(response) {
                                $scope.list_sub_industries = "";                               
                            });
            } else {
                $scope.list_sub_industries = "";
            }

	};
	
	    // Save Company Details
    $scope.fun_save_company_details = function($event){	
    	
    	if($('#CompanyDetails').valid()){
    		
    		var regex = new RegExp("[A-Za-z]{3}(P|p|C|c|H|h|F|f|A|a|T|t|B|b|L|l|J|j|G|j)[A-Za-z]{1}[0-9]{4}[A-Za-z]{1}$");
    		if(($('#pfRegistrationDate').val() != undefined || $('#pfRegistrationDate').val() != null) && ($('#pfStartDate').val() != undefined && $('#pfStartDate').val() != null)  && (new Date(moment($('#pfRegistrationDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime() >= new Date(moment($('#pfStartDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') ).getTime())){
      	    	$scope.Messager('error', 'Error', 'PF Registration Date should not be less than PF Start Date');
      	    	$scope.company.pfStartDate="";
        	}else if(($('#esiRegistrationDate').val() != undefined || $('#esiRegistrationDate').val() != null) && ($('#esiStartDate').val() != undefined && $('#esiStartDate').val() != null)  && (new Date(moment($('#esiRegistrationDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime() >= new Date(moment($('#esiStartDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime())){
      	    	$scope.Messager('error', 'Error', 'ESI Registration Date should not be less than ESI Start Date');
      	    	$scope.company.esiStartDate="";
        	}else if(($('#pfRegistrationDate').val() != undefined || $('#pfRegistrationDate').val() != null) && ($('#registrationDate').val() != undefined && $('#registrationDate').val() != null)  && (new Date(moment($('#registrationDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime() >= new Date(moment($('#pfRegistrationDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime())){
      	    	$scope.Messager('error', 'Error', 'PF Registration Date should not be less than Company Registration Date');
      	    	$scope.company.pfStartDate="";
    		}else if(($('#esiRegistrationDate').val() != undefined || $('#esiRegistrationDate').val() != null) && ($('#registrationDate').val() != undefined && $('#registrationDate').val() != null) && (new Date(moment($('#registrationDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime() >= new Date(moment($('#esiRegistrationDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime())){
      	    	$scope.Messager('error', 'Error', 'ESI Registration Date should not be less than Company Registration Date');
      	    	$scope.company.esiStartDate="";
    	  	}else if($scope.company.panNumber != undefined && $scope.company.panNumber != null && $scope.company.panNumber !='' && !(regex.test($scope.company.panNumber))){
    			$scope.Messager('error', 'Error', 'Please enter valid pan number');
 	  	 	}else if((($scope.company.address1 == undefined ||  $scope.company.address1 == "" || $scope.company.address1 == null) && ($scope.company.countryId == undefined || $scope.company.countryId == "" || $scope.company.countryId == null))){
    	  		$scope.Messager('error', 'Error', 'Please enter address');
    	  	}else if($scope.company.panNumber != undefined && $scope.company.panNumber != null && $scope.company.panNumber !='' && !(regex.test($scope.company.panNumber))){
    			$scope.Messager('error', 'Error', 'Please enter valid pan number');
 	  	 	}else if((($scope.company.address1 == undefined ||  $scope.company.address1 == "" || $scope.company.address1 == null) && ($scope.company.countryId == undefined || $scope.company.countryId == "" || $scope.company.countryId == null))){
    	  		$scope.Messager('error', 'Error', 'Please enter address');
    	  		
    	  	}else{
		    	$cookieStore.put('CompanyCustomerId',$scope.company.customerDetailsId);       
		        $cookieStore.put('CompanyCompanyId',$scope.company.companyDetailsId);
		        $cookieStore.put('CompanycompanyName',$scope.company.companyName);
		        $cookieStore.put('CompanycompanyCode',$scope.company.companyCode);
		        
		        myservice.customerId = $scope.company.customerDetailsId; 
		        myservice.companyId = $scope.company.companyDetailsId;     
		        myservice.companyName = $scope.company.companyName;
		        myservice.companyCode = $scope.company.companyCode;
		        
		        /*temporary fix for material datepicker*/	        
		        $scope.company.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
		        
		        if($('#registrationDate').val() != '' && $('#registrationDate').val() != undefined)
		        $scope.company.registrationDate = moment($('#registrationDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
		        
		        if($('#panRegistrationDate').val() != '' && $('#panRegistrationDate').val() != undefined)
		        $scope.company.panRegistrationDate = moment($('#panRegistrationDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
		        
		        if($('#pfStartDate').val() != '' && $('#pfStartDate').val() != undefined)
		        $scope.company.pfStartDate = moment($('#pfStartDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
		        
		        if($('#pfRegistrationDate').val() != '' && $('#pfRegistrationDate').val() != undefined)
		        $scope.company.pfRegistrationDate = moment($('#pfRegistrationDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
		        
		        if($('#esiStartDate').val() != '' && $('#esiStartDate').val() != undefined)
		        $scope.company.esiStartDate = moment($('#esiStartDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
		        
		        if($('#esiRegistrationDate').val() != '' && $('#esiRegistrationDate').val() != undefined)
		        $scope.company.esiRegistrationDate = moment($('#esiRegistrationDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
		        
		        $scope.company.createdBy = $cookieStore.get('createdBy'); 
		        $scope.company.modifiedBy = $cookieStore.get('modifiedBy'); 
		        
		        $http({
					url : ROOTURL+"CompanyController/saveCompanyDetails.json",
					method : "POST",
					data : angular.toJson($scope.company),
					headers : {
						"Content-Type" : "application/json"
					}
				}).then(function(response) {					
					/* $scope.company.transactionDate = $filter('date')( $scope.company.transactionDate, 'dd/MM/yyyy');
	                    $scope.company.registrationDate = $filter('date')( $scope.company.registrationDate, 'dd/MM/yyyy');
	                    $scope.company.panRegistrationDate = $filter('date')( $scope.company.panRegistrationDate, 'dd/MM/yyyy');
	                    $scope.company.pfStartDate = $filter('date')( $scope.company.pfStartDate, 'dd/MM/yyyy');
	                    $scope.company.pfRegistrationDate = $filter('date')( $scope.company.pfRegistrationDate, 'dd/MM/yyyy');
	                    $scope.company.esiStartDate = $filter('date')( $scope.company.esiStartDate, 'dd/MM/yyyy');
	                    $scope.company.esiRegistrationDate = $filter('date')( $scope.company.esiRegistrationDate, 'dd/MM/yyyy');*/
	                    /*myservice.customerId = response.data.customerId; 
	                    myservice.companyId = response.data.companyId;
	                    $cookieStore.put('customerId',response.data.customerId);       
	                    $cookieStore.put('companyId',response.data.companyId);*/
	                    $scope.Messager('success', 'success', 'Company Details Saved Successfully',angular.element($event.currentTarget));
	                    $cookieStore.put('companyInfoId',response.data.companyId);
	                    if($scope.saveBtn == true)
	                    	$location.path('/companyDetails/'+response.data.companyId);
	                   // $location.path('/companyProfile');
					},
				function(response) {
						$scope.Messager('error', 'Error', 'Technical Issue Please try after some time',angular.element($event.currentTarget));
				});
    	  	}
		}    
    }

}]);

// For Company Edit View Functionality 

companyControllers.controller("companyDetailsViewCtrl",  ['$scope', '$rootScope', '$http', '$filter', '$resource','$location','$routeParams','$cookieStore','myservice', function($scope,$rootScope, $http,$filter,$resource,$location,$routeParams,$cookieStore,myservice) {                   
    //initialsie the material-deisgn
    $.material.init();
     
     
/*    $('#transactionDate,#registrationDate,#panRegistrationDate,#pfRegistrationDate,#pfStartDate,#esiRegistrationDate,#esiStartDate,#createdDate').bootstrapMaterialDatePicker
    ({
        time: false,
        clearButton: true,
        format : "DD/MM/YYYY"
    });*/
    
    $('#transactionDate,#registrationDate,#panRegistrationDate,#pfRegistrationDate,#pfStartDate,#esiRegistrationDate,#esiStartDate,#createdDate').datepicker({
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
     // for button views
    if($scope.buttonArray == undefined || $scope.buttonArray == '')
    $http(
			{
				url : ROOTURL+"CommonController/getUserScreenButtons.json" ,
				method : "POST",
				data : { userName: $cookieStore.get('userName'), screenName:'Company'},
				headers : {
					"Content-Type" : "application/json"
				}
			}).then(
			function(response) {										
				$scope.buttonArray = response.data.screenButtonNamesArray;
			},
			function(response) {					
				
			});	
    $scope.updateBtn = true;
    $scope.saveBtn = false;
    $scope.currentHistoryBtn = false;
    $scope.resetBtn = false;
    $scope.cancelBtn = false;
    $scope.addrHistory = true;
    $scope.returnTOSearchBtn = true;
    $scope.readOnly = true;	
    $scope.datesReadOnly = true;
    $scope.transactionList = false;
    $cookieStore.put('companyInfoId', $routeParams.id );
    $scope.companyInfoId = $routeParams.id;
    $scope.editCompany = true;
    $scope.newCompany = false;
    
    $scope.updateCompanyBtnAction = function(this_obj) {
		$scope.readOnly = false;	
		$scope.datesReadOnly = false;
        $scope.updateBtn = false;
        $scope.saveBtn = true;
        $scope.returnTOSearchBtn = true;
        $scope.currentHistoryBtn = false;
        $scope.resetBtn = false;
        $scope.cancelBtn = true;
        $scope.addrHistory = false;
		$scope.transactionList = false;
        $scope.company.companyInfoId = 0;
		$('.dropdown-toggle').removeClass('disabled');		
		$scope.transactionDate=$filter('date')(new Date(),"dd/MM/yyyy");
	} 
	
    $scope.cancelBtnAction = function(){
    	$scope.updateBtn = true;
        $scope.saveBtn = false;
        $scope.currentHistoryBtn = false;
        $scope.resetBtn = false;
        $scope.cancelBtn = false;
        $scope.addrHistory = true;
        $scope.returnTOSearchBtn = true;
        $scope.readOnly = true;	
        $scope.datesReadOnly = true;
        $scope.transactionList = false;
        $scope.fun_getCompanyDetails( $routeParams.id );
    }
    
    $scope.historyBtnAction = function(this_obj) {
		$scope.readOnly = false;	
		$scope.datesReadOnly = false;
        $scope.updateBtn = false;
        $scope.saveBtn = false;
        $scope.currentHistoryBtn = true;
        $scope.resetBtn = false;
        $scope.returnTOSearchBtn = true;
        $scope.cancelBtn = false;
        $scope.addrHistory = false;	
        $scope.transactionList = true;
        $http(
				{
					url : ROOTURL+"CompanyController/getCompanyTransactionDates.json" ,
					method : "POST",
					data : {"customerId" : $scope.company.customerDetailsId, "companyId" : $scope.company.companyDetailsId },
					headers : {
						"Content-Type" : "application/json"
					}
				}).then(
				function(response) {										
					$scope.transactionDatesList = response.data;																										
				},
				function(response) {
					$scope.transactionDatesList = "";	
					
				});	
        
        $scope.fun_getCompanyDetails( $routeParams.id );
	} 
    
     $http(
            {
                url : ROOTURL+"CompanyController/getCompanyDetailsMasterInfoByCustomerd.json" ,
                async : false,	
                method : "POST",
                data : { "customerId" : ($scope.company == undefined || $scope.company.customerDetailsId == undefined )? '0' : $scope.company.customerDetailsId   },
                headers : {
                    "Content-Type" : "application/json"
                }
            }).then(
            function(response) {																	
                $scope.list_company_sectors = response.data.companySectors;
                $scope.list_providentFundTypes = response.data.pfTypes;
                $scope.list_registration_acts = response.data.regTypes;                                                                   
            },
            function(response) {														
                $scope.list_company_sectors = "";	
                $scope.list_providentFundTypes = "";	
                $scope.list_registration_acts = "";	 
               
            });
    
    // Transaction Dates Change Listener
    $scope.fun_Company_Trans_Change = function(){
       // console.log($scope.transactionModel);
        if($scope.transactionModel != undefined && $scope.transactionModel != '')
		$scope.fun_getCompanyDetails($scope.transactionModel);
	}
    
    $scope.fun_getCompanyDetails = function(company_info_Id){       
		 $http(
					{
						url : ROOTURL+"CompanyController/getCompanyDetailsByCompanyInfoId.json" ,
						async : false,	
						method : "POST",
						data : { "companyInfoId" :company_info_Id },
						headers : {
							"Content-Type" : "application/json"
						}
					}).then(
					function(response) {        
                       // console.log(angular.toJson(response.data));
						$scope.company = response.data;           
						$scope.transactionDate = $filter('date')( response.data.transactionDate, 'dd/MM/yyyy');
                        $scope.registrationDate = $filter('date')( response.data.registrationDate, 'dd/MM/yyyy');
                        $scope.panRegistrationDate = $filter('date')( response.data.panRegistrationDate, 'dd/MM/yyyy');
                        $scope.pfStartDate = $filter('date')( response.data.pfStartDate, 'dd/MM/yyyy');
                        $scope.pfRegistrationDate = $filter('date')( response.data.pfRegistrationDate, 'dd/MM/yyyy');
                        $scope.company.esiStartDate = $filter('date')( response.data.esiStartDate, 'dd/MM/yyyy');
                        $scope.esiRegistrationDate = $filter('date')( response.data.esiRegistrationDate, 'dd/MM/yyyy');
						
                        myservice.customerId = response.data.customerDetailsId; 
                        myservice.companyId = response.data.companyDetailsId; 
                        $scope.transactionModel = response.data.companyInfoId;
                        myservice.companyName = response.data.companyName;
                        myservice.companyCode = response.data.companyCode;                        
                        
                                               
                        $cookieStore.put('CompanyCustomerId',$scope.company.customerDetailsId);       
            	        $cookieStore.put('CompanyCompanyId',$scope.company.companyDetailsId);
            	        $cookieStore.put('CompanycompanyName',$scope.company.companyName);
            	        $cookieStore.put('CompanycompanyCode',$scope.company.companyCode);

                        // For  Dropdown List 
                            $http(
                                            {
                                                url : ROOTURL+"CompanyController/getCompanyDetailsMasterInfoByCustomerd.json" ,
                                                async : false,	
                                                method : "POST",
                                                data : { "customerId" : response.data.customerDetailsId   },
                                                headers : {
                                                    "Content-Type" : "application/json"
                                                }
                                            }).then(
                                            function(response) {																	
                                                $scope.list_industries = response.data.industries;
                                                $scope.list_countries = response.data.countries;   
                                                
                                            },
                                            function(response) {														                      
                                                $scope.list_industries = "";	
                                                $scope.list_countries = "";	
                                              
                                            });
                

                                    $http(
                                            {
                                                url : ROOTURL+"CompanyController/getSubIndustriesByCustomerId.json" ,
                                                async : false,	
                                                method : "POST",
                                                data : {"industryId" : response.data.industryId , "customerId" : response.data.customerDetailsId , "companyId" : response.data.companyDetailsId },
                                                headers : {
                                                    "Content-Type" : "application/json"
                                                }
                                            }).then(
                                            function(response) {
                                                $scope.list_sub_industries = response.data;																				
                                            },
                                            function(response) {
                                                $scope.list_sub_industries = ""; 
                                               
                                            });
                     
                        
            },function(response) {
						$scope.company = new Object();											                       
			}); 
            
    };
    
    // For getting Master Information Data
    $http(
				{
					url : ROOTURL+"CompanyController/getMasterInfoForCompanyGrid.json" ,
					method : "POST",	
					data : {"customerId": $cookieStore.get('customerId')},
					headers : {
						"Content-Type" : "application/json"
					}
				}).then(
				function(response) {										
					$scope.list_customerList = response.data.customerList;																													
				},
				function(response) {
					
			
				});	   
    // For getting Comapany Details By CompanyDetails Id
    $http(
					{
						url : ROOTURL+"CompanyController/getCompanyDetailsByCompanyInfoId.json" ,
						async : false,	
						method : "POST",
						data : { "companyInfoId" : $routeParams.id },
						headers : {
							"Content-Type" : "application/json"
						}
					}).then(
					function(response) {                      
						$scope.company = response.data;                                        
                        myservice.customerId = response.data.customerDetailsId; 
                        myservice.companyId = response.data.companyDetailsId; 
                        myservice.companyName = response.data.companyName;
                        myservice.companyCode = response.data.companyCode;
                        
                        $scope.transactionDate = $filter('date')( response.data.transactionDate, 'dd/MM/yyyy');
                        $scope.registrationDate = $filter('date')( response.data.registrationDate, 'dd/MM/yyyy');
                        $scope.panRegistrationDate = $filter('date')( response.data.panRegistrationDate, 'dd/MM/yyyy');
                        $scope.pfStartDate = $filter('date')( response.data.pfStartDate, 'dd/MM/yyyy');
                        $scope.pfRegistrationDate = $filter('date')( response.data.pfRegistrationDate, 'dd/MM/yyyy');
                        $scope.esiStartDate = $filter('date')( response.data.esiStartDate, 'dd/MM/yyyy');
                        $scope.esiRegistrationDate = $filter('date')( response.data.esiRegistrationDate, 'dd/MM/yyyy');
                        
                                               
                        $cookieStore.put('CompanyCustomerId',$scope.company.customerDetailsId);       
            	        $cookieStore.put('CompanyCompanyId',$scope.company.companyDetailsId);
            	        $cookieStore.put('CompanycompanyName',$scope.company.companyName);
            	        $cookieStore.put('CompanycompanyCode',$scope.company.companyCode);
                        
                        // For  Dropdown List 
                         $http(
                                {
                                    url : ROOTURL+"CompanyController/getCompanyDetailsMasterInfoByCustomerd.json" ,
                                    async : false,	
                                    method : "POST",
                                    data : { "customerId" : response.data.customerDetailsId },
                                    headers : {
                                        "Content-Type" : "application/json"
                                    }
                                }).then(
                                function(response) {
                                    $scope.list_company_sectors = response.data.companySectors;
                                    $scope.list_providentFundTypes = response.data.pfTypes;
                                    $scope.list_registration_acts = response.data.regTypes;
                                    $scope.list_industries = response.data.industries;
                                    $scope.list_countries = response.data.countries;                      
                                },
                                function(response) {                                   											
                                    $scope.list_company_sectors = "";	
                                    $scope.list_providentFundTypes = "";	
                                    $scope.list_registration_acts = "";	
                                    $scope.list_industries = "";	
                                    $scope.list_countries = "";							
                                });
                                             
                       
                            // For getting sub industry List 
                                if ($scope.company.industryId != response.data.industryId || $scope.list_sub_industries == "" || $scope.list_sub_industries  == undefined) {                                   
                                    $http(
                                            {
                                                url : ROOTURL+"CompanyController/getSubIndustriesByCustomerId.json" ,
                                                async : false,	
                                                method : "POST",
                                                data : {"industryId" : response.data.industryId , "customerId" : response.data.customerDetailsId , "companyId" : response.data.companyDetailsId },
                                                headers : {
                                                    "Content-Type" : "application/json"
                                                }
                                            }).then(
                                            function(response) {
                                                $scope.list_sub_industries = response.data;																				
                                            },
                                            function(response) {
                                                $scope.list_sub_industries = "";                                                
                                            });
                                } else {
                                    $scope.list_sub_industries = "";
                                }                                                                                        
					},
					function(response) {
						$scope.company = new Object();	
						
					});  
  
    // For Industry Change Listener
    $scope.fun_Industrychange = function(){	            
		if ($scope.company.industryId != "" && $scope.company.industryId > 0 && $scope.company.industryId != undefined ) {
			$http(
					{
						url : ROOTURL+"CompanyController/getSubIndustriesByCustomerId.json" ,
						async : false,	
						method : "POST",
						data : {"industryId" :$scope.company.industryId, "customerId" :  myservice.customerId, "companyId" :  myservice.companyId },
						headers : {
							"Content-Type" : "application/json"
						}
					}).then(
					function(response) {
						$scope.list_sub_industries = response.data;																		
					},
					function(response) {
						$scope.list_sub_industries = "";
						
					});
		} 

	};
    
    // Save Company Details
    $scope.fun_save_company_details = function($event){	
    	
    	var regex = new RegExp("[A-Za-z]{3}(P|p|C|c|H|h|F|f|A|a|T|t|B|b|L|l|J|j|G|j)[A-Za-z]{1}[0-9]{4}[A-Za-z]{1}$");
    
    	if($('#CompanyDetails').valid()){
    		if(($('#pfRegistrationDate').val() != undefined || $('#pfRegistrationDate').val() != null) && ($('#pfStartDate').val() != undefined && $('#pfStartDate').val() != null)  && (new Date(moment($('#pfRegistrationDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime() >= new Date(moment($('#pfStartDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') ).getTime())){
      	    	$scope.Messager('error', 'Error', 'PF Registration Date should not be less than PF Start Date');
      	    	$scope.company.pfStartDate="";
        	}else if(($('#esiRegistrationDate').val() != undefined || $('#esiRegistrationDate').val() != null) && ($('#esiStartDate').val() != undefined && $('#esiStartDate').val() != null)  && (new Date(moment($('#esiRegistrationDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime() >= new Date(moment($('#esiStartDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime())){
      	    	$scope.Messager('error', 'Error', 'ESI Registration Date should not be less than ESI Start Date');
      	    	$scope.company.esiStartDate="";
        	}else if(($('#pfRegistrationDate').val() != undefined || $('#pfRegistrationDate').val() != null) && ($('#registrationDate').val() != undefined && $('#registrationDate').val() != null)  && (new Date(moment($('#registrationDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime() >= new Date(moment($('#pfRegistrationDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime())){
      	    	$scope.Messager('error', 'Error', 'PF Registration Date should not be less than Company Registration Date');
      	    	$scope.company.pfStartDate="";
    		}else if(($('#esiRegistrationDate').val() != undefined || $('#esiRegistrationDate').val() != null) && ($('#registrationDate').val() != undefined && $('#registrationDate').val() != null) && (new Date(moment($('#registrationDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime() >= new Date(moment($('#esiRegistrationDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime())){
      	    	$scope.Messager('error', 'Error', 'ESI Registration Date should not be less than Company Registration Date');
      	    	$scope.company.esiStartDate="";
    	  	}else if($scope.company.panNumber != undefined && $scope.company.panNumber != null && $scope.company.panNumber !='' && !(regex.test($scope.company.panNumber))){
    			$scope.Messager('error', 'Error', 'Please enter valid pan number');
 	  	 	}else if((($scope.company.address1 == undefined ||  $scope.company.address1 == "" || $scope.company.address1 == null) && ($scope.company.countryId == undefined || $scope.company.countryId == "" || $scope.company.countryId == null))){
    	  		$scope.Messager('error', 'Error', 'Please enter address');
    	  	}else{
		        myservice.customerId = $scope.company.customerDetailsId; 
		        myservice.companyId = $scope.company.companyDetailsId;
		        myservice.companyName = $scope.company.companyName;
		        myservice.companyCode = $scope.company.companyCode;
		       	        
		        $cookieStore.put('CompanyCustomerId',$scope.company.customerDetailsId);       
		        $cookieStore.put('CompanyCompanyId',$scope.company.companyDetailsId);
		        $cookieStore.put('CompanycompanyName',$scope.company.companyName);
		        $cookieStore.put('CompanycompanyCode',$scope.company.companyCode);
		        
		        
		      /*temporary fix for material datepicker*/	        
		        if($('#registrationDate').val() != '' && $('#registrationDate').val() != undefined)
			        $scope.company.registrationDate = moment($('#registrationDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
			        
			    if($('#panRegistrationDate').val() != '' && $('#panRegistrationDate').val() != undefined)
			        $scope.company.panRegistrationDate = moment($('#panRegistrationDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
			        
			    if($('#pfStartDate').val() != '' && $('#pfStartDate').val() != undefined)
			        $scope.company.pfStartDate = moment($('#pfStartDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
			        
			    if($('#pfRegistrationDate').val() != '' && $('#pfRegistrationDate').val() != undefined)
			        $scope.company.pfRegistrationDate = moment($('#pfRegistrationDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
			        
			    if($('#esiStartDate').val() != '' && $('#esiStartDate').val() != undefined)
			        $scope.company.esiStartDate = moment($('#esiStartDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
			        
			    if($('#esiRegistrationDate').val() != '' && $('#esiRegistrationDate').val() != undefined)
			        $scope.company.esiRegistrationDate = moment($('#esiRegistrationDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
		        
		        
		        $scope.company.createdBy = $cookieStore.get('createdBy'); 
		        $scope.company.modifiedBy = $cookieStore.get('modifiedBy'); 
		        
				$http({
					url : ROOTURL+"CompanyController/saveCompanyDetails.json",
					method : "POST",
					data : angular.toJson($scope.company),
					headers : {
						"Content-Type" : "application/json"}
				}).then(function(response) {	
					
                       // myservice.customerId = response.data.customerId; 
                       // myservice.companyId = response.data.companyId; 
                       // $cookieStore.put('customerId',response.data.customerId);       
                       // $cookieStore.put('companyId',response.data.companyId);
                        $scope.Messager('success', 'success', 'Company Details Saved Successfully',angular.element($event.currentTarget)); 
                        $cookieStore.put('companyInfoId',response.data.companyId);
                        if($scope.saveBtn == true)
                        	$location.path('/companyDetails/'+response.data.companyId);
                       /* $scope.company.transactionDate = $filter('date')( $scope.company.transactionDate, 'dd/MM/yyyy');
                        $scope.company.registrationDate = $filter('date')( $scope.company.registrationDate, 'dd/MM/yyyy');
                        $scope.company.panRegistrationDate = $filter('date')( $scope.company.panRegistrationDate, 'dd/MM/yyyy');
                        $scope.company.pfStartDate = $filter('date')( $scope.company.pfStartDate, 'dd/MM/yyyy');
                        $scope.company.pfRegistrationDate = $filter('date')( $scope.company.pfRegistrationDate, 'dd/MM/yyyy');
                        $scope.company.esiStartDate = $filter('date')( $scope.company.esiStartDate, 'dd/MM/yyyy');
                        $scope.company.esiRegistrationDate = $filter('date')( $scope.company.esiRegistrationDate, 'dd/MM/yyyy');*/
					},function(response) {
						$scope.Messager('error', 'Error', 'Technical Issue Please try after some time',angular.element($event.currentTarget));
				});
    	  	}
		}
    }
    
    
 }]).directive('dateChangePicker', ['$compile', function ($compile) { 
    return {
        restrict : "A",
        replace: true,
        transclude: true,
        scope: true,
        require: 'ngModel',
        link: function (scope, elem, attrs, ngModel) {          
          scope.$watch(attrs.ngModel, function (newValue, oldValue) {                    
                if (newValue != undefined) {
                    // console.log(newValue+" :: "+attrs.ngModel);
                    elem.text(newValue);
                    //ngModel.$setViewValue(newValue);
                   // ngModel.$render();
                  
                }
            });
        }
    }
}]);  