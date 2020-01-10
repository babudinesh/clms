
InvoiceController.controller("InvoiceAddCtrl", ['$scope','$window', '$http', '$resource','$location', '$routeParams','$filter', '$cookieStore','$rootScope','myService', function ($scope, $window, $http, $resource,$location,  $routeParams, $filter,  $cookieStore, $rootScope, myService) {
		
	$('#fromDate,#toDate,#invoiceDate').datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown'	    	  
	});
	
	$scope.invoice = new Object();
	$scope.invoiceList = [];
	$scope.invoice.particularsList = [];
	//$scope.invoice.documentsList = [];
	$scope.invoice.customerId = $cookieStore.get('customerId');
	$scope.invoice.companyId = $cookieStore.get('companyId');
	$scope.invoice.vendorId = $cookieStore.get('vendorId');
	$scope.invoice.status = "New";
	$scope.invoice.invoiceDate = $filter('date')(new Date(), 'dd/MM/yyyy');
	
	$scope.gridButtons = true;
	
	
	$scope.list_status = [{"id":"New","name":"New"},
	                      {"id":"Initiated","name":"Initiated"},
	                      {"id":"Saved","name":"Saved"},
	                      {"id":"Pending For Approval","name":"Pending For Approval"},
	                      {"id":"Correction Required","name":"Correction Required"},
	                      {"id":"Approved","name":"Approved"},	                     
	                      {"id":"Pending For Payment","name":"Pending For Payment"},	                      
	                      {"id":"Rejected","name":"Rejected"},
	                      {"id":"Closed","name":"Closed"},
	                      {"id":"Cancelled","name":"Cancelled"}];
	
	$scope.list_frequency = [{"id":"Day","name" : "Day/s" },
								{"id":"Month","name" : "Month/s" }
							  ];

	if ($routeParams.id > 0) {
		$scope.vendorAddress = true;
		$scope.companyAddress = true;
		$scope.companyContact = true;
		$scope.vendorContact = true;
		$scope.vendorEmail = true;
		
		
		if($cookieStore.get("roleNamesArray").includes('Company Admin')){
	        $scope.readOnly = true;
	        $scope.correctionBtn = true;
	        $scope.updateBtn= false;
	        $scope.approve = true;
	        $scope.reject = true;
	        $scope.print = false;
	        $scope.download = false;
	        $scope.sendForApprovalBtn = false;
	        $scope.gridButtons = false;
	        $scope.cancel = false;
	        $scope.saveBtn = false;
	        $scope.resetBtn = false;
	        $scope.commentsShow = true;
		}else if($cookieStore.get("roleNamesArray").includes('Vendor Admin')){
			$scope.readOnly = true;
			$scope.gridButtons = true;
	        $scope.correctionBtn = false;
	        $scope.updateBtn= true;
	        $scope.approve = false;
	        $scope.reject = false;
	        $scope.print =  true;
	        $scope.download =  true;
	        $scope.sendForApprovalBtn = false;
	        $scope.cancel = false;
	        $scope.saveBtn = false;
	        $scope.resetBtn = false;
	        $scope.commentsShow = false;
		}
    } else {
    	if($cookieStore.get("roleNamesArray").includes('Vendor Admin')){
			$scope.readOnly = false;
	        $scope.correctionBtn = false;
	        $scope.updateBtn= false;
	        $scope.approve = false;
	        $scope.reject = false;
	        $scope.print = true;
	        $scope.download =  true;
	        $scope.sendForApprovalBtn = true;
	        $scope.cancel = false;
	        $scope.saveBtn = true;
	        $scope.resetBtn = false;
	        $scope.gridButtons = true;
	        $scope.commentsShow = false;
		}
    };
    
    $scope.updateBtnAction = function (this_obj) {
    	if($cookieStore.get("roleNamesArray").includes('Vendor Admin')){
			$scope.readOnly = true;
			$scope.onlyRead = true;
	        $scope.correctionBtn = false;
	        $scope.updateBtn= false;
	        $scope.approve = false;
	        $scope.reject = false;
	        $scope.print = true;
	        $scope.download = true;
	        $scope.sendForApprovalBtn = true;
	        $scope.cancel = false;
	        $scope.saveBtn = true;
	        $scope.resetBtn = false;
	        $scope.gridButtons =true;
	        $scope.commentsShow = false;
		}
       
        $('.dropdown-toggle').removeClass('disabled');
    };
    
    
    
    $scope.cancelBtnAction = function(){
    	$scope.readOnly = true;
        $scope.updateBtn = true;
        $scope.saveBtn = false;
        $scope.viewOrUpdateBtn = true;
        $scope.correcttHistoryBtn = false;
        $scope.resetBtn = false;
        $scope.transactionList = false;
        $scope.cancelBtn = false;
        $scope.gridButtons = false;
        $scope.commentsShow = false;
        $scope.getData('invoiceController/getInvoiceById.json', { customerId: $cookieStore.get('customerId'),companyId:$cookieStore.get('companyId'),vendorId:($cookieStore.get('vendorId') != null && $cookieStore.get('vendorId') != "") ? $cookieStore.get('vendorId')  : 0, invoiceId:$routeParams.id }, 'invoiceList');
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
    		} else if(type == 'invoiceList'){
    			if(response.data != undefined){
    				//alert(angular.toJson(response.data.companyList));
	    			$scope.invoiceList.companyList = response.data.companyList;
	    			$scope.invoiceList.vendorList = response.data.vendorList;
	    			$scope.invoiceList.locationsList = response.data.locationsList;
	    			$scope.invoiceList.departmentsList = response.data.departmentsList;
	    			$scope.defaultCurrency = response.data.defaultCurrency;
	    			
	    			if(response.data.defaultCurrency != undefined && response.data.defaultCurrency != null){
	             	   $scope.defaultCurrency = response.data.defaultCurrency[0].defaultCurrencyName;
	             	   $scope.defaultCurrencyId = response.data.defaultCurrency[0].defaultCurrencyId;
	                }else{
	             	   $scope.defaultCurrency = null;
	             	   $scope.defaultCurrencyId = null;
	             	   
	                }
	    			
	    			if( response.data.companyList != undefined && response.data.companyList != "" && response.data.companyList.length > 0 ){
		                $scope.invoice.companyId = response.data.companyList[0].id;	              
		            }
	    			
	    			if( response.data.vendorList != undefined && response.data.vendorList != "" && response.data.vendorList.length > 0 ){
		                $scope.invoice.vendorId = response.data.vendorList[0].id;	
		                $scope.dropdownDisable = true;
			         }
	    			
	                if($routeParams.id > 0 && response.data.invoiceVo != undefined &&response.data.invoiceVo != null && response.data.invoiceVo.length > 0 ){
	                	$scope.invoice = response.data.invoiceVo[0];
                		$scope.invoice.invoiceDate = $filter('date')(response.data.invoiceVo[0].invoiceDate, 'dd/MM/yyyy')
	                	
	                }else if(myService.invoiceCookies != undefined && myService.invoiceCookies != null && myService.invoiceCookies != "" ){
	                	//alert(2);
	                	$scope.invoice = myService.invoiceCookies;
	                	$scope.invoice.invoiceDate = $filter('date')(myService.invoiceCookies.invoiceDate, 'dd/MM/yyyy');
	                	$scope.invoice.invoiceNumber = null;
	                	$scope.invoice.customerId = $cookieStore.get('customerId');
		            	$scope.invoice.companyId = $cookieStore.get('companyId');
		            	$scope.invoice.vendorId = $cookieStore.get('vendorId');
		            	$scope.invoice.status = "New";
	                	myService.invoiceCookies = null;
	                	
	                	//To get company Address
		    			if( response.data.companyAddress != undefined && response.data.companyAddress != null && response.data.companyAddress.length > 0){
		    				if(response.data.companyAddress[0].name != null && response.data.companyAddress[0].name != undefined && response.data.companyAddress[0].name != ""){
		    					$scope.invoice.companyAddress = response.data.companyAddress[0].name;
		    					$scope.invoice.companyAddressId = response.data.companyAddress[0].id;
		    					$scope.companyAddress = true;
		    				}else{
		    					$scope.companyAddress = false;
		    				}
		    				//$scope.invoice.companyAddress = response.data.companyAddress[0].name;
		    				//$scope.invoice.companyAddressId = response.data.companyAddress[0].id;
		    			}
		    			
		    			//To get Company Contact
		    			if(response.data.companyContact != undefined && response.data.companyContact != null && response.data.companyContact.length > 0){
		    				//$scope.invoice.companyContactNumber = response.data.companyContact[0].name;
		    				//$scope.invoice.companyContactId = response.data.companyContact[0].id;
		    				if(response.data.companyContact[0].name != null && response.data.companyContact[0].name != undefined && response.data.companyContact[0].name != ""){
		    					$scope.invoice.companyContactNumber = response.data.companyContact[0].name;
		    					$scope.invoice.companyContactId = response.data.companyContact[0].id;
		    					$scope.companyContact = true;
		    				}else{
		    					$scope.companyContact = false;
		    				}
		    			}
		    			
		    			//To get vendor details
		                if(response.data.vendorAddressContact != undefined && response.data.vendorAddressContact != null &&  response.data.vendorAddressContact.length > 0){
		                	$scope.vendorName = response.data.vendorAddressContact[0].vendorName;
		                	
		                	if(response.data.vendorAddressContact[0].address != null && response.data.vendorAddressContact[0].address != undefined && response.data.vendorAddressContact[0].address != ""){
		                		$scope.invoice.vendorAddress = response.data.vendorAddressContact[0].address;
		    					$scope.vendorAddress = true;
		    				}else{
		    					$scope.vendorAddress = false;
		    				}
		                	
		    				if(response.data.vendorAddressContact[0].telephoneNumber != null && response.data.vendorAddressContact[0].telephoneNumber != undefined && response.data.vendorAddressContact[0].telephoneNumber != ""){
		    					$scope.invoice.vendorContactNumber = response.data.vendorAddressContact[0].telephoneNumber;
		    					$scope.vendorContact = true;
		    				}else{
		    					$scope.vendorContact = false;
		    				}
		    				
		    				if(response.data.vendorAddressContact[0].email != null && response.data.vendorAddressContact[0].email != undefined && response.data.vendorAddressContact[0].email != ""){
		    					$scope.invoice.vendorEmail = response.data.vendorAddressContact[0].email;
		    					$scope.vendorEmail = true;
		    				}else{
		    					$scope.vendorEmail = false;
		    				}
		    				
		    			}
	                	
		            }else {
		            	
		            	$scope.invoice.particularsList = [];
		            	//To get company Address
		    			if( response.data.companyAddress != undefined && response.data.companyAddress != null && response.data.companyAddress.length > 0){
		    				if(response.data.companyAddress[0].name != null && response.data.companyAddress[0].name != undefined && response.data.companyAddress[0].name != ""){
		    					$scope.invoice.companyAddress = response.data.companyAddress[0].name;
		    					$scope.invoice.companyAddressId = response.data.companyAddress[0].id;
		    					$scope.companyAddress = true;
		    				}else{
		    					$scope.companyAddress = false;
		    				}
		    				//$scope.invoice.companyAddress = response.data.companyAddress[0].name;
		    				//$scope.invoice.companyAddressId = response.data.companyAddress[0].id;
		    			}
		    			
		    			//To get Company Contact
		    			if(response.data.companyContact != undefined && response.data.companyContact != null && response.data.companyContact.length > 0){
		    				//$scope.invoice.companyContactNumber = response.data.companyContact[0].name;
		    				//$scope.invoice.companyContactId = response.data.companyContact[0].id;
		    				if(response.data.companyContact[0].name != null && response.data.companyContact[0].name != undefined && response.data.companyContact[0].name != ""){
		    					$scope.invoice.companyContactNumber = response.data.companyContact[0].name;
		    					$scope.invoice.companyContactId = response.data.companyContact[0].id;
		    					$scope.companyContact = true;
		    				}else{
		    					$scope.companyContact = false;
		    				}
		    			}
		    			
		    			//To get vendor details
		                if(response.data.vendorAddressContact != undefined && response.data.vendorAddressContact != null &&  response.data.vendorAddressContact.length > 0){
		                	$scope.vendorName = response.data.vendorAddressContact[0].vendorName;
		                	
		                	if(response.data.vendorAddressContact[0].address != null && response.data.vendorAddressContact[0].address != undefined && response.data.vendorAddressContact[0].address != ""){
		                		$scope.invoice.vendorAddress = response.data.vendorAddressContact[0].address;
		    					$scope.vendorAddress = true;
		    				}else{
		    					$scope.vendorAddress = false;
		    				}
		                	
		                	
		    				
		    				if(response.data.vendorAddressContact[0].telephoneNumber != null && response.data.vendorAddressContact[0].telephoneNumber != undefined && response.data.vendorAddressContact[0].telephoneNumber != ""){
		    					$scope.invoice.vendorContactNumber = response.data.vendorAddressContact[0].telephoneNumber;
		    					$scope.vendorContact = true;
		    				}else{
		    					$scope.vendorContact = false;
		    				}
		    				
		    				if(response.data.vendorAddressContact[0].email != null && response.data.vendorAddressContact[0].email != undefined && response.data.vendorAddressContact[0].email != ""){
		    					$scope.invoice.vendorEmail = response.data.vendorAddressContact[0].email;
		    					$scope.vendorEmail = true;
		    				}else{
		    					$scope.vendorEmail = false;
		    				}
		    				
		    			}
		                
		            	$scope.gridButtons = true;
		            }
	                
    			}
    			// for button views
   			 	if($scope.buttonArray == undefined || $scope.buttonArray == '')
   				 	$scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Invoice'}, 'buttonsAction');
   			 
    		}else if(type == 'saveInvoice'){
    			if(response.data.id > 0){
    				$scope.Messager('success', 'Success', 'Invoice saved successfully.');
    				$location.path('/addInvoice/'+response.data.id);
    			}else{
    				$scope.Messager('error', 'Error', 'Technical Issue Occurred.');
    				$scope.invoice.invoiceDate =  $filter('date')( $('#invoiceDate').val(), 'dd/MM/yyyy');
    			}
    		}
	    },function () { 
	    	$scope.Messager('error', 'Error', 'Technical Issue Occurred. Please try again after some time');
	    });
    };
    
   // $scope.getData('vendorController/getCustomerNamesAsDropDown.json', { customerId: $cookieStore.get('customerId')}, 'customerList')
	
   
	$scope.getData('invoiceController/getInvoiceById.json', { customerId: $cookieStore.get('customerId'),companyId:$cookieStore.get('companyId'),vendorId:($cookieStore.get('vendorId') != null && $cookieStore.get('vendorId') != "") ? $cookieStore.get('vendorId')  : 0, invoiceId:$routeParams.id }, 'invoiceList');
	
	$scope.sendForApprovalAction = function($event){
		$scope.invoice.status = "Pending For Approval";
		$scope.saveInvoice($event);
	};
	
	$scope.saveAction = function($event){
		$scope.invoice.status = "Initiated";
		$scope.saveInvoice($event);
	};
	
	$scope.approveAction = function($event){
		$scope.invoice.status = "Approved";
		$scope.saveInvoice($event);
	};
	
	$scope.rejectAction = function($event){
		$scope.invoice.status = "Rejected";
		$scope.saveInvoice($event);
	};
	
	$scope.correctionAction = function($event){
		$scope.invoice.status = "Correction Required";
		$scope.saveInvoice($event);
	};
	
	
	$scope.saveInvoice =function($event){
    	var regex = new RegExp("[A-Za-z]{3}(P|p|C|c|H|h|F|f|A|a|T|t|B|b|L|l|J|j|G|j)[A-Za-z]{1}[0-9]{4}[A-Za-z]{1}$");
    	
    	if($('#invoiceForm').valid()){
    		
    		$scope.invoice.invoiceDate = moment($('#invoiceDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 

    		if($scope.invoice.panNumber != undefined && $scope.invoice.panNumber != null && $scope.invoice.panNumber !='' && !(regex.test($scope.invoice.panNumber))){
    	  		$scope.Messager('error', 'Error', 'Please enter valid pan number');
    	  	}else{  
   			 	
    			$scope.invoice.createdBy = $cookieStore.get('createdBy'); 
   			 	$scope.invoice.modifiedBy = $cookieStore.get('modifiedBy');
   			    $scope.getData('invoiceController/saveInvoice.json', angular.toJson($scope.invoice), 'saveInvoice',angular.element($event.currentTarget)); 
    		}
    	}
    };
	

	$scope.particularPlusIcon = function(){
	   $scope.particular = new Object();
	   $('#fromDate').val("");
	   $('#toDate').val("");
	   $scope.particular.currencyName =  $scope.defaultCurrency ;
	   $scope.particular.currencyId = $scope.defaultCurrencyId ;
	   $scope.popUpSave = true;
       $scope.popUpUpdate =false;
	};
	
	$scope.saveParticulars = function(){
		//alert((new Date(moment($('#fromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime() > new Date(moment($('#toDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime()));
		if($('#particularsForm').valid()){
			if($('#fromDate').val() != undefined && $('#fromDate').val() != null && $('#toDate').val() != undefined && $('#toDate').val() != null && $('#toDate').val() != undefined
	    			   						&& (new Date(moment($('#fromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime() > new Date(moment($('#toDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime())){
				$scope.Messager('error', 'Error', 'From Date should not be less than to Date');
 		    }else {
 		    	$scope.invoice.particularsList.push({
	          		'particulars':$scope.particular.particulars,  		
	          		'fromDate':$('#fromDate').val(),
	          		'toDate':$('#toDate').val(),
	          		'rate':$scope.particular.rate,
	          		'amount':$scope.particular.amount,
	          		'frequency':$scope.particular.frequency,
 		    		'currencyId':$scope.particular.currencyId,
 		    		'currencyName':$scope.particular.currencyName,
          		
 		    	}); 
			    $('div[id^="particular"]').modal('hide');
			    
			    var total = 0;
			    $scope.popUpSave = true;
		       	$scope.popUpUpdate =false;
		       	angular.forEach($scope.invoice.particularsList, function(value, key){	
		    		  if(value.amount != undefined && value.amount != null ){
		    			  total = parseInt(total)+parseInt(value.amount);
		    		  }
	    	   });
		       	//alert(total);
		       	$scope.invoice.totalAmount = total;
		       	$scope.inWords(total);
 		    }
		}
	};
	
	
    
    $scope.deleteParticulars = function($event){    	
 	   var del = $window.confirm('Are you sure you want to delete?');
 	   var total = 0;
 	   if (del) {
 		   $scope.invoice.particularsList.splice($($event.target).parent().parent().index(),1);
 		    angular.forEach($scope.invoice.particularsList, function(value, key){	
				if(value.amount != undefined && value.amount != null ){
					total = total+parseInt(value.amount);
				}
			});
			$scope.invoice.totalAmount = total;
			$scope.inWords(total);
 	   }
    };
    
    $scope.editParticulars = function($event){    	
    	$scope.particularVar = $($event.target).parent().parent().index();
        $scope.particular =  $scope.invoice.particularsList[$($event.target).parent().parent().index()];
        
        $scope.particular.currencyName =  $scope.defaultCurrency ;
 	    $scope.particular.currencyId = $scope.defaultCurrencyId ;
        $scope.popUpSave = false;
        $scope.popUpUpdate =true;
    };
       
       
   $scope.updateParticulars = function($event){    	
 	  if($('#particularsForm').valid()){
 		  $scope.invoice.particularsList.splice($scope.particularVar,1);    	

 		  if($('#fromDate').val() != undefined && $('#fromDate').val() != null && $('#toDate').val() != undefined && $('#toDate').val() != null && $('#toDate').val() != undefined
   						&& (new Date(moment($('#fromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime() > new Date(moment($('#toDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime())){
				$scope.Messager('error', 'Error', 'From Date should not be less than to Date');
 		  }else {
				$scope.invoice.particularsList.push({
					'particulars':$scope.particular.particulars,  		
					'fromDate':$('#fromDate').val(),
					'toDate':$('#toDate').val(),
					'rate':$scope.particular.rate,
					'amount':$scope.particular.amount,
					'frequency':$scope.particular.frequency,
					'currencyId':$scope.particular.currencyId,
					'currencyName':$scope.particular.currencyName,
				
				}); 
				$('div[id^="particular"]').modal('hide');
				
				$scope.popUpSave = true;
				$scope.popUpUpdate =false;
				
				var total = 0;
				angular.forEach($scope.invoice.particularsList, function(value, key){	
					if(value.amount != undefined && value.amount != null ){
						total = total+parseInt(value.amount);
					}
				});
				$scope.invoice.totalAmount = total;
				$scope.inWords(total);
		}
 	  }
   };
   
   $scope.close_particulars = function(){
	   $('div[id^="particular"]').modal('hide');
   };
   
  var a = ['','one ','two ','three ','four ', 'five ','six ','seven ','eight ','nine ','ten ','eleven ','twelve ','thirteen ','fourteen ','fifteen ','sixteen ','seventeen ','eighteen ','nineteen '];
  var b = ['', '', 'twenty','thirty','forty','fifty', 'sixty','seventy','eighty','ninety'];

 $scope.inWords = function(num) {
      if ((num = num.toString()).length > 9) return 'overflow';
      n = ('000000000' + num).substr(-9).match(/^(\d{2})(\d{2})(\d{2})(\d{1})(\d{2})$/);
      if (!n) return; var str = '';
      str += (n[1] != 0) ? (a[Number(n[1])] || b[n[1][0]] + ' ' + a[n[1][1]]) + 'crore ' : '';
      str += (n[2] != 0) ? (a[Number(n[2])] || b[n[2][0]] + ' ' + a[n[2][1]]) + 'lakh ' : '';
      str += (n[3] != 0) ? (a[Number(n[3])] || b[n[3][0]] + ' ' + a[n[3][1]]) + 'thousand ' : '';
      str += (n[4] != 0) ? (a[Number(n[4])] || b[n[4][0]] + ' ' + a[n[4][1]]) + 'hundred ' : '';
      str += (n[5] != 0) ? ((str != '') ? 'and ' : '') + (a[Number(n[5])] || b[n[5][0]] + ' ' + a[n[5][1]]) : '' ;
      str += ' rupees only ' ;
      $scope.invoice.totalAmountInWords = str;
      return str;
  };
 
//To download the file
/* $scope.fun_download = function(event,fileName){
	  var file = document.getElementById("fileName").files[0];
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
 };
 
//To get the file name
 $scope.getFileDetails = function (e) {  
     $scope.$apply(function () {
          $scope.theFile = e.files[0];
     });
 };*/
 
}]);