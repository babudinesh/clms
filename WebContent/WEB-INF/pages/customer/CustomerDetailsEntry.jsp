<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core">
<head>
<title></title>
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




<!-- jQuery -->
<script
	src="${pageContext.request.contextPath}/Resources/JQUERY/jquery-2.2.1.js"></script>
<script
	src="${pageContext.request.contextPath}/Resources/bootstrap-3.3.6-dist/js/bootstrap.js"></script>
<script
	src="${pageContext.request.contextPath}/Resources/Material-Deisgn/dist/js/material.js"></script>

<!--AngularJS-->
<script src="${pageContext.request.contextPath}/js/Main.js"></script>
<!-- <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script> -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/Resources/Angular/angular.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/Resources/Angular/ngStorage.min.js"></script>
<script
	src="${pageContext.request.contextPath}/js/Customer_Details_Entry.js"></script>
<script
	src="${pageContext.request.contextPath}/js/Customer_Details_Search.js"></script>
	<script
	src="${pageContext.request.contextPath}/js/Form_Valiation.js"></script>


<!-- Bootstrap and Datatables-->
<script
	src="${pageContext.request.contextPath}/Resources/Material-Deisgn/dist_select/js/bootstrap-select.js"></script>
<script type="text/javascript"
	src="http://momentjs.com/downloads/moment-with-locales.min.js"></script>
<script
	src="${pageContext.request.contextPath}/Resources/Material-Deisgn/dist_Datepicker/js/bootstrap-material-datetimepicker.js"></script>

<link
	href="https://cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css"
	rel="stylesheet" />
<script
	src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>

<!-- Angular Error Messages-->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/angular-messages/1.5.6/angular-messages.min.js"></script>

<!-- Toaster-->
<link
	href="${pageContext.request.contextPath}/Resources/Toaster/toastr.css"
	rel="stylesheet" />
<script
	src="${pageContext.request.contextPath}/Resources/Toaster/toastr.js"></script>
<!-- User Defined Styles for Customer Details-->
<link href="${pageContext.request.contextPath}/css/CustomerDetails.css"
	rel="stylesheet" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/Main.js"></script>

<script type="text/javascript">
	$(document).ready(
			function() {
				//initialsie the material-deisgn
				$.material.init();

				$('#txt_effectivedate').bootstrapMaterialDatePicker({
					time : false,
					clearButton : true,
					format : "DD/MM/YYYY"
				});

				//$('#txt_effectivedate').bootstrapMaterialDatePicker('setDate',moment());
			});
</script>

<script type="text/javascript">
	function fun_toaster(priority, title, message) {
		$.toaster({
			priority : priority,
			title : title,
			message : message,
			settings : {
				timeout : 2500
			},
		});
	}
	$(document)
			.ready(
					function() {
						$('.AddCCustomer').show();
						if (localStorage.customerId != null
								&& localStorage.customerId != ""
								&& localStorage.customerId != 'undefined') {
							// alert("document Ready function if::"+localStorage.customerId);
							var scope = angular.element(
									document.getElementById('Customer_app'))
									.scope();
							scope
									.$apply(function() {
										scope
												.fun_getcustomerDetailsById(localStorage.customerId);
									});
						}
						$("#Radio11").click(
								function() {
									$('#ddl_countryofoper').selectpicker(
											'deselectAll');
									$("#ddl_countryofoper").selectpicker({
										actionsBox : true,
										maxOptions : false
									}).selectpicker("refresh");
								});
						$("#Radio21").click(
								function() {
									$('#ddl_countryofoper').selectpicker(
											'deselectAll');
									$("#ddl_countryofoper").selectpicker({
										actionsBox : false,
										maxOptions : 1
									}).selectpicker("refresh");
								});

						$(".clearFields").click(function() {
							// // alert("clicked");k
							$(".clear").val('');
							$(".selectClear").val(null);
						});

						//initialsie the material-deisgn
						$.material.init();

						$('#txt_Effectivedate').bootstrapMaterialDatePicker({
							time : false,
							clearButton : true,
							format : "DD/MM/YYYY"
						});

						//$('#txt_Effectivedate').bootstrapMaterialDatePicker('setDate', moment());

						function format(d) {
							// `d` is the original data object for the row
							return '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:200px;">'
									+ abc(d.extn) + '</table>';
						}
						function abc(value) {
							var a = value.split(',');
							var td = "";
							for (var i = 0; i <= a.length - 1; i++) {
								td += '<tr><td width="116px"></td><td colspan = "4"><a>'
										+ a[i] + '</a></td></tr>';
							}
							return td;

						}

						/* setTimeout(function() {
							$('#ddl_countryname').selectpicker('refresh');
						}, 1000); */

					});

	function fun_Cancel() {
		$(".close").click();

	}
