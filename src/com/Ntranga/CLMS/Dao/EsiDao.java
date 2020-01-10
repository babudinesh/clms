package com.Ntranga.CLMS.Dao;

import java.util.List;

import com.Ntranga.CLMS.vo.EsiDetailsVo;
import com.Ntranga.CLMS.vo.SimpleObject;

public interface EsiDao {

	
	public Integer saveOrUpdateWorkerDetails(EsiDetailsVo esiVo);
	
	public List<EsiDetailsVo> getEsiDetailsByCustomerCompanyIds(Integer customerId,Integer companyId,Integer esiId);

	public List<SimpleObject> getTransactionHistoryDatesListForESIDetails(int customerId, int companyId);
}
