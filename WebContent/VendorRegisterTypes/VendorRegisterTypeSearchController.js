'use strict';

var vendorRegisterTypesCtrl = angular.module("myApp.vendorRegisterTypes", []);
 
//vendorRegisterTypesCtrl.controller("vendorRegisterTypeSearch",  ['$scope', '$rootScope', '$http', '$resource','$location','$cookieStore','$sce','$compile','$routeParams', function($scope,$rootScope,$http,$resource,$location,$cookieStore,$sce,$compile,$routeParams) {
vendorRegisterTypesCtrl.controller("vendorRegisterTypeSearch", ['$window','$scope', '$http', '$resource', '$location','$filter','$cookieStore','$compile', '$routeParams', function ($window,$scope, $http, $resource, $location, $filter, $cookieStore,$compile,$routeParams) {
	
		var dt_attendanceReport ;           
		$scope.dataAvail = false;
	 	$("#workerDiv").hide();
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
       
       
	 	$scope.list_RegisterTypes = [{"id":"Advance","name":"Advance"},
		                             {"id":"Fines","name":"Fines"},
		                             {"id":"DamageOrLoss","name":"DamageOrLoss"}];
       
   
       
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

        	   if ( ! $.fn.DataTable.isDataTable( '#attendanceReportTables' ) ) {        		   
        		   dt_attendanceReport = $('#attendanceReportTables').DataTable({        
                       "columns": [
                          { "data": "vendorCode" },
                          { "data": "vendorName" },
                          { "data": "workerCode" },
                          { "data": "workerName" },
                          { "data": "registerType" },
                          { "data": "workerId"   ,
                          "render": function ( data, type, full, meta ) {                        	
                 		      return '<a onclick="angular.element(this).scope().fun_returnScreen('+data+')">Details</a>';
                 		    } 
                          }
                          ],
                 });  
        		}
        	   
        	   
                                
              } else if( type == 'gridData' ){
                     if(Object.prototype.toString.call(response.data) === '[object Array]'){     
                            dt_attendanceReport.clear().rows.add(response.data).draw();  
                            //alert(angular.toJson(response.data));
                            $("#loader").hide();
                            $("#workerDiv").show();
                            $scope.dataAvail = true;
                           // $('#attendanceReportTables').html($compile( $('#attendanceReportTables').html())($scope));
                     }else{
                    	 $("#loader").hide();
                         $("#workerDiv").show();
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
             }else if( type ==  'countries'){            	 
            	 $scope.list_countries = response.data;     
            	 if(response.data != null && response.data != undefined && response.data != ""){
                	 $scope.countryName = response.data[0].id;
                	 $scope.fun_CountryChangeListener();
                 }
              }else if(type == 'vendorAndDeptDrop'){
                  $scope.list_vendors = response.data.vendorList ;
                  //  $scope.list_countries = response.data.countriesList;
            }    
          })};
          
   // getSchemaNameByUserId
 //  $scope.getPostData("reportController/getSchemaNameByUserId.json",{userId : $cookieStore.get('userId') } ,"schemaName" );  
   
         // for getting Master data for Grid List 
         $scope.getPostData("reportController/getCustomerListForReportView.json",{customerId : $cookieStore.get('customerId') } ,"companyGrid" );
            
                  
   // Customer Change Listener to get company details
   $scope.fun_CustomerChangeListener = function(){                       
          if($scope.customerName != undefined && $scope.customerName != ''){
              $scope.getPostData("vendorController/getCompanyNamesAsDropDown.json",  { customerId : $scope.customerName,companyId: $cookieStore.get('companyId') != null && $cookieStore.get('companyId') != undefined ? $cookieStore.get('companyId')  : $scope.companyName } , "companyDropDown");
          }
   };
   
  // Company Change Listener to get locations details//getcountryListByCompanyId
   $scope.fun_CompanyChangeListener = function(){
          if($scope.companyName != undefined && $scope.companyName != '')
                // $scope.getPostData("vendorController/getVendorNamesAndDepartmentsAsDropDown.json", { customerId : $scope.customerName , companyId : $scope.companyName , vendorId : '0' } , "vendorAndDeptDrop");                
          $scope.getPostData("vendorController/getcountryListByCompanyId.json", {  companyId : $scope.companyName  } , "countries");                

   };
   $scope.fun_CountryChangeListener = function(){
       if($scope.companyName != undefined && $scope.companyName != '')
              $scope.getPostData("reportController/getVendorNamesAndDepartmentsAsDropDown.json", { customerId : $scope.customerName , companyId : $scope.companyName , vendorId : '0' } , "vendorAndDeptDrop");                
      // $scope.getPostData("vendorController/getcountryListByCompanyId.json", {  companyId : $scope.companyName  } , "vendorAndDeptDrop");                

};

   
   
   // for Search button 
   $scope.fun_searchGridData = function(){   
	   		 
	  if($('#attendanceSearchForm').valid()){ 
		  $("#loader").show();
		  $("#workerDiv").hide();         
		  $scope.getPostData("vendorRegisterTypesController/getGridData.json",{customerId : $scope.customerName == undefined ? '0' : $scope.customerName, 
																	  companyId : $scope.companyName == undefined ? '0' : $scope.companyName  ,
																	  vendorId: ($scope.vendorName == undefined || $scope.vendorName == 0 )  ? '-1' :$scope.vendorName  ,	 																
																	  employeeCode : ($scope.employeeCode == undefined || $scope.employeeCode == "") ? '-1' : $scope.employeeCode ,
																	  employeeName : ($scope.employeeName == undefined || $scope.employeeName == "") ? '-1' : $scope.employeeName ,
																	  registerType: $scope.registerType,		  
																	  year :  ($scope.year == undefined || $scope.year == "" || $scope.year == null) ? 0 : $scope.year ,
																	  month : ($scope.month == undefined || $scope.month == "" || $scope.month == null) ? 0 :$scope.month														      
																	  } , "gridData" ); 
	    
	  }
	   
  }

   
   $scope.fun_clearSsearchFields = function(){   
	   $scope.customerName = "";
	   $scope.companyName = "";
	   $scope.countryName = "";
	   $scope.vendorName = "";
	   $scope.year = "";
	   $scope.month = "";
	   $scope.registerType = "";
	   $scope.employeeCode = null;
	   $scope.employeeName = null;
	   
	   $("#workerDiv").hide();
   }
   
   $scope.fun_returnScreen = function(workerId){
	  // alert(workerCode);
	   //$cookieStore.put("VendorRegisterTypeWorkerId",workerId);
	   $cookieStore.put("registerType",$scope.registerType);
	   
	   $scope.$apply(function() {
		   if($scope.registerType == 'Advance'){
			   $location.path('/vendorRegisterTypes/'+workerId);
		   }else if($scope.registerType == 'Fines'){
			   $location.path('/vendorFinesRegister/'+workerId);
		   }else if($scope.registerType == 'DamageOrLoss'){
			   $location.path('/vendorDamageOrLossRegister/'+workerId);
		   }else {
			   $scope.Messager('error', 'Error', 'Select Register Type',angular.element($event.currentTarget));
		   }
   		});
   }
   
	   
}]);
