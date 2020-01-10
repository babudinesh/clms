package com.Ntranga.CLMS.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.servlet.ModelAndView;

import com.Ntranga.CLMS.Service.CommonService;
import com.Ntranga.CLMS.Service.CompanyService;
import com.Ntranga.CLMS.Service.DepartmentService;
import com.Ntranga.CLMS.Service.LocationService;
import com.Ntranga.CLMS.Service.VendorService;
import com.Ntranga.CLMS.vo.CompanyDetailsInfoVo;
import com.Ntranga.CLMS.vo.CompanyProfileVo;
import com.Ntranga.CLMS.vo.CustomerVo;
import com.Ntranga.CLMS.vo.DepartmentVo;
import com.Ntranga.CLMS.vo.LocationCharacteristicsVo;
import com.Ntranga.CLMS.vo.LocationVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@SuppressWarnings({"rawtypes","unchecked"})
@RestController
@RequestMapping(value="LocationController")
public class LocationController {

private static final Logger log = Logger.getLogger(LocationController.class);
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private VendorService vendorService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private DepartmentService departmentService;
	
	
	/*
	 * To get the location list on page load
	 */
	@RequestMapping(value = "/location", method = RequestMethod.GET)
	public ModelAndView location(HttpServletRequest request) {
		log.info("Entered in Location Controller location() ");
		ModelAndView mav = new ModelAndView("/location"); 
				
		try {
			List<SimpleObject> locationTypes = commonService.getLocationTypes();
			log.debug("Type of Locations : "+locationTypes);
			List<LocationVo> locationList = locationService.getLocationsList();
			log.debug("Locations List ::  "+locationList);
			
			mav.addObject("locationTypes", locationTypes);
			mav.addObject("locationList", locationList);
			
		} catch (Exception e) {
			log.error("Error Occured. ",e);
			log.info("Exiting from Location Controller location() ::  "+mav);
		}
		
		log.info("Exiting from Location Controller location() ::  "+mav);
		return mav;
	}
	
