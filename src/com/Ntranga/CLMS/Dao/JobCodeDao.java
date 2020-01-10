package com.Ntranga.CLMS.Dao;

import java.util.List;

import com.Ntranga.CLMS.vo.JobCodeVo;
import com.Ntranga.CLMS.vo.SimpleObject;

public interface JobCodeDao {

	public String saveJobCode(JobCodeVo paramJobCode);	
	
	public List<JobCodeVo>  getJobCodeById(Integer jobCodeDetailsId);
	
	public List<JobCodeVo> getJobCodesListBySearch(JobCodeVo paramJobCode);
	
	public List<SimpleObject> getTransactionListForJobCode(Integer customerId, Integer companyId, Integer jobCodeDetailsId);

	public int validateJobcodeAssociationWithWorker(JobCodeVo paramJobCode);
}
