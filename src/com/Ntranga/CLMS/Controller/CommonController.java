package com.Ntranga.CLMS.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Ntranga.CLMS.Service.AssociatingDepartmentToLocationPlantService;
import com.Ntranga.CLMS.Service.CommonService;
import com.Ntranga.CLMS.Service.EmployeeService;
import com.Ntranga.CLMS.Service.JobAllocationByVendorService;
import com.Ntranga.CLMS.vo.DepartmentVo;
import com.Ntranga.CLMS.vo.ScreenButtonsVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.UsersVo;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@RestController
@RequestMapping(value = "CommonController")
public class CommonController {

	@Autowired
	private CommonService commonService;
	
	private static Logger log = Logger.getLogger(CommonController.class);
	@Autowired
	private AssociatingDepartmentToLocationPlantService associatingDepartmentToLocationPlantService;
	
	@Autowired
	private JobAllocationByVendorService jobAllocationByVendorService;
	
	@Autowired
	private EmployeeService employeeService;
	
	
	// To get all countries in JSON Format
	@RequestMapping(value = "/countriesList.json", method = RequestMethod.GET)
	public ResponseEntity<List<SimpleObject>> getCountriesList() {		
		log.info("Entered in Common Controller getCountriesList()");		
		List<SimpleObject> countryList = commonService.getCountriesList();
		return new ResponseEntity<List<SimpleObject>>(countryList,HttpStatus.OK);

	}
	
	@RequestMapping(value = "/statesListByCountryId.json", method = RequestMethod.POST)
	public ResponseEntity<List<SimpleObject>> statesListByCountryId(@RequestBody String countryId) {		
		log.info("Entered in Common Controller statesListByCountryId() countryId: "+countryId);	
		List<SimpleObject> statelist = new ArrayList();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(countryId);
		log.debug("JSON Object : "+jo);
		 statelist = commonService.getStatesList(jo.get("countryId").getAsInt());
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>>(statelist,HttpStatus.OK);

	}

