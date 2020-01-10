'use strict';

// Declare app level module which depends on views, and components
angular.module('Authentication', []);
angular.module('myApp', [
    'ngRoute',  	
    'ui.chart',
    'myApp.DashboardDetails',
  	'myApp.vendorBankList',
  	'myapp.VendorBranchesSearch',  
  	'myapp.shifts',
    'myApp.ShiftDefine',
    'myApp.companyWorkOrder',
	'myApp.companyModule', 	
	'myApp.AssignShiftsSearch',
	'myApp.vendorList',
    'myApp.vendorSearch',    
    'myApp.CustomerDetails',       
	'myApp.Department',
	'myApp.Location',
    'myapp.JobCode',
    'myApp.Plant',
    'myApp.WorkArea',
    'myApp.WageRate',
    'myApp.WageCalculation',
    'myApp.ShiftPattern',
    'myapp.DefineComplianceTypes',
    'myApp.employeeInformation',
    'myApp.worker',
    'myapp.workerJobDetails',
    'myApp.auditMode',
    'myApp.time',
    'myApp.SectionModel',
    'myApp.workerBadgeGeneration',
    'myApp.DefineOverTimeControls',
    'myApp.Holiday',
    'myApp.VendorCompliance',
    'myApp.Attendancereport', 
    'myApp.actualAttendancereport',
    'myApp.associateWorkOrder',
    'myApp.VendorComplianceReport',
	'myApp.wageScale',
	'myApp.wageDeduction',
    'myApp.Assighment',
    'myApp.RegulationAndAbolitionReport',
    'myApp.WorkmenEmployedByContractorFormXIIIReport',
    'myApp.OverTimeReport',
    'myApp.WorkerVerification',
    'myApp.ReportCertificateByPrincipalEmployerFormV',
    'myApp.ViewOrEditTimeByWorkerCtrl',
    'myApp.Division',
    'myapp.gatePassAcess',
    'myApp.BulkShiftUpload',
    'myApp.ViewOrEditTimeByWorkerUpload',
    'myapp.BonusRules',
    'myApp.LWFRules',
    'myApp.ProfessionalTax',
    'myApp.esiDetails',
    'myApp.vendorRegisterTypes',
    'myApp.workerBankDetails',
    'myApp.JobAllocationByVendor',
    'myApp.DepartmentWiseWorkAllocation',
    'myApp.workerBankDetails',
    'myApp.definePF',
    'myApp.Budget',
    'myApp.WorkerGroup',
    'myApp.Exception',
	'myApp.definePF',
	'myApp.PaymentRules',
	'myApp.Invoice',
	'myApp.veificationTypeSetup',
	'myApp.associatingDepartmentToLocationAndPlant',
	'myApp.VendorDocument',
	'myApp.CompanyDocument',
	'myApp.ManpowerRequest',
	'myApp.JobAllocationReport',
	'myApp.workerMedicalExamination',
	'myApp.workerPoliceVerification',
	'myApp.ManpowerReport',
    'ngResource',
    'ngIdle',
    'ui.bootstrap',
    'ngCookies',
    'Authentication',
    'pascalprecht.translate',
    'ngSanitize',
    'myApp.screenActions',
    'myApp.roles',
    'myApp.users',
    'myApp.permissionsGroup',
    'myApp.ApprovalPathModule',
    'myApp.ApprovalPathTransaction',
    'myApp.ApprovalPathFlow'
])
.config(['$routeProvider','KeepaliveProvider','IdleProvider','TitleProvider',function ($routeProvider,KeepaliveProvider,IdleProvider,TitleProvider) {
 
    $routeProvider
        .when('/login', {
            controller: 'loginCtrl',
            templateUrl: 'login/login.html'
        }).when('/dashboardDetails', {
            controller: 'dashboardCtrl',
            templateUrl: 'Dashboard/AdminDashboard.html'
        }).when('/unauthorized', {
            controller: 'unAuthorizedController',
            templateUrl: 'login/UnauthorizedAccess.html'
        }).when('/dashboard', {
            controller: 'dashboardDetailsCtrl',
            templateUrl: 'Dashboard/Dashboard.html'
        }).when('/customersearchCtrl', {
            controller: 'customersearchCtrl',
            templateUrl: 'Customer/CustomerRegistration/CustomerSearch.html'
        }).when('/customerDetailsSearch', {
        	controller: 'customersearchCtrl',
            templateUrl: 'Customer/CustomerRegistration/CustomerSearch.html'
        }).when('/CustomerDetails/:customerInfoId', {
            controller: 'customerDetailsCtrl',
            templateUrl: 'Customer/CustomerRegistration/CustomerDetails.html'
        }).when('/customerContctsSearch', {
            controller: 'customerContactSearchCtrl',
            templateUrl: 'Customer/Contact/CustomerContactGrid.html'
        }).when('/customerContact/create', {
            controller: 'customerContactDtls',
            templateUrl: 'Customer/Contact/CustomerContact.html'
        }).when('/customerContact/:id', {
            controller: 'customerContactDtlsEditCtrl',
            templateUrl: 'Customer/Contact/CustomerContact.html'
        }).when('/customerAddress/create', {
            controller: 'customerAddressDtls',
            templateUrl: 'Customer/Contact/CustomerAddress.html'
        }).when('/customerAddress/:id', {
            controller: 'customerAddressDtlsEdit',
            templateUrl: 'Customer/Contact/CustomerAddress.html'
        }).when('/VendorSearch', {
            controller: 'vendorListCtrl',
            templateUrl: 'vendor/VendorRegistrationDetails/VendorSearch.html'
        }).when('/vendorEditDetails/:id', {
	        controller: 'vendorEditDetailsCtrl',
	        templateUrl: 'vendor/VendorRegistrationDetails/VendorDetails.html'
        }).when('/vendorBankSearchList', {
            controller: 'vendorBankSarchCtrl',
            templateUrl: 'vendor/BankDetails/BankSearch.html'
        }).when('/vendorBankDetails/create', {
            controller: 'vendorBankDetails',
            templateUrl: 'vendor/BankDetails/BankDetails.html'
        }).when('/vendorBankDetails/:vendorBankId', {
            controller: 'vendorBankDetailsEdit',
            templateUrl: 'vendor/BankDetails/BankDetails.html'
        }).when('/companyDetailsSearch', {
            controller: 'companyDetailsSearchCtrl',
            templateUrl: 'company/company/companyDetailsSearch.html'
        }).when('/companyDetails/create', {
            controller: 'companyDetailsCreateCtrl',
            templateUrl: 'company/company/companyDetails.html'
        }).when('/companyDetails/:id', {
            controller: 'companyDetailsViewCtrl',
            templateUrl: 'company/company/companyDetails.html'
        }).when('/companyProfile', {
            controller: 'companyProfileCtrl',
            templateUrl: 'company/companyProfile/companyProfile.html'
        }).when('/companyWorkOrder', {
            controller: 'companyWorkOrderSearchCtrl',
            templateUrl: 'company/companyWorkOrder/companyWorkOrderSearch.html'
        }).when('/companyWorkOrder/create', {
            controller: 'workorderCtrl',
            templateUrl: 'company/companyWorkOrder/companyWorkOrder.html'
        }).when('/companyWorkOrder/:id', {
            controller: 'workorderCtrl',
            templateUrl: 'company/companyWorkOrder/companyWorkOrder.html'
        }).when('/companyContctsSearch', {
            controller: 'companyContactSearchCtrl',
            templateUrl: 'company/companyContactAndAddress/companyContactGrid.html'
        }).when('/companyContact/create', {
            controller: 'companyContactDtls',
            templateUrl: 'company/companyContactAndAddress/companyContact.html'
        }).when('/companyContact/:id', {
            controller: 'companyContactViewDtls',
            templateUrl: 'company/companyContactAndAddress/companyContact.html'
        }).when('/companyAddress/create', {
            controller: 'companyAddressDtls',
            templateUrl: 'company/companyContactAndAddress/companyAddress.html'
        }).when('/companyAddress/:id', {
            controller: 'companyAddressViewDtls',
            templateUrl: 'company/companyContactAndAddress/companyAddress.html'
        }).when('/vendorBranchesSearch', {
            controller: 'vendorBranchesViewDtls',
            templateUrl: 'vendor/branches/VendorBranchSearch.html'
        }).when('/vendorBranchesAddOrView/:id', {
            controller: 'vendorBranchesAddViewDtls',
            templateUrl: 'vendor/branches/vendorBranches.html'
        }).when('/LocationSearch', {
            controller: 'LocationSearchCtrl',
            templateUrl: 'Location/LocationSearch.html'
        }).when('/LocationAdd/:id', {
            controller: 'LocationAddCtrl',
            templateUrl: 'Location/AddLocation.html'
        }).when('/LocationAdd', {
            controller: 'LocationAddCtrl',
            templateUrl: 'Location/AddLocation.html'
        }).when('/LocationCharacteristics', {
            controller: 'LocationCharacteristicsCtrl',
            templateUrl: 'Location/locationCharacteristics.html'
        }).when('/LocationCharacteristics/:id', {
            controller: 'LocationCharacteristicsCtrl',
            templateUrl: 'Location/locationCharacteristics.html'
        }).when('/DepartmentSearch', {
            controller: 'deptSearchCtrl',
            templateUrl: 'Department/deptSearch.html'
        }).when('/addDepartment', {
            controller: 'AddDepartmentCtrl',
            templateUrl: 'Department/addDepartment.html'
        }).when('/addDepartment/:id', {
            controller: 'AddDepartmentCtrl',
            templateUrl: 'Department/addDepartment.html'
        }).when('/SearchJobCode', {
            controller: 'JobCodeSearchctrl',
            templateUrl: 'JobCode/jobCodeSearch.html'
        }).when('/AddJobCode', {
            controller: 'JobCodeAddctrl',
            templateUrl: 'JobCode/AddJobCode.html'
        }).when('/AddJobCode/:id/:id1', {
            controller: 'JobCodeAddctrl',
            templateUrl: 'JobCode/AddJobCode.html'
        }).when('/PlantSearch', {
            controller: 'PlantSearchCtrl',
            templateUrl: 'Plant/PlantSearch.html'
        }).when('/PlantAdd/:id', {
            controller: 'PlantAddCtrl',
            templateUrl: 'Plant/PlantAdd.html'
        }).when('/shiftsDefaults', {
            controller: 'shiftControllerDtls',
            templateUrl: 'shiftsDefaults/shifts.html'
        }).when('/ShiftDefineSearch', {
            controller: 'ShiftDefineSearchCtrl',
            templateUrl: 'shiftsDefine/ShiftsDefineSearch.html'
        }).when('/shiftsDefineAddOrEdit/:id', {
            controller: 'ShiftDefineAddOrEditCtrl',
            templateUrl: 'shiftsDefine/ShiftsDefineAddOrEdit.html'
        }).when('/WorkAreaSearch', {
            controller: 'WorkAreaSearchCtrl',
            templateUrl: 'WorkArea/WorkAreaSearch.html'
        }).when('/WorkAreaAdd/:id', {
            controller: 'WorkAreaAddCtrl',
            templateUrl: 'WorkArea/WorkAreaAdd.html'
        }).when('/WageRateSearch', {
            controller: 'WageSearchCtrl',
            templateUrl: 'Wage/WageRateSearch.html'
        }).when('/WageCalculation', {
            controller: 'WageCalculationCtrl',
            templateUrl: 'Wage/WageCalculation/WageCalculation.html'
        }).when('/WageRateAdd/:id', {
            controller: 'WageAddCtrl',
            templateUrl: 'Wage/WageRateAdd.html'
        }).when('/DefineComplianceTypes', {
				 controller: 'DefineComplianceTypesControllerDtls',
            templateUrl: 'DefineComplianceTypes/DefineComplianceTypes.html'
        }).when('/EmployeeInformation', {
            controller: 'EmployeeSearchCtrl',
            templateUrl: 'Employee/EmployeeDetailsSearch.html'
        }).when('/EmployeeInformation/create', {
            controller: 'EmployeeRegistrationCtrl',
            templateUrl: 'Employee/EmployeeDetailsEntry.html'
        }).when('/EmployeeInformation/:id', {
            controller: 'EmployeeEditCtrl',
            templateUrl: 'Employee/EmployeeDetailsEntry.html'
        }).when('/ShiftPatternSearch', {
            controller: 'ShiftPatternSearchCtrl',
            templateUrl: 'ShiftPattern/ShiftPatternSearch.html'
        }).when('/ShiftPatternAdd/:id', {
            controller: 'ShiftPatternAddCtrl',
            templateUrl: 'ShiftPattern/ShiftPatternAdd.html'
        }).when('/workerSearch', {
            controller: 'workerSearchCtrl',
            templateUrl: 'Worker/WorkerDetails/WorkerDetailsSearch.html'
        }).when('/workerDetails/create', {
            controller: 'workerDetailsCtrl',
            templateUrl: 'Worker/WorkerDetails/WorkerDetails.html'
        }).when('/workerDetails/:workerInfoId', {
            controller: 'workerDetailsEditCtrl',
            templateUrl: 'Worker/WorkerDetails/WorkerDetails.html'
        }).when('/workerFamilyDetails', {
            controller: 'workerFamilyDetailsCtrl',
            templateUrl: 'Worker/WorkerDetails/WorkerFamilyDetails.jsp'
        }).when('/workerEducationEmploymentDetails', {
            controller: 'educationAndEmploymentDetailsController',
            templateUrl: 'Worker/WorkerDetails/EducationAndEmploymentDetails.html'
        }).when('/workerJobDetails', {
            controller: 'workerJobDetailsController',
            templateUrl: 'workerJobDetails/workerJobDetailsSearch.html'
        }).when('/workerJobDetails/create', {
            controller: 'workerJobDetailsAddViewDtls',
            templateUrl: 'workerJobDetails/workerJobDetails.html'
        }).when('/workerJobDetails/:id', {
            controller: 'workerJobDetailsAddViewDtls',
            templateUrl: 'workerJobDetails/workerJobDetails.html'
        }).when('/uploadWorker', {
            controller: 'workerUploadController',
            templateUrl: 'Worker/WorkerDetails/WorkerUpload.jsp'
        }).when('/auditModeController', {
            controller: 'auditController',
            templateUrl: 'Audit/AuditMode.html'
        })/* .when('/timeRules/create', {
            controller: 'timeController',
            templateUrl: 'Time/TimeRules/TimeRules.html'
        })*/.when('/timeRules/:id', {
            controller: 'timeCtrl',
            templateUrl: 'Time/TimeRules/TimeRules.html'
        }).when('/searchTimeRules', {
            controller: 'timeSearchController',
            templateUrl: 'Time/TimeRules/TimeRulesSearch.html'
        }).when('/exceptionRules/:id', {
            controller: 'timeExceptionCtrl',
            templateUrl: 'Time/TimeRules/TimeExceptionRules.html'
        }).when('/sectionDetails', {
            controller: 'sectionSearchCtrl',
            templateUrl: 'sections/SectionsSearch.html'
        }).when('/sectionDetails/:id', {
            controller: 'sectionAddCtrl',
            templateUrl: 'sections/SectionAdd.html'
        }).when('/workerBadgeGeneration', {
            controller: 'workerBadgeGenerationSearchCtrl',
            templateUrl: 'WorkerBadgeGeneration/WorkerBadgeGenerationSearch.html'
        }).when('/workerBadgeGeneration/:id', {
            controller: 'WorkerBadgeGenerationCtrl',
            templateUrl: 'WorkerBadgeGeneration/WorkerBadgeGeneration.html'
        }).when('/DefineOverTimeControl', {
            controller: 'defineOverTimeControlSearchCtrl',
            templateUrl: 'DefineOverTimeControls/DefineOverTimeControlSearch.html'
        }).when('/DefineOverTimeControl/:id', {
            controller: 'defineOverTimeControlsCtrl',
            templateUrl: 'DefineOverTimeControls/DefineOverTimeControls.html'       
        }).when('/HolidayCalendarSearch', {
            controller: 'HolidaySearchCtrl',
            templateUrl: 'HolidayCalendar/HolidayCalendarSearch.html'
        }).when('/HolidayCalendarAdd/:id', {
            controller: 'HolidayAddCtrl',
            templateUrl: 'HolidayCalendar/HolidayCalendarAdd.html'
        }).when('/VendorComplianceSearch', {
            controller: 'VendorComplianceSearchCtrl',
            templateUrl: 'VendorCompliance/VendorComplianceSearch.html'
        }).when('/VendorComplianceAdd/:id', {
            controller: 'VendorComplianceAddCtrl',
            templateUrl: 'VendorCompliance/VendorComplianceAdd.html'
       // by abedeen
		}).when('/shiftAssignment/:globalInfo', {
	           controller: 'listassignmentController',
	           templateUrl: 'shiftsAssignment/all.html'
	       }).when('/calandershiftAssignment/:workerId', {
	           controller: 'assignmentController',
	           templateUrl: 'shiftsAssignment/showCalander.html'
	       }).when('/editShiftAssignment/:workerObj', {
	           controller: 'editassignmentController',
	           templateUrl: 'shiftsAssignment/workerEditSchedule.html'
	       }).when('/editScheduleDate/:companyId/:customerId/:workerId/:vendorId/:dt', {
	           controller: 'editCalandarEventController',
	           templateUrl: 'shiftsAssignment/editCalandar.html'
	       }).when('/attendancereport', {
	        controller: 'attandanceReportSearchCtrl',
	        templateUrl: 'Reports/AttandanceReport/AttandanceReportSearch.html'
        }).when('/AttendanceExcelReport', {
            controller: 'attandanceReportSearchCtrl',
            templateUrl: 'Reports/AttandanceReport/AttandanceExcelReportSearch.html'
        }).when('/WorkerMonthlyExcelReport', {
            controller: 'attandanceReportSearchCtrl',
            templateUrl: 'Reports/AttandanceReport/WorkerMonthlyExcelReport.html'
        }).when('/actualAttendancereport', {
            controller: 'actualAttandanceReportSearchCtrl',
            templateUrl: 'Reports/ActualAttandanceReport/ActualAttandanceReportSearch.html'
        }).when('/WorkerMonthlyReport', {
            controller: 'attandanceReportSearchCtrl',
            templateUrl: 'Reports/AttandanceReport/WorkerMonthlyReport.html'
        }).when('/WorkerMonthlyAttendanceReport', {
            controller: 'attandanceReportSearchCtrl',
            templateUrl: 'Reports/AttandanceReport/WorkerMonthlyAttendanceReport.html'
        }).when('/wageScaleSearch', {
            controller: 'WageScaleSearchCtrl',
            templateUrl: 'Wage/WageScale/WageScaleSearch.html'
        }).when('/WageScale/:id', {
            controller: 'WageScaleCtrl',
            templateUrl: 'Wage/WageScale/WageScale.html'
        }).when('/associateWorkOrdersearch', {
            controller: 'associateWorkOrdersearchCtrl',
            templateUrl: 'vendor/AssociateWorkOrders/associateWorkOrdersSearch.html'
        }).when('/associateWorkOrder/:id', {
            controller: 'associateWorkOrderAddViewDtls',
            templateUrl: 'vendor/AssociateWorkOrders/associateWorkOrders.html'          
        }) .when('/WageDeductionSearch', {
            controller: 'WageDeductionSearchCtrl',
            templateUrl: 'Wage/WageDeduction/WageDeductionSearch.html'
        }).when('/WageDeduction/:id', {
            controller: 'WageDeductionCtrl',
            templateUrl: 'Wage/WageDeduction/WageDeduction.html'
        }).when('/vendorComplianceReport', {
            controller: 'vendorComplianceReportCtrl',
            templateUrl: 'Reports/VendorComplianceReport/VendorComplianceReport.html'
        }).when('/RegulationAndAbolitionReport', {
            controller: 'RegulationAndAbolitionReportCtrl',
            templateUrl: 'Reports/RegulationAndAbolitionReport/RegulationAndAbolitionReport.html'
        }).when('/workerVerificationSearch', {
            controller: 'WorkerVerificationSearchCtrl',
            templateUrl: 'WorkerVerification/WorkerVerificationSearch.html'
        }).when('/workerVerification/:id', {
            controller: 'WorkerVerificatonCtrl',
            templateUrl: 'WorkerVerification/WorkerVerification.jsp'
        }).when('/reportCertificateByPrincipalEmployerFormV', {
            controller: 'ReportCertificateByPrincipalEmployerFormVCtrl',
            templateUrl: 'Reports/ReportCertificateByPrincipalEmployerFormV/ReportCertificateByPrincipalEmployerFormV.html'
        }).when('/AssignShifts', {
            controller: 'AssignShiftsSearchCtrl',
            templateUrl: 'AssignShifts/AssignShiftsSearch.html'
        }).when('/AssignShiftsPatternUpdate/:id', {
            controller: 'AssignShiftsCtrl',
            templateUrl: 'AssignShifts/AssignShifts.html'
        }).when('/WorkerTimeModification', {
            controller: 'ViewOrEditTimeByWorkerCtrl',
            templateUrl: 'ViewOrEditTimeByWorker/ViewOrEditTimeByWorkerSearch.html'
        }).when('/WorkerTimeModificationPage', {
            controller: 'ViewOrEditTimeByWorkerController',
            templateUrl: 'ViewOrEditTimeByWorker/ViewOrEditTimeByWorker.html'
        }).when('/DivisionSearch', {
            controller: 'DivisionSearchCtrl',
            templateUrl: 'Division/DivisionSearch.html'
        }).when('/DivisionAdd/:id', {
            controller: 'DivisionAddCtrl',
            templateUrl: 'Division/DivisionAdd.html'
        }).when('/WorkmenEmployedByContractorFormXIIIReport', {
            controller: 'workmenEmployedByContractorFormXIIIReportCtrl',
            templateUrl: 'Reports/WorkmenEmployedByContractorFormXIII/WorkmenEmployedByContractorFormXIIIReport.html'
        }).when('/WorkmenEmployedByContractorFormXIIIExcelReport', {
            controller: 'workmenEmployedByContractorFormXIIIReportCtrl',
            templateUrl: 'Reports/WorkmenEmployedByContractorFormXIII/WorkmenEmployedByContractorFormXIIIExcelReport.html'
        }).when('/OverTimeReport', {
            controller: 'OverTimeReportCtrl',
            templateUrl: 'Reports/OverTimeReport/OverTimeReport.html'
        }).when('/RegisterOfOverTimeXXIII', {
            controller: 'OverTimeReportCtrl',
            templateUrl: 'Reports/RegisterOfOverTimeXXIII/RegisterOfOverTimeXXIII.html'
        }).when('/DepartmentORContractorWiseLeftJoiningreport', {
            controller: 'OverTimeReportCtrl',
            templateUrl: 'Reports/DepartmentORContractorWiseLeftJoiningreport/DepartmentORContractorWiseLeftJoiningreport.html'
        }).when('/OTReportbyWorkmen', {
            controller: 'OverTimeReportCtrl',
            templateUrl: 'Reports/OTReportbyWorkmen/OTReportbyWorkmen.html'
        }).when('/RegisterofContractors', {
            controller: 'OverTimeReportCtrl',
            templateUrl: 'Reports/RegisterofContractors/RegisterofContractors.html'
        })
		/*.when('/AttandanceReport', {
            controller: 'attandanceReportSearchCtrl',
            templateUrl: 'Reports/AttandanceReport/AttandanceReport.html'
        })*/
		.when('/RegisterOfWages', {
            controller: 'OverTimeReportCtrl',
            templateUrl: 'Reports/RegisterOfWages/RegisterOfWages.html'
        }).when('/AttendanceSummaryReportByWorkmen', {
            controller: 'OverTimeReportCtrl',
            templateUrl: 'Reports/AttendanceSummaryReportByWorkmen/AttendanceSummaryReportByWorkmen.html'
        }).when('/EmploymentCard', {
            controller: 'OverTimeReportCtrl',
            templateUrl: 'Reports/EmploymentCard/EmploymentCard.html'
        }).when('/ServiceCertificate', {
            controller: 'OverTimeReportCtrl',
            templateUrl: 'Reports/ServiceCertificate/ServiceCertificate.html'
        }).when('/EmployeeGatePassAccess', {
            controller: 'EmployeeGatePassAccessCtrl',
            templateUrl: 'EmployeeGatePassAccess/EmployeeGatePassAccess.html'
        }).when('/uploadShift', {
            controller: 'shiftUploadController',
            templateUrl: 'UploadShift/BulkUploadShift.html'
        }).when('/WorkerTimeUpload', {
            controller: 'ViewOrEditTimeUploadController',
            templateUrl: 'ViewOrEditTimeByWorker/ViewOrEditTimeUpload/ViewOrEditTimeUpload.html'
        }).when('/bonusRules', {
            controller: 'bonusRulesControllerDtls',
            templateUrl: 'StatutorySetups/BonusRules/BonusRules.html'

        }).when('/searchLWFRules', {
            controller: 'LWFRulesSearchCtrl',
            templateUrl: 'StatutorySetups/LWFRules/LWFSearch.html'
        }).when('/defineLWFRule/:id', {
            controller: 'LWFRulesAddCtrl',
            templateUrl: 'StatutorySetups/LWFRules/DefineLWFRule.html'
        }).when('/searchProfessionalTax', {
            controller: 'PTaxSearchCtrl',
            templateUrl: 'StatutorySetups/ProfessionalTax/ProfessionalTaxSearch.html'
        }).when('/defineProfessionalTax/:id', {
            controller: 'PTaxAddCtrl',
            templateUrl: 'StatutorySetups/ProfessionalTax/DefineProfessionalTax.html'
        }).when('/esiDetails', {
            controller: 'esiDetailsCtrl',
            templateUrl: 'Esi/EsiDetails.html'
        }).when('/vendorRegisterTypesSearch', {
            controller: 'vendorRegisterTypeSearch',
            templateUrl: 'VendorRegisterTypes/VendorRegisterTypeSearch.html'
        }).when('/vendorRegisterTypes/:id', {
            controller: 'vendorRegisterTypesCtrl',
            templateUrl: 'VendorRegisterTypes/vendorRegisterTypes.html'
        }).when('/vendorDamageOrLossRegister/:id', {
            controller: 'vendorDamageRegisterCtrl',
            templateUrl: 'VendorRegisterTypes/VendorDamageRegister.html'
        }).when('/vendorFinesRegister/:id', {
            controller: 'vendorFinesRegisterCtrl',
            templateUrl: 'VendorRegisterTypes/VendorFinesRegister.html'
        }).when('/jobAllocationByVendor', {
            controller: 'jobAllocationByVendorController',
            templateUrl: 'jobAllocationByVendor/JobAllocationByVendorSearch.html'
        }).when('/jobAllocationApproval', {
            controller: 'jobAllocationApproval',
            templateUrl: 'jobAllocationByVendor/ApproveRejectJobAllocation.html'
        }).when('/departmentWiseWorkAllocationSearch', {
            controller: 'departmentWiseWorkAllocationSearchController',
            templateUrl: 'DepartmentWiseWorkerAllocation/DepartmentWiseWorkerAllocationSearch.html'
        }).when('/departmentWiseWorkAllocationSearch', {
            controller: 'departmentWiseWorkAllocationSearchController',
            templateUrl: 'DepartmentWiseWorkerAllocation/DepartmentWiseWorkerAllocationSearch.html'
        }).when('/departmentWiseWorkAllocationEdit/:id', {
            controller: 'departmentWiseWorkAllocationEditController',
            templateUrl: 'DepartmentWiseWorkerAllocation/DepartmentWiseWorkerAllocationDetails.html'
        }).when('/workerBankDetailsReport', {
            controller: 'workerBankDetailsCtrl',
            templateUrl: 'Reports/WorkerBankDetailsReport/WorkerBankDetailsSearch.html'
         }).when('/budget', {
            controller: 'BudgetAddCtrl',
            templateUrl: 'StatutorySetups/Budget/DefineBudget.html'
        }).when('/definePFRules', {
            controller: 'pfRulesController',
            templateUrl: 'PFRulesSetUp/DefinePFRules.html'
         }).when('/addBudget/:id', {
            controller: 'BudgetAddCtrl',
            templateUrl: 'StatutorySetups/Budget/DefineBudget.html'
        }).when('/searchBudget', {
            controller: 'BudgetSearchCtrl',
            templateUrl: 'StatutorySetups/Budget/SearchBudget.html'
        }).when('/addException/:id', {
            controller: 'ExceptionAddCtrl',
            templateUrl: 'Time/Exception/DefineException.html'
        }).when('/searchException', {
            controller: 'ExceptionSearchCtrl',
            templateUrl: 'Time/Exception/SearchException.html'
        }).when('/addWorkerGroup/:id', {
            controller: 'WorkerGroupAddCtrl',
            templateUrl: 'StatutorySetups/DefineWorkerGroup/DefineWorkerGroup.html'
        }).when('/searchWorkerGroup', {
            controller: 'WorkerGroupSearchCtrl',
            templateUrl: 'StatutorySetups/DefineWorkerGroup/SearchWorkerGroup.html'
        }).when('/definePFRules', {
            controller: 'pfRulesController',
            templateUrl: 'PFRulesSetUp/DefinePFRules.html'
        }).when('/paymentRules', {
            controller: 'PaymentRuleCtrl',
            templateUrl: 'ContractorBillReport/RulesForPayment/PaymentRules.html'
        }).when('/searchInvoice', {
            controller: 'InvoiceSearchCtrl',
            templateUrl: 'ContractorBillReport/Invoice/SearchInvoice.html'
        }).when('/addInvoice/:id', {
            controller: 'InvoiceAddCtrl',
             templateUrl: 'ContractorBillReport/Invoice/AddInvoice.html'
        }).when('/searchVendorDocument', {
            controller: 'VendorDocumentSearchCtrl',
            templateUrl: 'Documents/VendorDocuments/SearchVendorDocument.html'
        }).when('/addVendorDocument/:id', {
            controller: 'VendorDocumentAddCtrl',
            templateUrl: 'Documents/VendorDocuments/AddVendorDocument.html'
        }).when('/searchCompanyDocument', {
            controller: 'CompanyDocumentSearchCtrl',
            templateUrl: 'Documents/CompanyDocuments/SearchCompanyDocument.html'
        }).when('/addCompanyDocument/:id', {
            controller: 'CompanyDocumentAddCtrl',
            templateUrl: 'Documents/CompanyDocuments/AddCompanyDocument.html'
        }).when('/verificationTypesSetupSearch', {
            controller: 'veificationTypeSetupCtrl',
            templateUrl: 'VerificationTypesSetup/VerificationTypeSearch.html'
        }).when('/verificationTypesSetup/:id', {
            controller: 'AddveificationTypeSetupCtrl',
            templateUrl: 'VerificationTypesSetup/VerificationTypeSetup.html'
        }).when('/AssociatingDepartmentToLocationAndPlant', {
            controller: 'associatingDepartmentToLocationAndPlantController',
            templateUrl: 'AssociatingDepartmentToLocationAndPlant/AssociateDepartmentToLocationAndPlant.html'
        }).when('/screenActions', {
            controller: 'screenActionsSearchCtrl',
            templateUrl: 'ScreenActions/ScreenActionsSearch.html'
        }).when('/screenActions/:id', {
            controller: 'screenActionsCtrl',
            templateUrl: 'ScreenActions/ScreenActions.html'
        }).when('/permissionsGroup', {
            controller: 'permissionsGroupSearchCtrl',
            templateUrl: 'PermissionsGroup/PermissionsGroupSearch.html'
        }).when('/permissionsGroup/:id', {
            controller: 'permissionsGroupCtrl',
            templateUrl: 'PermissionsGroup/PermissionsGroup.html'
        }).when('/roles', {
            controller: 'rolesSearchCtrl',
            templateUrl: 'Roles/RolesSearch.html'
        }).when('/roles/:id', {
            controller: 'rolesCtrl',
            templateUrl: 'Roles/Roles.html'
        }).when('/users', {
            controller: 'usersSearchCtrl',
            templateUrl: 'Users/UsersSearch.html'
        }).when('/users/:id', {
            controller: 'usersCtrl',
            templateUrl: 'Users/Users.html'
        }).when('/searchManpowerRequest', {
        	controller: 'ManpowerSearchCtrl',
        	templateUrl: 'ManpowerRequisition/ManpowerRequisitionSearch.html'
        }).when('/addManpowerRequest/:id', {
        	controller: 'ManpowerAddCtrl',
        	templateUrl: 'ManpowerRequisition/ManpowerRequisitionAdd.html'
        }).when('/searchManpowerApprovals', {
        	controller: 'ManpowerApprovalSearchCtrl',
        	templateUrl: 'ManpowerRequisition/ManpowerRequisitionSearch.html'
        }).when('/manpowerRequestApproval/:id', {
        	controller: 'ManpowerApprovalCtrl',
        	templateUrl: 'ManpowerRequisition/ManpowerRequisitionApproval.html'
        }).when('/jobAllocationReport', {
	        controller: 'JobAllocationReportCtrl',
	        templateUrl: 'Reports/JobAllocationByVendor/JobAllocationReport.html'
        }).when('/approvalPathModule', {
            controller: 'approvalPathModuleSearchCtrl',
            templateUrl: 'ApprovalPathModule/ApprovalPathModuleSearch.html'
        }).when('/approvalPathModule/:id', {
            controller: 'approvalPathModuleCtrl',
            templateUrl: 'ApprovalPathModule/ApprovalPathModule.html'
        }).when('/approvalPathTransaction', {
            controller: 'approvalPathTransactionSearchCtrl',
            templateUrl: 'ApprovalPathTransaction/ApprovalPathTransactionSearch.html'
        }).when('/approvalPathTransaction/:id', {
            controller: 'approvalPathTransactionCtrl',
            templateUrl: 'ApprovalPathTransaction/ApprovalPathTransaction.html'
 		}).when('/applicationApprovalPathFlow', {
            controller: 'approvalPathFlowSearchCtrl',
            templateUrl: 'ApplicationApprovalPathFlow/ApplicationApprovalPathFlowSearch.html'
        }).when('/applicationApprovalPathFlow/:id', {
            controller: 'approvalPathFlowCtrl',
            templateUrl: 'ApplicationApprovalPathFlow/ApplicationApprovalPathFlow.html'
        }).when('/workerMedicalExamination', {
            controller: 'workerMedicalExaminationController',
            templateUrl: 'WorkerMedicalExamination/WorkerMedicalExamination.html'
        }).when('/workerPoliceVerification', {
            controller: 'workerPoliceVerificationController',
            templateUrl: 'WorkerPoliceVerification/WorkerPoliceVerification.html'
        }).when('/manpowerReport', {
	        controller: 'ManpowerReportCtrl',
	        templateUrl: 'Reports/ManpowerReport/ManpowerReport.html'
        }).when('/manpowerExcelReport', {
            controller: 'ManpowerReportCtrl',
            templateUrl: 'Reports/ManpowerReport/ManpowerExcelReport.html'
        }).when('/labourLicenseUtilizationReport', {
            controller: 'ManpowerReportCtrl',
            templateUrl: 'Reports/LabourLicenseUtilizationReport/LabourLicenseUtilizationReport.html'
        }).when('/manpowerApprovedVSActual', {
            controller: 'ManpowerReportCtrl',
            templateUrl: 'Reports/ManpowerApprovedVSActual/ManpowerApprovedVSActualExcelReport.html'
        }).otherwise({ redirectTo: '/login' });
    
	    IdleProvider.idle(600);
		IdleProvider.timeout(30);
		KeepaliveProvider.interval(600);
		TitleProvider.enabled(false);
	
	}
        ]).config(['$translateProvider', function ($translateProvider) {
 // $translateProvider.useSanitizeValueStrategy('sanitize');

 
  $translateProvider.preferredLanguage('en');
     $translateProvider.useStaticFilesLoader({
	    prefix: 'l10n/',
	    suffix: '.js'
	  });
  
    
}])

