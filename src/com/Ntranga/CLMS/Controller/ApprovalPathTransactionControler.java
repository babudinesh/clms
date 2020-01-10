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
import com.Ntranga.CLMS.vo.ApprovalPathTransactionVo;
import com.Ntranga.CLMS.vo.CustomerVo;
import com.Ntranga.CLMS.vo.SimpleObject;

@RestController
@RequestMapping(value = "approvalPathTransactionControler")
public class ApprovalPathTransactionControler {
	
	private static Logger log = Logger.getLogger(ApprovalPathTransactionControler.class);

	
	@Autowired
	private ApprovalPathModuleTransactionService approvalPathModuleTransactionService;
	
	@Autowired
	private VendorService vendorService;

	
	@RequestMapping(value = "/getMaterDataForApprovalPathForTransaction.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,Object>> getMaterDataForApprovalPathForTransaction(@RequestBody ApprovalPathTransactionVo approvalPathTransactionVo ) {
		Map<String,Object> returnList = new HashMap<String,Object>();	
		try{
			List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(approvalPathTransactionVo.getCustomerId()+"");
			returnList.put("customerList", customerList);
			List<SimpleObject> companyList = vendorService.getComapanyNamesAsDropDown(approvalPathTransactionVo.getCustomerId()+"",approvalPathTransactionVo.getCompanyId()+"");
			returnList.put("companyList", companyList);
			ApprovalPathModuleVo approvalPathModuleVo = new ApprovalPathModuleVo();
			approvalPathModuleVo.setCustomerId(approvalPathTransactionVo.getCustomerId());
			approvalPathModuleVo.setCompanyId(approvalPathTransactionVo.getCompanyId());
			List moduleList = approvalPathModuleTransactionService.getApprovalPathModules(approvalPathModuleVo);				
			returnList.put("moduleList", moduleList);
			if(approvalPathTransactionVo.getApprovalPathTransactionInfoId() != null && approvalPathTransactionVo.getApprovalPathTransactionInfoId() >0){
				List tempList = approvalPathModuleTransactionService.getApprovalPathTransactions(approvalPathTransactionVo);
				if(tempList.size() > 0)
					returnList.put("transaction", tempList.get(0));
			}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,Object>>(returnList,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getApprovalPathTransactions.json", method = RequestMethod.POST)
	public  ResponseEntity<List<ApprovalPathTransactionVo>> getApprovalPathModules(@RequestBody ApprovalPathTransactionVo approvalPathTransactionVo ) {
		List<ApprovalPathTransactionVo> returnList = new ArrayList<ApprovalPathTransactionVo>();		
		try{
			returnList = approvalPathModuleTransactionService.getApprovalPathTransactions(approvalPathTransactionVo);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<ApprovalPathTransactionVo>>(returnList,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/saveOrUpdateApprovalPathTransactionDetails.json", method = RequestMethod.POST)
	public  ResponseEntity<Integer> saveOrUpdateApprovalPathTransactionDetails(@RequestBody ApprovalPathTransactionVo approvalPathTransactionVo) {				
		Integer id = approvalPathModuleTransactionService.saveOrUpdateApprovalPathTransactionDetails(approvalPathTransactionVo);
		return new ResponseEntity<Integer>(id,HttpStatus.OK);
	}

	@RequestMapping(value = "/getTransationHistoryDatesList.json", method = RequestMethod.POST)
	public  ResponseEntity<List<SimpleObject>> getTransationHistoryDatesList(@RequestBody ApprovalPathTransactionVo approvalPathTransactionVo) {				
		List<SimpleObject> datesList  = approvalPathModuleTransactionService.getTransationHistoryDatesList(approvalPathTransactionVo);
		return new ResponseEntity<List<SimpleObject>>(datesList,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getApprovalPathTransactionByTransactionInfoId.json", method = RequestMethod.POST)
	public  ResponseEntity<ApprovalPathTransactionVo> getApprovalPathTransactionByTransactionInfoId(@RequestBody ApprovalPathTransactionVo approvalPathTransactionVo) {
		ApprovalPathTransactionVo tvo = approvalPathModuleTransactionService.getApprovalPathTransactionByTransactionInfoId(approvalPathTransactionVo);
		return new ResponseEntity<ApprovalPathTransactionVo>(tvo,HttpStatus.OK);
	}
	
	
}

	