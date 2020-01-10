'use strict';


companyControllers.controller("companyContactSearchCtrl",  ['$scope', '$rootScope', '$http', '$resource','$location','$cookieStore', function($scope,$rootScope,$http,$resource,$location,$cookieStore) {

    
    
var addressInformationTable;
var contactInformationTable;
var URL_GET_COMPANY_ADRESS_JSON_LIST = ROOTURL + "CompanyController/getCompanyContactAndAddressGridList.json";
    
/*
myservice.customerId = $cookieStore.get('CompanyCustomerId');       
myservice.companyId  = $cookieStore.get('CompanyCompanyId');*/
$scope.companyInfoId = $cookieStore.get('companyInfoId');


    if($cookieStore.get('companyInfoId') > 0){
    	
    }else{
    	$location.path('/companyDetails/create');
    }
    if($cookieStore.get('CompanyCompanyId') > 0){
    	$scope.editCompany = true;
        $scope.newCompany = false;
    }else{
    	$scope.editCompany = false;
        $scope.newCompany = true;
    }

    
    $http({
            url 	: URL_GET_COMPANY_ADRESS_JSON_LIST,
							method 	: "POST",
							data 	: {
										"customerId" : $cookieStore.get('CompanyCustomerId'), 
										"companyId"	 : $cookieStore.get('CompanyCompanyId')
							},
							headers : { "Content-Type" : "application/json" }
						}).then(
						function(response) {	
							if ( ! $.fn.DataTable.isDataTable( '#AddressInformation' ) ) {        		   
					      		   addressInformationTable = $('#AddressInformation').DataTable({  
					      			    "data": response.data.addressList,
					                     "columns": [					                      
					                        { "data": "addressType",
					                        	"render": function ( data, type, full, meta ) {                 		                    		 
					               		      return '<a href="#/companyAddress/'+full.addressId+'">'+data+'</a>';
					               		    }
					                     },
					                        { "data": "address1" },
					                        { "data": "address2" },
					                        { "data": "address3" },
					                        { "data": "city" },
					                        { "data": "state" },
					                        { "data": "country" },
					                        { "data": "pincode" }]
					               });  
					      		}
							
							if ( ! $.fn.DataTable.isDataTable( '#ContactInformation' ) ) {        		   
								contactInformationTable = $('#ContactInformation').DataTable({ 
									 "data": response.data.contactList,
					                     "columns": [					                      
					                        { "data": "contactTypeName",
					                        	"render": function ( data, type, full, meta ) {                 		                    		 
					               		      return '<a href="#/companyContact/'+full.contactId+'">'+data+'</a>';
					               		    }
					                     },
					                        { "data": "contactName" },
					                        { "data": "mobileNumber" },
					                        { "data": "emailId" },
					                        { "data": "address" }]
					               });  
					      		}
							
							//addressInformationTable.clear().rows.add(response.data.addressList).draw(); 
							//contactInformationTable.clear().rows.add(response.data.contactList).draw(); 
							
							//$scope.address_result  = response.data.addressList;
                           // $scope.contact_result  = response.data.contactList;
						},
						function(response) {
													
						});
    
    
    }]);