.run(['$rootScope', '$location', '$cookieStore', '$http','$translate','$translateStaticFilesLoader','RoleBasedScreensService','$browser','Idle','$cookies','$uibModal','$timeout','$templateCache',
    function ($rootScope, $location, $cookieStore, $http,$translate,$translateStaticFilesLoader,RoleBasedScreensService,$browser,Idle,$cookies,$uibModal,$timeout,$templateCache) {

        // keep user logged in after page refresh
	var year = new Date().getFullYear();
	$rootScope.getYears= [{"id":""+(year-1)+"","name":""+(year-1)+""},
                         {"id":""+year+"","name":""+year+""},
                         {"id":""+(year+1)+"","name":""+(year+1)+""}
                         ];
	
		
	/*if($cookieStore.get('roleName') != null && $cookieStore.get('roleName') != undefined && $cookieStore.get('roleName') == 'Customer Audit Admin'){
		$rootScope.CustomerAuditAdmin = true;    
	} else {
		$rootScope.CustomerAuditAdmin = false;
	}
*/
	if($cookieStore.get('userId') != undefined){
		RoleBasedScreensService.roleBasedScreens();
		 if($cookieStore.get('roleNamesArray').includes("Vendor Admin")){ 
			 //$location.path('/workerSearch');
		     	$rootScope.homeUrl = 'workerSearch';
		 }else{
			 //$location.path('/dashboard');
		     	$rootScope.homeUrl = 'dashboard';
		 }
	}

		
	 function closeModals() {
	        if ($rootScope.warning) {
	        	$rootScope.warning.close();
	        	$rootScope.warning = null;
	        }

	        if ($rootScope.timedout) {
	        	$rootScope.timedout.close();
	          $rootScope.timedout = null;
	        }
	}
	
	 $timeout(function () {
		Idle.watch();
		// alert('Idle.watch');
	 });
	 
	  $rootScope.$on('IdleStart', function() {		  
		 
		  if($cookieStore.get('userId') != undefined && $cookieStore.get('userId') != '' && parseInt($cookieStore.get('userId')) > 0){			  
			  closeModals();
			//  alert();
			  /* Display modal warning or sth */ 
			  //alert('Seesion is Going to Expire in 5 secs');
		    $rootScope.warning = $uibModal.open({
	          templateUrl: 'warning-dialog.html',
	          windowClass: 'modal-danger'
	        });
		  }
	  } );
	  
	  
	  $rootScope.$on('IdleTimeout', function() {
		  /* Logout user */		
		 // alert('IdleTimeout');
		  if($cookieStore.get('userId') != undefined && $cookieStore.get('userId') != '' && parseInt($cookieStore.get('userId')) > 0){	
			  closeModals();
			   $rootScope.timedout = $uibModal.open({
			          templateUrl: 'timedout-dialog.html',
			          windowClass: 'modal-danger'
			        });
			  var cookies = $cookies.getAll();
			    angular.forEach(cookies, function (v, k) {
			        $cookies.remove(k);
			    }); 
			    
		 
		    $location.path( "/Login" );
		  } 
	  });
	  
	
	 

	 

	var Base64={_keyStr:"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",encode:function(e){var t="";var n,r,i,s,o,u,a;var f=0;e=Base64._utf8_encode(e);while(f<e.length){n=e.charCodeAt(f++);r=e.charCodeAt(f++);i=e.charCodeAt(f++);s=n>>2;o=(n&3)<<4|r>>4;u=(r&15)<<2|i>>6;a=i&63;if(isNaN(r)){u=a=64}else if(isNaN(i)){a=64}t=t+this._keyStr.charAt(s)+this._keyStr.charAt(o)+this._keyStr.charAt(u)+this._keyStr.charAt(a)}return t},decode:function(e){var t="";var n,r,i;var s,o,u,a;var f=0;e=e.replace(/[^A-Za-z0-9+/=]/g,"");while(f<e.length){s=this._keyStr.indexOf(e.charAt(f++));o=this._keyStr.indexOf(e.charAt(f++));u=this._keyStr.indexOf(e.charAt(f++));a=this._keyStr.indexOf(e.charAt(f++));n=s<<2|o>>4;r=(o&15)<<4|u>>2;i=(u&3)<<6|a;t=t+String.fromCharCode(n);if(u!=64){t=t+String.fromCharCode(r)}if(a!=64){t=t+String.fromCharCode(i)}}t=Base64._utf8_decode(t);return t},_utf8_encode:function(e){e=e.replace(/rn/g,"n");var t="";for(var n=0;n<e.length;n++){var r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r)}else if(r>127&&r<2048){t+=String.fromCharCode(r>>6|192);t+=String.fromCharCode(r&63|128)}else{t+=String.fromCharCode(r>>12|224);t+=String.fromCharCode(r>>6&63|128);t+=String.fromCharCode(r&63|128)}}return t},_utf8_decode:function(e){var t="";var n=0;var r=c1=c2=0;while(n<e.length){r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r);n++}else if(r>191&&r<224){c2=e.charCodeAt(n+1);t+=String.fromCharCode((r&31)<<6|c2&63);n+=2}else{c2=e.charCodeAt(n+1);c3=e.charCodeAt(n+2);t+=String.fromCharCode((r&15)<<12|(c2&63)<<6|c3&63);n+=3}}return t}}

    var global={search:{CustomerId:'37',CompanyId:'5'},SearchKey:''};
	$rootScope.load=false;
	$rootScope.error=function(obj,obj1){
		alert("ERROR :"+obj);
		window.location.href=obj1;
	}
	$rootScope.filterParameter=function(dtJsonObject){
		if(dtJsonObject==undefined || dtJsonObject==null){
			dtJsonObject={};
		}
		for (var i in dtJsonObject) {
			  if (dtJsonObject[i] === '' || dtJsonObject[i] === null || dtJsonObject[i] === undefined) {
			  // test[i] === undefined is probably not very useful here
			    delete dtJsonObject[i];
			  }
			}
		return dtJsonObject;
	};
	$rootScope.validateParameter=function(dtJsonObject){
		if(dtJsonObject==undefined || dtJsonObject==null){
			return false;
		}
		for (var i in dtJsonObject) {
			  if (dtJsonObject[i] === '' || dtJsonObject[i] === null || dtJsonObject[i] === undefined) {
			  // test[i] === undefined is probably not very useful here
			    return false;
			  }
			}
		return true;
	};
	$rootScope.global=Base64.encode(angular.toJson(global));
	$rootScope.code=function(string,type){
		var Base64={_keyStr:"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",encode:function(e){var t="";var n,r,i,s,o,u,a;var f=0;e=Base64._utf8_encode(e);while(f<e.length){n=e.charCodeAt(f++);r=e.charCodeAt(f++);i=e.charCodeAt(f++);s=n>>2;o=(n&3)<<4|r>>4;u=(r&15)<<2|i>>6;a=i&63;if(isNaN(r)){u=a=64}else if(isNaN(i)){a=64}t=t+this._keyStr.charAt(s)+this._keyStr.charAt(o)+this._keyStr.charAt(u)+this._keyStr.charAt(a)}return t},decode:function(e){var t="";var n,r,i;var s,o,u,a;var f=0;e=e.replace(/[^A-Za-z0-9+/=]/g,"");while(f<e.length){s=this._keyStr.indexOf(e.charAt(f++));o=this._keyStr.indexOf(e.charAt(f++));u=this._keyStr.indexOf(e.charAt(f++));a=this._keyStr.indexOf(e.charAt(f++));n=s<<2|o>>4;r=(o&15)<<4|u>>2;i=(u&3)<<6|a;t=t+String.fromCharCode(n);if(u!=64){t=t+String.fromCharCode(r)}if(a!=64){t=t+String.fromCharCode(i)}}t=Base64._utf8_decode(t);return t},_utf8_encode:function(e){e=e.replace(/rn/g,"n");var t="";for(var n=0;n<e.length;n++){var r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r)}else if(r>127&&r<2048){t+=String.fromCharCode(r>>6|192);t+=String.fromCharCode(r&63|128)}else{t+=String.fromCharCode(r>>12|224);t+=String.fromCharCode(r>>6&63|128);t+=String.fromCharCode(r&63|128)}}return t},_utf8_decode:function(e){var t="";var n=0;var r=c1=c2=0;while(n<e.length){r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r);n++}else if(r>191&&r<224){c2=e.charCodeAt(n+1);t+=String.fromCharCode((r&31)<<6|c2&63);n+=2}else{c2=e.charCodeAt(n+1);c3=e.charCodeAt(n+2);t+=String.fromCharCode((r&15)<<12|(c2&63)<<6|c3&63);n+=3}}return t}}
