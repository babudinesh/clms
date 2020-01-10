package com.Ntranga.CLMS.Controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.Ntranga.CLMS.Service.AssociatingDepartmentToLocationPlantService;
import com.Ntranga.CLMS.Service.JobAllocationByVendorService;
import com.Ntranga.CLMS.Service.ManpowerRequisitionService;
import com.Ntranga.CLMS.Service.ShiftsDefineService;
import com.Ntranga.CLMS.Service.WorkerJobDetailsService;
import com.Ntranga.CLMS.Service.WorkerService;
import com.Ntranga.CLMS.loginService.LoginService;
import com.Ntranga.CLMS.vo.DepartmentVo;
import com.Ntranga.CLMS.vo.JobAllocationByVendorVo;
import com.Ntranga.CLMS.vo.ManpowerRequisitionVo;
import com.Ntranga.CLMS.vo.SearchCriteriaVo;
import com.Ntranga.CLMS.vo.ShiftsDefineVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.UsersVo;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@SuppressWarnings({"unchecked","rawtypes","unused"})
@Transactional
@RestController
@RequestMapping(value = "jobAllocationByVendorController")

public class JobAllocationByVendorController {
	
	private static Logger log = Logger.getLogger(JobAllocationByVendorController.class);
	
	private @Value("#{db['db.schemaName']}")
	String dbSchemaName;
	
	@Autowired
	private WorkerJobDetailsService workerJobDetailsService;
	
	@Autowired
	private WorkerService workerService;
	
	
	@Autowired
	private JobAllocationByVendorService jobAllocationByVendorService;

	@Autowired
	private ShiftsDefineService shiftsDefineService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private ManpowerRequisitionService manpowerRequisitionService;
	
	@Autowired
	private AssociatingDepartmentToLocationPlantService associatingDepartmentToLocationPlantService;
	
	
	
