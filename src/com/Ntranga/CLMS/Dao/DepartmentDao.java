package com.Ntranga.CLMS.Dao;

import java.util.List;

import com.Ntranga.CLMS.vo.DepartmentVo;
import com.Ntranga.CLMS.vo.SimpleObject;

public interface DepartmentDao {

	public Integer saveDepartment(DepartmentVo dept);	
	
	public List<DepartmentVo>  getDepartmentById(Integer departmentId);
	
	public List<DepartmentVo> getDepartmentsListBySearch(DepartmentVo department);
	
	public List<SimpleObject> getDepartmentNameForCostCenter(Integer customerId, Integer companyId);
	
	public List<SimpleObject> getTransactionListForDepartment(Integer customerId, Integer companyId, Integer deptUniqueId);
	
	public List<SimpleObject> getDepartmentListDropDown(Integer customerId, Integer companyId,String status);
}
