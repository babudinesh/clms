package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.StatutorySetupsDao;
import com.Ntranga.CLMS.vo.BonusRulesVo;
import com.Ntranga.CLMS.vo.DefineBudgetDetailsVo;
import com.Ntranga.CLMS.vo.DefineProfessionalTaxInfoVo;
import com.Ntranga.CLMS.vo.DefineProfessionalTaxVo;
import com.Ntranga.CLMS.vo.DefineWorkerGroupVo;
import com.Ntranga.CLMS.vo.LWFRulesInfoVo;
import com.Ntranga.CLMS.vo.LWFRulesVo;
import com.Ntranga.CLMS.vo.SimpleObject;

@Service("statutorySetupsService")
public class StatutorySetupsServiceImpl implements StatutorySetupsService {

	@Autowired
	private StatutorySetupsDao statutorySetupsDao;
	
	
	@Override
	public Integer saveBonusRulesDetails(BonusRulesVo bonusRulesVo) {
		// TODO Auto-generated method stub
		return statutorySetupsDao.saveBonusRulesDetails(bonusRulesVo);
	}


	@Override
	public BonusRulesVo getBonusRulesDetails(BonusRulesVo bonusRulesVo) {
		// TODO Auto-generated method stub
		return statutorySetupsDao.getBonusRulesDetails(bonusRulesVo);
	}


	@Override
	public List<SimpleObject> getTransactionDatesForBonusHistory(BonusRulesVo bonusRulesVo) {
		// TODO Auto-generated method stub
		return statutorySetupsDao.getTransactionDatesForBonusHistory(bonusRulesVo);
	}


	@Override
	public BonusRulesVo getBonusRecordByBonusRulesId(String bonusRulesId) {
		// TODO Auto-generated method stub
		return statutorySetupsDao.getBonusRecordByBonusRulesId(bonusRulesId);
	}


	@Override
	public Integer saveLWFRules(LWFRulesVo paramRules) {
		return statutorySetupsDao.saveLWFRules(paramRules);
	}


	@Override
	public List<LWFRulesVo> getLWFRulesBySearch(LWFRulesVo paramRules) {
		return statutorySetupsDao.getLWFRulesBySearch(paramRules);
	}


	@Override
	public List<SimpleObject> getTransactionListForLWFRules(Integer customerId, Integer companyId, Integer lwfUniqueId) {
		return statutorySetupsDao.getTransactionListForLWFRules(customerId, companyId, lwfUniqueId);
	}


	@Override
	public List<LWFRulesVo> getLWFRuleByRuleId(Integer lwfRuleId) {
		return statutorySetupsDao.getLWFRuleByRuleId(lwfRuleId);
	}

	@Override
	public List<LWFRulesInfoVo> getLWFRulesListByRuleId(Integer lwfRuleId) {
		return statutorySetupsDao.getLWFRulesListByRuleId(lwfRuleId);
	}


	@Override
	public Integer saveProfessionalTax(DefineProfessionalTaxVo paramRules) {
		return statutorySetupsDao.saveProfessionalTax(paramRules);
	}


	@Override
	public List<DefineProfessionalTaxVo> getProfessionalTaxListBySearch(DefineProfessionalTaxVo paramRules) {
		return statutorySetupsDao.getProfessionalTaxListBySearch(paramRules);
	}


	@Override
	public List<SimpleObject> getTransactionListForProfessionalTax(Integer customerId, Integer companyId, Integer professionalTaxId) {
		return statutorySetupsDao.getTransactionListForProfessionalTax(customerId, companyId, professionalTaxId);
	}


	@Override
	public List<DefineProfessionalTaxVo> getProfessionalTaxByProfessionalTaxId(Integer professionalTaxId) {
		return statutorySetupsDao.getProfessionalTaxByProfessionalTaxId(professionalTaxId);
	}

	@Override
	public List<DefineProfessionalTaxInfoVo> getPTRulesListByProfessionalTaxId(Integer professionalTaxId) {
		return statutorySetupsDao.getPTRulesListByProfessionalTaxId(professionalTaxId);
	}


	@Override
	public List<DefineBudgetDetailsVo> getBudgetDetailsByBudgetDetailsId(Integer budgetDetailsId) {
		return statutorySetupsDao.getBudgetDetailsByBudgetDetailsId(budgetDetailsId);
	}


	@Override
	public Integer saveBudget(DefineBudgetDetailsVo paramBudget) {
		return statutorySetupsDao.saveBudget(paramBudget);
	}


	@Override
	public List<SimpleObject> getTransactionListForBudget(DefineBudgetDetailsVo paramBudget) {
		return statutorySetupsDao.getTransactionListForBudget(paramBudget);
	}


	@Override
	public List<DefineBudgetDetailsVo> getBudgetListBySearch(DefineBudgetDetailsVo paramBudget) {
		return statutorySetupsDao.getBudgetListBySearch(paramBudget);
	}


	@Override
	public Integer validateBudgetCodeandYear(Integer customerId, Integer companyId, String budgetCode, String budgetYear) {
		return statutorySetupsDao.validateBudgetCodeandYear(customerId, companyId, budgetCode, budgetYear);
	}


	@Override
	public List<DefineWorkerGroupVo> getWorkGroupBySearch(DefineWorkerGroupVo paramWorkerGroup) {
		return statutorySetupsDao.getWorkGroupBySearch(paramWorkerGroup);
	}


	@Override
	public Integer saveWorkerGroup(DefineWorkerGroupVo paramWorkerGroup) {
		return statutorySetupsDao.saveWorkerGroup(paramWorkerGroup);
	}


	@Override
	public List<DefineWorkerGroupVo> getWorkerGroupById(Integer workerGroupId) {
		return statutorySetupsDao.getWorkerGroupById(workerGroupId);
	}


	@Override
	public List<SimpleObject> getTransactionDatesListForWorkerGroup(Integer customerId, Integer companyId, Integer uniqueId) {
		return statutorySetupsDao.getTransactionDatesListForWorkerGroup(customerId, companyId, uniqueId);
	}


	@Override
	public Integer validateWorkerGroupCode(Integer customerId,Integer companyId, String groupCode) {
		return statutorySetupsDao.validateWorkerGroupCode(customerId, companyId, groupCode);
	}
}
