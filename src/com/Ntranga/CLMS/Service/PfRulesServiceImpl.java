package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.PfRulesDao;
import com.Ntranga.CLMS.Dao.WageDeductionDao;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.WageDeductionVo;




@Service("pfRulesService")
public class PfRulesServiceImpl implements PfRulesService {

	@Autowired
	private  PfRulesDao pfRulesDao;

	

	@Override
	public Integer saveOrUpdateWageDeductionDetails(WageDeductionVo WageDeductionVo) {
		// TODO Auto-generated method stub
		return pfRulesDao.saveOrUpdateWageDeductionDetails(WageDeductionVo);
	}

	@Override
	public List<WageDeductionVo> getDetailsBywageDeductionId(Integer customerId,Integer companyId,Integer pfrulesId) {
		// TODO Auto-generated method stub
		return pfRulesDao.getDetailsBywageDeductionId( customerId, companyId, pfrulesId);
	}

	@Override
	public List<SimpleObject> getTransactionDatesListForEditingWageDeduction(Integer customerId,Integer companyId) {
		// TODO Auto-generated method stub
		return pfRulesDao.getTransactionDatesListForEditingWageDeduction(customerId,companyId);
	}


	
}
