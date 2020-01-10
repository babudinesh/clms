package com.Ntranga.CLMS.Service;

import java.util.List;

import com.Ntranga.CLMS.vo.JobCodeVo;
import com.Ntranga.CLMS.vo.SimpleObject;


public interface JobCodeService {

	public String saveJobCode(JobCodeVo paramJobCode);	
	
	public List<JobCodeVo>  getJobCodeById(Integer jobCodeDetailsId);
	
	public List<JobCodeVo> getJobCodesListBySearch(JobCodeVo paramJobCode); 
	
	public int validateJobcodeAssociationWithWorker(JobCodeVo paramJobCode);
	
	public List<SimpleObject> getTransactionListForJobCode(Integer customerId, Integer companyId, Integer jobCodeDetailsId);
}
