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

import com.Ntranga.CLMS.Service.ApprovalPathModuleTransactionService;
import com.Ntranga.CLMS.Service.VendorService;
import com.Ntranga.CLMS.vo.ApprovalPathModuleVo;
import com.Ntranga.CLMS.vo.CustomerVo;
import com.Ntranga.CLMS.vo.SimpleObject;

@RestController
@RequestMapping(value = "approvalPathModuleControler")
public class ApprovalPathModuleControler {
	
	private static Logger log = Logger.getLogger(ApprovalPathModuleControler.class);

	
	@Autowired
	private ApprovalPathModuleTransactionService approvalPathModuleTransactionService;
	
	@Autowired
	private VendorService vendorService;

	
	@RequestMapping(value = "/getMaterDataForApprovalPathForModule.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,Object>> getMaterDataForRole(@RequestBody ApprovalPathModuleVo approvalPathModuleVo ) {
		Map<String,Object> returnList = new HashMap<String,Object>();	
		try{
			List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(approvalPathModuleVo.getCustomerId()+"");
			returnList.put("customerList", customerList);
			List<SimpleObject> companyList = vendorService.getComapanyNamesAsDropDown(approvalPathModuleVo.getCustomerId()+"",approvalPathModuleVo.getCompanyId()+"");
			returnList.put("companyList", companyList);
			if(approvalPathModuleVo.getApprovalPathModuleId() != null && approvalPathModuleVo.getApprovalPathModuleId() >0){
				List tempList = approvalPathModuleTransactionService.getApprovalPathModules(approvalPathModuleVo);
				if(tempList.size() > 0)
				returnList.put("module", tempList.get(0));
			}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,Object>>(returnList,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getApprovalPathModules.json", method = RequestMethod.POST)
	public  ResponseEntity<List<ApprovalPathModuleVo>> getApprovalPathModules(@RequestBody ApprovalPathModuleVo  approvalPathModuleVo) {
		List<ApprovalPathModuleVo> returnList = new ArrayList<ApprovalPathModuleVo>();		
		try{
			returnList = approvalPathModuleTransactionService.getApprovalPathModules(approvalPathModuleVo);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<ApprovalPathModuleVo>>(returnList,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/saveOrUpdateApprovalPathModuleDetails.json", method = RequestMethod.POST)
	public  ResponseEntity<Integer> saveOrUpdateApprovalPathModuleDetails(@RequestBody ApprovalPathModuleVo  approvalPathModuleVo) {				
		Integer id = approvalPathModuleTransactionService.saveOrUpdateApprovalPathModuleDetails(approvalPathModuleVo);
		return new ResponseEntity<Integer>(id,HttpStatus.OK);
	}

	
}

	