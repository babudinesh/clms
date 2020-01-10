package com.Ntranga.CLMS.Controller;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.Ntranga.CLMS.Service.VendorService;
import com.Ntranga.CLMS.Service.WorkerBadgeGenerationService;
import com.Ntranga.CLMS.Service.WorkerService;
import com.Ntranga.CLMS.vo.CustomerVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.VendorBankDetailsVo;
import com.Ntranga.CLMS.vo.WorkerBadgeGenerationVo;

@RestController
@RequestMapping("workerBadgeGenerationController")
public class WorkerBadgeGenerationController {

	private static Logger log = Logger.getLogger(WorkerBadgeGenerationController.class);
	
	
	@Autowired
	private WorkerBadgeGenerationService workerBadgeGenerationService;
	@Autowired
	private VendorService vendorService;

	@Autowired
	private CommonService commonService;
	
	@Autowired
	private WorkerService workerService;
	
	
	@RequestMapping(value = "/getWorkersByVendorId.json", method = RequestMethod.POST)
	public  ResponseEntity<List<SimpleObject>> getWorkersByVendorId(@RequestBody WorkerBadgeGenerationVo workerBadgeVo) {
		List<SimpleObject> workerList = new ArrayList();
		try{
		 workerList  = workerBadgeGenerationService.getWorkersByVendorId(workerBadgeVo);		
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<SimpleObject>>(workerList,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getWorkerDetailsForGrid.json", method = RequestMethod.POST)
	public  ResponseEntity<List<WorkerBadgeGenerationVo>> getWorkerDetailsForGrid(@RequestBody WorkerBadgeGenerationVo workerBadgeVo) {	
		List<WorkerBadgeGenerationVo> workerList = new ArrayList();
		try{
		 workerList  = workerBadgeGenerationService.getWorkerDetailsForGrid(workerBadgeVo);
		}catch(Exception e){
			log.error("Error Occured ",e);
			}
		
		return new ResponseEntity<List<WorkerBadgeGenerationVo>>(workerList,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/saveOrUpdateWorkerBadge.json", method = RequestMethod.POST)
	public  ResponseEntity<Integer> saveOrUpdateWorkerBadge(@RequestBody WorkerBadgeGenerationVo workerBadgeVo) {	
		Integer badgeId = 0;
		try{
		 badgeId  = workerBadgeGenerationService.saveOrUpdateWorkerBadge(workerBadgeVo);		
		}catch(Exception e){
			log.error("Error Occured ",e);
			}
		return new ResponseEntity<Integer>(badgeId,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getWorkerBadgeRecordByBadgeId.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,List>> getWorkerBadgeRecordByBadgeId(@RequestBody WorkerBadgeGenerationVo workerBadgeVo) {
		Map<String,List> map = new HashMap<String, List>();
		List<CustomerVo> customerList = new ArrayList();
		String locationId = null;
		try{
		 customerList = vendorService.getCustomerNamesAsDropDown(workerBadgeVo.getCustomerDetailsId()+"");					
		map.put("customerList", customerList);
		if(workerBadgeVo != null && workerBadgeVo.getLocationId().length > 0)
			locationId = Arrays.toString(workerBadgeVo.getLocationId()).replace("[", "").replace("]", "");
		if(workerBadgeVo != null && workerBadgeVo.getWorkerBadgeId() != null  && workerBadgeVo.getWorkerBadgeId() > 0 ){
			List<WorkerBadgeGenerationVo> badgeList  = workerBadgeGenerationService.getWorkerBadgeRecordByBadgeId(workerBadgeVo);	
			map.put("badgeList", badgeList);
			List<SimpleObject> vendorList  = workerService.getVendorNamesAsDropDown((badgeList.get(0).getCustomerDetailsId())+"", (badgeList.get(0).getCompanyDetailsId())+"",(badgeList.get(0).getVendorDetailsId())+"", "Validated",locationId);
			List<SimpleObject> countriesList = commonService.getCompanyCountries(badgeList.get(0).getCompanyDetailsId());
			WorkerBadgeGenerationVo workerBadgeVos = badgeList.get(0);
			List<SimpleObject> workerList  = workerBadgeGenerationService.getWorkersByVendorId(workerBadgeVos);
			List<SimpleObject> companyList  = vendorService.getComapanyNamesAsDropDown((badgeList.get(0).getCustomerDetailsId())+"",(badgeList.get(0).getCompanyDetailsId())+"");
			map.put("workerList", workerList);
			map.put("companyList", companyList);
			map.put("countriesList", countriesList);
			map.put("vendorList", vendorList);
		}		
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		
		return new ResponseEntity<Map<String,List>>(map,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/validateVendorBadgeCode.json", method = RequestMethod.POST)
	public  boolean validateVendorBankCode(@RequestBody WorkerBadgeGenerationVo paramVendor) {		
		boolean flag = workerBadgeGenerationService.validateVendorBadgeCode(paramVendor);
		return flag;
	}
	
	
}
