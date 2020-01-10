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
import com.Ntranga.CLMS.Service.JobCodeService;
import com.Ntranga.CLMS.Service.VendorService;
import com.Ntranga.CLMS.vo.CustomerVo;
import com.Ntranga.CLMS.vo.JobCodeVo;
import com.Ntranga.CLMS.vo.MedicalTestVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping(value = "JobcodeController")
public class JobCodeController {

private static final Logger log = Logger.getLogger(JobCodeController.class);
	
	@Autowired
	private JobCodeService jobCodeService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private VendorService vendorService;
	
	@Autowired
	private CompanyService companyService;
	
	/*
	 * This method will be used to save or update the job code details
	*/ 
	@RequestMapping(value = "/saveJobcode.json", method = RequestMethod.POST)
	public ResponseEntity<SimpleObject> saveJobCode(@RequestBody JobCodeVo paramJobCode) {
		log.info("Entered in JobCode Controller saveJobCode() ::  "+paramJobCode);
		
		String jobCodeId = null;
		SimpleObject object = new SimpleObject();
		try{
		int companyId = paramJobCode.getCompanyId();
		int customerId = paramJobCode.getCustomerId();
		int companyDetailsId = 0;
		List<SimpleObject> obj = companyService.getCompanyTransactionDates(customerId+"", companyId+"");
		if(obj!=null && obj.size() > 0) {
			companyDetailsId= (obj.get(0).getId());
		}
		
		log.info("Date "+companyDetailsId);
		java.util.Date companyDate = (companyService.getCompanyDetailsListByCompanyId(companyDetailsId).getTransactionDate());
		java.util.Date deptDate = (paramJobCode.getTransactionDate());
		log.info("Diff "+companyDate.compareTo(deptDate));
		
		if(companyDate.compareTo(deptDate) <= 0){
			if(paramJobCode != null){
				jobCodeId = jobCodeService.saveJobCode(paramJobCode);
				log.debug("Job Code ID: "+jobCodeId);
			}
			
			if (jobCodeId != null) {
				object.setId(0);
				object.setName(jobCodeId);
			} else {
				object.setId(1);
				object.setName("Fail");
			}
		}else{
			object.setId(-1);
			object.setName("Transaction date should not be less than Company Transaction Date");
		}	
		}catch(Exception e){
			object.setId(1);
			log.error("Error Occured ",e);
		}
		log.info("Exiting from JobCode Controller saveJobCode() ::  "+object.toString());
		return new ResponseEntity<SimpleObject>(object, HttpStatus.OK);
	}

	
	/*
	 * This method will be used to get the job code records based on given  company Id and job code
	 * 																					
	 */
	@RequestMapping(value = "/getJobCodeListBySearch.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getJobCodeListBySearch(@RequestBody JobCodeVo paramJobcode) throws JSONException {
		log.info("Entered in JobCode Controller getDepartmentsListBySearch()   ::   "+paramJobcode);
		Map<String,List> returnData = new HashMap<String,List>();
		try{
		//List<SimpleObject> deptTypelist = commonService.getDepartmentTypes();
		// List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(String.valueOf(paramJobcode.getCustomerId()));
		List<JobCodeVo> jobCodeList = jobCodeService.getJobCodesListBySearch(paramJobcode);
		log.debug("List of Job Codes : "+jobCodeList);
		
		
		//returnData.put("deptTypeList", deptTypelist);
		// returnData.put("customerList",customerList);
		returnData.put("jobCodeList",jobCodeList);
		
		log.info("Exiting from JobCode Controller getJobCodeListBySearch() ::  "+JSONObject.valueToString(null).toString());
		//return  JSONObject.valueToString(s).toString();
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(returnData,HttpStatus.OK);
	}
	
	/*
	 * This method will be used to get the job code  based on job code Id
	 */	
	@RequestMapping(value = "/getJobCodeById.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getJobCodeById(@RequestBody String paramJobCode) throws JSONException {
		log.info("Entered in JobCode Controller getJobCodeById()  ::   "+paramJobCode);
		Map<String,List> returnList = new HashMap<String,List>();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(paramJobCode);
		log.debug("JSON Object:  "+jo);
		
		String jobCodeDetailsId = null, customerId = null, jobCodeId= null;;
		if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null
						|| jo.get("jobCodeDetailsId") != null && jo.get("jobCodeDetailsId").getAsString() != null
						|| jo.get("jobCodeId") != null && jo.get("jobCodeId").getAsString() != null){
			customerId = jo.get("customerId").getAsString(); 
			jobCodeDetailsId = jo.get("jobCodeDetailsId").getAsString();  
			jobCodeId = jo.get("jobCodeId").getAsString();  
			log.debug("customer Id : "+customerId+" \t Job Code Details Id : "+jobCodeDetailsId+" \t Job Code Id : "+jobCodeId);
		}
		
		List<SimpleObject> trainingTypes = commonService.getTrainingTypes(Integer.valueOf(jobCodeId.trim()));
		List<SimpleObject> equipmentTypes = commonService.getEquipmentTypes(Integer.valueOf(jobCodeId.trim()));
		List<SimpleObject> skillTests = commonService.getSkillTests(Integer.valueOf(jobCodeId.trim()));
		List<MedicalTestVo> medicalTests = commonService.getMedicalTests(Integer.valueOf(jobCodeId.trim()));
	    List<JobCodeVo> jobCodeVo= jobCodeService.getJobCodeById(Integer.valueOf(jobCodeDetailsId.trim()));	
	    List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(customerId);
	    List<SimpleObject> countryList = new ArrayList<SimpleObject>();
	   // List<SimpleObject> countryList = vendorService.getComapanyNamesAsDropDown(customerId);
	    
	    if(jobCodeVo != null && jobCodeVo.size() > 0){
		    List<SimpleObject> companyList  = vendorService.getComapanyNamesAsDropDown(jobCodeVo.get(0).getCustomerId()+"",jobCodeVo.get(0).getCompanyId()+"");		

		    returnList.put("companyList", companyList);
		    countryList = commonService.getCompanyCountries(jobCodeVo.get(0).getCompanyId());
	    }
	    
		returnList.put("trainingTypes", trainingTypes);
		returnList.put("equipmentTypes", equipmentTypes);
		returnList.put("skillTests", skillTests);
		returnList.put("medicalTests", medicalTests);
		returnList.put("jobCodeVo",jobCodeVo);
		returnList.put("customerList", customerList);
		returnList.put("countrylist", countryList);
		
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		
		log.info("Exiting from JobCode Controller getJobCodeById() ::  "+returnList);
		return new ResponseEntity<Map<String,List>>(returnList, HttpStatus.OK);
	}

