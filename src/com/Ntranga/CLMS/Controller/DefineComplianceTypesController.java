package com.Ntranga.CLMS.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Ntranga.CLMS.Service.CommonService;
import com.Ntranga.CLMS.Service.DefineComplianceTypesService;
import com.Ntranga.CLMS.Service.VendorService;
import com.Ntranga.CLMS.vo.DefineComplianceTypesVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping(value = "defineComplianceTypesController")
public class DefineComplianceTypesController {

	private static final Logger log = Logger.getLogger(DefineComplianceTypesController.class);
	
	
	@Autowired
	private DefineComplianceTypesService defineComplianceTypesService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private VendorService vendorService;
	

	
	@RequestMapping(value = "/getCountryNamesAsDropDown.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getCountryNamesAsDropDown(@RequestBody String companyIdJson) {	
		Map<String,List> masterInfoMap = new HashMap<String,List>(); 
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(companyIdJson);			
		 // customerId
		List<SimpleObject> countriesList = commonService.getCompanyCountries(jo.get("companyId").getAsInt());
		masterInfoMap.put("countriesList", countriesList);	
		
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getComplianceListByLocationId.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getComplianceListByLocationId(@RequestBody String companyIdJson) {	
		Map<String,List> masterInfoMap = new HashMap<String,List>(); 
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(companyIdJson);			
		
		List<DefineComplianceTypesVo> complianceList = defineComplianceTypesService.getComplianceList(jo.get("customerId").getAsInt(),jo.get("companyId").getAsInt(),jo.get("locationId").getAsInt());
		masterInfoMap.put("complianceList", complianceList);
		if(complianceList != null && complianceList.size() > 0){
			List companyNames  = vendorService.getComapanyNamesAsDropDown(jo.get("customerId").getAsInt()+"",jo.get("companyId").getAsInt()+"");
			masterInfoMap.put("companyList", companyNames);	
		}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	}
	
	
	
	
	@RequestMapping(value = "/getTransactionDatesofHistory.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getTransactionDatesofHistory(@RequestBody String jsonString) {
		Map<String,List> masterInfoMap = new HashMap<String,List>(); 
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(jsonString);			
		
		List<SimpleObject> list = defineComplianceTypesService.getTransactionDatesofHistory(jo.get("customerId").getAsInt(), jo.get("companyId").getAsInt());
		masterInfoMap.put("transactionDatesList", list);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/saveCompilance.json", method = RequestMethod.POST)
	public  ResponseEntity<SimpleObject> saveCompilance(@RequestBody DefineComplianceTypesVo defineComplianceTypesVo) {
		SimpleObject object = new SimpleObject();
		Integer id = defineComplianceTypesService.saveCompliance(defineComplianceTypesVo);
		System.out.println(id);
		if(id != null && id > 0 ){
			object.setId(id);
			object.setName("Success");
		}else{
			object.setId(0);
			object.setName("Failed");
		}
		return new ResponseEntity<SimpleObject>(object,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getComplianceRecordById.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getComplianceRecordById(@RequestBody String complianceId) {
		Map<String,List> masterInfoMap = new HashMap<String,List>(); 
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(complianceId);			
		 		
		List<DefineComplianceTypesVo> complianceList = defineComplianceTypesService.getComplianceRecordById(jo.get("defineComplianceTypeId").getAsInt());
		masterInfoMap.put("complianceList", complianceList);
		if(complianceList != null && complianceList.size() > 0){
			List companyNames  = vendorService.getComapanyNamesAsDropDown(complianceList.get(0).getCustomerDetailsId()+"",complianceList.get(0).getCompanyDetailsId()+"");
			masterInfoMap.put("companyList", companyNames);	
			List<SimpleObject> countriesList = commonService.getCompanyCountries(complianceList.get(0).getCompanyDetailsId());
			masterInfoMap.put("countriesList", countriesList);	
		}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	}
	
	
}
