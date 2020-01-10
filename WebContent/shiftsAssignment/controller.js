/**
 * calendarDemoApp - 0.9.0
 */

var calendarDemoApp = angular.module('myApp.Assighment', ['ui.calendar', 'ui.bootstrap']);

calendarDemoApp.directive("backButton", ["$window", function ($window) {
    return {
        restrict: "A",
        link: function (scope, elem, attrs) {
            elem.bind("click", function () {
                $window.history.back();
            });
        }
    };
}]);
calendarDemoApp.controller('editassignmentController',
		   function($scope, $compile, $timeout,$resource,$routeParams,$rootScope) {
	var Base64={_keyStr:"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",encode:function(e){var t="";var n,r,i,s,o,u,a;var f=0;e=Base64._utf8_encode(e);while(f<e.length){n=e.charCodeAt(f++);r=e.charCodeAt(f++);i=e.charCodeAt(f++);s=n>>2;o=(n&3)<<4|r>>4;u=(r&15)<<2|i>>6;a=i&63;if(isNaN(r)){u=a=64}else if(isNaN(i)){a=64}t=t+this._keyStr.charAt(s)+this._keyStr.charAt(o)+this._keyStr.charAt(u)+this._keyStr.charAt(a)}return t},decode:function(e){var t="";var n,r,i;var s,o,u,a;var f=0;e=e.replace(/[^A-Za-z0-9+/=]/g,"");while(f<e.length){s=this._keyStr.indexOf(e.charAt(f++));o=this._keyStr.indexOf(e.charAt(f++));u=this._keyStr.indexOf(e.charAt(f++));a=this._keyStr.indexOf(e.charAt(f++));n=s<<2|o>>4;r=(o&15)<<4|u>>2;i=(u&3)<<6|a;t=t+String.fromCharCode(n);if(u!=64){t=t+String.fromCharCode(r)}if(a!=64){t=t+String.fromCharCode(i)}}t=Base64._utf8_decode(t);return t},_utf8_encode:function(e){e=e.replace(/rn/g,"n");var t="";for(var n=0;n<e.length;n++){var r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r)}else if(r>127&&r<2048){t+=String.fromCharCode(r>>6|192);t+=String.fromCharCode(r&63|128)}else{t+=String.fromCharCode(r>>12|224);t+=String.fromCharCode(r>>6&63|128);t+=String.fromCharCode(r&63|128)}}return t},_utf8_decode:function(e){var t="";var n=0;var r=c1=c2=0;while(n<e.length){r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r);n++}else if(r>191&&r<224){c2=e.charCodeAt(n+1);t+=String.fromCharCode((r&31)<<6|c2&63);n+=2}else{c2=e.charCodeAt(n+1);c3=e.charCodeAt(n+2);t+=String.fromCharCode((r&15)<<12|(c2&63)<<6|c3&63);n+=3}}return t}}
//	params
	//$rootScope.load=true;
	$scope.encruptParams=$routeParams.workerObj;
	
	var params=angular.fromJson(Base64.decode($routeParams.workerObj));

	$scope.shiftPatternDetailId='';
	$scope.dparams={
			Customer_Id:params.search.CustomerId,
			Company_Id:params.search.CompanyId,
			SearchKey:params.SearchKey
			};

	$scope.shiftPatternDetailId=$scope.shiftPatternDetailId;
	

	$scope.jobdetails=[];
	$scope.jobdetails.work =[];
	var work_schedule_id=-1;
	$scope.buildShift=function(response){
		
		var User = $resource('/restsql/res/getSheduleByWorkShift');
		User.save(response, function(rsp){
			if(rsp.rowsAffected>=0)
			//console.log("Successfully Scheduled Dates");
			$scope.populatePivot($scope.dparams.SearchKey);
			$rootScope.load=false;
		});
	};
