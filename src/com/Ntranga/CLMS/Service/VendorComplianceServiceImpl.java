package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.VendorComplianceDao;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.VendorComplianceVo;

@Service("vendorComplianceService")
public class VendorComplianceServiceImpl implements VendorComplianceService {

	@Autowired
	VendorComplianceDao vendorComplianceDao;
	
	@Override
	public Integer saveVendorCompliance(VendorComplianceVo paramCompliance) {
		return vendorComplianceDao.saveVendorCompliance(paramCompliance);
	}

	@Override
	public List<VendorComplianceVo> getVendorComplianceById(Integer vendorComplianceId) {
		return vendorComplianceDao.getVendorComplianceById(vendorComplianceId);
	}

	@Override
	public List<VendorComplianceVo> getVendorComplianceListBySearch(VendorComplianceVo paramCompliance) {
		return vendorComplianceDao.getVendorComplianceListBySearch(paramCompliance);
	}

	@Override
	public List<SimpleObject> getTransactionListForVendorCompliance(Integer customerId, Integer companyId,Integer vendorComplianceUniqueId) {
		return vendorComplianceDao.getTransactionListForVendorCompliance(customerId, companyId, vendorComplianceUniqueId);
	}

	@Override
	public List<SimpleObject> getNumberOfWorkersCovered(Integer customerId, Integer companyId, Integer vendorId) {
		return vendorComplianceDao.getNumberOfWorkersCovered(customerId, companyId, vendorId);
	}

	@Override
	public List<SimpleObject> getLocationListByVendor(Integer customerId, Integer companyId, Integer vendorId) {
		// TODO Auto-generated method stub
		return vendorComplianceDao.getLocationListByVendor(customerId,companyId,vendorId);
	}

}
