'use strict';
LocationController.controller("LocationCharacteristicsCtrl", ['$scope', '$http', '$filter', '$resource','$location', '$routeParams','myservice','$cookieStore', function ($scope, $http, $filter,  $resource, $location, $routeParams, myservice, $cookieStore) {
	     
			$.material.init();
			/*$('#pfRegistrationDate,#pfStartDate,#esiRegistrationDate,#esiStartDate').bootstrapMaterialDatePicker({
		    	 time: false,
		         clearButton: true,
		         format : "DD/MM/YYYY"
			});*/

			$('#pfRegistrationDate,#pfStartDate,#esiRegistrationDate,#esiStartDate').datepicker({
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
		    $scope.location = new Object();
		    if ($routeParams.id > 0) {
	           $scope.readOnly = true;
	           $scope.datesReadOnly = false;
	           $scope.updateBtn = true;
	           $scope.saveBtn = false;
	           $scope.viewOrUpdateBtn = false;
	           $scope.correcttHistoryBtn = false;
	           $scope.resetBtn = false;
	           $scope.cancelBtn = false;
	           $scope.transactionList = false;
	          /* $scope.location.customerId 		= $cookieStore.get('customerId');
	    	   $scope.location.companyId 		= $cookieStore.get('companyId');
	    	   $scope.location.countryId 		= $cookieStore.get('countryId');
	    	   $scope.location.locationCode     = $cookieStore.get('locationCode');*/
	           
	       } else {
	
	    	    $scope.customerId 		= myservice.customerId;
	    	    $scope.locationDetailsId = myservice.locationDetailsId;
	    	    
	           $scope.readOnly = false;
	           $scope.datesReadOnly = false;
	           $scope.updateBtn = false;
	           $scope.saveBtn = true;
	           $scope.cancelBtn = true;
	           $scope.resetBtn = false;
	           $scope.transactionList = false;
	       }

		    $scope.location.locationDetailsId = myservice.locationDetailsId;
		    $scope.locationDetailsId =$cookieStore.get('locationDetailsId');
	       $scope.getData = function (url, data, type,buttonDisable) {
	           var req = {
	               method: 'POST',
	               url: ROOTURL + url,
	               headers: {
	                   'Content-Type': 'application/json'
	               },
	               data: data
	           }
	           $http(req).then(function (response) {

	               if (type == 'locationList') {
	   				  // alert(JSON.stringify(response.data));
	                   $scope.locationList = response.data;
	                   $scope.location = response.data.location[0];
	                  
	                   if($scope.location != undefined){
	                	   $scope.locationLst = new Object();
	                	   $scope.location.locationId = myservice.locationId;
	                	   $scope.location.pfStartDate = $filter('date')($scope.location.pfStartDate, 'dd/MM/yyyy');
	                       $scope.location.pfRegistrationDate = $filter('date')( $scope.location.pfRegistrationDate, 'dd/MM/yyyy');
	                       $scope.location.esiStartDate = $filter('date')( $scope.location.esiStartDate, 'dd/MM/yyyy');
	                       $scope.location.esiRegistrationDate = $filter('date')( $scope.location.esiRegistrationDate, 'dd/MM/yyyy');
	                	  /* $scope.location.customerId 		= myservice.customerId;
	        	    	   $scope.location.companyId 	= myservice.companyId;
	        	    	   $scope.location.countryId 		= myservice.countryId;
	        	    	   $scope.location.locationCode     = myservice.locationCode*///$cookieStore.get('locationCode')
	                	  
	        	    	   $scope.location.locationDetailsId = myservice.locationDetailsId;
	                	   //$scope.locationLst.cmpId = response.data.companyId;
	                	   $scope.locationList.companyList = response.data.companyList;
	                   }else{
	                	 //  alert($cookieStore.get('customerId'));
	                	   
	                	   $scope.location = new Object();
	                	   $scope.location.locationId = myservice.locationId;
	                	   $scope.locationList.companyList = response.data.companyList;
	               
	                   }
	                   
	            
	               }else if (type == 'customerChange') {
	   					//alert(JSON.stringify(response.data.companyDetails));
	                    $scope.locationList.companyList = response.data;
	                    
	                    $scope.location.companyInfoId = $scope.location.companyId.companyInfoId;
	                   // alert($scope.location.companyInfoId);
	                    $scope.location.companyId = response.data.companyDetails.cmpId;
	               } else if (type == 'saveLocation') {
	            	   if(response.data.id > 0){
	            	   // alert(response.data.name);
	            		   $scope.Messager('success', 'success', 'location characterstics Saved Successfully',buttonDisable);
	            		   $location.path('/LocationCharacteristics/'+response.data.id);
	            	   }else if(response.data.id < 0){
	            		   $scope.Messager('error', 'Error', response.data.name,buttonDisable);
		               }else{
		            	   $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
		               }
	                   // alert(angular.toJson(response.data));
	               }
	           },
	           function () { 
	        	   $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
	        	   });
	       }
	       //alert($cookieStore.get('locationId'));
	       	$scope.getData('LocationController/getLocationCharacteristicsByLocationId.json', { locationId: ( myservice.locationId != undefined ? myservice.locationId : '0'), customerId: myservice.customerId}, 'locationList')
	       	
	       	$scope.customerChange = function () {
	   			//alert($scope.location.customerId);
	           $scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.location.customerId,companyId:$scope.location.companyId }, 'customerChange');
	       	 //$scope.getData( 'CompanyController/getCompanyDetailsForGridView.json' ,{customerId : ( $scope.location.customerId == undefined )? '0' : $scope.location.customerId, companyName:"", countryName:"" ,status:""   } , "customerChange" );    	   
	       	 
	       	};
	       	$scope.save = function () {
	       		if($('#charForm').valid()){
	       			if(($('#pfRegistrationDate').val() != undefined || $('#pfRegistrationDate').val() != null) && ($('#pfStartDate').val() != undefined && $('#pfStartDate').val() != null)  && (new Date(moment($('#pfRegistrationDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime() >= new Date(moment($('#pfStartDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') ).getTime())){
	          	    	$scope.Messager('error', 'Error', 'PF Registration Date should not be less than PF Start Date');
	            	}else if(($('#esiRegistrationDate').val() != undefined || $('#esiRegistrationDate').val() != null) && ($('#esiStartDate').val() != undefined && $('#esiStartDate').val() != null)  && (new Date(moment($('#esiRegistrationDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime() >= new Date(moment($('#esiStartDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime())){
	          	    	$scope.Messager('error', 'Error', 'ESI Registration Date should not be less than ESI Start Date');
	            	}else{
	       			
		           // alert(angular.toJson($scope.location));
		           // $scope.location.transactionDate = $('#transactionDate').val();
		            /*$scope.location.pfRegistrationDate = $('#PFRegistrationDate').val();
		            $scope.location.pfStartDate = $('#PFStartDate').val();
		            $scope.location.esiRegistrationDate = $('#esiRegistrationDate').val();
		            $scope.location.esiStartDate = $('#esiStartDate').val();*/
			            if($('#pfStartDate').val() != '' && $('#pfStartDate').val() != undefined)
					        $scope.location.pfStartDate = moment($('#pfStartDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
					        
					    if($('#pfRegistrationDate').val() != '' && $('#pfRegistrationDate').val() != undefined)
					        $scope.location.pfRegistrationDate = moment($('#pfRegistrationDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
					        
					    if($('#esiStartDate').val() != '' && $('#esiStartDate').val() != undefined)
					        $scope.location.esiStartDate = moment($('#esiStartDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
					        
					    if($('#esiRegistrationDate').val() != '' && $('#esiRegistrationDate').val() != undefined)
					        $scope.location.esiRegistrationDate = moment($('#esiRegistrationDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
				       
					    if($scope.location.isUnion == false){
					    	$scope.location.unionName = null;
					    }
					    
					    $scope.location.companyInfoId = $cookieStore.get('companyInfoId');
				        $scope.location.createdBy = $cookieStore.get('createdBy');
				        $scope.location.modifiedBy = $cookieStore.get('modifiedBy');
				        $scope.location.pfTypeId = $scope.location.pfTypeId == 0 ? null: 	$scope.location.pfTypeId;
			             //alert(angular.toJson($scope.location));
			            $scope.getData('LocationController/saveLocationCharacteristics.json', angular.toJson($scope.location), 'saveLocation');
	       			}
	       		}
	       	};


	       $scope.updateBtnAction = function () {
	    	   $scope.readOnly = false;
	           $scope.datesReadOnly = false;
	           $scope.updateBtn = false;
	           $scope.saveBtn = true;
	          // $scope.resetBtn = false;
	           $scope.cancelBtn = true;
	           $scope.gridButtons = true;
	           $scope.transactionList = false;
	           $('.dropdown-toggle').removeClass('disabled');
	       };
	       $scope.cancelBtnAction = function(){
		    	$scope.readOnly = true;
		        $scope.datesReadOnly = true;
		        $scope.updateBtn = true;
		        $scope.saveBtn = false;
		        $scope.viewOrUpdateBtn = true;
		        $scope.correcttHistoryBtn = false;
		        $scope.resetBtn = false;
		        $scope.transactionList = false;
		        $scope.cancelBtn = false;
		        $scope.returnToSearchBtn = true;
		        
		        $scope.getData('LocationController/getLocationById.json', { locationDetailsId: $routeParams.id,customerId : ''}, 'locationList')
		    };
	       $scope.fun_checkErr = function() {
	          	 if(($('#pfRegistrationDate').val() != undefined || $('#pfRegistrationDate').val() != null) && ($('#pfStartDate').val() != undefined || $('#pfStartDate').val() != null) || ($scope.pfStartDate != undefined)){
	          		  var from = new Date(moment($('#pfRegistrationDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'));
	        		  var to = new Date(moment($('#pfStartDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') );
	        		       
	        		   	    if(from.getTime() > to.getTime()){
	        		      	    	$scope.Messager('error', 'Error', 'PF Registration Date should not be less than PF Start Date');
	        		      	    	$scope.location.pfStartDate="";
	        		   	    }
	          	}
	          	if(($('#esiRegistrationDate').val() != undefined || $('#esiRegistrationDate').val() != null) && ($('#esiStartDate').val() != undefined || $('#esiStartDate').val() != null) || ($scope.esiStartDate != undefined)){
	          		  var from = new Date(moment($('#esiRegistrationDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'));
	        		  var to = new Date(moment($('#esiStartDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'));
	        		       
	  		   	    if(from.getTime() > to.getTime()){
	  		      	    	$scope.Messager('error', 'Error', 'ESI Registration Date should not be less than ESI Start Date');
	  		      	    	$scope.location.esiStartDate="";
	  		   	    }
	          	}
	          	
	          }
	          /*
	           * if(($('#esiRegistrationDate').val() != undefined || $('#esiRegistrationDate').val() != null) && ($('#esiStartDate').val() != undefined || $('#esiStartDate').val() != null) || ($scope.toDate != undefined) 
	          				&& new Date(moment($('#esiRegistrationDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime() > new Date(moment($('#esiStartDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime() ){
	        		       
  		      	    	$scope.Messager('error', 'Error', 'ESI Registration Date should not be less than ESI Start Date');
  		      	    	$scope.toDate="";
	          	}
	           */

}]);





