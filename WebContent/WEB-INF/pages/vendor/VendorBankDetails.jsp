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
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:300,400,500,700" type="text/css" />
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet" />
    <link 	href="${pageContext.request.contextPath}/Resources/font-awesome-4.6.3/css/font-awesome.css" rel="stylesheet" />

    <!-- Bootstrap -->
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet" />

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
<script type="text/javascript" 	src="${pageContext.request.contextPath}/js/Vendor_Details.js"></script>
<script type="text/javascript"  src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" 	src="${pageContext.request.contextPath}/Resources/Toaster/toastr.js"></script>
<script type="text/javascript" 	src="${pageContext.request.contextPath}/js/Form_Valiation.js"></script>
<script type="text/javascript" 	src="${pageContext.request.contextPath}/js/Main.js"></script>

    <script type="text/javascript">
        $(document).ready(function () {
            //initialsie the material-deisgn
            $.material.init();
            $('#searchResults').DataTable();
            $('.date').bootstrapMaterialDatePicker
			({
			    time: false,
			    clearButton: true
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

                        </ul>
                    </li>
                </ul>
                <p style="z-index: 1; position: relative; margin-right: -25px; max-width: 500px; margin-top: -5px;">John Abraham</p>
            </div>

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
                            <li class="dropdown-submenu active">
                                <a href="javascript:void(0)">Customer</a>
                                <ul class="dropdown-menu" style="top: 10px;">
                                    <li class="active"><a href="javascript:void(0)">Customer Details</a></li>
                                    <li><a href="javascript:void(0)">Address</a></li>
                                    <li><a href="javascript:void(0)">Contact</a></li>
                                </ul>
                            </li>
                            <li class="dropdown-submenu">
                                <a href="javascript:void(0)">Company</a>
                                <ul class="dropdown-menu" style="top: 45px;">
                                    <li><a href="javascript:void(0)">Company Details</a></li>
                                    <li><a href="javascript:void(0)">Address</a></li>
                                    <li><a href="javascript:void(0)">Contact</a></li>
                                </ul>
                            </li>
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
                </ul>
            </div>
        </div>
    </div>

    <div class="container-fluid" style="margin-bottom: 35px;">
        <div style="display: inline-block">
            <h3 class="cls_plainheader">Add Vendor Bank Detilas</h3>
        </div>
        <div class="pull-right">
            <span style="padding-top: 10px; cursor: pointer;"><i class="fa fa-question-circle fa-3x clsicon_help" aria-hidden="true" title="Help"></i></span>
        </div>



    </div>

    <div class="container-fluid">
        <div class="col-md-10 col-md-offset-1 well">
            <div class="col-md-12">

                <br />
                <br />
                <br />
                <div class="panel panel-default">
                    <div class="sepr">

                        <div class="row">
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label for="custName" class="col-md-6 control-label">Customer Name</label>
                                    <div class="col-md-6">
                                        <input type="text" class="form-control" id="custName" placeholder="Customer Name" />
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="venName" class="col-md-6 control-label">Vendor Name</label>
                                    <div class="col-md-6">
                                        <input type="text" class="form-control" id="venName" placeholder="Vendor Name" />
                                    </div>
                                </div>



                            </div>

                            <div class="col-md-4">
                                <div class="form-group">
                                    <label for="cmpName" class="col-md-6 control-label">Company Name</label>
                                    <div class="col-md-6">
                                        <input type="text" class="form-control" id="cmpName" placeholder="Company Name" />
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="locName" class="col-md-6 control-label">Location Name</label>
                                    <div class="col-md-6">
                                        <input type="text" class="form-control" id="locName" placeholder="Location Name" />
                                    </div>
                                </div>


                            </div>

                            <div class="col-md-4">
                                <div class="form-group">
                                    <label for="cntName" class="col-md-6 control-label">Country Name</label>
                                    <div class="col-md-6">
                                        <input type="text" class="form-control" id="cntName" placeholder="Country Name" />
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="bnkCode" class="col-md-6 control-label">Bank Code</label>
                                    <div class="col-md-6">
                                        <input type="text" class="form-control" id="bnkCode" placeholder="Bank Code" />
                                    </div>
                                </div>


                            </div>




                        </div>
                    </div>
                </div>


                <div class="panel panel-default">
                    <div class="sepr">
                        <h5 class="cls_plainheader">Define Bank Details</h5>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="tranDate" class="col-md-6 control-label ">Transction Date</label>
                                    <div class="col-md-6">
                                        <input type="text" class="form-control date" id="tranDate" placeholder="Transction Date" />
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="bnkName" class="col-md-6 control-label ">Bank Name</label>
                                    <div class="col-md-6">
                                        <input type="text" class="form-control" id="bnkName" placeholder="Bank Name" />
                                    </div>
                                </div>


                                <div class="form-group">
                                    <label for="accNo" class="col-md-6 control-label ">Account Number</label>
                                    <div class="col-md-6">
                                        <input type="number" class="form-control" id="accNo" placeholder="Account Number" />
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="branch" class="col-md-6 control-label ">Branch</label>
                                    <div class="col-md-6">
                                        <input type="text" class="form-control" id="branch" placeholder="Branch" />
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="ifsc" class="col-md-6 control-label ">IFSC Code</label>
                                    <div class="col-md-6">
                                        <input type="text" class="form-control" id="ifsc" placeholder="IFSC Cod" />
                                    </div>
                                </div>

                                <div class="form-group">
                                    <a href="#" class="btn btn-primary" style="text-decoration: underline;">Add Address<i class="fa fa-plus-circle"></i></a>
                                </div>
                                <div class="form-group">
                                    <label for="ifsc" class="col-md-6 control-label">Address</label>
                                    <div class="col-md-6">
                                        <textarea class="form-control" id="addressArea"></textarea>
                                    </div>

                                </div>
                            </div>

                            <div class="col-md-6">

                                <div class="form-group">
                                    <label for="status" class="col-md-6 control-label">Status</label>
                                    <div class="col-md-6">
                                        <select class="form-control" id="status">
                                            <option>abc</option>
                                            <option>abc</option>
                                            <option>abc</option>
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="accHolderName" class="col-md-6 control-label">Acc Holder Name</label>
                                    <div class="col-md-6">
                                      <input type="text" id="accHolderName" class="form-control" placeholder="Acc Holder Name" />
                                    </div>
                                </div>

                                 <div class="form-group">
                                    <label for="city" class="col-md-6 control-label">City</label>
                                    <div class="col-md-6">
                                      <input type="text" id="city" class="form-control" placeholder="City" />
                                    </div>
                                </div>
                              
                                 <div class="form-group">
                                    <label for="micr" class="col-md-6 control-label">MICR Code</label>
                                    <div class="col-md-6">
                                      <input type="text" id="micr" class="form-control" placeholder="MICR Code" />
                                    </div>
                                </div>

                                 <div class="form-group">
                                    <label for="phno" class="col-md-6 control-label">Phone No</label>
                                    <div class="col-md-6">
                                      <input type="text" id="phno" class="form-control" placeholder="Phone No" />
                                    </div>
                                </div>
                              
                            </div>


                        </div>
                        <div></div>


                    </div>

                    <br />


                </div>
                 <div class="btn-group btn-group-right ">
                <a href="javascript:void(0)" class="btn btn-primary">Save</a>
                <a href="javascript:void(0)" class="btn btn-success">Update</a>
                <a href="javascript:void(0)" class="btn btn-info">View Update History</a>
                <a href="javascript:void(0)" class="btn btn-warning">Return to serach</a>
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
