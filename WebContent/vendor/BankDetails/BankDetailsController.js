'use strict';

vendorBankControllers.controller('vendorBankDetails', ['$scope','$http', '$resource','$routeParams','$filter','myservice','$cookieStore','$location', function ($scope,$http, $resource, $routeParams,$filter,myservice,$cookieStore,$location) {
	 $.material.init();
		
		/*$('#transactionDate').bootstrapMaterialDatePicker({
	        time : false,
	        Button : true,
	        format : "DD/MM/YYYY",
	        clearButton: true
	    });*/
	 $('#transactionDate').datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown'
	    	  
	    });
	    $scope.readonly = false;
	    $scope.updateBtn = false;
	    $scope.saveBtn = true;
	    $scope.correcttHistoryBtn = false;
	    $scope.resetBtn = true;
	    $scope.cancelBtn = false;
	    $scope.addrHistory = false;
	    $scope.transactionList = false;
	    $scope.vendorBankVo = new Object();
	    $scope.masterData = false;
	    
	    
	    $scope.vendorBankVo.isActive = 'Y';
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
	        	
	        	if(type == 'buttonsAction'){
	        		$scope.buttonArray = response.data.screenButtonNamesArray;
	        	} else if(type == 'getCustomerNames'){        		
	        		$scope.customerList = response.data.customerList; 
	        		$scope.list_Country = response.data.countryList;
	        		
	        		  if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
	   		                $scope.vendorBankVo.customerId=response.data.customerList[0].customerId;		                
	   		                $scope.customerChange();
	   		                }
	        		// for button views
	        		  if($scope.buttonArray == undefined || $scope.buttonArray == '')
	        		  $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Vendor Bank Details'}, 'buttonsAction');
	        	}else if(type == 'customerChange'){	        		
	        		$scope.companyList = response.data; 
	        		
	        		if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	   	                $scope.vendorBankVo.companyid = response.data[0].id;
						$scope.companyChange();
	   	                }
	        	}else if (type == 'companyChange') {	
	        		//console.log(angular.toJson(response.data.vendorList));
	        		$scope.countriesList = response.data.countriesList; 
	                $scope.vendorList = response.data.vendorList; 
	                if(  response.data != undefined && response.data.vendorList != "" && response.data.vendorList.length == 1 ){
	   	                $scope.vendorBankVo.vendorId = response.data.vendorList[0].id;						
	   	                }
	                
	                $scope.locationList = response.data.locationList;	                
	                $scope.vendorBankVo.countryid = response.data.countriesList[0].id;
	            }else if(type == 'saveBankDetails'){
	            	//alert(angular.toJson(response.data));
	            	if($scope.saveBtn == true && response.data != undefined && response.data != '' && response.data > 0){
                    	$location.path('/vendorBankDetails/'+response.data);
                    	$scope.Messager('success', 'success', 'Bank Details Saved Successfully',buttonDisable);
                    }  else{
                    	$scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
                    }
	            	 
	            }else if (type == 'countryChange') {	            	
	                $scope.statesList = response.data;
	            }else if(type == 'validateBankCode'){
	            	if(response.data){
	            		$scope.saveBankDetails(buttonDisable);
	            	}else{
	            		$scope.Messager('error', 'Error', 'Bank Code Already Available',buttonDisable);
	            	}
	            	
	            }
	        	},
	        function () {
	        	  $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
	        	           
	          });
	    	}      
	    
	    
	    
	    $scope.getData('vendorController/getVendorBankDetailsbyId.json', {customerId: $cookieStore.get('customerId')}, 'getCustomerNames') 
     
     
	    $scope.customerChange = function () { 
	    	$scope.companyList ="";
	    	$scope.countriesList = ""; 
            $scope.vendorList = ""; 
            $scope.locationList = "";
	    	if($scope.vendorBankVo.customerId != undefined && $scope.vendorBankVo.customerId != ""){
	    	$scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.vendorBankVo.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.vendorBankVo.companyid ? $scope.vendorBankVo.companyid : 0}, 'customerChange');
	    	}
	    };
	    
	    
	    
	    $scope.companyChange = function () {
	    	$scope.countriesList = ""; 
            $scope.vendorList = ""; 
            $scope.locationList = "";
	    	if($scope.vendorBankVo.companyid != undefined && $scope.vendorBankVo.companyid != ""){
	        $scope.getData('vendorController/getVendorAndLocationNamesAsDropDowns.json', { customerId: $scope.vendorBankVo.customerId,companyId: $scope.vendorBankVo.companyid,vendorId:($cookieStore.get('vendorId') != null && $cookieStore.get('vendorId') != "") ? $cookieStore.get('vendorId') :($scope.vendorId != undefined && $scope.vendorId != "") ? $scope.vendorId : 0 }, 'companyChange');
	    	}
	    };
	    
	    
	    $scope.saveAddress = function(){
	    	if($('#addressForm').valid()){
		      	var value = "";
				if( $scope.vendorBankVo.addressLine1 != undefined &&  $scope.vendorBankVo.addressLine1 != '')
					value += $scope.vendorBankVo.addressLine1;
				if( $scope.vendorBankVo.addressLine2 != undefined &&  $scope.vendorBankVo.addressLine2 != '')
					value += ", "+$scope.vendorBankVo.addressLine2;
				if( $scope.vendorBankVo.addressLine3 != undefined &&  $scope.vendorBankVo.addressLine3 != '')
					value += ", "+$scope.vendorBankVo.addressLine3;
				if( $scope.vendorBankVo.country != undefined &&  $scope.vendorBankVo.country != '')
					value += ", "+$('#country option:Selected').html();
				if( $scope.vendorBankVo.state != undefined &&  $scope.vendorBankVo.state != '')
					value += ", "+$('#state option:Selected').html();
				if( $scope.vendorBankVo.city != undefined &&  $scope.vendorBankVo.city != '')
					value += ", "+$scope.vendorBankVo.city;
				if( $scope.vendorBankVo.pincode != undefined &&  $scope.vendorBankVo.pincode != '')
					value += ", "+$scope.vendorBankVo.pincode;
				
				value += ". ";
		        $scope.VendorAdders = value; 
		        $('div[id^="myModal"]').modal('hide');
	    	}
	    }
	    
	    
	    $scope.validateBankCode = function($event){
	    	if($('#bankDetails').valid())
	  		{
	    	$scope.getData('vendorController/validateVendorBankCode.json', angular.toJson($scope.vendorBankVo), 'validateBankCode',angular.element($event.currentTarget));
	  		}
	    }
	    
	    $scope.saveBankDetails =function($event){
	    	//alert(angular.toJson($scope.vendorBankVo));
	    	 if($('#bankDetails').valid())
	  		{
//	    		 if($scope.vendorBankVo.addressLine1 != undefined &&  $scope.vendorBankVo.addressLine1 != '')
//	    		  {
	    		 $scope.vendorBankVo.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
	    		 $scope.vendorBankVo.createdBy = $cookieStore.get('createdBy'); 
	 	    	 $scope.vendorBankVo.modifiedBy = $cookieStore.get('modifiedBy');
	 	    	 $scope.vendorBankVo.vendorBankId=0;
	    		 $scope.getData('vendorController/saveOrUpdateVendorBankDetails.json', angular.toJson($scope.vendorBankVo), 'saveBankDetails',angular.element($event.currentTarget)); 
//	    		}else{
//	    			 $scope.Messager('error', 'Error', 'Enter Address',angular.element($event.currentTarget));
//	    		}
	    		 
	  		}
	    	
	    }
	    
	    
	    
	    $scope.countryChange = function () {
	      // alert($scope.vendorBankVo.country+"::country"); 
	    	if($scope.vendorBankVo.country != null && $scope.vendorBankVo.country != ""){
	    		$scope.getData('CommonController/statesListByCountryId.json', { countryId: $scope.vendorBankVo.country }, 'countryChange');
	    	}
	    };
     
     
}]);


