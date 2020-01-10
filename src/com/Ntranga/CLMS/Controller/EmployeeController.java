package com.Ntranga.CLMS.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Ntranga.CLMS.Service.CommonService;
import com.Ntranga.CLMS.Service.CompanyService;
import com.Ntranga.CLMS.Service.CustomerService;
import com.Ntranga.CLMS.Service.EmployeeService;
import com.Ntranga.CLMS.Service.VendorService;
import com.Ntranga.CLMS.vo.CompanyProfileVo;
import com.Ntranga.CLMS.vo.CountryVo;
import com.Ntranga.CLMS.vo.CustomerVo;
import com.Ntranga.CLMS.vo.EmployeeInformationVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@RestController
@RequestMapping(value = "EmployeeController")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private VendorService vendorService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CompanyService companyService;
	
	private static Logger log = Logger.getLogger(EmployeeController.class);

	
	
	
	
	
	
	//
	
	
	@RequestMapping(value = "/getcustomerAndJobsAsDropDownsList.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getcustomerAndJobsAsDropDownsList(@RequestBody String CustomerIdJson) {
		Map<String,List> masterInfoMap = new HashMap<String,List>();	
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(CustomerIdJson);
		log.debug("JSON Object "+jo);		
		Integer customerId = 0;
		Integer companyId = 0;
		if(jo.get("customerId") != null && jo.get("customerId").getAsInt() >0){
			customerId = jo.get("customerId").getAsInt(); 
			log.debug("Customer Id : "+customerId);
		}	
				
		List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(customerId+"");
		List<SimpleObject> jobTypeList =	employeeService.getJobTypeList();
		List<SimpleObject> JobStatusLst =	employeeService.getJobStatusList();	
		masterInfoMap.put("customerList", customerList);
		masterInfoMap.put("jobTypeList", jobTypeList);		
		masterInfoMap.put("JobStatusLst", JobStatusLst);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getemployeeGridData.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getemployeeGridData(@RequestBody String CustomerIdJson) {
		Map<String,List> masterInfoMap = new HashMap<String,List>();	
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(CustomerIdJson);
		System.out.println(CustomerIdJson);
		log.debug("JSON Object "+jo);		
		Integer customerId = 0;
		Integer companyId = 0;
		Integer jobTypeId= 0;		
		Integer jobStatusId =0;
		Integer employeeCountryId = 0;
	    String employeeCode = null; 		 
	    		 
		if(jo.get("customerId") != null && jo.get("customerId").getAsInt() >0){
			customerId = jo.get("customerId").getAsInt(); 
			log.debug("Customer Id : "+customerId);
		}	
		
		if(jo.get("companyId") != null && jo.get("companyId").getAsInt() >0){
			companyId = jo.get("companyId").getAsInt(); 
			log.debug("companyId Id : "+companyId);
		}
			
		if(jo.get("jobTypeId") != null && jo.get("jobTypeId").getAsInt() >0){
			jobTypeId = jo.get("jobTypeId").getAsInt(); 
			log.debug("jobTypeId Id : "+jobTypeId);
		}
		if(jo.get("jobStatusId") != null && jo.get("jobStatusId").getAsInt() >0){
			jobStatusId = jo.get("jobStatusId").getAsInt(); 
			log.debug("jobStatusId Id : "+jobStatusId);
		}
		if(jo.get("employeeCountryId") != null && jo.get("employeeCountryId").getAsInt() >0){
			employeeCountryId = jo.get("employeeCountryId").getAsInt(); 
			log.debug("employeeCountryId Id : "+employeeCountryId);
		}
		
		
		if(jo.get("employeeCode") != null && jo.get("employeeCode").getAsString() != null){
			employeeCode = jo.get("employeeCode").getAsString(); 
			log.debug("employeeCode Id : "+employeeCode);
		}
		
		List<EmployeeInformationVo> empList =	employeeService.getEmployeeGridData(customerId,companyId,jobTypeId,jobStatusId,employeeCountryId,employeeCode);
		masterInfoMap.put("empList", empList);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getcountryAndCompanyList.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getcountryAndCompanyList(@RequestBody String CustomerIdJson) {
		Map<String,List> masterInfoMap = new HashMap<String,List>();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(CustomerIdJson);
		log.debug("JSON Object "+jo);		
		String customerId = null;
		String companyId = null;
		if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null){
			customerId = jo.get("customerId").getAsString(); 
			log.debug("Customer Id : "+customerId);
		}		
		if(jo.get("companyId") != null && jo.get("companyId").getAsString() != null){
			companyId = jo.get("companyId").getAsString(); 
			log.debug("companyId Id : "+companyId);
		}
		
		List<SimpleObject> companyList = vendorService.getComapanyNamesAsDropDown(customerId,companyId);		
		List<CountryVo> countryList  = customerService.getCountriesListByCustomerId(customerId);
		
		masterInfoMap.put("companyList", companyList);		
		masterInfoMap.put("countryList", countryList);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getEmployeeDropDownMasterData.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getEmployeeDropDownMasterData(@RequestBody String CustomerIdCompanyIdJson) {
		Map<String,List> masterInfoMap = new HashMap<String,List>();	
		try{
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(CustomerIdCompanyIdJson);
			log.debug("JSON Object "+jo);		
			Integer customerId = 0;
			Integer companyId =0;
			if(jo.get("customerId") != null && jo.get("customerId").getAsInt() >0){
				customerId = jo.get("customerId").getAsInt(); 
				log.debug("Customer Id : "+customerId);
			}		
			if(jo.get("companyId") != null && jo.get("companyId").getAsInt() >0){
				companyId = jo.get("companyId").getAsInt(); 
				log.debug("companyId Id : "+companyId);
			}
			
			List<SimpleObject> countryList = commonService.getCountriesList();		
			List<SimpleObject> locationList = new ArrayList();
			List<SimpleObject> jobCodesList = new ArrayList();
			List<SimpleObject> managerList = new ArrayList();
			List<CompanyProfileVo> profileList = new ArrayList<CompanyProfileVo>();
			//List<SimpleObject> departmentList = new ArrayList<SimpleObject>();
			
			if(customerId >0 && companyId >0){
				 locationList = employeeService.getLocationListByCustomerIdAndCompanyId(customerId, companyId, null);
				 jobCodesList = employeeService.getJobCodeListforCUstomerAndCompany(customerId,companyId);
				 managerList = employeeService.getReportingMangerList(customerId, companyId);
				// departmentList =	employeeService.getDepartmentListByCompanyId(customerId,companyId);
					
				 CompanyProfileVo profileVo = companyService.getCompanyProfileByCompanyId(companyId+"",customerId+"");  
				 profileList.add(profileVo);
			}		
			masterInfoMap.put("managerList", managerList);
			masterInfoMap.put("locationList", locationList);
			masterInfoMap.put("jobCodesList", jobCodesList);		
			masterInfoMap.put("countryList", countryList);
			masterInfoMap.put("companyProfile", profileList);
		//	masterInfoMap.put("departmentList", departmentList);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	}
	
	
	/*@RequestMapping(value = "/getDepartmentListByLocationId.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getDepartmentListByLocationId(@RequestBody String locationIdJson) {
		Map<String,List> masterInfoMap = new HashMap<String,List>();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(locationIdJson);
		log.debug("JSON Object "+jo);		
		Integer locationId = null;
		if(jo.get("locationId") != null && jo.get("locationId").getAsInt() >0){
			locationId = jo.get("locationId").getAsInt(); 
			log.debug("locationId Id : "+locationId);
		}		
	
		List<SimpleObject> departmentList =	employeeService.getDepartmentListByLocationId(locationId);
		masterInfoMap.put("departmentList", departmentList);
		
		
		}catch(Exception e){
			log.error("Error Occured ",e);
		}	
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	}*/
	
	/*@RequestMapping(value = "/getDepartmentListByCompanyId.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getDepartmentListByCompanyId(@RequestBody String locationIdJson) {
		Map<String,List> masterInfoMap = new HashMap<String,List>();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(locationIdJson);
		log.debug("JSON Object "+jo);		
		Integer customerId = null;
		Integer companyId = null;
		if(jo.get("customerId") != null && jo.get("customerId").getAsInt() >0){
			customerId = jo.get("customerId").getAsInt(); 
			log.debug("Customer Id : "+customerId);
		}	
		if(jo.get("companyId") != null && jo.get("companyId").getAsInt() >0){
			companyId = jo.get("companyId").getAsInt(); 
			log.debug("Company Id : "+companyId);
		}		
	
		List<SimpleObject> departmentList =	employeeService.getDepartmentListByCompanyId(customerId,companyId);
		masterInfoMap.put("departmentList", departmentList);
		
		
		}catch(Exception e){
			log.error("Error Occured ",e);
		}	
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	}*/
	
	
	@RequestMapping(value = "/saveEmployeeDetails.json", method = RequestMethod.POST)
	public ResponseEntity<SimpleObject> saveEmployeeDetails(@RequestBody EmployeeInformationVo employeeVo) {
		log.info("Entered in saveEmployeeDetails Controller  ::  ");
		SimpleObject object = new SimpleObject();
		
		try {
			Integer employeeId = employeeService.saveEmployeeDetails(employeeVo);
			
			if (employeeId != null && employeeId > 0) {
				object.setId(employeeId);
				object.setName("Success");
			} else {
				object.setId(0);
				object.setName("Failed");
			}
		} catch (Exception e) {
			log.error("Error Occured. ",e);
			log.info("Exiting from Employee Controller saveEmployeeDetails() ::  "+object);
			object.setId(0);
			object.setName("Failed");
		}
		log.info("Exiting from Employee Controller saveEmployeeDetails() ::  "+object);
		return new ResponseEntity<SimpleObject>(object,HttpStatus.OK);
		
		
	}
	
	@RequestMapping(value = "/getEmployeeDetailsByEmployeeId.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getEmployeeDetailsByEmployeeId(@RequestBody String employeeIdIdJson) {
		Map<String,List> masterInfoMap = new HashMap<String,List>();
		try{
		JsonParser jsonParser = new JsonParser();
		System.out.println("::employeeIdIdJson"+employeeIdIdJson);
		JsonObject jo = (JsonObject) jsonParser.parse(employeeIdIdJson);
		log.debug("JSON Object "+jo);		
		Integer employeeId = null;
		if(jo.get("employeeId") != null && jo.get("employeeId").getAsInt() >0){
			employeeId = jo.get("employeeId").getAsInt(); 
			log.debug("employeeId Id : "+employeeId);
		}	
		
		if(employeeId != null && employeeId > 0){
			List<EmployeeInformationVo> emplyeeList =	employeeService.employeeDetailsByemployeeId(employeeId);
			masterInfoMap.put("emplyeeList", emplyeeList);
					
			List<SimpleObject> countryList = commonService.getCountriesList();		
			List<SimpleObject> locationList = new ArrayList<SimpleObject>();
			List<SimpleObject> jobCodesList = new ArrayList<SimpleObject>();
			List<SimpleObject> managerList = new ArrayList<SimpleObject>();
			List<CompanyProfileVo> profileList = new ArrayList<CompanyProfileVo>();
			List<SimpleObject> departmentList = new ArrayList<SimpleObject>();
			
			locationList = employeeService.getLocationListByCustomerIdAndCompanyId(emplyeeList.get(0).getCustomerId(), emplyeeList.get(0).getCompanyId(), null);
			jobCodesList = employeeService.getJobCodeListforCUstomerAndCompany(emplyeeList.get(0).getCustomerId(),emplyeeList.get(0).getCompanyId());
			managerList = employeeService.getReportingMangerList(emplyeeList.get(0).getCustomerId(), emplyeeList.get(0).getCompanyId());
			 departmentList =	employeeService.getDepartmentListByCompanyId(emplyeeList.get(0).getCustomerId(),emplyeeList.get(0).getCompanyId());
			 
			CompanyProfileVo profileVo = companyService.getCompanyProfileByCompanyId(emplyeeList.get(0).getCompanyId()+"",emplyeeList.get(0).getCustomerId()+"");  
			profileList.add(profileVo);
			
			masterInfoMap.put("managerList", managerList);
			masterInfoMap.put("locationList", locationList);
			masterInfoMap.put("jobCodesList", jobCodesList);		
			masterInfoMap.put("countryList", countryList);
			masterInfoMap.put("companyProfile", profileList);
			masterInfoMap.put("departmentList", departmentList);
		}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
					
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getTransactionListForEmployee.json", method = RequestMethod.POST)
	public  ResponseEntity<List<SimpleObject>> getTransactionListForEmployee(@RequestBody String uniquIdJson) throws JSONException {
		log.info("Entered in Employee Controller getTransactionListForEmployee() ::  "+uniquIdJson);
		List<SimpleObject> simpleObjects = new ArrayList();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(uniquIdJson);
		log.debug("JSON Object "+jo);
		
		Integer uniqueId = null;
		if(jo.get("uniqueId") != null && jo.get("uniqueId").getAsInt() >0 ){
			uniqueId = jo.get("uniqueId").getAsInt(); 
			log.debug("uniqueId Id : "+uniqueId);
			
		}
		 simpleObjects = employeeService.getTransactionListForEmployeeInfo(uniqueId);	
		log.debug("JSON Object "+simpleObjects.size());
	}catch(Exception e){
		log.error("Error Occured ",e);
	}
		return new ResponseEntity<List<SimpleObject>>(simpleObjects, HttpStatus.OK);
	}
	

	/*@RequestMapping(value = "/getEmployeesList.json", method = RequestMethod.POST)
	public  ResponseEntity<List<SimpleObject>> getEmployeesList(@RequestBody String inputJson) throws JSONException {
		log.info("Entered in Employee Controller getEmployeesList() ::  "+inputJson);
		List<SimpleObject> simpleObjects = new ArrayList();
		try{
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(inputJson);
			log.debug("JSON Object "+jo);
			
			Integer customerId = null, companyId = null;
			if(jo.get("customerId") != null && jo.get("customerId").getAsInt() >0 ){
				customerId = jo.get("customerId").getAsInt(); 
				log.debug("Customer Id : "+customerId);
				
			}
			
			if(jo.get("companyId") != null && jo.get("companyId").getAsInt() >0 ){
				companyId = jo.get("companyId").getAsInt(); 
				log.debug("Company Id : "+companyId);
				
			}
			
			 simpleObjects = employeeService.getEmployeesList(customerId, companyId);	
			 log.debug("JSON Object "+simpleObjects.size());
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
	return new ResponseEntity<List<SimpleObject>>(simpleObjects, HttpStatus.OK);
	}*/
	
	
	
	
}
