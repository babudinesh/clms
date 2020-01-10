'use strict';
var WageCalculationController = angular.module("myApp.WageCalculation", ['ngCookies']);

WageCalculationController.directive('onLastRepeat', function () {
    return function (scope, element, attrs) {
        if (scope.$last) {
            setTimeout(function () {
                $('#wageRateTable').DataTable({
                	scrollX : true,
                	scrollY : '300',
                	fixedHeader : true,
                	sort : false,
                	retrieve: true
                });
            }, 1);
        }
    };
})

WageCalculationController.controller("WageCalculationCtrl", ['$scope', '$http', '$resource', '$routeParams', '$location', '$filter','$cookieStore', function ($scope, $http, $resource, $routeParams, $location, $filter, $cookieStore) {	
	$scope.year = new Date(moment(new Date(), 'DD/MM/YYYY').format('YYYY-MM-DD')).getFullYear();
	$scope.month = new Date(moment(new Date(), 'DD/MM/YYYY').format('YYYY-MM-DD')).getMonth()+1;
	
	$scope.yearList = [{"id":2018,"name":2018},{"id":2019,"name":2019},{"id":2020,"name":2020}];
	                   
	$scope.monthList = [{"id":1,"name":"January"},
                        {"id":2,"name":"February"},
                        {"id":3,"name":"March"},
                        {"id":4,"name":"April"},
                        {"id":5,"name":"May"},
                        {"id":6,"name":"June"},
                        {"id":7,"name":"July"},
                        {"id":8,"name":"August"},
                        {"id":9,"name":"September"},
                        {"id":10,"name":"October"},
                        {"id":11,"name":"November"},
                        {"id":12,"name":"December"}];
	
	 // $scope.result = [{otHours : 3, workerCode : 'workerCode', workerName : 'workerName', workSkill : 'workSkill', jobcode : 'jobcode', payableDays : 'payableDays',overTimeHours: 3}];
	
	$scope.wageCalculationTableView  = false;
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
	            url: ROOTURL+url,
	            headers: {
	                'Content-Type': 'application/json'
	            },
	            data:data
	        }
	        $http(req).then(function (response) {
	        	if(type == 'buttonsAction'){
	        		$scope.buttonArray = response.data.screenButtonNamesArray;
	        	} else if (type == 'companyGrid'){
	            	$scope.customerList = response.data.customerList;
		      		 if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
	   		                $scope.customerName=response.data.customerList[0].customerId;		                
	   		                $scope.fun_CustomerChangeListener();
		              }	   
		      		 // for button views
		      		if($scope.buttonArray == undefined || $scope.buttonArray == '')
		      		$scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Wage Calculation'}, 'buttonsAction');
	            }else if(type == 'companyDropDown'){
		      		$scope.companyList = response.data;
		      	  if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	 	                $scope.companyName = response.data[0].id;
	 	               $scope.fun_CompanyChangeListener();
	 	                }
		      	}else if(type== 'vendorDropDown'){
		      		$scope.vendorList = response.data.vendorList;		      	
		      	}else if(type== 'vendorChange'){
		      		var list  = response.data;
		      		list.splice(0, 0,
		      			    {id: 0, name: "All"}
		      			);
		      		$scope.workerList = list;
		      	}else if(type=='search'){
	            	$scope.wageCalculationTableView  = true;	            	
	                $scope.result = response.data;
	            }else if(type=='save'){	            		            	               
	                if(response.data != undefined && response.data > 0){
	                	$scope.Messager('success', 'success', 'Records Saved Successfully',true);
	                }else{
	                	$scope.Messager('error', 'Error', 'Technical Issue Please try after some time',true);
	                }
	            }else if( type ==  'schemaName'){            	 
	             	  $scope.schemaName = response.data.name;            
	             } 
	        },
	        function(){
	        	$scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable); 
	        });
	    }

	    // for getting Master data for Grid List 
	    $scope.getData("vendorController/getCustomerNamesAsDropDown.json",{ customerId: $cookieStore.get('customerId')}, "companyGrid" );
	    
	    