vendorBankControllers.controller('vendorBankDetailsEdit', ['$scope','$http', '$resource','$routeParams','$filter','myservice','$cookieStore','$location', function ($scope,$http, $resource, $routeParams,$filter,myservice,$cookieStore,$location) {
	  

    //initialsie the material-deisgn
           $.material.init();
   /* $('#transactionDate').bootstrapMaterialDatePicker
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
    
    
	$scope.vendorBankVo = new Object();
	 	
	
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
    
    $scope.masterData = true;
	    
	    
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
	        	if(type == 'getBankDetails'){
	        	//alert(angular.toJson(response.data));
	        	
	        	$scope.customerList = response.data.customerList; 
        		$scope.list_Country = response.data.countryList; 
        		
        		$scope.transactionDate = $filter('date')( response.data.vendorDetailsBank[0].transactionDate, 'dd/MM/yyyy');
        	
        		if($scope.updateBtn && !$scope.cancelError){        			
        			$scope.vendorBankVo = response.data.vendorDetailsBank[0];
            		$scope.customerChange();
            		$scope.companyChange();
            		$scope.countryChange();
        		}else if(!$scope.cancelError){        			
            		$scope.vendorBankVo = response.data.vendorDetailsBank[0];
            		//$scope.customerChange();            		
            		//$scope.companyChange();
            		//$scope.countryChange();
            		
        		}
        		
        		if($scope.cancelError){
        			$scope.vendorBankVo = response.data.vendorDetailsBank[0];
        			$scope.cancelError = false;
        		}
        		
        		var value = "";
        		if( $scope.vendorBankVo.addressLine1 != undefined &&  $scope.vendorBankVo.addressLine1 != '')
    				value += $scope.vendorBankVo.addressLine1+", ";
    			if( $scope.vendorBankVo.addressLine2 != undefined &&  $scope.vendorBankVo.addressLine2 != '')
    				value += $scope.vendorBankVo.addressLine2+", ";
    			if( $scope.vendorBankVo.addressLine3 != undefined &&  $scope.vendorBankVo.addressLine3 != '')
    				value += $scope.vendorBankVo.addressLine3+", ";
    			if( $scope.vendorBankVo.country != undefined &&  $scope.vendorBankVo.country != '')
    				value += $scope.vendorBankVo.country+", ";
    			if( $scope.vendorBankVo.city != undefined &&  $scope.vendorBankVo.city != '')
    				value += $scope.vendorBankVo.city+", ";
    			if( $scope.vendorBankVo.pincode != undefined &&  $scope.vendorBankVo.pincode != '')
    				value += $scope.vendorBankVo.pincode+". ";
    			
    		        $scope.VendorAdders = value;
    		        
    		           		        
	        	}else if(type == 'customerChange'){
	        		//alert("in Customer CHnage");
	        		$scope.companyList = response.data; 
	        	}else if (type == 'companyChange') {	
	        		
	        		$scope.countriesList = response.data.countriesList; 
	                $scope.vendorList = response.data.vendorList; 
	                $scope.locationList = response.data.locationList;	                
	                $scope.vendorBankVo.countryid = response.data.countriesList[0].id;
	            }else if (type == 'countryChange') {	            	
	                $scope.statesList = response.data;
                }else if(type == 'saveBankDetails'){                 	
                	if($scope.saveBtn == true && response.data != undefined && response.data != '' && response.data > 0){
                    	$location.path('/vendorBankDetails/'+response.data);
                    	$scope.Messager('success', 'success', 'Bank Details Saved Successfully',buttonDisable);
                   } else if($scope.correcttHistoryBtn && response.data > 0){
                    	 $scope.Messager('success', 'success', 'Bank Details Updated Successfully',buttonDisable);
                    }else{
                    	alert();
                    	$scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
                    }               
                   
                }else if (type == 'getTransactionDates') {                    
                    $scope.transactionDatesList = response.data;
                    $scope.transactionModel= parseInt($routeParams.vendorBankId);
                    $scope.getData('vendorController/getVendorBankDetailsbyId.json', {customerId:"",vendorBankId:$scope.transactionModel}, 'getBankDetails')
                }
	        },
	        function () {
	        	 $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
	        	  
	          
	          });
	    	}  
	    
	    
	    $scope.getData('vendorController/getVendorBankDetailsbyId.json', {customerId:"",vendorBankId :$routeParams.vendorBankId}, 'getBankDetails') 
	    
	    
	    
	    
	    $scope.customerChange = function () { 
	    	$scope.companyList ="";
	    	$scope.countriesList = ""; 
            $scope.vendorList = ""; 
            $scope.locationList = "";
           // alert($scope.vendorBankVo.customerId+"::customerId");
	    	if($scope.vendorBankVo.customerId != undefined && $scope.vendorBankVo.customerId != ""){
	    	$scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.vendorBankVo.customerId}, 'customerChange');
	    	}
	    };
	    
	    
	    
	    $scope.companyChange = function () {
	    	$scope.countriesList = ""; 
            $scope.vendorList = ""; 
            $scope.locationList = "";
           // alert($scope.vendorBankVo.companyid+"::companyid");
	    	if($scope.vendorBankVo.companyid != undefined && $scope.vendorBankVo.companyid != ""){
	        $scope.getData('vendorController/getVendorAndLocationNamesAsDropDowns.json', { customerId: $scope.vendorBankVo.customerId,companyId: $scope.vendorBankVo.companyid }, 'companyChange');
	    	}
	    };
	    
	    
	    $scope.countryChange = function () {		   
		    	if($scope.vendorBankVo.country != null && $scope.vendorBankVo.country != ""){
		    		$scope.getData('CommonController/statesListByCountryId.json', { countryId: $scope.vendorBankVo.country }, 'countryChange');
		    	}
		    };
	    
	    
	    
		    
		    $scope.validateBankCode =function($event){
		    	
			    	 if($('#bankDetails').valid())
			  		{
		    		 $scope.vendorBankVo.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
		    		 $scope.vendorBankVo.createdBy = $cookieStore.get('createdBy'); 
		 	    	 $scope.vendorBankVo.modifiedBy = $cookieStore.get('modifiedBy');
			 	    	if($scope.saveBtn == true){
			 	    		$scope.vendorBankVo.vendorBankId=0;
			 	    	}
		    		 $scope.getData('vendorController/saveOrUpdateVendorBankDetails.json', angular.toJson($scope.vendorBankVo), 'saveBankDetails',angular.element($event.currentTarget)); 
		    		 
		  		}
		    	
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
	        $scope.gridButtons = true;
	        $scope.cancelBtn = true;
	        $('.dropdown-toggle').removeClass('disabled');
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
	        $scope.gridButtons = true;
	        $scope.cancelBtn = false;
	        $scope.getData('vendorController/getTransactionDatesListForEditingVendorBankDetails.json', { uniqueId: $scope.vendorBankVo.uniqueId }, 'getTransactionDates');      
	        
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
	         $scope.cancelError = true;
	         
	         $scope.getData('vendorController/getVendorBankDetailsbyId.json', {customerId:"",vendorBankId: $routeParams.vendorBankId}, 'getBankDetails')
	    }
	    
	    
	    
	    $scope.saveAddress = function(){
	    	if($('#addressForm').valid()){
		      	var value = "";
		      	if( $scope.vendorBankVo.addressLine1 != undefined &&  $scope.vendorBankVo.addressLine1 != '')
					value += $scope.vendorBankVo.addressLine1;
				if( $scope.vendorBankVo.addressLine2 != undefined &&  $scope.vendorBankVo.addressLine2 != '')
					value += ", "+$scope.vendorBankVo.addressLine2;
				if( $scope.vendorBankVo.addressLine3 != undefined &&  $scope.vendorBankVo.addressLine3 != '')
					value += ", "+$scope.vendorBankVo.addressLine3;
				if( $scope.vendorBankVo.country != undefined &&  $scope.vendorBankVo.country != '')
					value +=  ", "+$('#country option:Selected').html();
				if( $scope.vendorBankVo.state != undefined &&  $scope.vendorBankVo.state != '')
					value +=  ", "+$('#state  option:Selected').html();
				if( $scope.vendorBankVo.city != undefined &&  $scope.vendorBankVo.city != '')
					value += ", "+$scope.vendorBankVo.city;
				if( $scope.vendorBankVo.pincode != undefined &&  $scope.vendorBankVo.pincode != '')
					value += ", "+$scope.vendorBankVo.pincode;
				
				value += ". ";
		        $scope.VendorAdders = value;   
		        $('div[id^="myModal"]').modal('hide');
	    	}
	    }
	    
	    $scope.transactionDatesListChnage = function(){    
	        $scope.getData('vendorController/getVendorBankDetailsbyId.json', {customerId:"",vendorBankId:$scope.transactionModel}, 'getBankDetails')
	        $('.dropdown-toggle').removeClass('disabled');
	    }
    
}]);
