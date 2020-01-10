package com.Ntranga.CLMS.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.Ntranga.CLMS.Service.DepartmentService;
import com.Ntranga.CLMS.Service.EmployeeService;
import com.Ntranga.CLMS.Service.JobAllocationByVendorService;
import com.Ntranga.CLMS.Service.LocationService;
import com.Ntranga.CLMS.Service.ManpowerRequisitionService;
import com.Ntranga.CLMS.Service.VendorService;
import com.Ntranga.CLMS.vo.CompanyProfileVo;
import com.Ntranga.CLMS.vo.CustomerVo;
import com.Ntranga.CLMS.vo.DepartmentVo;
import com.Ntranga.CLMS.vo.EmployeeInformationVo;
import com.Ntranga.CLMS.vo.LocationVo;
import com.Ntranga.CLMS.vo.ManpowerRequisitionVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import common.Logger;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping(value="manpowerRequisitionController")
public class ManpowerRequisitionController {

	private static final Logger log = Logger.getLogger(ManpowerRequisitionController.class);
	
	@Autowired
	private ManpowerRequisitionService manpowerRequisitionService;
	
	@Autowired
	private VendorService vendorService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private JobAllocationByVendorService jobAllocationByVendorService;
	
	
	
	@Autowired
	AssociatingDepartmentToLocationPlantService associatingDepartmentToLocationPlantService;
	/*
	 * This method will be used to save or update the Man power request
	*/ 
	@RequestMapping(value = "/saveManpowerRequisition.json", method = RequestMethod.POST)
	public ResponseEntity<SimpleObject> saveManpowerRequisition(@RequestBody ManpowerRequisitionVo  paramManpower) {
		log.info("Entered in ManpowerRequisitionController saveManpowerRequisition() ::  "+paramManpower);
		
		Integer manpowerRequisitionId = null;
		SimpleObject object = new SimpleObject();
		
		try{
		
			if(paramManpower != null){
				manpowerRequisitionId = manpowerRequisitionService.saveManpowerRequisition(paramManpower);
				log.debug("Manpower Requisition Id: "+manpowerRequisitionId);
			}
			
			if (manpowerRequisitionId != null) {
				object.setId(manpowerRequisitionId);
				object.setName("Success");
			} else {
				object.setId(0);
				object.setName("Fail");
			}
		
		}catch(Exception e){
			object.setId(-1);
			log.error("Error Occured ",e);
		}
		log.info("Exiting from ManpowerRequisitionController  saveManpowerRequisition() ::  "+object.toString());
		return new ResponseEntity<SimpleObject>(object, HttpStatus.OK);
	}
	

