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
import com.Ntranga.CLMS.Service.HolidayCalendarService;
import com.Ntranga.CLMS.Service.LocationService;
import com.Ntranga.CLMS.Service.VendorService;
import com.Ntranga.CLMS.vo.CustomerVo;
import com.Ntranga.CLMS.vo.HolidayCalendarHolidaysInfoVo;
import com.Ntranga.CLMS.vo.HolidayCalendarVo;
import com.Ntranga.CLMS.vo.LocationVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping(value = "HolidayController")
public class HolidayCalendarController {

private static final Logger log = Logger.getLogger(DepartmentController.class);
	
	@Autowired
	private HolidayCalendarService holidayCalendarService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private VendorService vendorService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private LocationService locationService;
	
	/*
	 * This method will be used to save or update the holiday details
	 */
	@RequestMapping(value = "/saveHolidayCalendar.json", method = RequestMethod.POST)
	public ResponseEntity<SimpleObject> saveHolidayCalendar(@RequestBody HolidayCalendarVo paramHoliday) {
		log.info("Entered in Holiday Controller saveHolidayCalendar() ::  "+paramHoliday);
		
		SimpleObject object = new SimpleObject();
		Integer holidayCalendarDetailsId = null;
		try{
		
		if (paramHoliday != null){
			holidayCalendarDetailsId = holidayCalendarService.saveHolidayCalendar(paramHoliday);
			log.debug("Department ID: "+holidayCalendarDetailsId);
		}
	
		if (holidayCalendarDetailsId != null) {
			object.setId(holidayCalendarDetailsId);
			object.setName("Success");
		} else {
			object.setId(0);
			object.setName("Fail");
		}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		log.info("Exiting from Department Controller saveHolidayCalendar() ::  "+object);
		return new ResponseEntity<SimpleObject>(object, HttpStatus.OK);
	}

	/*
	 * This method will be used to get the holiday calendars based on given customer Id and / or company Id 
	 * 							and/or location Id and/or status and/or calendar code
	 */
	@RequestMapping(value = "/getHolidayCalendarListBySearch.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getHolidayCalendarListBySearch(@RequestBody HolidayCalendarVo paramHoliday) throws JSONException {
		log.info("Entered in Holiday Controller getHolidayCalendarListBySearch()  ::   "+paramHoliday);
		Map<String,List> returnData = new HashMap<String,List>();
		try{
		List<HolidayCalendarVo> holidayCalendarList = holidayCalendarService.getHolidayCalendarListBySearch(paramHoliday);
		log.debug("List of holiday Calendars : "+holidayCalendarList);
		
		
		returnData.put("holidayCalendarList",holidayCalendarList);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		log.info("Exiting from Holiday Controller getHolidayCalendarListBySearch() ::  "+returnData);
		return new ResponseEntity<Map<String,List>>(returnData,HttpStatus.OK);
	}
	
	/*
	 * This method will be used to get the holiday calendar list based on holiday calendar details Id
	 */	
	@RequestMapping(value = "/getHolidayCalendarById.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getHolidayCalendarById(@RequestBody String paramHoliday) throws JSONException {
		log.info("Entered in Holiday Controller getHolidayCalendarById()  ::   "+paramHoliday);
		Map<String,List> returnList = new HashMap<String,List>();
		try{ 
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(paramHoliday);
		log.debug("JSON Object:  "+jo);
		
		String holidayCalendarDetailsId = null, customerId = null;
		if(jo.get("holidayCalendarDetailsId") != null && jo.get("holidayCalendarDetailsId").getAsString() != null){
			holidayCalendarDetailsId = jo.get("holidayCalendarDetailsId").getAsString();  
			log.debug("Holiday Calendar Details  Id : "+holidayCalendarDetailsId);
		}
		
		if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null){
			customerId = jo.get("customerId").getAsString();  
			log.debug("customer Id : "+customerId);
		}
		
		List<SimpleObject> holidayTypes = commonService.getHolidayTypes();
	    List<HolidayCalendarVo> holidayVo= holidayCalendarService.getHolidayCalendarById(Integer.valueOf(holidayCalendarDetailsId.trim()));	
	    List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(customerId);
	    List<SimpleObject> companyList = new ArrayList<SimpleObject>();
	    List<HolidayCalendarHolidaysInfoVo> holidayList = new ArrayList<HolidayCalendarHolidaysInfoVo>();
	    List<SimpleObject> countryList = new ArrayList<SimpleObject>();
	    List<LocationVo> locationList = new ArrayList<LocationVo>(); 
	    
	    if(holidayVo != null && holidayVo.size() > 0){
	    	LocationVo location = new LocationVo();
	    	location.setCustomerId(holidayVo.get(0).getCustomerId());
	    	location.setCompanyId(holidayVo.get(0).getCompanyId());
	    	location.setCountryId(holidayVo.get(0).getCountryId());
	    	
	    	
		    companyList = vendorService.getComapanyNamesAsDropDown((holidayVo.get(0).getCustomerId())+"",holidayVo.get(0).getCompanyId()+"");
		    holidayList = holidayCalendarService.getHolidaysList(Integer.valueOf(holidayCalendarDetailsId.trim()));
		    countryList = commonService.getCompanyCountries(holidayVo.get(0).getCompanyId());
		    locationList = locationService.getLocationsListBySearch(location);
	    }
		log.info("Exiting from Holiday Controller getHolidayCalendarById() ::  "+holidayVo);
		

		returnList.put("holidayTypes", holidayTypes);
		returnList.put("holidayVo",holidayVo);
		returnList.put("customerList", customerList);
		returnList.put("companyList", companyList);
		returnList.put("countryList",countryList);
		returnList.put("holidayList",holidayList);
		returnList.put("locationList",locationList);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		
		return new ResponseEntity<Map<String,List>>(returnList, HttpStatus.OK);
	}

	
	/*
	 * This service is used to validate the Calendar code code that already exists or not based on
	 * 											customer, company, country, location and year
	 */
	@RequestMapping(value = "/validateCalendarCode.json", method = RequestMethod.POST)
	public String validateCalendarCode(@RequestBody HolidayCalendarVo paramHoliday) throws JSONException {
		log.info("Entered in Plant Controller validateCalendarCode() ::  "+paramHoliday);
		String returnString = null;
		List<HolidayCalendarVo> holidayCalendarList = holidayCalendarService.getHolidayCalendarListBySearch(paramHoliday);
		log.debug("List of Calendar Codes : "+holidayCalendarList);
		if(holidayCalendarList != null && holidayCalendarList.size() > 0){
			returnString ="0";
		}else{
			returnString = "1";
		}
		
		log.info("Exiting from Plant Controller validateCalendarCode() ::  "+returnString);
		return returnString;
	}
	
}
