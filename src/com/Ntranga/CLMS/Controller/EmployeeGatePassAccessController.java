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

import com.Ntranga.CLMS.Service.CommonService;
import com.Ntranga.CLMS.Service.EmployeeGatePassAccessService;
import com.Ntranga.CLMS.Service.LocationService;
import com.Ntranga.CLMS.Service.VendorService;
import com.Ntranga.CLMS.vo.EmployeeGatePassAccessVo;
import com.Ntranga.CLMS.vo.LocationVo;
import com.Ntranga.CLMS.vo.OTExceededWorkersVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@RestController
@RequestMapping(value = "gatePassAccessController")
public class EmployeeGatePassAccessController {

	private static Logger log = Logger.getLogger(EmployeeGatePassAccessController.class);
	
	@Autowired
	private EmployeeGatePassAccessService employeeGatePassAccessService;
	@Autowired
	private LocationService  locationService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private VendorService vendorService;
	
	
	@RequestMapping(value = "/getlocationAndCountryDtlsByCompanyId.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getDeptLocPlantDtls(@RequestBody String JsonString) {
		Map<String,List> map = new HashMap<String, List>();
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(JsonString);
		
		String customerId = "";
		String companyId= "";
		
		if(jo.get("customerId")!= null && !jo.get("customerId").toString().equalsIgnoreCase("null")){
			customerId = jo.get("customerId").getAsString();
		}
		if(jo.get("companyId")!= null && !jo.get("companyId").toString().equalsIgnoreCase("null")){
			companyId = jo.get("companyId").getAsString();
		}
		
		List<SimpleObject> companyCoountries = commonService.getCompanyCountries(Integer.valueOf(companyId));
		map.put("countryList", companyCoountries);
		
		LocationVo LocationVo = new LocationVo();
		LocationVo.setCustomerId(Integer.valueOf(customerId));
		LocationVo.setCompanyId( Integer.valueOf(companyId));
		List<LocationVo> locationList = locationService.getLocationsListBySearch(LocationVo);		
		map.put("locationList", locationList);		
		
		return new ResponseEntity<Map<String,List>>(map,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/saveGatePassAccessRecord.json", method = RequestMethod.POST)
	public  ResponseEntity<Integer> saveGatePassAccessRecord(@RequestBody EmployeeGatePassAccessVo employeeGatePassAccessVo) {				
		Integer id = employeeGatePassAccessService.saveGatePassAccessRecord(employeeGatePassAccessVo);
		return new ResponseEntity<Integer>(id,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getGatePassAccessDetails.json", method = RequestMethod.POST)
	public  ResponseEntity<EmployeeGatePassAccessVo> getGatePassAccessDetails(@RequestBody EmployeeGatePassAccessVo employeeGatePassAccessVo) {				
		EmployeeGatePassAccessVo employeeGatePassAccessVo1 = employeeGatePassAccessService.getGatePassAccessDetails(employeeGatePassAccessVo);
		return new ResponseEntity<EmployeeGatePassAccessVo>(employeeGatePassAccessVo1,HttpStatus.OK);
	}

	
	
	@RequestMapping(value = "/getTransactionDatesForHistory.json", method = RequestMethod.POST)
	public  ResponseEntity<List<SimpleObject>> getTransactionDatesForHistory(@RequestBody EmployeeGatePassAccessVo employeeGatePassAccessVo) {				
		List<SimpleObject> objects = employeeGatePassAccessService.getTransactionDatesForHistory(employeeGatePassAccessVo);
		return new ResponseEntity<List<SimpleObject>>(objects,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getGatePassAccessRecordById.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getGatePassAccessRecordByShiftId(@RequestBody EmployeeGatePassAccessVo employeeGatePassAccessVo) {				
		EmployeeGatePassAccessVo accessVo = employeeGatePassAccessService.getGatePassAccessRecordById(employeeGatePassAccessVo);
		Map<String,List> map = new HashMap<String, List>();
		
		List companyNames  = vendorService.getComapanyNamesAsDropDown(accessVo.getCustomerId()+"",accessVo.getCompanyId()+"");
		map.put("companyList", companyNames);
		List<SimpleObject> companyCoountries = commonService.getCompanyCountries(Integer.valueOf(accessVo.getCompanyId()));
		map.put("countryList", companyCoountries);		
		LocationVo LocationVo = new LocationVo();
		LocationVo.setCustomerId(Integer.valueOf(accessVo.getCustomerId()));
		LocationVo.setCompanyId( Integer.valueOf(accessVo.getCompanyId()));
		List<LocationVo> locationList = locationService.getLocationsListBySearch(LocationVo);		
		map.put("locationList", locationList);
		List list = new ArrayList();
		list.add(accessVo);
		
		map.put("accessVo",list );
		return new ResponseEntity<Map<String,List>>(map,HttpStatus.OK);
	}
	/*
	 * 
	 * customerId  companyId locationId year month 
	 */
	
	@RequestMapping(value = "/getOTExceededWorkers.json", method = RequestMethod.POST)
	public  ResponseEntity<List<OTExceededWorkersVo>> getOTExceededWorkers(@RequestBody String employeeGatePassAccessJsonString) {					 
		List<OTExceededWorkersVo> objects = employeeGatePassAccessService.getOTExceededWorkers(employeeGatePassAccessJsonString);
		return new ResponseEntity<List<OTExceededWorkersVo>>(objects,HttpStatus.OK);
	}
	
	
}
