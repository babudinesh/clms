package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.EsiDao;
import com.Ntranga.CLMS.vo.EsiDetailsVo;
import com.Ntranga.CLMS.vo.SimpleObject;




@Service("esiService")
public class EsiServiceImpl implements EsiService {

	
	@Autowired
	EsiDao esiDao;
	
	
	@Override
	public Integer saveOrUpdateWorkerDetails(EsiDetailsVo esiVo) {
		// TODO Auto-generated method stub
		return esiDao.saveOrUpdateWorkerDetails(esiVo);
	}

	@Override
	public List<EsiDetailsVo> getEsiDetailsByCustomerCompanyIds(Integer customerId, Integer companyId,Integer esiId) {
		// TODO Auto-generated method stub
		return esiDao.getEsiDetailsByCustomerCompanyIds(customerId, companyId, esiId);
	}

	
	public List<SimpleObject> getTransactionHistoryDatesListForESIDetails(int customerId, int companyId){
		return esiDao.getTransactionHistoryDatesListForESIDetails(customerId, companyId);
	}

}
