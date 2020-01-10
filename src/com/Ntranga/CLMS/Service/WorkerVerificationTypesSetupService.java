package com.Ntranga.CLMS.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.Ntranga.CLMS.vo.WorkerMedicalExaminationVo;
import com.Ntranga.CLMS.vo.WorkerVerificationTypesSetupVo;

public interface WorkerVerificationTypesSetupService {

	public List<WorkerVerificationTypesSetupVo> getWorkerVerificationTypesSetupsByWorkerVerificationTypesSetupId(int workerVerificationTypesSetupId);
	
	public Integer saveOrUpdateVerificationTypesSetups(WorkerVerificationTypesSetupVo setupVo) ;

	public List<WorkerVerificationTypesSetupVo> getWorkerVerificationTypesSetupsGridData(WorkerVerificationTypesSetupVo setupVo);
	

}
