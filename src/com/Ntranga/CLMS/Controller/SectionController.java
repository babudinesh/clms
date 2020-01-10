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

import com.Ntranga.CLMS.Service.CommonService;
import com.Ntranga.CLMS.Service.CompanyService;
import com.Ntranga.CLMS.Service.LocationService;
import com.Ntranga.CLMS.Service.PlantService;
import com.Ntranga.CLMS.Service.SectionService;
import com.Ntranga.CLMS.Service.VendorService;
import com.Ntranga.CLMS.vo.CustomerVo;
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
@RequestMapping(value = "sectionController")
public class SectionController {

private static final Logger log = Logger.getLogger(SectionController.class);
	
	@Autowired
	private SectionService sectionService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private VendorService vendorService;
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private PlantService plantService;
	
	/*
	 * This method will be used to save or update the work area data
	*/ 
	@RequestMapping(value = "/saveSection.json", method = RequestMethod.POST)
	public ResponseEntity<Integer> saveSection(@RequestBody SectionDetailsInfoVo paramWork) {
		log.info("Entered in Work Area Controller saveSection() ::  "+paramWork);
		Integer workAreaId = sectionService.saveSection(paramWork);
		log.info("Exiting from  Work Area Controller saveWorkArea() ::  "+paramWork);
		return new ResponseEntity<Integer>(workAreaId, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/getSectionListBySearch.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getSectionListBySearch(@RequestBody SectionDetailsInfoVo infoVo) throws JSONException {
		log.info("Entered in  Work Area Controller getWorkAreaListBySearch()   ::   "+infoVo);				
		List<SectionDetailsInfoVo> sectionList = sectionService.getSectionListBySearch(infoVo);
		log.debug("List of sectionList  : "+sectionList);
		
		Map<String,List> returnData = new HashMap<String,List>();			
		returnData.put("sectionList",sectionList);

		return new ResponseEntity<Map<String,List>>(returnData,HttpStatus.OK);
	}
	
	/*
	 * This method will be used to get the work area record  based on given work area details Id
	 */	
	
	@RequestMapping(value = "/getSectionRecordById.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getSectionRecordById(@RequestBody String paramPlant) throws JSONException {
		log.info("Entered in  Work Area Controller getWorkAreaById()  ::   "+paramPlant);
		Map<String,List> returnList = new HashMap<String,List>();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(paramPlant);
		Integer customerId = 0;
		if(jo.get("customerId") != null && !jo.get("customerId").toString().equalsIgnoreCase("nul")){			
			customerId =  jo.get("customerId").getAsInt();		
			
		}
		
		
		List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(customerId+"");
		returnList.put("customerList", customerList);
		List<SimpleObject> countryList = commonService.getCountriesList();		
		returnList.put("countryList",countryList);	
		
		if(jo.get("sectionId") != null && jo.get("sectionId").getAsInt() > 0 ){
			SectionDetailsInfoVo detailsInfoVo = sectionService.getSectionRecordById( jo.get("sectionId").getAsInt() );
		    List<SectionDetailsInfoVo> detailsInfoVos = new ArrayList<SectionDetailsInfoVo>();
		    detailsInfoVos.add(detailsInfoVo);
		    	   	  
		    
		   
		    List<SimpleObject> companyList = new ArrayList<SimpleObject>();
		   // List<PlantVo> plantList = new ArrayList<PlantVo>(); 
		  //  List<SimpleObject>  statesList = new ArrayList<SimpleObject>(); 
		   // List<LocationVo> locationList = new ArrayList<LocationVo>(); 
		     	  
	    	LocationVo location = new LocationVo();
	    	location.setCustomerId(detailsInfoVo.getCustomerDetailsId());
	    	location.setCompanyId(detailsInfoVo.getCompanyDetailsId());
	    	location.setCountryId(detailsInfoVo.getCountry());
	    	
	    	PlantVo plant = new PlantVo();
	    	plant.setCustomerId(detailsInfoVo.getCustomerDetailsId());
	    	plant.setCompanyId(detailsInfoVo.getCompanyDetailsId());
	    	plant.setLocationId(detailsInfoVo.getLocationDetailsId());
	    	
	    	companyList = vendorService.getComapanyNamesAsDropDown(detailsInfoVo.getCustomerDetailsId()+"",detailsInfoVo.getCompanyDetailsId()+"");
	    	//plantList =  plantService.getPlantsListBySearch(plant);
	    	//statesList = commonService.getStatesList(detailsInfoVo.getCompanyDetailsId());
	    	//locationList = locationService.getLocationsListBySearch(location);
	   
		    
			returnList.put("detailsInfoVos",detailsInfoVos);		
			
			returnList.put("companyList",companyList);
			//returnList.put("plantList",plantList);
			//returnList.put("statesList",statesList);
			//returnList.put("locationList",locationList);
		}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		log.info("Exiting from  Work Area Controller getWorkAreaById() ::  "+returnList);
		return new ResponseEntity<Map<String,List>>(returnList, HttpStatus.OK);
	}

	/*
	 * This method will be used to get transaction dates list for work area based on given customerId, companyId and work area Id
	 */
	@RequestMapping(value = "/getTransactionDatesListOfHistory.json", method = RequestMethod.POST)
	public ResponseEntity<List<SimpleObject>> getTransactionDatesListOfHistory(@RequestBody String paramPlant) throws JSONException {
		log.info("Entered in  Work Area Controller getTransactionDatesListForWorkArea() ::  "+paramPlant);
		List<SimpleObject> simpleObjects = new ArrayList();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(paramPlant);
		 simpleObjects = sectionService.getTransactionDatesListOfHistory(jo.get("customerId").getAsInt() , jo.get("companyId").getAsInt() , jo.get("sectionId").getAsInt() );	
		log.info("Exiting from getTransactionDatesListOfHistory() ::  "+simpleObjects);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>>(simpleObjects, HttpStatus.OK);
	}
	
}
