package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.VendorDao;
import com.Ntranga.CLMS.vo.CustomerVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.VendorBankDetailsVo;
import com.Ntranga.CLMS.vo.VendorBranchDetailsInfoVo;
import com.Ntranga.CLMS.vo.VendorDetailsVo;




@Service("vendorService")
public class VendorServiceImpl implements VendorService {

	@Autowired
	private  VendorDao vendorDao;

	public List<SimpleObject> getVendorTypesList() {		
	return	vendorDao.getVendorTypesList();
	}

	public List<SimpleObject> getPaymentModesList(){		
		return	vendorDao.getPaymentModesList();
	}
	
	public List<SimpleObject> getPaymentFrequencyList(){		
		return	vendorDao.getPaymentFrequencyList();
	}
	
	public List<VendorDetailsVo> getVendorDetailsList(String customerId, String companyId, String vendorCode, String vendorName, String vendorId,Integer industryId, String status){		
		return	vendorDao.getVendorDetailsList(customerId, companyId, vendorCode, vendorName, vendorId, industryId, status);
	}
	
	public List<SimpleObject> getComapanyNamesAsDropDown(String customerId,String companyId){	
		return	vendorDao.getComapanyNamesAsDropDown(customerId,companyId);
	}
	
	public List<CustomerVo> getCustomerNamesAsDropDown(String customerid) {
		return	vendorDao.getCustomerNamesAsDropDown(customerid);		
	}
	
	public List<SimpleObject> getVendorNamesAsDropDown(String customerId,String companyId,String vendorId){		
		return	vendorDao.getVendorNamesAsDropDown(customerId,companyId,vendorId);
	}
	
	
	/*=============================== Vendor Branch Details START ====================================*/

	public List<VendorBranchDetailsInfoVo> getVendorBranchGridList(Integer customerId, Integer companyId, Integer vendorId, String vendorCode, String vendorName, String branchCode){
		return	vendorDao.getVendorBranchGridList(customerId, companyId, vendorId, vendorCode, vendorName, branchCode);
	}

	public VendorBranchDetailsInfoVo getVendorBranchDetails(String vendorBranchId){
		return	vendorDao.getVendorBranchDetails(vendorBranchId);
	}

	@Override
	public Integer saveOrUpdateVendorBranchDetails(VendorBranchDetailsInfoVo vendorBranchDetailsInfoVo) {
		// TODO Auto-generated method stub
		return vendorDao.saveOrUpdateVendorBranchDetails(vendorBranchDetailsInfoVo);
	}
	
	@Override
	public List<SimpleObject> getTransactionHistoryDatesListForVendorBranchDetails(Integer vendorBranchId) {
		// TODO Auto-generated method stub
		return vendorDao.getTransactionHistoryDatesListForVendorBranchDetails(vendorBranchId);
	}
	
	/*=============================== Vendor Branch Details END ====================================*/
	
	
	public List<SimpleObject> getSubIndustryListByIndustryId(String industryId){
		return	vendorDao.getSubIndustryListByIndustryId(industryId);
		
	}
	
	public Integer saveVendorDetails(String vendorDetailsJson){
		return	vendorDao.saveVendorDetails(vendorDetailsJson);
		
	}

	@Override
	public List<VendorDetailsVo> getVendorDetailsbyId(String vendorInfoId) {		
		return	vendorDao.getVendorDetailsbyId(vendorInfoId);
	}

	@Override
	public List<SimpleObject> getTransactionDatesListForEditingVendorDetails(Integer vendorId) {	
		return	vendorDao.getTransactionDatesListForEditingVendorDetails(vendorId);
	}
	
	@Override
	public boolean validateVendorCode(VendorDetailsVo paramVendor){
		return	vendorDao.validateVendorCode(paramVendor);
	}
	

	
/*=============================== Vendor Bank Details START ====================================*/
	@Override
	public List<VendorBankDetailsVo> VendorBankGridDetails(String customerId,String companyId,String vendorCode, String vendorName,String vendorId){
		return vendorDao.VendorBankGridDetails(customerId, companyId, vendorCode, vendorName,vendorId);
	}
	
	
	@Override
	public Integer saveOrUpdateVendorBankDetails(VendorBankDetailsVo vendorBankDetails){		
		return vendorDao.saveOrUpdateVendorBankDetails(vendorBankDetails);
	}
	
	@Override
	public List<VendorBankDetailsVo> getVendorBankDetailsbyId(String vendorBankId){
		return vendorDao.getVendorBankDetailsbyId(vendorBankId);
		
	}
	@Override
	public List<SimpleObject> getTransactionDatesListForEditingVendorBankDetails(Integer uniqueId){		
		return vendorDao.getTransactionDatesListForEditingVendorBankDetails(uniqueId);
	}
	
	public List<SimpleObject> getLocationNamesAsDropDown(String customerId, String companyId){		
		return vendorDao.getLocationNamesAsDropDown(customerId,companyId);
	}
	
	public List<SimpleObject> getCompanyCountrysAsDropDown(String customerId,String companyId){		
		return vendorDao.getCompanyCountrysAsDropDown(customerId,companyId);
	}
	/*=============================== Vendor Bank Details End ====================================*/

	@Override
	public boolean validateVendorBankCode(VendorBankDetailsVo paramVendor) {
		// TODO Auto-generated method stub
		return vendorDao.validateVendorBankCode(paramVendor);
	}
	
	
	
	
}
