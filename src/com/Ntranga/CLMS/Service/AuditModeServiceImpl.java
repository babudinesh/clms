package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.AuditModeDao;
import com.Ntranga.CLMS.vo.AuditModeControlVo;




@Service("auditService")
public class AuditModeServiceImpl implements AuditModeService {

	@Autowired
	private  AuditModeDao auditModeDao;

	@Override
	public Integer saveAudit(AuditModeControlVo audit) {
		// TODO Auto-generated method stub
		return auditModeDao.saveAudit(audit);
	}

	@Override
	public List<AuditModeControlVo>  getauditList(Integer customerId, Integer companyId, Integer locationId, Integer countryId, String schemaName) {
		// TODO Auto-generated method stub
		return auditModeDao.getauditList(customerId, companyId, locationId, countryId, schemaName);
	}
	
	
	public List<AuditModeControlVo>  getCompleteAuditList(Integer customerId,Integer companyId,Integer locationId,Integer countryId, String schemaName){
		return auditModeDao.getCompleteAuditList(customerId, companyId, locationId, countryId, schemaName);
	}

}
