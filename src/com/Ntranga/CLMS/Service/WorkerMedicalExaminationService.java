package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.Ntranga.CLMS.vo.WorkerMedicalExaminationVo;

public interface WorkerMedicalExaminationService {

	public List<WorkerMedicalExaminationVo> getWorkerMedicalExaminationDetailsByWorkerId(int workerId);

	public Integer saveOrUpdateWorkerMedicalExaminationDetails(String name, MultipartFile[] files);

	

}
