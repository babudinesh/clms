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
import com.Ntranga.CLMS.Service.WageDeductionService;
import com.Ntranga.CLMS.vo.CustomerVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.WageDeductionVo;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@RestController
@RequestMapping(value = "wageDeductionController")
public class WageDeductionController {
	
	private static Logger log = Logger.getLogger(WageDeductionController.class);
	
	
	@Autowired
	private WageDeductionService wageDeductionService;
	
	@Autowired
	private VendorService vendorService;
	
	@Autowired 
	private CommonService commonservice;
	

	@RequestMapping(value="/wageDeductionGridDetails.json", method = RequestMethod.POST)
	public ResponseEntity<List<WageDeductionVo>>  wageScaleGridDetails(@RequestBody String gridParameters) throws Exception {		
		System.out.println("gridParameters::"+gridParameters);
		String customerId = "";
	    String companyId =  "";
	    String locationId ="";
	    String status = "";
	    String wageDeductionCode = "";
	    List<WageDeductionVo> gridData = new ArrayList();
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
			if(jo.get("deductionCode") != null){
				wageDeductionCode =  jo.get("deductionCode").getAsString();
			}
			
			
		}			
		 gridData = wageDeductionService.wageDeductionGridDetails(customerId, companyId, locationId, status, wageDeductionCode)	;
	   }catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<WageDeductionVo>>(gridData,HttpStatus.OK);
	}

	
	
	
	
	
	@RequestMapping(value = "/saveOrUpdatewageDeduction.json", method = RequestMethod.POST)
	public  ResponseEntity<Integer> saveOrUpdatewageScale(@RequestBody WageDeductionVo wageScaleDetailsVo) {	
		Integer wageScaleId = 0;
		try{
		 wageScaleId = wageDeductionService.saveOrUpdateWageDeductionDetails(wageScaleDetailsVo);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Integer>(wageScaleId,HttpStatus.OK);
	}
	
	
	
	
	@RequestMapping(value = "/getTransactionDatesListForEditingWageDeduction.json", method = RequestMethod.POST)
	public  ResponseEntity<List<SimpleObject>> getTransactionDatesListForEditingTimeRules(@RequestBody String wageScaleIdJson) {
		List<SimpleObject> transactionDatesList = new ArrayList();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(wageScaleIdJson);				
		transactionDatesList  = wageDeductionService.getTransactionDatesListForEditingWageDeduction(jo.get("deductionCode").getAsString());
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>>(transactionDatesList,HttpStatus.OK);
	}
	
	
	
	
	
	
	@RequestMapping(value = "/getDetailsBywageDeductionId.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getDetailsBywageScaleId(@RequestBody String CustomerIdDeductionIdJson) {
					
		Map<String,List> masterInfoMap = new HashMap<String,List>();
		
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(CustomerIdDeductionIdJson);
		log.debug("JSON Object "+jo);
		
		String deductionId = null;
		String customerId = null;
		
		if(jo.get("deductionId") != null && jo.get("deductionId").getAsString() != null){
			deductionId = jo.get("deductionId").getAsString(); 
			log.debug("deductionId Id : "+deductionId);
		}
		
		
		List<WageDeductionVo> wageDeductionList =  new ArrayList();
		if(Integer.parseInt(deductionId) > 0){
			wageDeductionList	=wageDeductionService.getDetailsBywageDeductionId(Integer.parseInt(deductionId));	
		}
			
		if(CustomerIdDeductionIdJson != null && jo.get("customerId") != null) {	     
			customerId =  jo.get("customerId").getAsString();
		}
		List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(customerId);	
		List<SimpleObject> currency = commonservice.getCurrencyList();
		
		masterInfoMap.put("currency",currency);		
		masterInfoMap.put("customerList", customerList);	
		masterInfoMap.put("wageDeductionList",  wageDeductionList);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);				
		
	}
	
	
	
	@RequestMapping(value="/validateWageDeductionCode.json", method = RequestMethod.POST)
	  public  String validateCustomerCode(@RequestBody String parameters) {
		  	log.info("Entered in Customer Controller validateWageScaleCode() ::  "+parameters);
	        String wageIsValid =null;	 
	      try{
	        JsonParser jsonParser = new JsonParser();			
	        JsonObject jo = (JsonObject )jsonParser.parse(parameters);
	        log.debug("JSON Object:  "+jo);
	        
	        String wageDeductionCode =  jo.get("deductionCode").getAsString();
	        String customerId =  jo.get("customerId").getAsString();
	        String companyId =  jo.get("companyId").getAsString();
	        Integer deductionId =  jo.get("deductionId").getAsInt();
	        
	        log.debug("wageDeductionCode :  "+wageDeductionCode);
	        try {
	        	wageIsValid =  JSONObject.valueToString(wageDeductionService.validateWageDeductionCode(wageDeductionCode, customerId, companyId,deductionId)).toString();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("Error Occured ",e);
			}
	        log.info("Exiting from  wageIsValid() ::  "+wageIsValid);
	      }catch(Exception e){
				log.error("Error Occured ",e);
			}
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
		
	
		masterInfoMap.put("locationList", locationNames);
		masterInfoMap.put("countriesList", countriesList);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	}
	

	
	
	/*=============================== Vendor Branch Details END ====================================*/
}
