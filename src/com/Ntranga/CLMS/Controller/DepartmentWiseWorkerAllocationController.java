package com.Ntranga.CLMS.Controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Ntranga.CLMS.Service.DepartmentWiseWorkerAllocationService;
import com.Ntranga.CLMS.vo.CustomerVo;
import com.Ntranga.CLMS.vo.DepartmentwiseWorkerAllocationDetailsVo;
import com.Ntranga.CLMS.vo.ShiftsDefineVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.WorkerDetailsVo;
import com.Ntranga.core.DateHelper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@SuppressWarnings({"unchecked","rawtypes","unused"})
@Transactional
@RestController
@RequestMapping(value = "departmentWiseWorkerAllocationController")

public class DepartmentWiseWorkerAllocationController {
	
	private static Logger log = Logger.getLogger(DepartmentWiseWorkerAllocationController.class);
	
	private @Value("#{db['db.schemaName']}")
	String dbSchemaName;
	
	
	@Autowired
    private HttpServletRequest request;
	
	@Autowired
    private DepartmentWiseWorkerAllocationService workerAllocationService;
	
	
	
	@RequestMapping(value="/departmentWiseWorkerAllocationGridDetails.json", method = RequestMethod.POST)
	public ResponseEntity<List<DepartmentwiseWorkerAllocationDetailsVo>>  DepartmentWiseWorkerAllocationGridDetails(@RequestBody String CustomerIdCompanyIdVendorIdLocationIdJson) throws Exception {		
		System.out.println("CustomerIdCompanyIdVendorIdJson::"+CustomerIdCompanyIdVendorIdLocationIdJson);
		String customerId = "";
	    String companyId =  "";
	    String countryId ="";
	    String departmentId = "";
	    String locationId = "";
	    String plannedOrAdhoc = "";
	    String fromDate = "";
	    String toDate = "";
	    
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject)jsonParser.parse(CustomerIdCompanyIdVendorIdLocationIdJson);   
		
		List<DepartmentwiseWorkerAllocationDetailsVo> workerAllocationDetails = new ArrayList();
		try{
		if(CustomerIdCompanyIdVendorIdLocationIdJson != null) {
	   
			if(jo.get("customerId") != null){
				 customerId =  jo.get("customerId").getAsString();
			}
			if(jo.get("companyId") != null){
				companyId =  jo.get("companyId").getAsString();
			}
			if(jo.get("countryId") != null){
				countryId =  jo.get("countryId").getAsString();
			}
			if(jo.get("departmentId") != null){
				departmentId =  jo.get("departmentId").getAsString();
			}
			if(jo.get("locationId") != null){
				locationId =  jo.get("locationId").getAsString();
			}
			if(jo.get("plannedOrAdhoc") != null){
				plannedOrAdhoc =  jo.get("plannedOrAdhoc").getAsString();
			}
			
			if(!jo.get("fromDate").isJsonNull() && jo.get("fromDate") != null && !jo.get("fromDate").getAsString().equalsIgnoreCase("Invalid date")){
				fromDate =  jo.get("fromDate").getAsString();
			}
			
			if(!jo.get("toDate").isJsonNull() && jo.get("toDate") != null && !jo.get("toDate").getAsString().equalsIgnoreCase("Invalid date")){
				toDate =  jo.get("toDate").getAsString();
			}			
		
		}			
		 workerAllocationDetails = workerAllocationService.DepartmentWiseWorkerAllocationGridDetails(customerId,companyId, countryId,departmentId,locationId,plannedOrAdhoc,fromDate,toDate);	
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<DepartmentwiseWorkerAllocationDetailsVo>>(workerAllocationDetails,HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value = "/saveOrUpdateWorkerAllocationDetails.json", method = RequestMethod.POST)
	public  ResponseEntity<SimpleObject> saveOrUpdateWorkerAllocationDetails(@RequestBody DepartmentwiseWorkerAllocationDetailsVo workerVo ) {	
		SimpleObject object = new SimpleObject();
		try{
		Integer branchId = workerAllocationService.saveOrUpdateWorkerAllocationDetails(workerVo);
		if(branchId != null && branchId > 0){
			object.setId(branchId);
			object.setName("Success");
		}else{
			object.setId(0);
 			object.setName("Failed ");
		}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<SimpleObject>(object,HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value = "/getWorkerDetailsbyId.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getWorkerDetailsbyId(@RequestBody String workerAllocationId) {					
		Map<String,List> masterInfoMap = new HashMap<String,List>();		
		List<DepartmentwiseWorkerAllocationDetailsVo> workerAllocationDetails = new ArrayList<>();		
		JsonParser jsonParser = new JsonParser();
		System.out.println("workerInfoId::"+workerAllocationId);
		JsonObject jo = (JsonObject) jsonParser.parse(workerAllocationId);
		try{			
			if(jo.get("workerAllocationId")!= null && jo.get("workerAllocationId").toString() != null && !jo.get("workerAllocationId").getAsString().isEmpty() ){
				workerAllocationDetails  = workerAllocationService.WorkerAllocationDetailsId(jo.get("workerAllocationId").getAsInt());			
			}		
			masterInfoMap.put("workerAllocationDetails",  workerAllocationDetails);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
		
	}
	
	
}
