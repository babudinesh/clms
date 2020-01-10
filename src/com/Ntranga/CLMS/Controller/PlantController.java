package com.Ntranga.CLMS.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
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
import com.Ntranga.CLMS.Service.VendorService;
import com.Ntranga.CLMS.vo.CustomerVo;
import com.Ntranga.CLMS.vo.LocationVo;
import com.Ntranga.CLMS.vo.PlantVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@RestController
@RequestMapping(value = "PlantController")
public class PlantController {

private static final Logger log = Logger.getLogger(PlantController.class);
	
	@Autowired
	private PlantService plantService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private VendorService vendorService;
	
	@Autowired
	private LocationService locationService;
	
	/*
	 * This method will be used to save or update the job code details
	*/ 
	@RequestMapping(value = "/savePlant.json", method = RequestMethod.POST)
	public ResponseEntity<SimpleObject> savePlant(@RequestBody PlantVo paramPlant) {
		log.info("Entered in Plant Controller savePlant() ::  "+paramPlant);
		
		Integer plantId = null;
		int companyDetailsId = 0;
		int companyId = paramPlant.getCompanyId();
		int customerId = paramPlant.getCustomerId();
		
		SimpleObject object = new SimpleObject();
		try{
		List<SimpleObject> obj = companyService.getCompanyTransactionDates(customerId+"", companyId+"");
		
		if(obj!=null && obj.size() > 0) {
			companyDetailsId= (obj.get(0).getId());
		}
		
		log.info("Date "+companyDetailsId);
		java.util.Date companyDate = (companyService.getCompanyDetailsListByCompanyId(companyDetailsId).getTransactionDate());
		java.util.Date deptDate = (paramPlant.getTransactionDate());
		log.info("Diff "+companyDate.compareTo(deptDate));
		
		if(companyDate.compareTo(deptDate) <= 0){
			if(paramPlant != null ){
				plantId = plantService.savePlant(paramPlant);
				log.debug("PlantID: "+plantId);
			}
			
			if (plantId != null && plantId > 0) {
				object.setId(plantId);
				object.setName("Success");
			} else {
				object.setId(0);
				object.setName("Fail");
			}
		}else{
			object.setId(-1);
			object.setName("Transaction date should not be less than Company Transaction Date");
		}
		
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		log.info("Exiting from Plant Controller savePlant() ::  "+object.toString());
		return new ResponseEntity<SimpleObject>(object, HttpStatus.OK);
	}

	
	/*
	 * This method will be used to get the plants records based on given customerId, company Id and Plant Name
	 * 																					
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getPlantListBySearch.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getPlantListBySearch(@RequestBody PlantVo paramPlant) throws JSONException {
		log.info("Entered in Plant Controller getPlantListBySearch()   ::   "+paramPlant);
		Map<String,List> returnData = new HashMap<String,List>();
		try{
		//List<SimpleObject> deptTypelist = commonService.getDepartmentTypes();
		List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(String.valueOf(paramPlant.getCustomerId()));
		List<PlantVo> plantList = plantService.getPlantsListBySearch(paramPlant);
		log.debug("List of Job Codes : "+plantList);
		
		
		//returnData.put("deptTypeList", deptTypelist);
		returnData.put("customerList",customerList);
		returnData.put("plantList",plantList);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		log.info("Exiting from Plant Controller getPlantListBySearch() ::  "+JSONObject.valueToString(null).toString());
		//return  JSONObject.valueToString(s).toString();
		return new ResponseEntity<Map<String,List>>(returnData,HttpStatus.OK);
	}
	
	/*
	 * This method will be used to get the plant record  based on plant details Id
	 */	
	
	@RequestMapping(value = "/getPlantById.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getPlantById(@RequestBody String paramPlant) throws JSONException {
		log.info("Entered in Plant Controller getPlantById()  ::   "+paramPlant);
		Map<String,List> returnList = new HashMap<String,List>();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(paramPlant);
		log.debug("JSON Object:  "+jo);
		
		String PlantDetailsId = null, customerId = null;
		if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null
						|| jo.get("plantDetailsId") != null && jo.get("plantDetailsId").getAsString() != null){
			customerId = jo.get("customerId").getAsString(); 
			PlantDetailsId = jo.get("plantDetailsId").getAsString();  
			log.debug("customer d : "+customerId+" \t plant Details Id : "+PlantDetailsId);
		}

	    List<PlantVo> PlantVo= plantService.getPlantById(Integer.valueOf(PlantDetailsId.trim()));	
	    List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(customerId);
	    List<SimpleObject> countryList = commonService.getCountriesList();
	    List<SimpleObject> companyList = new ArrayList<SimpleObject>();
	    List<SimpleObject> statesList = new ArrayList<SimpleObject>();
	    List<LocationVo> locationList = new ArrayList<LocationVo>(); 
	     
	    if(PlantVo != null && PlantVo.size() > 0){
	    	LocationVo location = new LocationVo();
	    	location.setCustomerId(PlantVo.get(0).getCustomerId());
	    	location.setCompanyId(PlantVo.get(0).getCompanyId());
	    	location.setCountryId(PlantVo.get(0).getCountryId());
	    	
	    	companyList = vendorService.getComapanyNamesAsDropDown((PlantVo.get(0).getCustomerId())+"",(PlantVo.get(0).getCompanyId())+"");
	    	statesList = commonService.getStatesList(PlantVo.get(0).getCountryId());
	    	locationList = locationService.getLocationsListBySearch(location);
	    }
	    
		returnList.put("plantVo",PlantVo);
		returnList.put("customerList", customerList);
		returnList.put("countryList",countryList);
		returnList.put("companyList",companyList);
		returnList.put("statesList",statesList);
		returnList.put("locationList",locationList);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		log.info("Exiting from Plant Controller getPlantById() ::  "+returnList);
		return new ResponseEntity<Map<String,List>>(returnList, HttpStatus.OK);
	}

