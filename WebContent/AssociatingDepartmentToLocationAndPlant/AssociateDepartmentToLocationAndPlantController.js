'use strict';

var associatingDepartmentToLocationAndPlantController = angular.module("myApp.associatingDepartmentToLocationAndPlant", ['ngCookies']);



associatingDepartmentToLocationAndPlantController.directive('myAttribute1', function () {	
    return {
    	 scope: {myValue: "&myAttribute1"},
    	  link: function (scope, iElm, iAttrs) {
    	    var x = scope.myValue();
    	    // x == {name:"Umur", id:1}
    	  //  alert(angular.toJson(x));
    	  }
    };
});
associatingDepartmentToLocationAndPlantController.controller("associatingDepartmentToLocationAndPlantController", ['$scope', '$http', '$resource','$location','$filter', '$routeParams','myservice','$cookieStore', function ($scope, $http, $resource, $location, $filter, $routeParams, myservice, $cookieStore) {
	 $.material.init();
	

     $scope.location = new Object();    
     $scope.workerVo = new Object();
     $scope.deptToLocAndPlant = new Object();
     $scope.deptToLocAndPlant.departmentlist = [];
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
                   'Content-Type': 'application/json' },
               data: data
           }
           $http(req).then(function (response) {
        	   if(type == 'buttonsAction'){
        			$scope.buttonArray = response.data.screenButtonNamesArray;
        		} else if(type == 'customerList'){
        		   $scope.customerList = response.data.customerList; 
    		 
        		   if(response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
        			   $scope.deptToLocAndPlant.customerId=response.data.customerList[0].customerId;		                
		               $scope.customerChange();
	               }
        		   // for button views
        		   if($scope.buttonArray == undefined || $scope.buttonArray == '')
        			   $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Associating Department To Location And Plant'}, 'buttonsAction');
        	   }else if (type == 'customerChange'){
        		   $scope.companyList = response.data;
             
        		   if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
        			   $scope.deptToLocAndPlant.companyId = response.data[0].id;
        			   $scope.companyChange();
        		   }
        	   }else if (type == 'companyChange') {            
        		   $scope.locationList  = response.data.locationsList;            	
        	   }else if(type == 'locationChange'){
        		   $scope.plantList = response.data;
        	   }else if (type == 'plantChange') {            	
        		   $scope.deptToLocAndPlant.departmentlist = response.data.departmentList;	            	
        	   }else if(type == 'departmentChange'){
        		   $scope.departmentlst = response.data.deptVo[0];		        	  
        		   var flag= true;
        		   angular.forEach($scope.deptToLocAndPlant.departmentlist, function(value, key){
        			   if(value.departmentName == $scope.departmentlst.departmentName){
        				   flag= false;
        			   }
        		   });		       	   
	        	 
        		   if(flag){
        			   $scope.deptToLocAndPlant.departmentlist.push({
		              		'departmentCode':$scope.departmentlst.departmentCode,  		
		              		'departmentName':$scope.departmentlst.departmentName ,
		              		'departmentInfoId':$scope.departmentlst.departmentInfoId ,
		              		'departmentId':$scope.departmentlst.departmentId 
        			   }); 
        		   }
        	   }else if(type == 'masterDepartmentList'){
		        	  $scope.companyDepartmentList = response.data.departmentList;
		       }else if(type == 'saveDetails'){
		    	   if(response.data.id == 1){
		    		   $scope.Messager('success', 'success', 'Department Association Successfully Done.',buttonDisable);  
		    	   }
		       }
           },
           function () { 
        	   $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
           });
       }
             $scope.getData('vendorController/getCustomerNamesAsDropDown.json', { customerId: $cookieStore.get('customerId')}, 'customerList')
       
       $scope.customerChange = function () {
       		$scope.companyName = '';       
       		$scope.getData('vendorController/getCompanyNamesAsDropDown.json', {customerId:$scope.deptToLocAndPlant.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.deptToLocAndPlant.companyId != undefined && $scope.deptToLocAndPlant.companyId != '' ? $scope.deptToLocAndPlant.companyId : 0}, 'customerChange');
       };
       
       
	  	
       $scope.companyChange = function() {    	   
    	   $scope.workerVo.customerId = $scope.deptToLocAndPlant.customerId;
	       $scope.workerVo.companyId = $scope.deptToLocAndPlant.companyId;        	
	   	   $scope.getData('jobAllocationByVendorController/getDepartmentsAndLocationesList.json', angular.toJson($scope.workerVo), 'companyChange');
	   	   $scope.getData('associatingDepartmentToLocationPlantController/getMasterDepartmentsList.json', {customerId:$scope.deptToLocAndPlant.customerId,companyId:$scope.deptToLocAndPlant.companyId}, 'masterDepartmentList');
	      
       };
       
       
       $scope.locationChange = function(){
       	if($scope.deptToLocAndPlant.locationId != undefined && $scope.deptToLocAndPlant.locationId != null && $scope.deptToLocAndPlant.locationId != ''){
       		$scope.getData('jobAllocationByVendorController/getPlantsList.json', {locationId:$scope.deptToLocAndPlant.locationId}, 'locationChange');
       	}
       }
       
		
       $scope.plantChange = function(){
       	if($scope.deptToLocAndPlant.locationId != undefined && $scope.deptToLocAndPlant.locationId != null && $scope.deptToLocAndPlant.locationId != '' && $scope.deptToLocAndPlant.plantId != undefined && $scope.deptToLocAndPlant.plantId != null && $scope.deptToLocAndPlant.plantId != ''){
       		$scope.getData('associatingDepartmentToLocationPlantController/getDepartMentDetailsByLocationAndPlantId.json', {customerId:$scope.deptToLocAndPlant.customerId,companyId:$scope.deptToLocAndPlant.companyId,locationId:$scope.deptToLocAndPlant.locationId,plantId:$scope.deptToLocAndPlant.plantId}, 'plantChange');
       	}
       }
    	
       $scope.saveDepartmentDetails = function(){  
    	   var status = false;    	  
		   angular.forEach($scope.deptToLocAndPlant.departmentlist, function(value, key){			   	
			      if(value.departmentName == $scope.department.departmentName){
			    	  $scope.Messager('error', 'Error', 'Department already added',true); 
			    	  status = true;			    		
			      }else{
			    	  $('div[id^="myModal"]').modal('hide');
			      }
			         
			   });	
		   
    	   if(!status && $scope.department != undefined && $scope.department != ''){    		 
    		   $scope.getData('DepartmentController/getDepartmentById.json', { customerId: ($scope.deptToLocAndPlant.customerId != null || $scope.deptToLocAndPlant.customerId != undefined) ? $scope.deptToLocAndPlant.customerId : 0, companyId: ($scope.deptToLocAndPlant.companyId != null && $scope.deptToLocAndPlant.companyId != undefined) ? $scope.deptToLocAndPlant.companyId : 0, departmentInfoId:$scope.department.departmentInfoId, divisionId:0 }, 'departmentChange');
    		   $('div[id^="myModal"]').modal('hide');
			   $scope.popUpSave = true;
		       $scope.popUpUpdate =false;
    	   }
       };
       
      
       
       
       
       $scope.plusIconAction = function(){  	
    	   $scope.department="";
       		$scope.popUpSave = true;
       		$scope.popUpUpdate =false;
       };
       
       
       $scope.Delete = function($event){    	
       	var del = confirm("Do you want to delete the "+$scope.deptToLocAndPlant.departmentlist[$($event.target).parent().parent().index()].departmentName); 
		   	if(del){
		   		$scope.deptToLocAndPlant.departmentlist.splice($($event.target).parent().parent().index(),1);
		   	}       	
       };	
       
       $scope.associateDepts = function(){  
    	   if($scope.associateAllDepts){    		  
    		   angular.forEach($scope.companyDepartmentList, function(value, key){
    			   $scope.getData('DepartmentController/getDepartmentById.json', { customerId: ($scope.deptToLocAndPlant.customerId != null && $scope.deptToLocAndPlant.customerId != undefined) ? $scope.deptToLocAndPlant.customerId : 0, companyId: ($scope.deptToLocAndPlant.companyId != null && $scope.deptToLocAndPlant.companyId != undefined) ? $scope.deptToLocAndPlant.companyId : 0, departmentInfoId:value.departmentInfoId, divisionId:0 }, 'departmentChange');
    		   });   		 
    	   }else{    		 
    		   
    		   var r = confirm("Do you want to remove all the associated departments");    	
    	    	if(r){
    	    		 $scope.deptToLocAndPlant.departmentlist = [];
    	    	}else{
    	    		$scope.associateAllDepts = true;
    	    	}
    		  
    	   }
    	   
       }
       
       
       
       
       $scope.saveDetals = function($event){
    	   
    	   if($('#associatingDepartmentToLocationPlantForm').valid()){   		   
    		 
    		   if($scope.deptToLocAndPlant.departmentlist != undefined && $scope.deptToLocAndPlant.departmentlist.length >=1){
    			   //alert(angular.toJson($scope.deptToLocAndPlant));
        		   $scope.getData('associatingDepartmentToLocationPlantController/saveAssociatingDepartmentToLocationPlant.json',angular.toJson($scope.deptToLocAndPlant) , 'saveDetails',angular.element($event.currentTarget));
        		  
    		   }else{
    			   
    			   $scope.Messager('error', 'Error', 'Associate Atleast one Department'); 
    		   }
    		  
    	   }
    	   
       }
       
       $scope.clear = function(){
    	   $scope.deptToLocAndPlant = new Object();
       }
  	 
}]);




