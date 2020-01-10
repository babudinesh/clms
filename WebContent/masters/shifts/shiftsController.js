'use strict';
var shiftController = angular.module("myapp.shifts", []);
shiftController.controller("shiftControllerDtls", ['$scope', '$rootScope', '$http', '$filter', '$resource','$location','$routeParams','myservice', function($scope,$rootScope, $http,$filter,$resource,$location,$routeParams,myservice) {
	
	$.material.init();
    
    $('#pfStartDate,#pfRegistrationDate,#esiStartDate,#esiRegistrationDate,#transactionDate').bootstrapMaterialDatePicker({ 
    	time : false,
        Button : true,
        format : "DD/MM/YYYY",
        clearButton: true
    }); 
    
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
            if (type == 'masterData') {  
            	
            }    
            
        })
	};
	

    // GET MASTER DATA FOR  VENDOR DETAILS SCREEN   
	$scope.getPostData("shiftsController/getShiftsGridData.json", { id : '' }, "masterData");		    

	
	 
}]);
