package com.Ntranga.CLMS.Service;

import java.util.List;

import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.WageRateVo;

public interface WageRateService {

	public Integer saveWageRate(WageRateVo paramWage);	
	
	public List<WageRateVo>  getWageRateById(Integer departmentId);
	
	public List<WageRateVo> getWageRatesListBySearch(WageRateVo paramWage);
	
	public List<SimpleObject> getTransactionListForWageRate(Integer customerId, Integer companyId, Integer wageRateId);
	
	//public List<WageRateCurrencyVo> getWageRateCurrencyList(Integer customerId, Integer companyId, Integer wageRateId);
}
