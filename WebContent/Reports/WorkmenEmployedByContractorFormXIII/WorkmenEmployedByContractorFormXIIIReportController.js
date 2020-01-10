'use strict';

var workmenEmployedByContractorFormXIIIReport = angular.module("myApp.WorkmenEmployedByContractorFormXIIIReport", []);
 
workmenEmployedByContractorFormXIIIReport.controller("workmenEmployedByContractorFormXIIIReportCtrl",  ['$scope', '$rootScope', '$http', '$resource','$location','$cookieStore','$sce', function($scope,$rootScope,$http,$resource,$location,$cookieStore,$sce) {
       
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
       
       $scope.list_status = [{id:'Y', name:'Active'},{id:'N', name:'In Active'}];
       $("#data_container").hide();
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
      	       // for button views
      		   if($scope.buttonArray == undefined || $scope.buttonArray == '')
      			   	$scope.getPostData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Contractor Wise Master List ( Form XIII)'}, 'buttonsAction');
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
                  $scope.list_vendors = response.data.vendorList;          
                  $scope.shiftsList = response.data.shiftCodes;
                  if( response.data != undefined && response.data.vendorList != "" && response.data.vendorList.length == 1 ){
     	                $scope.vendorId = response.data.vendorList[0].id;					
     	                }else{
     	                	$scope.vendorId = 0;
     	                }
           }else if(type == 'workOrderDropDown'){       
                  $scope.list_workOrders = response.data;                 
           }else if(type == 'workerDropDown'){
          	 	var list  = response.data;
          	 	//alert(list.length+" :: "+response.data.length);          	 	
          	 	list.splice(0, 0, {id: 0, name: "All"});
          	 	$scope.list_workers = list;
          	 	//alert($scope.list_workers.length+" :: "+list.length+" :: "+response.data.length);
           } else if(type == 'plantsList'){
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
           }else if(type == 'VendorChange'){
        	   $scope.locationsList = response.data.locationList ;
        	   if( response.data.locationsList != undefined && response.data.locationsList[0] != "" && response.data.locationsList.length == 1 ){
  	                $scope.locationId = response.data.locationsList[0].id;
  	               // 
  	           }
       	   $scope.locationChange();
           }
           })
          	
       };
       
         
