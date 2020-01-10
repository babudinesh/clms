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
import com.Ntranga.CLMS.Service.ShiftPatternService;
import com.Ntranga.CLMS.Service.ShiftsDefineService;
import com.Ntranga.CLMS.Service.ShiftsService;
import com.Ntranga.CLMS.Service.VendorService;
import com.Ntranga.CLMS.vo.CustomerVo;
import com.Ntranga.CLMS.vo.LocationVo;
import com.Ntranga.CLMS.vo.PlantVo;
import com.Ntranga.CLMS.vo.ShiftPatternAdditionalRulesVo;
import com.Ntranga.CLMS.vo.ShiftPatternAssignShiftsVo;
import com.Ntranga.CLMS.vo.ShiftPatternVo;
import com.Ntranga.CLMS.vo.ShiftsDefineVo;
import com.Ntranga.CLMS.vo.ShiftsVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping(value="ShiftPatternController")
public class ShiftPatternController {

private static Logger log = Logger.getLogger(ShiftPatternController.class);
	
	@Autowired
	private ShiftPatternService shiftPatternService;

	@Autowired
	private ShiftsDefineService shiftsDefineService;
	
	@Autowired
	private VendorService vendorService;
	
	@Autowired
	private ShiftsService shiftsService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private PlantService plantService;
	
