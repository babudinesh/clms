'use strict';

var actualAtendancereport = angular.module("myApp.actualAttendancereport", []);
var downloadInitialized = false;

actualAtendancereport.controller("actualAttandanceReportSearchCtrl",  ['$scope', '$rootScope', '$http', '$resource','$location','$cookieStore','myservice', function($scope,$rootScope,$http,$resource,$location,$cookieStore,myservice) {
    
	  $scope.list_months = [{"id":"1","name":"January"},
	                        {"id":"2","name":"February"},
	                        {"id":"3","name":"March"},
	                        {"id":"4","name":"April"},
	                        {"id":"5","name":"May"},
	                        {"id":"6","name":"June"},
	                        {"id":"7","name":"July"},
	                        {"id":"8","name":"August"},
	                        {"id":"9","name":"September"},
	                        {"id":"10","name":"October"},
	                        {"id":"11","name":"November"},
	                        {"id":"12","name":"December"}];
	       
	       $('#todate,#fromdate').bootstrapMaterialDatePicker
	           ({
	               time: false,
	               clearButton: true,
	               format : "DD/MM/YYYY"
	           });
	       
	        // FOR COMMON POST METHOD
	         $scope.getPostData = function (url, data, type) {   
	        	 
	        	 
	        	 
	             var req = {
	                 method: 'POST',
	                 url: ROOTURL + url,
	                 headers: {
	                     'Content-Type': 'application/json'
	                 },
	                 data: data
	             }
	             $http(req).then(function (response) {
	           if( type == 'companyGrid' ){
	        	   if(Object.prototype.toString.call(response.data) === '[object Array]' &&  response.data.length > 0 ){
			      		$scope.customerList = response.data;
			      		if(response.data.length == 1){
		      				$scope.customerName = response.data[0].customerId;
		      				$scope.fun_CustomerChangeListener();
			      		}
		    		}	         	                                
	                dt_Menuitem = $('#actualTable').DataTable({        
	                     "columns": [
	                        { "data": "contractorName" },
	                        { "data": "workMenName" },
	                        { "data": "idNo" },
	                        { "data": "shift" },
	                        { "data": "date" },
	                        { "data": "inTime" },
	                        { "data": "outTime" },
	                        { "data": "manHours" },
	                        { "data": "overTime" },
	                        { "data": "status" }],
	                        "dom": 'Bfrtip',
	                            "buttons": [
	                                'excel', 'pdf'
	                            ]
	               });                  
	              } else if( type == 'gridData' ){

	                     if(response.data.length > 0){                            
	                            dt_Menuitem.clear().rows.add(response.data).draw();  
	                            $("#loader").hide();
	                            $("#data_container").show();
	                     }else{
	                    	 $("#loader").hide();
	                         $("#data_container").show();
	                     }
	             }else if(type == 'companyDropDown'){
	            	 if(Object.prototype.toString.call(response.data) === '[object Array]' &&  response.data.length > 0 ){
	 	      			$scope.companyList = response.data;
	 	      			if(response.data.length == 1){
	 	      				$scope.companyName = response.data[0].id;
	 	      				$scope.fun_CompanyChangeListener();
	 	      			}
	 	      		}else{
	 	      			$scope.companyList = [];
	 	      		}	            	                    
	             }else if(type == 'vendorAndDeptDrop'){
	                     $scope.list_vendors = response.data.vendorList  
	                     /*$scope.list_departments = response.data.departmentList;     
	                     $scope.list_countries = response.data.countriesList;
	                     $scope.country =  response.data.countriesList[0].id;*/
	             }else if(type == 'locationDrop'){       
	                     $scope.list_locations = response.data;                 
	              }else if(type == 'workOrderCodeDropDown'){            
	                     $scope.list_locations = response.data;                 
	              }else if(type == 'sectionDropDown'){
	                     $scope.list_sections = response.data.sectionList;           
	                     $scope.list_workOrder = response.data.workOrderList;        
	              }      
	             })
	             
	         };
	         
	         
	   // for getting Master data for Grid List 
	   $scope.getPostData("reportController/getCustomerListForReportView.json",{customerId : $cookieStore.get('customerId') } ,"companyGrid" );
	                   
	   // Customer Change Listener to get company details
	   $scope.fun_CustomerChangeListener = function(){                       
	          if($scope.customerName != undefined && $scope.customerName != ''){
	              $scope.getPostData("vendorController/getCompanyNamesAsDropDown.json",  { customerId : $scope.customerName,companyId: $cookieStore.get('companyId') != null && $cookieStore.get('companyId') != undefined ? $cookieStore.get('companyId')  : $scope.companyName } , "companyDropDown");
	          }
	   };
	   
	  // Company Change Listener to get locations details
	   $scope.fun_CompanyChangeListener = function(){
	          if($scope.companyName != undefined && $scope.companyName != '')
	                 $scope.getPostData("reportController/getVendorNamesAndDepartmentsAsDropDown.json", { customerId : $scope.customerName , companyId : $scope.companyName , vendorId : '0' } , "vendorAndDeptDrop");                
	   };
	   
	   //fun Department Change Listener
	   $scope.fun_DepartmentChangeListener = function(){
	          if($scope.departmentName != undefined && $scope.departmentName != '')
	                 $scope.getPostData("reportController/getLocationListByDept.json", { customerId : $scope.customerName , companyId : $scope.companyName , departmentId : $scope.departmentName} , "locationDrop");
	   }
	   
	// locations Change Listener to get Departments details
	   $scope.fun_locationChangeListener = function(){
	          if($scope.locationName != undefined && $scope.locationName != '')
	          $scope.getPostData("reportController/getSectionAndWorkOrderListByLocation.json", { customerId : $scope.customerName , companyId : $scope.companyName , departmentId : $scope.departmentName, locationId : $scope.locationName } , "sectionDropDown");               
	   };
	   
	   
	   
	   // for Search button 
	   $scope.fun_searchGridData = function(){   
		   
		   if(moment($('#fromdate').val()).isAfter($('#todate').val())){
			   alert(" To date should be greater than from date..");
			   return;
		   }else {
			  /* alert('else');
			   return;*/
	          if($('#attendanceSearchForm').valid()){ 
	          $scope.reporttodate = $('#todate').val();
	          $scope.reportFromdate = $('#fromdate').val();
	    	  $("#loader").show();
	      	 $("#data_container").hide();
	        $scope.getPostData("reportController/getActualAttandanceReport.json",{customerId : $scope.customerName == undefined ? '0' : $scope.customerName, 
		 																  companyId : $scope.companyName == undefined ? '0' : $scope.companyName  ,
																		  vendorId: $scope.vendorId == undefined ? '0' : $scope.vendorId  ,
		 																 /* locationId : $scope.locationName == undefined ? '0' : $scope.locationName,
																		  sectionId : $scope.sectionName == undefined ? '0' : $scope.sectionName,   			 																		
	 																	  departmentId : $scope.departmentName == undefined ? '0' : $scope.departmentName,  
																		  workOrderCode : $scope.workOrderCode == undefined ? '0' : $scope.workOrderCode ,*/
																		  employeeCode : $scope.employeeCode == undefined ? 'null' : $scope.employeeCode ,
																		  employeeName : $scope.employeeName == undefined ? 'null' : $scope.employeeName ,
																		  year : ($scope.year == undefined || $scope.year == '' )? '0' : $scope.year ,
																		  month : ($scope.month == undefined || $scope.month == ''  )? '0' : $scope.month ,
																	      fromdate : ($scope.reportFromdate == undefined || $scope.reportFromdate == '') ? 'null' : $scope.reportFromdate ,
																		  todate : ($scope.reporttodate == undefined || $scope.reporttodate == '') ? 'null' : $scope.reporttodate ,
	 																	  status : $scope.status == undefined ? 'null' : $scope.status 
		 																  } , "gridData" ); 
	        
	          }
		   }
	  }
	  
	  
	   $scope.byDateShow = true;
	   $scope.monthYear='Y';
	   $scope.ByMonthYearShow = false;
	   var year = moment().year();
	   var month = moment().month()+1;
	   
	    $scope.fun_date_year_Listener = function(){
		   //alert('here'+$scope.monthYear);
		  if($scope.monthYear== 'Y'){		  
			  $scope.byDateShow = true;
			  $scope.ByMonthYearShow = false;
			  $scope.year ="";
			  $scope.month = "";
		  }else{
			  $scope.byDateShow = false;
			  $scope.ByMonthYearShow = true;
			  $scope.year =year+"";
			  $scope.month = month;
			  $('#fromdate').val('');
			  $('#todate').val('');
		  }
	   }
	   
	   
	   $scope.fun_clearSsearchFields = function(){
	      
		  
	   }
	 
}]);
 
