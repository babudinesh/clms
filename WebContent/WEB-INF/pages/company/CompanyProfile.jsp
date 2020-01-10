<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
        <!-- Material Design fonts -->
<link rel="stylesheet" 	href="http://fonts.googleapis.com/css?family=Roboto:300,400,500,700" 	type="text/css" />
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" 	rel="stylesheet" />
<link 	href="${pageContext.request.contextPath}/Resources/font-awesome-4.6.3/css/font-awesome.css" rel="stylesheet" />

<!-- Bootstrap -->
<link 	href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" 	rel="stylesheet" />

<!-- Bootstrap Material Design -->
<link 	href="${pageContext.request.contextPath}/Resources/Material-Deisgn/dist/css/bootstrap-material-design.css" 	rel="stylesheet" />
<link 	href="${pageContext.request.contextPath}/Resources/Material-Deisgn/dist/css/bootstrap-material-design-inverse.css" rel="stylesheet" />

<link 	href="${pageContext.request.contextPath}/Resources/Material-Deisgn/dist/css/ripples.min.css" 	rel="stylesheet" />
<link 	href="${pageContext.request.contextPath}/Resources/Material-Deisgn/dist_select/css/bootstrap-select.css" 	rel="stylesheet" />
<link 	href="${pageContext.request.contextPath}/Resources/Material-Deisgn/dist_Datepicker/css/bootstrap-material-datetimepicker.css" 	rel="stylesheet" />

<link   href="https://cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css" 	rel="stylesheet" />
<link 	href="${pageContext.request.contextPath}/css/customerAddress.css" rel="stylesheet" />
<link 	href="${pageContext.request.contextPath}/Resources/Toaster/toastr.css" 	rel="stylesheet" />
<link 	href="${pageContext.request.contextPath}/css/Styles.css" 	rel="stylesheet" />	


    <!--Declare a css variables for reuse-->



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
        .hidden{
        display: none;
        }
    </style>

