package com.Ntranga.CLMS.Dao;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.Ntranga.CLMS.vo.WorkerMedicalExaminationVo;

public interface WorkerMedicalExaminationDao {

	
	public List<WorkerMedicalExaminationVo> getWorkerMedicalExaminationDetailsByWorkerId(int workerId);

	public Integer saveOrUpdateWorkerMedicalExaminationDetails(String name, MultipartFile[] files);
	
}

