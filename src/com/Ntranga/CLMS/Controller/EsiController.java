package com.Ntranga.CLMS.Controller;

import java.util.ArrayList;
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
import com.Ntranga.CLMS.Service.EsiService;
import com.Ntranga.CLMS.Service.VendorService;
import com.Ntranga.CLMS.vo.CustomerVo;
import com.Ntranga.CLMS.vo.EsiDetailsVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.VendorDetailsVo;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@RestController
@RequestMapping(value = "esiController")
@SuppressWarnings({"rawtypes","unchecked"})
public class EsiController {
	
	private static Logger log = Logger.getLogger(EsiController.class);
	@Autowired
	private VendorService vendorService;

	@Autowired
	private CommonService commonService;
	
	@Autowired
	private EsiService esiService;
	
	@RequestMapping(value = "/getTransactionHistoryDatesListForESIDetails.json", method = RequestMethod.POST)
	public  ResponseEntity<List<SimpleObject>> getTransactionHistoryDatesListForESIDetails(@RequestBody String esiCustAndCompanyJson) {
		List<SimpleObject> transactionDatesList = new ArrayList();
		try{
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(esiCustAndCompanyJson);
			if(!jo.get("customerId").isJsonNull() && jo.get("customerId").getAsInt() > 0 && !jo.get("companyId").isJsonNull() && jo.get("companyId").getAsInt() >0){
				transactionDatesList  = esiService.getTransactionHistoryDatesListForESIDetails(jo.get("customerId").getAsInt(), jo.get("companyId").getAsInt());			
			}				
		
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>>(transactionDatesList,HttpStatus.OK);
	}
	
	
	
	
	
	@RequestMapping(value = "/saveEsiDetails.json", method = RequestMethod.POST)
	public  ResponseEntity<SimpleObject> saveVendorDetails(@RequestBody EsiDetailsVo esiVo) throws Exception {
		//log.info("Entered in Vendor Controller saveVendorDetails() ::  "+vendorJsonJsonString);
		
		SimpleObject object = new SimpleObject();
		try{
		Integer esiId = null;
		if (esiVo != null)
			esiId = esiService.saveOrUpdateWorkerDetails(esiVo);
	
		if (esiId != null) {				
			object.setId(esiId);		
			object.setName("success");
		} else {
			object.setId(0);		
			object.setName("Error in saving ESI details");
		}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		log.info("Exiting from ESI Controller saveEsiDetails() ::  "+object);
		return new ResponseEntity<SimpleObject>(object, HttpStatus.OK);
	}
	
	
	
	
	
	@RequestMapping(value = "/getEsiDetailsByCustomerCompanyIds.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getEsiDetailsByCustomerCompanyIds(@RequestBody String esiIdJson) {
					
		Map<String,List> masterInfoMap = new HashMap<String,List>();	
		
		try{
		
		List<EsiDetailsVo> esiDetailsList = new ArrayList<>();		
		JsonParser jsonParser = new JsonParser();
		System.out.println("esiIdJson::"+esiIdJson);
		JsonObject jo = (JsonObject) jsonParser.parse(esiIdJson);
		if(!jo.get("customerId").isJsonNull() && jo.get("customerId").getAsInt() > 0 && !jo.get("companyId").isJsonNull() && jo.get("companyId").getAsInt() >0){
			esiDetailsList  = esiService.getEsiDetailsByCustomerCompanyIds(jo.get("customerId").getAsInt(), jo.get("companyId").getAsInt(),0);			
		}else if(!jo.get("esiId").isJsonNull() && jo.get("esiId").getAsInt() > 0){
			esiDetailsList  = esiService.getEsiDetailsByCustomerCompanyIds(0,0, jo.get("esiId").getAsInt());	
		}					
		
		masterInfoMap.put("esiDetailsList",  esiDetailsList);
		
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
		
	}
	
	
}