	/*
	 * This method will be used to get transaction dates list for department based on given customerId, companyId and departmentUniqueId
	 */
	@RequestMapping(value = "/getTransactionDatesList.json", method = RequestMethod.POST)
	public ResponseEntity<List<SimpleObject>> getTransactionDatesListForJobCode(@RequestBody String paramJobCode) throws JSONException {
		log.info("Entered in JobCode Controller getTransactionDatesListForJobCode() ::  "+paramJobCode);
		List<SimpleObject> simpleObjects = new ArrayList();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(paramJobCode);
		log.debug("JSON Object "+jo);
		
		String customerId = null ,companyId = null, jobCodeDetailsId = null;
		if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null
								&& jo.get("jobCodeId") != null && jo.get("jobCodeId").getAsString() != null
								&& jo.get("companyId") != null && jo.get("companyId").getAsString() != null){
			customerId = jo.get("customerId").getAsString(); 
			companyId = jo.get("companyId").getAsString();			
			jobCodeDetailsId = jo.get("jobCodeId").getAsString(); 
			log.debug("Customer Id:  "+customerId+"\t Company Id :   "+companyId+"\t Job Code Details Id : "+jobCodeDetailsId);
		}
		simpleObjects = jobCodeService.getTransactionListForJobCode(Integer.valueOf(customerId), Integer.valueOf(companyId),  Integer.valueOf(jobCodeDetailsId));	
		log.info("Exiting from JobCode Controller getTransactionDatesListForJobCode() ::  "+simpleObjects);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>>(simpleObjects, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getTrainingTypes.json", method = RequestMethod.POST)
	public ResponseEntity<List<SimpleObject>> getTrainingTypes(@RequestBody String paramJobCode) throws JSONException {
		log.info("Entered in JobCode Controller getTrainingTypes() ::  "+paramJobCode);
		List<SimpleObject> simpleObjects = new ArrayList();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(paramJobCode);
		log.debug("JSON Object "+jo);
		
		String customerId = null ,companyId = null, jobCodeId = null;
		if(jo.get("jobCodeId") != null && jo.get("jobCodeId").getAsString() != null){
				
			jobCodeId = jo.get("departmentUniqueId").getAsString(); 
			log.debug("Customer Id:  "+customerId+"\t Company Id :   "+companyId+"\t Job code Id : "+jobCodeId);
		}
		 simpleObjects = commonService.getTrainingTypes(Integer.valueOf(jobCodeId));	
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		log.info("Exiting from JobCode Controller getTrainingTypes() ::  "+simpleObjects);
		return new ResponseEntity<List<SimpleObject>>(simpleObjects, HttpStatus.OK);
	}
	
	/*
	 * This service is used to validate the Jobcode that already exists are not
	 */
	@RequestMapping(value = "/validateJobcode.json", method = RequestMethod.POST)
	public String validateJobcode(@RequestBody JobCodeVo paramJobCode) throws JSONException {
		log.info("Entered in JobCode Controller validateJobcode() ::  "+paramJobCode);
		String returnString = null;
		try{
		List<JobCodeVo> jobCodeList = jobCodeService.getJobCodesListBySearch(paramJobCode);
		log.debug("List of Job Codes : "+jobCodeList);
		if(jobCodeList != null && jobCodeList.size() > 0){
			returnString ="0";
		}else{
			returnString = "1";
		}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		log.info("Exiting from JobCode Controller validateJobcode() ::  "+returnString);
		return returnString;
	}
	
	
	
	/*
	 * This service is used to validate the Jobcode is associated with any worker or not
	 */
	@RequestMapping(value = "/validateJobcodeAssociationWithWorker.json", method = RequestMethod.POST)
	public String validateJobcodeAssociationWithWorker(@RequestBody JobCodeVo paramJobCode) throws JSONException {
		log.info("Entered in JobCode Controller validateJobcodeAssociationWithWorker() ::  "+paramJobCode);
		String returnString = null;
		try{
			int code = jobCodeService.validateJobcodeAssociationWithWorker(paramJobCode);
		log.debug("List of Job Codes : "+code);		
			returnString =code+"";		
		}catch(Exception e){			
			log.error("Error Occured ",e);
		}
		log.info("Exiting from JobCode Controller validateJobcode() ::  "+returnString);
		return returnString;
	}
	

}
