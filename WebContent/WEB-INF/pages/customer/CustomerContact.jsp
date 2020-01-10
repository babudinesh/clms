<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <!-- Material Design fonts -->
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:300,400,500,700" type="text/css" />
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/Resources/font-awesome-4.6.3/css/font-awesome.css" rel="stylesheet" />

    <!-- Bootstrap -->
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet" />

    <!-- Bootstrap Material Design -->
    <link href="${pageContext.request.contextPath}/Resources/Material-Deisgn/dist/css/bootstrap-material-design.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/Resources/Material-Deisgn/dist/css/bootstrap-material-design-inverse.css" rel="stylesheet" />

    <link href="${pageContext.request.contextPath}/Resources/Material-Deisgn/dist/css/ripples.min.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/Resources/Material-Deisgn/dist_select/css/bootstrap-select.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/Resources/Material-Deisgn/dist_Datepicker/css/bootstrap-material-datetimepicker.css" rel="stylesheet" />
    
	<link href="https://cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css" rel="stylesheet" />

	<link href="${pageContext.request.contextPath}/css/customerAddress.css" rel="stylesheet" />
	<link href="${pageContext.request.contextPath}/Resources/Toaster/toastr.css" rel="stylesheet" />

    <!-- jQuery -->
    <script src="${pageContext.request.contextPath}/Resources/JQUERY/jquery-2.2.1.js"></script>
    <script src="${pageContext.request.contextPath}/Resources/bootstrap-3.3.6-dist/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/Resources/Material-Deisgn/dist/js/material.js"></script>
    <!--<script src="Resources/Material-Deisgn/dropdown.js-master/jquery.dropdown.js"></script>-->
    <script src="${pageContext.request.contextPath}/Resources/Material-Deisgn/dist_select/js/bootstrap-select.js"></script>
    <script type="text/javascript" src="http://momentjs.com/downloads/moment-with-locales.min.js"></script>
    <script src="${pageContext.request.contextPath}/Resources/Material-Deisgn/dist_Datepicker/js/bootstrap-material-datetimepicker.js"></script>

	<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/Resources/Angular/angular.min.js"></script> --%>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.5/angular.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/angular-messages/1.5.6/angular-messages.min.js"></script> 
    <script type="text/javascript" src="${pageContext.request.contextPath}/Resources/Angular/ngStorage.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/customer_contact.js"></script>
	<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/Resources/Toaster/jquery.toaster.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/Resources/Toaster/toastr.js"></script>

	<script type="text/javascript" src="${pageContext.request.contextPath}/js/Form_Valiation.js"></script>

    <script type="text/javascript">
    function AddContacts() {
    	clearForm();																
        $('.ContactAdd').show();          
        $('#ddl_Address').focus();
        $("html, body").animate({ scrollTop: $(document).height() - $(window).height() });
       
    };
    function clearForm(){
    	var scope = angular.element(document.getElementById('Contact_app')).scope();
    	setTimeout(function(){												
			scope.$apply(function(){
				//scope.submitted = false;
				scope.contactName = '';
				scope.contactNameOtherLanguage = '';
				scope.mobileNumber = '';
				scope.businessPhoneNumber = '';
				scope.extensionNumber = '';
				scope.emailId = '';
				scope.contactTypeId = "";
				scope.addressId = '';
				scope.address = '';
				scope.isActive = 'Y';
				scope.transactionDate = moment().format('DD/MM/YYYY');;

			});
		},100);
		setTimeout(function() {		
				$('.selectaddresspicker,.selectcontacttypepicker').selectpicker('refresh');
			    $('.selectaddresspicker,.selectcontacttypepicker').selectpicker('deselectAll');
		     }, 500);
    }

    function onCancel(){
			clearForm();		
			$('.ContactAdd').hide();
			$('#example').focus();						
	}
    

    $(document).ready(function () {   
        //initialsie the material-deisgn
        $.material.init();
        $('.ContactAdd').hide();
        
        $('#txt_effectivedate').bootstrapMaterialDatePicker
		({
		    time: false,
		    Button: true,
		    format: "DD/MM/YYYY"
			   
		});

    });
    </script>
