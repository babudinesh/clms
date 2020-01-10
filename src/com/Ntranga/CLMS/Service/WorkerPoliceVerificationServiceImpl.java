package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Ntranga.CLMS.Dao.WorkerMedicalExaminationDao;
import com.Ntranga.CLMS.Dao.WorkerPoliceVerificationDao;
import com.Ntranga.CLMS.vo.WorkerDetailsVo;
import com.Ntranga.CLMS.vo.WorkerMedicalExaminationVo;
import com.Ntranga.CLMS.vo.WorkerPoliceVerificationVo;

@Service("workerPoliceVerificationService")
public  class WorkerPoliceVerificationServiceImpl implements WorkerPoliceVerificationService {

	@Autowired
	private WorkerPoliceVerificationDao workerPoliceVerificationDao;

	@Override
	public List<WorkerPoliceVerificationVo> getWorkerPoliceVerificationDetailsByWorkerId(int workerId) {
		// TODO Auto-generated method stub
		return workerPoliceVerificationDao.getWorkerPoliceVerificationDetailsByWorkerId(workerId);
	}

	@Override
	public Integer saveOrUpdateWorkerPoliceVerificationDetails(String name, MultipartFile[]files) {
		// TODO Auto-generated method stub
		return workerPoliceVerificationDao.saveOrUpdateWorkerPoliceVerificationDetails(name,files);
	}

	@Override
	public List<WorkerPoliceVerificationVo> getWorkerAddressByWorkerInfoId(int workerInfoId) {
		// TODO Auto-generated method stub
		return workerPoliceVerificationDao.getWorkerAddressByWorkerInfoId(workerInfoId);
	}

	

	
}
