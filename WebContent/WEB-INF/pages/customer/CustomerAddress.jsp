<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<!-- Material Design fonts -->
<link rel="stylesheet"
	href="http://fonts.googleapis.com/css?family=Roboto:300,400,500,700"
	type="text/css" />
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/Resources/font-awesome-4.6.3/css/font-awesome.css"
	rel="stylesheet" />

<!-- Bootstrap -->
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet" />

<!-- Bootstrap Material Design -->
<link
	href="${pageContext.request.contextPath}/Resources/Material-Deisgn/dist/css/bootstrap-material-design.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/Resources/Material-Deisgn/dist/css/bootstrap-material-design-inverse.css"
	rel="stylesheet" />

<link
	href="${pageContext.request.contextPath}/Resources/Material-Deisgn/dist/css/ripples.min.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/Resources/Material-Deisgn/dist_select/css/bootstrap-select.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/Resources/Material-Deisgn/dist_Datepicker/css/bootstrap-material-datetimepicker.css"
	rel="stylesheet" />

<link
	href="https://cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/css/customerAddress.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/Resources/Toaster/toastr.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/css/Styles.css"
	rel="stylesheet" />	
<!-- jQuery -->
<script
	src="${pageContext.request.contextPath}/Resources/JQUERY/jquery-2.2.1.js"></script>
<script
	src="${pageContext.request.contextPath}/Resources/bootstrap-3.3.6-dist/js/bootstrap.js"></script>
<script
	src="${pageContext.request.contextPath}/Resources/Material-Deisgn/dist/js/material.js"></script>
<!--<script src="Resources/Material-Deisgn/dropdown.js-master/jquery.dropdown.js"></script>-->
<script
	src="${pageContext.request.contextPath}/Resources/Material-Deisgn/dist_select/js/bootstrap-select.js"></script>
<script type="text/javascript"
	src="http://momentjs.com/downloads/moment-with-locales.min.js"></script>
<script
	src="${pageContext.request.contextPath}/Resources/Material-Deisgn/dist_Datepicker/js/bootstrap-material-datetimepicker.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.5/angular.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular-messages/1.5.6/angular-messages.min.js"></script> 
<script type="text/javascript"
	src="${pageContext.request.contextPath}/Resources/Angular/ngStorage.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/Customer_Address.js"></script>
<script
	src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
<%-- <script type="text/javascript"
	src="${pageContext.request.contextPath}/Resources/Toaster/jquery.toaster.js"></script> --%>
	<script type="text/javascript"
	src="${pageContext.request.contextPath}/Resources/Toaster/toastr.js"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/Form_Valiation.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/Main.js"></script>

<script type="text/javascript">


	$(document).ready(
			function() {

				//initialsie the material-deisgn
				$.material.init();
				// $('.AddressAdd').hide();
			
			$('#txt_effectivedate,#txt_effectivedate1').bootstrapMaterialDatePicker({
					time : false,
					Button : true,
					format : "DD/MM/YYYY"					
				}); 
				
				
			});
