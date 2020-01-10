package com.Ntranga.CLMS.Dao;

import java.util.List;

import com.Ntranga.CLMS.vo.DefineExceptionVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.TimeExceptionRulesVo;
import com.Ntranga.CLMS.vo.TimeRulesDetailsVo;

public interface TimeRulesDao {

	public Integer saveOrUpdateTimeRules(TimeRulesDetailsVo rulesDetailsVo);
	
	public List<SimpleObject> getRuleCodesList(Integer customerId,Integer companyId);
	
	public List<TimeRulesDetailsVo> getDetailsByRuleInfoId(Integer ruleInfoId);
	
	public List<SimpleObject> getTransactionDatesListForEditingTimeRules(Integer timeRulesId);
	
	public Integer saveException(DefineExceptionVo paramException);

	public List<DefineExceptionVo> getExceptionsListBySearch(DefineExceptionVo paramException);

	public List<DefineExceptionVo> getExceptionById(Integer valueOf);

	public List<SimpleObject> getTransactionDatesListForException(Integer customerId, Integer companyId, Integer exceptionUniqueId);

	public Integer validateExceptionCode(DefineExceptionVo paramException);
	
	public List<SimpleObject> getExceptionsDropDown(Integer customerId, Integer companyId, Integer countryId);
	
	public Integer validateTimeRuleCode(TimeRulesDetailsVo paramTime);

	public List<TimeRulesDetailsVo> getTimeRulesListBySearch(TimeRulesDetailsVo paramTime);
	
	public List<TimeExceptionRulesVo> getTimeExceptionById(TimeExceptionRulesVo paramException);

	public Integer saveTimeException(TimeExceptionRulesVo paramException);

	public List<SimpleObject> getTransactionDatesListForTimeException(Integer customerId, Integer companyId, Integer exceptionUniqueId);

	
}
