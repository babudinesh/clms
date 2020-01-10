'use strict';



/*workerDetailsControllers.directive('customOnChange', function() {
	
	  return {
	    restrict: 'A',
	    link: function (scope, element, attrs) {
	      var onChangeFunc = scope.$eval(attrs.customOnChange);
	      element.bind('change', function (changeEvent) {
	            	alert('abc');
              var reader = new FileReader();
              reader.onload = function (loadEvent) {
                  scope.$apply(function () {
                      scope.fileread = loadEvent.target.result;
                      alert(loadEvent.target.result);
                  });
              }
             
              reader.readAsDataURL(changeEvent.target.files[0]);
          });
	    }
	  }
	});*/


workerDetailsControllers.directive('fileModel', ['$parse', function ($parse) {
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



workerDetailsControllers.controller('workerUploadController', ['$scope','$http', '$resource','$routeParams','$filter','myservice','$cookieStore', function ($scope,$http, $resource, $routeParams,$filter,myservice,$cookieStore) {
	
	var uploadWorkerDtaTable;
	 $.material.init();
	 $scope.workerVo = new Object();
	 $scope.workerVo.createdBy = $cookieStore.get('createdBy'); 
	 $scope.workerVo.modifiedBy = $cookieStore.get('modifiedBy');
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
	        		
	        		  if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
			                $scope.workerVo.customerId=response.data.customerList[0].customerId;		                
			                $scope.customerChange();
			                }
	        		  
	        		  if ( ! $.fn.DataTable.isDataTable( '#uploadWorkerDtaTable' ) ) {        
	        			  uploadWorkerDtaTable = $('#uploadWorkerDtaTable').DataTable({        
	   	                    "columns": [
	   	                       { "data": "workerCode" },
	   	                       { "data": "firstName" },
	   	                       { "data": "lastName" },
	   	                       { "data": "dateOfJoining",
	   	                    	   "render":function(data){
	                				  return $filter('date')(data, "dd/MM/yyyy");
	                            }  },
	   	                       { "data": "dateOfBirth",
		   	                    	   "render":function(data){
			                				  return $filter('date')(data, "dd/MM/yyyy");
			                    } },	   	                      	   	                       
	   	                       { "data": "reasonForStatus",className: "colored" },
	   	                       {"data": "customerId"}]
	   	                       
	   	                });
	                   }
	        		  
	        		  
	        		 
	        	}else if(type == 'customerChange'){	        		
	        		$scope.companyList = response.data; 
	        		 if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	    	                $scope.workerVo.companyId = response.data[0].id;
	    	                $scope.companyChange();
	    	          }
	        		 
	        		
	        	}else if (type == 'companyChange') {	        		
	                $scope.vendorList = response.data.vendorList;
	                if( response.data != undefined && response.data.vendorList != "" && response.data.vendorList.length == 1 ){
       	                $scope.workerVo.vendorId = response.data.vendorList[0].id;					
       	                }
	                $scope.list_Country = response.data.countriesList;
	        		// alert(angular.toJson(response.data));
	        		 $scope.workerVo.countryId = response.data.countriesList[0].id;
	                
	            }else if(type == 'countryList'){
	            	$scope.list_Country = response.data;
	            }else if( type ==  'schemaName'){
		      		   if(response.data.name != undefined && response.data.name != null && response.data.name != ''){
		     			   $scope.workerVo.schemaName = response.data.name;   
		     		   }else{
		     			   $scope.workerVo.schemaName = 'null';
		     		   }
		     		  
		        }
	        },
	        function () {
	        	$scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
	        	           
	          });
	    	}    
	  
	    
	    
	    
	 
	 $scope.getData('vendorController/getCustomerNamesAsDropDown.json', { customerId: $cookieStore.get('customerId')}, 'customerList');
	 
	 $scope.getData('workerController/countriesList.json','', 'countryList');
	 
	  $scope.getData("reportController/getSchemaNameByUserId.json",{userId : $cookieStore.get('userId') } ,"schemaName" );
	 
	  $scope.customerChange = function () { 
	    	$scope.companyList ="";
	    	$scope.countriesList = ""; 
          $scope.vendorList = "";   
          $scope.errorsList = [];
	    	if($scope.workerVo.customerId != undefined && $scope.workerVo.customerId != ""){
	    	$scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.workerVo.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.workerVo.companyId}, 'customerChange');
	    	}
	    };
	    
	    
	    
	    $scope.companyChange = function () {
	      $scope.countriesList = ""; 
	      $scope.vendorList = ""; 
	      $scope.locationList = "";
	      $scope.errorsList = [];
    	 if($scope.workerVo.companyId != undefined && $scope.workerVo.companyId != ""){
            $scope.getData('workerController/getVendorAndWorkerNamesAsDropDowns.json', { customerId: $scope.workerVo.customerId,companyId: $scope.workerVo.companyId,vendorId : $cookieStore.get('vendorId') != undefined && $cookieStore.get('vendorId') != '' ? $cookieStore.get('vendorId') : 0  }, 'companyChange');
    	 }
	    };
	    
	    
	    
	    
	    
	    
	    $scope.Upload =function($event){	
	    	$scope.errorsList = [];
	    	if($('#WorkerUpload').valid() )
    		{
	    		
	    		if($scope.myFile != undefined ){
	    			 var file = $scope.myFile;
	    	    	 var formData = new FormData();
	    	    	 formData.append('file',file);
	    	    	 formData.append('name',angular.toJson($scope.workerVo));	    	    	
	    	    	 $http.post('workerController/uploadWorker.json', formData, {
	                     transformRequest: angular.identity,
	                     headers: {'Content-Type': undefined}
	                 })
	                 .success(function(response){ 
	                	 //alert(response.length);
	                	 if(response.length > 0){
	                		 $scope.Messager('warning', 'warning', 'Upload Successfull with Some Duplicate Worker Codes',angular.element($event.currentTarget));
	                		 $('#fileCsv').val("");
	                	 }else{
	                		 $scope.Messager('success', 'success', 'Upload Successfull',angular.element($event.currentTarget));
	                		 $('#fileCsv').val("");
	                	 }
	                	 
	                	 $scope.errorsList =response;
	                	 uploadWorkerDtaTable.clear().rows.add(response).draw(); 
	                 })
	                 .error(function(response){
	                	
	                	 $scope.Messager('error', 'Error', 'File Upload Failed',angular.element($event.currentTarget));
	                 });
	    		}else{
	    			 $scope.Messager('error', 'Error', 'Upload CSV File',angular.element($event.currentTarget));
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
			    saveAs(blob, fileName + '.csv');
			  
			}).error(function(){	
				
			});
	    }
	
     
}]);


