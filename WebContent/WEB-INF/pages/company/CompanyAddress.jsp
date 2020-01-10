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
	src="${pageContext.request.contextPath}/js/Company_Address.js"></script>
<script
	src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
<%-- <script type="text/javascript"
	src="${pageContext.request.contextPath}/Resources/Toaster/jquery.toaster.js"></script> --%>
	<script type="text/javascript"
	src="${pageContext.request.contextPath}/Resources/Toaster/toastr.js"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/Form_Valiation.js"></script>
	
<script type="text/javascript">
	$(document).ready(
			function() {

				//initialsie the material-deisgn
				$.material.init();
				$('.AddressAdd').hide();

				$('#txt_effectivedate').bootstrapMaterialDatePicker({
					time : false,
					clearButton : true,
					format : "DD/MM/YYYY"
				});
				
			});
</script>
</head>
<body>

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
					style="z-index: 1; position: relative; margin-right: -25px; max-width: 500px; margin-top: -5px;">John
					Abraham</p>
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
									<li class="active"><a href="javascript:void(0)">Customer
											Details</a></li>
									<li><a href="javascript:void(0)">Address</a></li>
									<li><a href="javascript:void(0)">Contact</a></li>
								</ul></li>
							<li class="dropdown-submenu"><a href="javascript:void(0)">Company</a>
								<ul class="dropdown-menu" style="top: 45px;">
									<li><a href="javascript:void(0)">Company Details</a></li>
									<li><a href="javascript:void(0)">Address</a></li>
									<li><a href="javascript:void(0)">Contact</a></li>
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
	<div id="Address_app" ng-app="LMS_App" ng-controller="LMS_Contr">
		<div style="display: inline-block">
			<h3 class="cls_plainheader">Company Address</h3>
		</div>
		<div class="pull-right">
			<span style="padding-top: 10px; cursor: pointer;"><i
				class="fa fa-question-circle fa-3x clsicon_help" aria-hidden="true"
				title="Help"></i></span>
		</div>
		<div class="container-fluid "
			style="margin-bottom: 35px; margin-top: 35px; padding-left: 2%; padding-right: 2%">
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
							<th>Action</th>
						</tr>
					</thead>

				</table>
			</div>
			<div class="pull-right" style="padding-right: 30px">
				<a href="javascript:void(0)"
					class="btn btn-raised btn-primary pull-right"
					 ng-click="addAddressBtnAction($event)">Add Address</a>
			</div>
		</div>
		<div class="container-fluid  AddressAdd" style="margin-bottom: 35px;">			
			<form name="companyAddress"  novalidate ng-submit="submit" 
			class="form-horizontal  companyAddressForm" >
				
										
					<fieldset class="well" style="padding-left: 3%;">
					<legend style="margin-bottom: 0px;"></legend>
					<div class="container-fluid">
						<div
							class="form-group col-lg-5 col-md-5 col-xs-12 col-sm-12"
							style="margin-top: -20px;">
							<label class="control-label required" for="focusedInput1"
								style="color: black;">Transaction Date </label> <input
								class="form-control" id="txt_effectivedate" type="text"
								placeholder="Pick a date" style="color: black;"
								ng-model="transactionDate" name="transactionDate"  ng-required="true" />
								<div ng-messages="companyAddress.transactionDate.$error" ng-if='submitted && companyAddress.transactionDate.$invalid' role="alert">
    							<div ng-message="required" class="errorMessage"> Select Transaction Date </div>
     					</div>
						</div>						
					</div>
						
					<hr class="pull-left" style="width: 100%" />
					<div class="form-group col-lg-5 col-md-5 col-xs-12 col-sm-12"
						style="margin-top: -25px;">
						<label class="control-label required" for="focusedInput1">Address
							Type </label> <select id="ddl_AddressType"
							class="form-control selectpicker selectaddresstypepicker "
							title="Select Address" ng-model="addressTypeId" name="addressTypeId"  ng-required="true">
							<c:forEach items="${addressTypes}" var="addresstype"
								varStatus="row">
								<option value="${addresstype.addressTypeId*1} ">
									${addresstype.addressTypeName}</option>
							</c:forEach>							
						</select>	
						<div ng-messages="companyAddress.addressTypeId.$error" ng-if='(submitted && companyAddress.addressTypeId.$invalid) || (companyAddress.addressTypeId.$touched) ' role="alert">
      							<div ng-message="required" class="errorMessage"> Select Address Type </div>
     							</div>       											
					</div>
						<input type="hidden" value="0" ng-model="customerDetailsId" class="customerDetailsId">
						<input type="hidden" value="0" ng-model="companyDetailsId" class="companyDetailsId">
						
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
						<div ng-messages="companyAddress.isActive.$error" ng-if='submitted && companyAddress.isActive.$invalid' role="alert">
      							<div ng-message="required" class="errorMessage"> Select isActive </div>      														
      					</div>
					</div>
					<input type="hidden" value="0" ng-model="customerDetailsId"
						class="customerDetailsId">
					<div
						class="form-group label-floating col-lg-5 col-md-5 col-xs-12 col-sm-12 "
						style="margin-bottom: -5px; width: 100%">
						<label class="control-label required" for="focusedInput1">Address
							Line 1 </label> <input class="form-control " id="txtAddressLine1" maxlength="50"  name="address1" 
							ng-model="address1" type="text" style="width: 50%"  ng-required="true" ng-maxlength="40"/>	
						<div ng-messages="companyAddress.address1.$error" ng-if='(submitted && companyAddress.address1.$invalid) || (companyAddress.address1.$touched) ' role="alert">
      							<div ng-show="companyAddress.address1.$error.required" class="errorMessage">Address Line1 required</div>      							
	      						<div ng-show="companyAddress.address1.$error.maxlength" class="errorMessage">Address Line1 Max 40 Characters are Allowed. </div>      														
      					</div>	      									
					</div>
					 
					<div
						class="form-group label-floating col-lg-5 col-md-5 col-xs-12 col-sm-12"
						style="margin-bottom: -5px; width: 100%">
						<label class="control-label" for="focusedInput1">Address
							Line 2</label> <input class="form-control " id="txtAddressLine2" maxlength="50" 
							ng-model="address2" type="text" style="width: 50%" />
					</div>
					<div
						class="form-group label-floating col-lg-5 col-md-5 col-xs-12 col-sm-12"
						style="margin-bottom: -5px; width: 100%">
						<label class="control-label" for="focusedInput1">Address
							Line 3</label> <input class="form-control " id="txtAddressLine3" maxlength="50" 
							ng-model="address3" type="text" style="width: 50%" />
					</div>

					<hr class="pull-left" style="width: 100%" />

					<div class="form-group col-lg-5 col-md-5 col-xs-12 col-sm-12"
						style="margin-top: -30px; margin-bottom: 12px;">
						<label class="control-label required" for="focusedInput1">Country </label>
						<select id="ddlCountry" ng-init=""
							class="form-control selectpicker selectcountrypicker "
							title="Select Country" ng-model="country" name="country"
							ng-change="fun_Countrychange()" ng-required="true">	
							<option ng-repeat="country in list_countries"
								value="{{country.countryId*1}}">{{country.countryName}}</option>													
						</select>
						<div ng-messages="companyAddress.country.$error" ng-if='(submitted && companyAddress.country.$invalid) || (companyAddress.country.$touched) ' role="alert">
      							<div ng-message="required" class="errorMessage"> Select Country </div>
      							</div> 
					</div>
					<div
						class="form-group col-lg-5 col-md-5 col-xs-12 col-sm-12 col-lg-offset-2 col-md-offset-2"
						style="margin-top: -30px; margin-bottom: 12px;">
						<label class="control-label required" for="focusedInput1">State </label> <select
							id="ddlState"
							class="form-control selectpicker selectstatepicker "
							title="Select State" ng-model="state" name="state"
							ng-change="fun_Statechange()"  ng-required="true">
							<option ng-repeat="states in list_states"
								value="{{states.stateId*1}}">{{states.stateName}}</option>

						</select>
 						<div ng-messages="companyAddress.state.$error" ng-if='(submitted && companyAddress.state.$invalid) || (companyAddress.state.$touched) ' role="alert">
    						<div ng-message="required" class="errorMessage"> Select State </div>
						</div>
					</div>

					<div class="form-group col-lg-5 col-md-5 col-xs-12 col-sm-12"
						style="margin-top: -30px; margin-bottom: 12px;">
						<label class="control-label required" for="focusedInput1">City </label> <select
							id="Select1"
							class="form-control selectpicker selectcitypicker "
							title="Select City" ng-model="city" name="city" ng-required="true" >

							<option ng-repeat="cities in list_cities"
								value="{{cities.cityyId*1}}">{{cities.cityName}}</option>

						</select>
						 <div ng-messages="companyAddress.city.$error" ng-if='(submitted && companyAddress.city.$invalid) || (companyAddress.city.$touched) ' role="alert">
    							<div ng-message="required" class="errorMessage">Select City </div> 
						</div>
					</div>
					<div
						class="form-group label-floating col-lg-5 col-md-5 col-xs-12 col-sm-12 col-lg-offset-2 col-md-offset-2"
						style="margin-top: 10px; margin-bottom: 12px;">
						<label class="control-label required" for="focusedInput1">Pincode </label>
						<input class="form-control  onlyNumbers" id="pincode" type="text"  ng-minlength="6"    ng-maxlength="6"  
							ng-model="pincode" name="pincode"  ng-required="true"/>
							   <div ng-messages="companyAddress.pincode.$error" ng-if='(submitted && companyAddress.pincode.$invalid) || (companyAddress.pincode.$touched)' role="alert">
	      							<div ng-show="companyAddress.pincode.$error.required" class="errorMessage">Pincode required</div> 
	      							<div ng-show="companyAddress.pincode.$error.maxlength" class="errorMessage">Pincode has Max 6 Digits </div>
	      							<div ng-show="companyAddress.pincode.$error.minlength" class="errorMessage"> Pincode has 6 Digits  </div>
      							</div> 
					</div>
					<hr class="pull-left" style="width: 100%" />	
					
					<div class="form-group col-xs-12">
						<br />
						<div class="text-right">

							<button class="btn btn-raised btn-primary" type="submit"
								ng-click="submitted = true;companyAddress.$valid && fun_savecompanyaddressdetails($event)" onclick="return false;">Save</button>
							<button class="btn btn-raised btn-primary" type="submit"
								ng-click="submitted = true;companyAddress.$valid && fun_savecompanyaddressdetailsandReirect($event)" onclick="return false;">Save
								& Proceed</button>
							<a href="javascript:void(0)"
								class="btn btn-raised btn-danger " ng-click="cancelBtnAction($event)">Cancel</a>
						</div>
					</div>
				</fieldset>
			</form>
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
