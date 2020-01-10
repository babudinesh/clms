'use strict';


assignShiftsSearchControllers.controller("AssignShiftsCtrl", ['$scope','$rootScope', '$http', '$resource','$filter', '$location','$cookieStore','$window','$routeParams',function($scope,$rootScope,$http,$resource,$filter,$location,$cookieStore,$window,$routeParams) {
	
		$scope.enable = false;
		$scope.year = new Date(moment(new Date(), 'DD/MM/YYYY').format('YYYY-MM-DD')).getFullYear();
		$scope.month = new Date(moment(new Date(), 'DD/MM/YYYY').format('YYYY-MM-DD')).getMonth()+1;
		
		$scope.yearList = $rootScope.getYears;  
		                   
		$scope.monthList = [{"id":1,"name":"January"},
			                        {"id":2,"name":"February"},
			                        {"id":3,"name":"March"},
			                        {"id":4,"name":"April"},
			                        {"id":5,"name":"May"},
			                        {"id":6,"name":"June"},
			                        {"id":7,"name":"July"},
			                        {"id":8,"name":"August"},
			                        {"id":9,"name":"September"},
			                        {"id":10,"name":"October"},
			                        {"id":11,"name":"November"},
			                        {"id":12,"name":"December"}];
		
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
		         if (type == 'masterData'){
		        	 $scope.workerShiftResults = response.data.workerShiftResults;
		        	 $scope.deptList =  response.data.deptList;
		        	 $scope.shiftCodes = response.data.shiftCodes;
		         }else if(type == 'save'){
		        	 if(response.data != undefined && response.data > 0){
		        		 $('#myModal').modal('hide');	
		        		 $scope.Messager('success', 'success', 'Record Updated Successfully',true);
		        		 $scope.getData('assignShiftsController/AssignShiftPatternUpdateMasterData.json', { workerId : $routeParams.id, year:$scope.year ,month:$scope.month  }, 'masterData');
		        	 }else{
		        		 $scope.Messager('error', 'ERROR', 'Please Try After Some Time...',true);
		        	 }
		         }
		     });
		 }
    
		 $scope.getData('assignShiftsController/AssignShiftPatternUpdateMasterData.json', { workerId : $routeParams.id, year:$scope.year ,month:$scope.month  }, 'masterData');
		 
		 $scope.fun_ShowWorkerDetails = function($event){
			 $('#myModal').modal('show');						
	    	 $scope.workerRecord = $scope.workerShiftResults[$($event.target).parent().parent().index()];		     
		 }
		 
		 $scope.fun_SaveWorkerDetails = function($event){			
			 if($('#assignShiftspopupForm').valid()){
				 $scope.getData('assignShiftsController/saveAssignShiftPatternRecord.json', angular.toJson($scope.workerRecord) , 'save');				 
			 }
		 }
		 
		 $scope.search = function(){
			 $scope.getData('assignShiftsController/AssignShiftPatternUpdateMasterData.json', { workerId : $routeParams.id, year:$scope.year ,month:$scope.month  }, 'masterData');
		 }
		 
}]);



