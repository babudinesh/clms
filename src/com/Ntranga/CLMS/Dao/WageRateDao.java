package com.Ntranga.CLMS.Dao;

import java.util.List;

import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.WageRateVo;

public interface WageRateDao {

	public Integer saveWageRate(WageRateVo paramWage);	
	
	public List<WageRateVo>  getWageRateById(Integer wageRateDetailsId);
	
	public List<WageRateVo> getWageRatesListBySearch(WageRateVo paramWage);
	
	public List<SimpleObject> getTransactionListForWageRate(Integer customerId, Integer companyId, Integer wageRateId);
	
	//public List<WageRateCurrencyVo> getWageRateCurrencyList(Integer customerId, Integer companyId, Integer wageRateId);
}
