package com.Ntranga.CLMS.Service;


import java.util.List;

import com.Ntranga.CLMS.vo.AuditModeControlVo;
import com.Ntranga.CLMS.vo.MedicalTestVo;
import com.Ntranga.CLMS.vo.SectorVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.core.CLMS.entities.MCurrency;
import com.Ntranga.core.CLMS.entities.MLanguage;




public interface AuditModeService {

	public Integer saveAudit(AuditModeControlVo audit);
	
	public List<AuditModeControlVo>  getauditList(Integer customerId,Integer companyId,Integer locationId,Integer countryId, String schemaName);
	
	public List<AuditModeControlVo>  getCompleteAuditList(Integer customerId,Integer companyId,Integer locationId,Integer countryId, String schemaName);
	

}
