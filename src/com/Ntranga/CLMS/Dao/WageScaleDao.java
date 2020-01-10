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
import com.Ntranga.CLMS.vo.WageScaleDetailsVo;
import com.Ntranga.core.CLMS.entities.VendorBankDetails;

public interface WageScaleDao {

	public List<WageScaleDetailsVo> wageScaleGridDetails(String customerId,String companyId,String Location,String status,String wageScaleCode,String wageScaleNmae);

	public Integer saveOrUpdateWageScaleDetails(WageScaleDetailsVo wageScaleDetailsVo);

	public List<WageScaleDetailsVo> getDetailsBywageScaleId(Integer wageScaleId);

	public List<SimpleObject> getTransactionDatesListForEditingWageScale(String wageScaleCode);

	public List validateWageScaleCode(String wageScaleCode,String customerID,String companyId,Integer wageScaleId);
	
	public List<SimpleObject> getWageScaleServices(String customerId, String companyId);

	public List<SimpleObject> getcomanyCurrency(Integer companyId);

}
