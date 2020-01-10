package com.Ntranga.CLMS.Service;


import java.util.List;

import com.Ntranga.CLMS.vo.AddressVo;
import com.Ntranga.CLMS.vo.CompanyDetailsInfoVo;
import com.Ntranga.CLMS.vo.CompanyDetailsVo;
import com.Ntranga.CLMS.vo.CompanyProfileVo;
import com.Ntranga.CLMS.vo.ContactVo;
import com.Ntranga.CLMS.vo.DivisionVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.WorkorderDetailInfoVo;
import com.Ntranga.core.CLMS.entities.CompanyAddress;
import com.Ntranga.core.CLMS.entities.CompanyContact;



public interface CompanyService {
	
			/************************** Company Details Start  **********************/
	public List<CompanyDetailsVo> getCompanyDetailsListByCustomerId(int customerId, String countryName, String companyName, String status,Integer companyId);
	
	public CompanyDetailsInfoVo getCompanyDetailsListByCompanyId(int companyInfoId);
	
	public List<SimpleObject> getCompanyTransactionDates(String customerId, String companyId);
	
	public Integer saveCompanyDetails(CompanyDetailsInfoVo companyDetailsInfoVo);
			/************************** Company Details End **********************/
	
	
			/************************** Company Profile Start **********************/
	public CompanyProfileVo getCompanyProfileByCompanyId(String companyId, String customerId);
	
	public Integer saveCompanyProfile(CompanyProfileVo profileVo);
			/************************** Company Profile End **********************/
	


	/************************** Company Contact Start ********************/

	public Integer saveCompanyContact(CompanyContact contact);
	
	//public List<MContactType> getContactTypeList();

	public List<ContactVo> getCompanyContactsList(Integer customerId, Integer companyId);

	public ContactVo getCompanyContactRecordById(Integer contactId);
	
	public List<SimpleObject> getTransactionListForContact(Integer customerId, Integer companyId, Integer contactUniqueId);
	
	public List<SimpleObject> getCompanyCurrentAddressRecordByCompanyId(Integer customerId, Integer companyId);

	/************************* Company Contact End ***********************/

	
	
	/************************** Company Address Start ********************/

	public Integer saveCompanyAddress(CompanyAddress companyAddress);
	
	public AddressVo getCompanyAddressRecordById(Integer companyAddressId);

	public List<AddressVo> getCompanyAddressList(Integer customerId, Integer companyId);

	//public List<CountryVo> getCountriesListByCompanyId(String customerID);

	public List<SimpleObject> getTransactionListForAddress(Integer customerId,Integer companyId, Integer addressUniqueId);
	
	/************************** Company Address End ********************/
	

	
	/************************************   Master Info  Start **************************************/
	
	public List<SimpleObject> getCusomerIndustries(Integer customerId);
	
	public List<SimpleObject> getCustomerSectors(Integer customerId, Integer industryId);
	
	/************************************   Master Info  End **************************************/
	
	
	/************************************   Company Work order  End **************************************/

	public List<WorkorderDetailInfoVo> getCompanyWorkOrderGridData(String customerId, String companyId, String locationName,
			String departmentName, String workOrderCode, String workOrderName, String status);

	public Integer saveCompanyWorkOrder(WorkorderDetailInfoVo workorderDetailInfoVo);
	
	public WorkorderDetailInfoVo getWorkorderDetailInfoByWorkOrderId( String orderId);

	public List<SimpleObject> getTransactionDatesOfWorkOrderHistory(Integer customerId,Integer companyId, Integer workerOrderId);

	public List<SimpleObject> getWorkOrderAsDropDown(String customerId, String companyId, String vendorId);
	
	/************************************   Company  Work order  End **************************************/

	
	/************************** Company Division Start ********************/

	public Integer saveDivision(DivisionVo paramDivision);
	
	public List<DivisionVo> getDivisionById(Integer divisionDetailsId);

	public List<DivisionVo> getDivisionsListBySearch(DivisionVo paramDivision);

	public List<SimpleObject> getTransactionListForDivision(Integer customerId,Integer companyId, Integer divisionUniqueId);
	
	/************************** Company Division End ********************/
	
	public Integer validateCompanyCode(String customerId, String companyCode) ;
	
	public boolean validateCompanyAddress(Integer customerId,Integer companyId, Integer addressTypeId);

}
