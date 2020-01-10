'use strict';


ShiftPatternController.controller("ShiftPatternAddCtrl",['$scope','$compile','$http','$resource','$location','$filter','$routeParams','myservice','$cookieStore',function($scope,$compile, $http, $resource, $location, $filter,$routeParams, myservice, $cookieStore) {
		$.material.init();
		
		/*$('#transactionDate').bootstrapMaterialDatePicker({
			time : false,
			clearButton : true,
			format : "DD/MM/YYYY"
		});*/
		$('#transactionDate').datepicker({
		      changeMonth: true,
		      changeYear: true,
		      dateFormat:"dd/mm/yy",
		      showAnim: 'slideDown'
		    	  
		    });
		var TableData = new Array();
		var TableData1 = new Array();
		$scope.cancelBtnActive = false;
		
		
		var flag = false;
		$scope.patternChange = function() {
					
			$scope.patternValue = [];
			
				if(!flag){
				$scope.shift = new Object();
				$scope.pattern.totalPatternHours = 0;
				$scope.value = new Object();
				}			
			var i = 0;
				if($scope.definePatternBy == 1){
					$scope.patternValue.push("Daily");
				}else{
					for (i = 1; i <= $scope.definePatternBy; i++) {
						var data = "";
						if([1,8,15,22,29].indexOf(i) >= 0){
							data = "SUNDAY";
						}else if([2,9,16,23,30].indexOf(i) >= 0){
							data = "MONDAY";
						}else if([3,10,17,24,31].indexOf(i) >= 0){
							data = "TUESDAY";
						}else if([4,11,18,25].indexOf(i) >= 0){
							data = "WEDNESDAY";
						}else if([5,12,19,26].indexOf(i) >= 0){
							data = "THURSDAY";
						}else if([6,13,20,27].indexOf(i) >= 0){
							data = "FRIDAY";
						}else if([7,14,21,28].indexOf(i) >= 0){
							data = "SATURDAY";
						}			
						$scope.patternValue.push(data);
					
					}
				}
			
			flag= false;
		};
	
		$scope.Week_Days = [
		                     {"id":"1","name" : "MONDAY" },
		                     {"id":"2","name" : "TUESDAY" },
		                     {"id":"3","name" : "WEDNESDAY" },
		                     {"id":"4","name" : "THURSDAY" },
		                     {"id":"5","name" : "FRIDAY" },
		                     {"id":"6","name" : "SATURDAY" },
		                     {"id":"7","name" : "SUNDAY" },
		                     {"id":0,"name" : "None" }
		                     ];
		
		$scope.list_defaultPatternType = [ 
		                                   {"id" : "1","name" : "Daily"}, 
		                                   {"id" : "7","name" : "Weekly"},
		                                   {"id" : "14","name" : "Bi Weekly"}, 
		                                   {"id" : "31","name" : "Monthly"} 
		                                 ];
		
		$scope.list_Of_Occurences = [
		                              {	"id": "1st week of the Month" , "name": "1st week of the Month"},
		                              {	"id": "2nd Week of the Month",  "name": "2nd Week of the Month"},
		                              {	"id": "3rd week of the Month",  "name": "3rd week of the Month"},
									  {	"id": "4th week of the Month",  "name": "4th week of the Month"},
									  {	"id": "5th week of the Month",  "name": "5th week of the Month"},
									  {	"id": "All",  "name": "All"},
									];
	
		if ($routeParams.id > 0) {
			
			$scope.readOnly = true;
			$scope.datesReadOnly = true;
			$scope.updateBtn = true;
			$scope.saveBtn = false;
			$scope.viewOrUpdateBtn = true;
			$scope.correcttHistoryBtn = false;
			$scope.resetBtn = false;
			$scope.gridButtons = false;
			$scope.transactionList = false;
			flag = true;
		} else {
			$scope.readOnly = false;
			$scope.datesReadOnly = false;
			$scope.updateBtn = false;
			$scope.saveBtn = true;
			$scope.viewOrUpdateBtn = false;
			$scope.correcttHistoryBtn = false;
			$scope.resetBtn = true;
			$scope.transactionList = false;
			$scope.gridButtons = true;
			flag = true;
		}
		
		
		
		$scope.updateBtnAction = function(this_obj) {
			$scope.readOnly = false;
			$scope.datesReadOnly = false;
			$scope.updateBtn = false;
			$scope.saveBtn = true;
			$scope.viewOrUpdateBtn = false;
			$scope.correctHistoryBtn = false;
			$scope.resetBtn = false;
			$scope.cancelBtn = true;
			$scope.transactionList = false;
			$scope.pattern.shiftPatternDetailsId = 0;
			$scope.gridButtons = true;
			$scope.transactionDate = $filter('date')(new Date(),"dd/MM/yyyy");
			$('.dropdown-toggle').removeClass('disabled');
		}
	
		$scope.viewOrEditHistory = function() {
			$scope.readOnly = false;
			$scope.datesReadOnly = false;
			$scope.updateBtn = false;
			$scope.saveBtn = false;
			$scope.viewOrUpdateBtn = false;
			$scope.correcttHistoryBtn = true;
			$scope.resetBtn = false;
			$scope.transactionList = true;
			$scope.gridButtons = true;
			flag= true;
			$scope.getData('ShiftPatternController/getShiftPatternTransactionDatesList.json',{companyId : $scope.pattern.companyId,customerId : $scope.pattern.customerId,shiftPatternId : $scope.pattern.shiftPatternId}, 'transactionDatesChange');
			$('.dropdown-toggle').removeClass('disabled');
		}
	
		$scope.cancelBtnAction = function() {
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
			$scope.getData('ShiftPatternController/getShiftPatternById.json',{shiftPatternDetailsId : $routeParams.id, customerId : ''}, 'patternList');
			$scope.cancelBtnActive = true;
		}
		$scope.shift = new Object();
		$scope.assign = new Object();
		
		$scope.enable = false;
		$scope.Messager = function(type, heading, text, btn) {
			$.toast({
				heading : heading,
				icon : type,
				text : text,
				stack : false,
				beforeShow : function() {
					$scope.enable = true;
				},
				afterHidden : function() {
					$scope.enable = false;
				}
			});
		}
	  var p =0;
		$scope.getData = function(url, data, type,buttonDisable) {
			var req = {
				method : 'POST',
				url : ROOTURL + url,
				headers : {
					'Content-Type' : 'application/json'
				},
				data : data
			}
			
			$http(req).then(function(response) {
				if(type == 'buttonsAction'){
					$scope.buttonArray = response.data.screenButtonNamesArray;
				} else if (type == 'patternList') {
					
					
						// alert(JSON.stringify(response.data));
						$scope.patternList = response.data;
						$scope.pattern = response.data.patternVo[0];
						// alert(JSON.stringify($scope.value));
						if ($scope.pattern != undefined) {
							$scope.transactionDate = $filter('date')(response.data.patternVo[0].transactionDate,'dd/MM/yyyy');
							//alert($scope.pattern.definePatternBy)
							$scope.definePatternBy = $scope.pattern.definePatternBy;
							$scope.patternList.companyList = response.data.companyList;
							$scope.patternList.countryList = response.data.countryList;							
							$scope.patternList.shiftCodeList  = response.data.shiftCodeList;
							$scope.patternList.locationList  = response.data.locationList;
							$scope.patternList.plantList  = response.data.plantList;
							$scope.value = new Object();							
							for(var i = 0; i < $scope.pattern.definePatternBy; i++){								
								$scope.value[i] = response.data.patternAssignList[i].shiftDefineId;								
							}		
							if($scope.cancelBtnActive == false)
							$scope.patternChange();
							$scope.cancelBtnActive = false;
							// alert(JSON.stringify($scope.value));							
							$scope.shift = $scope.patternList.patternAssignList;
							$scope.rule = response.data.patternAdditionalList;
							
							// for button views
							  	if($scope.buttonArray == undefined || $scope.buttonArray == '')
								$scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Define Shift Pattern'}, 'buttonsAction');
							  	
							var str = "";						
							for(var i=0;i <$scope.rule.length; i++){								
									str  = str + '<tr class="myrow2"  ><td><select  ng-model="rule['+i+'].shiftWeekDay" name="weekDay" class="form-control" ng-options="item.id as item.name for item in Week_Days " ></select></td><td><select ng-model="rule['+i+'].shiftOccurrence" name="weekDay" class="form-control"  ng-options="item.id as item.name for item in list_Of_Occurences "></select></td><td><select ng-model="rule['+i+'].shiftDefineId" name="shiftCode"  class="form-control" ng-options="item.shiftDefineId as item.shiftCode for item in patternList.shiftCodeList " ></select></td><td><i class="fa fa-trash" ng-click="Delete($event)" ng-show="gridButtons"></t></td></tr>';								
							}
							$('#abc').html($compile(str)($scope));
							m = $scope.rule.length ;

						//	alert(JSON.stringify($scope.rule));
						} else {
							$scope.pattern = new Object();
							$scope.transactionDate = $filter('date')(new Date(),'dd/MM/yyyy');
							$scope.pattern.status = "Y";
							 if( response.data.customerList[0] != undefined && response.data.customerList[0] != "" && response.data.customerList.length == 1 ){                		
			   		                $scope.pattern.customerId=response.data.customerList[0].customerId;		                
			   		                $scope.customerChange();
			   		          }
								
								// for button views
								  	if($scope.buttonArray == undefined || $scope.buttonArray == '')
									$scope.getData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Define Shift Pattern'}, 'buttonsAction');
								  	
							
						}
						
					
					} else if (type == 'customerChange') {
						$scope.patternList.companyList = response.data;
						   if( response.data[0] != undefined && response.data[0] != "" && response.data.length == 1 ){
			   	                $scope.pattern.companyId = response.data[0].id;
								$scope.companyChange();
			   	                }
						   
						   
					} else if (type == 'countryChange') {
						// alert(JSON.stringify(response.data));
						if( response.data.defaultPatternType == 'Daily'){
							response.data.defaultPatternType = "1";
						}else if( response.data.defaultPatternType == 'Weekly'){
							response.data.defaultPatternType = "7";
						}if( response.data.defaultPatternType == 'Monthly'){
							response.data.defaultPatternType = "31";
						}
						$scope.definePatternBy = response.data.defaultPatternType;
						
						$scope.patternChange();
						
						
					} else if (type == 'companyChange') {
						// alert(JSON.stringify(response.data));
						$scope.patternList.countryList = response.data.countryList;
						$scope.patternList.locationList  = response.data.locationList;
						if(response.data.countryList != undefined && response.data.countryList.length == 1){
							$scope.pattern.countryId = response.data.countryList[0].id;
							$scope.countryChange();
						}
						
					} else if (type == 'locationChange') {              	
	             		$scope.patternList.plantList = response.data;
		            }else if (type == "shiftCodeList") {
						//alert(JSON.stringify(response.data));
						$scope.patternList.shiftCodeList = response.data;
					} else if (type == "shiftChange") {
						//alert(JSON.stringify(response.data.shiftDefineVo));
						$scope.shift[buttonDisable] = response.data.shiftDefineVo[0];
						//alert(JSON.stringify(JSON.stringify($scope.shift)));
						var k = parseInt(($scope.shift[buttonDisable].totalHours).split(':')[0]);
						p = 0;
						//alert($('#patternTable tr').length);
						for(i=0;i<$('#patternTable tr').length -1 ;i++){
							
							if($scope.shift[i] != undefined && $scope.shift[i] != '' && $scope.shift[i].totalHours != undefined && $scope.shift[i].totalHours != ''){
								p+=parseInt($scope.shift[i].totalHours);
							}
						}					
						$scope.pattern.totalPatternHours = p;
						
						
					} else if (type == 'saveShiftPattern') {
						 //alert(angular.toJson(response.data));
						if (response.data.id > 0) {
							if($scope.correcttHistoryBtn != undefined && $scope.correcttHistoryBtn == true){
								$scope.Messager('success','success','Shift Pattern Updated Successfully',buttonDisable);
							}else{
								$scope.Messager('success','success','Shift Pattern Saved Successfully',buttonDisable);
								$location.path('/ShiftPatternAdd/'+ response.data.id);
							}
							
						} else if (response.data.id == -1) {
							$scope.Messager('error','Error','Transaction Date should be greater than or equal to company transaction date',buttonDisable);
						} else {
							$scope.Messager('error','Error','Technical Issue Please try after some time',buttonDisable);
						}
					} else if (type == 'transactionDatesChange') {
						// alert(JSON.stringify(response.data));
						var obj = response.data;
						$scope.transactionModel = response.data[(obj.length) - 1].id;
						$scope.transactionDatesList = response.data;
					}
				},
				function() {
					$scope.Messager('error','Error','Technical Issue Please try after some time',buttonDisable);
				});
			}
		
		var patternHours = 0;
		
		
		/*$scope.fun_totalScheduledHours = function(obj){
			alert(patternHours+"-"+obj);
			patternHours = patternHours + obj ;
			$scope.pattern.totalPatternHours = patternHours+$scope.pattern.totalPatternHours;
		}*/
		
		//$scope.pattern =  new Object();
			$scope.getData('ShiftPatternController/getShiftPatternById.json',{shiftPatternDetailsId : $routeParams.id,customerId : $cookieStore.get('customerId')}, 'patternList')
	
			$scope.customerChange = function() {
			// alert($scope.location.customerId);
				$scope.getData('vendorController/getCompanyNamesAsDropDown.json',{customerId : $scope.pattern.customerId,companyId:($cookieStore.get('companyId') != null && $cookieStore.get('companyId') != "") ? $cookieStore.get('companyId') : $scope.pattern.companyId != undefined ? $scope.pattern.companyId : 0}, 'customerChange');
			};
	
			$scope.companyChange = function() {
				$scope.getData("shiftsDefineController/getCountryNamesAsDropDown.json", { customerId : $scope.pattern.customerId,companyId : $scope.pattern.companyId}, "companyChange");	
				// $scope.getData('vendorController/getcountryListByCompanyId.json',{customerId : $scope.pattern.customerId,companyId : $scope.pattern.companyId}, 'companyChange')
				$scope.getData("shiftsDefineController/getShiftDefineGridData.json",{customerId : $scope.pattern.customerId, companyId : $scope.pattern.companyId, status : "Y",shiftCode : '',shiftName : ''}, "shiftCodeList");
			};
		
			$scope.fun_locationChangeListener = function () {
				// alert($scope.pattern.locationDetailsId);
		 		if($scope.pattern != undefined && $scope.pattern.locationDetailsId != undefined && ($scope.pattern.locationDetailsId != '' || $scope.pattern.locationDetailsId >= 0))
		 			$scope.getData('shiftsDefineController/getPlantsList.json', { customerId : $scope.pattern.customerId  , companyId : $scope.pattern.companyId  , locationId: $scope.pattern.locationDetailsId , status: 'Y'}, 'locationChange');
			};
			
			$scope.countryChange = function() {
				// alert(JSON.stringify($scope.plant.countryId));
				$scope.getData("shiftsController/getShiftRecord.json",{customerId : $scope.pattern.customerId,companyId : $scope.pattern.companyId,countryId : $scope.pattern.countryId}, "countryChange");
			};
		
			$scope.shiftChange = function($event,obj) {
			//alert(obj+"::");
				$scope.getData("shiftsDefineController/getShiftRecordByShiftId.json",{shiftId : (obj != undefined  ? obj: '0')  }, "shiftChange", $event);
			};
		
			$scope.save = function($event) {
				
				$('#patternTable .myRow').each(function(row,tr){
					TableData[row] = {'daySequence' :$(tr).find('td').eq(0).html()  // Task No.
					,'weekName' : $(tr).find('td').eq(1).html()
			        , 'shiftDefineId': $(tr).find('td').eq(2).find('select :selected').val().split(':')[1]
					,'shiftCode':$(tr).find('td').eq(2).find('select :selected').html()
					};					
					//$scope.pattern.totalPatternHours = $scope.fun_totalScheduledHours ($(tr).find('td').eq(6).html())
					
				});
				
				$('#additionalTable .myrow2').each(function(row,tr){
						TableData1[row] = {'shiftWeekDay' :$(tr).find('td').eq(0).find('select :selected').html()  // Task No.
				        , 'shiftOccurrence': $(tr).find('td').eq(1).find('select :selected').html()
						,'shiftDefineId':$(tr).find('td').eq(2).find('select :selected').val().split(':')[1]
						,'shiftCode':$(tr).find('td').eq(2).find('select :selected').html()
					};
					
				});
				
				if ($("#shiftPatternForm").valid()) {					
					$scope.pattern.transactionDate =  $('#transactionDate').val();
					$scope.pattern.patternAssignList = TableData;
					$scope.pattern.patternAdditionalList = TableData1;
					$scope.pattern.definePatternBy = $('#defaultPatternBy option:selected').html();
					$scope.pattern.createdBy = $cookieStore.get('createdBy');
					$scope.pattern.modifiedBy = $cookieStore.get('modifiedBy');
					//alert(angular.toJson($scope.pattern));
					$scope.getData('ShiftPatternController/saveShiftPattern.json',angular.toJson($scope.pattern),'saveShiftPattern');
				}
				
			};
	
			$scope.correctHistorySave = function($event) {
				$('#patternTable .myRow').each(function(row,tr){
					TableData[row] = {'daySequence' :$(tr).find('td').eq(0).html()  // Task No.
					,'weekName' : $(tr).find('td').eq(1).html()
			        , 'shiftDefineId': $(tr).find('td').eq(2).find('select :selected').val().split(':')[1]
					,'shiftCode':$(tr).find('td').eq(2).find('select :selected').html()
					};
					//$scope.pattern.totalPatternHours = $scope.fun_totalScheduledHours ($(tr).find('td').eq(6).html())
					
				});
				
				$('#additionalTable .myrow2').each(function(row,tr){
						TableData1[row] = {'shiftWeekDay' :$(tr).find('td').eq(0).find('select :selected').html()  // Task No.
				        , 'shiftOccurrence': $(tr).find('td').eq(1).find('select :selected').html()
						,'shiftDefineId':$(tr).find('td').eq(2).find('select :selected').val().split(':')[1]
						,'shiftCode':$(tr).find('td').eq(2).find('select :selected').html()
					};
					
				});
				if ($('#shiftPatternForm').valid()) {
					$scope.pattern.transactionDate =  $('#transactionDate').val(); 
					$scope.pattern.patternAssignList = TableData;
					$scope.pattern.patternAdditionalList = TableData1;
					$scope.pattern.definePatternBy = $('#defaultPatternBy option:selected').html();
					$scope.pattern.createdBy = $cookieStore.get('createdBy');
					$scope.pattern.modifiedBy = $cookieStore.get('modifiedBy');
					$scope.getData('ShiftPatternController/saveShiftPattern.json',angular.toJson($scope.pattern),'saveShiftPattern');}
			};
	
			$scope.saveChanges = function() {
				$scope.Messager('success', 'success','Address Saved Successfully');
				$('div[id^="myModal"]').modal('hide');
			};
	
			$scope.transactionDatesListChange = function() {
				// alert("--" +$scope.transactionModel);
				$scope.getData('ShiftPatternController/getShiftPatternById.json',{shiftPatternDetailsId : ($scope.transactionModel != undefined || $scope.transactionModel != null) ? $scope.transactionModel: '0',customerId : ""}, 'patternList')
				$('.dropdown-toggle').removeClass('disabled');
				flag= true;
				
			}
	

		var m = 0;
		 $scope.plusIconAction = function(){		
			 m=m+1;
			 var str2= '<tr class="myrow2" ><td><select  ng-model="rule['+m+'].shiftWeekDay" name="weekDay" class="form-control" ng-options="item.id as item.name for item in Week_Days " ></select></td><td><select ng-model="rule['+m+'].shiftOccurrence" name="weekDay" class="form-control"  ng-options="item.id as item.name for item in list_Of_Occurences "></select></td><td><select ng-model="rule['+m+'].shiftDefineId" name="shiftCode"  class="form-control" ng-options="item.shiftDefineId as item.shiftCode for item in patternList.shiftCodeList " ></select></td><td><i class="fa fa-trash" ng-click="Delete($event)" ng-show="gridButtons"></t></td></tr>';
			
			 $('#abc').html($compile(  $('#abc').html()+str2)($scope));
			
		 }
		 
	
		 $scope.Delete = function($event){			
		    	//$scope.patternList.patternAdditionalList.splice($($event.target).parent().parent().index(),1);	    	
		    	$("#abc tr").eq($($event.target).parent().parent().index()).remove();
			}
		 
		 
		 $scope.additionalRulesChnage = function(){
			 
			 if($scope.pattern.hasAdditionalRules == true ){
				 $('#abc').empty();
			 }
		 }
	
} ]);
