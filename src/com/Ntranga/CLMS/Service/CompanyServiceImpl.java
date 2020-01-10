package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.CompanyDao;
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



@Service("CompanyService")
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private  CompanyDao companyDao;

	/************************** Company Contact Start **********************/
	/*@Override
	public List<MContactType> getContactTypeList() {
		return companyDao.getContactTypeList();
	}*/

	@Override
	public Integer saveCompanyContact(CompanyContact contact) {
		return companyDao.saveCompanyContact(contact);
	}

	@Override
	public List<ContactVo> getCompanyContactsList(Integer customerId, Integer companyId) {
		return companyDao.getCompanyContactsList(customerId,companyId);
	}

	@Override
	public ContactVo getCompanyContactRecordById(Integer contactId) {
		return companyDao.getCompanyContactRecordById(contactId);
	}

	@Override
	public List<SimpleObject> getCompanyCurrentAddressRecordByCompanyId(Integer customerId, Integer companyId) {
		// TODO Auto-generated method stub
		return companyDao.getCompanyCurrentAddressRecordByCompanyId(customerId, companyId);
	}
	
	@Override
	public List<SimpleObject> getTransactionListForContact(Integer customerId,Integer companyId, Integer contactUniqueId) {
		// TODO Auto-generated method stub
		return companyDao.getTransactionListForContact(customerId, companyId, contactUniqueId);
	}

	/************************* Company Contact End ***********************/
	
	/************************* Company Address Start *********************/

	@Override
	public Integer saveCompanyAddress(CompanyAddress companyAddress) {		
		return companyDao.saveCompanyAddress(companyAddress);
	}

	@Override
	public AddressVo getCompanyAddressRecordById(Integer companyAddressId) {
		// TODO Auto-generated method stub
		return companyDao.getCompanyAddressRecordById(companyAddressId);
	}

	@Override
	public List<AddressVo> getCompanyAddressList(Integer customerId, Integer companyId) {
		// TODO Auto-generated method stub
		return companyDao.getCompanyAddressList(customerId, companyId);
	}

	/*@Override
	public List<CountryVo> getCountriesListByCompanyId(String customerID) {
		// TODO Auto-generated method stub
		return companyDao.getCountriesListByCompanyId(customerID);
	}*/

	@Override
	public List<SimpleObject> getTransactionListForAddress(Integer customerId, Integer companyId, Integer addressUniqueId) {
		// TODO Auto-generated method stub
		return companyDao.getTransactionListForAddress(customerId, companyId, addressUniqueId);
	}


	
	/************************* Company Address End ***********************/
	
	
	/************************* Company Details Start ***********************/
	
	@Override
	public List<CompanyDetailsVo> getCompanyDetailsListByCustomerId(int customerId,String countryName, String companyName, String status,Integer companyId) {
		// TODO Auto-generated method stub
		return companyDao.getCompanyDetailsListByCustomerId(customerId, countryName,  companyName,status,companyId);
	}

	@Override
	public CompanyDetailsInfoVo getCompanyDetailsListByCompanyId(int companyInfoId) {
		// TODO Auto-generated method stub
		return companyDao.getCompanyDetailsListByCompanyId(companyInfoId);
	}

	@Override
	public List<SimpleObject> getCompanyTransactionDates(String customerId, String companyId) {
		// TODO Auto-generated method stub
		return companyDao.getCompanyTransactionDates(customerId, companyId);
	}


	/************************* Company Details End ***********************/


	@Override
	public List<SimpleObject> getCusomerIndustries(Integer customerId) {
		// TODO Auto-generated method stub
		return companyDao.getCusomerIndustries(customerId);
	}

	@Override
	public List<SimpleObject> getCustomerSectors(Integer customerId, Integer industryId) {
		// TODO Auto-generated method stub
		return companyDao.getCustomerSectors(customerId, industryId);
	}

	@Override
	public Integer saveCompanyDetails(CompanyDetailsInfoVo companyDetailsInfoVo) {
		// TODO Auto-generated method stub
		return companyDao.saveCompanyDetails(companyDetailsInfoVo);
	}

	@Override
	public CompanyProfileVo getCompanyProfileByCompanyId(String companyId, String customerId) {
		// TODO Auto-generated method stub
		return companyDao.getCompanyProfileByCompanyId(companyId, customerId);
	}

	@Override
	public Integer saveCompanyProfile(CompanyProfileVo profileVo) {
		// TODO Auto-generated method stub
		return companyDao.saveCompanyProfile(profileVo);
	}

	@Override
	public List<WorkorderDetailInfoVo> getCompanyWorkOrderGridData(String customerId, String companyId,
			String locationName, String departmentName, String workOrderCode, String workOrderName, String status) {
		// TODO Auto-generated method stub
		return companyDao.getCompanyWorkOrderGridData(customerId, companyId, locationName, departmentName, workOrderCode, workOrderName, status);
	}

	@Override
	public Integer saveCompanyWorkOrder(WorkorderDetailInfoVo workorderDetailInfoVo) {
		// TODO Auto-generated method stub
		return companyDao.saveCompanyWorkOrder(workorderDetailInfoVo);
	}

	@Override
	public WorkorderDetailInfoVo getWorkorderDetailInfoByWorkOrderId(String orderId) {
		// TODO Auto-generated method stub
		return companyDao.getWorkorderDetailInfoByWorkOrderId(orderId);
	}

	@Override
	public List<SimpleObject> getTransactionDatesOfWorkOrderHistory(Integer customerId, Integer companyId,
			Integer workerOrderId) {
		// TODO Auto-generated method stub
		return companyDao.getTransactionDatesOfWorkOrderHistory(customerId, companyId, workerOrderId);
	}

	@Override
	public List<SimpleObject> getWorkOrderAsDropDown(String customerId, String companyId, String vendorId) {
		// TODO Auto-generated method stub
		return companyDao.getWorkOrderAsDropDown(customerId, companyId, vendorId);
	}

	
	/************************** Company Division Start **********************/
	@Override
	public Integer saveDivision(DivisionVo paramDivision) {
		return companyDao.saveDivision(paramDivision);
	}

	@Override
	public List<DivisionVo> getDivisionById(Integer divisionDetailsId) {
		return companyDao.getDivisionById(divisionDetailsId);
	}

	@Override
	public List<DivisionVo> getDivisionsListBySearch(DivisionVo paramDivision) {
		return companyDao.getDivisionsListBySearch(paramDivision);
	}

	@Override
	public List<SimpleObject> getTransactionListForDivision(Integer customerId,Integer companyId, Integer divisionUniqueId) {
		return companyDao.getTransactionListForDivision(customerId, companyId, divisionUniqueId);
	}

	/************************** Company Division Start **********************/	
	
	@Override
	public Integer validateCompanyCode(String customerId, String companyCode) {
		return companyDao.validateCompanyCode(customerId, companyCode);
	}
	
	public boolean validateCompanyAddress(Integer customerId,Integer companyId, Integer addressTypeId){
		return companyDao.validateCompanyAddress(customerId, companyId, addressTypeId);
	}
}
