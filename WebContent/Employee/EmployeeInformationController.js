'use strict';
var employeeControllers = angular.module("myApp.employeeInformation",[]);
employeeControllers.controller("EmployeeSearchCtrl",  ['$scope', '$rootScope', '$http', '$resource','$location','$cookieStore',function($scope, $rootScope, $http,$resource,$location,$cookieStore) {  
   // $.material.init();
					
	/*$('#txt_effectivedate, #transactionDate , #startDate, #dateOfBirth').bootstrapMaterialDatePicker({
        time : false,
        Button : true,
        format : "DD/MM/YYYY",
        clearButton: true
    });*/
	
	
	
	$('#transactionDate , #startDate, #dateOfBirth').datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown'	    	  
	    });
		
	var employeeDataTable;	
    $('#searchPanel').hide();
	$scope.employeeVo = new Object();
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
   
   
  
    $scope.getData = function (url, data, type,buttonDisable) {
        var req = {
            method: 'POST',
            url:ROOTURL+url,
            headers: {
                'Content-Type': 'application/json'
            },
            data:data
        }
        $http(req).then(function (response) {
        	// alert(JSON.stringify(response.data));
        	if(type == 'buttonsAction'){
        		$scope.buttonArray = response.data.screenButtonNamesArray;
        	} else if(type == 'getCompanyList'){
        		$scope.customerList = response.data.customerList;
        		
        		 if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
		                $scope.employeeVo.customerId=response.data.customerList[0].customerId;		                
		                $scope.customerChange();
		                }
        		 
        		$scope.jobTypeList = response.data.jobTypeList;
        		$scope.jobStatusList = response.data.JobStatusLst;
        		//$scope.result = response.data.empList;
        		
        		if ( ! $.fn.DataTable.isDataTable( '#employeeTable' ) ) {        		   
        			employeeDataTable = $('#employeeTable').DataTable({        
  	                     "columns": [	                           
  	                        { "data": "employeeCode",
  	                        	"render": function ( data, type, full, meta ) {                 		                    		 
	  	               		      return '<a href="#/EmployeeInformation/'+full.employeeId+'">'+data+'</a>';
	  	               		    }
	  	                     },  	                     	                     		
                     		{ "data": "firstName" },
  	                   		{ "data": "jobCode" },
  	                        { "data": "locationName" },  	                     
  	                        { "data": "departmentName" }]
  	               });  
  	      		}
        		// for button views
  			  	if($scope.buttonArray == undefined || $scope.buttonArray == '')
   			 	$scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Employee'}, 'buttonsAction');
        	}       	
        	else if(type == 'customerChange'){
        		$scope.companyList = response.data.companyList;
        		 $scope.list_countries  = response.data.countryList;
        		 if( response.data.companyList[0] != undefined && response.data.companyList[0] != "" && response.data.companyList.length == 1 ){
    	                $scope.employeeVo.companyId = response.data.companyList[0].id;
    	                }
        		 
				//$scope.list_cities = "";
        	} else if(type == 'getEmployeeGridData'){
        		  $('#searchPanel').show();
        		  employeeDataTable.clear().rows.add(response.data.empList).draw(); 	
        		// $scope.result = response.data.empList;
        	} else if(type  == 'validateEmployee'){
        		if(response.data.empList.length == 0){
        			 $cookieStore.put('EmployeecustomerId', $scope.employeeVo.customerId); 
	           	     $cookieStore.put('EmployeecompanyId', $scope.employeeVo.companyId);
	           	     $cookieStore.put('EmployeejobTypeId', $scope.employeeVo.jobTypeId);
	           	     $cookieStore.put('EmployeejobStatusId', $scope.employeeVo.jobStatusId);
	           	     $cookieStore.put('employeeCountryId', $scope.employeeVo.employeeCountryId);
	           	     
	           	     $cookieStore.put('employeeVo.customerName',  $('#customerName option:selected').html());
	           	     $cookieStore.put('employeeVo.companyName', $('#companyName option:selected').html());
	           	     $cookieStore.put('employeeVo.jobType', $('#jobType option:selected').html());
	           	     $cookieStore.put('employeeVo.jobStatus', $('#jobStatus option:selected').html());
	           	     $cookieStore.put('employeeVo.employeeCountryName',  $('#countryName option:selected').html());
	           	     $cookieStore.put('employeeVo.employeeCode', $('#employeeCode').val());
	           	     $location.path('/EmployeeInformation/create')
        		}else if(response.data.empList.length > 0){
        			$scope.Messager('error','Error', 'Employee code already exists')
        		}
        	}
                
          },
        function () {
        	  $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
        	  if(type == 'countryChnage'){
        		  $scope.statesList = "";
				//  $scope.list_cities = "";
          	}else if(type == 'getEmployeeGridData'){
        		$scope.result = [];
        	}
          
          });
    	}   
    
   
   
    	$scope.getData('EmployeeController/getcustomerAndJobsAsDropDownsList.json', {customerId :$cookieStore.get('customerId')}, 'getCompanyList')    	 
     
   
     $scope.customerChange = function () { 
    	$scope.getData('EmployeeController/getcountryAndCompanyList.json', { customerId: $scope.employeeVo.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : ($scope.employeeVo.companyId != undefined ? $scope.employeeVo.companyId : 0) }, 'customerChange');
    	
    };
    
  
    
    
    
    


    $scope.continueToEmployee = function($event){
    	if($('#search').valid()){
   	     	$scope.getData('EmployeeController/getemployeeGridData.json', {customerId :$scope.employeeVo.customerId,companyId:$scope.employeeVo.companyId, employeeCode:$('#employeeCode').val()}, 'validateEmployee')    
		}
    	
    }
    
    
    $scope.SearchExistingEmployees = function($event){
    		
    	     
    	     $cookieStore.put('EmployeecustomerId', $scope.employeeVo.customerId); 
    	     $cookieStore.put('EmployeecompanyId', $scope.employeeVo.companyId);
    	     
    	   	     
    	     
    	     $cookieStore.put('employeeVo.customerName',  $('#customerName option:selected').html());
    	     $cookieStore.put('employeeVo.companyName', $('#companyName option:selected').html());
    	     $cookieStore.put('employeeVo.jobType', $('#jobType option:selected').html());
    	     $cookieStore.put('employeeVo.jobStatus', $('#jobStatus option:selected').html());
    	     $cookieStore.put('employeeVo.employeeCountryName',  $('#countryName option:selected').html());
    	     $cookieStore.put('employeeVo.employeeCode', $('#employeeCode').val());
    	     
    	     $scope.getData('EmployeeController/getemployeeGridData.json', {customerId :$scope.employeeVo.customerId,companyId:$scope.employeeVo.companyId,jobTypeId:$scope.employeeVo.jobTypeId,jobStatusId:$scope.employeeVo.jobStatusId,employeeCountryId:$scope.employeeVo.employeeCountryId,employeeCode:$('#employeeCode').val()}, 'getEmployeeGridData') 
    		    
		
    	
    }
    
    
                  
    }]);


