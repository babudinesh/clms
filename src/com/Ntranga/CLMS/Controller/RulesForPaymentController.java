package com.Ntranga.CLMS.Controller;

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
import com.Ntranga.CLMS.Service.RulesForPaymentService;
import com.Ntranga.CLMS.vo.RulesForPaymentReleaseVo;
import com.Ntranga.CLMS.vo.RulesForPaymentVerificationVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping(value="rulesForPaymentController")
public class RulesForPaymentController {

	private static Logger log = Logger.getLogger(RulesForPaymentController.class);
	
	@Autowired
	private CommonService commonService;
	
	/*@Autowired
	private DefineComplianceTypesService defineService;*/
	
	@Autowired
	private RulesForPaymentService rulesForPaymentService;

	
	/*
	 * This method will be used to get the Rules for payment  details based on given customerId and companyId or rulesForPaymentId and also to get the compliance types list and 
	 */	
	@RequestMapping(value = "/getCompanyChangeData.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getCompanyChangeData(@RequestBody String paramCompliance) throws JSONException {
		log.info("Entered in RulesForPaymentController getCompanyChangeData()  ::   "+paramCompliance);
		Map<String,List> returnList = new HashMap<String,List>();
		
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(paramCompliance);
		log.debug("JSON Object:  "+jo);
		
		String companyId = null, customerId = null, rulesForPaymentId=null;
		if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null
						|| jo.get("companyId") != null && jo.get("companyId").getAsString() != null ){
			customerId = jo.get("customerId").getAsString(); 
			companyId = jo.get("companyId").getAsString(); 
			log.debug("Customer Id : "+customerId+" \t Company Id : "+companyId);
		}
		
		if(jo.get("rulesForPaymentId") != null && jo.get("rulesForPaymentId").getAsString() != null){
			rulesForPaymentId = jo.get("rulesForPaymentId").getAsString(); 
			log.debug(" Rules For Payment Id : "+rulesForPaymentId);
		}
	   
		List<RulesForPaymentReleaseVo> rulesList = rulesForPaymentService.getRulesForPaymentById(Integer.valueOf(customerId.trim()), Integer.valueOf(companyId.trim()), Integer.valueOf(rulesForPaymentId.trim()));
	    List<SimpleObject> 	countryList = commonService.getCompanyCountries(Integer.valueOf(companyId.trim()));
	    List<RulesForPaymentVerificationVo> complianceTypesList = rulesForPaymentService.getCompliances(Integer.valueOf(customerId.trim()), Integer.valueOf(companyId.trim()));
	    
	    returnList.put("ruleVo",rulesList);
		returnList.put("countryList",countryList);
		returnList.put("complianceTypesList",complianceTypesList);
		
		log.info("Exiting from RulesForPaymentController getCompanyChangeData() ::  "+returnList);
		return new ResponseEntity<Map<String,List>>(returnList, HttpStatus.OK);
	}
	
	/*
	 * This method will be used to save or update the rules for payment
	 */
	@RequestMapping(value = "/saveRulesForPayment.json", method = RequestMethod.POST)
	public ResponseEntity<SimpleObject> saveRulesForPayment(@RequestBody RulesForPaymentReleaseVo paramRules) {
		log.info("Entered in RulesForPaymentController saveRulesForPayment() ::  "+paramRules);
		
		SimpleObject object = new SimpleObject();
		Integer rulesForPaymentId = null;
		try{
		
		if (paramRules != null){
			rulesForPaymentId = rulesForPaymentService.saveRulesForPayment(paramRules);
			log.debug("Department ID: "+rulesForPaymentId);
		}
	
		if (rulesForPaymentId != null) {
			object.setId(rulesForPaymentId);
			object.setName("Success");
		} else {
			object.setId(0);
			object.setName("Fail");
		}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		log.info("Exiting from RulesForPaymentController saveRulesForPayment() ::  "+object);
		return new ResponseEntity<SimpleObject>(object, HttpStatus.OK);
	}
	
	/*
	 * This method will be used to get transaction dates list for Rules for payment based on given customerId, companyId and unique Id
	 */
	@RequestMapping(value = "/getTransactionListForRulesForPayment.json", method = RequestMethod.POST)
	public ResponseEntity<List<SimpleObject>> getTransactionListForVendorCompliance(@RequestBody String paramCompliance) throws JSONException {
		log.info("Entered in RulesForPaymentController getTransactionListForRulesForPayment() ::  "+paramCompliance);

		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(paramCompliance);
		log.debug("JSON Object "+jo);
		
		String customerId = null ,companyId = null, uniqueId = null;
		if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null
								&& jo.get("uniqueId") != null && jo.get("uniqueId").getAsString() != null
								&& jo.get("companyId") != null && jo.get("companyId").getAsString() != null){
			customerId = jo.get("customerId").getAsString(); 
			companyId = jo.get("companyId").getAsString();			
			uniqueId = jo.get("uniqueId").getAsString(); 
			log.debug("Customer Id:  "+customerId+"\t Company Id :   "+companyId+"\t Unique Id : "+uniqueId);
		}
		List<SimpleObject> simpleObjects = rulesForPaymentService.getTransactionListForRulesForPayment(Integer.valueOf(customerId), Integer.valueOf(companyId),  Integer.valueOf(uniqueId));	
		log.info("Exiting from RulesForPaymentController getTransactionListForRulesForPayment() ::  "+simpleObjects);
		return new ResponseEntity<List<SimpleObject>>(simpleObjects, HttpStatus.OK);
	}
}
