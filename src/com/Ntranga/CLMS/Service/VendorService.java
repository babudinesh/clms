package com.Ntranga.CLMS.Service;

import java.util.List;

import com.Ntranga.CLMS.vo.CustomerVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.VendorBankDetailsVo;
import com.Ntranga.CLMS.vo.VendorBranchDetailsInfoVo;
import com.Ntranga.CLMS.vo.VendorDetailsVo;

public interface VendorService {
	
	
	
	@SuppressWarnings("rawtypes")
	public List getVendorTypesList() ;
	
	public List<SimpleObject> getPaymentModesList();
	
	public List<SimpleObject> getPaymentFrequencyList();
	
	public List<VendorDetailsVo> getVendorDetailsList(String customerId,String companyId,String vendorCode,String vendorName, String vendorId, Integer industryId, String status);

	
	public List<SimpleObject> getComapanyNamesAsDropDown(String customerId,String companyId);
	
	public List<CustomerVo> getCustomerNamesAsDropDown(String customerid) ;
	
	public List<SimpleObject> getVendorNamesAsDropDown(String customerId,String companyId,String vendorId);
	
	public List<SimpleObject> getSubIndustryListByIndustryId(String industryId);
	
	
	/*=============================== Vendor Branch Details START ====================================*/

	public List<VendorBranchDetailsInfoVo> getVendorBranchGridList(Integer customerId, Integer companyId, Integer vendorId, String vendorCode, String vendorName, String branchCode);

	public VendorBranchDetailsInfoVo getVendorBranchDetails(String vendorBranchId);

	public Integer saveOrUpdateVendorBranchDetails(VendorBranchDetailsInfoVo vendorBranchDetailsInfoVo);
	
	public List<SimpleObject> getTransactionHistoryDatesListForVendorBranchDetails(Integer vendorBranchId);
	
	/*=============================== Vendor Branch Details END ====================================*/

	
	public List<VendorDetailsVo> getVendorDetailsbyId(String vendorInfoId);
	
	public List<SimpleObject> getTransactionDatesListForEditingVendorDetails(Integer vendorId);

	public Integer saveVendorDetails(String vendorJsonJsonString);
	
	public boolean validateVendorCode(VendorDetailsVo paramVendor);
	
	
	
/*=============================== Vendor Bank Details START ====================================*/
	
	public List<VendorBankDetailsVo> VendorBankGridDetails(String customerId,String companyId,String vendorCode, String vendorName,String vendorId);
	
	public Integer saveOrUpdateVendorBankDetails(VendorBankDetailsVo vendorBankDetails);
	
	public List<VendorBankDetailsVo> getVendorBankDetailsbyId(String vendorBankId);
	
	public List<SimpleObject> getTransactionDatesListForEditingVendorBankDetails(Integer uniqueId);

	public List<SimpleObject> getLocationNamesAsDropDown(String customerId, String companyId);
	
	public List<SimpleObject> getCompanyCountrysAsDropDown(String customerId,String companyId);
	/*=============================== Vendor Bank Details End ====================================*/

	public boolean validateVendorBankCode(VendorBankDetailsVo paramVendor);
	
	
	
	

}
