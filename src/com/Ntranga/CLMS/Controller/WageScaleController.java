package com.Ntranga.CLMS.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Ntranga.CLMS.Service.CommonService;
import com.Ntranga.CLMS.Service.VendorService;
import com.Ntranga.CLMS.Service.WageScaleService;
import com.Ntranga.CLMS.vo.CustomerVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.WageScaleDetailsVo;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@RestController
@RequestMapping(value = "wageScaleController")
public class WageScaleController {
	
	private static Logger log = Logger.getLogger(WageScaleController.class);
	
	
	@Autowired
	private WageScaleService wageScaleService;
	
	@Autowired
	private VendorService vendorService;
	
	@Autowired 
	private CommonService commonservice;
	

	@RequestMapping(value="/wageScaleGridDetails.json", method = RequestMethod.POST)
	public ResponseEntity<List<WageScaleDetailsVo>>  wageScaleGridDetails(@RequestBody String gridParameters) throws Exception {		
		System.out.println("gridParameters::"+gridParameters);
		String customerId = "";
	    String companyId =  "";
	    String locationId ="";
	    String status = "";
	    String wageScaleCode = "";
	    String wageScaleName = "";
	    List<WageScaleDetailsVo> gridData =  new ArrayList();
	    try{
		JsonParser jsonParser = new JsonParser();
		  JsonObject jo = (JsonObject)jsonParser.parse(gridParameters);   
		if(gridParameters != null) {
	   
			if(jo.get("customerId") != null){
				 customerId =  jo.get("customerId").getAsString();
			}
			if(jo.get("companyId") != null){
				companyId =  jo.get("companyId").getAsString();
			}
			if(jo.get("locationName") != null){
				locationId =  jo.get("locationName").getAsString();
			}
			if(jo.get("status") != null){
				status =  jo.get("status").getAsString();
			}
			if(jo.get("wageScaleCode") != null){
				wageScaleCode =  jo.get("wageScaleCode").getAsString();
			}
			if(jo.get("wageScaleName") != null){
				wageScaleName =  jo.get("wageScaleName").getAsString();
			}
			
		}			
		 gridData = wageScaleService.wageScaleGridDetails(customerId, companyId, locationId, status, wageScaleCode, wageScaleName)	;
	    }catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<WageScaleDetailsVo>>(gridData,HttpStatus.OK);
	}

	
	
	
	
	
	@RequestMapping(value = "/saveOrUpdatewageScale.json", method = RequestMethod.POST)
	public  ResponseEntity<Integer> saveOrUpdatewageScale(@RequestBody WageScaleDetailsVo wageScaleDetailsVo) {	
		Integer wageScaleId = 0;
		try{
		 wageScaleId = wageScaleService.saveOrUpdateWageScaleDetails(wageScaleDetailsVo);	
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Integer>(wageScaleId,HttpStatus.OK);
	}
	
	
	
	
	@RequestMapping(value = "/getTransactionDatesListForEditingWageScale.json", method = RequestMethod.POST)
	public  ResponseEntity<List<SimpleObject>> getTransactionDatesListForEditingTimeRules(@RequestBody String wageScaleIdJson) {
		List<SimpleObject> transactionDatesList = new ArrayList();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(wageScaleIdJson);				
		 transactionDatesList  = wageScaleService.getTransactionDatesListForEditingWageScale(jo.get("wageScaleCode").getAsString());
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>>(transactionDatesList,HttpStatus.OK);
	}
	
	
	
	
	
	
	@RequestMapping(value = "/getDetailsBywageScaleId.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getDetailsBywageScaleId(@RequestBody String CustomerIdwageScaleIdJson) {
		
		Map<String,List> masterInfoMap = new HashMap<String,List>();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(CustomerIdwageScaleIdJson);
		log.debug("JSON Object "+jo);
		
		String wageScaleId = null;
		String customerId = null;
		
		if(jo.get("wageScaleId") != null && jo.get("wageScaleId").getAsString() != null){
			wageScaleId = jo.get("wageScaleId").getAsString(); 
			log.debug("wageScaleId Id : "+wageScaleId);
		}
		
		
		List<WageScaleDetailsVo> wageScaleDetailsList =  new ArrayList();
		if(Integer.parseInt(wageScaleId) > 0){
			 wageScaleDetailsList	=wageScaleService.getDetailsBywageScaleId(Integer.parseInt(wageScaleId));	
		}
			
		//List<SimpleObject> currency = commonservice.getCurrencyList();
		
		if(CustomerIdwageScaleIdJson != null && jo.get("customerId") != null) {	     
			customerId =  jo.get("customerId").getAsString();
		}
		
		List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(customerId);					
		masterInfoMap.put("customerList", customerList);
		
		//masterInfoMap.put("currency",currency);		
		masterInfoMap.put("wageScaleDetailsList",  wageScaleDetailsList);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);				
		
	}
	
	
	
	@RequestMapping(value="/validateWageScaleCode.json", method = RequestMethod.POST)
	  public  String validateCustomerCode(@RequestBody String parameters) {
		  	log.info("Entered in Customer Controller validateWageScaleCode() ::  "+parameters);
	        String wageIsValid =null;	 
	        try {
	        JsonParser jsonParser = new JsonParser();			
	        JsonObject jo = (JsonObject )jsonParser.parse(parameters);
	        log.debug("JSON Object:  "+jo);
	        
	        String wageScaleCode =  jo.get("wageScaleCode").getAsString();
	        String customerId =  jo.get("customerId").getAsString();
	        String companyId =  jo.get("companyId").getAsString();
	        Integer wageScaleId = jo.get("wageScaleId").getAsInt();
	        log.debug("wageScaleCode :  "+wageScaleCode);
	       
	        	wageIsValid =  JSONObject.valueToString(wageScaleService.validateWageScaleCode(wageScaleCode, customerId, companyId,wageScaleId)).toString();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("Error Occured ",e);
			}
	        log.info("Exiting from  wageIsValid() ::  "+wageIsValid);
	        return wageIsValid;
	  }
	
	
	
	
	@RequestMapping(value = "/getlocationNamesAsDropDowns.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getLocationNamesAsDropDown(@RequestBody String customerCompanyJsonString) {		
		Map<String,List> masterInfoMap = new HashMap<String,List>();	
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(customerCompanyJsonString);
		System.out.println(customerCompanyJsonString);	
		Integer customerId = 0;
		Integer companyId = 0;
		
		
		if (jo.get("customerId") != null && !jo.get("customerId").toString().equalsIgnoreCase("null")) {
			customerId = jo.get("customerId").getAsInt();
		}

		if (jo.get("companyId") != null && !jo.get("companyId").toString().equalsIgnoreCase("null")) {
			companyId = jo.get("companyId").getAsInt();
		}

		List<SimpleObject> locationNames  = vendorService.getLocationNamesAsDropDown(customerId+"", companyId+"");
		List<SimpleObject> countriesList = commonservice.getCompanyCountries(companyId);
		
		List<SimpleObject> comanyCurrencyList = wageScaleService.getcomanyCurrency(companyId);
		
		masterInfoMap.put("comanyCurrencyList", comanyCurrencyList);
		masterInfoMap.put("locationList", locationNames);
		masterInfoMap.put("countriesList", countriesList);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	}
	

	
	
	/*=============================== Vendor Branch Details END ====================================*/
}
