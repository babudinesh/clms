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
import com.Ntranga.CLMS.Service.LocationService;
import com.Ntranga.CLMS.Service.PlantService;
import com.Ntranga.CLMS.Service.ShiftsDefineService;
import com.Ntranga.CLMS.Service.ShiftsService;
import com.Ntranga.CLMS.Service.VendorService;
import com.Ntranga.CLMS.vo.CustomerVo;
import com.Ntranga.CLMS.vo.LocationVo;
import com.Ntranga.CLMS.vo.PlantVo;
import com.Ntranga.CLMS.vo.ShiftsDefineVo;
import com.Ntranga.CLMS.vo.ShiftsVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@RestController
@RequestMapping(value="shiftsDefineController")
public class ShiftsDefineController {

	private static Logger log = Logger.getLogger(ShiftsDefineController.class);
	
	@Autowired
	private ShiftsDefineService shiftsDefineService;
	@Autowired
	private VendorService vendorService;
	@Autowired
	private ShiftsService shiftsService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private PlantService plantService;
	
	// for search page
	@RequestMapping(value = "/getShiftDefineGridData.json", method = RequestMethod.POST)
	public  ResponseEntity<List<ShiftsDefineVo>> getShiftDefineGridData(@RequestBody String shiftsVoString) {
		List<ShiftsDefineVo> shiftsDefineVos = new ArrayList<>();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(shiftsVoString);		
		 shiftsDefineVos = shiftsDefineService.getShiftDefineGridData(jo.get("customerId").getAsInt(),jo.get("companyId").getAsInt(),jo.get("status").getAsString(),jo.get("shiftCode").getAsString(),jo.get("shiftName").getAsString()  );
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<ShiftsDefineVo>>(shiftsDefineVos,HttpStatus.OK);
	}
	
	
	// for add or view Page 
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getMasterDataForShifteView.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List> > getMasterDataForShifteView(@RequestBody String CustomerIdshiftIdJson) {
		Map<String,List> masterInfoMap = new HashMap<String,List>();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(CustomerIdshiftIdJson);	
		ShiftsVo shiftsVo = new ShiftsVo();
		Integer customerId = 0;
		
		if(jo.get("customerId") != null && !jo.get("customerId").toString().equalsIgnoreCase("null")){
			customerId= jo.get("customerId").getAsInt();
		}
		
		
		List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(customerId+"");						
		masterInfoMap.put("customerList", customerList);
		
		if(jo.get("shiftId").getAsInt() > 0){
			ShiftsDefineVo  vo = (ShiftsDefineVo) shiftsDefineService.getShiftRecordByShiftId(jo.get("shiftId").getAsInt());
			List<ShiftsDefineVo> shiftsVos = new ArrayList<ShiftsDefineVo>();
			shiftsVos.add(vo);
			masterInfoMap.put("shiftDefineVo", shiftsVos);	
			
			List<SimpleObject> companyCoountries = commonService.getCompanyCountries(vo.getCompanyDetailsId());
			masterInfoMap.put("countryList", companyCoountries);
			
			LocationVo LocationVo = new LocationVo();
			LocationVo.setCustomerId(vo.getCustomerDetailsId());
			LocationVo.setCompanyId(vo.getCompanyDetailsId());
			LocationVo.setStatus("Y");
			List<LocationVo> locationList = locationService.getLocationsListBySearch(LocationVo);
			locationList.add(0, new LocationVo(0,"All"));
			masterInfoMap.put("locationList", locationList);
			
			PlantVo paramPlant = new PlantVo();
			paramPlant.setCustomerId(vo.getCustomerDetailsId());
			paramPlant.setCompanyId(vo.getCompanyDetailsId());
			paramPlant.setLocationId(vo.getLocationDetailsId());
			paramPlant.setStatus("Y");
			List<PlantVo> plantList = plantService.getPlantsListBySearch(paramPlant);
			plantList.add(0, new PlantVo(0,"All"));
			masterInfoMap.put("plantList", plantList);
			
			List companyNames  = vendorService.getComapanyNamesAsDropDown(vo.getCustomerDetailsId()+"",vo.getCompanyDetailsId()+"");
			masterInfoMap.put("companyList", companyNames);	
			if(companyNames!= null && companyNames.size() ==1){			
				shiftsVo.setCountryId(vo.getCountryId());
				shiftsVo.setCustomerDetailsId(vo.getCustomerDetailsId());
				shiftsVo.setCompanyDetailsId(vo.getCompanyDetailsId());
				ShiftsVo vos = shiftsService.getShiftRecord(shiftsVo);
				List<ShiftsVo> shiftsVoss = new ArrayList<ShiftsVo>();
				shiftsVoss.add(vos);
				masterInfoMap.put("shiftsVo", shiftsVoss);
			}
		}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap, HttpStatus.OK);
	}
	
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getTransactionDatesForShiftsHistory.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List> > getTransactionDatesForShiftsHistory(@RequestBody String shiftsVoString) {		
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(shiftsVoString);	
		ShiftsVo shiftsVo = new ShiftsVo();
		
		Map<String,List> masterInfoMap = shiftsDefineService.getTransactionDatesForShiftsHistory(jo.get("customerId").getAsInt(),jo.get("companyId").getAsInt(),jo.get("shiftCode").getAsString());
		List<SimpleObject> companyCoountries = commonService.getCompanyCountries(jo.get("companyId").getAsInt());
		masterInfoMap.put("countryList", companyCoountries);
		List companyCountries  = vendorService.getComapanyNamesAsDropDown(jo.get("customerId").getAsString(),jo.get("companyId").getAsString());
		masterInfoMap.put("companyList", companyCountries);
		if(companyCountries!= null && companyCountries.size() ==1){			
			shiftsVo.setCountryId(((SimpleObject)companyCountries.get(0)).getId());
			shiftsVo.setCustomerDetailsId(jo.get("customerId").getAsInt());
			shiftsVo.setCompanyDetailsId(jo.get("companyId").getAsInt());
			ShiftsVo vos = shiftsService.getShiftRecord(shiftsVo);
			List<ShiftsVo> shiftsVoss = new ArrayList<ShiftsVo>();
			shiftsVoss.add(vos);
			masterInfoMap.put("shiftsVo", shiftsVoss);
		}		
		return new ResponseEntity<Map<String,List>>(masterInfoMap, HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getShiftRecordByShiftId.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List> > getShiftRecordByShiftId(@RequestBody String shiftId) {
		Map<String,List> masterInfoMap = new HashMap<String,List>();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(shiftId);	
		ShiftsVo shiftsVo = new ShiftsVo();
		
		ShiftsDefineVo  vo = (ShiftsDefineVo) shiftsDefineService.getShiftRecordByShiftId(jo.get("shiftId").getAsInt());
		List<ShiftsDefineVo> shiftsVos = new ArrayList<ShiftsDefineVo>();
		shiftsVos.add(vo);
		masterInfoMap.put("shiftDefineVo", shiftsVos);	
		List<SimpleObject> companyCountries = commonService.getCompanyCountries(vo.getCompanyDetailsId());
		masterInfoMap.put("countryList", companyCountries);
		List companyNames  = vendorService.getComapanyNamesAsDropDown(vo.getCustomerDetailsId()+"",vo.getCompanyDetailsId()+"");
		masterInfoMap.put("companyList", companyNames);		
		
		if(companyCountries!= null && companyCountries.size() ==1){			
			shiftsVo.setCountryId(vo.getCountryId());
			shiftsVo.setCustomerDetailsId(vo.getCustomerDetailsId());
			shiftsVo.setCompanyDetailsId(vo.getCompanyDetailsId());
			ShiftsVo vos = shiftsService.getShiftRecord(shiftsVo);
			List<ShiftsVo> shiftsVoss = new ArrayList<ShiftsVo>();
			shiftsVoss.add(vos);
			masterInfoMap.put("shiftsVo", shiftsVoss);
		}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap, HttpStatus.OK);
	}
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getCountryNamesAsDropDown.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getCountryNamesAsDropDown(@RequestBody String shiftsVoString) {
		Map<String,List> masterInfoMap = new HashMap<String,List>();
		try{
		ShiftsVo shiftsVo = new ShiftsVo();
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(shiftsVoString);	
		List<SimpleObject> companyCountries = commonService.getCompanyCountries(jo.get("companyId").getAsInt());
		masterInfoMap.put("countryList", companyCountries);
		LocationVo LocationVo = new LocationVo();
		LocationVo.setCustomerId(jo.get("customerId").getAsInt());
		LocationVo.setCompanyId(jo.get("companyId").getAsInt());
		LocationVo.setStatus("Y");
		List<LocationVo> locationList = locationService.getLocationsListBySearch(LocationVo);
		locationList.add(0, new LocationVo(0,"All"));
		masterInfoMap.put("locationList", locationList);
		
		if(companyCountries!= null && companyCountries.size() ==1){			
			shiftsVo.setCountryId(companyCountries.get(0).getId());
			shiftsVo.setCustomerDetailsId(jo.get("customerId").getAsInt());
			shiftsVo.setCompanyDetailsId(jo.get("companyId").getAsInt());
			ShiftsVo vo = shiftsService.getShiftRecord(shiftsVo);
			List<ShiftsVo> shiftsVos = new ArrayList<ShiftsVo>();
			shiftsVos.add(vo);
			masterInfoMap.put("shiftsVo", shiftsVos);
		}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/saveShiftDefineRecord.json", method = RequestMethod.POST)
	public  ResponseEntity<Integer> saveShiftDefineRecord(@RequestBody ShiftsDefineVo shiftDefineVo) {		
		Integer id = shiftsDefineService.saveShiftDefineData(shiftDefineVo);
		log.info("Record Saved Sucessfully with IDid :: "+id);
		return new ResponseEntity<Integer>(id,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getPlantsList.json", method = RequestMethod.POST)
	public  ResponseEntity<List<PlantVo>> getPlantsList(@RequestBody PlantVo paramPlant) {
		List<PlantVo> plantList = plantService.getPlantsListBySearch(paramPlant);
		plantList.add(0, new PlantVo(0, "All"));
		return new ResponseEntity<List<PlantVo>>(plantList,HttpStatus.OK);
	}
	
}
