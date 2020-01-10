'use strict';


WorkAreaController.controller("WorkAreaAddCtrl", ['$scope', '$http', '$resource','$location','$filter', '$routeParams','myservice','$cookieStore', function ($scope, $http, $resource, $location, $filter, $routeParams, myservice, $cookieStore) {
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
	   
	   $scope.list_workSkill = [{"id":"Skilled","name" : "Skilled" },
			                       {"id":"Semi Skilled","name" : "Semi Skilled" },
			                       {"id":"High Skilled","name" : "High Skilled" },
			                       {"id":"Special Skilled","name" : "Special Skilled" },
			                       {"id":"UnSkilled","name" : "UnSkilled" }];
	   
	   if ($routeParams.id > 0) {
	       $scope.readOnly = true;
	       $scope.readOnlyEdit =true;
	       $scope.datesReadOnly = true;
	       $scope.updateBtn = true;
	       $scope.saveBtn = false;
	       $scope.viewOrUpdateBtn = true;
	       $scope.correcttHistoryBtn = false;
	       $scope.resetBtn = false;
	       $scope.gridButtons = false;
	       $scope.transactionList = false;
	   } else {
		   $('#transactionDate').val($filter('date')(new Date(),'dd/MM/yyyy'));
	       $scope.readOnly = false;
	       $scope.readOnlyEdit =false;
	       $scope.datesReadOnly = false;
	       $scope.updateBtn = false;
	       $scope.saveBtn = true;
	       $scope.viewOrUpdateBtn = false;
	       $scope.correcttHistoryBtn = false;
	       $scope.resetBtn = true;
	       $scope.transactionList = false;
	       $scope.gridButtons = true;
	       $scope.status="Y";
	   }
	   
	   $scope.updateBtnAction = function (this_obj) {
		   $scope.readOnlyEdit =true;
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
	       $scope.work.workAreaDetailsId = 0;
	       $('#transactionDate').val($filter('date')(new Date(),'dd/MM/yyyy'));
	       $('.dropdown-toggle').removeClass('disabled');
	   }
	   
	   $scope.viewOrEditHistory = function () {
		   $scope.readOnlyEdit =false;
	       $scope.readOnly = false;
	       $scope.datesReadOnly = false;
	       $scope.updateBtn = false;
	       $scope.saveBtn = false;
	       $scope.viewOrUpdateBtn = false;
	       $scope.correcttHistoryBtn = true;
	       $scope.resetBtn = false;      
	       $scope.gridButtons = true;
	       $scope.transactionList = true;
	       $scope.getData('WorkAreaController/getWorkAreaTransactionDatesList.json', { companyId: $scope.work.companyId, customerId: $scope.work.customerId ,workAreaId: $scope.work.workAreaId }, 'transactionDatesChange');       
	       
	       $('.dropdown-toggle').removeClass('disabled');
	   }
	   
	   $scope.cancelBtnAction = function(){
		   $scope.readOnlyEdit =true;
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
	        $scope.getData('WorkAreaController/getWorkAreaById.json', { workAreaDetailsId: $routeParams.id, customerId : ''}, 'workAreaList')
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
	    		} else if (type == 'workAreaList') {
				  // alert(JSON.stringify(response.data.workAreaVo[0]));
	               $scope.workList = response.data;
	               $scope.work = response.data.workAreaVo[0];
	               if($scope.work != undefined){
	            	   $scope.work.transactionDate =  $filter('date')( response.data.workAreaVo[0].transactionDate, 'dd/MM/yyyy');
	            	   $('#transactionDate').val($filter('date')( response.data.workAreaVo[0].transactionDate, 'dd/MM/yyyy'));
	            	   $scope.workList.companyList = response.data.companyList;
	            	   $scope.workList.locationList = response.data.locationList;
	            	   $scope.workList.statesList = response.data.statesList;
	            	   $scope.workList.plantList = response.data.plantList;
	            	   $scope.departmentList = response.data.departmentList;
	            	   $scope.sectionList = response.data.sectionList;
	            	   $scope.transactionModel = response.data.workAreaVo[0].workAreaDetailsId;
	               }else{
	            	   $scope.work = new Object();
	            	   $scope.work.transactionDate=  $filter('date')( new Date(), 'dd/MM/yyyy');
	            	   $scope.work.status='Y';
	            	   if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
	   		                $scope.work.customerId=response.data.customerList[0].customerId;		                
	   		                $scope.customerChange();
	   		                }
	               }
	               // for button views
	               if($scope.buttonArray == undefined || $scope.buttonArray == '')
	               $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Work Area'}, 'buttonsAction');
	           }else if (type == 'customerChange') {
					//alert(JSON.stringify(response.data));
	               $scope.workList.companyList = response.data;
	               
	               if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	   	                $scope.work.companyId = response.data[0].id;
	   	                $scope.companyChange();
	   	           }
	           }else if (type == 'companyChange') {
				   //alert(JSON.stringify(response.data));
	        	   $scope.workList.countryList = response.data;
	        	   if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	   	                $scope.work.countryId = response.data[0].id;
	   	                $scope.countryChange();
	   	           }
	           }else if(type == 'countryChange'){
	        	   $scope.workList.locationList = response.data;
	        	   if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	   	                $scope.work.locationId = response.data[0].locationId;
	   	                $scope.locationChange();
	   	           }
	           }else if(type == "locationChange"){
	        	   //alert(JSON.stringify(response.data));
	        		$scope.workList.plantList = response.data.plantList;
	        		if( response.data.plantList[0] != undefined && response.data.plantList[0] != "" && response.data.plantList.length == 1 ){
	   	                $scope.work.plantId = response.data.plantList[0].plantId;
	   	                $scope.plantChange();
	   	           	}
	           }else if (type == 'plantChange') {				
	        	   $scope.sectionList = response.data.sectionList;
	        	   if( response.data.sectionList[0] != undefined && response.data.sectionList[0] != "" && response.data.sectionList.length == 1 ){
	   	                $scope.work.sectionDetailsId = response.data.sectionList[0].sectionDetailsId;
	   	                
	   	           }
	           }else if (type == 'saveWorkArea') {
	        		if(response.data.id == 2){
	        			$scope.Messager('error', 'Error', ' Work Area Code Already Exists..', buttonDisable);
	        		}else if(response.data.id == 0){
	                	$scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
	                }else if(response.data.id == -1){
	                	$scope.Messager('error', 'Error', 'Invalid Transaction Date', buttonDisable);
	                }else{
	                	myservice.workAreaDetailsId = response.data.id;
		                if($scope.saveBtn == true){
		                	$scope.Messager('success', 'success', 'Work Area Saved Successfully',buttonDisable);
		                	$location.path('/WorkAreaAdd/'+response.data.id);
		                }else{
		                	$scope.Messager('success', 'success', 'Work Area Updated Successfully',buttonDisable);
		                }
	                }
	           }else if (type == 'transactionDatesChange') {
	        	   $scope.transactionDatesList = response.data;	        	 
	           }else if (type == 'departmentList') {            	
	         	   $scope.departmentList = response.data.departmentList;	            	
		    	  }
	       },
	       function () {
	    	   $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable); 
	       });
	   	}
	   	$scope.getData('WorkAreaController/getWorkAreaById.json', { workAreaDetailsId: $routeParams.id, customerId: $cookieStore.get('customerId') }, 'workAreaList')
	   	
	   	$scope.customerChange = function () {
			//alert($scope.location.customerId);
	        $scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.work.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : ($scope.work.companyId == undefined || $scope.work.companyId == null) ? '0' : $scope.work.companyId }, 'customerChange');
	   	};
	   	
	   	$scope.companyChange = function () {	   		   		
	   		$scope.getData('vendorController/getcountryListByCompanyId.json',{companyId : ($scope.work.companyId == undefined || $scope.work.companyId == null) ? '0' : $scope.work.companyId } , 'companyChange');
	   		
		};
		
		$scope.countryChange = function(){
			if($scope.work.countryId != undefined || $scope.work.countryId != null){
				$scope.getData('LocationController/getLocationsListBySearch.json',{customerId: ($scope.work.customerId == undefined || $scope.work.customerId == null )? '0' : $scope.work.customerId, companyId : ($scope.work.companyId == undefined || $scope.work.companyId == null) ? '0' : $scope.work.companyId, countryId : ($scope.work.countryId == undefined || $scope.work.countryId == null) ? '0' : $scope.work.countryId } , 'countryChange');
			}else{
				$scope.workList.countryList = [];
			}
			
		}
		
		$scope.locationChange= function(){
			//alert($scope.work.locationId);
			$scope.getData('PlantController/getPlantListBySearch.json', { customerId : ($scope.work.customerId == undefined ||$scope.work.customerId == null )? '0' : $scope.work.customerId  , companyId : ($scope.work.companyId == undefined || $scope.work.companyId == null) ? '0' : $scope.work.companyId  , locationId: $scope.work.locationId == undefined ? '' : $scope.work.locationId}, 'locationChange');
	
		}
		
		$scope.plantChange= function(){
			//$scope.getData('sectionController/getSectionListBySearch.json', { customerId : ($scope.work.customerId == undefined ||$scope.work.customerId == null )? '0' : $scope.work.customerId , companyId : ($scope.work.companyId == undefined || $scope.work.companyId == null) ? '0' : $scope.work.companyId  , locationId: $scope.work.locationId == undefined ? '' : $scope.work.locationId , plantDetailsId : $scope.work.plantId == undefined ? '' : $scope.work.plantId}, 'plantChange');
			
			if($scope.work.locationId != undefined && $scope.work.locationId != null && $scope.work.locationId != '' && $scope.work.plantId != undefined && $scope.work.plantId != null && $scope.work.plantId != ''){
	       		$scope.getData('associatingDepartmentToLocationPlantController/getDepartMentDetailsByLocationAndPlantId.json', {customerId:$scope.work.customerId,companyId:$scope.work.companyId,locationId:$scope.work.locationId,plantId:$scope.work.plantId}, 'departmentList');
	       	}
			
		};
		
		
		$scope.departmentChange= function(){		
			$scope.getData('sectionController/getSectionListBySearch.json', { customerId : ($scope.work.customerId == undefined ||$scope.work.customerId == null )? '0' : $scope.work.customerId , companyId : ($scope.work.companyId == undefined || $scope.work.companyId == null) ? '0' : $scope.work.companyId  , locationId: $scope.work.locationId == undefined ? '' : $scope.work.locationId , plantDetailsId : $scope.work.plantId == undefined ? '' : $scope.work.plantId,departmentId:$scope.work.departmentId}, 'plantChange');
		};
	   
	   	$scope.save = function ($event) {
	   		if($('#workForm').valid()){ 
		   		$scope.work.transactionDate = $('#transactionDate').val();
	            $scope.work.skillList = $scope.workList.skillList;
	            $scope.work.createdBy = $cookieStore.get('createdBy');
	            $scope.work.modifiedBy = $cookieStore.get('modifiedBy');
	           // alert( $scope.work.createdBy);
	            //alert(angular.toJson($scope.work));
	            $scope.getData('WorkAreaController/saveWorkArea.json', angular.toJson($scope.work), 'saveWorkArea',angular.element($event.currentTarget));
	   		}
	   };
	   
	   $scope.correctHistorySave= function($event){
		   if($('#workForm').valid()){ 
			   $scope.work.workAreaDetailsId = $scope.transactionModel;
	    	   $scope.work.transactionDate = $('#transactionDate').val();
	    	   $scope.work.modifiedBy = $cookieStore.get('modifiedBy');
	           $scope.getData('WorkAreaController/saveWorkArea.json', angular.toJson($scope.work), 'saveWorkArea');
		   }
	   };
	   
	   $scope.transactionDatesListChange = function(){
	        $('.dropdown-toggle').removeClass('disabled');
	        //alert($scope.transactionModel);
	       $scope.getData('WorkAreaController/getWorkAreaById.json', { workAreaDetailsId: ($scope.transactionModel != undefined || $scope.transactionModel != null) ? $scope.transactionModel:'0',customerId: '' }, 'workAreaList')
	    }
	   
	   // pop up actions
	   $scope.plusIconAction = function(){
		   if($scope.area != undefined && $scope.area.workAreaSkillName != undefined)
		   $scope.area.workAreaSkillName = '';
		   $scope.popUpSave = true;
		   $scope.popUpUpdate =false;
	   }
	   
	   $scope.saveDetails = function(){   
		   var status = false;
		   angular.forEach($scope.workList.skillList, function(value, key){			  
			      if(value.workAreaSkillName == $scope.area.workAreaSkillName){
			    	  $scope.Messager('error', 'Error', 'Skill Already Added',true); 
			    	  status = true;			    		
			      }else{
			    	  $('div[id^="myModal"]').modal('hide');
			      }
			         
			   });	
		   if(status){
			   $scope.Messager('error', 'Error', 'Skill Already Added',true); 
			   return;
		   }
		   if($scope.area.workAreaSkillName != null && $scope.area.workAreaSkillName != ''){
			  $scope.workList.skillList.push({
		       		'workAreaSkillName':$scope.area.workAreaSkillName
		       	});   	
			  	$('div[id^="myModal"]').modal('hide');
			   $scope.area.workAreaSkillName="";
			   
			   //$scope.Messager('success', 'success', 'Skill type Saved Successfully',true);
			   
		   }else{
			   $scope.Messager('error', 'Error',' Please Select Skill Type..',true); 
		   }
	   }
	   
	   /*$scope.Edit = function($event){    	
	       	
	    	var data  = $scope.workList.skillList[$($event.target).parent().parent().index()];
	    	if($scope.area == undefined || $scope.area.workAreaSkillName == undefined){
	    		$scope.area = new Object();
	    		$scope.area.workAreaSkillName = "";
	    	}
	    	$scope.area.workAreaSkillName = data.workAreaSkillName;
	       	$scope.popUpSave = false;
	       	$scope.popUpUpdate =true;
	   }
	
	   $scope.updateDetails= function($event){  
		   angular.forEach($scope.workList.skillList, function(value, key){			  
			      if(value.workAreaSkillName == $scope.area.workAreaSkillName){			    	 
			    	  status = true;			    		
			      }else{
			    	  $('div[id^="myModal"]').modal('hide');
			      }
			         
			   });	
		   if(status){
			   $scope.Messager('error', 'Error', 'Skill type Already Exists...',true); 
			   return;
		   }
		   
		  if($scope.area.workAreaSkillName != null || $scope.area.workAreaSkillName != ''){
			  $scope.workList.skillList.splice($($event.target).parent().parent().index(),1); 	
			  var skill =  $scope.workList.skillList.push({
		   		'workAreaSkillName':$scope.area.workAreaSkillName
		   	}); 
	   		
			  $('div[id^="myModal"]').modal('hide');
			  $scope.area.workAreaSkillName="";
			  $scope.popUpSave = true;
		      $scope.popUpUpdate =false;
			  $scope.Messager('success', 'success', 'Skill type Updated Successfully',true);
		  }
	   }*/
	
	   $scope.Delete = function($event){    
		   //window.confirm("Do you want to delete the Skill type  "+JSON.stringify($scope.workList.skillList))
		   var del = confirm("Are you sure you want to delete this skill ? ");
		   if (del){
			   $scope.workList.skillList.splice($($event.target).parent().parent().index(),1);
			   //$scope.Messager('success', 'success', 'Skill type deleted Successfully',true);
		   }
	   }
	
}]);