	/*
	 * This method will be used to get transaction dates list for plant based on given customerId, companyId and plantId
	 */
	@RequestMapping(value = "/getPlantTransactionDatesList.json", method = RequestMethod.POST)
	public ResponseEntity<List<SimpleObject>> getTransactionDatesListForPlants(@RequestBody String paramPlant) throws JSONException {
		log.info("Entered in Plant Controller getTransactionDatesListForPlant() ::  "+paramPlant);
		List<SimpleObject> simpleObjects = new ArrayList();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(paramPlant);
		log.debug("JSON Object "+jo);
		
		String customerId = null ,companyId = null, plantId = null;
		if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null
								&& jo.get("plantId") != null && jo.get("plantId").getAsString() != null
								&& jo.get("companyId") != null && jo.get("companyId").getAsString() != null){
			customerId = jo.get("customerId").getAsString(); 
			companyId = jo.get("companyId").getAsString();			
			plantId = jo.get("plantId").getAsString(); 
			log.debug("Customer Id:  "+customerId+"\t Company Id :   "+companyId+"\t Plant Id : "+plantId);
		}
		 simpleObjects = plantService.getTransactionListForPlants(Integer.valueOf(customerId), Integer.valueOf(companyId),  Integer.valueOf(plantId));	
		log.info("Exiting from Plant Controller getTransactionDatesListForPlant() ::  "+simpleObjects);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>>(simpleObjects, HttpStatus.OK);
	}
	
	 /*
		 * This service is used to validate the plant code that already exists are not
		 */
		@RequestMapping(value = "/validatePlantCode.json", method = RequestMethod.POST)
		public String validatePlantCode(@RequestBody PlantVo paramPlant) throws JSONException {
			log.info("Entered in Plant Controller validatePlantCode() ::  "+paramPlant);
			String returnString = null;
			try{
			List<PlantVo> plantList = plantService.getPlantsListBySearch(paramPlant);
			log.debug("List of Plant Codes : "+plantList);
			if(plantList != null && plantList.size() > 0){
				returnString ="0";
			}else{
				returnString = "1";
			}
			}catch(Exception e){
				log.error("Error Occured ",e);
			}
			
			log.info("Exiting from Plant Controller validatePlantCode() ::  "+returnString);
			return returnString;
		}
	
}
