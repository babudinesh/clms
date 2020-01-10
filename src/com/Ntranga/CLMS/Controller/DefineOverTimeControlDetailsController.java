package com.Ntranga.CLMS.Controller;

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
import com.Ntranga.CLMS.Service.DefineOverTimeControlDetailsService;
import com.Ntranga.CLMS.Service.VendorService;
import com.Ntranga.CLMS.vo.CustomerVo;
import com.Ntranga.CLMS.vo.DefineOverTimeControlDetailsInfoVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@RestController
@RequestMapping("defineOverTimeControlDetailsController")
public class DefineOverTimeControlDetailsController {

	private static final Logger log = Logger.getLogger(DefineOverTimeControlDetailsController.class);
	
	
	@Autowired
	private DefineOverTimeControlDetailsService defineOverTimeControlDetailsService;
	@Autowired
	private VendorService vendorService;

	@Autowired
	private CommonService commonService;
	
	@RequestMapping(value = "/getOverTimeDetailsForGrid.json", method = RequestMethod.POST)
	public  ResponseEntity<List<DefineOverTimeControlDetailsInfoVo>> getOverTimeDetailsForGrid(@RequestBody DefineOverTimeControlDetailsInfoVo overTimeVo) {		
		List<DefineOverTimeControlDetailsInfoVo> workerList  = defineOverTimeControlDetailsService.getOverTimeDetailsForGrid(overTimeVo);		
		return new ResponseEntity<List<DefineOverTimeControlDetailsInfoVo>>(workerList,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/saveOrUpdateOverTime.json", method = RequestMethod.POST)
	public  ResponseEntity<Integer> saveOrUpdateOverTime(@RequestBody DefineOverTimeControlDetailsInfoVo overTimeVo) {		
		Integer badgeId  = defineOverTimeControlDetailsService.saveOrUpdateOverTime(overTimeVo);		
		return new ResponseEntity<Integer>(badgeId,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getWorkerBadgeRecordByBadgeId.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getWorkerBadgeRecordByBadgeId(@RequestBody DefineOverTimeControlDetailsInfoVo overTimeVo) {
		Map<String,List> map = new HashMap<String, List>();
		try{
		String customerId = "";
		if(overTimeVo.getCustomerDetailsId()+"" != null){
			customerId = overTimeVo.getCustomerDetailsId()+"";
		}
		List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(customerId);					
		map.put("customerList", customerList);
		
		
		if(overTimeVo != null && overTimeVo.getDefineOverTimeDetailsInfoId() != null  && overTimeVo.getDefineOverTimeDetailsInfoId() > 0 ){
			List<DefineOverTimeControlDetailsInfoVo> overTimeList  = defineOverTimeControlDetailsService.getDefineOverTimeDetailsByInfoId(overTimeVo);	
			map.put("overTimeList", overTimeList);
			List<SimpleObject> overTimeNames  = defineOverTimeControlDetailsService.getOverTimeNamesByCompanyId(overTimeList.get(0).getCustomerDetailsId(), overTimeList.get(0).getCompanyDetailsId());
			List<SimpleObject> countriesList = commonService.getCompanyCountries(overTimeList.get(0).getCompanyDetailsId());
			DefineOverTimeControlDetailsInfoVo overTimeVos = overTimeList.get(0);
			List<SimpleObject> companyList  = vendorService.getComapanyNamesAsDropDown(overTimeList.get(0).getCustomerDetailsId()+"",overTimeList.get(0).getCompanyDetailsId()+"");
			map.put("companyList", companyList);
			map.put("countriesList", countriesList);
			map.put("overTimeNamesList", overTimeNames);
		}				
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(map,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getOverTimeNamesByCompanyId.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getOverTimeNamesByCompanyId(@RequestBody DefineOverTimeControlDetailsInfoVo overTimeVo) {		
		Map<String,List> masterInfoMap = new HashMap<String,List>();	
		try{
		List<SimpleObject> overTimeNames  = defineOverTimeControlDetailsService.getOverTimeNamesByCompanyId(overTimeVo.getCustomerDetailsId(), overTimeVo.getCompanyDetailsId());
		List<SimpleObject> countriesList = commonService.getCompanyCountries(overTimeVo.getCompanyDetailsId());
		
		masterInfoMap.put("countriesList", countriesList);
		masterInfoMap.put("overTimeNamesList", overTimeNames);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,List>>(masterInfoMap,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getTransactionDatesListForOverTimeHistory.json", method = RequestMethod.POST)
	public  ResponseEntity<List<SimpleObject>> getTransactionDatesListForOverTimeHistory(@RequestBody DefineOverTimeControlDetailsInfoVo overTimeVo) {		
		List<SimpleObject> objects   = defineOverTimeControlDetailsService.getTransactionDatesListForOverTimeHistory(overTimeVo);		
		return new ResponseEntity<List<SimpleObject>>(objects,HttpStatus.OK);
	}
	
}
