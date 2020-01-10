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

import com.Ntranga.CLMS.Service.ScreenActionsService;
import com.Ntranga.CLMS.vo.ScreenActionsVo;

@RestController
@RequestMapping(value = "screenActionsController")
public class ScreenActionsController {
	
	private static Logger log = Logger.getLogger(ScreenActionsController.class);
	
	@Autowired
	private ScreenActionsService screenActionsService;
	
	@RequestMapping(value = "/getGroupNames.json", method = RequestMethod.POST)
	public  ResponseEntity<List<String>> getGroupNames(@RequestBody String name) {
		List<String> returnList = new ArrayList<String>();		
		try{
			returnList = screenActionsService.getGroupNames();
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<String>>(returnList,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getScreenActionsSearchList.json", method = RequestMethod.POST)
	public  ResponseEntity<List<ScreenActionsVo>> getScreenActionsSearchList(@RequestBody ScreenActionsVo actionVo) {
		List<ScreenActionsVo> returnList = new ArrayList<ScreenActionsVo>();		
		try{
			returnList = screenActionsService.getScreenActionsSearchList(actionVo);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<ScreenActionsVo>>(returnList,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getScreenMasterData.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String, List>> getScreenMasterData(@RequestBody ScreenActionsVo actionVo) {
		Map<String, List> returnList = new HashMap<String, List>();	
		try{
			List groupList = screenActionsService.getGroupNames();
			List actionList = screenActionsService.getScreenActionsList();
			returnList.put("groupNames", groupList);
			returnList.put("actionsList", actionList);
			
			if(actionVo != null && actionVo.getScreenActionId() != null && actionVo.getScreenActionId() > 0){
				ScreenActionsVo actionsVo = screenActionsService.getScreenActionsByScreenId(actionVo.getScreenActionId() );
				List sreenActionsList = new ArrayList(); 
				sreenActionsList.add(actionsVo);
				returnList.put("sreenActions", sreenActionsList);
			}
			
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String, List>>(returnList,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/saveOrUpdateScreenActionsDetails.json", method = RequestMethod.POST)
	public  ResponseEntity<Integer> saveOrUpdateScreenActionsDetails(@RequestBody ScreenActionsVo actionVo) {				
		Integer id = screenActionsService.saveOrUpdateScreenActionsDetails(actionVo);
		return new ResponseEntity<Integer>(id,HttpStatus.OK);
	}
	
	/*@RequestMapping(value = "/getVendorNamesAsDropDown.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getVendorNamesAsDropDown(@RequestBody String JsonString) {
		Map<String,List> masterInfoMap = new HashMap<String,List>();
		try{
			
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	}*/
	
}

	/*
	
	@RequestMapping(value = "/getVendorNamesAsDropDown.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getVendorNamesAsDropDown(@RequestBody String JsonString) {
		Map<String,List> masterInfoMap = new HashMap<String,List>();		
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(JsonString);		
		
		String customerId = "";
		String companyId= "";
		String vendorId= "";
		if(jo.get("customerId")!= null && !jo.get("customerId").toString().equalsIgnoreCase("null")){
			customerId = jo.get("customerId").getAsString();
		}
		if(jo.get("companyId")!= null && !jo.get("companyId").toString().equalsIgnoreCase("null")){
			companyId = jo.get("companyId").getAsString();
		}
		if(jo.get("vendorId")!= null && !jo.get("vendorId").toString().equalsIgnoreCase("null")){
			vendorId = jo.get("vendorId").getAsString();
		}
		
		List<SimpleObject> vendorNames  = workerService.getVendorNamesAsDropDown(customerId, companyId,vendorId,"Validated");
		masterInfoMap.put("vendorList", vendorNames);
		List<SimpleObject> deptList = departmentService.getDepartmentListDropDown(Integer.valueOf(customerId), Integer.valueOf(companyId), "Y");
		deptList.add(0,new SimpleObject(0, "All"));
		masterInfoMap.put("deptList", deptList);
		LocationVo LocationVo = new LocationVo();
		LocationVo.setCustomerId(Integer.valueOf(customerId));
		LocationVo.setCompanyId( Integer.valueOf(companyId));
		List<LocationVo> locationList = locationService.getLocationsListBySearch(LocationVo);
		locationList.add(0,new LocationVo(0, "All"));
		masterInfoMap.put("locationList", locationList);
		
		List<SimpleObject> workAreaList =workAreaService.getWorkAreaListForDropDown(Integer.valueOf(customerId), Integer.valueOf(companyId), "Y");
		workAreaList.add(0,new SimpleObject(0, "All"));
		masterInfoMap.put("workAreaList", workAreaList);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getWorkerDetailsToGridView.json", method = RequestMethod.POST)
	public  ResponseEntity<List<AssignShiftsVo>> getWorkerDetailsToGridView(@RequestBody String JsonString) {
		List<AssignShiftsVo> assignShiftsVos = assignShiftservice.getWorkerDetailsToGridView(JsonString);
		return new ResponseEntity<List<AssignShiftsVo>>(assignShiftsVos,HttpStatus.OK);
	}
	
		
	@RequestMapping(value = "/getDeptLocPlantDtls.json", method = RequestMethod.POST)
	public  ResponseEntity<List<AssignShiftsVo>> getDeptLocPlantDtls(@RequestBody String JsonString) {				
		List<AssignShiftsVo> assignShiftsVos = assignShiftservice.getDeptLocPlantDtls(JsonString);
		return new ResponseEntity<List<AssignShiftsVo>>(assignShiftsVos,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/saveShiftPatternDetails.json", method = RequestMethod.POST)
	public  ResponseEntity<Integer> saveShiftPatternDetails(@RequestBody AssignShiftPatternDetailsVo assignShiftPatternDetailsVo) {				
		Integer id = assignShiftservice.saveShiftPatternDetails(assignShiftPatternDetailsVo);
		return new ResponseEntity<Integer>(id,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/previewDetails.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List<Object>>> previewDetails(@RequestBody AssignShiftPatternDetailsVo assignShiftPatternDetailsVo) {				
		Map<String,List<Object>> list = assignShiftservice.previewDetails(assignShiftPatternDetailsVo);
		return new ResponseEntity<Map<String,List<Object>>>(list,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/AssignShiftPatternUpdateMasterData.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getAssignShiftPatternUpdateMasterData(@RequestBody String jsonWorkerId) {	
		Map<String,List> masterInfoMap = new HashMap<String,List>();	
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(jsonWorkerId);
		List<AssignShiftPatternUpdateVo> vo = assignShiftservice.getAssignShiftPatternUpdateMasterData(jo.get("workerId").getAsInt(),jo.get("year").getAsInt(),jo.get("month").getAsInt());
		masterInfoMap.put("workerShiftResults", vo);
		if(vo.size() > 0){
			List<SimpleObject> deptList = departmentService.getDepartmentListDropDown(vo.get(0).getCustomerId(), vo.get(0).getCompanyId(), "Y");
			deptList.add(0, new SimpleObject(0, "All"));
			masterInfoMap.put("deptList", deptList);
			List<ShiftsDefineVo> shiftsDefineVos = shiftsDefineService.getShiftDefineGridData(vo.get(0).getCustomerId(), vo.get(0).getCompanyId(), "Y","","");
			masterInfoMap.put("shiftCodes", shiftsDefineVos);
		}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value = "/saveAssignShiftPatternRecord.json", method = RequestMethod.POST)
	public  ResponseEntity<Integer> saveAssignShiftPatternRecord(@RequestBody AssignShiftPatternUpdateVo assignShiftPatternDetailsVo) {				
		Integer id = assignShiftservice.saveAssignShiftPatternRecord(assignShiftPatternDetailsVo);
		return new ResponseEntity<Integer>(id,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getPatternCodesbyPlantId.json", method = RequestMethod.POST)
	public  ResponseEntity<List<SimpleObject>> getPatternCodesbyPlantId(@RequestBody PlantVo plantVo) {				
		List<SimpleObject> list = assignShiftservice.getPatternCodesbyPlantId(plantVo);
		return new ResponseEntity<List<SimpleObject>>(list,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getSectionsListByPlantId.json", method = RequestMethod.POST)
	public  ResponseEntity<List<SectionDetailsInfoVo>> getSectionsListByPlantId(@RequestBody AssignShiftPatternDetailsVo plantVo) {
		SectionDetailsInfoVo infoVo = new SectionDetailsInfoVo();
		List<SectionDetailsInfoVo> sectionList = new ArrayList();
		try{
		infoVo.setCustomerId(plantVo.getCustomerId());
		infoVo.setCompanyId(plantVo.getCompanyId());
		infoVo.setLocationId(plantVo.getLocationId());
		infoVo.setPlantDetailsId(plantVo.getPlantId());
		infoVo.setStatus("Y");
		 sectionList = sectionService.getSectionListBySearch(infoVo);
		sectionList.add(0, new SectionDetailsInfoVo(0, "All"));
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SectionDetailsInfoVo>>(sectionList,HttpStatus.OK);
	}
		
}
*/