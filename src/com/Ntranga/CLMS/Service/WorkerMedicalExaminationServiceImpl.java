package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Ntranga.CLMS.Dao.WorkerMedicalExaminationDao;
import com.Ntranga.CLMS.vo.WorkerMedicalExaminationVo;

@Service("workerMedicalExaminationService")
public  class WorkerMedicalExaminationServiceImpl implements WorkerMedicalExaminationService {

	@Autowired
	private WorkerMedicalExaminationDao workerMedicalExaminationDao;

	@Override
	public List<WorkerMedicalExaminationVo> getWorkerMedicalExaminationDetailsByWorkerId(int workerId) {
		// TODO Auto-generated method stub
		return workerMedicalExaminationDao.getWorkerMedicalExaminationDetailsByWorkerId(workerId);
	}

	@Override
	public Integer saveOrUpdateWorkerMedicalExaminationDetails(String name, MultipartFile[] files) {
		// TODO Auto-generated method stub
		return workerMedicalExaminationDao.saveOrUpdateWorkerMedicalExaminationDetails(name,files);
	}

	
}
