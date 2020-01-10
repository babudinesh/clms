'use strict';
var JobCodeController = angular.module("myapp.JobCode", ['ngCookies']);

JobCodeController/*.directive('onLastRepeat', function () {
    return function (scope, element, attrs) {
        if (scope.$last) {
            setTimeout(function () {
                $('.table').DataTable();
            }, 1);
        }
    };
})*/.controller("JobCodeSearchctrl", ['$scope', '$http', '$resource', '$routeParams', '$location','$filter','myservice','$cookieStore',  function ($scope,$http, $resource, $routeparams, $location, $filter, myservice, $cookieStore) {
	$('#searchPanel').hide();
	var jobCodeDataTable;
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
	 
    
    $scope.getData = function (url, data, type, buttonDisable) {
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
	                $scope.customerName=response.data.customerList[0];		                
	                $scope.customerChange();
	             }
         		 if ( ! $.fn.DataTable.isDataTable( '#jobCodeTable' ) ) {        		   
         			jobCodeDataTable = $('#jobCodeTable').DataTable({        
  	                     "columns": [	  
                         	{ "data": "companyName" },
  	                        { "data": "jobCode",
  	                        	"render": function ( data, type, full, meta ) {                 		                    		 
	  	               		      return '<a href="#/AddJobCode/'+full.jobCodeDetailsId+'/'+full.jobCodeId+'">'+data+'</a>';
	  	               		    }
	  	                     },  	                     	                     		
                     		{ "data": "jobName" },
  	                   		{ "data": "jobType" },
  	                        { "data": "jobCategory" },  	                      
  	                        { "data": "transactionDate","render":function(data){
               				  return $filter('date')(data, "dd/MM/yyyy");
                            } },
  	                        { "data": "status" }]
  	               });  
  	      		}
         		// for button views
 			  	if($scope.buttonArray == undefined || $scope.buttonArray == '')
         		$scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Job Code'}, 'buttonsAction');
            }else if (type == 'customerChange'){
                $scope.companyList = response.data;
                if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
   	                $scope.companyName = response.data[0].id;
   	                }
            }else if(type=='search'){
            	$('#searchPanel').show();
            	jobCodeDataTable.clear().rows.add(response.data.jobCodeList).draw(); 				
              //  $scope.result = response.data.jobCodeList;
            }
        },
        function () { 
        	$scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable)
        });
    }

    $scope.getData('vendorController/getCustomerNamesAsDropDown.json', { customerId: $cookieStore.get('customerId')}, 'customerList')

    $scope.customerChange = function () {
    	//alert($scope.customerName);
        $scope.getData('vendorController/getCompanyNamesAsDropDown.json', {customerId:$scope.customerName.customerId == undefined ?'0': $scope.customerName.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.companyName} , 'customerChange');
	};
	
    $scope.search = function () {
    	//alert($scope.customerName);
        $scope.getData('JobcodeController/getJobCodeListBySearch.json', { customerId : ($scope.customerName == undefined || $scope.customerName.customerId == undefined )? '0' : $scope.customerName.customerId  , companyId : ($scope.companyName == undefined) ? '0' : $scope.companyName , jobCode: $scope.jobCode == undefined ? '' : $scope.jobCode}, 'search');
    };
    
    $scope.clear = function () {
    	$scope.customerName = "";
    	$scope.companyList="";      
        $scope.jobCode = "";
       
    };
}]);