</head>
<body>

    <div class="navbar navbar-default" style="margin-bottom: 0px; padding-left: 10px;">
        <div class="pull-left">
            <h3 style="margin-bottom: 0px; margin-top: 0px; padding-top: 10px;">Contract Labour Management System</h3>
        </div>
        <div class="pull-right" style="line-height: 10px;">
            <div style="display: inline-block; padding-top: 0px; margin-right: 30px; text-align: right">
                <ul>
                    <li class="dropdown" style="display: block">
                        <a href="#" data-target="#" class="dropdown-toggle" data-toggle="dropdown" style="color: white">
                            <i class="fa fa-circle fa-3x" aria-hidden="true"></i>
                            <div class="fa fa-user fa-2x cls_iconuser" aria-hidden="true">
                            </div>
                        </a>
                        <ul class="dropdown-menu" style="margin-left: -100px;">
                            <li><a href="javascript:void(0)">Logout</a></li>
                            <!--<li class="divider"></li>-->
                            <!--<li class="dropdown-header">Dropdown header</li>
                            <li><a href="javascript:void(0)">Separated link</a></li>
                            <li><a href="javascript:void(0)">One more separated link</a></li>-->
                        </ul>
                    </li>
                </ul>
                <p style="z-index: 1; position: relative; margin-right: -25px; max-width: 500px; margin-top: -5px;">John Abraham</p>
            </div>
            <!--<div style="display: inline-block; margin-right: 15px;margin-left:-25px;">
                <i class="fa fa-caret-down" aria-hidden="true"></i>
            </div>-->
            <!--<span style="/*background-color:#d7eeec;border-radius:100px;padding:10px;margin-right:10px;margin-top:-15px;padding-top:15px;*/"><i class="fa fa-user fa-2x" aria-hidden="true"></i></span>-->

            <!--<div style="display:inline-block"><span style="padding-top:10px;"><i class="fa fa-question-circle fa-2x" aria-hidden="true"></i></span> </div>-->
        </div>
    </div>
    <div class="navbar navbar-default" style="margin-top: -28px;">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle pull-left" data-toggle="collapse" data-target=".navbar-responsive-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <!--<a class="navbar-brand" href="javascript:void(0)">Brand</a>-->
            </div>
            
            <div class="navbar-collapse collapse navbar-responsive-collapse">
                <ul class="nav navbar-nav">
                    <li class="dropdown active">
                        <a href="javascript:void(0)" data-submenu data-toggle="dropdown">Organization
                        <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li class="dropdown-submenu active">
                                <a href="javascript:void(0)">Customer</a>
                                <ul class="dropdown-menu" style="top: 10px;">
                                    <li class="dropdown-submenu active"><a
											href="javascript:void(0)">Customer</a>
											<ul class="dropdown-menu" style="top: 10px;">
												<li class="active"><a href="${pageContext.request.contextPath}/CustomerController/CustomerList.view">Customer Details</a></li>
												<li><a href="${pageContext.request.contextPath}/CustomerController/CustomerAddressList.view"> Contact </a></li>												
											</ul></li>
										<li class="dropdown-submenu"><a href="javascript:void(0)">Company</a>
											<ul class="dropdown-menu" style="top: 45px;">
												<li><a href="${pageContext.request.contextPath}/CompanyController/companyDetails.view">Company Details</a></li>
												<li><a href="${pageContext.request.contextPath}/CompanyController/CompanyAddressList.view"> Contact </a></li>												
											</ul></li>
                            <li><a href="javascript:void(0)">Location</a></li>
                            <li><a href="javascript:void(0)">Departments</a>
                            </li>
                            <li><a href="javascript:void(0)">Job Codes</a></li>
                            <!--<li class="divider"></li>-->
                            <!--<li class="dropdown-header">Dropdown header</li>
                            <li><a href="javascript:void(0)">Separated link</a></li>
                            <li><a href="javascript:void(0)">One more separated link</a></li>-->
                        </ul>
                    </li>
                    <li class="dropdown">
                        <a href="bootstrap-elements.html">Vendor
                        </a>
                    </li>
                    <li><a href="javascript:void(0)">Contract</a></li>
                    <li><a href="javascript:void(0)">Compliances</a></li>
                </ul>
                <form class="navbar-form navbar-left">
                    <div class="form-group">
                    </div>
                </form>
                <ul class="nav navbar-nav navbar-right">
                </ul>
            </div>
        </div>
    </div>
    <div id="Contact_app" ng-app="LMS_App" ng-controller="LMS_Contr" > 

    	<div class="container-fluid " style="margin-bottom: 35px; margin-top: 35px; padding-left: 2%; padding-right: 2%">
            <div class="well">
                <table id="example" class="display" cellspacing="0" width="100%">
                    <thead>
                        <tr>
                        	<!-- <th>Transaction Date</th> -->
                            <th>Contact Type</th>
                            <th>Contact Name</th>
                            <th>Phone Number</th>
                            <th>Business number</th>
                            <th>Extension</th>
                            <th>Email Id </th>
                            <th>Action</th>
                        </tr>
                    </thead>
                </table>
            </div>
            <div class="pull-right" style="padding-right: 30px">
                <a href="javascript:void(0)" class="btn btn-raised btn-primary pull-right" onclick="AddContacts()" class= "clearFields">Add Contact</a>
            </div>
         </div> 
        
       
       <div class="container-fluid ContactAdd" style="margin-bottom: 35px;">
       
        <form name = "customerContact" novalidate ng-submit="submit" id = "contact" class="form-horizontal well CustomerContactForm" style="background-color: white; padding: 15px; padding-top: 35px; width: 95%; margin: 0px auto; padding-bottom: 0px;">
            <fieldset style="padding-left: 3%;">
                <legend style="margin-bottom: 0px;"></legend>
                <input type="hidden" value="0" ng-model="customerId" class="customerId">
                
                 <div class="container-fluid">
		            <div class="form-group col-lg-4 col-md-4 col-xs-12 col-sm-12" style="margin-top: -20px;">
		                <label class="control-label required" for="focusedInput1" style="color: black;">Transaction Date </label>
		                <input class="form-control" id="txt_effectivedate" type="text" placeholder="Pick a date" style="color: black;"  ng-model="transactionDate" name = "transactionDate" ng-required="true"/>
		                <div ng-messages="customerContact.transactionDate.$error" ng-if='submitted && customerContact.transactionDate.$invalid' role="alert">
      						<div ng-message="required" class="errorMessage">Please Select Transaction Date </div>
      					</div>
		            </div>
          			 <!--  {{transactionDate}} -->
       		 	</div>
            	
            	
                <hr class="pull-left" style="width: 100%;" />
                
                
                <div class="form-group required col-lg-5 col-md-5 col-xs-12 col-sm-12" style="margin-top: -30px; margin-bottom: 12px;">
                    <label class="control-label required" for="focusedInput1">Address </label>
                    <select id="ddl_Address" class="form-control selectpicker selectaddresspicker" title="Select Address" ng-model="addressId" name="addressId"  ng-required="true">                  
                    	<option ng-repeat="address in addressListData" value="{{address.id}}">{{address.name}}</option>
                    </select>
                    <div ng-messages="customerContact.addressId.$error" ng-if='submitted && customerContact.addressId.$invalid' role="alert">
    					<div ng-message="required" class="errorMessage"> Select Address </div>
					</div>
                </div>
                
                <div class="form-group label-floating col-lg-5 col-md-5 col-xs-12 col-sm-12 col-lg-offset-2 col-md-offset-2" style="margin-top: 40px;">
                </div> 
                
                
                <hr class="pull-left" style="width: 100%;" />
                
                
                <div class="form-group col-lg-5 col-md-5 col-xs-12 col-sm-12"  style="margin-top: -40px;">
                    <label class="control-label required" for="focusedInput1">Contact Type </label>
                    <select id="ddl_ContactType" class="form-control selectpicker selectcontacttypepicker" title="Select Contact"  ng-model="contactTypeId" name="contactTypeId" ng-required="true">                   
                    	<c:forEach items="${contactTypes}" var="contacttype" varStatus="row"> 
                        	<option  value="${contacttype.contactTypeId } " > ${contacttype.contactTypeName } </option>
                     	</c:forEach>
                    </select>  
                    <div ng-messages="customerContact.contactTypeId.$error" ng-if='submitted && customerContact.contactTypeId.$invalid' role="alert">
    					<div ng-message="required" class="errorMessage"> Select Contact Type </div>
					</div>   
                </div>
                
                <div class="form-group required label-floating col-lg-5 col-md-5 col-xs-12 col-sm-12 col-lg-offset-2 col-md-offset-2" style="margin-top: 0px;">
                    <label class="control-label required" for="focusedInput1">Contact Name </label>
                    <input class="form-control clear" id="txtContactName" name="txtContactName" ng-model="contactName" type="text" style="width:100%"  ng-maxlength="45" ng-required="true"/>
                    <div ng-messages="customerContact.txtContactName.$error" ng-if='(submitted && customerContact.txtContactName.$invalid) || (customerContact.txtContactName.$touched)' role="alert">
    						<div ng-show="customerContact.txtContactName.$error.required" class="errorMessage"> Please enter name</div> 
    						<div ng-show="customerContact.txtContactName.$error.maxlength" class="errorMessage"> Must contain atleast 10 characters </div>
    						<div ng-show="customerContact.txtContactName.$error.minlength" class="errorMessage"> This field allows maximium 45 characters </div>
      				</div>
                </div>
                
                
                <hr class="pull-left" style="width: 100%;" />
                
 
                <div class="form-group label-floating col-lg-5 col-md-5 col-xs-12 col-sm-12" style="margin-top: 0px;">
                    <label class="control-label" for="focusedInput1">Business Phone Number</label>
                    <input class="form-control clear" id="txtBusinessPhoneNumber" ng-model="businessPhoneNumber" name="txtBusinessPhoneNumber" type="text" onkeypress='return event.charCode >= 48 && event.charCode <= 57' ng-maxlength ="12" style="width:100%" />
                	
                </div>  
                
                <div class="form-group required label-floating col-lg-5 col-md-5 col-xs-12 col-sm-12 col-lg-offset-2 col-md-offset-2" style="margin-top: 0px; margin-bottom: 0px;">
                    <label class="control-label" for="focusedInput1 required">Mobile Number</label>
                    <input class="form-control clear" id="txtMobileNumber" ng-model="mobileNumber" name="txtMobileNumber"  type="text" onkeypress='return event.charCode >= 48 && event.charCode <= 57' ng-minlength ="10" ng-maxlength="12"  style="width:100%" ng-required="true" />
                    <div ng-messages="customerContact.txtMobileNumber.$error" ng-if='(submitted && customerContact.txtMobileNumber.$invalid) || (customerContact.txtMobileNumber.$touched)' role="alert">
    					<div  ng-show="customerContact.txtMobileNumber.$error.required"  class="errorMessage"> Please enter mobile number</div> 
    					<div  ng-show="customerContact.txtMobileNumber.$error.minlength" class="errorMessage"> Mobile number should have atleast 10 digits </div>
    					<div  ng-show="customerContact.txtMobileNumber.$error.maxlength" class="errorMessage"> This field allows maximium 12 digits  </div>
      				</div> 
                </div>
                
                <div class="form-group label-floating col-lg-5 col-md-5 col-xs-12 col-sm-12" style="margin-top: 0px; margin-bottom: 0px;">
                    <label class="control-label" for="focusedInput1">Extension</label>
                    <input class="form-control clear" id="txtExtensionNumber" name="txtExtensionNumber" ng-model="extensionNumber" type="text" onkeypress='return event.charCode >= 48 && event.charCode <= 57' ng-maxlength ="5" style="width:100%" />
                </div>     
                
                <div class="form-group label-floating col-lg-5 col-md-5 col-xs-12 col-sm-12 col-lg-offset-2 col-md-offset-2" style="margin-top: 0px; margin-bottom: 0px;">
                </div>
                
                  
                <hr style="width: 100%" />  
                
                
                <div class="form-group required label-floating col-lg-5 col-md-5 col-xs-12 col-sm-12" style="margin-top: 0px;">
                    <label class="control-label" for="focusedInput1 required">Email Id </label>
                    <input class="form-control clear" id="txtEmailId" ng-model="emailId"  name="txtEmailId"  type="email" style="width:100%" ng-maxlength="45" required />
                    <div ng-messages="customerContact.txtEmailId.$error" ng-if='(submitted && customerContact.txtMobileNumber.$invalid) || (customerContact.txtMobileNumber.$touched)' role="alert">
    					<div  ng-show="customerContact.txtEmailId.$error.required"  class="errorMessage"> Please enter Email Id</div> 
    					<div  ng-message="email" class="errorMessage"> Please enter valid Email Id</div> 
    					<div  ng-show="customerContact.txtEmailId.$error.maxlength" class="errorMessage"> This field allows maximium 45 characters </div>
      				</div> 
                </div> 
                               
                <div class="form-group required col-lg-5 col-md-5 col-xs-12 col-sm-12 col-lg-offset-2 col-md-offset-2 cls_divradios" style="margin-top: -25px;">
                        <label class="col-lg-4 col-md-4 col-sm-5 col-xs-6 control-label cls_lblradios required">Is Active</label>
                        <div class="col-lg-8 col-md-8 col-sm-7 col-xs-6"
							style="margin-top: 15px;">
							<div class="radio radio-primary" style="display: inline-block">
								<label> <input type="radio" name="isActive"  ng-required="true" id="optionsRadios1" value="Y" ng-model="isActive"   ng-checked="isActive == 'Y'">
									Yes
								</label>
							</div>
							<div class="radio radio-primary" style="display: inline-block">
								<label> <input type="radio" name="isActive"  ng-required="true" id="optionsRadios2" value="N" ng-model="isActive"   ng-checked="isActive == 'N'"> No
								</label>
							</div>
							 <!--  {{isActive}}  -->
						<div ng-messages="customerContact.isActive.$error" ng-if='submitted && customerContact.isActive.$invalid' role="alert">
      							<div ng-message="required" class="errorMessage"> Please Select isActive </div>      														
      					</div>
                    </div>
						
                
                <hr style="width: 100%" />
                
                
                <div class="form-group col-xs-12">
                    <br />
                    <div class="text-right">
                        <button class="btn btn-raised btn-primary" type="submit" ng-click="submitted=true; customerContact.$valid && fun_savecustomercontactdetails($event)" onclick="return false;">Save</button>
                        <!-- <a href="javascript:void(0)" class="btn btn-raised btn-primary" ng-click="fun_savecustomercontactdetails($event)">Save & Proceed</a> -->
                        <a href="javascript:void(0)" class="btn btn-raised btn-danger clearFields" onclick="onCancel()">Cancel</a>
                    </div>
                </div>
                
            </fieldset>
        </form>
       
    </div>
 
</div>

    <div class="container-fluid">
        <div class="navbar navbar-default navbar-fixed-bottom text-center" style="min-height: 15px; padding: 0px;">
            <div class="container text-right" style="">
                <label class="control-label text-center" style="color: white; margin: 0px; font-size: 14px;">@Copyright</label>
            </div>
        </div>
    </div>
</body>
</html>
