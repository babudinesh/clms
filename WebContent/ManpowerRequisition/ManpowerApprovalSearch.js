

var ManpowerSearchTable;
ManpowerRequestController.controller("ManpowerApprovalSearchCtrl",  ['$scope', '$rootScope', '$http', '$resource','$location','myservice','$cookieStore', '$filter', function($scope,$rootScope,$http,$resource,$location,myservice,$cookieStore, $filter) {
    
   $scope.ManpowerSearchTableView = false;
   $scope.list_status = [{"id":"New","name":"New"},
	                      {"id":"Saved","name":"Saved"},
	                      {"id":"Pending For Approval","name":"Pending For Approval"},
	                      {"id":"Approved","name":"Approved"},
	                      {"id":"Rejected","name":"Rejected"}];
   
   
   /*if($cookieStore.get("roleNamesArray").includes('MANAGER')){
	   $scope.showAdd = true;
   }else{
	   $scope.showAdd = false;
   }*/
   
	  
	 // FOR COMMON POST METHOD
	  $scope.getData = function (url, data, type) {
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
	          }else if( type == 'search' ){	
		      		$scope.ManpowerSearchTableView = true;
		      		ManpowerSearchTable.clear().rows.add(response.data.requisitionsList).draw(); 
		      }else if (type == 'customerList'){ 
	    			$scope.customersList = response.data.customerList;
	    			
	    			if ( ! $.fn.DataTable.isDataTable( '#ManpowerSearchTable' ) ) {        		   
		      			ManpowerSearchTable = $('#ManpowerSearchTable').DataTable({        
		      		                     "columns": [
		      		                        { "data": "requestCode" ,
		      		                        	"render": function ( data, type, full, meta ) {                 		                    		 
			               		                      return '<a href="#/manpowerRequestApproval/'+full.manpowerRequisitionId+'">'+data+'</a>';
			               		                  }
		      		                        },
		      		                        { "data": "employeeName" },
		      		                        { "data": "locationName" },
		      		                        { "data": "plantName" },
		      		                        { "data": "departmentName" },
		      		                        { "data": "status" }]
		      		               });  
		      		      		}
		      		
			      		$scope.customerList = response.data.customerList;
			      		 if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
		   		                $scope.customerName=response.data.customerList[0].customerId;	
		   		                $scope.dropdownDisableCustomer = true;
		   		                $scope.customerChange();
		   		         }
			      	
	    			
	    			/*if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
		                $scope.customerName=response.data.customerList[0].customerId;	
		                $scope.dropdownDisableCustomer = true;
		                $scope.customerChange();
		            }*/
	    			// for button views
	  			  	if($scope.buttonArray == undefined || $scope.buttonArray == '')
	  	                $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Manpower Requisition Request'}, 'buttonsAction');
	        }else if(type == 'customerChange'){
	      		$scope.companyList = response.data;
	      		if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	      			$scope.companyName = response.data[0].id;
	      			$scope.companyChange();
	      		}
	      	}else if(type == 'companyChange'){
	      		$scope.locationsList = response.data.locationsList	; 
	      	}else if(type == 'locationChange'){
            	$scope.plantsList = response.data;
            }else if(type == 'plantChange'){
            	$scope.departmentsList = response.data.departmentList;
            }      	
	      })
	  };
	  
	  
   // for getting Master data for Grid List 
   $scope.getData("vendorController/getCustomerNamesAsDropDown.json",{ customerId: $cookieStore.get('customerId')}, "customerList" );
	  	     
   $scope.customerChange = function () {
  		$scope.companyName = '';       
  		$scope.getData('vendorController/getCompanyNamesAsDropDown.json', {customerId:$scope.customerName,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.companyName != undefined && $scope.companyName != '' ? $scope.companyName : 0}, 'customerChange');
   };
  
 	
   $scope.companyChange = function() {    	   
       $scope.getData('jobAllocationByVendorController/getDepartmentsAndLocationesList.json', { customerId: ($scope.customerName == undefined || $scope.customerName == null )? '0' : $scope.customerName,companyId: ($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.companyName != undefined ? $scope.companyName : 0  }, 'companyChange');
   };
   
   
   
   $scope.locationChange = function(){
	   	if($scope.locationName != undefined && $scope.locationName != null && $scope.locationName != ''){
	   		$scope.departmentsList = [];
	   		$scope.getData('jobAllocationByVendorController/getPlantsList.json', {locationId:$scope.locationName}, 'locationChange');
	   	}else{
	   		$scope.plantsList =[];
	   		$scope.departmentsList = [];
	   	}
   }
   
   $scope.plantChange = function(){
	   	if($scope.locationName != undefined && $scope.locationName != null && $scope.locationName != '' && $scope.plantName != undefined && $scope.plantName != null && $scope.plantName != ''){
	   		$scope.getData('associatingDepartmentToLocationPlantController/getDepartMentDetailsByLocationAndPlantId.json', {customerId:$cookieStore.get('customerId'), companyId: $cookieStore.get('companyId'), locationId:$scope.locationName,plantId:$scope.plantName}, 'plantChange');
	   	}else{
	   		$scope.departmentsList = [];
	   	}
   }
   
   // for Search button 
   $scope.search = function(){   
  	 $scope.getData("manpowerRequisitionController/searchManpowerRequisitions.json",{customerId : $scope.customerName == undefined ? '0' : $scope.customerName, 
  			 																  companyId : $scope.companyName == undefined ? '0' : $scope.companyName  , 
  			 																  locationId : ($scope.locationName == undefined  || $scope.locationName == null || $scope.locationName == "" ) ? '0' : $scope.locationName, 
		 																	  departmentId : ($scope.departmentName == undefined || $scope.departmentName == null || $scope.departmentName == "" )? '0' : $scope.departmentName,  
				 															  plantId : ($scope.plantName == undefined || $scope.plantName == null || $scope.plantName == "" )? '0' : $scope.plantName,  
		 																	  requestCode : ($scope.requestCode == undefined || $scope.requestCode == null || $scope.requestCode == "") ? '' : $scope.requestCode ,
		 																	  status : $scope.status == undefined ? '' : $scope.status ,
  			 																  } , "search" );    	   
  }
   
   $scope.fun_clearSearch = function(){
	 $scope.locationName = "";
	 $scope.departmentName = "";
	 $scope.plantName = "";
	 $scope.requestCode = null;
	 $scope.status = "";
		 
   }
   
}]);
 
