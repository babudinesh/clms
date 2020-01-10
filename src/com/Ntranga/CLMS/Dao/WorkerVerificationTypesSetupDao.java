package com.Ntranga.CLMS.Dao;

import java.util.List;

import com.Ntranga.CLMS.vo.WorkerVerificationTypesSetupVo;

public interface WorkerVerificationTypesSetupDao {

	public List<WorkerVerificationTypesSetupVo> getWorkerVerificationTypesSetupsByWorkerVerificationTypesSetupId(int workerVerificationTypesSetupId);
	
	public Integer saveOrUpdateVerificationTypesSetups(WorkerVerificationTypesSetupVo setupVo) ;
	
	public List<WorkerVerificationTypesSetupVo> getWorkerVerificationTypesSetupsGridData(WorkerVerificationTypesSetupVo setupVo);
	
	
}
