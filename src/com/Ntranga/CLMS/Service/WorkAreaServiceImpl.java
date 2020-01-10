package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.WorkAreaDao;
import com.Ntranga.CLMS.vo.MWorkSkillVo;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.WorkAreaVo;

@Service("workAreaService")
public class WorkAreaServiceImpl implements WorkAreaService {

	@Autowired
	private  WorkAreaDao workAreaDao;
	
	@Override
	public Integer saveWorkArea(WorkAreaVo paramArea) {
		return workAreaDao.saveWorkArea(paramArea);
	}

	@Override
	public List<WorkAreaVo> getWorkAreaById(Integer workAreaDetailsId) {
		return workAreaDao.getWorkAreaById(workAreaDetailsId);
	}

	@Override
	public List<WorkAreaVo> getWorkAreaListBySearch(WorkAreaVo paramWorkArea) {
		return workAreaDao.getWorkAreaListBySearch(paramWorkArea);
	}

	@Override
	public List<SimpleObject> getTransactionListForWorkArea(Integer customerId, Integer companyId, Integer workAreaId) {
		return workAreaDao.getTransactionListForWorkArea(customerId, companyId, workAreaId);
	}

	@Override
	public List<MWorkSkillVo> getWorkAreaSkillsList(Integer workAreaId) {
		return workAreaDao.getWorkAreaSkillsList(workAreaId);
	}

	@Override
	public List<SimpleObject> getWorkAreaListForDropDown(Integer customerId, Integer companyId, String status) {
		// TODO Auto-generated method stub
		return workAreaDao.getWorkAreaListForDropDown(customerId, companyId, status);
	}

}
