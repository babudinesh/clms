package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.WorkerVerificationTypesSetupDao;
import com.Ntranga.CLMS.vo.WorkerVerificationTypesSetupVo;

@Service("workerVerificationTypeSetupService")
public  class WorkerVerificationTypeSetupServiceImpl implements WorkerVerificationTypesSetupService {

	@Autowired
	private WorkerVerificationTypesSetupDao workerVerificationTypeSetupDao;

	public List<WorkerVerificationTypesSetupVo> getWorkerVerificationTypesSetupsByWorkerVerificationTypesSetupId(int workerVerificationTypesSetupId) {
		return workerVerificationTypeSetupDao.getWorkerVerificationTypesSetupsByWorkerVerificationTypesSetupId(workerVerificationTypesSetupId);
	}
	
	public Integer saveOrUpdateVerificationTypesSetups(WorkerVerificationTypesSetupVo setupVo) {
		return workerVerificationTypeSetupDao.saveOrUpdateVerificationTypesSetups(setupVo);
	}

	@Override
	public List<WorkerVerificationTypesSetupVo> getWorkerVerificationTypesSetupsGridData(
			WorkerVerificationTypesSetupVo setupVo) {
		// TODO Auto-generated method stub
		return workerVerificationTypeSetupDao.getWorkerVerificationTypesSetupsGridData(setupVo);
	}

	
}
