package com.Ntranga.CLMS.Dao;

import java.util.List;

import com.Ntranga.CLMS.vo.DefineComplianceTypesVo;
import com.Ntranga.CLMS.vo.SimpleObject;

public interface DefineComplianceTypesDao {

	public List<DefineComplianceTypesVo> getComplianceList(Integer customerId, Integer companyId,Integer locationId);
	
	public Integer saveCompliance(DefineComplianceTypesVo defineComplianceTypesVo);
	
	public List<SimpleObject> getTransactionDatesofHistory(Integer customerId, Integer companyId);	
	
	public List<DefineComplianceTypesVo> getComplianceRecordById(Integer defineComplianceTypeId);

	public List<DefineComplianceTypesVo> getComplianceListByApplicable(Integer customerId, Integer companyId, String applicableTo);
}
