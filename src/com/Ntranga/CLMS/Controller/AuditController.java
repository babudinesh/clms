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

import com.Ntranga.CLMS.Service.AuditModeService;
import com.Ntranga.CLMS.Service.VendorService;
import com.Ntranga.CLMS.vo.AuditModeControlVo;
import com.Ntranga.CLMS.vo.VendorBankDetailsVo;
import com.Ntranga.CLMS.vo.VendorDetailsVo;

@RestController
@RequestMapping(value = "auditController")
public class AuditController {
	
	private static Logger log = Logger.getLogger(AuditController.class);
	
	@Autowired
	private AuditModeService auditService;

	
	
	/*=============================== Audit Details ====================================*/

	
	
	
	@RequestMapping(value = "/saveAudit.json", method = RequestMethod.POST)
	public  ResponseEntity<Integer> saveAudit(@RequestBody AuditModeControlVo audit) {	
		System.out.println("asdfasdf");
		Integer auditId = auditService.saveAudit(audit);		
		return new ResponseEntity<Integer>(auditId,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getAuditList.json", method = RequestMethod.POST)
	public ResponseEntity<Map<String, List>> getAuditList(@RequestBody AuditModeControlVo audit) {	
		Map<String, List> map = new HashMap<String, List>();	
		try{
		List<AuditModeControlVo> auditList = auditService.getauditList(audit.getCustomerId(), audit.getCompanyId(), audit.getLocationId(), audit.getCountryId(),audit.getSchemaName());	
		List<AuditModeControlVo> completeAuditList = auditService.getCompleteAuditList(audit.getCustomerId(), audit.getCompanyId(), audit.getLocationId(), audit.getCountryId(),audit.getSchemaName());
		map.put("completeAuditList", completeAuditList);
		map.put("auditList", auditList);
		}catch(Exception e){
			log.error(e);
		}
		
		return new ResponseEntity<Map<String, List>>(map,HttpStatus.OK);
	}
	
	
	
	
	
	
	/*=============================== Audit Details END ====================================*/
}
