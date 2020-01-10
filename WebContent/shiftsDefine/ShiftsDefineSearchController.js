'use strict';
var ShiftDefineSearchControllers = angular.module("myApp.ShiftDefine", []);
ShiftDefineSearchControllers/*.directive('onLastRepeat', function () {
    return function (scope, element, attrs) {
        if (scope.$last) {
            setTimeout(function () {
                $('.table').DataTable();
            }, 1);
        }
    };
})*/.controller("ShiftDefineSearchCtrl", ['$scope', '$rootScope', '$http', '$filter', '$resource','$location','$routeParams','myservice','$cookieStore', function($scope,$rootScope, $http,$filter,$resource,$location,$routeParams,myservice,$cookieStore) {   	
    $.material.init();
    $scope.shiftsDefineTableView = false;
    var shiftsDefineDataTable;
    $scope.list_status = [{ id:"Y" , name:"Active"},{ id:"N" , name:"In Active"}];
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
        	} else if (type == 'masterData') {             	
            	$scope.customerList = response.data.customerList;        
            	 if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
		                $scope.customerDetailsId=response.data.customerList[0].customerId;		                
		                $scope.fun_CustomerChangeListener();
		                }
            	 
            	 if ( ! $.fn.DataTable.isDataTable( '#shiftsDefineTable' ) ) {        		   
            		 shiftsDefineDataTable = $('#shiftsDefineTable').DataTable({        
  	                     "columns": [
                            { "data": "customerName" },
							{ "data": "companyName" },							
  	                        { "data": "shiftCode",
  	                        	"render": function ( data, type, full, meta ) {                 		                    		 
  	                        		return '<a href="#/shiftsDefineAddOrEdit/'+full.shiftDefineId+'">'+data+'</a>';
  	               		    }
  	                     },
  	                        { "data": "shiftName" },
  	                        { "data": "locationName" },
                        	{ "data": "plantName" },
	                     	{ "data": "stringTransactionDate" },
  	                   		{ "data": "isActive" }]
  	               });  
  	      		}
            	// for button views
 			  	if($scope.buttonArray == undefined || $scope.buttonArray == '')
            	 $scope.getPostData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Define Shift'}, 'buttonsAction');
            }else if (type == 'customerChange') {              	
            		$scope.companyList = response.data;
            		   if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
          	                $scope.companyDetailsId = response.data[0].id;
          	               
          	                }
            }else if (type == 'getGridData') {  
            	$scope.shiftsDefineTableView = true;
            	shiftsDefineDataTable.clear().rows.add(response.data).draw();
            		//$scope.result = response.data;
            }
        });
    }
    
    // GET MASTER DATA FOR DETAILS SCREEN   
	$scope.getPostData("shiftsController/getShiftsGridData.json", { customerId: $cookieStore.get('customerId') }, "masterData");		    

	// Customer Change Listener to get Companies List
	$scope.fun_CustomerChangeListener = function(){	
		if($scope.customerDetailsId != undefined && $scope.customerDetailsId != '')
		$scope.getPostData("vendorController/getCompanyNamesAsDropDown.json", { customerId : $scope.customerDetailsId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.companyDetailsId != undefined ? $scope.companyDetailsId : 0 }, "customerChange");	
	}
	
    $scope.fun_Search_ShiftDefine_Data = function(){   	
    	 
    	$scope.getPostData("shiftsDefineController/getShiftDefineGridData.json", { customerId : $cookieStore.get('customerId'), 
    			                                                                companyId : $scope.companyDetailsId == undefined ? '0' : $scope.companyDetailsId,
		                                                                		status :    $scope.status == undefined ? '' : $scope.status,
		                                                                		shiftCode : $scope.shiftCode == undefined ? '' : $scope.shiftCode,
                                                                				shiftName : $scope.shiftName == undefined ? '' : $scope.shiftName,
    			                                                              }, "getGridData");
    }
    
    
    
    
}]);


