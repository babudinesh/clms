package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.TimeRulesDao;
import com.Ntranga.CLMS.vo.DefineExceptionVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.TimeExceptionRulesVo;
import com.Ntranga.CLMS.vo.TimeRulesDetailsVo;

@Service("timeRulesService")
public class TimeRulesServiceImpl implements TimeRulesService {

	@Autowired
	private  TimeRulesDao timeRuleDao;

	@Override
	public Integer saveOrUpdateTimeRules(TimeRulesDetailsVo rulesDetailsVo) {
		return timeRuleDao.saveOrUpdateTimeRules(rulesDetailsVo);
	}

	public List<SimpleObject> getRuleCodesList(Integer customerId,Integer companyId){
		return timeRuleDao.getRuleCodesList(customerId,companyId);
	}
	
	public List<TimeRulesDetailsVo> getDetailsByRuleInfoId(Integer ruleInfoId){
		return timeRuleDao.getDetailsByRuleInfoId(ruleInfoId);
	}

	@Override
	public List<SimpleObject> getTransactionDatesListForEditingTimeRules(Integer timeRulesId) {
		return timeRuleDao.getTransactionDatesListForEditingTimeRules(timeRulesId);
	}
	
	@Override
	public Integer saveException(DefineExceptionVo paramException) {
		return timeRuleDao.saveException(paramException);
	}

	@Override
	public List<DefineExceptionVo> getExceptionsListBySearch(DefineExceptionVo paramException) {
		return timeRuleDao.getExceptionsListBySearch(paramException);
	}

	@Override
	public List<DefineExceptionVo> getExceptionById(Integer valueOf) {
		return timeRuleDao.getExceptionById(valueOf);
	}

	@Override
	public List<SimpleObject> getTransactionDatesListForException(Integer customerId, Integer companyId, Integer exceptionUniqueId) {
		return timeRuleDao.getTransactionDatesListForException(customerId, companyId, exceptionUniqueId);
	}

	@Override
	public Integer validateExceptionCode(DefineExceptionVo paramException) {
		return timeRuleDao.validateExceptionCode(paramException);
	}
	
	@Override
	public List<SimpleObject> getExceptionsDropDown(Integer customerId,Integer companyId, Integer countryId) {
		return timeRuleDao.getExceptionsDropDown(customerId, companyId, countryId);
	}

	@Override
	public Integer validateTimeRuleCode(TimeRulesDetailsVo paramTime) {
		return timeRuleDao.validateTimeRuleCode(paramTime);
	}

	@Override
	public List<TimeRulesDetailsVo> getTimeRulesListBySearch(TimeRulesDetailsVo paramTime) {
		return timeRuleDao.getTimeRulesListBySearch(paramTime);
	}

	@Override
	public List<TimeExceptionRulesVo> getTimeExceptionById(TimeExceptionRulesVo paramException) {
		return timeRuleDao.getTimeExceptionById(paramException);
	}

	@Override
	public Integer saveTimeException(TimeExceptionRulesVo paramException) {
		return timeRuleDao.saveTimeException(paramException);
	}

	@Override
	public List<SimpleObject> getTransactionDatesListForTimeException(Integer customerId, Integer companyId, Integer exceptionUniqueId) {
		return timeRuleDao.getTransactionDatesListForTimeException(customerId, companyId, exceptionUniqueId);
	}

	
}
