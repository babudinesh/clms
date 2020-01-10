package com.Ntranga.CLMS.Dao;

import java.util.List;

import com.Ntranga.CLMS.vo.AddressVo;
import com.Ntranga.CLMS.vo.ContactVo;
import com.Ntranga.CLMS.vo.CountryVo;
import com.Ntranga.CLMS.vo.CustomerCountriesVo;
import com.Ntranga.CLMS.vo.CustomerDetailsVo;
import com.Ntranga.CLMS.vo.EmployeeInformationVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.core.CLMS.entities.CustomerAddress;
import com.Ntranga.core.CLMS.entities.CustomerContact;
import com.Ntranga.core.CLMS.entities.MContactType;



public interface EmployeeDao {
	public List<SimpleObject> getBloodGroupList();
	public List<SimpleObject> getJobTitleList();
	public List<SimpleObject> getJobTypeList();
	public List<SimpleObject> getJobStatusList();
	public List<SimpleObject> getLocationListByCustomerIdAndCompanyId(Integer customerId,Integer CompanyId);
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