$scope.populatePivot=function(id){
		
		var Worker = $resource('/restsql/res/getSheduleByWorkShift?id='+id, {});
		Worker.get( {}, function(response){
		//	alert(angular.toJson(response.ScheduleDatas));
			var a="2";
			var sum = $.pivotUtilities.aggregatorTemplates.sum;
	        var numberFormat = $.pivotUtilities.numberFormat;
	        var intFormat = numberFormat({digitsAfterDecimal: 0});
	        $scope.data=response.ScheduleDatas;
			$("#output").pivot(
					response.ScheduleDatas,
					
		            {
		                rows: ["First_name"],
		                cols: ["ScheduleDate"],
		                aggregator: sum(intFormat)(["Shift_Pattern_Id"])
		            }
		        );
			
			$scope.i=0;
			setInterval(function(){ if($scope.i==0){$rootScope.load=false; $scope.i=1; $scope.marshal();}}, 500);
		});
}
$scope.getShiftName=function(workername,date){
	var temp="";
	angular.forEach($scope.data, function(value, key) {
		if(value.First_name==workername && value.ScheduleDate==date) temp+= value.Shift_Name+',';
		} );
	return temp;
}
$scope.marshal=function (){
	
	String.prototype.chunk = function(size) {
	    return [].concat.apply([],
	        this.split('').map(function(x,i){ return i%size ? [] : this.slice(i,i+size) }, this)
	    )
	}
	//console.log("sss:"+angular.toJson(hashtbl));
	var first=$( "tr:first" ).text();
	first=first.replace('ScheduleDate','');
	first=first.replace('Totals','');
	$scope.table={tableDate:'',piWorker:'',colCount:0};
	$scope.table.tableDate=first.chunk(10);
	
	
	var index=0;
	var nname="";
	$(".pvtRowLabel").each(function(){		
		nname+=$(this).html()+',';	});
	$scope.table.piWorker=nname.split(',');
	$(function() {
	    var colCount = 0;
	    $('tr:nth-child(1) td').each(function () {
	        if ($(this).attr('colspan')) {
	            $scope.table.colCount += +$(this).attr('colspan');
	        } else {
	        	$scope.table.colCount++;	        	
	        }
	    });
	});
	
	var index=0,rowCount=0;
	$('.pvtTable .pvtVal').each(function()
			{
		//console.log("ddd:"+$(this).closest('tr').html());
		if($scope.table.colCount-1==index) {rowCount++;index=0;}		
			  $(this).html($scope.getShiftName($scope.table.piWorker[rowCount],$scope.table.tableDate[index]));			  
			  index++;
			  
			});
	
}

		$scope.populatePivot($scope.dparams.SearchKey);
		
	$scope.generateschedule=function(pattern_id){
		
		var Worker = $resource('/restsql/res/getShedule?Worker_Shift_Pattern_Id='+pattern_id, {});
		Worker.get( {}, function(response){
			work_schedule_id=response.ScheduleDates[0].Worker_Shift_Pattern_Id;
			if(work_schedule_id=pattern_id)
				$scope.buildShift(response);
			
			
			
		});
		
	};
	$scope.saveData=function(){
		$rootScope.load=true;
		//$scope.users.push({ 'Customer_Id':$routeParams.customerId, 'Company_Id': $routeParams.companyId, 'Vendor_Id':$routeParams.vendorId, 'Worker_Id':$routeParams.workerId, 'Shift_Pattern_Details_Id':$scope.shiftPatternDetailId });		
		// Create a resource class object
		//
		var User = $resource('/restsql/res/getWorkerShiftPattern');
		// Call action method (save) on the class 
		//
		//alert("<><>"+$scope.shiftPatternDetailId);
		var date1 = new Date();
		
		var pdate=  $scope.patternDate.getFullYear()+'-'+($scope.patternDate.getMonth() + 1) + '-' + $scope.patternDate.getDate() ;
	//	alert("ss1:"+pdate);
		User.save({WorkerShiftPatterns:[{
			Customer_Id:$scope.dparams.Customer_Id,
			 Company_Id:$scope.dparams.Company_Id,			  
			  Worker_Id:$scope.dparams.SearchKey,
			  Shift_Pattern_Id:$scope.shiftPatternDetailId,
			  Pattern_Date:pdate}]}, function(response){
			$scope.message = response.message;
			$scope.workshiftPatterns=response.WorkerShiftPatterns[0];
			if($scope.workshiftPatterns.Worker_Shift_Pattern_Id>0) 
			$scope.generateschedule($scope.workshiftPatterns.Worker_Shift_Pattern_Id);
		});
	
		//alert("abb");
	}
	var obj={
			Customer_Id:$scope.customerId,
			Company_id:$scope.companyId,Vendor_I:$scope.vendorId,Worker_Id:$scope.workerId
	};
	obj=$rootScope.filterParameter(obj);
	var job = $resource('/restsql/res/getWorkJobDetails', obj);
	//alert($routeParams.customerId)
	
	job.get( {}, function(response){
		$scope.jobdetails.work  = response.WorkJobDetailss[0];
	}, function(error) {
		$rootScope.error("Getting WorkJob","#/");
	});
	/*var workarea = $resource('/restsql/res/getWorkAreaByCustomerIdAndCompanyId?Customer_Id='+$scope.customerId+"&Company_Id="+$scope.companyId, {});
	//alert($routeParams.customerId)
	workarea.get( {}, function(response){
		$scope.jobdetails.workarea  = response.WorkAtraDetailss[0];
	}, function(error) {
		$rootScope.error("Getting workare info","#/");
	});*/
	var pattern = $resource('/restsql/res/geShiftByCustomerIdAndCompanyId?Customer_Id='+$scope.dparams.Customer_Id+"&Company_Id="+$scope.dparams.Company_Id, {});
	//alert($routeParams.customerId)
	pattern.get( {}, function(response){
		$scope.jobdetails.pattern  = response.ShiftDetailss;
		
	}, function(error) {
		$rootScope.error("Getting Shift info","#/");
	})

});
calendarDemoApp.controller('listassignmentController',
   function($scope, $compile, $timeout,$resource,$rootScope,$routeParams  ) {


$scope.checkAll = function () {
	//alert($scope.selectedAll);
    if ($scope.selectedAll) {
        $scope.selectedAll = true;
       
    } else {
        $scope.selectedAll = false;
        
    }
    angular.forEach($scope.searchResult, function (item) {
        item.Selected = $scope.selectedAll;
        });

};


	$scope.fun_companyChange=function (){
		
		//alert($scope.Assign.Company_id);
		var Country = $resource('/restsql/res/getCountryByCompanyId?Company_Id='+$scope.Assign.search.CompanyId+"&customer_id="+$scope.Assign.search.CustomerId+'', {});
		Country.get( {}, function(response){
			$scope.Assign.CompanyAddress  = response.CompanyAddresss[0];
		});
		
		var Vendor = $resource('/restsql/res/getVendorsByCustomerIdAndCompanyId?company_id='+$scope.Assign.search.CompanyId+"&Customer_id="+$scope.Assign.search.CustomerId+'', {});
		Vendor.get( {}, function(response){
			$scope.Assign.VendorDetails  = response.VendorDetailss;
		//	alert(angular.toJson("<><>"+$scope.Assign.VendorDetails));
		});
		var WorkOrder = $resource('/restsql/res/getWorkOrderByCustomerIdAndCompanyId?company_id='+$scope.Assign.search.CompanyId+"&Customer_id="+$scope.Assign.search.CustomerId+'', {});
		WorkOrder.get( {}, function(response){
			$scope.Assign.WorkOrderDetail  = response.WorkOrderDetailss;
			//alert(angular.toJson("<><1>"+$scope.Assign.WorkOrderDetail));
		});
		var Worker = $resource('/restsql/res/getWorkerByCustomerIdAndCompanyId?company_id='+$scope.Assign.search.CompanyId+"&customer_id="+$scope.Assign.search.CustomerId+'&_limit=100&_offset=0', {});
		Worker.get( {}, function(response){
			$scope.Assign.WorkerDetail  = response.WorkerDetailss;
		//	alert(angular.toJson("<><3>"+$scope.Assign.WorkerDetail));
		});
		var Department = $resource('/restsql/res/getDepartmentByCustomerIdAndCompanyId?Company_id='+$scope.Assign.search.CompanyId+"&Customer_id="+$scope.Assign.search.CustomerId+'', {});
		Department.get( {}, function(response){
			$scope.Assign.DepartmentDetail  = response.DepartmentDetailss;
		//	alert(angular.toJson("<><4>"+$scope.Assign.DepartmentDetail));
		});
		var WorkArea = $resource('/restsql/res/getWorkAreaByCustomerIdAndCompanyId?Company_Id='+$scope.Assign.search.CompanyId+"&Customer_Id="+$scope.Assign.search.CustomerId+'', {});
		WorkArea.get( {}, function(response){
			$scope.Assign.WorkAreaDetail  = response.WorkAtraDetailss;
		//	alert(angular.toJson("<><4>"+$scope.Assign.DepartmentDetail));
		});
	};
	$scope.initSearch=function(){
		
		var Base64={_keyStr:"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",encode:function(e){var t="";var n,r,i,s,o,u,a;var f=0;e=Base64._utf8_encode(e);while(f<e.length){n=e.charCodeAt(f++);r=e.charCodeAt(f++);i=e.charCodeAt(f++);s=n>>2;o=(n&3)<<4|r>>4;u=(r&15)<<2|i>>6;a=i&63;if(isNaN(r)){u=a=64}else if(isNaN(i)){a=64}t=t+this._keyStr.charAt(s)+this._keyStr.charAt(o)+this._keyStr.charAt(u)+this._keyStr.charAt(a)}return t},decode:function(e){var t="";var n,r,i;var s,o,u,a;var f=0;e=e.replace(/[^A-Za-z0-9+/=]/g,"");while(f<e.length){s=this._keyStr.indexOf(e.charAt(f++));o=this._keyStr.indexOf(e.charAt(f++));u=this._keyStr.indexOf(e.charAt(f++));a=this._keyStr.indexOf(e.charAt(f++));n=s<<2|o>>4;r=(o&15)<<4|u>>2;i=(u&3)<<6|a;t=t+String.fromCharCode(n);if(u!=64){t=t+String.fromCharCode(r)}if(a!=64){t=t+String.fromCharCode(i)}}t=Base64._utf8_decode(t);return t},_utf8_encode:function(e){e=e.replace(/rn/g,"n");var t="";for(var n=0;n<e.length;n++){var r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r)}else if(r>127&&r<2048){t+=String.fromCharCode(r>>6|192);t+=String.fromCharCode(r&63|128)}else{t+=String.fromCharCode(r>>12|224);t+=String.fromCharCode(r>>6&63|128);t+=String.fromCharCode(r&63|128)}}return t},_utf8_decode:function(e){var t="";var n=0;var r=c1=c2=0;while(n<e.length){r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r);n++}else if(r>191&&r<224){c2=e.charCodeAt(n+1);t+=String.fromCharCode((r&31)<<6|c2&63);n+=2}else{c2=e.charCodeAt(n+1);c3=e.charCodeAt(n+2);t+=String.fromCharCode((r&15)<<12|(c2&63)<<6|c3&63);n+=3}}return t}}
		try{
		$scope.Assign=angular.fromJson(Base64.decode($routeParams.globalInfo));
		$scope.fun_companyChange();
	}catch(e){
	//	alert(e);
		window.location.href="#/";
	}
		}
	$rootScope.load=true;
	$scope.initSearch();
	$rootScope.load=false;
	$scope.fun_workerCodeChange=function(){
		$scope.Assign.workerName=angular.fromJson($scope.Assign.worker).First_name;
		$scope.Assign.search.WorkerId=angular.fromJson($scope.Assign.worker).worker_id;
	}
	$scope.fun_departmentChange=function(){
		$scope.Assign.search.department_id=angular.fromJson($scope.Assign.department).department_Id;
	}
	$scope.fun_workAreaChange=function(){
		$scope.Assign.search.Work_Area_Id=angular.fromJson($scope.Assign.workarea).Work_Area_Id;
	}
	$scope.searchResult= new Object();
	$scope.fun_searchGridData=function(){
		$scope.searchResult=[];
		$rootScope.load=true;
		var dtJsonObject=$scope.Assign.search;
	//	alert(angular.toJson($scope.Assign.search));
		if(dtJsonObject==undefined || dtJsonObject==null){
			dtJsonObject={};
		}
		for (var i in dtJsonObject) {
			  if (dtJsonObject[i] === '' || dtJsonObject[i] === null || dtJsonObject[i] === undefined) {
			  // test[i] === undefined is probably not very useful here
			    delete dtJsonObject[i];
			  }
			}
	   //   alert(angular.toJson(dtJsonObject));
		var Search = $resource('/restsql/res/searchWorkerWorkOrder2', dtJsonObject);
		Search.get( {}, function(response){
			$scope.searchResult  = response.WorkerDetailss;
			$rootScope.load=false;
		//	alert("4:"+$scope.searchResult);
		//	alert(angular.toJson("<><4>"+$scope.Assign.DepartmentDetail));
		},function(error){
			
			alert("ERROR!");
			$rootScope.load=false;
		});
	}
	$scope.editPattern=function(){
		//alert("kk");
		var Account={WorkerGroups:[]};
		$scope.searchGroup="";
		angular.forEach($scope.searchResult, function (item) {
	        if(item.Selected) {Account.WorkerGroups.push(item); 
	        $scope.searchGroup=item.id;
	        }
	        
	    });
		var Base64={_keyStr:"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",encode:function(e){var t="";var n,r,i,s,o,u,a;var f=0;e=Base64._utf8_encode(e);while(f<e.length){n=e.charCodeAt(f++);r=e.charCodeAt(f++);i=e.charCodeAt(f++);s=n>>2;o=(n&3)<<4|r>>4;u=(r&15)<<2|i>>6;a=i&63;if(isNaN(r)){u=a=64}else if(isNaN(i)){a=64}t=t+this._keyStr.charAt(s)+this._keyStr.charAt(o)+this._keyStr.charAt(u)+this._keyStr.charAt(a)}return t},decode:function(e){var t="";var n,r,i;var s,o,u,a;var f=0;e=e.replace(/[^A-Za-z0-9+/=]/g,"");while(f<e.length){s=this._keyStr.indexOf(e.charAt(f++));o=this._keyStr.indexOf(e.charAt(f++));u=this._keyStr.indexOf(e.charAt(f++));a=this._keyStr.indexOf(e.charAt(f++));n=s<<2|o>>4;r=(o&15)<<4|u>>2;i=(u&3)<<6|a;t=t+String.fromCharCode(n);if(u!=64){t=t+String.fromCharCode(r)}if(a!=64){t=t+String.fromCharCode(i)}}t=Base64._utf8_decode(t);return t},_utf8_encode:function(e){e=e.replace(/rn/g,"n");var t="";for(var n=0;n<e.length;n++){var r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r)}else if(r>127&&r<2048){t+=String.fromCharCode(r>>6|192);t+=String.fromCharCode(r&63|128)}else{t+=String.fromCharCode(r>>12|224);t+=String.fromCharCode(r>>6&63|128);t+=String.fromCharCode(r&63|128)}}return t},_utf8_decode:function(e){var t="";var n=0;var r=c1=c2=0;while(n<e.length){r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r);n++}else if(r>191&&r<224){c2=e.charCodeAt(n+1);t+=String.fromCharCode((r&31)<<6|c2&63);n+=2}else{c2=e.charCodeAt(n+1);c3=e.charCodeAt(n+2);t+=String.fromCharCode((r&15)<<12|(c2&63)<<6|c3&63);n+=3}}return t}}
		var param=angular.fromJson(Base64.decode($routeParams.globalInfo));
		param.SearchKey=$scope.searchGroup;
		
		//alert(angular.toJson(param.search));
		$scope.eparam= Base64.encode(angular.toJson(param));
		               //Base64.encode(angular.toJson(global));
		
		 var GroupWorker = $resource('/restsql/res/createGroupSearch');
			// Call action method (save) on the class 
			//
			//alert("<><>"+$scope.shiftPatternDetailId);
		 $scope.load=true;
		 GroupWorker.save(Account, function(response){
				//$scope.message = response.message;
				//alert("Successs ");
				$scope.load=false;
			//	alert($scope.eparam);
		window.location.href="#/editShiftAssignment/"+$scope.eparam;
			//
				/*$scope.workshiftPatterns=response.WorkerShiftPatterns[0];
				if($scope.workshiftPatterns.Worker_Shift_Pattern_Id>0) 
					$scope.generateschedule($scope.workshiftPatterns.Worker_Shift_Pattern_Id);*/
			}, function(error){
				alert("Cannot Process Shift :"+$scope.searchGroup);
				window.location.href="#/";
				
				
			});
		/*	$scope.Assign.search.WorkerId=i.WorkerId;
		$scope.Assign.search.VendorId=i.VendorId;
		var s=''+angular.toJson($scope.Assign.search);
		//alert("s1s:"+s);
		var Base64={_keyStr:"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",encode:function(e){var t="";var n,r,i,s,o,u,a;var f=0;e=Base64._utf8_encode(e);while(f<e.length){n=e.charCodeAt(f++);r=e.charCodeAt(f++);i=e.charCodeAt(f++);s=n>>2;o=(n&3)<<4|r>>4;u=(r&15)<<2|i>>6;a=i&63;if(isNaN(r)){u=a=64}else if(isNaN(i)){a=64}t=t+this._keyStr.charAt(s)+this._keyStr.charAt(o)+this._keyStr.charAt(u)+this._keyStr.charAt(a)}return t},decode:function(e){var t="";var n,r,i;var s,o,u,a;var f=0;e=e.replace(/[^A-Za-z0-9+/=]/g,"");while(f<e.length){s=this._keyStr.indexOf(e.charAt(f++));o=this._keyStr.indexOf(e.charAt(f++));u=this._keyStr.indexOf(e.charAt(f++));a=this._keyStr.indexOf(e.charAt(f++));n=s<<2|o>>4;r=(o&15)<<4|u>>2;i=(u&3)<<6|a;t=t+String.fromCharCode(n);if(u!=64){t=t+String.fromCharCode(r)}if(a!=64){t=t+String.fromCharCode(i)}}t=Base64._utf8_decode(t);return t},_utf8_encode:function(e){e=e.replace(/rn/g,"n");var t="";for(var n=0;n<e.length;n++){var r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r)}else if(r>127&&r<2048){t+=String.fromCharCode(r>>6|192);t+=String.fromCharCode(r&63|128)}else{t+=String.fromCharCode(r>>12|224);t+=String.fromCharCode(r>>6&63|128);t+=String.fromCharCode(r&63|128)}}return t},_utf8_decode:function(e){var t="";var n=0;var r=c1=c2=0;while(n<e.length){r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r);n++}else if(r>191&&r<224){c2=e.charCodeAt(n+1);t+=String.fromCharCode((r&31)<<6|c2&63);n+=2}else{c2=e.charCodeAt(n+1);c3=e.charCodeAt(n+2);t+=String.fromCharCode((r&15)<<12|(c2&63)<<6|c3&63);n+=3}}return t}}
		window.location.href="#/editShiftAssignment/"+Base64.encode(s);
		//alert("s1s:"+angular.toJson($scope.Assign));*/
	}
});
//Abc

