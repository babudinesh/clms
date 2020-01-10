package com.Ntranga.CLMS.Dao;

import java.util.List;

import com.Ntranga.CLMS.vo.BonusRulesVo;
import com.Ntranga.CLMS.vo.DefineBudgetDetailsVo;
import com.Ntranga.CLMS.vo.DefineProfessionalTaxInfoVo;
import com.Ntranga.CLMS.vo.DefineProfessionalTaxVo;
import com.Ntranga.CLMS.vo.DefineWorkerGroupVo;
import com.Ntranga.CLMS.vo.LWFRulesInfoVo;
import com.Ntranga.CLMS.vo.LWFRulesVo;
import com.Ntranga.CLMS.vo.SimpleObject;

public interface StatutorySetupsDao {
	
	/*** Bonus start ****/
	
	public Integer saveBonusRulesDetails(BonusRulesVo bonusRulesVo);
	
	public BonusRulesVo getBonusRulesDetails(BonusRulesVo bonusRulesVo);
	
	public List<SimpleObject> getTransactionDatesForBonusHistory(BonusRulesVo bonusRulesVo);
	
	public BonusRulesVo getBonusRecordByBonusRulesId(String bonusRulesId);
	
	/*** Bonus end ****/
	
	/***LWF Rules Start***/
	
	public Integer saveLWFRules(LWFRulesVo paramRules);
	
	public List<LWFRulesVo> getLWFRulesBySearch(LWFRulesVo paramRules);
	
	public List<SimpleObject> getTransactionListForLWFRules(Integer customerId, Integer companyId, Integer lwfUniqueId );
	
	public List<LWFRulesVo> getLWFRuleByRuleId(Integer lwfRuleId);
	
	public List<LWFRulesInfoVo> getLWFRulesListByRuleId(Integer lwfRuleId);
	
	/***LWF Rules End***/
	
	
	/***Professional Tax Rules Start***/
	
	public Integer saveProfessionalTax(DefineProfessionalTaxVo paramRules);
	
	public List<DefineProfessionalTaxVo> getProfessionalTaxListBySearch(DefineProfessionalTaxVo paramRules);
	
	public List<SimpleObject> getTransactionListForProfessionalTax(Integer customerId, Integer companyId, Integer professionalTaxId );
	
	public List<DefineProfessionalTaxVo> getProfessionalTaxByProfessionalTaxId(Integer professionalTaxId);
	
	public List<DefineProfessionalTaxInfoVo> getPTRulesListByProfessionalTaxId(Integer professionalTaxId);
	
	/***Professional Tax Rules End***/	
	
	/*** Budget  Start ***/
	
	public List<DefineBudgetDetailsVo> getBudgetDetailsByBudgetDetailsId(Integer budgetDetailsId);
	
	public Integer saveBudget(DefineBudgetDetailsVo paramBudget);
	
	public List<SimpleObject> getTransactionListForBudget(DefineBudgetDetailsVo paramBudget);
	
	public List<DefineBudgetDetailsVo> getBudgetListBySearch(DefineBudgetDetailsVo paramBudget);
	
	public Integer validateBudgetCodeandYear(Integer customerId, Integer companyId, String budgetCode, String budgetYear);
	
	/*** Budget  End ***/
	
	/***** Worker Group Start *****/
	
	public List<DefineWorkerGroupVo> getWorkGroupBySearch(DefineWorkerGroupVo paramWorkerGroup);
	
	public Integer saveWorkerGroup(DefineWorkerGroupVo paramWorkerGroup);
	
	public List<DefineWorkerGroupVo> getWorkerGroupById(Integer workerGroupId);
	
	public List<SimpleObject> getTransactionDatesListForWorkerGroup(Integer customerId, Integer companyId, Integer uniqueId);
	
	public Integer validateWorkerGroupCode(Integer customerId,Integer companyId, String groupCode);
	
	/***** Worker Group End   *****/
}
