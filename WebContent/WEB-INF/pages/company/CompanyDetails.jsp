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
            float:right;
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
        .errorMessage {
		    background: maroon;
		    color: white;
		    padding: 5px;
		    border-radius: 5px;
		    position: absolute;
		    z-index: 99;
		    right: 0px;
		    top: -22px;
		}
        .errorMessage:after {
		    position: absolute;
		    top: 10px;
		    content: "";
		    left: -10px;
		    border-style: solid;
		    border-color: transparent transparent maroon;
		    border-width: 5px;
		    transform: rotate(270deg);
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
<script type="text/javascript" 	src="${pageContext.request.contextPath}/js/Company_Details.js"></script>
<script type="text/javascript"  src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" 	src="${pageContext.request.contextPath}/Resources/Toaster/toastr.js"></script>
<script type="text/javascript" 	src="${pageContext.request.contextPath}/js/Form_Valiation.js"></script>
<script type="text/javascript" 	src="${pageContext.request.contextPath}/js/Main.js"></script>

    <script type="text/javascript">
        $(document).ready(function () {
        	
        	
        	
        	
        	 $('.parentGrid').show(500);
        	$('.childGrid').hide(500);
        	
        	$('#rettosearch').click(function(){
        		
        		$('.parentGrid').show(500);
            	$('.childGrid').hide(500);
            	
        	});
        	$('.parentGrid table tbody tr').click(function(){
	        	$('.parentGrid').hide(500);
	        	$('.childGrid').show(500);
        	
        	}); 
            //initialsie the material-deisgn
            $.material.init();
            var dt_obj = $('.table').DataTable();
            $('#transactionDate,#registrationDate,#panRegistrationDate,#pfRegistrationDate,#pfStartDate,#esiRegistrationDate,#esiStartDate,#createdDate').bootstrapMaterialDatePicker
			({
			    time: false,
			    clearButton: true,
				format : "DD/MM/YYYY"
			});
            $('.addrow').click(function () {

                //adding Heading
                $('.addrow-modal .modal-title').html('Add ' + $(this).closest('.sepr').find('h5').html());
                //generating body
                var tr = $(this).closest('thead tr');
                var tabString = '<table style="width:55%">'
                tr.find('td').each(function () {
                    if ($(this).attr('data-type') != 'id' && $(this).attr('data-type') != 'no' && $(this).attr('data-type') != undefined) {                        
                        if ($(this).attr('data-type') == 'select' || $(this).attr('data-type') == 'select-multi') {
                            tabString = tabString + '<tr><td>' + $(this).html() + '</td><td>';
                            var type = $(this).attr('data-type');
                            $.ajax({
                                url: $(this).attr('data-source'),async:false, success: function (result) {
                                    result = JSON.parse(result);
                                    var setString = '';
                                    if (type == 'select') {
                                        setString = '<select class="form-control">'
                                    }
                                    else {
                                        setString = '<select multiple="" class="form-control">'
                                    }
                                    $.each(result, function (k, v) {
                                        //display the key and value pair                                        
                                        setString = setString + '<option value="' + k + '">' + v + '</option>';
                                    });
                                    setString = setString + '</select>';
                                    tabString = tabString + setString + '</td></tr>';
                                   
                                }
                            });
                        }
                        else if ($(this).attr('data-type') == 'text')
                        {
                            tabString = tabString + '<tr><td>' + $(this).html() + '</td><td><input class="form-control" placeholder="' + $(this).attr('data-placehold') + '" type="text" name="'+$(this).attr('data-name')+'" ng-moddel="'+$(this).attr('data-name')+'" id="'+$(this).attr('data-name')+'" /></td></tr>';
                        }
                        else if ($(this).attr('data-type') == 'number') {
                            tabString = tabString + '<tr><td>' + $(this).html() + '</td><td><input class="form-control" placeholder="' + $(this).attr('data-placehold') + '" type="number" name="'+$(this).attr('data-name')+'" ng-moddel="'+$(this).attr('data-name')+'" id="'+$(this).attr('data-name')+'" /></td></tr>';
                        }
                        else if ($(this).attr('data-type') == 'email') {
                            tabString = tabString + '<tr><td>' + $(this).html() + '</td><td><input class="form-control" placeholder="' + $(this).attr('data-placehold')+ '" type="email" name="'+$(this).attr('data-name')+'" ng-moddel="'+$(this).attr('data-name')+'" id="'+$(this).attr('data-name')+'" /></td></tr>';
                        }
                        else if ($(this).attr('data-type') == 'text-multi') {
                            tabString = tabString + '<tr><td>' + $(this).html() + '</td><td><textarea class="form-control" placeholder="' + $(this).attr('data-placehold') + '" name="'+$(this).attr('data-name')+'" ng-moddel="'+$(this).attr('data-name')+'" id="'+$(this).attr('data-name')+'"  ></textarea></td></tr>';
                        }
                        else if ($(this).attr('data-type') == 'checkbox') {
                            tabString = tabString + '<tr><td>' + $(this).html() + '</td><td><div class="checkbox" style="margin: 0px"><label><input type="checkbox" name="'+$(this).attr('data-name')+'" ng-moddel="'+$(this).attr('data-name')+'" id="'+$(this).attr('data-name')+'"  /></label></div></td></tr>';
                        }
                        
                    }
                   
                   
                });
                $('.addrow-modal .modal-body').html(tabString + '</table>');
                $.material.init();
                
                $('.addrow-modal').modal({
                    backdrop: 'static'
                });
            });

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
            <h3 class="cls_plainheader">Company Details</h3>
        </div>
        <div class="pull-right">
            <span style="padding-top: 10px; cursor: pointer;"><i class="fa fa-question-circle fa-3x clsicon_help" aria-hidden="true" title="Help"></i></span>
        </div>



    </div>
    
     <div class="container-fluid parentGrid">
        <div class="col-md-10 col-md-offset-1 well">
        	<div class="panel panel-default">
                    <div class="sepr">
                        <button class="btn btn-info btn-raised cls_plainheader panhead" class="btn btn-raised btn-primary  modelpopup"
					  ng-click="addCompanyBtnAction($event)" > Add Company </button>
                        <div>
                            <table class="table table-striped table-hover ">
                                <thead>
                                    <tr>                                       
                                        <td data-type="SNo" >#</td>                                        
                                        <td data-type="text" data-name="t" data-placehold="ds"> Company ID
                                        </td>
                                        <td data-type="text" data-name="t" data-placehold="ds"> Company Name
                                        </td>
                                        <td data-type="text" data-name="t" data-placehold="ds"> Country
                                        </td>
                                       <td data-type="text" data-name="t" data-placehold="ds">  Status
                                        </td>
                                        <!-- 
                                        <td data-type="text" data-name="t" data-placehold="ds"> Status
                                        </td>
                                         <td data-type="no" data-name="nn" data-placehold="sd">
                                            <i class="fa fa-plus addrow"></i>
                                        </td>                                        
                                         <td data-type="select" data-name="sel" data-source="http://localhost:8080/CLMS/CustomerController/countriesList.json" data-placehold=""> Country 
                                        </td>
                                        
                                        <td data-type="no" data-name="nn" data-placehold="ss">
                                            <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
                                        </td>
                                        <td data-type="no" data-name="nn" data-placehold="dd">
                                            <i class="fa fa-minus"></i>
                                        </td> -->
                                    </tr>
                                </thead>                                
                                <tbody class="companyDetailsTable">
                                <c:forEach  items="${companyDetails}" var="company" varStatus="row"  > 
                                    <tr ng-click="fun_getCompanyDetailById(${company.companyId});">                                     	
                                        <td> ${row.index+1}</td>
                                        <td> ${company.companyCode} </td>
                                        <td> ${company.companyName} </td>
                                        <td> ${company.countryName} </td> 
                                        <td> ${company.isActive} </td>                                                                                                       
                                    </tr>
                                </c:forEach>                                    
                                </tbody>
                            </table>

                        </div>
                    </div>
                </div>
        </div>
        </div>

    <div class="container-fluid  childGrid">
    <form name="CompanyDetails" novalidate ng-submit="submit" >
        <div class="col-md-10 col-md-offset-1 well">
            <ul class="nav nav-pills">
                <li class="active"><a href="${pageContext.request.contextPath}/CompanyController/companyDetails.view">Company</a></li>
                <li><a href="${pageContext.request.contextPath}/CompanyController/companyProfile.view">Profile</a></li>
                <li><a href="${pageContext.request.contextPath}/CompanyController/CompanyAddressList.view">Contacts</a></li>

            </ul>
            <br />
					<div class="panel panel-default transactionList">
						<div class="container">
							<h5 class="cls_plainheader"> Search : </h5>
							<div class="row">
								<div class="col-md-6 transactionList">
									<div class="form-group">
										<label for="transactionList" class="col-md-4 control-label">Transaction Date's :</label>
										<div class="col-md-6">
											<select id="transactionList" class="form-control" title="Select date" ng-model="transDates"
												name="transDates" ng-change="fun_Company_Trans_Change()">
												<option ng-repeat="dates in transactionDatesList"
													selected={{ dates.id== addressTransDate }} ? "selected" : "" value="{{dates.id}}">{{dates.name}}</option>
											</select>
										</div>
									</div>
								</div>
							</div>
						</div>
						<hr class="pull-left" style="width: 105%" />
					</div>

			
            <div class="col-md-12">
                <div class="container">
                    <!-- <div class="col-md-4"> Company Code  {{companyCode}} </div>
                    <div class="col-md-4"> Company Name  {{companyName}} </div>
                    <div class="col-md-4"> Country Name  INDIA </div> -->
                    <div class="row">
                            
                                <div class="col-md-10">
                                	<div class="col-md-4">
                                    <label for="companyCode" class="col-md-4 control-label">Company ID</label>
                                    <div>
                                        <input type="text" class="form-control" id="companyCode" placeholder="Company Code" ng-model="companyCode" name="companyCode"  ng-required="true" ng-disabled="readOnly"/>
                                    </div>
                                    </div>
                                    <div class="col-md-5">
									<label for="companyName" class="col-md-4 control-label"> Company Name </label>
                                    <div >
                                        <input type="text" class="form-control" id="companyName" placeholder="Company Name" ng-model="companyName" name="companyName"  ng-required="true" ng-disabled="readOnly"/>
                                    </div>
                                    </div>
                                    <div class="col-md-3">
									 <label for="countryId" class="col-md-4 control-label"> Country </label>
                                    <div>
                                        <select id="countryId" class="form-control" ng-model="countryId" name="countryId"  ng-required="true" ng-disabled="readOnly">
                                            <c:forEach items="${countries}" var="country"> 
                                            	<option value="${country.countryId}"> ${country.countryName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    </div>
                                </div>
                             
                      </div>
                </div>
                <br />
                <div class="panel panel-default">
                    <div class="sepr">
                        <h5 class="cls_plainheader">Compay details</h5>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="transactionDate" class="col-md-4 control-label">Transaction Date</label>
                                    <div class="col-md-8">
                                        <input type="text" class="form-control" id="transactionDate" placeholder="Transaction Date" ng-model="transactionDate" name="transactionDate"  ng-required="true" ng-disabled="readOnly"/>
                                    </div>
                                </div><!-- <input type="text" class="form-control" id="createdDate" ng-model="createdDate" /> -->

                                <div class="form-group">
                                    <label for="sectorId" class="col-md-4 control-label">Sector</label>
                                    <div class="col-md-8">
                                        <select id="sectorId" class="form-control" ng-model="sectorId" name="sectorId"  ng-required="true" ng-disabled="readOnly">
                                            <c:forEach items="${companySectors}" var="sector"> 
                                            	<option value="${sector.id}"> ${sector.name} </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="industryId" class="col-md-4 control-label">Industry</label>
                                    <div class="col-md-8">
                                        <select id="industryId" class="form-control" ng-model="industryId" name="industryId" ng-change="fun_Industrychange()"  ng-required="true" ng-disabled="readOnly">
                                            <c:forEach items="${industries}" var="industry"> 
                                            	<option value="${industry.id}"> ${industry.name} </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>

                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="isActive" class="col-md-4 control-label">Status</label>
                                    <div class="col-md-8">
                                        <select id="isActive" class="form-control" ng-model="isActive" name="isActive"  ng-required="true" ng-disabled="readOnly">
                                            <option value="Y"> Active </option>
                                            <option value="N"> In-Active </option>                                           
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="legacyId" class="col-md-4 control-label">Company Legacy ID </label>
                                    <div class="col-md-8">
                                        <input type="text" class="form-control" id="legacyId" placeholder="Company Legacy ID" ng-model="legacyId" name="legacyId"  ng-required="true" ng-disabled="readOnly"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="subIndustryId" class="col-md-4 control-label">Sub Industry</label>
                                    <div class="col-md-8">
                                        <select id="subIndustryId" class="form-control" ng-model="subIndustryId" name="subIndustryId"  ng-required="true" ng-disabled="readOnly">
                                             <option ng-repeat="subIndustry in list_sub_industries" value="{{subIndustry.id*1}}">{{subIndustry.name}}</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>


            </div>

            <br />
            <div class="col-md-12">

                <div class="panel panel-default">
                    <div class="sepr">
                        <h5 class="cls_plainheader">Indian Company Details</h5>
                        <div class="row">
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label for="corporateIdentityNumber" class="col-md-6 control-label">Corporate Identity Number</label>
                                        <div class="col-md-6">
                                            <input type="text" class="form-control" id="corporateIdentityNumber" placeholder="Corporate Identity Number" ng-model="corporateIdentityNumber" name="corporateIdentityNumber"  ng-required="true" ng-disabled="readOnly" />
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label for="rocRegistrationNumber" class="col-md-6 control-label">Roc Register Number</label>
                                        <div class="col-md-6">
                                            <input type="text" class="form-control" id="rocRegistrationNumber" placeholder="Roc Register Number"  ng-model="rocRegistrationNumber" name="rocRegistrationNumber"  ng-required="true" ng-disabled="readOnly" />
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label for="serviceTaxRegistrationNumber" class="col-md-6 control-label">Service Tax Number</label>
                                        <div class="col-md-6">
                                            <input type="text" class="form-control" id="serviceTaxRegistrationNumber" placeholder="Service Tax Number"  ng-model="serviceTaxRegistrationNumber" name="serviceTaxRegistrationNumber"  ng-required="true" ng-disabled="readOnly" />
                                        </div>
                                    </div>

                                </div>

                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label for="registrationDate" class="col-md-6 control-label">Registration Date</label>
                                        <div class="col-md-6">
                                            <input type="text" class="form-control" id="registrationDate" placeholder="Registration Date"  ng-model="registrationDate" name="registrationDate"  ng-required="true" ng-disabled="readOnly" />
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label for="registrationActId" class="col-md-6 control-label">Registered Under</label>
                                        <div class="col-md-6">
                                            <select id="registrationActId" class="form-control"  ng-model="registrationActId" name="registrationActId"  ng-required="true" ng-disabled="readOnly">
                                                 <c:forEach items="${regTypes}" var="reg"> 
	                                            	<option value="${reg.id}"> ${reg.name} </option>
	                                            </c:forEach>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label for="tinNumber" class="col-md-6 control-label">TIN Number</label>
                                        <div class="col-md-6">
                                            <input type="text" class="form-control" id="tinNumber" placeholder="TIN Number"  ng-model="tinNumber" name="tinNumber"  ng-required="true" ng-disabled="readOnly"/>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>

                        <br />
                        <div class="panel panel-default pandit">

                            <div class="sepr">
                                <h5 class="cls_plainheader">PAN details</h5>
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="panNumber" class="col-md-4 control-label">Company PAN</label>
                                            <div class="col-md-8">
                                                <input type="text" class="form-control" id="panNumber" placeholder="Company PAN"  ng-model="panNumber" name="panNumber"  ng-required="true" ng-disabled="readOnly"/>
                                            </div>
                                        </div>

                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="panRegistrationDate" class="col-md-6 control-label">PAN Registration Date</label>
                                            <div class="col-md-6">
                                                <input type="text" class="form-control" id="panRegistrationDate" placeholder="PAN Registration Date"  ng-model="panRegistrationDate" name="panRegistrationDate"  ng-required="true" ng-disabled="readOnly"/>
                                            </div>
                                        </div>


                                    </div>
                                </div>

                            </div>
                        </div>


                        <div class="panel panel-default pandit">

                            <div class="sepr">
                                <h5 class="cls_plainheader">PF details</h5>
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="row">
                                            <div class="form-group">
                                                <label for="pfNumber" class="col-md-6 control-label">PF Account Number</label>
                                                <div class="col-md-6">
                                                    <input type="text" class="form-control" id="pfNumber" placeholder="PF Account Number" ng-model="pfNumber" name="pfNumber"  ng-required="true" ng-disabled="readOnly"/>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label for="pfStartDate" class="col-md-6 control-label">PF start Date</label>
                                                <div class="col-md-6">
                                                    <input type="text" class="form-control" id="pfStartDate" placeholder="PF start Date" ng-model="pfStartDate" name="pfStartDate"  ng-required="true" ng-disabled="readOnly"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="row">
                                            <div class="form-group">
                                                <label for="pfRegistrationDate" class="col-md-6 control-label">PF Registration Date</label>
                                                <div class="col-md-6">
                                                    <input type="text" class="form-control" id="pfRegistrationDate" placeholder="PF Registration Date" ng-model="pfRegistrationDate" name="pfRegistrationDate"  ng-required="true" ng-disabled="readOnly"/>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label for="pfTypeId" class="col-md-6 control-label">PF Type</label>
                                                <div class="col-md-6">
                                                    <select id="pfTypeId" class="form-control"  ng-model="pfTypeId" name="pfTypeId"  ng-required="true" ng-disabled="readOnly">
                                                         <c:forEach items="${pfTypes}" var="pf"> 
			                                            	<option value="${pf.id}"> ${pf.name} </option>
			                                            </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>

                                    </div>
                                </div>

                            </div>
                        </div>


                        <div class="panel panel-default pandit">

                            <div class="sepr">
                                <h5 class="cls_plainheader">ESI details</h5>
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="row">
                                            <div class="form-group">
                                                <label for="esiNumber" class="col-md-6 control-label">ESI Account Number</label>
                                                <div class="col-md-6">
                                                    <input type="text" class="form-control" id="esiNumber" placeholder="ESI Account Number" ng-model="esiNumber" name="esiNumber"  ng-required="true" ng-disabled="readOnly" />
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label for="esiStartDate" class="col-md-6 control-label">ESI start Date</label>
                                                <div class="col-md-6">
                                                    <input type="text" class="form-control" id="esiStartDate" placeholder="ESI start Date" ng-model="esiStartDate" name="esiStartDate"  ng-required="true" ng-disabled="readOnly"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="row">
                                            <div class="form-group">
                                                <label for="esiRegistrationDate" class="col-md-6 control-label">ESI Registration Date</label>
                                                <div class="col-md-6">
                                                    <input type="text" class="form-control" id="esiRegistrationDate" placeholder="ESI Registration Date" ng-model="esiRegistrationDate" name="esiRegistrationDate"  ng-required="true" ng-disabled="readOnly"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>


            </div>

            <div class="btn-group btn-group-right ">
	            <a href="javascript:void(0)" class="btn btn-raised btn-success updateBtn" ng-click="updateCompanyBtnAction($event)"> Update </a>
				<button class="btn btn-raised btn-primary saveBtn"  ng-click=" submitted = true; CompanyDetails.$valid &&  fun_savecompanydetails($event)" onclick="return false;">Save</button> 
				<button class="btn btn-raised btn-primary currentHistoryBtn" type="submit" ng-click="submitted = true; CompanyDetails.$valid && fun_updatecompanydetails($event)" onclick="return false;"> Correct History </button>											
				<button class="btn btn-raised btn-danger  resetBtn" type="reset"> Reset </a> 
				<button type="button" class="btn btn-raised btn-warning cancelBtn" ng-click="cancelBtnAction($event)"> Return to serach </button>
				<a href="javascript:void(0)" class="btn btn-raised btn-info addrHistory" ng-click="historyBtnAction($event)"> View History </a>
            </div>

        </div>


        <div class="container-fluid">
            <div class="navbar navbar-default navbar-fixed-bottom text-center" style="min-height: 15px; padding: 0px;">
                <div class="container text-right" style="">
                    <label class="control-label text-center" style="color: white; margin: 0px; font-size: 14px;">@Copyright</label>
                </div>
            </div>
        </div>
        </form>
        </div>
          <!-- models -->
    <div class="modal fade addrow-modal">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">Modal title</h4>
                </div>
                <div class="modal-body">
                    <p>One fine body&hellip;</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary">Save changes</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
    <!-- /.modal -->
    </div>
</body>
</html>
