'use strict';
var dashBoardControlller = angular.module("myApp.DashboardDetails", ['ngCookies']);

var url_workerCountByAgeGroup = "dashboardController/getWorkerAgeGroup.json";
var url_overTimeHours = "dashboardController/getWorkersOvertime.json";
var url_shiftWise = "dashboardController/getShiftWiseAttendanceDetails.json";
var url_skillWise = "dashboardController/getSkillWiseAttendanceDetails.json";
var url_vendorWise = "dashboardController/getVendorWiseAttendanceDetails.json";
var url_compliance = "dashboardController/getVendorComplianceStatus.json";
var url_workerHeadCount = "dashboardController/getWorkersHeadCount.json";
var url_deptWise = "dashboardController/getDeptWiseAttendanceDetails.json";

dashBoardControlller.service('myservice', function () {
    this.dashboardCompanyId = 0;
    this.dashboardCustomerId = 0;
    this.dashboardVendorId = 0;
    this.dashboardVendorComplianceId = 0;
    this.dashboardWorkerType = null;
    
});

dashBoardControlller.controller("dashboardCtrl", ['$scope','$cookies', '$http', '$resource', '$location','$cookieStore','$filter','myservice', function ($scope, $cookies, $http, $resource, $location,$cookieStore,$filter,myservice) {  
	$scope.workerCountList = new Object();
	$.jqplot.config.enablePlugins = true;
	
	var plot_overTimeHours;
	var plot_shiftWise;
	var plot_skillWise ;
	var plot_vendorWise ;
	var plot_deptWise;
	
	var attendanceList = [],attendanceListtricks = [],overtimeList=[],overtimeListtricks = [];
	
	var cookies = $cookies.getAll();
	var userId = $cookieStore.get('userId');

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
    };
    
    $scope.from = moment().format('YYYY-MM-DD'); //moment().subtract(7, 'days').format('YYYY-MM-DD'),
    $scope.to = moment().format('YYYY-MM-DD');
	   
    $scope.fromDate = moment().format('DD/MM/YYYY'); 
    $scope.toDate = moment().format('DD/MM/YYYY'); 
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
        	if(type == 'HeadCount'){
        		//alert(angular.toJson(response.data));
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
        	}else if(type == 'AgeGroup'){
				// var sampleData = [['16-20',385],['20-30',1708],['30-40',300],['40-50',150],['50-60',25],['60-70',1]];
        		if(response.data != null && response.data.totalWorkerCount != undefined && response.data.totalWorkerCount[0] != 0){
					$scope.totalWorkerCount = response.data.totalWorkerCount[0];
				}else{
					response.data.totalWorkerCount[0] = 0;
				}
				if(response.data != null && response.data.workerCountByAgeGroup != undefined && response.data.workerCountByAgeGroup.length > 0){
					var plot1 = jQuery.jqplot('chartPie', [ response.data.workerCountByAgeGroup ], {
										animate : !$.jqplot.use_excanvas,
										//seriesColors: [ "#4bb2c5", "#c5b47f", "#EAA228", "#579575" ]
										seriesDefaults : {
											renderer : jQuery.jqplot.DonutRenderer, 
											rendererOptions : {
												sliceMargin : 0,
												startAngle : 180,
												showDataLabels : true,
												dataLabels : 'value',
												totalLabel : true,
												//highlightMouseOver: true,
												shadow: true
											}
										},
										legend : {
											show : true,
											location : 'e'
										},
										 highlighter: {
											 	formatString:'%s(%d)', 
								                useAxesFormatters:false,
								                showTooltip: true,
								                show: true,
								                sizeAdjust: 15.5
								            },
								            cursor: {
								                show: false
								            }
								            
										
									});
					/*$('#chartPie').bind('jqplotDataHighlight', function (ev, seriesIndex, pointIndex, data) { 
					    document.getElementById('chartPie').title = data;
					    //alert('series: '+seriesIndex+', point: '+pointIndex+', data: '+data);
					    }); */
					/*$('#chart').bind('jqplotDataClick', function(ev, seriesIndex, pointIndex, data) {
					    alert(plot1.series[seriesIndex].seriesColors[pointIndex]);
					});*/
					$('#chartPieLoader').hide();
					$('#chartPieNodata').hide();
			    	$('#chartPie').show();
				}else{
					$('#chartPieLoader').hide();
					$('#chartPieNodata').hide();
			    	$('#chartPie').show();
				}
        	}else if(type == 'Compliance'){
        		
        		 if(response.data != null && response.data!= null && response.data.length > 0){
        			// alert(angular.toJson(response.data));
   	        	  	$scope.complianceList = response.data;
   	        	  	$('#compliance').show();
   	        	  	$('#complianceNoData').hide();
   	        	  	$('#complianceLoader').hide();
   	          	}else{
   	        	  $('#compliance').hide();
   	        	  $('#complianceNoData').show();
   	        	  $('#complianceLoader').hide();
   	          }
        	}else if(type == 'OTHours'){
        		/*var data = [[266,309,123,120,132,210,214,266,309,123,120,132,210,214,266,309,123,120,132,210,214,266,309,123,120,132,210,214,309,123]];
			    var ticks = ['05/12/2016','06/12/2016','07/12/2016','08/12/2016','09/12/2016','10/12/2016','11/12/2016',
			             '05/12/2016','06/12/2016','07/12/2016','08/12/2016','09/12/2016','10/12/2016','11/12/2016',
			             '05/12/2016','06/12/2016','07/12/2016','08/12/2016','09/12/2016','10/12/2016','11/12/2016',
			             '05/12/2016','06/12/2016','07/12/2016','08/12/2016','09/12/2016','10/12/2016','11/12/2016','10/12/2016','11/12/2016'];*/
        		if(response.data != null && response.data.chartData != undefined && response.data.chartData.length > 0){
					//if (plot_overTimeHours) { 	plot_overTimeHours.destroy(); 	}
					$('#chartOverTime').show();
					plot_overTimeHours = jQuery.jqplot('chartOverTime',  response.data.chartData , {

					//var plot2 = jQuery.jqplot('chartOverTime',  response.data.chartData , {
									animate : !$.jqplot.use_excanvas,
									title:{text:'OT Hours', textColor: '#0c35af'},
									
									axes: {
										xaxis: {
								                renderer: $.jqplot.CategoryAxisRenderer,
								                ticks: response.data.ticks,
								                labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
								                tickRenderer: $.jqplot.CanvasAxisTickRenderer,
								                tickOptions: {
								                labelPosition: 'middle',
								                angle: -75,
								                fontSize: '8pt'
								            }
								        }
									},
									seriesDefaults:{
						            	  showMarker:true,
						                pointLabels: { show: false }
						            },
						            highlighter: {
						            	/*tooltipContentEditor: function (str, seriesIndex, pointIndex) {
						                    return str+",1 "+seriesIndex+",2 "+pointIndex ;
						                },*/
						            	tooltipAxes: 'y',
						                show: true,
						                sizeAdjust: 7.5
						            },
						            cursor: {
						                show: false
						            }
									
								});
					$('#chartOverTimeLoader').hide();
					$('#chartOverTimeNodata').hide();
			    	//$('#chartOverTime').show();
				}else{
					$('#chartOverTimeLoader').hide();
					$('#chartOverTime').hide();
					$('#chartOverTimeNodata').show();
				}
				//$('#chartOverTimeLoader').hide();
        	}else if(type == 'ShiftWise'){
        		/*data = [
		                  [2, 6, 7, 10, 5, 4, 9],
		                  [7, 5, 3, 2, 4, 6, 5]   			                  
                ];
		          ticks = ['A', 'A1', 'B', 'B1', 'C', 'C1', 'G'];*/
				//alert(angular.toJson(response.data));
				//$scope.shiftData = response.data.shiftWiseAttendance;
				/*var chartHeight = (totalVendors * 30);
				$('#chartShiftwise').attr('style', 'height:' + chartHeight + 'px !important');*/
        		if(response.data != null && response.data.shiftWiseAttendance != undefined && response.data.shiftWiseAttendance.length > 0){
					if (plot_shiftWise) { 	plot_shiftWise.destroy(); 	}
					$('#chartShiftwise').show();
					$('#chartShiftwise').html('');
					plot_shiftWise = jQuery.jqplot('chartShiftwise', response.data.shiftWiseAttendance, {
									  	animate : !$.jqplot.use_excanvas,
									  	title:{text:'Shift Wise', textColor: '#0c35af'},
		    			        	
									  	seriesColors:['#eaa228', '#4bb2c5'],
		      			            
									  	series: [{label: 'Scheduled'},
		  		                                {label: 'Present'}],
		   			       
		  		                        seriesDefaults: {
		  		                        		renderer:$.jqplot.BarRenderer,
		  		                        		pointLabels: { show: true },
		  		                        },
		  		                        axes: {
			    			                  xaxis: {
			    			                      renderer: $.jqplot.CategoryAxisRenderer,
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
					$('#chartShiftwise').hide();
					$('#chartShiftwise').show();
					$('#chartShiftwiseLoader').hide();
					$('#chartShiftwiseNodata').hide();
			    	//$('#chartShiftwise').show();
				}else{
					$('#chartShiftwiseLoader').hide();
					$('#chartShiftwise').hide();
					$('#chartShiftwiseNodata').show();
				}
	
		
        	}else if(type == 'SkillWise'){
					if(response.data != null && response.data.skillWiseAttendance != undefined && response.data.skillWiseAttendance.length > 0){
						$('#chartSkillwise').show();
						$('#chartSkillwise').html('');
						plot_skillWise = jQuery.jqplot('chartSkillwise', response.data.skillWiseAttendance, {
											animate : !$.jqplot.use_excanvas,
											title:{ text:'Skill Wise', textColor: '#0c35af' },
											seriesColors:['#eaa228', '#4bb2c5'],
   			            
											series: [{label: 'Scheduled'},
											         {label: 'Present'}],
											seriesDefaults: {
													renderer:$.jqplot.BarRenderer,
													pointLabels: { show: true }
 						
											},
											axes: {
						   			                  xaxis: {
						   			                      renderer: $.jqplot.CategoryAxisRenderer,
						   			                      labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
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
						$('#chartSkillwise').hide();
						$('#chartSkillwise').show();
						$('#chartSkillwiseLoader').hide();
						$('#chartSkillwiseNodata').hide();
						
					}else{
						$('#chartSkillwiseLoader').hide();
						$('#chartSkillwiseNodata').show();
						$('#chartSkillwise').hide();
					}

   				
        	}else if(type == 'VendorWise'){
        		 /*data = [
                	[[2,'Aarti Enterprises'], [4,'Abhijit Enterprises'], [6,'Bharti Enterprises'], [3,'Jay Enterprises']], 
                	[[5,'Aarti Enterprises'], [1,'Abhijit Enterprises'], [3,'Bharti Enterprises'], [4,'Jay Enterprises']]];	
        		  */
        		
        		var totalVendors = response.data.vendorWiseAttendance.length > 0 ? response.data.vendorWiseAttendance[0].length : 1;
        		if(response.data.vendorWiseAttendance.length > 1 && totalVendors < response.data.vendorWiseAttendance[1].length){
        			totalVendors = response.data.vendorWiseAttendance[1].length;
        		}
        		if(totalVendors > 10){
	        		var chartHeight = (totalVendors * 15);
	        		//var chartWidth = ((totalVendors * 20)-20);
	        		$('#chartVendorwise').attr('style', 'height:' + chartHeight + 'px !important;');
        		}
        		//alert(angular.toJson(response.data));
				if(response.data != null && response.data.vendorWiseAttendance != undefined && response.data.vendorWiseAttendance.length > 0){
					//if (plot_vendorWise) { 	plot_vendorWise.destroy(); 	}
					$('#chartVendorwise').show();
					$('#chartVendorwise').html('');
					plot_vendorWise = jQuery.jqplot('chartVendorwise', response.data.vendorWiseAttendance, {
						  				animate : !$.jqplot.use_excanvas,
						  				title:{ text:'Vendor Wise', textColor: '#0c35af'},
							        	  /*seriesColors:['#c5b47f', '#579575'],
							        	  series: [
						                          {label: 'Scheduled', color: '#c5b47f'},
						                          {label: 'Present', color: '#579575'},],*/
							        	seriesColors:['#eaa228', '#4bb2c5'],
							            
							            series: [{label: 'Scheduled'},
						                            {label: 'Present'}],
						                  
						                seriesDefaults: {	                        	  
						                    renderer:$.jqplot.BarRenderer,
						                    pointLabels: { show: true, location: 'w', edgeTolerance: -20 },
						                    shadowAngle: 135,
						                    rendererOptions: {
						                        barDirection: 'horizontal'
						                    }
						                },
		              
						                axes: {
						                	yaxis: {
						                		renderer: $.jqplot.CategoryAxisRenderer,
			                      				//ticks: response.data.ticks
						                	}
						                },
						                highlighter: {
								            	tooltipAxes: 'x',
								                show: false,
								                sizeAdjust: 7.5
								        },
							            cursor: {
							                show: false
							             }
									});
					$('#chartVendorwise').hide();
					$('#chartVendorwise').show();
					$('#chartVendorwiseLoader').hide();
					$('#chartVendorwiseNodata').hide();
			    
				}else{
					$('#chartVendorwiseLoader').hide();
					$('#chartVendorwiseNodata').show();
					$('#chartVendorwise').hide();
				}
        	}else if(type == 'DeptWise'){
        		/*var totalVendors = response.data.vendorWiseAttendance[0].length;
        		if(totalVendors < response.data.vendorWiseAttendance[1].length){
        			totalVendors = response.data.vendorWiseAttendance[1].length;
        		}
	    		var chartHeight = (totalVendors * 15);
	    		$('#chartDeptwise').attr('style', 'height:' + chartHeight + 'px !important');*/
	    		//alert(angular.toJson(response.data));
				if(response.data != null && response.data.deptWiseAttendance != undefined && response.data.deptWiseAttendance.length > 0){
					//if (plot_deptWise) { 	plot_deptWise.destroy(); 	}
					$('#chartDeptwise').show();
					$('#chartDeptwise').html('');
					plot_deptWise = jQuery.jqplot('chartDeptwise', response.data.deptWiseAttendance, {
											animate : !$.jqplot.use_excanvas,
											title:{ text:'Department Wise', textColor: '#0c35af' },
											seriesColors:['#eaa228', '#4bb2c5'],
					       
											series: [{label: 'Scheduled'},
											         {label: 'Present'}],
											seriesDefaults: {
													renderer:$.jqplot.BarRenderer,
													pointLabels: { show: true }
							
											},
											axes: {
						   			                  xaxis: {
						   			                      renderer: $.jqplot.CategoryAxisRenderer,
						   			                      labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
						   			                      tickRenderer: $.jqplot.CanvasAxisTickRenderer,
						   			                      tickOptions: {
											                labelPosition: 'middle',
											                angle: -75,
											                fontSize: '6pt'
						   			                      }
						   			                  }
					   			            },
					   			            highlighter: {
										            	tooltipAxes: 'y',
										                show: false,
										                sizeAdjust: 7.5
										    },
									       /* seriesDefaults: {	                        	  
								                    renderer:$.jqplot.BarRenderer,
								                    pointLabels: { show: true, location: 'w', edgeTolerance: -20 },
								                    shadowAngle: 135,
								                    rendererOptions: {
								                        barDirection: 'horizontal'
								                    }
								            },
				              
								            axes: {
								                	xaxis: {
								                		renderer: $.jqplot.CategoryAxisRenderer,
					                      				//ticks: response.data.ticks
								                	}
								            },*/
								            /*highlighter: {
										            	tooltipAxes: 'x',
										                show: false,
										                sizeAdjust: 7.5
										    },
									        cursor: {
									                show: false
									        }*/
								           
									});
					$('#chartDeptwise').hide();
					$('#chartDeptwise').show();
					$('#chartDeptwiseLoader').hide();
					$('#chartDeptwiseNodata').hide();
			    
				}else{
					$('#chartDeptwiseLoader').hide();
					$('#chartDeptwiseNodata').show();
					$('#chartDeptwise').hide();
				}
        	}
        },function(){
        	 $scope.Messager('error', 'Error', 'Technical Issue Please try after some time');
        	 if(type == 'SkillWise'){
        		 $('#chartSkillwiseLoader').hide();
				 $('#chartSkillwiseNodata').show();
				 $('#chartSkillwise').hide();
        	 }
        	 
        	 if(type == 'ShiftWise'){
        		 $('#chartShiftwiseLoader').hide();
				 $('#chartShiftwiseNodata').show();
				 $('#chartShiftwise').hide();
        	 }
        	 
        	 if(type == 'VendorWise'){
        		 $('#chartVendorwiseLoader').hide();
				 $('#chartVendorwiseNodata').show();
				 $('#chartVendorwise').hide();
        	 }
        	 
        	 if(type == 'DeptWise'){
        		 $('#chartDeptwiseLoader').hide();
				 $('#chartDeptwiseNodata').show();
				 $('#chartDeptwise').hide();
        	 }
        	 
        	 if(type == 'OTHours'){
        		 $('#chartOverTimeLoader').hide();
				 $('#chartOverTime').hide();
				 $('#chartOverTimeNodata').show();
        	 }
        	 
        	 if(type == 'AgeGroup'){
        		 $('#chartPieLoader').hide();
				 $('#chartPieNodata').hide();
			     $('#chartPie').show();
        	 }
        	 
        	 if(type == 'HeadCount'){
        		 $('#workerInfoData').hide();
 				 $('#workerNoData').show();
 				 $('#workerInfoLoader').hide();
        	 }
        	 
        	 if(type == 'Compliance'){
        		 $('#compliance').hide();
  	        	 $('#complianceNoData').show();
  	        	 $('#complianceLoader').hide();
        	 }

        });
    }; 
        
        $scope.getData(url_workerHeadCount, {'customerId' : $cookieStore.get('customerId'),'companyId' : $cookieStore.get('companyId'),"fromDate" : $scope.from, "toDate" : $scope.to,'userId':userId},'HeadCount');
        $scope.getData(url_compliance, {"customerId" : $cookieStore.get('customerId'),"companyId" : $cookieStore.get('companyId'),"fromDate" : $scope.from, "toDate" : $scope.to,"userId":userId},'Compliance');
        $scope.getData(url_workerCountByAgeGroup, {"customerId" : $cookieStore.get('customerId'),"companyId" : $cookieStore.get('companyId'),"fromDate" : $scope.from, "toDate" : $scope.to,"userId":userId},'AgeGroup');
        $scope.getData(url_overTimeHours, {"customerId" : $cookieStore.get('customerId'),"companyId" : $cookieStore.get('companyId'),"fromDate" : moment().subtract(29, 'days').format('YYYY-MM-DD'), "toDate" : moment().format('YYYY-MM-DD'),"userId":userId},'OTHours');
        $scope.getData(url_shiftWise, {"customerId" : $cookieStore.get('customerId'),"companyId" : $cookieStore.get('companyId'),"fromDate" : $scope.from, "toDate" : $scope.to,"userId":userId},'ShiftWise');
        $scope.getData(url_skillWise, {"customerId" : $cookieStore.get('customerId'),"companyId" : $cookieStore.get('companyId'),"fromDate" : $scope.from, "toDate" : $scope.to,"userId":userId},'SkillWise');
        $scope.getData(url_vendorWise, {"customerId" : $cookieStore.get('customerId'),"companyId" : $cookieStore.get('companyId'),"fromDate" : $scope.from, "toDate" : $scope.to,"userId":userId},'VendorWise');
        $scope.getData(url_deptWise, {"customerId" : $cookieStore.get('customerId'),"companyId" : $cookieStore.get('companyId'),"fromDate" : $scope.from, "toDate" : $scope.to,"userId":userId},'DeptWise');
			   			              



  
  //  $scope.goButton = false;
    // Go Button Action 
    $scope.refreshAttendance = function(){
    	$scope.goButton = true;
		var fromDate = moment($('#fromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
		var toDate = moment($('#toDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD');
		/*if($scope.fromDate != 'Invalid date' && $scope.toDate != 'Invalid date' ){
			$scope.loadData(fromDate, toDate);
		}else if(moment(new Date($('#fromdate').val())).isAfter(moment(new Date($('#todate').val())))){
			$scope.Messager('Error','error',' To Date should be greater than From Date.');
			//return;
		}*/
		if((fromDate == null || fromDate == undefined || fromDate == '' || fromDate == 'Invalid Date')&& (toDate == null || toDate == undefined || toDate == '' || toDate == 'Invalid Date')){
			$scope.Messager('error','Error',' Please select From Date and To date');
			//$scope.goButton = false;			
			return;
		}else if((new Date(moment($('#fromDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime() > new Date(moment($('#toDate').val(),'DD/MM/YYYY').format('YYYY-MM-DD')).getTime())){
			$scope.Messager('error','error',' To Date should be greater than From Date.');
			//$scope.goButton = false;			
			return;
		}else{			
		    /*$("#fromDate").datepicker('setDate', new Date());
		    $("#toDate").datepicker('setDate', new Date());
		    fromDate = moment().format('YYYY-MM-DD');
			toDate = moment().format('YYYY-MM-DD');*/
			$scope.loadData(fromDate, toDate);
		}
    };
    
    $scope.loadData = function(fromDate, toDate){
    	$('#chartShiftwiseLoader').show();
    	$('#chartSkillwiseLoader').show();
    	$('#chartVendorwiseLoader').show();
    	$('#chartDeptwiseLoader').show();
    	
    	$('#chartShiftwise').hide();
    	$('#chartSkillwise').hide();
    	$('#chartVendorwise').hide();
    	$('#chartDeptwise').hide();
    	
    	$('#chartShiftwiseNodata').hide();
    	$('#chartSkillwiseNodata').hide();
    	$('#chartVendorwiseNodata').hide();
    	$('#chartDeptwiseNodata').hide();
     
    	$scope.getData(url_shiftWise, {"customerId" : $cookieStore.get('customerId'),"companyId" : $cookieStore.get('companyId'),"fromDate" : fromDate, "toDate" : toDate,"userId":userId},'ShiftWise');
        $scope.getData(url_skillWise, {"customerId" : $cookieStore.get('customerId'),"companyId" : $cookieStore.get('companyId'),"fromDate" : fromDate, "toDate" : toDate,"userId":userId},'SkillWise');
        $scope.getData(url_vendorWise, {"customerId" : $cookieStore.get('customerId'),"companyId" : $cookieStore.get('companyId'),"fromDate" : fromDate, "toDate" : toDate,"userId":userId},'VendorWise');
        $scope.getData(url_deptWise, {"customerId" : $cookieStore.get('customerId'),"companyId" : $cookieStore.get('companyId'),"fromDate" : fromDate, "toDate" : toDate,"userId":userId},'DeptWise');

        
    };
    
    /*$scope.searchWorkers = function(range,type){
    	var status = '';
    	
    	if(type == 'New Joiners'){
    		status = "I";
    	}else if(type == 'Separated Workers'){
    		status = "N";
    	}
    	
    	$cookieStore.put('dashboardWorkerType',status);
    	$cookieStore.put('dateRange',range);
    	//myservice.dashboardWorkerType = dashboardWorkerType;
    	//myservice.range = range;
    	$location.path('/workerSearch');
      //  $scope.getData('workerController/workerGridDetails.json', { customerId: $cookieStore.get('customerId'), companyId: $cookieStore.get('companyId'), vendorId: "" ,status: status, workerCode:  "", workerName:  "" }, 'search');

    }*/
    $scope.searchWorkers = function(type){
    	//alert(type);
    	var status = '';
    	if(type != 'New Joiners' && type != 'Separated Workers' ){
    		type = "I";
    		
    		myservice.dashboardWorkerType = type;
    		$cookieStore.put('dashboardWorkerType',myservice.dashboardWorkerType);
        	//$cookieStore.put('dateRange',range);
        	////myservice.dashboardWorkerType = dashboardWorkerType;
        	//myservice.range = range;
        	$location.path('/workerSearch');
    	}
    };
    /*$scope.searchCompliance = function(vendorName, documentName){
    	//var status = '';
    	
    	if(type == 'New Joiners'){
    		status = "I";
    	}else if(type == 'Separated Workers'){
    		status = "N";
    	}
    	
    	$cookieStore.put('dashboardVendorName',vendorName);
    	$cookieStore.put('dashboardDocumentName',documentName);
    	//myservice.dashboardWorkerType = dashboardWorkerType;
    	//myservice.range = range;
    	$location.path('/VendorComplianceSearch');
      //  $scope.getData('workerController/workerGridDetails.json', { customerId: $cookieStore.get('customerId'), companyId: $cookieStore.get('companyId'), vendorId: "" ,status: status, workerCode:  "", workerName:  "" }, 'search');

    };*/
    
    
    }]);


