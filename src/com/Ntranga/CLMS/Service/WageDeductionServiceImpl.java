package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.WageDeductionDao;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.WageDeductionVo;




@Service("wageDeductionService")
public class WageDeductionServiceImpl implements WageDeductionService {

	@Autowired
	private  WageDeductionDao wageDeductionDao;

	@Override
	public List<WageDeductionVo> wageDeductionGridDetails(String customerId,String companyId,String Location,String status,String deductionCode) {
		// TODO Auto-generated method stub
		return wageDeductionDao.wageDeductionGridDetails(customerId, companyId, Location, status, deductionCode);
	}

	@Override
	public Integer saveOrUpdateWageDeductionDetails(WageDeductionVo WageDeductionVo) {
		// TODO Auto-generated method stub
		return wageDeductionDao.saveOrUpdateWageDeductionDetails(WageDeductionVo);
	}

	@Override
	public List<WageDeductionVo> getDetailsBywageDeductionId(Integer deductionId) {
		// TODO Auto-generated method stub
		return wageDeductionDao.getDetailsBywageDeductionId(deductionId);
	}

	@Override
	public List<SimpleObject> getTransactionDatesListForEditingWageDeduction(String deductionCode) {
		// TODO Auto-generated method stub
		return wageDeductionDao.getTransactionDatesListForEditingWageDeduction(deductionCode);
	}

	@Override
	public List validateWageDeductionCode(String deductionCode,String customerID,String companyId,Integer deductionId) {
		// TODO Auto-generated method stub
		return wageDeductionDao.validateWageDeductionCode(deductionCode, customerID, companyId,deductionId);
	}

	
}
