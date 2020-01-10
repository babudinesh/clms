'use strict';
var DepartmentController = angular.module("myApp.Department", ['ngCookies']);

/*DepartmentController.service('myservice', function () {
    this.companyId = 0;
    this.customerId = 0;
   
});*/
/*DepartmentController.directive('onLastRepeat', function () {
    return function (scope, element, attrs) {
        if (scope.$last) {
            setTimeout(function () {
                $('.table').DataTable();
            }, 1);
        }
    };
})*/



DepartmentController.controller("deptSearchCtrl", ['$scope', '$http', '$resource', '$routeParams', '$location', '$filter','$cookieStore', function ($scope, $http, $resource, $routeParams, $location, $filter, $cookieStore) {
     	$('#searchPanel').hide();
     	var deptTables;
	    $scope.isActive = "Y";
	    $scope.enable = false;
	    $scope.customerName = new Object();
	    $scope.companyName = new Object();
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
	            if (type == 'customerList'){
	                $scope.customerList = response.data.customerList;
	               
	                if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){
		                $scope.customerName.customerId=response.data.customerList[0].customerId;		                
		                $scope.customerChange();
		                }
	                
	                if ( ! $.fn.DataTable.isDataTable( '#deptTable' ) ) {        		   
	                	deptTables = $('#deptTable').DataTable({        
	 	                     "columns": [
	 	                         {"data" : "customerName"},
	 	                         {"data" : "companyName"},
	 	                         { "data": "departmentCode",
	 	                        	"render": function ( data, type, full, meta ) {                 		                    		 
			 	               		      return '<a href="#/addDepartment/'+full.departmentInfoId+'">'+data+'</a>';
			 	               		    }
	 	                        },
	 	                        { "data": "departmentName" },
	 	                        { "data": "transDate" },
	 	                        { "data": "isActive" }]
	 	               });  
	 	      		}
	                
	             // for button views
	  			  	if($scope.buttonArray == undefined || $scope.buttonArray == '')
	        	    $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Department'}, 'buttonsAction'); 
	                
	            } else if(type == 'buttonsAction'){
	              	$scope.buttonArray = response.data.screenButtonNamesArray;
	            } else if (type == 'customerChange'){
	                $scope.companyList = response.data;	                
	                if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	                $scope.companyName = response.data[0];
	                }
                
	            } else if(type=='search'){
	            	$('#searchPanel').show();
	            	deptTables.clear().rows.add(response.data.departmentList).draw(); 
	                // $scope.result = response.data.departmentList;
	            }
	        },
	        function(){
	        	$scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable); 
	        });
	    }


	    $scope.getData('vendorController/getCustomerNamesAsDropDown.json', { customerId: $cookieStore.get('customerId')}, 'customerList')

	    $scope.customerChange = function () {	    	
	        $scope.getData('vendorController/getCompanyNamesAsDropDown.json', {customerId : $scope.customerName == undefined ?'0': $scope.customerName.customerId,companyId: ($cookieStore.get('companyId') !=null && $cookieStore.get('companyId') !="") ? $cookieStore.get('companyId') : ($scope.companyName == undefined || $scope.companyName.id == undefined )? '0':$scope.companyName.id}, 'customerChange');
	    };

	    $scope.search = function () {
	        $scope.getData('DepartmentController/getDepartmentListBySearch.json', { customerId: ($scope.customerName == undefined || $scope.customerName.customerId == undefined )? '0': $scope.customerName.customerId, companyId: ($scope.companyName == undefined || $scope.companyName.id == undefined )? '0':$scope.companyName.id, departmentCode:($scope.departmentCode == undefined )? '':$scope.departmentCode, departmentName: ($scope.departmentName == undefined )? '': $scope.departmentName,divisionId:$cookieStore.get('divisionId') > 0 ? $cookieStore.get('divisionId') :0, isActive:$scope.isActive}, 'search');
	    };
	    
	    $scope.clear = function () {	    	
	    	 $scope.departmentCode="";
	    	 $scope.departmentName="";
	    	 $scope.isActive="Y";
	    };
}]
);





