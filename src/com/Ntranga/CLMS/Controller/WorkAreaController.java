package com.Ntranga.CLMS.Controller;

import java.util.ArrayList;
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

import com.Ntranga.CLMS.Service.AssociatingDepartmentToLocationPlantService;
import com.Ntranga.CLMS.Service.CommonService;
import com.Ntranga.CLMS.Service.CompanyService;
import com.Ntranga.CLMS.Service.LocationService;
import com.Ntranga.CLMS.Service.PlantService;
import com.Ntranga.CLMS.Service.SectionService;
import com.Ntranga.CLMS.Service.VendorService;
import com.Ntranga.CLMS.Service.WorkAreaService;
import com.Ntranga.CLMS.vo.CustomerVo;
import com.Ntranga.CLMS.vo.DepartmentVo;
import com.Ntranga.CLMS.vo.LocationVo;
import com.Ntranga.CLMS.vo.MWorkSkillVo;
import com.Ntranga.CLMS.vo.PlantVo;
import com.Ntranga.CLMS.vo.SectionDetailsInfoVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.WorkAreaVo;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping(value = "WorkAreaController")
public class WorkAreaController {

private static final Logger log = Logger.getLogger(WorkAreaController.class);
	
	@Autowired
	private WorkAreaService workAreaService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private VendorService vendorService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private PlantService plantService;
	
	@Autowired
	private SectionService sectionService;
	
