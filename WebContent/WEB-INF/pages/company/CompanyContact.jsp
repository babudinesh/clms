<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Company Contact</title>
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
		<link href="${pageContext.request.contextPath}/css/Styles.css" rel="stylesheet" />	
		
		 <style type="text/css">
        .sepr {
            padding: 2px;
        }

        .btn-group-right {
            float: right;
        }

        .pandit {
            margin-top: 10px;
        }

        .form-group label.control-label {
            font-size: 14px !important;
        }

        .char_required {
            color: red;
        }

        .span_hintmsg {
            font-size: 12px;
            color: #BDBDBD;
        }

        .form-group {
            margin-top: 15px;
        }

        .togglebutton label .toggle:after {
            box-shadow: 0px 0px 1px 0px rgba(0, 0, 0, 0.4);
        }

        .form-group label.control-label {
            font-size: 12px;
        }

        .form-control {
            font-size: 14px;
        }

        .cls_iconuser {
            vertical-align: top;
            margin-top: 7px;
            margin-left: -32.5px;
            color: var(--main-color);
        }

        .cls_plainheader {
            color: var(--main-color);
        }

        .clsicon_help {
            color: var(--main-color);
        }
    </style>

    <!--Overriding material design classes-->
    <style type="text/css">
        @media (min-width: 992px) {
            .col-md-offset-12 {
                margin-left: 100% !important;
            }

            .col-md-offset-11 {
                margin-left: 91.66666667% !important;
            }

            .col-md-offset-10 {
                margin-left: 83.33333333% !important;
            }

            .col-md-offset-9 {
                margin-left: 75% !important;
            }

            .col-md-offset-8 {
                margin-left: 66.66666667% !important;
            }

            .col-md-offset-7 {
                margin-left: 58.33333333% !important;
            }

            .col-md-offset-6 {
                margin-left: 50% !important;
            }

            .col-md-offset-5 {
                margin-left: 41.66666667% !important;
            }

            .col-md-offset-4 {
                margin-left: 33.33333333% !important;
            }

            .col-md-offset-3 {
                margin-left: 25% !important;
            }

            .col-md-offset-2 {
                margin-left: 16.66666667% !important;
            }

            .col-md-offset-1 {
                margin-left: 8.33333333% !important;
            }

            .col-md-offset-0 {
                margin-left: 0 !important;
            }
        }

        @media (min-width: 1200px) {
            .col-lg-offset-12 {
                margin-left: 100% !important;
            }

            .col-lg-offset-11 {
                margin-left: 91.66666667% !important;
            }

            .col-lg-offset-10 {
                margin-left: 83.33333333% !important;
            }

            .col-lg-offset-9 {
                margin-left: 75% !important;
            }

            .col-lg-offset-8 {
                margin-left: 66.66666667% !important;
            }

            .col-lg-offset-7 {
                margin-left: 58.33333333% !important;
            }

            .col-lg-offset-6 {
                margin-left: 50% !important;
            }

            .col-lg-offset-5 {
                margin-left: 41.66666667% !important;
            }

            .col-lg-offset-4 {
                margin-left: 33.33333333% !important;
            }

            .col-lg-offset-3 {
                margin-left: 25% !important;
            }

            .col-lg-offset-2 {
                margin-left: 16.66666667% !important;
            }

            .col-lg-offset-1 {
                margin-left: 8.33333333% !important;
            }

            .col-lg-offset-0 {
                margin-left: 0 !important;
            }
        }
        /*.form-group.form-group-sm.label-static label.control-label, .form-group.form-group-sm.label-floating.is-focused label.control-label, .form-group.form-group-sm.label-floating:not(.is-empty) label.control-label {
            padding-left:15px !important;
        }*/
        .form-group label.control-label {
            left: 15px !important;
        }
    </style>

    <style type="text/css">
        /*.form-group :not(.label-floating) {
            margin-top:-25px !important;
        }*/
        .dropdownjs > ul > li.selected, .dropdownjs > ul > li:active {
            background-color: var(--main-color);
            color: white;
        }

        .cls_divradios {
            padding-left: 0px !important;
        }

        .cls_lblradios {
            padding-left: 0px !important;
            text-align: left !important;
        }

        .dropdown-menu.inner {
            max-height: 150px !important;
        }

            .dropdown-menu.inner .selected {
                background-color: var(--main-color);
            }

        .dropdown-menu > .active > a, .dropdown-menu > .active > a:focus, .dropdown-menu > .active > a:hover {
            background-color: var(--main-color);
        }

        .dropdown-menu.inner .selected > a {
            color: white !important;
        }

        .bootstrap-select.btn-group .dropdown-toggle .filter-option {
            font-size: 16px;
            line-height: 1.42857143;
            font-weight: 100;
            font-family: 'Roboto', 'Helvetica', 'Arial', sans-serif;
            text-transform: capitalize;
        }

        .bootstrap-select.btn-group .dropdown-toggle .filter-option {
            font-size: 14px;
        }

        .btn-group.bootstrap-select {
            margin-top: 0px;
        }

        .cls_divindust .btn-group.bootstrap-select {
            margin-left: 15px;
        }

        hr {
            border-top: 2px solid #DAD5D5;
            padding-bottom: 5px !important;
            padding-top: 5px !important;
            margin-left: -25px;
        }
    </style>

    <style type="text/css">
        .navbar {
            min-height: 5px;
            padding-top: 3px;
            padding-bottom: 3px;
            margin-bottom: 0px;
        }

        .nav.navbar-nav > li > a {
            padding-top: 0px;
            padding-bottom: 0px;
        }

        .navbar-form.navbar-left {
            margin-top: 0px;
            margin-bottom: 0px;
        }

        .navbar .dropdown-menu li > a, .navbar.navbar-default .dropdown-menu li > a {
            padding: 5px 16px;
            text-decoration: none;
        }

        .caret {
            display: inline-block;
            width: 0;
            height: 0;
            margin-left: 2px;
            vertical-align: middle;
            border-top: 4px solid;
            border-right: 4px solid transparent;
            border-left: 4px solid transparent;
        }

        .caret_right {
            border-color: transparent transparent transparent #333;
            border-style: solid;
            border-width: 5px 0 5px 5px;
            content: " ";
            display: block;
            float: right;
            height: 0;
            margin-right: -10px;
            margin-top: 5px;
            width: 0;
        }

        /*.dropdown-submenu.open {
            position:static !important;
            
        }
        .dropdown-submenu.open .dropdown-menu {
            top:10px;
        }*/
        .dropdown-menu > li.kopie > a {
            padding-left: 5px;
        }

        .dropdown-submenu {
            position: static !important;
        }

            .dropdown-submenu > .dropdown-menu {
                top: 0px;
                left: 100%;
                margin-top: -6px;
                margin-left: -1px;
                -webkit-border-radius: 0 6px 6px 6px;
                -moz-border-radius: 0 6px 6px 6px;
                border-radius: 0 6px 6px 6px;
            }

            .dropdown-submenu > a:after {
                border-color: transparent transparent transparent #333;
                border-style: solid;
                border-width: 5px 0 5px 5px;
                content: " ";
                display: block;
                float: right;
                height: 0;
                margin-right: -10px;
                margin-top: 5px;
                width: 0;
            }

            .dropdown-submenu:hover > a:after {
                border-left-color: #555;
            }

        .dropdown-menu > li > a:hover, .dropdown-menu > .active > a:hover {
            text-decoration: underline;
        }

        @media (max-width: 767px) {

            .navbar-nav {
                display: inline;
            }

            .navbar-default .navbar-brand {
                display: inline;
            }

            .navbar-default .navbar-toggle .icon-bar {
                background-color: #fff;
            }

            .navbar-default .navbar-nav .dropdown-menu > li > a {
                color: red;
                background-color: #ccc;
                border-radius: 4px;
                margin-top: 2px;
            }

            .navbar-default .navbar-nav .open .dropdown-menu > li > a {
                color: #333;
            }

                .navbar-default .navbar-nav .open .dropdown-menu > li > a:hover,
                .navbar-default .navbar-nav .open .dropdown-menu > li > a:focus {
                    background-color: #ccc;
                }

            .navbar-nav .open .dropdown-menu {
                border-bottom: 1px solid white;
                border-radius: 0;
            }

            .dropdown-menu {
                padding-left: 10px;
            }

                .dropdown-menu .dropdown-menu {
                    padding-left: 20px;
                }

                    .dropdown-menu .dropdown-menu .dropdown-menu {
                        padding-left: 30px;
                    }

            li.dropdown.open {
                border: 0px solid red;
            }
        }

        @media (min-width: 768px) {
            ul.nav li:hover > ul.dropdown-menu {
                display: block;
            }

            #navbar {
                text-align: center;
            }
        }

        .navbar .dropdown-menu .active > a:hover, .navbar.navbar-default .dropdown-menu .active > a:hover, .navbar .dropdown-menu .active > a:focus, .navbar.navbar-default .dropdown-menu .active > a:focus {
            color: black;
        }
    </style>

    <style type="text/css">
        .dtp-header {
            background: var(--main-color) !important;
        }

        .dtp div.dtp-date, .dtp div.dtp-time {
            background: var(--main-color) !important;
        }

        .dtp .p10 > a {
            color: white; /*var(--main-color) !important*/
        }

        .dtp table.dtp-picker-days tr > td > a.selected {
            background: var(--main-color) !important;
        }
    </style>
		<!-- jQuery -->
		<script src="${pageContext.request.contextPath}/Resources/JQUERY/jquery-2.2.1.js"></script>
		<script src="${pageContext.request.contextPath}/Resources/bootstrap-3.3.6-dist/js/bootstrap.js"></script>
		<script src="${pageContext.request.contextPath}/Resources/Material-Deisgn/dist/js/material.js"></script>
		<!--<script src="Resources/Material-Deisgn/dropdown.js-master/jquery.dropdown.js"></script>-->
		<script src="${pageContext.request.contextPath}/Resources/Material-Deisgn/dist_select/js/bootstrap-select.js"></script>
		<script type="text/javascript" src="http://momentjs.com/downloads/moment-with-locales.min.js"></script>
		<script src="${pageContext.request.contextPath}/Resources/Material-Deisgn/dist_Datepicker/js/bootstrap-material-datetimepicker.js"></script>
	    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.5/angular.min.js"></script>
	    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular-messages/1.5.6/angular-messages.min.js"></script> 
		<script type="text/javascript" src="${pageContext.request.contextPath}/Resources/Angular/ngStorage.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/Company_Contact.js"></script>
		
		<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
		<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/Resources/Toaster/jquery.toaster.js"></script> --%>
		<script type="text/javascript" src="${pageContext.request.contextPath}/Resources/Toaster/toastr.js"></script>
	
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/Form_Valiation.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/Main.js"></script>
		
		<script type="text/javascript">
	
	
			$(document).ready(
					function() {
		
						//initialsie the material-deisgn
						$.material.init();
						 $('.ContactAdd').hide();
						 $('.AddressAdd').hide();
						
					$('#txt_effectivedate1, #txt_effectivedate').bootstrapMaterialDatePicker({
							time : false,
							Button : true,
							format : "DD/MM/YYYY"					
						}); 
						
						
					});
		</script>
	</head>
	<body>
	<!-- 	<div id="loderImg"> </div> -->
			
	
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
				<div class="pull-left" style="display: block; align=left">
			            <b>Customer Name :</b> Ntranga IT Services Pvt Ltd
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
								<li class="dropdown-submenu active">
									<a href="javascript:void(0)">Customer</a>
									<ul class="dropdown-menu" style="top: 10px;">
										<li class="active"><a href="${pageContext.request.contextPath}/CustomerController/CustomerList.view">Customer Details</a></li>
										<li><a href="${pageContext.request.contextPath}/CustomerController/CustomerAddressList.view"> Contact </a></li>												
									</ul>
								</li>
								<li class="dropdown-submenu"><a href="javascript:void(0)">Company</a>
									<ul class="dropdown-menu" style="top: 45px;">
										<li><a href="${pageContext.request.contextPath}/CompanyController/companyDetails.view">Company Details</a></li>
										<li><a href="${pageContext.request.contextPath}/CompanyController/CompanyAddressList.view"> Contact </a></li>												
									</ul>
								</li>
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
		<div id="Contact_app" ng-app="LMS_App" ng-controller="LMS_Contr">	
			<div class="container-fluid" style="margin-bottom: 35px;">
       			<div style="display: inline-block">
            		<h3 class="cls_plainheader">Company Contact</h3>
        		</div>
        		<div class="pull-right">
           		 	<span style="padding-top: 10px; cursor: pointer;"><i class="fa fa-question-circle fa-3x clsicon_help" aria-hidden="true" title="Help"></i></span>
        		</div>

    		</div>
    		
    		
	                		
    		<div class="container-fluid ContactGrid" >
				<div class="col-md-10 col-md-offset-1 well">	
					
					<!-- <div class="panel panel-default ">
	                 	<div class="sepr"> -->
	                     	<div class="col-md- 8 container">
			                    <div class="col-md-6"> <b>Company Code :</b>  {{companyCode}} </div>
			                    <div class="col-md-6"> <b>Company Name :</b>  {{companyName}} </div>
              				</div>

	       		 	<!-- 	</div> 
	        		</div> -->
	        	
		        	<br/>
		        								
					<div class="panel panel-default ">
	                 	<div class="sepr">
	                     	<h5 class="cls_plainheader panhead">Address Information</h5>
							<button class="btn btn-info btn-raised cls_plainheader panhead" class="btn btn-raised btn-primary  modelpopup"  ng-click="addAddressBtnAction($event)" > Add Address </button>
	            			<div>
		                		<table class="table table-striped table-hover " id="example">
					                <thead>
					                    <tr>
					                        <!-- <td data-type="id">S.No</td>  -->
					                        <td data-type="text">Address Type</td>
					                        <td data-type="text">Address 1</td>
					                        <td data-type="text">Address 2</td>
					                        <td data-type="text">City </td>
					                        <td data-type="text">State</td> 
					                       <!--  <td data-type="number">Mobile Number</td> -->
					                        <td data-type="text">Country</td>
					                        <td data-type="text">Pincode </td>
					                    </tr>
					                </thead>
			            		</table>
	            			</div>
	       		 		</div>
	       			</div>
		       		<br/>	 
		        	
	        		<div class="panel panel-default ">
	                 	 <div class="sepr">
	                     	<h5 class="cls_plainheader panhead">Contact Information</h5>
	     				 	<!-- <div class="pull-left" style="padding: 10px"> -->
								<button class="btn btn-info btn-raised cls_plainheader panhead" class="btn btn-raised btn-primary  modelpopup"  ng-click="addContactBtnAction($event)" > Add Contact </button>
						 	<!-- </div> -->
	            				<div>
			                		<table class="table table-striped table-hover " id="example1">
						                <thead>
						                    <tr>
						                        <td data-type="text">Contact Type</td>
						                        <td data-type="text">Contact Name</td>
						                        <td data-type="number">Phone Number </td>
						                        <!-- <td data-type="number">Mobile Number</td> -->
						                        <td data-type="email">Email</td>
						                        <td data-type="text" style="align:centre">Address </td>
						                    </tr>
						               </thead>
				            	</table>
	            			</div>
	            		</div>
	       		 	</div> 
		       		 
	        	</div>
	       </div> 		
	        	
	        
			<!--  <div class="container-fluid ContactGrid" >
				<div class="col-md-10 col-md-offset-1 well">
					<div class="panel panel-default ">
	                 	<div class="sepr">
	                     	<h5 class="cls_plainheader panhead">Contact Information</h5>
	     				 	<div class="pull-left" style="padding: 10px">
								<button class="btn btn-info btn-raised cls_plainheader panhead" class="btn btn-raised btn-primary  modelpopup"  ng-click="addContactBtnAction($event)" > Add Contact </button>
						 	</div>
	            				<div>
			                		<table class="table table-striped table-hover " id="example1">
						                <thead>
						                    <tr>
						                        <td data-type="text">Contact Type</td>
						                        <td data-type="text">Contact Name</td>
						                        <td data-type="number">Phone Number </td>
						                       
						                        <td data-type="number">Mobile Number</td>
						                        <td data-type="email">Email</td>
						                        <td data-type="text">Address </td>
						                    </tr>
						                </thead>
				            		</table>
	            				</div>
	            			
	       		 		</div> 
	        		</div>
	        	</div> -->
	        
	        
			<div class="container-fluid  ContactAdd" id ="companycontact" style="margin-bottom: 35px;">
				<form name = "companyContact" novalidate ng-submit="submit" class="form-horizontal">
					<div class="col-md-10 col-md-offset-1 well">
           				<ul class="nav nav-pills">
              				<li><a href="${pageContext.request.contextPath}/CompanyController/companyDetails.view">Company</a></li>
			                <li><a href="${pageContext.request.contextPath}/CompanyController/companyProfile.view">Profile</a></li>
			                <li class="active"><a href="${pageContext.request.contextPath}/CompanyController/CompanyAddressList.view">Contacts</a></li>
          				</ul>
           				<br/>
           				
           			
						<div class="col-md-12">
						<!-- 	<div class="panel panel-default">
	                    		<div class="sepr">
	                    		  <div class="row"> -->
									<div class="col-md- 8 container">
					                    <div class="col-md-6"> <b>Company Code :</b>  {{companyCode}} </div>
					                    <div class="col-md-6"> <b>Company Name :</b>  {{companyName}} </div>
	                				</div>
	                				<!-- </div>
	                			</div>
	                		</div> -->
	                	
                			<br />
                			<input type="hidden" value="0" ng-model="customerId" class="customerId">
							<input type="hidden" value="0" ng-model="companyId" class="companyId">
							<input type="hidden" value="0" ng-model="companyId" class="companyInfoId">
							
							<!-- <div class="panel panel-default">
	                    		<div class="sepr"> -->
									<div class="container-fluid transactionList1"> 
										<div class="col-md-6">
											<div class="form-group">
												<label class="col-md-4  control-label required" for="transactionList1"> Transaction Date: </label>
												<div class="col-md-8">
													<select id="transactionList1" class="form-control" title="Select date" ng-model="contactTransDate" name="contactTransDate" ng-change="fun_Contact_Trans_Change()" >	
													<option ng-repeat="dates in transactionDatesListForContact" selected={{ dates.id== addressTransDate }} ? "selected" : ""
														value="{{dates.id}}">{{dates.name}}</option>													
													</select>
												</div>					
											</div>
										</div>
									</div>
								<!-- </div>
							</div> -->
								
						
               				 <div class="panel panel-default">
	                    		<div class="sepr">
                        			<div class="row"> 
                           			 	<div class="col-md-6">
                                			<div class="form-group ">	
				               				 	<label class="col-md-4  control-label required" for="focusedInput1" style="color: black;">Transaction Date </label>
				                		 	 	<div class="col-md-8">
				                					<input class="form-control" id="txt_effectivedate1" type="text" placeholder="Pick a date" style="color: black;" ng-model="transactionDate1" name="transactionDate1" ng-required="true" ng-disabled="readonly"></input>
				                					<div ng-messages="companyAddress.transactionDate.$error" ng-if='submitted && companyAddress.transactionDate.$invalid' role="alert">
    													<div ng-message="required" class="errorMessage"> Select Transaction Date </div>
     												</div>
				                				</div>
				                			</div>
				                		</div>
				                		
				   					</div> 
					   			</div>
					   		</div>		
					   		<div class="panel panel-default">
	                    		<div class="sepr">			
					   				<div class="row"> 	
					   					 <div class="col-md-6">
					               			<div class="form-group required" >
					                   			<label class="col-md-4 control-label required" for="ddl_ContactType">Contact Type </label>
					                    		<div class="col-md-8">
					                    			<select id="ddl_ContactType" class="form-control" ng-disabled="readonly" placeholder="Select Contact Type" title="Select Contact"  ng-model="contactTypeId" name="contactTypeId" ng-required="true">                   
								                    	<c:forEach items="${contactTypes}" var="contacttype" varStatus="row"> 
								                        	<option  value="${contacttype.contactTypeId } " > ${contacttype.contactTypeName } </option>
								                     	</c:forEach>
						                   			</select>  
								                    <div ng-messages="companyContact.contactTypeId.$error" ng-if='submitted && companyContact.contactTypeId.$invalid' role="alert">
								    					<div ng-message="required" class="errorMessage"> Select Contact Type </div>
													</div>
												</div>   
					               		 	</div>
							             </div> 
							             
					 					 <div class="col-md-6">
					         				<div class="form-group required" >
								            	<label class="col-md-4 control-label required" for="txtContactName">Contact Name </label>
								                <div class="col-md-8">
								                    <input class="form-control onlyAlphabets" id="txtContactName" placeholder ="Contact Name"  ng-model="contactName" name="contactName" type="text" style="width:100%"  ng-readonly="readonly" ng-required="true" ng-maxlength="45"/>
									                <div ng-messages="companyContact.contactName.$error" ng-if='(submitted && companyContact.contactName.$invalid) || (companyContact.contactName.$touched)' role="alert">
							    						<div ng-show="companyContact.contactName.$error.required" class="errorMessage"> Please enter name</div> 
							    						<div ng-show="companyContact.contactName.$error.maxlength" class="errorMessage"> Contact name should have atleast 10 characters </div>
							    						<div ng-show="companyContact.contactName.$error.minlength" class="errorMessage"> This field allows maximium 45 characters </div>
									      			</div>
									      		</div>
								      		</div>
							      				<!-- {{submitted}}   {{ companyContact.contactName.$invalid}}  {{ companyContact.contactName.$touched}} -->
							            </div>
							        </div> 
							    </div>
							</div>         
					                
					        <div class="panel panel-default">
	                    		<div class="sepr">			
					   				<div class="row">
					   				     <div class="col-md-6"> 
						                	<div class="form-group">
							                    <label class="col-md-4 control-label" for="txtBusinessPhoneNumber">Business Phone</label>
							                    <div class="col-md-8">
							                    	<input class="form-control " id="txtBusinessPhoneNumber" ng-model="businessPhoneNumber" placeholder = "Business Phone" name="businessPhoneNumber" ng-required="!mobileNumber" ng-readonly="readonly" type="text" onkeypress='return event.charCode >= 48 && event.charCode <= 57' ng-maxlength ="12" style="width:100%" />
								                	 <div ng-messages="companyContact.businessPhoneNumber.$error"  role="alert">
								    					<div  ng-show="companyContact.businessPhoneNumberr.$error.required"  class="errorMessage"> Please enter Business phone number / mobile number</div> 
								    				</div>
								    			</div>
							    				<!-- {{submitted}}   {{ companyContact.businessPhoneNumber.$invalid}}  {{ companyContact.businessPhoneNumber.$touched}} -->
							                </div> 
							             </div> 
							              <div class="col-md-6"> 						                 
							                <div class="form-group">
							                    <label class="col-md-4 control-label" for="txtExtensionNumber">Extension</label>
							                    <div class="col-md-8">
							                    	<input class="form-control" id="txtExtensionNumber" ng-model="extensionNumber" placeholder= "Extension Number" name="extensionNumber" ng-readonly="readonly" type="text" onkeypress='return event.charCode >= 48 && event.charCode <= 57' ng-maxlength ="5" style="width:100%" />
							                    </div>
							                </div>     
						                </div>
					                	 <div class="col-md-6">
							                <div class="form-group" >
							                    <label class=" col-md-4 control-label" for="txtMobileNumber required">Mobile Number</label>
							                    <div class="col-md-8">
							                    	<input class="form-control " id="txtMobileNumber" placeholder = "Mobile Number" ng-model="mobileNumber" type="text" name="mobileNumber" ng-readonly="readonly"  onkeypress='return event.charCode >= 48 && event.charCode <= 57' ng-minlength ="10" ng-maxlength="12"  style="width:100%" ng-required="!businessPhoneNumber" />
								                     <div ng-messages="companyContact.mobileNumber.$error" ng-if='(submitted && companyContact.mobileNumber.$invalid) || (companyContact.mobileNumber.$touched)' role="alert">
								     					<div  ng-show="companyContact.mobileNumber.$error.minlength" class="errorMessage"> Mobile number should have atleast 10 digits </div>
								    					<div  ng-show="companyContact.mobileNumber.$error.maxlength" class="errorMessage"> This field allows maximium 12 digits  </div>
								      				</div> 
								      			</div>
							      				<!-- {{submitted}}   {{ companyContact.mobileNumber.$invalid}}  {{ companyContact.mobileNumber.$touched}} -->
							                </div>
						                 </div>
						                
						            </div>
						        </div>
						     </div> 
						                
						                 
						     <div class="panel panel-default">
	                    		<div class="sepr">			
					   				<div class="row">
					   				     <div class="col-md-6">            
							                <div class="form-group required" >
							                    <label class="col-md-4 control-label" for="txtEmailId">Email Id </label>
							                    <div class="col-md-8">
							                   		<input class="form-control" id="txtEmailId" placeholder= "Email Id" ng-model="emailId"  name="emailId" ng-readonly="readonly"  type="email" style="width:100%" ng-maxlength="45" required />
							                    	<div ng-messages="companyContact.emailId.$error" ng-if='(submitted && companyContact.emailId.$invalid) || (companyContact.emailId.$touched)' role="alert">
								    					<div  ng-show="companyContact.emailId.$error.required"  class="errorMessage"> Please enter Email Id</div> 
								    					<div  ng-show="companyContact.emailId.$error.email" class="errorMessage"> Please enter valid Email Id</div> 
								    					<div  ng-show="companyContact.emailId.$error.maxlength" class="errorMessage"> This field allows maximium 45 characters </div>
							      					</div> 
							                    </div>
							                </div>
							                  
						                  </div> 
						                  
						                  <div class="col-md-6">
				                			<div class="form-group cls_divradios" >
							               		<label class="col-md-4  control-label cls_lblradios required">Status</label>
							                    <div class="col-lg-8 " ">
													<div class="radio radio-primary" style="display: inline-block">
														<label class="col-md-4  control-labe"> <input type="radio" name="isActive"  ng-required="true" id="optionsRadios1" value="Y"  ng-model="isActive"  name="isActive"  ng-checked="isActive == 'Y'">Active
														</label>
													</div>
													<div class="radio radio-primary" style="display: inline-block">
														<label class="col-md-4  control-labe"> <input type="radio" name="isActive"  ng-required="true" id="optionsRadios2" value="N" ng-model="isActive"  name="isActive" ng-checked="isActive == 'N'">Inactive
														</label>
													</div>
													<div ng-messages="companyContact.isActive.$error" ng-if='submitted && companyContact.isActive.$invalid' role="alert">
					      								<div ng-message="required" class="errorMessage"> Select isActive </div>      														
					      							</div>
												</div>
						            		</div> 
				                		</div> 
						              </div>
						         </div>  
						      </div>               
							                <!-- <div class="form-group cls_divradios" style="margin-top: -30px;">
							               		<label class="col-lg-4 col-md-4 col-sm-5 col-xs-6 control-label cls_lblradios required">Status</label>
							                    <div class="col-lg-8 " style="margin-top: 15px;">
													<div class="radio radio-primary" style="display: inline-block">
														<label> <input type="radio" name="isActive"  ng-required="true" id="optionsRadios1" value="Y"  ng-model="isActive"  name="isActive"  ng-checked="isActive == 'Y'">Yes
														</label>
													</div>
													<div class="radio radio-primary" style="display: inline-block">
														<label> <input type="radio" name="isActive"  ng-required="true" id="optionsRadios2" value="N" ng-model="isActive"  name="isActive" ng-checked="isActive == 'N'"> No
														</label>
													</div>
														{{isActive}}
												</div>
												<div ng-messages="companyContact.isActive.$error" ng-if='submitted && companyContact.isActive.$invalid' role="alert">
							      					<div ng-message="required" class="errorMessage"> Select isActive </div>      														
							      				</div>
							               	</div>  -->
						     <div class="panel panel-default">
	                    		<div class="sepr">			
					   				<div class="row">
					   				     <div class="col-md-6">         	
							               	<div class="form-group">
							                    <label class="col-md-4 control-label required" for="ddlAddress">Address </label>
							                    <div class="col-md-8">
							                   		<select id="ddlAddress" class="form-control"  ng-disabled="readonly"  title = "Select Address" ng-model="addressId"  name="addressId" ng-required="true"> 
							                    		<option ng-repeat="address in addressListData" value="{{address.id}}">{{address.name}}</option>
							                    	</select>
								                    <div ng-messages="companyContact.addressId.$error" ng-if='submitted && companyContact.addressId.$invalid' role="alert">
								    					<div ng-message="required" class="errorMessage"> Select Address </div>
													</div> 
												</div>
							                </div>
					         			</div>
					         		</div>
					         	</div>	
					         </div> 
					    </div>
	
					  <div class="btn-group btn-group-right">
	                    	<a href="javascript:void(0)" class="btn btn-raised btn-danger updateContactBtn" ng-click="updateContactBtnAction($event)"> Update </a>
	                        <button class="btn btn-raised btn-primary saveContactBtn" type="submit" ng-click="submitted = true;companyContact.$valid && fun_savecompanycontactdetails($event)" onclick="return false;">Save</button>
	                        <button class="btn btn-raised btn-primary currentContactHistoryBtn" type="submit" ng-click="submitted = true;companyContact.$valid && fun_updatecompanycontactdetails($event)" onclick="return false;"> Correct History </button>
	                        <button class="btn btn-raised btn-danger  resetContactBtn "  type="reset"> Reset </button> 
	                        <button class="btn btn-raised btn-danger cancelContactBtn" ng-click="cancelContactBtnAction($event)">Cancel</button>
	                        <a href="javascript:void(0)" class="btn btn-raised btn-info ContactHistory"  ng-click="historyContactBtnAction($event)"> View History </a>
					   </div>					   
					   <div class="container-fluid">
            				<div class="navbar navbar-default navbar-fixed-bottom text-center" style="min-height: 15px; padding: 0px;">
				                <div class="container text-right" style="">
				                    <label class="control-label text-center" style="color: white; margin: 0px; font-size: 14px;">@Copyright</label>
				                </div>
            				</div>
        			  </div>
        			</div>
        		</form>
        	</div>
        	
        	<div class="container-fluid  AddressAdd" id ="companyaddress" style="margin-bottom: 35px;">					
				<form name="companyAddress" novalidate ng-submit="submit" class="form-horizontal">
					<div class="col-md-10 col-md-offset-1 well">
           				<ul class="nav nav-pills">
              				<li ><a href="${pageContext.request.contextPath}/CompanyController/companyDetails.view">Company</a></li>
               				<li><a href="${pageContext.request.contextPath}/CompanyController/companyDetails.view">Profile</a></li>
               				<li class="active"><a href="${pageContext.request.contextPath}/CompanyController/CompanyAddressList.view">Contacts</a></li>
          				</ul>
           				<br/>	
           				
           				
						
						
						<div class="col-md-12">
							
							<div class="container">
			                    <div class="col-md-6"> <b>Company Code :</b>  {{companyCode}} </div>
			                    <div class="col-md-6"> <b>Company Name :</b>  {{companyName}} </div>
			                    
	                		</div>
	                		
                			<br />
                			
							<!-- <div class="panel panel-default">
	                    		<div class="sepr"> -->		
	                    			<div class="col-md-6">					
										<div class="container-fluid transactionList"> 
											<div class="form-group">
												<label class="col-md-4 control-label required" for="transactionList"> Transaction Date: </label>
												<div class="col-md-8">
													<select id="transactionList" class="form-control" 
														title="Select date" ng-model="addressTransDate" name="addressTransDate"  ng-change="fun_Address_Trans_Change()" >	
														<option ng-repeat="dates in transactionDatesList" selected= {{ dates.id ==  addressTransDate }} ? "selected" : ""
															value="{{dates.id}}">{{dates.name}}</option>													
													</select>
												</div>						
											</div>
										</div>
									</div>
								<!-- </div>
							</div> -->
               				<div class="panel panel-default">
	                    		<div class="sepr"> 
                        			<div class="row"> 
                           			 	<div class="col-md-6">		
											<div class="form-group">
												<label class="col-md-4 control-label required" for="txt_effectivedate" style="color: black;">Transaction Date </label>
												<div class="col-md-8">
													<input class="form-control class_date1" id="txt_effectivedate" type="text" placeholder="Pick a date" style="color: black;display:inline-block" ng-model="transactionDate" name="transactionDate"  ng-required="true" ng-disabled="datesReadOnly"  />
													<div ng-messages="customerAddress.transactionDate.$error" ng-if='submitted && companyAddress.transactionDate.$invalid' role="alert">
							      						<div ng-message="required" class="errorMessage"> Select Transaction Date </div>
							     					</div>
							     				</div>
					     					</div>					
										</div>
									</div>
								 </div>
							</div> 
							
							<input type="hidden" value="0" ng-model="customerDetailsId" class="customerDetailsId">
							<input type="hidden" value="0" ng-model="companyId" class="companyId">
							<input type="hidden" value="0" ng-model="companyId" class="companyInfoId">
							<input type="hidden" value="0" ng-model="customerName" class="customerName">
							
							<div class="panel panel-default">
	                    		<div class="sepr">
                        			<div class="row"> 
                           			 	<div class="col-md-6">		
											<div class="form-group required">	
												<label class="col-md-4 control-label required" for="ddl_AddressType">Address Type </label> 
												<div class="col-md-8">
													<select id="ddl_AddressType" ng-disabled="readonly" class="form-control" title="Select Address" ng-model="addressTypeId" name="addressTypeId"  ng-required="true">
														<c:forEach items="${addressTypes}" var="addresstype" varStatus="row">
															<option value="${addresstype.addressTypeId*1} ">${addresstype.addressTypeName}</option>
														</c:forEach>							
													</select>
													<div ng-messages="companyAddress.addressTypeId.$error" ng-if='submitted && companyAddress.addressTypeId.$invalid' role="alert">
							      						<div ng-message="required" class="errorMessage"> Select Address Type </div>
							     					</div>
							     				</div>       											
											</div>
										</div>
										
										<div class="col-md-6">
				                			<div class="form-group cls_divradios" >
							               		<label class="col-md-4  control-label cls_lblradios required">Status</label>
							                    <div class="col-lg-8 " ">
													<div class="radio radio-primary" style="display: inline-block">
														<label class="col-md-4  control-labe"> <input type="radio" name="isActive"  ng-required="true" id="optionsRadios1" value="Y"  ng-model="isActive"  name="isActive"  ng-checked="isActive == 'Y'">Active </label>
														</div>
													<div class="radio radio-primary" style="display: inline-block">
														<label class="col-md-4  control-labe"> <input type="radio" name="isActive"  ng-required="true" id="optionsRadios2" value="N" ng-model="isActive"  name="isActive" ng-checked="isActive == 'N'"> Inactive </label>
													</div>
													<div ng-messages="companyAddress.isActive.$error" ng-if='submitted && companyAddress.isActive.$invalid' role="alert">
						      							<div ng-message="required" class="errorMessage"> Select isActive </div>      														
						      						</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
									
							<div class="panel panel-default">
	                    		<div class="sepr">
                        			<div class="row"> 
                           			 	<div class="col-md-6">		
											<div class="form-group required">		
												<label class="col-md-4 control-label" for="txtAddressLine1">Address Line 1 </label> 
												<div class="col-md-8">
													<input class="form-control " id="txtAddressLine1" placeholder="Address Line1"  maxlength="50"  name="address1" readonly="readonly" ng-model="address1" type="text" style="width: 100%"  ng-required="true" ng-readonly="readonly" />	
													<div ng-messages="customerAddress.address1.$error" ng-if='submitted && companyAddress.address1.$invalid ' role="alert">
						      							<div ng-message="required" class="errorMessage">Address1 required</div>      														
						      						</div>
												</div>
											</div>
									 	</div>
									 	
									 	<div class="col-md-6">		
											<div class="form-group">		
												<label class="col-md-4 control-label" for="txtAddressLine2">Address Line 2 </label> 
												<div class="col-md-8">
													<input class="form-control " id="txtAddressLine2" maxlength="50" placeholder="Address Line2" ng-model="address2" type="text" style="width: 100%"  ng-readonly="readonly" />
												</div>
											</div>
									 	</div>
									 	
									 	<div class="col-md-6">		
											<div class="form-group">		
												<label class="col-md-4 control-label" for="txtAddressLine3">Address Line 3 </label> 
												<div class="col-md-8">
													<input class="form-control " id="txtAddressLine3" maxlength="50" placeholder="Address Line3" ng-model="address3" type="text" style="width: 100%" ng-readonly="readonly" />
												</div>
											</div>
									 	</div>
									 </div>
								</div>
							</div>	
									
							<div class="panel panel-default">
	                    		<div class="sepr">
                        			<div class="row"> 
                           			 	<div class="col-md-6">		
											<div class="form-group required">
												<label class="col-md-4 control-label required" for="ddlCountry">Country </label>
												<div class="col-md-6">
													<select id="ddlCountry" ng-disabled="readonly" class="form-control" title="Select Country" ng-model="country" name="country"ng-change="fun_Countrychange()" ng-required="true">	
														<option ng-repeat="country in list_countries" value="{{country.id*1}}">{{country.name}}</option>													
													</select>
													<div ng-messages="companyAddress.country.$error" ng-if='submitted && companyAddress.country.$invalid' role="alert">
						      							<div ng-message="required" class="errorMessage"> Select Country </div>
						      						</div>
												</div>
											</div>
										</div>
										
										<div class="col-md-6">		
											<div class="form-group required">
												<label class="col-md-4 control-label required" for="ddlState">State </label> 
												<div class="col-md-6">
													<select id="ddlState" ng-disabled="readonly" class="form-control" title="Select State" ng-model="state" name="state" ng-change="fun_Statechange()"  ng-required="true">
														<option ng-repeat="states in list_states" value="{{states.stateId*1}}">{{states.stateName}}</option>
													</select>
													<div ng-messages="companyAddress.state.$error" ng-if='submitted && companyAddress.state.$invalid' role="alert">
						    							<div ng-message="required" class="errorMessage"> Select State </div>
													</div>
												</div>
											</div>
										</div>
										
										<div class="col-md-6">		
											<div class="form-group required">
												<label class="col-md-4 control-label required" for="ddlcity">City </label> 
												<div class="col-md-6">
													<select id="ddlcity" ng-disabled="readonly" class="form-control" title="Select City" ng-model="city" name="city" ng-required="true" >
														<option ng-repeat="cities in list_cities" value="{{cities.cityyId*1}}">{{cities.cityName}}</option>
													</select>
													<div ng-messages="companyAddress.city.$error" ng-if='submitted && companyAddress.city.$invalid' role="alert">
						    							<div ng-message="required" class="errorMessage">Select City </div> 
												</div>
												</div>
												
											</div>
										</div>
										
										<div class="col-md-6">		
											<div class="form-group required">
												<label class="col-md-4 control-label required" for="pincode">Pincode </label>
												<div class="col-md-6">
													<input class="form-control  onlyNumbers" id="pincode" type="text" placeholder="Pincode" ng-minlength="6"    ng-maxlength="6" ng-model="pincode" name="pincode"  ng-required="true" ng-readonly="readonly" />
												    <div ng-messages="companyAddress.pincode.$error" ng-if='(submitted && companyAddress.pincode.$invalid) || (companyAddress.pincode.$touched)' role="alert">
						      							<div ng-show="companyAddress.pincode.$error.required" class="errorMessage">Pincode required</div> 
						      							<div ng-show="companyAddress.pincode.$error.maxlength" class="errorMessage">Pincode should be 6 digits</div>
						      							<div ng-show="companyAddress.pincode.$error.minlength" class="errorMessage"> Pincode should be 6 digits</div>
					      							</div> 
					      						</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							
							<div class="btn-group btn-group-right">
		                    	<a href="javascript:void(0)" class="btn btn-raised btn-danger updateBtn" ng-click="updateAddressBtnAction($event)"> Update </a>
								<button class="btn btn-raised btn-primary saveBtn" ng-click=" submitted = true; companyAddress.$valid &&  fun_saveaddressdetails($event)" onclick="return false;">Save</button> 
								<button class="btn btn-raised btn-primary currentHistoryBtn" type="submit" ng-click="submitted = true; companyAddress.$valid && fun_updateaddressdetails($event)" onclick="return false;"> Correct History </button>						
								<button class="btn btn-raised btn-danger  resetBtn" type="reset"> Reset </a> 
								<button type="button" class="btn btn-raised btn-danger cancelBtn" ng-click="cancelBtnAction($event)"> Cancel </button>
								<a href="javascript:void(0)" class="btn btn-raised btn-info addrHistory" ng-click="historyBtnAction($event)"> View History </a>
							</div>	
											   
					   		<div class="container-fluid">
            					<div class="navbar navbar-default navbar-fixed-bottom text-center" style="min-height: 15px; padding: 0px;">
				                	<div class="container text-right" style="">
				                    	<label class="control-label text-center" style="color: white; margin: 0px; font-size: 14px;">@Copyright</label>
				                	</div>
            					</div>
        			  		</div>
        				</div>
        			</form>
				</div>
			</div>
		</div>
	</body>
</html>			 
							

										
								