	@RequestMapping(value = "/cityLisyByStateId.json", method = RequestMethod.POST)
	public ResponseEntity<List<SimpleObject>> cityLisyByStateId(@RequestBody String stateId) {	
		log.info("Entered in Common Controller statesListByCountryId() stateId: "+stateId);	
		List<SimpleObject> cityList = new ArrayList();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(stateId);
		log.debug("JSON Object : "+jo);		
		//List<SimpleObject> cityList = commonService.getCitiesList(Integer.valueOf(data));		
		 cityList = commonService.getCitiesList(jo.get("stateId").getAsInt());
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>>(cityList,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getCitiesArrayByStateId.json", method = RequestMethod.POST)
	public ResponseEntity<List<String>> getCitiesArrayByStateId(@RequestBody String stateId) {	
		log.info("Entered in Common Controller statesListByCountryId() stateId: "+stateId);	
		List<String> citinames = new ArrayList<String>();
		try{
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(stateId);
		log.debug("JSON Object : "+jo);		
		//List<SimpleObject> cityList = commonService.getCitiesList(Integer.valueOf(data));		
		List<SimpleObject> cityList = commonService.getCitiesList(jo.get("stateId").getAsInt());
		
		for(SimpleObject object : cityList){
			citinames.add(object.getName());
		}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<String>>(citinames,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getUserScreenButtons.json", method = RequestMethod.POST)
	public ResponseEntity<ScreenButtonsVo> getUserScreenButtons(@RequestBody ScreenButtonsVo screenButtonsVo) {	
		ScreenButtonsVo buttonsVo = new ScreenButtonsVo();
		try{
			buttonsVo = commonService.getUserScreenButtons(screenButtonsVo);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<ScreenButtonsVo>(buttonsVo,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getRoles.json", method = RequestMethod.POST)
	public ResponseEntity<List<SimpleObject>> getRoles(@RequestBody String customerDtls) {	
		log.info("Entered in Common Controller getRoles() : ");	
		List<SimpleObject> roleList = new ArrayList<SimpleObject>();
		try{
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(customerDtls);
			roleList = commonService.getRoles(jo.get("customerId").getAsInt(), jo.get("companyId").getAsInt());
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>>(roleList,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getUsersByRoleId.json", method = RequestMethod.POST)
	public ResponseEntity<List<SimpleObject>> getUsersByRoleId(@RequestBody String roleId) {	
		log.info("Entered in Common Controller getUsersByRoleId() roleId: "+roleId);	
		List<SimpleObject> userList = new ArrayList();
		try{
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(roleId);
			log.debug("JSON Object : "+jo);		
			userList = commonService.getUsersByRoleId(jo.get("roleId").getAsInt());
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>>(userList,HttpStatus.OK);
	}
	
	
	
	
	// for getting Locations by company Id 
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getLocationsByCompanyId.json", method = RequestMethod.POST)
	public ResponseEntity<List<SimpleObject>> getLocationsByCompanyId(@RequestBody  UsersVo usrVo) {
		List<SimpleObject>  locationList = new ArrayList<SimpleObject>();
		try {	
				locationList = commonService.getLocationsByCompanyId(usrVo);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error Occured. ",e);			
		}
		
		return new ResponseEntity< List<SimpleObject>>(locationList, HttpStatus.OK);
	}
	
	// for getting Locations by vendor Id 
	
	@RequestMapping(value = "/getLocationsByVendorId.json", method = RequestMethod.POST)
	public ResponseEntity<List<SimpleObject>> getLocationsByVendorId(@RequestBody  UsersVo usrVo) {
		List<SimpleObject>  locationList = new ArrayList<SimpleObject>();
		try {	
				locationList = commonService.getLocationsByVendorId(usrVo);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error Occured. ",e);			
		}
		
		return new ResponseEntity< List<SimpleObject>>(locationList, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/getPlantsByLocationId.json", method = RequestMethod.POST)
	public ResponseEntity<List<SimpleObject>>  getPlantsByLocationId(@RequestBody UsersVo usrVo) throws Exception {
		
		List<SimpleObject> plantsList = new ArrayList<SimpleObject>();
		try{
			if(usrVo.getLocationArrayId() != null && usrVo.getLocationArrayId().length > 0){
				plantsList = commonService.getPlantsByLocationId(usrVo);
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>>(plantsList,HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/getDepartmentsByLocationAndPlant.json", method = RequestMethod.POST)
	public ResponseEntity<List<SimpleObject>>  getDepartmentsByLocationAndPlant(@RequestBody UsersVo usrVo) throws Exception {
		List<SimpleObject> departmentsList = new ArrayList<SimpleObject>();
		try{
			 departmentsList = commonService.getDepartmentsByLocationAndPlant(usrVo);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>>(departmentsList,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getSectionDetailsByLocationAndPlantAndDeptId.json", method = RequestMethod.POST)
	public ResponseEntity<List<SimpleObject>> getSectionDetailsByLocationAndPlantAndDeptId(@RequestBody UsersVo usrVo) {					
		List<SimpleObject> list  = new ArrayList<SimpleObject>();		
		try{
				list = commonService.getSectionDetailsByLocationAndPlantAndDeptId(usrVo);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>>(list,HttpStatus.OK);
	}
	
	@RequestMapping(value="/getWorkAreasBySectionesAndLocationAndPlantAndDept.json", method = RequestMethod.POST)
	public ResponseEntity<List<SimpleObject>>  getAllWorkAreasBySectionesAndLocationAndPlant(@RequestBody UsersVo usrVo) throws Exception {
		List<SimpleObject> workAreasList = new ArrayList<SimpleObject>();
		try{
				workAreasList = commonService.getWorkAreasBySectionesAndLocationAndPlantAndDept(usrVo);	
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>>(workAreasList,HttpStatus.OK);
	}
	
}
