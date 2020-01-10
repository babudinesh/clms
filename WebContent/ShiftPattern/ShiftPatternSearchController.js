'use strict';

var ShiftPatternController = angular.module("myApp.ShiftPattern", ['ngCookies']);
ShiftPatternController.service('myservice', function () {
    this.customerId = 0;    
    this.companyId =0;
    this.locationId = 0;
    this.countryId = 0;
    this.plantId =0;
    this.plantDetailsId=0;
 });

ShiftPatternController/*.directive('onLastRepeat', function () {
    return function (scope, element, attrs) {
        if (scope.$last) {
        	//alert('asa');
        	$('.myRow').each(function(){
        		//alert($(this).find('td').eq(6).find('input').val());
        		
        		//var k = $(this).find('td').eq(6).find('input').val();
        		
        		//$scope.pattern.totalPatternHours = k;
        	});
            setTimeout(function () {
                $('.table').DataTable(); $.material.init();
            }, 1);
        }
    };
    
})*/.controller("ShiftPatternSearchCtrl", ['$scope', '$http', '$resource','$location', '$routeParams','myservice','$filter', '$cookieStore', function ($scope, $http, $resource,$location,  $routeParams, myservice, $filter,  $cookieStore) {

		$scope.companyName = "";
	    $scope.departmentCode="";
	    $scope.customerName = "";
	    $scope.status  = "Y";
	    var shiftPatternDataTable;
	    $scope.shiftPatternTableView = false;
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
	    			 if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
	   		                $scope.customerName=response.data.customerList[0].customerId;		                
	   		                $scope.customerChange();
	   		                }
	    			 if ( ! $.fn.DataTable.isDataTable( '#shiftPatternTable' ) ) {        		   
	    				 shiftPatternDataTable = $('#shiftPatternTable').DataTable({        
	  	                     "columns": [
	                           						
								{ "data": "companyName" },
								{ "data": "shiftPatternCode",
	  	                        	"render": function ( data, type, full, meta ) {                 		                    		 
	  	                        			return '<a href="#/ShiftPatternAdd/'+full.shiftPatternDetailsId+'">'+data+'</a>';
	  	                        		}
	  	                        },
	  	                        { "data": "shiftPatternName" },
	  	                        { "data": "definePatternBy" },
	  	                        { "data": "locationName" },
	                        	{ "data": "plantName" },
		                     	{ "data": "transactionDate","render":function(data){
		                     				return $filter('date')(data, "dd/MM/yyyy");
		                        } },
	  	                   		{ "data": "status" }]
	  	               });  
	  	      		}
	    			// for button views
	 			  	if($scope.buttonArray == undefined || $scope.buttonArray == '')
	    			 $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Define Shift Pattern'}, 'buttonsAction'); 
		        }else if (type == 'customerChange'){
		        	//alert(JSON.stringify(response.data));
		            $scope.companyList = response.data;
		            if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	   	                $scope.companyName = response.data[0].id;
						
	   	                }
		            
		        }else if(type=='search'){
		        	$scope.shiftPatternTableView = true;
		        	shiftPatternDataTable.clear().rows.add(response.data.shiftPatternList).draw(); 
		           // $scope.result = response.data.shiftPatternList;
		        }
		    },function () { alert('Error') });
	    }

	    $scope.getData('vendorController/getCustomerNamesAsDropDown.json', { customerId: $cookieStore.get('customerId')}, 'customerList')
		
		$scope.customerChange = function () {
	    	//alert(JSON.stringify($scope.customerName));
	    	$scope.getData('vendorController/getCompanyNamesAsDropDown.json',{customerId: ($scope.customerName == undefined || $scope.customerName == null )? '0' : $scope.customerName,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.companyName != undefined ? $scope.companyName : 0} , 'customerChange');
	    };

	
		$scope.search = function () {
			//alert($scope.customerName);
			$scope.getData('ShiftPatternController/getShiftPatternListBySearch.json', { customerId : ($scope.customerName == undefined || $scope.customerName == '' )? '0' : $scope.customerName  , companyId : ($scope.companyName == undefined || $scope.companyName == '') ? 0 : $scope.companyName  , shiftPatternCode: ($scope.shiftPatternCode == undefined || $scope.shiftPatternCode == null) ? '' : $scope.shiftPatternCode, shiftPatternName: $scope.shiftPatternName == undefined  ? '' : $scope.shiftPatternName, status: $scope.status != '' ? $scope.status : null}, 'search');
		};
		
		$scope.clear = function () {
			$scope.companyName="";
			$scope.customerName = "";
			$scope.shiftPatternCode = "";
			$scope.shiftPatternName="";
			$scope.status  = "Y";
		};
	
		/*var Customerlist = $resource('/Plant/PlantSearch/Customerdata.json');
			Customerlist.get(function (user) {
			$scope.result = user.result;
		});*/
}]);


