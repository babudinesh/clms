package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.TimeRulesDao;
import com.Ntranga.CLMS.Dao.WageScaleDao;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.TimeRulesDetailsVo;
import com.Ntranga.CLMS.vo.WageScaleDetailsVo;




@Service("wageScaleService")
public class WageScaleServiceImpl implements WageScaleService {

	@Autowired
	private  WageScaleDao wageScaleDao;

	@Override
	public List<WageScaleDetailsVo> wageScaleGridDetails(String customerId, String companyId, String Location,
			String status, String wageScaleCode, String wageScaleNmae) {
		// TODO Auto-generated method stub
		return wageScaleDao.wageScaleGridDetails(customerId, companyId, Location, status, wageScaleCode, wageScaleNmae);
	}

	@Override
	public Integer saveOrUpdateWageScaleDetails(WageScaleDetailsVo wageScaleDetailsVo) {
		// TODO Auto-generated method stub
		return wageScaleDao.saveOrUpdateWageScaleDetails(wageScaleDetailsVo);
	}

	@Override
	public List<WageScaleDetailsVo> getDetailsBywageScaleId(Integer wageScaleId) {
		// TODO Auto-generated method stub
		return wageScaleDao.getDetailsBywageScaleId(wageScaleId);
	}

	@Override
	public List<SimpleObject> getTransactionDatesListForEditingWageScale(String wageScaleCode) {
		// TODO Auto-generated method stub
		return wageScaleDao.getTransactionDatesListForEditingWageScale(wageScaleCode);
	}

	@Override
	public List validateWageScaleCode(String wageScaleCode, String customerID, String companyId,Integer wageScaleId) {
		// TODO Auto-generated method stub
		return wageScaleDao.validateWageScaleCode(wageScaleCode, customerID, companyId,wageScaleId);
	}

	@Override
	public List<SimpleObject> getWageScaleServices(String customerId, String companyId) {
		// TODO Auto-generated method stub
		return wageScaleDao.getWageScaleServices(customerId, companyId);
	}
	
	
	public List<SimpleObject> getcomanyCurrency(Integer companyId){
		// TODO Auto-generated method stub
				return wageScaleDao.getcomanyCurrency(companyId);
		
	}

	
}
