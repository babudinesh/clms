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
import com.Ntranga.CLMS.Service.JobCodeService;
import com.Ntranga.CLMS.Service.VendorService;
import com.Ntranga.CLMS.Service.WageRateService;
import com.Ntranga.CLMS.Service.WageScaleService;
import com.Ntranga.CLMS.Service.WorkerService;
import com.Ntranga.CLMS.vo.CompanyProfileVo;
import com.Ntranga.CLMS.vo.CustomerVo;
import com.Ntranga.CLMS.vo.JobCodeVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.WageRateVo;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@SuppressWarnings({"rawtypes","unused","unchecked"})
@RestController
@RequestMapping(value = "WageRateController")
public class WageRateController {

private static final Logger log = Logger.getLogger(WageRateController.class);
	
	@Autowired
	private WageRateService wageRateService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private VendorService vendorService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private JobCodeService jobCodeService;
	
	@Autowired
	private WageScaleService wageScaleService;
	
	@Autowired
	private WorkerService workerService;
	
	/*
	 * This method will be used to save or update the wage rate
	*/ 
	@RequestMapping(value = "/saveWageRate.json", method = RequestMethod.POST)
	public ResponseEntity<SimpleObject> saveWageRate(@RequestBody WageRateVo paramWage) {
		log.info("Entered in Wage Rate Controller saveWageRate() ::  "+paramWage);
		
		Integer workAreaId = null;
		SimpleObject object = new SimpleObject();
		try{
		int companyId = paramWage.getCompanyId();
		int customerId = paramWage.getCustomerId();
		int companyDetailsId = 0;
		List<SimpleObject> obj = companyService.getCompanyTransactionDates(customerId+"", companyId+"");
		if(obj!=null && obj.size() > 0) {
			companyDetailsId= (obj.get(0).getId());
		}
		
		//log.info("Date "+companyDetailsId);
		java.util.Date companyDate = (companyService.getCompanyDetailsListByCompanyId(companyDetailsId).getTransactionDate());
		java.util.Date wageRate = (paramWage.getTransactionDate());
		//log.info("Diff "+companyDate.compareTo(wageRate));
		
		if(companyDate.compareTo(wageRate) <= 0){
			if(paramWage != null){
				workAreaId = wageRateService.saveWageRate(paramWage);
				log.debug("Work Area Details ID: "+workAreaId);
			}
			
			if (workAreaId != null && workAreaId > 0) {
				object.setId(workAreaId);
				object.setName("Success");
			} else  {
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
		log.info("Exiting from  Wage Rate Controller saveWageRate() ::  "+object.toString());
		return new ResponseEntity<SimpleObject>(object, HttpStatus.OK);
	}

	
	/*
	 * This method will be used to get the wage rate records based on given customerId, company Id, wage rate code and wage rate name																				
	 */
	@RequestMapping(value = "/getWageRatesListBySearch.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getWageRatesListBySearch(@RequestBody WageRateVo paramWage) throws JSONException {
		log.info("Entered in  Wage Rate Controller getWageRatesListBySearch()   ::   "+paramWage);
		Map<String,List> returnData = new HashMap<String,List>();
		try{
		//List<SimpleObject> deptTypelist = commonService.getDepartmentTypes();
		
		// List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(String.valueOf(paramWage.getCustomerId()));
		List<WageRateVo> workAreaList = wageRateService.getWageRatesListBySearch(paramWage);
		log.debug("List of Wage rates : "+workAreaList);
		
	
		//returnData.put("deptTypeList", deptTypelist);
		//returnData.put("customerList",customerList);
		returnData.put("wageRateList",workAreaList);
		
		log.info("Exiting from  Wage Rate Controller getWageRatesListBySearch() ::  "+returnData);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		//return  JSONObject.valueToString(s).toString();
		return new ResponseEntity<Map<String,List>>(returnData,HttpStatus.OK);
	}
	
	/*
	 * This method will be used to get the wage rate record  based on given wage rate details Id
	 */	
	
	@RequestMapping(value = "/getWageRateById.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getWageRateById(@RequestBody String paramWage) throws JSONException {
		log.info("Entered in  Wage Rate Controller getWageRateById()  ::   "+paramWage);
		Map<String,List> returnList = new HashMap<String,List>();
		String locationId = null;
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(paramWage);
		log.debug("JSON Object:  "+jo);
		
		String wageRateDetailsId = null, customerId = null, companyId= null;
		
		if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null
				|| jo.get("companyId") != null && jo.get("companyId").getAsString() != null
						|| jo.get("wageRateDetailsId") != null && jo.get("wageRateDetailsId").getAsString() != null){
			customerId = jo.get("customerId").getAsString(); 
			//companyId = jo.get("companyId").getAsString(); 
			wageRateDetailsId = jo.get("wageRateDetailsId").getAsString();  
			log.debug("customer Id : "+customerId+" \t Wage Rate Details Id : "+wageRateDetailsId);
		}

		if(jo.has("locationId") && jo.get("locationId") != null &&  !jo.get("locationId").isJsonNull() && jo.get("locationId").getAsJsonArray().size() > 0 ){
			JsonArray locId = jo.get("locationId").getAsJsonArray();
			locationId = locId.toString().replace("]", "").replace("[", "");
		}
		
	    List<WageRateVo> wageRateList= wageRateService.getWageRateById(Integer.valueOf(wageRateDetailsId.trim()));	
	    List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(customerId);
	    Integer wageRateId = (wageRateList != null && wageRateList.size() > 0)? wageRateList.get(0).getWageRateId() : 0;
	    List<SimpleObject> countryList =  new ArrayList<SimpleObject>();
	    List<WageRateVo> wageRateCurrency = new ArrayList<WageRateVo>();
	    List<SimpleObject> currencyList = commonService.getCurrencyList();
	    List<SimpleObject> companyList = new ArrayList<SimpleObject>();
		List<JobCodeVo> jobCodeList = new ArrayList<JobCodeVo>();
		List<CompanyProfileVo> defaultCurrency = new ArrayList<CompanyProfileVo>();
		
		if(wageRateList != null && wageRateList.size() > 0){
			wageRateCurrency.add(wageRateList.get(0));
			
	    	JobCodeVo paramJobcode = new JobCodeVo();
	    	paramJobcode.setCustomerId(wageRateList.get(0).getCustomerId());
	    	paramJobcode.setCompanyId(wageRateList.get(0).getCompanyId());
	    	paramJobcode.setCountryId(wageRateList.get(0).getCountryId());
	    	
	    	companyList = vendorService.getComapanyNamesAsDropDown((wageRateList.get(0).getCustomerId())+"",(wageRateList.get(0).getCompanyId())+"");
	        
	    	jobCodeList = jobCodeService.getJobCodesListBySearch(paramJobcode);
	    	
	    	countryList = commonService.getCompanyCountries(wageRateList.get(0).getCompanyId());
	    	List<SimpleObject> vendorList  = workerService.getVendorNamesAsDropDown((wageRateList.get(0).getCustomerId())+"",(wageRateList.get(0).getCompanyId())+"","0","Validated",locationId);
			List<SimpleObject> wageScaleList  = wageScaleService.getWageScaleServices((wageRateList.get(0).getCustomerId())+"",(wageRateList.get(0).getCompanyId())+"");
			List<SimpleObject> workOrderNames  = companyService.getWorkOrderAsDropDown((wageRateList.get(0).getCustomerId())+"",(wageRateList.get(0).getCompanyId())+"",(wageRateList.get(0).getVendorId())+"");
			returnList.put("workOrderNames", workOrderNames);
			returnList.put("vendorList", vendorList);
			returnList.put("wageScaleList", wageScaleList);
			returnList.put("countryList", countryList);
			
			CompanyProfileVo companyProfile = companyService.getCompanyProfileByCompanyId(wageRateList.get(0).getCompanyId()+"", (wageRateList.get(0).getCustomerId()+""));
			defaultCurrency.add(companyProfile);
			returnList.put("companyProfile", defaultCurrency);
			
	    	
	    }
	    
		returnList.put("wageRateVo",wageRateList);
		returnList.put("customerList", customerList);		
		returnList.put("currencyList",currencyList);
		returnList.put("jobCodeList",jobCodeList);
		returnList.put("companyList",companyList);
		returnList.put("wageRateCurrency",wageRateCurrency);
		returnList.put("workPeiceList",wageRateCurrency);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		log.info("Exiting from  Wage Rate Controller getWageRateById() ::  "+returnList);
		return new ResponseEntity<Map<String,List>>(returnList, HttpStatus.OK);
	}

	/*
	 * This method will be used to get transaction dates list for wage rate based on given customerId, companyId and wageRateId
	 */
	@RequestMapping(value = "/getWageRateTransactionDatesList.json", method = RequestMethod.POST)
	public ResponseEntity<List<SimpleObject>> getTransactionDatesListForWageRate(@RequestBody String paramwage) throws JSONException {
		log.info("Entered in  Wage Rate Controller getTransactionDatesListForWageRate() ::  "+paramwage);
		List<SimpleObject> simpleObjects =  new ArrayList();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(paramwage);
		log.debug("JSON Object "+jo);
		
		String customerId = null ,companyId = null, wageRateId = null;
		
		if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null
								&& jo.get("companyId") != null && jo.get("companyId").getAsString() != null
								&& jo.get("wageRateId") != null && jo.get("wageRateId").getAsString() != null){
			customerId = jo.get("customerId").getAsString(); 
			companyId = jo.get("companyId").getAsString();			
			wageRateId = jo.get("wageRateId").getAsString(); 
			log.debug("Customer Id:  "+customerId+"\t Company Id :   "+companyId+"\t Wage Rate Id : "+wageRateId);
		}
		simpleObjects = wageRateService.getTransactionListForWageRate(Integer.valueOf(customerId), Integer.valueOf(companyId),  Integer.valueOf(wageRateId));	
		log.info("Exiting from  Wage Rate Controller getTransactionDatesListForWageRate() ::  "+simpleObjects);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>>(simpleObjects, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getWorkOrderAsDropDown.json", method = RequestMethod.POST)
	public  ResponseEntity<List<SimpleObject>> getWorkOrderAsDropDown(@RequestBody String searchJsonString) {
		List<SimpleObject> workOrderNames = new ArrayList();
		try{		
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(searchJsonString);		
		
		String customerId = "";
		String companyId= "";
		String vendorId= "";
		if(jo.get("customerId")!= null && !jo.get("customerId").toString().equalsIgnoreCase("null")){
			customerId = jo.get("customerId").getAsString();
		}
		if(jo.get("companyId")!= null && !jo.get("companyId").toString().equalsIgnoreCase("null")){
			companyId = jo.get("companyId").getAsString();
		}
		if(jo.get("vendorId")!= null && !jo.get("vendorId").toString().equalsIgnoreCase("null")){
			vendorId = jo.get("vendorId").getAsString();
		}
		
		 SimpleObject obj = new SimpleObject();
		 obj.setId(0);
		 obj.setName("ALL");
		 workOrderNames.add(obj);
		 workOrderNames  = companyService.getWorkOrderAsDropDown(customerId, companyId,vendorId);
		
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>>(workOrderNames,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getVendorNamesAsDropDown.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List<SimpleObject>>>getVendorNamesAsDropDown(@RequestBody String customerCompanyVendorJsonString) {	
		Map<String,List<SimpleObject>> map = new HashMap<String,List<SimpleObject>>();
		String locationId = null;
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(customerCompanyVendorJsonString);		
		
		String customerId = "",companyId= "",vendorId= "";		
		if(jo.get("customerId")!= null && !jo.get("customerId").toString().equalsIgnoreCase("null")){
			customerId = jo.get("customerId").getAsString();
		}
		if(jo.get("companyId")!= null && !jo.get("companyId").toString().equalsIgnoreCase("null")){
			companyId = jo.get("companyId").getAsString();
		}
		if(jo.get("vendorId")!= null && !jo.get("vendorId").toString().equalsIgnoreCase("null")){
			vendorId = jo.get("vendorId").getAsString();
		}
		if(jo.has("locationId") && jo.get("locationId") != null &&  !jo.get("locationId").isJsonNull() && jo.get("locationId").getAsJsonArray().size() > 0 ){
			JsonArray locId = jo.get("locationId").getAsJsonArray();
			locationId = locId.toString().replace("]", "").replace("[", "");
		}
		List<SimpleObject> vendorList  = workerService.getVendorNamesAsDropDown(customerId, companyId,vendorId,"Validated",locationId);
		List<SimpleObject> wageScaleList  = wageScaleService.getWageScaleServices(customerId, companyId);
		List<SimpleObject> countryList = commonService.getCompanyCountries(jo.get("companyId").getAsInt());
		map.put("vendorList", vendorList);
		map.put("wageScaleList", wageScaleList);
		map.put("countryList", countryList);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List<SimpleObject>>>(map,HttpStatus.OK);
	}
	
	
}
