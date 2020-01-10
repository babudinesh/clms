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

public interface PfRulesDao {

	public List<WageDeductionVo> getDetailsBywageDeductionId(Integer customerId,Integer companyId,Integer pfrulesId);

	public List<SimpleObject> getTransactionDatesListForEditingWageDeduction(Integer customerId,Integer companyId);

	public Integer saveOrUpdateWageDeductionDetails(WageDeductionVo wageDeductionVo) ;


}
