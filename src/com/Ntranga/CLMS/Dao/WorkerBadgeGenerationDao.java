package com.Ntranga.CLMS.Dao;

import java.util.List;

import com.Ntranga.CLMS.vo.SimpleObject;
import com.Ntranga.CLMS.vo.WorkerBadgeGenerationVo;

public interface WorkerBadgeGenerationDao {

	public List<SimpleObject> getWorkersByVendorId(WorkerBadgeGenerationVo workerBadgeVo);

	public List<WorkerBadgeGenerationVo> getWorkerDetailsForGrid(WorkerBadgeGenerationVo workerBadgeVo);

	public Integer saveOrUpdateWorkerBadge(WorkerBadgeGenerationVo workerBadgeVo);

	public List<WorkerBadgeGenerationVo> getWorkerBadgeRecordByBadgeId(WorkerBadgeGenerationVo workerBadgeVo);

	public boolean validateVendorBadgeCode(WorkerBadgeGenerationVo paramVendor);
	
	

}
