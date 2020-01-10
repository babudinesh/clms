'use strict';


customerControlller.controller("customerContactSearchCtrl",  ['$scope', '$rootScope', '$http', '$resource','$location','$cookieStore','myservice', function($scope,$rootScope,$http,$resource,$location,$cookieStore,myservice) {

 
var URL_GET_COMPANY_ADRESS_JSON_LIST = ROOTURL + "CustomerController/getCustomerAddressGridList.json";
    
    //console.log($cookieStore.get('customerId')+"customerContactSearchCtrl:: "+myservice.customerId);
    
    myservice.customerId = $cookieStore.get('customerId');
    var addressInformationTable;
    var contactInformationTable;
    //alert($cookieStore.get('customerinfoId'));
    
    $scope.customerinfoId = $cookieStore.get('customerinfoId');
    
    $http({
            url 	: URL_GET_COMPANY_ADRESS_JSON_LIST,
							method 	: "POST",
							data 	: {
										"customerId" : $cookieStore.get('CustomerCustomerId')
							},
							headers : { "Content-Type" : "application/json" }
						}).then(
						function(response) {		
							// alert(response.data);
							//$scope.address_result  = response.data.CustomerAddressList;
                           //  $scope.contact_result  = response.data.customerContactList;
                            
                            if ( ! $.fn.DataTable.isDataTable( '#AddressInformation' ) ) {        		   
					      		   addressInformationTable = $('#AddressInformation').DataTable({  
					      			    "data": response.data.CustomerAddressList,
					                     "columns": [					                      
					                        { "data": "addressType",
					                        	"render": function ( data, type, full, meta ) {                 		                    		 
					               		      return '<a href="#/customerAddress/'+full.addressId+'">'+data+'</a>';
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
					      		}else{
					      			addressInformationTable.clear().rows.add(response.data.CustomerAddressList).draw(); 
					      		}
							
							if ( ! $.fn.DataTable.isDataTable( '#ContactInformation' ) ) {        		   
								contactInformationTable = $('#ContactInformation').DataTable({ 
									 "data": response.data.customerContactList,
					                     "columns": [					                      
					                        { "data": "contactTypeName",
					                        	"render": function ( data, type, full, meta ) {                 		                    		 
					               		      return '<a href="#/customerContact/'+full.contactId+'">'+data+'</a>';
					               		    }
					                     },
					                        { "data": "contactName" },
					                        { "data": "mobileNumber" },
					                        { "data": "emailId" },
					                        { "data": "address" }]
					               });  
					      		}else{
					      			contactInformationTable.clear().rows.add(response.data.customerContactList).draw(); 
					      		}
							
						},
						function(response) {
													
						});
    
    
    }]);