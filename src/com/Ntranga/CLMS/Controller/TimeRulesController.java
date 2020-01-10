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
import com.Ntranga.CLMS.Service.TimeRulesService;
import com.Ntranga.CLMS.Service.VendorService;
import com.Ntranga.CLMS.vo.CompanyProfileVo;
import com.Ntranga.CLMS.vo.CustomerVo;
import com.Ntranga.CLMS.vo.DefineExceptionVo;
import com.Ntranga.CLMS.vo.LocationVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.TimeExceptionRulesVo;
import com.Ntranga.CLMS.vo.TimeRulesDetailsVo;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@SuppressWarnings({"rawtypes","unchecked"})
@RestController
@RequestMapping(value = "timeRulesController")
public class TimeRulesController {
	
	private static Logger log = Logger.getLogger(TimeRulesController.class);
	@Autowired
	private VendorService vendorService;

	@Autowired
	private CommonService commonService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private TimeRulesService timeRulesService;
	
	

	
	@RequestMapping(value = "/getCountryandShiftDetails.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getCountryandShiftDetails(@RequestBody String customerCompanyJsonString) {		
		log.info("Entered in TimeRulesController Controller getCountryandShiftDetails()  ::   "+customerCompanyJsonString);
		Map<String,List> returnData = new HashMap<String,List>();	
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(customerCompanyJsonString);
		log.debug("JSON Object "+jo);
		
		String customerId = null, companyId= null;
		
		if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null){
			customerId = jo.get("customerId").getAsString(); 
			log.debug("Customer Id : "+customerId);
		}
		
		if(jo.get("companyId") != null && jo.get("companyId").getAsString() != null){
			companyId = jo.get("companyId").getAsString(); 
			log.debug("Company Id : "+companyId);
		}	
		
		List<SimpleObject> countriesList = commonService.getCompanyCountries(Integer.parseInt(companyId));
		returnData.put("countriesList", countriesList);
		
