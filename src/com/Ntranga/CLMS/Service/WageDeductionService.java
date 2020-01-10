package com.Ntranga.CLMS.Service;

import java.util.List;

import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.WageDeductionVo;

public interface WageDeductionService {

	public List<WageDeductionVo> getDetailsBywageDeductionId(Integer deductionId);


	public List<SimpleObject> getTransactionDatesListForEditingWageDeduction(String deductionCode);

	public List validateWageDeductionCode(String deductionCode,String customerID,String companyId,Integer deductionId);

	public Integer saveOrUpdateWageDeductionDetails(WageDeductionVo wageDeductionVo) ;

	public List<WageDeductionVo> wageDeductionGridDetails(String customerId,String companyId,String Location,String status,String deductionCode);

}
