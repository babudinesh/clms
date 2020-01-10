'use strict';

var ViewOrEditTimeUploadController = angular.module("myApp.ViewOrEditTimeByWorkerUpload", []);

ViewOrEditTimeUploadController.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                    //alert(angular.toJson(element[0].files[0]));
                });
            });
        }
    };
}]);

ViewOrEditTimeUploadController.controller('ViewOrEditTimeUploadController', ['$scope','$http', '$resource','$routeParams','$filter','myservice','$cookieStore', function ($scope,$http, $resource, $routeParams,$filter,myservice,$cookieStore) {
	
	var viewOrEditTimeWorkerDtaTable;
	 $.material.init();
	 $scope.workerVo = new Object();
	 $scope.workerVo.createdBy = $cookieStore.get('createdBy'); 
	 $scope.workerVo.modifiedBy = $cookieStore.get('modifiedBy');
	 
	 $('#todate,#fromdate').datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown'
	    	  
	    });
	 
	 $scope.errorsList = new Object();
	 $scope.byDateShow = true;
	/* $scope.missingWorkerCode = new Object();
	 $scope.missingWorkerName = new Object();
	 $scope.missingVendorName = new Object();
	 $scope.missingShift = new Object();
	 $scope.missingBusinessDate = new Object();
	 $scope.missingAttendanceStatus = new Object();*/
	 
	 
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
	        	//alert(angular.toJson(response.data));
	        	if(type == 'customerList'){        		
	        		$scope.customerList = response.data.customerList;	   
	        		
	        		  if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){          		

			                $scope.workerVo.customerId=response.data.customerList[0].customerId;	
							$scope.customerName = response.data.customerList[0].customerId;						

			                $scope.customerChange();
			                }
	        		  
	        		  
	            	   if ( ! $.fn.DataTable.isDataTable( '#viewOrEditTimeWorkerDtaTable' ) ) {        
	            		   viewOrEditTimeWorkerDtaTable = $('#viewOrEditTimeWorkerDtaTable').DataTable({        
	   	                    "columns": [
	   	                       { "data": "vendorCode" },
	   	                       { "data": "vendorName" },
	   	                       { "data": "workerCode" },
	   	                       { "data": "workerName" },
	   	                       { "data": "businessDate"},
	   	                       { "data": "shift"},
	   	                    	{ "data": "attendanceStatus"},	   	                       
	   	                       { "data": "errorDescription",className: "colored" },
	   	                       {"data": "lineNumber"}]
	   	                       
	   	                });
	                   }
	        		 
	        	}else if(type == 'customerChange'){	        		
	        		$scope.companyList = response.data; 
	        		 if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	    	                $scope.workerVo.companyId = response.data[0].id;
							$scope.companyName = response.data[0].id;
	    	                $scope.companyChange();
							$scope.fun_CompanyChangeListener();
	    	          }
	        		 
	        		
	        	}else if (type == 'companyChange') {	        		
	                $scope.vendorList = response.data.vendorList;
	                $scope.list_Country = response.data.countriesList;
	        		// alert(angular.toJson(response.data));
	        		 $scope.workerVo.countryId = response.data.countriesList[0].id;
	                
	            }else if( type ==  'schemaName'){	            	
	            	$scope.workerVo.schemaName = response.data.name;            	
	             }  /*else if(type == 'countryList'){
	            	$scope.list_Country = response.data;
	            }*/
	        },
	        function () {
	        	$scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
	        	           
	          });
	    	}    
	  
	    