//     getSchemaNameByUserId
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
                     $scope.getPostData("reportController/getVendorNamesAndDepartmentsAsDropDown.json", { customerId : $scope.customerName , companyId : $scope.companyName , vendorId : '0' } , "vendorAndDeptDrop");
                     $scope.getPostData("CompanyController/getLocationsByCompanyId.json", { customerId : $scope.customerName , companyId : $scope.companyName } , "companyDropDownforDepts");
              }
       };
       
       //fun Vendor Change Listener
       $scope.fun_VendorChangeListener = function(){
              if($scope.vendorId != undefined && $scope.vendorId != ''){
            	  $scope.getPostData('vendorComplianceController/getLocationListByVendor.json',{customerId: ($scope.customerName == undefined || $scope.customerName == null )? '0' : $scope.customerName, companyId : ($scope.companyName == undefined || $scope.companyName == null) ? '0' : $scope.companyName, vendorId : ($scope.vendorId == undefined || $scope.vendorId == null) ? '0' : $scope.vendorId } , 'VendorChange');
              }else{
            	$scope.locationsList = [];
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

		duration = to.diff(from, 'days')     

		/*if($('#WorkmenEmployedByContractorFormXIIIReportForm').valid() && duration != 'NAN' && duration > 31 ){
			 alert(" Number of days should not be more than 31 days ");
			 return;
		} else*/
	   if($('#WorkmenEmployedByContractorFormXIIIReportForm').valid() &&  duration != 'NAN' && duration < 0 ){
			alert("To date should be greater than From date..");
			return;
		}
		   	
	    if($('#WorkmenEmployedByContractorFormXIIIReportForm').valid() && moment(new Date($('#fromdate').val())).isAfter(moment(new Date($('#todate').val())))){
		    alert(" To date should be greater than From date..");
		    return;
	   }else {			  
	          if($('#WorkmenEmployedByContractorFormXIIIReportForm').valid()){ 
	        	  $scope.reporttodate = $('#todate').val();
	              $scope.reportFromdate = $('#fromdate').val();
	        	  $("#loader").show();
	          	  $("#data_container").hide();
	          	  
				   $http({
					    url: ROOTURL +'reportController/workmenEmployedByContractorFormXIIIReport.view',
					    method: 'POST',
					    responseType: 'arraybuffer',
					    data: {customerId : $scope.customerName == undefined ? '0' : $scope.customerName, 
								  companyId : $scope.companyName == undefined ? '0' : $scope.companyName  ,
										  vendorId: ($scope.vendorId == undefined || $scope.vendorId == '')? '0' : $scope.vendorId  ,	
										  workerId: ($scope.workerId == undefined || $scope.workerId == '')? '0' : $scope.workerId  ,
										  workerCode: ($scope.workerCode == undefined || $scope.workerCode == '')? '-1' : $scope.workerCode  ,
										  workerName: ($scope.workerName == undefined || $scope.workerName == '')? '' : $scope.workerName  ,											
										  JoiningDateFrom : fromdate,
										  JoiningDateTo :todate ,
										  status : $scope.status,
										  reportName : 'WorkmenEmployedByContractorFormXIIIReport',
										  schemaName : ($scope.schemaName == undefined || $scope.schemaName == '') ? 'null' : $scope.schemaName,
										  plantId : $scope.plantId,
							    	      locationId:($scope.locationId != undefined && $scope.locationId != null && $scope.locationId != "") ? $scope.locationId : 'null',
							    	      plantName: $('#plantId option:selected').html(),
							    	      locationName:$('#locationId option:selected').html(),
							    	      companyName:$('#companyName option:selected').html(),
							    	      //jobType: $scope.jobType,
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
					    saveAs(blob, 'Workmen_Employed_By_Contractor_Form_XIII_Report' + '.pdf');
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

		duration = to.diff(from, 'days')     

		/*if($('#WorkmenEmployedByContractorFormXIIIReportForm').valid() && duration != 'NAN' && duration > 31 ){
			 alert(" Number of days should not be more than 31 days ");
			 return;
		} else*/
	   if($('#WorkmenEmployedByContractorFormXIIIReportForm').valid() &&  duration != 'NAN' && duration < 0 ){
			alert("To date should be greater than From date..");
			return;
		}
		   	
	    if($('#WorkmenEmployedByContractorFormXIIIReportForm').valid() && moment(new Date($('#fromdate').val())).isAfter(moment(new Date($('#todate').val())))){
		    alert(" To date should be greater than From date..");
		    return;
	   }else {			  
	          if($('#WorkmenEmployedByContractorFormXIIIReportForm').valid()){ 
	        	  $scope.reporttodate = $('#todate').val();
	              $scope.reportFromdate = $('#fromdate').val();
	        	  $("#loader").show();
	          	  $("#data_container").hide();
	          	  
				   $http({
					    url: ROOTURL +'reportController/workmenEmployedByContractorFormXIIIExcelReport.view',
					    method: 'POST',
					    responseType: 'arraybuffer',
					    data: {customerId : $scope.customerName == undefined ? '0' : $scope.customerName, 
								  companyId : $scope.companyName == undefined ? '0' : $scope.companyName  ,
										  vendorId: ($scope.vendorId == undefined || $scope.vendorId == '')? '0' : $scope.vendorId  ,	
										  workerId: ($scope.workerId == undefined || $scope.workerId == '')? '0' : $scope.workerId  ,
										  workerCode: ($scope.workerCode == undefined || $scope.workerCode == '')? '-1' : $scope.workerCode  ,
										  workerName: ($scope.workerName == undefined || $scope.workerName == '')? '' : $scope.workerName  ,
										  departmentId: ($scope.departmentId == undefined || $scope.departmentId == '')? '0' : $scope.departmentId  ,
										  fromdate : (fromdate == undefined || fromdate == null || fromdate == '' || fromdate == 'Invalid date') ? todate : fromdate,
										  todate :todate ,
										  status : $scope.status,
										  reportName : 'WorkmenEmployedByContractorFormXIIIExcelReport'  
								} , //this is your json data string
					    headers: {
					    	'Content-type': 'application/json',
					        'Accept': 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
					    }
					}).success(function(data){
					    var blob = new Blob([data], {
					        type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
					    });
					    saveAs(blob, 'Workmen_Employed_By_Contractor_Form_XIII_Report' + '.xlsx');
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

			duration = to.diff(from, 'days')     

			/*if($('#WorkmenEmployedByContractorFormXIIIReportForm').valid() && duration != 'NAN' && duration > 31 ){
				 alert(" Number of days should not be more than 31 days ");
				 return;
			} else*/
		   if($('#WorkmenEmployedByContractorFormXIIIReportForm').valid() &&  duration != 'NAN' && duration < 0 ){
				alert("To date should be greater than From date..");
				return;
			}
			   	
		    if($('#WorkmenEmployedByContractorFormXIIIReportForm').valid() && moment(new Date($('#fromdate').val())).isAfter(moment(new Date($('#todate').val())))){
			    alert(" To date should be greater than From date..");
			    return;
		   }else {			  
		          if($('#WorkmenEmployedByContractorFormXIIIReportForm').valid()){ 
		        	  $scope.reporttodate = $('#todate').val();
		              $scope.reportFromdate = $('#fromdate').val();
		        	  $("#loader").show();
		          	  $("#data_container").hide();
		          	  
					   $http({
						    url: ROOTURL +'reportController/workmenEmployedByContractorFormXIIIReport.view',
						    method: 'POST',
						    responseType: 'arraybuffer',
						    data: {customerId : $scope.customerName == undefined ? '0' : $scope.customerName, 
									  companyId : $scope.companyName == undefined ? '0' : $scope.companyName  ,
											  vendorId: ($scope.vendorId == undefined || $scope.vendorId == '')? '0' : $scope.vendorId  ,	
											  workerId: ($scope.workerId == undefined || $scope.workerId == '')? '0' : $scope.workerId  ,
											  workerCode: ($scope.workerCode == undefined || $scope.workerCode == '')? '-1' : $scope.workerCode  ,
											  workerName: ($scope.workerName == undefined || $scope.workerName == '')? '' : $scope.workerName  ,											
											  JoiningDateFrom : fromdate,
											  JoiningDateTo :todate ,
											  status : $scope.status,
											  reportName : 'WorkmenEmployedByContractorFormXIIIReport',
											  schemaName : ($scope.schemaName == undefined || $scope.schemaName == '') ? 'null' : $scope.schemaName,
											  plantId : $scope.plantId,
								    	      locationId:$scope.locationId,
								    	      plantName: $('#plantId option:selected').html(),
								    	      locationName:$('#locationId option:selected').html(),
								    	      companyName:$('#companyName option:selected').html(),
								    	      //jobType: $scope.jobType,
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
		  $scope.month =  month+"" ;
		  $('#fromdate').val('');
		  $('#todate').val('');
	  }
   }
   
   
   function daysInMonth(month,year) {
	    return new Date(year, month, 0).getDate();
	}
   
   
   
}]);
