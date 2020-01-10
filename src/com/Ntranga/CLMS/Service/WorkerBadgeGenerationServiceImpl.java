package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.WorkerBadgeGenerationDao;
import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.WorkerBadgeGenerationVo;




@Service("workerBadgeGenerationService")
public class WorkerBadgeGenerationServiceImpl implements WorkerBadgeGenerationService {

	@Autowired
	private  WorkerBadgeGenerationDao workerBadgeGenerationDao;

	@Override
	public List<SimpleObject> getWorkersByVendorId(WorkerBadgeGenerationVo workerBadgeVo) {
		// TODO Auto-generated method stub
		return workerBadgeGenerationDao.getWorkersByVendorId(workerBadgeVo);
	}

	@Override
	public List<WorkerBadgeGenerationVo> getWorkerDetailsForGrid(WorkerBadgeGenerationVo workerBadgeVo) {
		// TODO Auto-generated method stub
		return workerBadgeGenerationDao.getWorkerDetailsForGrid(workerBadgeVo);
	}

	@Override
	public Integer saveOrUpdateWorkerBadge(WorkerBadgeGenerationVo workerBadgeVo) {
		// TODO Auto-generated method stub
		return workerBadgeGenerationDao.saveOrUpdateWorkerBadge(workerBadgeVo);
	}

	@Override
	public List<WorkerBadgeGenerationVo> getWorkerBadgeRecordByBadgeId(WorkerBadgeGenerationVo workerBadgeVo) {
		// TODO Auto-generated method stub
		return workerBadgeGenerationDao.getWorkerBadgeRecordByBadgeId(workerBadgeVo);
	}
	
	@Override
	public boolean validateVendorBadgeCode(WorkerBadgeGenerationVo paramVendor){
		// TODO Auto-generated method stub
				return workerBadgeGenerationDao.validateVendorBadgeCode(paramVendor);
	}

	
}
