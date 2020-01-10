package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.DefineOverTimeControlDetailsDao;
import com.Ntranga.CLMS.vo.DefineOverTimeControlDetailsInfoVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.WorkerBadgeGenerationVo;




@Service("defineOverTimeControlDetailsService")
public class DefineOverTimeControlDetailsServiceImpl implements DefineOverTimeControlDetailsService {

	@Autowired
	private  DefineOverTimeControlDetailsDao defineOverTimeControlDetailsDao;

	@Override
	public List<DefineOverTimeControlDetailsInfoVo> getOverTimeDetailsForGrid(
			DefineOverTimeControlDetailsInfoVo overTimeVo) {
		// TODO Auto-generated method stub
		return defineOverTimeControlDetailsDao.getOverTimeDetailsForGrid(overTimeVo);
	}

	@Override
	public Integer saveOrUpdateOverTime(DefineOverTimeControlDetailsInfoVo overTimeVo) {
		// TODO Auto-generated method stub
		return defineOverTimeControlDetailsDao.saveOrUpdateOverTime(overTimeVo);
	}

	@Override
	public List<DefineOverTimeControlDetailsInfoVo> getDefineOverTimeDetailsByInfoId(
			DefineOverTimeControlDetailsInfoVo overTimeVo) {
		// TODO Auto-generated method stub
		return defineOverTimeControlDetailsDao.getDefineOverTimeDetailsByInfoId(overTimeVo);
	}

	@Override
	public List<SimpleObject> getOverTimeNamesByCompanyId(Integer customerDetailsId, Integer companyDetailsId) {
		// TODO Auto-generated method stub
		return defineOverTimeControlDetailsDao.getOverTimeNamesByCompanyId(customerDetailsId, companyDetailsId);
	}

	@Override
	public List<SimpleObject> getTransactionDatesListForOverTimeHistory(DefineOverTimeControlDetailsInfoVo overTimeVo) {
		// TODO Auto-generated method stub
		return defineOverTimeControlDetailsDao.getTransactionDatesListForOverTimeHistory(overTimeVo);
	}

	
	
}
