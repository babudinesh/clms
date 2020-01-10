package com.Ntranga.CLMS.Controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Ntranga.CLMS.Service.AssociatingDepartmentToLocationPlantService;
import com.Ntranga.CLMS.Service.DepartmentService;
import com.Ntranga.CLMS.vo.AssociatingDepartmentToLocationPlantVo;
import com.Ntranga.CLMS.vo.DepartmentVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.UsersVo;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@SuppressWarnings({"rawtypes"})
@Transactional
@RestController
@RequestMapping(value = "associatingDepartmentToLocationPlantController")

public class AssociatingDepartmentToLocationPlantController {
	
	private static Logger log = Logger.getLogger(AssociatingDepartmentToLocationPlantController.class);
	
	private @Value("#{db['db.schemaName']}")
	String dbSchemaName;	

	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private AssociatingDepartmentToLocationPlantService associatingDepartmentToLocationPlantService;
	
	@Autowired
    private HttpServletRequest request;
	
	@Autowired
	private DepartmentService departmentService;
	
	
	
	@RequestMapping(value = "/saveAssociatingDepartmentToLocationPlant.json", method = RequestMethod.POST)
	public  ResponseEntity<SimpleObject> saveWorkerWithoutFiles(@RequestBody AssociatingDepartmentToLocationPlantVo deptToPlantVo ) {	
		SimpleObject object = new SimpleObject();
		try{
			boolean flag = associatingDepartmentToLocationPlantService.associatingDepartmentToLocationAndPlantSaving(deptToPlantVo);
		if(flag){
			object.setId(1);
			object.setName("Success");
		}else{
			object.setId(0);
 			object.setName("Failed ");
		}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<SimpleObject>(object,HttpStatus.OK);
	}
	
		
	@RequestMapping(value = "/getDepartMentDetailsByLocationAndPlantId.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getDepartMentDetailsByLocationAndPlantId(@RequestBody UsersVo usersVo) {					
		Map<String,List> masterInfoMap = new HashMap<String,List>();		
		//JsonParser jsonParser = new JsonParser();
		//System.out.println("jsonDetails::"+jsonDetails);
	//	JsonObject jo = (JsonObject) jsonParser.parse(jsonDetails);
		try{
			if(usersVo.getLocationArrayId() != null && usersVo.getLocationArrayId().length > 0 && usersVo.getPlantArrayId() != null && usersVo.getPlantArrayId().length > 0){
				List<DepartmentVo> departmentList = associatingDepartmentToLocationPlantService.getDeparmentNamesAsDropDown(usersVo.getCustomerId()+"", usersVo.getCompanyId()+"", Arrays.toString(usersVo.getLocationArrayId()).replace("]", "").replace("[", ""),  Arrays.toString(usersVo.getPlantArrayId()).replace("]", "").replace("[", ""), Arrays.toString(usersVo.getDepartmentArrayId()).replace("]", "").replace("[", ""));
				masterInfoMap.put("departmentList",  departmentList);
			}else if(usersVo.getLocationId() != null &&  usersVo.getPlantId()!= null ){
				List<DepartmentVo> departmentList = associatingDepartmentToLocationPlantService.getDeparmentNamesAsDropDown(usersVo.getCustomerId()+"", usersVo.getCompanyId()+"", usersVo.getLocationId(), usersVo.getPlantId(), Arrays.toString(usersVo.getDepartmentArrayId()).replace("]", "").replace("[", ""));
				masterInfoMap.put("departmentList",  departmentList);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getMasterDepartmentsList.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getMasterDepartmentsList(@RequestBody String jsonDetails) {					
		Map<String,List> masterInfoMap = new HashMap<String,List>();		
		JsonParser jsonParser = new JsonParser();
		System.out.println("jsonDetails::"+jsonDetails);
		JsonObject jo = (JsonObject) jsonParser.parse(jsonDetails);
		try{
			List<DepartmentVo> departmentList = new ArrayList<DepartmentVo>();
		  
		    	DepartmentVo dept = new DepartmentVo();
				dept.setCompanyId(jo.get("customerId").getAsInt());
				dept.setCompanyId(jo.get("companyId").getAsInt());
				
				departmentList = departmentService.getDepartmentsListBySearch(dept);
			
			masterInfoMap.put("departmentList",  departmentList);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getSectionDetailsByLocationAndPlantAndDeptId.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getSectionDetailsByLocationAndPlantAndDeptId(@RequestBody UsersVo usersVo) {					
		Map<String,List> masterInfoMap = new HashMap<String,List>();		
		//JsonParser jsonParser = new JsonParser();
		//System.out.println("jsonDetails::"+jsonDetails);
		//JsonObject jo = (JsonObject) jsonParser.parse(jsonDetails);
		try{
			if(usersVo.getLocationArrayId() != null && usersVo.getLocationArrayId().length > 0 && usersVo.getPlantArrayId() != null && usersVo.getPlantArrayId().length > 0 && usersVo.getDepartmentArrayId().length > 0){
				List<SimpleObject> sectionsList = associatingDepartmentToLocationPlantService.getSectionNamesAsDropDown(usersVo.getCustomerId()+"", usersVo.getCompanyId()+"", Arrays.toString(usersVo.getLocationArrayId()).replace("]", "").replace("[", ""), Arrays.toString(usersVo.getPlantArrayId()).replace("]", "").replace("[", ""), Arrays.toString(usersVo.getDepartmentArrayId()).replace("]", "").replace("[", ""));
				masterInfoMap.put("sectionsList",  sectionsList);
			}else if(usersVo.getLocationId() != null &&  usersVo.getPlantId()!= null && usersVo.getDepartmentId() != null ){
				List<SimpleObject> sectionsList = associatingDepartmentToLocationPlantService.getSectionNamesAsDropDown(usersVo.getCustomerId()+"", usersVo.getCompanyId()+"", usersVo.getLocationId(), usersVo.getPlantId(), usersVo.getDepartmentId());
				masterInfoMap.put("sectionsList",  sectionsList);
			}
			
			
			
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	}

	
}
