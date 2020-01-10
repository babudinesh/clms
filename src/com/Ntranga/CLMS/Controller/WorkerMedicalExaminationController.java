package com.Ntranga.CLMS.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Ntranga.CLMS.Service.EmployeeService;
import com.Ntranga.CLMS.Service.SectionService;
import com.Ntranga.CLMS.Service.VendorService;
import com.Ntranga.CLMS.Service.WorkAreaService;
import com.Ntranga.CLMS.Service.WorkerJobDetailsService;
import com.Ntranga.CLMS.Service.WorkerMedicalExaminationService;
import com.Ntranga.CLMS.Service.WorkerService;
import com.Ntranga.CLMS.vo.AssignShiftsVo;
import com.Ntranga.CLMS.vo.CustomerVo;
import com.Ntranga.CLMS.vo.PlantVo;
import com.Ntranga.CLMS.vo.SectionDetailsInfoVo;
import com.Ntranga.CLMS.vo.ShiftsDefineVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.WorkAreaVo;
import com.Ntranga.CLMS.vo.WorkJobDetailsVo;
import com.Ntranga.CLMS.vo.WorkerDetailsVo;
import com.Ntranga.CLMS.vo.WorkerMedicalExaminationVo;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import common.Logger;

@SuppressWarnings({"rawtypes","unchecked"})
@RestController
@RequestMapping("workerMedicalExaminationController")
public class WorkerMedicalExaminationController {

	private static Logger log = Logger.getLogger(WorkerMedicalExaminationController.class);
	
	
	@Autowired
	private WorkerMedicalExaminationService workerMedicalExaminationService;
	
	@Autowired
	private VendorService vendorService;
	
	
	
	
	
	@RequestMapping(value = "/saveOrUpdateWorkerMedicalExaminationDetails.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,Integer>> saveOrUpdateWorkerMedicalExaminationDetails(@RequestParam("name") String name,
			@RequestParam("file") MultipartFile[] files) {
		Map<String,Integer> masterInfoMap = new HashMap<String,Integer>();
		try{
			Integer medicalExaminationId = workerMedicalExaminationService.saveOrUpdateWorkerMedicalExaminationDetails(name,files);
			masterInfoMap.put("medicalExaminationId", medicalExaminationId); 
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,Integer>>(masterInfoMap,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getWorkerMedicalExaminationDetailsByWorkerId.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getWorkerMedicalExaminationDetailsByWorkerId(@RequestBody String workerId) {					
		Map<String,List> masterInfoMap = new HashMap<String,List>();		
		List<WorkerMedicalExaminationVo> workerMedicalExaminationDetails = new ArrayList<>();	
		JsonParser jsonParser = new JsonParser();
		System.out.println("workerId=="+workerId);
		JsonObject jo = (JsonObject) jsonParser.parse(workerId);
		try{
		if( jo.get("workerId")!= null && jo.get("workerId").toString() != null && !jo.get("workerId").getAsString().isEmpty() && jo.get("workerId").getAsInt() >0){
			workerMedicalExaminationDetails  = workerMedicalExaminationService.getWorkerMedicalExaminationDetailsByWorkerId(jo.get("workerId").getAsInt());			
		}						
		
		masterInfoMap.put("workerMedicalExaminationDetails",  workerMedicalExaminationDetails);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
				
		//return new ResponseEntity<VendorDetailsVo>(vendorDetailsVo,HttpStatus.OK);
	}
	

	
	
}
