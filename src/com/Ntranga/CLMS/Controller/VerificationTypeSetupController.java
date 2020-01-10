package com.Ntranga.CLMS.Controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Ntranga.CLMS.Service.CommonService;
import com.Ntranga.CLMS.Service.CompanyService;
import com.Ntranga.CLMS.Service.DepartmentService;
import com.Ntranga.CLMS.Service.EmployeeService;
import com.Ntranga.CLMS.Service.JobCodeService;
import com.Ntranga.CLMS.Service.VendorService;
import com.Ntranga.CLMS.vo.CustomerVo;
import com.Ntranga.CLMS.vo.DepartmentVo;
import com.Ntranga.CLMS.vo.DivisionVo;
import com.Ntranga.CLMS.vo.JobCodeVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


@SuppressWarnings("rawtypes")
@RestController
@RequestMapping(value = "verificationTypeSetupController")
public class VerificationTypeSetupController {

	private static final Logger log = Logger.getLogger(VerificationTypeSetupController.class);
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private VendorService vendorService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private JobCodeService jobCodeService;
	
	@Autowired
	private EmployeeService employeeService;
	
	/*
	 * This method will be used to save or update the department details
	 */
	@RequestMapping(value = "/saveDepartment.json", method = RequestMethod.POST)
	public ResponseEntity<SimpleObject> saveDepartment(@RequestBody DepartmentVo department) {
		log.info("Entered in Department Controller saveDepartment() ::  "+department);
		
		SimpleObject object = new SimpleObject();
		Integer deptId = null;
		try{
		int companyId = department.getCompanyId();
		int customerId = department.getCustomerId();
		int companyDetailsId = 0;
		List<SimpleObject> obj = companyService.getCompanyTransactionDates(customerId+"", companyId+"");
		if(obj!=null && obj.size() > 0) {
			companyDetailsId= (obj.get(0).getId());
		}
		
		log.info("Date "+companyDetailsId);
		java.util.Date companyDate = (companyService.getCompanyDetailsListByCompanyId(companyDetailsId).getTransactionDate());
		java.util.Date deptDate = (department.getTransactionDate());
		log.info("Diff "+companyDate.compareTo(deptDate));
		
		if(companyDate.compareTo(deptDate) <= 0){
			if (department != null){
				deptId = departmentService.saveDepartment(department);
				log.debug("Department ID: "+deptId);
			}
		
			if (deptId != null) {
				object.setId(deptId);
				object.setName("Success");
			} else {
				object.setId(0);
				object.setName("Fail");
			}
		}else{
			object.setId(-1);
			object.setName("Transaction date should not be less than Company Transaction Date");
		}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		log.info("Exiting from Department Controller saveDepartment() ::  "+object);
		return new ResponseEntity<SimpleObject>(object, HttpStatus.OK);
	}

	/*
	 * This method will be used to get the department records based on given customer Id/name and company Id/name
	 * 																					and department Id/name and status
	 */
	@RequestMapping(value = "/getDepartmentListBySearch.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getDepartmentsListBySearch(@RequestBody DepartmentVo department) throws JSONException {
		log.info("Entered in Department Controller getDepartmentsListBySearch()  ::   "+department);
		Map<String,List> returnData = new HashMap<String,List>();
		try{
		List<SimpleObject> deptTypelist = commonService.getDepartmentTypes();
		List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(String.valueOf(department.getCustomerId()));
		List<DepartmentVo> departmentList = departmentService.getDepartmentsListBySearch(department);
		log.debug("List of departments : "+departmentList);
		
		
		returnData.put("deptTypeList", deptTypelist);
		returnData.put("customerList",customerList);
		returnData.put("departmentList",departmentList);
		
		log.info("Exiting from Department Controller getDepartmentsListBySearch() ::  "+JSONObject.valueToString(null).toString());
		//return  JSONObject.valueToString(s).toString();
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(returnData,HttpStatus.OK);
	}
	
	/*
	 * This method will be used to get the department list based on department Id
	 */	
	@RequestMapping(value = "/getDepartmentById.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getDepartmentByDeptId(@RequestBody String department) throws JSONException {
		log.info("Entered in Department Controller getDepartmentByDeptId()  ::   "+department);
		Map<String,List> returnDept = new HashMap<String,List>();
		try{
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(department);
			log.debug("JSON Object:  "+jo);
			
			String departmentInfoId = null, customerId = null,divisionId = null;
			if(jo.get("departmentInfoId") != null && jo.get("departmentInfoId").getAsString() != null){
				departmentInfoId = jo.get("departmentInfoId").getAsString();  
				log.debug("Contact Id : "+departmentInfoId);
			}
			
			if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null){
				customerId = jo.get("customerId").getAsString();  
				log.debug("customer Id : "+customerId);
			}
			
			if(jo.get("divisionId") != null && jo.get("divisionId").getAsString() != null){
				divisionId = jo.get("divisionId").getAsString();  
				log.debug("divisionId Id : "+divisionId);
			}
			
			List<SimpleObject> deptTypes = commonService.getDepartmentTypes();
		    List<DepartmentVo> deptVo= departmentService.getDepartmentById(Integer.valueOf(departmentInfoId.trim()));	
		    List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(customerId);
		    List<SimpleObject> companyList = new ArrayList<SimpleObject>();
		    List<SimpleObject> costCenter = new ArrayList<SimpleObject>();
		    List<JobCodeVo> jobCodeList = new ArrayList<JobCodeVo>();
		    List<DivisionVo> divisionList = new ArrayList<DivisionVo>();
		    List<SimpleObject> employeeList = new ArrayList();
		    if(deptVo != null && deptVo.size() > 0){
		    	JobCodeVo jobcode = new JobCodeVo();
			    companyList = vendorService.getComapanyNamesAsDropDown((deptVo.get(0).getCustomerId())+"",deptVo.get(0).getCompanyId()+"");
				jobcode.setCustomerId(deptVo.get(0).getCustomerId());
				jobcode.setCompanyId(deptVo.get(0).getCompanyId());
				jobcode.setStatus("Y");
				
				DivisionVo paramDivision = new DivisionVo();
				paramDivision.setCustomerId(deptVo.get(0).getCustomerId());
				paramDivision.setCompanyId(deptVo.get(0).getCompanyId());
				paramDivision.setStatus("Y");
				paramDivision.setDivisionId(Integer.parseInt(divisionId));
				divisionList = companyService.getDivisionsListBySearch(paramDivision);
				costCenter = departmentService.getDepartmentNameForCostCenter(deptVo.get(0).getCustomerId(), deptVo.get(0).getCompanyId());
			    jobCodeList = jobCodeService.getJobCodesListBySearch(jobcode);
			    employeeList = employeeService.getEmployeesList(deptVo.get(0).getCustomerId(), deptVo.get(0).getCompanyId());
			    
		    }
			log.info("Exiting from Department Controller getDepartmentByDeptId() ::  "+deptVo);
			
			returnDept.put("divisionList", divisionList);
			returnDept.put("deptTypeList", deptTypes);
			returnDept.put("deptVo",deptVo);
			returnDept.put("customerList", customerList);
			returnDept.put("companyList", companyList);
			returnDept.put("costCenter",costCenter);
			returnDept.put("jobCodeList", jobCodeList);
			returnDept.put("employeeList", employeeList);
			//returnDept.put("DivisionList", divisionList);
		
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(returnDept, HttpStatus.OK);
	}

	/*
	 * 	This method will be used to generate dropdown list for cost center
	 */
	@RequestMapping(value = "/getDeptnameForCostCenter.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getDepartmentNameForCostCenter(@RequestBody String department) throws JSONException {
		log.info("Entered in Department Controller getDepartmentNameForCostCenter() ::  "+department);
		Map<String,List> returnDept = new HashMap<String,List>();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(department);
		log.debug("JSON Object : "+jo);
		
		String customerId = null;
		String companyId = null,divisionId = null;
		
		if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null){
			customerId = jo.get("customerId").getAsString();  
			log.debug("Customer Id : "+customerId);
		}
		
		if(jo.get("companyId") != null && jo.get("companyId").getAsString() != null){
			companyId = jo.get("companyId").getAsString();  
			log.debug("Company Id : "+companyId);
		}
		if(jo.get("divisionId") != null && jo.get("divisionId").getAsString() != null){
			divisionId = jo.get("divisionId").getAsString();  
			log.debug("divisionId Id : "+divisionId);
		}
		
		JobCodeVo jobcode = new JobCodeVo();
		jobcode.setCustomerId(Integer.valueOf(customerId.trim()));
		jobcode.setCompanyId(Integer.valueOf(companyId.trim()));
		jobcode.setStatus("Y");
		
		List<JobCodeVo> jobCodeList = jobCodeService.getJobCodesListBySearch(jobcode);

		
		DivisionVo paramDivision = new DivisionVo();
		paramDivision.setCustomerId(Integer.valueOf(customerId.trim()));
		paramDivision.setCompanyId(Integer.valueOf(companyId.trim()));
		paramDivision.setStatus("Y");
		paramDivision.setDivisionId(Integer.valueOf(divisionId.trim()));
		List<DivisionVo> divisionList = companyService.getDivisionsListBySearch(paramDivision);
		
		
		List<SimpleObject> object = new ArrayList<SimpleObject>();
		object = departmentService.getDepartmentNameForCostCenter(Integer.valueOf(customerId.trim()), Integer.valueOf(companyId.trim()) );
		
		 List<SimpleObject> employeeList = new ArrayList();
	    employeeList = employeeService.getEmployeesList(Integer.valueOf(customerId.trim()), Integer.valueOf(companyId.trim()));

		returnDept.put("costCenterList", object);
		returnDept.put("jobCodeList",jobCodeList);
		returnDept.put("divisionList", divisionList);
		returnDept.put("employeeList", employeeList);
		log.info("Exiting from Department Controller getDepartmentNameForCostCenter() ::  "+returnDept);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(returnDept, HttpStatus.OK);
	}
	
	/*
	 * This method will be used to get transaction dates list for department based on given customerId, companyId and departmentUniqueId
	 */
	@RequestMapping(value = "/getTransactionDatesList.json", method = RequestMethod.POST)
	public ResponseEntity<List<SimpleObject>> getTransactionDatesListForDepartment(@RequestBody String department) throws JSONException {
		log.info("Entered in Department Controller getTransactionDatesListForDepartment() ::  "+department);
		
		List<SimpleObject> simpleObjects =  new ArrayList<SimpleObject>();
		try{
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(department);
			log.debug("JSON Object "+jo);
			
			String customerId = null ,companyId = null, deptUniqueId = null;
			if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null
									&& jo.get("departmentUniqueId") != null && jo.get("departmentUniqueId").getAsString() != null
									&& jo.get("companyId") != null && jo.get("companyId").getAsString() != null){
				customerId = jo.get("customerId").getAsString(); 
				companyId = jo.get("companyId").getAsString();			
				deptUniqueId = jo.get("departmentUniqueId").getAsString(); 
				log.debug("Customer Id:  "+customerId+"\t Company Id :   "+companyId+"\t Department Unique Id : "+deptUniqueId);
			}
			 simpleObjects = departmentService.getTransactionListForDepartment(Integer.valueOf(customerId), Integer.valueOf(companyId),  Integer.valueOf(deptUniqueId));	
			log.info("Exiting from Department Controller getTransactionDatesListForDepartment() ::  "+simpleObjects);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>>(simpleObjects, HttpStatus.OK);
	}
	
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/c.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String, List<SimpleObject>>> getLocationsByCompanyId(@RequestBody String companyJsonString) {
		Map<String, List<SimpleObject>> map = new HashMap<String, List<SimpleObject>>();	
		JsonParser jsonParser = new JsonParser();  
		JsonObject jo = (JsonObject) jsonParser.parse(companyJsonString);
		try {			
			List<SimpleObject> countriesList = commonService.getCompanyCountries(jo.get("companyId").getAsInt());
			map.put("countriesList", countriesList);
		} catch (Exception e) {
			log.error("Error Occured. ",e);			
		}
		
		return new ResponseEntity<Map<String, List<SimpleObject>>>(map, HttpStatus.OK);
	}
	
	
}
