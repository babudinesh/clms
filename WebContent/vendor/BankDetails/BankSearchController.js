'use strict';

var vendorBankControllers = angular.module("myApp.vendorBankList", []);

/*vendorBankControllers
.service('myservice', function () {
    this.companyId = 0;
    this.customerId = 0;
    this.vendorId = 0;
    this.locationId = 0;
    this.countryName = 0;
});*/


vendorBankControllers/*.directive('onLastRepeat', function () {
    return function (scope, element, attrs) {
        if (scope.$last) {
            setTimeout(function () {
                $('.table').DataTable();
            }, 1);
        }
    };
})*/.controller("vendorBankSarchCtrl", ['$scope', '$http', '$resource', '$location', '$cookieStore', function ($scope, $http, $resource, $location, $cookieStore) {  	
    $scope.companyName = "";   
    $scope.customerName = "";
    $scope.vendorName = "";  
    $scope.locationName = "";
    var vendorBankDataTable;
    $scope.vendorBankTableView = false;
    
    if($cookieStore.get("roleNamesArray").includes('Vendor Admin')){
		  $scope.vendorAdmin = true;
	  }else{
		  $scope.vendorAdmin = false;
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
                if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
	 
                	$scope.customerName=response.data.customerList[0].customerId;		                
	                $scope.customerChange();
                }
                
                if ( ! $.fn.DataTable.isDataTable( '#vendorBankTable' ) ) {        		   
                	vendorBankDataTable = $('#vendorBankTable').DataTable({        
 	                     "columns": [
 	                     	{ "data": "bankName" },
 	                     	{ "data": "bankCode",
 	                        	"render": function ( data, type, full, meta ) {                 		                    		 
 	               		      return '<a href="#/vendorBankDetails/'+full.vendorBankId+'">'+data+'</a>';
 	               		    }
 	                     },
	                     		{ "data": "companyName" },
	                     		{ "data": "vendorName" },
 	                   		{ "data": "strTransactionDate" }, 	                      
 	                        { "data": "isActive" }]
 	               });  
 	      		}
             // for button views
                if($scope.buttonArray == undefined || $scope.buttonArray == '')
                $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Vendor Bank Details'}, 'buttonsAction');
                
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
                $scope.vendorList = response.data.vendorList;
              //  $scope.locationList = response.data.locationList;
                if( response.data != undefined && response.data.vendorList != "" && response.data.vendorList.length == 1 ){
		      		$scope.vendorCode = response.data.vendorList[0].desc.trim();
		      		var str = response.data.vendorList[0].name
		      		$scope.vendorName =  str.substr(0,str.indexOf("(")).trim();
		      		//console.log(vendorCode+":::"+vendorName);
	      		 }
              
                
                
            }
            else if(type=='search')
            {
            	$scope.vendorBankTableView = true;
            	vendorBankDataTable.clear().rows.add(response.data).draw(); 
               // $scope.result = response.data;
            }
        },
        function () { alert('Error') });
    }   

    $scope.getData('vendorController/getCustomerNamesAsDropDown.json', { customerId: $cookieStore.get('customerId')}, 'customerList')
    
    $scope.customerChange = function () {
        $scope.getData('vendorController/getCompanyNamesAsDropDown.json', {customerId:$scope.customerName,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.companyName != undefined ? $scope.companyName :0}, 'customerChange');
    };

    
    $scope.companyChange = function () {    	
        if($scope.customerName != undefined && $scope.customerName != "" && $scope.companyName != undefined && $scope.companyName != ""){
        $scope.getData('vendorController/getVendorAndLocationNamesAsDropDowns.json', { customerId: $scope.customerName,companyId: $scope.companyName,vendorId:($cookieStore.get('vendorId') != null && $cookieStore.get('vendorId') != "") ? $cookieStore.get('vendorId') : $scope.vendorName != undefined ? $scope.vendorName : 0 }, 'companyChange');
    	}
    };
      
    $scope.search = function () {
        $scope.getData('vendorController/VendorBankGridDetails.json', { customerId: ($scope.customerName != undefined &&  $scope.customerName != "" && $scope.customerName != null) ? $scope.customerName : '', companyId: ($scope.companyName != undefined && $scope.companyName != null && $scope.companyName != '') ? $scope.companyName : '', vendorCode: ($scope.vendorCode != undefined && $scope.vendorCode != null) ? $scope.vendorCode : '', vendorName: ($scope.vendorName != undefined && $scope.vendorName != null) ? $scope.vendorName : '',vendorId:($cookieStore.get('vendorId') != null && $cookieStore.get('vendorId') != "") ? $cookieStore.get('vendorId') :""}, 'search');
    };
    
    $scope.clear = function () {
    	$scope.customerName = '';
    	$scope.companyName = '';
    	$scope.vendorCode = null;
    	$scope.vendorName = null;
    };
}]
);



