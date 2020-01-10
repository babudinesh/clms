package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.vo.DepartmentVo;
import com.Ntranga.CLMS.vo.SimpleObject;

@Service("departmentService")
public interface DepartmentService {
	
	public Integer saveDepartment(DepartmentVo dept);	
	
	public List<DepartmentVo>  getDepartmentById(Integer departmentId);
	
	public List<DepartmentVo> getDepartmentsListBySearch(DepartmentVo department);
	
	public List<SimpleObject> getDepartmentNameForCostCenter(Integer customerId, Integer companyId);
	
	public List<SimpleObject> getTransactionListForDepartment(Integer customerId, Integer companyId, Integer deptUniqueId);

	public List<SimpleObject> getDepartmentListDropDown(Integer customerId, Integer companyId,String status);
}
