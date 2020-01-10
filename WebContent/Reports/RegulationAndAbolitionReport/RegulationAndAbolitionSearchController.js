'use strict';
var dt_Menuitem;
var RegulationAndAbolitionReport = angular.module("myApp.RegulationAndAbolitionReport", []);
 
RegulationAndAbolitionReport.controller("RegulationAndAbolitionReportCtrl",  ['$scope', '$rootScope', '$http', '$resource','$location','$cookieStore','$sce', function($scope,$rootScope,$http,$resource,$location,$cookieStore,$sce) {
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
              
       $scope.list_years =  $rootScope.getYears;      
       $scope.year = moment().year()+"";
       $scope.month = moment().month()+1+"";
      
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
               } else {
            	   $scope.list_vendors = response.data.vendorList     
                   if( response.data != undefined && response.data.vendorList != "" && response.data.vendorList.length == 1 ){
      	                $scope.vendorId = response.data.vendorList[0].id;					
      	                }
               } 
           
          })
             
         };
         
         
         $scope.fun_clearSsearchFields = function(){
             
      	   $("#data_container").hide();
         }
   
   
   $scope.getPostData("reportController/getVendorNamesAndDepartmentsAsDropDown.json", { customerId : $cookieStore.get('customerId') , companyId : $cookieStore.get('companyId') , vendorId : ($cookieStore.get('vendorId') != undefined &&  $cookieStore.get('vendorId') != '') ?  $cookieStore.get('vendorId') :  '0' } , "vendorAndDeptDrop");
   $scope.getPostData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Muster Roll (Form XVI)'}, 'buttonsAction');
   
 
   
   $scope.fun_pdf_Download = function(){ 				  
	          if($('#musterRollForm').valid()){ 	        	 
	        	  $("#loader").show();
	          	  $("#data_container").hide();
	          	  
				   $http({
					    url: ROOTURL +'reportController/downloadPDFMustorRollReport.view',
					    method: 'POST',
					    responseType: 'arraybuffer',
					    data: {
					    	Vendor_Code: ($scope.vendorId == undefined || $scope.vendorId == '') ? '-1' : $scope.vendorId,									
					    	Year : ($scope.year == undefined || $scope.year == '' )? '0' : $scope.year ,
					    	Month : ($scope.month == undefined || $scope.month == ''  )? '0' : $scope.month ,
							reportName : 'Muster_Roll_FORMXVI'  
								} , 
					    headers: {
					        'Content-type': 'application/json',
					        'Accept': 'application/pdf'
					    }
					}).success(function(data){
					    var blob = new Blob([data], {
					        type: 'application/pdf'
					    });
					    saveAs(blob, 'Muster_Roll_FORMXVI' + '.pdf');
					    $("#loader").hide();
                        $("#data_container").show();
					}).error(function(){	
						$("#loader").hide();                       
					});
	          }
	   
		
   }
   
   
   $scope.fun_previewGridData = function(){ 				  
       if($('#musterRollForm').valid()){ 	        	 
     	  $("#loader").show();
       	  $("#data_container").hide();
       	  
			   $http({
				    url: ROOTURL +'reportController/downloadPDFMustorRollReport.view',
				    method: 'POST',
				    responseType: 'arraybuffer',
				    data: {
				    	Vendor_Code: ($scope.vendorId == undefined || $scope.vendorId == '') ? '-1' : $scope.vendorId,									
				    	Year : ($scope.year == undefined || $scope.year == '' )? '0' : $scope.year ,
				    	Month : ($scope.month == undefined || $scope.month == ''  )? '0' : $scope.month ,
						reportName : 'Muster_Roll_FORMXVI'  
							} , 
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
   
   
 
   
   
   
   
   
  
	   
   
}]);
