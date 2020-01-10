package com.Ntranga.CLMS.Controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Ntranga.CLMS.Service.DashboardService;
import com.Ntranga.CLMS.loginService.LoginService;
import com.Ntranga.CLMS.vo.DashboardChartVo;
import com.Ntranga.CLMS.vo.DashboardComplianceStatusVo;
import com.Ntranga.CLMS.vo.DashboardVo;

@SuppressWarnings({"rawtypes","unchecked"})
@RestController
@RequestMapping(value = "dashboardController")
public class DashboardController {
	
	private static Logger log = Logger.getLogger(DashboardController.class);
	
	@Autowired
	private DashboardService dashboardService;
	
	@Autowired
	private LoginService loginService;
	
	private @Value("#{db['db.schemaName']}")
	String dbSchemaName;
	
	
	@RequestMapping(value = "/getDashboardDetails.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getVendorNamesAsDropDown(@RequestBody String JsonString) {
		Map<String,List> masterInfoMap = new HashMap<String,List>();	
		try{
		masterInfoMap  = dashboardService.getDashboardDetails(JsonString);						
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getWorkerAgeGroup.json", method = RequestMethod.POST)
    public ResponseEntity<Map<String,List>> getWorkersAgeGroupCount(@RequestBody DashboardVo paramDashboard){
		log.info("Entered in dashboard controller in getWorkersAgeGroupCount()  ::  "+paramDashboard);
		Map<String,List> masterInfoMap = new HashMap<String,List>();
		int count = 0;
		
		String schemaName = loginService.getSchemaNameByUserId(paramDashboard.getUserId());	
		if(schemaName == null || schemaName.isEmpty() ){
			schemaName = dbSchemaName;
		}
		paramDashboard.setSchemaName(schemaName);
		List<Object> ageGroupList = dashboardService.getWorkersAgeGroupCount(paramDashboard);
		
		if(ageGroupList != null ){
			for(Object o : ageGroupList){
				Object[] obj = (Object[]) o;
				count = count + ((BigInteger)obj[1]).intValue();
			}
			//System.out.println(count);
		}
		
		List totalWorkerCount = new ArrayList();
		totalWorkerCount.add(count);
		
		masterInfoMap.put("workerCountByAgeGroup", ageGroupList );
		masterInfoMap.put("totalWorkerCount", totalWorkerCount);
		
		log.info("Exiting from dashboard controller in getWorkersAgeGroupCount()  ::  "+masterInfoMap);
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
    }
	
	/*@RequestMapping(value = "/getWorkerAgeGroup.json", method = RequestMethod.POST)
	public ResponseEntity<List<Object>> getWorkersAgeGroupCount(@RequestBody DashboardVo paramDashboard){
		log.info("Entered in dashboardController getWorkersAgeGroupCount()    ::   "+paramDashboard);
		List<Object> returnData = null;
		//String str = null;
	
		returnData  = dashboardService.getWorkersAgeGroupCount(paramDashboard);	
		//str = JSONObject.valueToString(returnString);
		//System.out.println(returnString);
		
		log.info("Exiting from dashboardController getWorkerAgeGroup()    ::   "+returnData);
		return new ResponseEntity<List<Object>>(returnData,HttpStatus.OK);
	}*/
	
	@RequestMapping(value = "/getWorkersOvertime.json", method = RequestMethod.POST)
	public ResponseEntity<DashboardChartVo>  getOvertimeDetails(@RequestBody DashboardVo paramDashboard){
		log.info("Entered in dashboardController getOvertimeDetails()    ::   "+paramDashboard);
		DashboardChartVo returnData = null;
		String schemaName = null; 
		Date start = new Date();
		try{
			schemaName = loginService.getSchemaNameByUserId(paramDashboard.getUserId());	
			if(schemaName == null || schemaName.isEmpty() ){
				schemaName = dbSchemaName;
			}
			paramDashboard.setSchemaName(schemaName);
			
			//System.out.println(schemaName);
			returnData  = dashboardService.getOvertimeDetails(paramDashboard);	
			log.info("Return Data From Controller (getOvertimeDetails) : "+returnData);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		log.info("Exiting from dashboardController getOvertimeDetails()    ::   "+returnData+"Start Time : "+start+"::::  End :"+new Date());
		return new ResponseEntity<DashboardChartVo>(returnData,HttpStatus.OK);
	}
	
	
/*	@RequestMapping(value = "/getWorkerAgeGroup.json", method = RequestMethod.POST)
    public ResponseEntity<Map<String,List>> getWorkersAgeGroupCount(@RequestBody DashboardVo paramDashboard){
		Map<String,List> masterInfoMap = new HashMap<String,List>();
		masterInfoMap.put("workerCountByAgeGroup", dashboardService.getWorkersAgeGroupCount(paramDashboard));
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
    }

	*/
	

	@RequestMapping(value = "/getWorkersHeadCount.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String,List>> getWorkersHeadCount(@RequestBody DashboardVo paramDashboard){
		log.info("Entered in dashboardController getWorkersStatus()    ::   "+paramDashboard);
		Map<String,List> masterInfoMap = new HashMap<String,List>();
		masterInfoMap.put("workerInfo", dashboardService.getWorkersHeadCount(paramDashboard.getCustomerId(), paramDashboard.getCompanyId(), paramDashboard.getVendorId()));
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);

		/*
		String returnString = null;
		List<DashboardWorkersInfoVo> returnData = null;
		String schemaName = null;
		
		try{
			schemaName = loginService.getSchemaNameByUserId(paramDashboard.getUserId());	
			if(schemaName == null || schemaName.isEmpty() ){
				schemaName = dbSchemaName;
			}
			paramDashboard.setSchemaName(schemaName);
			
			returnData = dashboardService.getWorkersStatusCount(paramDashboard.getCustomerId(), paramDashboard.getCompanyId(), paramDashboard.getVendorId());	
			//returnString = JSONObject.valueToString(returnData);
			log.info("Return Data From Controller (getWorkersStatus) : "+returnData);
			
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		log.info("Exiting from dashboardController getWorkersStatus()    ::   "+returnData);
		return new ResponseEntity<List<DashboardWorkersInfoVo>>(returnData,HttpStatus.OK);*/
	}
	
	@RequestMapping(value = "/getShiftWiseAttendanceDetails.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String, List>>  getShiftWiseAttendanceDetails(@RequestBody DashboardVo paramDashboard){
		log.info("Entered in dashboardController getShiftWiseAttendanceDetails()    ::   "+paramDashboard);
		//DashboardChartVo returnData = null;
		Map<String,List> masterInfoMap = new HashMap<String,List>();
		String schemaName = null;
		
		try{
			schemaName = loginService.getSchemaNameByUserId(paramDashboard.getUserId());	
			if(schemaName == null || schemaName.isEmpty() ){
				schemaName = dbSchemaName;
			}
			paramDashboard.setSchemaName(schemaName);
			
			//returnData  = dashboardService.getShiftWiseAttendanceDetails(paramDashboard);	
			masterInfoMap.put("shiftWiseAttendance", dashboardService.getShiftWiseAttendanceDetails(paramDashboard));
			log.info("Return Data From Controller (Shift wise) : "+masterInfoMap.toString());
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		log.info("Exiting from dashboardController getShiftWiseAttendanceDetails()    ::   "+masterInfoMap.toString());
		return new ResponseEntity<Map<String, List>>(masterInfoMap,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getSkillWiseAttendanceDetails.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String, List>>  getSkillWiseAttendanceDetails(@RequestBody DashboardVo paramDashboard){
		log.info("Entered in dashboardController getSkillWiseAttendanceDetails()    ::   "+paramDashboard);
		//DashboardChartVo returnData = null;
		Map<String,List> masterInfoMap = new HashMap<String,List>();
		String schemaName = null;
		
		try{
			schemaName = loginService.getSchemaNameByUserId(paramDashboard.getUserId());	
			if(schemaName == null || schemaName.isEmpty() ){
				schemaName = dbSchemaName;
			}
			paramDashboard.setSchemaName(schemaName);
			
			//returnData  = dashboardService.getSkillWiseAttendanceDetails(paramDashboard);	
			masterInfoMap.put("skillWiseAttendance", dashboardService.getSkillWiseAttendanceDetails(paramDashboard));
			log.info("Return Data From Controller (Skill wise) : "+masterInfoMap.toString());
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		log.info("Exiting from dashboardController getSkillWiseAttendanceDetails()    ::   "+masterInfoMap.toString());
		return new ResponseEntity<Map<String, List>>(masterInfoMap,HttpStatus.OK);
	}

	@RequestMapping(value = "/getVendorWiseAttendanceDetails.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String, List>>  getVendorWiseAttendanceDetails(@RequestBody DashboardVo paramDashboard){
		log.info("Entered in dashboardController getVendorWiseAttendanceDetails()    ::   "+paramDashboard);
		Map<String,List> masterInfoMap = new HashMap<String,List>();
		
		String schemaName = loginService.getSchemaNameByUserId(paramDashboard.getUserId());	
		if(schemaName == null || schemaName.isEmpty() ){
			schemaName = dbSchemaName;
		}
		paramDashboard.setSchemaName(schemaName);
		
		masterInfoMap.put("vendorWiseAttendance", dashboardService.getVendorWiseAttendanceDetails(paramDashboard));
		log.info("Exiting from controller  getVendorWiseAttendanceDetails ()  ::  "+masterInfoMap.toString());
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getVendorComplianceStatus.json", method = RequestMethod.POST)
	public ResponseEntity<List<DashboardComplianceStatusVo>>  getVendorComplianceStatus(@RequestBody DashboardVo paramDashboard){
		log.info("Entered in dashboardController getVendorWiseAttendanceDetails()    ::   "+paramDashboard);
		List<DashboardComplianceStatusVo> returnData = null;
		String schemaName = null;
		
		try{
			schemaName = loginService.getSchemaNameByUserId(paramDashboard.getUserId());	
			if(schemaName == null || schemaName.isEmpty() ){
				schemaName = dbSchemaName;
			}
			paramDashboard.setSchemaName(schemaName);
			
			returnData  = dashboardService.getVendorComplianceStatus(paramDashboard);	
			log.info("Return Data From Controller (getVendorComplianceStatus) : "+returnData);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		log.info("Exiting from dashboardController getVendorComplianceStatus()    ::   "+returnData);
		return new ResponseEntity<List<DashboardComplianceStatusVo>>(returnData,HttpStatus.OK);
	}
	
	/*@RequestMapping(value = "/getShiftAttendanceDetails.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String, List>>  getShiftAttendanceDetails(@RequestBody DashboardVo paramDashboard){
		log.info("Entered in dashboardController getShiftWiseAttendanceDetails()    ::   "+paramDashboard);
		DashboardChartVo returnData = null;
		Map<String,List> masterInfoMap = new HashMap<String,List>();
		String schemaName = null;
		
		try{
			schemaName = loginService.getSchemaNameByUserId(paramDashboard.getUserId());	
			if(schemaName == null || schemaName.isEmpty() ){
				schemaName = dbSchemaName;
			}
			paramDashboard.setSchemaName(schemaName);
			
			//returnData  = dashboardService.getShiftWiseAttendanceDetails(paramDashboard);	
			masterInfoMap.put("shiftWisePresentList", dashboardService.getShiftWisePresentDetails(paramDashboard));
			masterInfoMap.put("shiftWiseScheduledList", dashboardService.getShiftWiseScheduledDetails(paramDashboard));
			
			log.info("Return Data From Dashboard Controller (getShiftAttendanceDetails()) : "+masterInfoMap.toString());
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		log.info("Exiting from dashboardController getShiftAttendanceDetails()    ::   "+masterInfoMap.toString());
		return new ResponseEntity<Map<String, List>>(masterInfoMap,HttpStatus.OK);
	}*/
	
	@RequestMapping(value = "/getDeptWiseAttendanceDetails.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String, List>>  getDeptWiseAttendanceDetails(@RequestBody DashboardVo paramDashboard){
		log.info("Entered in dashboardController getDeptWiseAttendanceDetails()    ::   "+paramDashboard);
		Map<String,List> masterInfoMap = new HashMap<String,List>();
		String schemaName = null;
		
		try{
			schemaName = loginService.getSchemaNameByUserId(paramDashboard.getUserId());	
			if(schemaName == null || schemaName.isEmpty() ){
				schemaName = dbSchemaName;
			}
			paramDashboard.setSchemaName(schemaName);
			
			masterInfoMap.put("deptWiseAttendance", dashboardService.getDepartmentWiseAttendanceDetails(paramDashboard));
			log.info("Return Data From Controller (Department wise) : "+masterInfoMap.toString());
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		log.info("Exiting from dashboardController getDeptWiseAttendanceDetails()    ::   "+masterInfoMap.toString());
		return new ResponseEntity<Map<String, List>>(masterInfoMap,HttpStatus.OK);
	}
}
