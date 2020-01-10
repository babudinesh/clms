'use strict';

var workerDetails;
var workerVerification = angular.module("myApp.ViewOrEditTimeByWorkerCtrl", []);
workerVerification.controller("ViewOrEditTimeByWorkerCtrl", ['$scope', '$http', '$resource', '$routeParams', '$location', '$filter','$cookieStore', function ($scope, $http, $resource, $routeParams, $location, $filter, $cookieStore) {
	$scope.monthYear = 'Y';
	var year = moment().year();
	var month = moment().month()+1;
	$scope.byDateShow = true;
	/*$('#fromdate,#todate').bootstrapMaterialDatePicker({
        time : false,
        Button : true,
        format : "DD/MM/YYYY",
        clearButton: true
    });*/
	$('#fromdate,#todate').datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown'
	    	  
	    });
	 
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
	 
		$scope.workerVo= new Object();
	    $scope.getData = function (url, data, type) {
	    
	        var req = {
	            method: 'POST',
	            url: ROOTURL+url,
	            headers: {
	                'Content-Type': 'application/json'
	            },
	            data:data
	        }
	        $http(req).then(function (response) {           
	            if (type == 'customerList')
	            {
	                $scope.customerList = response.data.customerList;
	                if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
			                $scope.workerVo.customerId=response.data.customerList[0].customerId;		                
			                $scope.customerChange();
			                }	              
	                
	            }else if(type == 'customerChange'){	        		
	        		$scope.companyList = response.data; 
	        		
	        		 if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	    	                $scope.workerVo.companyId = response.data[0].id;
	    	                $scope.companyChange();
	    	                }
	        		 
	        	}else if (type == 'companyChange') {       		
	                $scope.vendorList = response.data.vendorList;      
	               
	            }
	            else if(type=='search')
	            {                          
	                
	            	$location.path('/WorkerTimeModificationPage');
	                    
	            }
	        },
	        function () {
	        	//alert('Error') 
	        	
	        });
	    }   

	    $scope.getData('vendorController/getCustomerNamesAsDropDown.json', { customerId: $cookieStore.get('customerId')}, 'customerList')
	    
	   $scope.customerChange = function () {
		    	if($scope.workerVo.customerId != undefined && $scope.workerVo.customerId != ""){
		    	$scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.workerVo.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.workerVo.companyId != undefined ? $scope.workerVo.companyId : 0}, 'customerChange');
		    	}
		    };
		    
		    
		    
		    $scope.companyChange = function () {
		    	if($scope.workerVo.companyId != undefined && $scope.workerVo.companyId != ""){
		        $scope.getData('vendorController/getVendorAndLocationNamesAsDropDowns.json', { customerId: $scope.workerVo.customerId,companyId: $scope.workerVo.companyId }, 'companyChange');
		    	}
		    };
	      
	      
	      
	    $scope.search = function ($event) {	    	
	        if($('#viewOrEditWorkerTimeForm').valid()){
	        	
	        	 var fromDate = $('#fromdate').val(), toDate = $('#todate').val(), from, to, duration;
	        	 from = moment(fromDate, 'DD/MM/YYYY'); // format in which you have the date
	        	 to = moment(toDate, 'DD/MM/YYYY');     // format in which you have the date
	        	 duration = to.diff(from, 'days') ;
	        	 
	        	 if(duration != 'NAN' && duration > 31 ){
	        		 alert(" Number of days should not be more than 31 days ");
	        		 return;
	        	} else if(duration != 'NAN' && duration < 0 ){
	        		alert("To date should be greater than From date..");
	        		return;
	        	}
	        	 
	        	 if( moment($('#fromdate').val()).isAfter($('#todate').val())){
	     		    alert(" To date should be greater than From date..");
	     		   return;
	     	   }else  if(($scope.workerVo.workerCode != undefined && $scope.workerVo.workerCode != '') || ($scope.workerVo.firstName != undefined && $scope.workerVo.firstName != '') ){
	        		 $scope.workerVo.workerCode = $scope.workerVo.workerCode != null ? $scope.workerVo.workerCode : 0;
	        		 $scope.workerVo.firstName = $scope.workerVo.firstName != null ? $scope.workerVo.firstName : "";
	        		 
	        		 $scope.workerVo.bussinessFromDate = ($('#fromdate').val() != '' && $('#fromdate').val() != undefined) ? moment($('#fromdate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') : ''; 
	        		 $scope.workerVo.bussinessEndDate = ($('#todate').val() != '' && $('#todate').val() != undefined) ? moment($('#todate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') : '';
	        		 
	        		 $scope.workerVo.year  = $scope.workerVo.year != undefined && $scope.workerVo.year != '' ? $scope.workerVo.year : 0;
	        		 $scope.workerVo.month =  $scope.workerVo.month != undefined && $scope.workerVo.month != '' ? $scope.workerVo.month : 0;
	        	  		$cookieStore.put('workerVo',$scope.workerVo);	        		
		        		$location.path('/WorkerTimeModificationPage');
	        	}else{
	        		 $scope.Messager('error', 'Error', 'Enter Worker Code or Worker Name',angular.element($event.currentTarget));
	        	}
	        	
	        }
	    };
	    
	    
	    
	    $scope.clear = function () {
	    	$scope.workerVo = new Object();
	          
	    };
	    
	    $scope.fun_date_year_Listener = function(){	 	   
	 	  if($scope.monthYear== 'Y'){		  
	 		  $scope.byDateShow = true;
	 		  $scope.ByMonthYearShow = false;
	 		  $scope.workerVo.year ="";
	 		  $scope.workerVo.month = "";	 		 		  
	 	  }else{	 		  
	 		  $scope.byDateShow = false;
	 		  $scope.ByMonthYearShow = true;
	 		  $scope.workerVo.year =year+"";
	 		  $scope.workerVo.month = month+"";
	 		  $('#fromdate').val('');
	 		  $('#todate').val('');
	 	  }
	    }
	    
}]
);





