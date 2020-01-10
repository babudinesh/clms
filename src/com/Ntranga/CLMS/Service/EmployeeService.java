package com.Ntranga.CLMS.Service;

import java.util.List;

import com.Ntranga.CLMS.vo.EmployeeInformationVo;
import com.Ntranga.CLMS.vo.SimpleObject;


public interface EmployeeService {

	public List<SimpleObject> getBloodGroupList();
	public List<SimpleObject> getJobTitleList();
	public List<SimpleObject> getJobTypeList();
	public List<SimpleObject> getJobStatusList();
	public List<SimpleObject> getLocationListByCustomerIdAndCompanyId(Integer customerId,Integer CompanyId, String locationArray);
	public Integer saveEmployeeDetails(EmployeeInformationVo employeeVo);
	public List<EmployeeInformationVo> employeeDetailsByemployeeId(Integer employeeId);
	public List<SimpleObject> getTransactionListForEmployeeInfo(Integer uniqueId);
	public List<SimpleObject> getDepartmentListByLocationId(Integer locationId) ;
	public List<SimpleObject> getJobCodeListforCUstomerAndCompany(Integer customerId,Integer CompanyId);
	public List<SimpleObject> getReportingMangerList(Integer customerId,Integer CompanyId);

	public List<EmployeeInformationVo> getEmployeeGridData(Integer customerId,Integer CompanyId,Integer jobTypeId,Integer jobStatusId,Integer employeeCountryId,String employeeCode);
	public List<SimpleObject> getDepartmentListByCompanyId(Integer customerId,Integer CompanyId);
	public List<SimpleObject> getEmployeesList(Integer customerId,Integer companyId);
	
}
