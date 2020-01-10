package com.Ntranga.CLMS.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Ntranga.CLMS.Service.VendorService;
import com.Ntranga.CLMS.Service.WorkerVerificationTypesSetupService;
import com.Ntranga.CLMS.vo.CustomerVo;
import com.Ntranga.CLMS.vo.WorkerMedicalExaminationVo;
import com.Ntranga.CLMS.vo.WorkerVerificationTypesSetupVo;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import common.Logger;

@SuppressWarnings({"rawtypes","unchecked"})
@RestController
@RequestMapping("workerVerificationTypesSetupController")
public class WorkerVerificationTypesSetupController {

	private static Logger log = Logger.getLogger(WorkerVerificationTypesSetupController.class);
	
	
	@Autowired
	private WorkerVerificationTypesSetupService workerVerificationTypeSetupService;
	
	@Autowired
	private VendorService vendorService;
	
	@RequestMapping(value = "/saveOrUpdateVerificationTypesSetups.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,Integer>> saveOrUpdateVerificationTypesSetups(@RequestBody WorkerVerificationTypesSetupVo setupVo) {
		Map<String,Integer> masterInfoMap = new HashMap<String,Integer>();
		try{
			Integer workerVerificationTypesSetupId = workerVerificationTypeSetupService.saveOrUpdateVerificationTypesSetups(setupVo);
			masterInfoMap.put("workerVerificationTypesSetupId", workerVerificationTypesSetupId); 
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,Integer>>(masterInfoMap,HttpStatus.OK);
	}
	
	

	@RequestMapping(value = "/getWorkerVerificationTypesSetupsGridData.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List<WorkerVerificationTypesSetupVo>>> getWorkerVerificationTypesSetupsGridData(@RequestBody WorkerVerificationTypesSetupVo workerVerificationTypesSetupVo) {
		Map<String,List<WorkerVerificationTypesSetupVo>> masterInfoMap = new HashMap<String,List<WorkerVerificationTypesSetupVo>>();
		try{
			List<WorkerVerificationTypesSetupVo> WorkerVerificationTypesSetupList = workerVerificationTypeSetupService.getWorkerVerificationTypesSetupsGridData(workerVerificationTypesSetupVo);
			masterInfoMap.put("WorkerVerificationTypesSetupList", WorkerVerificationTypesSetupList); 
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List<WorkerVerificationTypesSetupVo>>>(masterInfoMap,HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value = "/getWorkerVerificationTypesSetupsById.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getWorkerVerificationTypesSetupsByWorkerVerificationTypesSetupId(@RequestBody String workerVerificationTypesSetupId) {					
		Map<String,List> masterInfoMap = new HashMap<String,List>();		
		List<WorkerVerificationTypesSetupVo> workerVerificationTypesSetupVo = new ArrayList<>();	
		JsonParser jsonParser = new JsonParser();
		System.out.println("workerId=="+workerVerificationTypesSetupId);
		JsonObject jo = (JsonObject) jsonParser.parse(workerVerificationTypesSetupId);
		try{
		if( jo.get("workerVerificationTypesSetupId")!= null && jo.get("workerVerificationTypesSetupId").toString() != null && !jo.get("workerVerificationTypesSetupId").getAsString().isEmpty() && jo.get("workerVerificationTypesSetupId").getAsInt() >0){
			workerVerificationTypesSetupVo  = workerVerificationTypeSetupService.getWorkerVerificationTypesSetupsByWorkerVerificationTypesSetupId(jo.get("workerVerificationTypesSetupId").getAsInt());			
		}
		
		if( jo.get("customerId")!= null && jo.get("customerId").toString() != null && !jo.get("customerId").getAsString().isEmpty() && jo.get("customerId").getAsInt() >0){
			workerVerificationTypesSetupVo  = workerVerificationTypeSetupService.getWorkerVerificationTypesSetupsByWorkerVerificationTypesSetupId(jo.get("workerVerificationTypesSetupId").getAsInt());			
		}
		List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(jo.get("customerId").toString());
		masterInfoMap.put("customerList",  customerList);
		masterInfoMap.put("workerVerificationTypesSetupVo",  workerVerificationTypesSetupVo);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
				
		//return new ResponseEntity<VendorDetailsVo>(vendorDetailsVo,HttpStatus.OK);
	}
	

	
	
}
