'use strict';


companyWorkOrder.controller("workorderCtrl",  ['$scope', '$rootScope', '$http', '$routeParams', '$resource','$location','$cookieStore','$filter','$window',function($scope, $rootScope, $http, $routeParams,$resource,$location,$cookieStore,$filter,$window) {  
    
	
	$.material.init();
	/*$('#transactionDate,#fromDate,#toDate,#workPeriodTo,#workPeriodFrom').bootstrapMaterialDatePicker({ 
    	time : false,
        Button : true,
        format : "DD/MM/YYYY",
        clearButton: true
    }); */		
	
	$('#transactionDate,#fromDate,#toDate,#workPeriodTo,#workPeriodFrom').datepicker({
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
	    
	    
	$scope.list_status = [{ id:"Y" , name:"Active"},{ id:"N" , name:"In Active"}];
	$scope.list_workSkill = [{"id":"Skilled","name" : "Skilled" },
		                       {"id":"Semi Skilled","name" : "Semi Skilled" },
		                       {"id":"High Skilled","name" : "High Skilled" },
		                       {"id":"Special Skilled","name" : "Special Skilled" },
		                       {"id":"UnSkilled","name" : "UnSkilled" }];
	$scope.list_responsibilityTakenBy = [{ id:"Company" , name:"Company"} , {id:"Vendor" , name:"Vendor"} , {id:"Both" , name:"Both"} ];
	
    $scope.workOderDetails = new Object();
    $scope.updateBtn = true;
    $scope.saveBtn = false;
	$scope.readOnly =true;		
	
	$scope.workOderDetails.createdBy = $cookieStore.get('createdBy');	
	$scope.workOderDetails.modifiedBy= $cookieStore.get('modifiedBy');	
	$scope.transactionDate = $filter('date')(new Date(),"dd/MM/yyyy");
	$scope.workOderDetails.isActive = 'Y';
	
	if($routeParams.id > 0){
		$scope.readonlyEdit = true;
		$scope.readonly = true;
		$scope.saveBtn = false;
		$scope.updateBtn = true;
		$scope.correctHistoryBtn = false;
		$scope.resetBtn = false;
		$scope.viewHistoryBtn = true;
		$scope.cancelBtn = false;
		$scope.returnTOSearchBtn = true;
		$scope.transactionList = false;
	}else{
		$scope.readonlyEdit = false;
		$scope.readonly = false;
		$scope.saveBtn = true;
		$scope.updateBtn = false;
		$scope.correctHistoryBtn = false;
		$scope.resetBtn = true;
		$scope.viewHistoryBtn = false;
		$scope.cancelBtn = false;
		$scope.returnTOSearchBtn = true;
		$scope.transactionList = false;
	}
	
	$scope.fun_updateActionBtn = function(){
		$scope.readonlyEdit = true;
		$scope.readonly = false;
		$scope.saveBtn = true;
		$scope.updateBtn = false;
		$scope.correctHistoryBtn = false;
		$scope.resetBtn = false;
		$scope.viewHistoryBtn = false;
		$scope.cancelBtn = true;
		$scope.returnTOSearchBtn = true;
		$scope.transactionList = false;
		$scope.transactionDate = $filter('date')(new Date(),"dd/MM/yyyy");
	}
	
	$scope.fun_viewHistoryBtn = function(){
		$scope.readonlyEdit = true;
		$scope.readonly = false;
		$scope.saveBtn = false;
		$scope.updateBtn = false;
		$scope.correctHistoryBtn = true;
		$scope.resetBtn = false;
		$scope.viewHistoryBtn = false;
		$scope.cancelBtn = false;
		$scope.returnTOSearchBtn = true;
		$scope.transactionList = true;	
		$scope.getPostData('CompanyController/getTransactionDatesOfWorkOrderHistory.json', {  customerId : $scope.workOderDetails.customerDetailsId , companyId : $scope.workOderDetails.companyDetailsId,  workerOrderId :   $scope.workOderDetails.workrorderDetailId }, 'transactionDatesChnage');
	    $('.dropdown-toggle').removeClass('disabled');
		
	}
	
	$scope.fun_CancelBtn = function(){
		$scope.readonlyEdit = true;
		$scope.readonly = true;
		$scope.saveBtn = false;
		$scope.updateBtn = true;
		$scope.correctHistoryBtn = false;
		$scope.resetBtn = false;
		$scope.viewHistoryBtn = true;
		$scope.cancelBtn = false;
		$scope.returnTOSearchBtn = true;
		$scope.transactionList = false;		
		$scope.getPostData("CompanyController/getMasterInfoForcompanyWorkOrder.json", { workOrderId : $routeParams.id == undefined ? '0' : $routeParams.id }, "gridData" );
	}

	 // FOR COMMON POST METHOD
	  $scope.getPostData = function (url, data, type, buttonDisable) {
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
	    	} else if( type == 'gridData' ){	      	
	      		$scope.customerList = response.data.customerList;
	      		
        		  if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
 		                $scope.workOderDetails.customerDetailsId=response.data.customerList[0].customerId;		                
 		                $scope.fun_CustomerChangeListener();
 		                }
            	if(Object.prototype.toString.call(response.data.WorkorderDetails) === '[object Array]' &&  response.data.WorkorderDetails.length > 0 ){            		
            		$scope.companyList = response.data.companyList;
            		$scope.workOderDetails = response.data.WorkorderDetails[0];
            		$scope.workorderResponsibilitiesVo = [];
            		$scope.workorderResponsibilitiesVo = response.data.WorkorderDetails[0].workorderResponsibilitiesVo;
        			$scope.workorderManpowerDetailsVo = [];	
        			//alert('home start '+angular.toJson($scope.workorderManpowerDetailsVo));
        			angular.forEach(response.data.WorkorderDetails[0].workorderManpowerDetailsVo, function(manPower) {
        				$scope.workorderManpowerDetailsVo.push({	    	
        		    		'workSkillId':manPower.workSkillId ,
        		    		'jobCode' : manPower.jobCode,
        		    		'jobCodeId':manPower.jobCodeId ,		    		
        		    		'headCount':manPower.headCount ,
        		    		'toDate': $filter('date')(manPower.toDate,"dd/MM/yyyy") ,	    		
        		    		'fromDate':$filter('date')(manPower.fromDate,"dd/MM/yyyy"),
        		    		'duration':manPower.duration 	    		
        		    	});
        			 });
        			
		      		$scope.list_locations = response.data.locationList; 
		      		$scope.list_departments = response.data.departmentList;	
		      		$scope.countriesList = response.data.countriesList;	
		      		$scope.workOderDetails.country = response.data.countriesList[0].id;			      		
            		$scope.transactionModel = response.data.WorkorderDetails[0].workOrderInfoId;
            		$scope.transactionDate = $filter('date')(response.data.WorkorderDetails[0].transactionDate,"dd/MM/yyyy");
            		$scope.workPeriodFrom = $filter('date')(response.data.WorkorderDetails[0].workPeriodFrom,"dd/MM/yyyy");
            		$scope.workPeriodTo = $filter('date')(response.data.WorkorderDetails[0].workPeriodTo,"dd/MM/yyyy");
            		$scope.list_activeJobCodes = response.data.activeJobCodes;
            		$scope.locationChange();
            		$scope.plantChange();
            	}
            	
            	// for button views
	      		if($scope.buttonArray == undefined || $scope.buttonArray == '')
	      		$scope.getPostData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Company Work Order'}, 'buttonsAction');
	      	}else if(type == 'companyDropDown'){
	      		$scope.companyList = response.data;
	      		 if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	   	                $scope.workOderDetails.companyDetailsId = response.data[0].id;
	   	             $scope.fun_CompanyChangeListener();
	   	                }
	      	}else if (type == 'companyChange') {
				   //alert(JSON.stringify(response.data));
	        	   $scope.countriesList = response.data;
	        	   if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	   	                $scope.workOderDetails.country = response.data[0].id;
	   	                $scope.countryChange();
	   	           }
	           }else if(type == 'countryChange'){
	        	   $scope.list_locations = response.data;
	        	   if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
	   	                $scope.workOderDetails.location = response.data[0].locationId;
	   	                $scope.locationChange();
	   	           }
	           }else if(type == "locationChange"){
	        	   //alert(JSON.stringify(response.data));
	        		$scope.plantList = response.data.plantList;
	        		if( response.data.plantList[0] != undefined && response.data.plantList[0] != "" && response.data.plantList.length == 1 ){
	   	                $scope.workOderDetails.plantId = response.data.plantList[0].plantId;
	   	                $scope.plantChange();
	   	           	}
	           }else if (type == 'departmentList') {       
	        	 //  alert(angular.toJson(response.data));
	         	   $scope.list_departments = response.data.departmentList;	            	
		     }else if(type == 'locationsDropDown'){
	      	
	      		$scope.list_activeJobCodes = response.data.activeJobCodes;
	      		
	      	}else if(type == 'save'){
	      		if(response.data == 0){
	      			 $scope.Messager('error', 'Error', 'Work Order Code Already Exists...',true);
	      			 return;
	      		}
	      		
	      		if($scope.correctHistoryBtn == true){
	      			$scope.Messager('success', 'success', 'Company Work Order Saved Successfully',buttonDisable);
	      		}else{
	      			$scope.Messager('success', 'success', 'Company Work Order Updated Successfully',buttonDisable);
	      			$location.path('/companyWorkOrder/'+response.data);
	      		}
	      	}else if( type == 'transactionDatesChnage'){
	      		$scope.transactionDatesList = response.data;
	      	}else if(type == 'jobCodesList'){            	
            	$scope.list_activeJobCodes = response.data.jobCodes;
            	
            } 	
	      },
	        function () {
	        	 $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
	        	//alert('Error')         	
	        })
	  };
	  
	  
   // for getting Master data for Grid List 
   $scope.getPostData("CompanyController/getMasterInfoForcompanyWorkOrder.json", { workOrderId : $routeParams.id == undefined ? '0' : $routeParams.id,customerId: $cookieStore.get('customerId') }, "gridData" );
	  	     
   // Customer Change Listener to get company details
   $scope.fun_CustomerChangeListener = function(){	 	   
	   if($scope.workOderDetails.customerDetailsId != undefined && $scope.workOderDetails.customerDetailsId != '')
	   $scope.getPostData("vendorController/getCompanyNamesAsDropDown.json",  { customerId : $scope.workOderDetails.customerDetailsId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.workOderDetails.companyDetailsId != undefined ? $scope.workOderDetails.companyDetailsId : 0 } , "companyDropDown");	   
   };
   
 
   
   
   $scope.fun_CompanyChangeListener = function () {	   		  
	   if($scope.workOderDetails.companyDetailsId != undefined && $scope.workOderDetails.companyDetailsId != '')
  		$scope.getPostData('vendorController/getcountryListByCompanyId.json',{customerId : $scope.workOderDetails.customerDetailsId , companyId : $scope.workOderDetails.companyDetailsId  } , 'companyChange');
  		
	};
	
	$scope.countryChange = function(){
		if($scope.workOderDetails.country != undefined || $scope.workOderDetails.country != null){
			$scope.getPostData('LocationController/getLocationsListBySearch.json',{customerId: ($scope.workOderDetails.customerDetailsId == undefined || $scope.workOderDetails.customerDetailsId == null )? '0' : $scope.workOderDetails.customerDetailsId, companyId : ($scope.workOderDetails.companyDetailsId == undefined || $scope.workOderDetails.companyDetailsId == null) ? '0' : $scope.workOderDetails.companyDetailsId, countryId : ($scope.workOderDetails.country == undefined || $scope.workOderDetails.country == null) ? '0' : $scope.workOderDetails.country } , 'countryChange');
		}
		
	}
	
	$scope.locationChange= function(){
		//alert($scope.work.locationId);
		$scope.getPostData('PlantController/getPlantListBySearch.json', { customerId : ($scope.workOderDetails.customerDetailsId == undefined ||$scope.workOderDetails.customerDetailsId == null )? '0' : $scope.workOderDetails.customerDetailsId  , companyId : ($scope.workOderDetails.companyDetailsId == undefined || $scope.workOderDetails.companyDetailsId == null) ? '0' : $scope.workOderDetails.companyDetailsId  , locationId: $scope.workOderDetails.location == undefined ? '' :  $scope.workOderDetails.location}, 'locationChange');

	}
	
	$scope.plantChange= function(){
		if($scope.workOderDetails.location != undefined && $scope.workOderDetails.location != null && $scope.workOderDetails.location != '' && $scope.workOderDetails.plantId != undefined && $scope.workOderDetails.plantId != null && $scope.workOderDetails.plantId != ''){
			//alert();
      		$scope.getPostData('associatingDepartmentToLocationPlantController/getDepartMentDetailsByLocationAndPlantId.json', {customerId:$scope.workOderDetails.customerDetailsId,companyId:$scope.workOderDetails.companyDetailsId,locationId:$scope.workOderDetails.location,plantId:$scope.workOderDetails.plantId}, 'departmentList');
      	}
		
	};
   
   
   
   
   // save Work Order 
	$scope.fun_save_workOrder = function($event){	
		
		if($('#CompanyWokOrder').valid()){
			$scope.workOderDetails.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');	
			$scope.workOderDetails.workPeriodFrom = moment($('#workPeriodFrom').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
			$scope.workOderDetails.workPeriodTo = moment($('#workPeriodTo').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
			
			
			
		        if(!moment($scope.workOderDetails.workPeriodTo).isSameOrAfter($scope.workOderDetails.workPeriodFrom)){
		        	$scope.Messager('error', 'Error', 'Work Period From Date should not be Greater than Work Period To Date');
		        	return;
		        }
		       
			
			$scope.workOderDetails.createdBy = $cookieStore.get('createdBy');	
			$scope.workOderDetails.modifiedBy= $cookieStore.get('modifiedBy');	
			if($scope.correctHistoryBtn == true){
				$scope.workOderDetails.workOrderInfoId = $scope.transactionModel;
			}else{
				$scope.workOderDetails.workOrderInfoId = 0;
			}
			$scope.workOderDetails.workorderResponsibilitiesVo = $scope.workorderResponsibilitiesVo;
			$scope.workOderDetails.workorderManpowerDetailsVo = [];			
			angular.forEach($scope.workorderManpowerDetailsVo, function(manPower) {
				$scope.workOderDetails.workorderManpowerDetailsVo.push({	    	
		    		'workSkillId':manPower.workSkillId ,
		    		'jobCodeId':manPower.jobCodeId ,		    		
		    		'headCount':manPower.headCount ,
		    		'toDate': moment(manPower.toDate,'DD/MM/YYYY').format('YYYY-MM-DD')	,	    		
		    		'fromDate':moment(manPower.fromDate,'DD/MM/YYYY').format('YYYY-MM-DD')  ,
		    		'duration':manPower.duration 	    		
		    	});
			 });
			
			// alert(" "+angular.toJson($scope.workOderDetails));
			 $scope.getPostData("CompanyController/saveCompanyWorkOrder.json", angular.toJson($scope.workOderDetails)  , "save",angular.element($event.currentTarget));
		}			
	}

	$scope.fun_transactionDatesListChnage = function(){
		if($scope.transactionModel != undefined && $scope.transactionModel != '')
			$scope.getPostData("CompanyController/getMasterInfoForcompanyWorkOrder.json", { workOrderId : $scope.transactionModel }, "gridData" );
	}
		
	
	
	  // company work Order Manpower START
	
    $scope.delete_manpowerRecord = function($event){  
    	//alert($($event.target).parent().parent().index());
    	 if($window.confirm('Are you sure you want to delete this record ?')){
	    	$scope.workorderManpowerDetailsVo.splice($($event.target).parent().parent().index(),1);	  
	    	/*$scope.workOderDetails.totalHeadCount = 0;
	    	angular.forEach($scope.workorderManpowerDetailsVo, function(value, key){	    		
			      $scope.workOderDetails.totalHeadCount = (($scope.workOderDetails.totalHeadCount*1) + (value.headCount*1));		        		        		        
			   });*/
    	 }
    }
    
   
    $scope.edit_manpowerRecord = function($event){  		 
    	$scope.manpower_index = $($event.target).parent().parent().index();    	    	
    	var data = $scope.workorderManpowerDetailsVo[$scope.manpower_index];
    	//alert(angular.toJson(data));
    	$scope.workSkillId = data.workSkillId;
    	$scope.jobCodeId = data.jobCodeId;
    	$scope.jobCode = data.jobCode;
		$scope.headCount= data.headCount;
		$scope.toDate= data.toDate;
		$scope.fromDate= data.fromDate;
		$scope.duration= data.duration;		
    	$scope.popUpSave = false;
    	$scope.popUpUpdate =true;  
    	$('#myModal').modal('show');     	
    }
    
   
    $scope.save_manpowerRecord =function($event){  
    	if(Object.prototype.toString.call($scope.workorderManpowerDetailsVo) !== '[object Array]' ||  $scope.workorderManpowerDetailsVo.length == 0 ){
    		$scope.workorderManpowerDetailsVo = [];
    	}
    	if($('#CompanyManPowerPopup').valid()){  
    		if($scope.dateCheck($('#workPeriodFrom').val(),$('#workPeriodTo').val(),$('#fromDate').val()) && $scope.dateCheck($('#workPeriodFrom').val(),$('#workPeriodTo').val(),$('#toDate').val())){
     	       
    		} else{
    			$scope.Messager('error', 'Error', 'From Date And To Date should be within WorkPeriod Dates',true);
    			return;
    		}
    		
    		if($scope.headCount <= 0){
    			$scope.Messager('error', 'Error', 'Head Count must be greater than 0',true);
    			return;
    		}
    		var count = 0;
	    	var status = true;
	    	//var status1 = true;
	    	if($scope.workorderManpowerDetailsVo == "" || $scope.workorderManpowerDetailsVo == null || $scope.workorderManpowerDetailsVo == undefined){
	    		if($scope.headCount > $scope.workOderDetails.totalHeadCount){
					status = false;
					$scope.Messager('error', 'Error', 'Sum of Head Counts should not be greater than Total Head Count.');
					return;
	    		}
	    	}else{
		    	angular.forEach($scope.workorderManpowerDetailsVo , function(value,key){
		    		if($scope.headCount != null && $scope.headCount != undefined && $scope.headCount != ""){
		    			count = count + parseInt(value.headCount);
		    		}
		    		
		    		if((count+ parseInt($scope.headCount)) > parseInt($scope.workOderDetails.totalHeadCount)){
						status = false;
					}
		    	});
	    	}
	    	var  startDate , endDate  ;
        	startDate  = moment( $('#fromDate').val(), 'DD/MM/YYYY'); 
        	endDate  = moment( $('#toDate').val(), 'DD/MM/YYYY');
        	
	    	if(!status){
				 $scope.Messager('error', 'Error', 'Sum of Head Counts should not be greater than Total Head Count.');
			}else if(endDate.isBefore(startDate)){
        		$scope.Messager('error', 'Error', 'From Date Should be Greater Than To Date ',true);
        	}else{
    		
		    	$scope.workorderManpowerDetailsVo.push({	    	
		    		'workSkillId':$scope.workSkillId ,
		    		'jobCodeId':$scope.jobCodeId ,
		    		'jobCode': $('#jobCodeId option:selected').html(),
		    		'headCount':$scope.headCount ,
		    		'toDate': $('#toDate').val(), 	    		
		    		'fromDate':$('#fromDate').val(),
		    		'duration':$scope.duration 	    		
		    	});
		    	$scope.clearPopupFields();	    	    	
		    	$('#myModal').modal('hide');  	
		    	//$scope.workOderDetails.totalHeadCount = 0;
		    	/*angular.forEach($scope.workorderManpowerDetailsVo, function(value, key){	    		
				      $scope.workOderDetails.totalHeadCount = (($scope.workOderDetails.totalHeadCount*1) + (value.headCount*1));		        		        		        
				});*/
        	}
	    	
	    }    	
    }
        

    $scope.dateCheck  = function (from,to,check) {  
    	//alert(from+"::"+$.trim(to)+"::"+check);
    	var  startDate , endDate , date ;
    	startDate  = moment(from, 'DD/MM/YYYY'); 
    	endDate  = moment(to, 'DD/MM/YYYY');	
    	date  = moment(check, 'DD/MM/YYYY');
    	
	 	if ((date.isBefore(endDate) 
	 			 && date.isAfter(startDate)) 
	 			 || (date.isSame(startDate) || date.isSame(endDate))
	 			) { 
	 		return true;
	 	}else{
	 		return false;
	 	}
	 	
       /* var fDate,lDate,cDate;
        fDate = Date.parse(from);
        lDate = Date.parse(to);
        cDate = Date.parse(check);
        alert(fDate+" :: "+lDate+" :: "+cDate);
        if((cDate <= lDate && cDate >= fDate)) {        	
            return true;
            
        }      
        return false;*/
    }
    
    
    $("body").on("change", "#fromDate,#toDate", function(event) {    	
    	if($('#fromDate').val() != '' || $('#toDate').val() != ''){
    		var  start, end;
    		start = moment($('#fromDate').val(), 'DD/MM/YYYY'); 
    	 	end = moment($('#toDate').val(), 'DD/MM/YYYY');	 
    	 	$('#duration').val(end.diff(start, 'days')+1);
    	 	$scope.duration = end.diff(start, 'days')+1;	
    	}
    });
    
    
    $scope.update_manpowerRecord =function(){	
    	
    	if($('#CompanyWokOrderPopup').valid()){ 
    		
    		if($scope.dateCheck($('#workPeriodFrom').val(),$('#workPeriodTo').val(),$('#fromDate').val()) && $scope.dateCheck($('#workPeriodFrom').val(),$('#workPeriodTo').val(),$('#toDate').val())){
    	       
    		}else{
    			$scope.Messager('error', 'Error', 'From Date And To Date should be within WorkPeriod Dates',true);
    			return;
    		}
    		
    		if($scope.headCount <= 0){
    			$scope.Messager('error', 'Error', 'Head Count must be greater than 0',true);
    			return;
    		}
    		
    		var count = 0;
	    	var status = true;
	    	
	    	if($scope.workorderManpowerDetailsVo == "" || $scope.workorderManpowerDetailsVo == null || $scope.workorderManpowerDetailsVo == undefined){
	    		if($scope.headCount > $scope.workOderDetails.totalHeadCount){
					status = false;
					 $scope.Messager('error', 'Error', 'Sum of Head Counts should not be greater than Total Head Count.');
					 return;
				}
	    	}else{
		    	angular.forEach($scope.workorderManpowerDetailsVo , function(value,key){
		    		if($scope.headCount != null && $scope.headCount != undefined && $scope.headCount != ""){
		    			count = count + parseInt(value.headCount);
		    		}
		    		if(count+ parseInt($scope.headCount) > parseInt($scope.workOderDetails.totalHeadCount)){
						status = false;
						/*$scope.Messager('error', 'Error', 'Sum of Head Counts should not be greater than Total Head Count.');
						return;*/
					}
		    	});
	    	}
	    	
	    	var  startDate , endDate  ;
        	startDate  = moment( $('#fromDate').val(), 'DD/MM/YYYY'); 
        	endDate  = moment( $('#toDate').val(), 'DD/MM/YYYY');
    		    		
        	
        	
	    	if(!status){
	    		 $scope.Messager('error', 'Error', 'Sum of Head Counts should not be greater than Total Head Count.');
				
	    	}else if(endDate.isBefore(startDate)){
        		$scope.Messager('error', 'Error', 'From Date Should be Greater Than To Date ',true);
        		return;
        	}else{
	    		$scope.workorderManpowerDetailsVo.splice($scope.manpower_index,1);		    	
		    	$scope.workorderManpowerDetailsVo.push({	    	
		    		'workSkillId':$scope.workSkillId ,
		    		'jobCodeId':$scope.jobCodeId ,
		    		'jobCode': $('#jobCodeId option:selected').html(),
		    		'headCount':$scope.headCount ,
		    		'toDate': $('#toDate').val(), 	    		
		    		'fromDate':$('#fromDate').val(),
		    		'duration':$scope.duration 	      		
		    	});	    	
		    		/*$scope.workOderDetails.totalHeadCount = 0;
		    	angular.forEach($scope.workorderManpowerDetailsVo, function(value, key){	    		
				      $scope.workOderDetails.totalHeadCount =	(($scope.workOderDetails.totalHeadCount*1) + (value.headCount*1));		        
				   });*/
		    	$scope.clearPopupFields();
		    	$('#myModal').modal('hide');  
	    	}
    	}	    		   
    }
    
    $scope.add_manpowerRecord = function(){
    	//alert($('#workPeriodTo').val() +" :: "+ $('#workPeriodTo').val())
    	if($scope.workOderDetails.totalHeadCount > 0){
	    	if($('#workPeriodTo').val() == '' || $('#workPeriodTo').val() == ''){
	    		 $scope.Messager('error', 'Error', 'Please select Work Period From Date and Work Period To Date',true);
	    		 return;
	    	}
	    	if($scope.workOderDetails.companyDetailsId == undefined || $scope.workOderDetails.companyDetailsId == ''){
	    		$scope.Messager('error', 'Error', 'Please select Company',true);
	    		return;   		
	    	}
	    	$('#myModal').modal('show');  
	    	$scope.clearPopupFields();
	    	$('#fromDate').val($('#workPeriodFrom').val());
	    	$('#toDate').val($('#workPeriodTo').val());
	    	if($('#fromDate').val() != '' || $('#toDate').val() != ''){
	    		var  start, end;
	    		start = moment($('#fromDate').val(), 'DD/MM/YYYY'); 
	    	 	end = moment($('#toDate').val(), 'DD/MM/YYYY');	 
	    	 	$('#duration').val(end.diff(start, 'days')+1);
	    	 	$scope.duration = end.diff(start, 'days')+1;	
	    	}
			$scope.popUpSave = true;
			$scope.popUpUpdate = false;
    	}else{
    		$scope.Messager('error', 'Error', 'Please enter Total Head Count. Total Head Count must be greater than 0.',true);
    	}
    }
    
    $scope.clearPopupFields = function(){
    	$scope.workSkillId = "";
    	$scope.headCount = "";
		$('#toDate').val("");
		$scope.jobCodeId= "";
		$('#fromDate').val("");
		$scope.duration= "";
    }
    
    // company work Order Manpower   END 
    
    
// company responsibilities START
	
    $scope.delete_responsibilitiesRecord = function($event){  
    	//alert($($event.target).parent().parent().index());
    	if($window.confirm('Are you sure you want to delete this record ?')){
    		$scope.workorderResponsibilitiesVo.splice($($event.target).parent().parent().index(),1);	
    	}
    }
    
   
    $scope.edit_responsibilitiesRecord = function($event){  		 
    	$scope.manpower_index = $($event.target).parent().parent().index();    	    	
    	var data = $scope.workorderResponsibilitiesVo[$scope.manpower_index]; 
    	//alert(angular.toJson(data));
    	$scope.characteristics = data.characteristics;
    	$scope.responsibilityTakenBy = data.responsibilityTakenBy;
		$scope.companyShare= data.companyShare;
		$scope.vendorShare= data.vendorShare;
		$scope.additionalInfo= data.additionalInfo;	
    	$scope.popUpSave1 = false;
    	$scope.popUpUpdate1 =true;  
    	$('#myModal1').modal('show');  
    }
    
   
    $scope.save_responsibilitiesRecord =function($event){  
    	if(Object.prototype.toString.call($scope.workorderResponsibilitiesVo) !== '[object Array]' ||  $scope.workorderResponsibilitiesVo.length == 0 ){
    		$scope.workorderResponsibilitiesVo = [];
    	}
    	
    	if($('#CompanyresponsibilitiesPopup').valid()){     
    		if(parseInt($scope.companyShare)+parseInt($scope.vendorShare) == 100){
		    	$scope.workorderResponsibilitiesVo.push({	    	
		    		'characteristics' : $scope.characteristics,
		        	'responsibilityTakenBy' : $scope.responsibilityTakenBy ,
		    		'companyShare' : $scope.companyShare,
		    		'vendorShare' : $scope.vendorShare,
		    		'additionalInfo' : $scope.additionalInfo     		
		    	});
		    	$scope.clearResponsiblePopupFields();
		    	$('#myModal1').modal('hide');  	 
    		}else{
    			$scope.Messager('error', 'Error', 'Total company and vendor share must be equal to 100 ',true);
    		}
	    }    	
    }
    
    $scope.update_responsibilitiesRecord =function(){	
    	
    	if($('#CompanyresponsibilitiesPopup').valid()){ 
    		if(parseInt($scope.companyShare)+parseInt($scope.vendorShare == 100)){
	    		$scope.workorderResponsibilitiesVo.splice($scope.manpower_index,1);		    	
		    	$scope.workorderResponsibilitiesVo.push({
		    		'characteristics' : $scope.characteristics,
		        	'responsibilityTakenBy' : $scope.responsibilityTakenBy ,
		    		'companyShare' : $scope.companyShare,
		    		'vendorShare' : $scope.vendorShare,
		    		'additionalInfo' : $scope.additionalInfo    			      		
		    	});
		    	$scope.clearResponsiblePopupFields();
		    	$('#myModal1').modal('hide');
	    	}else{
				$scope.Messager('error', 'Error', 'Total company and vendor share must be equal to 100 ',true);
			}
    	}	    		   
    }
    
    $scope.add_responsibilitiesRecord = function(){
    	$scope.clearResponsiblePopupFields();
		$scope.popUpSave1 = true;
		$scope.popUpUpdate1 = false;
    }
    
    $scope.clearResponsiblePopupFields = function(){
    	$scope.characteristics = "";
    	$scope.responsibilityTakenBy = "";
		$scope.companyShare= "";
		$scope.vendorShare= "";
		$scope.additionalInfo= "";		
    }
    
    // company responsibilities   END 
    
    $scope.fun_findShare = function(){
    	if($scope.responsibilityTakenBy != null && $scope.responsibilityTakenBy != undefined && $scope.responsibilityTakenBy != ""){
    		if($scope.responsibilityTakenBy == "Company"){
    			$scope.vendorShare = 0;
    			$scope.companyShare = 100;
    			$scope.onlyRead = true;
    		}else if($scope.responsibilityTakenBy == "Vendor"){
    			$scope.vendorShare = 100;
    			$scope.companyShare = 0;
    			$scope.onlyRead = true;
    		}else if($scope.responsibilityTakenBy == "Both"){
    			$scope.vendorShare = null;
    			$scope.companyShare = null;
    			$scope.onlyRead = false;
    		}
    	}
    	
    };
    
    
    $scope.workSkillChange = function(){    	
   	 $scope.getPostData('jobAllocationByVendorController/getJobCodeListBySkillType.json', {customerId:$scope.workOderDetails.customerDetailsId,companyId:$scope.workOderDetails.companyDetailsId,skillType:$scope.workSkillId}, 'jobCodesList');
   }
    
    
}]);