if(type) return Base64.encode(angular.toJson(string));
else  return angular.fromJson(Base64.decode(string));
	}
        $rootScope.globals = $cookieStore.get('globals') || {};
         $rootScope.user = $cookieStore.get('user') || {};
       // $rootScope.user = angular.toJson($rootScope.user).replaceAll('"','').replaceAll('{','').replaceAll('}','')
         $rootScope.userName = $cookieStore.get('userName');
         //alert($rootScope.userName);
         /*if($rootScope.userName == undefined || $rootScope.userName == null || $rootScope.userName == ""){		
     		$("#menuDiv").removeClass("showMenu").addClass("hideMenu");
     	}*/
       // alert($rootScope.userName);
        if ($rootScope.globals.currentUser) {
            $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
            
        }
        if($rootScope.userName!= null && $rootScope.userName!= undefined && $rootScope.userName != '')
        	$("#menuDiv").attr('style','display:block');
        $rootScope.$on('$locationChangeStart', function (event, next, current) {        	
        	/*if(parseInt($cookieStore.get('userId')) == 5 && $location.path() == '/dashboard'){
        		$location.path('/unauthorized')
        	}  */     	
        	

        	if (($cookieStore.get('userName') == null || $cookieStore.get('userName') == undefined ||  $cookieStore.get('userName') == '' || $cookieStore.get('globals') == null || $cookieStore.get('globals') == undefined ||  $cookieStore.get('globals') == '') && $location.path() !== '/unauthorized') {        	

        		$location.path( "/login" );
        	}
            // redirect to login page if not logged in
            if ($location.path() !== '/login' && !$rootScope.globals.currentUser && $location.path() !== '/unauthorized' ) {
                $location.path('/login');
            }
            
           // $templateCache.removeAll();
            
        });
       // angular translate
            $rootScope.lang = { isopen: false };
           // $rootScope.langs = {en:'English', de_DE:'German', it_IT:'Italian'};
            $rootScope.langs = {en:'English',fr:'French',gj:'Gujrati',ar:'Arabic'};
            $rootScope.selectLang = $rootScope.langs[$translate.proposedLanguage()] || "English";
            
        $rootScope.setLang = function(langKey, $event) {//
              // set the current lang
                
              $rootScope.selectLang = $rootScope.langs[langKey];
              // You can change the language during runtime
              $translate.use(langKey);
              $rootScope.setLocale(langKey);
              $rootScope.lang.isopen = !$rootScope.lang.isopen;
                 $translate.use(langKey);
                $rootScope.changeLanguage(langKey);
            };
          $rootScope.changeLanguage = function (langKey) {
                $translate.use(langKey);
           };
                    $rootScope.setLocale = function(id) {
            	/*$rootScope.userLocalService.get({"id": id}, function (response) {
    	            if ($rootScope.handleErrors(response)) {
    	                
                        return;
    	            }
    	        })*/
    	    };
    }
     
     ]);
