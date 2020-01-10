package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ntranga.CLMS.Dao.JobCodeDao;
import com.Ntranga.CLMS.vo.JobCodeVo;
import com.Ntranga.CLMS.vo.SimpleObject;

@Service("jobCodeService")
public class JobCodeServiceImpl implements JobCodeService {

	@Autowired
	private JobCodeDao jobCodeDao;
	
	@Override
	public String saveJobCode(JobCodeVo paramJobCode) {
		return jobCodeDao.saveJobCode(paramJobCode);
	}

	@Override
	public List<JobCodeVo> getJobCodeById(Integer jobCodeDetailsId) {
		return jobCodeDao.getJobCodeById(jobCodeDetailsId);
	}

	@Override
	public List<JobCodeVo> getJobCodesListBySearch(JobCodeVo paramJobCode) {  
		return jobCodeDao.getJobCodesListBySearch(paramJobCode);
	}
	
	@Override
	public int validateJobcodeAssociationWithWorker(JobCodeVo paramJobCode) {  
		return jobCodeDao.validateJobcodeAssociationWithWorker(paramJobCode);
	}

	@Override
	public List<SimpleObject> getTransactionListForJobCode(Integer customerId,Integer companyId, Integer jobCodeDetailsId) {
		return jobCodeDao.getTransactionListForJobCode(customerId, companyId, jobCodeDetailsId);
	}

}