//   getSchemaNameByUserId
     $scope.getData("reportController/getSchemaNameByUserId.json",{userId : $cookieStore.get('userId') } ,"schemaName" ); 
	    
	 
	 $scope.getData('vendorController/getCustomerNamesAsDropDown.json', { customerId: $cookieStore.get('customerId')}, 'customerList')
	 //$scope.getData('workerController/countriesList.json','', 'countryList')
	 
	 
	  $scope.customerChange = function () { 
	    	$scope.companyList ="";
	    	$scope.countriesList = ""; 
	    	$scope.vendorList = "";             
	    	if($scope.workerVo.customerId != undefined && $scope.workerVo.customerId != ""){
	    	$scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.workerVo.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.workerVo.companyId}, 'customerChange');
	    	}
	    };
	    
	    
	    
	    $scope.companyChange = function () {
	    	$scope.countriesList = ""; 
          $scope.vendorList = ""; 
          $scope.locationList = "";
	    	if($scope.workerVo.companyId != undefined && $scope.workerVo.companyId != ""){
	        $scope.getData('workerController/getVendorAndWorkerNamesAsDropDowns.json', { customerId: $scope.workerVo.customerId,companyId: $scope.workerVo.companyId,vendorId:0 }, 'companyChange');
	    	}
	    };
	    
	    
	    
	    
	    
	    
	    $scope.Upload =function($event){
	    	 viewOrEditTimeWorkerDtaTable.clear().draw();
	    	 $("#loader1").show();
			 $("#WorkerUploads").hide();
			// alert(angular.toJson($scope.workerVo));
	    	if($('#WorkerUploads').valid() && $scope.myFile != undefined )
    		{  	
	    	 var file = $scope.myFile;
	    	 var formData = new FormData();
	    	 formData.append('file',file);
	    	 formData.append('name',angular.toJson($scope.workerVo));
	    	 //alert($scope.myFile);
	    	 $http.post('workerController/manageTimeAndShiftFileUpload.json', formData, {
                 transformRequest: angular.identity,
                 headers: {'Content-Type': undefined}
             })
             .success(function(response){  
            	//alert(response);            	
            	//alert(angular.toJson(response));
            	//alert(response.flag[0].flag);
            	 if(response.flag[0].flag == 0){
            		 $scope.Messager('error', 'Error', response.flag[0].description,angular.element($event.currentTarget));            		 
            	 }else{
            		 $scope.Messager('success', 'success', response.flag[0].description,angular.element($event.currentTarget)); 
            		 $('#fileCsv').val("");
            	 }
            	
            	 $scope.errorsList =response.errorsList;
            	 viewOrEditTimeWorkerDtaTable.clear().rows.add(response.errorsList).draw();  
            	 $("#loader1").hide();
				 $("#WorkerUploads").show();
            	//alert( $scope.errorsList.length);
            	/* $scope.missingWorkerCode = response.missingWorkerCode;
            	 $scope.missingWorkerName = response.missingWorkerName;
            	 $scope.missingVendorName = response.missingVendorName;
            	 $scope.missingShift = response.missingShift;
            	 $scope.missingBusinessDate = response.missingBusinessDate;
            	 $scope.missingAttendanceStatus = response.missingAttendanceStatus;*/
            	 
             })
             .error(function(response){
            	// alert(angular.toJson(response));
            	 $scope.Messager('error', 'Error', 'File Upload Failed',angular.element($event.currentTarget));
            	 $("#loader1").hide();
				 $("#WorkerUploads").show();
             });
	    	 
    		}else{
    			 $scope.Messager('error', 'Error', 'Upload CSV File',angular.element($event.currentTarget));
    			 $("#loader1").hide();
				 $("#WorkerUploads").show();
    		} 
	       
	    	
	    }
	    
	    
	    $scope.downloadUploadFormat = function(fileName){
	    //	alert(fileName);
	    	$http({
			    url: ROOTURL +'reportController/downloadSampleFile.view',
			    method: 'POST',
			    data:{name:fileName},
			    responseType: 'arraybuffer',
			     //this is your json data string
			    headers: {
			        'Content-type': 'application/json',
			        'Accept': 'application/pdf'
			    }
			}).success(function(data){
			    var blob = new Blob([data], {
			        type: 'application/pdf'
			    });
			    saveAs(blob, fileName + '.csv');
			  
			}).error(function(){	
				
			});
	    }
	    
	    
	    
	    // For CSV DATA DOWNLOAD
	    
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

          if(type == 'companyDropDown'){
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
            }    
            })
            
        };
	    
	    // Customer Change Listener to get company details
	    $scope.fun_CustomerChangeListener = function(){                       
	           if($scope.customerName != undefined && $scope.customerName != ''){
	               $scope.getPostData("vendorController/getCompanyNamesAsDropDown.json",  { customerId : ($cookieStore.get('customerId') != undefined && $cookieStore.get('customerId') != null) ? $scope.customerName : 0,companyId: $cookieStore.get('companyId') != null && $cookieStore.get('companyId') != undefined ? $cookieStore.get('companyId')  : $scope.companyName } , "companyDropDown");
	           }
	    };
	    
	   // Company Change Listener to get locations details
	    $scope.fun_CompanyChangeListener = function(){
	           if($scope.companyName != undefined && $scope.companyName != '')
	                  $scope.getPostData("reportController/getVendorNamesAndDepartmentsAsDropDown.json", { customerId : $scope.customerName , companyId : $scope.companyName , vendorId : '0' } , "vendorAndDeptDrop");                
	    };
	    
	    
	    
	    
	    
	   
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
    	          	  $("#attendanceSearchForm").hide();
    	          	  
    				   $http({
    					    url: ROOTURL +'reportController/downloadExcelAttendanceReportForCSV.view',
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
    											  status : $scope.status == undefined ? 'null' : $scope.status,reportName : 'BulkTimeUploadCSV' ,
    													  schemaName : ($scope.workerVo.schemaName == undefined || $scope.workerVo.schemaName == '')? 'null' : $scope.workerVo.schemaName
    								} , //this is your json data string
    					    headers: {
    					        'Content-type': 'application/json',
    					        'Accept': 'application/CSV'
    					    }
    					}).success(function(data){
    					    var blob = new Blob([data], {
    					        type: 'application/CSV;charset=utf-8;'
    					    });
    					    saveAs(blob, 'BulkTimeUploadCSV.csv');
    					    //$scope.getArray = blob;
    					    
    					    $("#loader").hide();
    					    $("#attendanceSearchForm").show();   					   
                            
    					}).error(function(){	
    						$("#loader").hide();
    						 $("#attendanceSearchForm").show();
    					});
    	          }
    	   
    		}
      }
     
}]);


