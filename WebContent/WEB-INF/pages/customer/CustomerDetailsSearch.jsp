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

<script type="text/javascript"
	src="${pageContext.request.contextPath}/Resources/Angular/angular.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/Resources/Angular/ngStorage.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/Customer_Details_Search.js"></script>
<script
	src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/Resources/Toaster/jquery.toaster.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/Main.js"></script>
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
				
				
				
			});
	

</script>
<style type="text/css">
/* .example tr td: nth-of-type(3){
 display: none;
}
.hidden {
 display: none;
} */
</style>
</head>
<body>

	
	<div id="Address_app" ng-app="customerDetailsSearch" ng-controller="customerDetailsSearch_Contr">
	
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
							</ul></li>
				</ul>
				<p
					style="z-index: 1; position: relative; margin-right: -25px; max-width: 500px; margin-top: -5px;">Customer Name:{{customerName}}</p>
					<!-- <input type="text" ng-model="customerName" value=""/> -->
			</div>
			
		</div>
	</div>
	<div class="navbar navbar-default navBarEnable" style="margin-top: -28px;">
		<div class="container-fluid ">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle pull-left"
					data-toggle="collapse" data-target=".navbar-responsive-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
			
			</div>

			<div class="navbar-collapse collapse navbar-responsive-collapse " >
				<ul class="nav navbar-nav">
					<li class="dropdown active"><a href="javascript:void(0)"
						data-submenu data-toggle="dropdown">Organization <b
							class="caret"></b></a>
						<ul class="dropdown-menu">
							<li class="dropdown-submenu active"><a
								href="javascript:void(0)">Customer</a>
								<ul class="dropdown-menu" style="top: 10px;">
									<<li class="dropdown-submenu active"><a
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
							<li><a href="${pageContext.request.contextPath}/DepartmentController/departmentDetails.view">Department Details</a></li>
							<li><a href="javascript:void(0)">Job Codes</a></li>
							</ul></li>
					<li class="dropdown-submenu"><a href="javascript:void(0)">Vendor</a>
					<ul class="dropdown-menu" style="top: 45px;">
					<li><a href="${pageContext.request.contextPath}/vendorController/VendorDetails.view">Vendor Details</a></li>
					<li><a href="${pageContext.request.contextPath}/vendorController/VendorBankDetails.view">Bank Details</a></li>
					</ul>
					</li>
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
			<h3 class="cls_plainheader">Customer List</h3>
		</div>
		<div class="pull-right">
			<span style="padding-top: 10px; cursor: pointer;"><i
				class="fa fa-question-circle fa-3x clsicon_help" aria-hidden="true"
				title="Help"></i></span>
		</div>
		<div class="container-fluid" style="padding-left: 15%;">
		<div class="pull-left" style="padding-left: 30px">
				<a href="javascript:void(0)"
					class="btn btn-raised btn-primary pull-right clearFields"
					 ng-click="fun_openCustomerScreen($event)">Add Customer</a>
			</div>
		</div>
		<div class="container-fluid "
			style="margin-bottom: 35px; margin-top: 35px; padding-left: 20%; padding-right: 20%">
			
			<div class="well">
				<table id="example" class="display example" cellspacing="0" width="100%">
					<thead>
						<tr>
							<th></th>
							<th>Customer Code</th>
							<th>Customer Name</th>
							<th>Status</th>
						</tr>
					</thead>

				</table>
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
