package com.Ntranga.CLMS.Service;

import java.util.List;
import java.util.Map;

import com.Ntranga.CLMS.vo.AssignShiftPatternDetailsVo;
import com.Ntranga.CLMS.vo.AssignShiftPatternUpdateVo;
import com.Ntranga.CLMS.vo.AssignShiftsVo;
import com.Ntranga.CLMS.vo.EsiDetailsVo;
import com.Ntranga.CLMS.vo.PlantVo;
import com.Ntranga.CLMS.vo.SimpleObject;

public interface EsiService {

	public Integer saveOrUpdateWorkerDetails(EsiDetailsVo esiVo);
	
	public List<EsiDetailsVo> getEsiDetailsByCustomerCompanyIds(Integer customerId,Integer companyId,Integer esiId);

	public List<SimpleObject> getTransactionHistoryDatesListForESIDetails(int customerId, int companyId);

}
