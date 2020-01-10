'use strict';

PTaxController.controller("PTaxAddCtrl", ['$scope', '$http', '$resource','$location','$filter', '$routeParams','myservice','$cookieStore', function ($scope, $http, $resource, $location, $filter, $routeParams, myservice, $cookieStore) {
	
	$.material.init();
	
	$('#transactionDate').datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown'
	});
	
	$scope.taxVo = new Object();
	$scope.isValid = true;
	 
	$scope.list_months = [ 	{"id":"All Months","name":"All Months"},
	                        {"id":"January","name":"January"},
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
	
	$scope.list_frequency=[{"id":"Monthly", "name":"Monthly"},
	                       {"id":"Quarterly", "name":"Quarterly"},
	                       {"id":"Half Yearly", "name":"Half Yearly"},
	                       {"id":"Yearly", "name":"Yearly"}]
	
    
	if ($routeParams.id > 0) {
       $scope.readOnly = true;
       $scope.datesReadOnly = true;
       $scope.updateBtn = true;
       $scope.saveBtn = false;
       $scope.viewOrUpdateBtn = true;
       $scope.correcttHistoryBtn = false;
       //$scope.gridButtons = false;
       $scope.resetBtn = false;
       $scope.transactionList = false;
    } else {
	   $('#transactionDate').val($filter('date')(new Date(),'dd/MM/yyyy'));
       $scope.readOnly = false;
       $scope.datesReadOnly = false;
       $scope.updateBtn = false;
       $scope.saveBtn = true;
       $scope.viewOrUpdateBtn = false;
       $scope.correcttHistoryBtn = false;
       $scope.resetBtn = true;
       $scope.gridButtons = true;
       $scope.transactionList = false;
    }
    
    $scope.updateBtnAction = function (this_obj) {
       $scope.readOnly = false;
       $scope.onlyRead = true;
       $scope.datesReadOnly = false;
       $scope.updateBtn = false;
       $scope.saveBtn = true;
       $scope.viewOrUpdateBtn = false;
       $scope.correctHistoryBtn = false;
       $scope.resetBtn = false;
       $scope.cancelBtn = true;
       $scope.transactionList = false;
       //$scope.gridButtons = true;
       $scope.taxVo.professionalTaxId = 0;
      // $scope.taxVo.professionalTaxInfoId = 0;
       $('#transactionDate').val($filter('date')(new Date(),'dd/MM/yyyy'));
       $('.dropdown-toggle').removeClass('disabled');
    };
	       
	       
	$scope.viewOrEditHistory = function () {
       $scope.readOnly = false;
       $scope.onlyRead = true;
       $scope.datesReadOnly = false;
       $scope.updateBtn = false;
       $scope.saveBtn = false;
       $scope.viewOrUpdateBtn = false;
       $scope.correcttHistoryBtn = true;
       $scope.resetBtn = false;  
       //$scope.gridButtons = true;
       $scope.transactionList = true;
       $scope.getData('statutorySetupsController/getTransactionDatesListForProfessionalTax.json', { companyId: $scope.taxVo.companyId, customerId: $scope.taxVo.customerId ,taxUniqueId: $scope.taxVo.taxUniqueId }, 'transactionDatesChange');       
       
       $('.dropdown-toggle').removeClass('disabled');
    };
   
    $scope.cancelBtnAction = function(){
    	$scope.readOnly = true;
        $scope.datesReadOnly = true;
        $scope.updateBtn = true;
        $scope.saveBtn = false;
        $scope.viewOrUpdateBtn = true;
        $scope.correcttHistoryBtn = false;
        $scope.resetBtn = false;
        $scope.transactionList = false;
        $scope.cancelBtn = false;
       // $scope.gridButtons = false;
        $scope.returnToSearchBtn = true;
        $scope.getData('statutorySetupsController/getProfessionalTaxById.json', { professionalTaxId: $routeParams.id,customerId : ''}, 'professionalTax')
    };
    
   
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
   };
   

   $scope.getData = function (url, data, type, buttonDisable) {
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
    		} else if (type == 'professionalTax') {
			    //alert(JSON.stringify(response.data));
               $scope.taxList = response.data;
               
               if( response.data.professionalTaxList != null && response.data.professionalTaxList[0] != undefined){
            	   $scope.taxVo = response.data.professionalTaxList[0];
            	   $('#transactionDate').val($filter('date')( response.data.professionalTaxList[0].transactionDate, 'dd/MM/yyyy'));
            	   $scope.taxVo.transactionDate =  $filter('date')( response.data.professionalTaxList[0].transactionDate, 'dd/MM/yyyy');
                   $scope.transDate1 =  $filter('date')( response.data.professionalTaxList[0].transactionDate, 'dd/MM/yyyy');
                   if(response.data.professionalTaxList[0].defineByState == true){
                	   $scope.defineBy = 'state';
                   }else if(response.data.professionalTaxList[0].defineByStateAndCity == true){
                	   $scope.defineBy = 'city';
                   }
                   $scope.taxList.customerList = response.data.customerList;
            	   $scope.taxList.companyList = response.data.companyList;
            	   $scope.taxList.countryList = response.data.countryList;
            	   $scope.taxList.statesList = response.data.statesList;
                 
               }else{
            	   $scope.taxVo = new Object();
            	   $scope.taxVo.transactionDate=  $filter('date')( new Date(), 'dd/MM/yyyy');
            	   $scope.taxVo.status='Y';
            	   $scope.defineBy = 'city';
            	   $scope.taxList.taxRulesList = [];
            	   //  alert(JSON.stringify(response.data.customerList[0]));
            	   if( response.data.customerList != null && response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){
            		   $scope.taxVo.customerId = response.data.customerList[0].customerId;		                
            		   $scope.customerChange();
	                }else{
	                	$scope. taxList.customerList = response.data.customerList;
	                }
               }
            // for button views
               if($scope.buttonArray == undefined || $scope.buttonArray == '')
               $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Professional Tax'}, 'buttonsAction');
           }else if (type == 'customerChange') {
				//alert(JSON.stringify(response.data));
               $scope.taxList.companyList = response.data;
               if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
            	   $scope.taxVo.companyId = response.data[0].id;
            	   $scope.companyChange();
                }
           }else if (type == 'companyChange') {
			   //alert(JSON.stringify(response.data));
               $scope.taxList.countryList = response.data;
               if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
            	   $scope.taxVo.countryId = response.data[0].id;
            	   $scope.countryChange();
                }
           }else if (type == 'countryChange') {
			   //alert(JSON.stringify(response.data));
        	   $scope.taxList.statesList = response.data;
        	   if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
            	   $scope.taxVo.stateId = response.data[0].id;
                }
        	  
           }else if (type == 'locationNames') {
			   //alert(JSON.stringify(response.data));
        	   $scope.taxList.locationsList = response.data;
        	   if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
            	   $scope.taxVo.locationId = response.data[0].locationId;
                }
        	  
           }else if(type == 'validate'){
        	   if(response.data.taxList != null && response.data.taxList.length > 0){
        	   		$scope.isValid = false;
        	   		$scope.Messager('error', 'Error', 'Professional Tax already exists for this combination',buttonDisable);
        	   }else
        	   		$scope.isValid = true;	
        	
           } else if (type == 'saveProfessionalTax') {
              // alert(angular.toJson(response.data));
        	   if(response.data.id > 0){
	            	$scope.Messager('success', 'success', 'Professional Tax Saved Successfully',buttonDisable);
	            	if($scope.saveBtn){
	            		$location.path('/defineProfessionalTax/'+response.data.id);
	            	}else{
	            		$('#transactionDate').val($filter('date')( $scope.taxVo.transactionDate, 'dd/MM/yyyy'));
	            	}
        	   }else if(response.data.id == -1){
        		   $scope.Messager('error', 'Error', 'Effective from date should be greater than or equal to company effective date',buttonDisable);
            	   $('#transactionDate').val($filter('date')( $scope.taxVo.transactionDate, 'dd/MM/yyyy'));

               }else{
            	   $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
            	   $('#transactionDate').val($filter('date')( $scope.taxVo.transactionDate, 'dd/MM/yyyy'));

               }
           }else if (type == 'transactionDatesChange') {
            	//alert(JSON.stringify(response.data));
            	var k = response.data.length;
            	if(response.data.length > 1){
	            	for(var i = response.data.length-1; i> 0;i--){
		            	 if($scope.dateDiffer(response.data[i].name.split('-')[0])){
		            		 k = response.data[i].id;
		            		 break;
		            	 }
	            	}
            	}else{
            		k = response.data[0].id;
            	}
            	$scope.transactionDatesList = response.data;
            	$scope.transactionModel=k;
		        $scope.getData('statutorySetupsController/getProfessionalTaxById.json', { professionalTaxId: $scope.transactionModel,customerId : ''}, 'lwfList')

            }
       },
       function () { 
    	   $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
    	   $('#transactionDate').val($filter('date')( $scope.taxVo.transactionDate, 'dd/MM/yyyy'));
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
    
   $scope.getData('statutorySetupsController/getProfessionalTaxById.json', { professionalTaxId: $routeParams.id, customerId:  $cookieStore.get('customerId') }, 'professionalTax')
   	
   $scope.customerChange = function () {
	   if($scope.taxVo.customerId != undefined && $scope.taxVo.customerId != null &&  $scope.taxVo.customerId != ""){
		   $scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.taxVo.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.taxVo.companyId  }, 'customerChange');
	   }
   };
   
   $scope.companyChange = function(){
	   if($scope.taxVo.companyId != undefined &&  $scope.taxVo.companyId != null &&  $scope.taxVo.companyId != ""){
	    	$scope.getData('vendorController/getcountryListByCompanyId.json', { companyId: $scope.taxVo.companyId }, 'companyChange');
   		}
   };
	
   $scope.countryChange = function () {
	   if($scope.taxVo.countryId != undefined && $scope.taxVo.countryId != null && $scope.taxVo.countryId != ""){
		   $scope.getData('CommonController/statesListByCountryId.json', { countryId : $scope.taxVo.countryId }, 'countryChange');
		   $scope.getData('LocationController/getLocationsListBySearch.json', { customerId:$scope.taxVo.customerId, companyId:$scope.taxVo.companyId, countryId: $scope.taxVo.countryId},'locationNames');
	   }
   };   
   
   $scope.validateProfessionalTax = function(){
	   if($scope.taxVo.customerId != undefined && $scope.taxVo.customerId != null &&  $scope.taxVo.customerId != ""
			   && $scope.taxVo.companyId != undefined &&  $scope.taxVo.companyId != null &&  $scope.taxVo.companyId != ""
			   && $scope.taxVo.countryId != undefined && $scope.taxVo.countryId != null && $scope.taxVo.countryId != ""
			   && $scope.taxVo.locationId != undefined && $scope.taxVo.locationId != null && $scope.taxVo.locationId != "")
	   $scope.getData('statutorySetupsController/getProfessionalTaxListBySearch.json', { customerId:$scope.taxVo.customerId, companyId: $scope.taxVo.companyId, countryId : $scope.taxVo.countryId, locationId : $scope.taxVo.locationId  }, 'validate');

   };
   
   $scope.save = function ($event) {
	   		
	    if($("#pTaxForm").valid()){
        	if(!$scope.isValid){
	    		$scope.Messager('error', 'Error', 'LWF Rules already defined for this combination '); 
	    	}else if($scope.transDate1 != '' && $scope.transDate1 != undefined && new Date(moment($scope.transDate1,'DD/MM/YYYY').format('YYYY-MM-DD')) .getTime() > new Date(moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime()){
	    		   $scope.Messager('error', 'Error', 'Effective From Date Should not be less than previous transaction date');
	        	   $scope.taxVo.transactionDate =  $scope.transDate1;
	    	}else{
	    		if($scope.defineBy == 'state'){
	    			$scope.taxVo.defineByState = true;
	    			$scope.taxVo.defineByStateAndCity = false;
	    		}else if($scope.defineBy == 'city'){
	    			$scope.taxVo.defineByState = false;
	    			$scope.taxVo.defineByStateAndCity = true;
	    		}
        		$scope.taxVo.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
        		$scope.taxVo.taxRulesList = $scope.taxList.taxRulesList;
        		$scope.taxVo.createdBy = $cookieStore.get('createdBy');
        		$scope.taxVo.modifiedBy = $cookieStore.get('modifiedBy');
        		//alert(angular.toJson($scope.taxVo));
        		$scope.getData('statutorySetupsController/saveProfessionalTax.json', angular.toJson($scope.taxVo), 'saveProfessionalTax');
		  }
       }
   };
   
   $scope.correctHistorySave= function($event){
	   if($('#pTaxForm').valid()){
		   $scope.taxVo.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
		   $scope.taxVo.modifiedBy = $cookieStore.get('modifiedBy');
		   $scope.taxVo.taxRulesList = $scope.taxList.taxRulesList;
		   
		   if($scope.defineBy == 'state'){
			   $scope.taxVo.defineByState = true;
			   $scope.taxVo.defineByStateAndCity = false;
	   	   }else{
	   		   $scope.taxVo.defineByState = false;
	   		   $scope.taxVo.defineByStateAndCity = true;
	   	   }
		 //  alert(angular.toJson($scope.location));
		   $scope.getData('statutorySetupsController/saveProfessionalTax.json', angular.toJson($scope.taxVo), 'saveProfessionalTax');
	   }
   };
   
   $scope.transactionDatesListChange = function(){
       $scope.getData('statutorySetupsController/getProfessionalTaxById.json', { professionalTaxId : ($scope.transactionModel != undefined || $scope.transactionModel != null) ? $scope.transactionModel:'0', customerId: "" }, 'professionalTax')
       $('.dropdown-toggle').removeClass('disabled');
   };

   
   //Pop Up actions
  $scope.plusIconAction = function(){  	
	    $scope.tax = new Object();
 		$scope.popUpSave = true;
 		$scope.popUpUpdate = false;
   };
   
   //var regex = new RegExp("^[1-9]\d*(((,\d{3}){1})?(\.\d{0,2})?)$");
   //var regex = new RegExp("([0-9]*)\\.?\d{0,2}$");
   var regex=new RegExp("([0-9]{1,3})(\\,?[0-9]{3})*\\.?\d{0,2}$");
   $scope.saveTax = function(){  
	   
	   if($scope.tax.slabFrom != null && $scope.tax.slabFrom != undefined && $scope.tax.slabFrom != "" 
		   && (!regex.test($scope.tax.slabFrom) || 
				   	(regex.test($scope.tax.slabFrom) && ( String($scope.tax.slabFrom).match(new RegExp('\\.','g')  || [] ) != undefined && String($scope.tax.slabFrom).match(new RegExp('\\.','g')  || [] ) != null)&&
				   			(String($scope.tax.slabFrom).match(new RegExp('\\.','g')  || [] ).length > 1 || 
				   				( String($scope.tax.slabFrom).match(new RegExp('\\.','g')  || [] ).length == 1 && $scope.tax.slabFrom.split(".")[1].length > 2 )
				   			)
				   	)
		   		)){
		   $scope.Messager('error', 'Error', 'Invalid slab from value '); 
	   }else if($scope.tax.slabTo != null && $scope.tax.slabTo != undefined && $scope.tax.slabTo != "" && 
			   (!regex.test($scope.tax.slabTo) || 
					   (regex.test($scope.tax.slabTo) && ( String($scope.tax.slabTo).match(new RegExp('\\.','g')  || [] ) != undefined && String($scope.tax.slabTo).match(new RegExp('\\.','g')  || [] ) != null)&&
					   			( String($scope.tax.slabTo).match(new RegExp('\\.','g')  || [] ).length > 1 || 
					   				( String($scope.tax.slabTo).match(new RegExp('\\.','g')  || [] ).length == 1 && $scope.tax.slabTo.split(".")[1].length > 2 )
					   			)
					   	)
			   		)){
		   $scope.Messager('error', 'Error', 'Invalid slab to value '); 
	   }else if($scope.tax.amount != null && $scope.tax.amount != undefined && $scope.tax.amount != "" 
		   && (!regex.test($scope.tax.amount) || 
				   (regex.test($scope.tax.amount) && ( String($scope.tax.amount).match(new RegExp('\\.','g')  || [] ) != undefined && String($scope.tax.amount).match(new RegExp('\\.','g')  || [] ) != null)&&
				   			( String($scope.tax.amount).match(new RegExp('\\.','g')  || [] ).length > 1 || 
				   				( String($scope.tax.amount).match(new RegExp('\\.','g')  || [] ).length == 1 && $scope.tax.amount.split(".")[1].length > 2 )
				   			)
				   	)
		   		)){
		   $scope.Messager('error', 'Error', 'Invalid amount value '); 
	   }else if($scope.tax.ptAmount != null && $scope.tax.ptAmount != undefined && $scope.tax.ptAmount != "" 
		   && (!regex.test($scope.tax.ptAmount)|| 
				   (regex.test($scope.tax.ptAmount) && ( String($scope.tax.ptAmount).match(new RegExp('\\.','g')  || [] ) != undefined && String($scope.tax.ptAmount).match(new RegExp('\\.','g')  || [] ) != null)&&
				   			( String($scope.tax.ptAmount).match(new RegExp('\\.','g')  || [] ).length > 1 || 
				   				( String($scope.tax.ptAmount).match(new RegExp('\\.','g')  || [] ).length == 1 && $scope.tax.ptAmount.split(".")[1].length > 2 )
				   			)
				   	)
		   		)){
		   $scope.Messager('error', 'Error', 'Invalid PT amount value '); 
	   }else if($scope.tax.slabFrom != null && $scope.tax.slabFrom != undefined && $scope.tax.slabFrom != ""  && 
			   			$scope.tax.slabTo != null && $scope.tax.slabTo != undefined && $scope.tax.slabTo != "" &&
			   									parseFloat($scope.tax.slabFrom) > parseFloat($scope.tax.slabTo)){
		   $scope.Messager('error', 'Error', 'Slab From should not be greater than Slab To'); 
	   }else{
		   angular.forEach($scope.taxList.taxRulesList, function(value, key){	
			   if(value.ptMonth == $scope.tax.ptMonth && value.slabFrom == $scope.tax.slabFrom && value.slabTo == $scope.tax.slabTo && 
			    		  ( value.amount == $scope.tax.amount ||  value.ptAmount == $scope.tax.ptAmount)){
			    	  $scope.Messager('error', 'Error', 'Duplicate PT Slab Exists',true); 
			    	  status = true;			    		
			    }
			         
		   });
		   if(!status && $scope.tax != null && $scope.tax != undefined && $scope.tax != "" 
			   &&(($scope.tax.ptMonth != "" && $scope.tax.ptMonth != undefined &&  $scope.tax.ptMonth != "")
					   || ($scope.tax.slabFrom != null && $scope.tax.slabFrom != undefined && $scope.tax.slabFrom != "")
					   || ($scope.tax.slabTo != null && $scope.tax.slabTo != undefined && $scope.tax.slabTo != "")
					   || ($scope.tax.amount != null && $scope.tax.amount != undefined && $scope.tax.amount != "")
					   || ($scope.tax.ptAmount != null && $scope.tax.ptAmount != undefined && $scope.tax.ptAmount != ""))){
				   $scope.taxList.taxRulesList.push({
					   		'ptMonth': $scope.tax.ptMonth != "" && $scope.tax.ptMonth != undefined && $scope.tax.ptMonth != null ? $('#ptMonth option:selected').html() : "", 	 	
					   		'slabFrom':$scope.tax.slabFrom,
					   		'slabTo':$scope.tax.slabTo,
					   		'amount':$scope.tax.ptMonth != "" && $scope.tax.ptMonth != undefined && $scope.tax.ptMonth != null && $scope.tax.ptMonth == 'All Months'? $scope.tax.amount : null,
					   		'ptAmount':$scope.tax.ptMonth != "" && $scope.tax.ptMonth != undefined && $scope.tax.ptMonth != null && $scope.tax.ptMonth != 'All Months' ? $scope.tax.ptAmount : null,
					   		'professionalTaxId':0,
					   		'professionalTaxInfoId':0
				   });   
				   $('div[id^="myModal"]').modal('hide');
				   $scope.popUpSave = true;
				   $scope.popUpUpdate =false;
		   }else if(status
				   /*($scope.tax.ptMonth == "" || $scope.tax.ptMonth == undefined &&  $scope.tax.ptMonth == "")
			   || ($scope.tax.slabFrom != null || $scope.tax.slabFrom != undefined || $scope.tax.slabFrom != "")
			   || ($scope.tax.slabTo != null || $scope.tax.slabTo != undefined || $scope.tax.slabTo != "")
			   || ($scope.tax.amount != null && $scope.tax.amount != undefined && $scope.tax.amount != "")
			   || ($scope.tax.ptAmount != null && $scope.tax.ptAmount != undefined && $scope.tax.ptAmount != "")*/
				   ){
			   $scope.Messager('error', 'Error', 'Duplicate PT Slab Exists',true); 
		   }
	   }
   };
   
   $scope.DeleteTax = function(index){    	
	   var del = false;
	   if($scope.updateBtn){
		   del  = false;
	   }else{
		    del = confirm('Are you sure you want to delete this Professional Tax ? ');
		   if (del) {
			   $scope.taxList.taxRulesList.splice(index,1);
		   }
	   }
	   $scope.popUpSave = true;
       $scope.popUpUpdate = false;
    	  
    };
		       
     $scope.EditTax = function(event){   
    	$scope.taxIndex = event;
      	
    	var data =  $scope.taxList.taxRulesList[event];
      	$scope.tax = data;
      	$scope.ptMonth = data.ptMonth;
      	$scope.slabFrom = data.slabFrom;
      	$scope.slabTo = data.slabTo;
      	$scope.amount = data.amount;
      	$scope.ptAmount = data.ptAmount;
      	
      	$scope.tax.ptMonth = data.ptMonth;
      	$scope.tax.slabFrom = data.slabFrom;
      	$scope.tax.slabTo = data.slabTo;
      	$scope.tax.amount = data.amount;
      	$scope.tax.ptAmount = data.ptAmount;
      	
      	$scope.popUpSave = false;
      	$scope.popUpUpdate =true;
     };
		          
	
     $scope.updateTax = function(event){
    	 if($scope.tax.slabFrom != null && $scope.tax.slabFrom != undefined && $scope.tax.slabFrom != "" 
  		   && (!regex.test($scope.tax.slabFrom) || 
  				   	(regex.test($scope.tax.slabFrom) && ( String($scope.tax.slabFrom).match(new RegExp('\\.','g')  || [] ) != undefined && String($scope.tax.slabFrom).match(new RegExp('\\.','g')  || [] ) != null)&&
  				   			( String($scope.tax.slabFrom).match(new RegExp('\\.','g')  || [] ).length > 1 || 
  				   				( String($scope.tax.slabFrom).match(new RegExp('\\.','g')  || [] ).length == 1 && $scope.tax.slabFrom.split(".")[1].length > 2 )
  				   			)
  				   	)
  		   		)){
  		   $scope.Messager('error', 'Error', 'Invalid slab from value '); 
  	   }else if($scope.tax.slabTo != null && $scope.tax.slabTo != undefined && $scope.tax.slabTo != "" && 
  			   (!regex.test($scope.tax.slabTo) || 
  					   (regex.test($scope.tax.slabTo) && ( String($scope.tax.slabTo).match(new RegExp('\\.','g')  || [] ) != undefined && String($scope.tax.slabTo).match(new RegExp('\\.','g')  || [] ) != null)&&
  					   			( String($scope.tax.slabTo).match(new RegExp('\\.','g')  || [] ).length > 1 || 
  					   				( String($scope.tax.slabTo).match(new RegExp('\\.','g')  || [] ).length == 1 && $scope.tax.slabTo.split(".")[1].length > 2 )
  					   			)
  					   	)
  			   		)){
  		   $scope.Messager('error', 'Error', 'Invalid slab to value '); 
  	   }else if($scope.tax.amount != null && $scope.tax.amount != undefined && $scope.tax.amount != "" 
  		   && (!regex.test($scope.tax.amount) || 
  				   (regex.test($scope.tax.amount) && ( String($scope.tax.amount).match(new RegExp('\\.','g')  || [] ) != undefined && String($scope.tax.amount).match(new RegExp('\\.','g')  || [] ) != null)&&
  				   			( String($scope.tax.amount).match(new RegExp('\\.','g')  || [] ).length > 1 || 
  				   				( String($scope.tax.amount).match(new RegExp('\\.','g')  || [] ).length == 1 && $scope.tax.amount.split(".")[1].length > 2 )
  				   			)
  				   	)
  		   		)){
  		   $scope.Messager('error', 'Error', 'Invalid amount value '); 
  	   }else if($scope.tax.ptAmount != null && $scope.tax.ptAmount != undefined && $scope.tax.ptAmount != "" 
  		   && (!regex.test($scope.tax.ptAmount)|| 
  				   (regex.test($scope.tax.ptAmount) && ($scope.tax.ptAmount.match(new RegExp('\\.','g')  || [] ) != undefined && String($scope.tax.ptAmount).match(new RegExp('\\.','g')  || [] ) != null)&&
  				   			( String($scope.tax.ptAmount).match(new RegExp('\\.','g')  || [] ).length > 1 || 
  				   				( String($scope.tax.ptAmount).match(new RegExp('\\.','g')  || [] ).length == 1 && $scope.tax.ptAmount.split(".")[1].length > 2 )
  				   			)
  				   	)
  		   		)){
  		   $scope.Messager('error', 'Error', 'Invalid PT amount value '); 
  	   }else if($scope.tax.slabFrom != null && $scope.tax.slabFrom != undefined && $scope.tax.slabFrom != ""  && 
  			   			$scope.tax.slabTo != null && $scope.tax.slabTo != undefined && $scope.tax.slabTo != "" &&
  			   									parseFloat($scope.tax.slabFrom) > parseFloat($scope.tax.slabTo)){
  		   $scope.Messager('error', 'Error', 'Slab From should not be greater than Slab To'); 
  	   }else{
  		   $scope.taxList.taxRulesList.splice($scope.taxIndex,1);
  		   angular.forEach($scope.taxList.taxRulesList, function(value, key){	
  			   if(value.ptMonth == $scope.tax.ptMonth && value.slabFrom == $scope.tax.slabFrom && value.slabTo == $scope.tax.slabTo && 
  			    		 ( value.amount == $scope.tax.amount || value.ptAmount == $scope.tax.ptAmount)){
  			    	  $scope.Messager('error', 'Error', 'Duplicate PT Slab Exists',true); 
  			    	  status = true;			    		
  			    }
  			         
  		   });
  		   if(!status && $scope.tax != null && $scope.tax != undefined && $scope.tax != "" 
  			   &&(($scope.tax.ptMonth != "" && $scope.tax.ptMonth != undefined &&  $scope.tax.ptMonth != "")
  					   || ($scope.tax.slabFrom != null && $scope.tax.slabFrom != undefined && $scope.tax.slabFrom != "")
  					   || ($scope.tax.slabTo != null && $scope.tax.slabTo != undefined && $scope.tax.slabTo != "")
  					   || ($scope.tax.amount != null && $scope.tax.amount != undefined && $scope.tax.amount != "")
  					   || ($scope.tax.ptAmount != null && $scope.tax.ptAmount != undefined && $scope.tax.ptAmount != ""))){
  				   $scope.taxList.taxRulesList.push({
  					   		'ptMonth': $scope.tax.ptMonth != "" && $scope.tax.ptMonth != undefined && $scope.tax.ptMonth != null ? $('#ptMonth option:selected').html() : "", 	 	
  					   		'slabFrom':$scope.tax.slabFrom,
  					   		'slabTo':$scope.tax.slabTo,
  					   		'amount':$scope.tax.ptMonth != "" && $scope.tax.ptMonth != undefined && $scope.tax.ptMonth != null && $scope.tax.ptMonth == 'All Months'? $scope.tax.amount : null,
  						   	'ptAmount':$scope.tax.ptMonth != "" && $scope.tax.ptMonth != undefined && $scope.tax.ptMonth != null && $scope.tax.ptMonth != 'All Months' ? $scope.tax.ptAmount : null,
  					   		'professionalTaxId':0,
  					   		'professionalTaxInfoId':0
  				   });   
  				   $('div[id^="myModal"]').modal('hide');
  				   $scope.popUpSave = true;
  				   $scope.popUpUpdate =false;
  		   }else if(status
  				   /*($scope.tax.ptMonth == "" || $scope.tax.ptMonth == undefined &&  $scope.tax.ptMonth == "")
  			   || ($scope.tax.slabFrom != null || $scope.tax.slabFrom != undefined || $scope.tax.slabFrom != "")
  			   || ($scope.tax.slabTo != null || $scope.tax.slabTo != undefined || $scope.tax.slabTo != "")
  			   || ($scope.tax.amount != null && $scope.tax.amount != undefined && $scope.tax.amount != "")
  			   || ($scope.tax.ptAmount != null && $scope.tax.ptAmount != undefined && $scope.tax.ptAmount != "")*/
  				   ){
  			   $scope.Messager('error', 'Error', 'Duplicate PT Slab Exists',true); 
  		   }
  	   }
     }
   /*$scope.monthChange = function(){
	   if($scope.tax.PTMonthBasedOnFrequency == "All"){
		   $scope.isAllMonths = true;
	   }else{
		   $scope.isAllMonths = false;
	   }
   };*/
   
   $scope.validatePrice = function() {
   
   }

   $scope.fun_Close = function(){
	   if($scope.popUpUpdate){
		    $scope.tax.ptMonth = $scope.ptMonth;
	      	$scope.tax.slabFrom = $scope.slabFrom;
	      	$scope.tax.slabTo = $scope.slabTo;
	      	$scope.tax.amount = $scope.amount;
	      	$scope.tax.ptAmount = $scope.ptAmount;
	   }else{
		   $scope.tax = new Object();
	   }
	   
	   $('div[id^="myModal"]').modal('hide');
   }

}]);