	/*
	 * This method will be used to save or update the job code details
	*/ 
	@RequestMapping(value = "/saveShiftPattern.json", method = RequestMethod.POST)
	public ResponseEntity<SimpleObject> saveShiftPattern(@RequestBody ShiftPatternVo paramShift) {
		log.info("Entered in Shift Pattern Controller savePlant() ::  "+paramShift);
		SimpleObject object = new SimpleObject();
		try{
		Integer shiftPatternId = null;
		int companyDetailsId = 0;
		
		int companyId = paramShift.getCompanyId();
		int customerId = paramShift.getCustomerId();
		
		
		List<SimpleObject> obj = companyService.getCompanyTransactionDates(customerId+"", companyId+"");
		
		if(obj!=null && obj.size() > 0) {
			companyDetailsId= (obj.get(0).getId());
		}
		
		//log.info("Date "+companyDetailsId);
		java.util.Date companyDate = (companyService.getCompanyDetailsListByCompanyId(companyDetailsId).getTransactionDate());
		java.util.Date patternDate = (paramShift.getTransactionDate());
		//log.info("Diff "+companyDate.compareTo(deptDate));
		
		if(companyDate.compareTo(patternDate) <= 0){
			if(paramShift != null ){
				shiftPatternId = shiftPatternService.saveShiftPattern(paramShift);
				log.debug("PlantID: "+shiftPatternId);
			}
			
			if (shiftPatternId != null && shiftPatternId > 0) {
				object.setId(shiftPatternId);
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
		log.info("Exiting from Shift Pattern Controller saveShiftPattern() ::  "+object.toString());
		return new ResponseEntity<SimpleObject>(object, HttpStatus.OK);
	}

	
	/*
	 * This method will be used to get the plants records based on given customerId, company Id and Plant Name
	 * 																					
	 */

	@RequestMapping(value = "/getShiftPatternListBySearch.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getShiftPatternListBySearch(@RequestBody ShiftPatternVo paarmShift) throws JSONException {
		log.info("Entered in Shift Pattern Controller getShiftPatternListBySearch()   ::   "+paarmShift);
		Map<String,List> returnData = new HashMap<String,List>();
		try{
		List<ShiftPatternVo> shiftPatternList = shiftPatternService.getShiftPatternListBySearch(paarmShift);
		log.debug("List of Job Codes : "+shiftPatternList);
		
		
		returnData.put("shiftPatternList",shiftPatternList);
		
		log.info("Exiting from Shift Pattern Controller getShiftPatternListBySearch() ::  "+JSONObject.valueToString(null).toString());
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(returnData,HttpStatus.OK);
	}
	
	/*
	 * This method will be used to get the plant record  based on plant details Id
	 */	
	@RequestMapping(value = "/getShiftPatternById.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getShiftPatternById(@RequestBody String paramShift) throws JSONException {
		log.info("Entered in Shift Pattern Controller getShiftPatternById()  ::   "+paramShift);
		Map<String,List> returnList = new HashMap<String,List>();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(paramShift);
		log.debug("JSON Object:  "+jo);
		
		String shiftPatternDetailsId = null, customerId = null;
		if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null
						|| jo.get("shiftPatternDetailsId") != null && jo.get("shiftPatternDetailsId").getAsString() != null){
			customerId = jo.get("customerId").getAsString(); 
			shiftPatternDetailsId = jo.get("shiftPatternDetailsId").getAsString();  
			log.debug("customer d : "+customerId+" \t Shift Pattern Details Id : "+shiftPatternDetailsId);
		}

	    List<ShiftPatternVo> patternVo= shiftPatternService.getShiftPatternById(Integer.valueOf(shiftPatternDetailsId.trim()));	
	    List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(customerId);
	    List<SimpleObject> countryList = new ArrayList<SimpleObject>();
	    List<SimpleObject> companyList = new ArrayList<SimpleObject>();
		List<ShiftsVo> shiftList = new ArrayList<ShiftsVo>();
		List<ShiftPatternAdditionalRulesVo> rules = new ArrayList<ShiftPatternAdditionalRulesVo>();
		List<ShiftPatternAssignShiftsVo> assignShifts = new ArrayList<ShiftPatternAssignShiftsVo>();
		
		List<ShiftsDefineVo> shiftsVos = new ArrayList<ShiftsDefineVo>();
		List<ShiftsDefineVo> shiftsDefineVos = new ArrayList<ShiftsDefineVo>();

	
	    if(patternVo != null && patternVo.size() > 0){
	    	companyList = vendorService.getComapanyNamesAsDropDown((patternVo.get(0).getCustomerId())+"",(patternVo.get(0).getCompanyId())+"");
	    	countryList = commonService.getCompanyCountries(patternVo.get(0).getCompanyId());
	    	ShiftsVo shiftsVo = new ShiftsVo();
			shiftsVo.setCustomerDetailsId(patternVo.get(0).getCustomerId());
			shiftsVo.setCompanyDetailsId(patternVo.get(0).getCompanyId());
			shiftsVo.setCountryId(patternVo.get(0).getCountryId());
			
		    ShiftsVo shift = shiftsService.getShiftRecord(shiftsVo);
		   if( shift.getDefaultPatternType().equalsIgnoreCase("Daily") ){
			   shift.setDefaultPatternType("1");
		   }else if( shift.getDefaultPatternType().equalsIgnoreCase("Weekly") ){
			   shift.setDefaultPatternType("7");
		   }else if( shift.getDefaultPatternType().equalsIgnoreCase("Bi Weekly")){
			   shift.setDefaultPatternType("14");
		   }else if( shift.getDefaultPatternType().equalsIgnoreCase("Monthly") ){
			   shift.setDefaultPatternType("31");
		   }
		    shiftList.add(shift);
		   
		    
		    
		    rules = shiftPatternService.getShiftAdditionalRules(patternVo.get(0).getCustomerId(), patternVo.get(0).getCompanyId(), patternVo.get(0).getShiftPatternDetailsId());
		    assignShifts = shiftPatternService.getShiftPatternAssignShifts(patternVo.get(0).getCustomerId(), patternVo.get(0).getCompanyId(), patternVo.get(0).getShiftPatternDetailsId());
		    shiftsDefineVos = shiftsDefineService.getShiftDefineGridData(patternVo.get(0).getCustomerId(),patternVo.get(0).getCompanyId(),"Y","",""); 
		    if(assignShifts.size() > 0) {
		    	ShiftsDefineVo  vo = (ShiftsDefineVo) shiftsDefineService.getShiftRecordByShiftId(assignShifts.get(0).getShiftDefineId());
		    	shiftsVos.add(vo);
		    }
		    
		    LocationVo LocationVo = new LocationVo();
			LocationVo.setCustomerId(patternVo.get(0).getCustomerId());
			LocationVo.setCompanyId(patternVo.get(0).getCompanyId());
			LocationVo.setStatus("Y");
			List<LocationVo> locationList = locationService.getLocationsListBySearch(LocationVo);
			locationList.add(0, new LocationVo(0,"All"));
			returnList.put("locationList", locationList);
			
			PlantVo paramPlant = new PlantVo();
			paramPlant.setCustomerId(patternVo.get(0).getCustomerId());
			paramPlant.setCompanyId(patternVo.get(0).getCompanyId());
			paramPlant.setLocationId(patternVo.get(0).getLocationDetailsId());
			paramPlant.setStatus("Y");
			List<PlantVo> plantList = plantService.getPlantsListBySearch(paramPlant);
			plantList.add(0, new PlantVo(0,"All"));
			returnList.put("plantList", plantList);
			
	    }
	    
		returnList.put("patternVo",patternVo);
		returnList.put("customerList", customerList);
		returnList.put("countryList",countryList);
		returnList.put("companyList",companyList);
		returnList.put("shiftDefault", shiftList);
		returnList.put("patternAssignList", assignShifts);
		returnList.put("shiftCodeList", shiftsDefineVos);
		returnList.put("patternAdditionalList", rules);
		returnList.put("shiftList", shiftsVos);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		log.info("Exiting from Shift Pattern Controller getShiftPatternById() ::  "+returnList);
		return new ResponseEntity<Map<String,List>>(returnList, HttpStatus.OK);
	}

	/*
	 * This method will be used to get transaction dates list for plant based on given customerId, companyId and plantId
	 */
	@RequestMapping(value = "/getShiftPatternTransactionDatesList.json", method = RequestMethod.POST)
	public ResponseEntity<List<SimpleObject>> getTransactionDatesListForShiftPattern(@RequestBody String paramPlant) throws JSONException {
		log.info("Entered in Shift Pattern Controller getTransactionDatesListForShiftPattern() ::  "+paramPlant);
		List<SimpleObject> simpleObjects = new ArrayList();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(paramPlant);
		log.debug("JSON Object "+jo);
		
		String customerId = null ,companyId = null, shiftPatternId = null;
		if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null
								&& jo.get("shiftPatternId") != null && jo.get("shiftPatternId").getAsString() != null
								&& jo.get("companyId") != null && jo.get("companyId").getAsString() != null){
			customerId = jo.get("customerId").getAsString(); 
			companyId = jo.get("companyId").getAsString();			
			shiftPatternId = jo.get("shiftPatternId").getAsString(); 
			log.debug("Customer Id:  "+customerId+"\t Company Id :   "+companyId+"\t Shift Pattern Id : "+shiftPatternId);
		}
		 simpleObjects = shiftPatternService.getTransactionListForShiftPattern(Integer.valueOf(customerId), Integer.valueOf(companyId),  Integer.valueOf(shiftPatternId));	
		log.info("Exiting from Shift Pattern Controller getTransactionDatesListForShiftPattern() ::  "+simpleObjects);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>>(simpleObjects, HttpStatus.OK);
	}
}
