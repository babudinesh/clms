package com.Ntranga.CLMS.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Ntranga.CLMS.Service.EmployeeService;
import com.Ntranga.CLMS.Service.SectionService;
import com.Ntranga.CLMS.Service.VendorService;
import com.Ntranga.CLMS.Service.WorkAreaService;
import com.Ntranga.CLMS.Service.WorkerJobDetailsService;
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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import common.Logger;

@SuppressWarnings({"rawtypes","unchecked"})
@RestController
@RequestMapping("workerJobDetailsController")
public class WorkerJobDetailsController {

	private static Logger log = Logger.getLogger(WorkerJobDetailsController.class);
	
	
	@Autowired
	private WorkerJobDetailsService workerJobDetailsService;
	
	@Autowired
	private VendorService vendorService;
	
	@Autowired
	private SectionService sectionService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private WorkAreaService workAreaService;
	
	@Autowired
	private WorkerService workerService;
	
	
	
	@RequestMapping(value = "/getSearchGridData.json", method = RequestMethod.POST)
	public  ResponseEntity<List> getMasterInfoForCompanyGrid(@RequestBody String jsonString) {
		List<WorkJobDetailsVo> jobDetailsVos = new ArrayList();
		JsonParser jsonParser = new JsonParser();
		try{
		JsonObject jo = (JsonObject) jsonParser.parse(jsonString);
		 jobDetailsVos =  workerJobDetailsService.getSearchGridData(jo.get("customerId").getAsInt(),jo.get("companyId").getAsInt(),jo.get("vendorId").getAsInt(),jo.get("workerCode").getAsString(),jo.get("workerName").getAsString(),jo.get("status").getAsString());
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List>(jobDetailsVos,HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value = "/getMasterData.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>>  getMasterData(@RequestBody String jsonString) {
		Map<String,List> masterInfoMap = new HashMap<String,List>();
		JsonParser jsonParser = new JsonParser();
		
		JsonObject jo = (JsonObject) jsonParser.parse(jsonString);
		Integer customerId = 0;
		String locationId = null;
		if(jo.get("customerId") != null && !jo.get("customerId").toString().equalsIgnoreCase("null")){
			customerId= jo.get("customerId").getAsInt();
		}
		
		List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(customerId+"");						
		masterInfoMap.put("customerList", customerList);
		
		try{
			if(jo.has("locationId") && jo.get("locationId") != null &&  !jo.get("locationId").isJsonNull() && jo.get("locationId").getAsJsonArray().size() > 0 ){
				JsonArray locId = jo.get("locationId").getAsJsonArray();
				locationId = locId.toString().replace("]", "").replace("[", "");
			}
			
			if(jo.get("workJobId") != null && jo.get("workJobId").getAsInt() > 0){
				List<WorkJobDetailsVo> detailsVos = workerJobDetailsService.getWorkJobDetailsById(jo.get("workJobId").getAsInt());
				
				
				List<SimpleObject> vendorNames  = workerService.getVendorNamesAsDropDown(detailsVos.get(0).getCustomerDetailsId()+"", detailsVos.get(0).getCompanyDetailsId()+"",detailsVos.get(0).getVendorDetailsId()+"","Validated",locationId);		
				List<SimpleObject> managers  = workerJobDetailsService.getReportingManagerDropDown(detailsVos.get(0).getCustomerDetailsId()+"", detailsVos.get(0).getCompanyDetailsId()+"");
				List<SimpleObject> jobCodes  = workerJobDetailsService.getJobCodesDropDown(detailsVos.get(0).getCustomerDetailsId()+"", detailsVos.get(0).getCompanyDetailsId()+"");
				jobCodes.add(new SimpleObject(0, "ALL"));
				
				SectionDetailsInfoVo paramSection = new SectionDetailsInfoVo();
				paramSection.setCustomerId(detailsVos.get(0).getCustomerDetailsId());
				paramSection.setCompanyId(detailsVos.get(0).getCompanyDetailsId());
				paramSection.setLocationId(detailsVos.get(0).getLocationId());
				paramSection.setPlantId(detailsVos.get(0).getPlantId() != null ? detailsVos.get(0).getPlantId() : 0);
				List<SectionDetailsInfoVo> sectionList = sectionService.getSectionListBySearch(paramSection);
				
				WorkAreaVo paramWork = new WorkAreaVo();
				paramWork.setCustomerId(detailsVos.get(0).getCustomerDetailsId());
				paramWork.setCompanyId(detailsVos.get(0).getCompanyDetailsId());
				paramWork.setLocationId(detailsVos.get(0).getLocationId());
				paramWork.setPlantId(detailsVos.get(0).getPlantId() != null ? detailsVos.get(0).getPlantId() : 0);
				paramWork.setSectionDetailsId(detailsVos.get(0).getSectionId() != null ? detailsVos.get(0).getSectionId() : 0);
				List<WorkAreaVo> workAreaList = workAreaService.getWorkAreaListBySearch(paramWork);
				
				//List<SimpleObject> cardTypes  = workerJobDetailsService.getCardTypesDropDown(detailsVos.get(0).getCustomerDetailsId()+"", detailsVos.get(0).getCompanyDetailsId()+"");
				List<SimpleObject> workOrders  = workerJobDetailsService.getWorkOrdersDropDown(detailsVos.get(0).getCustomerDetailsId()+"", detailsVos.get(0).getCompanyDetailsId()+"");
				List<SimpleObject> jobDetailsVos =  workerJobDetailsService.getAllWorkerNamesAsDropDown(detailsVos.get(0).getCustomerDetailsId(), detailsVos.get(0).getCompanyDetailsId(), detailsVos.get(0).getVendorDetailsId());
				List<SimpleObject> companyNames  = vendorService.getComapanyNamesAsDropDown(detailsVos.get(0).getCustomerDetailsId()+"",detailsVos.get(0).getCompanyDetailsId()+"");
				AssignShiftsVo assignShiftsVo = workerJobDetailsService.getDeptAntPlantDropDown(detailsVos.get(0).getCustomerDetailsId(),detailsVos.get(0).getCompanyDetailsId(),detailsVos.get(0).getWorkOrderId() );
				List<SimpleObject> wageRatesList = workerJobDetailsService.getWageRateDropDown(detailsVos.get(0).getCustomerDetailsId(), detailsVos.get(0).getCompanyDetailsId(), detailsVos.get(0).getVendorDetailsId(), detailsVos.get(0).getJobName(), detailsVos.get(0).getWorkSkill(), detailsVos.get(0).getJobType());
				/*detailsVos.get(0).setDepartment(assignShiftsVo.getDepartmentName());
				detailsVos.get(0).setLocationName(assignShiftsVo.getLocationName());
				detailsVos.get(0).setDepartment();*/
				detailsVos.get(0).setDepartmentName(assignShiftsVo.getDepartmentName());
				detailsVos.get(0).setLocationName(assignShiftsVo.getLocationName());
				
				masterInfoMap.put("workJobList", detailsVos);	
				masterInfoMap.put("plantList", assignShiftsVo.getPlantList());
				masterInfoMap.put("companyList", companyNames);
				masterInfoMap.put("jobDetailsVos", jobDetailsVos);
				masterInfoMap.put("workOrders", workOrders);
				masterInfoMap.put("sectionList", sectionList);
				masterInfoMap.put("workAreaList", workAreaList);
				//masterInfoMap.put("cardTypes", cardTypes);
				masterInfoMap.put("jobCodes", jobCodes);
				masterInfoMap.put("managers", managers);
				masterInfoMap.put("vendorList", vendorNames);	
				masterInfoMap.put("wageRatesList", wageRatesList);	
			}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
				
		return new  ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getVendorNamesAsDropDown.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String, List<SimpleObject>>> getVendorNamesAsDropDown(@RequestBody String customerCompanyJsonString) {
		Map<String,List<SimpleObject>> masterInfoMap = new HashMap<String,List<SimpleObject>>();
		JsonParser jsonParser = new JsonParser();
		List<SimpleObject> vendorNames = new ArrayList();		
		List<SimpleObject> managers    = new ArrayList();	
		List<SimpleObject> jobCodes    = new ArrayList();	
		List<SimpleObject> cardTypes   = new ArrayList();	
		List<SimpleObject> workOrders  = new ArrayList();	
		
		
		JsonObject jo = (JsonObject) jsonParser.parse(customerCompanyJsonString);		
		try{
			String customerId = "";
			String companyId= "";
			String vendorId= "";
			String locationId= null;
			if(jo.get("customerId")!= null && !jo.get("customerId").toString().equalsIgnoreCase("null")){
				customerId = jo.get("customerId").getAsString();
			}
			if(jo.get("companyId")!= null && !jo.get("companyId").toString().equalsIgnoreCase("null")){
				companyId = jo.get("companyId").getAsString();
			}
			if(jo.get("vendorId")!= null && !jo.get("vendorId").toString().equalsIgnoreCase("null")){
				vendorId = jo.get("vendorId").getAsString();
			}
			if(jo.has("locationId") && jo.get("locationId") != null &&  !jo.get("locationId").isJsonNull() && jo.get("locationId").getAsJsonArray().size() > 0 ){
				JsonArray locId = jo.get("locationId").getAsJsonArray();
				locationId = locId.toString().replace("]", "").replace("[", "");
			}
			/*SectionDetailsInfoVo infoVo = new SectionDetailsInfoVo();
			infoVo.setCustomerId(Integer.valueOf(customerId.trim()));
			infoVo.setCompanyId(Integer.valueOf(companyId.trim()));
			infoVo.setCustomerId(Integer.valueOf(customerId.trim()));*/
			
			 vendorNames  = workerService.getVendorNamesAsDropDown(customerId,companyId, vendorId,"Validated",locationId);		
			 managers  = workerJobDetailsService.getReportingManagerDropDown(customerId,companyId);
			 jobCodes  = workerJobDetailsService.getJobCodesDropDown(customerId,companyId);
			 jobCodes.add(new SimpleObject(0, "ALL"));
			 cardTypes  = workerJobDetailsService.getCardTypesDropDown(customerId,companyId);
			 workOrders  = workerJobDetailsService.getWorkOrdersDropDown(customerId,companyId);
			 //section = sectionService.getSectionListBySearch(infoVo);
			// department = employeeService.getDepartmentListByCompanyId(infoVo.getCustomerId(), infoVo.getCompanyId());
			 
			masterInfoMap.put("workOrders", workOrders);
			masterInfoMap.put("cardTypes", cardTypes);
			masterInfoMap.put("jobCodes", jobCodes);
			masterInfoMap.put("managers", managers);
			masterInfoMap.put("vendorList", vendorNames);	
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String, List<SimpleObject>>> (masterInfoMap,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getWageRateList.json", method = RequestMethod.POST)
	public  ResponseEntity<List<SimpleObject>> getWageRateDropDown(@RequestBody String paramInput) {	
		log.info("Entered in WorkerJobDetails Controller  from getWageRateDropDown()  :::  "+paramInput);
		
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(paramInput);

		String customerId = null, companyId = null, vendorId = null, jobType = null, workSkill = null, jobName = null;
		
		if(jo.get("customerId")!= null && !jo.get("customerId").toString().equalsIgnoreCase("null")){
			customerId = jo.get("customerId").getAsString();
		}
		if(jo.get("companyId")!= null && !jo.get("companyId").toString().equalsIgnoreCase("null")){
			companyId = jo.get("companyId").getAsString();
		}
		if(jo.get("vendorId")!= null && !jo.get("vendorId").toString().equalsIgnoreCase("null")){
			vendorId = jo.get("vendorId").getAsString();
		}
		if(jo.get("jobType")!= null && !jo.get("jobType").toString().equalsIgnoreCase("null")){
			jobType = jo.get("jobType").getAsString();
		}
		if(jo.get("workSkill")!= null && !jo.get("workSkill").toString().equalsIgnoreCase("null")){
			workSkill = jo.get("workSkill").getAsString();
		}
		if(jo.get("jobName")!= null && !jo.get("jobName").toString().equalsIgnoreCase("null")){
			jobName = jo.get("jobName").getAsString();
		}
		
		
		List<SimpleObject> wageRateList = new ArrayList<SimpleObject>();
		
		try{
			wageRateList = workerJobDetailsService.getWageRateDropDown(Integer.valueOf(customerId.trim()), Integer.valueOf(companyId.trim()), Integer.valueOf(vendorId.trim()), Integer.valueOf(jobName), workSkill.trim(), jobType.trim());
		}catch(Exception e){
			log.error("Error Occured ",e);
			log.info("Exiting from WorkerJobDetails Controller  from getWageRateDropDown()  :::  "+wageRateList);
		}
		log.info("Exiting from WorkerJobDetails Controller  from getWageRateDropDown()  :::  "+wageRateList);
		return new ResponseEntity<List<SimpleObject>> (wageRateList,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getDeptAndPlant.json", method = RequestMethod.POST)
	public  ResponseEntity<AssignShiftsVo> getDeptAndPlantDropDown(@RequestBody String customerCompanyJsonString) {	
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(customerCompanyJsonString);
		AssignShiftsVo assignShiftsVo = new AssignShiftsVo();
		try{
		 assignShiftsVo = workerJobDetailsService.getDeptAntPlantDropDown(Integer.valueOf(jo.get("customerId").getAsInt()),Integer.valueOf(jo.get("companyId").getAsInt()),Integer.valueOf(jo.get("workOrderId").getAsInt()));
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<AssignShiftsVo> (assignShiftsVo,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/PlantListByLocationId.json", method = RequestMethod.POST)
	public  ResponseEntity<List<SimpleObject>> getPlantDropDownByLocationId(@RequestBody String locationIdJsonString) {	
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(locationIdJsonString);
		List<SimpleObject> plantList = new ArrayList();
		try{
			String plantParam = null;
			if(jo.get("plantId") != null &&  !jo.get("plantId").isJsonNull() && jo.get("plantId").getAsJsonArray().size() > 0 ){
				JsonArray locId = jo.get("plantId").getAsJsonArray();
				System.out.println(locId);
				plantParam = locId.toString().replace("]", "").replace("[", "");
			}
			plantList = workerJobDetailsService.getPlantDropDownByLocationId(jo.get("locationId").getAsInt(),plantParam);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>> (plantList,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getWorkerNamesAsDropDown.json", method = RequestMethod.POST)
	public  ResponseEntity<List<SimpleObject>> getWorkerNamesAsDropDown(@RequestBody String jsonString) {
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(jsonString);
		List<SimpleObject> jobDetailsVos = new ArrayList();
		try{
		 jobDetailsVos =  workerJobDetailsService.getWorkerNamesAsDropDown(jo.get("customerId").getAsInt(),jo.get("companyId").getAsInt(),jo.get("vendorId").getAsInt());
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>>(jobDetailsVos,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getActiveAndNewWorkerNamesAsDropDown.json", method = RequestMethod.POST)
	public  ResponseEntity<List<SimpleObject>> getActiveAndNewWorkerNamesAsDropDown(@RequestBody String jsonString) {
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(jsonString);
		List<SimpleObject> jobDetailsVos = new ArrayList();
		try{
		 jobDetailsVos =  workerJobDetailsService.getAllWorkerNamesAsDropDown(jo.get("customerId").getAsInt(),jo.get("companyId").getAsInt(),jo.get("vendorId").getAsInt());
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>>(jobDetailsVos,HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value = "/getTransactionDatesOfHistory.json", method = RequestMethod.POST)
	public  ResponseEntity<List<SimpleObject>>  getTransactionDatesOfHistory(@RequestBody String jsonString) {
		List<SimpleObject> list = new ArrayList<SimpleObject>();
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(jsonString);
		try{
		list = workerJobDetailsService.getTransactionDatesOfHistory(jo.get("customerId").getAsInt(),jo.get("companyId").getAsInt(),jo.get("vendorId").getAsInt(),jo.get("workerId").getAsInt());
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/saveOrUpdateWorkJobRecord.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,Integer>> saveOrUpdateWorkJobRecord(@RequestBody WorkJobDetailsVo workJobDetailsVo) {
		Map<String,Integer> masterInfoMap = new HashMap<String,Integer>();
		try{
		Integer workJobId = workerJobDetailsService.saveOrUpdateWorkJobRecord(workJobDetailsVo);
		masterInfoMap.put("workJobId", workJobId); 
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,Integer>>(masterInfoMap,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getWorkerDetailsbyId.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getWorkerDetailsbyId(@RequestBody String workerInfoId) {					
		Map<String,List> masterInfoMap = new HashMap<String,List>();		
		List<WorkerDetailsVo> workerDetails = new ArrayList<>();	
		JsonParser jsonParser = new JsonParser();
		System.out.println("workerInfoId::"+workerInfoId);
		JsonObject jo = (JsonObject) jsonParser.parse(workerInfoId);
		try{
		if( jo.get("workerInfoId")!= null && jo.get("workerInfoId").toString() != null && !jo.get("workerInfoId").getAsString().isEmpty() ){
			workerDetails  = workerService.getWorkerDetailsbyId(jo.get("workerInfoId").getAsString());			
		}						
		
		masterInfoMap.put("workerDetails",  workerDetails);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
				
		//return new ResponseEntity<VendorDetailsVo>(vendorDetailsVo,HttpStatus.OK);
	}
	
	
	/*@RequestMapping(value = "/getLocationandDeptByWorkOrder.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List<SimpleObject>>> getLocationandDeptByWorkOrder(@RequestBody String paramInput) {	
		log.info("Entered in WorkerJobDetails Controller  :::  "+paramInput);
		
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(paramInput);
		
		String customerId= null;
		String companyId = null;
		String workOrderId = null;
		
		List<SimpleObject> departmentList = new ArrayList<SimpleObject>();
		List<SimpleObject> locationList = new ArrayList<SimpleObject>();
		
		Map<String, List<SimpleObject>> returnMap = new HashMap<String, List<SimpleObject>>();
		
		if(jo.get("customerId")!= null && !jo.get("customerId").toString().equalsIgnoreCase("null")){
			customerId = jo.get("customerId").getAsString();
		}
		if(jo.get("companyId")!= null && !jo.get("companyId").toString().equalsIgnoreCase("null")){
			companyId = jo.get("companyId").getAsString();
		}
		if(jo.get("workOrderId")!= null && !jo.get("workOrderId").toString().equalsIgnoreCase("null")){
			workOrderId = jo.get("workOrderId").getAsString();
		}
		
		try{
			locationList = workerJobDetailsService.getLocationsDropdown(Integer.valueOf(customerId.trim()), Integer.valueOf(companyId.trim()), Integer.valueOf(workOrderId.trim()));
			departmentList = workerJobDetailsService.getDepartmentsDropdown(Integer.valueOf(customerId.trim()), Integer.valueOf(companyId.trim()), Integer.valueOf(workOrderId.trim()));
			
			returnMap.put("locationList", locationList);
			returnMap.put("departmentList", departmentList);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String, List<SimpleObject>>>(returnMap,HttpStatus.OK);
	}*/
	
	
}
