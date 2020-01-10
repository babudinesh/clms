'use strict';

workerDetailsControllers.controller('educationAndEmploymentDetailsController', ['$scope','$http', '$resource','$routeParams','$filter','$cookieStore', function ($scope,$http, $resource, $routeParams,$filter,$cookieStore) {
	 	$.material.init();
		
		$('#yearOfPassing').bootstrapMaterialDatePicker({
	        time : false,
	        Button : true,
	        format : "DD/MM/YYYY",
	        clearButton: true
	    });
		
	   
	    $scope.popUpSave = true;
    	$scope.popUpUpdate =false;
    	
    	$scope.worker = new Object();
	   $scope.education = new Object();
	   $scope.educationList = [];
	   $scope.worker.educationList = [];
	   
	   $scope.worker.workerCode   = $cookieStore.get('workerCode');
	   $scope.worker.workerName   = $cookieStore.get('workerName');
	   $scope.worker.countryName  = $cookieStore.get('countryName');
	   $scope.worker.customerName = $cookieStore.get('customerName');
	   $scope.worker.companyName  = $cookieStore.get('companyName');
	   $scope.worker.vendorName   = $cookieStore.get('vendorName');
	   $scope.worker.vendorId  = $cookieStore.get('vendorId');
	   $scope.worker.workerId = $cookieStore.get('workerId');
	   $scope.worker.customerId = $cookieStore.get('customerId');
	   $scope.worker.companyId = $cookieStore.get('companyId');   
	   $scope.worker.createdBy = $cookieStore.get('createdBy'); 
  	   $scope.worker.modifiedBy = $cookieStore.get('modifiedBy');
  	   
  	   
	    
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
	        	
	        	 if(type == 'saveWorker'){	            	
	            	 $scope.Messager('success', 'success', 'Worker Nominee Details Saved Successfully',buttonDisable);
	            	// $scope.getData('workerController/getNomineeGridData.json', {customerId:$scope.workerNomine.customerId,companyId:$scope.workerNomine.companyId,workerId:$scope.workerNomine.workerId}, 'getNomineeGridta');
	            	
	            }else if(type == 'Delete'){	            	
	            	if(response.data == 0){
	            		 $scope.Messager('success', 'success', 'Record Deleted Successfully',buttonDisable);
	            	}else{
	            		$scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
	            	}	            	
	            }else if(type == 'getNomineeGridta'){	            
	            	$scope.nomineeList = response.data.NomineeGridData;	            	
	            }
	        	},
	        function () {
	        	  $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
	        	           
	          });
	    	}      
	    
	    
	    $scope.workSkillList = [
	                            	{"id":"Highly Skilled","name" : "Highly Skilled" },
									{"id":"Highly Skilled","name" : "Highly Skilled" },
									{"id":"Highly Skilled","name" : "Highly Skilled" },
									{"id":"Highly Skilled","name" : "Highly Skilled" },
									{"id":"Highly Skilled","name" : "Highly Skilled" }
									];
	    
	    
	    $scope.educationLevelList = [{"id":"Primary School","name" : "Primary School" },
	                        {"id":"Middle School","name" : "Middle School" },
	                        {"id":"10th STD","name" : "10th STD" },
	                        {"id":"10+2","name" : "10+2" },
	                        {"id":"Polytechnic","name" : "Polytechnic" },
	                        {"id":"Intermediate","name" : "Intermediate" },
	                        {"id":"Pre-University","name" : "Pre-University" },
	                        {"id":"Diploma","name" : "Diploma" },
	                        {"id":"Under Graduate Diploma","name" : "Under Graduate Diploma" },
	                        {"id":"Graduation","name" : "Graduation" },
	                        {"id":"Graduate Diploma","name" : "Graduate Diploma" },
	                        {"id":"Vocational Course","name" : "Vocational Course" },
	                        {"id":"Professional Course","name" : "Professional Course" },
	                        {"id":"Post Graduation","name" : "Post Graduation" },
	                        {"id":"Post Graduate Diploma","name" : "Post Graduate Diploma" },
	                        {"id":"M.Phil","name" : "M.Phil" }  
	                       ];
	    
	    $scope.modeofEducationList = [
										{"id":"Regular Full Time","name" : "Regular Full Time" },
										{"id":"Regular Part Time","name" : "Regular Part Time" },
										{"id":"Distance Full Time","name" : "Distance Full Time" },
										{"id":"Distance Part Time","name" : "Distance Part Time" }										                               
	                                  ];
	    
	    
	    
	    /*if($scope.workerNomine.customerId != "" && $scope.workerNomine.companyId != "" && $scope.workerNomine.workerId){
	   
	    $scope.getData('workerController/getNomineeGridData.json', {customerId:$scope.workerNomine.customerId,companyId:$scope.workerNomine.companyId,workerId:$scope.workerNomine.workerId}, 'getNomineeGridta'); 
	    }else{
	    	
	    	 $scope.Messager('error', 'Error', 'Enter Worker Details');
	    }*/
	    
	    
	 
	    
	    
     
	    $scope.saveEducationDetails = function($event){
	    	if($('#EducationForm').valid()){
	    	$scope.worker.educationList.push({	    		   		
	    		'educationLevel':$scope.education.educationLevel,
	    		'degreeName':$scope.education.degreeName,
	    		'yearOfPassing':$('#yearOfPassing').val() != undefined && $('#yearOfPassing').val() != "" ? moment($('#yearOfPassing').val(),'DD/MM/YYYY').format('YYYY-MM-DD') :"",
				'modeOfEducation':$scope.education.modeOfEducation,
				'institutionName':$scope.education.institutionName,
				'percentage':$scope.education.percentage,
				'workerEducationId':$scope.education.workerEducationId 	   		
	    	}); 
	    	
	    	 $('div[id^="Educaion"]').modal('hide');
	    	 $scope.clearEducationPopupValues();
	    	}
	    	
	       }
	    
	    
	    
	    
	    $scope.EditEducation = function($event){	
	    	$scope.trIndex = $($event.target).parent().parent().index();
	    	$scope.education = $scope.worker.educationList[$($event.target).parent().parent().index()];	    
	    	$scope.yearOfPassing = $filter('date')($scope.worker.educationList[$($event.target).parent().parent().index()].yearOfPassing, 'dd/MM/yyyy');
	    	$scope.popUpSave = false;
	    	$scope.popUpUpdate =true;
	    	
	    }
	    
	    
	    $scope.updateEducationDetails= function($event){
	    	if($('#EducationForm').valid()){	
	    		
	    		$scope.worker.educationList.splice($scope.trIndex ,1);    		
		    	$scope.worker.educationList.push({	    		   		
		    		'educationLevel':$scope.education.educationLevel,
		    		'degreeName':$scope.education.degreeName,
		    		'yearOfPassing':$('#yearOfPassing').val() != undefined && $('#yearOfPassing').val() != "" ? moment($('#yearOfPassing').val(),'DD/MM/YYYY').format('YYYY-MM-DD') :"",
					'modeOfEducation':$scope.education.modeOfEducation,
					'institutionName':$scope.education.institutionName,
					'percentage':$scope.education.percentage,
					'workerEducationId':$scope.education.workerEducationId 	   		
		    	}); 
		    	
		    	 $('div[id^="Educaion"]').modal('hide');
		    	 $scope.clearEducationPopupValues();
		    	}
	    	
	    	 
	    	  	
	    }
	    
	    
	    
	    $scope.DeleteEducation = function($event){	    	   	
	    	
	    	$scope.worker.educationList.splice($($event.target).parent().parent().index(),1);
	    	
	    	
	    }
	    
	    
	    $scope.clearEducationPopupValues =function(){
	    //	alert();
	    	$scope.education.educationLevel="";
    		$scope.education.degreeName="";
    		$('#yearOfPassing').val("");
			$scope.education.modeOfEducation="";
			$scope.education.institutionName="";
			$scope.education.percentage="";
    		$scope.popUpSave = true;
	    	$scope.popUpUpdate =false; 	
	    	
	    }
	    
	    
	    
	    $scope.plusIconAction = function(){
	    	$scope.popUpSave = true;
	    	$scope.popUpUpdate =false;
	    //	$scope.clearEducationPopupValues();
	    	
	    }
	    
	  
	    $scope.SaveWorker = function(){
	    	//alert(angular.toJson($scope.worker));
	    	
	    	$scope.getData('workerController/saveEducationAndEmploymentDetails.json',angular.toJson($scope.worker), 'saveWorker');
	    	
	    }
	    
	    
	    
	    
	 /*  Worker EDucation End */
	    
	    
	    
	    /*  Worker Certification  Start */
	    
	    
	    $scope.validityList = [
                            	{"id":"Permanent","name" : "Permanent" },
								{"id":"Temporary","name" : "Temporary" },
								{"id":"Renewable","name" : "Renewable" },
								{"id":"Unknown","name" : "Unknown" }
								];
	    

	    $scope.saveCertificationDetails = function($event){
	    	if($('#CertificateForm').valid()){
	    	$scope.worker.certificationList.push({	    		   		
	    		'certificateName':$scope.certification.certificateName,
	    		'issuingAuthority':$scope.certification.issuingAuthority,
	    		'validFrom':$('#validFrom').val() != undefined && $('#validFrom').val() != "" ? moment($('#validFrom').val(),'DD/MM/YYYY').format('YYYY-MM-DD') :"",
	    		'validTo':$('#validTo').val() != undefined && $('#validTo').val() != "" ? moment($('#validTo').val(),'DD/MM/YYYY').format('YYYY-MM-DD') :"",
				'validiy':$scope.certification.validiy,
				   		
	    	}); 
	    	
	    	 $('div[id^="Certificate"]').modal('hide');
	    	 $scope.clearCertificationPopupValues();
	    	}
	    	
	       }
	    
	    
	    
	    
	    $scope.EditCertification = function($event){	
	    	$scope.trCertificationIndex = $($event.target).parent().parent().index();
	    	$scope.certification = $scope.worker.certificationList[$($event.target).parent().parent().index()];	    
	    	$scope.validFrom = $filter('date')($scope.worker.certificationList[$($event.target).parent().parent().index()].validFrom, 'dd/MM/yyyy');
	    	$scope.validTo = $filter('date')($scope.worker.certificationList[$($event.target).parent().parent().index()].validTo, 'dd/MM/yyyy');
	    	$scope.popUpSave = false;
	    	$scope.popUpUpdate =true;
	    	
	    }
	    
	    
	    $scope.updateCertificationDetails= function($event){
	    	if($('#CertificateForm').valid()){	
	    		
	    		$scope.worker.certificationList.splice($scope.trCertificationIndex ,1);    		
	    		$scope.worker.certificationList.push({	    		   		
		    		'certificateName':$scope.certification.certificateName,
		    		'issuingAuthority':$scope.certification.issuingAuthority,
		    		'validFrom':$('#validFrom').val() != undefined && $('#validFrom').val() != "" ? moment($('#validFrom').val(),'DD/MM/YYYY').format('YYYY-MM-DD') :"",
		    		'validTo':$('#validTo').val() != undefined && $('#validTo').val() != "" ? moment($('#validTo').val(),'DD/MM/YYYY').format('YYYY-MM-DD') :"",
					'validiy':$scope.certification.validiy,
					   		
		    	}); 
		    	
		    	 $('div[id^="Certificate"]').modal('hide');
		    	 $scope.clearCertificationPopupValues();
		    	}
	    	 	
	    }
	    
	    
	    
	    $scope.DeleteCertification = function($event){    	
	    	$scope.worker.certificationList.splice($($event.target).parent().parent().index(),1);    	
	    }
	    
	    
	    $scope.clearCertificationPopupValues =function(){
	    	 		   		
	    		$scope.certification.certificateName="";
	    		$scope.certification.issuingAuthority="";
	    		$('#validFrom').val("");
	    		$('#validTo').val("") ;
				$scope.certification.validiy="";
	
    		$scope.popUpSave = true;
	    	$scope.popUpUpdate =false; 	
	    	
	    }
	    
	    
	    
	    
	    
	    
	 
	    /*  Worker Certification End */
	
	    
     
	    /* Worker Employment Details Start */
	    
	    
	    $scope.saveEmploymentDetails = function($event){
	    	if($('#EmploymentForm').valid()){
	    	$scope.worker.employmentList.push({	    		   		
	    		'organizationName':$scope.employment.organizationName,
	    		'contactNumber':$scope.employment.contactNumber,
	    		'startDate':$('#startDate').val() != undefined && $('#startDate').val() != "" ? moment($('#startDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') :"",
	    		'endDate':$('#endDate').val() != undefined && $('#endDate').val() != "" ? moment($('#endDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') :"",
				'designation':$scope.employment.designation,
				'current':$scope.employment.current			   		
	    	}); 
	    	
	    	 $('div[id^="Employment"]').modal('hide');
	    	 $scope.clearEmploymentPopupValues();
	    	}
	    	
	       }
	    
	    
	    
	    
	    $scope.EditEmployment = function($event){	
	    	$scope.trCertificationIndex = $($event.target).parent().parent().index();
	    	$scope.certification = $scope.worker.employmentList[$($event.target).parent().parent().index()];	    
	    	$scope.validFrom = $filter('date')($scope.worker.employmentList[$($event.target).parent().parent().index()].validFrom, 'dd/MM/yyyy');
	    	$scope.validTo = $filter('date')($scope.worker.employmentList[$($event.target).parent().parent().index()].validTo, 'dd/MM/yyyy');
	    	$scope.popUpSave = false;
	    	$scope.popUpUpdate =true;
	    	
	    }
	    
	    
	    $scope.updateEmploymentDetails= function($event){
	    	if($('#EmploymentForm').valid()){	
	    		
	    		$scope.worker.certificationList.splice($scope.trCertificationIndex ,1);
	    		
	    		$scope.worker.EmploymentList.push({	    		   		
		    		'organizationName':$scope.employment.organizationName,
		    		'contactNumber':$scope.employment.contactNumber,
		    		'startDate':$('#startDate').val() != undefined && $('#startDate').val() != "" ? moment($('#startDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') :"",
		    		'endDate':$('#endDate').val() != undefined && $('#endDate').val() != "" ? moment($('#endDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD') :"",
					'designation':$scope.employment.designation,
					'current':$scope.employment.current			   		
		    	}); 
		    	
		    	 $('div[id^="Employment"]').modal('hide');
		    	 $scope.clearEmploymentPopupValues();
		    	}
	    	 	
	    }
	    
	    
	    
	    $scope.DeleteEmployment = function($event){    	
	    	$scope.worker.certificationList.splice($($event.target).parent().parent().index(),1);    	
	    }
	    
	    
	    $scope.clearEmploymentPopupValues =function(){
	    	 		   		
	    		$scope.certification.certificateName="";
	    		$scope.certification.issuingAuthority="";
	    		$('#validFrom').val("");
	    		$('#validTo').val("") ;
				$scope.certification.validiy="";
	
    		$scope.popUpSave = true;
	    	$scope.popUpUpdate =false; 	
	    	
	    }
	    
	    
	    
	    
	    
	    
	    /* Worker Employment Details End*/
	    
	    
     
}]);