	/*
	 * This method will be used to get the ma power requests record based on given customerId, companyId, locationId, departmentId, status and request code
	 * 																					
	 */
	@RequestMapping(value = "/searchManpowerRequisitions.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> searchManpowerRequisitions(@RequestBody ManpowerRequisitionVo paramManpower) throws JSONException {
		log.info("Entered in ManpowerRequisitionController searchManpowerRequisitions()   ::   "+paramManpower);
		Map<String,List> returnData = new HashMap<String,List>();
		
		try{
		List<ManpowerRequisitionVo> requisitionsList = manpowerRequisitionService.searchManpowerRequisitions(paramManpower);
		log.debug("List of Requests : "+requisitionsList);
		
		returnData.put("requisitionsList",requisitionsList);
		
		log.info("Exiting from ManpowerRequisitionController searchManpowerRequisitions() ::  "+returnData.toString());
		
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(returnData,HttpStatus.OK);
	}
	
	/*
	 * This method will be used to get the Manpower Requisition record based on manpowerRequisitionId
	 */	
	@RequestMapping(value = "/getManpowerRequisitionById.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getManpowerRequisitionById(@RequestBody String paramManpower) throws JSONException {
		log.info("Entered in ManpowerRequisitionController getManpowerRequisitionById()  ::   "+paramManpower);
		Map<String,List> returnList = new HashMap<String,List>();
		
		try{
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(paramManpower);
			log.debug("JSON Object:  "+jo);
			jo.addProperty("uniqueId", "1");
			String manpowerRequisitionId = null, customerId = null, uniqueId = null;
			if(jo.get("customerId") != null && jo.get("customerId").getAsString() != null){
				customerId = jo.get("customerId").getAsString(); 
				log.debug("Customer Id : "+customerId);
			}
			
			if(jo.get("uniqueId") != null && jo.get("uniqueId").getAsString() != null){
				uniqueId = jo.get("uniqueId").getAsString(); 
				log.debug("Unique Id : "+customerId);
			}
				
			if(jo.get("manpowerRequisitionId") != null && jo.get("manpowerRequisitionId").getAsString() != null){
				manpowerRequisitionId = jo.get("manpowerRequisitionId").getAsString();  
				log.debug("manpower Requisition Id : "+manpowerRequisitionId);
			}
						
		    List<ManpowerRequisitionVo> manpowerVo= manpowerRequisitionService.getManpowerRequisitionById(Integer.valueOf(manpowerRequisitionId.trim()));	
		    List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(customerId);
		    
		    List<SimpleObject> companyList = new ArrayList<>();
		    List<SimpleObject>countriesList = new ArrayList<>();
		    List<CompanyProfileVo> profileList = new ArrayList<>();
		    List<LocationVo> locationsList = new ArrayList<>();
		    List<DepartmentVo> departmentsList = new ArrayList<>();
		    List<SimpleObject> plantsList = new ArrayList<>();
		    List<EmployeeInformationVo> employeesList = new ArrayList<>();
		    List<SimpleObject> jobCodesList = new ArrayList<>();
		    List<SimpleObject> costCenterList = new ArrayList<SimpleObject>();
		    List<EmployeeInformationVo> employeeListForApproval = new ArrayList();
		    
		    if(manpowerVo != null && manpowerVo.size() > 0 ){
		    	companyList  = vendorService.getComapanyNamesAsDropDown(customerId, manpowerVo.get(0).getCompanyId()+"");		
		    	countriesList = commonService.getCompanyCountries(manpowerVo.get(0).getCompanyId());	
			    CompanyProfileVo profileVo = companyService.getCompanyProfileByCompanyId(manpowerVo.get(0).getCompanyId()+"", customerId);
			    profileList.add(profileVo);
		    
			    LocationVo paramLocation = new LocationVo();
		    	paramLocation.setCustomerId(Integer.parseInt(customerId.trim()));
		    	paramLocation.setCompanyId(manpowerVo.get(0).getCompanyId());
			    locationsList = locationService.getLocationsListBySearch(paramLocation);
				plantsList = jobAllocationByVendorService.getAllPlantsListByLocationId(manpowerVo.get(0).getLocationId()+"");
				departmentsList = associatingDepartmentToLocationPlantService.getDeparmentNamesAsDropDown(manpowerVo.get(0).getCustomerId()+"", manpowerVo.get(0).getCompanyId()+"", manpowerVo.get(0).getLocationId()+"", manpowerVo.get(0).getPlantId()+"",null);

				/*ManpowerRequisitionVo manpower = new ManpowerRequisitionVo();
				manpower.setCustomerId(manpowerVo.get(0).getCustomerId());
				manpower.setCompanyId(manpowerVo.get(0).getCompanyId());
				//manpower.setLocationId(manpowerVo.get(0).getLocationId());
				//manpower.setDepartmentId(manpowerVo.get(0).getDepartmentId());
				manpower.setEmployeeId(manpowerVo.get(0).getEmployeeId());
				// employees list based on department
				//employeesList = manpowerRequisitionService.getEmployeesDetails(manpowerVo.get(0));
				employeesList = manpowerRequisitionService.getEmployeesDetails(uniqueId);*/
				jobCodesList = manpowerRequisitionService.getJobCodeListBySkillType(manpowerVo.get(0));
				
				// employees list based on company
				ManpowerRequisitionVo manpower = new ManpowerRequisitionVo();
			    manpower.setEmployeeId(manpowerVo.get(0).getEmployeeId());
			    employeesList = manpowerRequisitionService.getEmployeesDetails(manpower);
			    
				
				//employeeListForApproval = employeeService.getEmployeesList(manpowerVo.get(0).getCustomerId(), manpowerVo.get(0).getCompanyId());
				costCenterList = departmentService.getDepartmentNameForCostCenter(manpowerVo.get(0).getCustomerId(), manpowerVo.get(0).getCompanyId() );
				
				//employeesList for approved by 
				manpower = new ManpowerRequisitionVo();
			    manpower.setEmployeeId(Integer.parseInt(uniqueId.trim()));
				employeeListForApproval = manpowerRequisitionService.getEmployeesDetails(manpower);
		    }else{
		    	ManpowerRequisitionVo manpower = new ManpowerRequisitionVo();
			    manpower.setEmployeeId(Integer.parseInt(uniqueId.trim()));
			    employeesList = manpowerRequisitionService.getEmployeesDetails(manpower);
		    }
		    
		    //employeesList = manpowerRequisitionService.getEmployeesDetails(manpower);
			returnList.put("manpowerVo",manpowerVo);
			returnList.put("customerList", customerList);
			returnList.put("companyList", companyList);
			returnList.put("countriesList", countriesList);
			returnList.put("locationsList", locationsList);
			returnList.put("departmentsList", departmentsList);
			returnList.put("plantsList", plantsList);
			returnList.put("companyList", companyList);
			returnList.put("employeesList", employeesList);
			returnList.put("jobCodesList", jobCodesList);
			returnList.put("costCenterList", costCenterList);
			returnList.put("defaultCurrency", profileList);
			returnList.put("employeeListForApproval", employeeListForApproval);
			
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		
		log.info("Exiting from ManpowerRequisitionController getManpowerRequisitionById() ::  "+returnList);
		return new ResponseEntity<Map<String,List>>(returnList, HttpStatus.OK);
	}
	
	/*
	 * This service is used to validate the Request Id that already exists or not based on customer and company
	 */
	@RequestMapping(value = "/validateRequestCode.json", method = RequestMethod.POST)
	public String validateRequestCode(@RequestBody ManpowerRequisitionVo paramManpower) throws JSONException {
		log.info("Entered in ManpowerRequisitionController validateRequestCode() ::  "+paramManpower);
		String returnString = null;
		List<ManpowerRequisitionVo> manPowerList = manpowerRequisitionService.searchManpowerRequisitions(paramManpower);
		log.debug("List of Request Codes : "+manPowerList);
		if(manPowerList != null && manPowerList.size() > 0){
			returnString ="0";
		}else {
			returnString = "1";
		}
		
		log.info("Exiting from ManpowerRequisitionController validateRequestCode() ::  "+returnString);
		return returnString;
	}
	
	/*
	 * This service is used to get the employees List based on given customer, company, location and department
	 */
	@RequestMapping(value = "/getEmployeesList.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getEmployeesListByDepartmentId(@RequestBody ManpowerRequisitionVo paramManpower) throws JSONException {
		log.info("Entered in ManpowerRequisitionController getEmployeesListByDepartmentId() ::  "+paramManpower);
		Map<String,List> returnList = new HashMap<String,List>();
		
		List<EmployeeInformationVo> employeesList = manpowerRequisitionService.getEmployeesDetails(paramManpower);
		
		
		returnList.put("employeesList", employeesList);
		log.info("Exiting from ManpowerRequisitionController getEmployeesListByDepartmentId() ::  "+returnList);
		return new ResponseEntity<Map<String,List>>(returnList,HttpStatus.OK);
	}
	
	/*
	 * This service is used to get the employees List based on given customer, company, location and department
	 */
	@RequestMapping(value = "/getEmployeeDetails.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getEmployeeDetailsByEmployeeUniqueId(@RequestBody ManpowerRequisitionVo paramManpower) throws JSONException {
		log.info("Entered in ManpowerRequisitionController getEmployeeDetailsByEmployeeUniqueId() ::  "+paramManpower);
		Map<String,List> returnList = new HashMap<String,List>();
		
		List<EmployeeInformationVo> employeesList = manpowerRequisitionService.getEmployeesDetails(paramManpower);
		
		
		returnList.put("employeeDetails", employeesList);
		log.info("Exiting from ManpowerRequisitionController getEmployeeDetailsByEmployeeUniqueId() ::  "+returnList);
		return new ResponseEntity<Map<String,List>>(returnList,HttpStatus.OK);
	}
	
	/*
	 * This service is used to get the jobcodes List based on given customer, company and skill type
	 */
	@RequestMapping(value = "/getJobCodeListBySkillType.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getJobCodeListBySkillType(@RequestBody ManpowerRequisitionVo paramManpower) throws JSONException {
		log.info("Entered in ManpowerRequisitionController getJobCodeListBySkillType() ::  "+paramManpower);
		Map<String,List> returnList = new HashMap<String,List>();
		
		List<SimpleObject> jobCodesList = manpowerRequisitionService.getJobCodeListBySkillType(paramManpower);
		returnList.put("jobCodesList", jobCodesList);
		
		List<SimpleObject> assignedWorkersCount = manpowerRequisitionService.getAssignedWorkersCountBySkillType(paramManpower);
		returnList.put("assignedWorkersCount",assignedWorkersCount);
		
		log.info("Exiting from ManpowerRequisitionController getJobCodeListBySkillType() ::  "+returnList);
		return new ResponseEntity<Map<String,List>>(returnList,HttpStatus.OK);
	}
}
