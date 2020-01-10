package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.DepartmentDao;
import com.Ntranga.CLMS.vo.DepartmentVo;
import com.Ntranga.CLMS.vo.SimpleObject;

@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService{

	@Autowired
	private  DepartmentDao departmentDao;
	
	@Override
	public Integer saveDepartment(DepartmentVo dept) {
		return departmentDao.saveDepartment(dept);
	}

	@Override
	public List<DepartmentVo> getDepartmentsListBySearch(DepartmentVo department) {
		return departmentDao.getDepartmentsListBySearch(department);
	}

	@Override
	public List<DepartmentVo> getDepartmentById(Integer departmentId) {
		return departmentDao.getDepartmentById(departmentId);
	}

	@Override
	public List<SimpleObject> getDepartmentNameForCostCenter(Integer customerId, Integer companyId) {
		return departmentDao.getDepartmentNameForCostCenter(customerId, companyId);
	}

	@Override
	public List<SimpleObject> getTransactionListForDepartment(Integer customerId, Integer companyId, Integer deptUniqueId) {
		return departmentDao.getTransactionListForDepartment(customerId, companyId, deptUniqueId);
	}

	@Override
	public List<SimpleObject> getDepartmentListDropDown(Integer customerId, Integer companyId, String status) {
		// TODO Auto-generated method stub
		return departmentDao.getDepartmentListDropDown(customerId, companyId, status);
	}

}
