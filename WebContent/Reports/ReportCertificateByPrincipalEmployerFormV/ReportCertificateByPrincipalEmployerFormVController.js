'use strict';

var reportCertificateByPrincipalEmployerFormVControllers = angular.module("myApp.ReportCertificateByPrincipalEmployerFormV", []);
reportCertificateByPrincipalEmployerFormVControllers.controller("ReportCertificateByPrincipalEmployerFormVCtrl", ['$scope', '$http', '$resource', '$location','$cookieStore','$sce', function ($scope, $http, $resource, $location,$cookieStore,$sce) {
	$("#loader").hide();
	 $("#data_container").hide();
    $scope.companyName = "";
    $scope.customerName = "";
    $scope.vendorName = "";
    $scope.master = {};
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
        	if(type == 'buttonsAction'){
           		$scope.buttonArray = response.data.screenButtonNamesArray;
          } else if (type == 'customerList'){
                $scope.customerList = response.data.customerList;
                $scope.industryList = response.data.industryList;                
                if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
		                $scope.customerName=response.data.customerList[0];		                
		                $scope.customerChange();
		         }  
             // for button views
       		   if($scope.buttonArray == undefined || $scope.buttonArray == '')
       			   	$scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Certificate by Principal Employer (Form V)'}, 'buttonsAction');
            }else if (type == 'customerChange'){
                $scope.companyList = response.data;                
                if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
   	                $scope.companyName = response.data[0].id;
   	                $scope.companyChange();
   	            }
            }else if (type == 'companyChange') {
                $scope.vendorList = response.data.vendorList;             
                if(response.data != undefined && response.data.vendorList != undefined && response.data.vendorList != "" && response.data.vendorList.length == 1 ){
   	                $scope.vendorName = response.data.vendorList[0].id;   	              
   	            }
            }
            
        },
        function () { alert('Error') });
    }
   
   
    $scope.getData('vendorController/getCustomerNamesAsDropDown.json', { customerId: $cookieStore.get('customerId')}, 'customerList')
    
    $scope.customerChange = function () {
    	$scope.companyName = '';
    	$scope.vendorName = '';
        $scope.getData('vendorController/getCompanyNamesAsDropDown.json', {customerId:$scope.customerName.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.companyName != undefined ? $scope.companyName : 0}, 'customerChange');
    };

    $scope.companyChange = function () {    	
        $scope.getData('reportController/getVendorNamesAndDepartmentsAsDropDown.json', { companyId: $scope.companyName,customerId: $scope.customerName.customerId,vendorId:($cookieStore.get('vendorId') != null && $cookieStore.get('vendorId') > 0) ? $cookieStore.get('vendorId') : $scope.vendorName != undefined && $scope.vendorName != '' ? $scope.vendorName : 0}, 'companyChange');       
    };
    
    $scope.search = function () {  
    	if($('#certificateByPrincipalEmployerFormVReport').valid()){
	        $("#loader").show(); 
	        $http({
			    url: ROOTURL +'reportController/certificateByPrincipalEmployerFormVReport.view',
			    method: 'POST',
			    responseType: 'arraybuffer',
			    data: { companyId: $scope.companyName,customerId: $scope.customerName.customerId,		    		
			    		//vendorId: ($('#vendorName option:selected').html() != undefined && $('#vendorName option:selected').html() != '' && $('#vendorName option:selected').html() != 'Select') ? $('#vendorName option:selected').html() : '',
			    		vendorId :  ($scope.vendorName == undefined || $scope.vendorName == '0' || $scope.vendorName == '' ) ? '' : $scope.vendorName
			    	  } , //this is your json data string
			    headers: {
			        'Content-type': 'application/json',
			        'Accept': 'application/pdf'
			    }
			}).success(function(data){
			    var blob = new Blob([data], {
			        type: 'application/pdf'
			    });
			    saveAs(blob, 'Certificate by Principal Employer (Form V)' + '.pdf');
			    $("#loader").hide();                    
			}).error(function(){	
				$("#loader").hide();                    
			});		       
    	}
    };
    
    
    $scope.fun_previewGridData = function () {  
    	if($('#certificateByPrincipalEmployerFormVReport').valid()){
	        $("#loader").show(); 
	        $http({
			    url: ROOTURL +'reportController/certificateByPrincipalEmployerFormVReport.view',
			    method: 'POST',
			    responseType: 'arraybuffer',
			    data: { companyId: $scope.companyName,customerId: $scope.customerName.customerId,		    		
			    		//vendorId: ($('#vendorName option:selected').html() != undefined && $('#vendorName option:selected').html() != '' && $('#vendorName option:selected').html() != 'Select') ? $('#vendorName option:selected').html() : '',
			    		vendorId : ($scope.vendorName == undefined || $scope.vendorName == '0' || $scope.vendorName == '' ) ? '' : $scope.vendorName
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
    };
    
    
    $scope.clear = function () {
        $scope.customerName = null;
        $scope.companyName = null;        
        $scope.vendorName = null;                     
     	$("#data_container").hide();       
    };
}]
);