	@RequestMapping(value = "/getWorkerDropDownsListId.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getWorkerId(@RequestBody JobAllocationByVendorVo workerVo ) {	
		Map<String,List> masterInfoMap = new HashMap<String,List>();
		SimpleObject s = new SimpleObject();		
		try{			
			List<SimpleObject> workerDropDownList = 	workerJobDetailsService.getWorkerNamesAsDropDown(workerVo.getCustomerId(), workerVo.getCompanyId(), workerVo.getVendorId());
			masterInfoMap.put("workerDropDownList", workerDropDownList);	
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/workerGridDetails.json", method = RequestMethod.POST)
	public ResponseEntity<List<JobAllocationByVendorVo>>  workerGridDetails(@RequestBody SearchCriteriaVo searchVo) throws Exception {
		
		List<JobAllocationByVendorVo> workerDetailsList = new ArrayList();
		String schemaName = null;
		try{
			if(searchVo.getUserId() != null && searchVo.getUserId() > 0 ){
				schemaName = loginService.getSchemaNameByUserId(searchVo.getUserId()+"");
				searchVo.setSchemaName(schemaName);
			}
			System.out.println("In workerGridDetails SchemaName :: "+searchVo.getSchemaName()+" for user "+searchVo.getUserId());
			//workerDetailsList = jobAllocationByVendorService.workerGridDetails(workerVo.getCustomerId()+"",workerVo.getCompanyId()+"", workerVo.getVendorId()+"",workerVo.getIsActive(),workerVo.getWorkerCode(),workerVo.getFirstName(),workerVo.getWorkerId()+"");
			workerDetailsList = jobAllocationByVendorService.searchJobAllocation(searchVo);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<JobAllocationByVendorVo>>(workerDetailsList,HttpStatus.OK);
	}
	
	@RequestMapping(value="/getDepartmentsAndLocationesList.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>>  getDepartmentsList(@RequestBody SearchCriteriaVo workerVo) throws Exception {
		Map<String,List> masterInfoMap = new HashMap<String,List>();
		List<SimpleObject> departmentList = new ArrayList();
		List<SimpleObject> locationsList = new ArrayList();
		List<ShiftsDefineVo> shiftsDefineVos = new ArrayList<ShiftsDefineVo>();
		List<SimpleObject> jobCodes  = new ArrayList();	
		List<SimpleObject> vendorsList  = new ArrayList<>();

		ManpowerRequisitionVo manpowerVo = new ManpowerRequisitionVo();
		manpowerVo.setCustomerId(workerVo.getCustomerId());
		manpowerVo.setCompanyId(workerVo.getCompanyId());
		String locationArrayId = null;
		try{
			if(workerVo != null && workerVo.getLocationArrayId() != null && workerVo.getLocationArrayId().length > 0){
				locationArrayId = Arrays.toString(workerVo.getLocationArrayId()).replace("[", "").replace("]", "");
			}
			departmentList = jobAllocationByVendorService.getDepartmentsList(workerVo.getCustomerId(),workerVo.getCompanyId());	
			locationsList = jobAllocationByVendorService.getlocationsList(workerVo.getCustomerId()+"",workerVo.getCompanyId()+"",locationArrayId);

			ShiftsDefineVo shift = new ShiftsDefineVo();
			shift.setCustomerDetailsId(workerVo.getCustomerId());
			shift.setCompanyDetailsId(workerVo.getCompanyId());
			
			shiftsDefineVos = workerService.getAvailableShifts(shift);
			//jobCodes  = workerJobDetailsService.getJobCodesDropDown(workerVo.getCustomerId()+"",workerVo.getCompanyId()+"");
			jobCodes = manpowerRequisitionService.getJobCodeListBySkillType(manpowerVo);
			 
			masterInfoMap.put("jobCodes", jobCodes);
			masterInfoMap.put("shiftCodeList", shiftsDefineVos);
			masterInfoMap.put("departmentList", departmentList);
			masterInfoMap.put("locationsList", locationsList);
		}catch(Exception e){
			e.printStackTrace();
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/getJobCodeListBySkillType.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>>  getJobCodeListBySkillType(@RequestBody ManpowerRequisitionVo manpowerVo) throws Exception {
		Map<String,List> masterInfoMap = new HashMap<String,List>();
	
		List<SimpleObject> jobCodes  = new ArrayList();	
		
		try{		
			jobCodes = manpowerRequisitionService.getJobCodeListBySkillType(manpowerVo);			 
			masterInfoMap.put("jobCodes", jobCodes);			
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value="/getPlantsList.json", method = RequestMethod.POST)
	public ResponseEntity<List<SimpleObject>>  getAllPlantsListByLocationId(@RequestBody UsersVo usrVo) throws Exception {
		
		List<SimpleObject> plantsList = new ArrayList();
		//JsonParser jsonParser = new JsonParser();
		//JsonObject jo = (JsonObject) jsonParser.parse(JsonString);	
		String locationId = null;
	//	System.out.println(JsonString+"::::JsonString");
		
		try{
		
				System.out.println(Arrays.toString(usrVo.getLocationArrayId())+"::::JsonString as string"+usrVo.getLocationId());
				//locationId = jo.get("locationId").getAsString();
				if(usrVo.getLocationArrayId() != null && usrVo.getLocationArrayId().length > 0){
					plantsList = jobAllocationByVendorService.getAllPlantsListByLocationId(Arrays.toString(usrVo.getLocationArrayId()).replace("]", "").replace("[", ""));
				}else if(usrVo.getLocationId() != null){
					plantsList = jobAllocationByVendorService.getAllPlantsListByLocationId(usrVo.getLocationId());
				}
					
				
					
		}catch(Exception e){
			e.printStackTrace();
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>>(plantsList,HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/getDeptAndSectionsByLocationAndPlant.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>>  getAllSectionesByLocationAndPlant(@RequestBody String JsonString) throws Exception {
		
		Map<String,List> returnMap = new HashMap<String,List>();
		List<SimpleObject> sectionsList = new ArrayList();
		List<DepartmentVo> departmentsList = new ArrayList<>();
		
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(JsonString);	
		
		String locationId = null;
		String plantId =null;
		String customerId = null;
		String companyId =null;
		try{
			if(jo.get("customerId")!= null && !jo.get("customerId").isJsonNull() && !jo.get("customerId").toString().equalsIgnoreCase("null")){
				customerId = jo.get("customerId").getAsString();					
			}		
			if(jo.get("companyId")!= null && !jo.get("companyId").isJsonNull() && !jo.get("companyId").toString().equalsIgnoreCase("null")){
				companyId = jo.get("companyId").getAsString();					
			}	
			if(jo.get("locationId")!= null && !jo.get("locationId").isJsonNull() && !jo.get("locationId").toString().equalsIgnoreCase("null")){
				locationId = jo.get("locationId").getAsString();					
			}		
			if(jo.get("plantId")!= null && !jo.get("plantId").isJsonNull() && !jo.get("plantId").toString().equalsIgnoreCase("null")){
				plantId = jo.get("plantId").getAsString();					
			}			
			sectionsList = jobAllocationByVendorService.getAllSectionesByLocationAndPlant(locationId,plantId);	
			departmentsList = associatingDepartmentToLocationPlantService.getDeparmentNamesAsDropDown(customerId,companyId,locationId,plantId,null);
			
			returnMap.put("sectionsList", sectionsList);
			returnMap.put("departmentsList", departmentsList);		
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(returnMap,HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/getAllWorkAreasBySectionesAndLocationAndPlantAndDept.json", method = RequestMethod.POST)
	public ResponseEntity<List<SimpleObject>>  getAllWorkAreasBySectionesAndLocationAndPlant(@RequestBody UsersVo usersVo) throws Exception {
		
		List<SimpleObject> workAreasList = new ArrayList();
		JsonParser jsonParser = new JsonParser();
		/*JsonObject jo = (JsonObject) jsonParser.parse(JsonString);	
		String locationId = null;
		String plantId =null;
		String sectionId = null;
		String departmentId = null;
		String customerId = null;
		String companyId =null;*/
		
		
		try{
			/*if(jo.get("customerId")!= null && !jo.get("customerId").isJsonNull() && !jo.get("customerId").toString().equalsIgnoreCase("null")){
				customerId = jo.get("customerId").getAsString();					
			}		
			if(jo.get("companyId")!= null && !jo.get("companyId").isJsonNull() && !jo.get("companyId").toString().equalsIgnoreCase("null")){
				companyId = jo.get("companyId").getAsString();					
			}
			if(jo.get("locationId")!= null && !jo.get("locationId").isJsonNull() && !jo.get("locationId").toString().equalsIgnoreCase("null")){
				locationId = jo.get("locationId").getAsString();					
			}		
			if(jo.get("plantId")!= null && !jo.get("plantId").isJsonNull() && !jo.get("plantId").toString().equalsIgnoreCase("null")){
				plantId = jo.get("plantId").getAsString();					
			}	
			if(jo.get("sectionId")!= null && !jo.get("sectionId").isJsonNull() && !jo.get("sectionId").toString().equalsIgnoreCase("null")){
				sectionId = jo.get("sectionId").getAsString();					
			}
			if(jo.get("departmentId")!= null && !jo.get("departmentId").isJsonNull() && !jo.get("departmentId").toString().equalsIgnoreCase("null")){
				departmentId = jo.get("departmentId").getAsString();					
			}*/
			if(usersVo.getLocationArrayId() != null && usersVo.getLocationArrayId().length > 0 && usersVo.getPlantArrayId() != null && usersVo.getPlantArrayId().length > 0 && usersVo.getDepartmentArrayId().length > 0 && usersVo.getSectionArrayId().length > 0){				
				workAreasList = jobAllocationByVendorService.getAllWorkAreasBySectionesAndLocationAndPlant(usersVo.getCustomerId()+"", usersVo.getCompanyId()+"", Arrays.toString(usersVo.getLocationArrayId()).replace("]", "").replace("[", ""), Arrays.toString(usersVo.getPlantArrayId()).replace("]", "").replace("[", ""),Arrays.toString(usersVo.getSectionArrayId()).replace("]", "").replace("[", ""), Arrays.toString(usersVo.getDepartmentArrayId()).replace("]", "").replace("[", ""));	
				
			}else if(usersVo.getLocationId() != null &&  usersVo.getPlantId()!= null && usersVo.getDepartmentId() != null && usersVo.getSectionId() != null){				
				workAreasList = jobAllocationByVendorService.getAllWorkAreasBySectionesAndLocationAndPlant(usersVo.getCustomerId()+"", usersVo.getCompanyId()+"", usersVo.getLocationId(), usersVo.getPlantId(),usersVo.getSectionId(), usersVo.getDepartmentId());	
			}
			
					
					
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>>(workAreasList,HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/saveJobAllocationDetails.json", method = RequestMethod.POST)
	public ResponseEntity<SimpleObject> saveJobAllocationDetails(@RequestBody JobAllocationByVendorVo JobAllocationVo) throws Exception {
		boolean flag = false;
		SimpleObject so = new SimpleObject();
		String schemaName = loginService.getSchemaNameByUserId(JobAllocationVo.getUserId());
		try{
			if(schemaName == null || schemaName == ""){
				JobAllocationVo.setSchemaName(dbSchemaName);
			}else{
				JobAllocationVo.setSchemaName(schemaName);
			}
		
			 so =  jobAllocationByVendorService.saveJobAllocationDetails(JobAllocationVo,true);
		}catch(Exception e){
			so.setId(-1);	
			so.setDesc("Error");
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<SimpleObject>(so,HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/ApproveOrRejectJobAllocation.json", method = RequestMethod.POST)
	public ResponseEntity<SimpleObject> ApproveOrRejectJobAllocation(@RequestBody JobAllocationByVendorVo JobAllocationVo) throws Exception {
		boolean flag = false;
		SimpleObject so = new SimpleObject();
		String schemaName = loginService.getSchemaNameByUserId(JobAllocationVo.getUserId());
		try{
			if(schemaName == null || schemaName == ""){
				JobAllocationVo.setSchemaName(dbSchemaName);
			}else{
				JobAllocationVo.setSchemaName(schemaName);
			}
		
			 so =  jobAllocationByVendorService.saveJobAllocationDetails(JobAllocationVo,false);
		}catch(Exception e){
			so.setId(-1);	
			so.setDesc("Error");
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<SimpleObject>(so,HttpStatus.OK);
	}
	
	@RequestMapping(value="/getWorkersCountFromManpower.json", method = RequestMethod.POST)
	public ResponseEntity<List<SimpleObject>> getWorkersCountFromManpower(@RequestBody JobAllocationByVendorVo JobAllocationVo) throws Exception {
		boolean flag = false;
		
		String schemaName = loginService.getSchemaNameByUserId(JobAllocationVo.getUserId());
		Integer workersCount = 0;
		List<SimpleObject> list = new ArrayList();
		try{
			if(schemaName == null || schemaName == ""){
				JobAllocationVo.setSchemaName(dbSchemaName);
			}else{
				JobAllocationVo.setSchemaName(schemaName);
			}
		
			list = jobAllocationByVendorService.getWorkersCountFromManpower(JobAllocationVo);
		}catch(Exception e){
			flag = false;
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>> (list,HttpStatus.OK);
	}
	
	
}
