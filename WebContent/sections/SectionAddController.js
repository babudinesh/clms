'use strict';


sectionController.controller("sectionAddCtrl", ['$scope', '$http', '$resource','$location','$filter', '$routeParams','myservice','$cookieStore', function ($scope, $http, $resource, $location, $filter, $routeParams, myservice, $cookieStore) {
	$.material.init();
	/*$('#transactionDate').bootstrapMaterialDatePicker({
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
		   $scope.readOnlyEdit = true;
	       $scope.readOnly = true;
	       $scope.datesReadOnly = true;
	       $scope.updateBtn = true;
	       $scope.saveBtn = false;
	       $scope.viewOrUpdateBtn = true;
	       $scope.correcttHistoryBtn = false;
	       $scope.resetBtn = false;
	       $scope.gridButtons = false;
	       $scope.transactionList = false;
	   } else {		  
		   $scope.readOnlyEdit = false;
	       $scope.readOnly = false;
	       $scope.datesReadOnly = false;
	       $scope.updateBtn = false;
	       $scope.saveBtn = true;
	       $scope.viewOrUpdateBtn = false;
	       $scope.correcttHistoryBtn = false;
	       $scope.resetBtn = true;
	       $scope.transactionList = false;
	       $scope.gridButtons = true;	 
	       $scope.work = new Object();
	       $scope.transactionDate =  $filter('date')( new Date(), 'dd/MM/yyyy');
	       $scope.work.status ="Y"; 
	   }
	   
	   $scope.updateBtnAction = function (this_obj) {
		   $scope.readOnlyEdit = true;
	       $scope.readOnly = false;
	       $scope.datesReadOnly = false;
	       $scope.updateBtn = false;
	       $scope.saveBtn = true;
	       $scope.viewOrUpdateBtn = false;
	       $scope.correctHistoryBtn = false;
	       $scope.resetBtn = false;
	       $scope.cancelBtn = true;
	       $scope.transactionList = false;
	       $scope.gridButtons = true;	      
	       $('#transactionDate').val($filter('date')(new Date(),'dd/MM/yyyy'));
	       $('.dropdown-toggle').removeClass('disabled');
	   }
	   
	   $scope.viewOrEditHistory = function () {
		   $scope.readOnlyEdit = false;
	       $scope.readOnly = false;
	       $scope.datesReadOnly = false;
	       $scope.updateBtn = false;
	       $scope.saveBtn = false;
	       $scope.viewOrUpdateBtn = false;
	       $scope.correcttHistoryBtn = true;
	       $scope.resetBtn = false;      
	       $scope.gridButtons = true;
	       $scope.transactionList = true;
	        $scope.getData('sectionController/getTransactionDatesListOfHistory.json', { companyId: $scope.work.companyDetailsId, customerId: $scope.work.customerDetailsId ,sectionId: $scope.work.sectionDetailsId }, 'transactionDatesChange');       
	       
	       $('.dropdown-toggle').removeClass('disabled');
	   }
	   
	   $scope.cancelBtnAction = function(){
		   $scope.readOnlyEdit = true;
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
	        $scope.getData('sectionController/getSectionRecordById.json', { sectionId: $routeParams.id }, 'masterData')
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
	    		} else if (type == 'masterData') {				 
	               $scope.workList = response.data;
	               
	               if(Object.prototype.toString.call(response.data.detailsInfoVos) === '[object Array]' &&  response.data.detailsInfoVos.length > 0 ){
	            	   $scope.work = response.data.detailsInfoVos[0];
		               $scope.transactionModel= response.data.detailsInfoVos[0].sectionDetailsInfoId ;
		               $('#transactionDate').val($filter('date')( response.data.detailsInfoVos[0].transactionDate, 'dd/MM/yyyy'));
	            	   $scope.transactionDate =  $filter('date')( response.data.detailsInfoVos[0].transactionDate, 'dd/MM/yyyy');
	            	   $scope.workList.companyList = response.data.companyList;
	            	   $scope.workList.locationList = response.data.locationList;
	            	   $scope.workList.statesList = response.data.statesList;
	            	   $scope.workList.plantList = response.data.plantList;	
	            	   $scope.countryChange();
	            	   $scope.locationChange();
	               }else{
	            	   $scope.work = new Object();
	            	   $scope.transactionDate=  $filter('date')( new Date(), 'dd/MM/yyyy');
	            	   $scope.work.status='Y';
	            	   if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){
	                		//   alert(JSON.stringify(response.data.customerList[0].customerId));
	   		                $scope.work.customerDetailsId=response.data.customerList[0].customerId;		                
	   		                $scope.customerChange();
	   		           }
	               }	
	            // for button views
				  	if($scope.buttonArray == undefined || $scope.buttonArray == '')
	               $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Section'}, 'buttonsAction');
		        }else if (type == 'customerChange') {					
		               $scope.workList.companyList = response.data;	
	                   if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	   	                	$scope.work.companyDetailsId = response.data[0].id;	 
	   	                	$scope.companyChange();
	   	               }
		        }else if (type == 'companyChange') {				 
		        	   $scope.workList.countryList = response.data;
		        	   if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
		   	                $scope.work.country = response.data[0].id;	 
		   	                $scope.countryChange();
		   	           }
		        }else if(type == 'countryChange'){		        	   
		        	   $scope.workList.locationList = response.data;
		        	   if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
		   	                $scope.work.locationDetailsId = response.data[0].locationId;	 
		   	                $scope.locationChange();
		   	           }
		        } else if(type == "locationChange"){	        	 
		        		$scope.workList.plantList = response.data.plantList;
		        		if( response.data.plantList[0] != undefined && response.data.plantList[0] != "" && response.data.plantList.length == 1 ){
		        			$scope.work.plantDetailsId = response.data.plantList[0].plantId;
		        			$scope.plantChange();
			   	        }
		        } else if (type == 'save') {
	                		
	                	if(response.data == -1){
	                		 $scope.Messager('error', 'Error', 'Section Code Already Exists...',buttonDisable);
	                		 return;
	                	}else{
	                		$scope.Messager('success', 'success', 'Section Saved Successfully',buttonDisable);
	                		$location.path('/sectionDetails/'+response.data);	
	                	}
		        }else if (type == 'transactionDatesChange') {		        	  
		               $scope.transactionDatesList = response.data;
		        }else if(type == 'correctHistory'){
		        	   $scope.Messager('success', 'success', 'Section Updated Successfully',buttonDisable);
		        }else if (type == 'plantChange') {            	
		         	   $scope.departmentList = response.data.departmentList;	            	
		        }
	       },
	       function () {
	    	   $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable); 
	       });
	   	}
	   
	   
	   	$scope.getData('sectionController/getSectionRecordById.json', { sectionId : $routeParams.id,customerId:$cookieStore.get('customerId') }, 'masterData')
	   	
	   	$scope.customerChange = function () {			
	        $scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.work.customerDetailsId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : ($scope.work.companyDetailsId == undefined || $scope.work.companyDetailsId == null) ? '0' : $scope.work.companyDetailsId }, 'customerChange');
	   	};
	   	
	  
		$scope.companyChange = function () {	   		   		
	   		$scope.getData('vendorController/getcountryListByCompanyId.json',{companyId : $cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "" ? $cookieStore.get('companyId') : ($scope.work.companyDetailsId == undefined || $scope.work.companyDetailsId == null) ? '0' : $scope.work.companyDetailsId } , 'companyChange');
	   		
		};
		
		$scope.countryChange = function(){
			if($scope.work.country != undefined || $scope.work.country != null){
				$scope.getData('LocationController/getLocationsListBySearch.json',{customerId: ($scope.work.customerDetailsId == undefined || $scope.work.customerDetailsId == null )? '0' : $scope.work.customerDetailsId, companyId : ($scope.work.companyDetailsId == undefined || $scope.work.companyDetailsId == null) ? '0' : $scope.work.companyDetailsId, countryId : ($scope.work.country == undefined || $scope.work.country == null) ? '0' : $scope.work.country } , 'countryChange');
			}else{
				$scope.workList.locationList = [];
			}
			
		}
		
		$scope.locationChange= function(){			
			$scope.getData('PlantController/getPlantListBySearch.json', { customerId : ($scope.work.customerDetailsId == undefined ||$scope.work.customerDetailsId == null )? '0' : $scope.work.customerDetailsId  , companyId : ($scope.work.companyDetailsId == undefined || $scope.work.companyDetailsId == null) ? '0' : $scope.work.companyDetailsId  , locationId: $scope.work.locationDetailsId == undefined ? '' : $scope.work.locationDetailsId}, 'locationChange');
	
		}
		
		$scope.plantChange = function(){
	       	if($scope.work.locationDetailsId != undefined && $scope.work.locationDetailsId != null && $scope.work.locationDetailsId != '' && $scope.work.plantDetailsId != undefined && $scope.work.plantDetailsId != null && $scope.work.plantDetailsId != ''){
	       		$scope.getData('associatingDepartmentToLocationPlantController/getDepartMentDetailsByLocationAndPlantId.json', {customerId:$scope.work.customerDetailsId,companyId:$scope.work.companyDetailsId,locationId:$scope.work.locationDetailsId,plantId:$scope.work.plantDetailsId}, 'plantChange');
	       	}
		}
	   
	   	$scope.save = function ($event) {
	   		if($('#workForm').valid()){ 
	   		    $scope.work.sectionDetailsInfoId = 0;
		   		$scope.work.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');          
	            $scope.work.createdBy = $cookieStore.get('createdBy');
	            $scope.work.modifiedBy = $cookieStore.get('modifiedBy');	          
	            $scope.getData('sectionController/saveSection.json', angular.toJson($scope.work), 'save',angular.element($event.currentTarget));
	   		}
	   };
	   
	   $scope.correctHistorySave= function($event){
		   if($('#workForm').valid()){ 
			   $scope.work.sectionDetailsInfoId = $scope.transactionModel;
	    	   $scope.work.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
	    	   $scope.work.createdBy = $cookieStore.get('createdBy');
	    	   $scope.work.modifiedBy = $cookieStore.get('modifiedBy');
	           $scope.getData('sectionController/saveSection.json', angular.toJson($scope.work), 'correctHistory');
		   }
	   };
	   
	   $scope.transactionDatesListChange = function(){	     
	        $scope.getData('sectionController/getSectionRecordById.json',  { sectionId : ($scope.transactionModel != undefined || $scope.transactionModel != null) ? $scope.transactionModel:'0' }, 'masterData')
	    }
	   

	  
	
	
}]);


