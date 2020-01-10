package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.VendorRegisterTypesDao;
import com.Ntranga.CLMS.vo.VendorDamageOrLossDetailsVo;
import com.Ntranga.CLMS.vo.VendorFineRegisterVo;
import com.Ntranga.CLMS.vo.WorkerAdvanceAmountTakenVo;




@Service("vendorRegisterTypesService")
public class VendorRegisterTypesServiceImpl implements VendorRegisterTypesService {

	@Autowired
	private  VendorRegisterTypesDao vendorRegisterTypesDao;

	
	public List<WorkerAdvanceAmountTakenVo> getVendorRegisterTypes(String customerId,String companyId,String vendorId,Integer workerCode, String workerName,String registerType,Integer year,Integer month) {
		return vendorRegisterTypesDao.getVendorRegisterTypes(customerId, companyId, vendorId, workerCode, workerName, registerType, year, month);
	}
	
	public List<WorkerAdvanceAmountTakenVo> getVendorDetailsList(Integer workerId){		
		return	vendorRegisterTypesDao.getVendorDetailsList(workerId);
	}



	@Override
	public Integer saveOrUpdateVendorRegisterTypes(WorkerAdvanceAmountTakenVo advanceAmountTakenVo) {
		// TODO Auto-generated method stub
		return vendorRegisterTypesDao.saveOrUpdateVendorRegisterTypes(advanceAmountTakenVo);
	}



	@Override
	public Integer saveVendorDamageDetails(VendorDamageOrLossDetailsVo paramDamage) {
		return 	vendorRegisterTypesDao.saveVendorDamageDetails(paramDamage);

	}



	@Override
	public List<VendorDamageOrLossDetailsVo> getDamageDetailsById(Integer workerId) {
		return vendorRegisterTypesDao.getDamageDetailsById(workerId);
	}

	@Override
	public Integer saveVendorFines(VendorFineRegisterVo paramFine) {
		return vendorRegisterTypesDao.saveVendorFines(paramFine);
	}

	@Override
	public List<VendorFineRegisterVo> getVendorFinesById(Integer workerId) {
		return vendorRegisterTypesDao.getVendorFinesById(workerId);
	}
	
	
	
	
	
	
}
