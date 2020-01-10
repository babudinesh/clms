package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.EmployeeDao;
import com.Ntranga.CLMS.vo.EmployeeInformationVo;
import com.Ntranga.CLMS.vo.SimpleObject;


@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

	
	@Autowired
	private EmployeeDao employeeDao;

	@Override
	public List<SimpleObject> getBloodGroupList() {
		
		return employeeDao.getBloodGroupList();
	}

	@Override
	public List<SimpleObject> getJobTitleList() {
		// TODO Auto-generated method stub
		return employeeDao.getJobTitleList();
	}

	@Override
	public List<SimpleObject> getJobTypeList() {
		// TODO Auto-generated method stub
		return employeeDao.getJobTypeList();
	}

	@Override
	public List<SimpleObject> getJobStatusList() {
		// TODO Auto-generated method stub
		return employeeDao.getJobStatusList();
	}

	@Override
	public List<SimpleObject> getLocationListByCustomerIdAndCompanyId(Integer customerId, Integer CompanyId, String locationArray) {
		// TODO Auto-generated method stub
		return employeeDao.getLocationListByCustomerIdAndCompanyId(customerId,CompanyId);
		//return null;
	}

	@Override
	public Integer saveEmployeeDetails(EmployeeInformationVo employeeVo) {
		// TODO Auto-generated method stub
		return employeeDao.saveEmployeeDetails(employeeVo);
	}

	@Override
	public List<EmployeeInformationVo> employeeDetailsByemployeeId(Integer employeeId) {
		// TODO Auto-generated method stub
		return employeeDao.employeeDetailsByemployeeId(employeeId);
	}

	@Override
	public List<SimpleObject> getTransactionListForEmployeeInfo(Integer uniqueId) {
		// TODO Auto-generated method stub
		return employeeDao.getTransactionListForEmployeeInfo(uniqueId);
	}
	
	@Override
	public List<SimpleObject> getDepartmentListByLocationId(Integer locationId) {
		// TODO Auto-generated method stub
		return employeeDao.getDepartmentListByLocationId(locationId);
	}

	@Override
	public List<SimpleObject> getJobCodeListforCUstomerAndCompany(Integer customerId, Integer CompanyId) {
		// TODO Auto-generated method stub
		return employeeDao.getJobCodeListforCUstomerAndCompany(customerId, CompanyId);
	}

	@Override
	public List<SimpleObject> getReportingMangerList(Integer customerId, Integer CompanyId) {
		// TODO Auto-generated method stub
		return employeeDao.getReportingMangerList(customerId, CompanyId);
	}

	@Override
	public List<EmployeeInformationVo> getEmployeeGridData(Integer customerId,Integer CompanyId,Integer jobTypeId,Integer jobStatusId,Integer employeeCountryId,String employeeCode) {
		// TODO Auto-generated method stub
		return employeeDao.getEmployeeGridData(customerId, CompanyId, jobTypeId, jobStatusId, employeeCountryId, employeeCode);
	}

	@Override
	public List<SimpleObject> getDepartmentListByCompanyId(Integer customerId, Integer CompanyId) {
		// TODO Auto-generated method stub
		return employeeDao.getDepartmentListByCompanyId(customerId, CompanyId);
	}

	@Override
	public List<SimpleObject> getEmployeesList(Integer customerId,Integer companyId) {
		return employeeDao.getEmployeesList(customerId,companyId);
	} 
	
	


}
