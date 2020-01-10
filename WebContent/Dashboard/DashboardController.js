'use strict';

//var dashBoard = angular.module("myApp.Dashboard", ['ngCookies']);


var url_workerCountByAgeGroup = "dashboardController/getWorkerAgeGroup.json";
var url_shiftWise = "dashboardController/getShiftWiseAttendanceDetails.json";
var url_compliance = "dashboardController/getVendorComplianceStatus.json";
var url_workerInfo = "dashboardController/getWorkersHeadCount.json";


dashBoardControlller.controller("dashboardDetailsCtrl", ['$scope','$cookies', '$http', '$resource', '$location','$cookieStore','$filter', function ($scope, $cookies, $http, $resource, $location,$cookieStore,$filter) {  
	$scope.workerCountList = new Object();
	$.jqplot.config.enablePlugins = true;

	var plot_overTimeHours;
	var plot_shiftWise;

	var attendanceList = [],attendanceListtricks = [],overtimeList=[],overtimeListtricks = [];
	
	var cookies = $cookies.getAll();
	var userId = $cookieStore.get('userId');
	//alert(userId);
	var cookieString = "{ ";
    angular.forEach(cookies, function (v, k) {
    	if(k != 'globals')
    	cookieString += (" "+k+" : "+v+" ,");
    }); 
    cookieString += " secure : true } ";
  
    // DatePicker Render
    $('#fromDate, #toDate').datepicker({
	      changeMonth: true,
	      changeYear: true,
	      dateFormat:"dd/mm/yy",
	      showAnim: 'slideDown',
	      maxDate: '0'
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
    
    $scope.fromDate = $filter('date')(new Date(),'dd/MM/yyyy');
    $scope.toDate = $filter('date')(new Date(),'dd/MM/yyyy');
	   
    $('#workerInfoLoader').show();
    // Worker 
    $http({
		url : ROOTURL + url_workerInfo,
		method : "POST",
		async : false,
		headers : { 'Content-Type' : 'application/json' },
//		data: {"customerId":37, "companyId":5, "fromDate": "2016-12-01", "toDate":"2016-12-31"}
		data : { "customerId" : $cookieStore.get('customerId'), 
					"companyId" : $cookieStore.get('companyId')}
		})
		.then(
				function(response) {
					if(response.data != null && response.data.workerInfo != undefined ){
						//alert(angular.toJson(response.data));
						$scope.workerInfoList = response.data.workerInfo;
						$('#workerInfoData').show();
						$('#workerNoData').hide();
						$('#workerInfoLoader').hide();
					}else{
						$('#workerInfoData').hide();
						$('#workerNoData').show();
						$('#workerInfoLoader').hide();
					}
					$('#workerInfoLoader').hide();
				},
				function() {
					console.log('Error on getWorkerAgeGroup.json');
				});

    
  
    
    
    
    // Vendor Compliance Status 
    $http({
		url : ROOTURL + url_compliance,
		method : "POST",
		async : false,
		headers : { 'Content-Type' : 'application/json' },
		data : { "customerId" : $cookieStore.get('customerId'), 
				 "companyId" : $cookieStore.get('companyId')
			   }
		})
		.then(
				function(response) {
		          if(response.data != null && response.data!= null && response.data.length > 0){
		        	  $scope.complianceList = response.data;
		        	  $('#compliance').show();
		        	  $('#complianceNoData').hide();
		        	  $('#complianceLoader').hide();
		        	  
		          }else{
		        	  $('#compliance').hide();
		        	  $('#complianceNoData').show();
		        	  $('#complianceLoader').hide();
		          }
				},
				function() {
					console.log('Error on Vendor Compliance.json');
				});
    
    /*
    $http({
		url : ROOTURL + url_shiftWise,
		method : "POST",
		async : false,
		headers : { 'Content-Type' : 'application/json' },
//		data: {"customerId":37, "companyId":5, "fromDate": "2016-12-01", "toDate":"2016-12-31"}
		data : { "customerId" : $cookieStore.get('customerId'), 
					"companyId" : $cookieStore.get('companyId'), 
					"fromDate" : moment().format('YYYY-MM-DD'),//moment().subtract(7, 'days').format('YYYY-MM-DD'), 
					"toDate" : moment().format('YYYY-MM-DD'),
					"userId" : userId
					}
		})
		.then(
				function(response) {
			          
					//alert(angular.toJson(response.data));
					//$scope.shiftData = response.data.shiftWiseAttendance;
					if(response.data != null && response.data.shiftWiseAttendance != undefined && response.data.shiftWiseAttendance.length > 0){
							if (plot_shiftWise) { 	plot_shiftWise.destroy(); 	}
							$('#chartShift').show();
							plot_shiftWise = jQuery.jqplot('chartShift', response.data.shiftWiseAttendance, {

    					//var plot3 = jQuery.jqplot('chartShiftwise', response.data.chartData, {
    										  animate : !$.jqplot.use_excanvas,
    			    			        	  title:{text :'Shift Wise', textColor: '#0c35af' },
    			    			        	  seriesColors:['darkorange', '#839557'],
    			    			        	  series: [{label: 'Scheduled', color: 'darkorange'},
    			    			        	           {label: 'Present', color: '#839557'}],
    			   			              
    			   			              legend:{
    			   			            	    renderer: $.jqplot.EnhancedLegendRenderer,
    			   			            	    show:true, 
    			   			            	    //labels:['Present','Absent'],
    			   			            	    placement:'outsideGrid',
    			   			            	  
    			   			            	    location:'e',
    			   			            	    
    			   			            	    border:'none'
    			   			            	},
    			    			              seriesDefaults: {
    			    			                  renderer:$.jqplot.BarRenderer,
    			    			                  pointLabels: { show: true },
    			    			                 
    			    			              },
    			    			              axes: {
    			    			                  xaxis: {
    			    			                      renderer: $.jqplot.CategoryAxisRenderer,
    			    			                     // ticks: response.data.ticks,    			    			                      		                      
    			    			                  }
    			    			              },
    			    			              highlighter: {
    								            	tooltipAxes: 'y',
    								                show: false,
    								                sizeAdjust: 7.5
    								          },
								              cursor: {
								                show: false
								              }
    									});
							
    					$('#chartShiftLoader').hide();
    					$('#chartShiftNodata').hide();
    			    	//$('#chartShiftwise').show();
					}else{
    					$('#chartShiftLoader').hide();
    					$('#chartShift').hide();
    					$('#chartShiftNodata').show();
					}
				},
				function() {
					$('#chartShiftwiseLoader').hide();
					$('#chartShiftwise').hide();
					$('#chartShiftwiseNodata').show();
				});*/

   
    $scope.goButton = false;
    // Go Button Action 
    $scope.refreshAttendance = function(){
    	$scope.goButton = true;
    	//alert(moment(new Date($('#fromDate').val())).isAfter(moment(new Date($('#toDate').val()))));
		var fromDate = moment($('#fromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
		var toDate = moment($('#toDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
		if((fromDate == null || fromDate == undefined || fromDate == '' || fromDate == 'Invalid Date')&& (toDate == null || toDate == undefined || toDate == '' || toDate == 'Invalid Date')){
			$scope.Messager('error','Error',' Please select From Date and To date');
			$scope.goButton = false;
			return;
		}else if((new Date(moment($('#fromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime() > new Date(moment($('#toDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime())){
			$scope.Messager('error','error',' To Date should be greater than From Date.');
			$scope.goButton = false;
			return;
		}else{
		   // $("#fromDate").datepicker('setDate', new Date());
		  //  $("#toDate").datepicker('setDate', new Date());
			//var fromDate = moment($('#fromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
			//var toDate = moment($('#fromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
			$scope.loadData(fromDate, toDate);
		}
    };
    
    $scope.loadData = function(fromDate, toDate){
    	$('#chartPresentShiftwiseLoader').show();
    	$('#chartPresentShiftwise').hide();
    	$('#chartPresentShiftwiseNodata').hide();
    	$('#chartScheduledShiftwiseLoader').show();
    	$('#chartScheduledShiftwise').hide();
    	$('#chartScheduledShiftwiseNodata').hide();
    	
    	$('#chartShiftLoader').show();
    	$('#chartShift').hide();
    	$('#chartShiftNodata').hide();
     
    	 // Shiftwise 
        $http({
    		url : ROOTURL + url_shiftWise,
    		method : "POST",
    		async : false,
    		headers : { 'Content-Type' : 'application/json' },
//    		data: {"customerId":37, "companyId":5, "fromDate": "2016-12-01", "toDate":"2016-12-31"}
    		data : { "customerId" : $cookieStore.get('customerId'), 
    					"companyId" : $cookieStore.get('companyId'), 
    					"fromDate" :fromDate, 
    					"toDate" : toDate,
    					"userId" : userId
    					}
    		})
    		.then(
    				function(response) {
    			          
    					//console.log(angular.toJson(response.data.shiftWiseAttendance));
    					
    					if(response.data != null && response.data.shiftWiseAttendance != undefined && response.data.shiftWiseAttendance.length > 0){
    						//$('#chartShiftwise').show();
    						//if (plot_shiftWise1) { 	plot_shiftWise1.destroy();}

    						$('#chartShift').show();
    						$('#chartShift').html('');
    							var plot_shiftWise1 = jQuery.jqplot('chartShift', response.data.shiftWiseAttendance, {

        					//var plot3 = jQuery.jqplot('chartShiftwise', response.data.chartData, {
        										  animate : !$.jqplot.use_excanvas,
        			    			        	  title:{text : 'Shift Wise' , textColor: '#0c35af' },
        			    			        	  seriesColors:['darkorange', '#839557'],
        			    			        	  series: [{label: 'Scheduled', color: 'darkorange'},
        					                                {label: 'Present', color: '#839557'}],
        			   			              
        			   			              legend:{
        			   			            	    renderer: $.jqplot.EnhancedLegendRenderer,
        			   			            	    show:true, 
        			   			            	    //labels:['Present','Absent'],
        			   			            	    placement:'outsideGrid',
        			   			            	    
        			   			            	    location:'e',
        			   			            	    marginBottom :'2px',
        			   			            	    border:'none'
        			   			            	},
        			    			              seriesDefaults: {
        			    			                  renderer:$.jqplot.BarRenderer,
        			    			                  pointLabels: { show: true 
        			    			                      },
        			    			                  
        			    			              },
        			    			              axes: {
        			    			                  xaxis: {
        			    			                      renderer: $.jqplot.CategoryAxisRenderer,
        			    			                      labelRenderer: $.jqplot.CanvasAxisLabelRenderer
        			    			                  }
        			    			              },
        			    			              highlighter: {
      								            	tooltipAxes: 'y',
      								                show: false,
	      								                sizeAdjust: 7.5
	      								          },
	  								              cursor: {
	  								                show: false
	  								             
	  								              }
        									});
    							/*var temp = {title: {
    						           // fontFamily: '"Comic Sans MS", cursive',
    						           // fontSize: '18pt',
    						            textColor: '#0c35af'
    						        }
    								};
    							plot_shiftWise.themeEngine.newTheme('temp', temp);
    							plot_shiftWise.activateTheme('temp');*/
    						$('#chartShift').hide();
    						$('#chartShift').show();
        					$('#chartShiftLoader').hide();
        					$('#chartShiftNodata').hide();
    					}else{
        					$('#chartShiftLoader').hide();
        				//	$('#chartShift').hide();
        					$('#chartShiftNodata').show();
    					}
    				},
    				function() {
    					$('#chartShiftLoader').hide();
    					$('#chartShiftNodata').show();
    				});
        $scope.goButton = false;
    };
    
    $scope.searchWorkers = function(type){
    	//alert(type);
    	var status = '';
    	if(type != 'New Joiners' && type != 'Separated Workers' ){
    		type = "I";
    		$cookieStore.put('dashboardWorkerType',type);
        	//$cookieStore.put('dateRange',range);
        	////myservice.dashboardWorkerType = dashboardWorkerType;
        	//myservice.range = range;
        	$location.path('/workerSearch');
    	}
    	
    	
    	
    	
    	
      //  $scope.getData('workerController/workerGridDetails.json', { customerId: $cookieStore.get('customerId'), companyId: $cookieStore.get('companyId'), vendorId: "" ,status: status, workerCode:  "", workerName:  "" }, 'search');

    }

	$scope.urlNavigation = function(url){
    	//alert(url+"url");
    	$location.path(url);
    }

    
}]
).directive('onLastRepeat', function () {
    return function (scope, element, attrs) {
        if (scope.$last) {
            setTimeout(function () {            	
            		$('table.dashboardtables').DataTable({	                	
	                	scrollX : true,
	                	scrollY : '300',
	                	fixedHeader : true,
	                	sort : false
	                });            	
            }, 10);
        }
    };
});



