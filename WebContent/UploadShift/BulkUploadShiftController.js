'use strict';

var bulkShiftController = angular.module("myApp.BulkShiftUpload", []);




bulkShiftController.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                    //alert(angular.toJson(element[0].files[0]));
                });
            });
        }
    };
}]);



bulkShiftController.controller('shiftUploadController', ['$scope','$rootScope','$http', '$resource','$routeParams','$filter','myservice','$cookieStore', function ($scope,$rootScope,$http, $resource, $routeParams,$filter,myservice,$cookieStore) {
	
	var shiftUploadErrors;
	
	 $.material.init();
	 $scope.workerVo = new Object();
	 $scope.workerVo.createdBy = $cookieStore.get('createdBy'); 
	 $scope.workerVo.modifiedBy = $cookieStore.get('modifiedBy');
	 
	 var date = new Date();
	 $scope.list_years = $rootScope.getYears;  
	/* $scope.list_years = [{"id":(date.getFullYear()-1).toString().substr(2,2), "name":date.getFullYear()-1},
                          {"id":(date.getFullYear()).toString().substr(2,2),   "name":date.getFullYear()  },
                          {"id":(date.getFullYear()+1).toString().substr(2,2), "name":date.getFullYear()+1},
                          ];*/
	 
	 
     $scope.list_months = [{"id":"Jan","name":"January"},
                           {"id":"Feb","name":"February"},
                           {"id":"Mar","name":"March"},
                           {"id":"Apr","name":"April"},
                           {"id":"May","name":"May"},
                           {"id":"Jun","name":"June"},
                           {"id":"Jul","name":"July"},
                           {"id":"Aug","name":"August"},
                           {"id":"Sep","name":"September"},
                           {"id":"Oct","name":"October"},
                           {"id":"Nov","name":"November"},
                           {"id":"Dec","name":"December"}];
     
	 
	 $scope.errorsList = new Object();
	 
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
    
	    
	 $scope.getData = function (url, data, type,buttonDisable) {
	        var req = {
	            method: 'POST',
	            url:ROOTURL+url,
	            headers: {
	                'Content-Type': 'application/json'
	            },
	            data:data
	        }
	        $http(req).then(function (response) {
	        	//alert(angular.toJson(response.data));
	        	if(type == 'customerList'){        		
	        		$scope.customerList = response.data.customerList;	   
	        		
	        		  if( response.data.customerList != undefined && response.data.customerList.length == 1 && response.data.customerList[0] != undefined && response.data.customerList[0] != ""  ){                		
			                $scope.workerVo.customerId=response.data.customerList[0].customerId;		                
			                $scope.customerChange();
			          }
	        		  
	        		 
	        		  if ( ! $.fn.DataTable.isDataTable( '#shiftUploadErrors' ) ) {        
	        			  shiftUploadErrors = $('#shiftUploadErrors').DataTable({        
	   	                    "columns": [
	   	                       { "data": "monthYear"},
	   	                       { "data": "vendorCode" },
	   	                       { "data": "vendorName" },
	   	                       { "data": "workerCode" },
	   	                       { "data": "workerName" },
	   	                       { "data": "shift"},
	   	                    	{ "data": "weeklyOff"},	   	                       
	   	                       { "data": "errorDescription",className: "colored" }]
	   	                       
	   	                	});
	                   }
	        		
	        	}else if(type == 'customerChange'){	        		
	        		$scope.companyList = response.data; 
	        		 if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	    	                $scope.workerVo.companyId = response.data[0].id;
	    	                $scope.companyChange();
	    	          }
	        		 
	        		
	        	}else if (type == 'companyChange') {	        		
	                //$scope.vendorList = response.data.vendorList;
	                $scope.list_Country = response.data.countriesList;
	        		// alert(angular.toJson(response.data));
	        		 $scope.workerVo.countryId = response.data.countriesList[0].id;
	                
	            }else if( type ==  'schemaName'){            	 
	            	$scope.workerVo.schemaName = response.data.name;            	
	             }
	        },
	        function () {
	        	$scope.Messager('error', 'Error', 'Technical issue occured. Please try after some time',buttonDisable);
	        	           
	          });
	    	}    
 
	 //   getSchemaNameByUserId
     $scope.getData("reportController/getSchemaNameByUserId.json",{userId : $cookieStore.get('userId') } ,"schemaName" ); 
	
     $scope.getData('vendorController/getCustomerNamesAsDropDown.json', { customerId: $cookieStore.get('customerId')}, 'customerList')
	 
	 
	  $scope.customerChange = function () { 
	    	$scope.companyList ="";
	    	$scope.countriesList = ""; 
	    	$scope.vendorList = "";             
	    	if($scope.workerVo.customerId != undefined && $scope.workerVo.customerId != ""){
	    		$scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.workerVo.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.workerVo.companyId}, 'customerChange');
	    	}
	    };
	    
	    
	    
	    $scope.companyChange = function () {
	    	$scope.countriesList = ""; 
	    	$scope.vendorList = ""; 
	    	$scope.locationList = "";
	    	
	    	if($scope.workerVo.companyId != undefined && $scope.workerVo.companyId != ""){
	    		$scope.getData('workerController/getVendorAndWorkerNamesAsDropDowns.json', { customerId: $scope.workerVo.customerId,companyId: $scope.workerVo.companyId,vendorId:0 }, 'companyChange');
	    	}
	    };
	    
	    
	    
	    
	    
	    
	    $scope.Upload =function($event){
	    	$scope.errorsList = "";
	    	if($('#shiftUpload').valid()){  	
	    		if($scope.myFile != undefined){
	    			//alert(angular.toJson($scope.workerVo));
	    			
	    			if($('#fileInput').val().lastIndexOf(".csv")== -1) {
	    				//alert("Please upload only .csv extention file");
	            		$scope.Messager('error', 'Error', 'Please upload only .csv extension file');            		 
	            		$('#fileInput').focus(); 
	    				return false;
	    			}else{
				    	 var file = $scope.myFile;
				    	 var formData = new FormData();
				    	 formData.append('file',file);
				    	 formData.append('name',angular.toJson($scope.workerVo));
				    	 //alert($scope.myFile);
				    	 $http.post('workerController/uploadWorkerShift.json', formData, {
			                 transformRequest: angular.identity,
			                 headers: {'Content-Type': undefined}
			             })
			             .success(function(response){ 
			            	// alert("Success:  "+angular.toJson(response.flag[0].flag));
			            	 if(response.flag != null && response.flag[0].flag == 0){
			            		 $scope.Messager('error', 'Error', response.flag[0].description,angular.element($event.currentTarget));            		 
			            	 }else{
			            		 $scope.Messager('success', 'success', response.flag[0].description,angular.element($event.currentTarget));
			            	 }
			            	
			            	 $scope.errorsList =response.errorsList;
			            	 shiftUploadErrors.clear().rows.add(response.errorsList).draw();  
			             }).error(function(response){
			            	// alert(angular.toJson(response));
			            	 $scope.Messager('error', 'Error', 'File Upload Failed',angular.element($event.currentTarget));
			             });
	    			}
		    	 
	    		}else{
	    			 $scope.Messager('error', 'Error', 'Please upload .csv file',angular.element($event.currentTarget));
	    		} 
    		}
	    	
	    }
	    
	    
	    $scope.downloadUploadFormat = function(fileName){
	    	
	    	$http({
			    url: ROOTURL +'reportController/downloadSampleFile.view',
			    method: 'POST',
			    data : {name:fileName},
			    responseType: 'arraybuffer',
			     //this is your json data string
			    headers: {
			        'Content-type': 'application/json',
			        'Accept': 'application/pdf'
			    }
			}).success(function(data){
			    var blob = new Blob([data], {
			        type: 'application/pdf'
			    });
			    saveAs(blob, 'Worker_Shift_Upload_Format' + '.csv');
			  
			}).error(function(){	
				
			});
	    };
	    
	    $scope.clear = function(){
	    	//$scope.theFile = null;
	    	$('#fileInput').val("");
	       // $scope.getData('vendorController/getCustomerNamesAsDropDown.json', { customerId: $cookieStore.get('customerId')}, 'customerList')

	    }
	
     
}]);