</script>
<style>
td.details-control {
	background:
		url('https://datatables.net/examples/resources/details_open.png')
		no-repeat center center;
	cursor: pointer;
}

tr.shown td.details-control {
	background:
		url('https://datatables.net/examples/resources/details_close.png')
		no-repeat center center;
}
</style>


<style>
/* .msg-ErrorMessageStyle {
  background:maroon;
  color:white;
  padding:5px;
  border-radius:5px;
  position:absolute;
  z-index:99;
}
.msg-ErrorMessageStyle:after {
  position:absolute;
  top:-10px;
  content:"";
  left:20px;
  border-style:solid;
  border-color:transparent transparent maroon;
  border-width:5px;
}
 */
.msg-ErrorMessageStyle {
	background: maroon;
	color: white;
	padding: 5px;
	border-radius: 5px;
	position: absolute;
	z-index: 99;
	right: 0;
	bottom: 15px;
}

.msg-ErrorMessageStyle:after {
	position: absolute;
	top: 10px;
	content: "";
	left: -10px;
	border-style: solid;
	border-color: transparent transparent maroon;
	border-width: 5px;
	transform: rotate(-90deg);
}
</style>




</head>
<body>
	<div ng-app="CustomerDetailsApp">
		<div id="Customer_app" ng-controller="CustomerDetailsController">

			<div>

				<div class="navbar navbar-default"
					style="margin-bottom: 0px; padding-left: 10px;">
					<div class="pull-left">
						<h3
							style="margin-bottom: 0px; margin-top: 0px; padding-top: 10px;">Contract
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
									</ul></li>
							</ul>
							<p
								style="z-index: 1; position: relative; margin-right: -25px; max-width: 500px; margin-top: -5px;">John
								Abraham</p>
						</div>

					</div>
				</div>
				<div class="navbar navbar-default  navBarEnable" style="margin-top: -28px;">
					<div class="container-fluid">
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


				<div class="container-fluid" style="margin-bottom: 35px;">
					<div style="display: inline-block">
						<h3 class="cls_plainheader">Customer Details</h3>
					</div>
					<div class="pull-right">
						<span style="padding-top: 10px; cursor: pointer;"><i
							class="fa fa-question-circle fa-3x clsicon_help"
							aria-hidden="true" title="Help"></i></span>
					</div>

					<div class="container-fluid AddCCustomer">
						<form:form commandName="customerDetailsVo"
							name="customerDetailsForm" class="form-horizontal well "
							style="background-color: white; padding: 15px; padding-top: 35px; width: 95%; margin: 0px auto; padding-bottom: 0px;"
							novalidate="novalidate">


							<div class="container-fluid CustomertransactionList">
								<div class="form-group col-lg-5 col-md-5 col-xs-12 col-sm-12"
									style="margin-top: -30px; margin-bottom: 12px;">
									<label class="control-label required"
										for="CustomertransactionList"> Transaction Date: </label>
										 <select
										id="CustomertransactionList" class="form-control "
										title="Select date" ng-model="val_CustomertransactionList"
										name="CustomertransactionList"
										ng-change="fun_CustomerTransactionDateChnage()">
										<option ng-repeat="dates in CustomertransactionDatesList"
											value="{{dates.id}}">{{dates.name}}</option>
									</select>
									<!-- {{val_CustomertransactionList}} -->
								</div>
								<hr class="pull-left" style="width: 100%;margin-top:0px;margin-bottom:0px;" />
							</div>



							<fieldset style="padding-left: 3%;">
								<legend style="margin-bottom: 0px;"></legend>
								<div class="form-group col-lg-5 col-md-5 col-xs-12 col-sm-12">
									<input ng-model="customer_ID" ng-show="false" type="hidden" />
									<input ng-model="customerinfoId" ng-show="false" type="hidden" />
									<%-- <input ng-model="val_hiddenField" ng-show="false" type="hidden"
										ng-init="val_hiddenField = '${pageContext.request.contextPath}'" /> --%>
										<label class="control-label" for="focusedInput1"
											style="color: black;">Transaction Date <span
											style="color: red;">*</span></label>
										<form:input path="transactionDate" class="form-control transactionDate"
											id="txt_effectivedate" type="text" placeholder="Pick a date"
											style="color: black;" name="transactionDate"
											ng-model="val_TransactionDate" ng-disabled="transactionDatereadonly" />
										<div ng-messages="customerDetailsForm.transactionDate.$error"
											ng-if='submitted && customerDetailsForm.transactionDate.$invalid'
											role="alert">
											<div ng-message="required" class="msg-ErrorMessageStyle">Date
												is required</div>
										</div>
								</div>
								<div class="form-group label-floating col-lg-5 col-md-5 col-xs-12 col-sm-12 col-lg-offset-2 col-md-offset-2" 
										style="margin-top:45px;margin-bottom:10px">
								      	<label for="inputEmail" class="control-label">Customer Code</label>	
							        	<form:input path="customerCode" id="Text2"
										class="form-control clear" type="text" name="customerCode"
										ng-model="val_customerCode" ng-minlength="2" ng-maxlength="10" max="10" min="2" size="10"
										ng-required="true" 
										ng-readonly="readonly" />
										<div ng-messages="customerDetailsForm.customerCode.$error"
										ng-if='(submitted && customerDetailsForm.customerCode.$invalid) || (customerDetailsForm.customerCode.$touched)'
										role="alert">
										<div
											ng-show="customerDetailsForm.customerCode.$error.required"
											class="msg-ErrorMessageStyle">Customer Code is required</div>
										<div
											ng-show="customerDetailsForm.customerCode.$error.minlength"
											class="msg-ErrorMessageStyle">Name must be over 2
											characters</div>
										<div
											ng-show="customerDetailsForm.customerCode.$error.maxlength"
											class="msg-ErrorMessageStyle">Name must not exceed 10
											characters</div>										
									</div>
								</div>
								


								<div
									class="form-group label-floating col-lg-5 col-md-5 col-xs-12 col-sm-12">
									<label class="control-label" for="focusedInput1">Customer
										Name <span style="color: red;">*</span>
									</label>
									<form:input path="customerName" id="Text2"
										class="form-control clear bothNumbersAndAlphabets" type="text" name="customerName"
										ng-model="val_CustomerName" ng-minlength="2" ng-maxlength="45" size="45"
										ng-required="true" ng-blur="fun_validateCustomerName($event)"
										ng-readonly="readonly" max="45" min="2"/>

									<div style="position: absolute; top: 0px; right: -20px;">
										<i
											ng-show="val_CustomerName != undefined && flag_ajaxlogo == -1"
											class=" fa fa-spinner fa-pulse fa-2x fa-fw"></i> <i
											ng-show="val_CustomerName != undefined && flag_ajaxlogo == 0"
											class="fa fa-check fa-2x" style="color: green"></i> <i
											ng-show="val_CustomerName != undefined && flag_ajaxlogo == 1"
											class="fa fa-times fa-2x" style="color: red"></i>
									</div>

									<div ng-messages="customerDetailsForm.customerName.$error"
										ng-if='(submitted && customerDetailsForm.customerName.$invalid) || (customerDetailsForm.customerName.$touched)'
										role="alert">
										<div
											ng-show="customerDetailsForm.customerName.$error.required"
											class="msg-ErrorMessageStyle">Customer Name is required</div>
										<div
											ng-show="customerDetailsForm.customerName.$error.minlength"
											class="msg-ErrorMessageStyle">Name must be over 2
											characters</div>
										<div
											ng-show="customerDetailsForm.customerName.$error.maxlength"
											class="msg-ErrorMessageStyle">Name must not exceed 45
											characters</div>
										<div
											ng-show="val_customerError != undefined && flag_ajaxlogo == 1"
											class="msg-ErrorMessageStyle">Customer Already Existed
											with name</div>
									</div>
								</div>

								<div
									class="form-group col-lg-5 col-md-5 col-xs-12 col-sm-12 col-lg-offset-2 col-md-offset-2 cls_divradios"
									style="margin-top:25px;">
									<label class="col-lg-4 col-md-4 col-sm-5 col-xs-6 control-label cls_lblradios"
										for="focusedInput1" style="margin-top:0px;">Status <span
										style="color: red;">*</span></label>

									<div class="col-lg-8 col-md-8 col-sm-7 col-xs-6">
										<div class="radio radio-primary" style="display: inline-block;margin-bottom:0px;">
											<label> <input type="radio" name="isActive"
												ng-required="true" id="optionsRadios1" value="Y"
												ng-model="isActive" name="isActive" ng-init="isActive = 'Y'"
												ng-checked="isActive == 'Y'"> Active
											</label>
										</div>
										<div class="radio radio-primary" style="display: inline-block;margin-bottom:0px;">
											<label> <input type="radio" name="isActive"
												ng-required="true" id="optionsRadios2" value="N"
												ng-model="isActive" name="isActive"
												ng-checked="isActive == 'N'"> Inactive
											</label>
										</div>
										<!-- {{isActive}}  -->
									</div>
									<div ng-messages="customerDetailsForm.isActive.$error"
										ng-if='submitted && customerDetailsForm.isActive.$invalid'
										role="alert">
										<div ng-message="required" class="msg-ErrorMessageStyle">Select
											isActive</div>
									</div>
								</div>

								<div
									class="form-group label-floating col-lg-5 col-md-5 col-xs-12 col-sm-12"
									style="margin-top:30px;">
									<label class="control-label" for="focusedInput1">Legacy
										Name 
									</label>
									<form:input path="legacyName" id="Text2"
										class="form-control clear" type="text" name="legacyName" ng-model="val_legacyName"    ng-maxlength="45" size="45"
										 ng-readonly="readonly" />

									<div ng-messages="customerDetailsForm.legacyName.$error"
										ng-if='(customerDetailsForm.legacyName.$touched)'
										role="alert">
										<!-- <div ng-show="customerDetailsForm.legacyName.$error.required"
											class="msg-ErrorMessageStyle">Legacy Name is required</div>  
										<div ng-show="customerDetailsForm.legacyName.$error.minlength"
											class="msg-ErrorMessageStyle">Name must be over 2
											characters</div>-->
										<div ng-show="customerDetailsForm.legacyName.$error.maxlength"
											class="msg-ErrorMessageStyle">Name must not exceed 45
											characters</div>

									</div>
								</div>




								<div
									class="form-group col-lg-5 col-md-5 col-xs-12 col-sm-12 col-lg-offset-2 col-md-offset-2 "
									style="margin-top: -10px; margin-bottom: 5px;">


									<label class="control-label" for="focusedInput1">Country
										<span style="color: red;">*</span>
									</label> <select id="ddl_countryname" ng-disabled="readonly"
										class="form-control"										
										title="Please select a Country" ng-model="customer_country"
										name="customerCountry" ng-required="true">
										<c:forEach var="country" items="${countriesList}">
											<option value="${country.countryId*1}">${country.countryName}</option>
										</c:forEach>
									</select> <!-- {{customer_country}} -->
									<div ng-messages="customerDetailsForm.customerCountry.$error"
										ng-if='submitted && customerDetailsForm.customerCountry.$invalid'
										role="alert">
										<div ng-message="required" class="msg-ErrorMessageStyle">Select
											Country</div>
									</div>

								</div>


								<hr style="width: 100%;" class="pull-left" />

								<div
									class="form-group col-lg-5 col-md-5 col-xs-12 col-sm-12 cls_divradios"
									style="margin-top: -10px; margin-bottom: 5px;">
									 <label
										class="col-lg-4 col-md-4 col-sm-5 col-xs-6 control-label cls_lblradios">
										 Entity Type <span style="color: red;">*</span>
									</label> 
									<div class="col-lg-8 col-md-8 col-sm-7 col-xs-6"
										style="margin-top: 15px;">
										<div class="radio radio-primary" style="display: inline-block">
											<label> <form:radiobutton path="entityType"
													name="optionsRadios" id="optionsRadios3" value="Legal"
													ng-init="rdn_entitytype = 'Legal'" ng-model="rdn_entitytype"
													ng-required="true" ng-checked="rdn_entitytype == 'Legal'"/> Legal
											</label>
											<!-- {{rdn_entitytype}} -->

										</div>
										<div class="radio radio-primary" style="display: inline-block">
											<label> <form:radiobutton path="entityType"
													name="optionsRadios" id="optionsRadios4" value="Logical"
													ng-model="rdn_entitytype" ng-required="true" ng-checked="rdn_entitytype == 'Logical'"/> Logical
											</label>
										</div>
										<div ng-messages="customerDetailsForm.optionsRadios.$error"
											ng-if='submitted && customerDetailsForm.optionsRadios.$invalid'
											role="alert">
											<div ng-message="required" class="msg-ErrorMessageStyle">Select
												Legal Or Logical</div>
										</div>

									</div>
								</div>
								<div
									class="form-group label-floating col-lg-5 col-md-5 col-xs-12 col-sm-12 col-lg-offset-2 col-md-offset-2"
									style="margin-top: 0px;">
									<label class="control-label" for="focusedInput1">Total
										No of Companies <span style="color: red;">*</span>
									</label>
									<form:input path="numberOfCompanies" id="numberOfCompanies"
										name="numberOfCompanies" class="form-control clear"
										type="text" ng-model="val_noofcompanies" ng-maxlength="2"
										required="required" ng-readonly="readonly" min="1" ng-pattern="/^|[1-9][0-9]*$/" />
																								
										
									<div ng-messages="customerDetailsForm.numberOfCompanies.$error"
										ng-if='(submitted && customerDetailsForm.numberOfCompanies.$invalid) || (customerDetailsForm.numberOfCompanies.$touched)'
										role="alert">
										<div
											ng-show="customerDetailsForm.numberOfCompanies.$error.required"
											class="msg-ErrorMessageStyle ">No Of Companies is
											required</div>
										<div
											ng-show="customerDetailsForm.numberOfCompanies.$error.maxlength"
											class="msg-ErrorMessageStyle">Accepts only two digits</div>
											
											<div
											ng-show="customerDetailsForm.numberOfCompanies.$error.min"
											class="msg-ErrorMessageStyle">Should be greater than 0</div>
											
											<div
											ng-show="customerDetailsForm.numberOfCompanies.$error.pattern"
											class="msg-ErrorMessageStyle">Numbers only...</div>
									</div>
								</div>

								<hr style="width: 100%" class="pull-left" />

								<div
									class="form-group col-lg-5 col-md-5 col-xs-12 col-sm-12 cls_divradios"
									style="margin-top: 0px; margin-bottom: 5px;">
									<label
										class="col-lg-4 col-md-4 col-sm-5 col-xs-6 control-label cls_lblradios">Is
										Multi National <span style="color: red;">*</span>
									</label>

									<div class="col-lg-8 col-md-8 col-sm-7 col-xs-6"
										style="margin-top: 15px;">
										<div class="radio radio-primary" style="display: inline-block">
											<label> <form:radiobutton path="isMultinaional"
													class="multiselected" name="optionsRadios1" id="Radio11"
													value="Yes" 	ng-init="rdn_ismultination = 'Yes'"											
													ng-model="rdn_ismultination" ng-required="true"  ng-checked="rdn_ismultination == 'Yes'"/> Yes
											</label>
										</div>
										<div class="radio radio-primary" style="display: inline-block">
											<label> <form:radiobutton path="isMultinaional"
													name="optionsRadios1" id="Radio21" value="No"
													class="multiNotselected" ng-model="rdn_ismultination"
													ng-required="true"  ng-checked="rdn_ismultination == 'No'" /> No
											</label>
										</div>
										<!-- {{rdn_ismultination}} -->
										<div ng-messages="customerDetailsForm.optionsRadios1.$error"
											ng-if='submitted && customerDetailsForm.optionsRadios1.$invalid'
											role="alert">
											<div ng-message="required" class="msg-ErrorMessageStyle">Select
												Is Multinational</div>
										</div>
									</div>
								</div>
								<%-- <div
									class="form-group col-lg-5 col-md-5 col-xs-12 col-sm-12 col-lg-offset-2 col-md-offset-2"
									style="margin-top: -30px; margin-bottom: 12px;">
									<label class="control-label" for="focusedInput1">Countries
										of Operations <span style="color: red;">*</span> </label>
										 <select id="ddl_countryofoper" multiple=""  
										class="form-control selectClear selectpicker multipleCountryPicker" data-live-search="true"
										data-live-search-placeholder="Search" data-actions-box="true"
										title="Please select a country" ng-required="true"
										ng-model="selected_CountrysIds" name="countries" mydirective>
										<c:forEach var="country" items="${countriesList}">
											<option value="${country.countryId}"
												<c:if test="${country.countryId == customerDetailsVo.mncCountries}">selected="selected"</c:if>>${country.countryName}</option>
										</c:forEach>
									</select>
									<div ng-messages="customerDetailsForm.countries.$error" ng-if='(submitted && customerDetailsForm.countries.$invalid) || (rdn_ismultination=="Yes" && selected_CountrysIds.length<=1)' role="alert">
	      								<div ng-show="customerDetailsForm.countries.$error.required" class="msg-ErrorMessageStyle">Select Countries</div>
	      								 <div ng-show="rdn_ismultination=='Yes' && selected_CountrysIds.length<=1" class="msg-ErrorMessageStyle">Select Multiple Countries</div> 
   								 	</div>
   								 </div> --%>
								<hr style="width: 100%" class="pull-left" />

								<div
									class="form-group col-lg-5 col-md-5 col-xs-12 col-sm-12 cls_divradios cls_divindust"
									style="margin-top: -15px; margin-bottom: 10px;">
									<label
										class="col-lg-4 col-md-4 col-sm-5 col-xs-6 control-label cls_lblradios"
										style="margin-top: 0px;">industries <span
										style="color: red;">*</span>
									</label> <select id="Select1" path="industryId" multiple=""
										ng-disabled="readonly"
										class="form-control selectpicker selectIndustryspicker selectClear"
										data-live-search="true" data-live-search-placeholder="Search"
										data-actions-box="true" title="Please select a Industry"
										ng-change="fun_Indchange()" ng-required="true"
										ng-model="selected_custIndustry" name="industryIdss">
										<c:forEach var="industry" items="${industyList}">
											<option value="${industry.industryId}">${industry.industryName}</option>
										</c:forEach>
									</select>


									<div ng-messages="customerDetailsForm.industryIdss.$error"
										ng-if='submitted && customerDetailsForm.industryIdss.$invalid'
										role="alert">
										<div ng-message="required" class="msg-ErrorMessageStyle">Select
											Industries</div>
									</div>


								</div>
								<div
									class="form-group col-lg-5 col-md-5 col-xs-12 col-sm-12 col-lg-offset-2 col-md-offset-2"
									style="margin-top: -15px;">
									<label class="control-label" for="focusedInput1"
										style="margin-top: 0px;">Industries & Sectors <span
										style="color: red;">*</span>
									</label> <select id="ddl_IndSectors" multiple=""
										ng-model="selected_custSector" ng-required="true"
										class="form-control selectpicker sectorListPicker selectClear"
										style="height: 125px;" data-live-search="true"
										data-live-search-placeholder="Search" name="Sectors"
										data-actions-box="true" ng-disabled="readonly"
										title="Please select a Sector for an Industry">
										<optgroup label="{{x}}" ng-repeat="(x,y) in list_sector">
											<option ng-repeat="z in y" value="{{z.sectorId}}">{{z.sectorName}}</option>
										</optgroup>
									</select>

									<div ng-messages="customerDetailsForm.Sectors.$error"
										ng-if='submitted && customerDetailsForm.Sectors.$invalid'
										role="alert">
										<div ng-message="required" class="msg-ErrorMessageStyle">Select
											Sectors</div>
									</div>

								</div>

								<hr style="width: 100%" class="pull-left" />
								<div class="col-lg-12 col-md-12 col-xs-12 col-sm-12" ng-show="rdn_ismultination == 'Yes'" style="margin-left: -25px;padding: 0px;">
									<div class="container-fluid "
										style="margin-bottom: 35px; margin-top: 35px;padding:0px;">

										<div class="well">
											<table id="countryDetails" class="display example"
												cellspacing="0" width="100%" >
												<thead>
													<tr>
														<th class='c1'>Country</th>
														<th class='c2'>Base Name</th>
														<th class='c3'>No Of Companies</th>
														<th class='c4'>Language</th>
														<th class='c5'>Currency</th>
														<!-- <th>Currency</th>
														<th>Currency</th>
														<th>Currency</th> -->
													</tr>

												</thead>
											</table>
										</div>

									</div>


									</div>


								<div class="form-group col-xs-12">
									<br />
									<div class="text-right">
										<!--  {{submitted}} {{customerDetailsForm.$valid}} -->
										<button class="btn btn-raised btn-primary saveBtn"
											type="submit"
											ng-click="submitted=true;customerDetailsForm.$valid && fun_savecustomer($event)"
											onclick="return false;" ng-disabled="readonly">Save</button>
										<button class="btn btn-raised btn-primary updateBtn"
											type="submit" ng-click="fun_updateBtnAction($event)"
											onclick="return false;">Update</button>
										<button class="btn btn-raised btn-primary viewOrEdit"
											type="submit" ng-click="fun_VieworEditHistory($event)"
											onclick="return false;">View/Edit History</button>
										<button class="btn btn-raised btn-primary correctHistory"
											type="submit"
											ng-click="submitted=true;customerDetailsForm.$valid && fun_savecustomer($event)"
											onclick="return false;">Correct History</button>
										<button class="btn btn-raised btn-primary" type="button"
											ng-click="fun_ReturntoSearch($event)" onclick="return false;">Return
											To Search</button>


										<!-- <button class="btn btn-raised btn-primary" type="button" ng-click="fun_savecustomerAndRedirect($event)"  onclick="return false;">Save & Proceed</button> -->
										<!-- <button  class="btn btn-raised btn-danger clearFields" type="button" ng-click="fun_clearFields($event)"  onclick="fun_Cancel()">Reset</button> -->


									</div>
								</div>

							</fieldset>

						</form:form>
						
						<br>
						<br>
						<br>
					</div>
				</div>
			</div>
			<form name="customerDetailsFormPopup">
						<div class="modal fade  saveCountryDetails" id="myModal"
										role="dialog">
										<div class="modal-dialog">

											<div class="modal-content">
												<div class="modal-header">
													<button type="button" class="close" data-dismiss="modal">&times;</button>
													<h4 class="modal-title">Enter Country Details</h4>
												</div>

												<div class="container-fluid  modal-body"
													style="margin-bottom: 35px;padding-left:50px;padding-right:50px;">

													<table>
														<input type="text" ng-show="false"
															ng-model="customerCountriesId">
														<input type="text" ng-show="false" ng-model="index">
														<tr>
														<div class="form-group label-floating">
														      <label for="ddl_countrynamess" class="control-label">Country Name<span style="color: red;">*</span></label>
														      <select id="ddl_countrynamess"
																	class="form-control"
																	data-live-search="true"
																	data-live-search-placeholder="Search"
																	title="Please select a Country"
																	ng-model="customer_countryies" name="customerCountrys" 															
																	ng-required="true">
																	<c:forEach var="country" items="${countriesList}">
																		<option value="${country.countryId}">${country.countryName}</option>
																	</c:forEach>
																</select>
																<!--  {{customer_countryies}} -->
																<div
																	ng-messages="customerDetailsFormPopup.customerCountrys.$error"
																	ng-if='customerDetailsFormPopup.$submitted && customerDetailsFormPopup.customerCountrys.$invalid'
																	role="alert">
																	<div ng-message="required"
																		class="msg-ErrorMessageStyle">Select Country</div>
																</div>
														    </div>
														</tr>
														<tr>

															<div class="form-group label-floating is-empty">
																<label class="control-label" for="Text2">Base
																	Company
																</label>
																<input path="companyBaseName" id="Text2"
																	class="form-control clear" type="text"
																	name="companyBaseName" ng-model="val_companyBaseName" 
																	 ng-maxlength="45"  max="45" value="" />

																 <div
																	ng-messages="customerDetailsFormPopup.companyBaseName.$error"
																	ng-if='(customerDetailsFormPopup.$submitted &&  customerDetailsFormPopup.companyBaseName.$invalid) || (customerDetailsFormPopup.companyBaseName.$touched)'
																	role="alert">																	
																	<div
																		ng-show="customerDetailsFormPopup.companyBaseName.$error.maxlength"
																		class="msg-ErrorMessageStyle">Name must not exceed 45 characters</div>

																</div> 
															</div>

														</tr>

														<tr>
														<div class="form-group label-floating is-empty">
															<label class="control-label" for="focusedInput1">No
																Of Companies <span style="color: red;">*</span>
															</label>
															<input path="totNumberOfCompanies"
																id="totNumberOfCompanies" name="totNumberOfCompanies"
																class="form-control clear " type="number" ng-required="true"
																ng-model="val_totNumberOfCompanies" ng-maxlength="2" ng-pattern="/^(|[1-9][0-9]*)$/"/>
															<div
																ng-messages="customerDetailsFormPopup.totNumberOfCompanies.$error"
																ng-if='(customerDetailsFormPopup.$submitted &&  customerDetailsFormPopup.totNumberOfCompanies.$invalid) || (customerDetailsFormPopup.totNumberOfCompanies.$touched)'
																role="alert">
																<div
																	ng-show="customerDetailsFormPopup.totNumberOfCompanies.$error.required"
																	class="msg-ErrorMessageStyle ">No Of Companies is
																	required</div>
																<div
																	ng-show="customerDetailsFormPopup.totNumberOfCompanies.$error.maxlength"
																	class="msg-ErrorMessageStyle">Accepts only two
																	digits</div>
																	
																	<div
																		ng-show="customerDetailsFormPopup.totNumberOfCompanies.$error.pattern"
																		class="msg-ErrorMessageStyle">Numbers only...</div>
															</div>
															</div>	
														</tr>
														<tr>
														<div class="form-group label-floating is-empty">
															<label class="control-label" for="focusedInput1">Language
																<span style="color: red;">*</span>
															</label>
															<select id="ddl_Language" class="form-control"
																data-live-search="true"
																data-live-search-placeholder="Search"
																title="Please select a Language"
																ng-model="val_languages" convert-to-number
																name="Language" ng-required="true">
																<c:forEach var="language" items="${languageList}">
																	<option value="${language.languageId}">${language.language}</option>
																</c:forEach>
															</select> <!-- {{val_languages}} -->
															<div ng-messages="customerDetailsFormPopup.Language.$error"
																ng-if='customerDetailsFormPopup.$submitted && customerDetailsFormPopup.Language.$invalid'
																role="alert">
																<div ng-message="required" class="msg-ErrorMessageStyle">Select
																	Language</div>
															</div>
														</div>
														</tr>
														<tr>
														<div class="form-group label-floating is-empty">
															<label class="control-label" for="focusedInput1">Currency<span
																style="color: red;">*</span></label>
															<select id="ddl_currency" class="form-control"
																data-live-search="true"
																data-live-search-placeholder="Search"
																title="Please select Currency" ng-model="val_currencys"
																convert-to-number name="currency" ng-required="true">
																<c:forEach var="currency" items="${currencyList}">
																	<option value="${currency.currencyId}">${currency.currency}</option>
																</c:forEach>
															</select> <!-- {{val_currencys}} -->
															<div ng-messages="customerDetailsFormPopup.currency.$error"
																ng-if='customerDetailsFormPopup.$submitted && customerDetailsFormPopup.currency.$invalid'
																role="alert">
																<div ng-message="required" class="msg-ErrorMessageStyle">Select
																	Currency</div>
															</div>
														</div>
														</tr>
														<tr>
													<!-- 	a{{PopUpsubmitted}}
														b{{customerDetailsFormPopup.$valid}}	 -->													
															<button class="btn btn-raised btn-primary popupSave" type="submit"
																
																ng-click="customerDetailsFormPopup.$setSubmitted();customerDetailsFormPopup.$valid && fun_saveCountryDetails($event)"
																onclick="">Save</button>
																<button class="btn btn-raised btn-primary popupUpdate" type="submit"
																
																ng-click="customerDetailsFormPopup.$valid && fun_UpdateCountryDetails($event)"
																onclick="return false;">Update</button>																
															<button class="btn btn-raised btn-danger " type="button"
																 onclick="fun_Cancel()">Cancel</button>

														</tr>
													</table>
												</div>
											</div>
										</div>
									</div>
								
						</form>	
		</div>
		 <div>
			<form name="confirmPopupOnDelete">
						<div class="modal fade" id="confirmPopup"
										role="dialog">
										<div class="modal-dialog">

											<div class="modal-content">
												<div class="modal-header">
													<button type="button" class="close" data-dismiss="modal">&times;</button>
													<h4 class="modal-title">Are you sure you want to delete ?</h4>
												</div>

												<div class="container-fluid  modal-body"
													style="margin-bottom: 35px;padding-left:50px;padding-right:50px;">

													<table>
														<button class="btn btn-raised btn-primary popupSave" type="submit"																
																ng-click="deleteGridRowData($event)"
																onclick="return false;">Yes</button>
														<button class="btn btn-raised btn-danger " type="button"
																 onclick="fun_Cancel()">No</button>
													</table>
												</div>
											</div>
										</div>
									</div>
								
			</form>	
		</div> 
		
		</div>		
					
</body>
</html>