	/*
	 * This method will be used to save or update the location details
	 */
	@RequestMapping(value = "/saveLocation.json", method = RequestMethod.POST)
	public ResponseEntity<SimpleObject> saveLocation(@RequestBody LocationVo paramLocation) {
		log.info("Entered in Location Controller saveLocation() ::  "+paramLocation);
		
		String locationId = null;
		SimpleObject object = new SimpleObject();
		try{
		int companyId = paramLocation.getCompanyId();
		int customerId = paramLocation.getCustomerId();
		int companyDetailsId = 0;
		
		List<SimpleObject> obj = companyService.getCompanyTransactionDates(customerId+"", companyId+"");
		if(obj!=null && obj.size() > 0) {
			companyDetailsId= (obj.get(0).getId());
		}
		
		log.info("Date "+companyDetailsId);
		java.util.Date companyDate = (companyService.getCompanyDetailsListByCompanyId(companyDetailsId).getTransactionDate());
		java.util.Date deptDate = paramLocation.getTransactionDate();
		log.info("Diff "+companyDate.compareTo(deptDate));
		
		if(companyDate.compareTo(deptDate) <= 0){
			if (paramLocation != null){
				locationId = locationService.saveLocation(paramLocation);
				log.debug("location ID: "+locationId);
			}
			String[] s= locationId.split("\\-");
			if (locationId != null && s[0]!=null) {
				object.setId(Integer.valueOf(s[0]));
				object.setName(s[1]);
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
		log.info("Exiting from Location Controller saveLocation() ::  "+object);
		return new ResponseEntity<SimpleObject>(object, HttpStatus.OK);
	}

	
	/*
	 * This method will be used to get the location records 
	 */
	@RequestMapping(value = "/getLocationsList.json", method = RequestMethod.GET)
	public String getLocationsList(HttpServletRequest request) throws JSONException {
		log.info("Entered in Location Controller getLocationsList()");
		String s ="";
		try{
		List<LocationVo> locationList = locationService.getLocationsList();
		log.debug("List of Locations : "+locationList);
		
		StringBuilder br = new StringBuilder();
		br.append("[");
		for(LocationVo location : locationList){
			br.append("[\""+location.getCustomerName()+"\",\""+location.getCompanyName()+"\",\""+location.getLocationCode()+"\",\""+location.getLocationName()+"\",\""+location.getTransactionDate()+"\",\""+location.getStatus()+"\",\""+location.getLocationId()+"\"],");
		}
		 s = br.substring(0, br.length()-1);
		s+="]";
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		log.info("Exiting from Location Controller getLocationsList() ::  "+JSONObject.valueToString(s).toString());
		return  JSONObject.valueToString(s).toString();
	}
  
	/*
	 * This method will be used to get the location records based on given customer name and company name
	 * 																					and location code/name and status
	 */
	@RequestMapping(value = "/getLocationsListBySearch.json", method = RequestMethod.POST)
	public ResponseEntity<List<LocationVo>> getLocationsListBySearch(@RequestBody LocationVo location) throws JSONException {
		log.info("Entered in Location Controller getLocationsListBySearch()  ::   "+location);
		
		List<LocationVo> locationList = locationService.getLocationsListBySearch(location);
		log.debug("List of Locations : "+locationList);
		
		log.info("Exiting from Location Controller getLocationsListBySearch() ::  "+locationList);
		return  new ResponseEntity<List<LocationVo>>(locationList,HttpStatus.OK);
	}
	
	/*
	 * This method will be used to get the location list based on location Id
	 */	
	@RequestMapping(value = "/getLocationById.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>>getLocationById(@RequestBody String paramLocation) throws JSONException {
		log.info("Entered in Location Controller getLocationById()  ::   "+paramLocation);
		Map<String,List> returnLocation = new HashMap<String,List>();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(paramLocation);
		log.debug("JSON Object:  "+jo);
		
		String locationId = null, customerId= null;
		if(jo.get("locationDetailsId") != null && jo.get("locationDetailsId").getAsString() != null){
			locationId = jo.get("locationDetailsId").getAsString();  
			log.debug("Location Details Id : "+locationId);
		}
		
		if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null){
			customerId = jo.get("customerId").getAsString();  
			log.debug("Customer Id : "+customerId);
		}
		
		List<SimpleObject> locationTypes = commonService.getLocationTypes();
		List<LocationVo> location = locationService.getLocationById(Integer.valueOf(locationId.trim()));		
	    List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(customerId);
	    List<SimpleObject> countryList = commonService.getCountriesList();
	    List<LocationVo> locDeptList = new ArrayList<LocationVo>();
	    List<SimpleObject> companyList = new ArrayList<SimpleObject>();
	    List<SimpleObject> statesList = new ArrayList<SimpleObject>();
		
		
		//List<SimpleObject> deptTypelist = commonService.getDepartmentTypes();
		
		List<DepartmentVo> departmentList = new ArrayList<DepartmentVo>();
	    if(location != null && location.size() > 0){
	    	DepartmentVo dept = new DepartmentVo();
			dept.setCompanyId(location.get(0).getCustomerId());
			dept.setCompanyId(location.get(0).getCompanyId());
			
			// departmentList = departmentService.getDepartmentsListBySearch(dept);
	    	companyList = vendorService.getComapanyNamesAsDropDown((location.get(0).getCustomerId())+"",location.get(0).getCompanyId()+"");
	    	statesList = commonService.getStatesList(location.get(0).getCountryId());
	    //	locDeptList = locationService.getDepartmentList(location.get(0).getLocationId());
	    }
	    
	    returnLocation.put("locationTypesList", locationTypes);
	    returnLocation.put("locationVo",location);
	    returnLocation.put("customerList",customerList);
	    returnLocation.put("countryList", countryList);
	    returnLocation.put("locDeptList", locDeptList);
	    returnLocation.put("departmentList", departmentList);
	    returnLocation.put("companyList",companyList);
	    returnLocation.put("statesList",statesList);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		log.info("Exiting from Location Controller getLocationById() ::  ");

		return new ResponseEntity<Map<String,List>>(returnLocation, HttpStatus.OK);

	}

	/*
	 * This method will be used to get transaction dates list for location based on given customerId, companyId and locationId
	 */
	@RequestMapping(value = "/getTransactionDatesList.json", method = RequestMethod.POST)
	public ResponseEntity<List<SimpleObject>> getTransactionDatesListForLocation(@RequestBody String location) throws JSONException {
		log.info("Entered in Location Controller getTransactionDatesListForLocation() ::  "+location);
		List<SimpleObject> simpleObjects =  new ArrayList();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(location);
		log.debug("JSON Object "+jo);
		
		String customerId = null ,companyId = null, locationId = null;
		if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null
								&& jo.get("locationId") != null && jo.get("locationId").getAsString() != null
								&& jo.get("companyId") != null && jo.get("companyId").getAsString() != null){
			customerId = jo.get("customerId").getAsString(); 
			companyId = jo.get("companyId").getAsString();			
			locationId = jo.get("locationId").getAsString(); 
			log.debug("Customer Id:  "+customerId+"\t Company Id :   "+companyId+"\t Location Id : "+locationId);
		}
		 simpleObjects = locationService.getTransactionListForLocation(Integer.valueOf(customerId), Integer.valueOf(companyId),  Integer.valueOf(locationId));
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		log.info("Exiting from Location Controller getTransactionDatesListForLocation() ::  "+simpleObjects);
		return new ResponseEntity<List<SimpleObject>>(simpleObjects, HttpStatus.OK);
	}
	
	/*
	 * This method will be used to save or update the location characteristic details
	 */
	@RequestMapping(value = "/saveLocationCharacteristics.json", method = RequestMethod.POST)
	public ResponseEntity<SimpleObject> saveLocationCharacteristics(@RequestBody LocationCharacteristicsVo paramLocation) {
		log.info("Entered in Location Controller saveLocationCharacteristics() ::  "+paramLocation);
		
		Integer locationCharacteristicId = null;
		
		SimpleObject object = new SimpleObject();
		try{
		//List<CompanyDetailsVo> details = companyService.getCompanyDetailsListByCustomerId(paramLocation.getCustomerId(),"", paramLocation.getCompanyName(),"");
		CompanyDetailsInfoVo cmpDetails = new CompanyDetailsInfoVo();
		java.util.Date cmpRegDate = null;
		
		if(paramLocation.getCompanyInfoId() > 0){
			cmpDetails = companyService.getCompanyDetailsListByCompanyId(paramLocation.getCompanyInfoId());
			log.debug("Date "+cmpDetails);			
			cmpRegDate = cmpDetails.getRegistrationDate();
		}
		
		int esiRegDate = 0;
		//int esiStartDate = 0;
		int pfRegDate = 0;
		//int pfStartDate = 0;

		if(cmpRegDate != null){
			esiRegDate 		= (paramLocation.getEsiRegistrationDate() != null ) ? cmpRegDate.compareTo(paramLocation.getEsiRegistrationDate()) : 0 ;
			//esiStartDate 	= paramLocation.getEsiStartDate() != null ? cmpRegDate.compareTo(paramLocation.getEsiStartDate()) : 0 ;
			pfRegDate 		= paramLocation.getPfRegistrationDate() != null ? cmpRegDate.compareTo(paramLocation.getPfRegistrationDate()) : 0 ;
			//pfStartDate		= paramLocation.getPfStartDate() != null ? cmpRegDate.compareTo(paramLocation.getPfStartDate()) : 0;
		}
	
		if(esiRegDate <= 0 &&  pfRegDate <=0 ){
			if (paramLocation != null){
				locationCharacteristicId = locationService.saveLocationCharacteristics(paramLocation);
				log.debug("location ID: "+locationCharacteristicId);
			}
			if (locationCharacteristicId > 0) {
				object.setId(locationCharacteristicId);
				object.setName(paramLocation.getLocationId()+"");
			} else {
				object.setId(0);
				object.setName("Fail");
			}
		}else{
			String str="";
			object.setId(-1);
			if(esiRegDate > 0){ str+=" ESI Registration date";}
			//if(esiStartDate > 0) { str+=" ESI Start date";}
			if(pfRegDate > 0) { str+=" PF  Registration date";}
			//if(pfStartDate > 0) { str+=" PF  Start date";}
			
			object.setName(str+" should not be less than Company Registration Date");
		}
			log.info("Exiting from Location Controller saveLocationCharacterstics() ::  "+object);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
			return new ResponseEntity<SimpleObject>(object, HttpStatus.OK);
		
		}

	
	
	/*
	 * This method will be used to get the location characterstics  based on location Id
	 */	
	@RequestMapping(value = "/getLocationCharacteristicsByLocationId.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getLocationCharacteristicsByLocationId(@RequestBody String paramLocation) throws JSONException {
		log.info("Entered in Location Controller getLocationCharactersticsById()  ::   "+paramLocation);
		
		Map<String,List> returnLocation = new HashMap<String,List>();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(paramLocation);
		log.debug("JSON Object "+jo);
		
		String locationId = null, customerId= null;
		
		if(jo.get("locationId") != null && jo.get("locationId").getAsString() != null){
			locationId = jo.get("locationId").getAsString(); 
			log.debug("Location Id : "+locationId);
		}
		
		if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null){
			customerId = jo.get("customerId").getAsString();  
			log.debug("Customer Id : "+customerId);
		}
		/*if(jo.get("companyId") != null && jo.get("companyId").getAsString() != null){
			companyId = jo.get("companyId").getAsString();  
			log.debug("Company Id : "+customerId);
		}*/
	    List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(customerId);
	    List <LocationCharacteristicsVo> location = locationService.getLocationCharacteristicsByLocationId(Integer.valueOf(locationId.trim()));	   
		List<SimpleObject> pfTypeList = commonService.getPFTypes();
		List<SimpleObject> countryList = new ArrayList<SimpleObject>();
		List<SimpleObject> companyList = new ArrayList<SimpleObject>();
		List<SimpleObject> statesList = new ArrayList<SimpleObject>();
		 
		 if(location != null && location.size() > 0){
			// companyList = companyService.getCompanyDetailsListByCustomerId(location.get(0).getCustomerId(),null,null,null);
			 companyList = vendorService.getComapanyNamesAsDropDown(location.get(0).getCustomerId()+"",location.get(0).getCompanyId()+"" );
			 countryList = commonService.getCompanyCountries(location.get(0).getCompanyId());
			 statesList = commonService.getStatesList(location.get(0).getCompanyId());
		 }
		returnLocation.put("location",location);
		returnLocation.put("statesList",statesList); 
	    returnLocation.put("countryList",  countryList);
	    returnLocation.put("customerList",customerList);
	    returnLocation.put("pfTypesList", pfTypeList);
	    returnLocation.put("companyList",companyList);
	    
	    log.info("Exiting from Location Controller getLocationCharactersticsById() ::  "+returnLocation);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		
		return new ResponseEntity<Map<String,List>>(returnLocation, HttpStatus.OK);
	}

	/*
	 * This method will be used to get the location characterstics  based on location Id
	 */	
	@RequestMapping(value = "/getCmpProfileDept.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getCompanyProfileByCompanyId(@RequestBody String paramLocation) throws JSONException {
		log.info("Entered in Location Controller getCompanyProfileByCompanyId()  ::   "+paramLocation);
		
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(paramLocation);
		log.debug("JSON Object "+jo);
		Map<String,List> returnData = new HashMap<String,List>();
		try{
		String customerId = null, companyId= null;
		
		if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null){
			customerId = jo.get("customerId").getAsString(); 
			log.debug("Customer Id : "+customerId);
		}
		
		if(jo.get("companyId") != null && jo.get("companyId").getAsString() != null){
			companyId = jo.get("companyId").getAsString(); 
			log.debug("Company Id : "+companyId);
		}
		
		DepartmentVo dept = new DepartmentVo();
		dept.setCompanyId(Integer.valueOf(customerId));
		dept.setCompanyId(Integer.valueOf(companyId));
		
		List<SimpleObject> deptTypelist = commonService.getDepartmentTypes();
		List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(companyId);
		List<DepartmentVo> departmentList = departmentService.getDepartmentsListBySearch(dept);
		List<SimpleObject> countryList = commonService.getCompanyCountries(Integer.valueOf(companyId.trim()));
		log.debug("List of Departments : "+departmentList);
		
		
		List<LocationVo> profileList = new ArrayList<LocationVo>();
		CompanyProfileVo profile = companyService.getCompanyProfileByCompanyId(companyId, customerId);	
		LocationVo cmp = new LocationVo();
		cmp.setStandardHoursPerWeek(profile.getStandarHoursPerWeek());
		cmp.setBusinessEndTime((profile.getBussinessEndTime() != null && !profile.getBussinessEndTime().equals("")) ? (profile.getBussinessEndTime()).substring(0,5):null);
		cmp.setBusinessStartTime((profile.getBussinessStartTime() != null && !profile.getBussinessStartTime().equals("")) ?(profile.getBussinessStartTime()).substring(0,5) : null);
		cmp.setBusinessHoursPerDay(profile.getBussinessHoursPerDay());
		cmp.setWorkWeekEndDay(String.valueOf(profile.getWorkWeekEndId()));
		cmp.setNumberOfWorkingDays(profile.getNumberOfWorkingDays());
		cmp.setWorkWeekStartDay(String.valueOf(profile.getWorkWeekStartId()));
		
		profileList.add(cmp);
		
		
		returnData.put("deptTypeList", deptTypelist);
		returnData.put("customerList",customerList);
		returnData.put("departmentList",departmentList);
		returnData.put("Profile",profileList);
		returnData.put("countryList", countryList);
		log.info("Exiting from Location Controller getCompanyProfileByCompanyId() ::  "+returnData);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}

		return new ResponseEntity<Map<String,List>>(returnData,HttpStatus.OK);
	}
	
	/*
	 * This service is used to validate the Jobcode that already exists are not
	 */
	@RequestMapping(value = "/validateLocationCode.json", method = RequestMethod.POST)
	public Integer validateLocationCode(@RequestBody LocationVo paramLocation) throws JSONException {
		log.info("Entered in Location Controller validateLocationCode() ::  "+paramLocation);
		Integer location = -1;
		
		try{
			location = locationService.validateLocationCode(paramLocation);
			log.debug("Location Size : "+location);
			
		}catch(Exception e){
			log.error("Error Occured ",e);
			location = -1;
		}
		
		log.info("Exiting from Location Controller validateLocationCode() ::  "+location);
		return location;
	}
	
}
