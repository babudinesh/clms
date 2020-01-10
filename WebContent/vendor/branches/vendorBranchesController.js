'use strict';
// var Vendorcontroller = angular.module("myapp.VendorLocationAdd", []);
VendorSearchcontroller.controller("vendorBranchesAddViewDtls", ['$scope', '$rootScope', '$http', '$filter', '$resource','$location','$routeParams','$cookieStore', 'myservice', function($scope,$rootScope, $http,$filter,$resource,$location,$routeParams,$cookieStore,myservice) {
	
	$.material.init();
    
   /* $('#pfStartDate,#pfRegistrationDate,#esiStartDate,#esiRegistrationDate,#transactionDate').bootstrapMaterialDatePicker({ 
    	time : false,
        Button : true,
        format : "DD/MM/YYYY",
        clearButton: true
    }); 
    */
    $('#pfStartDate,#pfRegistrationDate,#esiStartDate,#esiRegistrationDate,#transactionDate').datepicker({
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
    
    $scope.vendor = new Object();
    $scope.vendor.isActive = 'Y';
    $scope.transactionDate = $filter('date')(new Date(),"dd/MM/yyyy");
	if($routeParams.id > 0){
		$scope.readonly = true;
		$scope.saveBtn = false;
		$scope.updateBtn = true;
		$scope.correctHistoryBtn = false;
		$scope.resetBtn = false;
		$scope.viewHistoryBtn = true;
		$scope.cancelBtn = false;
		$scope.returnTOSearchBtn = true;
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
	
	$scope.fun_updateActionBtn = function(){
		$scope.readonly = false;
		$scope.onlyRead = true;
		$scope.validCode = true;
		$scope.saveBtn = true;
		$scope.updateBtn = false;
		$scope.correctHistoryBtn = false;
		$scope.resetBtn = false;
		$scope.viewHistoryBtn = false;
		$scope.cancelBtn = true;
		$scope.returnTOSearchBtn = true;
		$scope.transactionList = false;
		$scope.transactionDate = $filter('date')(new Date(),"dd/MM/yyyy");
	}
	
	$scope.fun_viewHistoryBtn = function(){
		$scope.readonly = false;
		$scope.onlyRead = true;
		$scope.saveBtn = false;
		$scope.updateBtn = false;
		$scope.correctHistoryBtn = true;
		$scope.resetBtn = false;
		$scope.viewHistoryBtn = false;
		$scope.cancelBtn = false;
		$scope.returnTOSearchBtn = true;
		$scope.transactionList = true;	
		$scope.getPostData('vendorController/getTransactionHistoryDatesListForVendorBranchDetails.json', { vendorBranchId : $scope.vendor.vendorBranchesId }, 'transactionDatesChnage');
	    $('.dropdown-toggle').removeClass('disabled');
		
	}
	
	$scope.fun_vendorBranchCancelBtn = function(){
		$scope.readonly = true;
		$scope.saveBtn = false;
		$scope.updateBtn = true;
		$scope.correctHistoryBtn = false;
		$scope.resetBtn = false;
		$scope.viewHistoryBtn = true;
		$scope.cancelBtn = false;
		$scope.returnTOSearchBtn = true;
		$scope.transactionList = false;
		$scope.getPostData("vendorController/getVendorBranchDetails.json", { vendorBranchId :  $scope.vendor.vendorBranchDetailsInfoId } , "masterData");
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
	            	//console.log(response.data.vendorBranchDetails);
	            	$scope.countryList = response.data.countryList;
	            	// for button views
	            	if($scope.buttonArray == undefined || $scope.buttonArray == '')
	            	$scope.getPostData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Vendor Branch'}, 'buttonsAction');
            	if(Object.prototype.toString.call(response.data.vendorBranchDetails) === '[object Array]' &&  response.data.vendorBranchDetails.length > 0 ){
            		$scope.vendor = response.data.vendorBranchDetails[0]; 
            		//$scope.countryList = response.data.countryList;
            		$scope.statesList = response.data.statesList;
            		//$scope.fun_countryChange();
            		var value = "";
            		if( response.data.vendorBranchDetails[0].address1 != undefined &&  response.data.vendorBranchDetails[0].address1 != '' )
            			value += response.data.vendorBranchDetails[0].address1;
            		if( response.data.vendorBranchDetails[0].address2 != undefined &&  response.data.vendorBranchDetails[0].address2 != '')
            			value += ", "+response.data.vendorBranchDetails[0].address2;
            		if( response.data.vendorBranchDetails[0].address3 != undefined &&  response.data.vendorBranchDetails[0].address3 != '')
            			value += ", "+response.data.vendorBranchDetails[0].address3;
            		if( response.data.vendorBranchDetails[0].country != undefined &&  response.data.vendorBranchDetails[0].country != ''){
            			angular.forEach(response.data.countryList,function(value,key){
            				if(response.data.vendorBranchDetails[0].country == value.id){
            					$scope.countryName = value.name;
            					
            				}
            			});
            			value += ", "+$scope.countryName;
            		}
            		if( response.data.vendorBranchDetails[0].state != undefined &&  $scope.vendor.state != ''){
            			angular.forEach($scope.statesList,function(value,key){
            				if(response.data.vendorBranchDetails[0].state == value.id){
            					$scope.stateName = value.name;
            				}
            			});
            			value += ", "+$scope.stateName;
            		}
            			//value += ", "+response.data.vendorBranchDetails[0].stateName;
            		if( response.data.vendorBranchDetails[0].city != undefined &&  response.data.vendorBranchDetails[0].city != '')
            			value += ", "+response.data.vendorBranchDetails[0].city;
            		if( response.data.vendorBranchDetails[0].pincode != undefined &&  response.data.vendorBranchDetails[0].pincode != '')
            			value += ", "+response.data.vendorBranchDetails[0].pincode;
            		
            		value += ". ";
            	        $scope.addressData = value;
            	        
            		//$scope.addressData = response.data.vendorBranchDetails[0].address1 + ', ' + response.data.vendorBranchDetails[0].address2 + ', ' + response.data.vendorBranchDetails[0].address3+ ', ' + response.data.vendorBranchDetails[0].city+ ', ' + response.data.vendorBranchDetails[0].pincode+'. ';
            		
            		$scope.transactionDate = $filter('date')(response.data.vendorBranchDetails[0].transactionDate,"dd/MM/yyyy");
                    $scope.pfStartDate = $filter('date')(response.data.vendorBranchDetails[0].pfStartDate,"dd/MM/yyyy");
                    $scope.pfRegistrationDate = $filter('date')(response.data.vendorBranchDetails[0].pfRegistrationDate,"dd/MM/yyyy");
                    $scope.esiStartDate = $filter('date')(response.data.vendorBranchDetails[0].esiStartDate,"dd/MM/yyyy");
                    $scope.esiRegistrationDate = $filter('date')(response.data.vendorBranchDetails[0].esiRegistrationDate,"dd/MM/yyyy");
                    if($scope.vendor.isProfessionalTaxApplicable == 'Y' )
                    	$scope.isProfessionalTaxApplicable = true;
                    else
                    	$scope.isProfessionalTaxApplicable = false ;
                    
                	$scope.companyList = response.data.companyList;
                	$scope.vendorList = response.data.vendorList;
                	
                	$scope.fun_stateChange();
            	}            		
            	
            	$scope.customerList = response.data.customerList;
            	$scope.list_pfTypes = response.data.pfTypes;
            	$scope.list_skills = response.data.skills;   
        	  if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
	                $scope.vendor.customerDetailsId=response.data.customerList[0].customerId;		                
	                $scope.fun_CustomerChangeListener();
	           }
            	
            	
            }else if(type == 'save'){
            	if($scope.saveBtn == true){
            		$scope.Messager('success', 'success', 'Vendor Details Saved Successfully',buttonDisable);
            		$location.path('/vendorBranchesAddOrView/'+response.data.branchId);            		
            	}            	
            	else{
            		$scope.Messager('success', 'success', 'Vendor Details Updated Successfully',buttonDisable);
            	}
            }else if(type == 'companyDropDown'){
	      		$scope.companyList = response.data;
	      		
	      		if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
   	                $scope.vendor.companyDetailsId = response.data[0].id;
					$scope.fun_CompanyChangeListener();
   	                }
	      	}else if(type == 'vendorDropDown'){
	      		$scope.vendorList = response.data.vendorList;
	      		if( response.data != undefined && response.data.vendorList != "" && response.data.vendorList.length == 1 ){
   	                $scope.vendor.vendorDetailsId = response.data.vendorList[0].id;					
   	                }
	      		
	      	}else if(type == 'countryChange'){
	      		$scope.statesList = response.data;
	      	}else if(type == 'stateChange'){
	      		$scope.cityList = response.data;
	      	}else if (type == 'transactionDatesChnage') {            
                $scope.transactionDatesList = response.data;
            } else if(type == "validateBranch"){
            	
            	if(response.data.length > 0 ){
            		$scope.Messager('error', 'Error', 'Branch ID already exists.');
            		$scope.validCode = false;
            	}else{
            		$scope.validCode = true;
            	}
            }   
            
        },
        function () {
       	 $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
       	//alert('Error')         	
       })
	};
	
	
	
	
    // GET MASTER DATA FOR  VENDOR DETAILS SCREEN   
	$scope.getPostData("vendorController/getVendorBranchDetails.json", { vendorBranchId : $routeParams.id,customerId: $cookieStore.get('customerId') } , "masterData");		    

	// vendor save logic
	$scope.fun_vendorBranchSaveBtn = function($event){ 
		
		 if($('#vendorBranchDetails').valid()){
			// $scope.validateBranchCode();
		 		if(!$scope.validCode && !$scope.correctHistoryBtn){
		 			$scope.Messager('error', 'Error', 'Branch ID already exists.');
		 		}else if( ($scope.vendor.address1 == undefined || $scope.vendor.address1 == null ) || ($scope.vendor.country == undefined  || $scope.vendor.country == null) ){
					$scope.Messager('error', 'Error', 'Please Enter Vendor Branch Address...',angular.element($event.currentTarget));					
					//return;
				}else if(($('#esiRegistrationDate').val() != undefined || $('#esiRegistrationDate').val() != null) && ($('#esiStartDate').val() != undefined && $('#esiStartDate').val() != null)  && (new Date(moment($('#esiRegistrationDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime() >= new Date(moment($('#esiStartDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime())){
	      	    	$scope.Messager('error', 'Error', 'ESI Registration Date should not be less than ESI Start Date');
	        	}else if(($('#pfRegistrationDate').val() != undefined || $('#pfRegistrationDate').val() != null) && ($('#pfStartDate').val() != undefined && $('#pfStartDate').val() != null)  && (new Date(moment($('#pfRegistrationDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime() >= new Date(moment($('#pfStartDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') ).getTime())){
	      	    	$scope.Messager('error', 'Error', 'PF Registration Date should not be less than PF Start Date');
	        	}else{		
					$scope.vendor.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
					if($('#pfStartDate').val() != '' && $('#pfStartDate').val() != undefined)
			        $scope.vendor.pfStartDate = moment($('#pfStartDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
					
					if($('#pfRegistrationDate').val() != '' && $('#pfRegistrationDate').val() != undefined)
			        $scope.vendor.pfRegistrationDate = moment($('#pfRegistrationDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
					
					if($('#esiStartDate').val() != '' && $('#esiStartDate').val() != undefined)
			        $scope.vendor.esiStartDate = moment($('#esiStartDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
					
					if($('#esiRegistrationDate').val() != '' && $('#esiRegistrationDate').val() != undefined)
			        $scope.vendor.esiRegistrationDate = moment($('#esiRegistrationDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
					
			        $scope.vendor.createdBy = $cookieStore.get('createdBy'); 
			    	$scope.vendor.modifiedBy = $cookieStore.get('createdBy');
			    	
			        if($scope.isProfessionalTaxApplicable == true )
			        	$scope.vendor.isProfessionalTaxApplicable = 'Y';
			        else
			        	$scope.vendor.isProfessionalTaxApplicable = 'N';
			        var BranchDetailsInfoId =0;
			        var savedata; 
			        if($scope.correctHistoryBtn == false){
			        	BranchDetailsInfoId = $scope.vendor.vendorBranchDetailsInfoId;
			        	 $scope.vendor.vendorBranchDetailsInfoId = 0;
			        	 savedata = angular.toJson($scope.vendor) ;			        	 
			        	 $scope.vendor.vendorBranchDetailsInfoId = BranchDetailsInfoId;	 
					}else{
						 savedata = angular.toJson($scope.vendor) ;
					}
					$scope.getPostData("vendorController/saveOrUpdateVendorBranchDetails.json", savedata , "save",angular.element($event.currentTarget));					
			            
				}
			}
	}
	
    // Customer Change Listener to get company details
    $scope.fun_CustomerChangeListener = function(){	
	   if($scope.vendor.customerDetailsId != null && $scope.vendor.customerDetailsId != undefined)
	   $scope.getPostData("vendorController/getCompanyNamesAsDropDown.json",  { customerId : $scope.vendor.customerDetailsId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.companyDetailsId != undefined ? $scope.companyDetailsId  : 0} , "companyDropDown");	   
    };
	   
	// Company Change Listener to get vendor details
    $scope.fun_CompanyChangeListener = function(){
	   if($scope.vendor.customerDetailsId != null && $scope.vendor.customerDetailsId != undefined && $scope.vendor.companyDetailsId != null && $scope.vendor.companyDetailsId != undefined)
	   $scope.getPostData("vendorController/getVendorAndLocationNamesAsDropDowns.json", { customerId : $scope.vendor.customerDetailsId , companyId : $scope.vendor.companyDetailsId,vendorId:($cookieStore.get('vendorId') != null && $cookieStore.get('vendorId') != "") ? $cookieStore.get('vendorId') : $scope.vendorDetailsId != undefined ? $scope.vendorDetailsId : 0 } , "vendorDropDown");		   
    };
	   
	// Country Change Listener 
    $scope.fun_countryChange = function(){
       if($scope.vendor.country != null && $scope.vendor.country != undefined)
 	   $scope.getPostData("CommonController/statesListByCountryId.json", { countryId : $scope.vendor.country } , "countryChange");       
     };
     
  // State Change Listener 
     $scope.fun_stateChange = function(){
  	   if($scope.vendor.state != null && $scope.vendor.state != undefined)
  	   $scope.getPostData("CommonController/getCitiesArrayByStateId.json", { stateId : $scope.vendor.state } , "stateChange");		   
     };
	
	$scope.fun_city_autoComplete = function(){			
		$( "#city" ).autocomplete({
            source: $scope.cityList
          });
	}	
	
	$scope.fun_dispalyAddress = function(){
		 if($('#vendorBranchAddressDetails').valid()){
			var value = "";
			if( $scope.vendor.address1 != undefined &&  $scope.vendor.address1 != '')
				value += $scope.vendor.address1;
			if( $scope.vendor.address2 != undefined &&  $scope.vendor.address2 != '')
				value += ", "+$scope.vendor.address2;
			if( $scope.vendor.address3 != undefined &&  $scope.vendor.address3 != '')
				value += $scope.vendor.address3;
			if( $scope.vendor.country != undefined &&  $scope.vendor.country != '')
				value += ", "+$("#country option:selected").html();
			if( $scope.vendor.state != undefined &&  $scope.vendor.state != '')
				value += ", "+$("#state option:selected").html();
			if( $scope.vendor.city != undefined &&  $scope.vendor.city != '')
				value += ", "+$scope.vendor.city;
			if( $scope.vendor.pincode != undefined &&  $scope.vendor.pincode != '')
				value += ", "+$scope.vendor.pincode;
			
			value += ". ";
		        $scope.addressData = value;
		        $('#myModal').modal('hide');
		}   
	       // $scope.vendor.address1 + ', ' + $scope.vendor.address2 + ', ' + $scope.vendor.address3+ ', ' + $scope.vendor.city+ ', ' + $scope.vendor.pincode+'. ';	        
	}
	
	// Transaction Dates Change Listener
	$scope.fun_transactionDatesListChnage = function(){
		if($scope.vendor.vendorBranchDetailsInfoId != undefined && $scope.vendor.vendorBranchDetailsInfoId != '' && $scope.vendor.vendorBranchDetailsInfoId > 0 )
		$scope.getPostData("vendorController/getVendorBranchDetails.json", { vendorBranchId :  $scope.vendor.vendorBranchDetailsInfoId } , "masterData");
	};
	
	$scope.validateBranchCode = function(){
		if($scope.vendor.customerDetailsId != null && $scope.vendor.customerDetailsId != undefined && $scope.vendor.companyDetailsId != null && $scope.vendor.companyDetailsId != undefined && $scope.vendor.branchCode != null && $scope.vendor.branchCode != undefined)
			$scope.getPostData("vendorController/getvendorBranchGridData.json",{customerId : $scope.vendor.customerDetailsId , companyId : $scope.vendor.companyDetailsId  , vendorId : ($cookieStore.get('vendorId') != null && $cookieStore.get('vendorId') != "") ? $cookieStore.get('vendorId') : $scope.vendor.vendorDetailsId != undefined ? $scope.vendor.vendorDetailsId : 0 , branchCode :  $scope.vendor.branchCode  } , "validateBranch" );    	   
	}
	 
}]);
