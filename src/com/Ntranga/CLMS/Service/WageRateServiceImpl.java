package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.WageRateDao;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.WageRateVo;

@Service("wageRateService")
public class WageRateServiceImpl implements WageRateService {

	@Autowired
	WageRateDao wageRatedao;
	
	@Override
	public Integer saveWageRate(WageRateVo paramWage) {
		return wageRatedao.saveWageRate(paramWage);
	}

	@Override
	public List<WageRateVo> getWageRateById(Integer wageRateDetailsId) {
		return wageRatedao.getWageRateById(wageRateDetailsId);
	}

	@Override
	public List<WageRateVo> getWageRatesListBySearch(WageRateVo paramWage) {
		return wageRatedao.getWageRatesListBySearch(paramWage);
	}

	@Override
	public List<SimpleObject> getTransactionListForWageRate(Integer customerId,Integer companyId, Integer wageRateId) {
		return wageRatedao.getTransactionListForWageRate(customerId, companyId, wageRateId);
	}

	/*@Override
	public List<WageRateCurrencyVo> getWageRateCurrencyList(Integer customerId, Integer companyId, Integer wageRateId) {
		return wageRatedao.getWageRateCurrencyList(customerId, companyId, wageRateId);
	}*/
	
}
