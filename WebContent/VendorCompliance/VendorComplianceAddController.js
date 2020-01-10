'use strict';

VendorComplianceController.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                    //console.log(angular.toJson(element[0].files[0]));
                });
            });
        }
    };
}]);

VendorComplianceController.controller("VendorComplianceAddCtrl", ['$scope', '$http', '$resource','$location','$filter', '$routeParams','myservice','$cookieStore', function ($scope, $http, $resource, $location, $filter, $routeParams, myservice, $cookieStore) {
	 $.material.init();
	 $scope.listPanel = false;
	 $scope.vCompliance = new Object();
	 $scope.transDate1 = new Object();
	 var vendorComplianceDataTable;
	 var roleName = $cookieStore.get('roleName');
	 $scope.disableDocument= false;
	 
	 $scope.list_status = [{ id:"Y" , name:"Active"},{ id:"N" , name:"In Active"}];	
	
    /* $('#transactionDate, #issueDate, #expiryDate, #renewalDate').bootstrapMaterialDatePicker({
	     time: false,
	     clearButton: true,
	     format : "DD/MM/YYYY"
     });*/

     $('#transactionDate, #issueDate, #expiryDate, #renewalDate').datepicker({
         changeMonth: true,
         changeYear: true,
         dateFormat:"dd/mm/yy",
         showAnim: 'slideDown'
       	  
       });
     
	 $scope.checkCode = false;
	// $scope.vCompliance = new Object();
	
	 if($cookieStore.get("roleNamesArray").includes('Vendor Admin')){
		 $scope.vendorAdmin = true;
	 }else {
		 $scope.vendorAdmin = false;
	 }
	 
	 
	 $scope.documentStatus_list = [{"id":"Saved", "name":"Saved"},
                                   {"id":"Verified", "name":"Verified"}];
	 
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
       if ($routeParams.id > 0) {
    	   
    		   $scope.readOnly = false;
	           $scope.onlyRead = true;
	           $scope.listPanel = true;
	           $scope.datesReadOnly = true;
	           $scope.updateBtn = false;
	           $scope.saveBtn = false;
	           $scope.viewOrUpdateBtn = false;
	           $scope.correcttHistoryBtn = false;
	           $scope.resetBtn = false;
	           $scope.transactionList = false;
	           $scope.cancelBtn = false;
	           $scope.verifyButton = false;
	           $scope.gridButtons = true;
	           /*if($scope.showPanel){
	        	   $scope.viewOrUpdateBtn = true;
	           }else{
	        	   $scope.viewOrUpdateBtn = false;
	           }*/
    	   /*}else{
	           $scope.readOnly = true;
	           $scope.onlyRead = false;
	           $scope.listPanel = false;
	           $scope.datesReadOnly = true;
	           $scope.updateBtn = true;
	           $scope.saveBtn = false;
	           $scope.viewOrUpdateBtn = true;
	           $scope.correcttHistoryBtn = false;
	           $scope.resetBtn = false;
	           $scope.transactionList = false;
	           $scope.cancelBtn = false;
	           $scope.verifyButton = false;
    	   }*/
           
       } else {
    	   
    	   $('#transactionDate').val($filter('date')(new Date(),'dd/MM/yyyy'));
    	   $scope.readOnly = false;
           $scope.onlyRead = false;
           $scope.listPanel = false;
           $scope.datesReadOnly = false;
           $scope.updateBtn = false;
           $scope.viewOrUpdateBtn = false;
           $scope.correcttHistoryBtn = false;
           $scope.transactionList = false;
           $scope.gridButtons = true;
           $scope.cancelBtn = false;
           $scope.saveBtn = false;
           $scope.resetBtn = false;
           $scope.verifyButton = false;
    	   /*if(roleName != 'Vendor Admin'){
	           $scope.resetBtn = false;
	           $scope.verifyButton = false;
    	   }else{
	           $scope.resetBtn = false;
	           $scope.verifyButton = false;
	       }*/
       }

       $scope.updateBtnAction = function (this_obj) {
    	   $scope.readOnly = false;
           $scope.onlyRead = true;
           $scope.listPanel = true;
           $scope.datesReadOnly = false;
           $scope.updateBtn = false;
           $scope.saveBtn = true;
           $scope.viewOrUpdateBtn = false;
           $scope.correcttHistoryBtn = false;
           $scope.resetBtn = false;
           $scope.transactionList = false;
           $scope.gridButtons = false;
           $scope.cancelBtn = true;
           $scope.verifyButton = true;
           $scope.vCompliance.transactionDate = $filter('date')(new Date(),"dd/MM/yyyy");
           $('.dropdown-toggle').removeClass('disabled');
    	   if(roleName != 'Vendor Admin'){
	           $scope.verifyButton = true;
    	   }else{
               $scope.verifyButton = false;
               $('.dropdown-toggle').removeClass('disabled');
	       }
    	   
       }
       
       
       $scope.viewOrEditHistory = function () {
    	   $scope.onlyRead = true;
    	   $scope.listPanel = true;
    	   $scope.gridButtons = false;
           $scope.readOnly = false;
           $scope.datesReadOnly = false;
           $scope.updateBtn = false;
           $scope.saveBtn = false;
           $scope.viewOrUpdateBtn = false;
           $scope.correcttHistoryBtn = true;
           $scope.resetBtn = false;      
           $scope.transactionList = true;
           $scope.cancelBtn = true;
           $scope.getData('vendorComplianceController/getVendorComplianceTransactionDatesList.json', { companyId: $scope.vCompliance.companyId, customerId: $scope.vCompliance.customerId ,vendorComplianceUniqueId: $scope.vCompliance.vendorComplianceUniqueId }, 'transactionDatesChange');       
           $('.dropdown-toggle').removeClass('disabled');
       };
       
       $scope.cancelBtnAction = function(){
    	  
    	   if( ($scope.correcttHistoryBtn || $scope.saveBtn) && (( $scope.vCompliance.defineComplianceTypeId != ""  && $scope.vCompliance.defineComplianceTypeId != undefined && $scope.vCompliance.defineComplianceTypeId != null)
       			|| ( $scope.vCompliance.registeredState != "" && $scope.vCompliance.registeredState != undefined  && $scope.vCompliance.registeredState != null)
       			|| ( $('#issueDate').val() != null &&  $('#issueDate').val() != undefined  &&  $('#issueDate').val() != "" )
       			|| ( $('#expiryDate').val() != null && $('#expiryDate').val() != undefined &&  $('#expiryDate').val() != "" )
       			|| ( $('#renewalDate').val() != null && $('#renewalDate').val() != undefined &&  $('#renewalDate').val() != "" )
       			|| ( $scope.vCompliance.licensePolicyNumber != null && $scope.vCompliance.licensePolicyNumber != undefined && $scope.vCompliance.licensePolicyNumber != "" )
       			|| ( $scope.vCompliance.numberOfWorkersCovered != null && $scope.vCompliance.numberOfWorkersCovered != undefined  && $scope.vCompliance.numberOfWorkersCovered != "")
       			|| ( $scope.vCompliance.transactionAmount != null && $scope.vCompliance.transactionAmount != undefined  && $scope.vCompliance.transactionAmount != "")
       			|| ( $scope.vCompliance.amountType != null && $scope.vCompliance.amountType != undefined  && $scope.vCompliance.amountType != ""))){
   		
    		   var r = confirm("If you click on Ok all the changes will be lost. Are you sure? ");    
    		   if(r){
		    	   $scope.disableDiv = false;
		    	   $scope.showPanel  = false;
		    	   $scope.gridButtons = true;
			    	if ($routeParams.id ==  0) {
			    	    $scope.readOnly = false;
			    	    $scope.onlyRead = false;
			    	}else{
			    	    $scope.onlyRead = true;
			    	    $scope.showPanel  = false;
				    	$scope.readOnly = false;
				        $scope.getData('vendorComplianceController/getVendorComplianceById.json', { vendorComplianceId: $routeParams.id,customerId : ''}, 'vendorComplianceList');
					   }
    		   }else{
    			   
    		   }
    	   }else{
			   $scope.disableDiv = false;
	    	   $scope.showPanel  = false;
	    	   $scope.gridButtons = true;
	    	   if ($routeParams.id ==  0) {
	    		   $scope.readOnly = false;
	    		   $scope.onlyRead = false;
	    	   }else{
		    	   $scope.onlyRead = true;
		    	   $scope.readOnly = false;
			       $scope.getData('vendorComplianceController/getVendorComplianceById.json', { vendorComplianceId: $routeParams.id,customerId : ''}, 'vendorComplianceList');
				}
			}
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
        		} else if (type == 'vendorComplianceList') {
            	   $scope.vComplianceList = response.data;
                   $scope.vCompliance = response.data.vendorComplianceVo[0];
                   if($scope.vCompliance != undefined){
                	   $scope.listPanel=true;
                	   $scope.result = response.data.vendorComplianceList;
                	   //console.log("1: "+JSON.stringify(response.data.vendorComplianceVo[0].vendorComplianceUniqueId));
                	   $scope.vCompliance.vendorComplianceUniqueId = response.data.vendorComplianceVo[0].vendorComplianceUniqueId;
                	   $scope.vCompliance.transactionDate =  $filter('date')( response.data.vendorComplianceVo[0].transactionDate, 'dd/MM/yyyy');
                	   $scope.vCompliance.issueDate =  $filter('date')( response.data.vendorComplianceVo[0].issueDate, 'dd/MM/yyyy');
                	   $scope.vCompliance.expiryDate =  $filter('date')( response.data.vendorComplianceVo[0].expiryDate, 'dd/MM/yyyy');
                	   
                	   if(response.data.vendorComplianceVo[0].renewalDate != null && response.data.vendorComplianceVo[0].renewalDate != ""){
                		   $scope.vCompliance.renewalDate =  $filter('date')( response.data.vendorComplianceVo[0].renewalDate, 'dd/MM/yyyy');
                	   }
                	   $scope.transDate1 =  $filter('date')( response.data.vendorComplianceVo[0].transactionDate, 'dd/MM/yyyy');
            	       $scope.getData('vendorComplianceController/getVendorComplianceListBySearch.json', { customerId:  $scope.vCompliance.customerId, companyId: $scope.vCompliance.companyId, vendorId: $scope.vCompliance.vendorId,defineComplianceTypeId:0, transactionDate:''}, 'vendorChange');

                	   $scope.vComplianceList.companyList  = response.data.companyList;
                	   $scope.vComplianceList.currencyList = response.data.currencyList;
                	   $scope.locationList = response.data.locationList;
                	   $scope.locationChange();
                   }else{
                	   $scope.vCompliance = new Object();
                	   if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
      		                $scope.vCompliance.customerId=response.data.customerList[0].customerId;		                
      		                $scope.customerChange();
      		           }
                   }
                   // for button views
                   if($scope.buttonArray == undefined || $scope.buttonArray == '')
                   $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Vendor Compliance'}, 'buttonsAction');
               }else if (type == 'customerChange') {
                   $scope.vComplianceList.companyList = response.data;
                   
                   if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
      	                $scope.vCompliance.companyId= response.data[0].id;
      	                $scope.companyChange();
                   }   
               }else if (type == 'companyChange') {
            	   var count = 0;
            	   //console.log(JSON.stringify(response.data));
            	  $scope.vComplianceList.countryList = response.data.countryList;
            	  $scope.vComplianceList.vendorList = response.data.vendorList;
            	  $scope.vComplianceList.complianceTypesList = response.data.complianceTypesList;
            	  if( response.data.countryList[0] != undefined && response.data.countryList[0] != "" && response.data.countryList.length == 1 ){
            		  $scope.vCompliance.countryId= response.data.countryList[0].id;
            		  $scope.countryChange();
            		  count++;
            	  }
            	
            	  if( response.data.vendorList[0] != undefined && response.data.vendorList[0] != "" && response.data.vendorList.length == 1 ){
            		  $scope.vCompliance.vendorId= response.data.vendorList[0].id;
            		  count++;
            	  }
            	 
            	  if(count == 2 && $scope.onlyRead == false){
            		  $scope.vendorChange();
            	  }
               } else if(type == 'locationChange'){
            	   if(response.data.profileList != null && response.data.profileList != "" && response.data.profileList != undefined && response.data.profileList.length > 0)
        			   $scope.amountType = response.data.profileList[0].defaultCurrencyId;
            	   if(response.data.vendorComplianceList != undefined && response.data.vendorComplianceList != null && response.data.vendorComplianceList != ""){
            		   $scope.result = response.data.vendorComplianceList;
	            	   $scope.listPanel = true;
	            	}else{
	            		$scope.listPanel = true;
	            		$scope.updateBtn = false;
	            		$scope.saveBtn = false;
		            	$scope.resetBtn = false;
		            	$scope.viewOrUpdateBtn = false;
		            	if(roleName != 'Vendor Admin'){
		       	           $scope.resetBtn = false;
		       	           $scope.verifyButton = false;
		           	    }else{
		       	           $scope.resetBtn = true;
		       	           $scope.verifyButton = false;
		       	           $scope.saveBtn = false;
		       	        }	
	            	}
            	  
               } else if(type == 'countrychange'){
            	   $scope.vComplianceList.statesList = response.data;
               }else if (type == 'saveVendorCompliance') {
            	   //console.log(JSON.stringify(response.data));
            	   if(response.data.id > 0){
		            	$scope.Messager('success', 'success', 'Vendor Compliance Saved Successfully',buttonDisable);
		            	$location.path('/VendorComplianceAdd/'+response.data.id);
            	   }else if(response.data.id < 0){
            		   $scope.Messager('error', 'Error', response.data.name,buttonDisable);
	               }else{
	            	   $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
	               }
               
               }else if(type == 'vendorList'){
            	  
            	   $scope.vComplianceList = response.data;
            	  // console.log(angular.toJson($scope.vComplianceList.complianceTypesList));
            	   $scope.vCompliance = response.data.vendorComplianceVo[0];
            	   $scope.readOnly = true;
            	   $scope.showPanel = true;
            	   $scope.vCompliance.registeredState = response.data.vendorComplianceVo[0].registeredState;
            	   $scope.vCompliance.transactionDate = $filter('date')(response.data.vendorComplianceVo[0].transactionDate,'dd/MM/yyyy');
   
            	   $scope.vCompliance.defineComplianceTypeId = response.data.vendorComplianceVo[0].defineComplianceTypeId;
            	   
            	   if(response.data.vendorComplianceVo[0].amountType == undefined || response.data.vendorComplianceVo[0].amountType == null || response.data.vendorComplianceVo[0].amountType ==  0){
            		   $scope.vCompliance.amountType = response.data.profileList[0].defaultCurrencyId;
            	   }else{
            		   $scope.vCompliance.amountType = response.data.vendorComplianceVo[0].amountType;
            	   }
            		   
            		   // $scope.vCompliance.transactionDate =  $filter('date')( response.data.vendorComplianceVo[0].transactionDate, 'dd/MM/yyyy');
            	   $scope.vCompliance.issueDate =  $filter('date')( response.data.vendorComplianceVo[0].issueDate, 'dd/MM/yyyy');
            	   $scope.vCompliance.expiryDate =  $filter('date')( response.data.vendorComplianceVo[0].expiryDate, 'dd/MM/yyyy');
            	   $scope.vCompliance.renewalDate = response.data.vendorComplianceVo[0].renewalDate != undefined && response.data.vendorComplianceVo[0].renewalDate != null && response.data.vendorComplianceVo[0].renewalDate != "" ? $filter('date')( response.data.vendorComplianceVo[0].renewalDate, 'dd/MM/yyyy') : null;
            	   $scope.transDate1 =  $filter('date')( response.data.vendorComplianceVo[0].transactionDate, 'dd/MM/yyyy');
            	   $scope.vCompliance.countryId= response.data.countryList[0].id;
         		   $scope.countryChange();
         		   if(response.data.vendorComplianceVo[0].path != null || response.data.vendorComplianceVo[0].path != undefined){
         			   $scope.downloadfile = true;
         		   }
         		   
            	   
               }else if(type == 'VendorChange'){
            	  //console.log(JSON.stringify(response.data));
	        	   $scope.locationList = response.data.locationList;
	        	   if( response.data.locationList != undefined && response.data.locationList[0] != "" && response.data.locationList.length == 1 ){
	   	                $scope.vCompliance.locationId = response.data.locationList[0].id;
	   	               // 
	   	           }
	        	   $scope.locationChange();
	           }else if (type == 'transactionDatesChange') {
            	  // console.log(JSON.stringify(response.data));
	            	var obj = response.data;
	            	var k = response.data.length;
	            	if(response.data.length > 1){
		            	for(var i = obj.length-1; i> 0;i--){
			            	 if($scope.dateDiffer(response.data[i].name.split('-')[0])){
			            		 k =  response.data[i];
			            		 break;
			            	 }
		            	}
	            	}else{
	            		k = response.data[0];
	            	}
	            	
	            	$scope.transactionModel=k.id;
	            	$scope.transactionDatesList = response.data;
	    	        $scope.getData('vendorComplianceController/getVendorComplianceById.json', { vendorComplianceId: $scope.transactionModel,customerId : ''}, 'vendorComplianceList')
               }
           },
           function () { 
        	   $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
        	   $filter('date')($('#transactionDate'),'dd/MM/yyyy');
        	   $filter('date')($('#issueDate'),'dd/MM/yyyy');
        	   $filter('date')($('#expiryDate'),'dd/MM/yyyy');
        	   $filter('date')($('#renewalDate'),'dd/MM/yyyy');
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
       
       $scope.getData('vendorComplianceController/getVendorComplianceById.json', { vendorComplianceId: $routeParams.id,customerId : $cookieStore.get('customerId')}, 'vendorComplianceList')
       	
       $scope.customerChange = function () {
           $scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId:  $scope.vCompliance.customerId != undefined ? $scope.vCompliance.customerId : '0' ,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.vCompliance.companyId != undefined ? $scope.vCompliance.companyId :0 }, 'customerChange');
       };
       	
	   $scope.companyChange = function() {
		   $scope.getData('vendorComplianceController/getDropdowns.json',{customerId:  $scope.vCompliance.customerId ? $scope.vCompliance.customerId : '0', companyId:  $scope.vCompliance.companyId ? $scope.vCompliance.companyId : '0',vendorId:($cookieStore.get('vendorId') != null && $cookieStore.get('vendorId') != "") ? $cookieStore.get('vendorId') : $scope.vCompliance.vendorId != undefined ? $scope.vCompliance.vendorId : 0 }, 'companyChange')

	  };
	  	
	  $scope.locationChange = function(){
	  		if($scope.vCompliance.customerId != undefined  && $scope.vCompliance.customerId != null
	  				&& $scope.vCompliance.companyId != undefined  && $scope.vCompliance.companyId != null 
	  				&& $scope.vCompliance.vendorId != undefined && $scope.vCompliance.vendorId != null  &&  $scope.vCompliance.locationId != undefined && $scope.vCompliance.locationId != null){
		    	$scope.getData('vendorComplianceController/getVendorComplianceListBySearch.json', { customerId:  $scope.vCompliance.customerId, companyId: $scope.vCompliance.companyId, vendorId: $scope.vCompliance.vendorId,locationId:$scope.vCompliance.locationId }, 'locationChange');

	  		}else{
	  			$scope.result = [];
	  		}
	  };
	  	
	  $scope.countryChange = function(){
		   $scope.getData('CommonController/statesListByCountryId.json', { countryId: $scope.vCompliance.countryId }, 'countrychange');
		  
	  };
	  
	  $scope.VendorChange = function(){
		  
		   if($scope.vCompliance.countryId != undefined || $scope.vCompliance.countryId != null){
				$scope.getData('vendorComplianceController/getLocationListByVendor.json',{customerId: ($scope.vCompliance.customerId == undefined || $scope.vCompliance.customerId == null )? '0' : $scope.vCompliance.customerId, companyId : ($scope.vCompliance.companyId == undefined || $scope.vCompliance.companyId == null) ? '0' : $scope.vCompliance.companyId, vendorId : ($scope.vCompliance.vendorId == undefined || $scope.vCompliance.vendorId == null) ? '0' : $scope.vCompliance.vendorId } , 'VendorChange');
			}
	  }
	  
		
	   
       $scope.save = function (event) {
    	   var formData = new FormData();
    	   if($('#vendorComplianceForm').valid()){
    		  // alert(angular.toJson($scope.vCompliance));
    		   if($scope.transDate1 != '' && $scope.transDate1 != undefined  && new Date(moment($scope.transDate1,'DD/MM/YYYY').format('YYYY-MM-DD')) .getTime() > new Date(moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime()){
        		   $scope.Messager('error', 'Error', 'Transaction date should not be less than previous transaction date');
            	   $scope.vCompliance.transactionDate =  $scope.transDate1;
    		   }else if(new Date(moment($('#issueDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')) .getTime() >= new Date(moment($('#expiryDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime()){
        		   $scope.Messager('error', 'Error', 'Expiry date should not be less than issue date');
        	   }else if($('#renewalDate') != undefined && $('#renewalDate') != null && $('#renewalDate') != "" && new Date(moment($('#renewalDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')) .getTime() < new Date(moment($('#issueDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime()){
        		   $scope.Messager('error', 'Error', 'Renewal date should not be less than issue date');
        	   }else if($('#renewalDate') != undefined && $('#renewalDate') != null && $('#renewalDate') != "" && new Date(moment($('#renewalDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')) .getTime() > new Date(moment($('#expiryDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime()){
        		   $scope.Messager('error', 'Error', 'Renewal date should not be greater than expiry date');
        	   }else{
	    		   $scope.vCompliance.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
	    		   $scope.vCompliance.issueDate = moment($('#issueDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
	    		   $scope.vCompliance.expiryDate = moment($('#expiryDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
	    		   $scope.vCompliance.renewalDate = $('#renewalDate').val() != undefined && $('#renewalDate').val() != null  && $('#renewalDate').val() != "" ?  moment($('#renewalDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') : null;
	    		   $scope.vCompliance.vendorComplianceId =0;
	    		   $scope.vCompliance.createdBy = $cookieStore.get('createdBy');
			       $scope.vCompliance.modifiedBy = $cookieStore.get('modifiedBy');
			       $scope.vCompliance.validated = false;
			       var file = $scope.myFile;
			      
			       if(event == 1){
			    	   $scope.vCompliance.validated = true;  
			    	   $scope.vCompliance.status = "Verified";
			       }else{
			    	   $scope.vCompliance.status = "Saved";
			       }
			       
			       if($scope.myFile == undefined ||(( $scope.myFile == null  || $scope.myFile == "") &&  ($scope.vCompliance.documentPath == undefined || $scope.vCompliance.documentPath == null ||  $scope.myFile == ""))){
			    	   formData.append('name',angular.toJson($scope.vCompliance));  
			    	   $http.post('vendorComplianceController/updateVendorCompliance.json', formData, {
			                 transformRequest: angular.identity,
			                 headers: {'Content-Type': undefined}
			             })
			             .success(function(response,data){
			            	 if(response.id > 0){
					            	$scope.Messager('success', 'success', 'Vendor Compliance Saved Successfully');
					            	$location.path('/VendorComplianceAdd/'+response.id);
			            	   }else if(response.id < 0){
			            		    $scope.Messager('error', 'Error', response.name);
			            		    $scope.vCompliance.transactionDate =  $filter('date')( $('#transactionDate').val(), 'dd/MM/yyyy');
				                	$scope.vCompliance.issueDate =  $filter('date')( $('#issueDate').val(), 'dd/MM/yyyy');
				                	$scope.vCompliance.expiryDate =  $filter('date')( $('#expiryDate').val(), 'dd/MM/yyyy');
				 	    		   $scope.vCompliance.renewalDate = $('#renewalDate').val() != undefined && $('#renewalDate').val() != null && $('#renewalDate').val() != "" ?  $filter('date')( $('#expiryDate').val(), 'dd/MM/yyyy') : "";
				                	
				               }else{
				            	    $scope.Messager('error', 'Error', "Technical issues occurred. Please try again after some time.");
				            	    $scope.vCompliance.transactionDate =  $filter('date')( $('#transactionDate').val(), 'dd/MM/yyyy');
				                	$scope.vCompliance.issueDate =  $filter('date')( $('#issueDate').val(), 'dd/MM/yyyy');
				                	$scope.vCompliance.expiryDate =  $filter('date')( $('#expiryDate').val(), 'dd/MM/yyyy');
				 	    		    $scope.vCompliance.renewalDate = $('#renewalDate').val() != undefined && $('#renewalDate').val() != null && $('#renewalDate').val() != "" ? $filter('date')( $('#expiryDate').val(), 'dd/MM/yyyy') : "";

				               }
			             })
			             .error(function(data,status,headers,config){
			            	 $scope.Messager('error', 'Error', 'Technical Issue while saving. Please try again after some time');
			            	 $scope.vCompliance.transactionDate =  $filter('date')( $('#transactionDate').val(), 'dd/MM/yyyy');
		                	 $scope.vCompliance.issueDate =  $filter('date')( $('#issueDate').val(), 'dd/MM/yyyy');
		                	 $scope.vCompliance.expiryDate =  $filter('date')( $('#expiryDate').val(), 'dd/MM/yyyy');
		  	    		     $scope.vCompliance.renewalDate = $('#renewalDate').val() != undefined && $('#renewalDate').val() != null && $('#renewalDate').val() != "" ?  $filter('date')( $('#expiryDate').val(), 'dd/MM/yyyy') : "";


			             });
			       }else{
			        
				       formData.append('file',file);
				       formData.append('name',angular.toJson($scope.vCompliance)); 
				       
				    	 //console.log("1: "+$scope.myFile);
				    	 
				    	 $http.post('vendorComplianceController/saveVendorCompliance.json', formData, {
			                 transformRequest: angular.identity,
			                 headers: {'Content-Type': undefined}
			             })
			             .success(function(response,data){
			            	 //$scope.Messager('success', 'success', 'File Upload Success',angular.element($event.currentTarget));
			            	 if(response.id > 0){
					            	$scope.Messager('success', 'success', 'Vendor Compliance Saved Successfully');
					            	$location.path('/VendorComplianceAdd/'+response.id);
			            	   }else if(response.id < 0){
			            		    $scope.Messager('error', 'Error', response.name);
			            		    $scope.vCompliance.transactionDate =  $filter('date')( $('#transactionDate').val(), 'dd/MM/yyyy');
				                	$scope.vCompliance.issueDate =  $filter('date')( $('#issueDate').val(), 'dd/MM/yyyy');
				                	$scope.vCompliance.expiryDate =  $filter('date')( $('#expiryDate').val(), 'dd/MM/yyyy');
				 	    		    $scope.vCompliance.renewalDate = $('#renewalDate').val() != undefined && $('#renewalDate').val() != null && $('#renewalDate').val() != "" ?  $filter('date')( $('#expiryDate').val(), 'dd/MM/yyyy') : $('#renewalDate').val("");
	
				               }else{
				            	    $scope.Messager('error', 'Error', "Technical issues occurred. Please try again after some time.");
				            	    $scope.vCompliance.transactionDate =  $filter('date')( $('#transactionDate').val(), 'dd/MM/yyyy');
				                	$scope.vCompliance.issueDate =  $filter('date')( $('#issueDate').val(), 'dd/MM/yyyy');
				                	$scope.vCompliance.expiryDate =  $filter('date')( $('#expiryDate').val(), 'dd/MM/yyyy');
				 	    		    $scope.vCompliance.renewalDate = $('#renewalDate').val() != undefined && $('#renewalDate').val() != null && $('#renewalDate').val() != "" ?  $filter('date')( $('#expiryDate').val(), 'dd/MM/yyyy') : $('#renewalDate').val("");
				               }
			             })
			             .error(function(data,status,headers,config){
			            	 //console.log(data);
			            	 $scope.Messager('error', 'Error', 'Technical Issue while saving. Please try again after some time');
			            	 $scope.vCompliance.transactionDate =  $filter('date')( $('#transactionDate').val(), 'dd/MM/yyyy');
		                	 $scope.vCompliance.issueDate =  $filter('date')( $('#issueDate').val(), 'dd/MM/yyyy');
		                	 $scope.vCompliance.expiryDate =  $filter('date')( $('#expiryDate').val(), 'dd/MM/yyyy');
		  	    		     $scope.vCompliance.renewalDate = $('#renewalDate').val() != undefined && $('#renewalDate').val() != null && $('#renewalDate').val() != "" ? $filter('date')( $('#expiryDate').val(), 'dd/MM/yyyy'): $('#renewalDate').val("");
	
			             });
			       }
    	   		}
       		}
       };
       
       var file  = {};
       $scope.upload = function(fileInput) {
           file = fileInput;
       };
       
     
       
       $scope.correctHistorySave= function(){
    	   
    	   if($('#vendorComplianceForm').valid() || !($('#vendorComplianceForm').valid()) && $scope.myFile != undefined ){
    		   if($('#vendorComplianceForm').valid()){
        		   if($scope.transDate1 != '' && $scope.transDate1 != undefined && new Date(moment($scope.transDate1,'DD/MM/YYYY').format('YYYY-MM-DD')) .getTime() > new Date(moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime()){
            		   $scope.Messager('error', 'Error', 'Transaction date should not be less than previous transaction date');
                	   $scope.vCompliance.transactionDate =  $scope.transDate1;
            	   }else if(new Date(moment($('#issueDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')) .getTime() >= new Date(moment($('#expiryDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime()){
            		   $scope.Messager('error', 'Error', 'Expiry date should not be less than issue date');
            	   }else if($('#renewalDate') != undefined && $('#renewalDate') != null && $('#renewalDate') != "" && new Date(moment($('#renewalDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')) .getTime() < new Date(moment($('#issueDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime()){
            		   $scope.Messager('error', 'Error', 'Renewal date should not be less than issue date');
            	   }else if($('#renewalDate') != undefined && $('#renewalDate') != null && $('#renewalDate') != "" && new Date(moment($('#renewalDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')) .getTime() > new Date(moment($('#expiryDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime()){
            		   $scope.Messager('error', 'Error', 'Renewal date should not be greater than the expiry date');
            	   }else{
		    		   $scope.vCompliance.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
		    		   $scope.vCompliance.issueDate = moment($('#issueDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
		    		   $scope.vCompliance.expiryDate = moment($('#expiryDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
		    		   $scope.vCompliance.renewalDate = $('#renewalDate').val() != undefined && $('#renewalDate').val() != null ?  moment($('#renewalDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') : null;
				       $scope.vCompliance.modifiedBy = $cookieStore.get('modifiedBy');
				       $scope.vCompliance.vendorSequenceId = 0;
				       $scope.vCompliance.validated = false;
				       var file = $scope.myFile;
				       //console.log(file.value);
				       var formData = new FormData();
				       
				       
				       if($scope.myFile == undefined){
				    	   formData.append('name',angular.toJson($scope.vCompliance));
				    	   $http.post('vendorComplianceController/updateVendorCompliance.json', formData, {
				                 transformRequest: angular.identity,
				                 headers: {'Content-Type': undefined}
				             })
				             .success(function(response,data){
				            	 //$scope.Messager('success', 'success', 'File Upload Success',angular.element($event.currentTarget));
				            	 if(response.id > 0){
						            	$scope.Messager('success', 'success', 'Vendor Compliance Saved Successfully');
						            	//$location.path('/VendorComplianceAdd/'+response.id);
						            	 $scope.Edit(response.id);
						            	
						            	 $scope.vCompliance.transactionDate =  $filter('date')( $('#transactionDate').val(), 'dd/MM/yyyy');
						                	$scope.vCompliance.issueDate =  $filter('date')( $('#issueDate').val(), 'dd/MM/yyyy');
						                	$scope.vCompliance.expiryDate =  $filter('date')( $('#expiryDate').val(), 'dd/MM/yyyy');
						 	    		    $scope.vCompliance.renewalDate = $('#renewalDate').val() != undefined && $('#renewalDate').val() != null && $('#renewalDate').val() != "" ?  $filter('date')( $('#renewalDate').val(), 'dd/MM/yyyy') : $('#renewalDate').val("");

				            	   }else if(response.id < 0){
				            		    $scope.Messager('error', 'Error', response.name);
				            		    $scope.vCompliance.transactionDate =  $filter('date')( $('#transactionDate').val(), 'dd/MM/yyyy');
					                	$scope.vCompliance.issueDate =  $filter('date')( $('#issueDate').val(), 'dd/MM/yyyy');
					                	$scope.vCompliance.expiryDate =  $filter('date')( $('#expiryDate').val(), 'dd/MM/yyyy');
					 	    		    $scope.vCompliance.renewalDate = $('#renewalDate').val() != undefined && $('#renewalDate').val() != null && $('#renewalDate').val() != "" ?  $filter('date')( $('#expiryDate').val(), 'dd/MM/yyyy') : $('#renewalDate').val("");

					               }else{
					            	    $scope.Messager('error', 'Error', "Technical issues occurred. Please try again after some time.");
					            	    $scope.vCompliance.transactionDate =  $filter('date')( $('#transactionDate').val(), 'dd/MM/yyyy');
					                	$scope.vCompliance.issueDate =  $filter('date')( $('#issueDate').val(), 'dd/MM/yyyy');
					                	$scope.vCompliance.expiryDate =  $filter('date')( $('#expiryDate').val(), 'dd/MM/yyyy');
					 	    		    $scope.vCompliance.renewalDate = $('#renewalDate').val() != undefined && $('#renewalDate').val() != null && $('#renewalDate').val() != "" ?  $filter('date')( $('#expiryDate').val(), 'dd/MM/yyyy') : $('#renewalDate').val("");

					            	   
					               }
				             })
				             .error(function(data,status,headers,config){
				            	 $scope.Messager('error', 'Error', 'Technical Issue while saving. Please try again after some time');
				            	 $scope.vCompliance.transactionDate =  $filter('date')( $('#transactionDate').val(), 'dd/MM/yyyy');
			                	 $scope.vCompliance.issueDate =  $filter('date')( $('#issueDate').val(), 'dd/MM/yyyy');
			                	 $scope.vCompliance.expiryDate =  $filter('date')( $('#expiryDate').val(), 'dd/MM/yyyy');
				 	    		 $scope.vCompliance.renewalDate = $('#renewalDate').val() != undefined && $('#renewalDate').val() != null && $('#renewalDate').val() != "" ?  $filter('date')( $('#expiryDate').val(), 'dd/MM/yyyy') : $('#renewalDate').val("");

				             });
				       }else{
					       formData.append('file',file);
					       formData.append('name',angular.toJson($scope.vCompliance));
					    	 //console.log($scope.myFile);
					    	 $http.post('vendorComplianceController/saveVendorCompliance.json', formData, {
				                 transformRequest: angular.identity,
				                 headers: {'Content-Type': undefined}
				             })
				             .success(function(response,data){
				            	 if(response.id > 0){
						            	$scope.Messager('success', 'success', 'Vendor Compliance Saved Successfully');
						            	$location.path('/VendorComplianceAdd/'+response.id);
				            	   }else if(response.id < 0){
				            		    $scope.Messager('error', 'Error', response.name);
				            		    $scope.vCompliance.transactionDate =  $filter('date')( $('#transactionDate').val(), 'dd/MM/yyyy');
					                	$scope.vCompliance.issueDate =  $filter('date')( $('#issueDate').val(), 'dd/MM/yyyy');
					                	$scope.vCompliance.expiryDate =  $filter('date')( $('#expiryDate').val(), 'dd/MM/yyyy');
					 	    		    $scope.vCompliance.renewalDate = $('#renewalDate').val() != undefined && $('#renewalDate').val() != null && $('#renewalDate').val() != "" ? $filter('date')( $('#expiryDate').val(), 'dd/MM/yyyy') : $('#renewalDate').val("");
	
					               }else{
					            	    $scope.Messager('error', 'Error', 'File Upload Failed');
					            	    $scope.vCompliance.transactionDate =  $filter('date')( $('#transactionDate').val(), 'dd/MM/yyyy');
					                	$scope.vCompliance.issueDate =  $filter('date')( $('#issueDate').val(), 'dd/MM/yyyy');
					                	$scope.vCompliance.expiryDate =  $filter('date')( $('#expiryDate').val(), 'dd/MM/yyyy');
					 	    		    $scope.vCompliance.renewalDate = $('#renewalDate').val() != undefined && $('#renewalDate').val() != null && $('#renewalDate').val() != "" ? $filter('date')( $('#expiryDate').val(), 'dd/MM/yyyy') : $('#renewalDate').val("");
	
					            	   
					               }
				             })
				             .error(function(data,status,headers,config){
				            	 $scope.Messager('error', 'Error', 'Technical Issue while saving. Please try again after some time');
				            	 $scope.vCompliance.transactionDate =  $filter('date')( $('#transactionDate').val(), 'dd/MM/yyyy');
			                	 $scope.vCompliance.issueDate =  $filter('date')( $('#issueDate').val(), 'dd/MM/yyyy');
			                	 $scope.vCompliance.expiryDate =  $filter('date')( $('#expiryDate').val(), 'dd/MM/yyyy');
			                	 $scope.vCompliance.renewalDate =  $filter('date')( $('#renewalDate').val(), 'dd/MM/yyyy');
	
				             }); 
				       }
				   }
    		   }
    	   }
	   };
       
       $scope.transactionDatesListChange = function(){
	       $scope.getData('vendorComplianceController/getVendorComplianceById.json', { vendorComplianceId : $scope.transactionModel, customerId: "" }, 'vendorComplianceList')
	       $('.dropdown-toggle').removeClass('disabled');
	   };
       
      /*$scope.fun_checkErr = function() {
    	  if(($('#issueDate').val() != undefined || $('#issueDate').val() != null) && ($('#expiryDate').val() != undefined || $('#expiryDate').val() != null) || ($scope.vCompliance.expiryDate != undefined)){
    		  var issue = new Date(moment($('#issueDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') );
		       var expire = new Date(moment($('#expiryDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') );
		       
		   	    if(issue.getTime() >= expire.getTime()){
		   	    	$scope.Messager('error', 'Error', 'Expiry Date should not be less than Issue Date');
		    	    return false;
		  	    } else{
		  	    	$scope.vCompliance.renewalDate=new Object();
		  	    	$scope.vCompliance.renewalDate  = $filter('date')(expire.getTime()-30*(24*60*60*1000),'dd/MM/yyyy');
		  	    }
	   	    	
    	  }
   	    
      };
      
      $scope.fun_findExpiryDate = function() {
          var issue = new Date(moment($('#issueDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') );
          $scope.fun_checkErr ();
      };
       */
      
      $scope.plusIconAction = function(){
    	  //$scope.vCompliance.amountType = $scope.amountType;
    	  $scope.showPanel = true;  
    	  $scope.disableDocument= false;
    	  $scope.readOnly = false;
      	  $scope.onlyRead = true;
      	  $scope.updateBtn = false;
      	  $scope.viewOrUpdateBtn = false;
      	  $scope.correcttHistoryBtn = false;
      	  $scope.gridButtons = false;
      	  $scope.cancelBtn = true;
      	  $scope.saveBtn = true;
      	 // alert($scope.vCompliance.amountType);
      	  if(roleName != 'Vendor Admin'){
		      $scope.verifyButton = true;
      	  }else{
		      $scope.verifyButton = false;
      	  }
      	  
      	  $('#transactionDate').val($filter('date')(new Date(),'dd/MM/yyyy'));
      	  $scope.vCompliance.reason = null;
      	  $('#issueDate').val("");
      	  $('#expiryDate').val("");
      	  $('#renewalDate').val("");
      	  //$scope.vCompliance.issueDate = "";
    	 // $scope.vCompliance.expiryDate = "";
    	  //$scope.vCompliance.renewalDate = "";
      	  
      	  $scope.vCompliance.registeredState = "";
      	  $scope.vCompliance.status = "Saved";
      	  $scope.vCompliance.licensePolicyNumber= null;
      	  $scope.vCompliance.defineComplianceTypeId = "";
      	  $scope.vCompliance.remarks ="";
      	  $scope.vCompliance.documentPath ="";
      	  $scope.disableDiv = true;
      	  $scope.vCompliance.numberOfWorkersCovered =  null;
      	  $scope.vCompliance.transactionAmount = null;
      	  $scope.vCompliance.amountType = $scope.amountType;
      	  $scope.vCompliance.vendorComplianceUniqueId = 0;
      	  $scope.myFile = "";
      	
      	  
  	 
      };
      
      $scope.Edit = function($event){
    	  //$('#transactionDate').val($filter('date')(new Date(),'dd/MM/yyyy'));
    	  $scope.disableDocument= true;
    	  $scope.checkcode = false;
    	  $scope.disableDiv = true;
    	  $scope.updateBtn = true;
          $scope.viewOrUpdateBtn = true;
          $scope.correcttHistoryBtn = false;
          $scope.gridButtons = false;
          $scope.cancelBtn = true;
          $scope.saveBtn = false;
          $scope.transactionList = false;
          $scope.verifyButton = false;
          $scope.getData('vendorComplianceController/getVendorComplianceById.json', { vendorComplianceId: $event != undefined ? $event : 0,customerId : $cookieStore.get('customerId')}, 'vendorList')

      };
      
      $scope.fun_download = function(){
    	  //console.log($scope.vCompliance.path);
    	  $http({
			    url: ROOTURL +'vendorComplianceController/downloadfile.view',
			    method: 'POST',
			    data : { 'path': $scope.vCompliance.path } ,
			    responseType: 'arraybuffer',
			    //this is your json data string
			    headers: {
			        'Content-type': 'application/json',
			        'Accept': '*'
			    }
			}).success(function(data){
			   //console.log("success");
			   var blob = new Blob([data], {
			        type: 'application/pdf'
			    });
			  //console.log(blob);
			    saveAs(blob,$scope.vCompliance.documentPath);
			    $("#loader").hide();
              $("#data_container").show();
              
			}).error(function(){	
				console.log("Failed to download..");
				
			});
      };
     
}]);





