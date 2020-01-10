'use strict';


LocationController.directive('myAttribute', function () {	
    return {
    	 scope: {myValue: "&myAttribute"},
    	  link: function (scope, iElm, iAttrs) {
    	    var x = scope.myValue();
    	    // x == {name:"Umur", id:1}
    	  //  alert(angular.toJson(x));
    	  }
    };
});


LocationController.controller("LocationAddCtrl", ['$scope', '$http', '$resource','$location','$filter', '$routeParams','myservice','$cookieStore', function ($scope, $http, $resource, $location, $filter, $routeParams, myservice, $cookieStore) {
	 $.material.init();
	 $scope.timeRegex= /^([0[0-9]|1[0-9]|2[0-4]):[0-5][0-9]$/i;
	 $scope.Week_Days = [
	                     {"id":"1","name" : "Monday" },
	                     {"id":"2","name" : "Tuesday" },
	                     {"id":"3","name" : "Wednesday" },
	                     {"id":"4","name" : "Thursday" },
	                     {"id":"5","name" : "Friday" },
	                     {"id":"6","name" : "Saturday" },
	                     {"id":"7","name" : "Sunday" }
	                     ];
	 $scope.days = /^[0-7]$/i;
	  
	 $('#transactionDate').datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown'
	    	  
	 });
	  
	 $('#businessEndTime,#businessStartTime').bootstrapMaterialDatePicker({ 
	        format : 'HH:mm',
	        date: false, 
	        clearButton: true,
	        shortTime: false
	 }); 
	    
    /* $('#businessHours').bootstrapMaterialDatePicker({ 
        format : 'HH', 
        date: false, 
        clearButton: true,
        shortTime: true 
     });*/
	  
     /*$('#transactionDate').bootstrapMaterialDatePicker({
	     time: false,
	     clearButton: true,
	     format : "DD/MM/YYYY"
     });*/

     $scope.location = new Object();
     $scope.labelName = " Add ";
     $scope.lCode = new Object();
     $scope.valid = new Object();
     
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
 
     if ($routeParams.id > 0) {
    	 $scope.readOnly = true;
    	 $scope.datesReadOnly = true;
    	 $scope.updateBtn = true;
    	 $scope.saveBtn = false;
    	 $scope.viewOrUpdateBtn = true;
       	 $scope.correcttHistoryBtn = false;
       	 $scope.resetBtn = false;
       	 $scope.gridButtons = true;
       	 $scope.transactionList = false;
       	 $scope.cancelBtn = false;
       	 $scope.avilableListCheckBoxDisable = true;
     }else {
    	 $('#transactionDate').val($filter('date')(new Date(),'dd/MM/yyyy'));
    	 $scope.readOnly = false;
    	 $scope.datesReadOnly = false;
    	 $scope.updateBtn = false;
    	 $scope.saveBtn = true;
    	 $scope.viewOrUpdateBtn = false;
    	 $scope.correcttHistoryBtn = false;
    	 $scope.resetBtn = true;
    	 $scope.transactionList = false;
    	 $scope.gridButtons = true;
    	 $scope.cancelBtn = false;
    	 myservice.locationDetailsId=0;
    	 $scope.avilableListCheckBoxDisable = false;
     }

     $scope.updatelocationBtnAction = function (this_obj) {
    	  $scope.lCode = 0;
    	  $scope.readOnly = false;
    	  $scope.onlyRead = true;
          $scope.datesReadOnly = false;
          $scope.updateBtn = false;
          $scope.saveBtn = true;
          $scope.viewOrUpdateBtn = false;
          $scope.correctHistoryBtn = false;
          $scope.resetBtn = false;
          $scope.cancelBtn = true;
          $scope.gridButtons = true;
          $scope.transactionList = false;
          $scope.location.locationDetailsId = 0;
          $('#transactionDate').val($filter('date')(new Date(),'dd/MM/yyyy'));
          $('.dropdown-toggle').removeClass('disabled');
      };
       
       
      $scope.viewOrEditHistory = function () {
           $scope.readOnly = false;
           $scope.onlyread = true;
           $scope.datesReadOnly = false;
           $scope.updateBtn = false;
           $scope.saveBtn = false;
           $scope.viewOrUpdateBtn = false;
           $scope.correcttHistoryBtn = true;
           $scope.resetBtn = false;      
           $scope.gridButtons = true;
           $scope.transactionList = true;
           $scope.getData('LocationController/getTransactionDatesList.json', { companyId: $scope.location.companyId, customerId: $scope.location.customerId ,locationId: $scope.location.locationId }, 'transactionDatesChange');       
           
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
	        $scope.getData('LocationController/getLocationById.json', { locationDetailsId: ($routeParams.id != undefined  || $routeParams.id != null) ? $routeParams.id :'0' , customerId: $cookieStore.get('customerId') }, 'locationList');
	    };
       
       $scope.getData = function (url, data, type, buttonDisable) {
    	   var req = {
               method: 'POST',
               url: ROOTURL + url,
               headers: {
                   'Content-Type': 'application/json' },
               data: data
           }
           $http(req).then(function (response) {
        	 
        	   $('.saveDept').show();
        	   $('.updateDept').hide();
        	   if(type == 'buttonsAction'){
        			$scope.buttonArray = response.data.screenButtonNamesArray;
        		} else if (type == 'locationList') {
   				
            	   $scope.locationList = response.data;
                   $scope.location = response.data.locationVo[0];
                   
                   if($scope.location != undefined){
                	   myservice.customerId = $scope.location.customerId;
               	       myservice.companyId = $scope.location.companyId;
               	       myservice.countryId = $scope.location.countryId;
               	       myservice.locationId = $scope.location.locationId;
               	       myservice.locationDetailsId = $scope.location.locationDetailsId;
               	       $cookieStore.put('locationDetailsId',response.data.locationVo[0].locationDetailsId );
               	       $scope.location.locationId = response.data.locationVo[0].locationId;  
               	       $('#transactionDate').val($filter('date')(response.data.locationVo[0].transactionDate,'dd/MM/yyyy'));
               	       $scope.location.transactionDate = $filter('date')(response.data.locationVo[0].transactionDate,'dd/MM/yyyy');
               	       $scope.transDate1 =  $filter('date')(response.data.locationVo[0].transactionDate,'dd/MM/yyyy'); 
               	     //  alert(angular.toJson(response.data.statesList));
               	       
               	       if(response.data.locationVo[0].address1 != undefined && response.data.locationVo[0].address1 != null && response.data.locationVo[0].address1 != ""
               	    	   				&& response.data.locationVo[0].countryId != undefined && response.data.locationVo[0].countryId != null && response.data.locationVo[0].countryId != ""){
               	    	   $scope.labelName = " View/Edit ";
               	       }
               	       
               	       $scope.locationList.statesList = response.data.statesList;
               	       $scope.locationList.deptList = response.data.departmentList;
               	       $scope.locationList.countryList = response.data.countryList;
               	       $scope.locationList.departmentList =   response.data.locDeptList;
               	       $scope.avilableDepatmentList = $scope.locationList.departmentList;               	   
                   }else{
                	   $scope.location = new Object();
                	   $scope.location.transactionDate = $filter('date')(new Date(),"dd/MM/yyyy");
                	   $scope.location.status = "Y";
                	   $scope.locationList.departmentList =[];
                	   if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){
                		//   alert(JSON.stringify(response.data.customerList[0].customerId));
                		   $scope.location.customerId=response.data.customerList[0].customerId;		                
                		   $scope.customerChange();
   		                }
                   }
                // for button views
   			  	if($scope.buttonArray == undefined || $scope.buttonArray == '')
  	              $scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Location'}, 'buttonsAction');
  	               
               }else if (type == 'customerChange') {
                   $scope.locationList.companyList = response.data;
                   if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
                	   $scope.location.companyId = response.data[0].id;
                	   $scope.companyChange();
   	               }
               }else if (type == 'companyChange') {
            	 //alert(JSON.stringify(response.data.Profile[0]));
            	   $scope.list = response.data.Profile[0];
                   $scope.locationList.deptList = response.data.departmentList;
                   $scope.locationList.countryList = response.data.countryList;
                   if( response.data.countryList != undefined && response.data.countryList != "" && response.data.countryList.length == 1 ){
      	                $scope.location.countryId = response.data.countryList[0].id;
      	                $scope.countryChange();
      	           }
                   if($routeParams.id == 0){
	                   $scope.location.workWeekStartDay  = $scope.list.workWeekStartDay;
	                   $scope.location.workWeekEndDay = $scope.list.workWeekEndDay;
	                   $scope.location.businessHoursPerDay = $scope.list.businessHoursPerDay;
	                   $scope.location.businessStartTime = $scope.list.businessStartTime;
	                   $scope.location.businessEndTime = $scope.list.businessEndTime;	                   
	                   $scope.location.standardHoursPerWeek = $scope.list.standardHoursPerWeek;
	                   $scope.location.numberOfWorkingDays = $scope.list.numberOfWorkingDays;
                   }
                   
               }else if (type == 'departmentChange') {
            	 //  alert(JSON.stringify(response.data.deptVo[0]));
            	   /*$scope.locationList.departmentlst = response.data.deptVo[0];
            	   var flag= true;
            	   
            	   angular.forEach($scope.locationList.departmentList, function(value, key){
            		   if(value.departmentName == $scope.locationList.departmentlst.departmentName){
            			   flag= false;
            		   }
            	   });
            	   
            	   if(flag){
            		   $scope.locationList.departmentList.push({
   	              		'departmentCode':$scope.locationList.departmentlst.departmentCode,  		
   	              		'departmentName':$scope.locationList.departmentlst.departmentName ,
   	              		'departmentInfoId':$scope.locationList.departmentlst.departmentInfoId ,
   	              		'departmentId':$scope.locationList.departmentlst.departmentId 
                 	   }); 
            	   }*/
              	            
               }else if (type == 'countryChange') {
				  // alert(JSON.stringify(response.data));
                   $scope.locationList.statesList = response.data;
               }else if(type == 'validateCode'){
           			$scope.lCode = response.data;
           			if($scope.lCode > 0){
           				//$scope.Messager('error', 'Error', 'Location Code already exists',buttonDisable);
           			}
               }else if(type == 'validHeadquarter'){
            	   $scope.valid = response.data;
            	   if($scope.valid > 0){
          				$scope.Messager('error', 'Error', 'Location Headquarter is already selected.Please uncheck the checkbox.',buttonDisable);
            	   }
               }else if (type == 'saveLocation') {
            	   //alert(JSON.stringify(response.data));
            	   if(response.data.id > 0){
		            	$scope.Messager('success', 'success', 'Location Saved Successfully',buttonDisable);
		            	/*myservice.customerId = $cookieStore.put('customerId', $scope.location.customerId);
		                myservice.companyId =  $cookieStore.put('companyId', $scope.location.companyId);
		                myservice.countryId = $cookieStore.put('countryId', $scope.location.countryId);
		                myservice.locationCode = $cookieStore.put('locationCode', $scope.location.locationCode);*/
		            	 myservice.customerId = $scope.location.customerId;
	               	     myservice.companyId = $scope.location.companyId;
	               	     myservice.countryId = $scope.location.countryId;
	               	     myservice.locationId = $scope.location.locationId;
		            	 myservice.locationDetailsId = response.data.id;
		            	 myservice.locationId = response.data.name;
		            	//alert(response.data.name);
		            	//myservice.locationDetailsId = $cookieStore.put('locationDetailsId', response.data.id);
		            	//$scope.locationId =  $cookieStore.put('locationId', response.data.name);
		            	 $location.path('/LocationAdd/'+response.data.id);
            	   }else if(response.data.id < 0){
            		   $scope.Messager('error', 'Error', response.data.name,buttonDisable);
	               }else{
	            	   $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
	               }
               
               }else if (type == 'transactionDatesChange') {
	            	var k = response.data.length;
	            	if(response.data.length > 1){
		            	for( var i = response.data.length-1; i> 0;i--){
			            	 if($scope.dateDiffer(response.data[i-1].name.split('-')[0])){
			            		 k = response.data[i-1].id;
			            		 break;
			            	 }
		            	}
	            	}else{
	            		k = response.data[0].id;
	            	}
	            	$scope.transactionModel=k;
	            	$scope.transactionDatesList = response.data;
	     	        $scope.getData('LocationController/getLocationById.json', { locationDetailsId : ($scope.transactionModel != undefined || $scope.transactionModel != null) ? $scope.transactionModel:'0' , customerId: "" }, 'locationList')

	            }
           },
           function () { 
        	   $scope.Messager('error', 'Error', 'Technical Issue Please try after some time',buttonDisable);
           });
       }
       
       $scope.dateDiffer = function(date1){
    	   var cmpDate = new Date( moment(date1,'DD/MM/YYYY').format('YYYY-MM-DD HH:MM:SS'));
       	   if(cmpDate.getTime() <= (new Date()).getTime()){
          			return true;
       	   }else{
       		   return false;       	
       	   }
       };
       

       $scope.fun_checkLocationCode = function(){
    	   if($scope.location.locationId > 0){
	  	    	if($scope.location.customerId != null || $scope.location.customerId != undefined || $scope.location.customerId != ''
	  	    			&& ($scope.location.companyId != null || $scope.location.companyId != undefined || $scope.location.companyId != '')
	  	    			&& ($scope.location.countryId != null || $scope.location.countryId != undefined || $scope.location.countryId != '')
	  	    				    			&& ($scope.location.locationCode != null || $scope.location.locationCode != undefined || $scope.location.locationCode != '')){
	  	    		$scope.getData('LocationController/validateLocationCode.json',{ customerId : $scope.location.customerId, companyId: $scope.location.companyId, locationCode:$scope.location.locationCode, countryId:$scope.location.countryId,locationId:0, isHeadquarter:false },'validateCode')
	  	    	}
    	   }else{
    		   $scope.lCode = 0;
    	   }
  	   };
  	   
  	   //To check isHeadquarter exists or not
  	   $scope.validateHeadquarter = function(){
  		   if($scope.location.isHeadquarter == true){
	  		   $scope.loc = new Object();
	  		   $scope.loc.customerId = $scope.location.customerId;
	  		   $scope.loc.companyId = $scope.location.companyId;
	  		   $scope.loc.countryId = $scope.location.countryId;
	  		   $scope.loc.isHeadquarter = $scope.location.isHeadquarter;
	  		   //$scope.loc.locationCode = $scpe.location.locationCode;
	  		   
	  		   if($scope.location.locationId > 0){
	  			   $scope.loc.locationId = $scope.location.locationId;
	  		   };
	  	   
	  		   if($scope.location.customerId != null || $scope.location.customerId != undefined || $scope.location.customerId != ''
	   			&& ($scope.location.companyId != null || $scope.location.companyId != undefined || $scope.location.companyId != '')
	   				    			&& ($scope.location.countryId != null || $scope.location.countryId != undefined || $scope.location.countryId != '')
	   				    			&& ($scope.location.isHeadquarter != null || $scope.location.isHeadquarter != undefined || $scope.location.isHeadquarter != '')){
	  			   $scope.getData('LocationController/validateLocationCode.json',angular.toJson($scope.loc)  ,'validHeadquarter');
	  		   }
  		   }
  	   };  	    
       $scope.getData('LocationController/getLocationById.json', { locationDetailsId: ($routeParams.id != undefined  || $routeParams.id != null) ? $routeParams.id :'0' , customerId: $cookieStore.get('customerId') }, 'locationList');
       
       $scope.customerChange = function () {
            $scope.getData('vendorController/getCompanyNamesAsDropDown.json', { customerId: $scope.location.customerId,companyId:$cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "" ? $cookieStore.get('companyId') :$scope.location.companyId }, 'customerChange');
       };
       	
	   $scope.companyChange = function() {
		    $scope.getData('LocationController/getCmpProfileDept.json', { customerId: $scope.location.customerId != undefined ? $scope.location.customerId : '0', companyId: $scope.location.companyId != undefined ? $scope.location.companyId :'0' }, 'companyChange');
	   };
	  	
	   $scope.countryChange = function () {
		    $scope.getData('CommonController/statesListByCountryId.json', { countryId: $scope.location.countryId }, 'countryChange');
	   };
	   	
		
    	
       $scope.save = function () {
    	   ///var re = new RegExp("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$");
    	   //$scope.fun_FindTime();
    	   if($('#locationForm').valid()){
    		   if(($scope.location.address1 == '' || $scope.location.address1 == undefined || $scope.location.address1 == null)
        			   || $scope.location.countryId == '' || $scope.location.countryId == undefined || $scope.location.countryId == null){
        		   $scope.Messager('error', 'Error', 'Please enter address');
        	   }/*else if(($scope.location.businessStartTime != null && $scope.location.businessStartTime != undefined  && $scope.location.businessStartTime != '') && !(re.test($scope.location.businessStartTime)) ){    		  
    			  // $scope.location.businessStartTime="";
    			   $scope.Messager('error', 'Error', 'Business Hours/Day should be less than or equal to 24 Hours');
        		   
        	   }else if($scope.location.businessEndTime != null && $scope.location.businessEndTime != undefined  && $scope.location.businessEndTime != '' && !(re.test($scope.location.businessEndTime)) ){
    			  // $scope.location.businessEndTime="";
    			   $scope.Messager('error', 'Error', 'Time Format should be HH:MM and should be less than 24 Hours');
        		   
        	   }*/else if($scope.location.businessHoursPerDay != null && $scope.location.businessHoursPerDay != undefined  && $scope.location.businessHoursPerDay != '' && parseInt($scope.location.businessHoursPerDay) > 24  ){
    			   //$scope.location.businessHoursperDay="";
    			   $scope.Messager('error', 'Error', 'Business Hours/Day should be less than or equal to 24 Hours');
        	   }else if($scope.location.standardHoursPerWeek != null && $scope.location.standardHoursPerWeek != undefined  && $scope.location.standardHoursPerWeek != "" &&  parseInt($scope.location.standardHoursPerWeek) > 168){
    			 //  $scope.location.standardHoursPerWeek="";
    			   $scope.Messager('error', 'Error', 'Standard Hours/Week should be less than or equal to 168 Hours');
	    	   }else if($scope.transDate1 != '' && $scope.transDate1 != undefined && new Date(moment($scope.transDate1,'DD/MM/YYYY').format('YYYY-MM-DD')) .getTime() > new Date(moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime()){
        		   $scope.Messager('error', 'Error', 'Transaction Date Should not be less than previous transaction date');
            	   $scope.location.transactionDate =  $scope.transDate1;
        	   }else if($scope.lCode > 0){
       				$scope.Messager('error', 'Error', 'Location Code already exists'); 
        	   }else if($scope.lCode == -1){
       				$scope.Messager('error', 'Error', 'Technical problem. Please try again later...'); 
        	   }else if($scope.valid > 0){
     				$scope.Messager('error', 'Error', 'Location Headquarter is already selected.Please uncheck the checkbox.',buttonDisable);
        	   }/*else if($scope.location.businessHoursPerDay != null && $scope.location.businessHoursPerDay != undefined  && $scope.location.businessHoursPerDay != '' && $scope.location.businessHoursPerDay+".00" != $scope.businessHours && $scope.location.businessHoursPerDay+".00" != $scope.businessHours1 ){
        		   $scope.Messager('error', 'Error', 'Business Start Time and Business End Time total duration should not be greater than Business Hours/Day.');
        	   }else if($scope.location.standarHoursPerWeek != null && $scope.location.standarHoursPerWeek != undefined  && $scope.location.standarHoursPerWeek != '' && $scope.location.standarHoursPerWeek != parseInt($scope.location.businessHoursPerDay)*parseInt($scope.location.numberOfWorkingDays)){
         		   $scope.Messager('error', 'Error', 'Standard Hours/Week should be the multiple of Business Hours/Day and Number of Working Days.');
   	       	   }*//*else if(($scope.location.workWeekStartDay != undefined && $scope.location.workWeekStartDay != null && $scope.location.workWeekStartDay != ""  && $scope.location.workWeekStartDay != 0  && $scope.location.workWeekStartDay != "null") 
			   				&& ($scope.location.workWeekEndDay != undefined && $scope.location.workWeekEndDay != null && $scope.location.workWeekEndDay != "" && $scope.location.workWeekEndDay != 0  && $scope.location.workWeekEndDay != "null")
			   				&& ($scope.location.workWeekStartDay ==  $scope.location.workWeekEndDay)){
		   			$scope.Messager('error', 'Error', 'Week Start day and week end day should not be same');
	   			}*/else{
	    		   $scope.location.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
	    		   //$scope.location.businessHours = $('#startTime').val();
	    		   $scope.location.businessStartTime = $('#businessStartTime').val();
	    		   $scope.location.businesEndTime = $('#businesEndTime').val();
	    		   //alert($('#workWeekStartId option:selected').html());
	    		   if($scope.location.workWeekStartDay  != "")
	    			   $scope.location.workWeekStartDay  = $('#workWeekStartId option:selected').html();
	    		   if($scope.location.workWeekEndDay != "")
	    			   $scope.location.workWeekEndDay =$('#workWeekEndId option:selected').html();
	    		   
	    		   $scope.location.departmentList =$scope.locationList.departmentList;
	    		   $scope.location.locationDetailsId =0;
	    		   $scope.location.createdBy = $cookieStore.get('createdBy');
			       $scope.location.modifiedBy = $cookieStore.get('modifiedBy');
	    		   //alert(angular.toJson($scope.location));
	    		   $scope.getData('LocationController/saveLocation.json', angular.toJson($scope.location), 'saveLocation');
    	   		}
    	   }
       };
       
       $scope.correctHistorySave= function(){
    	   //$scope.fun_FindTime();
    	   //var re = new RegExp("^([0[0-9]|1[0-9]|2[0-4]):[0-5][0-9]$");
    	   if($('#locationForm').valid()){
	    	   if(($scope.location.address1 == '' || $scope.location.address1 == undefined || $scope.location.address1 == null)
	    			   || $scope.location.countryId == '' || $scope.location.countryId == undefined || $scope.location.countryId == null){
	    		   $scope.Messager('error', 'Error', 'Please enter address');
	    	   }else if($scope.location.businessHoursPerDay != null && $scope.location.businessHoursPerDay != undefined  && $scope.location.businessHoursPerDay != '' && parseInt($scope.location.businessHoursPerDay) > 24  ){
    			   //$scope.location.businessHoursperDay="";
    			   $scope.Messager('error', 'Error', 'Business Hours/Day should be less than or equal to 24 Hours');
        	   }else if($scope.location.standardHoursPerWeek != null && $scope.location.standardHoursPerWeek != undefined  && $scope.location.standardHoursPerWeek != "" &&  parseInt($scope.location.standardHoursPerWeek) > 168){
    			 //  $scope.location.standardHoursPerWeek="";
    			   $scope.Messager('error', 'Error', 'Standard Hours/Week should be less than or equal to 168 Hours');
	    	   }else if($scope.transDate1 != '' && $scope.transDate1 != undefined && new Date(moment($scope.transDate1,'DD/MM/YYYY').format('YYYY-MM-DD')) .getTime() > new Date(moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime()){
	    		   $scope.Messager('error', 'Error', 'Transaction Date Should not be less than previous transaction date');
	        	   $scope.location.transactionDate =  $scope.transDate1;
	    	   }/*else if($scope.location.businessHoursperDay != null && $scope.location.businessHoursPerDay != undefined  && $scope.location.businessHoursPerDay != '' && $scope.location.businessHoursPerDay+".00" != $scope.businessHours && $scope.location.businessHoursPerDay+".00" != $scope.businessHours1){
	    		   $scope.Messager('error', 'Error', 'Business Start Time and Business End Time total duration should not be greater than Business Hours/Day.');
	    	   }else if($scope.location.standarHoursPerWeek != null && $scope.location.standarHoursPerWeek != undefined  && $scope.location.standarHoursPerWeek != '' && $scope.location.standarHoursPerWeek != parseInt($scope.location.businessHoursPerDay)*parseInt($scope.location.numberOfWorkingDays)){
	     		   $scope.Messager('error', 'Error', 'Standard Hours/Week should be the multiple of Business Hours/Day and Number of Working Days.');
		       }*/else if($scope.valid > 0){
     				$scope.Messager('error', 'Error', 'Location Headquarter is already selected.Please uncheck the checkbox.',buttonDisable);
        	   }else{
		    		   $scope.location.transactionDate = moment($('#transactionDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD'); 
		    		   //$scope.location.businessHours = $('#startTime').val();
		    		   $scope.location.businessStartTime = $('#businessStartTime').val();
		    		   $scope.location.businesEndTime = $('#businesEndTime').val();
		    		   //alert($('#workWeekStartId option:selected').html());
		    		   $scope.location.workWeekStartDay  = $('#workWeekStartId option:selected').html();
		               $scope.location.workWeekEndDay =$('#workWeekEndId option:selected').html();
		    		   $scope.location.departmentList =$scope.locationList.departmentList;
				       $scope.location.modifiedBy = $cookieStore.get('modifiedBy');
		    		   //alert(angular.toJson($scope.location));
		    		   $scope.getData('LocationController/saveLocation.json', angular.toJson($scope.location), 'saveLocation');
		       }
    	   }
	   };
       
       $scope.transactionDatesListChange = function(){
	       //alert("--" +$scope.transactionModel);
	       $scope.getData('LocationController/getLocationById.json', { locationDetailsId : ($scope.transactionModel != undefined || $scope.transactionModel != null) ? $scope.transactionModel:'0' , customerId: "" }, 'locationList')
	       $('.dropdown-toggle').removeClass('disabled');
	   };
       
       $scope.saveAddress = function(){
    	   if(($scope.location.address1 == '' || $scope.location.address1 == undefined || $scope.location.address1 == null)
    			  || ($scope.location.countryId == '' && $scope.location.countryId == undefined && $scope.location.countryId == null)){
    		   $scope.Messager('error', 'Error', 'Please enter required fields');
    	   }else{
    		   $('div[id^="myModal"]').modal('hide');
    		   $scope.labelName = " View/Edit ";
    	   }
       };
       
       $scope.saveDepartmentDetails = function(){  
    	   var status = false;
		   angular.forEach($scope.locationList.departmentList, function(value, key){	
			      if(value.departmentName == $scope.department.departmentName){
			    	  $scope.Messager('error', 'Error', 'Department already added',true); 
			    	  status = true;			    		
			      }else{
			    	  $('div[id^="myModal"]').modal('hide');
			      }
			         
			   });	
    	   if(!status && $scope.department != undefined && $scope.department != ''){
    		   //alert($scope.department.departmentInfoId.departmentInfoId);
    		//   $scope.getData('DepartmentController/getDepartmentById.json', { customerId: ($scope.location.customerId != null || $scope.location.customerId != undefined) ? $scope.location.customerId : 0, companyId: ($scope.location.companyId != null && $scope.location.companyId != undefined) ? $scope.location.companyId : 0, departmentInfoId:$scope.department.departmentInfoId, divisionId:0 }, 'departmentChange');
    		   $('div[id^="myModal"]').modal('hide');
			   $scope.popUpSave = true;
		       $scope.popUpUpdate =false;
    	   }
       };
       
       
       $scope.plusIconAction = function(){  	
    	   $scope.department="";
       		$scope.popUpSave = true;
       		$scope.popUpUpdate =false;
       };
       
       $scope.Delete = function($event){    	
       	var del = confirm("Do you want to delete the "+$scope.locationList.departmentList[$($event.target).parent().parent().index()].departmentName);    	
       	if(del){
       		$scope.locationList.departmentList.splice($($event.target).parent().parent().index(),1);
       	}
       	//alert(JSON.stringify($scope.customerDetails.customerCountriesList));
       };
       
  		 /*$scope.checkWeek = function(){
  			if(($scope.location.workWeekStartDay != undefined && $scope.location.workWeekStartDay != null && $scope.location.workWeekStartDay != undefined ) 
     			   && ($scope.location.workWeekEndDay != undefined && $scope.location.workWeekEndDay != null && $scope.location.workWeekEndDay != undefined )
     			   && ($scope.location.workWeekStartDay !=  $scope.location.workWeekEndDay)){
     		   	$scope.location.numberOfWorkingDays = parseInt($scope.location.workWeekEndDay) - parseInt($scope.location.workWeekStartDay)+1;
     	   }
  		 };*/
  		 
       $scope.validateDays = function(){
		   if ($scope.location.numberOfWorkingDays != undefined && $scope.location.numberOfWorkingDays != null && $scope.location.numberOfWorkingDays != "" && ($scope.location.numberOfWorkingDays > 7 || $scope.location.numberOfWorkingDays <= 1)){
			   $scope.Messager('error', 'Error', 'Number of Working days should be between 2 and 7.');
		   }else{
			   //alert(2);
			   if(($scope.location.workWeekStartDay != undefined && $scope.location.workWeekStartDay != null && $scope.location.workWeekStartDay != "" ) 
	      			   && ($scope.location.numberOfWorkingDays != undefined && $scope.location.numberOfWorkingDays != null && $scope.location.numberOfWorkingDays != "" ) ){
				   $scope.$apply(function(){
			  				$scope.location.workWeekEndDay = (parseInt($scope.location.workWeekStartDay) + parseInt($scope.location.numberOfWorkingDays)-1)+"";
			  				if(parseInt($scope.location.workWeekEndDay) >  7 ){
			  					//$scope.location.workWeekEndDay =( parseInt($scope.location.workWeekEndDay) - parseInt($scope.location.workWeekStartDay))+"";
			  					$scope.location.workWeekEndDay =( parseInt($scope.location.workWeekEndDay) - 7 )+"";
			  				}
				   });
			   }
		   }
       };
       
       $scope.changeWeekEnd = function(){
    	   if(($scope.location.workWeekStartDay != undefined && $scope.location.workWeekStartDay != null && $scope.location.workWeekStartDay != "" ) 
      			   && ($scope.location.numberOfWorkingDays != undefined && $scope.location.numberOfWorkingDays != null && $scope.location.numberOfWorkingDays != "" ) ){
  				$scope.location.workWeekEndDay = (parseInt($scope.location.workWeekStartDay) + parseInt($scope.location.numberOfWorkingDays)-1)+"";
  				if(parseInt($scope.location.workWeekEndDay) >  7 ){
  					$scope.location.workWeekEndDay =(  parseInt($scope.location.workWeekEndDay) - 7 )+"";
  				}
  				//alert("2: "+$scope.location.workWeekEndDay);
		   }
       }
       
  		 
     /*  $scope.fun_FindTime = function(){
    	   if($('#businessStartTime').val() != null && $('#businessStartTime').val() != undefined && $('#businessStartTime').val() != ""
    		   			&& $('#businessEndTime').val() != null && $('#businessEndTime').val() != undefined && $('#businessEndTime').val() != ""){
			 	var startDateTime = $('#businessStartTime').val();
			 	var endDateTime =  $('#businessEndTime').val();	 
			 	var minsNegativeFlag = false;
			 	//alert(startDateTime +"::"+endDateTime);
			 	
			 	var startTimeHours = startDateTime.split(":")[0];
			 	var startTimemins = startDateTime.split(":")[1];
			 	
			 	var endDateTimeHours = endDateTime.split(":")[0];
		 		var endDateTimeMins = endDateTime.split(":")[1];
		 		//alert(endDateTimeMins +"::"+startTimemins);
		 		var mins = parseInt(endDateTimeMins) - parseInt(startTimemins);
		 		//alert(mins);
		 			if(parseInt(mins) < 0){
		 				mins = 60 - ( - parseInt(mins));
		 				minsNegativeFlag = true;
		 			}
		 			
		 		var hours = parseInt(endDateTimeHours) - parseInt(startTimeHours);
		 		if(parseInt(hours) < 0){
		 			hours = 24 -(- parseInt(hours));
		 		}
		 		if(minsNegativeFlag){
	 	 			hours = hours -1;
	 	 		}
		 		var totalMinutes = parseInt(hours)*60 + parseInt(mins);
		 		
		 		hours = Math.floor(parseInt(totalMinutes)/60);
		 		mins = parseInt(totalMinutes)% 60;
		 		
		 		var strHours, strMins,strHours1;
	 	 		strHours = ''+hours;
	 	 		strHours1 = ''+hours;
	 	 		strMins = ''+mins;
		     	if(hours < 10)
		     		  strHours1 = '0'+hours;
		     	if(mins < 10)
		     		  strMins = '0'+mins;
		 		$scope.businessHours = (strHours+'.'+strMins);	
		 		$scope.businessHours1 = (strHours1+'.'+strMins);
		 }
  			 
       };*/
  		
       
       $scope.associateDepts = function(){    	
    	   if($scope.associateAllDepts){    		  
    		//   angular.forEach($scope.locationList.deptList, function(value, key){
    			//   $scope.getData('DepartmentController/getDepartmentById.json', { customerId: ($scope.location.customerId != null || $scope.location.customerId != undefined) ? $scope.location.customerId : 0, companyId: ($scope.location.companyId != null && $scope.location.companyId != undefined) ? $scope.location.companyId : 0, departmentInfoId:value.departmentInfoId, divisionId:0 }, 'departmentChange');
    		  // });   	
    		 
    	   }else{
    		      
    		 //  $scope.locationList.departmentList = $scope.avilableDepatmentList;
    		  //$scope.avilableDepatmentList
    	   }
    	   
       }
       
       
  	 
}]);




