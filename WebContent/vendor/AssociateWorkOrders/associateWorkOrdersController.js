'use strict';

associateWorkOrder.controller("associateWorkOrderAddViewDtls", ['$scope', '$rootScope', '$http', '$filter', '$resource','$location','$routeParams','$cookieStore', '$window', function($scope,$rootScope, $http,$filter,$resource,$location,$routeParams,$cookieStore,$window) {
		
	if($routeParams.id > 0){
		
	}else{
		$location.path('/associateWorkOrdersearch/'); 
	}

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
    
    $scope.getPostData = function (url, data, type) {
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
	    	} else if( type == 'getMasterData' ){		      		
	      		$scope.list_workrorderDetails = response.data.workOrderList;
	      		$scope.workOrder=response.data.associateWorkOrder[0];
	      		$scope.workOrder.customerName = response.data.associateWorkOrder[0].customerName;
      			$scope.workOrder.companyName =response.data.associateWorkOrder[0].companyName;
  				$scope.workOrder.vendorName =response.data.associateWorkOrder[0].vendorName;
  				
	      		if(Object.prototype.toString.call($scope.workOrderList) !== '[object Array]' ||  $scope.workOrderList.length == 0 ){
	    	   		$scope.workOrderList = [];
	    	   	}
	      		angular.forEach(response.data.associateWorkOrder, function(value, key){	
	      			if(value.workrorderDetailId > 0){
		      			$scope.workOrderList.push({	    	
				    		'workOrderCode' : value.workOrderCode,
				        	'workrorderDetailId' : value.workrorderDetailId ,
				    		'orderPeriodFrom' : value.orderPeriodFrom,
				    		'orderPeriodTo' : value.orderPeriodTo   		
				    	});	
	      			}
				   });	
	      	// for button views
	      		if($scope.buttonArray == undefined || $scope.buttonArray == '')
	      		$scope.getPostData('CommonController/getUserScreenButtons.json', { userName: $cookieStore.get('userName'), screenName:'Associate Work Order'}, 'buttonsAction');
	      		
	      	}else if(  type ==  'workorderChange'){
	      		$scope.orderPeriodFrom = (response.data.orderPeriodFrom == null || response.data.orderPeriodFrom == 'null' || response.data.orderPeriodFrom == '') ? '' : response.data.orderPeriodFrom;
	      		$scope.orderPeriodTo = (response.data.orderPeriodTo == null || response.data.orderPeriodTo == 'null' || response.data.orderPeriodTo == '') ? '' :  response.data.orderPeriodTo;
	      	}else if(  type ==  'save'){
	      		$scope.list_workrorderDetails = response.data;
	      		$scope.Messager('success', 'success', 'Associate Work Order Saved Successfully',true);
	      	}else if(  type ==  'update'){
	      		$scope.list_workrorderDetails = response.data;
	      		$scope.Messager('success', 'success', 'Associate Work Order Updated Successfully',true);
	      	}else if(  type ==  'delete'){
	      		$scope.list_workrorderDetails = response.data;
	      		$scope.Messager('success', 'success', 'Associate Work Order Deleted Successfully',true);
	      	}
	      })
	  };
    
	  $scope.getPostData("associateWorkOrderController/getAssociateMasterData.json",{ vendorDetailsId : $routeParams.id }, "getMasterData" );
	  
	  
	  $scope.fun_workorderChangeListener = function(){
		  if( $scope.workrorderDetailId != undefined &&  $scope.workrorderDetailId != '')
			  $scope.getPostData("associateWorkOrderController/workorderChangeListener.json",{ workrorderDetailId : $scope.workrorderDetailId }, "workorderChange" );
	  }
	  
	 /* $scope.saveAssociateWorkOrderDetails = function(){
		  
			if($('#associateWorkOrederForm').valid()){  
				$scope.workOrder.associateWorkOrderVo = $scope.workOrderList;
				$scope.workOrder.createdBy = $cookieStore.get('createdBy');
		        $scope.workOrder.modifiedBy = $cookieStore.get('modifiedBy');				
				$scope.getPostData("associateWorkOrderController/saveAssociateWorkOrder.json", angular.toJson($scope.workOrder), "save" );
			}
	  }*/
	  
	  
	  $scope.add_workOrderRecord = function(){
	   		$scope.clearPopupFields();
			$scope.popUpSave = true;
			$scope.popUpUpdate = false;
	   }
	   
	   
	   $scope.clearPopupFields = function(){
	   		$scope.workOrderCode = "";
	   		$scope.workOrderName = "";
			$scope.orderPeriodFrom= "";
			$scope.orderPeriodTo= "";		
	   }
	   
	   $scope.edit_workOrderRecord = function($event){  		  	 
	   	$scope.order_index = $($event.target).parent().parent().index();    	    	
	   	var data = $scope.workOrderList[$scope.order_index];  	  
	   		$scope.workOrderCode = data.workOrderCode;
			$scope.workrorderDetailId = data.workrorderDetailId;
			$scope.orderPeriodFrom= data.orderPeriodFrom;
			$scope.orderPeriodTo= data.orderPeriodTo;
			$scope.popUpSave = false;
			$scope.popUpUpdate =true; 
			
			var dataValidation = false;
			angular.forEach($scope.list_workrorderDetails, function(value, key){	
      			if(value.workOrderName == data.workOrderCode){
      				dataValidation = true;      				
      			}
			   });
			if(!dataValidation){
				$scope.list_workrorderDetails.push({
					   'workOrderName' :  data.workOrderCode ,
					   'workrorderDetailId' :data.workrorderDetailId
				});
   			}
	   	 	$('div[id^="myModal"]').modal('show');
	   }
	   
	  
	   $scope.save_workOrderRecord =function($event){  
	   	if(Object.prototype.toString.call($scope.workOrderList) !== '[object Array]' ||  $scope.workOrderList.length == 0 ){
	   		$scope.workOrderList = [];
	   	}	   	
	   	if($('#workOrderPopUp').valid()){   	   		
		    	$scope.workOrderList.push({	    	
		    		'workOrderCode' : $('#workrorderDetailId option:selected').html() != 'Select' ? $('#workrorderDetailId option:selected').html() : '',
		        	'workrorderDetailId' : $scope.workrorderDetailId ,
		    		'orderPeriodFrom' : $scope.orderPeriodFrom,
		    		'orderPeriodTo' : $scope.orderPeriodTo   		
		    	});
		    	$scope.clearPopupFields();
		    	$('div[id^="myModal"]').modal('hide');
		    	$scope.workOrderSave = $scope.workOrder;
		    	$scope.workOrderSave.workrorderDetailId = $scope.workrorderDetailId;
		    	$scope.workOrderSave.action = 'save';
				$scope.workOrderSave.createdBy = $cookieStore.get('createdBy');
		        $scope.workOrderSave.modifiedBy = $cookieStore.get('modifiedBy');				
				$scope.getPostData("associateWorkOrderController/saveAssociateWorkOrder.json", angular.toJson($scope.workOrderSave), "save" );
			
		    }    	
	   }
	   
	   $scope.update_workOrderRecord =function(){		   	
	   	if($('#workOrderPopUp').valid()){ 	   		
	   		$scope.workOrderList.splice($scope.order_index,1);		    	
		    	$scope.workOrderList.push({
		    		'workOrderCode' :$('#workrorderDetailId option:selected').html() != 'Select' ? $('#workrorderDetailId option:selected').html() : '',
		        	'workrorderDetailId' : $scope.workrorderDetailId ,
		    		'orderPeriodFrom' : $scope.orderPeriodFrom,
		    		'orderPeriodTo' : $scope.orderPeriodTo    			      		
		    	});		    
		    	$scope.clearPopupFields();
		    	$('div[id^="myModal"]').modal('hide');
		    	$scope.workOrderUpdate = $scope.workOrder;
		    	$scope.workOrderUpdate.oldWorkrorderDetailId = $scope.workrorderDetailId;
		    	$scope.workOrderUpdate.action = 'update';
				$scope.workOrderUpdate.createdBy = $cookieStore.get('createdBy');
		        $scope.workOrderUpdate.modifiedBy = $cookieStore.get('modifiedBy');				
				$scope.getPostData("associateWorkOrderController/saveAssociateWorkOrder.json", angular.toJson($scope.workOrderUpdate), "update" );
		    	
	   	}	    		   
	   }
	   
	   $scope.delete_workOrderRecord = function($event){
		   if($window.confirm('Are you sure you want to delete this record ?')){			    	    
			   	var data = $scope.workOrderList[$($event.target).parent().parent().index()]; 			   	
	    		$scope.workOrderList.splice($($event.target).parent().parent().index(),1);
	    		$scope.workOrderDelete = $scope.workOrder;
		    	$scope.workOrderDelete.workrorderDetailId = data.workrorderDetailId;
		    	$scope.workOrderDelete.action = 'delete';
	    		$scope.getPostData("associateWorkOrderController/saveAssociateWorkOrder.json", angular.toJson($scope.workOrder), "delete" );
		   }
	   }
	   
    
	 
}]);
