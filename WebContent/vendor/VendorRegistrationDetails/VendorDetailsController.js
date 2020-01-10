'use strict';
var vendorControllers = angular.module("myApp.vendorList",[]);

vendorControllers.controller('vendorEditDetailsCtrl', ['$scope','$http', '$resource','$routeParams','$filter','$cookieStore','$location','$rootScope', function ($scope,$http, $resource, $routeParams,$filter,$cookieStore,$location,$rootScope) {
     //initialize the material-design
            $.material.init();
  /*   $('#transactionDate,#registrationDate,#panRegistrationDate,#pfRegistrationDate,#pfStartDate,#esiRegistrationDate,#esiStartDate,#createdDate').bootstrapMaterialDatePicker
                ({
                    time: false,
                    clearButton: true,
                    format : "DD/MM/YYYY"
                });*/
            
            $('#transactionDate, #statusDate').datepicker({
      	      changeMonth: true,
      	      changeYear: true,
      	      dateFormat:"dd/mm/yy",
      	      showAnim: 'slideDown'
      	    	  
      	    });
            
            $('#registrationDate,#vendorRegistrationDate').datepicker({
        	      changeMonth: true,
        	      changeYear: true,
        	      dateFormat:"dd/mm/yy",
        	      showAnim: 'slideDown',
        	      maxDate: '+0D'
        	    	  
        	    });
            
            
  /*   $('#transactionDate').bootstrapMaterialDatePicker
                ({
                    time: false,
                    clearButton: true,
                    format : "DD/MM/YYYY"
                });
     
     
     $('#registrationDate').bootstrapMaterialDatePicker
     ({
         time: false,
         clearButton: true,
         format : "DD/MM/YYYY",
         maxDate: new Date()
         
     });*/
     $scope.changeStatus = false;
     $scope.vendorDetailsVo = new Object();
     var vendorValidation;
     $scope.transactionDate = $filter('date')(new Date(),'dd/MM/yyyy'); 
     $scope.vendorDetailsVo.isActive = 'Y';
     $scope.transDate1 = new Object();
     $scope.vendorStatus1 = new Object();
     var roleName = $cookieStore.get('roleName');

     if(roleName != 'Vendor Admin'){
     	$scope.statusDisabled = false;
     }else{
     	$scope.statusDisabled = true;
     }
     
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
        
    } else {
        $scope.readOnly = false;
        $scope.datesReadOnly = false;
        $scope.updateBtn = false;
        $scope.saveBtn = true;       
        $scope.viewOrUpdateBtn = false;
        $scope.correcttHistoryBtn = false;
        $scope.resetBtn = true;
        $scope.transactionList = false;
        $scope.cancelBtn= false;
        $scope.vendorDetailsVo.vendorStatus = "New";
       // $scope.statusDisabled = true;
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

    
  

    $scope.vendorStatus_list = [{"id":"New", "name":"New"},
                                {"id":"Pending For Approval", "name":"Pending For Approval"},
                                {"id":"Validated", "name":"Validated"},
    							{"id":"Suspended", "name":"Suspended"},
    							{"id":"Debarred", "name":"Debarred"},
    							{"id":"Terminated", "name":"Terminated"}];
    
    
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
        	} else if (type == 'vendorList') {
                $scope.vendorList = response.data;
               
                if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
	                $scope.vendorDetailsVo.customerId=response.data.customerList[0].customerId;		                
	                $scope.customerChange();
	            }
            
                
                if(Object.prototype.toString.call(response.data.vendorDetailsVoList) === '[object Array]' &&  response.data.vendorDetailsVoList.length > 0 ){
                	
	                $scope.vendorDetailsVo = response.data.vendorDetailsVoList[0];
	                $scope.customerChange();
	                $scope.companyChange();
	                $scope.transactionDate =  $filter('date')($scope.vendorDetailsVo.transactionDate, 'dd/MM/yyyy'); 
	                $scope.transDate1 = $filter('date')($scope.vendorDetailsVo.transactionDate, 'dd/MM/yyyy'); 
	                $scope.registrationDate =  $filter('date')($scope.vendorDetailsVo.registrationDate, 'dd/MM/yyyy'); 
	                $scope.vendorRegistrationDate =  $filter('date')($scope.vendorDetailsVo.vendorRegistrationDate, 'dd/MM/yyyy'); 
	                if(response.data.vendorDetailsVoList[0].statusDate != null && response.data.vendorDetailsVoList[0].reasonForChange != null){
	                	$scope.changeStatus = true;
	                	$scope.statusDate =  $filter('date')($scope.vendorDetailsVo.statusDate, 'dd/MM/yyyy'); 
	                }else{
	                	$scope.changeStatus = false;
	                }
	                $scope.vendorStatus1 = response.data.vendorDetailsVoList[0].vendorStatus;
	                $scope.vendorDetailsVo.industyIds = JSON.parse(response.data.vendorDetailsVoList[0].industyIds);
	                         
	                var value = "";
	        		if( $scope.vendorDetailsVo.address1 != undefined &&  $scope.vendorDetailsVo.address1 != '')
	        			value += $scope.vendorDetailsVo.address1;
	        		if( $scope.vendorDetailsVo.address2 != undefined &&  $scope.vendorDetailsVo.address2 != '')
	        			value += ", "+$scope.vendorDetailsVo.address2;
	        		if( $scope.vendorDetailsVo.address3 != undefined &&  $scope.vendorDetailsVo.address3 != '')
	        			value += ", "+$scope.vendorDetailsVo.address3;        		
	        		if( $scope.vendorDetailsVo.cityName != undefined &&  $scope.vendorDetailsVo.cityName != ''){
	        			value += ", "+$scope.vendorDetailsVo.cityName; 
	        		}	        		
	        		if( $scope.vendorDetailsVo.stateName != undefined &&  $scope.vendorDetailsVo.stateName != ''){
	        			value += ", "+$scope.vendorDetailsVo.stateName; 	
	        		}
	        		if( $scope.vendorDetailsVo.countryName != undefined &&  $scope.vendorDetailsVo.countryName != ''){
	        			value += ", "+$scope.vendorDetailsVo.countryName; 	
	        		}
	        		if( $scope.vendorDetailsVo.pincode != undefined &&  $scope.vendorDetailsVo.pincode != '')
	        			value += ", "+$scope.vendorDetailsVo.pincode;
	        		
	        		value += ". ";
	        	        $scope.CompAdders = value;
	   	        
	                $scope.industryChange();
	                $scope.vendorId = response.data.vendorDetailsVoList[0].vendorId;
	               
	                $scope.countryChange();
	                $scope.stateChange(); 
	                
                }
                // for button views
                if($scope.buttonArray == undefined || $scope.buttonArray == '')
                $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Vendor'}, 'buttonsAction');
            }
            else if (type == 'customerChange') {
                $scope.companyList = response.data;
                if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
   	                $scope.vendorDetailsVo.companyId = response.data[0].id;
   	             $scope.companyChange();
   	                }
              
            }
            else if (type == 'companyChange') {    

                $scope.countriesList = response.data.countriesList; 
                $scope.locationList = response.data.locationList;
                $scope.vendorDetailsVo.countryId = response.data.countriesList[0].id
            } else if (type == 'countryChange') {
                $scope.statesList = response.data;
            } else if (type == 'stateChange') {
                $scope.cityList = response.data;            
            } else if (type == 'industryChange') {
                $scope.vendorDetailsVo.subIndustryList = response.data;
            } else if (type == 'saveVendor') {
            	if(response.data.id != undefined && response.data.id > 0){
                	$location.path('/vendorEditDetails/'+response.data.id);
                	$scope.Messager('success', 'success', 'Vendor Details Saved Successfully',buttonDisable);
                }else{
                	$scope.Messager('error', 'Error', response.data.name,buttonDisable);
                }
            	 
            	
            } else if (type == 'transactionDatesChnage') {
               // alert(angular.toJson(response.data));
                $scope.transactionDatesList = response.data;
                $scope.transactionModel= parseInt($routeParams.id);
                $scope.getData('vendorController/getVednorDetailsById.json', {vendorInfoId:$scope.transactionModel, customerId: ""}, 'vendorList')
            }else if (type == 'validate') {
            	//console.log(response.data);
            	vendorValidation = response.data;
            }else if(type == 'getVendorInfoId'){
            	//console.log(angular.toJson(response.data));
            	 $scope.getData('vendorController/getVednorDetailsById.json', { vendorInfoId: response.data[0].vendorDetailsInfoId, customerId: $cookieStore.get('customerId') }, 'vendorList')
            	// for button views
                 if($scope.buttonArray == undefined || $scope.buttonArray == '')
                 $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Vendor Details'}, 'buttonsAction');
            }
        },
        function () {
        	$scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
        });
    }
    

    if($rootScope.roleNamesArray.includes('Vendor Admin')){
    	$scope.getData('vendorController/VendorGridDetails.json', { customerId: $cookieStore.get('customerId'), companyId: $cookieStore.get('companyId'),vendorId: $cookieStore.get('vendorId'),status:"Validated" }, 'getVendorInfoId');
    }else{
    	$scope.getData('vendorController/getVednorDetailsById.json', { vendorInfoId: $routeParams.id, customerId: $cookieStore.get('customerId') }, 'vendorList');
    }
    
    $scope.customerChange = function () { 
    	$scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.vendorDetailsVo.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.vendorDetailsVo.companyId != undefined ? $scope.vendorDetailsVo.companyId : 0 }, 'customerChange');
    };
    
    $scope.companyChange = function () {
    	//alert($scope.vendorDetailsVo.companyId+"::in company CHnage");
    	if($scope.vendorDetailsVo.companyId != undefined && $scope.vendorDetailsVo.companyId != "" && $scope.vendorDetailsVo.customerId != undefined && $scope.vendorDetailsVo.customerId !=""){
    		$scope.getData('CompanyController/getLocationsByCompanyId.json', { companyId: $scope.vendorDetailsVo.companyId, customerId: $scope.vendorDetailsVo.customerId  }, 'companyChange');
    	}
    };
    
    $scope.countryChange = function () {
      // alert($scope.vendorDetailsVo.country+"::country"); 
    	if($scope.vendorDetailsVo.country != null && $scope.vendorDetailsVo.country != ""){
    		$scope.getData('CommonController/statesListByCountryId.json', { countryId: $scope.vendorDetailsVo.country }, 'countryChange');
    	}
    };
    $scope.stateChange = function () {
    	if($scope.vendorDetailsVo.state != null && $scope.vendorDetailsVo.state != ""){
        $scope.getData('CommonController/cityLisyByStateId.json', { stateId: $scope.vendorDetailsVo.state }, 'stateChange');
    	}
    };
    $scope.industryChange = function () {
    	if($scope.vendorDetailsVo.industyIds != undefined && $scope.vendorDetailsVo.industyIds != ""){
    		 $scope.getData('vendorController/getsubIndustryList.json', { industryId: $scope.vendorDetailsVo.industyIds }, 'industryChange');
    	}
       
    }
    
    if($routeParams.id > 0){
	    $scope.statusChange = function($event){	    	
	    	if($event == 'New'){
	    		$scope.changeStatus = true;
	    		//$scope.Messager('warn', 'Warn', "Status cannot be modified to New.");
	    		$scope.vendorDetailsVo.vendorStatus= $scope.vendorStatus1;
	    	}else{
		    	var r = confirm("Do you really want to change the "+$event+" status");    
		    	if(r){
		    		$scope.vendorDetailsVo.vendorStatus= $event;
		    		$scope.changeStatus = true;
		    	}else{
		    		$scope.vendorDetailsVo.vendorStatus= $scope.vendorStatus1;
		    		$scope.changeStatus = false;
		    	}
	    	}
	    };
    }
    
    $scope.save = function ($event) {   
    	//alert($scope.vendorStatus1);
    	var regex = new RegExp("[A-Za-z]{3}(P|p|C|c|H|h|F|f|A|a|T|t|B|b|L|l|J|j|G|j)[A-Za-z]{1}[0-9]{4}[A-Za-z]{1}$");
    	 $scope.vendorDetailsVo.registrationDate = moment($('#registrationDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
	        $scope.vendorDetailsVo.vendorRegistrationDate = moment($('#vendorRegistrationDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
    	if($('#vendorRegistrationDetails').valid()){
    		if($scope.vendorDetailsVo.employeeStrength != undefined && $scope.vendorDetailsVo.employeeStrength !='' && isNaN($scope.vendorDetailsVo.employeeStrength)){
	  			$scope.Messager('error', 'Error', 'Please enter valid Employee Strength');
	  			return;
	  		}
    		if($scope.vendorDetailsVo.discounts != undefined && $scope.vendorDetailsVo.discounts !='' && isNaN($scope.vendorDetailsVo.discounts)){
    			$scope.Messager('error', 'Error', 'Please enter valid Discounts');
    			return;
    		}
    		if($scope.vendorDetailsVo.lastYearSalesTurnover != undefined && $scope.vendorDetailsVo.lastYearSalesTurnover !='' && isNaN($scope.vendorDetailsVo.lastYearSalesTurnover)){
    			$scope.Messager('error', 'Error', 'Please enter valid Last Year Sales Turnover');
    			return;
    		}
    		
    		if(!vendorValidation){
    			$scope.Messager('error', 'Error', 'Vendor Code already exists');    			
		    }else if($scope.transDate1 != '' && $scope.transDate1 != undefined && new Date(moment($scope.transDate1,'DD/MM/YYYY').format('YYYY-MM-DD')) .getTime() > new Date(moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime()){
     		    $scope.Messager('error', 'Error', 'Transaction Date Should not be less than previous transaction date');
        	    $scope.vendorDetailsVo.transactionDate =  $scope.transDate1;
        	   
    	    }else if($scope.vendorDetailsVo.panNumber != undefined && $scope.vendorDetailsVo.panNumber != null && $scope.vendorDetailsVo.panNumber !='' && !(regex.test($scope.vendorDetailsVo.panNumber))){
	 			$scope.Messager('error', 'Error', 'Please enter valid pan number');
	 	  	  		
 		    }else if(!($scope.vendorDetailsVo.address1 != undefined && $scope.vendorDetailsVo.address1 != null  &&  $scope.vendorDetailsVo.address1 != "" && $scope.vendorDetailsVo.country != undefined && $scope.vendorDetailsVo.country != "" )){
 			    $scope.Messager('error', 'Error', 'Please enter address',angular.element($event.currentTarget));
 		   
 		    }else if($scope.vendorStatus1 != undefined && $scope.vendorStatus1 != "" && $scope.vendorStatus1 != null && $scope.vendorStatus1 != "New" && $scope.vendorDetailsVo.vendorStatus == "New" && $routeParams.id > 0){
 			    $scope.Messager('error', 'Error', "Status cannot be modified to New .");

 		    }else if($('#vendorRegistrationDate').val() != '' && $('#vendorRegistrationDate').val() != undefined && $('#registrationDate').val() != '' && $('#registrationDate').val() != undefined && !moment($scope.vendorDetailsVo.vendorRegistrationDate).isSameOrAfter($scope.vendorDetailsVo.registrationDate)){
	        	$scope.Messager('error', 'Error', 'Vendor registration date should not be earlier than company registration date');
	        	
		    }/*else if($scope.vendorStatus1 != undefined && $scope.vendorStatus1 != "" && $scope.vendorStatus1 != null && $scope.vendorStatus1 == "Validated" &&( $scope.vendorDetailsVo.vendorStatus == "New" || $scope.vendorDetailsVo.vendorStatus == "Pending For Approval")){
 			    $scope.Messager('error', 'Error', "Validated status can't be modified to New/Pending For Approval status.");

 		    }else if($scope.vendorStatus1 != undefined && $scope.vendorStatus1 != "" && $scope.vendorStatus1 != null && $scope.vendorStatus1 == "Pending For Approval" &&( $scope.vendorDetailsVo.vendorStatus == "New" || $scope.vendorDetailsVo.vendorStatus == "Validated")){
 			    $scope.Messager('error', 'Error', "Pending For Approval status can't be modified to New/Validated status.");

 		    }else if($scope.vendorStatus1 != undefined && $scope.vendorStatus1 != "" && $scope.vendorStatus1 != null && $scope.vendorStatus1 == "New" &&( $scope.vendorDetailsVo.vendorStatus == "Pending For Approval" || $scope.vendorDetailsVo.vendorStatus == "Validated")){
 			    $scope.Messager('error', 'Error', "New status can't be modified to Pending For Approval/Validated status.");

 		    }*/else{
 			    $scope.vendorDetailsVo.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
		        $scope.vendorDetailsVo.registrationDate = moment($('#registrationDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
		        $scope.vendorDetailsVo.vendorregistrationDate = moment($('#vendorRegistrationDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
	 			   if($('#statusDate') != null && $('#statusDate') != undefined && $('#statusDate') != '')
	 				  $scope.vendorDetailsVo.statusDate = moment($('#statusDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 

		        $scope.vendorDetailsVo.createdBy = $cookieStore.get('createdBy'); 
		        $scope.vendorDetailsVo.modifiedBy = $cookieStore.get('modifiedBy'); 
		    	$scope.vendorDetailsVo.vendorDetailsInfoId = 0;
		    //	alert(angular.toJson($scope.vendorDetailsVo));
		        $scope.getData('vendorController/saveVendorDetails.json', angular.toJson($scope.vendorDetailsVo) , 'saveVendor',angular.element($event.currentTarget));
  		 
 		    }
    	}
        
    };
    
    $scope.correctHistorySave= function($event){
    	var regex = new RegExp("[A-Za-z]{3}(P|p|C|c|H|h|F|f|A|a|T|t|B|b|L|l|J|j|G|j)[A-Za-z]{1}[0-9]{4}[A-Za-z]{1}$");

 	   if($('#vendorRegistrationDetails').valid()){
 		  if($scope.vendorDetailsVo.employeeStrength != undefined && $scope.vendorDetailsVo.employeeStrength !='' && isNaN($scope.vendorDetailsVo.employeeStrength)){
	  			$scope.Messager('error', 'Error', 'Please enter valid Employee Strength');
	  			return;
	  		}
 		   if($scope.vendorDetailsVo.discounts != undefined && $scope.vendorDetailsVo.discounts !='' && isNaN($scope.vendorDetailsVo.discounts)){
	  			$scope.Messager('error', 'Error', 'Please enter valid Discounts');
	  			return;
	  		}
	  		if($scope.vendorDetailsVo.lastYearSalesTurnover != undefined && $scope.vendorDetailsVo.lastYearSalesTurnover !='' && isNaN($scope.vendorDetailsVo.lastYearSalesTurnover)){
	  			$scope.Messager('error', 'Error', 'Please enter valid Last Year Sales Turnover');
	  			return;
	  		}
 		   if($scope.vendorDetailsVo.panNumber != undefined && $scope.vendorDetailsVo.panNumber != null && $scope.vendorDetailsVo.panNumber !='' && !(regex.test($scope.vendorDetailsVo.panNumber))){
 			   $scope.Messager('error', 'Error', 'Please enter valid pan number');
  	  		
		    }else if(!($scope.vendorDetailsVo.address1 != undefined && $scope.vendorDetailsVo.address1 != null  &&  $scope.vendorDetailsVo.address1 != "" && $scope.vendorDetailsVo.country != undefined && $scope.vendorDetailsVo.country != "" )){
 			    $scope.Messager('error', 'Error', 'Please enter address',angular.element($event.currentTarget));
 		   
 		    }else if($scope.vendorStatus1 != undefined && $scope.vendorStatus1 != "" && $scope.vendorStatus1 != null && $scope.vendorStatus1 != "New" && $scope.vendorDetailsVo.vendorStatus == "New" ){
 			    $scope.Messager('error', 'Error', "Status cannot be modified to New.");

 		    }else if($('#vendorRegistrationDate').val() != '' && $('#vendorRegistrationDate').val() != undefined && $('#registrationDate').val() != '' && $('#registrationDate').val() != undefined && !moment($scope.vendorDetailsVo.vendorRegistrationDate).isSameOrAfter($scope.vendorDetailsVo.registrationDate)){
	        	$scope.Messager('error', 'Error', 'Vendor registration date should not be earlier than company registration date');
	        	
		    }else{
 			   $scope.vendorDetailsVo.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
 			   $scope.vendorDetailsVo.registrationDate = moment($('#registrationDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
 			   $scope.vendorDetailsVo.vendorregistrationDate = moment($('#vendorRegistrationDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
 			   if($('#statusDate') != null && $('#statusDate') != undefined && $('#statusDate') != '')
 				  $scope.vendorDetailsVo.statusDate = moment($('#statusDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
 			   
 			   $scope.vendorDetailsVo.createdBy = $cookieStore.get('createdBy'); 
 			   $scope.vendorDetailsVo.modifiedBy = $cookieStore.get('modifiedBy');	    	
	 	       $scope.getData('vendorController/saveVendorDetails.json', angular.toJson($scope.vendorDetailsVo) , 'saveVendor',angular.element($event.currentTarget));
  		 
 		  }
 		}
    };
    
    $scope.saveAddress = function(){
      	var value = "";
      	
      	if($scope.vendorDetailsVo.address1 != undefined &&  $scope.vendorDetailsVo.address1 != "" && $scope.vendorDetailsVo.address1 != null && $scope.vendorDetailsVo.country != undefined &&  $scope.vendorDetailsVo.country != ""){
			if( $scope.vendorDetailsVo.address1 != undefined &&  $scope.vendorDetailsVo.address1 != '')
				value += $scope.vendorDetailsVo.address1;
			if( $scope.vendorDetailsVo.address2 != undefined &&  $scope.vendorDetailsVo.address2 != '')
				value += ", "+$scope.vendorDetailsVo.address2;
			if( $scope.vendorDetailsVo.address3 != undefined &&  $scope.vendorDetailsVo.address3 != '')
				value += ", "+$scope.vendorDetailsVo.address3;					
			if( $scope.vendorDetailsVo.city != undefined &&  $scope.vendorDetailsVo.city != ''){
				value += ", "+$scope.vendorDetailsVo.city;
			}
			if( $scope.vendorDetailsVo.state != undefined &&  $scope.vendorDetailsVo.state != ''){
				value += ", "+$("#Select4 option:selected").html();			
			}
			if( $scope.vendorDetailsVo.country != undefined &&  $scope.vendorDetailsVo.country != ''){
				value += ", "+$("#Select3 option:selected").html();			
			}
			if( $scope.vendorDetailsVo.pincode != undefined &&  $scope.vendorDetailsVo.pincode != ''){
				value += ", "+$scope.vendorDetailsVo.pincode;
      		}
			value += "."
			$('div[id^="myModal"]').modal('hide');
		        $scope.CompAdders = value;
      	}else if(($scope.vendorDetailsVo.address1 == undefined ||  $scope.vendorDetailsVo.address1 == "" || $scope.vendorDetailsVo.address1 == null) && $scope.vendorDetailsVo.country == undefined ||  $scope.vendorDetailsVo.country == ""){
      		 $scope.Messager('error', 'Error', 'Please fill all required fields');
      	}else if($scope.vendorDetailsVo.address1 == undefined ||  $scope.vendorDetailsVo.address1 == "" || $scope.vendorDetailsVo.address1 == null){
			   $scope.Messager('error', 'Error', 'Please enter address line 1');
      	}else if($scope.vendorDetailsVo.country == undefined ||  $scope.vendorDetailsVo.country == "" ){
			   $scope.Messager('error', 'Error', 'Please select country');

      	}
       
        
    }

    $scope.updateVendorBtnAction = function (this_obj) {
        $scope.readOnly = false;
        $scope.onlyRead = true;
        $scope.datesReadOnly = false;
        $scope.updateBtn = false;        
        $scope.viewOrUpdateBtn = false;
        $scope.correcttHistoryBtn = false;
        $scope.resetBtn = false;
        $scope.cancelBtn = true;
        $scope.addrHistory = true;
        $scope.transactionList = false;       
        $('.dropdown-toggle').removeClass('disabled');
        vendorValidation = true;
        $scope.transactionDate = $filter('date')(new Date(),'dd/MM/yyyy');      
     
        	$scope.saveBtn = true;
      

    }

    $scope.viewOrEditHistory = function () {
        $scope.readOnly = false;
        $scope.onlyRead = true;
        $scope.datesReadOnly = false;
        $scope.updateBtn = false;
        $scope.saveBtn = false;
        $scope.viewOrUpdateBtn = false;       
        $scope.resetBtn = false;      
        $scope.transactionList = true;
        $scope.cancelBtn = false;
         $scope.getData('vendorController/getTransactionDatesListForEditingVendorDetails.json', { vendorId: $scope.vendorId }, 'transactionDatesChnage');      
        
        $('.dropdown-toggle').removeClass('disabled');      
        	 $scope.correcttHistoryBtn = true;
       
    }

    
    $scope.transactionDatesListChnage = function(){
         $('.dropdown-toggle').removeClass('disabled');
       // alert("in change");
        $scope.getData('vendorController/getVednorDetailsById.json', { vendorInfoId: $scope.transactionModel, customerId: "" }, 'vendorList')
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
         $scope.getData('vendorController/getVednorDetailsById.json', { vendorInfoId: $routeParams.id, customerId: "" }, 'vendorList')
    	
    }
    
    
    
    
    $scope.validateVendor = function(){
    	$scope.getData('vendorController/validateVendorCode.json', { customerId: $scope.vendorDetailsVo.customerId != undefined && $scope.vendorDetailsVo.customerId != ""? $scope.vendorDetailsVo.customerId : 0, companyId: $scope.vendorDetailsVo.companyId != undefined && $scope.vendorDetailsVo.companyId  != "" ? $scope.vendorDetailsVo.companyId : 0, vendorCode:$scope.vendorDetailsVo.vendorCode}, 'validate');
    };
    
    
    $scope.clearFun = function(){
    	$scope.vendorDetailsVo =  new Object();
    	$('#transactionDate').val($filter('date')(new Date(),'dd/MM/yyyy')); 
    	$scope.CompAdders = ''; 	 
    	$scope.registrationDate ='';
    	 
    }
    
}]);
