package com.Ntranga.CLMS.Service;

import java.util.List;

import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.VendorComplianceVo;

public interface VendorComplianceService {

	public Integer saveVendorCompliance(VendorComplianceVo paramCompliance);	
	
	public List<VendorComplianceVo>  getVendorComplianceById(Integer vendorComplianceId);
	
	public List<VendorComplianceVo> getVendorComplianceListBySearch(VendorComplianceVo paramCompliance);
	
	public List<SimpleObject> getTransactionListForVendorCompliance(Integer customerId, Integer companyId, Integer vendorComplianceUniqueId);
	
	public List<SimpleObject> getNumberOfWorkersCovered(Integer customerId, Integer companyId, Integer vendorId);
	
	public List<SimpleObject> getLocationListByVendor(Integer customerId, Integer companyId, Integer vendorId) ;
	
}
