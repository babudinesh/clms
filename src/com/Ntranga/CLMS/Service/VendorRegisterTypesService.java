package com.Ntranga.CLMS.Service;

import java.util.List;

import com.Ntranga.CLMS.vo.VendorDamageOrLossDetailsVo;
import com.Ntranga.CLMS.vo.VendorFineRegisterVo;
import com.Ntranga.CLMS.vo.WorkerAdvanceAmountTakenVo;

public interface VendorRegisterTypesService {
	
	public List<WorkerAdvanceAmountTakenVo> getVendorRegisterTypes(String customerId,String companyId,String vendorId,Integer workerCode, String workerName,String registerType,Integer year,Integer month);
	
	public List<WorkerAdvanceAmountTakenVo> getVendorDetailsList(Integer workerId);

	public Integer saveOrUpdateVendorRegisterTypes(WorkerAdvanceAmountTakenVo advanceAmountTakenVo);

	public Integer saveVendorDamageDetails(VendorDamageOrLossDetailsVo paramDamage);

	public List<VendorDamageOrLossDetailsVo> getDamageDetailsById(Integer workerId);

	public Integer saveVendorFines(VendorFineRegisterVo paramFine);

	public List<VendorFineRegisterVo> getVendorFinesById(Integer workerId);

}
