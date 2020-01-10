'use strict';


usersControllers.controller('usersCtrl', ['$scope','$http', '$resource','$routeParams','$filter','$cookieStore','$compile','$window', '$location', '$parse', function ($scope,$http, $resource, $routeParams,$filter,$cookieStore,$compile,$window,$location,$parse) {

	var employeeDataTable;	
	var vendorDataTable;
	$scope.json_status = [{"id":"Y","name":"Active"},
         {"id":"N","name":"In-Active"}];
	
	 if($cookieStore.get('roleNamesArray').includes("Customer Audit Admin")){ 
		 $scope.userTypeList = [{"id":"Vendor","name":"Vendor"}];
	 }else{
		 $scope.userTypeList = [{"id":"Employee","name":"Employee"},
		                        {"id":"Vendor","name":"Vendor"}];
	 }
	 
	
	
	$scope.users = new Object();
	$scope.users.isActive = 'Y';
    $scope.enable = false;
    $scope.showTableData = false;
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
    
    $scope.bindData = function(responseData){    	
    	angular.forEach(responseData, function (value, key) {
  		    $parse(key).assign($scope, value);  
  		});
    }

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
        	if(type == 'masterData'){
        		$scope.customerList = response.data.customerList;
        		$scope.companyList = response.data.companyList;
        		$scope.locationList = response.data.locationList;
        		
    		 if($cookieStore.get('roleNamesArray').includes("Customer Audit Admin")){
    			 $scope.roles = [];
		        		angular.forEach(response.data.roles, function(value, key) {		        			
		        			if(value.roleName == "Vendor Admin"){    			
		        				$scope.roles.push({"roleId:":response.data.roles[key].roleId,"roleName":response.data.roles[key].roleName});
		        			}        			
		        		});        	
		        		
        		}else{
        			$scope.roles = response.data.roles;
        		}
        		if($routeParams.id > 0){
	        		$scope.users = response.data.users;
	        		$scope.users.vendorName = (response.data.users.vendorName == undefined || response.data.users.vendorName == 'null' || response.data.users.vendorName == null || response.data.users.vendorName == ' ' || response.data.users.vendorName == '') ? '' : response.data.users.vendorName
	        		$scope.users.employeeName = (response.data.users.employeeName == undefined || response.data.users.employeeName == 'null' || response.data.users.employeeName == null || response.data.users.employeeName == ' ' || response.data.users.employeeName == '') ? ' ' : response.data.users.employeeName
	        		$scope.users.userRoles = $scope.users.userRoles.map(Number);
	        		$scope.users.locationArrayId = $scope.users.locationArrayId.map(Number);
	        		$scope.users.plantArrayId = $scope.users.plantArrayId.map(Number);
	        		$scope.users.departmentArrayId = $scope.users.departmentArrayId.map(Number);
	        		$scope.users.sectionArrayId = $scope.users.sectionArrayId.map(Number);
	        		$scope.users.workAreaArrayId = $scope.users.workAreaArrayId.map(Number);
	        		   $scope.locationChange();
	        		    $scope.plantChange ();
	        		    $scope.departmentChange();
	        		    $scope.sectionChange ();
	        		alert(angular.toJson($scope.users));
        		}
        		if ( ! $.fn.DataTable.isDataTable( '#vendorTable' ) ) {
        			vendorDataTable = $('#vendorTable').DataTable({        
	                     "columns": [
	                    	 { "data": "vendorCode",
	  	                        	"render": function ( data, type, full, meta ) {                 		                    		 
		  	               		      return '<input type="radio" name="vendorId" ng-model="selectedVendor" id="'+full.vendorId+'"  onclick="angular.element(this).scope().fun_selectedVendor('+full.vendorId+',\''+full.vendorName+'\')"></a>';
		  	               		    }
		  	                     },
		  	                     
	                        { "data": "vendorCode"},
	                        { "data": "vendorName" },
	                        { "data": "transactionDate", "render":function (data) {
	                            return $filter('date')(data,"dd/MM/yyyy");;
	                        } },
	                        { "data": "vendorStatus" },
	                        { "data": "locationName" },],                      
	                        
	               });
        		}
        		if ( ! $.fn.DataTable.isDataTable( '#employeeTable' ) ) {        		   
        			employeeDataTable = $('#employeeTable').DataTable({        
  	                     "columns": [	                           
  	                        { "data": "employeeCode",
  	                        	"render": function ( data, type, full, meta ) {                 		                    		 
	  	               		      return '<input type="radio" name="employeeId" ng-model="selectedEmp" id="'+full.employeeId+'" onclick="angular.element(this).scope().fun_selectedEmployee('+full.employeeId+',\''+full.firstName+'\')"></a>';
	  	               		    }
	  	                     },  	   
	  	                    { "data": "employeeCode" },
                     		{ "data": "firstName" },
  	                   		{ "data": "jobCode" },
  	                        { "data": "locationName" },  	                     
  	                        { "data": "departmentName" }]
  	               });  
  	      		}
        	} else if(type == 'saveOrUpdate'){
            	if(!angular.isUndefined(response.data) && response.data == -1){
            		$scope.Messager('error', 'Error', 'User Name already Exists...');
            	}else if(!angular.isUndefined(response.data) && response.data == -2){
            		$scope.Messager('error', 'Error', 'User in use, Please remove dependency...');
            	}else if(!angular.isUndefined(response.data) && response.data > 0){
            		$scope.Messager('success', 'success', 'User Details '+$scope.saveOrUpdate+'d Successfully...');
            		$location.path('/users');  /* /'+response.data); */
            	}else{
            		$scope.Messager('error', 'Error', 'User Details not saved... ');
            	}
            }  else if(type == 'getEmployeeGridData'){
            	  $scope.showTableData = true;
            	  $scope.employeeList = response.data;
	    		  employeeDataTable.clear().rows.add($scope.employeeList).draw();
	    		  vendorDataTable.clear();
	    		  $('#vendorTable').hide();
	    		  $('#employeeTable').show();
	    		//  $('#employeeTable').html($compile( $('#employeeTable').html())($scope));
	    		//  $compile(angular.element($('#employeeTable').html()))($scope).append(angular.element(('#employeeTable')));
	    		  $scope.selectedEmp = $scope.users.employeeUniqueId;
            } else if ( type == 'getVendorGridData'){
            	  $scope.showTableData = true;
      	    	  $scope.vendorList = response.data;
          	      employeeDataTable.clear();
          	      $('#employeeTable').hide();
          	      $('#vendorTable').show();
          	      vendorDataTable.clear().rows.add($scope.vendorList).draw();
	    		// $('#vendorTable').html($compile( $('#vendorTable').html())($scope));
          	   // $compile(angular.element($('#vendorTable').html()))($scope).replaceWith(('#vendorTable'));
            }else if(type == 'plantsList'){
            	$scope.plantList = response.data;
            	//alert(angular.toJson(response.data));
            }else if(type == 'departmentsList'){
            	$scope.departmentList = response.data.departmentList;
            }else if(type == 'sectionsList'){            	
            	$scope.sectionList = response.data.sectionsList;
            }else if(type == 'workAreasList'){
            	$scope.workAreaList = response.data;
            }
        },
        function () {
        	 $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
        });
    }
   
    if($routeParams.id > 0){
    	$scope.saveOrUpdate = 'Update';
    }else{
    	$scope.saveOrUpdate = 'Save';
    }   
    $scope.users.customerId = $cookieStore.get('customerId');
    $scope.users.companyId = $cookieStore.get('companyId');
    $scope.users.userId = $routeParams.id;
    
    $scope.getData('usersController/getMaterDataForUser.json', angular.toJson($scope.users)  , 'masterData');
    
    $scope.saveOrUpdateDetails = function(){
    	if($('#usersForm').valid()){
    		if(($scope.users.employeeUniqueId == undefined || $scope.users.employeeUniqueId == '' || $scope.users.employeeUniqueId < 1 ) && ( $scope.users.vendorId == undefined || $scope.users.vendorId == '' || $scope.users.vendorId < 1)){
    			$scope.Messager('error', 'Error', 'Please Select '+$scope.users.userType+' ... ',true);
    		} else if($scope.users.password != $scope.users.confirmPassword){
    			$scope.Messager('error', 'Error', 'Password and Confirm Password Does not match. ',true);
    		}else if($window.confirm('Are you sure to change roles for this User ?') ){
    			$scope.users.createdBy = $cookieStore.get('createdBy');
				$scope.users.modifiedBy = $cookieStore.get('modifiedBy');
		    	$scope.getData('usersController/saveOrUpdateUserDetails.json', angular.toJson($scope.users) , 'saveOrUpdate');
			}
    	}
    }
    
    $scope.fun_popupReset = function(){
    	$scope.empCode = '';
    	$scope.empName = '';
    	$scope.showTableData = false;
    }
    
    $scope.searchEmployee = function(){
    	if($scope.users.userType == 'Employee')
    		$scope.getData('usersController/getEmployeeDetails.json', {employeeCode: $scope.empCode, firstName : $scope.empName, customerId: $scope.users.customerId, companyId: $scope.users.companyId  } , 'getEmployeeGridData');
    	else if($scope.users.userType == 'Vendor')
    		$scope.getData('vendorController/VendorGridDetails.json', { customerId: $cookieStore.get('customerId') , companyId: $cookieStore.get('companyId') , status : 'validated',vendorCode: $scope.empCode, vendorName : $scope.empName }, 'getVendorGridData');
    }
    
    $scope.fun_selectedEmployee = function(empId, employeeName){
    	$scope.$apply(function() { 
    	$scope.users.employeeUniqueId = empId;
    	$scope.users.employeeName = employeeName;
    	$scope.users.vendorId = 0;
    	$scope.users.vendorName = ' ';
    	});
    }
    
    $scope.fun_selectedVendor = function(vendorId, vendorName){
    	//alert(vendorId+"::::"+vendorName);
    	$scope.$apply(function() { 
	    	$scope.users.vendorId = vendorId;
	    	$scope.users.vendorName = vendorName;
	    	$scope.users.employeeUniqueId = 0;
	    	$scope.users.employeeName = ' ';
    	});
    	//alert($scope.users.vendorId+"::::"+$scope.users.vendorName);
    	
    }
    
    $scope.passwordChanged = function(){
    	$scope.users.passwordChanged = true;
    	$scope.users.confirmPassword = '';
    	
    }
 
    $scope.locationChange = function(){
    	if($scope.users.locationArrayId != undefined && $scope.users.locationArrayId != null && $scope.users.locationArrayId != ''){
    		$scope.departmentList = [];
    		$scope.sectionList = [];
    		$scope.workAreaList = [];
    		//alert($scope.users.locationId);
    		$scope.getData('jobAllocationByVendorController/getPlantsList.json', {locationArrayId:$scope.users.locationArrayId}, 'plantsList');
    	}else{
    		$scope.plantsList =[];
    		$scope.departmentList = [];
    		$scope.sectionList = [];
    		$scope.workAreaList = [];
    	}
    }
    
    $scope.plantChange = function(){
    	if($scope.users.locationArrayId != undefined && $scope.users.locationArrayId != null && $scope.users.locationArrayId != '' && $scope.users.plantArrayId != undefined && $scope.users.plantArrayId != null && $scope.users.plantArrayId != ''){
    		$scope.sectioneList = [];
    		$scope.workAreaList = [];
    		$scope.getData('associatingDepartmentToLocationPlantController/getDepartMentDetailsByLocationAndPlantId.json', {customerId:$cookieStore.get('customerId'), companyId: $cookieStore.get('companyId'), locationArrayId:$scope.users.locationArrayId,plantArrayId:$scope.users.plantArrayId}, 'departmentsList');
    	}else{
    		$scope.departmentList = [];
    		$scope.sectionList = [];
    		$scope.workAreaList = [];
    	}
    }
    
    $scope.departmentChange = function(){
    	if($scope.users.locationArrayId != undefined && $scope.users.locationArrayId != null && $scope.users.locationArrayId != '' && $scope.users.plantArrayId != undefined && $scope.users.plantArrayId != null && $scope.users.plantArrayId != '' && $scope.users.departmentArrayId != undefined && $scope.users.departmentArrayId != null && $scope.users.departmentArrayId != ''){
    		$scope.workAreasList = [];    	
    		$scope.getData('associatingDepartmentToLocationPlantController/getSectionDetailsByLocationAndPlantAndDeptId.json', {customerId:$cookieStore.get('customerId'), companyId: $cookieStore.get('companyId'), locationArrayId:$scope.users.locationArrayId,plantArrayId:$scope.users.plantArrayId, departmentArrayId : $scope.users.departmentArrayId}, 'sectionsList');
    	}else{
    		$scope.sectionList = []; 
    		$scope.workAreaList = [];
    	}
    }
    

    
    $scope.sectionChange = function(){
    	if($scope.users.locationArrayId != undefined && $scope.users.locationArrayId != null && $scope.users.locationArrayId != '' && $scope.users.plantArrayId != undefined && $scope.users.plantArrayId != null && $scope.users.plantArrayId != '' && $scope.users.sectionArrayId != undefined && $scope.users.sectionArrayId != null && $scope.users.sectionArrayId != '' && $scope.users.departmentArrayId != undefined && $scope.users.departmentArrayId != null && $scope.users.departmentArrayId != ''){
    			$scope.getData('jobAllocationByVendorController/getAllWorkAreasBySectionesAndLocationAndPlantAndDept.json', {customerId:$cookieStore.get('customerId'), companyId: $cookieStore.get('companyId'), locationArrayId:$scope.users.locationArrayId,plantArrayId:$scope.users.plantArrayId,sectionArrayId:$scope.users.sectionArrayId,departmentArrayId:$scope.users.departmentArrayId}, 'workAreasList');
    	}else{    	
    		$scope.workAreaList = [];
    	}
    }
   
}]);