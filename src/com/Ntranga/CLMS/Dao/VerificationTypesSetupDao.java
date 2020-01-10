package com.Ntranga.CLMS.Dao;

import java.util.List;

import com.Ntranga.CLMS.vo.DepartmentVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.VerificationTypesSetupVo;

public interface VerificationTypesSetupDao {

	public Integer saveDepartment(VerificationTypesSetupVo dept);	
	
	public List<VerificationTypesSetupVo>  getVerificationTypeSetupDetails(Integer departmentId);
	
	public List<VerificationTypesSetupVo> getVerificationTypesSetupListBySearch(VerificationTypesSetupVo department);
	
	public List<SimpleObject> getDepartmentNameForCostCenter(Integer customerId, Integer companyId);
	
	public List<SimpleObject> getTransactionListForDepartment(Integer customerId, Integer companyId, Integer deptUniqueId);
	
	public List<SimpleObject> getDepartmentListDropDown(Integer customerId, Integer companyId,String status);
}