<!-- jQuery -->
<script type="text/javascript"  src="${pageContext.request.contextPath}/Resources/JQUERY/jquery-2.2.1.js"></script>
<script type="text/javascript"  src="${pageContext.request.contextPath}/Resources/bootstrap-3.3.6-dist/js/bootstrap.js"></script>
<script	type="text/javascript"  src="${pageContext.request.contextPath}/Resources/Material-Deisgn/dist/js/material.js"></script>
<script	type="text/javascript"  src="${pageContext.request.contextPath}/Resources/Material-Deisgn/dist_select/js/bootstrap-select.js"></script>
<script type="text/javascript"  src="http://momentjs.com/downloads/moment-with-locales.min.js"></script>
<script type="text/javascript"  src="${pageContext.request.contextPath}/Resources/Material-Deisgn/dist_Datepicker/js/bootstrap-material-datetimepicker.js"></script>
<script type="text/javascript"  src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.5/angular.min.js"></script>
<script type="text/javascript"  src="https://cdnjs.cloudflare.com/ajax/libs/angular-messages/1.5.6/angular-messages.min.js"></script> 
<script type="text/javascript"  src="${pageContext.request.contextPath}/Resources/Angular/ngStorage.min.js"></script>
<script type="text/javascript" 	src="${pageContext.request.contextPath}/js/Company_Profile.js"></script>
<script type="text/javascript"  src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" 	src="${pageContext.request.contextPath}/Resources/Toaster/toastr.js"></script>
<script type="text/javascript" 	src="${pageContext.request.contextPath}/js/Form_Valiation.js"></script>
<script type="text/javascript" 	src="${pageContext.request.contextPath}/js/Main.js"></script>

    <script type="text/javascript">
        $(document).ready(function () {
        	
        	$('.saveBtn').hide();
    		$('.updateBtn').show();		    		    		
    		
            //initialsie the material-deisgn
            $.material.init();

            
            $('#bussinessEndTime,#bussinessStartTime').bootstrapMaterialDatePicker({ format : 'HH:mm A',date: false, clearButton: true,shortTime: true }); 
            
           /*  $('#date,#date1,#padedate,#pfsdate,#pfrdate,#esisdate').bootstrapMaterialDatePicker
			({
			    time: false,
			    clearButton: true
			}); */


            //$('[data-submenu]').submenupicker();
            //initialise the material deisgn multiselect
            //$('#ddl_countryname, #ddl_countryofoper,#ddl_IndSectors').dropdown({ "dropdownClass": "my-dropdown", "optionClass": "my-option awesome" });
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
                        <a href="javascript:void(0)" data-submenu data-toggle="dropdown">Organistion
                        <b class="caret"></b></a>
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
                    <li><a href="javascript:void(0)">Contractor</a></li>
                    <li><a href="javascript:void(0)">Compliances</a></li>
                </ul>
                <form class="navbar-form navbar-left">
                    <div class="form-group">
                    </div>
                </form>
                <ul class="nav navbar-nav navbar-right">
                    <!--<li>
                        <input type="text" class="form-control col-md-8" placeholder="Search In th website" />
                    </li>-->
                    <!--<li class="dropdown">
                        <a href="bootstrap-elements.html" data-target="#" class="dropdown-toggle" data-toggle="dropdown">Modules <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a href="AskHR.aspx">Ask HR</a></li>
                            <li><a href="AssetManagement.aspx">Asset Management</a></li>
                            <li><a href="CoreHR.aspx">Employee CoreHR</a></li>
                            <li><a href="ExitMgmt.aspx">Exit Management</a></li>
                            <li><a href="ExpenseMgmt.aspx">Expense Management</a></li>
                            <li><a href="LeaveMgmt.aspx">Leave Management</a></li>
                            <li><a href="PayrollMgmt.aspx">Payroll Management</a></li>
                            <li><a href="PerformanceMgmt.aspx">Performance Management</a></li>
                            <li><a href="TimeMgmt.aspx">Time Management</a></li>
                            <li><a href="TravelMgmt.aspx">Travel Management</a></li>
                            <!--<li class="divider"></li>
                        </ul>
                    </li>-->
                </ul>
            </div>
        </div>
    </div>
<div id="Company_app" ng-app="LMS_App" ng-controller="LMS_Contr">
    <div class="container-fluid" style="margin-bottom: 35px;">
        <div style="display: inline-block">
            <h3 class="cls_plainheader">Company Profile</h3>
        </div>
        <div class="pull-right">
            <span style="padding-top: 10px; cursor: pointer;"><i class="fa fa-question-circle fa-3x clsicon_help" aria-hidden="true" title="Help"></i></span>
        </div>



    </div>
<form name="CompanyProfile" novalidate ng-submit="submit">
    <div class="container-fluid">
        <div class="col-md-10 col-md-offset-1 well">
             <ul class="nav nav-pills">
                <li><a href="${pageContext.request.contextPath}/CompanyController/companyDetails.view">Company</a></li>
                <li  class="active"> <a href="${pageContext.request.contextPath}/CompanyController/companyProfile.view">Profile</a></li>
                <li><a href="${pageContext.request.contextPath}/CompanyController/CompanyAddressList.view">Contacts</a></li>

            </ul>
            <br />
            <div class="col-md-12">
                <div class="container">
                    <div class="col-md-4">Company ID    {{companyCode}}</div>
                    <div class="col-md-4">Company Name  {{companyName}}</div>
                    <div class="col-md-4">Country Name  {{countryName}}</div>
                </div>
                <br />
                <div class="panel panel-default">
                    <div class="sepr">
                        <h5 class="cls_plainheader">Company details</h5>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="defaultCurrencyId" class="col-md-4 control-label">Default Currency</label>
                                    <div class="col-md-8">
                                       <select id="defaultCurrencyId" class="form-control" ng-model="defaultCurrencyId" name="defaultCurrencyId"  ng-required="true" ng-disabled="readOnly">
                                             <c:forEach items="${currency}" var="curency"> 
                                            	<option value="${curency.currencyId}"> ${curency.currency}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group" style="margin-left: 30px">
                                    <div class="checkbox">
                                        <label>
                                            <input type="checkbox"  ng-model="isMultiCurrency" name="isMultiCurrency" ng-required="true" ng-disabled="readOnly" ng-change="fun_CurrencyChange()" />
                                            Multi Currency
                                        </label>
                                    </div>
                                </div>

                                <div class="form-group currencyDiv">
                                    <label for="currencys" class="col-md-4 control-label">Currency </label>
                                    <div class="col-md-8">
                                    <select id="currencys" class="form-control selectpicker selectCurrencypicker"
										data-live-search="true" data-live-search-placeholder="Search"
										data-actions-box="true" title="Please select  Currency"  ng-model="currencys" name="currencys" multiple ng-required="true" ng-disabled="readOnly" >
										<c:forEach items="${currency}" var="curency"> 
                                            	<option value="${curency.currencyId}"> ${curency.currency}</option>
                                            </c:forEach>
									</select>
                                        <%-- <select id="currencys" class="form-control" ng-model="currencys" name="currencys" multiple ng-required="true" ng-disabled="readOnly">
                                            <c:forEach items="${currency}" var="curency"> 
                                            	<option value="${curency.currencyId}"> ${curency.currency}</option>
                                            </c:forEach>
                                        </select> --%>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="minAge" class="col-md-4 control-label">Min Age</label>
                                    <div class="col-md-8">
                                        <input type="number" class="form-control" id="minAge" placeholder="Min Age" ng-model="minAge" name="minAge" multiple ng-required="true" ng-disabled="readOnly" />
                                    </div>
                                </div>
                                 <div class="form-group">
                                    <label for="retireAge" class="col-md-4 control-label">Retirement Age</label>
                                    <div class="col-md-8">
                                        <input type="number" class="form-control" id="retireAge" placeholder="Retirement Age" ng-model="retireAge" name="retireAge" multiple ng-required="true" ng-disabled="readOnly"/>
                                    </div>
                                </div>
                            </div>

                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="officialLanguageId" class="col-md-4 control-label">Official Language</label>
                                    <div class="col-md-8">
                                       <select id="officialLanguageId" class="form-control" ng-model="officialLanguageId" name="officialLanguageId"  ng-required="true" ng-disabled="readOnly">
                                            <c:forEach items="${languages}" var="language"> 
                                            	<option value="${language.languageId}"> ${language.language}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group" style="margin-left: 30px">
                                    <div class="checkbox">
                                        <label>
                                            <input type="checkbox" ng-model="isMultiLanguage" name="isMultiLanguage" ng-required="true" ng-disabled="readOnly" ng-change="fun_LanguageChange()" />
                                            Multi Language
                                        </label>
                                    </div>
                                </div>

                               <div class="form-group langdiv">
                                    <label for="languages" class="col-md-4 control-label">Language {{languages}}</label>
                                    <div class="col-md-8">
                                        <select id="languages" class="form-control selectpicker selectLaguagepicker"
										data-live-search="true" data-live-search-placeholder="Search"
										data-actions-box="true" title="Please select  languages"  ng-model="languages" name="languages" multiple ng-required="true" ng-disabled="readOnly">
                                            <c:forEach items="${languages}" var="language"> 
                                            	<option value="${language.languageId}"> ${language.language}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                            </div>

                                <div class="form-group">
                                    <label for="maxAge" class="col-md-4 control-label">Max Age</label>
                                    <div class="col-md-8">
                                        <input type="number" class="form-control" id="maxAge" placeholder="Max Age" ng-model="maxAge" name="maxAge" ng-required="true" ng-disabled="readOnly" />
                                    </div>
                                </div>
                        </div>
                    </div>
                </div>


            </div>

            <br />
          <div class="panel panel-default">
                    <div class="sepr">
                        <h5 class="cls_plainheader">Standard workng hours & shift details </h5>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="workWeekStartId" class="col-md-6 control-label">Work week start day</label>
                                    <div class="col-md-6">
                                        <select id="workWeekStartId" class="form-control" ng-model="workWeekStartId" name="workWeekStartId" ng-required="true" ng-disabled="readOnly">
                                            <option value="1">MONDAY</option>
                                            <option value="2">TUESDAY</option> 
                                            <option value="3">WEDNESDAY</option>
                                            <option value="4">THURSDAY</option>
                                            <option value="5">FRIDAY</option>
                                            <option value="6">SATURDAY</option>
                                            <option value="7">SUNDAY</option>                                                                                 
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="workWeekEndId" class="col-md-6 control-label">Work week end day</label>
                                    <div class="col-md-6">
                                        <select id="workWeekEndId" class="form-control" ng-model="workWeekEndId" name="workWeekEndId" ng-required="true" ng-disabled="readOnly">
                                            <option value="1">MONDAY</option>
                                            <option value="2">TUESDAY</option> 
                                            <option value="3">WEDNESDAY</option>
                                            <option value="4">THURSDAY</option>
                                            <option value="5">FRIDAY</option>
                                            <option value="6">SATURDAY</option>
                                            <option value="7">SUNDAY</option>                                           
                                        </select>
                                    </div>
                                </div>                                


                                <div class="form-group">
                                    <label for="bussinessHoursPerDay" class="col-md-6 control-label">Business hours/Day</label>
                                    <div class="col-md-6">
                                       <input type="number" class="form-control" id="bussinessHoursPerDay" placeholder="Business hours/Day" ng-model="bussinessHoursPerDay" name="bussinessHoursPerDay" ng-required="true" ng-disabled="readOnly" />
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="bussinessStartTime" class="col-md-6 control-label">Business start time</label>
                                    <div class="col-md-6">
                                       <input type="text" class="form-control" id="bussinessStartTime" placeholder="Business start time" ng-model="bussinessStartTime" name="bussinessStartTime" ng-required="true" ng-disabled="readOnly"/>
                                    </div>
                                </div>

                                <!-- <div class="form-group">
                                    <label for="holcal" class="col-md-6 control-label">Holiday Calendar</label>
                                    <div class="col-md-6">
                                       <input type="text" class="form-control" id="holcal" placeholder="Holiday Calendar" />
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="shisys" class="col-md-6 control-label">Shift System</label>
                                    <div class="col-md-6">
                                       <input type="text" class="form-control" id="shisys" placeholder="Shift System" />
                                    </div>
                                </div> -->
                                
                            </div>

                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="numberOfWorkingDays" class="col-md-6 control-label">Num of standard work days</label>
                                    <div class="col-md-6">
                                       <input type="number" class="form-control" id="numberOfWorkingDays" placeholder="Num of standard work days" ng-model="numberOfWorkingDays" name="numberOfWorkingDays" ng-required="true" ng-disabled="readOnly" />
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="standarHoursPerWeek" class="col-md-6 control-label">Standard Work/hours</label>
                                    <div class="col-md-6">
                                       <input type="number" class="form-control" id="standarHoursPerWeek" placeholder="Standard Work/hours" ng-model="standarHoursPerWeek" name="standarHoursPerWeek" ng-required="true" ng-disabled="readOnly"/>
                                    </div>
                                </div>

                               <!--  <div class="form-group">
                                    <label for="shftid" class="col-md-6 control-label">shift ID</label>
                                    <div class="col-md-6">
                                       <input type="text" class="form-control" id="shftid" placeholder="shift ID" />
                                    </div>
                                </div> -->

                                 <div class="form-group">
                                    <label for="bussinessEndTime" class="col-md-6 control-label">Business End Time</label>
                                    <div class="col-md-6">
                                       <input type="text" class="form-control" id="bussinessEndTime" placeholder="Business End Time" ng-model="bussinessEndTime" name="bussinessEndTime" ng-required="true" ng-disabled="readOnly"/>
                                    </div>
                                </div>
                        </div>
                    </div>
                </div>


            </div>


            <div class="btn-group btn-group-right ">
            	<a href="javascript:void(0)" class="btn btn-raised btn-success updateBtn" ng-click="updateCompanyProfileBtnAction($event)"> Update </a>
				<button class="btn btn-raised btn-primary saveBtn"  ng-click=" submitted = true; CompanyProfile.$valid &&  fun_savecompanyprofile($event)" onclick="return false;">Save</button> 				
				<a href="${pageContext.request.contextPath}/CompanyController/companyDetails.view" class="btn btn-raised btn-warning cancelBtn" > Return to Company search</a>
				<%-- <button type="button" class="btn btn-raised btn-warning cancelBtn" ng-click="${pageContext.request.contextPath}/CompanyController/companyDetails.view"> Return to Company search </button>	 --%>			
            </div>

        </div>

</div>
</div>
</form>
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
