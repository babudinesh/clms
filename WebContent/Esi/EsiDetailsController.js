'use strict';
var esiDetails = angular.module("myApp.esiDetails",[]);

esiDetails.controller('esiDetailsCtrl', ['$scope','$http', '$resource','$routeParams','$filter','$cookieStore','$location','$route', function ($scope,$http, $resource, $routeParams,$filter,$cookieStore,$location,$route) {
           
	$.material.init();            
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
            

 $scope.transactionDate = $filter('date')(new Date(),'dd/MM/yyyy'); 
 
 $scope.monthList = [{"id":"January","name":"January"},
                       {"id":"February","name":"February"},
                       {"id":"March","name":"March"},
                       {"id":"April","name":"April"},
                       {"id":"May","name":"May"},
                       {"id":"June","name":"June"},
                       {"id":"July","name":"July"},
                       {"id":"August","name":"August"},
                       {"id":"September","name":"September"},
                       {"id":"October","name":"October"},
                       {"id":"November","name":"November"},
                       {"id":"December","name":"December"}];   
  
        $scope.readOnly = false;
        $scope.datesReadOnly = false;
        $scope.updateBtn = false;
        $scope.saveBtn = true;
        $scope.viewOrUpdateBtn = false;
        $scope.correcttHistoryBtn = false;
        $scope.resetBtn = true;
        $scope.transactionList = false;
        $scope.cancelBtn= false;       
        $scope.statusDisabled = true;
        
        $scope.esiDetails = new Object();
    	$scope.esiDetails.esiSlabDetailsList = [];
    	$scope.esiSlabDetails = new Object();
    	$scope.esiDetails.isActive = 'Y';
    
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

    
  

    $scope.yearList = [{"id":2015, "name":2015},
                                {"id":2016, "name":2016},
                                {"id":2017, "name":2017}];
    
    
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
          		$scope.customerList = response.data.customerList;
          	  if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
    	                $scope.esiDetails.customerId=response.data.customerList[0].customerId;		                
    	                $scope.customerChange();
    	     }
          	// for button views
			  	if($scope.buttonArray == undefined || $scope.buttonArray == '')
         	 $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'ESI Setup'}, 'buttonsAction');
            }else if(type == 'getEsiDetailsByCustomerCompanyIds'){
            	//alert(angular.toJson(response.data));
            	
            	
            	if( !$scope.correcttHistoryBtn   && response.data.esiDetailsList != undefined && response.data.esiDetailsList != ''  && response.data.esiDetailsList.length > 0){
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
        	        $scope.esiDetails = response.data.esiDetailsList[0];
                	$scope.esiId = $scope.esiDetails.esiId;
            	}else if(!$scope.correcttHistoryBtn){
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
                   // $scope.esiDetails =  new Object();
                	
            	}else if($scope.correcttHistoryBtn){
            		 $scope.esiDetails = response.data.esiDetailsList[0];
                 	$scope.esiId = $scope.esiDetails.esiId;
            	}
            	
            	
            	
            	
            } else if (type == 'customerChange') {
                $scope.companyList = response.data;
                if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
   	                $scope.esiDetails.companyId = response.data[0].id;
					$scope.companyChange();
   	                }
              
            }else if (type == 'companyChange') {
            	$scope.countriesList = response.data.countriesList;  
            	 if( response.data.countriesList[0] != undefined && response.data.countriesList[0] != "" && response.data.countriesList.length == 1 ){
    	                $scope.esiDetails.countryId = response.data.countriesList[0].id;    	              
    	     }else if(type == 'getEsiDetails'){
    	    	  $scope.esiDetails = '';
    	      
    	     }          	 
            }else if (type == 'transactionDatesChnage') {
               // alert(angular.toJson(response.data));
                $scope.transactionDatesList = response.data;
                $scope.transactionModel= parseInt($scope.esiId);
                $scope.getData('esiController/getEsiDetailsByCustomerCompanyIds.json', { esiId: ($scope.transactionModel != undefined  && $scope.transactionModel != '') ? $scope.transactionModel : 0 }, 'getEsiDetailsByCustomerCompanyIds');
           }else if(type == 'saveESI'){
        	  // alert(angular.toJson(response.data));
        	   if(response.data.id != undefined && response.data.id > 0) {  	   
	                $scope.Messager('success', 'success', 'ESI Details Saved Successfully',buttonDisable);
	                //$route.reload();
	                $scope.companyChange();
	               // $scope.getData('esiController/getEsiDetailsByCustomerCompanyIds.json', { esiId: response.data.id }, 'getEsiDetailsByCustomerCompanyIds');
        	   } else{
        		   $scope.Messager('error', 'Error', 'Error in saving the details',buttonDisable);
        	   }    
           }  
        },
        function () {
        	$scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
        });
    }
    
    
    
      
    $scope.getData('CompanyController/getMasterInfoForCompanyGrid.json', { customerId: ($cookieStore.get('customerId') != undefined && $cookieStore.get('customerId') != "" ) ? $cookieStore.get('customerId') : $scope.esiDetails.customerId != undefined ? $scope.esiDetails.customerId : 0 }, 'customerList');
    
   // $scope.getData('esiController/getEsiDetailsByCustomerCompanyIds.json', { customerId: ($cookieStore.get('customerId') != undefined && $cookieStore.get('customerId') != "" ) ? $cookieStore.get('customerId') : $scope.esiDetails.customerId != undefined ? $scope.esiDetails.customerId : 0 ,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.esiDetails.companyId != undefined ? $scope.esiDetails.companyId : 0 }, 'getEsiDetailsByCustomerCompanyIds');
    
    $scope.customerChange = function () { 
    	$scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.esiDetails.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.esiDetails.companyId != undefined ? $scope.esiDetails.companyId : 0 }, 'customerChange');
    };
    
    $scope.companyChange = function () {    	
    	if($scope.esiDetails.companyId != undefined && $scope.esiDetails.companyId != "" && $scope.esiDetails.customerId != undefined && $scope.esiDetails.customerId !=""){
    		$scope.getData('CompanyController/getLocationsByCompanyId.json', { companyId: $scope.esiDetails.companyId, customerId: $scope.esiDetails.customerId  }, 'companyChange');
    		$scope.getData('esiController/getEsiDetailsByCustomerCompanyIds.json', { customerId:  $scope.esiDetails.customerId != undefined ? $scope.esiDetails.customerId : 0 ,companyId: $scope.esiDetails.companyId != undefined ? $scope.esiDetails.companyId : 0 }, 'getEsiDetailsByCustomerCompanyIds');
    	}
    };
    
 
  
    
    $scope.save = function ($event) {    	
    	 $scope.esiDetails.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
		 $scope.esiDetails.createdBy = $cookieStore.get('createdBy'); 
	     $scope.esiDetails.modifiedBy = $cookieStore.get('modifiedBy');
	     if(!$scope.correcttHistoryBtn){
	    	 $scope.esiDetails.esiId = 0;	    	 
	     }
	     // alert(angular.toJson($scope.esiDetails));  	
    	 $scope.getData('esiController/saveEsiDetails.json', angular.toJson($scope.esiDetails), 'saveESI',angular.element($event.currentTarget));
    };
    
   

    $scope.updateVendorBtnAction = function (this_obj) {
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
        $('.dropdown-toggle').removeClass('disabled');     
        $scope.transactionDate = $filter('date')(new Date(),'dd/MM/yyyy');

    }

    $scope.viewOrEditHistory = function () {
    	//alert();
        $scope.readOnly = false;
        $scope.onlyRead = true;
        $scope.datesReadOnly = false;
        $scope.updateBtn = false;
        $scope.saveBtn = false;
        $scope.viewOrUpdateBtn = false;
        $scope.correcttHistoryBtn = true;
        $scope.resetBtn = false;      
        $scope.transactionList = true;
        $scope.cancelBtn = true;
         $scope.getData('esiController/getTransactionHistoryDatesListForESIDetails.json', { customerId: ($cookieStore.get('customerId') != undefined && $cookieStore.get('customerId') != "" ) ? $cookieStore.get('customerId') : $scope.esiDetails.customerId != undefined ? $scope.esiDetails.customerId : 0 ,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.esiDetails.companyId != undefined ? $scope.esiDetails.companyId : 0 }, 'transactionDatesChnage');       
        
        $('.dropdown-toggle').removeClass('disabled');
    }

    
    $scope.transactionDatesListChnage = function(){
         $('.dropdown-toggle').removeClass('disabled');
       // alert("in change");
         $scope.getData('esiController/getEsiDetailsByCustomerCompanyIds.json', { customerId: 0 ,companyId: 0 ,esiId: $scope.transactionModel}, 'getEsiDetailsByCustomerCompanyIds');
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
         $scope.getData('esiController/getEsiDetailsByCustomerCompanyIds.json', { customerId: ($cookieStore.get('customerId') != undefined && $cookieStore.get('customerId') != "" ) ? $cookieStore.get('customerId') : $scope.esiDetails.customerId != undefined ? $scope.esiDetails.customerId : 0 ,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.esiDetails.companyId != undefined ? $scope.esiDetails.companyId : 0 }, 'getEsiDetailsByCustomerCompanyIds');    	
    }
    
    
    
    
    $scope.validateVendor = function(){
    	$scope.getData('vendorController/validateVendorCode.json', { customerId: $scope.esiDetails.customerId != undefined && $scope.esiDetails.customerId != ""? $scope.esiDetails.customerId : 0, companyId: $scope.esiDetails.companyId != undefined && $scope.esiDetails.companyId  != "" ? $scope.esiDetails.companyId : 0, vendorCode:$scope.esiDetails.vendorCode}, 'validate');
    };
    
    
    $scope.clearFun = function(){
    	$scope.esiDetails =  new Object();
    	$('#transactionDate').val($filter('date')(new Date(),'dd/MM/yyyy')); 
    	$scope.CompAdders = ''; 	 
    	$scope.registrationDate ='';
    	 
    }
    
    
   
    
    
  $scope.saveDetails = function(){    	
    	$scope.esiDetails.esiSlabDetailsList.push({
    		//'SNo':$scope.esiDetails.esiSlabDetailsList.length + 1,    		
    		'contributionStartMonth':$scope.esiSlabDetails.contributionStartMonth,
    		'contributionEndMonth':$scope.esiSlabDetails.contributionEndMonth,
    		'benefitStartMonth':$scope.esiSlabDetails.benefitStartMonth,    		
    		'benefitEndMonth':$scope.esiSlabDetails.benefitEndMonth,    		
    		'contributionStartMonthYear':$scope.esiSlabDetails.contributionStartMonthYear,
    		'contributionEndMonthYear':$scope.esiSlabDetails.contributionEndMonthYear,
    		'benefitStartMonthYear':$scope.esiSlabDetails.benefitStartMonthYear,
    		'benefitEndMonthYear':$scope.esiSlabDetails.benefitEndMonthYear  		
    	   		
    	});   
    	
    	 $('div[id^="myModal"]').modal('hide');
    	
       }
    
    $scope.Edit = function($event){    	
    	
    	$scope.trIndex = $($event.target).parent().parent().index();
    	$scope.esiSlabDetails = $scope.esiDetails.esiSlabDetailsList[$scope.trIndex];
    	$scope.popUpSave = false;
    	$scope.popUpUpdate =true;
    }
    
    
    $scope.updateDetails= function($event){
    	   	
    	$scope.esiDetails.esiSlabDetailsList.splice($scope.trIndex,1);    	
    	$scope.esiDetails.esiSlabDetailsList.push({
    		//'SNo':$scope.esiDetails.esiSlabDetailsList.length + 1,    		
    		'contributionStartMonth':$scope.esiSlabDetails.contributionStartMonth,
    		'contributionEndMonth':$scope.esiSlabDetails.contributionEndMonth,
    		'benefitStartMonth':$scope.esiSlabDetails.benefitStartMonth,    		
    		'benefitEndMonth':$scope.esiSlabDetails.benefitEndMonth,    		
    		'contributionStartMonthYear':$scope.esiSlabDetails.contributionStartMonthYear,
    		'contributionEndMonthYear':$scope.esiSlabDetails.contributionEndMonthYear,
    		'benefitStartMonthYear':$scope.esiSlabDetails.benefitStartMonthYear,
    		'benefitEndMonthYear':$scope.esiSlabDetails.benefitEndMonthYear  		
    	});    	
    	 $('div[id^="myModal"]').modal('hide');
    	//alert(JSON.stringify($scope.customerDetails.customerCountriesList));
    }
    
    
    
    $scope.Delete = function($event){    	
    	
    	var r = confirm("Do you want to delete the "+$scope.esiDetails.esiSlabDetailsList[$($event.target).parent().parent().index()].contributionStartMonth);    	
    	if(r){
    		$scope.esiDetails.esiSlabDetailsList.splice($($event.target).parent().parent().index(),1);
    	}
    	//alert(JSON.stringify($scope.customerDetails.customerCountriesList));
    }
    
    
    $scope.plusIconAction = function(){
    	$scope.popUpSave = true;
    	$scope.popUpUpdate =false;
    	$scope.esiSlabDetails = new Object();

    }
    
}]);
