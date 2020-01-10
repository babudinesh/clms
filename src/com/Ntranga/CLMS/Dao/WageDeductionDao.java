package com.Ntranga.CLMS.Dao;

import java.util.List;

import com.Ntranga.CLMS.vo.CompanyVo;
import com.Ntranga.CLMS.vo.CustomerVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.TimeRulesDetailsVo;
import com.Ntranga.CLMS.vo.VendorBankDetailsVo;
import com.Ntranga.CLMS.vo.VendorBranchDetailsInfoVo;
import com.Ntranga.CLMS.vo.VendorDetailsVo;
import com.Ntranga.CLMS.vo.VendorVo;
import com.Ntranga.CLMS.vo.WageDeductionVo;
import com.Ntranga.CLMS.vo.WageScaleDetailsVo;
import com.Ntranga.core.CLMS.entities.VendorBankDetails;

public interface WageDeductionDao {

	public List<WageDeductionVo> getDetailsBywageDeductionId(Integer deductionId);


	public List<SimpleObject> getTransactionDatesListForEditingWageDeduction(String deductionCode);

	public List validateWageDeductionCode(String deductionCode,String customerID,String companyId,Integer deductionId);

	public Integer saveOrUpdateWageDeductionDetails(WageDeductionVo wageDeductionVo) ;

	public List<WageDeductionVo> wageDeductionGridDetails(String customerId,String companyId,String Location,String status,String deductionCode);

}