//      getSchemaNameByUserId
        $scope.getData("reportController/getSchemaNameByUserId.json",{userId : $cookieStore.get('userId') } ,"schemaName" ); 
        
	 	  	     
	    // Customer Change Listener to get company details
	    $scope.fun_CustomerChangeListener = function(){	 	   
	 	   if($scope.customerName != undefined && $scope.customerName != '')
	 	   $scope.getData("vendorController/getCompanyNamesAsDropDown.json",  { customerId : $scope.customerName,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : (($scope.companyName != undefined) ? $scope.companyName :0)} , "companyDropDown");	   
	    };
	    
	   // Company Change Listener to get locations details
	    $scope.fun_CompanyChangeListener = function(){
	 	   if($scope.companyName != undefined && $scope.companyName != '')
	 		   $scope.getData("WageRateController/getVendorNamesAsDropDown.json", { customerId : $scope.customerName , companyId : $scope.companyName } , "vendorDropDown");
	    };
	    
	    $scope.fun_vendorChangeListener = function(){
		 	   if($scope.vendorName != undefined && $scope.vendorName != '')
		 		  $scope.getData('workerBadgeGenerationController/getWorkersByVendorId.json', { customerDetailsId: $scope.customerName , companyDetailsId: $scope.companyName, vendorDetailsId: $scope.vendorName  }, 'vendorChange');		 	   		 		  
		    };
	    

	    $scope.search = function () {
	    	
	    	 if($('#wageCalculatorForm').valid()){ 
	    		 /* $scope.result = [];
	    		  if($.fn.dataTable.isDataTable()){
	    		  $('#wageRateTable').DataTable( {
	    			  retrieve: true   			    
	    			} );
	    		  }*/
	    		  
	    		 $scope.getData('wageCalculatorController/getWageCalculatorList.json', { customerId: ($scope.customerName == undefined || $scope.customerName.customerId == undefined )? '0': $scope.customerName,
								 companyId: ($scope.companyName == undefined || $scope.companyName == undefined )? '0':$scope.companyName,
								 vendorId: ($scope.vendorName == undefined || $scope.vendorName == '' )? '0':$scope.vendorName,
								 workerId: ($scope.workerName == undefined || $scope.workerName == '' )? '0':$scope.workerName,
								 month: ( $scope.month == undefined || $scope.month == '' )? '0': $scope.month,
								 year:( $scope.year == undefined || $scope.year == '' )? '0': $scope.year,
								schema : ( $scope.schemaName == undefined ||  $scope.schemaName == null ||  $scope.schemaName == '' ) ? '': $scope.schemaName		 
								 },
								'search');
	    	 }
	    };
	    $scope.clear = function () {
	    	 $scope.customerName="";
	    	 $scope.companyName="";	    	  
	    };
	    
	    
	    $scope.totalEarningsCalculation = function(index){
	    	var data = $scope.result[index];
	    	$scope.result[index].totalEarnings =  parseFloat((data.dailyRatePerDay == undefined || data.dailyRatePerDay == '') ? 0 : (data.dailyRatePerDay*data.payableDays) ) +  parseFloat( (data.baseRatePerDay  == undefined || data.baseRatePerDay == '') ? 0 : (data.baseRatePerDay*data.payableDays) ) +  parseFloat((data.overTimeRatePerHour == undefined || data.overTimeRatePerHour == '') ? 0 : (data.overTimeRatePerHour*data.overTimeHours)) + parseFloat((data.additionalAllowance == undefined || data.additionalAllowance == '') ? 0 : data.additionalAllowance) + parseFloat((data.oneTimeEarnings == undefined || data.oneTimeEarnings == '') ? 0 : data.oneTimeEarnings) ;
	    	$scope.result[index].grossSalaray = $scope.result[index].totalEarnings;
	    	$scope.result[index].netSalary = parseFloat($scope.result[index].totalEarnings) - parseFloat(($scope.result[index].totalDeductions == undefined || $scope.result[index].totalDeductions == '') ? 0 : $scope.result[index].totalDeductions );
	    }
	    
	    $scope.totalDeductionsCalculation = function(index){
	    	var data = $scope.result[index];
	    	$scope.result[index].totalDeductions =  parseFloat((data.pf == undefined || data.pf == '') ? 0 : data.pf ) +  parseFloat( (data.esi  == undefined || data.esi == '') ? 0 : data.esi ) +  parseFloat((data.lwf == undefined || data.lwf == '') ? 0 : data.lwf) + parseFloat((data.pt == undefined || data.pt == '') ? 0 : data.pt) + parseFloat((data.advance == undefined || data.advance == '') ? 0 : data.advance) + parseFloat((data.damageOrLoss == undefined || data.damageOrLoss == '') ? 0 : data.damageOrLoss) + parseFloat((data.fines == undefined || data.fines == '') ? 0 : data.fines) ;
	    	$scope.result[index].netSalary = parseFloat($scope.result[index].totalEarnings) - parseFloat(($scope.result[index].totalDeductions == undefined || $scope.result[index].totalDeductions == '') ? 0 : $scope.result[index].totalDeductions );
	    }
	    
	    $scope.fun_SaveCalculationDetails = function(){
	    	if($('#wageCalculatorForm').valid()){ 
	    		
	    		 $scope.wageCalculator =new Object();
	    		 $scope.wageCalculator.year =$scope.year;
    			 $scope.wageCalculator.month = $scope.month;
				 $scope.wageCalculator.createdBy =$cookieStore.get('createdBy'); 
			 	 $scope.wageCalculator.modifiedBy = $cookieStore.get('modifiedBy'); 				 
	    		 $scope.wageCalculator.wageCalculatorVo = $scope.result;
	    		 $scope.getData('wageCalculatorController/saveWageCalculator.json', angular.toJson($scope.wageCalculator), 'save');
	    	 }
	    	
	    	
	    }
	    
}]);