/********************* Registration  *****************************/

employeeControllers.directive("fileread", [function () {
    return {
        scope: {
            fileread: "="
        },
        link: function (scope, element, attributes) {
            element.bind("change", function (changeEvent) {
            //	alert('abc');
                var reader = new FileReader();
                reader.onload = function (loadEvent) {
                    scope.$apply(function () {
                        scope.fileread = loadEvent.target.result;
                       // alert(loadEvent.target.result);
                    });
                }
               
                reader.readAsDataURL(changeEvent.target.files[0]);
            });
        }
    }
}]).controller("EmployeeRegistrationCtrl",  ['$scope', '$rootScope', '$http', '$resource','$location','$cookieStore','$filter',function($scope, $rootScope, $http,$resource,$location,$cookieStore,$filter) {  
    $.material.init();
	$scope.label = "Add";	
    
    $scope.blood_group_list = [{ "id":"A+", "name":"A+"}, { "id":"A-", "name":"A-"},
	                            { "id":"AB+", "name":"AB+"}, { "id":"AB-", "name":"AB-"},
	                            { "id":"A1+", "name":"A1+"}, { "id":"A1-", "name":"A2-"},
	                            { "id":"A2+", "name":"A2+"}, { "id":"A1-", "name":"A2-"},
	                            { "id":"A1B+", "name":"A1B+"}, { "id":"A1B-", "name":"A1B-"},
	                            { "id":"A2B+", "name":"A2B+"}, { "id":"A2B-", "name":"A2B-"},
	                            { "id":"B+", "name":"B+"}, { "id":"B-", "name":"B-"},
	                            { "id":"O+", "name":"O+"}, { "id":"O-", "name":"O-"},
	                            { "id":"U", "name":"U"}, { "id":"Other", "name":"Other"}
	                            ];
    
    $scope.marital_status_list = [{ "id":"Single", "name":"Single"},
  	                            { "id":"Married", "name":"Married"},
  	                            { "id":"Unmarried", "name":"Unmarried"}, 
  	                            { "id":"Widowed", "name":"Widowed"}, 
  	                            { "id":"Divorced", "name":"Divorced"}, 
  	                            { "id":"Unknown", "name":"Unknown"}
  	                            ];
	/*$('#txt_effectivedate, #transactionDate , #startDate, #dateOfBirth').bootstrapMaterialDatePicker({
        time : false,
        Button : true,
        format : "DD/MM/YYYY",
        clearButton: true
    });*/
    $('#transactionDate , #startDate').datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown'	    	  
	    });
    $scope.readonly = false;
    $scope.updateBtn = false;
    $scope.saveBtn = true;
    $scope.currentHistoryBtn = false;
    $scope.resetBtn = true;
    $scope.cancelBtn = false;
    $scope.addrHistory = false;
    $scope.transactionList = false;   
    $scope.employeeVo = new Object();
    
    
    
    $scope.employeeVo.customerName =  $cookieStore.get('employeeVo.customerName');
    $scope.employeeVo.companyName = $cookieStore.get('employeeVo.companyName');
    $scope.employeeVo.jobType =  $cookieStore.get('employeeVo.jobType');
    $scope.employeeVo.jobStatus = $cookieStore.get('employeeVo.jobStatus');
    $scope.employeeVo.employeeCountryName = $cookieStore.get('employeeVo.employeeCountryName');
    
    $scope.employeeVo.employeeCode = $cookieStore.get('employeeVo.employeeCode');
    $scope.employeeVo.customerId= $cookieStore.get('EmployeecustomerId'); 
    $scope.employeeVo.companyId	= $cookieStore.get('EmployeecompanyId');
    $scope.employeeVo.jobTypeId	=  $cookieStore.get('EmployeejobTypeId');
    $scope.employeeVo.jobStatusId = $cookieStore.get('EmployeejobStatusId'); 
    $scope.employeeVo.employeeCountryId	=$cookieStore.get('employeeCountryId');	   
	//	alert($cookieStore.get('customerId')+"::"+$scope.employeeVo.jobStatusId+"::"+$scope.employeeVo.employeeCountryId);	     
    $scope.employeeVo.isActive = 'Y';
    $scope.transactionDate = $filter('date')(new Date(),"dd/MM/yyyy");
    
    
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
    $scope.years = new Object();
    $scope.months = new Object();
    
    $scope.dateOfBirthValidaton = function($event){
		if($('#dateOfBirth').val() != '' || $event != ''){			

			var d1 = new Date(moment(new Date(), 'DD/MM/YYYY').format('YYYY-MM-DD'));
			var d2 = new Date(moment($('#dateOfBirth').val(), 'DD/MM/YYYY').format('YYYY-MM-DD'));		
			 
			var dy,dm,dd;
			 
		 	dy = d1.getYear()  - d2.getYear();
		 	dm = d1.getMonth() - d2.getMonth();
			dd = d1.getDate()  - d2.getDate();
			    
			if (dd < 0) { dm -= 1; dd += 30; }
			if (dm < 0) { dy -= 1; dm += 12; }

			 $scope.years= dy;
			 $scope.months = dm;		
		 }
		 
	};  
   
    $('#transactionDate').val($filter('date')(new Date(),'dd/MM/yyyy'));
  //  $scope.employeeVo.fileName = 'base64,/9j/4AAQSkZJRgABAgEAYABgAAD/7gAOQWRvYmUAZAAAAAAB/+EN/kV4aWYAAE1NACoAAAAIAAgBMgACAAAAFAAAAG4BOwACAAAACwAAAIJHRgADAAAAAQAFAABHSQADAAAAAQBYAACCmAACAAAAFgAAAI2cnQABAAAAFgAAAADqHAAHAAAHogAAAACHaQAEAAAAAQAAAKMAAAENMjAwOTowMzoxMiAxMzo0ODozMgBUb20gQWxwaGluAE1pY3Jvc29mdCBDb3Jwb3JhdGlvbgAABZADAAIAAAAUAAAA5ZAEAAIAAAAUAAAA+ZKRAAIAAAADNzcAAJKSAAIAAAADNzcAAOocAAcAAAe0AAAAAAAAAAAyMDA4OjAyOjExIDExOjMyOjUxADIwMDg6MDI6MTEgMTE6MzI6NTEAAAUBAwADAAAAAQAGAAABGgAFAAAAAQAAAU8BGwAFAAAAAQAAAVcCAQAEAAAAAQAAAV8CAgAEAAAAAQAADJcAAAAAAAAASAAAAAEAAABIAAAAAf/Y/+AAEEpGSUYAAQEAAAEAAQAA/9sAQwAQCwwODAoQDg0OEhEQExgoGhgWFhgxIyUdKDozPTw5Mzg3QEhcTkBEV0U3OFBtUVdfYmdoZz5NcXlwZHhcZWdj/9sAQwEREhIYFRgvGhovY0I4QmNjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2Nj/8AAEQgAeACgAwEiAAIRAQMRAf/EAB8AAAEFAQEBAQEBAAAAAAAAAAABAgMEBQYHCAkKC//EALUQAAIBAwMCBAMFBQQEAAABfQECAwAEEQUSITFBBhNRYQcicRQygZGhCCNCscEVUtHwJDNicoIJChYXGBkaJSYnKCkqNDU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6g4SFhoeIiYqSk5SVlpeY...8WmIAKutA0mhuOVuwB/wAPdGkxpGQOq08+msVPqpBdpWWZD42Qspj1ktpDG1mte30J97DkgAZavW9Iz0qGnjrayBInhg1RPqMhSIHQxkjCsWRYrpxa9wQfagkZ+Zr1VFWtTnV/q/Z0ocHRJUV61Uhnp8PHplngWfRPKWdY2WJtBE0ckrXtblPenJA0Yr5dXDL8QNPs/wA3S1G8KHAVuulx6SVVU88E7UkhpVGNAaNzFJEAy1DkgqWDAj8e2+7gpoKU62aLSpzXpLtTtkKygevkWroxU6GoYQyDTIC6ColTiSYxX5X8i3vfmcHh1UEH4T5/l/k6g7h+xpWFBjMlPJQxRyMlJPeJoZ3kCPCUQhHKhQVY/X8+9AVOqnXia1WuOnHZ+XkoI61pMgsCBfE9OoVpakLIEcAFSpcK3pYfQfX3snNKVU9USurS5+fS2oajHZrIxPLD5WcOlNSFnR6GMq4WomjUBWhCjU5Xge74Ykjz6slKj06WmwqLK02SbNY/rh970+NqG00sb/tvPTMJ/NRNUMIZqqMEeNRqfkaRq96oo7uAH516spo2c9Bv2n2dujtDcOVzuUwGDwlRWTQ0uTxmIxkePgqqiiX7daythVmaHKkR2nZAvkcM1vU3tP2+JiladXZtZBpjoN8ZWpTMyyRvEBeUxwMRMk8N40U+ksWLckfge69zZr3D/Vx61XPHpzj3Tm8b5VgqWpYK6VJ5Hj/bmm8LaxUFY1BXS/BYe3QwA/p04dVYD4xTj/q+z8unjDZGjlyElKfLNVZMzSTQvHAcdMfC05nVSYmjCeq4te5uD72ZFQanIB/2etqe4aRVa59Ok8+TpXirEgpnSaByBrQS02tZbaEmWRHiS3Klgb/T3p9LHiKdbUErnh03RT1uSlljVqanRoSrIKrTH43f1O/DuVZgARf22NI4Lw68TitPPrD/AAuWrpJKdKnHwBFZVPik8tTMZEBCsEbSeRYem493IHAfiHWq6vKlOv/Z';
    
    $scope.getData = function (url, data, type,buttonDisable) {
        var req = {
            method: 'POST',
            url:ROOTURL+url,
            headers: {
                'Content-Type': 'application/json'
            },
            data:data
        }
        $http(req).then(function (response) {
        	// alert(JSON.stringify(response.data));
        	if(type == 'buttonsAction'){
        		$scope.buttonArray = response.data.screenButtonNamesArray;
        	} else if(type == 'customerChange'){
        		$scope.companyList = response.data.companyList;
        		 $scope.list_countries  = response.data.countryList;
				//$scope.list_cities = "";
        	}else if(type == 'getMasterDataAsDropDown'){
        		//alert(JSON.stringify(response.data));
        		$scope.locationList = response.data.locationList;
        		$scope.jobCodesList = response.data.jobCodesList;
        		$scope.countryList = response.data.countryList;
        		$scope.managerList = response.data.managerList;
        		$scope.companyProfile = response.data.companyProfile[0];
        		//$scope.departmentList = response.data.departmentList;
        		// for button views
  			  	if($scope.buttonArray == undefined || $scope.buttonArray == '')
  			  		$scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Employee'}, 'buttonsAction');
        	}/*else if(type == 'locationChnage'){
        		$scope.departmentList = response.data.departmentList;
        	}*/else if(type == 'countryChnage'){
        		$scope.statesList = response.data;
        	}else if(type == 'saveEmployeeDetails'){

        			if(response.data.id > 0){
        				 $scope.Messager('success', 'success', 'Employee added successfully',buttonDisable);
        				 $location.path('/EmployeeInformation/'+response.data.id);
        			}else{
        				 $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
        	        	//	alert("Company Address Saved Successfully..."+ response.data);	
        			}
        		
        	}else if(type == 'locationDrop'){       

                $scope.list_locations = response.data;                 
            }else if(type == 'sectionDropDown'){
                $scope.list_sections = response.data.sectionList;     
                     
            }else if(type == 'plantsList'){
            	$scope.plantsList = response.data;
            }else if(type == 'departmentsList'){
            	$scope.departmentsList = response.data.departmentList;
            }else if(type == 'sectionsList'){
            	//$scope.departmentsList = response.data.departmentsList;
            	$scope.sectionesList = response.data.sectionsList;
            }else if(type == 'workAreasList'){
            	$scope.workAreasList = response.data;
            }
           
                
          },
        function () {
        	  $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
        	  if(type == 'countryChnage'){
        		  $scope.statesList = "";
				//  $scope.list_cities = "";
          	}
          
          });
    	}   
    
    $scope.getData('EmployeeController/getEmployeeDropDownMasterData.json', {customerId:($cookieStore.get('customerId') != null && $cookieStore.get('customerId') != "") ? $cookieStore.get('customerId') : $cookieStore.get('EmployeecustomerId'),companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $cookieStore.get('EmployeecompanyId')}, 'getMasterDataAsDropDown')
   
  
    
    /*$scope.locationChnage= function(){
    	if($scope.employeeVo.locationId != null && $scope.employeeVo.locationId != ""){
    	$scope.getData('EmployeeController/getDepartmentListByCompanyId.json', { customerId: $scope.employeeVo.customerId }, 'locationChange');
    	}
    }*/
    
    $scope.fun_Countrychange = function() {
    	if ($scope.employeeVo.country != "" && $scope.employeeVo.country > 0) {
    	 $scope.getData('CommonController/statesListByCountryId.json', {countryId : $scope.employeeVo.country}, 'countryChnage')    	 
    	}else{
    		$scope.statesList = "";
    	}
    	 
    }  
    
    
    $scope.locationChange = function(){
	   	if($scope.employeeVo.locationId != undefined && $scope.employeeVo.locationId != null && $scope.employeeVo.locationId != ''){
	   		$scope.departmentsList = [];
	   		$scope.sectionesList = [];
	   		$scope.workAreasList = [];
	   		
	   		$scope.getData('jobAllocationByVendorController/getPlantsList.json', {locationId:$scope.employeeVo.locationId}, 'plantsList');
	   	}else{
	   		$scope.plantsList =[];
	   		$scope.departmentsList = [];
	   		$scope.sectionesList = [];
	   		$scope.workAreasList = [];
	   	}
	   }
	   
	   $scope.plantChange = function(){
	   	if($scope.employeeVo.locationId != undefined && $scope.employeeVo.locationId != null && $scope.employeeVo.locationId != '' && $scope.employeeVo.plantId != undefined && $scope.employeeVo.plantId != null && $scope.employeeVo.plantId != ''){
	   		$scope.sectionesList = [];
	   		$scope.workAreasList = [];
	   		$scope.getData('reportController/getDepartmentsByLocationAndPlantId.json', {customerId:$cookieStore.get('customerId'), companyId: $cookieStore.get('companyId'), locationId:$scope.employeeVo.locationId,plantId:$scope.employeeVo.plantId}, 'departmentsList');
	   	//	$scope.getPostData('associatingDepartmentToLocationPlantController/getDepartMentDetailsByLocationAndPlantId.json', {customerId:$cookieStore.get('customerId'), companyId: $cookieStore.get('companyId'), locationId:$scope.locationId,plantId:$scope.plantId}, 'departmentsList');
	   	}else{
	   		$scope.departmentsList = [];
	   		$scope.sectionesList = [];
	   		$scope.workAreasList = [];
	   	}
	   }
	  
	   $scope.departmentChange = function(){
	   	if($scope.employeeVo.locationId != undefined && $scope.employeeVo.locationId != null && $scope.employeeVo.locationId != '' && $scope.employeeVo.plantId != undefined && $scope.employeeVo.plantId != null && $scope.employeeVo.plantId != '' && $scope.employeeVo.departmentId != undefined && $scope.employeeVo.departmentId != null && $scope.employeeVo.departmentId != ''){
	   		$scope.workAreasList = [];	   		
	   		$scope.getData('associatingDepartmentToLocationPlantController/getSectionDetailsByLocationAndPlantAndDeptId.json', {customerId:$cookieStore.get('customerId'), companyId: $cookieStore.get('companyId'), locationId:$scope.employeeVo.locationId,plantId:$scope.employeeVo.plantId, departmentId : $scope.employeeVo.departmentId}, 'sectionsList');
	   	}else{
	   		$scope.sectionesList = []; 
	   		$scope.workAreasList = [];
	   	}
	   }
	   

	   
	   $scope.sectionChange = function(){
	   	if($scope.employeeVo.locationId != undefined && $scope.employeeVo.locationId != null && $scope.employeeVo.locationId != '' && $scope.employeeVo.plantId != undefined && $scope.employeeVo.plantId != null && $scope.employeeVo.plantId != '' && $scope.employeeVo.sectionId != undefined && $scope.employeeVo.sectionId != null && $scope.employeeVo.sectionId != '' && $scope.employeeVo.departmentId != undefined && $scope.employeeVo.departmentId != null && $scope.employeeVo.departmentId != ''){
	   			$scope.getData('jobAllocationByVendorController/getAllWorkAreasBySectionesAndLocationAndPlantAndDept.json', {customerId:$cookieStore.get('customerId'), companyId: $cookieStore.get('companyId'), locationId:$scope.employeeVo.locationId,plantId:$scope.employeeVo.plantId,sectionId:$scope.employeeVo.sectionId,departmentId:$scope.employeeVo.departmentId}, 'workAreasList');
	   	}else{    	
	   		$scope.workAreasList = [];
	   	}
	   }
    
    // Save Employee details
    $scope.save = function($event){
    	var regex = new RegExp("[A-Za-z]{3}(P|p|C|c|H|h|F|f|A|a|T|t|B|b|L|l|J|j|G|j)[A-Za-z]{1}[0-9]{4}[A-Za-z]{1}$");
    	//alert($('#employeeEntryForm').valid());
    	if($('#employeeEntryForm').valid() ){
    		if(!moment($('#dateOfBirth').val(), 'DD/MM/YYYY', true).isValid()) {
    			$scope.Messager('error', 'Error', 'Please enter valid Date of birth');
    		}else if($scope.employeeVo.panNumber != undefined && $scope.employeeVo.panNumber != null && $scope.employeeVo.panNumber !='' && !(regex.test($scope.employeeVo.panNumber))){
    	  		$scope.Messager('error', 'Error', 'Please enter valid pan number');
    	  	}else if($scope.years < $scope.companyProfile.minAge) {
    	  		$scope.Messager('error', 'Error', 'Employee is below minimum age limit. Please enter valid date of birth');
    	  	}else if($scope.years > $scope.companyProfile.maxAge) {
    	  		$scope.Messager('error', 'Error', 'Employee is over maximum age limit. Please enter valid date of birth');
    	  	}else{
    			$scope.employeeVo.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
    		
	    		if($('#startDate').val() != '' && $('#startDate').val() != undefined){
	    			$scope.employeeVo.startDate = moment($('#startDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
		        }
		        
		        if($('#dateOfBirth').val() != '' && $('#dateOfBirth').val() != undefined){
		        	$scope.employeeVo.dateOfBirth = moment($('#dateOfBirth').val(),'DD/MM/YYYY').format('YYYY-MM-DD') ; 
		        }
		        
		        if($('#startDate').val() != '' && $('#startDate').val() != undefined && $('#dateOfBirth').val() != '' && $('#dateOfBirth').val() != undefined){
		        	if(!moment($scope.employeeVo.startDate).isSameOrAfter($scope.employeeVo.dateOfBirth)){
		        		$scope.Messager('error', 'Error', 'Start Date should not be Less than Date Of Birth');
		        	return;
		        	}
			    }
		        
			    $scope.employeeVo.employeeId = 0;
			    $scope.employeeVo.uniqueId = 0;
	         
	        	$scope.getData('EmployeeController/saveEmployeeDetails.json', angular.toJson($scope.employeeVo), 'saveEmployeeDetails',angular.element($event.currentTarget))
    	  	}
		    /*}else{
				$scope.Messager('error', 'Error', 'Enter Address',angular.element($event.currentTarget));
			}	*/
        /*$scope.employeeVo.transactionDate =  $filter('date')( $scope.employeeVo.transactionDate, 'dd/MM/yyyy')
		$scope.employeeVo.startDate =  $filter('date')( $scope.employeeVo.startDate, 'dd/MM/yyyy')
		$scope.employeeVo.dateOfBirth =  $filter('date')( $scope.employeeVo.dateOfBirth, 'dd/MM/yyyy')*/
		}        
    }


    
    
    
    $scope.saveAddress = function(){  
    	
    	if($('#employeePopupForm').valid() ) {
    		var value = "";
    		if( $scope.employeeVo.address1 != undefined &&  $scope.employeeVo.address1 != '')
    			value += $scope.employeeVo.address1;
    		if( $scope.employeeVo.address2 != undefined &&  $scope.employeeVo.address2 != '')
    			value += ", "+$scope.employeeVo.address2;
    		if( $scope.employeeVo.address3 != undefined &&  $scope.employeeVo.address3 != '')
    			value += ", "+$scope.employeeVo.address3;
    		if( $('#countryIds option:selected').html() != 'Select' &&  $('#countryIds option:selected').html() != '')
    			value += ", "+$('#countryIds option:selected').html();
    		if( $('#state option:selected').html() != 'Select' &&  $('#state option:selected').html() != '')
    			value += ", "+$('#state option:selected').html();
    		if($scope.employeeVo.pincode != undefined &&  $scope.employeeVo.pincode != '')
    			value += ", "+$scope.employeeVo.pincode;        	    
    		
    		value += ". ";
    		$scope.label = "View/Edit";
    		$scope.EmpAdders = value;
    		$('#myModal').modal('hide');
    	}
    	
    	
    	
    	
       // $scope.EmpAdders = $scope.employeeVo.address1 + ',' + ($scope.employeeVo.address2 !=undefined ?$scope.employeeVo.address2 : "") + ',' + ($scope.employeeVo.address3 !=undefined ? $scope.employeeVo.address3 : "" )+','+ ($('#countryIds option:selected').html() !=undefined ? $('#countryIds option:selected').html() : "" )+','+ ($('#state option:selected').html() != undefined  ? $('#state option:selected').html() : "" ) +','+ ($scope.employeeVo.city != undefined ? $scope.employeeVo.city : "" )+','+ ($scope.employeeVo.pincode != undefined ? $scope.employeeVo.pincode : "");  
    }
    
    $scope.clearFun = function(){
    	$scope.employeeVo = new Object();
    	$scope.transactionDate = $filter('date')(new Date(),"dd/MM/yyyy");
    	$scope.startDate= '';
    	$scope.dateOfBirth= '';

    }
                  
    }]);