</script>
</head>
<body>
<!-- 	<div id="loderImg"> </div> -->
	
	<div id="Address_app" ng-app="LMS_App" ng-controller="LMS_Contr">	
	<div class="navbar navbar-default"
		style="margin-bottom: 0px; padding-left: 10px;">
		<div class="pull-left">
			<h3 style="margin-bottom: 0px; margin-top: 0px; padding-top: 10px;">Contract
				Labour Management System</h3>
		</div>
		<div class="pull-right" style="line-height: 10px;">
			<div
				style="display: inline-block; padding-top: 0px; margin-right: 30px; text-align: right">
				<ul>
					<li class="dropdown" style="display: block"><a href="#"
						data-target="#" class="dropdown-toggle" data-toggle="dropdown"
						style="color: white"> <i class="fa fa-circle fa-3x"
							aria-hidden="true"></i>
							<div class="fa fa-user fa-2x cls_iconuser" aria-hidden="true">
							</div>
					</a>
						<ul class="dropdown-menu" style="margin-left: -100px;">
							<li><a href="javascript:void(0)">Logout</a></li>
							<!--<li class="divider"></li>-->
							<!--<li class="dropdown-header">Dropdown header</li>
                            <li><a href="javascript:void(0)">Separated link</a></li>
                            <li><a href="javascript:void(0)">One more separated link</a></li>-->
						</ul></li>
				</ul>
				<p
					style="z-index: 1; position: relative; margin-right: -25px; max-width: 500px; margin-top: -5px;">Customer Name : {{customerName}}</p>
			</div>
			<!--<div style="display: inline-block; margin-right: 15px;margin-left:-25px;">
                <i class="fa fa-caret-down" aria-hidden="true"></i>
            </div>-->
			<!--<span style="/*background-color:#d7eeec;border-radius:100px;padding:10px;margin-right:10px;margin-top:-15px;padding-top:15px;*/"><i class="fa fa-user fa-2x" aria-hidden="true"></i></span>-->

			<!--<div style="display:inline-block"><span style="padding-top:10px;"><i class="fa fa-question-circle fa-2x" aria-hidden="true"></i></span> </div>-->
		</div>
	</div>
	<div class="navbar navbar-default" style="margin-top: -28px;">
		<div class="container-fluid ">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle pull-left"
					data-toggle="collapse" data-target=".navbar-responsive-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<!--<a class="navbar-brand" href="javascript:void(0)">Brand</a>-->
			</div>

			<div class="navbar-collapse collapse navbar-responsive-collapse">
				<ul class="nav navbar-nav">
					<li class="dropdown active"><a href="javascript:void(0)"
						data-submenu data-toggle="dropdown">Organization <b
							class="caret"></b></a>
						<ul class="dropdown-menu">
							<li class="dropdown-submenu active"><a
								href="javascript:void(0)">Customer</a>
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
							<li><a href="javascript:void(0)">Departments</a></li>
							<li><a href="javascript:void(0)">Job Codes</a></li>
							<!--<li class="divider"></li>-->
							<!--<li class="dropdown-header">Dropdown header</li>
                            <li><a href="javascript:void(0)">Separated link</a></li>
                            <li><a href="javascript:void(0)">One more separated link</a></li>-->
						</ul></li>
					<li class="dropdown"><a href="bootstrap-elements.html">Vendor
					</a></li>
					<li><a href="javascript:void(0)">Contract</a></li>
					<li><a href="javascript:void(0)">Compliances</a></li>
				</ul>
				<form class="navbar-form navbar-left">
					<div class="form-group"></div>
				</form>
				<ul class="nav navbar-nav navbar-right">
				</ul>
			</div>
		</div>
	</div>	
		<div style="display: inline-block">
			<h3 class="cls_plainheader">Customer Address</h3>
		</div>
		<div class="pull-right">
			<span style="padding-top: 10px; cursor: pointer;"><i
				class="fa fa-question-circle fa-3x clsicon_help" aria-hidden="true"
				title="Help"></i></span>
		</div>
		
		<div class="container-fluid "
			style="margin-bottom: 5px; margin-top: 35px; padding-left: 2%; padding-right: 2%">
			<div class="pull-left" style="padding: 15px">
				<a href="javascript:void(0)"
					class="btn btn-raised btn-primary  modelpopup"
					  ng-click="addAddressBtnAction($event)" data-toggle="modal" data-target="#myModal" >Add Address</a>
			</div>
			<div class="well">
				<table id="example" class="display" cellspacing="0" width="100%">
					<thead>
						<tr>
							<th>Address Type</th>
							<th>Address 1</th>
							<th>Address 2</th>
							<th>City</th>
							<th>State</th>
							<th>Country</th>
							<th>Pincode</th>							
						</tr>
					</thead>

				</table>
			</div>
					
		</div>
				
		<!--  -->
		<div style="display: inline-block">
			<h3 class="cls_plainheader">Customer Contact</h3>
		</div>
		<!-- <div class="pull-right">
			<span style="padding-top: 10px; cursor: pointer;"><i
				class="fa fa-question-circle fa-3x clsicon_help" aria-hidden="true"
				title="Help"></i></span>
		</div> -->
		<div class="container-fluid "
			style="margin-bottom: 5px; margin-top: 35px; padding-left: 2%; padding-right: 2%">
			<div class="pull-left" style="padding: 15px">
				<a href="javascript:void(0)"
					class="btn btn-raised btn-primary  modelpopup1"
					  ng-click="addContactBtnAction($event)" data-toggle="modal" data-target="#myModal1" >Add Contact</a>
			</div>
			<div class="well">
				<table id="example1" class="display" cellspacing="0" width="100%">
					<thead>
						<tr>
							<th>Contact Type</th>
                            <th>Contact Name</th>
                            <th>Phone Number</th>
                            <th>Business number</th>
                            <!-- ,th>Extension</th> -->
                            <th>Email Id </th>
                            <th>Address</th>							
						</tr>
					</thead>

				</table>
			</div>
					
		</div>
			<div class="modal fade" id="myModal1" role="dialog">
    <div class="modal-dialog">
    
		<div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Enter Customer Contact </h4>
        </div>
        
		<div class="container-fluid  modal-body" style="margin-bottom: 35px;">
		
		<form name = "customerContact" novalidate ng-submit="submit" 
			class="form-horizontal">
		
		<div class="container-fluid transactionList1"> 
					<div class="form-group col-lg-5 col-md-5 col-xs-12 col-sm-12"
						style="margin-top: -30px; margin-bottom: 12px;">
						<label class="control-label required" for="transactionList1"> Transaction Date: </label>
						<select id="transactionList1" class="form-control" 
						title="Select date" ng-model="contactTransDate" name="contactTransDate" ng-change="fun_Contact_Trans_Change()" >	
							<option ng-repeat="dates in transactionDatesListForContact" selected= {{ dates.id ==  contactTransDate }} ? "selected" : "" 
								value="{{dates.id}}">{{dates.name}}</option>													
						</select>
											
					</div>

					<hr class="pull-left" style="width: 100%" />
				</div>
				
            <fieldset style="padding-left: 3%;">
                <legend style="margin-bottom: 0px;"></legend>
                <input type="hidden" value="0" ng-model="customerId" class="customerId"/>
                
                 <div class="container-fluid">
            		<div class="form-group required col-lg-4 col-md-4 col-xs-12 col-sm-12" style="margin-top: -25px;">
               			 <label class="control-label required" for="focusedInput1" style="color: black;">Transaction Date </label>
                		<input class="form-control" id="txt_effectivedate1" type="text" placeholder="Pick a date" style="color: black;"
                		  ng-model="transactionDate1" name="transactionDate1" ng-required="true" ng-disabled="datesReadOnly" />
                		<div ng-messages="customerContact.transactionDate1.$error" ng-if='submitted && customerContact.transactionDate1.$invalid' role="alert">
      						<div ng-message="customerContact.transactionDate1.$error.required" class="msg-ErrorMessageStyle">Date is required</div>
      					</div>
   					 </div>
            	</div>            
            		            	             
                <hr class="pull-left" style="width: 100%;" />
 
               <div class="form-group col-lg-5 col-md-5 col-xs-12 col-sm-12"  style="margin-top: -40px;">
                    <label class="control-label required" for="ddl_ContactType">Contact Type </label>
                    <select id="ddl_ContactType" class="form-control" ng-disabled="readonly"  
                    title="Select Contact"  ng-model="contactTypeId" name="contactTypeId" ng-required="true">                   
                    	<c:forEach items="${contactTypes}" var="contacttype" varStatus="row"> 
                        	<option  value="${contacttype.contactTypeId}" > ${contacttype.contactTypeName} </option>
                     	</c:forEach>
                    </select>  
                    <div ng-messages="customerContact.contactTypeId.$error" ng-if='submitted && customerContact.contactTypeId.$invalid' role="alert">
    					<div ng-message="required" class="errorMessage"> Select Contact Type </div>
					</div> 
                </div>
                
                <div class="form-group required label-floating col-lg-5 col-md-5 col-xs-12 col-sm-12 col-lg-offset-2 col-md-offset-2" style="margin-top: 0px;">
                    <label class="control-label required" for="txtContactName">Contact Name </label>
                    <input class="form-control onlyAlphabets" id="txtContactName" ng-model="contactName" name="contactName" type="text" style="width:100%"  ng-readonly="readonly" ng-required="true" ng-maxlength="45"/>
                    <div ng-messages="customerContact.contactName.$error" ng-if='(submitted && customerContact.contactName.$invalid) || (customerContact.contactName.$touched)' role="alert">
    						<div ng-show="customerContact.contactName.$error.required" class="errorMessage"> Please enter name</div> 
    						<div ng-show="customerContact.contactName.$error.maxlength" class="errorMessage"> Contact name should have atleast 10 characters </div>
    						<div ng-show="customerContact.contactName.$error.minlength" class="errorMessage"> This field allows maximium 45 characters </div>
      				</div>
                </div>
                
                
                <hr class="pull-left" style="width: 100%;" />
                
 
                <div class="form-group label-floating col-lg-5 col-md-5 col-xs-12 col-sm-12" style="margin-top: 0px;">
                    <label class="control-label" for="txtBusinessPhoneNumber">Business Phone Number</label>
                    <input class="form-control " id="txtBusinessPhoneNumber" ng-model="businessPhoneNumber" name="businessPhoneNumber" ng-required="!mobileNumber" ng-readonly="readonly" type="text" onkeypress='return event.charCode >= 48 && event.charCode <= 57' ng-maxlength ="12" style="width:100%" />
                	 <div ng-messages="customerContact.businessPhoneNumber.$error" ng-if='(submitted && customerContact.businessPhoneNumber.$invalid) || (customerContact.businessPhoneNumber.$touched)' role="alert">
    					<div  ng-show="customerContact.businessPhoneNumber.$error.required"  class="errorMessage"> Please enter Business phone number / mobile number</div>
    					<div  ng-show="customerContact.businessPhoneNumber.$error.maxlength" class="errorMessage"> This field allows maximium 12 digits  </div> 
    				</div>
                </div>  
                
                <div class="form-group  label-floating col-lg-5 col-md-5 col-xs-12 col-sm-12 col-lg-offset-2 col-md-offset-2" style="margin-top: 0px; margin-bottom: 0px;">
                    <label class="control-label" for="txtMobileNumber required">Mobile Number</label>
                    <input class="form-control " id="txtMobileNumber" ng-model="mobileNumber" type="text" name="mobileNumber" ng-readonly="readonly"  onkeypress='return event.charCode >= 48 && event.charCode <= 57' ng-minlength ="10" ng-maxlength="12"  style="width:100%" ng-required="!businessPhoneNumber" />
                     <div ng-messages="customerContact.mobileNumber.$error" ng-if='(submitted && customerContact.mobileNumber.$invalid) || (customerContact.mobileNumber.$touched)' role="alert">
