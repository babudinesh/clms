package com.Ntranga.CLMS.Service;

import java.util.List;

import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.WageDeductionVo;

public interface PfRulesService {

	public List<WageDeductionVo> getDetailsBywageDeductionId(Integer customerId,Integer companyId,Integer pfrulesId);

	public List<SimpleObject> getTransactionDatesListForEditingWageDeduction(Integer customerId,Integer companyId);

	public Integer saveOrUpdateWageDeductionDetails(WageDeductionVo wageDeductionVo) ;

}