		List<LocationVo> profileList = new ArrayList<LocationVo>();
		CompanyProfileVo profile = companyService.getCompanyProfileByCompanyId(companyId, customerId);	
		//List<SimpleObject> ruleCodesList = timeRulesService.getRuleCodesList(Integer.parseInt(customerId), Integer.parseInt(companyId));
		LocationVo cmp = new LocationVo();		
		cmp.setWorkWeekEndDay(profile.getWorkWeekEndId()+"");
		cmp.setWorkWeekStartDay(profile.getWorkWeekStartId()+"");
		cmp.setBusinessHoursPerDay((profile.getBussinessHoursPerDay()));
		cmp.setStandardHoursPerWeek(profile.getStandarHoursPerWeek());			
		profileList.add(cmp);	
		//returnData.put("ruleCodesList", ruleCodesList);
		returnData.put("profileList", profileList);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(returnData,HttpStatus.OK);
	}
	
	/*
	 * This method will be used to get the time rule records based on given data
	 * 																					
	 */
	@RequestMapping(value = "/getTimeRulesListBySearch.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getTimeRulesListBySearch(@RequestBody TimeRulesDetailsVo paramTime) throws JSONException {
		log.info("Entered in  Time Rules Controller getTimeRulesListBySearch()   ::   "+paramTime);
		Map<String,List> returnData = new HashMap<String,List>();
		try{
			List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(String.valueOf(paramTime.getCustomerId()));
			List<TimeRulesDetailsVo> timeRulesList = timeRulesService.getTimeRulesListBySearch(paramTime);
			log.debug("List of TimeRules : "+timeRulesList);
		
			returnData.put("customerList",customerList);
			returnData.put("timeRulesList",timeRulesList);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		log.info("Exiting from  Time Rules Controller getTimeRulesListBySearch() ::  "+returnData);
		//return  JSONObject.valueToString(s).toString();
		return new ResponseEntity<Map<String,List>>(returnData,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/saveOrUpdateTimeRules.json", method = RequestMethod.POST)
	public  ResponseEntity<Integer> saveOrUpdateTimeRules(@RequestBody TimeRulesDetailsVo rulesDetailsVo) {				
		Integer branchId = timeRulesService.saveOrUpdateTimeRules(rulesDetailsVo);		
		return new ResponseEntity<Integer>(branchId,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getTransactionDatesListForTimeRules.json", method = RequestMethod.POST)
	public  ResponseEntity<List<SimpleObject>> getTransactionDatesListForEditingTimeRules(@RequestBody String timeRulesId) {
		List<SimpleObject> transactionDatesList = new ArrayList();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(timeRulesId);				
		 transactionDatesList  = timeRulesService.getTransactionDatesListForEditingTimeRules(jo.get("timeRulesId").getAsInt());
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>>(transactionDatesList,HttpStatus.OK);
	}

	
	@RequestMapping(value = "/getDetailsByRuleInfoId.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getDetailsByRuleInfoId(@RequestBody String ruleInfoIdJson) {
		Map<String,List> masterInfoMap = new HashMap<String,List>();		
		try{
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(ruleInfoIdJson);
			log.debug("JSON Object "+jo);
			
			String timeRulesInfoId = null, customerId = null;
			
			if(jo.get("timeRulesInfoId") != null && jo.get("timeRulesInfoId").getAsString() != null){
				timeRulesInfoId = jo.get("timeRulesInfoId").getAsString(); 
				log.debug("Time Rules Info Id : "+timeRulesInfoId);
			}
			
			if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null){
				customerId = jo.get("customerId").getAsString(); 
				log.debug("Customer Id : "+customerId);
			}
			
			List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(customerId);
			List<TimeRulesDetailsVo> timeRulesList	=timeRulesService.getDetailsByRuleInfoId(Integer.parseInt(timeRulesInfoId));
			//List<LocationVo> profileList = new ArrayList<LocationVo>();
			List<SimpleObject> countriesList = new ArrayList<>();
			List<SimpleObject> companyList = new ArrayList<>();
			
			if(timeRulesList != null && timeRulesList.size() > 0 ){
				companyList  = vendorService.getComapanyNamesAsDropDown(timeRulesList.get(0).getCustomerId()+"",timeRulesList.get(0).getCompanyId()+"");
				countriesList = commonService.getCompanyCountries(timeRulesList.get(0).getCompanyId());
			}
			
			masterInfoMap.put("timeRulesList",  timeRulesList);
			masterInfoMap.put("customerList", customerList);
			masterInfoMap.put("companyList", companyList);
			masterInfoMap.put("countriesList", countriesList);
		
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);				
		
	}
	
	/*
	 * This service is used to validate the time rule code that already exists are not
	 */
	@RequestMapping(value = "/validateTimeRuleCode.json", method = RequestMethod.POST)
	public Integer validateTimeRuleCode(@RequestBody TimeRulesDetailsVo  paramTime) throws JSONException {
		log.info("Entered in  Time Rules Controller validateExceptionCode() ::  "+paramTime);
		Integer returnString = null;
		try{
			Integer timeRuleCode = timeRulesService.validateTimeRuleCode(paramTime);
			
			log.info("Exception Code:  "+timeRuleCode);
			System.out.println(timeRuleCode);

			if(timeRuleCode != null && timeRuleCode == 0 ){
				returnString = 0;
			}else{
				returnString = 1;
			}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		System.out.println(returnString);
			log.info("Exiting from  Time Rules Controller validateTimeRuleCode() ::  "+returnString);
			return returnString;
	}
	
	/******************** Time Rule Exception Start    ***************/
	
	/*
	 * This method will be used to save or update the time exception data
	*/ 
	@RequestMapping(value = "/saveTimeException.json", method = RequestMethod.POST)
	public ResponseEntity<SimpleObject> saveTimeException(@RequestBody TimeExceptionRulesVo paramException) {
		log.info("Entered in Time Rules Controller saveTimeException() ::  "+paramException);
		
		Integer timeRulesInfoId = null;
		SimpleObject object = new SimpleObject();
		try{
			
			if(paramException != null ){
				timeRulesInfoId = timeRulesService.saveTimeException(paramException);
				log.debug("Time Rules Info Id : "+timeRulesInfoId);
			}
			
			if (timeRulesInfoId != null && timeRulesInfoId > 0) {
				object.setId(timeRulesInfoId);
				object.setName("Success");
			} else {
				object.setId(0);
				object.setName("Fail");
			}
		
		}catch(Exception e){
			log.error("Error Occured ",e);
			object.setId(-1);
			object.setName("Exception Occurred. "+e);
		}
		log.info("Exiting from Time Rules Controller saveTimeException() ::  "+object.toString());
		return new ResponseEntity<SimpleObject>(object, HttpStatus.OK);
	}

	
	/*
	 * This method will be used to get the time exception record  based on time rule  id
	 */	
	
	@RequestMapping(value = "/getTimeExceptionById.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getTimeExceptionById(@RequestBody TimeExceptionRulesVo paramException) throws JSONException {
		log.info("Entered in  Time Rules Controller getTimeExceptionById()  ::   "+paramException);
		
		Map<String,List> returnList = new HashMap<String,List>();
		try{
			
		    List<TimeExceptionRulesVo> exceptionRulesList = timeRulesService.getTimeExceptionById(paramException);	
		    List<SimpleObject> exceptionsList = new ArrayList<>();
		    
		    if(paramException.getCustomerId() != null && paramException.getCustomerId() > 0
		    			&& paramException.getCompanyId() != null && paramException.getCompanyId() > 0
		    			&& paramException.getCountryId() != null && paramException.getCountryId() > 0){
		    	exceptionsList = timeRulesService.getExceptionsDropDown(paramException.getCustomerId(), paramException.getCompanyId(), paramException.getCountryId());
		    }
		    //List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(paramException.getCustomerId()+"");
		   // List<SimpleObject> countryList = commonService.getCountriesList();
		   // List<SimpleObject> companyList = new ArrayList<SimpleObject>();
		     
		    /*if(exceptionVo != null && exceptionVo.size() > 0){
		    	companyList = vendorService.getComapanyNamesAsDropDown((exceptionVo.get(0).getCustomerId())+"",(exceptionVo.get(0).getCompanyId())+"");
		    }*/
		    
			returnList.put("exceptionRulesList",exceptionRulesList);
		    returnList.put("exceptionsList",exceptionsList);
			//returnList.put("customerList", customerList);
			//returnList.put("countryList",countryList);
			//returnList.put("companyList",companyList);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		log.info("Exiting from  Time Rules Controller getTimeExceptionById() ::  "+returnList);
		return new ResponseEntity<Map<String,List>>(returnList, HttpStatus.OK);
	}

	/*
	 * This method will be used to get transaction dates list for Exception based on given customerId, companyId and exception unique Id
	 */
	@RequestMapping(value = "/getTransactionDatesListForTimeException.json", method = RequestMethod.POST)
	public ResponseEntity<List<SimpleObject>> getTransactionDatesListForTimeException(@RequestBody String paramException) throws JSONException {
		log.info("Entered in  Time Rules Controller getTransactionDatesListForException() ::  "+paramException);
		List<SimpleObject> simpleObjects = new ArrayList();
		try{
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(paramException);
			log.debug("JSON Object "+jo);
			
			String customerId = null ,companyId = null, exceptionUniqueId = null;
			if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null
									&& jo.get("exceptionUniqueId") != null && jo.get("exceptionUniqueId").getAsString() != null
									&& jo.get("companyId") != null && jo.get("companyId").getAsString() != null){
				customerId = jo.get("customerId").getAsString(); 
				companyId = jo.get("companyId").getAsString();			
				exceptionUniqueId = jo.get("exceptionUniqueId").getAsString(); 
				log.debug("Customer Id:  "+customerId+"\t Company Id :   "+companyId+"\t Exception Unique Id : "+exceptionUniqueId);
			}
			
			simpleObjects = timeRulesService.getTransactionDatesListForTimeException(Integer.valueOf(customerId), Integer.valueOf(companyId),  Integer.valueOf(exceptionUniqueId));	
			log.info("Exiting from  Time Rules Controller getTransactionDatesListForTimeException() ::  "+simpleObjects);
		}catch(Exception e){
			log.error("Error Occured ",e);
			log.info("Exiting from  Time Rules Controller getTransactionDatesListForTimeException() ::  "+simpleObjects);

		}
		return new ResponseEntity<List<SimpleObject>>(simpleObjects, HttpStatus.OK);
	}	

	
	/************************ Exception Start ******************************/
	/*
	 * This method will be used to save or update the exception details
	*/ 
	@RequestMapping(value = "/saveException.json", method = RequestMethod.POST)
	public ResponseEntity<SimpleObject> saveException(@RequestBody DefineExceptionVo paramException) {
		log.info("Entered in Time Rules Controller saveException() ::  "+paramException);
		
		Integer exceptionId = null;
		
		SimpleObject object = new SimpleObject();
		try{
			
			if(paramException != null ){
				exceptionId = timeRulesService.saveException(paramException);
				log.debug("divisionId: "+exceptionId);
			}
			
			if (exceptionId != null && exceptionId > 0) {
				object.setId(exceptionId);
				object.setName("Success");
			} else {
				object.setId(0);
				object.setName("Fail");
			}
		
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		log.info("Exiting from Time Rules Controller saveException() ::  "+object.toString());
		return new ResponseEntity<SimpleObject>(object, HttpStatus.OK);
	}

	
	/*
	 * This method will be used to get the exception records based on given data
	 * 																					
	 */
	@RequestMapping(value = "/getExceptionsListBySearch.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getExceptionsListBySearch(@RequestBody DefineExceptionVo paramException) throws JSONException {
		log.info("Entered in  Time Rules Controller getExceptionsListBySearch()   ::   "+paramException);
		Map<String,List> returnData = new HashMap<String,List>();
		try{
		List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(String.valueOf(paramException.getCustomerId()));
		List<DefineExceptionVo> exceptionList = timeRulesService.getExceptionsListBySearch(paramException);
		log.debug("List of Exceptions : "+exceptionList);
		
		
		returnData.put("customerList",customerList);
		returnData.put("exceptionList",exceptionList);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		log.info("Exiting from  Time Rules Controller getExceptionsListBySearch() ::  "+returnData);
		return new ResponseEntity<Map<String,List>>(returnData,HttpStatus.OK);
	}
	
	/*
	 * This method will be used to get the exception record  based on exceptionId
	 */	
	
	@RequestMapping(value = "/getExceptionById.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getExceptionById(@RequestBody String paramException) throws JSONException {
		log.info("Entered in  Time Rules Controller getExceptionById()  ::   "+paramException);
		
		Map<String,List> returnList = new HashMap<String,List>();
		try{
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(paramException);
			log.debug("JSON Object:  "+jo);
			
			String exceptionId = null, customerId = null;
			if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null
							|| (jo.get("exceptionId") != null && jo.get("exceptionId").getAsString() != null)){
				customerId = jo.get("customerId").getAsString(); 
				exceptionId = jo.get("exceptionId").getAsString();  
				log.debug("customer Id : "+customerId+" \t Exception Id : "+exceptionId);
			}
	
		    List<DefineExceptionVo> exceptionVo= timeRulesService.getExceptionById(Integer.valueOf(exceptionId.trim()));	
		    List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(customerId);
		    List<SimpleObject> countryList = commonService.getCountriesList();
		    List<SimpleObject> companyList = new ArrayList<SimpleObject>();
		     
		    if(exceptionVo != null && exceptionVo.size() > 0){
		    	companyList = vendorService.getComapanyNamesAsDropDown((exceptionVo.get(0).getCustomerId())+"",(exceptionVo.get(0).getCompanyId())+"");
		    }
		    
			returnList.put("exceptionVo",exceptionVo);
			returnList.put("customerList", customerList);
			returnList.put("countryList",countryList);
			returnList.put("companyList",companyList);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		log.info("Exiting from  Time Rules Controller getExceptionById() ::  "+returnList);
		return new ResponseEntity<Map<String,List>>(returnList, HttpStatus.OK);
	}

	/*
	 * This method will be used to get transaction dates list for Exception based on given customerId, companyId and exception unique Id
	 */
	@RequestMapping(value = "/getTransactionDatesListForException.json", method = RequestMethod.POST)
	public ResponseEntity<List<SimpleObject>> getTransactionDatesListForException(@RequestBody String paramException) throws JSONException {
		log.info("Entered in  Time Rules Controller getTransactionDatesListForException() ::  "+paramException);
		List<SimpleObject> simpleObjects = new ArrayList();
		try{
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(paramException);
			log.debug("JSON Object "+jo);
			
			String customerId = null ,companyId = null, exceptionId = null;
			if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null
									&& jo.get("exceptionId") != null && jo.get("exceptionId").getAsString() != null
									&& jo.get("companyId") != null && jo.get("companyId").getAsString() != null){
				customerId = jo.get("customerId").getAsString(); 
				companyId = jo.get("companyId").getAsString();			
				exceptionId = jo.get("exceptionId").getAsString(); 
				log.debug("Customer Id:  "+customerId+"\t Company Id :   "+companyId+"\t Exception Id : "+exceptionId);
			}
			
			simpleObjects = timeRulesService.getTransactionDatesListForException(Integer.valueOf(customerId), Integer.valueOf(companyId),  Integer.valueOf(exceptionId));	
			log.info("Exiting from  Time Rules Controller getTransactionDatesListForException() ::  "+simpleObjects);
		}catch(Exception e){
			log.error("Error Occured ",e);
			log.info("Exiting from  Time Rules Controller getTransactionDatesListForException() ::  "+simpleObjects);

		}
		return new ResponseEntity<List<SimpleObject>>(simpleObjects, HttpStatus.OK);
	}

	
	/*
	 * This service is used to validate the exception code that already exists are not
	 */
	@RequestMapping(value = "/validateExceptionCode.json", method = RequestMethod.POST)
	public Integer validateExceptionCode(@RequestBody DefineExceptionVo paramException) throws JSONException {
		log.info("Entered in  Time Rules Controller validateExceptionCode() ::  "+paramException);
		Integer returnString = null;
		try{
			//List<DefineExceptionVo> exceptionList = timeRulesService.getExceptionsListBySearch(paramException);
			Integer exceptionCode = timeRulesService.validateExceptionCode(paramException);
			
			log.info("Exception Code:  "+exceptionCode);
			System.out.println(exceptionCode);
			//List<CompanyDetailsInfoVo>
			if((exceptionCode != null && exceptionCode == 0)){
				returnString = 0;
			}else{
				returnString = 1;
			}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		System.out.println(returnString);
		log.info("Exiting from  Time Rules Controller validateExceptionCode() ::  "+returnString);
		return returnString;
	}
	
	
	 /********************* Exception End ***************************/
}
