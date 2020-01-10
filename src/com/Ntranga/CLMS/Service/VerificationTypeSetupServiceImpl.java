package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.VerificationTypesSetupDao;
import com.Ntranga.CLMS.vo.DepartmentVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.VerificationTypesSetupVo;

@Service("verificationTypeSetupService")
public class VerificationTypeSetupServiceImpl implements VerificationTypeSetupService{

	@Autowired
	private  VerificationTypesSetupDao verificationTypesSetupDao;
	
	@Override
	public Integer saveDepartment(VerificationTypesSetupVo dept) {
		return verificationTypesSetupDao.saveDepartment(dept);
	}

	@Override
	public List<VerificationTypesSetupVo> getVerificationTypesSetupListBySearch(VerificationTypesSetupVo department) {
		return verificationTypesSetupDao.getVerificationTypesSetupListBySearch(department);
	}

	@Override
	public List<VerificationTypesSetupVo> getVerificationTypeSetupDetails(Integer departmentId) {
		return verificationTypesSetupDao.getVerificationTypeSetupDetails(departmentId);
	}

	@Override
	public List<SimpleObject> getDepartmentNameForCostCenter(Integer customerId, Integer companyId) {
		return verificationTypesSetupDao.getDepartmentNameForCostCenter(customerId, companyId);
	}

	@Override
	public List<SimpleObject> getTransactionListForDepartment(Integer customerId, Integer companyId, Integer deptUniqueId) {
		return verificationTypesSetupDao.getTransactionListForDepartment(customerId, companyId, deptUniqueId);
	}

	@Override
	public List<SimpleObject> getDepartmentListDropDown(Integer customerId, Integer companyId, String status) {
		// TODO Auto-generated method stub
		return verificationTypesSetupDao.getDepartmentListDropDown(customerId, companyId, status);
	}

}