	@Autowired
	private AssociatingDepartmentToLocationPlantService associatingDepartmentToLocationPlantService;
	/*
	 * This method will be used to save or update the work area data
	*/ 
	@RequestMapping(value = "/saveWorkArea.json", method = RequestMethod.POST)
	public ResponseEntity<SimpleObject> saveWorkArea(@RequestBody WorkAreaVo paramWork) {
		log.info("Entered in Work Area Controller saveWorkArea() ::  "+paramWork);
		
		Integer workAreaId = null;
		SimpleObject object = new SimpleObject();
		
		int companyId = paramWork.getCompanyId();
		int customerId = paramWork.getCustomerId();
		int companyDetailsId = 0;
		List<SimpleObject> obj = new ArrayList();
		try{
		 obj = companyService.getCompanyTransactionDates(customerId+"", companyId+"");
		if(obj!=null && obj.size() > 0) {
			companyDetailsId= (obj.get(0).getId());
		}
		
		log.info("Date "+companyDetailsId);
		java.util.Date companyDate = (companyService.getCompanyDetailsListByCompanyId(companyDetailsId).getTransactionDate());
		java.util.Date deptDate = (paramWork.getTransactionDate());
		log.info("Diff "+companyDate.compareTo(deptDate));
		
		if(companyDate.compareTo(deptDate) <= 0){
			if(paramWork != null){
				workAreaId = workAreaService.saveWorkArea(paramWork);
				log.debug("Work Area Details ID: "+workAreaId);
			}
			
			if (workAreaId != null && workAreaId > 0) {
				object.setId(workAreaId);
				object.setName("Success");
			} else  {
				object.setId(0);
				object.setName("Fail");
			}
		}else{
			object.setId(-1);
			object.setName("Transaction date should not be less than Company Transaction Date");
		}
		log.info("Exiting from  Work Area Controller saveWorkArea() ::  "+object.toString());
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<SimpleObject>(object, HttpStatus.OK);
	}

	
	/*
	 * This method will be used to get the work area records based on given customerId, company Id, location Id, 
	 * 																					country Id, plant Id and work area name																				
	 */
	@RequestMapping(value = "/getWorkAreaListBySearch.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getWorkAreaListBySearch(@RequestBody WorkAreaVo paramWork) throws JSONException {
		log.info("Entered in  Work Area Controller getWorkAreaListBySearch()   ::   "+paramWork);
		Map<String,List> returnData = new HashMap<String,List>();
		try{
		List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(String.valueOf(paramWork.getCustomerId()));
		List<WorkAreaVo> workAreaList = workAreaService.getWorkAreaListBySearch(paramWork);
		log.debug("List of Work Areas : "+workAreaList);
		
		
		//returnData.put("deptTypeList", deptTypelist);
		returnData.put("customerList",customerList);
		returnData.put("workAreaList",workAreaList);
		
		log.info("Exiting from  Work Area Controller getWorkAreaListBySearch() ::  "+returnData);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		//return  JSONObject.valueToString(s).toString();
		return new ResponseEntity<Map<String,List>>(returnData,HttpStatus.OK);
	}
	
	/*
	 * This method will be used to get the work area record  based on given work area details Id
	 */	
	
	@RequestMapping(value = "/getWorkAreaById.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getWorkAreaById(@RequestBody String paramPlant) throws JSONException {
		log.info("Entered in  Work Area Controller getWorkAreaById()  ::   "+paramPlant);
		Map<String,List> returnList = new HashMap<String,List>();
		
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(paramPlant);
		log.debug("JSON Object:  "+jo);
		
		String workAreaDetailsId = null, customerId = null;
		if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null
						|| jo.get("workAreaDetailsId") != null && jo.get("workAreaDetailsId").getAsString() != null){
			customerId = jo.get("customerId").getAsString(); 
			workAreaDetailsId = jo.get("workAreaDetailsId").getAsString();  
			log.debug("customer Id : "+customerId+" \t Work Area Details Id : "+workAreaDetailsId);
		}

	    List<WorkAreaVo> workAreaVo= workAreaService.getWorkAreaById(Integer.valueOf(workAreaDetailsId.trim()));	
	    List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(customerId);
	    Integer workAreaId = (workAreaVo != null && workAreaVo.size() > 0)? workAreaVo.get(0).getWorkAreaId() : 0;
	    List<SimpleObject> countryList = new ArrayList();
	    List<MWorkSkillVo> skillList = workAreaService.getWorkAreaSkillsList(workAreaId);
	    List<SimpleObject> companyList = new ArrayList<SimpleObject>();
	    List<PlantVo> plantList = new ArrayList<PlantVo>(); 
	    List<SimpleObject>  statesList = new ArrayList<SimpleObject>(); 
	    List<LocationVo> locationList = new ArrayList<LocationVo>(); 
	    List<SectionDetailsInfoVo> sectionList = new ArrayList<SectionDetailsInfoVo>(); 
	    List<DepartmentVo> departmentList =  new ArrayList<DepartmentVo>();
	    if(workAreaVo != null && workAreaVo.size() > 0){
	    	LocationVo location = new LocationVo();
	    	location.setCustomerId(workAreaVo.get(0).getCustomerId());
	    	location.setCompanyId(workAreaVo.get(0).getCompanyId());
	    	location.setCountryId(workAreaVo.get(0).getCountryId());
	    	
	    	PlantVo plant = new PlantVo();
	    	plant.setCustomerId(workAreaVo.get(0).getCustomerId());
	    	plant.setCompanyId(workAreaVo.get(0).getCompanyId());
	    	plant.setLocationId(workAreaVo.get(0).getLocationId());
	    	
	    	companyList = vendorService.getComapanyNamesAsDropDown((workAreaVo.get(0).getCustomerId())+"",(workAreaVo.get(0).getCompanyId())+"");
	    	plantList =  plantService.getPlantsListBySearch(plant);
	    	departmentList = associatingDepartmentToLocationPlantService.getDeparmentNamesAsDropDown(workAreaVo.get(0).getCustomerId()+"", workAreaVo.get(0).getCompanyId()+"", workAreaVo.get(0).getLocationId()+"", workAreaVo.get(0).getPlantId()+"",null);
	    	statesList = commonService.getStatesList(workAreaVo.get(0).getCompanyId());
	    	locationList = locationService.getLocationsListBySearch(location);
	    	countryList = commonService.getCompanyCountries(workAreaVo.get(0).getCompanyId());
	    	SectionDetailsInfoVo infoVo = new SectionDetailsInfoVo();
	    	infoVo.setCustomerId(workAreaVo.get(0).getCustomerId());
	    	infoVo.setCompanyId(workAreaVo.get(0).getCompanyId());
	    	infoVo.setLocationId(workAreaVo.get(0).getLocationId());
	    	infoVo.setPlantDetailsId(workAreaVo.get(0).getPlantId());
	    	sectionList = sectionService.getSectionListBySearch(infoVo);
	    }
	    
	    returnList.put("departmentList",departmentList);
		returnList.put("workAreaVo",workAreaVo);
		returnList.put("customerList", customerList);
		returnList.put("countryList",countryList);
		returnList.put("skillList",skillList);
		returnList.put("companyList",companyList);
		returnList.put("plantList",plantList);
		returnList.put("statesList",statesList);
		returnList.put("locationList",locationList);
		returnList.put("sectionList",sectionList);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		log.info("Exiting from  Work Area Controller getWorkAreaById() ::  "+returnList);
		return new ResponseEntity<Map<String,List>>(returnList, HttpStatus.OK);
	}

	/*
	 * This method will be used to get transaction dates list for work area based on given customerId, companyId and work area Id
	 */
	@RequestMapping(value = "/getWorkAreaTransactionDatesList.json", method = RequestMethod.POST)
	public ResponseEntity<List<SimpleObject>> getTransactionDatesListForWorkArea(@RequestBody String paramPlant) throws JSONException {
		log.info("Entered in  Work Area Controller getTransactionDatesListForWorkArea() ::  "+paramPlant);
		List<SimpleObject> simpleObjects =  new ArrayList();
		JsonParser jsonParser = new JsonParser();
		try{
		JsonObject jo = (JsonObject) jsonParser.parse(paramPlant);
		log.debug("JSON Object "+jo);
		
		String customerId = null ,companyId = null, workAreaId = null;
		if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null
								&& jo.get("workAreaId") != null && jo.get("workAreaId").getAsString() != null
								&& jo.get("companyId") != null && jo.get("companyId").getAsString() != null){
			customerId = jo.get("customerId").getAsString(); 
			companyId = jo.get("companyId").getAsString();			
			workAreaId = jo.get("workAreaId").getAsString(); 
			log.debug("Customer Id:  "+customerId+"\t Company Id :   "+companyId+"\t Work Area Id : "+workAreaId);
		}
		simpleObjects = workAreaService.getTransactionListForWorkArea(Integer.valueOf(customerId), Integer.valueOf(companyId),  Integer.valueOf(workAreaId));	
		log.info("Exiting from  Work Area Controller getTransactionDatesListForWorkArea() ::  "+simpleObjects);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>>(simpleObjects, HttpStatus.OK);
	}
	
}
