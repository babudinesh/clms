'use strict';

var overTimeReport = angular.module("myApp.OverTimeReport", []);
 
overTimeReport.controller("OverTimeReportCtrl",  ['$scope', '$rootScope', '$http', '$resource','$location','$cookieStore','$sce', function($scope,$rootScope,$http,$resource,$location,$cookieStore,$sce) {	
       $scope.list_reportTypes = [
	                          /*  {"id":"DepartmentWiseOverTimeReport","name":"Department Wise OT Report"},*/
		                        {"id":"VendorWiseOTReport","name":"Vendor Wise Overtime Report"},
		                        {"id":"DateWiseOTReport","name":"Date Wise Overtime Report"}		                       
	                        ];
       
       $scope.list_deptreportTypes = [
  	                            {"id":"DepartmentORContractorWiseLeftJoiningreport","name":"Department Wise Left and Joining report"},
  		                        {"id":"VendorORContractorWiseLeftandJoiningreport","name":"Contractor Wise Left and Joining report "}		                       
  	                        ];
       $scope.list_searchByList = [
        	                            {"id":"Y","name":"By Date"},
        		                        {"id":"N report","name":"By Month & Year"}		                       
        	                        ];
       
       $scope.list_skills = [{"id":"Skilled","name" : "Skilled" },
    	                       {"id":"Semi Skilled","name" : "Semi Skilled" },
    	                       {"id":"High Skilled","name" : "High Skilled" },
    	                       {"id":"Special Skilled","name" : "Special Skilled" },
    	                       {"id":"UnSkilled","name" : "UnSkilled" }];
       
       $scope.monthYear = 'Y';	  
	   $scope.byDateShow = true;
       $scope.status = '';
       $scope.list_status = [{id:'', name:'Both'},{id:'Y', name:'Active'},{id:'N', name:'In Active'}];
       $scope.list_status1 = [{id:'Y', name:'Active'},{id:'N', name:'In Active'}];
       $("#data_container").hide();
       $('#todate,#fromdate').datepicker({
    	      changeMonth: true,
    	      changeYear: true,
    	      dateFormat:"dd/mm/yy",
    	      showAnim: 'slideDown'
    	    	  
    	    });
       
       $scope.list_years =  $rootScope.getYears;    
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
            } else if( type == 'companyGrid' ){
        	   if(Object.prototype.toString.call(response.data) === '[object Array]' &&  response.data.length > 0 ){
		      		$scope.customerList = response.data;
		      		if(response.data.length == 1){
	      				$scope.customerName = response.data[0].customerId;
	      				$scope.fun_CustomerChangeListener();
		      		}
	    		}	                
              } else if( type == 'gridData' ){

                     if(Object.prototype.toString.call(response.data) === '[object Array]'){                            
                            dt_attendanceReport.clear().rows.add(response.data).draw();  
                            $("#loader").hide();                         
                     }else{
                    	 $("#loader").hide();                       
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
             }else if(type == 'companyDropDownforDepts'){
            	    $scope.list_departments = response.data.departmentList;                 
             }else if(type == 'vendorAndDeptDrop'){
                    $scope.list_vendors = response.data.vendorList  ; 
                    $scope.locationsList = response.data.locationsList ;
                    $scope.shiftsList = response.data.shiftCodes;
                    if( response.data != undefined && response.data.vendorList != "" && response.data.vendorList.length == 1 ){
       	                $scope.vendorId = response.data.vendorList[0].id;					
       	                }
             }else if(type == 'workOrderDropDown'){       
                    $scope.list_workOrders = response.data;                 
             }else if(type == 'workerDropDown'){            	 
            	 	var list  = response.data;
            	 	list.splice(0, 0, {id: 0, name: "All"});
            	 	$scope.list_workers = list;            	 	
             }else if(type == 'plantsList'){
             	$scope.plantsList = response.data;
             }else if(type == 'departmentsList'){
             	$scope.departmentsList = response.data.departmentList;
             }else if(type == 'sectionsList'){
             	//$scope.departmentsList = response.data.departmentsList;
             	$scope.sectionesList = response.data.sectionsList;
             }else if(type == 'workAreasList'){
             	$scope.workAreasList = response.data;
             }else if( type ==  'schemaName'){            	 
           	  		$scope.schemaName = response.data.name; 
           	  		var CurrentScreenName = "";
           	  	 var urllink = $location.path();
		          	  if(urllink == '/OverTimeReport'){
		          		  CurrentScreenName = "Over Time Report";
		          	  } else if(urllink == '/RegisterOfOverTimeXXIII'){
		          		  CurrentScreenName = "Register of Overtime ( Form XXIII)";
		          	  } else if(urllink == '/DepartmentORContractorWiseLeftJoiningreport'){
		          		  CurrentScreenName = "Department and Contractor Wise Left and Joining report";
		          	  } else if(urllink == '/OTReportbyWorkmen'){
		          		  CurrentScreenName = "OT Report by Workmen";
		          	  } else if(urllink == '/RegisterofContractors'){
		          		  CurrentScreenName = "Register of Contractors";
		          	  } else if(urllink == '/RegisterOfWages'){
		          		  CurrentScreenName = "Register of Wages";
		          	  } else if(urllink == '/AttendanceSummaryReportByWorkmen'){
		          		  CurrentScreenName = "Attendance Summary Report By Workmen";
		          	  } else if(urllink == '/EmploymentCard'){
		          		  CurrentScreenName = "Employment Card (Form XIV)";
		          	  } else if(urllink == '/ServiceCertificate'){
		          		  CurrentScreenName = "Service Certificate (Form XV)";
		          	  }
		          	  $scope.getPostData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName: CurrentScreenName}, 'buttonsAction');
		             }  
             })
            	
         };