/************************   EDIT ***********************/


employeeControllers.controller("EmployeeEditCtrl",  ['$scope', '$rootScope', '$http', '$resource','$location','$routeParams','$cookieStore','$filter',function($scope, $rootScope, $http,$resource,$location,$routeParams,$cookieStore,$filter) {  
    $.material.init();
			
    $scope.years = '';
    $scope.months = '';
    
    $scope.blood_group_list = [{ "id":"A+", "name":"A+"}, { "id":"A-", "name":"A-"},
	                            { "id":"AB+", "name":"AB+"}, { "id":"AB-", "name":"AB-"},
	                            { "id":"A1+", "name":"A1+"}, { "id":"A1-", "name":"A2-"},
	                            { "id":"A2+", "name":"A2+"}, { "id":"A1-", "name":"A2-"},
	                            { "id":"A1B+", "name":"A1B+"}, { "id":"A1B-", "name":"A1B-"},
	                            { "id":"A2B+", "name":"A2B+"}, { "id":"A2B-", "name":"A2B-"},
	                            { "id":"B+", "name":"B+"}, { "id":"B-", "name":"B-"},
	                            { "id":"O+", "name":"O+"}, { "id":"O-", "name":"O-"},
	                            { "id":"U", "name":"U"}, { "id":"Other", "name":"Other"}
	                            ];
   
   $scope.marital_status_list = [{ "id":"Single", "name":"Single"},
 	                            { "id":"Married", "name":"Married"},
 	                            { "id":"Unmarried", "name":"Unmarried"}, 
 	                            { "id":"Widowed", "name":"Widowed"}, 
 	                            { "id":"Divorced", "name":"Divorced"}, 
 	                            { "id":"Unknown", "name":"Unknown"}
 	                            ];
   
   
	/*$('#txt_effectivedate').bootstrapMaterialDatePicker({
        time : false,
        Button : true,
        format : "DD/MM/YYYY",
        clearButton: true
    });*/
    
    $('#txt_effectivedate, #transactionDate , #startDate').datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown'	    	  
	    });
	
    $scope.readonly = true;
    $scope.updateBtn = true;
    $scope.saveBtn = false;
    $scope.currentHistoryBtn = false;
    $scope.resetBtn = false;
    $scope.cancelBtn = false;
    $scope.addrHistory = true;
    $scope.transactionList = false;     
  
   
    
    $scope.employeeVo = new Object();
    
    
    
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
    
    $scope.dateOfBirthValidaton = function($event){
		if($('#dateOfBirth').val() != '' || $event != ''){			

			var d1 = new Date(moment(new Date(), 'DD/MM/YYYY').format('YYYY-MM-DD'));
			var d2 = new Date(moment($('#dateOfBirth').val(), 'DD/MM/YYYY').format('YYYY-MM-DD'));		
			 
			var dy,dm,dd;
			 
		 	dy = d1.getYear()  - d2.getYear();
		 	dm = d1.getMonth() - d2.getMonth();
			dd = d1.getDate()  - d2.getDate();
			    
			if (dd < 0) { dm -= 1; dd += 30; }
			if (dm < 0) { dy -= 1; dm += 12; }

			 $scope.years= dy;
			 $scope.months = dm;		
		 }
		 
	};  
	
    $scope.getData = function (url, data, type,buttonDisable) {
        var req = {
            method: 'POST',
            url:ROOTURL+url,
            headers: {
                'Content-Type': 'application/json'
            },
            data:data
        }
        $http(req).then(function (response) {       	 
        	if(type == 'buttonsAction'){
        		$scope.buttonArray = response.data.screenButtonNamesArray;
        	}else if(type == 'getEmployeeDetailsById'){        		
        		
        		
        		if(response.data.emplyeeList[0] != undefined){
        			$scope.employeeVo = response.data.emplyeeList[0];
        			$scope.transactionDate =  $filter('date')(response.data.emplyeeList[0].transactionDate, 'dd/MM/yyyy');
        			$scope.startDate =  $filter('date')(response.data.emplyeeList[0].startDate, 'dd/MM/yyyy');
        			$scope.dateOfBirth =  $filter('date')(response.data.emplyeeList[0].dateOfBirth, 'dd/MM/yyyy');
        			if(response.data.emplyeeList[0].dateOfBirth != null && response.data.emplyeeList[0].dateOfBirth != ""){
        				$scope.dateOfBirthValidaton(response.data.emplyeeList[0].dateOfBirth);
        			}
        			//alert(response.data.locationList);
        			$scope.locationList = response.data.locationList;
        			$scope.jobCodesList = response.data.jobCodesList;
        			$scope.countryList = response.data.countryList;
        			$scope.managerList = response.data.managerList;
        			//$scope.departmentList = response.data.departmentList;
        			
        			if($scope.employeeVo.pincode == 0){
        				$scope.employeeVo.pincode = null;
        			}
        			var value = "";
        			if( $scope.employeeVo.address1 != undefined &&  $scope.employeeVo.address1 != '' && $scope.employeeVo.address1 != 'null')
        				value += $scope.employeeVo.address1;
        			if( $scope.employeeVo.address2 != undefined &&  $scope.employeeVo.address2 != '' && $scope.employeeVo.address2 != 'null')
        				value += ", "+$scope.employeeVo.address2;
        			if( $scope.employeeVo.address3 != undefined &&  $scope.employeeVo.address3 != '' && $scope.employeeVo.address3 != 'null')
        				value += ", "+$scope.employeeVo.address3;
        			if( $('#countryIds option:selected').html() != 'Select' &&  $('#countryIds option:selected').html() != '' &&  $('#countryIds option:selected').html() != 'null')
        				value += ", "+$('#countryIds option:selected').html();
        			if( $('#state option:selected').html() != 'Select' &&  $('#state option:selected').html() != '' && $('#state option:selected').html() != 'null')
        				value += ", "+$('#state option:selected').html();
        			if($scope.employeeVo.pincode != undefined &&  $scope.employeeVo.pincode != '' && $scope.employeeVo.pincode != 0)
        				value += ", "+$scope.employeeVo.pincode;
        	    	
        			if(value !='null' && value != ''){

       				 $scope.label = "View/Edit";
       				 value += ". ";
        			}else{
        				$scope.label = "Add";
        			}
        	    	
        			$scope.EmpAdders = value;
        			$scope.companyProfile = response.data.companyProfile[0];
        			
        			//$scope.EmpAdders = $scope.employeeVo.address1 + ',' + $scope.employeeVo.address2 + ',' + $scope.employeeVo.address3 +','+$scope.employeeVo.countryName+','+$scope.employeeVo.stateName+','+$scope.employeeVo.city+','+$scope.employeeVo.pincode;
        			/*$scope.employeeVo.customerName =  $cookieStore.get('employeeVo.customerName');
        			$scope.employeeVo.companyName = $cookieStore.get('employeeVo.companyName');
        			$scope.employeeVo.jobType =  $cookieStore.get('employeeVo.jobType');
        			$scope.employeeVo.jobStatus = $cookieStore.get('employeeVo.jobStatus');
        			$scope.employeeVo.countryName = $cookieStore.get('employeeVo.countryName');
        			$scope.employeeVo.employeeCode = $cookieStore.get('employeeVo.employeeCode');*/
        			$scope.fun_Countrychange();
        			$scope.locationChange();
        	    	 $scope.plantChange();
        	    	 $scope.departmentChange();
        	    	 $scope.sectionChange();
        			//$scope.locationChnage();
        		}
        		// for button views
  			  	if($scope.buttonArray == undefined || $scope.buttonArray == '')
  			  		$scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Employee'}, 'buttonsAction');
        		//; 
                
        	}/*else if(type == 'locationChange'){
        		$scope.departmentList = response.data.departmentList;     
        	}*/else if(type == 'countryChnage'){
        		$scope.statesList = response.data;
        	}else if(type == 'getEmployeeDetailsByIdForCancel'){
            	$scope.employeeVo = response.data.emplyeeList[0];
    			$scope.transactionDate =  $filter('date')(response.data.emplyeeList[0].transactionDate, 'dd/MM/yyyy');
    			$scope.startDate =  $filter('date')(response.data.emplyeeList[0].startDate, 'dd/MM/yyyy');
    			$scope.dateOfBirth =  $filter('date')(response.data.emplyeeList[0].dateOfBirth, 'dd/MM/yyyy');
        	}else if(type == 'saveEmployeeDetails'){
        		/*if(response.data == ""){
   				 $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
	   			}else{
	   				if($scope.saveBtn == true && $routeParams.id >0){
	   					//alert(JSON.stringify(response.data)); 
	                	$location.path('/EmployeeInformation/'+response.data.employeeId);
	                }  
	   				 $scope.Messager('success', 'success', 'Address Details Saved Successfully',buttonDisable);
	   	        	//	alert("Company Address Saved Successfully..."+ response.data);	
	   			}*/
        		if(response.data.id > 0){
        			$scope.Messager('success', 'success', 'Employee saved successfully',buttonDisable);
   				 	$location.path('/EmployeeInformation/'+response.data.id);
        		}else{
        			$scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
   	        	//	alert("Company Address Saved Successfully..."+ response.data);	
        		}
        	}else if(type == 'saveHistory'){
	       		if(response.data.id > 0){
	       			$scope.Messager('success', 'success', 'Employee saved successfully',buttonDisable);
	       		}else{
	       			$scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
	       		}
        	}else if(type == 'getTransactionDates'){
        		//alert(response.data[0].id);
        		$scope.list_transactionDatesList = response.data;
        		 $scope.transactionModel= parseInt($routeParams.id);
        		//$scope.employeeVo.employeeId = response.data[0].id;
        		$scope.getData('EmployeeController/getEmployeeDetailsByEmployeeId.json', {employeeId : parseInt($routeParams.id),customerId:$cookieStore.get('customerId'),companyId:$cookieStore.get('companyId')}, 'getEmployeeDetailsById')
        	}else if(type == 'locationDrop'){       

                $scope.list_locations = response.data;                 
            }else if(type == 'sectionDropDown'){
                $scope.list_sections = response.data.sectionList;     
                     
            }else if(type == 'plantsList'){
            	$scope.plantsList = response.data;
            }else if(type == 'departmentsList'){
            	$scope.departmentsList = response.data.departmentList;
            }else if(type == 'sectionsList'){
            	//$scope.departmentsList = response.data.departmentsList;
            	$scope.sectionesList = response.data.sectionsList;
            }else if(type == 'workAreasList'){
            	$scope.workAreasList = response.data;
            }
        },
        function () {
        	 $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
        	  if(type == 'countryChnage'){
        		  $scope.list_states = "";			
          	}
          
          });
    	}  
    	//alert($cookieStore.get('customerId')+"::CustIdCompId"+$cookieStore.get('companyId'));
    
    	$scope.getData('EmployeeController/getEmployeeDetailsByEmployeeId.json', {employeeId : $routeParams.id,customerId:$cookieStore.get('customerId'),companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $cookieStore.get('EmployeecompanyId')}, 'getEmployeeDetailsById') 
   
    
    
     $scope.fun_Countrychange = function() {    
    		
    	if ($scope.employeeVo.country != "" && $scope.employeeVo.country > 0) {    	
    	 $scope.getData('CommonController/statesListByCountryId.json', {countryId : $scope.employeeVo.country}, 'countryChnage')    	 
    	}else{
    		$scope.statesList = "";
    	}
    	 
    }  
    
    	 $scope.locationChange = function(){
    		   	if($scope.employeeVo.locationId != undefined && $scope.employeeVo.locationId != null && $scope.employeeVo.locationId != ''){
    		   		$scope.departmentsList = [];
    		   		$scope.sectionesList = [];
    		   		$scope.workAreasList = [];
    		   		
    		   		$scope.getData('jobAllocationByVendorController/getPlantsList.json', {locationId:$scope.employeeVo.locationId}, 'plantsList');
    		   	}else{
    		   		$scope.plantsList =[];
    		   		$scope.departmentsList = [];
    		   		$scope.sectionesList = [];
    		   		$scope.workAreasList = [];
    		   	}
    		   }
    		   
    		   $scope.plantChange = function(){
    		   	if($scope.employeeVo.locationId != undefined && $scope.employeeVo.locationId != null && $scope.employeeVo.locationId != '' && $scope.employeeVo.plantId != undefined && $scope.employeeVo.plantId != null && $scope.employeeVo.plantId != ''){
    		   		$scope.sectionesList = [];
    		   		$scope.workAreasList = [];
    		   		$scope.getData('reportController/getDepartmentsByLocationAndPlantId.json', {customerId:$cookieStore.get('customerId'), companyId: $cookieStore.get('companyId'), locationId:$scope.employeeVo.locationId,plantId:$scope.employeeVo.plantId}, 'departmentsList');
    		   	//	$scope.getPostData('associatingDepartmentToLocationPlantController/getDepartMentDetailsByLocationAndPlantId.json', {customerId:$cookieStore.get('customerId'), companyId: $cookieStore.get('companyId'), locationId:$scope.locationId,plantId:$scope.plantId}, 'departmentsList');
    		   	}else{
    		   		$scope.departmentsList = [];
    		   		$scope.sectionesList = [];
    		   		$scope.workAreasList = [];
    		   	}
    		   }
    		  
    		   $scope.departmentChange = function(){
    		   	if($scope.employeeVo.locationId != undefined && $scope.employeeVo.locationId != null && $scope.employeeVo.locationId != '' && $scope.employeeVo.plantId != undefined && $scope.employeeVo.plantId != null && $scope.employeeVo.plantId != '' && $scope.employeeVo.departmentId != undefined && $scope.employeeVo.departmentId != null && $scope.employeeVo.departmentId != ''){
    		   		$scope.workAreasList = [];	   		
    		   		$scope.getData('associatingDepartmentToLocationPlantController/getSectionDetailsByLocationAndPlantAndDeptId.json', {customerId:$cookieStore.get('customerId'), companyId: $cookieStore.get('companyId'), locationId:$scope.employeeVo.locationId,plantId:$scope.employeeVo.plantId, departmentId : $scope.employeeVo.departmentId}, 'sectionsList');
    		   	}else{
    		   		$scope.sectionesList = []; 
    		   		$scope.workAreasList = [];
    		   	}
    		   }
    		   

    		   
    		   $scope.sectionChange = function(){
    		   	if($scope.employeeVo.locationId != undefined && $scope.employeeVo.locationId != null && $scope.employeeVo.locationId != '' && $scope.employeeVo.plantId != undefined && $scope.employeeVo.plantId != null && $scope.employeeVo.plantId != '' && $scope.employeeVo.sectionId != undefined && $scope.employeeVo.sectionId != null && $scope.employeeVo.sectionId != '' && $scope.employeeVo.departmentId != undefined && $scope.employeeVo.departmentId != null && $scope.employeeVo.departmentId != ''){
    		   			$scope.getData('jobAllocationByVendorController/getAllWorkAreasBySectionesAndLocationAndPlantAndDept.json', {customerId:$cookieStore.get('customerId'), companyId: $cookieStore.get('companyId'), locationId:$scope.employeeVo.locationId,plantId:$scope.employeeVo.plantId,sectionId:$scope.employeeVo.sectionId,departmentId:$scope.employeeVo.departmentId}, 'workAreasList');
    		   	}else{    	
    		   		$scope.workAreasList = [];
    		   	}
    		   }
    
    /* $scope.locationChnage= function(){
    	 if($scope.employeeVo.locationId != undefined && $scope.employeeVo.locationId != ''){
    	$scope.getData('EmployeeController/getDepartmentListByLocationId.json', { locationId: $scope.employeeVo.locationId }, 'locationChange');
    	 }
     }*/
    
     
     
     // Save Customer adress details
     $scope.save = function($event){	
    	 var regex = new RegExp("[A-Za-z]{3}(P|p|C|c|H|h|F|f|A|a|T|t|B|b|L|l|J|j|G|j)[A-Za-z]{1}[0-9]{4}[A-Za-z]{1}$");
     	if($('#employeeEntryForm').valid()){
     		if(!moment($('#dateOfBirth').val(), 'DD/MM/YYYY', true).isValid()) {
    			$scope.Messager('error', 'Error', 'Please enter valid Date of birth');
    		}else if($scope.employeeVo.panNumber != undefined && $scope.employeeVo.panNumber != null && $scope.employeeVo.panNumber !='' && !(regex.test($scope.employeeVo.panNumber))){
    	  		$scope.Messager('error', 'Error', 'Please enter valid pan number');
    	  	}else if($scope.years < $scope.companyProfile.minAge) {
    	  		$scope.Messager('error', 'Error', 'Employee is below minimum age limit. Please enter valid date of birth');
    	  	}else if($scope.years > $scope.companyProfile.maxAge) {
    	  		$scope.Messager('error', 'Error', 'Employee is over maximum age limit. Please enter valid date of birth');
    	  	}else{
    			$scope.employeeVo.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
    		
	    		if($('#startDate').val() != '' && $('#startDate').val() != undefined){
	    			$scope.employeeVo.startDate = moment($('#startDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
		        }
		        
		        if($('#dateOfBirth').val() != '' && $('#dateOfBirth').val() != undefined){
		        	$scope.employeeVo.dateOfBirth = moment($('#dateOfBirth').val(),'DD/MM/YYYY').format('YYYY-MM-DD') ; 
		        }
		        
		        if($('#startDate').val() != '' && $('#startDate').val() != undefined && $('#dateOfBirth').val() != '' && $('#dateOfBirth').val() != undefined){
		        	if(!moment($scope.employeeVo.startDate).isSameOrAfter($scope.employeeVo.dateOfBirth)){
		        		$scope.Messager('error', 'Error', 'Start Date should not be Less than Date Of Birth');
		        	return;
		        	}
			    }
	     		/*$scope.employeeVo.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
	     		 if($('#startDate').val() != '' && $('#startDate').val() != undefined){
	            $scope.employeeVo.startDate = moment($('#startDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
	     		 }
	     		 if($('#dateOfBirth').val() != '' && $('#dateOfBirth').val() != undefined){
	            $scope.employeeVo.dateOfBirth = moment($('#dateOfBirth').val(),'DD/MM/YYYY').format('YYYY-MM-DD') ;
	     		 }*/
	            $scope.employeeVo.employeeId = 0;       
	            // alert(angular.toJson($scope.employeeVo));   
	            $scope.getData('EmployeeController/saveEmployeeDetails.json', angular.toJson($scope.employeeVo), 'saveEmployeeDetails',angular.element($event.currentTarget))
    	  	}
         /*$scope.employeeVo.transactionDate =  $filter('date')( $scope.employeeVo.transactionDate, 'dd/MM/yyyy')
 		$scope.employeeVo.startDate =  $filter('date')( $scope.employeeVo.startDate, 'dd/MM/yyyy')
 		$scope.employeeVo.dateOfBirth =  $filter('date')( $scope.employeeVo.dateOfBirth, 'dd/MM/yyyy')*/
 		}        
     }
     
     
     
     
    
    $scope.historyBtnAction = function(){
        $scope.readonly = false;
        $scope.updateBtn = false;
        $scope.saveBtn = false;
        $scope.currentHistoryBtn = true;
        $scope.resetBtn = false;
        $scope.cancelBtn = false;
        $scope.addrHistory = false;
        $scope.transactionList = true;     
        $scope.getData('EmployeeController/getTransactionListForEmployee.json', {uniqueId:$scope.employeeVo.uniqueId}, 'getTransactionDates')
        
    }
    
    
    // transactiondates change listener
    $scope.transactionDatesListChnage = function(){
    	$scope.getData('EmployeeController/getEmployeeDetailsByEmployeeId.json', {employeeId : $scope.transactionModel,customerId:$cookieStore.get('customerId'),companyId:$cookieStore.get('companyId')}, 'getEmployeeDetailsById') 
    }
    
    
    
    
    $scope.fun_CorrectHistory = function($event){	
    	var regex = new RegExp("[A-Za-z]{3}(P|p|C|c|H|h|F|f|A|a|T|t|B|b|L|l|J|j|G|j)[A-Za-z]{1}[0-9]{4}[A-Za-z]{1}$");
    	if($('#employeeEntryForm').valid())
		{
    		/*$scope.employeeVo.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
    		 if($('#startDate').val() != '' && $('#startDate').val() != undefined){
            $scope.employeeVo.startDate = moment($('#startDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
    		 }
    		 if($('#dateOfBirth').val() != '' && $('#dateOfBirth').val() != undefined){
            $scope.employeeVo.dateOfBirth = moment($('#dateOfBirth').val(),'DD/MM/YYYY').format('YYYY-MM-DD') ;
    		 }*/
    		//alert($scope.years);
    		if(!moment($('#dateOfBirth').val(), 'DD/MM/YYYY', true).isValid()) {
    			$scope.Messager('error', 'Error', 'Please enter valid Date of birth');
    		}else if($scope.employeeVo.panNumber != undefined && $scope.employeeVo.panNumber != null && $scope.employeeVo.panNumber !='' && !(regex.test($scope.employeeVo.panNumber))){
    	  		$scope.Messager('error', 'Error', 'Please enter valid pan number');
    	  	}else if($scope.years < $scope.companyProfile.minAge) {
    	  		$scope.Messager('error', 'Error', 'Employee is below minimum age limit. Please enter valid date of birth');
    	  	}else if($scope.years > $scope.companyProfile.maxAge) {
    	  		$scope.Messager('error', 'Error', 'Employee is over maximum age limit. Please enter valid date of birth');
    	  	}else{
    			$scope.employeeVo.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
    		
	    		if($('#startDate').val() != '' && $('#startDate').val() != undefined){
	    			$scope.employeeVo.startDate = moment($('#startDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
		        }
		        
		        if($('#dateOfBirth').val() != '' && $('#dateOfBirth').val() != undefined){
		        	$scope.employeeVo.dateOfBirth = moment($('#dateOfBirth').val(),'DD/MM/YYYY').format('YYYY-MM-DD') ; 
		        }
		        
		        if($('#startDate').val() != '' && $('#startDate').val() != undefined && $('#dateOfBirth').val() != '' && $('#dateOfBirth').val() != undefined){
		        	if(!moment($scope.employeeVo.startDate).isSameOrAfter($scope.employeeVo.dateOfBirth)){
		        		$scope.Messager('error', 'Error', 'Start Date should not be Less than Date Of Birth');
		        	return;
		        	}
			    }
                 
          
		        $scope.getData('EmployeeController/saveEmployeeDetails.json', angular.toJson($scope.employeeVo), 'saveHistory',angular.element($event.currentTarget))
    	  	}
       /*  $scope.employeeVo.transactionDate =  $filter('date')( $scope.employeeVo.transactionDate, 'dd/MM/yyyy')
 		$scope.employeeVo.startDate =  $filter('date')( $scope.employeeVo.startDate, 'dd/MM/yyyy')
 		$scope.employeeVo.dateOfBirth =  $filter('date')( $scope.employeeVo.dateOfBirth, 'dd/MM/yyyy')*/
		}
                    
    }
							    
    $scope.saveAddress = function(){  
    	
    	if($('#employeePopupForm').valid() ) {
    		var value = "";
    		if( $scope.employeeVo.address1 != undefined &&  $scope.employeeVo.address1 != '')
    			value += $scope.employeeVo.address1;
    		if( $scope.employeeVo.address2 != undefined &&  $scope.employeeVo.address2 != '')
    			value += ", "+$scope.employeeVo.address2;
    		if( $scope.employeeVo.address3 != undefined &&  $scope.employeeVo.address3 != '')
    			value += ", "+$scope.employeeVo.address3;
    		if( $('#countryIds option:selected').html() != 'Select' &&  $('#countryIds option:selected').html() != '')
    			value += ", "+$('#countryIds option:selected').html();
    		if( $('#state option:selected').html() != 'Select' &&  $('#state option:selected').html() != '')
    			value += ", "+$('#state option:selected').html();
    		if($scope.employeeVo.pincode != undefined &&  $scope.employeeVo.pincode != '')
    			value += ", "+$scope.employeeVo.pincode;        	    
    		
    		value += ". ";
    		$scope.label = "View/Edit";
    		$scope.EmpAdders = value;
    		$('#myModal').modal('hide');
    	}
    };
    
    $scope.fun_cancelBtnCLick = function(){
    	$scope.readonly = true;
        $scope.updateBtn = true;
        $scope.saveBtn = false;
        $scope.currentHistoryBtn = false;
        $scope.resetBtn = false;
        $scope.cancelBtn = false;
        $scope.addrHistory = true;
        $scope.transactionList = false;
        $scope.getData('EmployeeController/getEmployeeDetailsByEmployeeId.json', {employeeId :$routeParams.id}, 'getEmployeeDetailsByIdForCancel')
    	
    }
    
    
    $scope.updateEmployeeBtnAction = function($event){       
        $scope.readonly = false;
        $scope.updateBtn = false;      
        $scope.saveBtn = true;
        $scope.currentHistoryBtn = false;
        $scope.resetBtn = false;
        $scope.cancelBtn = true;
        $scope.addrHistory = false;
        $scope.transactionList = false;      
        $scope.transactionDate = $filter('date')(new Date(),'dd/MM/yyyy');
    }
    
}]);