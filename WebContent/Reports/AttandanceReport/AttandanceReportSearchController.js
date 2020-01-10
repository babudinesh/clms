'use strict';

var attendancereport = angular.module("myApp.Attendancereport", []);
 
attendancereport.controller("attandanceReportSearchCtrl",  ['$scope', '$rootScope', '$http', '$resource','$location','$cookieStore','$sce', function($scope,$rootScope,$http,$resource,$location,$cookieStore,$sce) {
	var dt_attendanceReport;           
	$scope.dataAvail = false;
	$scope.departmentId = null;
	$scope.sectionId = null;
	$scope.workAreaId = null;
	
	 $("#data_container").hide();
       
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
       
       $scope.list_skills = [{"id":"Skilled","name" : "Skilled" },
  	                       {"id":"Semi Skilled","name" : "Semi Skilled" },
  	                       {"id":"High Skilled","name" : "High Skilled" },
  	                       {"id":"Special Skilled","name" : "Special Skilled" },
  	                       {"id":"UnSkilled","name" : "UnSkilled" }];
       
     /*  
      $('#todate,#fromdate').bootstrapMaterialDatePicker
           ({
               time: false,
               clearButton: true,
               format : "DD/MM/YYYY"
           });
       */
       $('#todate,#fromdate').datepicker({
    	      changeMonth: true,
    	      changeYear: true,
    	      dateFormat:"dd/mm/yy",
    	      showAnim: 'slideDown'
    	    	  
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
            	 if(type == 'buttonsAction'){
                		$scope.buttonArray = response.data.screenButtonNamesArray;
                	//	alert($scope.buttonArray)
               } else if( type == 'companyGrid' ){
            	   if(Object.prototype.toString.call(response.data) === '[object Array]' &&  response.data.length > 0 ){
            		   $scope.customerList = response.data;
            		   if(response.data.length == 1){
            			   $scope.customerName = response.data[0].customerId;
            			   $scope.fun_CustomerChangeListener();
            		   }
	    		}	
        	   if ( !$.fn.DataTable.isDataTable( '#attendanceReportTable' ) ) {        		   
        		   dt_attendanceReport = $('#attendanceReportTable').DataTable({        
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
                                 /* 'excel', 'pdf'*/
                              ]
                 });  
        		}
        	   //$('.table').DataTable().destroy();
        	   
                                
              } else if( type == 'gridData' ){

                     if(Object.prototype.toString.call(response.data) === '[object Array]'){                            
                            dt_attendanceReport.clear().rows.add(response.data).draw();  
                            $("#loader").hide();
                            $("#data_container").show();
                            $scope.dataAvail = true;
                     }else{
                    	 $("#loader").hide();
                         $("#data_container").show();
                         $scope.dataAvail = false;
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
            	 $scope.list_vendors = response.data.vendorList;
            	 $scope.locationsList = response.data.locationsList;
            	 $scope.shiftsList = response.data.shiftCodes;
                 if( response.data != undefined && response.data.vendorList != "" && response.data.vendorList.length == 1 ){
       	             $scope.vendorId = response.data.vendorList[0].id;					
       	         }else{
	   	            $scope.vendorId = 0;
	   	         }
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
             }else if(type == 'plantsList'){
             	$scope.plantsList = response.data;
             }else if(type == 'departmentsList'){
             	$scope.departmentsList = response.data.departmentList;
             }else if(type == 'sectionsList'){
             	//$scope.departmentsList = response.data.departmentsList;
             	$scope.sectionesList = response.data.sectionsList;
             }else if(type == 'workAreasList'){
             	$scope.workAreasList = response.data;
             }/*else if( type ==  'schemaName'){            	 
            	 $scope.schemaName = response.data.name;
            	 var CurrentScreenName = "";
            	 //alert($location.path());
                     $scope.list_locations = response.data;                 
              }*/else if(type == 'workOrderCodeDropDown'){            
                     $scope.list_locations = response.data;                 
              }else if(type == 'sectionDropDown'){
                     $scope.list_sections = response.data.sectionList;           
                     $scope.list_workOrder = response.data.workOrderList;        
              }else if( type ==  'schemaName'){            	 
            	  $scope.schemaName = response.data.name;
            	  var CurrentScreenName = "";
            	//alert($location.path());

            	 // window.location.href.split("/CLMS/#/")[1];
            	  var urllink = $location.path();
            	  if(urllink == '/attendancereport'){
            		  CurrentScreenName = "Attendance Report";
            	  } else if(urllink == '/WorkerMonthlyReport'){
            		  CurrentScreenName = "Horizontal Attendance Report";
            	  } else if(urllink == '/WorkerMonthlyAttendanceReport'){
            		  CurrentScreenName = "Horizontal Report";
            	  } else if(urllink == '/AttendanceExcelReport'){
            		  CurrentScreenName = "Excel Attendance Report";
            	  } else if(urllink == '/WorkerMonthlyExcelReport'){
            		  CurrentScreenName = "Excel Horizontal Attendance Report";
            	  } 
            	  
            	  $scope.getPostData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName: CurrentScreenName}, 'buttonsAction');
              }    
             })
             
         };
         
         
   // getSchemaNameByUserId
   $scope.getPostData("reportController/getSchemaNameByUserId.json",{userId : $cookieStore.get('userId') } ,"schemaName" );  
   
   
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
                 $scope.getPostData("reportController/getVendorNamesAndDepartmentsAsDropDown.json", { customerId : $scope.customerName , companyId : $scope.companyName , vendorId : $cookieStore.get('vendorId') != undefined && $cookieStore.get('vendorId') != '' ? $cookieStore.get('vendorId') : 0 } , "vendorAndDeptDrop");                
   };
   
  /* //fun Department Change Listener
   $scope.fun_DepartmentChangeListener = function(){
          if($scope.departmentName != undefined && $scope.departmentName != '')
                 $scope.getPostData("reportController/getLocationListByDept.json", { customerId : $scope.customerName , companyId : $scope.companyName , departmentId : $scope.departmentName} , "locationDrop");
   }
   
// locations Change Listener to get Departments details
   $scope.fun_locationChangeListener = function(){
          if($scope.locationName != undefined && $scope.locationName != '')
          $scope.getPostData("reportController/getSectionAndWorkOrderListByLocation.json", { customerId : $scope.customerName , companyId : $scope.companyName , departmentId : $scope.departmentName, locationId : $scope.locationName } , "sectionDropDown");               
   };*/
   
    $scope.locationChange = function(){
	   	if($scope.locationId != undefined && $scope.locationId != null && $scope.locationId != ''){
	   		$scope.departmentsList = [];
	   		$scope.sectionesList = [];
	   		$scope.workAreasList = [];
	   		
	   		$scope.getPostData('jobAllocationByVendorController/getPlantsList.json', {locationId:$scope.locationId}, 'plantsList');
	   	}else{
	   		$scope.plantsList =[];
	   		$scope.departmentsList = [];
	   		$scope.sectionesList = [];
	   		$scope.workAreasList = [];
	   	}
	   }
	   
	   $scope.plantChange = function(){
	   	if($scope.locationId != undefined && $scope.locationId != null && $scope.locationId != '' && $scope.plantId != undefined && $scope.plantId != null && $scope.plantId != ''){
	   		$scope.sectionesList = [];
	   		$scope.workAreasList = [];
	   		$scope.getPostData('reportController/getDepartmentsByLocationAndPlantId.json', {customerId:$cookieStore.get('customerId'), companyId: $cookieStore.get('companyId'), locationId:$scope.locationId,plantId:$scope.plantId}, 'departmentsList');
	   	//	$scope.getPostData('associatingDepartmentToLocationPlantController/getDepartMentDetailsByLocationAndPlantId.json', {customerId:$cookieStore.get('customerId'), companyId: $cookieStore.get('companyId'), locationId:$scope.locationId,plantId:$scope.plantId}, 'departmentsList');
	   	}else{
	   		$scope.departmentsList = [];
	   		$scope.sectionesList = [];
	   		$scope.workAreasList = [];
	   	}
	   }
	  
	   $scope.departmentChange = function(){
	   	if($scope.locationId != undefined && $scope.locationId != null && $scope.locationId != '' && $scope.plantId != undefined && $scope.plantId != null && $scope.plantId != '' && $scope.departmentId != undefined && $scope.departmentId != null && $scope.departmentId != ''){
	   		$scope.workAreasList = [];
	   		$scope.getPostData('associatingDepartmentToLocationPlantController/getSectionDetailsByLocationAndPlantAndDeptId.json', {customerId:$cookieStore.get('customerId'), companyId: $cookieStore.get('companyId'), locationId:$scope.locationId,plantId:$scope.plantId, departmentId : $scope.departmentId}, 'sectionsList');
	   	}else{
	   		$scope.sectionesList = []; 
	   		$scope.workAreasList = [];
	   	}
	   }
	   

	   
	   $scope.sectionChange = function(){
	   	if($scope.locationId != undefined && $scope.locationId != null && $scope.locationId != '' && $scope.plantId != undefined && $scope.plantId != null && $scope.plantId != '' && $scope.sectionId != undefined && $scope.sectionId != null && $scope.sectionId != '' && $scope.departmentId != undefined && $scope.departmentId != null && $scope.departmentId != ''){
	   			$scope.getPostData('jobAllocationByVendorController/getAllWorkAreasBySectionesAndLocationAndPlantAndDept.json', {customerId:$cookieStore.get('customerId'), companyId: $cookieStore.get('companyId'), locationId:$scope.locationId,plantId:$scope.plantId,sectionId:$scope.sectionId,departmentId:$scope.departmentId}, 'workAreasList');
	   	}else{    	
	   		$scope.workAreasList = [];
	   	}
	   }
   
   // for Search button 
   $scope.fun_searchGridData = function(){   
	   

	  // alert(moment($('#fromdate').val()).isAfter($('#todate').val())); 
	   var fromDate = $('#fromdate').val(), toDate = $('#todate').val(), from, to, duration;
 
	from = moment(fromDate, 'DD/MM/YYYY'); // format in which you have the date
	to = moment(toDate, 'DD/MM/YYYY');     // format in which you have the date
 
	/* using diff */
	duration = to.diff(from, 'days')     
	
	/* show the result */
	// alert(duration + ' days');
	if($('#attendanceSearchForm').valid() && duration != 'NAN' && duration > 31 ){
		 alert(" Number of days should not be more than 31 days ");
		 return;
	} else if($('#attendanceSearchForm').valid() &&  duration != 'NAN' && duration < 0 ){
		alert("To date should be greater than From date..");
		return;
	}
	   
	   if($('#attendanceSearchForm').valid() && moment(new Date($('#fromdate').val())).isAfter(moment(new Date($('#todate').val())))){
		    alert(" To date should be greater than From date..");
		   return;
	   }else {
		  /* alert('else');
		   return;*/
          if($('#attendanceSearchForm').valid()){ 
          $scope.reporttodate = $('#todate').val();
          $scope.reportFromdate = $('#fromdate').val();
    	  $("#loader").show();
      	  $("#data_container").hide();
          // alert($scope.reporttodate);
        $scope.getPostData("reportController/getAttandanceReport.json",{customerId : $scope.customerName == undefined ? '0' : $scope.customerName, 
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
 																	  status : $scope.status == undefined ? 'null' : $scope.status,
 																	  schemaName : ($scope.schemaName == undefined || $scope.schemaName == '')? 'null' : $scope.schemaName,
 																	  plantId : $scope.plantId,
 																	  locationId:$scope.locationId,
														    	      plantName: $('#plantId option:selected').text(),
														    	      locationName:$('#locationId option:selected').text(),
														    	      companyName:$('#companyName option:selected').text(),
														    	      jobType: $scope.jobType,
														    	      departmentId: ($scope.departmentId != undefined && $scope.departmentId != null && $scope.departmentId != "") ? $scope.departmentId : 'null',
														    	      sectionId: ($scope.sectionId != undefined && $scope.sectionId != null && $scope.sectionId != "") ? $scope.sectionId : 'null',
														    	      workAreaId: ($scope.workAreaId != undefined && $scope.workAreaId != null && $scope.workAreaId != "") ? $scope.workAreaId : 'null',
														    	      workSkill: ($scope.workSkill != undefined && $scope.workSkill != null && $scope.workSkill != "") ? $scope.workSkill : 'null',
														    	      shift: ($scope.shift != undefined && $scope.shift != null && $scope.shift != "") ? $scope.shift : 'null'
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
		/*  $('#fromdate').attr('required');
		  $('#todate').attr('required');
		  $('#year').removeAttr('required');
		  $('#month').removeAttr('required');
		  */
		  
	  }else{
		  $scope.byDateShow = false;
		  $scope.ByMonthYearShow = true;
		  $scope.year =year+"";
		  $scope.month = month+"";
		  $('#fromdate').val('');
		  $('#todate').val('');

		 /* $('#year').attr('required');
		  $('#month').attr('required');		 		 
		  $('#fromdate').removeAttr('required');
		  $('#todate').removeAttr('required');*/
	  }
   }
   
   
   $scope.fun_clearSsearchFields = function(){
      
	   $("#data_container").hide();
   }
   
   $scope.fun_pdf_Download = function(){ 

		var fromDate = $('#fromdate').val(), toDate = $('#todate').val(), from, to, duration;
	 
		from = moment(fromDate, 'DD/MM/YYYY'); // format in which you have the date
		to = moment(toDate, 'DD/MM/YYYY');     // format in which you have the date

		duration = to.diff(from, 'days')     

		if($('#attendanceSearchForm').valid() && duration != 'NAN' && duration > 31 ){
			 alert(" Number of days should not be more than 31 days ");
			 return;
		} else if($('#attendanceSearchForm').valid() &&  duration != 'NAN' && duration < 0 ){
			alert("To date should be greater than From date..");
			return;
		}
		   	
	    if($('#attendanceSearchForm').valid() && moment(new Date($('#fromdate').val())).isAfter(moment(new Date($('#todate').val())))){
		    alert(" To date should be greater than From date..");
		    return;
	   }else {			  
	          if($('#attendanceSearchForm').valid()){ 
	        	  $scope.reporttodate = $('#todate').val();
	              $scope.reportFromdate = $('#fromdate').val();
	        	  $("#loader").show();
	          	  $("#data_container").hide();
	          	  
				   $http({
					    url: ROOTURL +'reportController/downloadPDFAttendanceReport.view',
					    method: 'POST',
					    responseType: 'arraybuffer',
					    data: {customerId : $scope.customerName == undefined ? '0' : $scope.customerName, 
								  companyId : $scope.companyName == undefined ? '0' : $scope.companyName  ,
										  vendorId: ($scope.vendorId == undefined || $scope.vendorId == '')? '0' : $scope.vendorId  ,									
										  employeeCode : $scope.employeeCode == undefined ? 'null' : $scope.employeeCode ,
										  employeeName : $scope.employeeName == undefined ? 'null' : $scope.employeeName ,
										  year : ($scope.year == undefined || $scope.year == '' )? '0' : $scope.year ,
										  month : ($scope.month == undefined || $scope.month == ''  )? '0' : $scope.month ,
									      fromdate : ($scope.reportFromdate == undefined || $scope.reportFromdate == '') ? 'null' : $scope.reportFromdate ,
										  todate : ($scope.reporttodate == undefined || $scope.reporttodate == '') ? 'null' : $scope.reporttodate ,
										  status : $scope.status == undefined ? 'null' : $scope.status,reportName : $scope.reportName ,
										  schemaName : ($scope.schemaName == undefined || $scope.schemaName == '')? 'null' : $scope.schemaName,
										  plantId : $scope.plantId,
										  locationId:$scope.locationId,
							    	      plantName: $('#plantId option:selected').text(),
							    	      locationName:$('#locationId option:selected').text(),
							    	      companyName:$('#companyName option:selected').text(),
							    	      jobType: $scope.jobType,
							    	      departmentId: ($scope.departmentId != undefined && $scope.departmentId != null && $scope.departmentId != "") ? $scope.departmentId : 'null',
							    	      sectionId: ($scope.sectionId != undefined && $scope.sectionId != null && $scope.sectionId != "") ? $scope.sectionId : 'null',
							    	      workAreaId: ($scope.workAreaId != undefined && $scope.workAreaId != null && $scope.workAreaId != "") ? $scope.workAreaId : 'null',
							    	      workSkill: ($scope.workSkill != undefined && $scope.workSkill != null && $scope.workSkill != "") ? $scope.workSkill : 'null',
									      shift: ($scope.shift != undefined && $scope.shift != null && $scope.shift != "") ? $scope.shift : 'null'


								} , //this is your json data string
					    headers: {
					        'Content-type': 'application/json',
					        'Accept': 'application/pdf'
					    }
					}).success(function(data){
					    var blob = new Blob([data], {
					        type: 'application/pdf'
					    });
					    saveAs(blob, 'Attendance_Report' + '.pdf');
					    $("#loader").hide();
					    if($scope.dataAvail)
                        $("#data_container").show();
					}).error(function(){	
						$("#loader").hide();
                      //  $("#data_container").show();
					});
	          }
	   
		}
   }
   
   
   $scope.fun_Excel_Download = function(){

		var fromDate = $('#fromdate').val(), toDate = $('#todate').val(), from, to, duration;
	 
		from = moment(fromDate, 'DD/MM/YYYY'); // format in which you have the date
		to = moment(toDate, 'DD/MM/YYYY');     // format in which you have the date

		duration = to.diff(from, 'days')     

		if($('#attendanceSearchForm').valid() && duration != 'NAN' && duration > 31 ){
			 alert(" Number of days should not be more than 31 days ");
			 return;
		} else if($('#attendanceSearchForm').valid() &&  duration != 'NAN' && duration < 0 ){
			alert("To date should be greater than From date..");
			return;
		}
		   
	    if($('#attendanceSearchForm').valid() && moment(new Date($('#fromdate').val())).isAfter(moment(new Date($('#todate').val())))){
		    alert(" To date should be greater than From date..");
		    return;
	   }else {			  
	          if($('#attendanceSearchForm').valid()){ 
	        	  $scope.reporttodate = $('#todate').val();
	              $scope.reportFromdate = $('#fromdate').val();
	        	  $("#loader").show();
	          	  $("#data_container").hide();
	          	  
				   $http({
					    url: ROOTURL +'reportController/downloadExcelAttendanceReport.view',
					    method: 'POST',
					    responseType: 'arraybuffer',
					    data: {customerId : $scope.customerName == undefined ? '0' : $scope.customerName, 
								  companyId : $scope.companyName == undefined ? '0' : $scope.companyName  ,
										  vendorId: ($scope.vendorId == undefined || $scope.vendorId == '')? '0' : $scope.vendorId  ,									
										  employeeCode : $scope.employeeCode == undefined ? 'null' : $scope.employeeCode ,
										  employeeName : $scope.employeeName == undefined ? 'null' : $scope.employeeName ,
										  year : ($scope.year == undefined || $scope.year == '' )? '0' : $scope.year ,
										  month : ($scope.month == undefined || $scope.month == ''  )? '0' : $scope.month ,
									      fromdate : ($scope.reportFromdate == undefined || $scope.reportFromdate == '') ? 'null' : $scope.reportFromdate ,
										  todate : ($scope.reporttodate == undefined || $scope.reporttodate == '') ? 'null' : $scope.reporttodate ,
										  status : $scope.status == undefined ? 'null' : $scope.status,reportName : $scope.reportName ,
										  schemaName : ($scope.schemaName == undefined || $scope.schemaName == '')? 'null' : $scope.schemaName,
										  plantId : $scope.plantId,
										  locationId:$scope.locationId,
							    	      plantName: $('#plantId option:selected').text(),
							    	      locationName:$('#locationId option:selected').text(),
							    	      companyName:$('#companyName option:selected').text(),
							    	      jobType: $scope.jobType,
							    	      departmentId: ($scope.departmentId != undefined && $scope.departmentId != null && $scope.departmentId != "") ? $scope.departmentId : 'null',
							    	      sectionId: ($scope.sectionId != undefined && $scope.sectionId != null && $scope.sectionId != "") ? $scope.sectionId : 'null',
							    	      workAreaId: ($scope.workAreaId != undefined && $scope.workAreaId != null && $scope.workAreaId != "") ? $scope.workAreaId : 'null',
							    	      workSkill: ($scope.workSkill != undefined && $scope.workSkill != null && $scope.workSkill != "") ? $scope.workSkill : 'null',
									      shift: ($scope.shift != undefined && $scope.shift != null && $scope.shift != "") ? $scope.shift : 'null'


								} , //this is your json data string
					    headers: {
					        'Content-type': 'application/json',
					        'Accept': 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
					    }
					}).success(function(data){
					    var blob = new Blob([data], {
					        type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
					    });
					    saveAs(blob, 'Attendance_Report' + '.xlsx');
					    $("#loader").hide();
					    if($scope.dataAvail)
                         $("#data_container").show();
					}).error(function(){	
						$("#loader").hide();
                     //  $("#data_container").show();
					});
	          }
	   
		}
  }
   
   
   
   
   $scope.fun_WorkerMonthlyReport_Download = function(reportNameByUser){ 

		var fromDate = $('#fromdate').val(), toDate = $('#todate').val(), from, to, duration;
	 
		from = moment(fromDate, 'DD/MM/YYYY'); // format in which you have the date
		to = moment(toDate, 'DD/MM/YYYY');     // format in which you have the date

		duration = to.diff(from, 'days')     

		if($('#WorkerMonthlyReportForm').valid() && duration != 'NAN' && duration > 31 ){
			 alert(" Number of days should not be more than 31 days ");
			 return;
		} else if($('#WorkerMonthlyReportForm').valid() &&  duration != 'NAN' && duration < 0 ){
			alert("To date should be greater than From date..");
			return;
		}
		if($('#WorkerMonthlyReportForm').valid() && moment(new Date($('#fromdate').val())).isAfter(moment(new Date($('#todate').val())))){   	  
		    alert(" To date should be greater than From date..");
		    return;
	   }else { 
	          if($('#WorkerMonthlyReportForm').valid()){ 
	        	  $scope.reporttodate = $('#todate').val();
	              $scope.reportFromdate = $('#fromdate').val();
	        	  $("#loader").show();
	          	  $("#data_container").hide();
	          	  
				   $http({
					    url: ROOTURL +'reportController/downloadPDFAttendanceReport.view',
					    method: 'POST',
					    responseType: 'arraybuffer',
					    data: {customerId : $scope.customerName == undefined ? '0' : $scope.customerName, 
								  companyId : $scope.companyName == undefined ? '0' : $scope.companyName  ,
										  vendorId: ($scope.vendorId == undefined || $scope.vendorId == '')? '0' : $scope.vendorId  ,									
										  employeeCode : $scope.employeeCode == undefined ? 'null' : $scope.employeeCode ,
										  employeeName : $scope.employeeName == undefined ? 'null' : $scope.employeeName ,
										  year : ($scope.year == undefined || $scope.year == '' )? '0' : $scope.year ,
										  month : ($scope.month == undefined || $scope.month == ''  )? '0' : $scope.month ,
									      fromdate : ($scope.reportFromdate == undefined || $scope.reportFromdate == '') ? 'null' : $scope.reportFromdate ,
										  todate : ($scope.reporttodate == undefined || $scope.reporttodate == '') ? 'null' : $scope.reporttodate ,
										  status : $scope.status == undefined ? 'null' : $scope.status,
										  reportName : reportNameByUser,
										  schemaName : ($scope.schemaName == undefined || $scope.schemaName == '')? 'null' : $scope.schemaName,
										  plantId : $scope.plantId,
							    	      locationId:$scope.locationId,
							    	      plantName: $('#plantId option:selected').text(),
							    	      locationName:$('#locationId option:selected').text(),
							    	      companyName:$('#companyName option:selected').text(),
							    	      jobType: $scope.jobType,
							    	      departmentId: ($scope.departmentId != undefined && $scope.departmentId != null && $scope.departmentId != "") ? $scope.departmentId : 'null',
							    	      sectionId: ($scope.sectionId != undefined && $scope.sectionId != null && $scope.sectionId != "") ? $scope.sectionId : 'null',
							    	      workAreaId: ($scope.workAreaId != undefined && $scope.workAreaId != null && $scope.workAreaId != "") ? $scope.workAreaId : 'null',
							    	      workSkill: ($scope.workSkill != undefined && $scope.workSkill != null && $scope.workSkill != "") ? $scope.workSkill : 'null',
										  shift: ($scope.shift != undefined && $scope.shift != null && $scope.shift != "") ? $scope.shift : 'null'


								} , //this is your json data string
					    headers: {
					        'Content-type': 'application/json',
					        'Accept': 'application/pdf'
					    }
					}).success(function(data){
					    var blob = new Blob([data], {
					        type: 'application/pdf'
					    });					   
					    saveAs(blob, 'WorkerMonthly_Report' + '.pdf');
					    $("#loader").hide();
                       $("#data_container").show();
					}).error(function(){	
						$("#loader").hide();
                       $("#data_container").show();
					});
	          }
	   
		}
  }
	   
   $scope.fun_WorkerMonthlyReport_ExcelDownload = function(){
	   var fromDate = $('#fromdate').val(), toDate = $('#todate').val(), from, to, duration;
		 
		from = moment(fromDate, 'DD/MM/YYYY'); // format in which you have the date
		to = moment(toDate, 'DD/MM/YYYY');     // format in which you have the date

		duration = to.diff(from, 'days')     

		if($('#WorkerMonthlyReportForm').valid() && duration != 'NAN' && duration > 31 ){
			 alert(" Number of days should not be more than 31 days ");
			 return;
		} else if($('#WorkerMonthlyReportForm').valid() &&  duration != 'NAN' && duration < 0 ){
			alert("To date should be greater than From date..");
			return;
		}
		   
	  if($('#WorkerMonthlyReportForm').valid() && moment(new Date($('#fromdate').val())).isAfter(moment(new Date($('#todate').val())))){   	  
		    alert(" To date should be greater than From date..");
		    return;
	   }else {			  
	          if($('#WorkerMonthlyReportForm').valid()){ 
	        	  $scope.reporttodate = $('#todate').val();
	              $scope.reportFromdate = $('#fromdate').val();
	        	  $("#loader").show();
	          	  $("#data_container").hide();
	          	$http({
				    url: ROOTURL +'reportController/downloadExcelAttendanceReport.view',
				    method: 'POST',
				    responseType: 'arraybuffer',
				    data: {customerId : $scope.customerName == undefined ? '0' : $scope.customerName, 
							  companyId : $scope.companyName == undefined ? '0' : $scope.companyName  ,
									  vendorId: ($scope.vendorId == undefined || $scope.vendorId == '')? '0' : $scope.vendorId  ,									
									  employeeCode : $scope.employeeCode == undefined ? 'null' : $scope.employeeCode ,
									  employeeName : $scope.employeeName == undefined ? 'null' : $scope.employeeName ,
									  year : ($scope.year == undefined || $scope.year == '' )? '0' : $scope.year ,
									  month : ($scope.month == undefined || $scope.month == ''  )? '0' : $scope.month ,
								      fromdate : ($scope.reportFromdate == undefined || $scope.reportFromdate == '') ? 'null' : $scope.reportFromdate ,
									  todate : ($scope.reporttodate == undefined || $scope.reporttodate == '') ? 'null' : $scope.reporttodate ,
									  status : $scope.status == undefined ? 'null' : $scope.status,reportName : 'WorkerMonthlyExcelReport'  ,
									  schemaName : ($scope.schemaName == undefined || $scope.schemaName == '')? 'null' : $scope.schemaName,
											  plantId : $scope.plantId,
								    	      locationId:$scope.locationId,
								    	      plantName: $('#plantId option:selected').text(),
								    	      locationName:$('#locationId option:selected').text(),
								    	      companyName:$('#companyName option:selected').text(),
								    	      jobType: $scope.jobType,
								    	      departmentId: ($scope.departmentId != undefined && $scope.departmentId != null && $scope.departmentId != "") ? $scope.departmentId : 'null',
								    	      sectionId: ($scope.sectionId != undefined && $scope.sectionId != null && $scope.sectionId != "") ? $scope.sectionId : 'null',
								    	      workAreaId: ($scope.workAreaId != undefined && $scope.workAreaId != null && $scope.workAreaId != "") ? $scope.workAreaId : 'null',
								    	      workSkill: ($scope.workSkill != undefined && $scope.workSkill != null && $scope.workSkill != "") ? $scope.workSkill : 'null',
											  shift: ($scope.shift != undefined && $scope.shift != null && $scope.shift != "") ? $scope.shift : 'null'

									 
							} , //this is your json data string
				    headers: {
				        'Content-type': 'application/json',
				        'Accept': 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
				    }
				}).success(function(data){
				    var blob = new Blob([data], {
				        type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
				    });
				    saveAs(blob, 'Monthly_Attendance_Report' + '.xlsx');
				    $("#loader").hide();
				    if($scope.dataAvail)
                     $("#data_container").show();
				}).error(function(){	
					$("#loader").hide();
                 //  $("#data_container").show();
				});
	          	  
	          }
	   }
   
   }
   
   
      
	 $scope.fun_previewGridData = function(reportNameByUser){
		var fromDate = $('#fromdate').val(), toDate = $('#todate').val(), from, to, duration;
	
		from = moment(fromDate, 'DD/MM/YYYY'); // format in which you have the date
		to = moment(toDate, 'DD/MM/YYYY');     // format in which you have the date
	
		duration = to.diff(from, 'days')     
		
		if($('#WorkerMonthlyReportForm').valid() && duration != 'NAN' && duration > 31 ){
			 alert(" Number of days should not be more than 31 days ");
			 return;
		} else if($('#WorkerMonthlyReportForm').valid() &&  duration != 'NAN' && duration < 0 ){
			alert("To date should be greater than From date..");
			return;
		}
		if($('#WorkerMonthlyReportForm').valid() && moment(new Date($('#fromdate').val())).isAfter(moment(new Date($('#todate').val())))){   	  
		    alert(" To date should be greater than From date..");
		    return;
	  }else {		  
	         if($('#WorkerMonthlyReportForm').valid()){ 
	        	// alert(parseInt($cookieStore.get('userId')) == 10 ? 'WorkerMonthlyTotalCountReport' : 'WorkerMonthlyReport');
	       	  $scope.reporttodate = $('#todate').val();
	             $scope.reportFromdate = $('#fromdate').val();
	       	  $("#loader").show();
	         	  $("#data_container").hide();
	         	  
				   $http({
					    url: ROOTURL +'reportController/downloadPDFAttendanceReport.view',
					    method: 'POST',
					    responseType: 'arraybuffer',
					    data: {customerId : $scope.customerName == undefined ? '0' : $scope.customerName, 
								  companyId : $scope.companyName == undefined ? '0' : $scope.companyName  ,
										  vendorId: ($scope.vendorId == undefined || $scope.vendorId == '')? '0' : $scope.vendorId  ,									
										  employeeCode : $scope.employeeCode == undefined ? 'null' : $scope.employeeCode ,
										  employeeName : $scope.employeeName == undefined ? 'null' : $scope.employeeName ,
										  year : ($scope.year == undefined || $scope.year == '' )? '0' : $scope.year ,
										  month : ($scope.month == undefined || $scope.month == ''  )? '0' : $scope.month ,
									      fromdate : ($scope.reportFromdate == undefined || $scope.reportFromdate == '') ? 'null' : $scope.reportFromdate ,
										  todate : ($scope.reporttodate == undefined || $scope.reporttodate == '') ? 'null' : $scope.reporttodate ,
										  status : $scope.status == undefined ? 'null' : $scope.status,reportName : reportNameByUser ,
										  schemaName : ($scope.schemaName == undefined || $scope.schemaName == '')? 'null' : $scope.schemaName,
										  plantId : $scope.plantId,
							    	      locationId:$scope.locationId,
							    	      plantName: $('#plantId option:selected').text(),
							    	      locationName:$('#locationId option:selected').text(),
							    	      companyName:$('#companyName option:selected').text(),
							    	      jobType: $scope.jobType,
							    	      departmentId: ($scope.departmentId != undefined && $scope.departmentId != null && $scope.departmentId != "") ? $scope.departmentId : 'null',
							    	      sectionId: ($scope.sectionId != undefined && $scope.sectionId != null && $scope.sectionId != "") ? $scope.sectionId : 'null',
							    	      workAreaId: ($scope.workAreaId != undefined && $scope.workAreaId != null && $scope.workAreaId != "") ? $scope.workAreaId : 'null',
					    	    		  workSkill: ($scope.workSkill != undefined && $scope.workSkill != null && $scope.workSkill != "") ? $scope.workSkill : 'null',
										  shift: ($scope.shift != undefined && $scope.shift != null && $scope.shift != "") ? $scope.shift : 'null'

								} , //this is your json data string
					    headers: {
					        'Content-type': 'application/json',
					        'Accept': 'application/pdf'
					    }
					}).success(function(data){
						
					    var blob = new Blob([data], {
					        type: 'application/pdf'
					    });
					    var fileURL = URL.createObjectURL(blob);
					    
					    $scope.content = $sce.trustAsResourceUrl(fileURL);
					   /* console.log(blob);
					    saveAs(blob, 'WorkerMonthly_Report' + '.pdf');*/
					    $("#loader").hide();
	                  $("#data_container").show();
	                 // $('san #title').attr('title','worker Monthy Report')
					}).error(function(){	
						$("#loader").hide();
	                  $("#data_container").show();
					});
	         }
	  
		}
	}
	   
}]);