calendarDemoApp.controller('assignmentController',
   function($scope, $compile,uiCalendarConfig,$resource,$routeParams,$http,$location) {

//	alert(param);
	$scope.init=function(){
		//$scope.events=[];
	
	$scope.edit=false;
	$scope.goodEvent=function(){
		var Base64={_keyStr:"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",encode:function(e){var t="";var n,r,i,s,o,u,a;var f=0;e=Base64._utf8_encode(e);while(f<e.length){n=e.charCodeAt(f++);r=e.charCodeAt(f++);i=e.charCodeAt(f++);s=n>>2;o=(n&3)<<4|r>>4;u=(r&15)<<2|i>>6;a=i&63;if(isNaN(r)){u=a=64}else if(isNaN(i)){a=64}t=t+this._keyStr.charAt(s)+this._keyStr.charAt(o)+this._keyStr.charAt(u)+this._keyStr.charAt(a)}return t},decode:function(e){var t="";var n,r,i;var s,o,u,a;var f=0;e=e.replace(/[^A-Za-z0-9+/=]/g,"");while(f<e.length){s=this._keyStr.indexOf(e.charAt(f++));o=this._keyStr.indexOf(e.charAt(f++));u=this._keyStr.indexOf(e.charAt(f++));a=this._keyStr.indexOf(e.charAt(f++));n=s<<2|o>>4;r=(o&15)<<4|u>>2;i=(u&3)<<6|a;t=t+String.fromCharCode(n);if(u!=64){t=t+String.fromCharCode(r)}if(a!=64){t=t+String.fromCharCode(i)}}t=Base64._utf8_decode(t);return t},_utf8_encode:function(e){e=e.replace(/rn/g,"n");var t="";for(var n=0;n<e.length;n++){var r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r)}else if(r>127&&r<2048){t+=String.fromCharCode(r>>6|192);t+=String.fromCharCode(r&63|128)}else{t+=String.fromCharCode(r>>12|224);t+=String.fromCharCode(r>>6&63|128);t+=String.fromCharCode(r&63|128)}}return t},_utf8_decode:function(e){var t="";var n=0;var r=c1=c2=0;while(n<e.length){r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r);n++}else if(r>191&&r<224){c2=e.charCodeAt(n+1);t+=String.fromCharCode((r&31)<<6|c2&63);n+=2}else{c2=e.charCodeAt(n+1);c3=e.charCodeAt(n+2);t+=String.fromCharCode((r&15)<<12|(c2&63)<<6|c3&63);n+=3}}return t}}
		//alert($routeParams.workerId);
		
		try{
		
		var param=angular.fromJson(Base64.decode($routeParams.workerId));
		
		//alert(angular.toJson(param));
		var urlPass = '/restsql/res/getWorkerScheduled';//$resource('/restsql/res/getShedule', {});
		var dataJsonIn={url:urlPass,data:{id:param.SearchKey},async:false};
		var responseResult=AjaxGetMethod(dataJsonIn);
		
		if(!responseResult.IsError){
		// alert(angular.toJson(responseResult)); 
			$.each(responseResult.dataResult.ScheduleDatas,function(index,value){
				$scope.addEvent(value);
			});
			//$scope.remove(1);
		}else{
			
		} 
	//	$scope.addEvent('Demo',1467305400000,1467311400000,'green');
		}catch(e){
			//window.location.href="#/";
		}
	}
	$scope.count=0;
    var date = new Date();
    var d = date.getDate();
    var m = date.getMonth();
    var y = date.getFullYear();

    $scope.changeTo = 'Hungarian';
    /* event source that pulls from google.com */
    $scope.eventSource = {
           
    };
    /* event source that contains custom events on the scope */
    $scope.events = [
     
    ];
    /* event source that calls a function on every view switch */
    $scope.eventsF = function (start, end, timezone, callback) {
      var s = new Date(start).getTime() / 1000;
      var e = new Date(end).getTime() / 1000;
      var m = new Date(start).getMonth();
      var events = [{title: 'Feed Me ' + m,start: s + (50000),end: s + (100000),allDay: false, className: ['customFeed']}];
      callback(events);
    };

    $scope.calEventsExt = {
       color: '#f00',
       textColor: 'yellow',
       events: [
          {type:'party',title: 'Lunch',start: new Date(y, m, d, 12, 0),end: new Date(y, m, d, 14, 0),allDay: false},
          {type:'party',title: 'Lunch 2',start: new Date(y, m, d, 12, 0),end: new Date(y, m, d, 14, 0),allDay: false},
          {type:'party',title: 'Click for Google',start: new Date(y, m, 28),end: new Date(y, m, 29),url: 'http://google.com/'}
        ]
    };
    /* alert on eventClick */
    $scope.alertOnEventClick = function( date, jsEvent, view){    	
        $scope.alertMessage = (date.jsonObj + 'qq');
        $scope.edit=true;
        $scope.cevent=date.jsonObj;
        var Shift = $resource('/restsql/res/getShiftInfo', {});
    	//alert($routeParams.customerId)
        Shift.get( {}, function(response){
    		$scope.shifts  = response.ShiftInfos ;
    	//	alert($scope.shifts);
    	});
      
        $scope.updateInfo=function(){        	
        
        	var temp =angular.fromJson($scope.ceven1);
        	var obj = angular.extend({}, $scope.cevent, temp);
       
        	var release = '{\"ScheduleDatas\":['+angular.toJson(obj)+']}';
        	var resp=angular.fromJson(release);
        	
        	
        	//alert(angular.toJson(resp));
        	
        	$http.put('/restsql/res/getWorkerScheduled', resp)
        	   .then(
        	       function(response){
        	    	   
        	        alert("success");
        	        location.reload();
        	        //window.location.href="/CLMS/#/calandershiftAssignment/16";
        	       // $scope.edit=false;
        	      //$scope.init();
        	       
        	       }, 
        	       function(response){
        	         // failure callback
        	    	   $scope.edit=false;
        	    	   alert("error");
        	       }
        	    );
        	
        }  
        
    };

    /* alert on Drop */
     $scope.alertOnDrop = function(event, delta, revertFunc, jsEvent, ui, view){
       $scope.alertMessage = ('Event Droped to make dayDelta ' + delta);
    };
    /* alert on Resize */
    $scope.alertOnResize = function(event, delta, revertFunc, jsEvent, ui, view ){
       $scope.alertMessage = ('Event Resized to make dayDelta ' + delta);
    };
    /* add and removes an event source of choice */
    $scope.addRemoveEventSource = function(sources,source) {
      var canAdd = 0;
      angular.forEach(sources,function(value, key){
        if(sources[key] === source){
          sources.splice(key,1);
          canAdd = 1;
        }
      });
      if(canAdd === 0){
        sources.push(source);
      }
    };
    /* add custom event*/
    $scope.addEvent = function(calandarEvent) {
    	//title,from1,end,color,starttime,endtime,
    	var objArr=['openSesame'];

    	var from = ((calandarEvent.start)+"").split("-");
    	
    	var f = new Date(from[0], from[1] - 1, from[2]);
    	var from= ((calandarEvent.Start_Time)+"").split(":");
    	f.setHours(from[0]);
    	f.setMinutes(from[1]);
    	//alert(f);
    	var from = ((calandarEvent.start)+"").split("-");
    	var to = new Date(from[0], from[1] - 1, from[2]);
    	var from= ((calandarEvent.End_Time)+"").split(":");
    	to.setHours(from[0]);
    	to.setMinutes(from[1]);
      $scope.events.push({
        title:calandarEvent.First_name+"- "+calandarEvent.Shift_Name,
        color:calandarEvent.Shift_Color,
        start: f,
        end: to,
        jsonObj:calandarEvent,
       className: objArr
      });
      //url: '#/editScheduleDate/'+(calandarEvent.Company_id)+'/'+(calandarEvent.Customer_Id)+'/'+(calandarEvent.Worker_Id)+'/'+(calandarEvent.Vendor_Id)+'/'+(calandarEvent.start)+'',
    };
    /* remove event */
    $scope.remove = function(index) {
    $.each($scope.events,function(indexItem,value){
        $scope.events.splice(indexItem,1);
    });	
    };
    /* Change View */
    $scope.changeView = function(view,calendar) {
      uiCalendarConfig.calendars[calendar].fullCalendar('changeView',view);
    };
    /* Change View */
    $scope.renderCalender = function(calendar) {
      $timeout(function() {
        if(uiCalendarConfig.calendars[calendar]){
          uiCalendarConfig.calendars[calendar].fullCalendar('render');
        }
      });
    };
    
    //On change date
    $scope.fun_changeshiftPatternDetailId=function(){
    //	alert(123);
    	
    };
     /* Render Tooltip */
    $scope.eventRender = function( event, element, view ) {
        element.attr({'tooltip': event.title,
                      'tooltip-append-to-body': true});
        $compile(element)($scope);
    };
    /* config object */
    $scope.uiConfig = {
      calendar:{
        height: 450,
        editable: true,
        firstDay:1,
        header:{
          left: 'title',
          center: '',
          right: 'today prev,next'
        },
        eventClick: $scope.alertOnEventClick,
        eventDrop: $scope.alertOnDrop,
        eventResize: $scope.alertOnResize,
        eventRender: $scope.eventRender
      }
    };

    $scope.changeLang = function() {
      if($scope.changeTo === 'Hungarian'){
        $scope.uiConfig.calendar.dayNames = ["Vasárnap", "Hétfő", "Kedd", "Szerda", "Csütörtök", "Péntek", "Szombat"];
        $scope.uiConfig.calendar.dayNamesShort = ["Vas", "Hét", "Kedd", "Sze", "Csüt", "Pén", "Szo"];
        $scope.changeTo= 'English';
      } else {
        $scope.uiConfig.calendar.dayNames = [ "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday","Sunday"];
        $scope.uiConfig.calendar.dayNamesShort = [ "Mon", "Tue", "Wed", "Thu", "Fri", "Sat","Sun"];
        $scope.changeTo = 'Hungarian';
      }
    };
    
    /* event sources array*/
    $scope.eventSources = [$scope.events, $scope.eventSource, $scope.eventsF];
    $scope.eventSources2 = [$scope.calEventsExt, $scope.eventsF, $scope.events];
    $scope.goodEvent();
	}
	$scope.init();
});
function AjaxGetMethod(dataJson) {
	var dt={};
	$.ajax({
		  dataType: 'json',
		  type: 'GET',
		  url: dataJson.url,
		  data: dataJson.data,
		  async:dataJson.async,
		  success: function(resultDt){
			  dt={isError:false, dataResult:resultDt};
		  },
		  error: function(err){
			  dt={isError:true, dataResult:err};
		  }
		});
	  return dt;
	
  }
/* EOF */
