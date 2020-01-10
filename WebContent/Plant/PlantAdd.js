'use strict';

//var PlantAddcontroller = angular.module("myApp.PlantAdd", []);

PlantController.controller("PlantAddCtrl", ['$scope', '$http', '$resource','$location','$filter', '$routeParams','myservice','$cookieStore', function ($scope, $http, $resource, $location, $filter, $routeParams, myservice, $cookieStore) {
			$.material.init();
		   /* $('#transactionDate').bootstrapMaterialDatePicker({
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
			$scope.plant = new Object();
			
	       if ($routeParams.id > 0) {
	           $scope.readOnly = true;
	           $scope.datesReadOnly = true;
	           $scope.updateBtn = true;
	           $scope.saveBtn = false;
	           $scope.viewOrUpdateBtn = true;
	           $scope.correcttHistoryBtn = false;
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
	           $scope.transactionList = false;
	       }
	       $scope.updateBtnAction = function (this_obj) {
	    	   $scope.pCode = 1;
	           $scope.readOnly = false;
	           $scope.onlyread = true;
	           $scope.datesReadOnly = false;
	           $scope.updateBtn = false;
	           $scope.saveBtn = true;
	           $scope.viewOrUpdateBtn = false;
	           $scope.correctHistoryBtn = false;
	           $scope.resetBtn = false;
	           $scope.cancelBtn = true;
	           $scope.transactionList = false;
	           $scope.plant.plantDetailsId = 0;
	           $('#transactionDate').val($filter('date')(new Date(),'dd/MM/yyyy'));
	           $('.dropdown-toggle').removeClass('disabled');
	       }
	       
	       $scope.labelName1 = "Add";
	       
	       $scope.viewOrEditHistory = function () {
	           $scope.readOnly = false;
	           $scope.onlyread = true;
	           $scope.datesReadOnly = false;
	           $scope.updateBtn = false;
	           $scope.saveBtn = false;
	           $scope.viewOrUpdateBtn = false;
	           $scope.correcttHistoryBtn = true;
	           $scope.resetBtn = false;      
	           $scope.transactionList = true;
	            $scope.getData('PlantController/getPlantTransactionDatesList.json', { companyId: $scope.plant.companyId, customerId: $scope.plant.customerId ,plantId: $scope.plant.plantId }, 'transactionDatesChange');       
	           
	           $('.dropdown-toggle').removeClass('disabled');
	       }
	       
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
		        $scope.returnToSearchBtn = true;
		        $scope.getData('PlantController/getPlantById.json', { plantDetailsId: $routeParams.id,customerId : ''}, 'plantList')
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
	        		} else if (type == 'plantList') {
	   				   //alert(JSON.stringify(response.data));
	                   $scope.plantList = response.data;
	                   $scope.plant = response.data.plantVo[0];
	                   if( $scope.plant != undefined){
	                	   $('#transactionDate').val($filter('date')( response.data.plantVo[0].transactionDate, 'dd/MM/yyyy'));
	                	   $scope.plant.transactionDate =  $filter('date')( response.data.plantVo[0].transactionDate, 'dd/MM/yyyy');
		                   $scope.transDate1 =  $filter('date')( response.data.plantVo[0].transactionDate, 'dd/MM/yyyy'); 
	                	   $scope.plantList.companyList = response.data.companyList;
	                	   $scope.plantList.locationList = response.data.locationList;
	                	   $scope.plantList.statesList = response.data.statesList;
	                	   $scope.labelName1 = "View/Edit";
		                 
	                   }else{
	                	   $scope.plant = new Object();
	                	   $scope.plant.transactionDate=  $filter('date')( new Date(), 'dd/MM/yyyy');
	                	   $scope.plant.status='Y';
	                	   
	                	   //  alert(JSON.stringify(response.data.customerList[0]));
	                	   if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){
	                		//   alert(JSON.stringify(response.data.customerList[0].customerId));
	   		                $scope.plant.customerId=response.data.customerList[0].customerId;		                
	   		                $scope.customerChange();
	   		                
	   		                }
	                   }
	                // for button views
	   			  	if($scope.buttonArray == undefined || $scope.buttonArray == '')
	                   $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Plant'}, 'buttonsAction');  
	               }else if (type == 'customerChange') {
	   					//alert(JSON.stringify(response.data));
	                   $scope.plantList.companyList = response.data;
	                   if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	   	                	$scope.plant.companyId = response.data[0].id;
	   	                	$scope.companyChange();
	   	                }
	                   $scope.fun_checkPlantCode();
	                   
	               }else if (type == 'companyChange') {
					   //alert(JSON.stringify(response.data));
	                   $scope.plantList.countryList = response.data;
	                   if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	   	                	$scope.plant.countryId = response.data[0].id;
	   	                	$scope.countryChange();
	   	                }
	                   $scope.fun_checkPlantCode();
	               }else if (type == 'countryChange') {
					   //alert(JSON.stringify(response.data));
	                   $scope.plantList.locationList = response.data;
	                   if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	   	                	$scope.plant.locationId = response.data[0].locationId;
	   	                	$scope.locationChange();
	   	                }
	                   $scope.fun_checkPlantCode();
	               }else if (type == 'locationChange') {
					   //alert(JSON.stringify(response.data));
	                   $scope.plantList.statesList = response.data;
	                   $scope.fun_checkPlantCode();
	               } else if(type == 'validateCode'){
	            	  
	            	   	$scope.pCode = new Object();
	            	   	$scope.pCode = response.data;
	            	
	            } else if (type == 'savePlant') {
	                   //alert(angular.toJson(response.data));
	            	   if(response.data.id > 0){
			            	$scope.Messager('success', 'success', 'Plant Saved Successfully',buttonDisable);
			            	$location.path('/PlantAdd/'+response.data.id);
	            	   }else if(response.data.id == -1){
	            		   $scope.Messager('error', 'Error', 'Transaction Date should be greater than or equal to company transaction date',buttonDisable);
		               }else{
		            	   $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
		               }
	               }else if (type == 'transactionDatesChange') {
		            	//alert(JSON.stringify(response.data));

		            	var k = response.data.length;
		            	if(response.data.length > 1){
			            	for(var i = response.data.length-1; i> 0;i--){
				            	 if($scope.dateDiffer(response.data[i-1].name.split('-')[0])){
				            		 k = response.data[i-1].id;
				            		 break;
				            	 }
			            	}
		            	}else{
		            		k = response.data[0].id;
		            	}
		            	$scope.transactionDatesList = response.data;
		            	$scope.transactionModel=k;
				        $scope.getData('PlantController/getPlantById.json', { plantDetailsId: $scope.transactionModel,customerId : ''}, 'plantList')

		            }
	           },
	           function () { 
	        	   $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
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
		    
	       $scope.getData('PlantController/getPlantById.json', { plantDetailsId: $routeParams.id, customerId:  $cookieStore.get('customerId') }, 'plantList')
	       	
	       $scope.customerChange = function () {
	    	   if($scope.plant.customerId != undefined || $scope.plant.customerId != null){
	    		   $scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.plant.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.plant.companyId  }, 'customerChange');
	    	   }
	       };
	       
	       $scope.companyChange = function(){
	    	   if($scope.plant.companyId != undefined || $scope.plant.companyId != null){
			    	$scope.getData('vendorController/getcountryListByCompanyId.json', { companyId: $scope.plant.companyId }, 'companyChange');
	       		}
	       };
	       $scope.locationChange = function () {
	       		//alert($scope.plant.companyId);
			    $scope.getData('CommonController/statesListByCountryId.json', { countryId: $scope.plant.countryId }, 'locationChange');
		   };
			
		   $scope.countryChange = function () {
			   
				$scope.getData('LocationController/getLocationsListBySearch.json',{customerId: ($scope.plant.customerId == undefined || $scope.plant.customerId == null )? '0' : $scope.plant.customerId, companyId : ($scope.plant.companyId == undefined || $scope.plant.companyId == null) ? '0' : $scope.plant.companyId, countryId : ($scope.plant.countryId == undefined || $scope.plant.countryId == null) ? '0' : $scope.plant.countryId, status : 'Y'} , 'countryChange');
		   };
		   
		   $scope.fun_checkPlantCode = function(){
		    	if($scope.plant.customerId != null || $scope.plant.customerId != undefined || $scope.plant.customerId != ''
		    			&& ($scope.plant.companyId != null || $scope.plant.companyId != undefined || $scope.plant.companyId != '')
		    				    			&& ($scope.plant.plantCode != null || $scope.plant.plantCode != undefined || $scope.plant.plantCode != '')
		    								&& ($scope.plant.locationId != null || $scope.plant.locationId != undefined || $scope.plant.locationId != '')){

		    		$scope.getData('PlantController/validatePlantCode.json',{ customerId : $scope.plant.customerId, companyId: $scope.plant.companyId, plantCode:$scope.plant.plantCode, locationId :$scope.plant.locationId },'validateCode')
		    	}
		    };
		   
	       $scope.save = function ($event) {
	    	    if($("#plantForm").valid()){
	            	if($scope.plant.address1 == undefined ||  $scope.plant.address1 == '' || $scope.plant.countryId == undefined ||  $scope.plant.countryId == ''  ){
	            		 $scope.Messager('error', 'Error', 'Please enter Address',angular.element($event.currentTarget));
	            	}else if($scope.pCode == 0){
			    		$scope.Messager('error', 'Error', 'Plant code already exists'); 
			    	}else if($scope.transDate1 != '' && $scope.transDate1 != undefined && new Date(moment($scope.transDate1,'DD/MM/YYYY').format('YYYY-MM-DD')) .getTime() > new Date(moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime()){
			    		   $scope.Messager('error', 'Error', 'Transaction Date Should not be less than previous transaction date');
			        	   $scope.plant.transactionDate =  $scope.transDate1;
			    	}else{
			    		
	            		$scope.plant.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
	            		$scope.plant.createdBy = $cookieStore.get('createdBy');
	            		$scope.plant.modifiedBy = $cookieStore.get('modifiedBy');
	            		$scope.getData('PlantController/savePlant.json', angular.toJson($scope.plant), 'savePlant');
	    		  }
	           }
	       };
	       
	       $scope.correctHistorySave= function($event){
	    	   if($('#plantForm').valid()){
	    		   $scope.plant.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
				   $scope.plant.modifiedBy = $cookieStore.get('modifiedBy');
	    		 //  alert(angular.toJson($scope.location));
				   $scope.getData('PlantController/savePlant.json', angular.toJson($scope.plant), 'savePlant');
	    	   }
		   }

	       $scope.saveChanges = function(){
	    	   if(($scope.plant.address1 == '' || $scope.plant.address1 == undefined || $scope.plant.address1 == null)
	     			  || ($scope.plant.countryId == '' && $scope.plant.countryId == undefined && $scope.plant.countryId == null)){
	     		   $scope.Messager('error', 'Error', 'Please enter required fields');
	     	   }else{
	     		  $scope.labelName1 = "View/Edit";
	     		   $('div[id^="myModal"]').modal('hide');
	     	   }
	       };
	       
	       $scope.transactionDatesListChange = function(){
		       //alert("--" +$scope.transactionModel);
		       $scope.getData('PlantController/getPlantById.json', { plantDetailsId : ($scope.transactionModel != undefined || $scope.transactionModel != null) ? $scope.transactionModel:'0', customerId: "" }, 'plantList')
		       $('.dropdown-toggle').removeClass('disabled');
		   }
	
		    $('#addressAdd').click(function () {
		        $('#myModal').modal({
		            backdrop: 'static'
		        });
		    });
   // $.material.init();
}]);


