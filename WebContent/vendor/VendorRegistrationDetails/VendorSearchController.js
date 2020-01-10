'use strict';
var vendorDetails;
var vendorControllers = angular.module("myApp.vendorSearch", []);
vendorControllers.controller("vendorListCtrl", ['$scope', '$http', '$resource', '$location','$filter','$cookieStore', function ($scope, $http, $resource, $location, $filter, $cookieStore) {
	$("#vendorDiv").hide();
    $scope.companyName = "";
    $scope.customerName = "";
    $scope.vendorName = "";
    $scope.master = {};

    
  //  $scope.statusList = [{id:"Y",name:"Active"},{id:"N",name:"InActive"}];
    $scope.statusList = [{"id":"New", "name":"New"},
                                {"id":"Pending For Approval", "name":"Pending For Approval"},
                                {"id":"Validated", "name":"Validated"},
    							{"id":"Suspended", "name":"Suspended"},
    							{"id":"Debarred", "name":"Debarred"},
    							{"id":"Terminated", "name":"Terminated"}];
   // $scope.roleVendor =$cookieStore.get('vendorId')== null || $cookieStore.get('vendorId') == ''  ? false : true ;
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
        	} else if (type == 'customerList')
            {
                $scope.customerList = response.data.customerList;
                $scope.industryList = response.data.industryList;
                
                if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
		                $scope.customerName=response.data.customerList[0];		                
		                $scope.customerChange();
		                }
                
               vendorDetails = $('#vendorTable').DataTable({        
                    "columns": [
                       { "data": "vendorName" , "render": function ( data, type, full, meta ) {
              		      return '<a href="#/vendorEditDetails/'+full.vendorDetailsInfoId+'">'+data+'</a>';
              		    } },
                       { "data": "vendorCode" },
                       { "data": "transactionDate", "render":function (data) {
                           return $filter('date')(data,"dd/MM/yyyy");;
                       } },
                       { "data": "vendorStatus" },
                       { "data": "locationName" },],                      
                       
              });
            // for button views
               if($scope.buttonArray == undefined || $scope.buttonArray == '')
               $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Vendor'}, 'buttonsAction'); 
            }
            else if (type == 'customerChange')
            {
                $scope.companyList = response.data;
                
                if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
   	                $scope.companyName = response.data[0].id;
   	                $scope.companyChange();
   	                }
            }
            else if (type == 'companyChange') {
                $scope.vendorList = response.data;
                // alert(angular.toJson(response.data));
                if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
   	                $scope.vendorName = response.data[0].name.slice(0,response.data[0].name.indexOf("("));
   	                var x = response.data[0].name;   	                 
   	                $scope.vendorCode = x.slice(x.indexOf("(")+1, x.indexOf(")"));  
   	              
   	                }
            }
            else if(type=='search')
            {                                      
                	vendorDetails.clear().rows.add(response.data).draw();
                		 $("#loader").hide();
                         $("#vendorDiv").show();
                	
            }
        },
        function () { 
          	$scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
        });
    }
   
   
    $scope.getData('vendorController/getCustomerNamesAsDropDown.json', { customerId: $cookieStore.get('customerId')}, 'customerList')
    
    $scope.customerChange = function () {
    	$scope.companyName = '';
    	$scope.vendorName = '';
        $scope.getData('vendorController/getCompanyNamesAsDropDown.json', {customerId:$scope.customerName.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.companyName != undefined ? $scope.companyName : 0}, 'customerChange');
    };

    $scope.companyChange = function () {
    	$scope.vendorName = '';
    	//alert($cookieStore.get('vendorId')+"::"+$scope.vendorName);
        $scope.getData('vendorController/getVendorNamesAsDropDown.json', { companyId: $scope.companyName,customerId: $scope.customerName.customerId,vendorId:($cookieStore.get('vendorId') != null && $cookieStore.get('vendorId') > 0) ? $cookieStore.get('vendorId') : 0}, 'companyChange');       
    };
    
    $scope.search = function () {   	
        $scope.getData('vendorController/VendorGridDetails.json', { customerId: $scope.customerName != undefined && $scope.customerName != "" ? $scope.customerName.customerId : 0, companyId: $scope.companyName != undefined && $scope.companyName  != "" ? $scope.companyName : 0, vendorName: $scope.vendorName != undefined && $scope.vendorName  != "" ? ($scope.vendorName).trim() : "", vendorCode: $scope.vendorCode != undefined && $scope.vendorCode  != "" ? ($scope.vendorCode).trim() : "", status : $scope.status,vendorId:($cookieStore.get('vendorId') != null && $cookieStore.get('vendorId') != "") ? $cookieStore.get('vendorId') : 0}, 'search');
        $("#loader").show();
        $("#vendorDiv").hide();
    };
    
    $scope.clear = function () {
        $scope.customerName = null;
        $scope.companyName = null;        
        $scope.vendorName = null;   
        $scope.industryId = null; 
    };
}]
);



