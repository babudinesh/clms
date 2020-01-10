'use strict';
//var departmentControllers = angular.module("myApp.addDepartment", []);

/*departmentControllers
.service('myservice', function () {
    this.companyId = 0;
    this.customerId = 0;
   
});*/
DepartmentController.controller('AddDepartmentCtrl', ['$scope', '$http', '$resource', '$routeParams', '$location','$filter','myservice','$cookieStore', function ($scope, $http, $resource, $routeParams, $location, $filter, myservice, $cookieStore) {
		$.material.init();
		$scope.div = true;
		
		$scope.dept=new Object();
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
	        $scope.readOnly = true;
	        $scope.datesReadOnly = true;
	        $scope.updateBtn = true;
	        $scope.saveBtn = false;
	        $scope.viewOrUpdateBtn = true;
	        $scope.correcttHistoryBtn = false;
	        $scope.resetBtn = false;
	        $scope.transactionList = false;
	        $scope.gridButtons = false;
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
        	$scope.dept.isActive ="Y";
        	$scope.dept.deptOwner='N';
        	$scope.gridButtons = true;
	    }
	    
	    $scope.updateDeptBtnAction = function (this_obj) {
	    	$scope.dCode=1;
	        $scope.readOnly = false;
	        $scope.onlyRead = true;
	        $scope.datesReadOnly = false;
	        $scope.updateBtn = false;
	        $scope.saveBtn = true;
	        $scope.viewOrUpdateBtn = false;
	        $scope.correctHistoryBtn = false;
	        $scope.resetBtn = false;
	        $scope.cancelBtn = true;
	    	$scope.dept.transactionDate=  $filter('date')( new Date(), 'dd/MM/yyyy');
	        $scope.transactionList = false;
	        $scope.dept.departmentInfoId = 0;
	        
	        
	        if($scope.deptList.divisionList != null && $scope.deptList.divisionList != undefined && $scope.deptList.divisionList != "" && $scope.deptList.divisionList.length == 1 ){
         	   $scope.gridButtons = false;
            }else{
            	$scope.gridButtons = true;
            }
	        $('.dropdown-toggle').removeClass('disabled');
	    }
	    
	    $scope.viewOrEditHistory = function () {
	        $scope.readOnly = false;
	        $scope.onlyRead = true;
	        $scope.datesReadOnly = false;
	        $scope.updateBtn = false;
	        $scope.saveBtn = false;
	        $scope.viewOrUpdateBtn = false;
	        $scope.correcttHistoryBtn = true;
	        $scope.resetBtn = false;      
	        $scope.gridButtons = true;
	        $scope.transactionList = true;
	        $scope.getData('DepartmentController/getTransactionDatesList.json', { companyId: $scope.dept.companyId, customerId: $scope.dept.customerId ,departmentUniqueId: $scope.dept.departmentId }, 'transactionDatesChange');       
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
	        $scope.gridButtons = false;
	        $scope.returnToSearchBtn = true;
	        $scope.getData('DepartmentController/getDepartmentById.json', { departmentInfoId: $routeParams.id,customerId : '',divisionId:$cookieStore.get('divisionId') > 0 ? $cookieStore.get('divisionId') :0}, 'deptList')
	    }
	    
	    $scope.customerChange = function () {
	        $scope.getData('vendorController/getCompanyNamesAsDropDown.json', {customerId:  $scope.dept.customerId, companyId:$cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "" ? $cookieStore.get('companyId') : $scope.dept.companyId }, 'customerChange');
	    };
	    
	    $scope.companyChange = function () {
	    	//alert($scope.dept.companyId);
	        $scope.getData('DepartmentController/getDeptnameForCostCenter.json', { companyId: $scope.dept.companyId, customerId: $scope.dept.customerId,divisionId:$cookieStore.get('divisionId') > 0 ? $cookieStore.get('divisionId') :0 }, 'companyChange');
	    };
	    
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
	              } else if (type == 'deptList') {					
	                $scope.deptList = response.data;	 
	               // alert(angular.toJson(response.data.costCenterList));
	                $scope.dept = response.data.deptVo[0];
	                
	                if( $scope.dept != undefined ){
		               if($scope.dept.departmentOwnerEmp != null || $scope.dept.departmentOwnerEmp != undefined ){
		                	$scope.dept.deptOwner='E';
		                	
		                }else if($scope.dept.departmentOwnerJob != null  ){
		                	$scope.dept.deptOwner='J';
		                }else{
		                	$scope.dept.deptOwner='N';
		                }
		              
		               $scope.dept.transactionDate = $filter('date')(response.data.deptVo[0].transactionDate,'dd/MM/yyyy')
		               $scope.transDate1 = $filter('date')(response.data.deptVo[0].transactionDate,'dd/MM/yyyy');
		               $scope.deptList.companyList = response.data.companyList;
		              // $scope.deptList.costCenterList = response.data.costCenterList;
		             
		               $scope.customerChange();
		               $scope.companyChange();
		               
		             
		             //  $scope.deptList.jobCodeList = response.data.jobCodeList;
		               $scope.deptList.divList = response.data.divisionList;
		               $scope.deptList.divisionList = response.data.deptVo[0].departmentDivisionList;
		               if( (response.data.divisionList == null || response.data.divisionList == "" || response.data.divisionList == undefined) && (response.data.divisionList.length == 0)){
		                	$scope.div = false;
		               }
		               if($scope.deptList.divisionList != null && $scope.deptList.divisionList != undefined && $scope.deptList.divisionList != "" && $scope.deptList.divisionList.length == 1 ){
		            	   $scope.gridButtons = false;
		               }
		            		                
	                }else{
	                	$scope.dept = new Object();
	                	$scope.dept.transactionDate=  $filter('date')( new Date(), 'dd/MM/yyyy');
	                	$scope.dept.isActive ="Y";
	                	$scope.dept.deptOwner='N';
	                	 $scope.dept.isCostCenter = true;
	                	if(response.data.customerList != undefined && response.data.customerList != '' && response.data.customerList.length == 1){
		                	
		                	$scope.dept.customerId =response.data.customerList[0].customerId;
		                	$scope.customerChange();
		                }
	                	
	                }
	                
	                $scope.deptList.deptTypeList.push({"id":0,"name":"Both"});  
	                
	             // for button views
	  			  	if($scope.buttonArray == undefined || $scope.buttonArray == '')
	        	    $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Department'}, 'buttonsAction');
	            }
	            else if (type == 'customerChange') {
					
	                $scope.deptList.companyList = response.data;
	                if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	                	$scope.dept.companyId = response.data[0].id;
	                	 $scope.companyChange();
	                }
	                $scope.fun_checkDeptCode();
	            }
	            else if (type == 'companyChange') {
	            	//alert(JSON.stringify(response.data));
	                $scope.deptList.costCenterList = response.data.costCenterList;
	                $scope.deptList.jobCodeList = response.data.jobCodeList;
	                $scope.deptList.divList = response.data.divisionList;
	                $scope.deptList.employeeList = response.data.employeeList;
	                $scope.isCostCenterClick();
	                if( (response.data.divisionList == null || response.data.divisionList == "" || response.data.divisionList == undefined) && (response.data.divisionList.length == 0)){
	                	$scope.div = false;
	                }
	                $scope.fun_checkDeptCode();
	            }else if(type == 'validateCode'){
	            	$scope.dCode = new Object();
	            	//alert(JSON.stringify(response.data));
	            	$scope.dCode = response.data;
	            }else if (type == 'saveDepartment') {
	                //alert(angular.toJson(response.data));
	                if(response.data.id == 0){
	                	$scope.Messager('error', 'Error', 'Invalid Data',buttonDisable);
	                	$scope.transactionDate = $filter('date')($('#transactionDate').val(),'dd/MM/yyyy');
	                }else if(response.data.id == -1){
	                	$scope.Messager('error', 'Transaction Date should not be less than Company Transaction Date', 'Invalid Data',buttonDisable);
	                	$scope.transactionDate = $filter('date')($('#transactionDate').val(),'dd/MM/yyyy');
	                }else{
	                	myservice.departmentInfoId = response.data.id;
		                $cookieStore.put('departmentInfoId', response.data.id);
	                	$scope.Messager('success', 'success', 'Department Saved Successfully',buttonDisable);
	                	$location.path('/addDepartment/'+response.data.id);
	                }
	            	
	            }else if (type == 'transactionDatesChange') {
	            	//alert(JSON.stringify(response.data));
	            	
	            	var k = response.data.length;
	            	if(response.data.length > 1){
		            	for(var i = response.data.length; i> 0;i--){
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
	     	       $scope.getData('DepartmentController/getDepartmentById.json', { departmentInfoId: ($scope.transactionModel != undefined || $scope.transactionModel != null) ? $scope.transactionModel:'0' , customerId: "" }, 'deptList')

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
	       
	    $scope.getData('DepartmentController/getDepartmentById.json', { departmentInfoId: $routeParams.id, customerId: $cookieStore.get('customerId'),divisionId:$cookieStore.get('divisionId') > 0 ? $cookieStore.get('divisionId') :0}, 'deptList')
	    
	     

	    
	   
	
	    $scope.fun_checkDeptCode = function(){
	    	if($scope.dept.customerId != null && $scope.dept.customerId != undefined && $scope.dept.customerId != ''
	    			&& ($scope.dept.companyId != null && $scope.dept.companyId != undefined && $scope.dept.companyId != '')
	    				    			&& ($scope.dept.departmentCode != null && $scope.dept.departmentCode != undefined && $scope.dept.departmentCode != '')){

	    		$scope.getData('DepartmentController/validateDeptCode.json',{ customerId : $scope.dept.customerId, companyId: $scope.dept.companyId, departmentCode:$scope.dept.departmentCode },'validateCode')
	    	}
	    };
	    
	    
	    $scope.isCostCenterClick = function(){
	    	if($routeParams.id > 0){
	    		//alert($scope.deptList.costCenterList.length);
	    		 angular.forEach($scope.deptList.costCenterList, function(value, key){
	    			// alert(value+":::"+key);
				      if(value.id == $scope.dept.departmentId){
				    	  $scope.deptList.costCenterList.splice(key,1);
				      }
				         
				   });	
	    	}	    	
	    	/*if(!$scope.dept.isCostCenter){
	    		$scope.dept.costCenter = "";
	    	}*/
	    }
	    
	    $scope.save = function ($event) {
	    	if($('#deptForm').valid()){
	    		if($scope.dCode == 0){
		    			$scope.Messager('error', 'Error', 'Department code already exists'); 
		    	}else if($scope.transDate1 != '' && $scope.transDate1 != undefined && new Date(moment($scope.transDate1,'DD/MM/YYYY').format('YYYY-MM-DD')) .getTime() > new Date(moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime()){
		    		   $scope.Messager('error', 'Error', 'Transaction Date Should not be less than previous transaction date');
		        	   $scope.location.transactionDate =  $scope.transDate1;
		    	}else{
			    	  $scope.dept.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
			    	  $scope.dept.departmentDivisionList = $scope.deptList.divisionList;
			    	  $scope.dept.createdBy = $cookieStore.get('createdBy');
			          $scope.dept.modifiedBy = $cookieStore.get('modifiedBy');
			          //alert(angular.toJson($scope.dept));
			          
			          if($scope.dept.isCostCenter){
				    		$scope.dept.costCenter = "";
				    	}
			    	  $scope.getData('DepartmentController/saveDepartment.json', angular.toJson($scope.dept), 'saveDepartment');
		    	}
	    	}
	        
	    };
	
	    
	    $scope.correctHistorySave= function(){
	    	//alert(1);
	    	if($('#deptForm').valid()){ 
	    		if($scope.transDate1 != '' && $scope.transDate1 != undefined && new Date(moment($scope.transDate1,'DD/MM/YYYY').format('YYYY-MM-DD')) .getTime() > new Date(moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime()){
	    			$scope.Messager('error', 'Error', 'Transaction Date Should not be less than previous transaction date');
	    			$scope.location.transactionDate =  $scope.transDate1;
	    		}else{
	    			$scope.dept.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
	    			$scope.dept.departmentDivisionList = $scope.deptList.divisionList;
	    			$scope.dept.modifiedBy = $cookieStore.get('modifiedBy');
	    			$scope.getData('DepartmentController/saveDepartment.json', angular.toJson($scope.dept), 'saveDepartment');
	   
	    		}
	    	}
	    };
	
	    $scope.transactionDatesListChange = function(){
	       $scope.getData('DepartmentController/getDepartmentById.json', { departmentInfoId: ($scope.transactionModel != undefined || $scope.transactionModel != null) ? $scope.transactionModel:'0' , customerId: "",divisionId:$cookieStore.get('divisionId') > 0 ? $cookieStore.get('divisionId') :0 }, 'deptList')
	       $('.dropdown-toggle').removeClass('disabled');
	   };
	   
	   
	   $scope.saveDivisionDetails = function(division){  
			   
		   var status = false;
		   angular.forEach($scope.deptList.divisionList, function(value, key){	
			      if(value.divisionName == $scope.division.divisionName){
			    	  $scope.Messager('error', 'Error', 'Division already added',true); 
			    	  status = true;
			      }
			         
			   });	
    	   if(!status && $scope.division != undefined && $scope.division != ''){
    		  var name =  $('#divisionName option:selected').html();
    		   $scope.deptList.divisionList.push({
             		'divisionCode':  name.split('-')[1],		
             		'divisionName':name.split('-')[0],
             		'divisionDetailsId':$scope.division.divisionDetailsId ,
             		'divisionId':$scope.division.divisionId 
         	   });  
    		   $scope.gridButtons = false;
    		   $('div[id^="myModal1"]').modal('hide');
			   $scope.popUpSave = true;
		       $scope.popUpUpdate =false;
    	   }else if(status){
    		   $scope.Messager('error', 'Error', 'Division already added',true); 
    	   }
       };
       
       
       $scope.plusIconAction = function(){  	
    	   if($scope.div){
    		   $('div[id^="myModal1"]').modal('show');
    		   $scope.division="";
        	   $scope.popUpSave = true;
        	   $scope.popUpUpdate =false;
    	   }else{
    		   $scope.Messager('error', 'Error', 'Divisions does not exist'); 
    	   }
    	   
       };
       
       $scope.Delete = function($event){    
    	   if($scope.updateBtn){
    		   //no action
    	   }else{
	    	   var del = confirm("Do you want to delete the "+$scope.deptList.divisionList[$($event.target).parent().parent().index()].divisionName);    	
	    	   if(del){
	    		   $scope.deptList.divisionList.splice($($event.target).parent().parent().index(),1);
	       		}
	    	   if($scope.deptList.divisionList != null && $scope.deptList.divisionList != undefined && $scope.deptList.divisionList != "" && $scope.deptList.divisionList.length >= 1 ){
	        	   $scope.gridButtons = false;
	           }else{
	        	   $scope.gridButtons = true;
	           }
    	   }
       };

}]);
