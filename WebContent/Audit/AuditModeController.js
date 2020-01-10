'use strict';
var auditController = angular.module("myApp.auditMode", []);

auditController.controller('auditController', ['$scope','$http', '$resource','$routeParams','$filter','myservice','$cookieStore', function ($scope,$http, $resource, $routeParams,$filter,myservice,$cookieStore) {
	 $.material.init();
		var customerSearchResultsTable;
	/*	$('#startDate,#endDate').bootstrapMaterialDatePicker({
	        time : false,
	        Button : true,
	        format : "DD/MM/YYYY",
	        clearButton: true
	    });*/
		
		
		    $( "#startDate,#endDate" ).datepicker({
		      changeMonth: true,
		      changeYear: true,
		      dateFormat:"dd/mm/yy",
		      showAnim: 'slideDown'
		    	  
		    });
	 
	    	    
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
	    
	    $scope.audit = new Object();
	  //  $('#auditHistory').hide();
	    $('#history').hide();
	    
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
	        	
	        	if(type == 'customerList'){        		
	        		$scope.customerList = response.data.customerList; 	
	        		 if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
	   		                $scope.customerId=response.data.customerList[0].customerId;		                
	   		                $scope.customerChange();
	   		                }
	        	}else if( type ==  'schemaName'){            	 
	             	  $scope.schemaName = response.data.name;            	
	            }else if(type == 'customerChange'){	        		
	        		$scope.companyList = response.data; 
	        		  if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	     	                $scope.companyId = response.data[0].id;
	     	               $scope.companyChange();
	     	                }
	        	}else if (type == 'companyChange') {	        		
	        		$scope.countriesList = response.data.countriesList;              
	                $scope.locationList = response.data.locationList;	                
	                $scope.countryId = response.data.countriesList[0].id;
	            }else if(type == 'saveAudit'){
	            	//alert(angular.toJson(response.data));
	            	 $scope.Messager('success', 'success', 'Audit Details Saved Successfully',buttonDisable);
	            }else if(type == 'getAuditList'){	
	            	//alert($.fn.DataTable.isDataTable( '#searchResults' ));
	            	
	            	 if ( ! $.fn.DataTable.isDataTable( '#searchResults' ) ) {        		   
	            		 customerSearchResultsTable = $('#searchResults').DataTable({  
	            			   "data" : response.data.completeAuditList,
	                           "columns": [                        
	                              { "data": "startDate","render":function(data){
	                   				  return $filter('date')(data, "dd/MM/yyyy");
	                              }},
	                              { "data": "endDate" ,"render":function(data){
	                   				  return $filter('date')(data, "dd/MM/yyyy");
	                              }},
	                              { "data": "comments" }]
	                     });  
	            		}
	            	 
	            	if(response.data.auditList.length > 0){
	            	 $scope.audit = response.data.auditList[0];
	            	 $scope.customerId = response.data.auditList[0].customerId;
		    		 $scope.companyId = response.data.auditList[0].companyId;
		    		 $scope.locationId = response.data.auditList[0].locationId;
		    		 $scope.countryId = response.data.auditList[0].countryId;		    		 
		    		 $scope.startDate = $filter('date')( response.data.auditList[0].startDate, 'dd/MM/yyyy');  
		    		 $scope.endDate = $filter('date')( response.data.auditList[0].endDate, 'dd/MM/yyyy'); 		    		 
		    		 var  start, end;
		    		 start = moment($scope.startDate, 'DD/MM/YYYY'); 
			    	 end = moment($scope.endDate, 'DD/MM/YYYY');
			    	
			    	 var x = end.diff(start, 'days');
			    	
			    	 if(end.diff(start, 'days')+1 == 1){
			    		 $('#duration').html('').append(end.diff(start, 'days')+1).append(' Day');
			    		}else if(end.diff(start, 'days')+1 > 1){
			    			 $('#duration').html('').append(end.diff(start, 'days')+1).append(' Days');
			    		}else{
			    			$('#duration').html('').append('0').append(' Days');
			    		}
			    	 $scope.fun_renderTable();   	  
			    				    	 
	            	}else{
	            		$scope.renderTable = false;	  
	            		$scope.audit = new Object();
	    	    		$scope.startDate =$filter('date')( "", 'dd/MM/yyyy');
	    	    		$scope.endDate = $filter('date')( "", 'dd/MM/yyyy');
	    	    		$scope.audit.startDate =$filter('date')( "", 'dd/MM/yyyy');
	    	    		$scope.audit.endDate = $filter('date')( "", 'dd/MM/yyyy');	    	    		
	    	    		$('#duration').html('').append('0').append(' Days');	    	    		
	            	}
	            	
	            	if(response.data.completeAuditList.length > 0){
	            		$('#history').show();
	            		$scope.completeAuditList = response.data.completeAuditList;
				    	 customerSearchResultsTable.clear().rows.add($scope.completeAuditList).draw();
	            	}else{
	            		$scope.completeAuditList =[];
	    	    		$scope.history='N';
	    	    		 $('#history').hide();
	    	    		customerSearchResultsTable.clear().rows.add($scope.completeAuditList).draw(); 
	            	}
	            
	            	
	            }
	        	},
	        function () {
	        	  $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
	        	           
	          });
	    	}      
	    
	    
	    $scope.getData("reportController/getSchemaNameByUserId.json",{userId : $cookieStore.get('userId') } ,"schemaName" );
	    $scope.getData('vendorController/getCustomerNamesAsDropDown.json', { customerId: $cookieStore.get('customerId')}, 'customerList')
     
     
	    $scope.customerChange = function () { 
	    	$scope.companyList ="";
	    	$scope.countriesList = "";            
            $scope.locationList = "";
	    	if($scope.customerId != undefined && $scope.customerId != ""){
	    	$scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.customerId, companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.companyId != undefined ?  $scope.companyId  : 0 }, 'customerChange');
	    	}
	    };
	    
	    
	    
	    $scope.companyChange = function () {
	    	$scope.countriesList = "";           
            $scope.locationList = "";
	    	if($scope.companyId != undefined && $scope.companyId != ""){
	        $scope.getData('vendorController/getVendorAndLocationNamesAsDropDowns.json', { customerId: $scope.customerId,companyId: $scope.companyId }, 'companyChange');
	    	}
	    };
	    
	    
	    $scope.getAuditList = function () {	    	
	    	if($scope.companyId != undefined && $scope.companyId != "" && $scope.customerId != undefined && $scope.customerId != "" && $scope.locationId != undefined && $scope.locationId != "" &&  $scope.countryId != undefined && $scope.countryId != ""){
	        $scope.getData('auditController/getAuditList.json',   { customerId: $scope.customerId,companyId: $scope.companyId, locationId: $scope.locationId, countryId: $scope.countryId ,schemaName : $scope.schemaName}, 'getAuditList');
	    	}
	    };
	    
	  
	    
	    
	    $scope.saveAudit =function($event){
	    
	    	 if($('#auditModeForm').valid())
	  		{	    		 
	    		  
	    		 $scope.audit.customerId = $scope.customerId;
	    		 $scope.audit.companyId = $scope.companyId;
	    		 $scope.audit.locationId = $scope.locationId;
	    		 $scope.audit.countryId = $scope.countryId;
	    		 $scope.audit.auditId = 0;
	    		 $scope.audit.comments = $scope.audit.comments != null ? $scope.audit.comments : '';
	    		 $scope.audit.createdBy = $cookieStore.get('createdBy'); 
	 	    	 $scope.audit.modifiedBy = $cookieStore.get('modifiedBy');
	 	    	 $scope.audit.schemaName = $scope.schemaName;
	 	    	 
	    		 if($scope.audit.auditStatus == 'Y' || $scope.audit.auditStatus == true){
	    			
				    	if($('#startDate').val() != undefined && $('#startDate').val() != "" && $('#endDate').val() != undefined && $('#endDate').val() != "" ){
				    		
				    		 var startDate = $('#startDate').val(), endDate = $('#endDate').val(), start, end;					    	 
					    		start = moment(startDate, 'DD/MM/YYYY'); 
					    		end = moment(endDate, 'DD/MM/YYYY'); 	    	 
					    		end.diff(start, 'days')
					    		 if( end.diff(start, 'days')+1 > 0){
					    			// alert();
					    			 $scope.audit.startDate = moment($('#startDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
					    			 $scope.audit.endDate = moment($('#endDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');	
					    			 $scope.getData('auditController/saveAudit.json', angular.toJson($scope.audit), 'saveAudit',angular.element($event.currentTarget));
					    		 }else{
					    			 
					    			 $scope.Messager('error', 'Error', 'End date should be greater than start date');
					    		 }
				    	}else if($('#endDate').val() == undefined ||  $('#endDate').val() == ""){
				    		$scope.audit.startDate = moment($('#startDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
			    			// $scope.audit.endDate = moment($('#endDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');	
			    			 //alert(angular.toJson($scope.audit));
			    			 $scope.getData('auditController/saveAudit.json', angular.toJson($scope.audit), 'saveAudit',angular.element($event.currentTarget));
				    	}	
				    		
				  
			    		 
	    		 }else{
	    			 $scope.getData('auditController/saveAudit.json', angular.toJson($scope.audit), 'saveAudit',angular.element($event.currentTarget)); 
	    		 }
	    	 
	  		
	  	}
	    	
	    }
	    
	    $scope.fun_renderTable = function(){    	
	    
	    	if($scope.audit.auditStatus == 'Y' ){    	    		
	    		$scope.renderTable = true;	    		
	    	}else{    	    		
	    		$scope.renderTable = false;	    		
	    		$scope.startDate =$filter('date')( "", 'dd/MM/yyyy');
	    		$scope.endDate = $filter('date')( "", 'dd/MM/yyyy');
	    		$scope.audit.startDate =$filter('date')( "", 'dd/MM/yyyy');
	    		$scope.audit.endDate = $filter('date')( "", 'dd/MM/yyyy');
	    		$('#duration').html('').append('0').append(' Days');
	    		$scope.audit.comments = ""
	    			//alert($scope.audit.comments);
	    	}   
	    }
	    

		/*$scope.fun_ShowHistory = function() {
					if ($scope.history == 'Y') {
						$('#auditHistory').show()
					} else {
						$('#auditHistory').hide()
					}
			}*/
	    
	    
	    
	    $scope.dateChange= function(){
	    	
	    	 if($('#startDate').val() != undefined && $('#startDate').val() != "" && $('#endDate').val() != undefined && $('#endDate').val() != "" ){
    			
	    		
    		 }
    		 
	    }
	    
	    $('#startDate,#endDate').change(function() {
	    	
	    	if($('#startDate').val() != undefined && $('#startDate').val() != "" && $('#endDate').val() != undefined && $('#endDate').val() != "" ){    		
	    		var startDate = $('#startDate').val(), endDate = $('#endDate').val(), start, end;
	    	 
	    		start = moment(startDate, 'DD/MM/YYYY'); 
	    		end = moment(endDate, 'DD/MM/YYYY'); 	    	 
	    		
	    		// $scope.duration = end.diff(start, 'days');
	    		if(end.diff(start, 'days') == 1){
	    		 $('#duration').html('').append(end.diff(start, 'days')+1).append(' Day');
	    		}else{
	    			 $('#duration').html('').append(end.diff(start, 'days')+1).append(' Days');
	    		}
	    		
	    	}else{
	    		$('#duration').html('').append('0').append(' Days');
	    	}
	    });
	    
	  
	  
     
     
}]);



