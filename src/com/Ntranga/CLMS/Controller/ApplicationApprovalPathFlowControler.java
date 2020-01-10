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
import com.Ntranga.CLMS.vo.ApplicationApprovalPathVo;
import com.Ntranga.CLMS.vo.ApprovalPathModuleVo;
import com.Ntranga.CLMS.vo.ApprovalPathTransactionVo;
import com.Ntranga.CLMS.vo.CustomerVo;
import com.Ntranga.CLMS.vo.SimpleObject;

@RestController
@RequestMapping(value = "applicationApprovalPathFlowControler")
public class ApplicationApprovalPathFlowControler {
	
	private static Logger log = Logger.getLogger(ApplicationApprovalPathFlowControler.class);

	
	@Autowired
	private ApprovalPathModuleTransactionService approvalPathModuleTransactionService;
	
	@Autowired
	private VendorService vendorService;

	
	@RequestMapping(value = "/getMaterDataForApprovalPathFlow.json", method = RequestMethod.POST)
	public  ResponseEntity<Map<String,Object>> getMaterDataForApprovalPathFlow(@RequestBody ApplicationApprovalPathVo applicationApprovalPathVo ) {
		Map<String,Object> returnList = new HashMap<String,Object>();	
		try{
			List<CustomerVo> customerList = vendorService.getCustomerNamesAsDropDown(applicationApprovalPathVo.getCustomerId()+"");
			returnList.put("customerList", customerList);
			List<SimpleObject> companyList = vendorService.getComapanyNamesAsDropDown(applicationApprovalPathVo.getCustomerId()+"",applicationApprovalPathVo.getCompanyId()+"");
			returnList.put("companyList", companyList);
			ApprovalPathModuleVo approvalPathModuleVo = new ApprovalPathModuleVo();
			approvalPathModuleVo.setCustomerId(applicationApprovalPathVo.getCustomerId());
			approvalPathModuleVo.setCompanyId(applicationApprovalPathVo.getCompanyId());
			List moduleList = approvalPathModuleTransactionService.getApprovalPathModules(approvalPathModuleVo);
			returnList.put("moduleList", moduleList);
			
			if(applicationApprovalPathVo.getApplicationApprovalPathId()!= null && applicationApprovalPathVo.getApplicationApprovalPathId() >0){
				ApplicationApprovalPathVo  pathflow = approvalPathModuleTransactionService.getApplicationApprovalPathsById(applicationApprovalPathVo);
				returnList.put("pathflow", pathflow);
				ApprovalPathTransactionVo approvalPathTransactionVo = new ApprovalPathTransactionVo();
				approvalPathTransactionVo.setApprovalPathModuleId(pathflow.getApprovalPathModuleId());
				List transactionList =  approvalPathModuleTransactionService.getApprovalPathTransactions(approvalPathTransactionVo);
				returnList.put("transactionList", transactionList);
				
			}
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<Map<String,Object>>(returnList,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getApplicationApprovalPaths.json", method = RequestMethod.POST)
	public  ResponseEntity<List<ApplicationApprovalPathVo>> getApplicationApprovalPaths(@RequestBody ApplicationApprovalPathVo applicationApprovalPathVo ) {
		List<ApplicationApprovalPathVo> returnList = new ArrayList<ApplicationApprovalPathVo>();		
		try{
			returnList = approvalPathModuleTransactionService.getApplicationApprovalPaths(applicationApprovalPathVo);
		}catch(Exception e){
			log.error("Error Occured ",e);
		}
		return new ResponseEntity<List<ApplicationApprovalPathVo>>(returnList,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/saveOrUpdateApprovalFlowDetails.json", method = RequestMethod.POST)
	public  ResponseEntity<Integer> saveOrUpdateApprovalFlowDetails(@RequestBody ApplicationApprovalPathVo applicationApprovalPathVo) {				
		Integer id = approvalPathModuleTransactionService.saveOrUpdateApprovalFlowDetails(applicationApprovalPathVo);
		return new ResponseEntity<Integer>(id,HttpStatus.OK);
	}

	
	
}

	