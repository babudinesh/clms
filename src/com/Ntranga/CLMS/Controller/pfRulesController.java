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
import com.Ntranga.CLMS.Service.PfRulesService;
import com.Ntranga.CLMS.Service.VendorService;
import com.Ntranga.CLMS.Service.WageDeductionService;
import com.Ntranga.CLMS.Service.WageScaleService;
import com.Ntranga.CLMS.vo.CustomerVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.WageDeductionVo;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@RestController
@RequestMapping(value = "pfRulesController")
public class pfRulesController {
	
	private static Logger log = Logger.getLogger(pfRulesController.class);
	
	
	@Autowired
	private PfRulesService  pfRulesService;
	
	@Autowired
	private VendorService vendorService;
	
	@Autowired 
	private CommonService commonservice;
	
	@Autowired
	private WageScaleService wageScaleService;
	
	
	@RequestMapping(value = "/saveOrUpdatepfRules.json", method = RequestMethod.POST)
	public  ResponseEntity<Integer> saveOrUpdatewageScale(@RequestBody WageDeductionVo wageScaleDetailsVo) {	
		Integer wageScaleId = 0;
		try{
		 wageScaleId = pfRulesService.saveOrUpdateWageDeductionDetails(wageScaleDetailsVo);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Integer>(wageScaleId,HttpStatus.OK);
	}
	
	
	
	
	@RequestMapping(value = "/getTransactionDatesListForEditingpfRulesController.json", method = RequestMethod.POST)
	public  ResponseEntity<List<SimpleObject>> getTransactionDatesListForEditingTimeRules(@RequestBody String wageScaleIdJson) {
		List<SimpleObject> transactionDatesList = new ArrayList();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(wageScaleIdJson);				
		transactionDatesList  = pfRulesService.getTransactionDatesListForEditingWageDeduction(jo.get("customerId").getAsInt(),jo.get("companyId").getAsInt());
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>>(transactionDatesList,HttpStatus.OK);
	}
	
	
	
	
	
	
	@RequestMapping(value = "/getDropDownDetailsForpfRules.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getDropDownDetailsForpfRules(@RequestBody String CustomerIdDeductionIdJson) {
					
		Map<String,List> masterInfoMap = new HashMap<String,List>();
		
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(CustomerIdDeductionIdJson);
		log.debug("JSON Object "+jo);
		
		Integer companyId = 0;
		Integer customerId = 0;		
		
		if(jo.get("customerId") != null && jo.get("customerId").getAsInt() > 0){
			customerId = jo.get("customerId").getAsInt(); 
			log.debug("customerId Id : "+customerId);
		}
		
		if(jo.get("companyId") != null && jo.get("companyId").getAsInt() > 0){
			companyId = jo.get("companyId").getAsInt(); 
			log.debug("companyId Id : "+companyId);
		}
		
	/*	if(jo.get("pfrulesId") != null && jo.get("pfrulesId").getAsString() != null){
			pfrulesId = jo.get("pfrulesId").getAsInt(); 
			log.debug("deductionId Id : "+pfrulesId);
		}*/
		
		
		
		List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(customerId+"");	
		List<SimpleObject> currency = commonservice.getCurrencyList();
		List<SimpleObject> comanyCurrencyList = wageScaleService.getcomanyCurrency(companyId);
		masterInfoMap.put("comanyCurrencyList",comanyCurrencyList);		
		masterInfoMap.put("currency",currency);		
		masterInfoMap.put("customerList", customerList);		
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);				
		
	}
	
	
	
	@RequestMapping(value = "/getDetailsForpfRulesController.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getDetailsForpfRules(@RequestBody String CustomerIdDeductionIdJson) {
					
		Map<String,List> masterInfoMap = new HashMap<String,List>();
		
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(CustomerIdDeductionIdJson);
		log.debug("JSON Object "+jo);
		
		Integer companyId = 0;
		Integer customerId = 0;
		Integer pfrulesId = 0;
		
		if(jo.get("customerId") != null && jo.get("customerId").getAsInt() > 0){
			customerId = jo.get("customerId").getAsInt(); 
			log.debug("customerId Id : "+customerId);
		}
		
		if(jo.get("companyId") != null && jo.get("companyId").getAsInt() > 0){
			companyId = jo.get("companyId").getAsInt(); 
			log.debug("companyId Id : "+companyId);
		}
		
		if(jo.get("pfrulesId") != null && jo.get("pfrulesId").getAsInt() >0){
			pfrulesId = jo.get("pfrulesId").getAsInt(); 
			log.debug("deductionId Id : "+pfrulesId);
		}
		List<WageDeductionVo> wageDeductionList =  new ArrayList();		
		wageDeductionList	=pfRulesService.getDetailsBywageDeductionId(customerId,companyId,pfrulesId);	
		
		masterInfoMap.put("wageDeductionList",  wageDeductionList);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);				
		
	}
	
	
	

	
	
	/*=============================== Vendor Branch Details END ====================================*/
}
