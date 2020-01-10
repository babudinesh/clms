package com.Ntranga.CLMS.Dao;

import java.util.List;

import com.Ntranga.CLMS.vo.AuditModeControlVo;

public interface AuditModeDao {

	
	public Integer saveAudit(AuditModeControlVo audit);

	public List<AuditModeControlVo>  getauditList(Integer customerId, Integer companyId, Integer locationId, Integer countryId, String schemaName);
	
	public List<AuditModeControlVo>  getCompleteAuditList(Integer customerId,Integer companyId,Integer locationId,Integer countryId, String schemaName);
	

}