//       getSchemaNameByUserId
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
          if($scope.companyName != undefined && $scope.companyName != ''){
                 $scope.getPostData("reportController/getVendorNamesAndDepartmentsAsDropDown.json", { customerId : $scope.customerName , companyId : $scope.companyName , vendorId : $cookieStore.get('vendorId') != undefined && $cookieStore.get('vendorId') != '' ? $cookieStore.get('vendorId') : 0  } , "vendorAndDeptDrop");
                // $scope.getPostData("CompanyController/getLocationsByCompanyId.json", { customerId : $scope.customerName , companyId : $scope.companyName } , "companyDropDownforDepts");
          }
   };
   
   //fun Vendor Change Listener
   $scope.fun_VendorChangeListener = function(){
          if($scope.vendorId != undefined && $scope.vendorId != '')
                //$scope.getPostData("associateWorkOrderController/getVendorAssociatedWorkOrder.json", { customerDetailsId : $scope.customerName , companyDetailsId : $scope.companyName , vendorDetailsId : $scope.vendorId} , "workerDropDown");
      			$scope.getPostData("workerJobDetailsController/getWorkerNamesAsDropDown.json", { customerId : $scope.customerName , companyId : $scope.companyName , vendorId : $scope.vendorId } , "workerDropDown");
          else{
        	  	var list  = [];        	
      	 		list.splice(0, 0, {id: 0, name: "All"});
      	 		$scope.list_workers = list;      	 	
          }
   }  
  
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
   		$scope.getPostData('associatingDepartmentToLocationPlantController/getDepartMentDetailsByLocationAndPlantId.json', {customerId:$cookieStore.get('customerId'), companyId: $cookieStore.get('companyId'), locationId:$scope.locationId,plantId:$scope.plantId}, 'departmentsList');
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
   
   $scope.fun_clearSsearchFields = function(){
      
	   $("#data_container").hide();
   }
   
   $scope.fun_pdf_Download = function(){ 
	  
	   var fromdate, todate, from, to, duration;
	   	  if($scope.monthYear == 'Y'){
	   		  fromdate =  moment($('#fromdate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
				  todate = moment($('#todate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') ;
	   	  }else{
	   		 fromdate = $scope.year+'-'+$scope.month+'-01';
				  todate = $scope.year+'-'+$scope.month+'-'+daysInMonth($scope.month,$scope.year);
	   	  }

			from = moment(fromdate, 'YYYY-MM-DD'); // format in which you have the date
			to = moment(todate, 'YYYY-MM-DD');     // format in which you have the date

			duration = to.diff(from, 'days');    

		/*if($('#OverTimeReportForm').valid() && duration != 'NAN' && duration > 31 ){
			 alert(" Number of days should not be more than 31 days ");
			 return;
		} else */
			if($('#OverTimeReportForm').valid() &&  duration != 'NAN' && duration < 0 ){
			alert("To date should be greater than From date..");
			return;
		}
		   	
	    if($('#OverTimeReportForm').valid() && moment(new Date($('#fromdate').val())).isAfter(moment(new Date($('#todate').val())))){
		    alert(" To date should be greater than From date..");
		    return;
	   }else {			  
	          if($('#OverTimeReportForm').valid()){ 
	        	  $scope.reporttodate = $('#todate').val();
	              $scope.reportFromdate = $('#fromdate').val();
	        	  $("#loader").show();	          	 
	        	  $("#data_container").hide();
				   $http({
					    url: ROOTURL +'reportController/overTimeReport.view',
					    method: 'POST',
					    responseType: 'arraybuffer',
					    data: {customerId : $scope.customerName == undefined ? '0' : $scope.customerName, 
								  companyId : $scope.companyName == undefined ? '0' : $scope.companyName  ,
										  vendorId: ($scope.vendorId == undefined || $scope.vendorId == '')? '0' : $scope.vendorId  ,	
										  workerName: ($scope.workerName == undefined || $scope.workerName == '')? '' : $scope.workerName  ,
										  workerId: ($scope.workerId == undefined || $scope.workerId == '')? '0' : $scope.workerId  ,
										  departmentId: ($scope.departmentId == undefined || $scope.departmentId == '')? '0' : $scope.departmentId  ,
										  workerCode: ($scope.workerCode == undefined || $scope.workerCode == '')? '-1' : $scope.workerCode  ,
									      fromdate : fromdate ,
										  todate : todate  ,
										  status : ($scope.status == undefined || $scope.status == '')? '' : $scope.status  ,
										  reportName :  $scope.reportName , 
										  schemaName : ($scope.schemaName == undefined || $scope.schemaName == '') ? 'null' : $scope.schemaName,
										  plantId : $scope.plantId,
							    	      locationId:$scope.locationId,
							    	      plantName: $('#plantId option:selected').html(),
							    	      locationName:$('#locationId option:selected').html(),
							    	      companyName:$('#companyName option:selected').html(),
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
					    saveAs(blob, $scope.reportName+ '.pdf');
					    $("#loader").hide();					   
					}).error(function(){	
						$("#loader").hide();
					});
	          }
	   
		}
   }
 
   $scope.fun_previewGridData = function(){ 
		  
	   var fromdate, todate, from, to, duration;
	   	  if($scope.monthYear == 'Y'){
	   		  fromdate =  moment($('#fromdate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
				  todate = moment($('#todate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') ;
	   	  }else{
	   		 fromdate = $scope.year+'-'+$scope.month+'-01';
				  todate = $scope.year+'-'+$scope.month+'-'+daysInMonth($scope.month,$scope.year);
	   	  }

			from = moment(fromdate, 'YYYY-MM-DD'); // format in which you have the date
			to = moment(todate, 'YYYY-MM-DD');     // format in which you have the date

			duration = to.diff(from, 'days');    

		/*if($('#OverTimeReportForm').valid() && duration != 'NAN' && duration > 31 ){
			 alert(" Number of days should not be more than 31 days ");
			 return;
		} else */
			if($('#OverTimeReportForm').valid() &&  duration != 'NAN' && duration < 0 ){
			alert("To date should be greater than From date..");
			return;
		}
		   	
	    if($('#OverTimeReportForm').valid() && moment(new Date($('#fromdate').val())).isAfter(moment(new Date($('#todate').val())))){
		    alert(" To date should be greater than From date..");
		    return;
	   }else {			  
	          if($('#OverTimeReportForm').valid()){ 
	        	  $scope.reporttodate = $('#todate').val();
	              $scope.reportFromdate = $('#fromdate').val();
	        	  $("#loader").show();	          	 
	        	  $("#data_container").hide();
				   $http({
					    url: ROOTURL +'reportController/overTimeReport.view',
					    method: 'POST',
					    responseType: 'arraybuffer',
					    data: {customerId : $scope.customerName == undefined ? '0' : $scope.customerName, 
								  companyId : $scope.companyName == undefined ? '0' : $scope.companyName  ,
										  vendorId: ($scope.vendorId == undefined || $scope.vendorId == '')? '0' : $scope.vendorId  ,	
										  workerName: ($scope.workerName == undefined || $scope.workerName == '')? '' : $scope.workerName  ,
										  workerId: ($scope.workerId == undefined || $scope.workerId == '')? '0' : $scope.workerId  ,
										  departmentId: ($scope.departmentId == undefined || $scope.departmentId == '')? '0' : $scope.departmentId  ,
										  workerCode: ($scope.workerCode == undefined || $scope.workerCode == '')? '-1' : $scope.workerCode  ,
									      fromdate : fromdate ,
										  todate : todate  ,
										  status : ($scope.status == undefined || $scope.status == '')? '' : $scope.status  ,
										  reportName :  $scope.reportName , 
										  schemaName : ($scope.schemaName == undefined || $scope.schemaName == '') ? 'null' : $scope.schemaName,
										  plantId : $scope.plantId,
										  locationId:$scope.locationId,
										  plantName: $('#plantId option:selected').html(),
							    	      locationName:$('#locationId option:selected').html(),
							    	      companyName:$('#companyName option:selected').html(),
							    	      departmentName:$('#departmentId option:selected').html(),
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
					    $("#loader").hide();
					    $("#data_container").show();
					}).error(function(){	
						$("#loader").hide();
					});
	          }
	   
		}
   }
   
   var year = moment().year();
   var month = moment().month()+1;
   
   $scope.fun_date_year_Listener = function(){		  
	  if($scope.monthYear== 'Y'){		  
		  $scope.byDateShow = true;
		  $scope.ByMonthYearShow = false;
		  $scope.year ="";
		  $scope.month = "";
	  }else{
		  $scope.byDateShow = false;
		  $scope.ByMonthYearShow = true;
		  $scope.year =year+"";
		  $scope.month = month+"" ;
		  $('#fromdate').val('');
		  $('#todate').val('');
	  }
   }
   
   
   function daysInMonth(month,year) {
	    return new Date(year, month, 0).getDate();
	}
   
   
   
}]);