<!--     					<div  ng-show="customerContact.mobileNumber.$error.required"  class="errorMessage"> Please enter Business phone / mobile number</div> 
 -->    					<div  ng-show="customerContact.mobileNumber.$error.minlength" class="errorMessage"> Mobile number should have atleast 10 digits </div>
    					<div  ng-show="customerContact.mobileNumber.$error.maxlength" class="errorMessage"> This field allows maximium 12 digits  </div>
      				</div> 
                </div>
                <!-- <div ng-messages="customerContact.$error" ng-if='(submitted && (customerContact.mobileNumber.$invalid) || (customerContact.mobileNumber.$touched) ||((customerContact.businessPhoneNumber.$invalid) || (customerContact.businessPhoneNumber.$touched)) )' role="alert">
    					<div  ng-show="customerContact.$error.required"  class="errorMessage"> Please enter Business phone / mobile number</div> 
    				</div> -->
                <div class="form-group label-floating col-lg-5 col-md-5 col-xs-12 col-sm-12" style="margin-top: 0px; margin-bottom: 0px;">
                    <label class="control-label" for="txtExtensionNumber">Extension</label>
                    <input class="form-control " id="txtExtensionNumber" ng-model="extensionNumber" name="extensionNumber" ng-readonly="readonly" type="text" onkeypress='return event.charCode >= 48 && event.charCode <= 57' ng-maxlength ="5" style="width:100%" />
                </div>     
                
                <hr style="width: 100%" />  
                
                
                <div class="form-group required label-floating col-lg-5 col-md-5 col-xs-12 col-sm-12" style="margin-top: 0px;">
                    <label class="control-label" for="txtEmailId">Email Id </label>
                    <input class="form-control" id="txtEmailId" ng-model="emailId"  name="emailId" ng-readonly="readonly"  type="email" style="width:100%" ng-maxlength="45" required />
                    <div ng-messages="customerContact.emailId.$error" ng-if='(submitted && customerContact.emailId.$invalid) || (customerContact.emailId.$touched)' role="alert">
    					<div  ng-show="customerContact.emailId.$error.required"  class="errorMessage"> Please enter Email Id</div> 
    				<!-- 	<div  ng-show="customerContact.emailId.$error.email" class="errorMessage"> Please enter valid Email Id</div>  -->
    					<div  ng-show="customerContact.emailId.$error.maxlength" class="errorMessage"> This field allows maximium 45 characters </div>
      				</div> 
                </div>  
                               
               <div class="form-group required col-lg-5 col-md-5 col-xs-12 col-sm-12 col-lg-offset-2 col-md-offset-2 cls_divradios" style="margin-top: -30px;">
               		<label class="col-lg-4 col-md-4 col-sm-5 col-xs-6 control-label cls_lblradios required">Is Active</label>
                    <div class="col-lg-8 col-md-8 col-sm-7 col-xs-6" style="margin-top: 15px;">
						<div class="radio radio-primary" style="display: inline-block">
							<label> <input type="radio" name="isActive"  ng-required="true" id="optionsRadios1" value="Y"  ng-model="isActive"  name="isActive"  ng-checked="isActive == 'Y'">Yes
							</label>
						</div>
						<div class="radio radio-primary" style="display: inline-block">
							<label> <input type="radio" name="isActive"  ng-required="true" id="optionsRadios2" value="N" ng-model="isActive"  name="isActive" ng-checked="isActive == 'N'"> No
							</label>
						</div>
							<!-- {{isActive}} -->
					</div>
					<div ng-messages="customerContact.isActive.$error" ng-if='submitted && customerContact.isActive.$invalid' role="alert">
      					<div ng-message="required" class="errorMessage"> Select isActive </div>      														
      				</div>
               	</div> 
               	
               	<hr class="pull-left" style="width: 100%;" />   
               	
               	<div class="form-group required col-lg-12 col-md-12 col-xs-12 col-sm-12" style="margin-top: -30px; margin-bottom: 12px;">
                    <label class="control-label required" for="ddlAddress">Address </label>
                    <select id="ddlAddress" class="form-control"  ng-disabled="readonly" 
                     title = "Select Address" ng-model="addressId"  name="addressId" ng-required="true"> 
                    	<!-- <option value="">Select Address</option>  -->                 
                    	<option ng-repeat="address in addressListData" value="{{address.id}}">{{address.name}}</option>
                    </select>
                    <div ng-messages="customerContact.addressId.$error" ng-if='submitted && customerContact.addressId.$invalid' role="alert">
    					<div ng-message="required" class="errorMessage"> Select Address </div>
					</div> 			
                </div>
  
                <hr style="width: 100%" />
                <div class="form-group col-xs-12">
                    <br />
                    <div class="text-right">
                    	<a href="javascript:void(0)" class="btn btn-raised btn-danger updateContactBtn" ng-click="updateContactBtnAction($event)"> Update </a>
                        <button class="btn btn-raised btn-primary saveContactBtn" type="submit" ng-click="submitted = true;customerContact.$valid && fun_savecustomercontactdetails($event)" onclick="return false;">Save</button>
                        <button class="btn btn-raised btn-primary currentContactHistoryBtn" type="submit" ng-click="submitted = true;customerContact.$valid && fun_updatecustomercontactdetails($event)" onclick="return false;"> Correct History </button>
                        <button class="btn btn-raised btn-danger  resetContactBtn "  type="reset" ng-click="contactFormFieldsClear()"> Reset </button> 
                        <button class="btn btn-raised btn-danger cancelContactBtn" ng-click="cancelContactBtnAction($event)">Cancel</button>
                        <a href="javascript:void(0)" class="btn btn-raised btn-info ContactHistory"  ng-click="historyContactBtnAction($event)"> View History </a>
                    </div>
                </div>
                
            </fieldset>
        </form>			
		
		</div>
		</div>
		</div>
		</div>
		
		
		
		<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
    
		<div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Enter Customer Address </h4>
        </div>
        
		<div class="container-fluid  modal-body" style="margin-bottom: 35px;">
					
		<form name="customerAddress" novalidate ng-submit="submit"
				class="form-horizontal">								
				<div class="container-fluid transactionList"> 
					<div class="form-group col-lg-5 col-md-5 col-xs-12 col-sm-12"
						style="margin-top: -30px; margin-bottom: 12px;">
						<label class="control-label required" for="transactionList"> Transaction Date: </label>
						<select id="transactionList" class="form-control" 
						title="Select date" ng-model="addressTransDate" name="addressTransDate"  ng-change="fun_Address_Trans_Change()" >	
							<option ng-repeat="dates in transactionDatesList" selected= {{ dates.id ==  addressTransDate }} ? "selected" : ""
								value="{{dates.id}}">{{dates.name}}</option>													
						</select>						
					</div>
					<hr class="pull-left" style="width: 100%" />
				</div>
				<fieldset class="" style="padding-left: 3%;">
					<legend style="margin-bottom: 0px;"></legend>
					<div class="container-fluid">
						<div
							class="form-group col-lg-5 col-md-5 col-xs-12 col-sm-12"
							style="margin-top: -20px;">
							<label class="control-label required" for="txt_effectivedate"
								style="color: black;">Transaction Date </label>
							<input class="form-control class_date1" id="txt_effectivedate" type="text"
								placeholder="Pick a date" style="color: black;display:inline-block"
								ng-model="transactionDate" name="transactionDate"  ng-required="true" ng-disabled="datesReadOnly"  />
						</div>						
					</div>
					<div ng-messages="customerAddress.transactionDate.$error" ng-if='submitted && customerAddress.transactionDate.$invalid' role="alert">
      							<div ng-message="required" class="errorMessage"> Select Transaction Date </div>
     					</div>
					<hr class="pull-left" style="width: 100%" />
					<div class="form-group col-lg-5 col-md-5 col-xs-12 col-sm-12"
						style="margin-top: -25px;">
						<label class="control-label required" for="ddl_AddressType">Address
							Type </label> <select id="ddl_AddressType" ng-disabled="readonly"
							class="form-control"
							title="Select Address" ng-model="addressTypeId" name="addressTypeId"  ng-required="true">
							<c:forEach items="${addressTypes}" var="addresstype"
								varStatus="row">
								<option value="${addresstype.addressTypeId*1} ">
									${addresstype.addressTypeName}</option>
							</c:forEach>							
						</select>	
						<div ng-messages="customerAddress.addressTypeId.$error" ng-if='submitted && customerAddress.addressTypeId.$invalid' role="alert">
      							<div ng-message="required" class="errorMessage"> Select Address Type </div>
     							</div>       											
					</div>
					<div
						class="form-group col-lg-5 col-md-5 col-xs-12 col-sm-12 col-lg-offset-2 col-md-offset-2 cls_divradios"
						style="margin-top: -10px; margin-bottom: 5px;">
						<label
							class="col-lg-4 col-md-4 col-sm-5 col-xs-6 control-label cls_lblradios required">Is
							Active</label>
						<div class="col-lg-8 col-md-8 col-sm-7 col-xs-6"
							style="margin-top: 15px;">
							<div class="radio radio-primary" style="display: inline-block">
								<label> <input type="radio" name="isActive"  ng-required="true"
									id="optionsRadios1" value="Y"  ng-model="isActive" name="isActive"   ng-checked="isActive == 'Y'">
									Yes
								</label>
							</div>
							<div class="radio radio-primary" style="display: inline-block">
								<label> <input type="radio" name="isActive"  ng-required="true"
									id="optionsRadios2" value="N" ng-model="isActive" name="isActive"   ng-checked="isActive == 'N'"> No
								</label>
							</div>
							<!-- {{isActive}} -->
						</div>
						<div ng-messages="customerAddress.isActive.$error" ng-if='submitted && customerAddress.isActive.$invalid' role="alert">
      							<div ng-message="required" class="errorMessage"> Select isActive </div>      														
      					</div>
					</div>
					<input type="hidden" value="0" ng-model="customerDetailsId"
						class="customerDetailsId">
					<div
						class="form-group label-floating col-lg-5 col-md-5 col-xs-12 col-sm-12 "
						style="margin-bottom: -5px; width: 100%">
						<label class="control-label required" for="txtAddressLine1">Address
							Line 1 </label> <input class="form-control " id="txtAddressLine1" maxlength="50"  name="address1" readonly="readonly"
							ng-model="address1" type="text" style="width: 50%"  ng-required="true" ng-readonly="readonly" />	
						<div ng-messages="customerAddress.address1.$error" ng-if='submitted && customerAddress.address1.$invalid ' role="alert">
      							<div ng-message="required" class="errorMessage">Address1 required</div>      														
      					</div>	      									
					</div>
					 
					<div
						class="form-group label-floating col-lg-5 col-md-5 col-xs-12 col-sm-12"
						style="margin-bottom: -5px; width: 100%">
						<label class="control-label" for="txtAddressLine2">Address
							Line 2</label> <input class="form-control " id="txtAddressLine2" maxlength="50" 
							ng-model="address2" type="text" style="width: 50%"  ng-readonly="readonly" />
					</div>
					<div
						class="form-group label-floating col-lg-5 col-md-5 col-xs-12 col-sm-12"
						style="margin-bottom: -5px; width: 100%">
						<label class="control-label" for="txtAddressLine3">Address
							Line 3</label> <input class="form-control " id="txtAddressLine3" maxlength="50" 
							ng-model="address3" type="text" style="width: 50%" ng-readonly="readonly" />
					</div>

					<hr class="pull-left" style="width: 100%" />

					<div class="form-group col-lg-5 col-md-5 col-xs-12 col-sm-12"
						style="margin-top: -30px; margin-bottom: 12px;">
						<label class="control-label required" for="ddlCountry">Country </label>
						<select id="ddlCountry" ng-disabled="readonly"
							class="form-control"
							title="Select Country" ng-model="country" name="country"
							ng-change="fun_Countrychange()" ng-required="true">	
							<option ng-repeat="country in list_countries"
								value="{{country.countryId*1}}">{{country.countryName}}</option>													
						</select>
						<div ng-messages="customerAddress.country.$error" ng-if='submitted && customerAddress.country.$invalid' role="alert">
      							<div ng-message="required" class="errorMessage"> Select Country </div>
      							</div> 
					</div>
					<div
						class="form-group col-lg-5 col-md-5 col-xs-12 col-sm-12 col-lg-offset-2 col-md-offset-2"
						style="margin-top: -30px; margin-bottom: 12px;">
						<label class="control-label required" for="ddlState">State </label> <select
							id="ddlState" ng-disabled="readonly"
							class="form-control"
							title="Select State" ng-model="state" name="state"
							ng-change="fun_Statechange()"  ng-required="true">
							<option ng-repeat="states in list_states"
								value="{{states.stateId*1}}">{{states.stateName}}</option>

						</select>
 						<div ng-messages="customerAddress.state.$error" ng-if='submitted && customerAddress.state.$invalid' role="alert">
    						<div ng-message="required" class="errorMessage"> Select State </div>
						</div>
					</div>

					<div class="form-group col-lg-5 col-md-5 col-xs-12 col-sm-12"
						style="margin-top: -30px; margin-bottom: 12px;">
						<label class="control-label required" for="ddlcity">City </label> <select
							id="ddlcity" ng-disabled="readonly"
							class="form-control"
							title="Select City" ng-model="city" name="city" ng-required="true" >

							<option ng-repeat="cities in list_cities"
								value="{{cities.cityyId*1}}">{{cities.cityName}}</option>

						</select>
						 <div ng-messages="customerAddress.city.$error" ng-if='submitted && customerAddress.city.$invalid' role="alert">
    							<div ng-message="required" class="errorMessage">Select City </div> 
						</div>
					</div>
					<div
						class="form-group label-floating col-lg-5 col-md-5 col-xs-12 col-sm-12 col-lg-offset-2 col-md-offset-2"
						style="margin-top: 10px; margin-bottom: 12px;">
						<label class="control-label required" for="pincode">Pincode </label>
						<input class="form-control  onlyNumbers" id="pincode" type="text" ng-minlength="6"    ng-maxlength="6" 
							ng-model="pincode" name="pincode"  ng-required="true" ng-readonly="readonly" />
							   <div ng-messages="customerAddress.pincode.$error" ng-if='(submitted && customerAddress.pincode.$invalid) || (customerAddress.pincode.$touched)' role="alert">
      							<div ng-show="customerAddress.pincode.$error.required" class="errorMessage">Pincode required</div> 
      							<div ng-show="customerAddress.pincode.$error.maxlength" class="errorMessage">Pincode should be 6 digits</div>
      							<div ng-show="customerAddress.pincode.$error.minlength" class="errorMessage"> Pincode should be 6 digits</div>
      							</div> 
					</div>
					<hr class="pull-left" style="width: 100%" />
					<div class="form-group col-xs-12">
						<br />
						<div class="text-right">
							<a href="javascript:void(0)"
									class="btn btn-raised btn-danger updateBtn" ng-click="updateAddressBtnAction($event)"> Update </a>
							 <button class="btn btn-raised btn-primary saveBtn" 
								ng-click=" submitted = true; customerAddress.$valid &&  fun_savecustomeraddressdetails($event)" onclick="return false;">Save</button> 
							<button class="btn btn-raised btn-primary currentHistoryBtn" type="submit"
								ng-click="submitted = true; customerAddress.$valid && fun_updatecustomeraddressdetails($event)" onclick="return false;"> Correct History </button>						
						<!-- <button class="btn btn-raised btn-primary" type="submit"
								ng-click="submitted = true;customerAddress.$valid && fun_savecustomeraddressdetailsandReirect($event)" onclick="return false;">Save
								& Proceed</button> -->
							 <button class="btn btn-raised btn-danger  resetBtn" type="reset"> Reset </a> 
								<button type="button" class="btn btn-raised btn-danger cancelBtn" ng-click="cancelBtnAction($event)"> Cancel </button>
								<a href="javascript:void(0)"
								class="btn btn-raised btn-info addrHistory" ng-click="historyBtnAction($event)"> View History </a>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
		</div>
		</div>
		</div>
		

	</div>
	

	<div class="container-fluid">
		<div class="navbar navbar-default navbar-fixed-bottom text-center"
			style="min-height: 15px; padding: 0px;">
			<div class="container text-right" style="">
				<label class="control-label text-center"
					style="color: white; margin: 0px; font-size: 14px;">@Copyright</label>
			</div>
		</div>
	</div>
</body>
</html>
