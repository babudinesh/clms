CompanyDocumentController.directive('fileModel', ['$parse', function ($parse) {
	return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;
            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);



CompanyDocumentController.controller("CompanyDocumentAddCtrl", ['$scope','$window', '$http', '$resource','$location', '$routeParams','$filter', '$cookieStore','$rootScope','myService', function ($scope, $window, $http, $resource,$location,  $routeParams, $filter,  $cookieStore, $rootScope, myService) {
		
	$('#documentDate').datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown'	    	  
	});
	
	$scope.document = new Object();
	$scope.documentList = [];
	$scope.document.customerId = $cookieStore.get('customerId');
	$scope.document.companyId = $cookieStore.get('companyId');
	$scope.document.status = "Saved";
	$('#fileName').val("");
	$scope.document.documentDate = $filter('date')(new Date(),'dd/MM/yyyy');
	
    $scope.theFile= "";
    //$scope.document.currencyName =  $scope.defaultCurrency ;
   // $scope.document.currencyId = $scope.defaultCurrencyId ;
	   
	
	
	
	$scope.list_status = [{"id":"Saved","name":"Saved"},
	                      {"id":"Submitted","name":"Submitted"},
	                      {"id":"Verified","name":"Verified"}];
	
	$scope.list_frequency = [{"id":"Monthly","name" : "Monthly" },
							 {"id":"Quarterly","name" : "Quarterly"},
							 {"id":"Half yearly","name" : "Half yearly" },
							 {"id":"Annually","name" : "Annually" }
							  ];
	$scope.roleId = $cookieStore.get("roleId");
	if ($routeParams.id > 0) {
		
        $scope.readOnly = true;
        $scope.updateBtn= true;
        $scope.cancel = false;
        $scope.saveBtn = false;
        $scope.submitBtn = false;
        $scope.verifyBtn = true;
        $scope.resetBtn = false;
		
    } else {
		$scope.readOnly = false;
        $scope.updateBtn= false;
        $scope.cancel = false;
        $scope.saveBtn = true;
        $scope.submitBtn = true;
        $scope.verifyBtn = false;
        $scope.resetBtn = true;
    };
    
    $scope.updateBtnAction = function (this_obj) {
		$scope.readOnly = true;
		$scope.onlyRead = true;
        $scope.updateBtn= false;
        $scope.saveBtn = true;
        $scope.resetBtn = false;
        $scope.submitBtn = true;
        $scope.verifyBtn = false;
        $scope.commentsShow = false;
       
        $('.dropdown-toggle').removeClass('disabled');
    };
    
    
    
    $scope.cancelBtnAction = function(){
    	$scope.readOnly = true;
        $scope.updateBtn = true;
        $scope.saveBtn = false;
        $scope.resetBtn = false;
        $scope.transactionList = false;
        $scope.cancelBtn = false;
        $scope.gridButtons = false;
        $scope.submitBtn = false;
        $scope.verifyBtn = false;
        $scope.commentsShow = false;
        $scope.getData('documentController/getCompanyDocumentById.json', { customerId: $cookieStore.get('customerId'),companyId:$cookieStore.get('companyId'),vendorId:($cookieStore.get('vendorId') != null && $cookieStore.get('vendorId') != "") ? $cookieStore.get('vendorId')  : 0, vendorDocumentId:$routeParams.id }, 'documentList');
        //$scope.getData('PlantController/getPlantById.json', { plantDetailsId: $routeParams.id,customerId : ''}, 'plantList')
    };
    
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
    };
	
	
	
	
	
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
    		} else if(type == 'documentList'){
    			if(response.data != undefined){
    				//alert(angular.toJson(response.data));
	    			$scope.documentList.companyList = response.data.companyList;
	    			$scope.documentList.locationsList = response.data.locationList;
	    			$scope.documentList.documentsList = response.data.documentList;
	    			$scope.defaultCurrency = response.data.defaultCurrency;
	    			
	    			//alert(angular.toJson(response.data.defaultCurrency));
	    			if(response.data.defaultCurrency != undefined && response.data.defaultCurrency != null){
	             	   $scope.document.currencyName = response.data.defaultCurrency[0].defaultCurrencyName;
	             	   $scope.document.currencyId = response.data.defaultCurrency[0].defaultCurrencyId;
	             	   $scope.companyCode = response.data.defaultCurrency[0].companyCode;
	             	  $scope.companyName = response.data.defaultCurrency[0].companyName;
	                }
	    			
	    			if( response.data.companyList != undefined && response.data.companyList != "" && response.data.companyList.length == 1 ){
		                $scope.document.companyId = response.data.companyList[0].id;	
		               // $scope.companyName = response.data.companyList[0].name
			         }
	    			
	                if( response.data.locationList != undefined && response.data.locationList != "" && response.data.locationList.length == 1 ){
		                $scope.document.locationId = response.data.locationList[0].id;	
		            }
	                
	                if(response.data.documentVo != undefined && response.data.documentVo != null && response.data.documentVo != "" && response.data.documentVo.length == 1){
	                	$scope.document = response.data.documentVo[0];
	                	$scope.document.documentDate =  $filter('date')(response.data.documentVo[0].documentDate, 'dd/MM/yyyy');
	                	if(response.data.documentVo[0].fileName != null){
	                		//$('#fileName').val(response.data.documentVo[0].fileName);
	                	}else{
	                		$('#fileName').val("");
	                	}
	                }  
    			}
    			// for button views
			  	if($scope.buttonArray == undefined || $scope.buttonArray == '')
			  		$scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Company Documents'}, 'buttonsAction');

    		}
	    },function () { 
	    	$scope.Messager('error', 'Error', 'Technical Issue Occurred. Please try again after some time');
	    });
    };
    
	$scope.getData('documentController/getCompanyDocumentById.json', { customerId: $cookieStore.get('customerId'),companyId:$cookieStore.get('companyId'), companyDocumentId:$routeParams.id }, 'documentList');
	
	$scope.saveDocument = function($event){
		$scope.document.status = "Saved";
		$scope.save($event);
	};
	
	$scope.submitDocument = function($event){
		$scope.document.status = "Submitted";
		$scope.save($event);
	};
	
	$scope.verifyDocument = function($event){
		$scope.document.status = "Verified";
		$scope.save($event);
	};
	
	$scope.save =function($event){
    	
    	if($('#companyDocumentForm').valid()){
    		
    		$scope.document.documentDate = moment($('#documentDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 	 
   			 	
			$scope.document.createdBy = $cookieStore.get('createdBy'); 
		 	$scope.document.modifiedBy = $cookieStore.get('modifiedBy');
		 //   $scope.getData('invoiceController/saveVendorDocument.json', angular.toJson($scope.document), 'saveDocument',angular.element($event.currentTarget)); 
		 	//alert($scope.theFile);
		 	var formData = new FormData();
		 	if($scope.theFile != null && $scope.theFile != ""){
		 		formData.append("file", $scope.theFile); 
		 	}else{
		 		formData.append("file", new File([""],""));
		 	}
		 	
		 	//$scope.document.fileData = $scope.theFile;
		 	formData.append('file', $scope.theFile);
	 		formData.append('name',angular.toJson($scope.document));
         
	 		$http.post('documentController/saveCompanyDocument.json', formData, {
	 			transformRequest: angular.identity,
	 			headers: {'Content-Type': undefined}
	 		}).success(function(response,data){
 				if(response.id > 0){
 					//alert(response.id);
 					//alert(angular.toJson(response));
	            	$scope.Messager('success', 'success', 'Company Document Saved Successfully');
	            	$scope.document.documentDate =  $filter('date')( $('#documentDate').val(), 'dd/MM/yyyy');
	            	$location.path('/addCompanyDocument/'+response.id);
	            	
 				}else if(response.id < 0){
        		    $scope.Messager('error', 'Error', response.data.name);
        		    $scope.document.documentDate =  $filter('date')( $('#documentDate').val(), 'dd/MM/yyyy');
 				}else{
            	    $scope.Messager('error', 'Error', "Technical issues occurred. Please try again after some time.");
            	    $scope.document.documentDate =  $filter('date')( $('#documentDate').val(), 'dd/MM/yyyy');
 				}
	 		}).error(function(data,status,headers,config){
	 			$scope.Messager('error', 'Error', 'Technical Issue while saving. Please try again after some time');
	 			$scope.document.documentDate =  $filter('date')( $('#documentDate').val(), 'dd/MM/yyyy');
	 		});
		 	
		}
    	//}
    }; 
 
 
 // To download the file
 $scope.fun_download = function(event,fileName){
	  //alert(event);
	  if(event instanceof Object){
	   	  var file = document.getElementById("fileName").files[0];
	   	 // alert(file);
	   	  if (file) {
	   	      var reader = new FileReader();
	   	      reader.readAsArrayBuffer(event);
	   	      reader.onload = function (evt) {
	   	    	  var blob = new Blob([evt.target.result], {type: 'application/octet-stream'});
	 			      saveAs(blob,fileName);
	   	      }
	   	      reader.onerror = function (evt) {
	   	    	  $scope.Messager('error', 'Error', 'Failed to download ');
	   	      }
	   	  }
	  }else{
		  $http({
			    url: ROOTURL +'vendorComplianceController/downloadfile.view',
			    method: 'POST',
			    data : { 'path': event } ,
			    responseType: 'arraybuffer',
			    headers: {
			        'Content-type': 'application/json',
			        'Accept': '*'
			    }
			}).success(function(data){
			   var blob = new Blob([data], {
			        type: 'application/pdf'
			    });
			    saveAs(blob,fileName);
			    $("#loader").hide();
			    $("#data_container").show();
           
			}).error(function(){	
			    $scope.Messager('error', 'Error', 'Failed to download ');
			});
	  }
 };
 
//To get the file name
 $scope.getFileDetails = function (e) {  
     $scope.$apply(function () {
          $scope.theFile = e.files[0];
     });
 };
 
}]);